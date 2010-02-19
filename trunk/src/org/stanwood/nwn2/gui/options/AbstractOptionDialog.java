package org.stanwood.nwn2.gui.options;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public abstract class AbstractOptionDialog extends JPanel implements IOptionsPanel {

	private static final long serialVersionUID = -8526220764053684079L;

	public AbstractOptionDialog() {
		init();
	}

	private void init() {
		setLayout(new BorderLayout());				
		JScrollPane scroll = new JScrollPane();		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));		
		createControl(panel);
		scroll.getViewport().add(panel);
		
		add(scroll,BorderLayout.CENTER);
	}

	protected abstract void createControl(JPanel panel);
}
