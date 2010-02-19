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

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stanwood.nwn2.gui.icons.NWN2IconManager;
import org.stanwood.nwn2.gui.options.NWN2GuiSettings;

public class NWN2ResourceHandler {

	private final static Log log = LogFactory.getLog(NWN2ResourceHandler.class);
		
	public static void indexResources() {
		File nwn2Dir = NWN2GuiSettings.getInstance().getNWN2Dir(NWN2GuiSettings.DEFAULT_NWN2_DIR);
		loadStyles(nwn2Dir);
		indexIcons(nwn2Dir);
		setupFonts(nwn2Dir);
		setupTLKParsers(nwn2Dir);
	}
	
	private static void setupFonts(File nwn2Dir) {
		FontManager.getInstance().removeAllFonts();
		List<File>fontDirs= new ArrayList<File>();
		fontDirs.add(new File(nwn2Dir,"UI"+File.separator+"default"+File.separator+"fonts"));
		fontDirs.addAll(NWN2GuiSettings.getInstance().getFileList(NWN2GuiSettings.PROP_EXTRA_FONT_DIRS));
		log.info("Indexing Neverwinter Nights 2 fonts....");
		
		for (File fontDir : fontDirs) {
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
							if (log.isDebugEnabled()) {
								log.debug("Adding font file: " + fontFile);
							}
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
	}
	
	private static void setupTLKParsers(File nwn2Dir) {
		TLKManager.getInstance().removeAllTlkFiles();
		List<File>tlkFiles= new ArrayList<File>();
		tlkFiles.add(new File(nwn2Dir,"dialog.TLK"));
		tlkFiles.addAll(NWN2GuiSettings.getInstance().getFileList(NWN2GuiSettings.PROP_EXTRA_TLKS));
		log.info("Indexing Neverwinter Nights 2 tlk files....");
		
		for (File tlkFile : tlkFiles) {
			try {
				if (log.isDebugEnabled()) {
					log.debug("Indexing tlk file: " + tlkFile.getAbsolutePath());
				}
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
	}

	private static void loadStyles(File nwn2Dir) {
		StyleSheetManager.getInstance().removeAllStyles();
		File styleSheets[] = new File[]{
			new File(nwn2Dir,"UI"+File.separator+"default"+File.separator+"stylesheet.xml"),
			new File(nwn2Dir,"UI"+File.separator+"default"+File.separator+"fontfamily.xml"),
		};
		
		for (File styleSheet : styleSheets) {
			StyleSheetManager.getInstance().loadStyleSheet(styleSheet);
		}
	}

	private static void indexIcons(File nwn2Dir) {
		NWN2IconManager.getInstance().removeAllIcons();
		List<File>rootIconDirs= new ArrayList<File>();
		rootIconDirs.add(new File(nwn2Dir,"UI"+File.separator+"default"+File.separator+"images"));
		rootIconDirs.addAll(NWN2GuiSettings.getInstance().getFileList(NWN2GuiSettings.PROP_EXTRA_ICON_DIRS));
		log.info("Indexing Neverwinter Nights 2 icons....");
		try {
			for (File iconDir : rootIconDirs) {
				if (log.isDebugEnabled()) {
					log.debug("Indexing icon directory: " + iconDir);
				}
				NWN2IconManager iconManager = NWN2IconManager.getInstance();
				iconManager.addIconDir(iconDir);			
			}
		}
		catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	
}
