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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stanwood.nwn2.gui.model.NWN2GUIObject;
import org.stanwood.nwn2.gui.model.UIFontFamily;
import org.stanwood.nwn2.gui.model.UIObject;
import org.stanwood.nwn2.gui.parser.GUIParseException;
import org.stanwood.nwn2.gui.parser.GUIStyleSheetParser;

public class StyleSheetManager {

	private final static Log log = LogFactory.getLog(StyleSheetManager.class);
	
	private static StyleSheetManager instance = null;
	private Map<String, UIObject> uiObjects;
	private Map<String, UIFontFamily> fonts;

	private StyleSheetManager() {
		removeAllStyles();
	}

	public static synchronized StyleSheetManager getInstance() {
		if (instance == null) {
			instance = new StyleSheetManager();
		}
		return instance;
	}

	private void parse(File stylesheet) throws GUIParseException, IOException {
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(stylesheet);
			GUIStyleSheetParser parser = new GUIStyleSheetParser(fs);
			parser.parse();			

			for (NWN2GUIObject obj : parser.getUIObjects()) {	
				if (obj instanceof UIObject) {
					uiObjects.put(obj.getName(), (UIObject)obj);
				}
				else if (obj instanceof UIFontFamily) {
					fonts.put(obj.getName(),(UIFontFamily)obj);
				}
			}
		} finally {
			fs.close();
		}

	}
	
	public void loadStyleSheet(File styleSheet) {
		if (!styleSheet.exists()) {
			log.error("Unable to find GUI style sheet: " + styleSheet.getAbsolutePath());
		}
		else {
			try {
				parse(styleSheet);
			} catch (GUIParseException e) {
				log.error(e.getMessage(),e);
			} catch (IOException e) {
				log.error(e.getMessage(),e);
			}
		}
	}

	public UIObject getObjectByName(String name) {
		return uiObjects.get(name);
	}
	
	public UIFontFamily getFontByName(String name) {
		return fonts.get(name);
	}
	
	public void removeAllStyles() {
		uiObjects = new HashMap<String, UIObject>();
		fonts = new HashMap<String, UIFontFamily>();
	}
}
