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

import java.io.File;
import java.util.List;

import org.stanwood.swing.perferences.Settings;

public class NWN2GuiSettings extends Settings {

	
	public static final File DEFAULT_NWN2_DIR = new File("C:"+File.separator+"Games"+File.separator+"Neverwinter Nights 2");
	private final static String PROP_GUI_FILE_LIST  ="PROP_GUI_FILE_LIST";
	private static final String PROP_GUI_LAST_BROWSE_DIR = "PROP_GUI_LAST_BROWSE_DIR";
	private static final String PROP_LOOK_AND_FEEL = "PROP_LOOK_AND_FEEL";
	private static final String PROP_NWN2_DIR = "PROP_NWN2_DIR";	
	public static final String PROP_EXTRA_TLKS = "PROP_EXTRA_TLKS";
	public static final String PROP_EXTRA_FONT_DIRS = "PROP_EXTRA_FONT_DIRS";
	public static final String PROP_EXTRA_ICON_DIRS = "PROP_EXTRA_ICON_DIRS";
	
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

	public boolean getBoolean(String key, boolean defaultValue) {
		return Boolean.parseBoolean(getString(key, String.valueOf(defaultValue)));		
	}
	
	public void setBoolean(String key, boolean value) {
		setString(key,String.valueOf(value));
	}
	
	public void removeAllTlkFiles() {
		// TODO Auto-generated method stub
		
	}


	
}
