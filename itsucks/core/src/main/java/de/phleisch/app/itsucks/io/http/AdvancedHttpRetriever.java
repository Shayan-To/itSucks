/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 * 
 * $Id$
 */

package de.phleisch.app.itsucks.io.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jmx.mbeanserver.MetaData;

import de.phleisch.app.itsucks.io.DataProcessor;
import de.phleisch.app.itsucks.io.DataRetriever;


public class AdvancedHttpRetriever extends DataRetriever {

	private static Log mLog = LogFactory.getLog(AdvancedHttpRetriever.class);
	
	private static HttpClient mClient;
	private GetMethod mGet = null;
	private HttpMetadata mMetadata;
	
	{
     	MultiThreadedHttpConnectionManager connectionManager = 
      		new MultiThreadedHttpConnectionManager();
     	mClient = new HttpClient(connectionManager);
	}
	
	public AdvancedHttpRetriever() {
		super();
		
		//mClient = new HttpClient();
	}
	
	@Override
	public void connect() throws IOException {
		
		mGet = new GetMethod(mUrl.toString());
		mGet.setFollowRedirects(false);
		mClient.executeMethod(mGet);
		mLog.debug("Connected to: " + mUrl + " / " + mGet.getStatusCode());
		
		//build metadata

		mMetadata = new HttpMetadata();
		mMetadata.setMimetype(mGet.getResponseHeader("Content-Type").getValue());
		mMetadata.setStatusCode(mGet.getStatusCode());
		mMetadata.setConnection(mGet);
	}
	
	@Override
	protected boolean isDataAvailable() throws Exception {
		return mGet.getStatusCode() < 400;
	}
	
	@Override
	public void retrieve() throws Exception {
		try {
			download();
		} catch (Exception e) {
			mLog.error("Error downloading url: " + mUrl, e);
			throw e;
		} finally {
			disconnect();
		}
	}
	
	@Override
	public void disconnect() {
		if(mGet != null) {
			try {
				if(mGet.getResponseBodyAsStream() != null) {
					mGet.getResponseBodyAsStream().close();
				}
			} catch (IOException e) {
				mLog.error(e);
			} finally {
				mGet.releaseConnection();
			}
			mGet = null;
		}
	}
	
	private void download() throws Exception {
		
		InputStream input = mGet.getResponseBodyAsStream(); 

		for (Iterator<DataProcessor> it = mDataProcessors.iterator(); it.hasNext();) {
			DataProcessor processor = it.next();
			processor.init();
		}
		
		//100k buffer
		byte buffer[] = new byte[100000];
		int bytesRead;
		
		while((bytesRead = input.read(buffer)) > 0) {
			
			for (Iterator<DataProcessor> it = mDataProcessors.iterator(); it.hasNext();) {
				DataProcessor processor = it.next();
				processor.process(buffer, bytesRead);
			}
		}
		
		for (Iterator<DataProcessor> it = mDataProcessors.iterator(); it.hasNext();) {
			DataProcessor processor = it.next();
			
			processor.finish();
		}
		
	}

	public HttpMetadata getMetadata() {
		return mMetadata;
	}

}
