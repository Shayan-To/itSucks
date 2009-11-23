/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 * 
 * $Id: DownloadJob.java,v 1.1 2006-12-03 19:31:43 olli Exp $
 * Created on 03.03.2006
 */

package de.phleisch.app.itsucks.job.download.impl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.phleisch.app.itsucks.io.FileManager;
import de.phleisch.app.itsucks.io.Metadata;
import de.phleisch.app.itsucks.io.ResumeUrlDataRetriever;
import de.phleisch.app.itsucks.io.UrlDataRetriever;
import de.phleisch.app.itsucks.io.impl.ProgressInputStream;
import de.phleisch.app.itsucks.job.Job;
import de.phleisch.app.itsucks.job.JobParameter;
import de.phleisch.app.itsucks.job.download.DownloadJob;
import de.phleisch.app.itsucks.job.impl.AbstractJob;
import de.phleisch.app.itsucks.processing.AbortProcessingException;
import de.phleisch.app.itsucks.processing.DataProcessor;
import de.phleisch.app.itsucks.processing.DataProcessorChain;
import de.phleisch.app.itsucks.processing.ProcessingException;
import de.phleisch.app.itsucks.processing.download.impl.PersistenceProcessor;
import de.phleisch.app.itsucks.processing.impl.DataProcessorManager;
import de.phleisch.app.itsucks.processing.impl.SeekDataProcessorWrapper;


/**
 * This is the implementation of a job for downloading files.
 * It contains a URL to the file or directory to be downloaded.
 * 
 * @author olli
 *
 */
public class UrlDownloadJob extends AbstractJob implements DownloadJob, Cloneable {

	private static final long serialVersionUID = 1714294563019859104L;

	/**
	 * Constant used to determine when the progress property has changed.
	 * The old value in the PropertyChangeEvent will be the old progress
	 * and the new value will be the new progress.
	 * 
	 * @see #getProgress
	 * @see #addPropertyChangeListener
	 */
	public static final String JOB_PROGRESS_PROPERTY = "Progress";
	
	public static final String JOB_PARAMETER_SKIP_DOWNLOADED_FILE = "not implemented";
	
	public static enum RetryBehaviour {
		DIRECTLY_WAIT_FOR_RETRY_TIMEOUT,
		MOVE_JOB_BACK_INTO_QUEUE
	};
	
	
	private static Log mLog = LogFactory.getLog(UrlDownloadJob.class);
	
	protected boolean mSaveToDisk = true;
	protected boolean mAbort = false; //indicates if the download has been aborted
	protected File mSavePath = null;
	
	protected URL mUrl;
	protected WeakReference<UrlDownloadJob> mParent = null;
	protected int mDepth = 0;
	protected int mMaxRetryCount = 3;
	protected int mTryCount = 0;
	protected transient DataProcessorManager mDataProcessorManager;
	protected transient DataRetrieverManager mDataRetrieverManager;
	
	protected transient UrlDataRetriever mDataRetriever;
	protected transient ResumeUrlDataRetriever mFileResumeRetriever;
	protected transient ProgressInputStream mProgressInputStream;
	
	protected float mProgress = -1;
	protected long mBytesDownloaded = -1;
	protected transient Metadata mMetadata = null;

	protected RetryBehaviour mRetryBehaviour = 
		RetryBehaviour.DIRECTLY_WAIT_FOR_RETRY_TIMEOUT; // default
	protected long mWaitUntil = 0;

	public UrlDownloadJob() {
		super();
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.chaoscrawler.refactoring.Job#run()
	 */
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.job.download.impl.DownloadJob#run()
	 */
	@Override
	public void run() throws Exception {
	
		try {
			download();
		} catch (Exception e) {
			
			this.setState(STATE_ERROR);
			
			//check if the exception can be ignored because we aborted the retrieval
			if(mDataRetriever != null 
					&& mDataRetriever.getResultCode() == UrlDataRetriever.RESULT_RETRIEVAL_ABORTED) {
				
				mLog.info("Aborting download caused exception. URL: " + mUrl, e);
				
			} else {
				mLog.error("Error downloading url: " + mUrl, e);
				
				throw e;
			}
		} finally {
			try {
				if(mDataRetriever != null) {
					mDataRetriever.disconnect();
					mDataRetriever = null;
					mFileResumeRetriever = null;
					mProgressInputStream = null;
				}
			} catch (Exception e) {
				mLog.warn("Error occured while trying to disconnect", e);
			}
		}
		
	}
	
	protected void download() throws IOException {
		URL url = mUrl;
		String protocol = url.getProtocol();
		
		DataRetrieverFactory retrieverFactoryForProtocol = 
			mDataRetrieverManager.getRetrieverFactoryForProtocol(protocol);
		
		//check if an data retriever is available
		if(retrieverFactoryForProtocol == null) {
			mLog.warn("Protocol not supported: '" + protocol + "', job aborted. - " + this);
			setState(Job.STATE_IGNORED);
			
			return;
		}
		
		//create data retriever
		mDataRetriever = retrieverFactoryForProtocol.createDataRetriever(
			url, getGroupContext(), getParameterList());
		
//		//check if this file could be resumed
//		if(isSaveToDisk()) {
//			
//			FileManager fileManager = new FileManager(this.getSavePath());
//			File file = fileManager.buildSavePath(url);
//			
//			if(file.exists()) {
//			
//				mLog.info("Try to resume job: " + this);
//				
//				//ok, it seems the file already exists partially/completely
//				//try to resume the file
//				mFileResumeRetriever = retrieverFactoryForProtocol.createResumeDataRetriever(mDataRetriever, file);
//				mDataRetriever = mFileResumeRetriever;
//			}
//		}
		

		//retry connect until job is no longer in state retry
		while(true) {
			executeDownload();
			if(getState() != Job.STATE_IN_PROGRESS_RETRY) {
				break;
			}
		}
			
		
		mDataRetriever = null;
		mFileResumeRetriever = null;
		mProgressInputStream = null;
	}

	protected void executeDownload() throws IOException {
		
		//check if we must wait, important when retrying downloads
		if(mWaitUntil > System.currentTimeMillis()) {
			try {
				long waitingTime = mWaitUntil - System.currentTimeMillis();
				mLog.debug("Wait " + waitingTime + "ms before " + (getRetryCount()+1) + "th retry.");
				
				Thread.sleep(waitingTime);
			} catch (InterruptedException e) {
				mLog.info("Aborted while waiting.");
			}
		}
		
		//if the job has been aborted in the meantime, stop here
		if(mAbort) {
			setState(Job.STATE_IGNORED);
			return;
		}
		
		int resultCode;
		try {
			//connect the retriever
			mDataRetriever.connect();
			
			//save the metadata
			mMetadata = mDataRetriever.getMetadata();
			
			resultCode = mDataRetriever.getResultCode();
			
			//if retrieval was ok, process the data
			if(resultCode == UrlDataRetriever.RESULT_RETRIEVAL_OK) {
				
				//prepare resume if possible 
				tryResume();

				//process data
				executeProcessorChain();
			}
			
		} finally {
			try {
				mDataRetriever.disconnect();
			} catch(Exception ex) {
				mLog.warn("Error disconnecting", ex);
			}
			mTryCount ++;
		}
		
		//set final state of job
		if(resultCode == UrlDataRetriever.RESULT_RETRIEVAL_OK) {
			
			setState(Job.STATE_FINISHED);
			
		} else if(resultCode == UrlDataRetriever.RESULT_RETRIEVAL_FAILED_BUT_RETRYABLE) {
			
			//Retry is possible, check if max retry has been reached
			if(getRetryCount() < mMaxRetryCount) {
				
				mWaitUntil = System.currentTimeMillis() + 
					mDataRetriever.getSuggestedTimeToWaitForRetry();
				
				if(mRetryBehaviour.equals(RetryBehaviour.DIRECTLY_WAIT_FOR_RETRY_TIMEOUT)) {
					setState(Job.STATE_IN_PROGRESS_RETRY);
				} else if(mRetryBehaviour.equals(RetryBehaviour.MOVE_JOB_BACK_INTO_QUEUE)) {
					setState(Job.STATE_REOPEN);
				} else {
					throw new IllegalStateException("Unknwon retry behaviour: " + mRetryBehaviour);
				}
				
			} else {
				setState(Job.STATE_ERROR);
			}
			
		} else if(resultCode == UrlDataRetriever.RESULT_RETRIEVAL_FAILED) {
			setState(Job.STATE_ERROR);
		} else if(resultCode == UrlDataRetriever.RESULT_RETRIEVAL_ABORTED) {
			setState(Job.STATE_IGNORED);
		} else {
			setState(Job.STATE_ERROR);
		}
	}

	protected void tryResume() throws IOException {
		//check if this file could be resumed
		if(isSaveToDisk()) {
			
			FileManager fileManager = new FileManager(this.getSavePath());
			File file = fileManager.buildSavePath(getUrl(), mMetadata.getFilename());
			
			if(file.exists()) {
			
				mLog.info("Try to resume job: " + this);
				
				//ok, it seems the file already exists partially/completely
				//try to resume the file
				DataRetrieverFactory retrieverFactoryForProtocol = 
					mDataRetrieverManager.getRetrieverFactoryForProtocol(getUrl().getProtocol());
				
				mFileResumeRetriever = retrieverFactoryForProtocol.createResumeDataRetriever(mDataRetriever, file);
				mDataRetriever = mFileResumeRetriever;
				
				mFileResumeRetriever.connect();
			}
		}
	}

	protected void executeProcessorChain() throws IOException {
		
		//build the data processor chain
		DataProcessorChain dataProcessorChain =
			mDataProcessorManager.getProcessorChainForJob(this);
		
		//set up processor chain
		long contentLength = mDataRetriever.getContentLenght();
		InputStream stream;
		
		//use progress input stream to track progress when content length is known
		if(contentLength > 0) {
			mProgressInputStream = 
				new ProgressInputStream(mDataRetriever.getDataAsInputStream(), contentLength);
			mProgressInputStream.addPropertyChangeListener(new ProgressListener());
			stream = mProgressInputStream;
		} else {
			stream = mDataRetriever.getDataAsInputStream();
		}
		
		dataProcessorChain.setInputStream(stream);
		dataProcessorChain.setJobManager(mJobManager);
		
		
		//if resuming from file, configure the resume retriever
		if(mFileResumeRetriever != null) {
			
			//recheck if resume is necessary
			long resumeOffset = mFileResumeRetriever.getResumeOffset();
			if(resumeOffset > 0) {
				prepareResume(dataProcessorChain, resumeOffset);
			}
		}
		
		
		try {
			dataProcessorChain.init();
			dataProcessorChain.run();
			
		} catch(IOException ioex) {
			throw ioex;
		} catch(AbortProcessingException ex) {
			mLog.info("Chain was aborted.");
		} catch(ProcessingException ex) {
			throw new RuntimeException("Error retrieving/processing data.", ex);
		} finally {
			dataProcessorChain.finish();
		}
	}

	protected void prepareResume(DataProcessorChain dataProcessorChain,
			long resumeOffset) throws IOException {
		if (dataProcessorChain.canResume()) {
			// ok, resume of chain is possible, advise every processor to resume at
			// the given position.
			dataProcessorChain.resumeAt(resumeOffset);
			
			//data from disk is not needed, resume retriever can pipe the data through
			dataProcessorChain.getInputStream().skip(resumeOffset);

		} else {
			
			// resume is not possible, read the data from the file and pipe
			// it through the processors
			List<DataProcessor> dataProcessors = dataProcessorChain
					.getDataProcessors();

			for (DataProcessor processor : dataProcessors) {

				// skip the persistence processor
				if (processor instanceof PersistenceProcessor) {

					processor.resumeAt(resumeOffset);
					dataProcessorChain.replaceDataProcessor(processor,
							new SeekDataProcessorWrapper(processor,
									resumeOffset));

					continue;
				}
			}

		}
	}
	
	protected class ProgressListener implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent pEvt) {
			if ("progress".equals(pEvt.getPropertyName())) {
				float oldProgress = mProgress;
				mProgress = mProgressInputStream.getProgress();
				mBytesDownloaded = mProgressInputStream.getDataRead();

				firePropertyChange(JOB_PROGRESS_PROPERTY, oldProgress,
						mProgress);
			}

		}
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.job.download.impl.DownloadJob#abort()
	 */
	@Override
	public void abort() {
		mAbort  = true;
		
		if(mDataRetriever != null) {
			mDataRetriever.abort();
		}
		
		mLog.info("Job aborted: " + this);
	}
	
	/**
	 * Gets the URL to be downloaded.
	 * 
	 * @return
	 */
	public URL getUrl() {
		return mUrl;
	}
	
	/**
	 * Sets the url to be downloaded.
	 * 
	 * @param pUrl
	 */
	public void setUrl(URL pUrl) {
		
		//fix the url if no path part exists
		if("".equals(pUrl.getPath()) && pUrl.getQuery() == null) {
			try {
				pUrl = new URL(pUrl.toExternalForm() + "/");
			} catch (MalformedURLException e) {
				throw new IllegalStateException("Error appending slash to url: " + pUrl, e);
			}
		}
		
		mUrl = pUrl;
	}

	/**
	 * Sets the data retriever manager.
	 * @param pDataRetrieverManager
	 */
	public void setDataRetrieverManager(DataRetrieverManager pDataRetrieverManager) {
		mDataRetrieverManager = pDataRetrieverManager;
	}

	/**
	 * Sets the data processor manager.
	 * @param pDataProcessorManager
	 */
	public void setDataProcessorManager(DataProcessorManager pDataProcessorManager) {
		mDataProcessorManager = pDataProcessorManager;
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.job.download.impl.DownloadJob#getDepth()
	 */
	public int getDepth() {
		return mDepth;
	}

	/**
	 * Sets the recursive depth of the job.
	 * @param pDepth
	 */
	public void setDepth(int pDepth) {
		mDepth = pDepth;
	}

	/**
	 * Parent is saved as wek reference to save memory. Therefore this reference do not count.
	 * @return the parent of the job, null if none.
	 * 
	 */
	public Job getParent() {
		Job parent = null;
		
		if(mParent != null) {
			parent = mParent.get();
		} 
		
		return parent;
	}

	/**
	 * Sets the parent of the job.
	 * This method also copies the configuration 
	 * from the parent to this instance.
	 * 
	 * @param pParent
	 */
	public void setParent(UrlDownloadJob pParent) {
		mParent = new WeakReference<UrlDownloadJob>(pParent);
		mDepth = pParent.getDepth() + 1;
		setSavePath(pParent.getSavePath());
		setMaxRetryCount(pParent.getMaxRetryCount());
		setParameter(new JobParameter("RefererURL", pParent.getUrl()));
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.job.download.impl.DownloadJob#isSaveToDisk()
	 */
	public boolean isSaveToDisk() {
		return mSaveToDisk;
	}

	/**
	 * Sets if this file should be saved as file.
	 * @param pSaveToFile
	 */
	public void setSaveToDisk(boolean pSaveToDisk) {
		mSaveToDisk = pSaveToDisk;
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.job.download.impl.DownloadJob#getSavePath()
	 */
	public File getSavePath() {
		return mSavePath;
	}

	/**
	 * Sets the base save path for saving downloaded files.
	 * 
	 * @param pSavePath
	 */
	public void setSavePath(File pSavePath) {
		mSavePath = pSavePath;
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.job.download.impl.DownloadJob#getDataRetriever()
	 */
	public UrlDataRetriever getDataRetriever() {
		return mDataRetriever;
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.job.download.impl.DownloadJob#getProgress()
	 */
	public float getProgress() {
		return mProgress;
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.job.download.impl.DownloadJob#getMaxRetryCount()
	 */
	public int getMaxRetryCount() {
		return mMaxRetryCount;
	}

	/**
	 * Sets the maximum count of retries when an retryable error occurs.
	 * @param pMaxRetryCount
	 */
	public void setMaxRetryCount(int pMaxRetryCount) {
		mMaxRetryCount = pMaxRetryCount;
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.job.download.DownloadJob#getRetryCount()
	 */
	public int getRetryCount() {
		return mTryCount - 1; //first try is not retry
	}

	public RetryBehaviour getRetryBehaviour() {
		return mRetryBehaviour;
	}

	public void setRetryBehaviour(RetryBehaviour pRetryBehaviour) {
		mRetryBehaviour = pRetryBehaviour;
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.job.download.impl.DownloadJob#getBytesDownloaded()
	 */
	public long getBytesDownloaded() {
		return mBytesDownloaded;
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.job.download.impl.DownloadJob#getMetadata()
	 */
	public Metadata getMetadata() {
		if(mDataRetriever != null) {
			return mDataRetriever.getMetadata();
		} else {
			return mMetadata;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DownloadJob (State: " + getState() + ", Prio: " + getPriority() + ", URL: '" + getUrl() + "')";
	}

	@Override
	public UrlDownloadJob clone() {
		try {
			return (UrlDownloadJob) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
	
}
