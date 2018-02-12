package visual.roles;

import java.awt.Color;

public class ExecutivePane extends ShipPanel {

	public ExecutivePane() {
		super(Color.MAGENTA);
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public void draw() {
		defaultDraw(Color.GREEN, Color.RED);
		
	}
	
}
