package org.stanwood.nwn2.gui.view;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class UISceneView {

	private List<UIObjectView>children = new ArrayList<UIObjectView>();
	
	public void addChild(UIObjectView viewObject) {
		children.add(viewObject);
	}

	public List<UIObjectView> getChildren() {
		return children;
	}

	public void paint(Graphics g) {
		
	}

	
}
