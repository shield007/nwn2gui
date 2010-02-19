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
package org.stanwood.swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import org.stanwood.swing.icons.IconManager;

public class FileListWidget extends JPanel {

	private String title;	
	private JList lstFiles;
	private JButton cmdRemove;
	private int mode;
	private FileFilter fileFilter;
	private FileListModel fileListModel;

	public FileListWidget(String title,int mode,FileFilter fileFilter) {
		this.title = title;
		this.mode = mode;
		this.fileFilter =fileFilter;
		createControl();		
	}

	private void createControl() {
		setLayout(new BorderLayout(5,5));
		add(new JLabel(title),BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5,5));					
		add(panel,BorderLayout.CENTER);
		
		fileListModel = new FileListModel();
		lstFiles = new JList(fileListModel);	
		lstFiles.addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent e) {								
				updateEnabledState();				
			}
		});
		
		JScrollPane pane = new JScrollPane(lstFiles);
		JPanel buttonPanel1 = new JPanel();
		buttonPanel1.setLayout(new BorderLayout());
		panel.add(buttonPanel1,BorderLayout.EAST);
		
		JPanel buttonPanel2 = new JPanel();
		buttonPanel1.add(buttonPanel2,BorderLayout.NORTH);			
		
		buttonPanel2.setLayout(new GridLayout(3, 1,5,5));
				
		JButton cmdAdd = new JButton(IconManager.getInstance().getIcon(IconManager.ICON_LIST_ADD));
		cmdAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addPressed();
			}
					
		});
		
		buttonPanel2.add(cmdAdd);
		cmdRemove = new JButton(IconManager.getInstance().getIcon(IconManager.ICON_LIST_REMOVE));
		cmdRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileListModel.removeFile(lstFiles.getSelectedIndex());				
				updateEnabledState();				
			}			
		});
		
		buttonPanel2.add(cmdRemove);
		
		panel.add(pane,BorderLayout.CENTER);
	}

	protected void addPressed() {					
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(mode);
		if (fileFilter!=null) {
			fc.setFileFilter(fileFilter);
		}
		fc.showOpenDialog(FileListWidget.this);
		File selFile = fc.getSelectedFile();
		addFile(selFile);
		lstFiles.setSelectedIndex(fileListModel.getSize()-1);
		if (selFile!=null) {			
			updateEnabledState();			
		}
	}

	protected void updateEnabledState() {
		if (lstFiles.getSelectedIndex()!=-1) {			
			cmdRemove.setEnabled(true);
		}
		else {			
			cmdRemove.setEnabled(false);			
		}		
	}
	
	public List<File> getFiles() {
		return fileListModel.getFiles();
	}
	
	public void addFile(File file) {
		fileListModel.addFile(file);		
	}
}
