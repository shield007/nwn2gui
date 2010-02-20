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
package org.stanwood.nwn2.gui.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.stanwood.nwn2.gui.model.NWN2GUIObject;
import org.stanwood.nwn2.gui.model.UIButton;
import org.stanwood.nwn2.gui.model.UIFrame;
import org.stanwood.nwn2.gui.model.UIGrid;
import org.stanwood.nwn2.gui.model.UIIcon;
import org.stanwood.nwn2.gui.model.UIListBox;
import org.stanwood.nwn2.gui.model.UIScene;
import org.stanwood.nwn2.gui.model.UIScrollBar;
import org.stanwood.nwn2.gui.model.UIText;

public class UIObjectFactory {
	
	private static Dimension screenDimension;
	
	static
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		screenDimension = toolkit.getScreenSize(); 
	}
	
	
	public static UIObjectView createViewObject(NWN2GUIObject uiObject,UIScene scene) {
		UIObjectView view = null; 
		if (uiObject instanceof UIIcon) {			
			view = new UIIconView((UIIcon)uiObject, scene, screenDimension);
		}
		else if (uiObject instanceof UIFrame) {
			view = new UIFrameView((UIFrame)uiObject, scene, screenDimension);
		}
		else if (uiObject instanceof UIButton) {
			view = new UIButtonView((UIButton)uiObject, scene, screenDimension);
		}
		else if (uiObject instanceof UIGrid ) {
			view = new UIGridView((UIGrid)uiObject, scene, screenDimension);
		}		
		else if (uiObject instanceof UIText ) {
			view =  new UITextView((UIText)uiObject, scene, screenDimension);
		}
		else if (uiObject instanceof UIListBox ) {
			view =  new UIListBoxView((UIListBox)uiObject, scene, screenDimension);
		}
		else if (uiObject instanceof UIScrollBar ) {
			view =  new UIScrollBarView((UIScrollBar)uiObject, scene, screenDimension);
		}
		return view;
	}

}
