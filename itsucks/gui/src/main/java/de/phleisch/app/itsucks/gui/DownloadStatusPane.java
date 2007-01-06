/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 * 
 * $Id$
 */

package de.phleisch.app.itsucks.gui;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import de.phleisch.app.itsucks.DispatcherThread;
import de.phleisch.app.itsucks.JobList;
import de.phleisch.app.itsucks.JobList.JobListNotification;
import de.phleisch.app.itsucks.io.DownloadJob;

public class DownloadStatusPane extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPane = null;
	private JTable jDownloadStatusTable = null;
	private DownloadJobTableModel mDownloadStatusTableModel = null;
	
	private JLabel jLabel = null; 
	private DispatcherThread mJobDispatcher;
	
	private JobListObserver mJobListObserver = new JobListObserver();
	
	
	/**
	 * This is the default constructor
	 */
	public DownloadStatusPane() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		mDownloadStatusTableModel = new DownloadJobTableModel(); 
		
		jLabel = new JLabel();
		jLabel.setText("JLabel");
		this.setSize(300, 200);
		this.setLayout(new BorderLayout());
		this.add(getJScrollPane(), BorderLayout.CENTER);
		this.add(jLabel, BorderLayout.NORTH);
	}

	public void setDispatcher(DispatcherThread pDispatcher) {
		mJobDispatcher = pDispatcher;
		
		mJobDispatcher.getJobManager().getJobList().addObserver(mJobListObserver);
	}
	
	public DispatcherThread getDispatcher() {
		return mJobDispatcher;
	}
	
	public void removeDispatcher() {
		mJobDispatcher.getJobManager().getJobList().deleteObserver(mJobListObserver);
		mDownloadStatusTableModel.removeAllDownloadJobs();
		mJobDispatcher = null;
	}
	
	private class JobListObserver implements Observer {
	
		public void update(Observable pO, Object pArg) {
			JobListNotification notification = (JobListNotification) pArg;
			
			if(notification.message == JobList.NOTIFICATION_JOB_ADDED) {
				DownloadJob job = (DownloadJob) notification.affectedJob;
				mDownloadStatusTableModel.addDownloadJob(job);
			} else if(notification.message == JobList.NOTIFICATION_JOB_REMOVED) {
				DownloadJob job = (DownloadJob) notification.affectedJob;
				mDownloadStatusTableModel.removeDownloadJob(job);
			}
		}
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJDownloadStatusTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jDownloadStatusTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJDownloadStatusTable() {
		if (jDownloadStatusTable == null) {
			jDownloadStatusTable = new JTable(mDownloadStatusTableModel);
		}
		
		jDownloadStatusTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumnModel columnModel = jDownloadStatusTable.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(30);
		columnModel.getColumn(4).setPreferredWidth(420);
		
		return jDownloadStatusTable;
	}

}  //  @jve:decl-index=0:visual-constraint="9,66"
