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

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.JXLabel;

public class AboutDialog extends EnhancedDialog {
	
	private static final long serialVersionUID = 7038991844104135431L;
	
	private Icon icon;
	private String title;
	private String version;
	private String appUrl;
	private String message;
	private List<Author>authors = new ArrayList<Author>();

	public AboutDialog(JFrame parent, String title,String version) {		
		super(parent, "About " +title, true);
		this.title = title;
		this.version = version;

		Border border = BorderFactory.createEmptyBorder(5,5,5,5);		
		getRootPane().setBorder(border);		
	}
	
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	
	public void setApplicationWebLink(String url) {
		this.appUrl = url;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void addAuthor(Author author) {
		authors.add(author);
	}

	public void init() {
		Box box = Box.createVerticalBox();
		if (icon==null) {		
			createTitleArea(box,title,version);
		}
		else {
			Box hbox = Box.createHorizontalBox();
			hbox.add(Box.createHorizontalStrut(40));
			hbox.add(new JLabel(icon));
			hbox.add(Box.createHorizontalStrut(10));
			box.add(hbox);
			Box vbox = Box.createVerticalBox();
			createTitleArea(vbox,title,version);
			hbox.add(vbox);		
			hbox.add(Box.createHorizontalGlue());
		}
				
		JTabbedPane infoTabbedPane = new JTabbedPane();
		infoTabbedPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JComponent tabAbout = createAboutTab();
		infoTabbedPane.addTab("About",tabAbout);
		JComponent tabAuthors = createAuthorsTab();
		infoTabbedPane.addTab("Authors",tabAuthors);
		box.add(infoTabbedPane);
		createButtonPane(box);
		
		setContentPane(box);
		setSize(450,280);
		setLocationRelativeTo(getParent());				
	}	
	
	private JComponent createAuthorsTab() {
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);		
		Box box = Box.createVerticalBox();
		box.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		for (Author author : authors) {
			box.add(new JLabel(author.getName()));	
			JXHyperlink link = new JXHyperlink(new LinkAction(author.getEmail(),"emailto:"+author.getEmail()));
			box.add(link);
			link.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
			JLabel lblDescription = new JLabel(author.getDescription());
			lblDescription.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
			box.add(lblDescription);
		}
		
		scroll.getViewport().add(box);
		return scroll;
	}

	private JComponent createAboutTab() {
		JScrollPane scroll = new JScrollPane();
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);		
		Box box = Box.createVerticalBox();
		box.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		box.add(Box.createVerticalGlue());
		if (message!=null) {
			JXLabel lblMessage = new JXLabel(message);
			lblMessage.setLineWrap(true);			
			box.add(lblMessage);
		}
		if (appUrl!=null) {
			JXHyperlink link = new JXHyperlink(new LinkAction(appUrl,appUrl));
			box.add(link);
		}
		box.add(Box.createVerticalGlue());
		scroll.getViewport().add(box);
		return scroll;
	}
	
	private void createButtonPane(Box box) {
		Box hBox = Box.createHorizontalBox();
		hBox.add(Box.createHorizontalGlue());
		JButton cmdClose = new JButton(StandardActions.getDialogCloseAction(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		}));
		hBox.add(cmdClose);
		box.add(hBox);
		
		hBox.setBorder(BorderFactory.createEmptyBorder(0,5,5,5));
		
		cmdClose.requestFocusInWindow();
	}


	private void createTitleArea(Box box, String title, String version) {
		JLabel lblTitle = new JLabel(title);
		lblTitle.setHorizontalTextPosition(JLabel.LEFT);
		lblTitle.setHorizontalAlignment(JLabel.LEFT);
		lblTitle.setFont(new Font("Serif",Font.BOLD,24));
		box.add(lblTitle);
		JLabel lblVersion = new JLabel(version);
		lblVersion.setFont(new Font("Serif",Font.BOLD,12));
		lblVersion.setHorizontalAlignment(JLabel.LEFT);
		lblVersion.setHorizontalTextPosition(JLabel.LEFT);
		box.add(lblVersion);		
	}

	private static class LinkAction extends AbstractAction {
		private static final long serialVersionUID = 4127702255942800989L;

		LinkAction(String linkText, String link) {
			putValue(Action.NAME, linkText);
			putValue(Action.SHORT_DESCRIPTION, link);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String link = (String) getValue(Action.SHORT_DESCRIPTION);			
			BareBonesBrowserLaunch.openURL(link);
		}
	}

	@Override
	public void cancel() {
		dispose();
	}

	@Override
	public void ok() {
		dispose();
	}
}