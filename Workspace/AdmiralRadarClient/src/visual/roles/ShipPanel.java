package visual.roles;

import java.awt.Color;

import javax.swing.JPanel;

public abstract class ShipPanel extends JPanel{

	protected Color background;
	
	public void RoleComponentPaneColor(){
		setBackground(background);
	}
}
