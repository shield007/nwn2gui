package org.stanwood.nwn2.gui.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.stanwood.nwn2.gui.model.NWN2GUIObject;
import org.stanwood.nwn2.gui.model.UIButton;
import org.stanwood.nwn2.gui.model.UIFrame;
import org.stanwood.nwn2.gui.model.UIGrid;
import org.stanwood.nwn2.gui.model.UIIcon;
import org.stanwood.nwn2.gui.model.UIScene;
import org.stanwood.nwn2.gui.model.UIText;

public class UIObjectFactory {
	
	private static Dimension screenDimension;
	
	static
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		screenDimension = toolkit.getScreenSize(); 
	}
	
	
	public static UIObjectView createViewObject(NWN2GUIObject uiObject,UIScene scene) {
		UIObjectView view = null; 
		if (uiObject instanceof UIIcon) {			
			view = new UIIconView((UIIcon)uiObject, scene, screenDimension);
		}
		else if (uiObject instanceof UIFrame) {
			view = new UIFrameView((UIFrame)uiObject, scene, screenDimension);
		}
		else if (uiObject instanceof UIButton) {
			view = new UIButtonView((UIButton)uiObject, scene, screenDimension);
		}
		else if (uiObject instanceof UIGrid ) {
			view = new UIGridView((UIGrid)uiObject, scene, screenDimension);
		}		
		else if (uiObject instanceof UIText ) {
			view =  new UITextView((UIText)uiObject, scene, screenDimension);
		}
		return view;
	}

}
