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

import org.stanwood.nwn2.gui.model.NWN2GUIObject;
import org.stanwood.nwn2.gui.model.UIListBox;
import org.stanwood.nwn2.gui.model.UIScene;
import org.stanwood.nwn2.gui.model.UIScrollBar;

/**
 * Used to render the UIList box
 */
public class UIListBoxView extends UIObjectView {
		
	public UIListBoxView(UIListBox listBox,UIScene scene, Dimension screenDimension) {
		super(listBox,scene, screenDimension);		
		createView(listBox);
		positionChanged();
	}
	
	private void createView(UIListBox listBox) {
		for (NWN2GUIObject child : listBox.getChildren()) {
			if (child instanceof UIScrollBar) {
				UIScrollBarView viewChild = (UIScrollBarView) UIObjectFactory.createViewObject(child, getScene());
				if (listBox.getScrollBarOnRight()!=null && listBox.getScrollBarOnRight()) {					
					viewChild.setX(this.getX()+this.getWidth()-viewChild.getWidth());				
				}
				else {
					viewChild.setX(this.getX());
				}
				viewChild.setY(this.getY());
				viewChild.positionChanged();
				addChild(viewChild);			
			}
		}		
	}

}
