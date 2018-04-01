package visual.roles;

import java.awt.Color;

import visual.util.components.ShipPanel;
import visual.util.operations.GUIController;

public class RadioPane extends ShipPanel {

	public RadioPane(GUIController cx) {
		super(cx);
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public void draw() {
		defaultDraw(Color.PINK, Color.BLUE);
		
	}
	
}
