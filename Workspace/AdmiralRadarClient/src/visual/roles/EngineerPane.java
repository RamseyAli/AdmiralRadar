package visual.roles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

import javax.swing.SwingUtilities;

import visual.util.ColorPallate;
import visual.util.components.ShipPanel;
import visual.util.operations.GUIController;

public class EngineerPane extends ShipPanel implements MouseListener {

	private float	A	= 0.5f;
	private float	B	= 0.5f;

	private int margin = 10;

	private int[][]	blue	= new int[2][6];
	private int[][]	orange	= new int[2][5];
	private int[][]	grey	= new int[2][3];
	private Ellipse2D[] buttonAreas = new Ellipse2D[24];

	Rectangle	west;
	Rectangle	north;
	Rectangle	south;
	Rectangle	east;

	private boolean[] buttons = new boolean[24];

	public EngineerPane(GUIController cx) {
		super( cx );

		for (int i = 0; i < 24; i++)
			buttons[i] = true;

		addMouseListener( this );

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void draw() {

		if (control.getSpaceship() != null)
			buttons = control.getSpaceship().getShipSystem().getPartsStatus();

		int x = getSize().width;
		int y = getSize().height;

		int boxW = (int) ( ( x ) / 4 );
		int boxH = (int) ( y / 1.2 );

		west = new Rectangle( margin , ( y - boxH ) / 2 , boxW - 2 * margin , boxH );
		north = new Rectangle( margin + ( x ) / 4 , ( y - boxH ) / 2 , boxW - 2 * margin , boxH );
		south = new Rectangle( margin + ( 2 * x ) / 4 , ( y - boxH ) / 2 , boxW - 2 * margin , boxH );
		east = new Rectangle( margin + ( 3 * x ) / 4 , ( y - boxH ) / 2 , boxW - 2 * margin , boxH );

		g.setColor( ColorPallate.ENGINEER_DIRECTION_BOX );
		g.fill( west );
		g.fill( north );
		g.fill( south );
		g.fill( east );

		calculateSystemLines( west , new Rectangle( west.x , west.y , 4 * west.width + 6 * margin , west.height ) );

		g.setStroke( new BasicStroke( 5 ) );
		g.setColor( ColorPallate.ENGR_CIRCUIT_WWWE );
		g.drawPolyline( blue[0] , blue[1] , 6 );
		g.setColor( ColorPallate.ENGR_CIRCUIT_NNNE );
		g.drawPolyline( orange[0] , orange[1] , 5 );
		g.setColor( ColorPallate.ENGR_CIRCUIT_SSSE );
		g.drawPolyline( grey[0] , grey[1] , 3 );

		drawWestBox( west );
		drawNorthBox( north );
		drawSouthBox( south );
		drawEastBox( east );

	}

	private void calculateSystemLines(Rectangle west, Rectangle big) {

		int a = (int) ( west.width / ( 2 * ( A + 1 ) ) );
		int b = (int) ( west.height / ( 2 * ( B + 1 ) ) );
		int ap = (int) ( A * a );
		int bp = (int) ( B * b );

		// Blue X
		blue[0][0] = west.x + ap + a * 2;
		blue[0][1] = west.x + ap + a * 2;
		blue[0][2] = west.x + ap;
		blue[0][3] = west.x + ap;
		blue[0][4] = west.x + big.width - ap;
		blue[0][5] = west.x + big.width - ap;

		// Blue Y
		blue[1][0] = west.y + bp + b;
		blue[1][1] = west.y + bp;
		blue[1][2] = west.y + bp;
		blue[1][3] = west.y + bp + (int) ( 1.5 * b );
		blue[1][4] = west.y + bp + (int) ( 1.5 * b );
		blue[1][5] = west.y + bp + b;

		int orangeOffset = west.width + 3 * margin;
		// Orange X
		orange[0][0] = orangeOffset + ap + 2 * a;
		orange[0][1] = orangeOffset + ap;
		orange[0][2] = orangeOffset + ap;
		orange[0][3] = 3 * orangeOffset + ap - 2 * margin;
		orange[0][4] = 3 * orangeOffset + ap - 2 * margin;

		// Orange Y
		orange[1][0] = west.y + bp + b;
		orange[1][1] = west.y + bp + b;
		orange[1][2] = west.y + bp - b / 3;
		orange[1][3] = west.y + bp - b / 3;
		orange[1][4] = west.y + bp;

		// Grey X
		grey[0][0] = 3 * orangeOffset + ap - 2 * margin;
		grey[0][1] = 2 * orangeOffset + ap - margin;
		grey[0][2] = 2 * orangeOffset + ap - margin;

		// Grey Y
		grey[1][0] = west.y + bp + b;
		grey[1][1] = west.y + bp + b;
		grey[1][2] = west.y + bp;

	}

	private void drawNorthBox(Rectangle o) {

		buttonAreas[6] = drawPart( o , new Point( 1 , 1 ) , 3 , buttons[6] , ColorPallate.ENGR_CIRCUIT_NNNE );
		buttonAreas[7] = drawPart( o , new Point( 1 , 2 ) , 1 , buttons[7] , ColorPallate.ENGR_CIRCUIT_NNNE );
		buttonAreas[8] = drawPart( o , new Point( 3 , 2 ) , 3 , buttons[8] , ColorPallate.ENGR_CIRCUIT_NNNE );

		buttonAreas[9] = drawPart( o , new Point( 1 , 3 ) , 2 , buttons[9] , null );
		buttonAreas[10] = drawPart( o , new Point( 2 , 3 ) , 1 , buttons[10] , null );
		buttonAreas[11] = drawPart( o , new Point( 3 , 3 ) , 4 , buttons[11] , null );

	}

	private void drawEastBox(Rectangle o) {

		buttonAreas[18] = drawPart( o , new Point( 1 , 1 ) , 2 , buttons[18] , ColorPallate.ENGR_CIRCUIT_NNNE );
		buttonAreas[19] = drawPart( o , new Point( 1 , 2 ) , 3 , buttons[19] , ColorPallate.ENGR_CIRCUIT_SSSE );
		buttonAreas[20] = drawPart( o , new Point( 3 , 2 ) , 1 , buttons[20] , ColorPallate.ENGR_CIRCUIT_WWWE );

		buttonAreas[21] = drawPart( o , new Point( 1 , 3 ) , 4 , buttons[21] , null );
		buttonAreas[22] = drawPart( o , new Point( 2 , 3 ) , 2 , buttons[22] , null );
		buttonAreas[23] = drawPart( o , new Point( 3 , 3 ) , 4 , buttons[23] , null );

	}

	private void drawSouthBox(Rectangle o) {

		buttonAreas[12] = drawPart( o , new Point( 1 , 1 ) , 2 , buttons[12] , ColorPallate.ENGR_CIRCUIT_SSSE );
		buttonAreas[13] = drawPart( o , new Point( 1 , 2 ) , 3 , buttons[13] , ColorPallate.ENGR_CIRCUIT_SSSE );
		buttonAreas[14] = drawPart( o , new Point( 3 , 2 ) , 1 , buttons[14] , ColorPallate.ENGR_CIRCUIT_SSSE );

		buttonAreas[15] = drawPart( o , new Point( 1 , 3 ) , 1 , buttons[15] , null );
		buttonAreas[16] = drawPart( o , new Point( 2 , 3 ) , 4 , buttons[16] , null );
		buttonAreas[17] = drawPart( o , new Point( 3 , 3 ) , 3 , buttons[17] , null );

	}

	private void drawWestBox(Rectangle o) {

		buttonAreas[0] = drawPart( o , new Point( 1 , 1 ) , 1 , buttons[0] , ColorPallate.ENGR_CIRCUIT_WWWE );
		buttonAreas[1] = drawPart( o , new Point( 3 , 1 ) , 3 , buttons[1] , ColorPallate.ENGR_CIRCUIT_WWWE );
		buttonAreas[2] = drawPart( o , new Point( 3 , 2 ) , 2 , buttons[2] , ColorPallate.ENGR_CIRCUIT_WWWE );

		buttonAreas[3] = drawPart( o , new Point( 1 , 3 ) , 2 , buttons[3] , null );
		buttonAreas[4] = drawPart( o , new Point( 2 , 3 ) , 4 , buttons[4] , null );
		buttonAreas[5] = drawPart( o , new Point( 3 , 3 ) , 4 , buttons[5] , null );

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		System.out.println( "Clicky!" );
		int indexPart = 0;
		
		for (indexPart = 0; indexPart < 24; indexPart++)
			if (buttonAreas[indexPart].contains(e.getPoint()))

		if (buttons[indexPart]){
			System.out.println( buttons[indexPart] + "Button" + indexPart );
			if (control.getConnectionManager().engineerButtonsEnabled()){
				buttons[indexPart] = false;
				control.breakPart(indexPart);
			}
		}

		SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				repaint();
			}
		} );

	}


	// 1 - Red, 2-Green, 3-Yellow, 4-Atomic
	private Ellipse2D drawPart(Rectangle r, Point p, int type, boolean active, Color system) {

		int a = (int) ( r.width / ( 2 * ( A + 1 ) ) );
		int b = (int) ( r.height / ( 2 * ( B + 1 ) ) );
		int rad = (int) ( a / 2.5 );
		int x_o = r.x + (int) ( A * a ) + a * ( p.x - 1 );
		int y_o = r.y + (int) ( B * b ) + b * ( p.y - 1 );

		if (system == null)
			g.setColor( Color.BLACK );
		else g.setColor( system );
		g.fillOval( x_o - rad , y_o - rad , 2 * rad , 2 * rad );

		int ap = (int) ( A * a );
		int bp = (int) ( B * b );
		rad = a / 3;

		switch (type) {
			case 1:
				g.setColor( ColorPallate.ENGR_ACTIVE_WEAPON );
				break;
			case 2:
				g.setColor( ColorPallate.ENGR_ACTIVE_SENSOR );
				break;
			case 3:
				g.setColor( ColorPallate.ENGR_ACTIVE_EXTRAS );
				break;
			case 4:
				g.setColor( ColorPallate.ENGR_ACTIVE_REACTR );
				break;
		}

		x_o = r.x + ap + a * ( p.x - 1 );
		y_o = r.y + bp + b * ( p.y - 1 );
		
		Ellipse2D er2d = new Ellipse2D.Double( x_o - rad , y_o - rad , 2 * rad , 2 * rad );
		g.fill(er2d);

		if (active) return er2d;

		switch (type) {
			case 1:
				g.setColor( ColorPallate.ENGR_BROKEN_WEAPON );
				break;
			case 2:
				g.setColor( ColorPallate.ENGR_BROKEN_SENSOR );
				break;
			case 3:
				g.setColor( ColorPallate.ENGR_BROKEN_EXTRAS );
				break;
			case 4:
				g.setColor( ColorPallate.ENGR_BROKEN_REACTR );
				break;
		}

		int rad2 = (int) ( a / 3.3 );
		g.fillOval( x_o - rad2 , y_o - rad2 , 2 * rad2 , 2 * rad2 );
		
		return er2d; 

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
