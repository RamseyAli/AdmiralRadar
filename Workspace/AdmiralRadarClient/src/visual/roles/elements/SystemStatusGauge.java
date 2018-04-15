package visual.roles.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import game.Systems;
import visual.util.ColorPallate;
import visual.util.operations.GUIController;

public class SystemStatusGauge extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private static final double	SCALE				= 1.5;

	Systems	name;
	Color	col;
	int		spaces;
	int		charge;

	double	rad1	= .7;
	int		rad2;
	Arc2D za;
	Ellipse2D zb;
	GUIController ctrl;

	public SystemStatusGauge(Systems n, Color c, int s) {
		name = n;
		col = c;
		spaces = s;
		charge = 0;

	//	addMouseListener( this );
		setOpaque( false );
		setBackground(ColorPallate.INVISIBLE);

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

		zb = new Ellipse2D.Double( (int) ( x_ctr - rad1 * rad2 / 2 ) , (int) ( y_ctr - rad1 * rad2 / 2 ) , (int) ( rad1 * rad2 ) ,
				(int) ( rad1 * rad2 ) );
		
		g.setColor( ColorPallate.EXEC_SYSTEM_DIAL );
		g.fillOval( x_ctr - rad2 / 2 , y_ctr - rad2 / 2 , rad2 , rad2 );

		for (int i = 0; i < spaces; i++) {

			if (ctrl.getSpaceship().getShipSystem().getPowerLevel( name ) > i)
				g.setColor( ColorPallate.EXEC_SYSTEM_YES );
			else g.setColor( ColorPallate.EXEC_SYSTEM_NO );
			za = new Arc2D.Double( x_ctr - rad2 / 2.0 , y_ctr - rad2 / 2.0 , 1.0*rad2 , 1.0*rad2 , 89.0 - ( 45.0 * i ) , -43.0 , Arc2D.PIE);
			
			Area ax = new Area(za);
			Area bx = new Area(zb);
			ax.subtract( bx );
			g.fill(ax);

		}
		g.setColor( col );
		g.fill(zb);

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		rad2 = (int) ( Math.min( getSize().width , getSize().height ) / SCALE );

		if (new Ellipse2D.Double( ( getSize().width / 2 ) - rad1 * rad2 / 2 ,
				( getSize().height / 2 ) - rad1 * rad2 / 2 , rad1 * rad2 , rad1 * rad2 ).contains( e.getPoint() )) {
			if (! ctrl.getSpaceship().getShipSystem().isSystemCharged( name ) ) 
				ctrl.charge(name);

			SwingUtilities.invokeLater( new Runnable() {
				public void run() {
					repaint();
				}
			} );
		}
		
		removeMouseListener(this);

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

	public void addController(GUIController control) {
		ctrl = control;
		
	}
	
	public void activateGuageInteraction(){
		addMouseListener(this);
	}

}
