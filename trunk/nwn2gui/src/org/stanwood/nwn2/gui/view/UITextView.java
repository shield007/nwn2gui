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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import org.stanwood.nwn2.gui.StyleSheetManager;
import org.stanwood.nwn2.gui.TLKManager;
import org.stanwood.nwn2.gui.model.UIFont;
import org.stanwood.nwn2.gui.model.UIFontFamily;
import org.stanwood.nwn2.gui.model.UIScene;
import org.stanwood.nwn2.gui.model.UIText;

public class UITextView extends UIObjectView {

	private UIText text;

	public UITextView(UIText text,UIScene scene, Dimension screenDimension) {
		super(scene, screenDimension);
		this.text = text;
		if (text.getX()!=null && text.getY()!=null) {
			setX(text.getX().getValue(getScreenDimension(), getScene()));
			setY(text.getY().getValue(getScreenDimension(), getScene()));
		}
	}


	@Override
	public void paintUIObject(Graphics g) {
		int x = getX();
		int y = getY();
		String value = text.getText();		
		if (value==null && text.getStrRef()!=null) {
			value = TLKManager.getInstance().getText(text.getStrRef());
		}
		if (value!=null) {
//			NWN2GUIObject parent = text.getParent();	
			
			
			UIFont uiFont = getUIFont(text.getFontFamily(),text.getStyle());
			if (uiFont!=null) {
				g.setFont(g.getFont().deriveFont(uiFont.getPointSize()));
//				Font font = FontManager.getInstance().getFont(uiFont);
//				if (font!=null) {
//					g.setFont(font);
//				}			
			}
			g.setColor(Color.GREEN);
			g.drawString(value,x , y);
		}
	}
	

	private UIFont getUIFont(String fontFamily,String style) {
		UIFontFamily ff = StyleSheetManager.getInstance().getFontByName(fontFamily);
		if (ff==null) {
			return null;
		}
		UIFont font = ff.getFont(style);			
		return font;
	}

}
