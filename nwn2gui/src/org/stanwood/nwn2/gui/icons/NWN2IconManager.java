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
package org.stanwood.nwn2.gui.icons;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class NWN2IconManager {

	private static NWN2IconManager instance = null;;
	
	private Map<String,String>iconsByName;	
	private Map<String,Image>iconCache;
	
	private NWN2IconManager() {
		removeAllIcons();		
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

	
	public void removeAllIcons() {
		iconsByName = new HashMap<String,String>();
		iconCache = new HashMap<String,Image>();
	}
}
