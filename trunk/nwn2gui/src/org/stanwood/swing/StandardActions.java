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
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.stanwood.swing.icons.IconManager;


public class StandardActions {

	private static final String COMMAND_KEY_HELP_ABOUT = "helpAbout";	
	private static final String COMMAND_KEY_APPLICATION_EXIT = "appExit";
	private static final Object COMMAND_KEY_DIALOG_APPLY = "dialogApply";
	private static final Object COMMAND_KEY_DIALOG_CANCEL = "dialogCancel";
	private static final Object COMMAND_KEY_DIALOG_OK = "dialogOk";
	private static final Object COMMAND_KEY_DIALOG_CLOSE = "dialogClose";

	public static Action getApplicationExit(ActionListener actionListener) {
		Action action = createAction(actionListener);
		
		action.putValue(Action.NAME, "Exit");		
		action.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		action.putValue(Action.ACTION_COMMAND_KEY, COMMAND_KEY_APPLICATION_EXIT);
		action.putValue(Action.LARGE_ICON_KEY, IconManager.getInstance().getIcon(IconManager.SIZE_22,IconManager.ICON_APPLICATION_EXIT ));
		action.putValue(Action.SMALL_ICON, IconManager.getInstance().getIcon(IconManager.SIZE_16,IconManager.ICON_APPLICATION_EXIT ));
		action.putValue(Action.SHORT_DESCRIPTION, "Exit the application");
		
		return action;
	}
	
	public static Action getAboutAction(ActionListener actionListener) {
		Action action = createAction(actionListener);
		
		action.putValue(Action.NAME, "About");
		action.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		action.putValue(Action.ACTION_COMMAND_KEY, COMMAND_KEY_HELP_ABOUT);
		action.putValue(Action.LARGE_ICON_KEY, IconManager.getInstance().getIcon(IconManager.SIZE_22,IconManager.ICON_HELP_ABOUT ));
		action.putValue(Action.SMALL_ICON, IconManager.getInstance().getIcon(IconManager.SIZE_16,IconManager.ICON_HELP_ABOUT ));
		action.putValue(Action.SHORT_DESCRIPTION, "About the application");
		
		return action;
	}
	
	public static Action getDialogApplyAction(ActionListener actionListener) {
		Action action = createAction(actionListener);
		
		action.putValue(Action.NAME, "Apply");
		action.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		action.putValue(Action.ACTION_COMMAND_KEY, COMMAND_KEY_DIALOG_APPLY);
		action.putValue(Action.LARGE_ICON_KEY, IconManager.getInstance().getIcon(IconManager.SIZE_16,IconManager.ICON_DIALOG_OK_APPLY ));
		action.putValue(Action.SMALL_ICON, IconManager.getInstance().getIcon(IconManager.SIZE_16,IconManager.ICON_DIALOG_OK_APPLY ));
		action.putValue(Action.SHORT_DESCRIPTION, "Apply settings");
		
		return action;
	}
	
	public static Action getDialogOkAction(ActionListener actionListener) {
		Action action = createAction(actionListener);
		
		action.putValue(Action.NAME, "Ok");
		action.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		action.putValue(Action.ACTION_COMMAND_KEY, COMMAND_KEY_DIALOG_OK);
		action.putValue(Action.LARGE_ICON_KEY, IconManager.getInstance().getIcon(IconManager.SIZE_16,IconManager.ICON_DIALOG_OK ));
		action.putValue(Action.SMALL_ICON, IconManager.getInstance().getIcon(IconManager.SIZE_16,IconManager.ICON_DIALOG_OK ));
		action.putValue(Action.SHORT_DESCRIPTION, "Apply settings and close dialog");
		
		return action;
	}
	
	public static Action getDialogCancelAction(ActionListener actionListener) {
		Action action = createAction(actionListener);
		
		action.putValue(Action.NAME, "Cancel");
		action.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		action.putValue(Action.ACTION_COMMAND_KEY, COMMAND_KEY_DIALOG_CANCEL);
		action.putValue(Action.LARGE_ICON_KEY, IconManager.getInstance().getIcon(IconManager.SIZE_16,IconManager.ICON_DIALOG_CANCEL ));
		action.putValue(Action.SMALL_ICON, IconManager.getInstance().getIcon(IconManager.SIZE_16,IconManager.ICON_DIALOG_CANCEL ));
		action.putValue(Action.SHORT_DESCRIPTION, "Cancel changes and close dialog");
		
		return action;
	}

	public static Action getDialogCloseAction(ActionListener actionListener) {
		Action action = createAction(actionListener);
		
		action.putValue(Action.NAME, "Close");
		action.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		action.putValue(Action.ACTION_COMMAND_KEY, COMMAND_KEY_DIALOG_CLOSE);
		action.putValue(Action.LARGE_ICON_KEY, IconManager.getInstance().getIcon(IconManager.SIZE_16,IconManager.ICON_DIALOG_CLOSE ));
		action.putValue(Action.SMALL_ICON, IconManager.getInstance().getIcon(IconManager.SIZE_16,IconManager.ICON_DIALOG_CLOSE ));
		action.putValue(Action.SHORT_DESCRIPTION, "Close the dialog");
		
		return action;
	}

	private static Action createAction(final ActionListener actionListener) {
		Action action =  new AbstractAction() {
			private static final long serialVersionUID = 7718563206724554062L;

			@Override
			public void actionPerformed(ActionEvent e) {
				actionListener.actionPerformed(e);
			}			
		};
		return action;
	}
		
}
