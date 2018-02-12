package visual.roles;

import java.awt.Color;

public class RadioPane extends ShipPanel {

	public RadioPane() {
		super(Color.YELLOW);
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
