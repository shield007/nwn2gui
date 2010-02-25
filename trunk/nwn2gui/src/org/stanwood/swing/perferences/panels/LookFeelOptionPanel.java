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

package org.stanwood.swing.perferences.panels;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.stanwood.swing.LabelledItemPanel;
import org.stanwood.swing.perferences.AbstractOptionPanel;
import org.stanwood.swing.perferences.Settings;

public class LookFeelOptionPanel extends AbstractOptionPanel {

	private DefaultComboBoxModel lafModel;

	@Override
	protected void createControl(JPanel parent) {
		parent.setLayout(new BorderLayout(5,5));
		Box box = Box.createVerticalBox();
		
		LabelledItemPanel panel = new LabelledItemPanel();
		lafModel = new DefaultComboBoxModel();
		JComboBox cboLookAndFeel = new JComboBox(lafModel);
		panel.addItem("Look and Feel",cboLookAndFeel);
		
		LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
		if (lafs!=null) {
			for (LookAndFeelInfo laf : lafs) {
				lafModel.addElement(laf);
				
			}
		}	
		cboLookAndFeel.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(
			        JList list,
			        Object value,
			        int index,
			        boolean isSelected,
			        boolean cellHasFocus) {
				if (value instanceof LookAndFeelInfo) {
					LookAndFeelInfo laf = (LookAndFeelInfo)value;					
					return super.getListCellRendererComponent(list, laf.getName(), index, isSelected, cellHasFocus);
				}
				else {
					return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				}
			}
		});
		
		
		box.add(panel);
		box.add(Box.createVerticalGlue());
		parent.add(box,BorderLayout.CENTER);
	}

	@Override
	public String getDescription() {
		return "Application look and feel settings";
	}

	@Override
	public String getPanelName() {
		return "Look & Feel";
	}

	@Override
	public void loadSettings(Settings settings) {		
		String lafName = settings.getLookAndFeel(UIManager.getLookAndFeel().getClass().getName());
		for (int i=0;i<lafModel.getSize();i++) {
			LookAndFeelInfo laf = (LookAndFeelInfo) lafModel.getElementAt(i);
			if (laf.getClassName().equals(lafName)) {
				lafModel.setSelectedItem(laf);
				break;
			}
		}
	}

	public void saveSettings(Settings settings) {
		LookAndFeelInfo laf = (LookAndFeelInfo) lafModel.getSelectedItem();		
		settings.setLookAndFeel(laf.getClassName());
	}	
}
