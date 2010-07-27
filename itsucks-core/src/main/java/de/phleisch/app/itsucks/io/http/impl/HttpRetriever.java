/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 * 
 * $Id$
 */

package de.phleisch.app.itsucks.io.http.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.contrib.ssl.EasySSLProtocolSocketFactory;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.phleisch.app.itsucks.io.UrlUtils;
import de.phleisch.app.itsucks.io.http.impl.HttpRetrieverResponseCodeBehaviour.Action;
import de.phleisch.app.itsucks.io.http.impl.HttpRetrieverResponseCodeBehaviour.ResponseCodeRange;
import de.phleisch.app.itsucks.io.impl.AbstractUrlDataRetriever;
import de.phleisch.app.itsucks.io.impl.ThrottledInputStream;

/**
 * Implementation of an data retriever for the http protocol.
 * 
 * @author olli
 *
 */
public class HttpRetriever extends AbstractUrlDataRetriever {

	private static final int HTTP_STATUS_PARTIAL_CONTENT = 206;
	
	private static final int HTTP_STATUS_REQUEST_TIMEOUT = 408;
	private static final int HTTP_STATUS_RANGE_NOT_SATISFIABLE = 416;
	
	private static final int HTTP_STATUS_INTERNAL_SERVER_ERROR = 500;
	private static final int HTTP_STATUS_SERVICE_UNAVAILABLE = 503;
	private static final int HTTP_STATUS_GATEWAY_TIMEOUT = 504;
	
	protected static final long DEFAULT_TIME_TO_WAIT_BETWEEN_RETRY = 5000; //5 seconds
	
	private static Log mLog = LogFactory.getLog(HttpRetriever.class);
	
	protected long mTimeToWaitBetweenRetry; 
	
	protected HttpRetrieverConfiguration mConfiguration;
	protected HttpRetrieverResponseCodeBehaviour mResponseCodeBehaviour = 
		createDefaultHttpRetrieverBehaviour();
	
	protected List<String> mCookieList;
	protected URL mReferer;
	
	protected GetMethod mGet = null;
	protected HttpMetadata mMetadata;
	protected int mResultCode = RESULT_RETRIEVAL_NOT_STARTED_YET;
	
	protected boolean mAbort = false;

	protected long mBytesToSkip;
	
	public HttpRetriever(URL pUrl,
			HttpRetrieverConfiguration pHttpRetrieverConfiguration) {
		super();
		setUrl(pUrl);
		setConfiguration(pHttpRetrieverConfiguration);
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#connect()
	 */
	public void connect() throws IOException {
		
		if(mAbort) {
			throw new IllegalStateException("Retriever is aborted.");
		}
		
		if(isConnected()) {
			throw new IllegalStateException("Already connected");
		}
		
		HttpClient client = getHttpClientFromConfiguration();
		
		mGet = new GZIPAwareGetMethod(mUrl.toString());
		mGet.setFollowRedirects(false);
		mGet.setDoAuthentication(true);
		
		HttpMethodParams params = mGet.getParams();
		params.setSoTimeout(90 * 1000); //90 seconds
		
		//set cookie single header to improve compatibility
		params.setBooleanParameter("http.protocol.single-cookie-header", true);

		if(mBytesToSkip > 0) { //try to resume
			mGet.addRequestHeader("Range", "bytes=" + mBytesToSkip + "-");
		}
		
		//accept gzip
		mGet.addRequestHeader("Accept-Encoding", "gzip");
		
		//add referer if existing
		if(mReferer != null) {
			mGet.addRequestHeader("Referer", mReferer.toExternalForm());
		}
		
		//add cookies to header
		List<String> cookieList = getCookieList();
		if(cookieList != null && cookieList.size() > 0) {
			CookieHelper cookieHelper = new CookieHelper();
			mGet.addRequestHeader("Cookie", cookieHelper.buildCookieString(cookieList));
		}
		
		client.executeMethod(mGet);
		mLog.debug("Connected to: " + mUrl + " Status: " + mGet.getStatusCode());
		
		buildMetadata();
		analyzeResultCode();
		
	}

	private void buildMetadata() {
		
		//build metadata
		mMetadata = new HttpMetadata();
		
		Header contentType = mGet.getResponseHeader("Content-Type");
		if(contentType != null && mGet.getStatusCode() < 400) {
			mMetadata.setContentType(contentType.getValue());	
		} else {
			mMetadata.setContentType("undefined");
		}

		//try to get the filename from the content disposition header field
		String filename = null;
		Header contentDisposition = mGet.getResponseHeader("Content-Disposition");
		if(contentDisposition != null) {
			HeaderElement[] elements = contentDisposition.getElements();
			for (HeaderElement headerElement : elements) {
				if(headerElement.getName().equals("filename")) {
					filename = headerElement.getValue();
				}
			}
		}
		
		//try to get it from the url
		if(filename == null) {
			filename = UrlUtils.getFilenameFromUrl(getUrl());
		}
		
		//try defaults
		if(filename == null) {
			if(mMetadata.getContentType().startsWith("text/html")) {
				filename = "index.html";
			} else {
				filename = "unknown";
			}
		}
		mMetadata.setFilename(filename);
		
		mMetadata.setContentLength(mGet.getResponseContentLength());
		mMetadata.setStatusCode(mGet.getStatusCode());
		mMetadata.setStatusText(mGet.getStatusText());
		mMetadata.setEncoding(mGet.getResponseCharSet());
		
		Header[] responseHeaders = mGet.getResponseHeaders();
		Map<String, String[]> headers = new HashMap<String, String[]>(responseHeaders.length);
		for (Header header : responseHeaders) {
			//get the value unformatted
			String[] elementsAsString = new String[]{header.getValue()};
			headers.put(header.getName(), elementsAsString);
		}
		mMetadata.setResponseHeader(headers);
		
	}
	
	protected HttpClient getHttpClientFromConfiguration() {
		
		HttpRetrieverConfiguration configuration = getConfiguration();
		
		//try to get share httpclient from configuration
		HttpClient httpClient = 
			(HttpClient) configuration.getSharedObjects().get("AdvancedHttpRetriever_HttpClient");
		
		if(httpClient == null) {
			//httpclient instance not available, create a new one.
			
			synchronized (configuration) {
			
				//try again, because in the time waiting for the lock, the http client 
				//could be created by another thread.
				httpClient = 
					(HttpClient) configuration.getSharedObjects().get("AdvancedHttpRetriever_HttpClient");
				
				//create and save the http client
				if(httpClient == null) {
		     		httpClient = createHttpClient(configuration);
		     		configuration.getSharedObjects().put(
		     				"AdvancedHttpRetriever_HttpClient", httpClient);
				}
			}
		}
		
		return httpClient;
	}

	protected HttpClient createHttpClient(HttpRetrieverConfiguration pConfiguration) {
		
     	MultiThreadedHttpConnectionManager connectionManager = 
      		new MultiThreadedHttpConnectionManager();
     	
     	HttpClient httpClient = new HttpClient(connectionManager);
     	
     	//disabled ssl certificate check
     	Protocol easyhttps = new Protocol("https", 
     			(ProtocolSocketFactory)new EasySSLProtocolSocketFactory(), 443);
     	Protocol.registerProtocol("https", easyhttps);
     	
     	if(pConfiguration != null) {
     		
     		HttpConnectionManagerParams connectionManagerParams = connectionManager.getParams();
     		
     		//set max connections per server
     		if(pConfiguration.getMaxConnectionsPerServer() != null) {
     			connectionManagerParams.setDefaultMaxConnectionsPerHost(
     					pConfiguration.getMaxConnectionsPerServer() );
     		}

     		//set proxy configuration
     		if(pConfiguration.isProxyEnabled()) {
     			
     			if(pConfiguration.getProxyServer() == null || 
     					pConfiguration.getProxyPort() == null) {
     				throw new IllegalArgumentException("Proxy usage enabled but server or port is null");
     			}
     			
     			httpClient.getHostConfiguration().setProxy(
     					pConfiguration.getProxyServer(), 
     					pConfiguration.getProxyPort());
     		}
     		if(pConfiguration.isProxyAuthenticationEnabled()) {
     			
     			httpClient.getState().setProxyCredentials(
     					new AuthScope(
     							pConfiguration.getProxyServer(), 
     							AuthScope.ANY_PORT, 
     							pConfiguration.getProxyRealm(), 
     							AuthScope.ANY_SCHEME),
     					new UsernamePasswordCredentials(
     							pConfiguration.getProxyUser(), 
     							pConfiguration.getProxyPassword()));
     		}
     		if(pConfiguration.getAuthenticationCredentials().size() > 0) {
     			
     			List<HttpAuthenticationCredentials> authenticationCredentials = 
     				pConfiguration.getAuthenticationCredentials();
     			
     			AuthScope serverAuthScope;
     			Credentials serverCredentials;
     			
     			for (HttpAuthenticationCredentials credentials : 
     					authenticationCredentials) {
					
     				serverAuthScope = new AuthScope(
     						credentials.getHost(), 
 							AuthScope.ANY_PORT, 
 							AuthScope.ANY_REALM, 
 							AuthScope.ANY_SCHEME);
     				
    				serverCredentials = new UsernamePasswordCredentials(
    						credentials.getUser(), 
    						credentials.getPassword());
     				
    				//add credentials to httpclient
    				httpClient.getState().setCredentials(
    						serverAuthScope, serverCredentials);
    				
				}
     			/*
     			httpClient.getParams().setParameter(
     	        		CredentialsProvider.PROVIDER, 
     	        		new ConfigurationAuthProvider(pConfiguration.getAuthenticationCredentials()));
     	        		*/
     		}
     		
     		HttpClientParams httpClientParams = httpClient.getParams();
     		
     		if(pConfiguration.getUserAgent() != null) {
     			httpClientParams.setParameter(HttpMethodParams.USER_AGENT, 
   						pConfiguration.getUserAgent());
     		}
     		
     	}
     	
		return httpClient;
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#isDataAvailable()
	 */
	public boolean isDataAvailable() throws IOException {
		if(mGet == null) {
			throw new IllegalStateException("Not connected!");
		}
		
		return mGet.getStatusCode() < 300;
	}
	
	@Override
	public boolean isConnected() throws IOException {
		return mGet != null;
	}	
	
	public InputStream getDataAsInputStream() throws IOException {
		
		if(mGet == null) {
			throw new IllegalStateException("Not connected");
		}
		
		InputStream in = mGet.getResponseBodyAsStream();
		
		HttpRetrieverConfiguration httpRetrieverConfiguration = getConfiguration();
		
		if(httpRetrieverConfiguration != null) {
			Integer bandwidthLimit = httpRetrieverConfiguration.getBandwidthLimit();
			
			if(bandwidthLimit != null && bandwidthLimit > 0) {
				//build throttled stream
				in = new ThrottledInputStream(in, bandwidthLimit);
			}
		}
		
		return in;
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#disconnect()
	 */
	public void disconnect() {
		
		if(mGet != null) {
			mGet.abort();
			mGet.releaseConnection();
			mGet = null;
			mResultCode = RESULT_RETRIEVAL_NOT_STARTED_YET;
			
			mLog.debug("Disconnected from: " + mUrl);
		}
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#getMetadata()
	 */
	public HttpMetadata getMetadata() {
		return mMetadata;
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#abort()
	 */
	public void abort() {
		mAbort = true;
		if(mGet != null) {
			mGet.abort();
		}
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#setBytesToSkip(long)
	 */
	public void setBytesToSkip(long pBytesToSkip) {
		if(mGet != null && mGet.isRequestSent()) {
			throw new IllegalStateException("Request already sent!");
		}
		
		mBytesToSkip = pBytesToSkip;
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#getBytesSkipped()
	 */
	public long getBytesSkipped() {
		
		if(mGet == null || !mGet.isRequestSent()) {
			throw new IllegalStateException("Request not sent!");
		}
		
		if(mMetadata.getStatusCode() == HTTP_STATUS_PARTIAL_CONTENT ||
				mMetadata.getStatusCode() == HTTP_STATUS_RANGE_NOT_SATISFIABLE) {
			return mBytesToSkip;
		} else {
			return 0;
		}
		
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.DataRetriever#getResultCode()
	 */
	public int getResultCode() {
		
		int result; 
		
		if(mAbort) {
			result = RESULT_RETRIEVAL_ABORTED;
		} else {
			result = mResultCode;
		}
		
		return result;
	}
		
	protected void analyzeResultCode() {
		
		int statusCode = mMetadata.getStatusCode();
		
		ResponseCodeRange responseCodeConfig = 
			mResponseCodeBehaviour.findConfigurationForResponseCode(statusCode);
		
		if(responseCodeConfig == null) {
			throw new IllegalStateException("No action found for response code: " + statusCode);
		}
		
		//save result code
		Action action = responseCodeConfig.getAction();
		mResultCode = action.getRetrieverAction();
		
		//resolve time to wait 
		if(responseCodeConfig.getTimeToWaitBetweenRetry() != null) {
			mTimeToWaitBetweenRetry = responseCodeConfig.getTimeToWaitBetweenRetry();
		} else {
			mTimeToWaitBetweenRetry = DEFAULT_TIME_TO_WAIT_BETWEEN_RETRY;
		}
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.io.UrlDataRetriever#getSuggestedTimeToWaitForRetry()
	 */
	public long getSuggestedTimeToWaitForRetry() {
		return mTimeToWaitBetweenRetry;
	}

	public long getContentLenght() {
		if(mMetadata == null) {
			throw new IllegalStateException("Not connected");
		}
		
		return mMetadata.getContentLength();
	}

	public HttpRetrieverConfiguration getConfiguration() {
		return mConfiguration;
	}

	public void setConfiguration(HttpRetrieverConfiguration pConfiguration) {
		if(pConfiguration == null) {
			throw new NullPointerException("Configuration is null.");
		}
		
		mConfiguration = pConfiguration;
	}	

	public HttpRetrieverResponseCodeBehaviour getResponseCodeBehaviour() {
		return mResponseCodeBehaviour;
	}

	public void setResponseCodeBehaviour(
			HttpRetrieverResponseCodeBehaviour pResponseCodeBehaviour) {
		mResponseCodeBehaviour = pResponseCodeBehaviour;
	}
	
	protected static HttpRetrieverResponseCodeBehaviour createDefaultHttpRetrieverBehaviour() {
		
		HttpRetrieverResponseCodeBehaviour defaultBehaviour = new HttpRetrieverResponseCodeBehaviour();
		
		//all between 100 and 399 is ok
		defaultBehaviour.add(100, 399, HttpRetrieverResponseCodeBehaviour.Action.OK,
				HttpRetrieverResponseCodeBehaviour.ResponseCodeRange.LOW_PRIORITY);

		//resume not possible because file is already fully downloaded.
		defaultBehaviour.add(HTTP_STATUS_RANGE_NOT_SATISFIABLE, 
				HttpRetrieverResponseCodeBehaviour.Action.OK, 
				HttpRetrieverResponseCodeBehaviour.ResponseCodeRange.LOW_PRIORITY);
		
		defaultBehaviour.add(HTTP_STATUS_REQUEST_TIMEOUT, 
				HttpRetrieverResponseCodeBehaviour.Action.FAILED_BUT_RETRYABLE,
				HttpRetrieverResponseCodeBehaviour.ResponseCodeRange.LOW_PRIORITY);
		
		defaultBehaviour.add(HTTP_STATUS_INTERNAL_SERVER_ERROR, 
				HttpRetrieverResponseCodeBehaviour.Action.FAILED_BUT_RETRYABLE,
				HttpRetrieverResponseCodeBehaviour.ResponseCodeRange.LOW_PRIORITY);
		
		defaultBehaviour.add(HTTP_STATUS_SERVICE_UNAVAILABLE, 
				HttpRetrieverResponseCodeBehaviour.Action.FAILED_BUT_RETRYABLE,
				HttpRetrieverResponseCodeBehaviour.ResponseCodeRange.LOW_PRIORITY);
		
		defaultBehaviour.add(HTTP_STATUS_GATEWAY_TIMEOUT, 
				HttpRetrieverResponseCodeBehaviour.Action.FAILED_BUT_RETRYABLE,
				HttpRetrieverResponseCodeBehaviour.ResponseCodeRange.LOW_PRIORITY);
		
		
		//default is failed when return code is not known.
		defaultBehaviour.add(0, Integer.MAX_VALUE, 
				HttpRetrieverResponseCodeBehaviour.Action.FAILED,
				HttpRetrieverResponseCodeBehaviour.ResponseCodeRange.LOW_PRIORITY);
		
		
		return defaultBehaviour;
	}
	
	public List<String> getCookieList() {
		if(mCookieList != null) {
			return Collections.unmodifiableList(mCookieList);
		} else {
			return null;
		}
	}
	public void setCookieList(List<String> pCookieList) {
		mCookieList = new ArrayList<String>(pCookieList);
	}

	public URL getReferer() {
		return mReferer;
	}

	public void setReferer(URL pReferer) {
		mReferer = pReferer;
	}
	
}
