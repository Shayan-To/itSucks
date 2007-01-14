/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 * 
 * $Id: DownloadJob.java,v 1.1 2006-12-03 19:31:43 olli Exp $
 * Created on 03.03.2006
 */

package de.phleisch.app.itsucks.io;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.phleisch.app.itsucks.Job;


/**
 * This is the implementation of a job for downloading files.
 * It contains a URL to the file or directory to be downloaded.
 * 
 * @author olli
 *
 */
public class DownloadJob extends Job {

	private static Log mLog = LogFactory.getLog(DownloadJob.class);
	
	private boolean mSaveToFile = true;
	private File mSavePath = null;
	
	private URL mUrl;
	private DownloadJob mParent = null;
	private int mDepth = 0;
	private DataProcessorManager mDataProcessorManager;
	private DataRetrieverManager mDataRetrieverManager;
	
	private DataRetriever mDataRetriever;
	
	public DownloadJob() {
		super();
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.chaoscrawler.refactoring.Job#run()
	 */
	public void run() throws Exception {
	
		try {
			download();
		} catch (Exception e) {
			mLog.error("Error downloading url: " + mUrl, e);
			throw e;
		} finally {
			try {
				mDataRetriever.disconnect();
			} catch (Exception e) {
				mLog.warn("Problem in disconnecting after error", e);
			}
		}
		
	}

	private void download() throws Exception {
		String protocol = mUrl.getProtocol();
		
		mDataRetriever = 
			mDataRetrieverManager.getRetrieverForProtocol(protocol);

		mDataRetriever.setUrl(mUrl);
		mDataRetriever.connect();
		
		if(mDataRetriever.isDataAvailable()) {
		
			List<DataProcessor> dataProcessors =
				mDataProcessorManager.getProcessorsForJob(this);
	
			for (Iterator<DataProcessor> it = dataProcessors.iterator(); it.hasNext();) {
				DataProcessor dataProcessor = it.next();
				
				dataProcessor.setDataRetriever(mDataRetriever);
				dataProcessor.setJobManager(mJobManager);
				dataProcessor.setJob(this);
				mDataRetriever.addDataProcessor(dataProcessor);
			}
	
			mDataRetriever.retrieve();
		}
		
		mDataRetriever.disconnect();
	}
	
	/**
	 * @return the url to be downloaded
	 */
	public URL getUrl() {
		return mUrl;
	}

	/**
	 * Sets the url to be downloaded.
	 * @param pUrl
	 */
	public void setUrl(URL pUrl) {
		//fix the url if no path part exists
		if("".equals(pUrl.getPath()) && pUrl.getQuery() == null) {
			try {
				mUrl = new URL(pUrl.toExternalForm() + "/");
			} catch (MalformedURLException e) {
				throw new IllegalStateException("Error appending slash to url: " + pUrl, e);
			}
		} else {
			mUrl = pUrl;
		}
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

	/**
	 * Gets the recursive depth of the job.
	 * @return
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
	 * @return the parent of the job, null if none.
	 */
	public Job getParent() {
		return mParent;
	}

	/**
	 * Sets the parent of the job.
	 * @param pParent
	 */
	public void setParent(DownloadJob pParent) {
		mParent = pParent;
		mDepth = pParent.getDepth() + 1;
		mSavePath = pParent.getSavePath();
	}

	/**
	 * @return true if this file should be saved as file.
	 */
	public boolean isSaveToFile() {
		return mSaveToFile;
	}

	/**
	 * Sets if this file should be saved as file.
	 * @param pSaveToFile
	 */
	public void setSaveToFile(boolean pSaveToFile) {
		mSaveToFile = pSaveToFile;
	}
	
	public File getSavePath() {
		return mSavePath;
	}

	public void setSavePath(File pSavePath) {
		mSavePath = pSavePath;
	}

	/**
	 * @return the data retriever used to download from the url.
	 */
	public DataRetriever getDataRetriever() {
		return mDataRetriever;
	}

	@Override
	public String toString() {
		return "DownloadJob (State: " + getState() + ", Prio: " + getPriority() + ", URL: '" + getUrl() + "')";
	}

}
