/*
 * EntryListPanel.java
 *
 * Created on __DATE__, __TIME__
 */

package de.phleisch.app.itsucks.gui.common.panel;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.phleisch.app.itsucks.gui.util.ExtendedListModel;
import de.phleisch.app.itsucks.gui.util.SwingUtils;

/**
 *
 * @author  __USER__
 */
public abstract class EditListPanel extends javax.swing.JPanel {

	protected ExtendedListModel mListModel = new ExtendedListModel();
	protected ListElement mElementInEditMode;

	public static interface ListElement {
	}
	
	protected FocusListener mFocusListener = new FocusListener() {

		public void focusGained(FocusEvent pE) {
			//no action
		}

		public void focusLost(FocusEvent pE) {
			if(mElementInEditMode != null) {
				updateListElement(mElementInEditMode);
			}
		}
		
	};
	
	protected ItemListener mItemListener = new ItemListener() {

		public void itemStateChanged(ItemEvent pE) {
			if(mElementInEditMode != null) {
				updateListElement(mElementInEditMode);
			}
		}
	};
	
	protected ChangeListener mChangeListener = new ChangeListener() {

		public void stateChanged(ChangeEvent pE) {
			if(mElementInEditMode != null) {
				updateListElement(mElementInEditMode);
			}
		}
	};

	/** Creates new form EntryListPanel */
	public EditListPanel() {
		initComponents();
	}
	
	public ExtendedListModel getListModel() {
		return mListModel;
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		editListPane = new javax.swing.JScrollPane();
		editList = new javax.swing.JList();
		editListAddButton = new javax.swing.JButton();
		editListMoveUpButton = new javax.swing.JButton();
		editListRemoveButton = new javax.swing.JButton();
		editListMoveDownButton = new javax.swing.JButton();

		editList.setFont(new java.awt.Font("Dialog", 0, 12));
		editList.setModel(mListModel);
		editList
				.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
					public void valueChanged(
							javax.swing.event.ListSelectionEvent evt) {
						editListValueChanged(evt);
					}
				});
		editListPane.setViewportView(editList);

		editListAddButton.setFont(new java.awt.Font("Dialog", 0, 12));
		editListAddButton.setText("+");
		editListAddButton.setMargin(new java.awt.Insets(2, 4, 2, 4));
		editListAddButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						editListAddButtonActionPerformed(evt);
					}
				});

		editListMoveUpButton.setFont(new java.awt.Font("Dialog", 0, 12));
		editListMoveUpButton.setText("up");
		editListMoveUpButton.setMargin(new java.awt.Insets(2, 4, 2, 4));
		editListMoveUpButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						editListMoveUpButtonActionPerformed(evt);
					}
				});

		editListRemoveButton.setFont(new java.awt.Font("Dialog", 0, 12));
		editListRemoveButton.setText("-");
		editListRemoveButton.setMargin(new java.awt.Insets(2, 4, 2, 4));
		editListRemoveButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						editListRemoveButtonActionPerformed(evt);
					}
				});

		editListMoveDownButton.setFont(new java.awt.Font("Dialog", 0, 12));
		editListMoveDownButton.setText("down");
		editListMoveDownButton.setMargin(new java.awt.Insets(2, 4, 2, 4));
		editListMoveDownButton
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						editListMoveDownButtonActionPerformed(evt);
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
								layout
										.createSequentialGroup()
										.add(
												editListPane,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												326, Short.MAX_VALUE)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING,
																false)
														.add(
																editListMoveDownButton,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.add(
																editListMoveUpButton,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.add(
																layout
																		.createSequentialGroup()
																		.add(
																				layout
																						.createParallelGroup(
																								org.jdesktop.layout.GroupLayout.TRAILING,
																								false)
																						.add(
																								org.jdesktop.layout.GroupLayout.LEADING,
																								editListRemoveButton,
																								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.add(
																								org.jdesktop.layout.GroupLayout.LEADING,
																								editListAddButton,
																								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE))
																		.addContainerGap()))));
		layout.setVerticalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup().add(editListAddButton)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED).add(
								editListRemoveButton).addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED).add(
								editListMoveUpButton).addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED).add(
								editListMoveDownButton)).add(editListPane, 0,
				0, Short.MAX_VALUE));
	}// </editor-fold>
	//GEN-END:initComponents

	protected void editListMoveDownButtonActionPerformed(
			java.awt.event.ActionEvent evt) {
		int selection = editList.getSelectedIndex();
		if (selection < (mListModel.getSize() - 1)) {
			Object source = mListModel.get(selection);

			//move the entry
			mListModel.moveEntry(selection, 1);

			//move the selection
			editList.setSelectedValue(source, true);
		}
	}

	protected void editListRemoveButtonActionPerformed(
			java.awt.event.ActionEvent evt) {
		int[] selections = editList.getSelectedIndices();
		if (selections.length > 0) {
			for (int i = selections.length - 1; i >= 0; i--) {
				mListModel.remove(selections[i]);
			}
		}
	}

	protected void editListMoveUpButtonActionPerformed(
			java.awt.event.ActionEvent evt) {
		int selection = editList.getSelectedIndex();
		if (selection > 0) {
			Object source = mListModel.get(selection);

			//move the entry
			mListModel.moveEntry(selection, -1);

			//move the selection
			editList.setSelectedValue(source, true);
		}
	}

	protected void editListAddButtonActionPerformed(
			java.awt.event.ActionEvent evt) {
		ListElement element = createNewElement();

		mListModel.addElement(element);

		//move the selection
		editList.setSelectedValue(element, true);
	}

	protected void editListValueChanged(javax.swing.event.ListSelectionEvent evt) {
		//ignore event when list readjusting
		if (evt.getValueIsAdjusting()) {
			return;
		}

		//remove rule from edit
		mElementInEditMode = null;

		Object[] selectedValues = editList.getSelectedValues();
		if (selectedValues != null && selectedValues.length == 1) {
			ListElement element = (ListElement) selectedValues[0];

			enableEditArea(true);
			loadEditArea(element);

			mElementInEditMode = element;

		} else {

			//empty all fields

			enableEditArea(false);
			emptyEditArea();
		}
	}

	public void registerDataField(Component pComponent) {
		if(pComponent instanceof JComboBox) {
			((JComboBox)pComponent).addItemListener(mItemListener);
		}
		if(pComponent instanceof JCheckBox) {
			((JCheckBox)pComponent).addChangeListener(mChangeListener);
		}
		
		pComponent.addFocusListener(mFocusListener);
	}
	
	public void deregisterDataField(Component pComponent) {
		if(pComponent instanceof JComboBox) {
			((JComboBox)pComponent).removeItemListener(mItemListener);
		}
		if(pComponent instanceof JCheckBox) {
			((JCheckBox)pComponent).removeChangeListener(mChangeListener);
		}
		
		pComponent.removeFocusListener(mFocusListener);
	}
	
	protected abstract ListElement createNewElement();

	protected abstract void loadEditArea(ListElement pElement);

	protected abstract void emptyEditArea();

	protected abstract void enableEditArea(boolean pEnable);
	
	protected abstract void updateListElement(ListElement pElement);
	
	@Override
	public void setEnabled(boolean pEnabled) {
		super.setEnabled(pEnabled);
		SwingUtils.setChildrenEnabled(this, pEnabled);
		
		if(!pEnabled) {
			editList.clearSelection();
		}
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JList editList;
	private javax.swing.JButton editListAddButton;
	private javax.swing.JButton editListMoveDownButton;
	private javax.swing.JButton editListMoveUpButton;
	private javax.swing.JScrollPane editListPane;
	private javax.swing.JButton editListRemoveButton;
	// End of variables declaration//GEN-END:variables

}