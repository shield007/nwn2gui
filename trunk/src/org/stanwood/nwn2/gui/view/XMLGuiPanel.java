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
