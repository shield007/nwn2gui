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
import java.awt.Image;
import java.io.FileNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stanwood.nwn2.gui.icons.ImageException;
import org.stanwood.nwn2.gui.model.UIFrame;
import org.stanwood.nwn2.gui.model.UIScene;

public class UIFrameView  extends UIObjectView {

	private final static Log log = LogFactory.getLog(UIFrameView.class);
	private UIFrame frame;

	public UIFrameView(UIFrame frame,UIScene scene, Dimension screenDimension) {
		super(scene, screenDimension);
		this.frame = frame;
		if (frame.getX()!=null && frame.getY()!=null) {
			setX(frame.getX().getValue(getScreenDimension(), getScene()));
			setY(frame.getY().getValue(getScreenDimension(), getScene()));
		}
	}

	@Override
	public void paintUIObject(Graphics g) {
		if (frame.getState()==null || frame.getState().equalsIgnoreCase("base")|| frame.getState().equalsIgnoreCase("up")) {
			try {								
				int x = getX();
				int y = getY();
				int width = frame.getWidth().getValue(getScreenDimension());
				int height = frame.getHeight().getValue(getScreenDimension());
				int border = frame.getBorder();
		
				drawFramePart(g,  width - (border*2), height - (border*2),  x+border, y+border, frame.getFill());
				drawFramePart(g,  border,border,  x, y, frame.getTopleft());
				drawFramePart(g,  width - (border*2),border,   x+border, y, frame.getTop());
				drawFramePart(g,  border,border,  x+width-border, y, frame.getTopright());
				drawFramePart(g,  border,height - (border*2),  x+width-border, y+border, frame.getRight());
				drawFramePart(g,  border,border,   x+width-border,y+height-border, frame.getBottomright());
				drawFramePart(g,  width - (border*2), border,  x+border, y+height-border, frame.getBottom());
				drawFramePart(g,  border,border,  x,y+height-border, frame.getBottomleft());
				drawFramePart(g,  border,height - (border*2),  x, y+border, frame.getLeft());			
			}
			catch (ImageException e) {
				log.error(e.getMessage(),e);
			}
		}
	}

	private void drawFramePart(Graphics g, int iconWidth, int iconHeight,
			int iconX, int iconY, String iconName) throws ImageException {
		if (iconName!=null) {					
			try {
				Image fill = getIconManager().getIcon(iconName).getScaledInstance(iconWidth, iconHeight,Image.SCALE_SMOOTH);
				g.drawImage(fill, iconX,iconY, null);
			}
			catch (FileNotFoundException e) {
				log.error(e.getMessage());
			}
		}
	}
}
