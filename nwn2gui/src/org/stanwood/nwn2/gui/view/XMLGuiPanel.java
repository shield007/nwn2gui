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

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXPanel;
import org.stanwood.nwn2.gui.model.NWN2GUIObject;
import org.stanwood.nwn2.gui.model.UIScene;

public class XMLGuiPanel extends JXPanel {
	
	private static final long serialVersionUID = 3199212196302509796L;
	private UISceneView sceneView;
	private boolean ready = false;

	public XMLGuiPanel(UIScene scene) {		
		createViewObjects(scene);		
	}

	private void createViewObjects(final UIScene scene) {
		setLayout(new BorderLayout(5,5));
		final JXBusyLabel label = new JXBusyLabel();		
		label.setToolTipText("Parsing the scene");		 
		add(label,BorderLayout.CENTER);
		
		Thread t = new Thread() {
			@Override
			public void run() {				
				sceneView = new UISceneView();
				for (int i=scene.getChildren().size()-1;i>=0;i--) {
		    		NWN2GUIObject uiObject =scene.getChildren().get(i); 
		    		UIObjectView viewObject = UIObjectFactory.createViewObject(uiObject,scene);
		    		if (viewObject!=null) {
		    			sceneView.addChild(viewObject);
		    		}
		    	}
				label.setVisible(false);
				ready = true;
				
			}			
		};
		t.start();

	}

	// Since we're always going to fill our entire
    // bounds, allow Swing to optimize painting of us
    @Override
	public boolean isOpaque() {
        return true;
    }

    @Override
	protected void paintComponent(Graphics g) {
    	if (!ready) {
    		super.paintComponent(g);
    		return;
    	}
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    	g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    	for (UIObjectView uiObject : sceneView.getChildren()) {    	    		
    		uiObject.paintUIObject(g);    	
    	}
    }
}
