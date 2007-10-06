/*
 * DownloadJobAdvancedRulesPanel.java
 *
 * Created on __DATE__, __TIME__
 */

package de.phleisch.app.itsucks.gui.panel;

import java.awt.Dialog;

import de.phleisch.app.itsucks.JobParameter;
import de.phleisch.app.itsucks.filter.RegExpJobFilter.RegExpFilterAction;
import de.phleisch.app.itsucks.filter.RegExpJobFilter.RegExpFilterRule;
import de.phleisch.app.itsucks.gui.EditRegularExpressionDialog;
import de.phleisch.app.itsucks.gui.util.ExtendedListModel;
import de.phleisch.app.itsucks.gui.util.SwingUtils;
import de.phleisch.app.itsucks.io.DownloadJob;

/**
 *
 * @author  __USER__
 */
public class DownloadJobAdvancedRulesPanel extends javax.swing.JPanel {

	private static final long serialVersionUID = 9062521650244140654L;

	protected ExtendedListModel advancedFilterFilterListModel;
	protected RegExpFilterRule mRuleInEditMode;

	/** Creates new form DownloadJobAdvancedRulesPanel */
	public DownloadJobAdvancedRulesPanel() {
		advancedFilterFilterListModel = new ExtendedListModel();
		mRuleInEditMode = null;

		initComponents();

		//add elements to combo boxes
		for (ComboBoxEntry entry : mStatusChangeList) {
			editAdvancedFilterMatchStatusChangeComboBox.addItem(entry);
			editAdvancedFilterNoMatchStatusChangeComboBox.addItem(entry);
		}

		//disable advanced edit filter panel
		SwingUtils.setContainerAndChildrenEnabled(editAdvancedFilterPanel,
				false);
	}

	protected class RegExpFilterRuleListElement {

		private RegExpFilterRule mRule;

		public RegExpFilterRuleListElement(RegExpFilterRule pRule) {
			mRule = pRule;
		}

		public RegExpFilterRule getRule() {
			return mRule;
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
			return "<html>"
					+ (mRule.getName() != null ? "Name: '" + mRule.getName()
							+ "'<br>\n" : "") + "Pattern: '"
					+ mRule.getPattern() + "' <br>\n" + "Match: "
					+ mRule.getMatchAction() + "<br>\nNo Match: "
					+ mRule.getNoMatchAction() + "</html>";
		}
	}

	protected class ComboBoxEntry {

		private String mName;
		private Object mValue;

		public ComboBoxEntry(String pName, Object pValue) {
			mName = pName;
			mValue = pValue;
		}

		public String getName() {
			return mName;
		}

		public Object getValue() {
			return mValue;
		}

		@Override
		public String toString() {
			return mName.toString();
		}
	}

	protected ComboBoxEntry mStatusChangeList[] = new ComboBoxEntry[] {
			new ComboBoxEntry("No change", null),
			new ComboBoxEntry("Accept", Boolean.TRUE),
			new ComboBoxEntry("Reject", Boolean.FALSE), };

	private void updateAdvancedFilter() {

		Object[] selectedValues = advancedFilterList.getSelectedValues();
		if (mRuleInEditMode == null || selectedValues == null
				|| selectedValues.length != 1) {
			return;
		}

		RegExpFilterRule rule = mRuleInEditMode;

		rule.setName(editAdvancedFilterNameField.getText());
		rule.setDescription(editAdvancedFilterDescriptionTextArea.getText());
		rule.setPattern(editAdvancedFilterRegExpTextArea.getText());

		//match Action
		{
			RegExpFilterAction matchAction = rule.getMatchAction();

			ComboBoxEntry selectedItem = (ComboBoxEntry) editAdvancedFilterMatchStatusChangeComboBox
					.getSelectedItem();
			matchAction.setAccept((Boolean) selectedItem.getValue());

			try {
				matchAction.setPriorityChange(Integer
						.parseInt(editAdvancedFilterMatchPrioChangeTextField
								.getText()));
			} catch (NumberFormatException ex) {
				editAdvancedFilterMatchPrioChangeTextField.setText(String
						.valueOf(matchAction.getPriorityChange()));
			}

			matchAction.addJobParameter(new JobParameter(
					DownloadJob.JOB_PARAMETER_SKIP_DOWNLOADED_FILE,
					new Boolean(
							editAdvancedFilterMatchAssumeFinishedFileCheckBox
									.isSelected())));
		}

		//no match Action
		{
			RegExpFilterAction noMatchAction = rule.getNoMatchAction();

			ComboBoxEntry selectedItem = (ComboBoxEntry) editAdvancedFilterNoMatchStatusChangeComboBox
					.getSelectedItem();
			noMatchAction.setAccept((Boolean) selectedItem.getValue());

			try {
				noMatchAction.setPriorityChange(Integer
						.parseInt(editAdvancedFilterNoMatchPrioChangeTextField
								.getText()));
			} catch (NumberFormatException ex) {
				editAdvancedFilterNoMatchPrioChangeTextField.setText(String
						.valueOf(noMatchAction.getPriorityChange()));
			}

			noMatchAction.addJobParameter(new JobParameter(
					DownloadJob.JOB_PARAMETER_SKIP_DOWNLOADED_FILE,
					new Boolean(
							editAdvancedFilterNoMatchAssumeFinishedFileCheckBox
									.isSelected())));
		}

		//notify list
		int selectionIndex = advancedFilterList.getSelectedIndex();
		advancedFilterFilterListModel.fireContentsChanged(selectionIndex,
				selectionIndex);
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc=" Generated Code ">
	private void initComponents() {
		advancedFilterChainLabel = new javax.swing.JLabel();
		advancedFilterChainExplanationLabel = new javax.swing.JLabel();
		advancedFilterPane = new javax.swing.JScrollPane();
		advancedFilterList = new javax.swing.JList();
		advancedFilterAddButton = new javax.swing.JButton();
		advancedFilterRemoveButton = new javax.swing.JButton();
		advancedFilterMoveUpButton = new javax.swing.JButton();
		advancedFilterMoveDownButton = new javax.swing.JButton();
		editAdvancedFilterPanel = new javax.swing.JPanel();
		editAdvancedFilterNameLabel = new javax.swing.JLabel();
		editAdvancedFilterNameField = new javax.swing.JTextField();
		editAdvancedFilterDescriptionLabel = new javax.swing.JLabel();
		editAdvancedFilterDescriptionPane = new javax.swing.JScrollPane();
		editAdvancedFilterDescriptionTextArea = new javax.swing.JTextArea();
		editAdvancedFilterRegExpLabel = new javax.swing.JLabel();
		editAdvancedFilterRegExpPane = new javax.swing.JScrollPane();
		editAdvancedFilterRegExpTextArea = new javax.swing.JTextArea();
		editAdvancedFilterOpenRegExpEditorButton = new javax.swing.JButton();
		editAdvancedFilterMatchPanel = new javax.swing.JPanel();
		editAdvancedFilterMatchStatusChangeLabel = new javax.swing.JLabel();
		editAdvancedFilterMatchStatusChangeComboBox = new javax.swing.JComboBox();
		editAdvancedFilterMatchPrioChangeLabel = new javax.swing.JLabel();
		editAdvancedFilterMatchPrioChangeTextField = new javax.swing.JTextField();
		editAdvancedFilterMatchAssumeFinishedFileCheckBox = new javax.swing.JCheckBox();
		editAdvancedFilterNoMatchPanel = new javax.swing.JPanel();
		editAdvancedFilterNoMatchStatusChangeLabel = new javax.swing.JLabel();
		editAdvancedFilterNoMatchStatusChangeComboBox = new javax.swing.JComboBox();
		editAdvancedFilterNoMatchPrioChangeLabel = new javax.swing.JLabel();
		editAdvancedFilterNoMatchPrioChangeTextField = new javax.swing.JTextField();
		editAdvancedFilterNoMatchAssumeFinishedFileCheckBox = new javax.swing.JCheckBox();

		advancedFilterChainLabel.setText("Advanced Filter Chain");

		advancedFilterChainExplanationLabel.setFont(new java.awt.Font("Dialog",
				0, 12));
		advancedFilterChainExplanationLabel
				.setText("<html>Every found link will be filtered through the chain. A link must have the state 'accepted' after running through the chain, otherwise it will be rejected</html>");

		advancedFilterList.setFont(new java.awt.Font("Dialog", 0, 12));
		advancedFilterList.setModel(advancedFilterFilterListModel);
		advancedFilterList
				.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
					public void valueChanged(
							javax.swing.event.ListSelectionEvent evt) {
						advancedFilterListValueChanged(evt);
					}
				});

		advancedFilterPane.setViewportView(advancedFilterList);

		advancedFilterAddButton.setFont(new java.awt.Font("Dialog", 0, 12));
		advancedFilterAddButton.setText("+");
		advancedFilterAddButton.setMargin(new java.awt.Insets(2, 4, 2, 4));
		advancedFilterAddButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						advancedFilterAddButtonActionPerformed(evt);
					}
				});

		advancedFilterRemoveButton.setFont(new java.awt.Font("Dialog", 0, 12));
		advancedFilterRemoveButton.setText("-");
		advancedFilterRemoveButton.setMargin(new java.awt.Insets(2, 4, 2, 4));
		advancedFilterRemoveButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						advancedFilterRemoveButtonActionPerformed(evt);
					}
				});

		advancedFilterMoveUpButton.setFont(new java.awt.Font("Dialog", 0, 12));
		advancedFilterMoveUpButton.setText("up");
		advancedFilterMoveUpButton.setMargin(new java.awt.Insets(2, 4, 2, 4));
		advancedFilterMoveUpButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						advancedFilterMoveUpButtonActionPerformed(evt);
					}
				});

		advancedFilterMoveDownButton
				.setFont(new java.awt.Font("Dialog", 0, 12));
		advancedFilterMoveDownButton.setText("down");
		advancedFilterMoveDownButton.setMargin(new java.awt.Insets(2, 4, 2, 4));
		advancedFilterMoveDownButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						advancedFilterMoveDownButtonActionPerformed(evt);
					}
				});

		editAdvancedFilterPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Advanced Filter"));
		editAdvancedFilterNameLabel.setFont(new java.awt.Font("Dialog", 0, 12));
		editAdvancedFilterNameLabel.setText("Filter Name:");

		editAdvancedFilterNameField
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusLost(java.awt.event.FocusEvent evt) {
						editAdvancedFilterNameFieldFocusLost(evt);
					}
				});

		editAdvancedFilterDescriptionLabel.setFont(new java.awt.Font("Dialog",
				0, 12));
		editAdvancedFilterDescriptionLabel
				.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		editAdvancedFilterDescriptionLabel.setText("Filter Description:");

		editAdvancedFilterDescriptionTextArea.setColumns(20);
		editAdvancedFilterDescriptionTextArea.setLineWrap(true);
		editAdvancedFilterDescriptionTextArea.setRows(2);
		editAdvancedFilterDescriptionTextArea
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusLost(java.awt.event.FocusEvent evt) {
						editAdvancedFilterDescriptionTextAreaFocusLost(evt);
					}
				});

		editAdvancedFilterDescriptionPane
				.setViewportView(editAdvancedFilterDescriptionTextArea);

		editAdvancedFilterRegExpLabel
				.setFont(new java.awt.Font("Dialog", 0, 12));
		editAdvancedFilterRegExpLabel
				.setText("Regular Expression, partial match:");

		editAdvancedFilterRegExpTextArea.setColumns(20);
		editAdvancedFilterRegExpTextArea.setLineWrap(true);
		editAdvancedFilterRegExpTextArea.setRows(3);
		editAdvancedFilterRegExpTextArea
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusLost(java.awt.event.FocusEvent evt) {
						editAdvancedFilterRegExpTextAreaFocusLost(evt);
					}
				});

		editAdvancedFilterRegExpPane
				.setViewportView(editAdvancedFilterRegExpTextArea);

		editAdvancedFilterOpenRegExpEditorButton.setFont(new java.awt.Font(
				"Dialog", 0, 10));
		editAdvancedFilterOpenRegExpEditorButton
				.setText("Open Regular Expression Editor");
		editAdvancedFilterOpenRegExpEditorButton.setMargin(new java.awt.Insets(
				2, 4, 2, 4));
		editAdvancedFilterOpenRegExpEditorButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						editAdvancedFilterOpenRegExpEditorButtonActionPerformed(evt);
					}
				});

		editAdvancedFilterMatchPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Action in case of match"));
		editAdvancedFilterMatchStatusChangeLabel.setFont(new java.awt.Font(
				"Dialog", 0, 12));
		editAdvancedFilterMatchStatusChangeLabel.setText("Change status:");

		editAdvancedFilterMatchStatusChangeComboBox.setFont(new java.awt.Font(
				"Dialog", 0, 12));
		editAdvancedFilterMatchStatusChangeComboBox
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						editAdvancedFilterMatchStatusChangeComboBoxActionPerformed(evt);
					}
				});

		editAdvancedFilterMatchPrioChangeLabel.setFont(new java.awt.Font(
				"Dialog", 0, 12));
		editAdvancedFilterMatchPrioChangeLabel.setText("Priority change:");

		editAdvancedFilterMatchPrioChangeTextField
				.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		editAdvancedFilterMatchPrioChangeTextField
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusLost(java.awt.event.FocusEvent evt) {
						editAdvancedFilterMatchPrioChangeTextFieldFocusLost(evt);
					}
				});

		editAdvancedFilterMatchAssumeFinishedFileCheckBox
				.setFont(new java.awt.Font("Dialog", 0, 12));
		editAdvancedFilterMatchAssumeFinishedFileCheckBox
				.setText("<html>Assume file is already downloaded completely when found on disk.</html>");
		editAdvancedFilterMatchAssumeFinishedFileCheckBox
				.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0,
						0));
		editAdvancedFilterMatchAssumeFinishedFileCheckBox
				.setMargin(new java.awt.Insets(0, 0, 0, 0));
		editAdvancedFilterMatchAssumeFinishedFileCheckBox
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						editAdvancedFilterMatchAssumeFinishedFileCheckBoxActionPerformed(evt);
					}
				});

		org.jdesktop.layout.GroupLayout editAdvancedFilterMatchPanelLayout = new org.jdesktop.layout.GroupLayout(
				editAdvancedFilterMatchPanel);
		editAdvancedFilterMatchPanel
				.setLayout(editAdvancedFilterMatchPanelLayout);
		editAdvancedFilterMatchPanelLayout
				.setHorizontalGroup(editAdvancedFilterMatchPanelLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								editAdvancedFilterMatchPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												editAdvancedFilterMatchPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																editAdvancedFilterMatchAssumeFinishedFileCheckBox,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																265,
																Short.MAX_VALUE)
														.add(
																editAdvancedFilterMatchPanelLayout
																		.createParallelGroup(
																				org.jdesktop.layout.GroupLayout.TRAILING,
																				false)
																		.add(
																				org.jdesktop.layout.GroupLayout.LEADING,
																				editAdvancedFilterMatchPanelLayout
																						.createSequentialGroup()
																						.add(
																								editAdvancedFilterMatchStatusChangeLabel)
																						.addPreferredGap(
																								org.jdesktop.layout.LayoutStyle.RELATED)
																						.add(
																								editAdvancedFilterMatchStatusChangeComboBox,
																								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																								128,
																								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
																		.add(
																				org.jdesktop.layout.GroupLayout.LEADING,
																				editAdvancedFilterMatchPanelLayout
																						.createSequentialGroup()
																						.add(
																								editAdvancedFilterMatchPrioChangeLabel)
																						.addPreferredGap(
																								org.jdesktop.layout.LayoutStyle.RELATED)
																						.add(
																								editAdvancedFilterMatchPrioChangeTextField))))
										.addContainerGap()));

		editAdvancedFilterMatchPanelLayout.linkSize(new java.awt.Component[] {
				editAdvancedFilterMatchPrioChangeLabel,
				editAdvancedFilterMatchStatusChangeLabel },
				org.jdesktop.layout.GroupLayout.HORIZONTAL);

		editAdvancedFilterMatchPanelLayout
				.setVerticalGroup(editAdvancedFilterMatchPanelLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								editAdvancedFilterMatchPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												editAdvancedFilterMatchPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(
																editAdvancedFilterMatchStatusChangeLabel)
														.add(
																editAdvancedFilterMatchStatusChangeComboBox,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												editAdvancedFilterMatchPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(
																editAdvancedFilterMatchPrioChangeLabel)
														.add(
																editAdvancedFilterMatchPrioChangeTextField,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												editAdvancedFilterMatchAssumeFinishedFileCheckBox)
										.addContainerGap(24, Short.MAX_VALUE)));

		editAdvancedFilterNoMatchPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Action in case of no match"));
		editAdvancedFilterNoMatchStatusChangeLabel.setFont(new java.awt.Font(
				"Dialog", 0, 12));
		editAdvancedFilterNoMatchStatusChangeLabel.setText("Change status:");

		editAdvancedFilterNoMatchStatusChangeComboBox
				.setFont(new java.awt.Font("Dialog", 0, 12));
		editAdvancedFilterNoMatchStatusChangeComboBox
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						editAdvancedFilterNoMatchStatusChangeComboBoxActionPerformed(evt);
					}
				});

		editAdvancedFilterNoMatchPrioChangeLabel.setFont(new java.awt.Font(
				"Dialog", 0, 12));
		editAdvancedFilterNoMatchPrioChangeLabel.setText("Priority change:");

		editAdvancedFilterNoMatchPrioChangeTextField
				.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		editAdvancedFilterNoMatchPrioChangeTextField
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusLost(java.awt.event.FocusEvent evt) {
						editAdvancedFilterNoMatchPrioChangeTextFieldFocusLost(evt);
					}
				});

		editAdvancedFilterNoMatchAssumeFinishedFileCheckBox
				.setFont(new java.awt.Font("Dialog", 0, 12));
		editAdvancedFilterNoMatchAssumeFinishedFileCheckBox
				.setText("<html>Assume file is already downloaded completely when found on disk.</html>");
		editAdvancedFilterNoMatchAssumeFinishedFileCheckBox
				.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0,
						0));
		editAdvancedFilterNoMatchAssumeFinishedFileCheckBox
				.setMargin(new java.awt.Insets(0, 0, 0, 0));
		editAdvancedFilterNoMatchAssumeFinishedFileCheckBox
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						editAdvancedFilterNoMatchAssumeFinishedFileCheckBoxActionPerformed(evt);
					}
				});

		org.jdesktop.layout.GroupLayout editAdvancedFilterNoMatchPanelLayout = new org.jdesktop.layout.GroupLayout(
				editAdvancedFilterNoMatchPanel);
		editAdvancedFilterNoMatchPanel
				.setLayout(editAdvancedFilterNoMatchPanelLayout);
		editAdvancedFilterNoMatchPanelLayout
				.setHorizontalGroup(editAdvancedFilterNoMatchPanelLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								editAdvancedFilterNoMatchPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												editAdvancedFilterNoMatchPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																editAdvancedFilterNoMatchAssumeFinishedFileCheckBox,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																277,
																Short.MAX_VALUE)
														.add(
																editAdvancedFilterNoMatchPanelLayout
																		.createSequentialGroup()
																		.add(
																				editAdvancedFilterNoMatchPanelLayout
																						.createParallelGroup(
																								org.jdesktop.layout.GroupLayout.LEADING)
																						.add(
																								editAdvancedFilterNoMatchPrioChangeLabel)
																						.add(
																								editAdvancedFilterNoMatchStatusChangeLabel))
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				editAdvancedFilterNoMatchPanelLayout
																						.createParallelGroup(
																								org.jdesktop.layout.GroupLayout.LEADING,
																								false)
																						.add(
																								editAdvancedFilterNoMatchPrioChangeTextField)
																						.add(
																								editAdvancedFilterNoMatchStatusChangeComboBox,
																								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																								128,
																								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
										.addContainerGap()));
		editAdvancedFilterNoMatchPanelLayout
				.setVerticalGroup(editAdvancedFilterNoMatchPanelLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								editAdvancedFilterNoMatchPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												editAdvancedFilterNoMatchPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(
																editAdvancedFilterNoMatchStatusChangeComboBox,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(
																editAdvancedFilterNoMatchStatusChangeLabel))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												editAdvancedFilterNoMatchPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(
																editAdvancedFilterNoMatchPrioChangeLabel)
														.add(
																editAdvancedFilterNoMatchPrioChangeTextField,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												editAdvancedFilterNoMatchAssumeFinishedFileCheckBox)
										.addContainerGap(24, Short.MAX_VALUE)));

		org.jdesktop.layout.GroupLayout editAdvancedFilterPanelLayout = new org.jdesktop.layout.GroupLayout(
				editAdvancedFilterPanel);
		editAdvancedFilterPanel.setLayout(editAdvancedFilterPanelLayout);
		editAdvancedFilterPanelLayout
				.setHorizontalGroup(editAdvancedFilterPanelLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								editAdvancedFilterPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												editAdvancedFilterPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																org.jdesktop.layout.GroupLayout.TRAILING,
																editAdvancedFilterRegExpPane,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																616,
																Short.MAX_VALUE)
														.add(
																org.jdesktop.layout.GroupLayout.TRAILING,
																editAdvancedFilterPanelLayout
																		.createSequentialGroup()
																		.add(
																				editAdvancedFilterMatchPanel,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				editAdvancedFilterNoMatchPanel,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
														.add(
																editAdvancedFilterPanelLayout
																		.createSequentialGroup()
																		.add(
																				editAdvancedFilterNameLabel)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				editAdvancedFilterNameField,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																				138,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				editAdvancedFilterDescriptionLabel,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																				111,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				editAdvancedFilterDescriptionPane,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				261,
																				Short.MAX_VALUE))
														.add(
																org.jdesktop.layout.GroupLayout.TRAILING,
																editAdvancedFilterPanelLayout
																		.createSequentialGroup()
																		.add(
																				editAdvancedFilterRegExpLabel,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				441,
																				Short.MAX_VALUE)
																		.add(
																				175,
																				175,
																				175))
														.add(
																org.jdesktop.layout.GroupLayout.TRAILING,
																editAdvancedFilterOpenRegExpEditorButton))
										.addContainerGap()));
		editAdvancedFilterPanelLayout
				.setVerticalGroup(editAdvancedFilterPanelLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								editAdvancedFilterPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												editAdvancedFilterPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																editAdvancedFilterPanelLayout
																		.createParallelGroup(
																				org.jdesktop.layout.GroupLayout.BASELINE)
																		.add(
																				editAdvancedFilterNameLabel)
																		.add(
																				editAdvancedFilterNameField,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																		.add(
																				editAdvancedFilterDescriptionLabel))
														.add(
																editAdvancedFilterDescriptionPane,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																41,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(editAdvancedFilterRegExpLabel)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												editAdvancedFilterRegExpPane,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												58, Short.MAX_VALUE)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												editAdvancedFilterOpenRegExpEditorButton)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												editAdvancedFilterPanelLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING,
																false)
														.add(
																editAdvancedFilterMatchPanel,
																0, 131,
																Short.MAX_VALUE)
														.add(
																editAdvancedFilterNoMatchPanel,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																131,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));

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
												layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.TRAILING)
														.add(
																org.jdesktop.layout.GroupLayout.LEADING,
																editAdvancedFilterPanel,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.add(
																layout
																		.createSequentialGroup()
																		.add(
																				advancedFilterPane,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				599,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				layout
																						.createParallelGroup(
																								org.jdesktop.layout.GroupLayout.LEADING)
																						.add(
																								advancedFilterRemoveButton)
																						.add(
																								advancedFilterAddButton)
																						.add(
																								advancedFilterMoveUpButton)
																						.add(
																								advancedFilterMoveDownButton)))
														.add(
																org.jdesktop.layout.GroupLayout.LEADING,
																advancedFilterChainExplanationLabel,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																650,
																Short.MAX_VALUE)
														.add(
																org.jdesktop.layout.GroupLayout.LEADING,
																advancedFilterChainLabel))
										.addContainerGap()));

		layout.linkSize(new java.awt.Component[] { advancedFilterAddButton,
				advancedFilterRemoveButton },
				org.jdesktop.layout.GroupLayout.HORIZONTAL);

		layout.linkSize(new java.awt.Component[] {
				advancedFilterMoveDownButton, advancedFilterMoveUpButton },
				org.jdesktop.layout.GroupLayout.HORIZONTAL);

		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.add(advancedFilterChainLabel)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												advancedFilterChainExplanationLabel,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												35,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																layout
																		.createSequentialGroup()
																		.add(
																				advancedFilterAddButton)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				advancedFilterRemoveButton)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				advancedFilterMoveUpButton)
																		.addPreferredGap(
																				org.jdesktop.layout.LayoutStyle.RELATED)
																		.add(
																				advancedFilterMoveDownButton))
														.add(
																advancedFilterPane,
																0, 0,
																Short.MAX_VALUE))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												editAdvancedFilterPanel,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												341, Short.MAX_VALUE)
										.addContainerGap()));

		layout.linkSize(new java.awt.Component[] { advancedFilterAddButton,
				advancedFilterRemoveButton },
				org.jdesktop.layout.GroupLayout.VERTICAL);

		layout.linkSize(new java.awt.Component[] {
				advancedFilterMoveDownButton, advancedFilterMoveUpButton },
				org.jdesktop.layout.GroupLayout.VERTICAL);

	}// </editor-fold>//GEN-END:initComponents

	//GEN-FIRST:event_editAdvancedFilterOpenRegExpEditorButtonActionPerformed
	private void editAdvancedFilterOpenRegExpEditorButtonActionPerformed(
			java.awt.event.ActionEvent evt) {
		
		EditRegularExpressionDialog dialog = 
			new EditRegularExpressionDialog(
					(Dialog) getRootPane().getParent(), true);
		
		dialog.setRegularExpression(
				this.editAdvancedFilterRegExpTextArea.getText());
		dialog.setVisible(true);
		
		if(dialog.isOk()) {
			//copy the value from the editor
			this.editAdvancedFilterRegExpTextArea.setText(
					dialog.getRegularExpression());
		}
	}//GEN-LAST:event_editAdvancedFilterOpenRegExpEditorButtonActionPerformed

	//GEN-FIRST:event_editAdvancedFilterNoMatchAssumeFinishedFileCheckBoxActionPerformed
	private void editAdvancedFilterNoMatchAssumeFinishedFileCheckBoxActionPerformed(
			java.awt.event.ActionEvent evt) {

		updateAdvancedFilter();

	}//GEN-LAST:event_editAdvancedFilterNoMatchAssumeFinishedFileCheckBoxActionPerformed

	//GEN-FIRST:event_editAdvancedFilterNoMatchPrioChangeTextFieldFocusLost
	private void editAdvancedFilterNoMatchPrioChangeTextFieldFocusLost(
			java.awt.event.FocusEvent evt) {

		updateAdvancedFilter();

	}//GEN-LAST:event_editAdvancedFilterNoMatchPrioChangeTextFieldFocusLost

	//GEN-FIRST:event_editAdvancedFilterNoMatchStatusChangeComboBoxActionPerformed
	private void editAdvancedFilterNoMatchStatusChangeComboBoxActionPerformed(
			java.awt.event.ActionEvent evt) {

		updateAdvancedFilter();

	}//GEN-LAST:event_editAdvancedFilterNoMatchStatusChangeComboBoxActionPerformed

	//GEN-FIRST:event_editAdvancedFilterMatchAssumeFinishedFileCheckBoxActionPerformed
	private void editAdvancedFilterMatchAssumeFinishedFileCheckBoxActionPerformed(
			java.awt.event.ActionEvent evt) {

		updateAdvancedFilter();

	}//GEN-LAST:event_editAdvancedFilterMatchAssumeFinishedFileCheckBoxActionPerformed

	//GEN-FIRST:event_editAdvancedFilterMatchPrioChangeTextFieldFocusLost
	private void editAdvancedFilterMatchPrioChangeTextFieldFocusLost(
			java.awt.event.FocusEvent evt) {

		updateAdvancedFilter();

	}//GEN-LAST:event_editAdvancedFilterMatchPrioChangeTextFieldFocusLost

	//GEN-FIRST:event_editAdvancedFilterMatchStatusChangeComboBoxActionPerformed
	private void editAdvancedFilterMatchStatusChangeComboBoxActionPerformed(
			java.awt.event.ActionEvent evt) {

		updateAdvancedFilter();

	}//GEN-LAST:event_editAdvancedFilterMatchStatusChangeComboBoxActionPerformed

	//GEN-FIRST:event_editAdvancedFilterRegExpTextAreaFocusLost
	private void editAdvancedFilterRegExpTextAreaFocusLost(
			java.awt.event.FocusEvent evt) {

		updateAdvancedFilter();

	}//GEN-LAST:event_editAdvancedFilterRegExpTextAreaFocusLost

	//GEN-FIRST:event_editAdvancedFilterDescriptionTextAreaFocusLost
	private void editAdvancedFilterDescriptionTextAreaFocusLost(
			java.awt.event.FocusEvent evt) {

		updateAdvancedFilter();

	}//GEN-LAST:event_editAdvancedFilterDescriptionTextAreaFocusLost

	//GEN-FIRST:event_editAdvancedFilterNameFieldFocusLost
	private void editAdvancedFilterNameFieldFocusLost(
			java.awt.event.FocusEvent evt) {

		updateAdvancedFilter();

	}//GEN-LAST:event_editAdvancedFilterNameFieldFocusLost

	//GEN-FIRST:event_advancedFilterListValueChanged
	private void advancedFilterListValueChanged(
			javax.swing.event.ListSelectionEvent evt) {

		//ignore event when list readjusting
		if (evt.getValueIsAdjusting()) {
			return;
		}

		//remove rule from edit
		mRuleInEditMode = null;

		Object[] selectedValues = advancedFilterList.getSelectedValues();
		if (selectedValues != null && selectedValues.length == 1) {
			SwingUtils.setContainerAndChildrenEnabled(editAdvancedFilterPanel,
					true);

			RegExpFilterRule rule = ((RegExpFilterRuleListElement) selectedValues[0])
					.getRule();

			editAdvancedFilterNameField.setText(rule.getName());
			editAdvancedFilterDescriptionTextArea
					.setText(rule.getDescription());
			editAdvancedFilterRegExpTextArea.setText(rule.getPattern()
					.pattern());

			//match action
			{
				RegExpFilterAction matchAction = rule.getMatchAction();

				if (matchAction.getAccept() == null) {
					editAdvancedFilterMatchStatusChangeComboBox
							.setSelectedIndex(0);
				} else if (matchAction.getAccept().booleanValue()) {
					editAdvancedFilterMatchStatusChangeComboBox
							.setSelectedIndex(1);
				} else {
					editAdvancedFilterMatchStatusChangeComboBox
							.setSelectedIndex(2);
				}

				editAdvancedFilterMatchPrioChangeTextField.setText(String
						.valueOf(matchAction.getPriorityChange()));

				JobParameter assumeCompleteMatchParameter = matchAction
						.getJobParameter(DownloadJob.JOB_PARAMETER_SKIP_DOWNLOADED_FILE);
				editAdvancedFilterMatchAssumeFinishedFileCheckBox
						.setSelected(assumeCompleteMatchParameter != null
								&& assumeCompleteMatchParameter.getValue()
										.equals(Boolean.TRUE));
			}

			//no match action
			{
				RegExpFilterAction noMatchAction = rule.getNoMatchAction();

				if (noMatchAction.getAccept() == null) {
					editAdvancedFilterNoMatchStatusChangeComboBox
							.setSelectedIndex(0);
				} else if (noMatchAction.getAccept().booleanValue()) {
					editAdvancedFilterNoMatchStatusChangeComboBox
							.setSelectedIndex(1);
				} else {
					editAdvancedFilterNoMatchStatusChangeComboBox
							.setSelectedIndex(2);
				}

				editAdvancedFilterNoMatchPrioChangeTextField.setText(String
						.valueOf(noMatchAction.getPriorityChange()));

				JobParameter assumeCompleteNoMatchParameter = noMatchAction
						.getJobParameter(DownloadJob.JOB_PARAMETER_SKIP_DOWNLOADED_FILE);
				editAdvancedFilterNoMatchAssumeFinishedFileCheckBox
						.setSelected(assumeCompleteNoMatchParameter != null
								&& assumeCompleteNoMatchParameter.getValue()
										.equals(Boolean.TRUE));
			}

			mRuleInEditMode = rule;

		} else {

			//empty all fields
			SwingUtils.setContainerAndChildrenEnabled(editAdvancedFilterPanel,
					false);

			editAdvancedFilterNameField.setText(null);
			editAdvancedFilterDescriptionTextArea.setText(null);
			editAdvancedFilterRegExpTextArea.setText(null);

			editAdvancedFilterMatchStatusChangeComboBox.setSelectedIndex(0);
			editAdvancedFilterMatchPrioChangeTextField.setText(null);
			editAdvancedFilterMatchAssumeFinishedFileCheckBox
					.setSelected(false);

			editAdvancedFilterNoMatchStatusChangeComboBox.setSelectedIndex(0);
			editAdvancedFilterNoMatchPrioChangeTextField.setText(null);
			editAdvancedFilterNoMatchAssumeFinishedFileCheckBox
					.setSelected(false);

		}

	}//GEN-LAST:event_advancedFilterListValueChanged

	//GEN-FIRST:event_advancedFilterMoveDownButtonActionPerformed
	private void advancedFilterMoveDownButtonActionPerformed(
			java.awt.event.ActionEvent evt) {

		int selection = advancedFilterList.getSelectedIndex();
		if (selection < (advancedFilterFilterListModel.getSize() - 1)) {
			Object source = advancedFilterFilterListModel.get(selection);

			//move the entry
			advancedFilterFilterListModel.moveEntry(selection, 1);

			//move the selection
			advancedFilterList.setSelectedValue(source, true);
		}

	}//GEN-LAST:event_advancedFilterMoveDownButtonActionPerformed

	//GEN-FIRST:event_advancedFilterMoveUpButtonActionPerformed
	private void advancedFilterMoveUpButtonActionPerformed(
			java.awt.event.ActionEvent evt) {

		int selection = advancedFilterList.getSelectedIndex();
		if (selection > 0) {
			Object source = advancedFilterFilterListModel.get(selection);

			//move the entry
			advancedFilterFilterListModel.moveEntry(selection, -1);

			//move the selection
			advancedFilterList.setSelectedValue(source, true);
		}

	}//GEN-LAST:event_advancedFilterMoveUpButtonActionPerformed

	//GEN-FIRST:event_advancedFilterAddButtonActionPerformed
	private void advancedFilterAddButtonActionPerformed(
			java.awt.event.ActionEvent evt) {

		Object element = new RegExpFilterRuleListElement(new RegExpFilterRule());

		advancedFilterFilterListModel.addElement(element);

		//move the selection
		advancedFilterList.setSelectedValue(element, true);

	}//GEN-LAST:event_advancedFilterAddButtonActionPerformed

	//GEN-FIRST:event_advancedFilterRemoveButtonActionPerformed
	private void advancedFilterRemoveButtonActionPerformed(
			java.awt.event.ActionEvent evt) {

		int[] selections = advancedFilterList.getSelectedIndices();
		if (selections.length > 0) {
			for (int i = selections.length - 1; i >= 0; i--) {
				advancedFilterFilterListModel.remove(selections[i]);
			}
		}

	}//GEN-LAST:event_advancedFilterRemoveButtonActionPerformed	

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	protected javax.swing.JButton advancedFilterAddButton;
	protected javax.swing.JLabel advancedFilterChainExplanationLabel;
	protected javax.swing.JLabel advancedFilterChainLabel;
	protected javax.swing.JList advancedFilterList;
	protected javax.swing.JButton advancedFilterMoveDownButton;
	protected javax.swing.JButton advancedFilterMoveUpButton;
	protected javax.swing.JScrollPane advancedFilterPane;
	protected javax.swing.JButton advancedFilterRemoveButton;
	protected javax.swing.JLabel editAdvancedFilterDescriptionLabel;
	protected javax.swing.JScrollPane editAdvancedFilterDescriptionPane;
	protected javax.swing.JTextArea editAdvancedFilterDescriptionTextArea;
	protected javax.swing.JCheckBox editAdvancedFilterMatchAssumeFinishedFileCheckBox;
	protected javax.swing.JPanel editAdvancedFilterMatchPanel;
	protected javax.swing.JLabel editAdvancedFilterMatchPrioChangeLabel;
	protected javax.swing.JTextField editAdvancedFilterMatchPrioChangeTextField;
	protected javax.swing.JComboBox editAdvancedFilterMatchStatusChangeComboBox;
	protected javax.swing.JLabel editAdvancedFilterMatchStatusChangeLabel;
	protected javax.swing.JTextField editAdvancedFilterNameField;
	protected javax.swing.JLabel editAdvancedFilterNameLabel;
	protected javax.swing.JCheckBox editAdvancedFilterNoMatchAssumeFinishedFileCheckBox;
	protected javax.swing.JPanel editAdvancedFilterNoMatchPanel;
	protected javax.swing.JLabel editAdvancedFilterNoMatchPrioChangeLabel;
	protected javax.swing.JTextField editAdvancedFilterNoMatchPrioChangeTextField;
	protected javax.swing.JComboBox editAdvancedFilterNoMatchStatusChangeComboBox;
	protected javax.swing.JLabel editAdvancedFilterNoMatchStatusChangeLabel;
	protected javax.swing.JButton editAdvancedFilterOpenRegExpEditorButton;
	protected javax.swing.JPanel editAdvancedFilterPanel;
	protected javax.swing.JLabel editAdvancedFilterRegExpLabel;
	protected javax.swing.JScrollPane editAdvancedFilterRegExpPane;
	protected javax.swing.JTextArea editAdvancedFilterRegExpTextArea;
	// End of variables declaration//GEN-END:variables

}