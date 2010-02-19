package org.stanwood.nwn2.gui.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import org.stanwood.nwn2.gui.model.NWN2GUIObject;
import org.stanwood.nwn2.gui.model.UIGrid;
import org.stanwood.nwn2.gui.model.UIObject;
import org.stanwood.nwn2.gui.model.UIScene;

public class UIGridView extends UIObjectView {

	private UIGrid grid;
	private List<UIObjectView>children = new ArrayList<UIObjectView>();

	public UIGridView(UIGrid grid, UIScene scene, Dimension screenDimension) {
		super(scene, screenDimension);
		this.grid = grid;
		
		constructGrid();
	}

	private void constructGrid() {
		if (!grid.getChildren().isEmpty()) {
			List<NWN2GUIObject> children = grid.getChildren();
			int childIndex=0;
					
			int height = ((UIObject)children.get(0)).getHeight().getValue(getScreenDimension());
			int x = grid.getX().getValue(getScreenDimension(), getScene())+grid.getxPadding();
			int y = grid.getY().getValue(getScreenDimension(), getScene())+grid.getyPadding();			
			for (int row=0;row<grid.getRows();row++) {				
				for (int column=0;column<grid.getColumns();column++) {
					UIObject child = (UIObject) children.get(childIndex);
					
					UIObjectView viewChild = UIObjectFactory.createViewObject(child,getScene());					
					viewChild.setX(x);
					viewChild.setY(y);
					System.out.println("Setting grid cell pos : " + viewChild.getX()+","+viewChild.getY()+":"+viewChild.getClass());
					this.children.add(viewChild);
										
					x+=child.getWidth().getValue(getScreenDimension())+grid.getxPadding();
					childIndex++;
					if (childIndex>children.size()-1) {
						childIndex = 0;
					}
				}
				
				y+=height+grid.getyPadding();	
				x = grid.getX().getValue(getScreenDimension(), getScene())+grid.getxPadding();
			}		
		}
	}

	@Override
	public void paintUIObject(Graphics g) {
		for (UIObjectView child : children) {						
			child.paintUIObject(g);
		}
	}

}
