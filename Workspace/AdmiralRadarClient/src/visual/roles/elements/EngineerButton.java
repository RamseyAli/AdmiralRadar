package visual.roles.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class EngineerButton extends Ellipse2D.Double {

	private Color				col;
	private Color				sys;
	private boolean				selected;
	private static final long	serialVersionUID	= 1L;

	public EngineerButton(double x, double y, double r, Color c, Color s, boolean b) {
		super( x , y , 2 * r , 2 * r );
		col = c;
		sys = s;
		selected = b;
	}

	public void draw(Graphics2D g) {

		int rad = (int) ( height / 2.0f );
		int rad2 = (int) ( height / 2.25f );
		int rad3 = (int) ( height / 2.5f );
		int x_o = (int) x + rad;
		int y_o = (int) y + rad;

		if (sys != null) {
			g.setColor( sys );
			g.fillOval( x_o - rad , y_o - rad , 2 * rad , 2 * rad );
		}

		g.setColor( col );
		g.fillOval( x_o - rad2 , y_o - rad2 , 2 * rad2 , 2 * rad2 );

		if (!selected) return;
		g.setColor( Color.DARK_GRAY );
		g.fillOval( x_o - rad3 , y_o - rad3 , 2 * rad3 , 2 * rad3 );

		System.out.println( "beep" );
	}

	public void click() {
		selected = !selected;
		System.out.println( "SWAP" + selected );

	}

}
