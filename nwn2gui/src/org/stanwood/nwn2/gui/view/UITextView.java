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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import org.stanwood.nwn2.gui.FontManager;
import org.stanwood.nwn2.gui.StyleSheetManager;
import org.stanwood.nwn2.gui.TLKManager;
import org.stanwood.nwn2.gui.model.UIFont;
import org.stanwood.nwn2.gui.model.UIFontFamily;
import org.stanwood.nwn2.gui.model.UIScene;
import org.stanwood.nwn2.gui.model.UIText;

public class UITextView extends UIObjectView {

	private static final Color DROP_SHADOW_COLOUR = Color.BLACK;
	private static final int DROP_SHADOW_OFFSET = 2;
	private static final Color DEFAULT_TEXT_COLOR = Color.WHITE;
	
	private UIText text;

	public UITextView(UIText text,UIScene scene, Dimension screenDimension) {
		super(text,scene, screenDimension);
		this.text = text;		
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
			if (text.getUpperCase()!=null && text.getUpperCase()) {
				value = value.toUpperCase();
			}
			UIFont uiFont = getUIFont(text.getFontFamily(),text.getStyle());
			if (uiFont!=null) {
				g.setFont(g.getFont().deriveFont(uiFont.getPointSize()));
				Font font = FontManager.getInstance().getFont(uiFont);
				if (font!=null) {
					g.setFont(font);
				}
				else {
					System.out.println("Unable to find font");
				}
			}

			FontMetrics metrics = g.getFontMetrics();
			Rectangle2D bounds = metrics.getStringBounds(value, g);			
			
			String valign = text.getValign();
			if (valign==null) {
				valign="middle";
			}
			if (valign.equals("middle")) {
				y +=(getHeight()/2)-((int)bounds.getCenterY());
			}			
			else if (valign.equals("top")) {
				y+=bounds.getHeight();
			}
			else if (valign.equals("bottom")) {
				y+=getHeight();
			}
			
			String halign = text.getAlign();
			if (halign==null) {
				halign="left";
			}
			if (halign.equals("left")) {
				// Do nothing
			}
			else if (halign.equals("center")) {
				x+=(getWidth()/2)-((int)bounds.getCenterX());
			}
			else if (halign.equals("right")) {
				x+=getWidth()-((int)bounds.getWidth());
			}
					
			if (uiFont!=null) {
				if (uiFont.getDropShadows() != null && uiFont.getDropShadows()) {
					g.setColor(DROP_SHADOW_COLOUR);
					g.drawString(value,x+DROP_SHADOW_OFFSET , y+2);
				}
			}
			g.setColor(DEFAULT_TEXT_COLOR);
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
