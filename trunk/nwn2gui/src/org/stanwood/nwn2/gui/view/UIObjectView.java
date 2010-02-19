package org.stanwood.nwn2.gui.view;

import java.awt.Dimension;
import java.awt.Graphics;

import org.stanwood.nwn2.gui.icons.NWN2IconManager;
import org.stanwood.nwn2.gui.model.UIScene;

public abstract class UIObjectView {

	private static NWN2IconManager iconManager;

	static {
		iconManager = NWN2IconManager.getInstance();
	}

	private UIScene scene;
	private Dimension screenDimension;
	private int x;
	private int y;
	
	public UIObjectView(UIScene scene, Dimension screenDimension) {
		this.scene = scene;
		this.screenDimension = screenDimension;
	}
	
	public abstract void paintUIObject(Graphics g);
	
	protected NWN2IconManager getIconManager() {
		return iconManager;
	}

	protected UIScene getScene() {
		return scene;
	}

	protected Dimension getScreenDimension() {
		return screenDimension;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}


}
