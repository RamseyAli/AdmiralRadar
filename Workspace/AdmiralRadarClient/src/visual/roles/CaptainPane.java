package visual.roles;

import java.awt.Color;
import java.awt.Graphics2D;

import game.Position;
import visual.common.general.MapBasedElement;
import visual.util.operations.GUIController;

public class CaptainPane extends MapBasedElement {

	private Position	start;
	private Object		signal	= new Object();

	public CaptainPane(GUIController cx) {
		super( cx );

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Position getStartLocation() {
		try {
			synchronized (signal) {
				signal.wait();
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return start;
	}

	@Override
	public void clickGridDot(int x, int y) {
		start = new Position( x , y );
		if (!start.isValid()) return;

		synchronized (signal) {
			signal.notify();
		}

		control.setStartLocation( start );
		removeMouseListener( ear );
		removeMouseMotionListener( ear );
		control.threadSafeRepaint( this );

	}

	@Override
	protected void drawPath(Graphics2D g, int x0, int y0, int k, int r, int sp) {


		if ((control.getSpaceship() != null) && (control.getStartLocation() != null))

			for (Position p : getPositionPath(control.getSpaceship().getPath() , control.getStartLocation()))

				g.fillOval( x0 - k * r + ( p.getX() * sp ) , y0 - k * r + ( p.getY() * sp ) , 2 * k * r , 2 * k * r );
		



	}
	
	protected void drawMines(Graphics2D g, int x0, int y0, int k, int r, int sp) {


		if ((control.getSpaceship() != null) && (control.getStartLocation() != null))

			for (Position p : control.getSpaceship().getShipMines().getMines()){

				g.setColor( Color.PINK );
				g.fillOval( x0 - k * r + ( p.getX() * sp ) , y0 - k * r + ( p.getY() * sp ) , 2 * k * r , 2 * k * r );
			}
		
		
		



	}

	@Override
	protected void drawOthers(Graphics2D g, int x0, int y0, int k, int r, int sp2) {
		drawMines(g, x0 , y0 , k , r , sp2);
		
	}

}
