package visual.roles;

import java.awt.Color;

public class CaptainPane extends ShipPanel {

	public CaptainPane() {
		super(Color.GREEN);
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
