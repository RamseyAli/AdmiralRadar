package visual.common.general;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.RoundRectangle2D;

import javax.swing.SwingUtilities;

import game.GameMap;
import game.Position;
import util.Preferences;
import visual.util.components.ShipPanel;
import visual.util.operations.GUIController;

public abstract class MapBasedElement extends ShipPanel {

	private static Color AST_1 = new Color(50,50,50);
	private static Color AST_2 = new Color(70,70,70);
	
	static int MARGIN = 30;
	private static int Y = 5;
	private static int Z = 8;
	
	private GameMap map;
	protected MapMouseListener ear;
	private Position currentMouse;
	
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
		
		
		
	}


	@Override
	public void draw() {
		
		int x = getSize().width;
		int y = getSize().height;
		
		int sBig = (int) (0.9*Math.min(x,y));
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect((x-sBig) / 2, (y-sBig) / 2, sBig, sBig);
		
		int sMax = sBig - 2*MARGIN;
		int s = sMax - ( sMax % (Preferences.SEG - 1) );
		int r = 2;
		
		int x0 = (x-s) / 2;
		int y0 = (y-s) / 2;
		
		sp = (int) (s / (Preferences.SEG - 1));

		char c = 'A';
		
		g.setColor(Color.GREEN);
		
		if (currentMouse.isValid())
			g.fillOval(x0 - Y*r + (int) (currentMouse.getX()*sp), y0 - Y*r + (int) (currentMouse.getY()*sp), 2*Y*r, 2*Y*r);
		
		g.setColor(Color.YELLOW);
		for (int i = 0; i < Preferences.SEG; i++){
			
			drawChar(c , x0 + (i*sp), y0 - MARGIN/2);
			drawInt((int) (c - 'A') , x0  - MARGIN/2, y0  + (i*sp) + 5, 2);
			c++;
			
			for (int j = 0; j < Preferences.SEG; j++)
				if (map.isAsteroid(i, j)) {
					g.setColor(AST_1);
					
					g.fill(new RoundRectangle2D.Double(x0 - Z*r + (i*sp), y0 - Z*r + (j*sp), 2*Z*r, 2*Z*r , 10*r, 10*r));
					
					g.setColor(AST_2);
					
					g.fillOval(x0 - Y*r + (i*sp), y0 - Y*r + (j*sp), 2*Y*r, 2*Y*r);
					
					g.setColor(Color.YELLOW);
				}
				else g.fillOval(x0 - r + (i*sp), y0 - r + (j*sp), 2*r, 2*r);
			
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
