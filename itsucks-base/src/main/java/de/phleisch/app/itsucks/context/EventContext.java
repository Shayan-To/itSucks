/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * $Id$
 * Created on 07.02.2008
 */

package de.phleisch.app.itsucks.context;

import de.phleisch.app.itsucks.event.EventDispatcher;

public class EventContext extends Context {

	private EventDispatcher mEventDispatcher = null;
	
	public EventDispatcher getEventDispatcher() {
		return mEventDispatcher;
	}

	public void setEventDispatcher(EventDispatcher pEventDispatcher) {
		mEventDispatcher = pEventDispatcher;
	}
	
}
