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
package org.stanwood.nwn2.gui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.util.WindowUtils;
import org.stanwood.nwn2.gui.logging.LogSetupHelper;
import org.stanwood.nwn2.gui.options.NWN2GuiSettings;
import org.stanwood.nwn2.gui.options.OptionsDialog;
import org.stanwood.nwn2.gui.parser.NWN2GUIParser;
import org.stanwood.swing.AboutDialog;
import org.stanwood.swing.Author;
import org.stanwood.swing.icons.IconManager;

public class MainWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final String APP_TITLE = "NWN2GUI";
	/**
	 * 
	 */
	private static final String APP_VERSION = "Version 0.1 (Beta 1)";
	private static final long serialVersionUID = 6014789774789616365L;
	private final static Log log = LogFactory.getLog(MainWindow.class);
		
	private JButton cmdDisplay;
	private JList guiFileList;
	private JButton cmdRemove;
	private DefaultListModel guiFileListModel;
	private NWN2GuiSettings settings = NWN2GuiSettings.getInstance();
	private AbstractButton cmdEdit;

	public MainWindow() {
		setTitle(APP_TITLE);
		
		JPanel panel = new JPanel();			
		Border padding = BorderFactory.createEmptyBorder(5,5,5,5);
		panel.setBorder(padding);

		createMenuBar();
		createComponents(panel);
		updateEnabledState();
		getContentPane().add(panel);
		setSize(500,300);				
		setLocation(WindowUtils.getPointForCentering(this));		
		
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent evt)
			{
				closeWindow();
			}
		});

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		loadSettings();
		NWN2ResourceHandler.indexResources();		
	}	
	
	protected void closeWindow() {
		saveChanges();
		try {
			TLKManager.getInstance().close();
		} catch (IOException e) {
			log.error("Unable to close TLK files",e);
		}
		System.exit(0);
	}

	private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu mnuFile = new JMenu("File");
        mnuFile.setMnemonic('F');
        JMenuItem newAction = new JMenuItem("Add GUI file");
        newAction.setMnemonic('A');
        newAction.setIcon(IconManager.getInstance().getIcon(IconManager.SIZE_16, IconManager.ICON_LIST_ADD));
        newAction.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewGUIFile();
			}
		});
        mnuFile.add(newAction);
                
        JMenuItem exitAction = new JMenuItem("Exit");
        exitAction.setIcon(IconManager.getInstance().getIcon(IconManager.SIZE_16, IconManager.ICON_APPLICATION_EXIT));
        exitAction.setMnemonic('x');
        exitAction.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				closeWindow();
			}
		});
        mnuFile.add(exitAction);
        
        JMenu mnuPrefences = new JMenu("Prefences");
        mnuPrefences.setMnemonic('P');     
        
        JMenuItem pref = new JMenuItem("Options");
        pref.setMnemonic('O');
        pref.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showOptionsDialog();
			}
        	
        });
        mnuPrefences.add(pref);
        
        
        JMenu mnuHelp = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        about.setMnemonic('A');
        about.setIcon(IconManager.getInstance().getIcon(IconManager.SIZE_16, IconManager.ICON_HELP_ABOUT));
        about.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				  AboutDialog ad = new AboutDialog (MainWindow.this,
				          APP_TITLE,
				          APP_VERSION);

				  ad.setApplicationWebLink("http://code.google.com/p/nwn2gui/");
				  ad.setMessage("A NWN2 GUI XML viewer.\n\n (C) 2010, The NWN2 GUI developers");
				  ad.addAuthor(new Author("John-Paul Stanford","dev@stanwood.org.uk","Lead developer and project creator"));
				  ad.init();
				  ad.setVisible(true);
				
			}
		});
        mnuHelp.add(about);
        
        menuBar.add(mnuFile);
        menuBar.add(mnuPrefences);
        menuBar.add(mnuHelp);
        
        setJMenuBar(menuBar);
	}
	
	protected void showOptionsDialog() {		
		OptionsDialog options = new OptionsDialog(this);		
		options.setVisible(true);
		if (options.hasSettingsChanged()) {
			NWN2ResourceHandler.indexResources();
		}
	}	
	
	private void createComponents(JPanel panel1) {
		BorderLayout layout = new BorderLayout(5,5);		
		panel1.setLayout(layout);
		JLabel lblList = new JLabel("Neverwinter Nights 2 GUI XML files:");
		panel1.add(lblList,BorderLayout.NORTH);
		
		JPanel panel = new JPanel();		
		panel.setLayout(new BorderLayout(5,5));
		
		guiFileListModel = new DefaultListModel();				
		guiFileList = new JList(guiFileListModel);	
		guiFileList.addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent e) {								
				updateEnabledState();
			}
		});
		
		JScrollPane pane = new JScrollPane(guiFileList);
				
		JPanel buttonPanel1 = new JPanel();
		buttonPanel1.setLayout(new BorderLayout());
		panel.add(buttonPanel1,BorderLayout.EAST);
		
		JPanel buttonPanel2 = new JPanel();
		buttonPanel1.add(buttonPanel2,BorderLayout.NORTH);
		
		buttonPanel2.setLayout(new GridLayout(4, 1,5,5));
				
		JButton cmdAdd = new JButton(IconManager.getInstance().getIcon(IconManager.SIZE_22,IconManager.ICON_LIST_ADD));
		cmdAdd.setToolTipText("Add a XML GUI file to the list");
		cmdAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewGUIFile();
			}
					
		});
		
		buttonPanel2.add(cmdAdd);
		cmdRemove = new JButton(IconManager.getInstance().getIcon(IconManager.SIZE_22,IconManager.ICON_LIST_REMOVE));
		cmdRemove.setToolTipText("Remove the selected XML GUI file from the list");
		cmdRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiFileListModel.remove(guiFileList.getSelectedIndex());
				updateEnabledState();
				saveChanges();
			}			
		});
		
		buttonPanel2.add(cmdRemove);
		cmdDisplay = new JButton(IconManager.getInstance().getIcon(IconManager.SIZE_22,IconManager.ICON_LIST_VIEW));
		cmdDisplay.setToolTipText("Render the selected XML GUI file");
		cmdDisplay.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				displayGUIFile();
			}
		});
		buttonPanel2.add(cmdDisplay);
		
		cmdEdit = new JButton(IconManager.getInstance().getIcon(IconManager.SIZE_22,IconManager.ICON_ACCESSORIES_TEXT_EDITOR));
		cmdEdit.setToolTipText("Open the selected XML GUI file in a editor");
		cmdEdit.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				File guiFile = getSelectedFile();
				try {
					Desktop.getDesktop().edit(guiFile);
				} catch (IOException e1) {
					log.error(e1.getMessage(),e1);
				}
			}
		});
		buttonPanel2.add(cmdEdit);
		
		panel.add(pane,BorderLayout.CENTER);
		panel1.add(panel,BorderLayout.CENTER);
	}

	protected void saveChanges() {
		List<File>files = new ArrayList<File>();
		for (int i=0;i<guiFileListModel.getSize();i++) {
			files.add((File) guiFileListModel.getElementAt(i));
		}
		settings.setGUIFileList(files);		
		settings.save();
	}
	
	private void loadSettings() {
		guiFileListModel.clear();
		for (File file : settings.getGUIFileList()) {
			guiFileListModel.addElement(file);
		}		
	}

	private void addNewGUIFile() {
		File dir = settings.getGUIXMLBrowseDirLastDir(new File(settings.getNWN2Dir(NWN2GuiSettings.DEFAULT_NWN2_DIR),"UI"+File.separator+"default"));				
		JFileChooser fc = new JFileChooser(dir);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(new FileFilter() {			
			@Override
			public String getDescription() {
				return "NWN2 GUI (*.xml)";
			}
			
			@Override
			public boolean accept(File f) {						
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml");
			}
		});
		fc.showOpenDialog(MainWindow.this);
		File selFile = fc.getSelectedFile(); 		
		if (selFile!=null) {
			guiFileListModel.addElement(selFile);
			guiFileList.setSelectedIndex(guiFileListModel.getSize()-1);
			settings.setGUIXMLBrowseDirLastDir(selFile.getParentFile());
			updateEnabledState();
			saveChanges();
		}
	}
		
	private void updateEnabledState() {
		if (guiFileList.getSelectedIndex()!=-1) {
			cmdDisplay.setEnabled(true);
			cmdRemove.setEnabled(true);
			cmdEdit.setEnabled(true);
		}
		else {
			cmdDisplay.setEnabled(false);
			cmdRemove.setEnabled(false);
			cmdEdit.setEnabled(false);
		}
	}
	
	private void displayGUIFile() {
		File guiFile = getSelectedFile();
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(guiFile);
			NWN2GUIParser parser = new NWN2GUIParser(fs);
			parser.parse();

			GUIWindow window = new GUIWindow("GUI: "+guiFile.getName(),parser.getGUI());
			window.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private File getSelectedFile() {
		File guiFile = (File) guiFileListModel.get(guiFileList.getSelectedIndex());
		return guiFile;
	}
	
	public static void changeLookAndFeel(final String name) {
		if (log.isDebugEnabled()) {
			log.debug("Changing to look and feel: " + name);
		}
		if (name != null) {
			Runnable runnable = new Runnable() {
				public void run() {
					try {
						UIManager.setLookAndFeel(name);
						NWN2GuiSettings.getInstance().setLookAndFeel(name);
					} catch (Exception e1) {
						log.error(e1.getMessage());
					}
				}
			};
			SwingUtilities.invokeLater(runnable);
		}
	}

	public static void main(String[] args) {		
		LogSetupHelper.initLogingInternalConfigFile("info.log4j.properties");
		
		changeLookAndFeel(NWN2GuiSettings.getInstance().getLookAndFeel(UIManager.getLookAndFeel().getClass().getName()));
		
		MainWindow mainWindow = new MainWindow();
		mainWindow.setVisible(true);
	}
}
