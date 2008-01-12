/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 * 
 * $Id$
 */

package de.phleisch.app.itsucks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.phleisch.app.itsucks.constants.ApplicationConstants;

public class SpringContextSingelton {

	private static Log mLog = LogFactory.getLog(SpringContextSingelton.class);
	
	private boolean mInitialized = false;
	private ApplicationContext mApplicationContext;
	private static SpringContextSingelton mMyself = null;
	
	static {
		mMyself = new SpringContextSingelton();
	}
	
	private SpringContextSingelton() {
	}

	private synchronized void initialize() {
		
		if(isInitialized()) return;
		
		String configFile = ApplicationConstants.CORE_SPRING_CONFIG_FILE;
		
		mLog.info("Load spring context from classpath: " + configFile);
		
		mApplicationContext = new ClassPathXmlApplicationContext(configFile);
		
		if(mApplicationContext == null) {
			throw new RuntimeException("Can't initialize spring framework!");
		} else {
			mInitialized = true;
		}
	}

	private boolean isInitialized() {
		return mInitialized;
	}
	
	public static ApplicationContext getApplicationContext() {
		
		if(!mMyself.isInitialized()) {
			mMyself.initialize();
		}
		
		return mMyself.mApplicationContext;
	}
	
}
