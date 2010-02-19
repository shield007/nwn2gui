package org.stanwood.nwn2.gui.icons;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class NWN2IconManager {

	private static NWN2IconManager instance = null;;
	
	private Map<String,String>iconsByName = new HashMap<String,String>();
	
	private Map<String,Image>iconCache = new HashMap<String,Image>();
	
	private NWN2IconManager() {
		
	}
	
	public void addIconDir(File dir) throws FileNotFoundException {
		if (!dir.exists()) {
			throw new FileNotFoundException("Unable to find directory " +dir.getAbsolutePath());
		} else if (!dir.isDirectory()) { 
			throw new IllegalArgumentException("The file " + dir.getAbsolutePath() +" must be a directory");
		}
		
		processIcons(dir,dir);
	}
	
	private void processIcons(File root,File parentDir) {
		if (parentDir.isDirectory()) {
			File[] files = parentDir.listFiles();
			for (int i = 0; i < files.length; i++) {
				processIcons(root,files[i]);
			}
		} else if (parentDir.getName().toLowerCase().endsWith(".tga")) {
			String name = parentDir.getName().toLowerCase();					
			iconsByName.put(name,parentDir.getAbsolutePath());
		}
	}
		
	public String getIconPath(String iconName) throws FileNotFoundException {
		String icon = iconsByName.get(iconName.toLowerCase());
		if (icon==null) {
			throw new FileNotFoundException("Unable to find icon " + iconName);			
		}
		
		return icon; 
	}

	public static synchronized NWN2IconManager getInstance() {
		if (instance == null) {
			instance = new NWN2IconManager();
		}
		return instance;
	}

	public Image getIcon(String name) throws FileNotFoundException, ImageException {
		if (iconCache.get(name)==null) {			
			File iconFile = new File(getIconPath(name));			
			BufferedImage img = TgaLoader.loadImage(iconFile);		
			iconCache.put(name,img);
		}
		return iconCache.get(name);
	}
}
