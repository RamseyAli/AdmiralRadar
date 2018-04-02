package visual.common;

import java.awt.Color;
import java.awt.geom.AffineTransform;

import visual.util.components.ShipPanel;
import visual.util.operations.GUIController;

public class OrdersPane extends ShipPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	

	public OrdersPane(GUIController cx) {
		super(cx);
	}

	@Override
	public void draw() {
		g.setBackground(Color.CYAN);
		g.fillRect(2, 2, 130, 600);
		drawArrow(5,5, 50, 50);
		
		
	}

	void drawArrow(int x1, int y1, int x2, int y2) {
		
		int ARR_SIZE = 4;

	    double dx = x2 - x1, dy = y2 - y1;
	    double angle = Math.atan2(dy, dx);
	    int len = (int) Math.sqrt(dx*dx + dy*dy);
	    AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
	    at.concatenate(AffineTransform.getRotateInstance(angle));
	    g.transform(at);

	    // Draw horizontal arrow starting in (0, 0)
	    g.drawLine(0, 0, len, 0);
	    g.fillPolygon(new int[] {len, len - ARR_SIZE, len - ARR_SIZE, len},
	                  new int[] {0, - ARR_SIZE , ARR_SIZE , 0}, 4);
	}
	
}
