package org.stanwood.nwn2.gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stanwood.nwn2.gui.model.UIIcon;
import org.stanwood.nwn2.gui.model.UIScene;

public class UIIconView extends UIObjectView {			
	
	private final static Log log = LogFactory.getLog(UIIconView.class);
	private UIIcon icon;	
	private int width;
	private int height;
	
	public UIIconView(UIIcon icon,UIScene scene,Dimension screenDimension) {
		super(scene,screenDimension);
		this.icon = icon;
		
		if (icon.getX()!=null && icon.getY()!=null) {
			setX(icon.getX().getValue(getScreenDimension(), getScene()));
			setY(icon.getY().getValue(getScreenDimension(), getScene()));
		}
		width = icon.getWidth().getValue(getScreenDimension());
		height = icon.getHeight().getValue(getScreenDimension());
	}
		
	@Override
	public void paintUIObject(Graphics g) {
		int x = getX();
		int y = getY();
		try {
			
			Image img = getIconManager().getIcon(icon.getImg());
			img = img.getScaledInstance(width,height, Image.SCALE_SMOOTH);
			g.drawImage(img,x,y , null);
		}
		catch (Exception e) {
			log.error(e.getMessage());
			drawMissingIcon(x,y,width,height,g);
		}
	}
	
	private void drawMissingIcon(int x,int y,int width,int height,Graphics g) {
		g.setColor(Color.RED);						
		 
		 g.drawRect(x,y,width,height);
		 
		 g.drawLine(x, y, x+width, y+height);
		 g.drawLine(x+width, y, x, y+height);
	}
	

}
