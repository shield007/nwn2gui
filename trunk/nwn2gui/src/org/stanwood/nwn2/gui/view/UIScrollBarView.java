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
import java.awt.Graphics;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stanwood.nwn2.gui.StyleSheetManager;
import org.stanwood.nwn2.gui.model.NWN2GUIObject;
import org.stanwood.nwn2.gui.model.UIButton;
import org.stanwood.nwn2.gui.model.UIObject;
import org.stanwood.nwn2.gui.model.UIScene;
import org.stanwood.nwn2.gui.model.UIScrollBar;

/**
 * Used to render the UIList box
 */
public class UIScrollBarView extends UIObjectView {
	
	private final static Log log = LogFactory.getLog(UIScrollBarView.class);	
	private UIButtonView back;
	private UIButtonView slider;
	private UIButtonView down;
	private UIButtonView up;

	public UIScrollBarView(UIScrollBar scrollBar,UIScene scene, Dimension screenDimension) {
		super(scrollBar,scene, screenDimension);
		createView(scrollBar);
	}
	
	private void createView(UIScrollBar scrollBar) {
		UIScrollBar newScrollBar = scrollBar;
		if (scrollBar.getStyle()!=null) {
			UIObject styleListBox = StyleSheetManager.getInstance().getObjectByName(scrollBar.getStyle());
			if (styleListBox!=null) {
				try {
					newScrollBar = (UIScrollBar) scrollBar.clone();					
					newScrollBar.applyStyle(styleListBox);
				} catch (CloneNotSupportedException e) {
					log.error("Unable to clone button, " + e.getMessage(),e);
				}				
			}
			else {
				log.error("Unable to find the style: " +scrollBar.getStyle());
			}				
		}			
				
		up = getChildWithState(newScrollBar,"up");		
		down = getChildWithState(newScrollBar,"down");
		slider = getChildWithState(newScrollBar,"slider");		
		back = getChildWithState(newScrollBar,"back");
	}
	
	@Override
	public void positionChanged() {
		childSetX(up,this.getX());
		up.setY(this.getY());
		
		childSetX(down,this.getX());		
		down.setY(this.getY()+this.getHeight()-down.getHeight());
		
		childSetX(slider,this.getX());		
		slider.setY(this.getY()+up.getHeight());
		
		childSetX(back,this.getX());		
		back.setY(this.getY()+up.getHeight());
		back.setHeight(this.getHeight()-up.getHeight()-down.getHeight());
	}
	
	private void childSetX(UIButtonView child,int value) {		
		value+=up.getX();		
		up.setX(value);
	}

	private UIButtonView getChildWithState(UIScrollBar scrollBar,String state) {	
		for (NWN2GUIObject obj :   scrollBar.getChildren()) {
			if (obj instanceof UIButton) {
				UIButton button = (UIButton) obj;
				if (button.getName()!=null && button.getName().equalsIgnoreCase(state)) {
					return (UIButtonView) UIObjectFactory.createViewObject(button, getScene());
				}
			}
		}
		return null;
	}

	@Override
	public void paintUIObject(Graphics g) {			
		System.out.println(up);
		up.paintUIObject(g);		
		down.paintUIObject(g);
		back.paintUIObject(g);
		slider.paintUIObject(g);
	}

	
}
