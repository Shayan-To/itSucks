/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 * 
 * $Id$
 */

package de.phleisch.app.itsucks.gui.panel;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import de.phleisch.app.itsucks.Job;
import de.phleisch.app.itsucks.io.DownloadJob;

public class DownloadJobTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 7125707024529473869L;

	private static final int COLUMN_ID 			= 0;
	private static final int COLUMN_PRIORITY 	= 1;
	private static final int COLUMN_STATE 		= 2;
	private static final int COLUMN_PROGRESS 	= 3;
	private static final int COLUMN_URL 		= 4;
	
	private static final int COLUMN_COUNT 		= 5;

	private Vector<DownloadJob> mRows;
	private JobObserver mJobObserver;
	
	public DownloadJobTableModel() {
		mRows = new Vector<DownloadJob>(); //use vector to be thread safe
		mJobObserver = new JobObserver();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int pColumn) {
		
		String name;
		
		switch (pColumn) {
		case COLUMN_ID:
			name = "ID";
			break;
		
		case COLUMN_PRIORITY:
			name = "Priority";
			break;

		case COLUMN_STATE:
			name = "State";
			break;
			
		case COLUMN_URL:
			name = "URL";
			break;
			
		case COLUMN_PROGRESS:
			name = "Progress";
			break;			
			
		default:
			name = "";
			break;
		}
		
		return name;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return mRows.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int pRowIndex, int pColumnIndex) {
		
		DownloadJob job = mRows.get(pRowIndex);
		
		return getColumnValue(job, pColumnIndex);
	}
	
	public void addDownloadJob(DownloadJob pJob) {
		int index = mRows.size();
		mRows.add(pJob);
		fireTableRowsInserted(index, index);
		pJob.addObserver(mJobObserver);
	}
	
	public void removeDownloadJob(DownloadJob pJob) {
		int index = mRows.indexOf(pJob);
		if(index > -1) {
			mRows.remove(index);
			pJob.deleteObserver(mJobObserver);
			fireTableRowsDeleted(index, index);
		}
	}

	public void removeAllDownloadJobs() {
		for (DownloadJob job : mRows) {
			job.deleteObserver(mJobObserver);
		}
		mRows.clear();
		fireTableDataChanged();
	}
	
	private Object getColumnValue(DownloadJob pJob, int pColumnIndex) {
		
		Object val = null;
		
		switch (pColumnIndex) {
			case COLUMN_ID:
				val = pJob.getId();
				break;
				
			case COLUMN_PRIORITY:
				val = pJob.getPriority();
				break;
	
			case COLUMN_STATE:
				val = translateState(pJob.getState());
				break;			
			
			case COLUMN_URL:
				val = pJob.getUrl();
				break;		
	
			case COLUMN_PROGRESS:
				val = "-";
				break;		
				
			default:
				break;
		}
		return val;
	}

	
    private Object translateState(int pState) {
    	
    	String result;
    	
    	switch (pState) {
    	
    	case Job.STATE_OPEN:
    		result = "Open";
    		break;
    	
    		
    	case Job.STATE_ASSIGNED:
    		result = "Assigned";
    		break;
    		
    	case Job.STATE_IN_PROGRESS:
    		result = "In progress";
    		break;
    		

    	// every state over 50 is closed
    	case Job.STATE_CLOSED:
    		result = "Closed";
    		break;
    		
    	case Job.STATE_FINISHED:
    		result = "Finished";
    		break;
    		
    	case Job.STATE_IGNORED:
    		result = "Ignored";
    		break;
    		
    	case Job.STATE_ALREADY_PROCESSED:
    		result = "Already processed";
    		break;
    		
    	case Job.STATE_ERROR:
    		result = "Error";
    		break;
    	
    	default:
    		result = "Unknown";
		}
    	
		return result;
	}

	/**
     * Notifies all listeners that all cell values in the table's
     * rows may have changed. The number of rows may also have changed
     * and the <code>JTable</code> should redraw the
     * table from scratch. The structure of the table (as in the order of the
     * columns) is assumed to be the same.
     *
     * @see TableModelEvent
     * @see EventListenerList
     * @see javax.swing.JTable#tableChanged(TableModelEvent)
     */
    public void fireTableDataChanged() {
        fireTableChanged(new TableModelEvent(this));
    }

    /**
     * Notifies all listeners that the table's structure has changed.
     * The number of columns in the table, and the names and types of
     * the new columns may be different from the previous state.
     * If the <code>JTable</code> receives this event and its
     * <code>autoCreateColumnsFromModel</code>
     * flag is set it discards any table columns that it had and reallocates
     * default columns in the order they appear in the model. This is the
     * same as calling <code>setModel(TableModel)</code> on the
     * <code>JTable</code>.
     *
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableStructureChanged() {
        fireTableChanged(new TableModelEvent(this, TableModelEvent.HEADER_ROW));
    }

    /**
     * Notifies all listeners that rows in the range
     * <code>[firstRow, lastRow]</code>, inclusive, have been inserted.
     *
     * @param  firstRow  the first row
     * @param  lastRow   the last row
     *
     * @see TableModelEvent
     * @see EventListenerList
     *
     */
    public void fireTableRowsInserted(int firstRow, int lastRow) {
        fireTableChanged(new TableModelEvent(this, firstRow, lastRow,
                             TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
    }

    /**
     * Notifies all listeners that rows in the range
     * <code>[firstRow, lastRow]</code>, inclusive, have been updated.
     *
     * @param firstRow  the first row
     * @param lastRow   the last row
     *
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableRowsUpdated(int firstRow, int lastRow) {
        fireTableChanged(new TableModelEvent(this, firstRow, lastRow,
                             TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE));
    }

    /**
     * Notifies all listeners that rows in the range
     * <code>[firstRow, lastRow]</code>, inclusive, have been deleted.
     *
     * @param firstRow  the first row
     * @param lastRow   the last row
     *
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableRowsDeleted(int firstRow, int lastRow) {
        fireTableChanged(new TableModelEvent(this, firstRow, lastRow,
                             TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
    }
	
    
	private class JobObserver implements Observer {
		
		public void update(Observable pO, Object pArg) {
			
			if((Integer)pArg == Job.NOTIFICATION_AFTER_CHANGE) {
				
				DownloadJob job = (DownloadJob) pO;
				
				int index = mRows.indexOf(job); //TODO this is very expensive, fix it with an cache
				if(index > -1) {
					fireTableRowsUpdated(index, index);
				}
				
			}
			
		}
	}

}
