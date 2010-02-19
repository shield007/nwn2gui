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
