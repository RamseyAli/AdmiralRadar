package visual.common.general;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import game.GameMap;
import game.Position;
import pref.GamePreferences;
import visual.util.ColorPallate;
import visual.util.components.ShipPanel;
import visual.util.operations.GUIController;

public abstract class MapBasedElement extends ShipPanel{

	private GameMap				map;
	protected MapMouseListener	ear;
	private Position			currentMouse;
	private Image				shipIcon;
	private int					margin;
	private int					imageX;
	private int sp;
	private BufferedImage bi;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MapBasedElement(GUIController cx) {
		super( cx );
		map = cx.getMap();

		currentMouse = new Position( -1 , -1 );
		ear = new MapMouseListener( this );

		addMouseListener( ear );
		addMouseMotionListener( ear );

		this.setOpaque( false );
		this.setBackground( new Color( 0 , 0 , 0 , 0 ) );

		sp = 30;

		try {
			bi = ImageIO.read( new File(GamePreferences.RESOURCES_PATH + "ship.png" ) );
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		setImageSize();

	}

	@Override
	public void draw() {

		int x = getSize().width;
		int y = getSize().height;
		int sBig = (int) ( 0.9 * Math.min( x , y ) );

		margin = sBig / 8;

		g.setColor( ColorPallate.MAP_BACKGROUND );
		g.fillRect( ( x - sBig ) / 2 , ( y - sBig ) / 2 , sBig , sBig );

		int sMax = sBig - 2 * margin;
		int s = sMax - ( sMax % ( GamePreferences.SEG - 1 ) );

		int x0 = ( x - s ) / 2;
		int y0 = ( y - s ) / 2;

		sp =  (int) ( s / ( GamePreferences.SEG - 1 ) ) ;

		int r = (int) ( sp / 5 );
		int z = (int) ( sp / (1.0 * r) );
		int w = (int) ( z / 1.5 );

		char c = 'A';

		g.setColor( ColorPallate.MAP_MOUSEOVER );

		if (currentMouse.isValid()) g.fillOval( x0 - w * r + (int) ( currentMouse.getX() * sp ) ,
				y0 - w * r + (int) ( currentMouse.getY() * sp ) , 2 * w * r , 2 * w * r );

		g.setColor( ColorPallate.MAP_DOTS );
		for (int i = 0; i < GamePreferences.SEG; i++) {

			drawChar( c , x0 + ( i * sp ) , y0 - margin / 2 );
			drawInt( (int) ( c - 'A' ) , x0 - margin / 2 , y0 + ( i * sp ) + 5 , 2 );
			c++;

			for (int j = 0; j < GamePreferences.SEG; j++)
				if (map.isAsteroid( i , j )) {
					g.setColor( ColorPallate.ASTEROID_DARK );

					g.fill( new RoundRectangle2D.Double( x0 - z * r + ( i * sp ) , y0 - z * r + ( j * sp ) , 2 * z * r ,
							2 * z * r , 5 * r , 5 * r ) );

					g.setColor( ColorPallate.ASTEROID_LIGHT );

					g.fillOval( x0 - w * r + ( i * sp ) , y0 - w * r + ( j * sp ) , 2 * w * r , 2 * w * r );

					g.setColor( ColorPallate.MAP_DOTS );
				} else g.fillOval( x0 - r + ( i * sp ) , y0 - r + ( j * sp ) , 2 * r , 2 * r );

		}

		if (control.getStartLocation() != null) {
			Position pos = ( !control.getSpaceship().getPosition().isValid() ) ? ( control.getStartLocation() )
					: ( control.getSpaceship().getPosition() );

			setImageSize();
			
			g.drawImage( shipIcon , x0 + pos.getX() * sp - shipIcon.getWidth( null ) / 2 ,
					y0 + pos.getY() * sp - shipIcon.getHeight( null ) / 2 , null );
		}
	}

	private void drawInt(int i, int x, int y, int X) {

		String s = String.valueOf( i );
		g.drawString( s , (int) ( x - g.getFontMetrics().stringWidth( s ) / X ) , y );

	}

	private void drawChar(char c, int x, int y) {

		g.drawString( String.valueOf( c ) , x - g.getFontMetrics().charWidth( c ) / 2 , y );

	}

	public abstract void clickGridDot(int x, int y);

	public void setMouseOver(Point p) {
		currentMouse.setPosition( p.x , p.y );

		SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				repaint();
			}
		} );

	}

	public int getMarginSize() {
		return margin;
	}


	public void setImageSize(){

		if (imageX != sp){
			imageX = sp;
			
			shipIcon = bi.getScaledInstance( imageX , (int) ( ( bi.getHeight() * imageX ) / ( (float) bi.getWidth() ) ) ,
					Image.SCALE_DEFAULT );
		}
	}

	public int getSp() {
		return sp;
	}
}
