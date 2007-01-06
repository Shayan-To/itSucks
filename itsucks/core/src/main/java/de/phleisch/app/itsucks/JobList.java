/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details.
 *  
 * $Id: JobList.java,v 1.1 2006-12-03 19:31:43 olli Exp $
 * Created on 03.03.2006
 */

package de.phleisch.app.itsucks;

import java.util.Observable;
import java.util.Observer;
import java.util.SortedSet;
import java.util.TreeSet;

public class JobList extends Observable implements Observer {

	private SortedSet<Job> mJobList;
	private int mJobIdSequence = 0;
	
	public static final class JobListNotification {
		
		public JobListNotification(int pMessage, Job pAffectedJob) {
			message = pMessage;
			affectedJob = pAffectedJob;
		}
		
		public int message;
		public Job affectedJob;
	}
	
	public static final int NOTIFICATION_JOB_ADDED  = 1000;
	public static final int NOTIFICATION_JOB_REMOVED = 2000;
	public static final int NOTIFICATION_JOB_CHANGED = 3000;
	
	/*
	private class JobComparator implements Comparator{

		public int compare(Object pO1, Object pO2) {
			Job lhs = (Job) pO1;
			Job rhs = (Job) pO2;
			int result = new Integer(lhs.getState()).compareTo(new Integer(rhs.getState()));
			
			return -result;
		}
		
	}*/
	
	public JobList() {
		super();
		
		mJobList = new TreeSet<Job>();
	}

	public void addJob(Job pJob) {
		synchronized (this) {
			pJob.setId(mJobIdSequence ++);
			pJob.addObserver(this);
			mJobList.add(pJob);
			setChanged();
		}
		
		this.notifyObservers(
				new JobListNotification(NOTIFICATION_JOB_ADDED, pJob));
	}
	
	public void removeJob(Job pJob) {
		synchronized (this) {
			pJob.deleteObserver(this);
			mJobList.remove(pJob);
			setChanged();
		}
		
		this.notifyObservers(
				new JobListNotification(NOTIFICATION_JOB_REMOVED, pJob));
	}
	
	public void clear() {
		synchronized (this) {
			mJobList.clear();
		}
	}

	public Job getNextOpenJob() {

		synchronized (this) {
			Job job = mJobList.first();
			if(job.getState() == Job.STATE_OPEN) { 
				return job;
			}
			
			/*for (Job job : mJobList) {
				if(job.getState() != Job.STATE_OPEN) continue;
				return job;
			}*/
		}
		
		return null;
	}

	/**
	 * Is called when a job in the list has changed.
	 */
	public void update(Observable pO, Object pArg) {
		
		/**
		 * When a job has changed, the ordering of the list must be refreshed
		 */
		
		if((Integer)pArg == Job.NOTIFICATION_BEFORE_CHANGE) {
			synchronized (this) {
				mJobList.remove((Job)pO);
			}
		} else if((Integer)pArg == Job.NOTIFICATION_AFTER_CHANGE) {
			synchronized (this) {
				mJobList.add((Job)pO);
			}
		} else {
			throw new IllegalArgumentException("Unknown notification: " + pArg);
		}
	}
	
}
