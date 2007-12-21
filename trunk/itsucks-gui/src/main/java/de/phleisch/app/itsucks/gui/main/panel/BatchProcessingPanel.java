/*
 * BatchProcessingPanel.java
 *
 * Created on __DATE__, __TIME__
 */

package de.phleisch.app.itsucks.gui.main.panel;

import java.awt.Dialog;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.phleisch.app.itsucks.core.impl.DispatcherList;
import de.phleisch.app.itsucks.core.impl.DispatcherThread;
import de.phleisch.app.itsucks.event.Event;
import de.phleisch.app.itsucks.event.EventObserver;
import de.phleisch.app.itsucks.event.impl.CoreEvents;
import de.phleisch.app.itsucks.gui.job.EditDownloadJobHelper;
import de.phleisch.app.itsucks.gui.job.ifc.AddDownloadJobCapable;
import de.phleisch.app.itsucks.gui.main.helper.DispatcherHelper;
import de.phleisch.app.itsucks.gui.util.ExtendedListModel;
import de.phleisch.app.itsucks.persistence.SerializableJobPackage;

/**
 *
 * @author  __USER__
 */
public class BatchProcessingPanel extends javax.swing.JPanel implements
		AddDownloadJobCapable {

	private static final long serialVersionUID = 202226684812236519L;
	@SuppressWarnings("unused")
	private static Log mLog = LogFactory.getLog(BatchProcessingPanel.class);

	private DispatcherList mDispatcherList;
	protected ExtendedListModel jobListModel;

	/** Creates new form BatchProcessingPanel */
	public BatchProcessingPanel() {
		jobListModel = new ExtendedListModel();

		initComponents();
	}

	public void addDownload(SerializableJobPackage pJob) {

		if(pJob != null) {
			jobListModel.add(jobListModel.getSize(), new JobListElement(pJob));
		}

	}

	public void setDispatcherList(DispatcherList pDispatcherList) {
		mDispatcherList = pDispatcherList;
	}

	private void startNextJob() {

		JobListElement[] list = new JobListElement[jobListModel.size()];
		jobListModel.copyInto(list);

		for (int i = 0; i < list.length; i++) {
			if (JobListElement.State.OPEN.equals(list[i].mState)) {

				DispatcherHelper helper = new DispatcherHelper();
				DispatcherThread dispatcher = helper
						.createDispatcher(list[i].mJobList);

				//add the dispatcher to the list, the panel will be added by the event
				int id = mDispatcherList.addDispatcher(dispatcher);
				list[i].mDispatcherListId = id;

				mLog.debug("New dispatcher added with id: " + id + ".");

				dispatcher.getEventManager().registerObserver(
						new DispatcherListener(id));

				helper.startDispatcher(dispatcher);

				//update state and inform the model of the change
				list[i].mState = JobListElement.State.RUNNING;
				jobListModel.fireContentsChanged(i, i);

				//only one item per call
				break;
			}
		}

	}

	private void finishJob(int pId) {
		mLog.debug("Finish dispatcher with id: " + pId + ".");

		setJobListElementState(pId, JobListElement.State.FINISHED);

		//remove the dispatcher from the list
		mDispatcherList.removeDispatcherById(pId);

		startNextJob();
	}

	private void setJobListElementState(int pId, JobListElement.State pState) {

		JobListElement[] list = new JobListElement[jobListModel.size()];
		jobListModel.copyInto(list);

		for (int i = 0; i < list.length; i++) {
			if (list[i].mDispatcherListId == pId) {

				mLog.debug("Set dispatcher with id: " + pId + " to State: "
						+ pState);

				list[i].mState = pState;
				jobListModel.fireContentsChanged(i, i);
			}
		}

	}

	protected class DispatcherListener implements EventObserver {

		private int mId;

		public DispatcherListener(int pId) {
			mId = pId;
		}

		public void processEvent(Event pEvent) {
			if (pEvent.equals(CoreEvents.EVENT_DISPATCHER_FINISH)) {

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						finishJob(mId);
					}

				});
			}
		}

	}

	//	protected class DispatcherListListener implements EventObserver {
	//
	//		public void processEvent(final Event pEvent) {
	//			
	//			switch(pEvent.getType()) {
	//			
	//				case DispatcherList.EVENT_DISPATCHER_ADDED:
	//					
	//					SwingUtilities.invokeLater(new Runnable() {
	//						public void run() {
	//							processDispatcherAdded((DispatcherListEvent)pEvent);
	//						}
	//					});
	//					break;
	//					
	//				case DispatcherList.EVENT_DISPATCHER_REMOVED:
	//					SwingUtilities.invokeLater(new Runnable() {
	//						public void run() {
	//							processDispatcherRemoved((DispatcherListEvent)pEvent);
	//						}
	//					});							
	//					break;
	//					
	//				default: 
	//					throw new IllegalStateException("Unknown Event: " + pEvent);			
	//			}
	//		}
	//
	//		private void processDispatcherAdded(DispatcherListEvent pEvent) {
	//			
	//		}
	//		
	//		private void processDispatcherRemoved(DispatcherListEvent pEvent) {
	//		}
	//	}

	protected static class JobListElement {

		enum State {
			OPEN, RUNNING, FINISHED
		}

		private SerializableJobPackage mJobList;
		private State mState = State.OPEN;
		private int mDispatcherListId = -1;

		public JobListElement(SerializableJobPackage pJobList) {
			mJobList = pJobList;
		}

		@Override
		public String toString() {
			return toHtmlString();
		}

		/**
		 * Returns a string containing all information about the filter.
		 * HTML format.
		 * 
		 * @return
		 */
		public String toHtmlString() {
			return "<html>" + mJobList.getJobs().get(0).getName() + "<br>"
					+ "State: " + mState + "</html>";
		}

		public boolean isFinished() {
			return mState.equals(State.FINISHED);
		}
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc=" Generated Code ">
	private void initComponents() {
		jobListScrollPane = new javax.swing.JScrollPane();
		jobList = new javax.swing.JList();
		addJobButton = new javax.swing.JButton();
		editJobButton = new javax.swing.JButton();
		loadJobButton = new javax.swing.JButton();
		removeJobButton = new javax.swing.JButton();
		moveUpButton = new javax.swing.JButton();
		moveDownButton = new javax.swing.JButton();
		startButton = new javax.swing.JButton();

		jobList.setFont(new java.awt.Font("Dialog", 0, 12));
		jobList.setModel(jobListModel);
		jobListScrollPane.setViewportView(jobList);

		addJobButton.setFont(new java.awt.Font("Dialog", 0, 12));
		addJobButton.setText("Add Job");
		addJobButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addJobButtonActionPerformed(evt);
			}
		});

		editJobButton.setFont(new java.awt.Font("Dialog", 0, 12));
		editJobButton.setText("Edit Job");
		editJobButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editJobButtonActionPerformed(evt);
			}
		});

		loadJobButton.setFont(new java.awt.Font("Dialog", 0, 12));
		loadJobButton.setText("Load Job");
		loadJobButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadJobButtonActionPerformed(evt);
			}
		});

		removeJobButton.setFont(new java.awt.Font("Dialog", 0, 12));
		removeJobButton.setText("Remove Job");
		removeJobButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeJobButtonActionPerformed(evt);
			}
		});

		moveUpButton.setFont(new java.awt.Font("Dialog", 0, 12));
		moveUpButton.setText("Move up");
		moveUpButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				moveUpButtonActionPerformed(evt);
			}
		});

		moveDownButton.setFont(new java.awt.Font("Dialog", 0, 12));
		moveDownButton.setText("Move down");
		moveDownButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				moveDownButtonActionPerformed(evt);
			}
		});

		startButton.setText("Start batch");
		startButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startButtonActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(
				this);
		this.setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								org.jdesktop.layout.GroupLayout.TRAILING,
								layout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												jobListScrollPane,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												315, Short.MAX_VALUE)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING,
																false)
														.add(
																addJobButton,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.add(
																loadJobButton,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																94,
																Short.MAX_VALUE)
														.add(startButton)
														.add(
																moveUpButton,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.add(
																moveDownButton,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.add(
																editJobButton,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.add(
																removeJobButton,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap()));

		layout.linkSize(new java.awt.Component[] { addJobButton, editJobButton,
				loadJobButton, moveDownButton, moveUpButton, removeJobButton,
				startButton }, org.jdesktop.layout.GroupLayout.HORIZONTAL);

		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																jobListScrollPane,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																276,
																Short.MAX_VALUE)
														.add(
																layout
																		.createSequentialGroup()
																		.add(
																				addJobButton)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				loadJobButton)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				editJobButton)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				removeJobButton)
																		.add(
																				26,
																				26,
																				26)
																		.add(
																				moveUpButton)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				moveDownButton)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED,
																				51,
																				Short.MAX_VALUE)
																		.add(
																				startButton)))
										.addContainerGap()));

		layout.linkSize(new java.awt.Component[] { addJobButton, editJobButton,
				loadJobButton, moveDownButton, moveUpButton, removeJobButton,
				startButton }, org.jdesktop.layout.GroupLayout.VERTICAL);

	}// </editor-fold>//GEN-END:initComponents

	//GEN-FIRST:event_startButtonActionPerformed
	private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {

		addJobButton.setEnabled(false);
		loadJobButton.setEnabled(false);
		editJobButton.setEnabled(false);
		removeJobButton.setEnabled(false);
		moveUpButton.setEnabled(false);
		moveDownButton.setEnabled(false);
		jobList.clearSelection();
		startButton.setEnabled(false);
		startButton.setText("Batch running...");

		startNextJob();

	}//GEN-LAST:event_startButtonActionPerformed

	//GEN-FIRST:event_moveDownButtonActionPerformed
	private void moveDownButtonActionPerformed(java.awt.event.ActionEvent evt) {

		int[] selectedIndices = jobList.getSelectedIndices();

		//check if move is possible
		for (int i = 0; i < selectedIndices.length; i++) {
			if (selectedIndices[i] == (jobListModel.getSize() - 1))
				return;
		}

		//move the entries
		for (int i = (selectedIndices.length - 1); i > -1; i--) {
			selectedIndices[i] = jobListModel.moveEntry(selectedIndices[i], 1);
		}
		jobList.setSelectedIndices(selectedIndices);

	}//GEN-LAST:event_moveDownButtonActionPerformed

	//GEN-FIRST:event_moveUpButtonActionPerformed
	private void moveUpButtonActionPerformed(java.awt.event.ActionEvent evt) {

		int[] selectedIndices = jobList.getSelectedIndices();

		//check if move is possible
		for (int i = 0; i < selectedIndices.length; i++) {
			if (selectedIndices[i] == 0)
				return;
		}

		//move the entries
		for (int i = 0; i < selectedIndices.length; i++) {
			selectedIndices[i] = jobListModel.moveEntry(selectedIndices[i], -1);
		}
		jobList.setSelectedIndices(selectedIndices);

	}//GEN-LAST:event_moveUpButtonActionPerformed

	//GEN-FIRST:event_removeJobButtonActionPerformed
	private void removeJobButtonActionPerformed(java.awt.event.ActionEvent evt) {

		int[] selectedIndices = jobList.getSelectedIndices();
		for (int i = (selectedIndices.length - 1); i > -1; i--) {
			jobListModel.remove(selectedIndices[i]);
		}

	}//GEN-LAST:event_removeJobButtonActionPerformed

	//GEN-FIRST:event_editJobButtonActionPerformed
	private void editJobButtonActionPerformed(java.awt.event.ActionEvent evt) {
		
		EditDownloadJobHelper helper = new EditDownloadJobHelper(
				(Dialog) getRootPane().getParent());
		
		int[] selectedIndices = jobList.getSelectedIndices();
		for (int i = (selectedIndices.length - 1); i > -1; i--) {
			JobListElement element = (JobListElement) jobListModel.get(selectedIndices[i]);
			helper.editDownload(this, element.mJobList);
		}
		
	}//GEN-LAST:event_editJobButtonActionPerformed
	
	//GEN-FIRST:event_loadJobButtonActionPerformed
	private void loadJobButtonActionPerformed(java.awt.event.ActionEvent evt) {

		EditDownloadJobHelper helper = new EditDownloadJobHelper(
				(Dialog) getRootPane().getParent());
		
		List<SerializableJobPackage> loadDownload = helper.loadDownload();
		for (SerializableJobPackage serializableJobPackage : loadDownload) {
			this.addDownload(serializableJobPackage);
		}

	}//GEN-LAST:event_loadJobButtonActionPerformed

	//GEN-FIRST:event_addJobButtonActionPerformed
	private void addJobButtonActionPerformed(java.awt.event.ActionEvent evt) {

		EditDownloadJobHelper helper = new EditDownloadJobHelper(
				(Dialog) getRootPane().getParent());
		helper.openAddDownloadDialog(this);

	}//GEN-LAST:event_addJobButtonActionPerformed

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton addJobButton;
	private javax.swing.JButton editJobButton;
	private javax.swing.JList jobList;
	private javax.swing.JScrollPane jobListScrollPane;
	private javax.swing.JButton loadJobButton;
	private javax.swing.JButton moveDownButton;
	private javax.swing.JButton moveUpButton;
	private javax.swing.JButton removeJobButton;
	private javax.swing.JButton startButton;
	// End of variables declaration//GEN-END:variables

}