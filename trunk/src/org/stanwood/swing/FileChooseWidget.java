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
