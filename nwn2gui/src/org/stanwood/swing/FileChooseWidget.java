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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class FileChooseWidget extends JPanel {

	private JTextField txtFile;
	private int mode;
	private FileFilter fileFilter;

	public FileChooseWidget() {
		this(JFileChooser.FILES_AND_DIRECTORIES);
	}
	
	public FileChooseWidget(int mode) {
		this(null,mode);
	}
	
	public FileChooseWidget(FileFilter fileFilter,int mode) {
		this.mode = mode;
		this.fileFilter = fileFilter;
		
		createComponent();
	}

	private void createComponent() {
		setLayout(new BorderLayout(5,5));
		txtFile = new JTextField();
		JButton cmdBrowse = new JButton("Browse...");
		cmdBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(mode);
				if (fileFilter!=null) {
					fc.setFileFilter(fileFilter);
				}
				fc.showOpenDialog(FileChooseWidget.this);
				File selFile = fc.getSelectedFile(); 								
				if (selFile!=null) {
					txtFile.setText(selFile.getAbsolutePath());
				}						
			}			
		});
		add(txtFile,BorderLayout.CENTER);
		add(cmdBrowse,BorderLayout.EAST);
	}

	public void setFile(File file) {
		txtFile.setText(file.getAbsolutePath());
	}

	public File getFile() {
		return new File(txtFile.getText());
	}
}
