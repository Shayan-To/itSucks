/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * $Id$
 * Created on 27.02.2007
 */

package de.phleisch.app.itsucks.io;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileRetriever extends AbstractDataRetriever {

	private static Log mLog = LogFactory.getLog(FileRetriever.class);
	
	private File mFile;
	private FileInputStream mIn;
	
	private float mProgress = -1;
	private long mFileSize;
	private long mBytesRead;
	
	private boolean mAbort = false;

	private long mByteOffset;

	public FileRetriever(File pFile) {
		mFile = pFile;
	}
	
	public void abort() {
		mAbort = true;
	}

	public void connect() throws Exception {
		mIn = new FileInputStream(mFile);
		
		mFileSize = mFile.length();
	}

	public void disconnect() throws Exception {
		mIn.close();
	}

	public long getBytesRetrieved() {
		return mBytesRead;
	}

	public Metadata getMetadata() {
		throw new IllegalStateException("Not implemented yet!");
	}

	public float getProgress() {
		return mProgress;
	}

	public boolean isDataAvailable() throws Exception {
		return mIn.available() > 0;
	}

	public void retrieve() throws Exception {

		//skip bytes in front when given
		if(mByteOffset > 0) {
			mIn.skip(mByteOffset);
		}
		
		for (Iterator<AbstractDataProcessor> it = mDataProcessors.iterator(); it.hasNext();) {
			AbstractDataProcessor processor = it.next();
			processor.init();
		}
		
		//100k buffer
		byte buffer[] = new byte[102400];
		
		mBytesRead = 0; //reset bytes read
		int bytesRead;
		
		while((bytesRead = mIn.read(buffer)) > 0) {
			
			if(mAbort ) {
				mLog.warn("File retriever aborted: " + this);
				break;
			}
			
			//run through the data processor list
			for (Iterator<AbstractDataProcessor> it = mDataProcessors.iterator(); it.hasNext();) {
				AbstractDataProcessor processor = it.next();
				processor.process(buffer, bytesRead);
			}

			//update the progress
			mBytesRead += bytesRead;
			updateProgress(((float)mBytesRead / (float)mFileSize));
		}
		
		for (Iterator<AbstractDataProcessor> it = mDataProcessors.iterator(); it.hasNext();) {
			AbstractDataProcessor processor = it.next();
			
			processor.finish();
		}
	}

	private void updateProgress(float pProgress) {
		mLog.trace("Update Progress: " + pProgress);
		
		if(mProgress != pProgress) {
			mProgress = pProgress;
			this.setChanged();
		}
		
		this.notifyObservers(NOTIFICATION_PROGRESS);
	}

	public void setBytesToSkip(long pBytesToSkip) {
		mByteOffset = pBytesToSkip;
	}

	public long getBytesSkipped() {
		return mByteOffset;
	}

	public int getResultCode() {
		return RESULT_RETRIEVAL_OK;
	}
	
}
