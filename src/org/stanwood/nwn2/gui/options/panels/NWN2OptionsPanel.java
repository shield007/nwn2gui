package org.stanwood.nwn2.gui.options.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;

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
import org.stanwood.swing.layouts.VerticalFlowLayout;

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

        JPanel panel = new JPanel();
//        panel.setLayout(new VerticalFlowLayout(5,VerticalFlowLayout.LEFT));
        panel.setLayout(new GridLayout(10, 1, 5, 5));
        JLabel lblGameDir = new JLabel("Neverwinter Nights 2 Game Directory:");
        panel.add(lblGameDir);
        txtNWN2Dir = new FileChooseWidget(JFileChooser.DIRECTORIES_ONLY);
        panel.add(txtNWN2Dir);
        panel.add(new JSeparator(SwingConstants.HORIZONTAL));
                
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
        panel.add(tlkList);
        
        iconList = new FileListWidget("Root icon directories:", JFileChooser.DIRECTORIES_ONLY, null); 
        panel.add(iconList);
        
        fontList = new FileListWidget("Font directories:", JFileChooser.DIRECTORIES_ONLY, null);
        panel.add(fontList);
        
        parent.add(panel,BorderLayout.CENTER);
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
