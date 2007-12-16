/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 * 
 * $Id: Dispatcher.java,v 1.1 2006-12-03 19:31:43 olli Exp $
 * Created on 03.03.2006
 *
 */

package de.phleisch.app.itsucks.core.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import de.phleisch.app.itsucks.core.Dispatcher;
import de.phleisch.app.itsucks.core.WorkerPool;
import de.phleisch.app.itsucks.event.EventDispatcher;
import de.phleisch.app.itsucks.event.impl.CoreEvents;
import de.phleisch.app.itsucks.filter.JobFilter;
import de.phleisch.app.itsucks.job.Context;
import de.phleisch.app.itsucks.job.Job;
import de.phleisch.app.itsucks.job.impl.FilterJobManagerImpl;

/**
 * The dispatcher is the central class to start an job.
 * It contains a JobManager which holds and manages all Jobs and a Worker
 * Pool which contains a thread pool to do the work.
 * 
 * @author olli
 *
 */
public class DispatcherImpl implements ApplicationContextAware, Dispatcher {
	
	@SuppressWarnings("unused")
	private ApplicationContext mSpringApplicationContext;
	
	private String mName;
	private Context mContext;
	private FilterJobManagerImpl mJobManager;
	private WorkerPool mWorkerPool;
	private EventDispatcher mEventManager;
	
	private int mDispatchDelay = 0;
	private boolean mRunning;
	private boolean mStop;

	private boolean mPause;
	
	private static Log mLog = LogFactory.getLog(DispatcherImpl.class);
	
	public DispatcherImpl() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.core.Dispatcher#getName()
	 */
	public String getName() {
		return mName;
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.core.Dispatcher#setName(java.lang.String)
	 */
	public void setName(String pName) {
		mName = pName;
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.Dispatcher#processJobs()
	 */
	public void processJobs() throws Exception{

		synchronized(this) {
			if(isRunning()) {
				return;
			} else {
				setRunning(true);
			}
		}
		
		mLog.info("Start processing jobs");
		mEventManager.fireEvent(CoreEvents.EVENT_EVENTDISPATCHER_CMD_START);
		mEventManager.fireEvent(CoreEvents.EVENT_DISPATCHER_START);
		
		startup();
		
		Job job;
		while(true) {
			
			if(mPause) {
				doPauseLoop();
			}
			
			
			if(mStop) { //check for stop event
				job = null;
			} else {
				job = mJobManager.getNextOpenJob();
			}
			
			if(job == null) {
				
				if(mWorkerPool.getBusyWorkerCount() > 0) {
					//stop dispatcher only when all working threads finished working
					Thread.sleep(100);
				} else {
					//shutdown dispatcher
					break;
				}
				
			} else {
				mWorkerPool.dispatchJob(job);
				
				if(mDispatchDelay > 0) {
					Thread.sleep(mDispatchDelay);
				}
				
			}
			
		}
		
		shutdown();
		setRunning(false);
		
		mLog.info("Finished processing jobs");
		mEventManager.fireEvent(CoreEvents.EVENT_DISPATCHER_FINISH);
		mEventManager.fireEvent(CoreEvents.EVENT_EVENTDISPATCHER_CMD_STOP);
	}

	private void doPauseLoop() throws InterruptedException {
		
		mEventManager.fireEvent(CoreEvents.EVENT_DISPATCHER_PAUSE);
		
		while(mPause) {
			synchronized (this) {
				if(mPause) { //check again
					this.wait(); // wait until unpause notify
				}
			}
		}
		
		mEventManager.fireEvent(CoreEvents.EVENT_DISPATCHER_UNPAUSE);
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.Dispatcher#stop()
	 */
	public void stop() {
		unpause();
		mStop = true;
		mWorkerPool.abortBusyWorker();
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.Dispatcher#pause()
	 */
	public void pause() {
		mPause = true;
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.Dispatcher#unpause()
	 */
	public void unpause() {
		if(mPause) {
			synchronized (this) {
				if(mPause) {
					mPause = false;
					this.notifyAll();
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.Dispatcher#isPaused()
	 */
	public boolean isPaused() {
		return mPause;
	}
	
	private void startup() {
		mJobManager.setContext(mContext);
		mWorkerPool.initialize();
	}
	
	private void shutdown() {
		mWorkerPool.shutdown();
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.Dispatcher#getJobManager()
	 */
	public FilterJobManagerImpl getJobManager() {
		return mJobManager;
	}

	public void setFilterJobManager(FilterJobManagerImpl pJobManager) {
		mJobManager = pJobManager;
		mJobManager.setContext(mContext);
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.Dispatcher#getWorkerPool()
	 */
	public WorkerPool getWorkerPool() {
		return mWorkerPool;
	}

	public void setWorkerPool(WorkerPool pWorkerPool) {
		mWorkerPool = pWorkerPool;
	}
	
	public void setApplicationContext(ApplicationContext pContext) throws BeansException {
		mSpringApplicationContext = pContext;
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.Dispatcher#getEventManager()
	 */
	public EventDispatcher getEventManager() {
		return mEventManager;
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.Dispatcher#getContext()
	 */
	public Context getContext() {
		return mJobManager.getContext();
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.Dispatcher#getDispatchDelay()
	 */
	public int getDispatchDelay() {
		return mDispatchDelay;
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.Dispatcher#setDispatchDelay(int)
	 */
	public void setDispatchDelay(int pDispatchDelay) {
		mDispatchDelay = pDispatchDelay;
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.Dispatcher#isRunning()
	 */
	public boolean isRunning() {
		return mRunning;
	}

	private void setRunning(boolean pRunning) {
		mRunning = pRunning;
	}

	//Delegate methods for easy use

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.Dispatcher#addJob(de.phleisch.app.itsucks.Job)
	 */
	public void addJob(Job pJob) {
		mJobManager.addJob(pJob);
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.Dispatcher#addJobFilter(de.phleisch.app.itsucks.filter.JobFilter)
	 */
	public void addJobFilter(JobFilter pJobFilter) {
		mJobManager.addJobFilter(pJobFilter);
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.Dispatcher#addJobFilter(java.util.List)
	 */
	public void addJobFilter(List<JobFilter> pJobFilter) {
		mJobManager.addJobFilter(pJobFilter);
	}

	public void setContext(Context pContext) {
		mContext = pContext;
	}

	public void setEventManager(EventDispatcher pEventManager) {
		
		if(mContext == null) {
			throw new IllegalStateException("Context is not set!");
		}
		
		mEventManager = pEventManager;
		mContext.setEventDispatcher(mEventManager);
	}

}
