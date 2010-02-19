package org.stanwood.nwn2.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.stanwood.nwn2.gui.model.UIFont;

public class FontManager {

	private static FontManager fontManager;
	private Map<String,Font>fonts = new HashMap<String,Font>();
	
	private FontManager() {
		
	}
	
	public synchronized static FontManager getInstance() {
		if (fontManager==null) {
			fontManager = new FontManager();
		}
		return fontManager;
	}
	
	public void addFont(File fontFile) throws FontFormatException, IOException {		
		Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);		
		fonts.put(fontFile.getName().toLowerCase(),font);
	}
	
	public Font getFont(UIFont uiFont) {
		String name = uiFont.getFont();
		name = name.substring(name.lastIndexOf('\\')+1).toLowerCase();
		Font baseFont = fonts.get(name);
		Font font = null;
		if (baseFont!=null) {
			font = baseFont.deriveFont(uiFont.getPointSize());
		}
		return font;
	}
}
