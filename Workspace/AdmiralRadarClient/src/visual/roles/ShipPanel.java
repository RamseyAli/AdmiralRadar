package visual.roles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import visual.util.PanelPosition;

public abstract class ShipPanel extends JPanel{

	public static final long serialVersionUID = 1L;
	
	protected Color background;
	private Dimension panelSize;
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
	}
	
	public void setConfigurationType(PanelPosition p, Dimension frameSize){
		float x = frameSize.width;
		float y = frameSize.height;
		
		int side = (int) (x*15.0f/64.0f);
		int mainW = (int) (x*49.0f/64.0f);
		int bannerHeight = (int) (y/8.0f);
		
	//	System.out.println("Inits: " + x + "," + y);
		
		switch(p){
		case BANNER:
			panelSize = new Dimension(mainW,bannerHeight);
			break;
		case MIDDLEBIG:
			panelSize = new Dimension(mainW, (int)y - bannerHeight);
			break;
		case MIDDLESHORT:
			panelSize = new Dimension(mainW,(int)y - 2*bannerHeight);
			break;
		case MIDDLESMALL:
			panelSize = new Dimension((int) (x - 2*side), (int)y - 2*bannerHeight); //TODO
			break;
		case SIDERECT:
			panelSize = new Dimension(side, (int) (x/2.0f));
			break;
		case SIDESQUARE:
			panelSize = new Dimension(side, (int) ((x - bannerHeight)/2.0f));
			break;
		case SIDETOP:
			panelSize = new Dimension(side, bannerHeight);
			break;
		default:
			break;
		
		}
		//Dimension s = new Dimension((int) (panelSize.height*0.9) , (int) (panelSize.width*0.9));
		//Dimension l = new Dimension((int) (panelSize.height*1.1) , (int) (panelSize.width*1.1));
		
		//System.out.println(panelSize);
		//setMinimumSize(s);
		setPreferredSize(panelSize);
		//setMaximumSize(l);
		
	}
	
	public abstract void draw();
	
	
}
