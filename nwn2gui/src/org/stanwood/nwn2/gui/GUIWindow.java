package org.stanwood.nwn2.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.stanwood.nwn2.gui.model.UIScene;
import org.stanwood.nwn2.gui.view.XMLGuiPanel;

public class GUIWindow extends JFrame {

	private static final long serialVersionUID = -3132763668971367647L;

	public GUIWindow(String title,UIScene scene) {
		setTitle(title);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenDimension = toolkit.getScreenSize();

		XMLGuiPanel panel = new XMLGuiPanel(scene);
		getContentPane().add(panel);
		setSize(scene.getWidth().getValue(screenDimension)+20, scene.getHeight()
				.getValue(screenDimension)+20);
		setLocation(scene.getX().getValue(screenDimension, scene), scene.getY()
				.getValue(screenDimension, scene));
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

}
