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

import java.awt.Graphics;

import javax.swing.JPanel;

import org.stanwood.nwn2.gui.model.NWN2GUIObject;
import org.stanwood.nwn2.gui.model.UIScene;

public class XMLGuiPanel extends JPanel {
	
	private static final long serialVersionUID = 3199212196302509796L;
	private UISceneView sceneView;

	public XMLGuiPanel(UIScene scene) {		
		createViewObjects(scene);
	}

	private void createViewObjects(UIScene scene) {
		sceneView = new UISceneView();
		for (int i=scene.getChildren().size()-1;i>=0;i--) {
    		NWN2GUIObject uiObject =scene.getChildren().get(i); 
    		UIObjectView viewObject = UIObjectFactory.createViewObject(uiObject,scene);
    		if (viewObject!=null) {
    			sceneView.addChild(viewObject);
    		}
    	}
	}

	// Since we're always going to fill our entire
    // bounds, allow Swing to optimize painting of us
    public boolean isOpaque() {
        return true;
    }

    protected void paintComponent(Graphics g) {
    	for (UIObjectView uiObject : sceneView.getChildren()) {    	    		
    		uiObject.paintUIObject(g);    	
    	}
    }
}
