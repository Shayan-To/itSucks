/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * $Id$
 * Created on 26.02.2007
 */

package de.phleisch.app.itsucks.io.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.net.URL;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.phleisch.app.itsucks.io.DataConsumer;
import de.phleisch.app.itsucks.io.DataRetriever;
import de.phleisch.app.itsucks.io.Metadata;
import de.phleisch.app.itsucks.job.Context;
import de.phleisch.app.itsucks.processing.DataProcessor;
import de.phleisch.app.itsucks.processing.DataProcessorChain;
import de.phleisch.app.itsucks.processing.download.impl.PersistenceProcessor;
import de.phleisch.app.itsucks.processing.impl.SeekDataProcessorWrapper;

/**
 * This retriever is used to resume partial downloaded files.
 * It creates an additional retriever, the file retriever, and combine 
 * it with the original data retriever.
 * So it can send the complete data through the processing chain without receiving it
 * completly from the data retriever.
 * 
 * @author olli
 */
public class FileResumeRetriever implements DataRetriever {

	private static Log mLog = LogFactory.getLog(FileResumeRetriever.class);
	
	private FileRetriever mFileRetriever;
	private DataRetriever mDataRetriever;
	
	protected DataProcessorChain mDataProcessorChain;
	
	private File mLocalFile;
	
	private long mResumeOffset;
	
	private boolean mReadFromFile;
	private boolean mResumePrepared;

	private InputStream mIn;
	
	
	public FileResumeRetriever(DataRetriever pDataRetriever, 
			File pFile) {
		
		mFileRetriever = null;
		mDataRetriever = pDataRetriever;
		mLocalFile = pFile;
		mReadFromFile = false;
		mResumePrepared = false;
		mDataProcessorChain = null;
		mResumeOffset = 0;
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#abort()
	 */
	public void abort() {
		if(mReadFromFile) {
			mFileRetriever.abort();
		}
		mDataRetriever.abort();
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#connect()
	 */
	public void connect() throws IOException {
		
		//first check if the file exists
		if(mLocalFile.exists() && mLocalFile.length() > 0) {
			mResumeOffset = mLocalFile.length();
		
			//try to resume the data stream
			mDataRetriever.setBytesToSkip(mResumeOffset);
			mDataRetriever.connect();
			
			if(mDataRetriever.getBytesSkipped() > 0) {
				mLog.info("Resume of URL successful: " 
						+ mDataRetriever.getUrl());
				
			} else {
				//resume not possible, read everything from the live stream
				mResumeOffset = 0;
				
				mLog.info("Resume of URL not possible: " 
						+ mDataRetriever.getUrl() + ", seeking not allowed.");
			}
			
		} else {
			//resume not possible, read everything from the live stream
			mResumeOffset = 0;
			
			//connect without skipping any bytes
			mDataRetriever.connect();
			
			mLog.info("Resume of url not possible: " 
					+ mDataRetriever.getUrl() + ", no local data available.");
			
		}

	}

	private void prepareResume() throws IOException {

		if(mResumePrepared) return;
		
		//check the processor chain
		DataProcessorChain dataProcessorChain = mDataProcessorChain;
		
		if(dataProcessorChain == null) {
			throw new IllegalArgumentException("Processor Chain not set!");
		}
		
		if(mResumeOffset > 0) {
		
			//retriever successfully seeked to the position
			//reorganize the data processors
			
			if(dataProcessorChain.canResume()) {
				//ok, resume is possible, advise every processor to resume at the given position.
				
				dataProcessorChain.resumeAt(mResumeOffset);
				
				mReadFromFile = false;
				
			} else {
				//resume is not possible, read the data from the disc and pipe it through the processors
				
				mFileRetriever = new FileRetriever(mLocalFile);
				
				List<DataProcessor> dataProcessors = dataProcessorChain.getDataProcessors();
				
				for (DataProcessor processor : dataProcessors) {
					
					//skip the persistence processor
					if(processor instanceof PersistenceProcessor) {
						
						processor.resumeAt(mResumeOffset);
						dataProcessorChain.replaceDataProcessor(processor, 
								new SeekDataProcessorWrapper(processor, mResumeOffset));
						
						continue;
					}
				}
				
				mFileRetriever.setDataConsumer(mDataRetriever.getDataConsumer());
				mFileRetriever.connect();
				
				mReadFromFile = true;
			}
			
		} else {
			//only pipe through
			mReadFromFile = false;
		}
		
		if(mReadFromFile) {
			mIn = new SequenceInputStream(
					mFileRetriever.getDataAsInputStream(), 
					mDataRetriever.getDataAsInputStream());
		} else {
			mIn = mDataRetriever.getDataAsInputStream();
		}
		
		mResumePrepared = true;
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#getDataAsInputStream()
	 */
	public InputStream getDataAsInputStream() throws IOException {
		
		prepareResume();

		return mIn;
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#disconnect()
	 */
	public void disconnect() throws IOException {
		if(mReadFromFile) {
			mFileRetriever.disconnect();
		}
		mDataRetriever.disconnect();
	
		mResumePrepared = false;
		mIn = null;
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#getMetadata()
	 */
	public Metadata getMetadata() {
		return mDataRetriever.getMetadata();
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#setUrl(java.net.URL)
	 */
	public void setUrl(URL pUrl) {
		throw new IllegalArgumentException("Not possible!");
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#getUrl()
	 */
	public URL getUrl() {
		return mDataRetriever.getUrl();
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#isDataAvailable()
	 */
	public boolean isDataAvailable() throws IOException {
		
		prepareResume();
		
		boolean result;
		
		if(mReadFromFile) { 
			result = mFileRetriever.isDataAvailable();
			if(!result) {
				result = mDataRetriever.isDataAvailable();
			}
		} else {
			result = mDataRetriever.isDataAvailable();
		}
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#getDataConsumer()
	 */
	public DataConsumer getDataConsumer() {
		return mDataRetriever.getDataConsumer();
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#setDataConsumer(de.phleisch.app.itsucks.io.DataConsumer)
	 */
	public void setDataConsumer(DataConsumer pDataConsumer) {

		if(mResumePrepared) {
			throw new RuntimeException("Resuming already prepared, " +
					"changing the data consumer not allowed at this point.");
		}
		
		mDataRetriever.setDataConsumer(pDataConsumer);
		if(mFileRetriever != null) {
			mFileRetriever.setDataConsumer(pDataConsumer);
		}
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#setBytesToSkip(long)
	 */
	public void setBytesToSkip(long pBytesToSkip) {
		throw new IllegalStateException("Not supported.");
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#getBytesSkipped()
	 */
	public long getBytesSkipped() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#getResultCode()
	 */
	public int getResultCode() {
		return mDataRetriever.getResultCode();
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#getContext()
	 */
	public Context getContext() {
		return mDataRetriever.getContext();
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#setContext(de.phleisch.app.itsucks.Context)
	 */
	public void setContext(Context pContext) {
		mDataRetriever.setContext(pContext);
	}

	public DataProcessorChain getDataProcessorChain() {
		return mDataProcessorChain;
	}

	public void setDataProcessorChain(DataProcessorChain pDataProcessorChain) {
		mDataProcessorChain = pDataProcessorChain;
	}

	public long getContentLenght() throws IOException {
		
		prepareResume();
		
		long length;
		
		if(mReadFromFile) { 
			length = mFileRetriever.getContentLenght();
			if(length > -1) {
				length += mDataRetriever.getContentLenght();
			}
		} else {
			length = mDataRetriever.getContentLenght();
		}
		
		return length;
	}


}
