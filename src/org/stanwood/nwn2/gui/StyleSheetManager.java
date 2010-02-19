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
		uiObjects = new HashMap<String, UIObject>();
		fonts = new HashMap<String, UIFontFamily>();
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
}
