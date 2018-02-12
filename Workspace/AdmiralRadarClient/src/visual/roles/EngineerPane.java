package visual.roles;

import java.awt.Color;

public class EngineerPane extends ShipPanel {

	public EngineerPane() {
		super(Color.CYAN);
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public void draw() {
		defaultDraw(Color.GREEN, Color.BLUE);
		
	}
}
