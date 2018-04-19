package visual.roles;

import java.awt.Graphics2D;

import game.Position;
import visual.common.general.MapBasedElement;
import visual.util.operations.GUIController;

public class RadioPane extends MapBasedElement {

	private Position radio_start;
	
	public RadioPane(GUIController cx) {
		super( cx );
		ear.enabled( true );
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void clickGridDot(int x, int y) {
		System.out.println( "Radio Click! " + x + ":" + y );
		radio_start = new Position(x, y);
		
		control.threadSafeRepaint( this );

	}
	
	@Override
	protected void drawPath(Graphics2D g, int x0, int y0, int k, int r, int sp) {

	//	System.out.println( "Refreshing Path " + control.getRadioPath().size() );

		if ((control.getRadioPath() != null) && (radio_start != null))

			for (Position p : getPositionPath(control.getRadioPath() , radio_start))

				g.fillOval( x0 - k * r + ( p.getX() * sp ) , y0 - k * r + ( p.getY() * sp ) , 2 * k * r , 2 * k * r );



	}

}
