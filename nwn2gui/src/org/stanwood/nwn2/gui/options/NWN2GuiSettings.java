package org.stanwood.nwn2.gui.options;

import java.io.File;
import java.util.List;

public class NWN2GuiSettings extends Settings {

	private final static String PROP_GUI_FILE_LIST  ="PROP_GUI_FILE_LIST";
	private static final String PROP_GUI_LAST_BROWSE_DIR = "PROP_GUI_LAST_BROWSE_DIR";
	private static final String PROP_LOOK_AND_FEEL = "PROP_LOOK_AND_FEEL";
	private static final String PROP_NWN2_DIR = "PROP_NWN2_DIR";
	
	private static NWN2GuiSettings instance = null;
	
	
	
	protected NWN2GuiSettings() {
		super();
	}
	
	protected File getPropertiesPath() {
		File f = new File(System.getProperty("user.home"));
		f = new File(f,".nwn2gui");
		f = new File(f,"settings");
		return f;
	}
	
	public static synchronized NWN2GuiSettings getInstance() {
		if (instance==null) {
			instance = new NWN2GuiSettings();
		}
		return instance;
	}
	
	public void setGUIFileList(List<File> files) {
		setFileList(PROP_GUI_FILE_LIST, files);
	}
	
	public List<File> getGUIFileList() {
		return getFileList(PROP_GUI_FILE_LIST);
	}

	public File getGUIXMLBrowseDirLastDir(File defaultValue) {
		String value = getString(PROP_GUI_LAST_BROWSE_DIR, defaultValue.getAbsolutePath());		
		return new File(value);
	}
	
	public void setGUIXMLBrowseDirLastDir(File dir) { 
		setString(PROP_GUI_LAST_BROWSE_DIR, dir.getAbsolutePath());
	}

	public void setLookAndFeel(String className) {
		setString(PROP_LOOK_AND_FEEL, className);
	}
	
	public String getLookAndFeel(String defaultValue) {
		return getString(PROP_LOOK_AND_FEEL,defaultValue);
	}

	public File getNWN2Dir(File defaultValue) {
		return new File(getString(PROP_NWN2_DIR,defaultValue.getAbsolutePath()));
	}
	
	public void setNWN2Dir(File nwn2Dir) {
		setString(PROP_NWN2_DIR, nwn2Dir.getAbsolutePath());
	}

	public void propertiesChanged() {
		// TODO Auto-generated method stub
		
	}

	public boolean getBoolean(String string, boolean defaultValue) {
		//TODO implement this
		return defaultValue;
	}
	
}
