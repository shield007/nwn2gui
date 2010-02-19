package org.stanwood.nwn2.gui.options;

public interface IOptionsPanel {
	
	public String getPanelName();
	
	public String getDescription();

	void loadSettings(Settings settings);

	void saveSettings(Settings settings);

}
