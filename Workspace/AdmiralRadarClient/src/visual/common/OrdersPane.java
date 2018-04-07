package visual.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

import game.Direction;
import pref.VisualPreferences;
import visual.util.ColorPallate;
import visual.util.components.ShipPanel;
import visual.util.operations.GUIController;

public class OrdersPane extends ShipPanel implements MouseInputListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RoundRectangle2D north, south, east, west;
	private boolean[] overlay;

	/**
	 * 
	 */

	public OrdersPane(GUIController cx) {
		super( cx );

	}

	private static boolean[] directionToBooleans(Direction d) {
		switch (d) {
			case NORTH:
				return new boolean[] { true , false , false , false };
			case SOUTH:
				return new boolean[] { false , true , false , false };
			case EAST:
				return new boolean[] { false , false , true , false };
			case WEST:
				return new boolean[] { false , false , false , true };
			case STOP:
				return new boolean[] { false , false , false , false };
			default:
				return new boolean[] { false , false , false , false };
		}
	}

	@Override
	public void draw() {

		// Display Math

		int w_o = getWidth();
		int w_m = (int) ( w_o * ( 5.0 / 6 ) );
		int w = w_m - 2 * VisualPreferences.GENERAL_BORDER;
		int h = getHeight();

		int sysWidth = w / 2; // .5
		int sysHeight = (int) ( h / 14 );
		int sysYGap = sysHeight / 2;

		int sysX = (int) ( ( w * 0.125 ) / 2.0 );

		int sysCautR = sysHeight / 2;
		int sysCautL = sysCautR - VisualPreferences.SYS_CAUTION_B;
		int sysCautX = w - sysCautR;

		int compSquare = (int) Math.min( w * 0.8 , h * ( 12.0 / 28.0 ) );
		int compRo = compSquare / 2;

		int compX0 = VisualPreferences.GENERAL_BORDER + ( w / 2 );
		int compY0 = (int) ( h - 1.1 * compRo );

		int ls = (int) ( compRo / 1.5 );
		int ss = (int) ( compRo / 4 );
		int tg = (int) ( ss * 0.9 );
		int c = ss / 2;

		// Display Art

		g.setColor( ColorPallate.ORDER_PANEL_BORDER );
		g.fillRect( 0 , 0 , w_m , h );

		g.setColor( ColorPallate.ORDER_PANEL );
		g.fillRect( VisualPreferences.GENERAL_BORDER , VisualPreferences.GENERAL_BORDER , w ,
				h - 2 * VisualPreferences.GENERAL_BORDER );

		g.setColor( ColorPallate.ORDER_COMPASS );
		g.fillOval( compX0 - compRo , compY0 - compRo , 2 * compRo , 2 * compRo );

		// g.setColor(ColorPallate.ORDER_PANEL);
		// g.fillOval(compX0 - compRi, compY0 - compRi, 2*compRi, 2*compRi);

		north = new RoundRectangle2D.Double( compX0 - ss / 2 , compY0 - tg - ls , ss , ls , c , c );
		east = new RoundRectangle2D.Double( compX0 + tg , compY0 - ss / 2 , ls , ss , c , c );
		south = new RoundRectangle2D.Double( compX0 - ss / 2 , compY0 + tg , ss , ls , c , c );
		west = new RoundRectangle2D.Double( compX0 - tg - ls , compY0 - ss / 2 , ls , ss , c , c );

		overlay = directionToBooleans( control.getSpaceship().getDirection() );

		g.setColor( overlay[0] ? Color.WHITE : ColorPallate.ORDER_COMPASS_BUTTON );
		g.fill( north );

		g.setColor( overlay[1] ? Color.WHITE : ColorPallate.ORDER_COMPASS_BUTTON );
		g.fill( south );

		g.setColor( overlay[2] ? Color.WHITE : ColorPallate.ORDER_COMPASS_BUTTON );
		g.fill( east );

		g.setColor( overlay[3] ? Color.WHITE : ColorPallate.ORDER_COMPASS_BUTTON );
		g.fill( west );

		g.setColor( ColorPallate.ORDER_SYSTEM_BOX );
		g.fillRect( VisualPreferences.GENERAL_BORDER + sysX , VisualPreferences.GENERAL_BORDER + sysYGap , sysWidth ,
				sysHeight );
		g.fillRect( VisualPreferences.GENERAL_BORDER + sysX ,
				VisualPreferences.GENERAL_BORDER + sysYGap + 1 * ( sysYGap + sysHeight ) , sysWidth , sysHeight );
		g.fillRect( VisualPreferences.GENERAL_BORDER + sysX ,
				VisualPreferences.GENERAL_BORDER + sysYGap + 2 * ( sysYGap + sysHeight ) , sysWidth , sysHeight );
		g.fillRect( VisualPreferences.GENERAL_BORDER + sysX ,
				VisualPreferences.GENERAL_BORDER + sysYGap + 3 * ( sysYGap + sysHeight ) , sysWidth , sysHeight );
		g.fillRect( VisualPreferences.GENERAL_BORDER + sysX ,
				VisualPreferences.GENERAL_BORDER + sysYGap + 4 * ( sysYGap + sysHeight ) , sysWidth , sysHeight );

		g.setColor( Color.BLACK );
		int sh = g.getFontMetrics().getHeight();
		g.setFont( g.getFont().deriveFont( Font.BOLD ) );

		g.drawString( "Drone" , VisualPreferences.GENERAL_BORDER + sysX + 10 ,
				(int) ( VisualPreferences.GENERAL_BORDER + 0.5 * ( sysYGap + sysHeight ) + ( sh / 3 ) ) );
		g.drawString( "Radar" , VisualPreferences.GENERAL_BORDER + sysX + 10 ,
				(int) ( VisualPreferences.GENERAL_BORDER + 1.5 * ( sysYGap + sysHeight ) + ( sh / 3 ) ) );
		g.drawString( "Missile" , VisualPreferences.GENERAL_BORDER + sysX + 10 ,
				(int) ( VisualPreferences.GENERAL_BORDER + 2.5 * ( sysYGap + sysHeight ) + ( sh / 3 ) ) );
		g.drawString( "Mines" , VisualPreferences.GENERAL_BORDER + sysX + 10 ,
				(int) ( VisualPreferences.GENERAL_BORDER + 3.5 * ( sysYGap + sysHeight ) + ( sh / 3 ) ) );
		g.drawString( "Boosters" , VisualPreferences.GENERAL_BORDER + sysX + 10 ,
				(int) ( VisualPreferences.GENERAL_BORDER + 4.5 * ( sysYGap + sysHeight ) + ( sh / 3 ) ) );

		g.setColor( ColorPallate.ORDER_SYSTEM_BOX );
		g.fillOval( VisualPreferences.GENERAL_BORDER + sysCautX - sysCautR - sysX ,
				VisualPreferences.GENERAL_BORDER + sysYGap + sysHeight / 2 - sysCautR + 0 * ( sysYGap + sysHeight ) ,
				2 * sysCautR , 2 * sysCautR );
		g.fillOval( VisualPreferences.GENERAL_BORDER + sysCautX - sysCautR - sysX ,
				VisualPreferences.GENERAL_BORDER + sysYGap + sysHeight / 2 - sysCautR + 1 * ( sysYGap + sysHeight ) ,
				2 * sysCautR , 2 * sysCautR );
		g.fillOval( VisualPreferences.GENERAL_BORDER + sysCautX - sysCautR - sysX ,
				VisualPreferences.GENERAL_BORDER + sysYGap + sysHeight / 2 - sysCautR + 2 * ( sysYGap + sysHeight ) ,
				2 * sysCautR , 2 * sysCautR );
		g.fillOval( VisualPreferences.GENERAL_BORDER + sysCautX - sysCautR - sysX ,
				VisualPreferences.GENERAL_BORDER + sysYGap + sysHeight / 2 - sysCautR + 3 * ( sysYGap + sysHeight ) ,
				2 * sysCautR , 2 * sysCautR );
		g.fillOval( VisualPreferences.GENERAL_BORDER + sysCautX - sysCautR - sysX ,
				VisualPreferences.GENERAL_BORDER + sysYGap + sysHeight / 2 - sysCautR + 4 * ( sysYGap + sysHeight ) ,
				2 * sysCautR , 2 * sysCautR );

		if (control.getSpaceship() != null) {
			g.setColor( ColorPallate.SENSORY );
			if (control.getSpaceship().getShipSystem().isSystemCharged( "Drone" ))
				g.fillRect( VisualPreferences.GENERAL_BORDER + sysX + VisualPreferences.SYS_BORDER ,
						VisualPreferences.GENERAL_BORDER + sysYGap + VisualPreferences.SYS_BORDER ,
						sysWidth - 2 * VisualPreferences.SYS_BORDER , sysHeight - 2 * VisualPreferences.SYS_BORDER );

			if (control.getSpaceship().getShipSystem().isSystemCharged( "Radar" ))
				g.fillRect( VisualPreferences.GENERAL_BORDER + sysX + VisualPreferences.SYS_BORDER ,
						VisualPreferences.GENERAL_BORDER + sysYGap + VisualPreferences.SYS_BORDER
								+ 1 * ( sysYGap + sysHeight ) ,
						sysWidth - 2 * VisualPreferences.SYS_BORDER , sysHeight - 2 * VisualPreferences.SYS_BORDER );

			g.setColor( ColorPallate.TACTICAL );
			// if
			// (control.getSpaceship().getShipSystem().isSystemCharged("Missile"))
			g.fillRect( VisualPreferences.GENERAL_BORDER + sysX + VisualPreferences.SYS_BORDER ,
					VisualPreferences.GENERAL_BORDER + sysYGap + VisualPreferences.SYS_BORDER
							+ 2 * ( sysYGap + sysHeight ) ,
					sysWidth - 2 * VisualPreferences.SYS_BORDER , sysHeight - 2 * VisualPreferences.SYS_BORDER );

			if (control.getSpaceship().getShipSystem().isSystemCharged( "Mine" ))
				g.fillRect( VisualPreferences.GENERAL_BORDER + sysX + VisualPreferences.SYS_BORDER ,
						VisualPreferences.GENERAL_BORDER + sysYGap + VisualPreferences.SYS_BORDER
								+ 3 * ( sysYGap + sysHeight ) ,
						sysWidth - 2 * VisualPreferences.SYS_BORDER , sysHeight - 2 * VisualPreferences.SYS_BORDER );

			g.setColor( ColorPallate.UTILITY );
			// if
			// (control.getSpaceship().getShipSystem().isSystemCharged("Silent"))
			g.fillRect( VisualPreferences.GENERAL_BORDER + sysX + VisualPreferences.SYS_BORDER ,
					VisualPreferences.GENERAL_BORDER + sysYGap + VisualPreferences.SYS_BORDER
							+ 4 * ( sysYGap + sysHeight ) ,
					sysWidth - 2 * VisualPreferences.SYS_BORDER , sysHeight - 2 * VisualPreferences.SYS_BORDER );

			g.setColor( ColorPallate.ORDER_SYSTEM_CAUTION );

			if (control.getSpaceship().getShipSystem().isSystemDestroyed( "Drone" )) g.fillOval(
					VisualPreferences.GENERAL_BORDER + sysCautX - sysCautL - sysX , VisualPreferences.GENERAL_BORDER
							+ sysYGap + sysHeight / 2 - sysCautL + 0 * ( sysYGap + sysHeight ) ,
					2 * sysCautL , 2 * sysCautL );

			// if
			// (control.getSpaceship().getShipSystem().isSystemDestroyed("Radar"))
			g.fillOval(
					VisualPreferences.GENERAL_BORDER + sysCautX - sysCautL - sysX , VisualPreferences.GENERAL_BORDER
							+ sysYGap + sysHeight / 2 - sysCautL + 1 * ( sysYGap + sysHeight ) ,
					2 * sysCautL , 2 * sysCautL );

			if (control.getSpaceship().getShipSystem().isSystemDestroyed( "Missile" )) g.fillOval(
					VisualPreferences.GENERAL_BORDER + sysCautX - sysCautL - sysX , VisualPreferences.GENERAL_BORDER
							+ sysYGap + sysHeight / 2 - sysCautL + 2 * ( sysYGap + sysHeight ) ,
					2 * sysCautL , 2 * sysCautL );

			// if
			// (control.getSpaceship().getShipSystem().isSystemDestroyed("Mine"))
			g.fillOval(
					VisualPreferences.GENERAL_BORDER + sysCautX - sysCautL - sysX , VisualPreferences.GENERAL_BORDER
							+ sysYGap + sysHeight / 2 - sysCautL + 3 * ( sysYGap + sysHeight ) ,
					2 * sysCautL , 2 * sysCautL );

			if (control.getSpaceship().getShipSystem().isSystemDestroyed( "Silent" )) g.fillOval(
					VisualPreferences.GENERAL_BORDER + sysCautX - sysCautL - sysX , VisualPreferences.GENERAL_BORDER
							+ sysYGap + sysHeight / 2 - sysCautL + 4 * ( sysYGap + sysHeight ) ,
					2 * sysCautL , 2 * sysCautL );
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (north.contains( e.getPoint() )) {
			System.out.println( "North" );
		} else if (south.contains( e.getPoint() )) {
			System.out.println( "South" );
		} else if (east.contains( e.getPoint() )) {
			System.out.println( "East" );
		} else if (west.contains( e.getPoint() )) {
			System.out.println( "West" );
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (north.contains( e.getPoint() )) {
			overlay[0] = true;
			overlay[1] = false;
			overlay[2] = false;
			overlay[3] = false;
		} else if (south.contains( e.getPoint() )) {
			overlay[0] = false;
			overlay[1] = true;
			overlay[2] = false;
			overlay[3] = false;
		} else if (east.contains( e.getPoint() )) {
			overlay[0] = false;
			overlay[1] = false;
			overlay[2] = true;
			overlay[3] = false;
		} else if (west.contains( e.getPoint() )) {
			overlay[0] = false;
			overlay[1] = false;
			overlay[2] = false;
			overlay[3] = true;
		} else {
			overlay[0] = false;
			overlay[1] = false;
			overlay[2] = false;
			overlay[3] = false;
		}

		SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				repaint();
			}
		} );

	}

	public void setup() {
		switch (control.getRole()) {
			case CAPTAIN:
				addMouseListener( this );
				addMouseMotionListener( this );
				break;
			case ENGINE:
			case FIRST:
			case RADIO:

				break;
			default:
				break;
		}

	}

}
