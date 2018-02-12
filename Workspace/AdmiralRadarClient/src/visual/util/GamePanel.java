package visual.util;

import javax.swing.JPanel;

import visual.roles.*;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private GUIController control;
	
	NetworkPane net;
	RadioPane rad;
	ExecutivePane xo;
	EngineerPane egr;
	CaptainPane cap;
	HealthPane hth;
	AlertsPane itl;
	
	public GamePanel(GUIController c){
		control = c;
		control.setPanel(this);
		
		net = new NetworkPane();
		rad = new RadioPane();
		xo = new ExecutivePane();
		egr = new EngineerPane();
		cap = new CaptainPane();
		itl = new AlertsPane();
		hth = new HealthPane();
		
		control.makeGUI();
		
	}


}
