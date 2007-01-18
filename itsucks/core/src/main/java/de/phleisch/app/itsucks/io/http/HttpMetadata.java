/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * $Id$
 * Created on 15.01.2007
 */

package de.phleisch.app.itsucks.io.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;

import de.phleisch.app.itsucks.io.Metadata;

public class HttpMetadata extends Metadata {

	private String mMimetype;
	private int mStatusCode;
	private HttpMethodBase mConnection;
	
	public String getMimetype() {
		return mMimetype;
	}
	public void setMimetype(String mimetype) {
		mMimetype = mimetype;
	}
	public int getStatusCode() {
		return mStatusCode;
	}
	public void setStatusCode(int statusCode) {
		mStatusCode = statusCode;
	}
	public void setConnection(HttpMethodBase pConnection) {
		mConnection = pConnection;
	}
	public String[] getHeaderField(String pName) {
		List<String> fields = new ArrayList<String>();
		
		HeaderElement[] values = new HeaderElement[0];
		try {
			values = mConnection.getRequestHeader(pName).getValues();
		} catch (HttpException e) {
			throw new RuntimeException(e);
		}
		for (int i = 0; i < values.length; i++) {
			fields.add(values[i].getValue());
		}
		
		return (String[]) fields.toArray(new String[fields.size()]);
	}
}
