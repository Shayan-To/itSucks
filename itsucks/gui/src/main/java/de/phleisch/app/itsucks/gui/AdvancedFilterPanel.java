/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 * 
 * $Id$
 */

package de.phleisch.app.itsucks.gui;

import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.phleisch.app.itsucks.filter.RegExpJobFilter.RegExpFilterRule;

public class AdvancedFilterPanel extends JPanel {

	private static final long serialVersionUID = -8188216132610940036L;

	private JTextArea jAddAdvancedFilter = null;

	private JRadioButton jRadioAFilterNoChange = null;

	private JRadioButton jRadioAFilterAccept = null;

	private JRadioButton jRadioAFilterReject = null;

	private JTextField jPriorityChange = null;

	private JCheckBox jCheckBox = null;

	private JLabel jAdvancedFilterHelp = null;

	private JLabel jLabelAdvancedFilterChangeStatus = null;

	private JLabel jLabel = null;

	private JScrollPane jAddAdvancedFilterScrollablePane = null;
	
	/**
	 * This is the default constructor
	 */
	public AdvancedFilterPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(10, 10, 351, 21));
		jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		jLabel.setText("Advanced filter (Regular expression, partial match):");
		jLabelAdvancedFilterChangeStatus = new JLabel();
		jLabelAdvancedFilterChangeStatus.setBounds(new Rectangle(10, 120, 351, 21));
		jLabelAdvancedFilterChangeStatus.setText("Change the status of the download:");
		jLabelAdvancedFilterChangeStatus.setFont(new Font("Dialog", Font.PLAIN, 12));
		jAdvancedFilterHelp = new JLabel();
		jAdvancedFilterHelp.setBounds(new Rectangle(370, 10, 181, 231));
		jAdvancedFilterHelp.setFont(new Font("Dialog", Font.PLAIN, 12));
		jAdvancedFilterHelp.setVerticalAlignment(SwingConstants.TOP);
		jAdvancedFilterHelp.setText(
				"<html>" +
				"<b>Hint:</b><br>" +
				"The advanced filter will be used to filter every found url. " +
				"In the Field 'Change Status' you can define what should happen " +
				"with this download. In the field 'Change priority' you can enter " +
				"a difference which will be applied to the priority of the download. " +
				"If you enter zero, nothing will happen (old prio. + 0), if you " +
				"enter a positive value or a negative one, this will change the priority " +
				"of the download." +
				"</html>"
				);
		setLayout(null);
		this.add(jLabel, null);
		this.add(getJAddAdvancedFilterScrollablePane(), null);
		this.add(jLabelAdvancedFilterChangeStatus, null);
		add(getJRadioAFilterReject(), null);
		add(getJRadioAFilterAccept(), null);
		add(getJRadioAFilterNoChange(), null);
		add(getJCheckBox(), null);
		this.add(getJPriorityChange(), null);
		add(jAdvancedFilterHelp, null);
		
		ButtonGroup group = new ButtonGroup();
		group.add(getJRadioAFilterReject());
		group.add(getJRadioAFilterAccept());
		group.add(getJRadioAFilterNoChange());
	}


	/**
	 * This method initializes jRadioAFilterNoChange	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioAFilterNoChange() {
		if (jRadioAFilterNoChange == null) {
			jRadioAFilterNoChange = new JRadioButton();
			jRadioAFilterNoChange.setFont(new Font("Dialog", Font.PLAIN, 12));
			jRadioAFilterNoChange.setSelected(true);
			jRadioAFilterNoChange.setBounds(new Rectangle(10, 140, 161, 21));
			jRadioAFilterNoChange.setText("Do not change status");
		}
		return jRadioAFilterNoChange;
	}

	/**
	 * This method initializes jRadioAFilterAccept	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioAFilterAccept() {
		if (jRadioAFilterAccept == null) {
			jRadioAFilterAccept = new JRadioButton();
			jRadioAFilterAccept.setFont(new Font("Dialog", Font.PLAIN, 12));
			jRadioAFilterAccept.setBounds(new Rectangle(10, 160, 161, 21));
			jRadioAFilterAccept.setText("Accept URL");
		}
		return jRadioAFilterAccept;
	}

	/**
	 * This method initializes jRadioAFilterReject	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioAFilterReject() {
		if (jRadioAFilterReject == null) {
			jRadioAFilterReject = new JRadioButton();
			jRadioAFilterReject.setFont(new Font("Dialog", Font.PLAIN, 12));
			jRadioAFilterReject.setBounds(new Rectangle(10, 180, 161, 21));
			jRadioAFilterReject.setText("Reject URL");
		}
		return jRadioAFilterReject;
	}

	
	/**
	 * This method initializes jPriorityChange	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJPriorityChange() {
		if (jPriorityChange == null) {
			jPriorityChange = new JTextField();
			jPriorityChange.setBounds(new Rectangle(160, 210, 81, 21));
			jPriorityChange.setEnabled(false);
			jPriorityChange.setText("0");
		}
		return jPriorityChange;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(10, 210, 141, 21));
			jCheckBox.setFont(new Font("Dialog", Font.PLAIN, 12));
			jCheckBox.setSelected(false);
			jCheckBox.setText("Change the priority");
			jCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					jPriorityChange.setEnabled(jCheckBox.isSelected());
				}
			});
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jAddAdvancedFilter	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextArea getJAddAdvancedFilter() {
		if (jAddAdvancedFilter == null) {
			jAddAdvancedFilter = new JTextArea();
			jAddAdvancedFilter.setText("");
			jAddAdvancedFilter.setLineWrap(true);
		}
		return jAddAdvancedFilter;
	}

	/**
	 * This method initializes jAddAdvancedFilterScrollablePane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJAddAdvancedFilterScrollablePane() {
		if (jAddAdvancedFilterScrollablePane == null) {
			jAddAdvancedFilterScrollablePane = new JScrollPane();
			jAddAdvancedFilterScrollablePane.setBounds(new Rectangle(10, 30, 351, 81));
			jAddAdvancedFilterScrollablePane.setViewportView(getJAddAdvancedFilter());
		}
		return jAddAdvancedFilterScrollablePane;
	}

	public RegExpFilterRule buildRule() {

		String pattern = getJAddAdvancedFilter().getText();
		
		Boolean action = null;
		if(getJRadioAFilterNoChange().isSelected()) action = null;
		if(getJRadioAFilterAccept().isSelected()) action = Boolean.TRUE;
		if(getJRadioAFilterReject().isSelected()) action = Boolean.FALSE;
		
		int prioChange = 0;
		if(getJCheckBox().isSelected()) {
			prioChange = Integer.parseInt(getJPriorityChange().getText());
		}
		
		RegExpFilterRule rule = new RegExpFilterRule(
				pattern, 
				action,
				prioChange);
		
		return rule;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
