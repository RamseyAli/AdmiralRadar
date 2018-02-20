package visual.roles;

import java.awt.Color;
import java.awt.Dimension;

public class CaptainPane extends ShipPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public CaptainPane() {
		super(Color.GREEN);
		
		this.setPreferredSize(new Dimension(100,100));
	}


	@Override
	public void draw() {
		defaultDraw(Color.GREEN, Color.RED);
		
		
	}
	
}
