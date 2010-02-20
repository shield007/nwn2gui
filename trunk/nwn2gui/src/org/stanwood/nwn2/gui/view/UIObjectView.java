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
import java.awt.Graphics;

import org.stanwood.nwn2.gui.icons.NWN2IconManager;
import org.stanwood.nwn2.gui.model.UIObject;
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
	private int width;
	private int height;
	private String name;
	
	public UIObjectView(UIObject object, UIScene scene, Dimension screenDimension) {
		this.scene = scene;
		this.screenDimension = screenDimension;
		this.name = object.getName();
		if (object.getX()!=null && object.getY()!=null) {
			setX(object.getX().getValue(getScreenDimension(), getScene()));
			setY(object.getY().getValue(getScreenDimension(), getScene()));
		}
		if (object.getWidth()!=null) {
			setWidth(object.getWidth().getValue(getScreenDimension()));
		}
		if (object.getHeight()!=null) {
			setHeight(object.getHeight().getValue(getScreenDimension()));
		}
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void positionChanged() {
		
	}

	@Override
	public String toString() {	
		return name+" - x="+x+", y="+y+", width="+width+", height="+height;
	}

	
}
