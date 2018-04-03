package visual.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

import game.Role;
import visual.util.components.ShipPanel;
import visual.util.operations.GUIController;

public class OrdersPane extends ShipPanel implements MouseInputListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private RoundRectangle2D north , south, east, west;
	private boolean[] overlay = new boolean[4]; 
	/**
	 * 
	 */


	public OrdersPane(GUIController cx) {
		super(cx);

	}

	private static boolean[] directionToBooleans(String direction) {
		switch(direction){
		case "N": return new boolean[] {true , false , false, false};
		case "S": return new boolean[] {false , true , false, false};
		case "E": return new boolean[] {false , false , true, false};
		case "W": return new boolean[] {false , false , false, true};
		default: return new boolean[] {false , false , false, false};
		}
	}

	@Override
	public void draw() {

		int b1 = 20;
		int b2 = b1 + 1;
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.BLACK);
		g.fillRect(2, 2, getWidth() - 4, getHeight() - 4);

		int r_t = (getWidth() - b1*2) / 2;
		int r_t2 = (getWidth() - b2*2) / 2;

		g.setColor(Color.BLUE);
		g.fillOval(b1, getHeight() - 2*r_t - b1, 2*r_t, 2*r_t);

		g.setColor(Color.BLACK);
		g.fillOval(b2, getHeight() - 2*r_t2 - b2, 2*r_t2, 2*r_t2);

		int x0 = b1 + r_t;
		int y0 = getHeight() - r_t - b1;


		int ls = (int) (r_t / 1.5);
		int ss = (int) (r_t / 4);
		int tg = (int) (ss* 0.9);
		int c = ss / 2;

		north = new RoundRectangle2D.Double(x0 - ss/2, y0 - tg - ls, ss , ls , c, c);
		east = new RoundRectangle2D.Double(x0 + tg, y0 - ss / 2, ls , ss , c, c);
		south = new RoundRectangle2D.Double(x0 - ss/2, y0 + tg, ss , ls , c, c);
		west = new RoundRectangle2D.Double(x0 - tg - ls, y0 - ss / 2, ls , ss , c, c);

		g.setColor(overlay[0] ? Color.WHITE : Color.CYAN);
		g.fill(north);

		g.setColor(overlay[1] ? Color.WHITE : Color.CYAN);
		g.fill(south);

		g.setColor(overlay[2] ? Color.WHITE : Color.CYAN);
		g.fill(east);

		g.setColor(overlay[3] ? Color.WHITE : Color.CYAN);
		g.fill(west);

		int sysWidth = getWidth() / 2;
		int sysHeight = (int) (ls /1.4);
		int sysCautR = sysHeight / 2;
		int sysCautB = 2;
		int sysCautL = sysCautR - sysCautB;

		int b3 = b1 - 1;
		g.setColor(Color.DARK_GRAY);
		g.fillRect(b3, b3			   , sysWidth + 2, sysHeight + 2);
		g.fillRect(b3, b3 + 2*sysHeight, sysWidth + 2, sysHeight + 2);
		g.fillRect(b3, b3 + 4*sysHeight, sysWidth + 2, sysHeight + 2);
		g.fillRect(b3, b3 + 6*sysHeight, sysWidth + 2, sysHeight + 2);
		g.fillRect(b3, b3 + 8*sysHeight, sysWidth + 2, sysHeight + 2);
		
		g.setColor(Color.GREEN);
		g.fillRect(b1, b1, sysWidth, sysHeight);
		g.fillRect(b1, b1 + 2*sysHeight, sysWidth, sysHeight);

		g.setColor(Color.RED);
		g.fillRect(b1, b1 + 4*sysHeight, sysWidth, sysHeight);
		g.fillRect(b1, b1 + 6*sysHeight, sysWidth, sysHeight);

		g.setColor(Color.YELLOW);
		g.fillRect(b1, b1 + 8*sysHeight, sysWidth, sysHeight);

		
		g.setColor(Color.BLACK);
		int sh = g.getFontMetrics().getHeight();
		g.setFont(g.getFont().deriveFont(Font.BOLD));
		
		g.drawString("Drone", b1 + 10, 		(int)(b1 + 0.5*sysHeight + (sh/3)));
		g.drawString("Radar", b1 + 10, 		(int)(b1 + 2.5*sysHeight + (sh/3)));
		g.drawString("Missile", b1 + 10, 	(int)(b1 + 4.5*sysHeight + (sh/3)));
		g.drawString("Mines", b1 + 10, 		(int)(b1 + 6.5*sysHeight + (sh/3)));
		g.drawString("Boosters", b1 + 10, 	(int)(b1 + 8.5*sysHeight + (sh/3)));
		
		
		g.setColor(Color.DARK_GRAY);
		g.fillOval(2*b1 + sysWidth, b1 + 0*sysHeight, 2*sysCautR, 2*sysCautR);
		g.fillOval(2*b1 + sysWidth, b1 + 2*sysHeight, 2*sysCautR, 2*sysCautR);
		g.fillOval(2*b1 + sysWidth, b1 + 4*sysHeight, 2*sysCautR, 2*sysCautR);
		g.fillOval(2*b1 + sysWidth, b1 + 6*sysHeight, 2*sysCautR, 2*sysCautR);
		g.fillOval(2*b1 + sysWidth, b1 + 8*sysHeight, 2*sysCautR, 2*sysCautR);

		if (control.getSpaceship() != null){
			g.setColor(Color.RED);
			if (control.getSpaceship().getShipSystem().isSystemDestroyed("Drone"))   g.fillOval(2*b1 + sysWidth + sysCautB, b1  				+ sysCautB, 2*sysCautL, 2*sysCautL);
			if (control.getSpaceship().getShipSystem().isSystemDestroyed("Radar"))   g.fillOval(2*b1 + sysWidth + sysCautB, b1 + 2*sysHeight + sysCautB, 2*sysCautL, 2*sysCautL);
			if (control.getSpaceship().getShipSystem().isSystemDestroyed("Missile")) g.fillOval(2*b1 + sysWidth + sysCautB, b1 + 4*sysHeight + sysCautB, 2*sysCautL, 2*sysCautL);
			if (control.getSpaceship().getShipSystem().isSystemDestroyed("Mine"))    g.fillOval(2*b1 + sysWidth + sysCautB, b1 + 6*sysHeight + sysCautB, 2*sysCautL, 2*sysCautL);
			if (control.getSpaceship().getShipSystem().isSystemDestroyed("Silent"))  g.fillOval(2*b1 + sysWidth + sysCautB, b1 + 8*sysHeight + sysCautB, 2*sysCautL, 2*sysCautL);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (north.contains(e.getPoint())){
			System.out.println("North");
		}
		else if (south.contains(e.getPoint())){
			System.out.println("South");
		}
		else if (east.contains(e.getPoint())){
			System.out.println("East");
		}
		else if (west.contains(e.getPoint())){
			System.out.println("West");
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
		if (north.contains(e.getPoint())){
			overlay[0] = true;
			overlay[1] = false;
			overlay[2] = false;
			overlay[3] = false;
		}
		else if (south.contains(e.getPoint())){
			overlay[0] = false;
			overlay[1] = true;
			overlay[2] = false;
			overlay[3] = false;
		}
		else if (east.contains(e.getPoint())){
			overlay[0] = false;
			overlay[1] = false;
			overlay[2] = true;
			overlay[3] = false;
		}
		else if (west.contains(e.getPoint())){
			overlay[0] = false;
			overlay[1] = false;
			overlay[2] = false;
			overlay[3] = true;
		}
		else{
			overlay[0] = false;
			overlay[1] = false;
			overlay[2] = false;
			overlay[3] = false;
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				repaint();
			}
		});

	}

	public void setup() {
		if (control.getRole() == Role.CAPTAIN){
			addMouseListener(this);
			addMouseMotionListener(this);
		} else if (control.getRole() != Role.NETWORK){
			System.out.println(control.getRole());
			overlay = directionToBooleans(control.getSpaceship().getDirection());
		}

	}

}
