/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * $Id$
 * Created on 11.06.2007
 */

package de.phleisch.app.itsucks.processing.download.http.impl;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.phleisch.app.itsucks.io.Metadata;
import de.phleisch.app.itsucks.io.UrlDataRetriever;
import de.phleisch.app.itsucks.io.http.impl.HttpMetadata;
import de.phleisch.app.itsucks.job.Job;
import de.phleisch.app.itsucks.job.download.DownloadJob;
import de.phleisch.app.itsucks.job.download.impl.DownloadJobFactory;
import de.phleisch.app.itsucks.job.download.impl.UrlDownloadJob;
import de.phleisch.app.itsucks.processing.DataChunk;
import de.phleisch.app.itsucks.processing.DataProcessorChain;
import de.phleisch.app.itsucks.processing.DataProcessorInfo;
import de.phleisch.app.itsucks.processing.ProcessingException;
import de.phleisch.app.itsucks.processing.impl.AbstractDataProcessor;

/**
 * This data processor analyzes the http request and searches
 * for any 'Location' header fields.
 * 
 * @author olli
 *
 */
public class HttpRedirectorProcessor extends AbstractDataProcessor {

	private static Log mLog = LogFactory.getLog(HttpRedirectorProcessor.class);
	
	private DownloadJobFactory mDownloadJobFactory;
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.processing.AbstractDataProcessor#supports(de.phleisch.app.itsucks.Job)
	 */
	@Override
	public boolean supports(Job pJob) {
		
		if(pJob instanceof UrlDownloadJob) {
			DownloadJob downloadJob = (DownloadJob) pJob;
			
			Metadata metadata = downloadJob.getDataRetriever().getMetadata();
			
			if(metadata instanceof HttpMetadata) {
				return true;
			}
		}
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.processing.AbstractDataProcessor#init()
	 */
	@Override
	public void init() throws ProcessingException {
		super.init();
		
		UrlDownloadJob job = (UrlDownloadJob) getProcessorChain().getJob();
		UrlDataRetriever dataRetriever = job.getDataRetriever();
		HttpMetadata metadata = (HttpMetadata)dataRetriever.getMetadata();
		
		URI baseURI;
		try {
			baseURI = dataRetriever.getUrl().toURI();
		} catch (URISyntaxException e) {
			throw new ProcessingException("Error converting URL to URI: " + dataRetriever.getUrl(), e);
		}
		
		//get URL's from header
		String[] location = metadata.getResponseHeaderField("Location");
		
		HashSet<URI> urlList = new HashSet<URI>();
		
		if(location != null && location.length > 0) {
			for (int i = 0; i < location.length; i++) {
				URI uri = null;
				try {
					uri = baseURI.resolve(location[i]);
				} catch(Exception ex) {
					mLog.warn(ex);
				}
				if(uri != null) {
					//mLog.debug("Add uri: " + uri);
					urlList.add(uri);
				}
			}
			
			addNewJobs(urlList.toArray(new URI[urlList.size()]));
			
			//do not save the page
			job.setSaveToDisk(false);
			
		}
		
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.processing.DataProcessor#process(byte[], int)
	 */
	public DataChunk process(DataChunk pDataChunk) {
		return pDataChunk;
	}

	private void addNewJobs(URI[] uris) {
		for (int i = 0; i < uris.length; i++) {
			URI referenceURI = uris[i];
			
			URL url;
			try {
				url = referenceURI.toURL();
			} catch (MalformedURLException ex) {
				mLog.warn("Parsed bad link: " + referenceURI);
				continue;
			}
			
			DataProcessorChain processorChain = getProcessorChain();
			
			UrlDownloadJob job = mDownloadJobFactory.createDownloadJob();
			
			job.setUrl(url);
			job.setParent((UrlDownloadJob)processorChain.getJob());
			processorChain.getJobManager().addJob(job);
		}
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.processing.impl.AbstractDataProcessor#resumeAt(long)
	 */
	public void resumeAt(long pByteOffset) {
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.processing.DataProcessor#getInfo()
	 */
	public DataProcessorInfo getInfo() {
		
		return new DataProcessorInfo(
				DataProcessorInfo.ResumeSupport.RESUME_SUPPORTED,
				DataProcessorInfo.ProcessorType.FILTER,
				DataProcessorInfo.StreamingSupport.STREAMING_SUPPORTED
		);
	}

	public void setDownloadJobFactory(DownloadJobFactory pDownloadJobFactory) {
		mDownloadJobFactory = pDownloadJobFactory;
	}
	
}
