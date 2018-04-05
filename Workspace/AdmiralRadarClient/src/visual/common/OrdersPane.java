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
import visual.util.ColorPallate;
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

		//Display Math
		int border = 2;
		int sysCautB = 2;
		int sysBorder = 2;

		int w_o = getWidth();
		int w_m = (int) (w_o * (5.0/6));
		int w = w_m - 2*border;
		int h = getHeight();

		int sysWidth = w / 2; //.5
		int sysHeight = (int) (h /14);
		int sysYGap = sysHeight / 2; 

		int sysX = (int) ((w * 0.125) / 2.0);

		int sysCautR =sysHeight / 2;
		int sysCautL = sysCautR - sysCautB;
		int sysCautX = w - sysCautR;


		int compSquare = (int) Math.min(w*0.8 , h * (12.0/28.0) );
		int compRo = compSquare / 2 ;
		//	int compRi = (compSquare - border )/2 ;

		int compX0 = border +  (w / 2);
		int compY0 = (int) (h - 1.1*compRo);

		int ls = (int) (compRo / 1.5);
		int ss = (int) (compRo / 4);
		int tg = (int) (ss* 0.9);
		int c = ss / 2;

		//Display Art

		g.setColor(ColorPallate.ORDER_PANEL_BORDER);
		g.fillRect(0, 0, w_m, h);

		g.setColor(ColorPallate.ORDER_PANEL);
		g.fillRect(border, border, w, h - 2*border);

		g.setColor(ColorPallate.ORDER_COMPASS);
		g.fillOval(compX0 - compRo, compY0 - compRo, 2*compRo, 2*compRo);

		//	g.setColor(ColorPallate.ORDER_PANEL);
		//	g.fillOval(compX0 - compRi, compY0 - compRi, 2*compRi, 2*compRi);

		north = new RoundRectangle2D.Double(compX0 - ss/2, compY0 - tg - ls, ss , ls , c, c);
		east = new RoundRectangle2D.Double(compX0 + tg, compY0 - ss / 2, ls , ss , c, c);
		south = new RoundRectangle2D.Double(compX0 - ss/2, compY0 + tg, ss , ls , c, c);
		west = new RoundRectangle2D.Double(compX0 - tg - ls, compY0 - ss / 2, ls , ss , c, c);

		g.setColor(overlay[0] ? Color.WHITE : ColorPallate.ORDER_COMPASS_BUTTON);
		g.fill(north);

		g.setColor(overlay[1] ? Color.WHITE : ColorPallate.ORDER_COMPASS_BUTTON);
		g.fill(south);

		g.setColor(overlay[2] ? Color.WHITE : ColorPallate.ORDER_COMPASS_BUTTON);
		g.fill(east);

		g.setColor(overlay[3] ? Color.WHITE : ColorPallate.ORDER_COMPASS_BUTTON);
		g.fill(west);


		g.setColor(ColorPallate.ORDER_SYSTEM_BOX);
		g.fillRect(border + sysX, border + sysYGap							, sysWidth, sysHeight);
		g.fillRect(border + sysX, border + sysYGap + 1*(sysYGap + sysHeight), sysWidth, sysHeight);
		g.fillRect(border + sysX, border + sysYGap + 2*(sysYGap + sysHeight), sysWidth, sysHeight);
		g.fillRect(border + sysX, border + sysYGap + 3*(sysYGap + sysHeight), sysWidth, sysHeight);
		g.fillRect(border + sysX, border + sysYGap + 4*(sysYGap + sysHeight), sysWidth, sysHeight);

		g.setColor(Color.BLACK);
		int sh = g.getFontMetrics().getHeight();
		g.setFont(g.getFont().deriveFont(Font.BOLD));

		g.drawString("Drone"	, border + sysX + 10, 		(int)(border + 0.5*(sysYGap + sysHeight) + (sh/3))		);
		g.drawString("Radar"	, border + sysX + 10, 		(int)(border + 1.5*(sysYGap + sysHeight) + (sh/3))		);
		g.drawString("Missile"	, border + sysX + 10, 		(int)(border + 2.5*(sysYGap + sysHeight) + (sh/3))		);
		g.drawString("Mines"	, border + sysX + 10, 		(int)(border + 3.5*(sysYGap + sysHeight) + (sh/3))		);
		g.drawString("Boosters"	, border + sysX + 10, 		(int)(border + 4.5*(sysYGap + sysHeight) + (sh/3))		);


		g.setColor(ColorPallate.ORDER_SYSTEM_BOX);
		g.fillOval(border + sysCautX - sysCautR - sysX, border + sysYGap + sysHeight/2 - sysCautR + 0*(sysYGap + sysHeight), 2*sysCautR, 2*sysCautR);
		g.fillOval(border + sysCautX - sysCautR - sysX, border + sysYGap + sysHeight/2 - sysCautR + 1*(sysYGap + sysHeight), 2*sysCautR, 2*sysCautR);
		g.fillOval(border + sysCautX - sysCautR - sysX, border + sysYGap + sysHeight/2 - sysCautR + 2*(sysYGap + sysHeight), 2*sysCautR, 2*sysCautR);
		g.fillOval(border + sysCautX - sysCautR - sysX, border + sysYGap + sysHeight/2 - sysCautR + 3*(sysYGap + sysHeight), 2*sysCautR, 2*sysCautR);
		g.fillOval(border + sysCautX - sysCautR - sysX, border + sysYGap + sysHeight/2 - sysCautR + 4*(sysYGap + sysHeight), 2*sysCautR, 2*sysCautR);

		if (control.getSpaceship() != null){
			g.setColor(ColorPallate.SENSORY);
			if (control.getSpaceship().getShipSystem().isSystemCharged("Drone"))
				g.fillRect(border + sysX + sysBorder, border + sysYGap + sysBorder							, sysWidth - 2*sysBorder, sysHeight - 2*sysBorder);

			if (control.getSpaceship().getShipSystem().isSystemCharged("Radar"))
				g.fillRect(border + sysX + sysBorder, border + sysYGap + sysBorder + 1*(sysYGap + sysHeight), sysWidth - 2*sysBorder, sysHeight - 2*sysBorder);

			g.setColor(ColorPallate.TACTICAL);
			if (control.getSpaceship().getShipSystem().isSystemCharged("Missile"))
				g.fillRect(border + sysX + sysBorder, border + sysYGap + sysBorder + 2*(sysYGap + sysHeight), sysWidth - 2*sysBorder, sysHeight - 2*sysBorder);

			if (control.getSpaceship().getShipSystem().isSystemCharged("Mine"))
				g.fillRect(border + sysX + sysBorder, border + sysYGap + sysBorder + 3*(sysYGap + sysHeight), sysWidth - 2*sysBorder, sysHeight - 2*sysBorder);

			g.setColor(ColorPallate.UTILITY);
			if (control.getSpaceship().getShipSystem().isSystemCharged("Silent"))
				g.fillRect(border + sysX + sysBorder, border + sysYGap + sysBorder + 4*(sysYGap + sysHeight), sysWidth - 2*sysBorder, sysHeight - 2*sysBorder);


			g.setColor(ColorPallate.ORDER_SYSTEM_CAUTION);
			
			if (control.getSpaceship().getShipSystem().isSystemDestroyed("Drone")) 
				g.fillOval(border + sysCautX - sysCautL - sysX, border + sysYGap + sysHeight/2 - sysCautL + 0*(sysYGap + sysHeight), 2*sysCautL, 2*sysCautL);

			if (control.getSpaceship().getShipSystem().isSystemDestroyed("Radar")) 
				g.fillOval(border + sysCautX - sysCautL - sysX, border + sysYGap + sysHeight/2 - sysCautL + 1*(sysYGap + sysHeight), 2*sysCautL, 2*sysCautL);

			if (control.getSpaceship().getShipSystem().isSystemDestroyed("Missile"))
				g.fillOval(border + sysCautX - sysCautL - sysX, border + sysYGap + sysHeight/2 - sysCautL + 2*(sysYGap + sysHeight), 2*sysCautL, 2*sysCautL);

			if (control.getSpaceship().getShipSystem().isSystemDestroyed("Mine"));  
			g.fillOval(border + sysCautX - sysCautL - sysX, border + sysYGap + sysHeight/2 - sysCautL + 3*(sysYGap + sysHeight), 2*sysCautL, 2*sysCautL);

			if (control.getSpaceship().getShipSystem().isSystemDestroyed("Silent")) 
				g.fillOval(border + sysCautX - sysCautL - sysX, border + sysYGap + sysHeight/2 - sysCautL + 4*(sysYGap + sysHeight), 2*sysCautL, 2*sysCautL);
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
