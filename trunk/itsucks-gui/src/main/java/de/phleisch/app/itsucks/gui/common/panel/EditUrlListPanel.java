/*
 * EditUrlListPanel.java
 *
 * Created on __DATE__, __TIME__
 */

package de.phleisch.app.itsucks.gui.common.panel;

/**
 *
 * @author  __USER__
 */
public class EditUrlListPanel extends javax.swing.JPanel {

	private static final long serialVersionUID = -6093975410692303519L;
	
	/** Creates new form EditUrlListPanel */
	public EditUrlListPanel() {
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
		jLabel1 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();

		setBorder(javax.swing.BorderFactory.createTitledBorder("Edit URL List"));
		jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabel1.setText("<html>Enter a URL per line.</html>");

		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jScrollPane1.setViewportView(jTextArea1);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(
				this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				org.jdesktop.layout.GroupLayout.TRAILING,
				layout.createSequentialGroup().addContainerGap().add(
						layout.createParallelGroup(
								org.jdesktop.layout.GroupLayout.TRAILING).add(
								org.jdesktop.layout.GroupLayout.LEADING,
								jScrollPane1,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								366, Short.MAX_VALUE).add(
								org.jdesktop.layout.GroupLayout.LEADING,
								jLabel1,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								366, Short.MAX_VALUE)).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup().add(jLabel1,
						org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28,
						org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED).add(
								jScrollPane1,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								229, Short.MAX_VALUE).addContainerGap()));
	}// </editor-fold>//GEN-END:initComponents

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JLabel jLabel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextArea jTextArea1;
	// End of variables declaration//GEN-END:variables

}