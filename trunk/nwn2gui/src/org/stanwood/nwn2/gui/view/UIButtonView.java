package org.stanwood.nwn2.gui.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stanwood.nwn2.gui.StyleSheetManager;
import org.stanwood.nwn2.gui.model.NWN2GUIObject;
import org.stanwood.nwn2.gui.model.UIButton;
import org.stanwood.nwn2.gui.model.UIObject;
import org.stanwood.nwn2.gui.model.UIScene;
import org.stanwood.nwn2.gui.model.UIText;

public class UIButtonView extends UIObjectView {
	
	private final static Log log = LogFactory.getLog(UIButtonView.class);	
	private List<UIObjectView>children = new ArrayList<UIObjectView>();

	public UIButtonView(UIButton button,UIScene scene, Dimension screenDimension) {
		super(scene, screenDimension);
		
		if (button.getX()!=null && button.getY()!=null) {
			setX(button.getX().getValue(getScreenDimension(), getScene()));
			setY(button.getY().getValue(getScreenDimension(), getScene()));
		}
		createView(button);
	}

	private void createView(UIButton button) {
		UIButton newButton = button;
		if (button.getStyle()!=null) {
			UIObject styleButton = StyleSheetManager.getInstance().getObjectByName(button.getStyle());
			if (styleButton!=null) {
				try {
					newButton = (UIButton) button.clone();
//					newButton.setParent(button.getParent());
					newButton.applyStyle(styleButton);
				} catch (CloneNotSupportedException e) {
					log.error("Unable to clone button, " + e.getMessage(),e);
				}				
			}
			else {
				log.error("Unable to find the style: " +button.getStyle());
			}
		}
		for (NWN2GUIObject uiObject : newButton.getChildren()) {
			if (uiObject instanceof UIText ) {
				UIText text = (UIText)uiObject;
				if (newButton.getText()!=null) {
					text.setText(newButton.getText());
				}				
				if (newButton.getStrRef()!=null) {
					text.setStrRef(newButton.getStrRef());
				}
			}				
			
			UIObjectView viewChild = UIObjectFactory.createViewObject(uiObject,getScene());
			if (newButton.getX()!=null && newButton.getY()!=null) {
				viewChild.setX(newButton.getX().getValue(getScreenDimension(), getScene()));
				viewChild.setY(newButton.getY().getValue(getScreenDimension(), getScene()));				
			}
			this.children.add(viewChild);
		}		
	}

	@Override
	public void paintUIObject(Graphics g) {
		for (UIObjectView viewChild : children) {
			viewChild.paintUIObject(g);
		}
	}

}