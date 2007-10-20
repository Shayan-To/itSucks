/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * $Id$
 * Created on 20.10.2007
 */

package de.phleisch.app.itsucks.processing;

import de.phleisch.app.itsucks.Job;
import de.phleisch.app.itsucks.filter.FileSizeFilter;
import de.phleisch.app.itsucks.filter.FileSizeFilter.FileSizeConfig;
import de.phleisch.app.itsucks.io.DownloadJob;
import de.phleisch.app.itsucks.io.Metadata;
import de.phleisch.app.itsucks.io.http.HttpMetadata;

public class FilterFileSizeProcessor extends AbstractDataProcessor {

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.processing.AbstractDataProcessor#supports(de.phleisch.app.itsucks.Job)
	 */
	@Override
	public boolean supports(Job pJob) {
		
		if(!(pJob instanceof DownloadJob) 
				|| pJob.getParameter(FileSizeFilter.FILE_SIZE_CONFIG_PARAMETER) == null) {
			return false;
		}

		FileSizeConfig fileSizeConfig = (FileSizeConfig)
			pJob.getParameter(FileSizeFilter.FILE_SIZE_CONFIG_PARAMETER).getValue();
		
		DownloadJob downloadJob = (DownloadJob) pJob;
		if(downloadJob.isSaveToDisk()) {
			
			Metadata metadata = downloadJob.getDataRetriever().getMetadata();
			long contentLength = 0;
			
			if(metadata instanceof HttpMetadata) {
				HttpMetadata httpMetadata = (HttpMetadata) metadata;
				contentLength = httpMetadata.getContentLength();
			}
			
			downloadJob.setSaveToDisk(checkContentLength(contentLength, fileSizeConfig));
		}

		return true;
	}

	private boolean checkContentLength(long pContentLength, 
			FileSizeConfig pFileSizeConfig) {
		
		if(pContentLength <= 0) {
			return pFileSizeConfig.isAcceptWhenLengthNotSet();
		}
		
		boolean accept = true;
		if(accept && pFileSizeConfig.getMinSize() > -1) {
			accept = pContentLength >= pFileSizeConfig.getMinSize();
		}
		
		if(accept && pFileSizeConfig.getMaxSize() > -1) {
			accept = pContentLength <= pFileSizeConfig.getMaxSize();
		}
		
		return accept;
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.processing.DataProcessor#isConsumer()
	 */
	public boolean isConsumer() {
		return false;
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.processing.DataProcessor#needsDataAsWholeChunk()
	 */
	public boolean needsDataAsWholeChunk() {
		return false;
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.processing.DataProcessor#process(byte[], int)
	 */
	public byte[] process(byte[] pBuffer, int pBytes) throws Exception {
		return pBuffer;
	}

}
