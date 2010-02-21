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
import java.util.List;

import org.stanwood.nwn2.gui.model.NWN2GUIObject;
import org.stanwood.nwn2.gui.model.UIObject;
import org.stanwood.nwn2.gui.model.UIPane;
import org.stanwood.nwn2.gui.model.UIScene;

public class UIPaneView extends UIObjectView {

	public UIPaneView(UIPane pane, UIScene scene, Dimension screenDimension) {
		super(pane, scene, screenDimension);
		
		createView(pane);
	}

	
	private void createView(UIPane pane) {
		if (!pane.getChildren().isEmpty() && (pane.getPrototype()==null || !pane.getPrototype())) {
			List<NWN2GUIObject> children = pane.getChildren();			
			int x = pane.getX().getValue(getScreenDimension(), getScene());
			int y = pane.getY().getValue(getScreenDimension(), getScene());
			
			for (int i=0;i<children.size();i++) {
				UIObject child = (UIObject) children.get(i);
				UIObjectView viewChild = UIObjectFactory.createViewObject(child,getScene());
				if (viewChild!=null) {
					viewChild.setX(x);
					viewChild.setY(y);
					viewChild.positionChanged();					
					addChild(viewChild);
				}
								
				x+=child.getWidth().getValue(getScreenDimension());			
			}
		}
		
	}

}
