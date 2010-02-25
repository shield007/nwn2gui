/*
 *  Copyright (C) 2008  John-Paul.Stanford <dev@stanwood.org.uk>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.stanwood.swing.perferences;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXHeader;
import org.jdesktop.swingx.util.WindowUtils;
import org.stanwood.swing.EnhancedDialog;
import org.stanwood.swing.StandardActions;
import org.stanwood.swing.icons.IconManager;

public abstract class AbstractPreferenceDialog extends EnhancedDialog implements ActionListener {

	private JButton cmdApply;
	private JButton cmdCancel;
	private JButton cmdOk;
	private JSplitPane splitPane;
	private List<IPreferencePanel> optionPanels = new ArrayList<IPreferencePanel>();
	private Settings settings;
	private JPanel mainArea;
	private CardLayout optionPanelLayout;
	private JXHeader header;
	private JList lstOptions;
	private boolean settingsChanged = false;
	private boolean dirty;
	
	public AbstractPreferenceDialog(Frame parent,String title,Settings settings) {
		super(parent, title, true);		
		this.settings = settings;
		createControl();
		header.setTitle(title);

		addPreferencePanels();
		loadSettings();
		
		lstOptions.setSelectedIndex(0);
		selectionChanged();
		
		setSize(600,400);
		
		setLocation(WindowUtils.getPointForCentering(this));		
	}

	protected abstract void addPreferencePanels();
	
	protected void addPreferencePanel(AbstractPreferencePanel optionsPanel) {
		optionPanels.add(optionsPanel);
		mainArea.add(optionsPanel,optionsPanel.getPanelName());		
	}
	
	private void createControl() {
		setLayout(new BorderLayout(1,1));	
		
		header = new JXHeader();				
		header.setBackground(Color.white);
		header.setIcon(IconManager.getInstance().getIcon(IconManager.SIZE_48,IconManager.ICON_PREFERENCES_SYSTEM));
		header.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		add(header,BorderLayout.NORTH);
		
		JComponent prefSelection = createPrefSelection();
		mainArea = createMainArea();

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, prefSelection,
				mainArea);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);
		add(splitPane, BorderLayout.CENTER);

		createButtonPane();
	}

	private JComponent createPrefSelection() {		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		AbstractListModel listOptions = new AbstractListModel() {
			private static final long serialVersionUID = -4294619156657302370L;

			@Override
			public Object getElementAt(int index) {
				return optionPanels.get(index);
			}

			@Override
			public int getSize() {
				return optionPanels.size();
			}
		};
		lstOptions = new JList(listOptions);
		lstOptions.setCellRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = -4037143996417535601L;

			@Override
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				if (value instanceof IPreferencePanel) {
					value =((IPreferencePanel) value).getPanelName();
				}
				return super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);

			}
		});
		lstOptions.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectionChanged();
			}
		});
		

		panel.add(lstOptions, BorderLayout.CENTER);
		return panel;
	}

	private JPanel createMainArea() {		
		JPanel panel = new JPanel();
		optionPanelLayout = new CardLayout();		
		panel.setLayout(optionPanelLayout);
		
		return panel;
	}

	private void createButtonPane() {
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		panel.setLayout(new GridLayout(1,0,5,5));
		cmdOk = new JButton(StandardActions.getDialogOkAction(this));	
		panel.add(cmdOk);
		getRootPane().setDefaultButton(cmdOk);

		cmdCancel = new JButton(StandardActions.getDialogCancelAction(this));		
		panel.add(cmdCancel);

		cmdApply = new JButton(StandardActions.getDialogApplyAction(this));		
		panel.add(cmdApply);
		panel2.add(panel, BorderLayout.EAST);
		this.add(panel2, BorderLayout.SOUTH);
	}

	@Override
	public void cancel() {
		dispose();
	}

	@Override
	public void ok() {
		apply();		
		dispose();
	}

	private void apply() {
		for (IPreferencePanel panel : optionPanels) {
			panel.saveSettings(settings);
		}
		settings.save();
		settingsChanged = true;
		setDirty(false);
	}
	
	public boolean hasSettingsChanged() {
		return settingsChanged;
	}
	
	private void loadSettings() {
		for (IPreferencePanel panel : optionPanels) {
			panel.loadSettings(settings);
		}
		setDirty(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == cmdOk) {
			ok();
		} else if (source == cmdCancel) {
			cancel();
		} else if (source == cmdApply) {
			apply();
		}
	}

	private void selectionChanged() {
		int index = lstOptions.getSelectedIndex();
		IPreferencePanel optPanel = optionPanels.get(index);
		optionPanelLayout.show(mainArea, optPanel.getPanelName());
		header.setDescription(optPanel.getDescription());
	}
	
	protected boolean isDirty() {
		return dirty;
	}
	
	protected void setDirty(boolean dirty) {
		this.dirty = dirty;
		if (dirty==true) {
			cmdApply.setEnabled(true);
			cmdOk.setEnabled(true);
		}
		else {
			cmdApply.setEnabled(false);
			cmdOk.setEnabled(false);
		}
	}
}
