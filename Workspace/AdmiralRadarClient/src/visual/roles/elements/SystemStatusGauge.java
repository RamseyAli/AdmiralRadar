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
		//rads are actually diams
		int rad1 = 70;
		int rad2 = 100;
		
		
		g.setColor(Color.GRAY);
		g.fillOval(x_ctr - rad2/2, y_ctr - rad2/2, rad2, rad2);
		
		for (int i = 0; i < spaces; i++){
			
			if (charge > i) g.setColor(Color.BLACK); else g.setColor(Color.WHITE);
			g.fillArc(x_ctr - rad2/2, y_ctr - rad2/2, rad2, rad2, 89 - (45*i) , -43);	
		
		}
		g.setColor(col);
		g.fillOval(x_ctr - rad1/2, y_ctr - rad1/2, rad1, rad1);
		
		
		
	}

}
