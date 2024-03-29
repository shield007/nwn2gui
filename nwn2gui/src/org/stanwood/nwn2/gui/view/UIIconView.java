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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.color.ColorUtil;
import org.stanwood.nwn2.gui.model.UIIcon;
import org.stanwood.nwn2.gui.model.UIScene;

public class UIIconView extends UIObjectView {			
	
	private final static Log log = LogFactory.getLog(UIIconView.class);
	private UIIcon icon;	
	
	public UIIconView(UIIcon icon,UIScene scene,Dimension screenDimension) {
		super(icon,scene,screenDimension);
		this.icon = icon;
		if (icon.getScaleWithScene()!=null && icon.getScaleWithScene()) {
			int sceneWidth = screenDimension.width;
			if (scene.getWidth()!=null) {
				sceneWidth = scene.getWidth().getValue(screenDimension);
			}
			int sceneHeight = screenDimension.height;
			if (scene.getHeight()!=null) {
				sceneHeight = scene.getHeight().getValue(screenDimension);
			}
			setWidth(sceneWidth-getX());
			setHeight(sceneHeight-getY());			
		}
	}
		
	@Override
	public void paintUIObject(Graphics g) {
		int x = getX();
		int y = getY();
		try {			
			BufferedImage img = getIconManager().getIcon(icon.getImg());
			int width = getWidth();
			int height = getHeight();
			
			if (img.getHeight()!= height && img.getWidth()!=width) {				
				Image newImg = img.getScaledInstance(width,height, Image.SCALE_SMOOTH);
				img = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
				Graphics ig = img.getGraphics();
				ig.drawImage(newImg, 0,0,null);
			}
			
			if (icon.getColor()!=null) {
				Color colour = getColor(icon.getColor());
				for(int w = 0; w < img.getWidth(); w++){
			  		 for(int h = 0; h < img.getHeight(); h++){			  			
			  			Color rgb = ColorUtil.blend(new Color(img.getRGB(w, h)), colour);
			  			img.setRGB(w, h, rgb.getRGB());
			  		 }
				}
			}
			
			g.drawImage(img,x,y , null);
		}
		catch (Exception e) {
			log.error(e.getMessage());
			drawMissingIcon(x,y,getWidth(),getHeight(),g);
		}
	}
		
	private Color getColor(String color) {		
		return new Color(Integer.parseInt(color, 16));		
	}

	private void drawMissingIcon(int x,int y,int width,int height,Graphics g) {
		g.setColor(Color.RED);						
		 
		 g.drawRect(x,y,width,height);
		 
		 g.drawLine(x, y, x+width, y+height);
		 g.drawLine(x+width, y, x, y+height);
	}
	

}
