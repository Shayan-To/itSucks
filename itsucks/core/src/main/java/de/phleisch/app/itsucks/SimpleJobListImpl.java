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

public class SimpleJobListImpl extends Observable implements Observer, JobList {

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
	
	public SimpleJobListImpl() {
		super();
		
		mJobList = new TreeSet<Job>();
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.IJobList#addJob(de.phleisch.app.itsucks.Job)
	 */
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
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.IJobList#removeJob(de.phleisch.app.itsucks.Job)
	 */
	public void removeJob(Job pJob) {
		synchronized (this) {
			pJob.deleteObserver(this);
			mJobList.remove(pJob);
			setChanged();
		}
		
		this.notifyObservers(
				new JobListNotification(NOTIFICATION_JOB_REMOVED, pJob));
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.IJobList#clear()
	 */
	public void clear() {
		synchronized (this) {
			mJobList.clear();
		}
	}

	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.IJobList#getNextOpenJob()
	 */
	public Job getNextOpenJob() {

		synchronized (this) {
			Job job = mJobList.first();
			if(job.getState() == Job.STATE_OPEN) { 
				return job;
			}
			
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
