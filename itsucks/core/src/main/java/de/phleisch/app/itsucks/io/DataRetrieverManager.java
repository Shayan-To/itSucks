/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 * 
 * $Id$
 */

package de.phleisch.app.itsucks.io;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class DataRetrieverManager implements ApplicationContextAware {

	private ApplicationContext mContext;
	private Map<String, String> mRetriever;
	
	public DataRetrieverManager() {
		super();
	}

	public DataRetriever getRetrieverForProtocol(String pProtocol) {
		DataRetriever retriever = null;
		
		String retrieverBeanName = mRetriever.get(pProtocol);
		if(retrieverBeanName != null) {
			retriever = (DataRetriever) mContext.getBean(retrieverBeanName);
		}
		
		return retriever;
	}
	
	public void setRetriever(Map<String, String> pRetriever) {
		mRetriever = new HashMap<String, String>(pRetriever);
	}

	public void setApplicationContext(ApplicationContext pContext) throws BeansException {
		mContext = pContext;
	}

}
