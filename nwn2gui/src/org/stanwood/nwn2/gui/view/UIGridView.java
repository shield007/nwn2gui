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
import org.stanwood.nwn2.gui.model.UIGrid;
import org.stanwood.nwn2.gui.model.UIObject;
import org.stanwood.nwn2.gui.model.UIScene;

public class UIGridView extends UIObjectView {

	private UIGrid grid;


	public UIGridView(UIGrid grid, UIScene scene, Dimension screenDimension) {
		super(grid,scene, screenDimension);
		this.grid = grid;
		
		constructGrid();
	}

	private void constructGrid() {
		if (!grid.getChildren().isEmpty()) {
			List<NWN2GUIObject> children = grid.getChildren();
			int childIndex=0;
					
			int height = ((UIObject)children.get(0)).getHeight().getValue(getScreenDimension());
			int x = grid.getX().getValue(getScreenDimension(), getScene())+grid.getXPadding();
			int y = grid.getY().getValue(getScreenDimension(), getScene())+grid.getYPadding();			
			for (int row=0;row<grid.getRows();row++) {				
				for (int column=0;column<grid.getColumns();column++) {
					UIObject child = (UIObject) children.get(childIndex);
					
					UIObjectView viewChild = UIObjectFactory.createViewObject(child,getScene());					
					viewChild.setX(x);
					viewChild.setY(y);
					viewChild.positionChanged();					
					addChild(viewChild);
										
					x+=child.getWidth().getValue(getScreenDimension())+grid.getXPadding();
					childIndex++;
					if (childIndex>children.size()-1) {
						childIndex = 0;
					}
				}
				
				y+=height+grid.getYPadding();	
				x = grid.getX().getValue(getScreenDimension(), getScene())+grid.getXPadding();
			}		
		}
	}

	
}
