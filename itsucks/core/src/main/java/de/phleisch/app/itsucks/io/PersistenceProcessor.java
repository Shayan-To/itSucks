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
	private OutputStream mOut;
	
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
	
	public void init() throws Exception {
		
		URL url = mDataRetriever.getUrl();
		
		DownloadJob downloadJob = (DownloadJob) mJob;
		
		File target_path = downloadJob.getSavePath();
		if(target_path == null) throw new IOException("No target path set in job");
		
		String hostname_part = url.getHost();
		if(url.getPort() != -1) {
			hostname_part += ":" + url.getPort();
		}
		
		String full_path = url.getPath();
		if(url.getQuery() != null) full_path += "?" + url.getQuery();
		
		String path = url.getPath();
		String filename = "index.html";
		if(!full_path.endsWith("/") && full_path.lastIndexOf('/') != -1) {
		
			path = 
				full_path.substring(0, full_path.lastIndexOf('/'));
			filename = full_path.substring(url.getPath().lastIndexOf('/') + 1);
			
			//replace all invalid characters
			filename = filename.replaceAll("[\\" + File.separator + "]", "");
		}
		
		File local_path = new File(
			target_path + File.separator 
			+ hostname_part + File.separator
			+ path + File.separator);
		mLog.debug("creating path: " + local_path);
		
		local_path.mkdirs();
		
		mFile = new File(local_path + File.separator + filename);
		mLog.debug("saving file: " + mFile);
		mOut = new BufferedOutputStream(new FileOutputStream(mFile));
	}

	@Override
	public void process(byte[] pBuffer, int pBytes) throws Exception {
		mOut.write(pBuffer, 0, pBytes);
	}

	@Override
	public void finish() throws Exception {
		super.finish();
		mOut.close();
	}


}
