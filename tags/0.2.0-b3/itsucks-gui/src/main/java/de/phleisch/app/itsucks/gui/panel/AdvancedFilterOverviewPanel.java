/* Copyright (C) 2006-2007 Oliver Mihatsch (banishedknight@users.sf.net)
 * This is free software distributed under the terms of the
 * GNU Public License.  See the file COPYING for details. 
 *
 * $Id$
 * Created on 13.01.2007
 */

package de.phleisch.app.itsucks.gui.panel;

import java.awt.Dialog;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.phleisch.app.itsucks.filter.MaxLinksToFollowFilter;
import de.phleisch.app.itsucks.filter.RegExpJobFilter;
import de.phleisch.app.itsucks.filter.RegExpJobFilter.RegExpFilterAction;
import de.phleisch.app.itsucks.filter.RegExpJobFilter.RegExpFilterRule;
import de.phleisch.app.itsucks.gui.AddAdvancedFilterDialog;
import de.phleisch.app.itsucks.gui.AddAdvancedFilterInterface;

public class AdvancedFilterOverviewPanel extends JPanel implements AddAdvancedFilterInterface {

	private static final long serialVersionUID = 1L;

	private JList jAdvancedFilterList = null;
	private ExtendedListModel mAdvancedFilterListModel = null;

	private JLabel jLabelAdvancedFilter = null;

	private JButton jButtonAdvancedFilterAdd = null;

	private JButton jButtonAdvancedFilterRemove = null;
	
	private JScrollPane jAdvancedFilterListScrollPane = null;
	
	private JButton jButtonMoveRuleUp = null;

	private JButton jButtonMoveRuleDown = null;
	
	private Dialog mParentDialog = null;

	private JButton jEdit = null;

	private JLabel jLabelMaxLinksToFollow = null;

	private JTextField jMaxLinksToFollow = null;
	
	/**
	 * This is the default constructor
	 */
	public AdvancedFilterOverviewPanel(Dialog pParentDialog) {
		super();
		mParentDialog = pParentDialog;
		
		initialize();
		
		buildDefaultRule();
	}

	private void buildDefaultRule() {
		//build default rule
		RegExpFilterRule rule = new RegExpFilterRule("css$|jpg$|gif$|png$|js$");
		
		RegExpFilterAction ruleMatchAction = new RegExpFilterAction(null, +50);
//		ruleMatchAction.addJobParameter(
//				new JobParameter(DownloadJob.PARAMETER_SKIP_DOWNLOADED_FILE, Boolean.TRUE));
		
		RegExpFilterAction ruleNoMatchAction = new RegExpFilterAction(null, 0);
//		ruleNoMatchAction.addJobParameter(
//				new JobParameter(DownloadJob.PARAMETER_SKIP_DOWNLOADED_FILE, Boolean.FALSE));
		
		rule.setMatchAction(ruleMatchAction);
		rule.setNoMatchAction(ruleNoMatchAction);
		
		addAdvancedFilterRule(rule);
	}
	
	public boolean checkFields() {
		
		List<String> errorMessages = new ArrayList<String>();
		
		//check fields
		try {
			Integer.parseInt(jMaxLinksToFollow.getText());
		} catch (NumberFormatException e) {
			errorMessages.add("Maximum files to download is not a number.");
		}
		
		if(errorMessages.size() > 0) {
			String messageText = "";
			
			for (String message : errorMessages) {
				messageText += message + "\n";
			}
			
			JOptionPane.showMessageDialog(this, messageText, "Validation error", JOptionPane.ERROR_MESSAGE );
			
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabelMaxLinksToFollow = new JLabel();
		jLabelMaxLinksToFollow.setBounds(new Rectangle(10, 240, 271, 21));
		jLabelMaxLinksToFollow.setText("Max links to follow: (-1 = unlimited):");
		jLabelMaxLinksToFollow.setFont(new Font("Dialog", Font.PLAIN, 12));
		this.setSize(394, 366);
		this.setLayout(null);
		jLabelAdvancedFilter = new JLabel();
		jLabelAdvancedFilter.setBounds(new Rectangle(10, 10, 341, 31));
		jLabelAdvancedFilter.setFont(new Font("Dialog", Font.PLAIN, 12));
		jLabelAdvancedFilter.setVerticalAlignment(SwingConstants.TOP);
		jLabelAdvancedFilter.setText("<html>Advanced Filter: <br>(A job without any 'accept' match will be rejected)</html>");
		add(jLabelAdvancedFilter, null);
		this.add(getJAdvancedFilterListScrollPane(), null);
		this.add(getJButtonAdvancedFilterAdd(), null);
		this.add(getJButtonAdvancedFilterRemove(), null);
		this.add(getJButtonMoveRuleUp(), null);
		this.add(getJButtonMoveRuleDown(), null);
		this.add(getJEdit(), null);
		this.add(jLabelMaxLinksToFollow, null);
		this.add(getJMaxLinksToFollow(), null);
	}


	/**
	 * This method initializes mFileFilterListModel1	
	 * 	
	 * @return javax.swing.DefaultListModel	
	 */
	private ExtendedListModel getAdvancedFilterListModel() {
		if (mAdvancedFilterListModel == null) {
			mAdvancedFilterListModel = new ExtendedListModel();
		}
		return mAdvancedFilterListModel;
	}

	/**
	 * This method initializes jButtonAdvancedFilterAdd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonAdvancedFilterAdd() {
		if (jButtonAdvancedFilterAdd == null) {
			jButtonAdvancedFilterAdd = new JButton();
			jButtonAdvancedFilterAdd.setBounds(new Rectangle(40, 190, 101, 21));
			jButtonAdvancedFilterAdd.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButtonAdvancedFilterAdd.setText("Add");
			jButtonAdvancedFilterAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					AddAdvancedFilterDialog advancedFilterDialog = 
						new AddAdvancedFilterDialog(mParentDialog, AdvancedFilterOverviewPanel.this);
					advancedFilterDialog.setModal(true);
					advancedFilterDialog.setVisible(true);
				}
			});
		}
		return jButtonAdvancedFilterAdd;
	}

	/**
	 * This method initializes jAdvancedFilterListScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJAdvancedFilterListScrollPane() {
		if (jAdvancedFilterListScrollPane == null) {
			jAdvancedFilterListScrollPane = new JScrollPane();
			jAdvancedFilterListScrollPane.setBounds(new Rectangle(40, 40, 341, 141));
			jAdvancedFilterListScrollPane.setViewportView(getJAdvancedFilterList());
		}
		return jAdvancedFilterListScrollPane;
	}	
	
	/**
	 * This method initializes jAdvancedFilterList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJAdvancedFilterList() {
		if (jAdvancedFilterList == null) {
			jAdvancedFilterList = new JList(getAdvancedFilterListModel());
			jAdvancedFilterList.setFont(new Font("Dialog", Font.PLAIN, 12));
		}
		return jAdvancedFilterList;
	}
	

	/**
	 * This method initializes jButtonAdvancedFilterRemove	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonAdvancedFilterRemove() {
		if (jButtonAdvancedFilterRemove == null) {
			jButtonAdvancedFilterRemove = new JButton();
			jButtonAdvancedFilterRemove.setBounds(new Rectangle(280, 190, 101, 21));
			jButtonAdvancedFilterRemove.setFont(new Font("Dialog", Font.PLAIN, 12));
			jButtonAdvancedFilterRemove.setText("Remove");
			jButtonAdvancedFilterRemove.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int[] selections = jAdvancedFilterList.getSelectedIndices();
					if(selections.length > 0) {
						for (int i = selections.length - 1; i >= 0; i--) {
							mAdvancedFilterListModel.remove(selections[i]);
						}
					}
				}
			});
		}
		return jButtonAdvancedFilterRemove;
	}
	

	/**
	 * This method initializes jButtonMoveRuleUp	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonMoveRuleUp() {
		if (jButtonMoveRuleUp == null) {
			jButtonMoveRuleUp = new JButton();
			jButtonMoveRuleUp.setBounds(new Rectangle(10, 50, 21, 21));
			jButtonMoveRuleUp.setIcon(new ImageIcon(getClass().getResource("/go-up.png")));
			jButtonMoveRuleUp.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					int selection = jAdvancedFilterList.getSelectedIndex();
					if(selection > 0) {
						Object source = mAdvancedFilterListModel.get(selection);
						
						//move the entry
						mAdvancedFilterListModel.moveEntry(selection, -1);
						
						//move the selection
						jAdvancedFilterList.setSelectedValue(source, true);
					}
					
				}
			});
		}
		return jButtonMoveRuleUp;
	}

	/**
	 * This method initializes jButtonMoveRuleDown	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonMoveRuleDown() {
		if (jButtonMoveRuleDown == null) {
			jButtonMoveRuleDown = new JButton();
			jButtonMoveRuleDown.setBounds(new Rectangle(10, 150, 21, 21));
			jButtonMoveRuleDown.setIcon(new ImageIcon(getClass().getResource("/go-down.png")));
			jButtonMoveRuleDown.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					int selection = jAdvancedFilterList.getSelectedIndex();
					if(selection > 0) {
						Object source = mAdvancedFilterListModel.get(selection);
						
						//move the entry
						mAdvancedFilterListModel.moveEntry(selection, 1);
						
						//move the selection
						jAdvancedFilterList.setSelectedValue(source, true);
					}
					
				}
			});
		}
		return jButtonMoveRuleDown;
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.chaoscrawler.gui.second_try.AddAdvancedFilterInterface#addAdvancedFilterRule(de.phleisch.app.chaoscrawler.filter.RegExpJobFilter.RegExpFilterRule)
	 */
	public void addAdvancedFilterRule(RegExpJobFilter.RegExpFilterRule pRule) {
		mAdvancedFilterListModel.add(mAdvancedFilterListModel.size(), pRule);
		this.jAdvancedFilterList.ensureIndexIsVisible(mAdvancedFilterListModel.size() - 1);
	}
	
	/* (non-Javadoc)
	 * @see de.phleisch.app.itsucks.gui.AddAdvancedFilterInterface#updateAdvancedFilterRule(de.phleisch.app.itsucks.filter.RegExpJobFilter.RegExpFilterRule)
	 */
	public void updateAdvancedFilterRule(RegExpJobFilter.RegExpFilterRule pRule) {
		int selection = jAdvancedFilterList.getSelectedIndex();
		if(selection > -1) {
			mAdvancedFilterListModel.set(selection, pRule);
			this.jAdvancedFilterList.ensureIndexIsVisible(selection);
		}
	}

	public RegExpJobFilter buildAdvancedFilter() {
		
		//copy the filter from the model to the filter list
		RegExpJobFilter regExpJobFilter = new RegExpJobFilter();
		Enumeration<?> advancedFilters = mAdvancedFilterListModel.elements();
		
		while(advancedFilters.hasMoreElements()) {
			RegExpFilterRule rule = (RegExpFilterRule) advancedFilters.nextElement();
			regExpJobFilter.addFilterRule(rule);
		}
		
		return regExpJobFilter;
	}

	public void loadAdvancedFilter(RegExpJobFilter pRegExpJobFilter) {
		
		if(pRegExpJobFilter == null) return;
		
		ExtendedListModel filterListModel = getAdvancedFilterListModel();
		filterListModel.clear();
		
		for (RegExpFilterRule rule : pRegExpJobFilter.getFilterRules()) {
			filterListModel.addElement(rule);
		}
	}

	public void loadDownloadFilter(MaxLinksToFollowFilter pDownloadFilter) {
		if(pDownloadFilter == null) return;
		
		jMaxLinksToFollow.setText(Integer.toString(pDownloadFilter.getMaxLinksToFollow()));
		
	}
	
	public MaxLinksToFollowFilter buildDownloadFilter() {

		MaxLinksToFollowFilter filter = new MaxLinksToFollowFilter();
		
		filter.setMaxLinksToFollow(Integer.parseInt(jMaxLinksToFollow.getText()));
		
		return filter;
	}
	
	/**
	 * This method initializes jEdit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJEdit() {
		if (jEdit == null) {
			jEdit = new JButton();
			jEdit.setBounds(new Rectangle(160, 190, 101, 21));
			jEdit.setFont(new Font("Dialog", Font.PLAIN, 12));
			jEdit.setText("Edit");
			jEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					int selection = jAdvancedFilterList.getSelectedIndex();
					if(selection > -1) {
						RegExpFilterRule rule = (RegExpFilterRule) 
							mAdvancedFilterListModel.get(selection);

						AddAdvancedFilterDialog advancedFilterDialog = 
							new AddAdvancedFilterDialog(mParentDialog, AdvancedFilterOverviewPanel.this);
						advancedFilterDialog.fill(rule);
						advancedFilterDialog.setModal(true);
						advancedFilterDialog.setVisible(true);
					}
				}
			});
		}
		return jEdit;
	}

	/**
	 * This method initializes jMaxLinksToFollow	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJMaxLinksToFollow() {
		if (jMaxLinksToFollow == null) {
			jMaxLinksToFollow = new JTextField();
			jMaxLinksToFollow.setBounds(new Rectangle(10, 260, 61, 21));
			jMaxLinksToFollow.setText("-1");
			jMaxLinksToFollow.setHorizontalAlignment(JTextField.RIGHT);
			jMaxLinksToFollow.setFont(new Font("Dialog", Font.PLAIN, 12));
		}
		return jMaxLinksToFollow;
	}



}  //  @jve:decl-index=0:visual-constraint="4,-5"