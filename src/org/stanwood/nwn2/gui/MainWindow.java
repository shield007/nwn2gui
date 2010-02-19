package org.stanwood.nwn2.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.util.WindowUtils;
import org.stanwood.nwn2.gui.icons.NWN2IconManager;
import org.stanwood.nwn2.gui.logging.LogSetupHelper;
import org.stanwood.nwn2.gui.options.NWN2GuiSettings;
import org.stanwood.nwn2.gui.options.OptionsDialog;
import org.stanwood.nwn2.gui.parser.NWN2GUIParser;
import org.stanwood.swing.icons.IconManager;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 6014789774789616365L;
	private final static Log log = LogFactory.getLog(MainWindow.class);
	
	private File nwn2Dir;
	private JButton cmdDisplay;
	private JList guiFileList;
	private JButton cmdRemove;
	private DefaultListModel guiFileListModel;
	private NWN2GuiSettings settings = NWN2GuiSettings.getInstance();

	public MainWindow() {
		setTitle("NWN2GUI");
		
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
			public void windowClosing(WindowEvent evt)
			{
				closeWindow();
			}
		});

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		loadSettings();		
		
				
		
		indexIcons();
		loadStyles();
		setupFonts();
		setupTLKParsers();		
	}

	private void setupFonts() {
		File fontDir = new File(nwn2Dir,"UI/default/fonts");
		if (fontDir.exists()) {
			File fontFiles[] = fontDir.listFiles(new FilenameFilter() {			
				@Override
				public boolean accept(File dir, String name) {
					return (name.toLowerCase().endsWith(".ttf"));				
				}
			});
			
			if (fontFiles!=null) {
				for (File fontFile : fontFiles) {
					try {
						FontManager.getInstance().addFont(fontFile);
					} catch (Exception e) {
						log.error("Unable to add font file " + fontFile.getAbsolutePath()+": " + e.getMessage());
					}
				}
			}
		}
		else {
			log.error("Unable to find font directory:" + fontDir);
		}
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

	private void setupTLKParsers() {
		try {
			File tlkFile = new File(nwn2Dir,"dialog.TLK");
			if (tlkFile.exists()) {
				TLKManager.getInstance().addTLKFile(tlkFile);
			}
			else {
				log.error("Unable to find tlk file: " + tlkFile);
			}
		} catch (IOException e) {
			log.error("Unable to add tlk file: " + e.getMessage(),e);
		}
	}

	private void loadStyles() {
		File styleSheets[] = new File[]{
			new File(nwn2Dir,"UI"+File.separator+"default"+File.separator+"stylesheet.xml"),
			new File(nwn2Dir,"UI"+File.separator+"default"+File.separator+"fontfamily.xml"),
		};
		
		for (File styleSheet : styleSheets) {
			StyleSheetManager.getInstance().loadStyleSheet(styleSheet);
		}
	}

	

	private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        JMenuItem newAction = new JMenuItem("Add GUI file");
        newAction.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewGUIFile();
			}
		});
        fileMenu.add(newAction);
        
        JMenu prefencesMenu = new JMenu("Prefences");
        prefencesMenu.setMnemonic('P');
        
        JMenu lookAndFeelMenu = new JMenu("Look and feel");
        LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
        if (lafs!=null) {
        	String currentLaf = UIManager.getLookAndFeel().getName();
	        for (final LookAndFeelInfo laf : lafs) {
	        	JRadioButtonMenuItem item = new JRadioButtonMenuItem(laf.getName());
	        	item.addActionListener(new ActionListener() {					
					@Override
					public void actionPerformed(ActionEvent e) {						
						changeLookAndFeel(laf.getClassName()); 
					}

					
				});
	        	lookAndFeelMenu.add(item);	        	
	        	item.setSelected(laf.getName().equals(currentLaf)); 	
	        }
        }
        prefencesMenu.add(lookAndFeelMenu);
        
        JMenuItem pref = new JMenuItem("Options");
        pref.setMnemonic('O');
        pref.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showOptionsDialog();
			}
        	
        });
        prefencesMenu.add(pref);
        
        menuBar.add(fileMenu);
        menuBar.add(prefencesMenu);
        
        setJMenuBar(menuBar);
	}
	
	protected void showOptionsDialog() {		
		OptionsDialog options = new OptionsDialog(this);		
		options.setVisible(true);
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
		
		buttonPanel2.setLayout(new GridLayout(3, 1,5,5));
				
		JButton cmdAdd = new JButton(IconManager.getInstance().getIcon(IconManager.ICON_LIST_ADD));
		cmdAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewGUIFile();
			}
					
		});
		
		buttonPanel2.add(cmdAdd);
		cmdRemove = new JButton(IconManager.getInstance().getIcon(IconManager.ICON_LIST_REMOVE));
		cmdRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiFileListModel.remove(guiFileList.getSelectedIndex());
				updateEnabledState();
				saveChanges();
			}			
		});
		
		buttonPanel2.add(cmdRemove);
		cmdDisplay = new JButton(IconManager.getInstance().getIcon(IconManager.ICON_LIST_VIEW));
		cmdDisplay.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				displayGUIFile();
			}
		});
		buttonPanel2.add(cmdDisplay);
		
		panel.add(pane,BorderLayout.CENTER);
		panel1.add(panel,BorderLayout.CENTER);
	}

	protected void saveChanges() {
		List<File>files = new ArrayList<File>();
		for (int i=0;i<guiFileListModel.getSize();i++) {
			files.add((File) guiFileListModel.getElementAt(i));
		}
		settings.setGUIFileList(files);
		settings.setNWN2Dir(nwn2Dir);
		settings.save();
	}
	
	private void loadSettings() {
		guiFileListModel.clear();
		for (File file : settings.getGUIFileList()) {
			guiFileListModel.addElement(file);
		}
		nwn2Dir = settings.getNWN2Dir(new File("C:"+File.separator+"Games"+File.separator+"Neverwinter Nights 2"));
	}

	private void addNewGUIFile() {
		File dir = settings.getGUIXMLBrowseDirLastDir(new File(nwn2Dir,"UI"+File.separator+"default"));				
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
	
	private void indexIcons() {
		try {
			log.info("Indexing Neverwinter Nights 2 icons....");
			NWN2IconManager iconManager = NWN2IconManager.getInstance();
			iconManager.addIconDir(new File(nwn2Dir,"UI"+File.separator+"default"+File.separator+"images"));			
		}
		catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	
	private void updateEnabledState() {
		if (guiFileList.getSelectedIndex()!=-1) {
			cmdDisplay.setEnabled(true);
			cmdRemove.setEnabled(true);
		}
		else {
			cmdDisplay.setEnabled(false);
			cmdRemove.setEnabled(false);
		}
	}
	
	private void displayGUIFile() {
		File guiFile = (File) guiFileListModel.get(guiFileList.getSelectedIndex());
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
