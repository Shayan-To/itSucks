/*
 * EditDownloadJobFrame.java
 *
 * Created on __DATE__, __TIME__
 */

package de.phleisch.app.itsucks.gui;

import java.awt.Frame;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.phleisch.app.itsucks.SpringContextSingelton;
import de.phleisch.app.itsucks.gui.ifc.AddDownloadJobCapable;
import de.phleisch.app.itsucks.io.DownloadJob;
import de.phleisch.app.itsucks.persistence.JobSerialization;
import de.phleisch.app.itsucks.persistence.SerializableJobList;

/**
 *
 * @author  __USER__
 */
public class EditDownloadJobDialog extends javax.swing.JDialog {

	private static final long serialVersionUID = 3587076557680397119L;

	private static Log mLog = LogFactory.getLog(EditDownloadJobDialog.class);

	private AddDownloadJobCapable mDownloadJobManager = null;

	/** Creates new form EditDownloadJobFrame */
	public EditDownloadJobDialog(Frame pOwner,
			AddDownloadJobCapable pDownloadJobManager) {
		super(pOwner);
		mDownloadJobManager = pDownloadJobManager;

		initComponents();
	}

	public void loadJob(SerializableJobList pJobList) {
		this.editDownloadJobGroupPanel.loadJob(pJobList);
	}

	private void saveDownloadTemplate() {
		SerializableJobList downloadJobList = this.editDownloadJobGroupPanel
				.buildJob();
		if (downloadJobList == null)
			return;

		//open dialog
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter(
				"ItSucks Download Templates (*.suck)", new String[] { "suck" }));

		fc.setSelectedFile(new File("ItSucks_"
				+ downloadJobList.getJobs().get(0).getName().replace(' ', '_')
				+ "_Template.suck"));

		// Show save dialog; this method does not return until the dialog is closed
		int result = fc.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {

			JobSerialization serializationManager = (JobSerialization) SpringContextSingelton
					.getApplicationContext().getBean("JobSerialization");

			try {
				serializationManager.serialize(downloadJobList, fc.getSelectedFile());
			} catch (Exception e1) {

				mLog.error("Error occured while saving download template", e1);

				String message = e1.getMessage();
				if(message == null) {
					message = e1.toString();
				}
				
				JOptionPane.showMessageDialog(this,
						"Error occured while saving download template.\n"
								+ message, "Error occured",
						JOptionPane.ERROR_MESSAGE);
			}

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
		editDownloadJobGroupPanel = new de.phleisch.app.itsucks.gui.panel.EditDownloadJobGroupPanel();
		buttonPanel = new javax.swing.JPanel();
		startButton = new javax.swing.JButton();
		saveButton = new javax.swing.JButton();
		cancelButton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setLocationByPlatform(true);

		startButton.setText("Start download");
		startButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startButtonActionPerformed(evt);
			}
		});

		buttonPanel.add(startButton);

		saveButton.setText("Save as template");
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveButtonActionPerformed(evt);
			}
		});

		buttonPanel.add(saveButton);

		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});

		buttonPanel.add(cancelButton);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				editDownloadJobGroupPanel,
				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 620,
				Short.MAX_VALUE).add(buttonPanel,
				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 620,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup().add(editDownloadJobGroupPanel,
						org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 594,
						Short.MAX_VALUE).addPreferredGap(
						org.jdesktop.layout.LayoutStyle.RELATED).add(
						buttonPanel,
						org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
						org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
						org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)));
		pack();
	}// </editor-fold>//GEN-END:initComponents

	//GEN-FIRST:event_saveButtonActionPerformed
	private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
		saveDownloadTemplate();
	}//GEN-LAST:event_saveButtonActionPerformed

	//GEN-FIRST:event_cancelButtonActionPerformed
	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
		dispose();
	}//GEN-LAST:event_cancelButtonActionPerformed

	//GEN-FIRST:event_startButtonActionPerformed
	private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {

		SerializableJobList job = this.editDownloadJobGroupPanel.buildJob();
		if (job == null)
			return;

		mDownloadJobManager.addDownload((DownloadJob)job.getJobs().get(0), job.getFilters());

		this.dispose();

	}//GEN-LAST:event_startButtonActionPerformed

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JPanel buttonPanel;
	private javax.swing.JButton cancelButton;
	private de.phleisch.app.itsucks.gui.panel.EditDownloadJobGroupPanel editDownloadJobGroupPanel;
	private javax.swing.JButton saveButton;
	private javax.swing.JButton startButton;
	// End of variables declaration//GEN-END:variables

}