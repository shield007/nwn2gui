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
package org.stanwood.nwn2.gui.options;

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
import org.stanwood.nwn2.gui.options.panels.NWN2OptionsPanel;
import org.stanwood.swing.EnhancedDialog;
import org.stanwood.swing.icons.IconManager;

public class OptionsDialog extends EnhancedDialog implements ActionListener {

	private static final long serialVersionUID = 2557972118818391136L;
	
	private JButton cmdApply;
	private JButton cmdCancel;
	private JButton cmdOk;
	private JSplitPane splitPane;
	private List<IOptionsPanel> optionPanels = new ArrayList<IOptionsPanel>();
	private NWN2GuiSettings settings;
	private JPanel mainArea;
	private CardLayout optionPanelLayout;
	private JXHeader header;
	private JList lstOptions;

	public OptionsDialog(Frame parent) {
		super(parent, "NWN2 GUI Options", true);
		settings = NWN2GuiSettings.getInstance();
		
		createControl();

		addPreferencePanel(new NWN2OptionsPanel());
		loadSettings();
		
		lstOptions.setSelectedIndex(0);
		selectionChanged();
		
		setSize(400,400);
		
		setLocation(WindowUtils.getPointForCentering(this));		
	}

	private void addPreferencePanel(NWN2OptionsPanel optionsPanel) {
		optionPanels.add(optionsPanel);
		mainArea.add(optionsPanel,optionsPanel.getPanelName());		
	}

	private void createControl() {
		setLayout(new BorderLayout(1,1));	
		
		header = new JXHeader();		
		header.setTitle("NWN2GUI Settings");
		header.setBackground(Color.white);
		header.setIcon(IconManager.getInstance().getIcon(IconManager.ICON_PREFERENCES_SYSTEM));
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

			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				if (value instanceof IOptionsPanel) {
					value =((IOptionsPanel) value).getPanelName();
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
		cmdOk = new JButton();
		panel.add(cmdOk);
		cmdOk.addActionListener(this);
		cmdOk.setText("Ok");
		cmdOk.setMnemonic('O');
		getRootPane().setDefaultButton(cmdOk);

		cmdCancel = new JButton();
		cmdCancel.setText("Cancel");
		cmdCancel.setMnemonic('A');
		cmdCancel.addActionListener(this);
		panel.add(cmdCancel);

		cmdApply = new JButton();
		cmdApply.setText("Apply");
		cmdApply.setMnemonic('A');
		cmdApply.addActionListener(this);
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
		for (IOptionsPanel panel : optionPanels) {
			panel.saveSettings(settings);
		}
		settings.save();
	}
	
	private void loadSettings() {
		for (IOptionsPanel panel : optionPanels) {
			panel.loadSettings(settings);
		}
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
		IOptionsPanel optPanel = optionPanels.get(index);
		optionPanelLayout.show(mainArea, optPanel.getPanelName());
		header.setDescription(optPanel.getDescription());
	}

}
