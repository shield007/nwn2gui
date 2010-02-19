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
package org.stanwood.nwn2.gui.options.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import org.stanwood.nwn2.gui.options.AbstractOptionDialog;
import org.stanwood.nwn2.gui.options.NWN2GuiSettings;
import org.stanwood.nwn2.gui.options.Settings;
import org.stanwood.swing.FileChooseWidget;
import org.stanwood.swing.FileListWidget;

public class NWN2OptionsPanel extends AbstractOptionDialog{

	private static final long serialVersionUID = -188846817682510395L;
	
	private FileChooseWidget txtNWN2Dir;
	private FileListWidget iconList;
	private FileListWidget fontList;
	private FileListWidget tlkList;


	public NWN2OptionsPanel() {
		super();
	}
	
	@Override
	protected void createControl(JPanel parent) {	        
        parent.setLayout(new BorderLayout(5,5));

        Box box = Box.createVerticalBox();
        JLabel lblGameDir = new JLabel("Neverwinter Nights 2 Game Directory:");
        box.add(lblGameDir);
        txtNWN2Dir = new FileChooseWidget(JFileChooser.DIRECTORIES_ONLY);        
        box.add(txtNWN2Dir);
        box.add(new JSeparator(SwingConstants.HORIZONTAL));
                
        tlkList = new FileListWidget("TLK Files:", JFileChooser.FILES_ONLY, new FileFilter() {			
			@Override
			public String getDescription() {
				return "TLK String file (*.tlk)";
			}
			
			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".tlk");
			}
		});
        box.add(tlkList);
        
        iconList = new FileListWidget("Root icon directories:", JFileChooser.DIRECTORIES_ONLY, null); 
        box.add(iconList);
        
        fontList = new FileListWidget("Font directories:", JFileChooser.DIRECTORIES_ONLY, null);
        box.add(fontList);
        box.add(Box.createVerticalGlue());
        
        parent.add(box,BorderLayout.CENTER);
    }
	
	@Override
	public void loadSettings(Settings settings) {
		if (settings instanceof NWN2GuiSettings) {
			NWN2GuiSettings guiSettings = (NWN2GuiSettings)settings;
			txtNWN2Dir.setFile(guiSettings.getNWN2Dir(new File(File.separator)));
		}
	}
	
	@Override
	public void saveSettings(Settings settings) {
		if (settings instanceof NWN2GuiSettings) {
			NWN2GuiSettings guiSettings = (NWN2GuiSettings)settings;
			guiSettings.setNWN2Dir(txtNWN2Dir.getFile());
		}
	}

	@Override
	public String getPanelName() {
		return "Content";
	}

	@Override
	public String getDescription() {		
		return "Neverwinter nights 2 game resources";
	}

	
}
