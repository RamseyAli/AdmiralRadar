package visual.roles.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SystemStatusGauge extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final double SCALE = 1.5;

	char name;
	Color col;
	int spaces;
	int charge;

	double rad1 = .7;
	int rad2;

	public SystemStatusGauge(char n, Color c, int s) {
		name = n;
		col = c;
		spaces = s;
		charge = 0;

		addMouseListener( this );

	}

	protected void paintComponent(Graphics gin) {

		int x = getSize().width;
		int y = getSize().height;
		int x_ctr = x / 2;
		int y_ctr = y / 2;
		// rads are actually diams
		rad2 = (int) ( Math.min( x , y ) / SCALE );

		Graphics2D g = (Graphics2D) gin;
		super.paintComponent( g );

		g.setColor( Color.GRAY );
		g.fillOval( x_ctr - rad2 / 2 , y_ctr - rad2 / 2 , rad2 , rad2 );

		for (int i = 0; i < spaces; i++) {

			if (charge > i)
				g.setColor( Color.BLACK );
			else g.setColor( Color.WHITE );
			g.fillArc( x_ctr - rad2 / 2 , y_ctr - rad2 / 2 , rad2 , rad2 , 89 - ( 45 * i ) , -43 );

		}
		g.setColor( col );
		g.fillOval( (int) ( x_ctr - rad1 * rad2 / 2 ) , (int) ( y_ctr - rad1 * rad2 / 2 ) , (int) ( rad1 * rad2 ) ,
				(int) ( rad1 * rad2 ) );

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		rad2 = (int) ( Math.min( getSize().width , getSize().height ) / SCALE );

		System.out.println( e.getPoint() );

		if (new Ellipse2D.Double( ( getSize().width / 2 ) - rad1 * rad2 / 2 ,
				( getSize().height / 2 ) - rad1 * rad2 / 2 , rad1 * rad2 , rad1 * rad2 ).contains( e.getPoint() )) {
			if (charge < spaces) charge++;

			SwingUtilities.invokeLater( new Runnable() {
				public void run() {
					repaint();
				}
			} );
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
