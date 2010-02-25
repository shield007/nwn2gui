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
package org.stanwood.nwn2.gui.options;

import java.awt.Frame;

import org.stanwood.nwn2.gui.options.panels.NWN2ContentOptionPanel;
import org.stanwood.swing.perferences.AbstractPreferenceDialog;
import org.stanwood.swing.perferences.panels.LookFeelOptionPanel;


public class OptionsDialog extends AbstractPreferenceDialog {

	private static final long serialVersionUID = 2557972118818391136L;
	
	public OptionsDialog(Frame parent) {
		super(parent, "NWN2GUI Settings",NWN2GuiSettings.getInstance());
	}

	@Override
	protected void addPreferencePanels() {
		addPreferencePanel(new NWN2ContentOptionPanel());
		addPreferencePanel(new LookFeelOptionPanel());
	}
}
