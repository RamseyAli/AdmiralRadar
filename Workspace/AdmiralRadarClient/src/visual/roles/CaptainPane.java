package visual.roles;

import java.awt.Color;
import java.awt.Point;

import visual.util.components.ShipPanel;
import visual.util.operations.GUIController;

public class CaptainPane extends ShipPanel {

	private static int SEG = 15;
	
	private Point start;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public CaptainPane(GUIController cx) {
		super(cx);
		
		start = new Point(3,5);
		
	}


	@Override
	public void draw() {
		
		int margin = 30;
		
		int x = getSize().width;
		int y = getSize().height;
		
		int sBig = (int) (0.9*Math.min(x,y));
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect((x-sBig) / 2, (y-sBig) / 2, sBig, sBig);
		
		int sMax = sBig - 2*margin;
		int s = sMax - ( sMax % (SEG - 1) );
		int r = 2;
		
		int x0 = (x-s) / 2;
		int y0 = (y-s) / 2;
		
		int sp = (int) (s / (SEG - 1));

		char c = 'A';
		
		g.setColor(Color.YELLOW);
		for (int i = 0; i < SEG; i++){
			
			drawChar(c , x0 + (i*sp), y0 - margin/2);
			drawInt((int) (c - 'A') , x0  - margin/2, y0  + (i*sp) + 5, 2);
			c++;
			
			for (int j = 0; j < SEG; j++)
				g.fillOval(x0 - r + (i*sp), y0 - r + (j*sp), 2*r, 2*r);
			
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
	
}
