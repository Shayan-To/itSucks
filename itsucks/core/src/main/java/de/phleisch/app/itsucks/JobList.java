/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * $Id$
 * Created on 31.01.2007
 */

package de.phleisch.app.itsucks;

import java.util.Observer;

public interface JobList extends Observer {

	public static final int NOTIFICATION_JOB_ADDED  = 1000;
	public static final int NOTIFICATION_JOB_REMOVED = 2000;
	//public static final int NOTIFICATION_JOB_CHANGED = 3000;
	
	public abstract void addJob(Job pJob);

	public abstract void removeJob(Job pJob);

	public abstract void clear();

	public abstract Job getNextOpenJob();
	
	
	public void addObserver(Observer o);
	public void deleteObserver(Observer o);

}