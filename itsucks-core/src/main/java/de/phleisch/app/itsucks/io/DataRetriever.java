/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * $Id$
 * Created on 26.02.2007
 */

package de.phleisch.app.itsucks.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.phleisch.app.itsucks.context.Context;

/**
 * This interface specifies an DataRetriever. DataRetriever are used to provided
 * an extended input stream for the processor chain.
 * 
 * @author olli
 */
public interface DataRetriever {

	/**
	 * This type of notification will be send if the progress changes.
	 */
	public final static Integer NOTIFICATION_PROGRESS = 100;

	
	/**
	 * This value is returned if the download was not started yet.
	 */
	public final static Integer RESULT_RETRIEVAL_NOT_STARTED_YET = 1;
	
	/**
	 * This value is returned if the retrieval finished without errors.
	 */
	public final static Integer RESULT_RETRIEVAL_OK = 2;
	
	/**
	 * This value is returned if the retrieval failed.
	 */
	public final static Integer RESULT_RETRIEVAL_FAILED = 10;

	/**
	 * This value is returned if the retrieval failed but it's retryable.
	 */
	public final static Integer RESULT_RETRIEVAL_FAILED_BUT_RETRYABLE = 11;

	/**
	 * This value is returned if the retrieval was aborted.
	 */
	public final static Integer RESULT_RETRIEVAL_ABORTED = 12;

	/**
	 * Sets the context for this data retriever.
	 * @param pContext
	 */
	public void setContext(Context pContext);
	
	/**
	 * Gets the context from this data retriever.
	 * @return
	 */
	public Context getContext();
	
	/**
	 * Returns the URL which is retrieved.
	 * 
	 * @return
	 */
	public URL getUrl();
	
	/**
	 * Sets the URL to be retrieved.
	 * 
	 * @param pUrl
	 */
	public void setUrl(URL pUrl);

	//control retriever
	/**
	 * Advise the Retriever to connect to the data source.
	 * 
	 * @throws IOException
	 */
	public void connect() throws IOException;
	
	/**
	 * Returns true if the data source contains any data to be read.
	 * 
	 * @return
	 * @throws IOException
	 */
	public boolean isDataAvailable() throws IOException;
	
	/**
	 * Returns an input stream for the retrieved data. 
	 * @return
	 * @throws IOException 
	 */
	public InputStream getDataAsInputStream() throws IOException;
	
	/**
	 * Disconnect from the data source.
	 * 
	 * @throws IOException
	 */
	public void disconnect() throws IOException;
	
	/**
	 * Aborts the current retrieving.
	 */
	public void abort();
	
	/**
	 * Returns the internal state and result code.
	 * Check RESULT_RETRIEVAL_* constants for possible values.
	 * 
	 * @return
	 */
	public int getResultCode();
	
	/**
	 * Returns the metadata of the data source connection.
	 * 
	 * @return
	 */
	public Metadata getMetadata();
	
	/**
	 * Returns the content length in bytes.
	 * 
	 * @return
	 */
	public long getContentLenght() throws IOException;

	//resume interface
	/**
	 * Skip the given bytes (seek) when reading the data source.
	 * 
	 * @param pBytesToSkip
	 */
	public void setBytesToSkip(long pBytesToSkip);
	
	/**
	 * Returns the skipped bytes.
	 * 
	 * @return
	 */
	public long getBytesSkipped();
	
}