package visual.roles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
public class EngineerPane extends ShipPanel {
	
	private float A = 0.5f;
	private float B = 0.5f;

	private int margin = 10;
	
	private Color ACTIVE_WEAPON = Color.RED;
	private Color BROKEN_WEAPON = Color.DARK_GRAY;
	private Color ACTIVE_SENSOR = Color.GREEN;
	private Color BROKEN_SENSOR = Color.DARK_GRAY;
	private Color ACTIVE_EXTRAS = Color.YELLOW;
	private Color BROKEN_EXTRAS = Color.DARK_GRAY;
	private Color ACTIVE_REACTR = Color.GRAY;
	private Color BROKEN_REACTR = Color.DARK_GRAY;
	
	private int[][] blue = new int[2][6];
	private int[][] orange = new int[2][5];
	private int[][] grey = new int[2][3];
	
	private boolean []buttons = new boolean[24];
	
	public EngineerPane() {
		super(Color.CYAN);
		
		for (int i = 0; i < 24; i++) buttons[i] = false;
		
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public void draw() {
		
		int x = getSize().width;
		int y = getSize().height;
		
		int boxW = (int) ((x) / 4);
		int boxH = (int) (y / 1.2);
		
		
		Rectangle west = new Rectangle(margin, (y - boxH) / 2, boxW - 2*margin, boxH);
		Rectangle north = new Rectangle(margin + (x) / 4, (y - boxH) / 2, boxW - 2*margin, boxH);
		Rectangle south = new Rectangle(margin + (2*x) / 4, (y - boxH) / 2, boxW - 2*margin, boxH);
		Rectangle east = new Rectangle(margin + (3*x) / 4, (y - boxH) / 2, boxW - 2*margin, boxH);
		
		g.setColor(Color.LIGHT_GRAY);
		g.fill(west);
		g.fill(north);
		g.fill(south);
		g.fill(east);
		
		calculateSystemLines(west, new Rectangle(west.x, west.y, 4*west.width  + 6*margin , west.height));
		
		g.setStroke(new BasicStroke(5));
		g.setColor(Color.BLUE);
		g.drawPolyline(blue[0], blue[1], 6);
		g.setColor(Color.ORANGE);
		g.drawPolyline(orange[0], orange[1], 5);
		g.setColor(Color.DARK_GRAY);
		g.drawPolyline(grey[0], grey[1], 3);
		
		drawWestBox(west);
		drawNorthBox(north);
		drawSouthBox(south);
		drawEastBox(east);
		

		
		
		
	}


	private void calculateSystemLines(Rectangle west, Rectangle big) {
		
		int a = (int) (west.width / (2 * (A + 1)));
		int b = (int) (west.height / (2 * (B + 1)));
		int ap = (int) (A*a);
		int bp = (int) (B*b);
		
		//Blue X
		blue[0][0] = west.x + ap + a*2;
		blue[0][1] = west.x + ap + a*2;
		blue[0][2] = west.x + ap;
		blue[0][3] = west.x + ap;
		blue[0][4] = west.x + big.width - ap;
		blue[0][5] = west.x + big.width - ap;
		
		//Blue Y
		blue[1][0] = west.y + bp + b;
		blue[1][1] = west.y + bp;
		blue[1][2] = west.y + bp;
		blue[1][3] = west.y + bp + (int) (1.5*b);
		blue[1][4] = west.y + bp + (int) (1.5*b);
		blue[1][5] = west.y + bp + b;
		
		int orangeOffset = west.width + 3*margin;
		//Orange X
		orange[0][0] = orangeOffset + ap + 2*a;
		orange[0][1] = orangeOffset + ap;
		orange[0][2] = orangeOffset + ap;
		orange[0][3] = 3*orangeOffset + ap - 2*margin;
		orange[0][4] = 3*orangeOffset + ap - 2*margin;
		
		//Orange Y
		orange[1][0] = west.y + bp + b;
		orange[1][1] = west.y + bp + b;
		orange[1][2] = west.y + bp - b/3;
		orange[1][3] = west.y + bp - b/3;
		orange[1][4] = west.y + bp;
		
		//Grey X
		grey[0][0] = 3*orangeOffset + ap - 2*margin;
		grey[0][1] = 2*orangeOffset + ap - margin;
		grey[0][2] = 2*orangeOffset + ap - margin;
		
		//Grey Y
		grey[1][0] = west.y + bp + b;
		grey[1][1] = west.y + bp + b;
		grey[1][2] = west.y + bp;
		
		
	}


	private void drawNorthBox(Rectangle o) {
		
		
		
		
		drawPart(o,new Point(1,1),3,buttons[0], Color.ORANGE);
		drawPart(o,new Point(1,2),1,buttons[1], Color.ORANGE);
		drawPart(o,new Point(3,2),3,buttons[2], Color.ORANGE);
		
		drawPart(o,new Point(1,3),2,buttons[3]);
		drawPart(o,new Point(2,3),1,buttons[4]);
		drawPart(o,new Point(3,3),4,buttons[5]);
		
		
	}


	private void drawEastBox(Rectangle o) {
		
		drawPart(o,new Point(1,1),2,buttons[0], Color.ORANGE);
		drawPart(o,new Point(1,2),3,buttons[1], Color.DARK_GRAY);
		drawPart(o,new Point(3,2),1,buttons[2], Color.BLUE);
		
		drawPart(o,new Point(1,3),4,buttons[3]);
		drawPart(o,new Point(2,3),2,buttons[4]);
		drawPart(o,new Point(3,3),4,buttons[5]);
		
		
	}


	private void drawSouthBox(Rectangle o) {
		
		drawPart(o,new Point(1,1),2,buttons[0], Color.DARK_GRAY);
		drawPart(o,new Point(1,2),3,buttons[1], Color.DARK_GRAY);
		drawPart(o,new Point(3,2),1,buttons[2], Color.DARK_GRAY);
		
		drawPart(o,new Point(1,3),1,buttons[3]);
		drawPart(o,new Point(2,3),4,buttons[4]);
		drawPart(o,new Point(3,3),3,buttons[5]);
		
		
	}


	private void drawWestBox(Rectangle o) {
		
		drawPart(o,new Point(1,1),1,buttons[0], Color.BLUE);
		drawPart(o,new Point(3,1),3,buttons[1], Color.BLUE);
		drawPart(o,new Point(3,2),2,buttons[2], Color.BLUE);
		
		drawPart(o,new Point(1,3),2,buttons[3]);
		drawPart(o,new Point(2,3),4,buttons[4]);
		drawPart(o,new Point(3,3),4,buttons[5]);
		
		
	}
	
	//1 - Red, 2-Green, 3-Yellow, 4-Atomic
	private void drawPart(Rectangle r , Point p, int type, boolean active){
		//r = Bounding Rect
		//p = Coordinates in our system
		
		int a = (int) (r.width / (2 * (A + 1)));
		int b = (int) (r.height / (2 * (B + 1)));
		int ap = (int) (A*a);
		int bp = (int) (B*b);
		int rad = a / 3;
		
		switch(type){
		case 1: g.setColor(ACTIVE_WEAPON); break;
		case 2: g.setColor(ACTIVE_SENSOR); break;
		case 3: g.setColor(ACTIVE_EXTRAS); break;
		case 4: g.setColor(ACTIVE_REACTR); break;
		}
		
		int x_o = r.x + ap + a*(p.x - 1);
		int y_o = r.y + bp + b*(p.y - 1);
		g.fillOval(x_o - rad, y_o - rad, 2*rad , 2*rad );
		
		if (active ) return;
		
		switch(type){
		case 1: g.setColor(BROKEN_WEAPON); break;
		case 2: g.setColor(BROKEN_SENSOR); break;
		case 3: g.setColor(BROKEN_EXTRAS); break;
		case 4: g.setColor(BROKEN_REACTR); break;
		}
		
		int rad2 = (int) (a / 3.3);
		g.fillOval(x_o - rad2, y_o - rad2, 2*rad2 , 2*rad2 );
		
	}
	
	//1 - Red, 2-Green, 3-Yellow, 4-Atomic
	private void drawPart(Rectangle r , Point p, int type, boolean active, Color system){
		
		int a = (int) (r.width / (2 * (A + 1)));
		int b = (int) (r.height / (2 * (B + 1)));
		int rad = (int) (a / 2.5);
		int x_o = r.x + (int) (A*a) + a*(p.x - 1);
		int y_o = r.y + (int) (B*b) + b*(p.y - 1);
		
		
		g.setColor(system);
		
		g.fillOval(x_o - rad, y_o - rad, 2*rad , 2*rad);
		
		drawPart( r ,  p,  type, active);
		
	}
}
