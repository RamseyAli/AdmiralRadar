package visual.roles.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class SystemStatusGauge extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	char name;
	Color col;
	int spaces;
	int charge;
	
	public SystemStatusGauge(char n, Color c, int s){
		name = n;
		col = c;
		spaces = s;
		charge = 0;
			
	}
	
	protected void paintComponent(Graphics gin){
		Graphics2D g = (Graphics2D) gin;
		super.paintComponent(g);
		
		
		int x = getSize().width;
		int y = getSize().height;
		int x_ctr = x / 2;
		int y_ctr = y / 2;
		int rad1 = 30;
		int rad2 = 70;
		
		
		g.setColor(Color.GRAY);
		g.fillOval(x_ctr - rad2/2, y_ctr - rad2/2, rad2, rad2);
		
		g.setColor(col);
		g.fillOval(x_ctr - rad1/2, y_ctr - rad1/2, rad1, rad1);
		
		
		
	}

}
