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
package org.stanwood.swing.icons;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IconManager {

	private final static Log log = LogFactory.getLog(IconManager.class);
	
	public final static String ICON_APPLICATION_EXIT = "application-exit.png";
	public final static String ICON_DIALOG_CLOSE = "dialog-close.png";
	public final static String ICON_HELP_ABOUT = "help-about.png";
	public final static String ICON_LIST_ADD = "list-add.png";
	public final static String ICON_LIST_REMOVE = "list-remove.png";
	public final static String ICON_LIST_VIEW = "list-view.png";
	public final static String ICON_DIALOG_HEADER_CORNER = "dialog-header-corner.png";
	public final static String ICON_PREFERENCES_SYSTEM = "preferences-system.png";
	public final static String ICON_ACCESSORIES_TEXT_EDITOR = "accessories-text-editor.png";
	public final static String ICON_DIALOG_CANCEL = "dialog-cancel.png";
	public final static String ICON_DIALOG_OK_APPLY = "dialog-ok-apply.png";
	public final static String ICON_DIALOG_OK = "dialog-ok.png";
	public final static String ICON_NWN2GUI = "nwn2gui.png";
	
	public final static String SIZE_16 = "i16";
	public final static String SIZE_22 = "i22";	
	public final static String SIZE_48 = "i48";
	
	
	private static IconManager instance = null;
	
	private IconManager() {
		
	}
	
	public synchronized static IconManager getInstance() {
		if (instance==null) {
			instance = new IconManager();
		}
		return instance;
	}
	
	public Icon getIcon(String size,String name) {
		URL url = IconManager.class.getResource(size+"/"+name);
		if (url==null) {
			log.error("Unable to find icon " + name+" in size " + size);
			return null;
		}
		return new ImageIcon(url);
	}
}
