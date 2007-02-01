/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * $Id$
 * Created on 31.01.2007
 */

package de.phleisch.app.itsucks;

public class JobListNotification {
	
	public JobListNotification(int pMessage, Job pAffectedJob) {
		message = pMessage;
		affectedJob = pAffectedJob;
	}
	
	public int message;
	public Job affectedJob;
}