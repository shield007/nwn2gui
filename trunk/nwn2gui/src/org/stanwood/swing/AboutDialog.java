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

package org.stanwood.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXHyperlink;

public class AboutDialog extends JDialog {
	public AboutDialog(JFrame parent, String title, String message,
			String linkText, String link) {
		// Assign title to the dialog box’s title bar and make sure dialog box
		// is
		// modal.

		super(parent, title, true);

		// Tell dialog box to automatically hide and dispose itself when the
		// user
		// selects the Close menu item from the dialog box’s system menu.

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// Establish the dialog box’s border area. A compound border establishes
		// a beveled outer border with an empty inner border, which is used as a
		// margin.

		Border border = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
		Border margin = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border cborder = BorderFactory.createCompoundBorder(border, margin);
		getRootPane().setBorder(cborder);

		// Use a vertical Box as the dialog box’s content pane, to simplify
		// layout.

		Box box = Box.createVerticalBox();
		setContentPane(box);

		// Add a message (typically identifying the program) label to the box’s
		// top. This label is horizontally centered within the box.

		JLabel label = new JLabel(message);
		label.setAlignmentX(0.5f);
		box.add(label);

		// Add a 30-pixel vertical blank space below the message label to the
		// box
		// (for aesthetic purposes).

		box.add(Box.createVerticalStrut(30));

		// Add a hyperlink to the box’s middle. This hyperlink is horizontally
		// centered within the box.

		JXHyperlink hyperlink;
		hyperlink = new JXHyperlink(new LinkAction(linkText, link));
		hyperlink.setAlignmentX(0.5f);
		box.add(hyperlink);

		// Add a 30-pixel vertical blank space below the hyperlink to the box
		// (for aesthetic purposes).

		box.add(Box.createVerticalStrut(30));

		// Add an OK button to the box’s bottom. This button is horizontally
		// centered within the box. Assign an action listener to hide and
		// dispose
		// the dialog box (when the button is clicked).

		JButton button = new JButton("OK");
		button.setAlignmentX(0.5f);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		box.add(button);

		// Size dialog box to fit the preferred size and layouts of its
		// components.

		pack();

		// Center the dialog box (when displayed) relative to its parent window.
		// If the parent window is not showing, the dialog box is centered on
		// the
		// screen.

		setLocationRelativeTo(parent);

		// Give input focus to the button.

		button.requestFocusInWindow();
	}

	private static class LinkAction extends AbstractAction {
		LinkAction(String linkText, String link) {
			// Save the link’s text and the actual link for later recall when
			// the
			// user clicks the hyperlink.

			putValue(Action.NAME, linkText);
			putValue(Action.SHORT_DESCRIPTION, link);
		}

		public void actionPerformed(ActionEvent e) {
			// Retrieve the actual link and output link to the console (for test
			// purposes).

			String link = (String) getValue(Action.SHORT_DESCRIPTION);
			System.out.println(link);

			// Launch the default Web browser and have the Web browser display
			// the
			// Web page associated with the link.

			BareBonesBrowserLaunch.openURL(link);
		}
	}
}