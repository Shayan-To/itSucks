/*
 * QueueDownloadJobOverview.java
 *
 * Created on __DATE__, __TIME__
 */

package de.phleisch.app.itsucks.gui2.panel;

/**
 *
 * @author  __USER__
 */
public class DownloadJobQueueOverviewPanel extends javax.swing.JPanel {

	/** Creates new form QueueDownloadJobOverview */
	public DownloadJobQueueOverviewPanel() {
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc=" Generated Code ">
	private void initComponents() {
		jTabbedPane1 = new javax.swing.JTabbedPane();
		downloadJobStatusTablePanel1 = new de.phleisch.app.itsucks.gui2.panel.DownloadJobStatusTablePanel();
		downloadJobStatusTablePanel2 = new de.phleisch.app.itsucks.gui2.panel.DownloadJobStatusTablePanel();
		downloadJobStatusTablePanel3 = new de.phleisch.app.itsucks.gui2.panel.DownloadJobStatusTablePanel();
		downloadJobStatusTablePanel4 = new de.phleisch.app.itsucks.gui2.panel.DownloadJobStatusTablePanel();
		jPanel1 = new javax.swing.JPanel();

		jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
		jTabbedPane1.setFont(new java.awt.Font("Dialog", 0, 12));
		jTabbedPane1.addTab("All", downloadJobStatusTablePanel1);

		jTabbedPane1.addTab("Running", downloadJobStatusTablePanel2);

		jTabbedPane1.addTab("Open", downloadJobStatusTablePanel3);

		jTabbedPane1.addTab("Finished", downloadJobStatusTablePanel4);

		org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(0, 584,
				Short.MAX_VALUE));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(0, 445,
				Short.MAX_VALUE));
		jTabbedPane1.addTab("Info", jPanel1);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(
				this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(jTabbedPane1,
				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 589,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(jTabbedPane1,
				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 472,
				Short.MAX_VALUE));
	}// </editor-fold>//GEN-END:initComponents

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private de.phleisch.app.itsucks.gui2.panel.DownloadJobStatusTablePanel downloadJobStatusTablePanel1;
	private de.phleisch.app.itsucks.gui2.panel.DownloadJobStatusTablePanel downloadJobStatusTablePanel2;
	private de.phleisch.app.itsucks.gui2.panel.DownloadJobStatusTablePanel downloadJobStatusTablePanel3;
	private de.phleisch.app.itsucks.gui2.panel.DownloadJobStatusTablePanel downloadJobStatusTablePanel4;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JTabbedPane jTabbedPane1;
	// End of variables declaration//GEN-END:variables

}