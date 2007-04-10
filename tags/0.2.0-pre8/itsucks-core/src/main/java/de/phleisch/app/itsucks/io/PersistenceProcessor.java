/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 * 
 * $Id$
 */

package de.phleisch.app.itsucks.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.phleisch.app.itsucks.Job;


public class PersistenceProcessor extends DataProcessor {

	private static Log mLog = LogFactory.getLog(PersistenceProcessor.class);
	
	private File mFile;
	private FileOutputStream mFileOut;
	private OutputStream mBufferedOut;
	
	private long mResumeAt;
	
	public PersistenceProcessor() {
		super();
	}
	
	@Override
	public boolean supports(Job pJob) {
		if(pJob instanceof DownloadJob) {
			DownloadJob downloadJob = (DownloadJob) pJob;
			return downloadJob.isSaveToFile();
		}
		
		return false;
	}

	@Override
	public void init() throws Exception {
	
		DownloadJob downloadJob = (DownloadJob) mJob;
		File target_path = downloadJob.getSavePath();
		
		
		FileManager fileManager = new FileManager(target_path);
		
		URL url = mDataRetriever.getUrl();
		mFile = fileManager.buildSavePath(url);
		
		//create the folder
		File folder = mFile.getParentFile();
		if(!folder.exists() && !folder.mkdirs()) {
			throw new IOException("Cannot create folder(s): " + folder);
		}
		
		mLog.debug("saving file: " + mFile);
		
		if(mResumeAt > 0) { //skip bytes when resuming
			mFileOut = new FileOutputStream(mFile, true);
			mFileOut.getChannel().position(mResumeAt);
		} else {
			mFileOut = new FileOutputStream(mFile, false);
		}
		
		mBufferedOut = new BufferedOutputStream(mFileOut);
	}

	@Override
	public boolean canResume() {
		return true;
	}

	@Override
	public void resumeAt(long pByteOffset) {
		mResumeAt = pByteOffset;
	}

	@Override
	public void process(byte[] pBuffer, int pBytes) throws Exception {
		mBufferedOut.write(pBuffer, 0, pBytes);
	}

	@Override
	public void finish() throws Exception {
		super.finish();
		mBufferedOut.close();
	}


}
