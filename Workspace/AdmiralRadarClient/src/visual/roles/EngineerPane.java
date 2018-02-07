package visual.roles;

import java.awt.Color;

import javax.swing.JLabel;

public class EngineerPane extends ShipPanel {

	public EngineerPane(){
		Color x = Color.RED;
		background = new Color(x.getRed(),x.getGreen(),x.getBlue(), 200);
		
		RoleComponentPaneColor();
		
		add(new JLabel("Hello Ground"));
		setVisible(true);
	}
	
}
