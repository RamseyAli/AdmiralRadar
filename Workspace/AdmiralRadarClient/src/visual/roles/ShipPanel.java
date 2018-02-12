package visual.roles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class ShipPanel extends JPanel{

	protected Color background;
	Graphics2D g;
	
	public ShipPanel(Color c){
		background = new Color(c.getRed(),c.getGreen(),c.getBlue(), 200);
		
		setBackground(background);
		
		setVisible(true);
	}
	
	protected void paintComponent(Graphics gin){
		g = (Graphics2D) gin;
		super.paintComponent(g);
		
		draw();
		
	}
	
	
	public void defaultDraw(Color A, Color B){
		g.setColor(A);
		g.fillRect(5, 5, getWidth() - 10, getHeight() - 10);
		
		
		g.setColor(B);
		g.fillOval(50, 50, 100, 100);
	}
	
	public abstract void draw();
	
	
}
