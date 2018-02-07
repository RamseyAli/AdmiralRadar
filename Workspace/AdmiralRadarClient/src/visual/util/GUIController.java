package visual.util;

import java.awt.BorderLayout;

import general.Role;

public class GUIController {

	GamePanel mainPane;
	Role display;
	
	public GUIController(Role r) {
		display = r;
	}
	
	public void setPanel(GamePanel p) {
		mainPane = p;
		mainPane.setLayout(new BorderLayout());
		
	}

	public void makeGUI() {
		// TODO Auto-generated method stub
		
	}

}
