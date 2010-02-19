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
