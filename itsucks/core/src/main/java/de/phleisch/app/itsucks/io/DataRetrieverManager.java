/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 * 
 * $Id$
 */

package de.phleisch.app.itsucks.io;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class DataRetrieverManager implements ApplicationContextAware {

	private ApplicationContext mContext;
	private List<String> mRetriever;
	
	public DataRetrieverManager() {
		super();
	}

	public DataRetriever getRetrieverForProtocol(String pProtocol) {
		return (DataRetriever) mContext.getBean(mRetriever.get(0));
	}
	
	public void setRetriever(List<String> pList) {
		mRetriever = new ArrayList<String>(pList);
	}

	public void setApplicationContext(ApplicationContext pContext) throws BeansException {
		mContext = pContext;
	}

}
