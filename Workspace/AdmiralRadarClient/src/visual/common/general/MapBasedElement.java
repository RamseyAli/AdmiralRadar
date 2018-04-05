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

public abstract class MapBasedElement extends ShipPanel {

	private static Color AST_1 = new Color(50,50,50);
	private static Color AST_2 = new Color(70,70,70);

	static int MARGIN = 30;
	private static int Y = 5;
	private static int Z = 8;
	private static int R = 2;

	private GameMap map;
	protected MapMouseListener ear;
	private Position currentMouse;
	private Image shipIcon;


	int sp;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public MapBasedElement(GUIController cx) {
		super(cx);
		map = cx.getMap();

		currentMouse = new Position(-1, -1);
		ear = new MapMouseListener(this);

		addMouseListener(ear);
		addMouseMotionListener(ear);

		this.setOpaque(false);
		this.setBackground(new Color(0,0,0,0));

		try {
			int x = 30;

			BufferedImage bi = ImageIO.read(new File("ship.png"));
			shipIcon = bi.getScaledInstance(x, (int) ((bi.getHeight()*x) / ((float) bi.getWidth())), Image.SCALE_DEFAULT);

		} catch (IOException e) {
			e.printStackTrace();
		}



	}


	@Override
	public void draw() {

		int x = getSize().width;
		int y = getSize().height;

		int sBig = (int) (0.9*Math.min(x,y));

		g.setColor(new Color(20, 20, 20, 50));
		g.fillRect((x-sBig) / 2, (y-sBig) / 2, sBig, sBig);

		int sMax = sBig - 2*MARGIN;
		int s = sMax - ( sMax % (GamePreferences.SEG - 1) );

		int x0 = (x-s) / 2;
		int y0 = (y-s) / 2;

		sp = (int) (s / (GamePreferences.SEG - 1));

		char c = 'A';

		g.setColor(ColorPallate.MAP_MOUSEOVER);

		if (currentMouse.isValid())
			g.fillOval(x0 - Y*R + (int) (currentMouse.getX()*sp), y0 - Y*R + (int) (currentMouse.getY()*sp), 2*Y*R, 2*Y*R);

		g.setColor(Color.YELLOW);
		for (int i = 0; i < GamePreferences.SEG; i++){

			drawChar(c , x0 + (i*sp), y0 - MARGIN/2);
			drawInt((int) (c - 'A') , x0  - MARGIN/2, y0  + (i*sp) + 5, 2);
			c++;

			for (int j = 0; j < GamePreferences.SEG; j++)
				if (map.isAsteroid(i, j)) {
					g.setColor(AST_1);

					g.fill(new RoundRectangle2D.Double(x0 - Z*R + (i*sp), y0 - Z*R + (j*sp), 2*Z*R, 2*Z*R , 10*R, 10*R));

					g.setColor(AST_2);

					g.fillOval(x0 - Y*R + (i*sp), y0 - Y*R + (j*sp), 2*Y*R, 2*Y*R);

					g.setColor(Color.YELLOW);
				}
				else g.fillOval(x0 - R + (i*sp), y0 - R + (j*sp), 2*R, 2*R);

		}

		if (control.getStartLocation() != null){
			Position pos = (control.getSpaceship() == null) ? (control.getStartLocation()) : (control.getSpaceship().getPosition());

			g.drawImage(shipIcon,x0 + pos.getX()*sp - shipIcon.getWidth(null)/2, y0 + pos.getY()*sp - shipIcon.getHeight(null)/2, null);
		}
	}

	//X = 0
	private void drawInt(int i, int x, int y, int X) {

		String s = String.valueOf(i);

		g.drawString(s, (int) (x - g.getFontMetrics().stringWidth(s) / X), y);

	}


	private void drawChar(char c, int x, int y) {

		g.drawString(String.valueOf(c), x - g.getFontMetrics().charWidth(c)/2, y);

	}


	public abstract void clickGridDot(int x , int y);


	public void setMouseOver(Point p) {
		currentMouse.setPosition(p.x, p.y);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				repaint();
			}
		});


	}



}
