/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * $Id$
 * Created on 27.04.2007
 */

package de.phleisch.app.itsucks.event.job;

import de.phleisch.app.itsucks.event.Event;
import de.phleisch.app.itsucks.event.impl.SimpleEvent;
import de.phleisch.app.itsucks.job.Job;

/**
 * Implementation of an Event fired by jobs.
 * 
 * @author olli
 *
 */
public class JobEvent extends SimpleEvent {

	private Job mJob;
	
	public JobEvent(Event pEvent, Job pJob) {
		super(pEvent);
		
		mJob = pJob;
	}
	
	public JobEvent(int pType, int pCategory, Job pJob) {
		super(pType, pCategory);
		
		mJob = pJob;
	}

	/**
	 * Returns the job associated to this event.
	 * @return
	 */
	public Job getJob() {
		return mJob;
	}

}
