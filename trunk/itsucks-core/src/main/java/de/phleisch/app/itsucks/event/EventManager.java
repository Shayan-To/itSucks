/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * $Id$
 * Created on 22.04.2007
 */

package de.phleisch.app.itsucks.event;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EventManager {

	private static Log mLog = LogFactory.getLog(EventManager.class);
	
	private boolean initialized = false;
	
	private BlockingDeque<Event> mEventDequeue = 
		new LinkedBlockingDeque<Event>();

	private Set<EventObserverConfig> mRegisteredObserver =
		new HashSet<EventObserverConfig>();
	
	private EventManagerThread mEventThread;
	private static int mEventManagerThreadCounter = 0;
	
	public EventManager() {
	}
	
	public synchronized void init() {
		if(initialized) return;
		
		mEventThread = new EventManagerThread();
		mEventThread.start();
		initialized = true;
	}
	
	public synchronized void shutdown() {
		if(!initialized) return;
		
		mEventThread.stopThread();
		mEventThread = null;
		initialized = false;
	}
	
	public void fireEvent(final Event pEvent) {
		mLog.debug("Got event: " + pEvent);
		
		//do not dispatch this event if this is an system cmd
		if(pEvent.getCategory() == CoreEvents.EVENT_CATEGORY_SYSTEM_CMD) {
			handleSystemCmd(pEvent);
			return;
		}

		//add the event at the tail of the deque
		mEventDequeue.add(pEvent);
	}

	private void handleSystemCmd(final Event pEvent) {
		if(pEvent.getType() == CoreEvents.EVENT_MANAGER_CMD_START.getType()) {
			init();
		} else if(pEvent.getType() == CoreEvents.EVENT_MANAGER_CMD_STOP.getType()) {
			shutdown();
		}
	}

	public void registerObserver(EventObserver pObserver) {
		registerObserver(pObserver, null);
	}

	public void registerObserver(EventObserver pObserver, EventFilter pFilter) {
		
		synchronized (mRegisteredObserver) {
			mRegisteredObserver.add(new EventObserverConfig(pObserver, pFilter));
		}
	}
	
	public void unregisterObserver(EventObserver pObserver) {
		
		synchronized (mRegisteredObserver) {
			mRegisteredObserver.remove(new EventObserverConfig(pObserver, null));
		}
	}
	
	private class EventObserverConfig {
		
		private EventObserver mObserver;
		private EventFilter mFilter;
		
		public EventObserverConfig(EventObserver pObserver, EventFilter pFilter) {
			mObserver = pObserver;
			mFilter = pFilter;
		}

		public EventFilter getFilter() {
			return mFilter;
		}

		public EventObserver getObserver() {
			return mObserver;
		}

		@Override
		public int hashCode() {
			return mObserver.hashCode();
		}
	}
	
	private class EventManagerThread extends Thread {

		private boolean mStop;
		private List<EventObserverConfig> mLocalObserverCopy;
		
		public EventManagerThread() {
			setDaemon(true);
			setName("EventManagerThread-" + ++mEventManagerThreadCounter);
		}
		
		@Override
		public void run() {
			mStop = false;
			mLocalObserverCopy = new ArrayList<EventObserverConfig>(
				mRegisteredObserver.size() > 10 ? mRegisteredObserver.size() : 10);

			try {
				processEvents();
			} catch(RuntimeException ex) {
				mLog.fatal("Event manager thread died unexpected.", ex);
				
				throw ex;
			}
			
			mLog.debug("Event manager thread shut down.");
		}

		private void processEvents() {
			
			Event event;
			
			while(!mStop) {
				
				try {
					event = mEventDequeue.takeFirst();
					if(event != null) {
						dispatchEvent(event);
					}
						
				} catch (InterruptedException e) {
					mLog.info(e, e);
				}
				
			}
			
		}

		private void dispatchEvent(final Event pEvent) {
			
			//create a local copy to hold synchronized part as small as possible
			mLocalObserverCopy.clear();
			
			//get all observer which will receive this event
			synchronized (mRegisteredObserver) {
				
				EventFilter filter;
				for (EventObserverConfig config : mRegisteredObserver) {

					filter = config.getFilter();
					if(filter != null) {
						
						if(filter.isEventAccepted(pEvent)) {
							mLocalObserverCopy.add(config);
						}
						
					} else {
						mLocalObserverCopy.add(config);
					}
					
				}
			} // end synchronized
			
			//dispatch the event to the found observer
			for (EventObserverConfig config : mLocalObserverCopy) {
				config.getObserver().processEvent(pEvent);
			}
		}
		
		public void stopThread() {
			mStop = true;
			
			mEventThread.interrupt();
		}
		
	}
	
}
