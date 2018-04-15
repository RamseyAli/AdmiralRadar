package visual.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

import game.Direction;
import game.Role;
import game.Systems;
import pref.GamePreferences;
import pref.VisualPreferences;
import visual.util.ColorPallate;
import visual.util.components.ShipPanel;
import visual.util.operations.GUIController;

public class OrdersPane extends ShipPanel implements MouseInputListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RoundRectangle2D	north, south, east, west;
	private Rectangle droneBox , radarBox, missleBox , dropMine, blastMineBox, boostBox, walkBox, cruiseBox;
	private Ellipse2D	compass;
	private boolean[]			overlay = {false, false, false, false};
	private Polygon bigMineArrow, litMineArrow , bigDroneArrow, litDroneArrow, bigBoostArrow, litBoostArrow;
	int selectedMine = 0;
	int droneSector = 0;
	int boostPower = 4;

	int phaseOfTurn = 0;
	boolean commandHasBeenSent = false;
	// 0 - Place Ship (Captain)
	// 1 = Special Action (Captain)
	// 2 - Pick Direction / Charge System / Destructor (All)
	// 3 - Waiting! (All)

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
		int sysHeight = (int) ( h / 20 );
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


		compass = new Ellipse2D.Double( compX0 - compRo , compY0 - compRo , 2 * compRo , 2 * compRo );
		north = new RoundRectangle2D.Double( compX0 - ss / 2 , compY0 - tg - ls , ss , ls , c , c );
		east = new RoundRectangle2D.Double( compX0 + tg , compY0 - ss / 2 , ls , ss , c , c );
		south = new RoundRectangle2D.Double( compX0 - ss / 2 , compY0 + tg , ss , ls , c , c );
		west = new RoundRectangle2D.Double( compX0 - tg - ls , compY0 - ss / 2 , ls , ss , c , c );


		int B = 2;
		int W = 10;
		int H = sysHeight;
		int hp = (B*H) / (2 * W);
		int xp = (int) ( H*Math.sqrt( (hp * hp) + (B * B) ) / (2*W) );


		int xtB =  VisualPreferences.GENERAL_BORDER*2 + sysX + sysWidth;
		int ytB = VisualPreferences.GENERAL_BORDER + sysYGap + 4 * ( sysYGap + sysHeight );
		int ytB4 = VisualPreferences.GENERAL_BORDER + sysYGap + 0 * ( sysYGap + sysHeight );
		int ytB5 = VisualPreferences.GENERAL_BORDER + sysYGap + 5 * ( sysYGap + sysHeight );

		int[] xp1 = {xtB ,
				xtB ,
				xtB + W};
		int[] yp1 = {ytB , ytB + sysHeight , ytB + sysHeight / 2};

		int[] xp2 = {xp1[0] + B ,
				xp1[1] + B ,
				xp1[2] - xp};
		int[] yp2 = {yp1[0] + 2*hp , yp1[1] - 2*hp , yp1[2] };	

		int[] yd1 = {ytB4 , ytB4 + sysHeight , ytB4 + sysHeight / 2};
		int[] yd2 = {yp1[0] + 2*hp , yp1[1] - 2*hp , yp1[2] };

		int[] yb1 = {ytB5 , ytB5 + sysHeight , ytB5 + sysHeight / 2};
		int[] yb2 = {yp1[0] + 2*hp , yp1[1] - 2*hp , yp1[2] };

		bigMineArrow = new Polygon(xp1 , yp1 , 3);
		litMineArrow = new Polygon(xp2 , yp2 , 3);

		bigDroneArrow = new Polygon(xp1 , yd1 , 3);
		litDroneArrow = new Polygon(xp2 , yd2 , 3);

		bigBoostArrow = new Polygon(xp1 , yb1 , 3);
		litBoostArrow = new Polygon(xp2 , yb2 , 3);



		// Display Art

		g.setColor( ColorPallate.ORDER_PANEL_BORDER );
		g.fillRect( 0 , 0 , w_m , h );

		g.setColor( ColorPallate.ORDER_PANEL );
		g.fillRect( VisualPreferences.GENERAL_BORDER , VisualPreferences.GENERAL_BORDER , w ,
				h - 2 * VisualPreferences.GENERAL_BORDER );

		g.setColor( ColorPallate.ORDER_COMPASS );
		g.fill(compass);

		if (control.getRole() != Role.CAPTAIN) overlay = directionToBooleans( control.getSpaceship().getDirection() );
		g.setColor( overlay[0] ? Color.WHITE : ColorPallate.ORDER_COMPASS_BUTTON );
		g.fill( north );

		g.setColor( overlay[1] ? Color.WHITE : ColorPallate.ORDER_COMPASS_BUTTON );
		g.fill( south );

		g.setColor( overlay[2] ? Color.WHITE : ColorPallate.ORDER_COMPASS_BUTTON );
		g.fill( east );

		g.setColor( overlay[3] ? Color.WHITE : ColorPallate.ORDER_COMPASS_BUTTON );
		g.fill( west );

		g.setColor( ColorPallate.ORDER_SYSTEM_BOX );

		droneBox = new Rectangle( VisualPreferences.GENERAL_BORDER + sysX , VisualPreferences.GENERAL_BORDER + sysYGap , sysWidth ,
				sysHeight );
		radarBox = new Rectangle( VisualPreferences.GENERAL_BORDER + sysX ,
				VisualPreferences.GENERAL_BORDER + sysYGap + 1 * ( sysYGap + sysHeight ) , sysWidth , sysHeight );
		missleBox = new Rectangle( VisualPreferences.GENERAL_BORDER + sysX ,
				VisualPreferences.GENERAL_BORDER + sysYGap + 2 * ( sysYGap + sysHeight ) , sysWidth , sysHeight );
		dropMine = new Rectangle( VisualPreferences.GENERAL_BORDER + sysX ,
				VisualPreferences.GENERAL_BORDER + sysYGap + 3 * ( sysYGap + sysHeight ) , sysWidth , sysHeight );
		blastMineBox = new Rectangle( VisualPreferences.GENERAL_BORDER + sysX ,
				VisualPreferences.GENERAL_BORDER + sysYGap + 4 * ( sysYGap + sysHeight ) , sysWidth , sysHeight );
		boostBox = new Rectangle( VisualPreferences.GENERAL_BORDER + sysX ,
				VisualPreferences.GENERAL_BORDER + sysYGap + 5 * ( sysYGap + sysHeight ) , sysWidth , sysHeight );
		walkBox = new Rectangle( VisualPreferences.GENERAL_BORDER + sysX ,
				VisualPreferences.GENERAL_BORDER + sysYGap + 6 * ( sysYGap + sysHeight ) , sysWidth , sysHeight );
		cruiseBox = new Rectangle( VisualPreferences.GENERAL_BORDER + sysX ,
				VisualPreferences.GENERAL_BORDER + sysYGap + 7 * ( sysYGap + sysHeight ) , sysWidth , sysHeight );

		g.fillPolygon( bigMineArrow );
		g.fillPolygon( bigDroneArrow );
		g.fillPolygon( bigBoostArrow );
		g.fill( droneBox );
		g.fill( radarBox );
		g.fill( missleBox );
		g.fill( dropMine );
		g.fill( blastMineBox );
		g.fill( boostBox );
		g.fill( walkBox );
		g.fill( cruiseBox );

		g.setColor( Color.WHITE );
		int sh = g.getFontMetrics().getHeight();
		g.setFont( g.getFont().deriveFont( Font.BOLD ) );

		g.drawString( "Drone Sec" + droneSector , VisualPreferences.GENERAL_BORDER + sysX + 10 ,
				(int) ( VisualPreferences.GENERAL_BORDER + 0.75 * ( sysYGap + sysHeight ) + ( sh / 3 ) ) );
		g.drawString( "Radar" , VisualPreferences.GENERAL_BORDER + sysX + 10 ,
				(int) ( VisualPreferences.GENERAL_BORDER + 1.75 * ( sysYGap + sysHeight ) + ( sh / 3 ) ) );
		g.drawString( "Missile" , VisualPreferences.GENERAL_BORDER + sysX + 10 ,
				(int) ( VisualPreferences.GENERAL_BORDER + 2.75 * ( sysYGap + sysHeight ) + ( sh / 3 ) ) );
		g.drawString( "Drop Mine" , VisualPreferences.GENERAL_BORDER + sysX + 10 ,
				(int) ( VisualPreferences.GENERAL_BORDER + 3.75 * ( sysYGap + sysHeight ) + ( sh / 3 ) ) );
		g.drawString( "Blast Mine" + selectedMine , VisualPreferences.GENERAL_BORDER + sysX + 10 ,
				(int) ( VisualPreferences.GENERAL_BORDER + 4.75 * ( sysYGap + sysHeight ) + ( sh / 3 ) ) );
		g.drawString( "Boost " + boostPower, VisualPreferences.GENERAL_BORDER + sysX + 10 ,
				(int) ( VisualPreferences.GENERAL_BORDER + 5.75 * ( sysYGap + sysHeight ) + ( sh / 3 ) ) );
		g.drawString( "Spacewalk" , VisualPreferences.GENERAL_BORDER + sysX + 10 ,
				(int) ( VisualPreferences.GENERAL_BORDER + 6.75 * ( sysYGap + sysHeight ) + ( sh / 3 ) ) );
		g.drawString( "Cruise" , VisualPreferences.GENERAL_BORDER + sysX + 10 ,
				(int) ( VisualPreferences.GENERAL_BORDER + 7.75 * ( sysYGap + sysHeight ) + ( sh / 3 ) ) );

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
				(int) (VisualPreferences.GENERAL_BORDER + sysYGap + sysHeight / 2 - sysCautR + 3 * ( sysYGap + sysHeight )) ,
				2 * sysCautR , 2 * sysCautR );
		g.fillOval( VisualPreferences.GENERAL_BORDER + sysCautX - sysCautR - sysX ,
				VisualPreferences.GENERAL_BORDER + sysYGap + sysHeight / 2 - sysCautR + 5 * ( sysYGap + sysHeight ) ,
				2 * sysCautR , 2 * sysCautR );

		if (control.getSpaceship() != null) {
			g.setColor( ColorPallate.SENSORY );
			if (control.getSpaceship().getShipSystem().isSystemCharged( Systems.DRONE )){
				g.fillRect( VisualPreferences.GENERAL_BORDER + sysX + VisualPreferences.SYS_BORDER ,
						VisualPreferences.GENERAL_BORDER + sysYGap + VisualPreferences.SYS_BORDER ,
						sysWidth - 2 * VisualPreferences.SYS_BORDER , sysHeight - 2 * VisualPreferences.SYS_BORDER );

				g.fillPolygon(litMineArrow);
			}
			if (control.getSpaceship().getShipSystem().isSystemCharged( Systems.RADAR ))
				g.fillRect( VisualPreferences.GENERAL_BORDER + sysX + VisualPreferences.SYS_BORDER ,
						VisualPreferences.GENERAL_BORDER + sysYGap + VisualPreferences.SYS_BORDER
						+ 1 * ( sysYGap + sysHeight ) ,
						sysWidth - 2 * VisualPreferences.SYS_BORDER , sysHeight - 2 * VisualPreferences.SYS_BORDER );

			g.setColor( ColorPallate.TACTICAL );
			if (control.getSpaceship().getShipSystem().isSystemCharged(Systems.MISSILE))
				g.fillRect( VisualPreferences.GENERAL_BORDER + sysX + VisualPreferences.SYS_BORDER ,
						VisualPreferences.GENERAL_BORDER + sysYGap + VisualPreferences.SYS_BORDER
						+ 2 * ( sysYGap + sysHeight ) ,
						sysWidth - 2 * VisualPreferences.SYS_BORDER , sysHeight - 2 * VisualPreferences.SYS_BORDER );


			if (control.getSpaceship().getShipSystem().isSystemCharged( Systems.MINE ))
				g.fillRect( VisualPreferences.GENERAL_BORDER + sysX + VisualPreferences.SYS_BORDER ,
						(int) (VisualPreferences.GENERAL_BORDER + sysYGap + VisualPreferences.SYS_BORDER
								+ 3 * ( sysYGap + sysHeight ) ),
						sysWidth - 2 * VisualPreferences.SYS_BORDER , sysHeight - 2 * VisualPreferences.SYS_BORDER );

			if (control.getSpaceship().getShipSystem().isSystemCharged( Systems.MINE )){
				g.fillRect( VisualPreferences.GENERAL_BORDER + sysX + VisualPreferences.SYS_BORDER ,
						(int) (VisualPreferences.GENERAL_BORDER + sysYGap + VisualPreferences.SYS_BORDER
								+ 4 * ( sysYGap + sysHeight ) ),
						sysWidth - 2 * VisualPreferences.SYS_BORDER , sysHeight - 2 * VisualPreferences.SYS_BORDER );


				g.fillPolygon(litMineArrow);

			}
			g.setColor( ColorPallate.UTILITY );

			if (control.getSpaceship().getShipSystem().isSystemCharged(Systems.BOOST)){
				g.fillRect( VisualPreferences.GENERAL_BORDER + sysX + VisualPreferences.SYS_BORDER ,
						VisualPreferences.GENERAL_BORDER + sysYGap + VisualPreferences.SYS_BORDER
						+ 5 * ( sysYGap + sysHeight ) ,
						sysWidth - 2 * VisualPreferences.SYS_BORDER , sysHeight - 2 * VisualPreferences.SYS_BORDER );
				g.fillPolygon(litBoostArrow);
			}
			g.setColor( ColorPallate.ORDER_SYSTEM_CAUTION );

			if (control.getSpaceship().getShipSystem().isSystemDestroyed( Systems.DRONE )) g.fillOval(
					VisualPreferences.GENERAL_BORDER + sysCautX - sysCautL - sysX , VisualPreferences.GENERAL_BORDER
					+ sysYGap + sysHeight / 2 - sysCautL + 0 * ( sysYGap + sysHeight ) ,
					2 * sysCautL , 2 * sysCautL );

			if (control.getSpaceship().getShipSystem().isSystemDestroyed(Systems.RADAR))
				g.fillOval(
						VisualPreferences.GENERAL_BORDER + sysCautX - sysCautL - sysX , VisualPreferences.GENERAL_BORDER
						+ sysYGap + sysHeight / 2 - sysCautL + 1 * ( sysYGap + sysHeight ) ,
						2 * sysCautL , 2 * sysCautL );

			if (control.getSpaceship().getShipSystem().isSystemDestroyed( Systems.MISSILE )) g.fillOval(
					VisualPreferences.GENERAL_BORDER + sysCautX - sysCautL - sysX , VisualPreferences.GENERAL_BORDER
					+ sysYGap + sysHeight / 2 - sysCautL + 2 * ( sysYGap + sysHeight ) ,
					2 * sysCautL , 2 * sysCautL );

			if (control.getSpaceship().getShipSystem().isSystemDestroyed(Systems.MINE)){
				g.fillOval(
						VisualPreferences.GENERAL_BORDER + sysCautX - sysCautL - sysX , (int) (VisualPreferences.GENERAL_BORDER
								+ sysYGap + sysHeight / 2 - sysCautL + 3 * ( sysYGap + sysHeight )) ,
						2 * sysCautL , 2 * sysCautL );

			}
			if (control.getSpaceship().getShipSystem().isSystemDestroyed( Systems.BOOST )) g.fillOval(
					VisualPreferences.GENERAL_BORDER + sysCautX - sysCautL - sysX , VisualPreferences.GENERAL_BORDER
					+ sysYGap + sysHeight / 2 - sysCautL + 4 * ( sysYGap + sysHeight ) ,
					2 * sysCautL , 2 * sysCautL );
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (compass.contains( e.getPoint() ) && (phaseOfTurn == 2)){
			if (north.contains( e.getPoint() )) {
				removeMouseMotionListener( this );
				control.flyInDirection(Direction.NORTH);
				phaseOfTurn = 0;
			} else if (south.contains( e.getPoint() )) {
				removeMouseMotionListener( this );
				phaseOfTurn = 0;
				control.flyInDirection(Direction.SOUTH);
			} else if (east.contains( e.getPoint() )) {
				removeMouseMotionListener( this );
				phaseOfTurn = 0;
				control.flyInDirection(Direction.EAST);
			} else if (west.contains( e.getPoint() )) {
				removeMouseMotionListener( this );
				phaseOfTurn = 0;
				control.flyInDirection(Direction.WEST);
			}


		}
		else if (phaseOfTurn == 1){

			if( bigMineArrow.contains( e.getPoint() )){
				increaseSelectedMine();
			} else if ( bigMineArrow.contains( e.getPoint() )){
				increaseSelectedDroneSector();
			} else if ( bigMineArrow.contains( e.getPoint() )){
				increaseBoostPower();
			} 

			else if 		(droneBox.contains( e.getPoint() )){
				commandHasBeenSent = true;
				phaseOfTurn = 2;
				Systems x =  Systems.DRONE;
				x.setPayload( "" + droneSector );
				control.specialAction(x);
			}
			else if (radarBox.contains( e.getPoint() )){
				commandHasBeenSent = true;
				phaseOfTurn = 2;
				control.specialAction(Systems.RADAR);
			}
			else if (missleBox.contains( e.getPoint() )){
				commandHasBeenSent = true;
				phaseOfTurn = 2;
				Systems x =  Systems.MISSILE;
				x.setPayload( "" + JOptionPane.showInputDialog(this, "MISSILE COORDINATES:") );
				control.specialAction(x);
			}
			else if (dropMine.contains( e.getPoint() )){
				commandHasBeenSent = true;
				phaseOfTurn = 2;
				Systems x =  Systems.MINE;
				x.setPayload("Drop " + JOptionPane.showInputDialog(this, "DROP MINE COORDINATES:"));
				control.specialAction(x);
			}
			else if (blastMineBox.contains( e.getPoint() )){
				commandHasBeenSent = true;
				phaseOfTurn = 2;
				Systems x =  Systems.MINE;
				x.setPayload( "Blast " + + selectedMine );
				control.specialAction(x);
			}
			else if (boostBox.contains( e.getPoint() )){
				commandHasBeenSent = true;
				phaseOfTurn = 2;
				Systems x =  Systems.BOOST;
				x.setPayload( "Boost " + boostPower );
				control.specialAction(x);
			}
			else if (walkBox.contains( e.getPoint() )){
				commandHasBeenSent = true;
				phaseOfTurn = 2;
				control.specialAction(Systems.SPACEWALK);
			}

			else if (cruiseBox.contains( e.getPoint() )){
				commandHasBeenSent = true;
				phaseOfTurn = 2;
				control.specialAction(Systems.SCENARIO);
			}
		}

	}

	private void increaseSelectedDroneSector() {
		droneSector++;
		if (droneSector > (GamePreferences.SEG * GamePreferences.SEG)) droneSector = 0;

	}

	private void increaseBoostPower() {
		boostPower++;
		if (boostPower > 4) boostPower = 0;

	}

	private int getDroneSectorNumber() {
		return droneSector;
	}

	private void increaseSelectedMine() {
		selectedMine++;
		if (selectedMine > control.getSpaceship().getShipMines().getMines().size()) selectedMine = 0;
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
		if (!compass.contains( e.getPoint() )) return;


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
				System.out.println( control.getSpaceship().getPosition() );
				if (!control.getSpaceship().getPosition().isValid()){
					System.out.println( "Captain Window Turn Start --------------------------------------------NOT" );
					phaseOfTurn = 0;
					commandHasBeenSent = false;

				} else{ //Position is set, so EITHER we need to send a command (1) or direction (2)
					System.out.println( "Captain Window Turn Start --------------------------------------------YEP" );
					if (!commandHasBeenSent){
						phaseOfTurn = 1;
						addMouseMotionListener( this );
					} else phaseOfTurn = 2;
					addMouseListener( this );
					
				}
				System.out.println( "Captain Window Turn Start --------------------------------------------YE" + phaseOfTurn );
				break;
			case ENGINE:
				break;
			case FIRST:
				break;
			case NETWORK:
				break;
			case RADIO:
				break;
			default:
				break;
		}

	}

	public void setState(int i)
	{
		phaseOfTurn = i;
	}

}
