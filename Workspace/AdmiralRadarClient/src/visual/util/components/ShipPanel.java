package visual.util.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import visual.util.operations.GUIController;

public abstract class ShipPanel extends JPanel {

	public static final long serialVersionUID = 1L;
	protected GUIController control;
	protected Color background;
	protected Graphics2D g;

	public ShipPanel(GUIController ctr) {

		control = ctr;
		setBorder( BorderFactory.createEmptyBorder( 10 , 10 , 10 , 10 ) );
		setOpaque( false );

	}

	protected void paintComponent(Graphics gin) {
		g = (Graphics2D) gin;
		super.paintComponent( g );

		draw();

	}

	public void defaultDraw(Color A, Color B) {}

	public abstract void draw();

	protected void swell(Rectangle2D r_outer, double dx, double dy) {
		r_outer.setRect( r_outer.getX() - dx / 2 , r_outer.getY() - dy / 2 , r_outer.getWidth() + dx ,
				r_outer.getHeight() + dy );
		;

	}

}
