/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * olli
 * Created on 26.08.2007
 */
package de.phleisch.app.itsucks.gui2.panel;

/**
 *
 * @author  olli
 */
public class DownloadJobBasicPanel extends javax.swing.JPanel {

	private static final long serialVersionUID = 6676129664345121404L;

	/** Creates new form DownloadJobMainPanel */
	public DownloadJobBasicPanel() {
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
		basicParametersPanel = new javax.swing.JPanel();
		nameLabel = new javax.swing.JLabel();
		nameTextField = new javax.swing.JTextField();
		urlLabel = new javax.swing.JLabel();
		urlTextField = new javax.swing.JTextField();
		savePathLabel = new javax.swing.JLabel();
		savePathTextField = new javax.swing.JTextField();
		savePathButton = new javax.swing.JButton();
		connectionSettingsPanel = new javax.swing.JPanel();
		workingThreadsLabel = new javax.swing.JLabel();
		workingThreadsTextField = new javax.swing.JTextField();
		maxConnectionsLabel = new javax.swing.JLabel();
		maxConnectionsTextField = new javax.swing.JTextField();
		proxySettingsPanel = new javax.swing.JPanel();
		enableProxyCheckBox = new javax.swing.JCheckBox();
		proxyServerLabel = new javax.swing.JLabel();
		proxyServerTextField = new javax.swing.JTextField();
		proxyPortLabel = new javax.swing.JLabel();
		proxyPortTextField = new javax.swing.JTextField();
		enableAuthenticationCheckBox = new javax.swing.JCheckBox();
		authenticationUserLabel = new javax.swing.JLabel();
		authenticationUserTextField = new javax.swing.JTextField();
		authenticationPasswordLabel = new javax.swing.JLabel();
		authenticationPasswordTextField = new javax.swing.JTextField();

		basicParametersPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Basic Parameters"));
		nameLabel.setFont(new java.awt.Font("Dialog", 0, 12));
		nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		nameLabel.setText("Name:");

		urlLabel.setFont(new java.awt.Font("Dialog", 0, 12));
		urlLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		urlLabel.setText("Start URL:");

		savePathLabel.setFont(new java.awt.Font("Dialog", 0, 12));
		savePathLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		savePathLabel.setText("Save path:");

		savePathButton.setText("Browse");

		org.jdesktop.layout.GroupLayout basicParametersPanelLayout = new org.jdesktop.layout.GroupLayout(
				basicParametersPanel);
		basicParametersPanel.setLayout(basicParametersPanelLayout);
		basicParametersPanelLayout
				.setHorizontalGroup(basicParametersPanelLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								basicParametersPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												basicParametersPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																org.jdesktop.layout.GroupLayout.TRAILING,
																savePathLabel)
														.add(
																org.jdesktop.layout.GroupLayout.TRAILING,
																urlLabel)
														.add(
																org.jdesktop.layout.GroupLayout.TRAILING,
																nameLabel))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												basicParametersPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																urlTextField,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																355,
																Short.MAX_VALUE)
														.add(
																basicParametersPanelLayout
																		.createSequentialGroup()
																		.add(
																				savePathTextField,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				269,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				savePathButton))
														.add(
																basicParametersPanelLayout
																		.createSequentialGroup()
																		.add(
																				nameTextField,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				151,
																				Short.MAX_VALUE)
																		.add(
																				204,
																				204,
																				204)))
										.addContainerGap()));

		basicParametersPanelLayout.linkSize(new java.awt.Component[] {
				nameLabel, savePathLabel, urlLabel },
				org.jdesktop.layout.GroupLayout.HORIZONTAL);

		basicParametersPanelLayout
				.setVerticalGroup(basicParametersPanelLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								basicParametersPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												basicParametersPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(nameLabel)
														.add(
																nameTextField,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												basicParametersPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(urlLabel)
														.add(
																urlTextField,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												basicParametersPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(savePathLabel)
														.add(
																savePathTextField,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(savePathButton))
										.addContainerGap(
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		connectionSettingsPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Connection Settings"));
		workingThreadsLabel.setFont(new java.awt.Font("Dialog", 0, 12));
		workingThreadsLabel
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		workingThreadsLabel.setText("Working Threads:");

		workingThreadsTextField
				.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

		maxConnectionsLabel.setFont(new java.awt.Font("Dialog", 0, 12));
		maxConnectionsLabel
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		maxConnectionsLabel.setText("Max. connections per server:");

		maxConnectionsTextField
				.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

		org.jdesktop.layout.GroupLayout connectionSettingsPanelLayout = new org.jdesktop.layout.GroupLayout(
				connectionSettingsPanel);
		connectionSettingsPanel.setLayout(connectionSettingsPanelLayout);
		connectionSettingsPanelLayout
				.setHorizontalGroup(connectionSettingsPanelLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								connectionSettingsPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												connectionSettingsPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																maxConnectionsLabel)
														.add(
																workingThreadsLabel))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												connectionSettingsPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING,
																false)
														.add(
																maxConnectionsTextField)
														.add(
																workingThreadsTextField,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																46,
																Short.MAX_VALUE))
										.addContainerGap(214, Short.MAX_VALUE)));

		connectionSettingsPanelLayout.linkSize(new java.awt.Component[] {
				maxConnectionsLabel, workingThreadsLabel },
				org.jdesktop.layout.GroupLayout.HORIZONTAL);

		connectionSettingsPanelLayout
				.setVerticalGroup(connectionSettingsPanelLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								connectionSettingsPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												connectionSettingsPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(
																workingThreadsLabel)
														.add(
																workingThreadsTextField,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												connectionSettingsPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(
																maxConnectionsLabel)
														.add(
																maxConnectionsTextField,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(16, Short.MAX_VALUE)));

		proxySettingsPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Proxy Settings"));
		proxySettingsPanel.setEnabled(false);
		enableProxyCheckBox.setFont(new java.awt.Font("Dialog", 0, 12));
		enableProxyCheckBox.setText("Enable Proxy");
		enableProxyCheckBox.setBorder(javax.swing.BorderFactory
				.createEmptyBorder(0, 0, 0, 0));
		enableProxyCheckBox.setEnabled(false);
		enableProxyCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

		proxyServerLabel.setFont(new java.awt.Font("Dialog", 0, 12));
		proxyServerLabel
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		proxyServerLabel.setText("Server:");
		proxyServerLabel.setEnabled(false);

		proxyServerTextField.setEnabled(false);

		proxyPortLabel.setFont(new java.awt.Font("Dialog", 0, 12));
		proxyPortLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		proxyPortLabel.setText("Port:");
		proxyPortLabel.setEnabled(false);

		proxyPortTextField.setEnabled(false);

		enableAuthenticationCheckBox
				.setFont(new java.awt.Font("Dialog", 0, 12));
		enableAuthenticationCheckBox.setText("Enable authentication");
		enableAuthenticationCheckBox.setBorder(javax.swing.BorderFactory
				.createEmptyBorder(0, 0, 0, 0));
		enableAuthenticationCheckBox.setEnabled(false);
		enableAuthenticationCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

		authenticationUserLabel.setFont(new java.awt.Font("Dialog", 0, 12));
		authenticationUserLabel
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		authenticationUserLabel.setText("User:");
		authenticationUserLabel.setEnabled(false);

		authenticationUserTextField.setEnabled(false);

		authenticationPasswordLabel.setFont(new java.awt.Font("Dialog", 0, 12));
		authenticationPasswordLabel
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		authenticationPasswordLabel.setText("Password:");
		authenticationPasswordLabel.setEnabled(false);

		authenticationPasswordTextField.setEnabled(false);

		org.jdesktop.layout.GroupLayout proxySettingsPanelLayout = new org.jdesktop.layout.GroupLayout(
				proxySettingsPanel);
		proxySettingsPanel.setLayout(proxySettingsPanelLayout);
		proxySettingsPanelLayout
				.setHorizontalGroup(proxySettingsPanelLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								proxySettingsPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												proxySettingsPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																proxySettingsPanelLayout
																		.createSequentialGroup()
																		.add(
																				proxyServerLabel)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				proxyServerTextField,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																				130,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				proxyPortLabel)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				proxyPortTextField,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																				122,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
														.add(
																proxySettingsPanelLayout
																		.createParallelGroup(
																				org.jdesktop.layout.GroupLayout.LEADING)
																		.add(
																				proxySettingsPanelLayout
																						.createSequentialGroup()
																						.add(
																								authenticationUserLabel)
																						.addPreferredGap(
																								org.jdesktop.layout.LayoutStyle.RELATED)
																						.add(
																								authenticationUserTextField,
																								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																								130,
																								Short.MAX_VALUE)
																						.addPreferredGap(
																								org.jdesktop.layout.LayoutStyle.RELATED)
																						.add(
																								authenticationPasswordLabel)
																						.addPreferredGap(
																								org.jdesktop.layout.LayoutStyle.RELATED)
																						.add(
																								authenticationPasswordTextField,
																								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																								117,
																								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
																		.add(
																				proxySettingsPanelLayout
																						.createSequentialGroup()
																						.add(
																								enableAuthenticationCheckBox)
																						.addPreferredGap(
																								org.jdesktop.layout.LayoutStyle.RELATED,
																								255,
																								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
														.add(
																proxySettingsPanelLayout
																		.createSequentialGroup()
																		.add(
																				enableProxyCheckBox,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																				107,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED,
																				288,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
										.add(44, 44, 44)));

		proxySettingsPanelLayout.linkSize(new java.awt.Component[] {
				authenticationPasswordTextField, authenticationUserTextField,
				proxyPortTextField, proxyServerTextField },
				org.jdesktop.layout.GroupLayout.HORIZONTAL);

		proxySettingsPanelLayout.linkSize(new java.awt.Component[] {
				authenticationPasswordLabel, proxyPortLabel },
				org.jdesktop.layout.GroupLayout.HORIZONTAL);

		proxySettingsPanelLayout.linkSize(new java.awt.Component[] {
				authenticationUserLabel, proxyServerLabel },
				org.jdesktop.layout.GroupLayout.HORIZONTAL);

		proxySettingsPanelLayout
				.setVerticalGroup(proxySettingsPanelLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								org.jdesktop.layout.GroupLayout.TRAILING,
								proxySettingsPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.add(enableProxyCheckBox)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												proxySettingsPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(proxyServerLabel)
														.add(
																proxyServerTextField,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(proxyPortLabel)
														.add(
																proxyPortTextField,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(enableAuthenticationCheckBox)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												proxySettingsPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(
																authenticationUserLabel)
														.add(
																authenticationPasswordLabel)
														.add(
																authenticationPasswordTextField,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(
																authenticationUserTextField,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

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
								proxySettingsPanel,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE).add(
								org.jdesktop.layout.GroupLayout.LEADING,
								connectionSettingsPanel,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE).add(
								org.jdesktop.layout.GroupLayout.LEADING,
								basicParametersPanel,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup().addContainerGap().add(
						basicParametersPanel,
						org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
						org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
						org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED).add(
								connectionSettingsPanel,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED).add(
								proxySettingsPanel,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
	}// </editor-fold>//GEN-END:initComponents

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JLabel authenticationPasswordLabel;
	private javax.swing.JTextField authenticationPasswordTextField;
	private javax.swing.JLabel authenticationUserLabel;
	private javax.swing.JTextField authenticationUserTextField;
	private javax.swing.JPanel basicParametersPanel;
	private javax.swing.JPanel connectionSettingsPanel;
	private javax.swing.JCheckBox enableAuthenticationCheckBox;
	private javax.swing.JCheckBox enableProxyCheckBox;
	private javax.swing.JLabel maxConnectionsLabel;
	private javax.swing.JTextField maxConnectionsTextField;
	private javax.swing.JLabel nameLabel;
	private javax.swing.JTextField nameTextField;
	private javax.swing.JLabel proxyPortLabel;
	private javax.swing.JTextField proxyPortTextField;
	private javax.swing.JLabel proxyServerLabel;
	private javax.swing.JTextField proxyServerTextField;
	private javax.swing.JPanel proxySettingsPanel;
	private javax.swing.JButton savePathButton;
	private javax.swing.JLabel savePathLabel;
	private javax.swing.JTextField savePathTextField;
	private javax.swing.JLabel urlLabel;
	private javax.swing.JTextField urlTextField;
	private javax.swing.JLabel workingThreadsLabel;
	private javax.swing.JTextField workingThreadsTextField;
	// End of variables declaration//GEN-END:variables

}