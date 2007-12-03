/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * $Id$
 * Created on 30.09.2007
 */

package de.phleisch.app.itsucks.io.http.impl;

public class HttpRetrieverConfiguration {

	public static final String CONTEXT_PARAMETER_HTTP_RETRIEVER_CONFIGURATION = 
		"HttpRetrieverConfiguration";
	
	private Integer mMaxConnectionsPerServer = null;
	
	private boolean mProxyEnabled = false;
	private String mProxyServer;
	private Integer mProxyPort;
	
	private boolean mProxyAuthenticationEnabled = false;
	private String mProxyUser;
	private String mProxyPassword;
	private String mProxyRealm;
	
	
	public Integer getMaxConnectionsPerServer() {
		return mMaxConnectionsPerServer;
	}
	public void setMaxConnectionsPerServer(Integer pMaxConnectionsPerServer) {
		mMaxConnectionsPerServer = pMaxConnectionsPerServer;
	}
	public boolean isProxyEnabled() {
		return mProxyEnabled;
	}
	public void setProxyEnabled(boolean pProxyEnabled) {
		mProxyEnabled = pProxyEnabled;
	}
	public String getProxyServer() {
		return mProxyServer;
	}
	public void setProxyServer(String pProxyServer) {
		mProxyServer = pProxyServer;
	}
	public Integer getProxyPort() {
		return mProxyPort;
	}
	public void setProxyPort(Integer pProxyPort) {
		mProxyPort = pProxyPort;
	}
	public boolean isProxyAuthenticationEnabled() {
		return mProxyAuthenticationEnabled;
	}
	public void setProxyAuthenticationEnabled(boolean pProxyAuthenticationEnabled) {
		mProxyAuthenticationEnabled = pProxyAuthenticationEnabled;
	}
	public String getProxyUser() {
		return mProxyUser;
	}
	public void setProxyUser(String pProxyUser) {
		mProxyUser = pProxyUser;
	}
	public String getProxyPassword() {
		return mProxyPassword;
	}
	public void setProxyPassword(String pProxyPassword) {
		mProxyPassword = pProxyPassword;
	}
	public String getProxyRealm() {
		return mProxyRealm;
	}
	public void setProxyRealm(String pProxyRealm) {
		mProxyRealm = pProxyRealm;
	}
	
}
