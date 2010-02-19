package org.stanwood.swing.icons;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class IconManager {

	public final static String ICON_LIST_ADD = "list-add.png";
	public final static String ICON_LIST_REMOVE = "list-remove.png";
	public final static String ICON_LIST_VIEW = "list-view.png";
	public final static String ICON_DIALOG_HEADER_CORNER = "dialog-header-corner.png";
	public final static String ICON_PREFERENCES_SYSTEM = "preferences-system.png";
	
	private static IconManager instance = null;
	
	private IconManager() {
		
	}
	
	public synchronized static IconManager getInstance() {
		if (instance==null) {
			instance = new IconManager();
		}
		return instance;
	}
	
	public Icon getIcon(String name) {
		URL url = getClass().getResource(name);
		return new ImageIcon(url);
	}
}
