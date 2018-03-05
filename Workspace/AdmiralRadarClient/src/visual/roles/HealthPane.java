package visual.roles;

import java.awt.Color;

import visual.util.operations.GUIController;

public class HealthPane extends ShipPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HealthPane(GUIController cx) {
		super(cx);
	}

	@Override
	public void draw() {
		
		int health = 2;
		
		g.setColor(Color.GRAY);
		int x = getSize().width;
		int y = getSize().height;
		
		int boxW = (int) ((x) / 4);
		int boxH = (int) (y / 1.5);
		int margin = 20;
		int border = 2;

		g.fillRect(margin, (y - boxH) / 2, boxW - 2*margin, boxH);
		g.fillRect(margin + (x) / 4, (y - boxH) / 2, boxW - 2*margin, boxH);
		g.fillRect(margin + (2*x) / 4, (y - boxH) / 2, boxW - 2*margin, boxH);
		g.fillRect(margin + (3*x) / 4, (y - boxH) / 2, boxW - 2*margin, boxH);
		
		g.setColor(Color.RED);
		
		if (health < 4) g.fill3DRect(margin + border, ((y - boxH) / 2) + border, boxW - 2*margin - 2*border, boxH - 2*border, true);
		if (health < 3) g.fill3DRect(margin + border + (x) / 4, ((y - boxH) / 2) + border, boxW - 2*margin - 2*border, boxH - 2*border, true);
		if (health < 2) g.fill3DRect(margin + border + (2*x) / 4, ((y - boxH) / 2) + border, boxW - 2*margin - 2*border, boxH - 2*border, true);
		if (health < 1) g.fill3DRect(margin + border + (3*x) / 4, ((y - boxH) / 2) + border, boxW - 2*margin - 2*border, boxH - 2*border, true);

	}

}
