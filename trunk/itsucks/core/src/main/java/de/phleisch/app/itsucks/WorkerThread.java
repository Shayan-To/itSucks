/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 * 
 * $Id: WorkerThread.java,v 1.1 2006-12-03 19:31:43 olli Exp $
 * Created on 03.03.2006
 */

package de.phleisch.app.itsucks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class WorkerThread extends Thread {

	private static Log mLog = LogFactory.getLog(WorkerThread.class);
	
	public final static int CMD_SHUTDOWN = 0;
	public final static int CMD_PROCESS_JOB = 1;
	public final static int CMD_RETURN_TO_POOL = 2;
	
	private final static long CMD_POLL_INTERVAL = 50; 
	
	private List<Integer> mCommandQueue = new ArrayList<Integer>();
	private Job mJob;
	private boolean mShutdown = false;
	private WorkerPool mPool;
	
	public WorkerThread(WorkerPool pWorkerPool, String pName) {
		super(pName);
		mPool = pWorkerPool;
		this.setDaemon(true);
	}
	
	public void abort() {
		mJob.abort();
		this.interrupt();
	}

	@Override
	public void run() {
		super.run();
		
		try {
			waitForWork();
		} catch (InterruptedException e) {
			mLog.error("Worker Thread Interrupted", e);
		}
	}

	
	private void waitForWork() throws InterruptedException {
		while(!mShutdown) {
			
			if(mCommandQueue.size() == 0) {
				try {
					Thread.sleep(CMD_POLL_INTERVAL);
				} catch(InterruptedException ex) {
					mLog.info("Worker Thread interrupted", ex);
				}
			} else {
				doWork();
			}
			
		}
	}

	private void doWork() {
		int cmd = mCommandQueue.get(0);
		mCommandQueue.remove(0);
		
		switch(cmd) {
		
			case CMD_SHUTDOWN:
				mShutdown = true;
				break;
				
			case CMD_PROCESS_JOB:
				mLog.info("Start working on job: " + mJob);
				mJob.setState(Job.STATE_IN_PROGRESS);
				
				try {
					mJob.run();
					mJob.setState(Job.STATE_FINISHED);
				} catch(Exception ex) {
					mJob.setState(Job.STATE_ERROR);
					mLog.error("Error executing job: " + mJob, ex);
				}
				
				mLog.info("Finished working on job: " + mJob);
				mJob = null;
				break;
		
			case CMD_RETURN_TO_POOL:
				mPool.returnWorker(this);
				break;
		}
	}
	
	public void addCommand(int pCmd) {
		if(mShutdown) {
			throw new IllegalStateException("Shutdown in progress!");
		}
		
		mCommandQueue.add(pCmd);
	}
	
	public Job getJob() {
		return mJob;
	}

	public void setJob(Job pJob) {
		mJob = pJob;
	}
	
}
