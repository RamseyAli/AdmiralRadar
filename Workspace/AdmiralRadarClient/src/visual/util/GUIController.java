package visual.util;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import general.Role;
import visual.roles.ShipPanel;

public class GUIController {

	GamePanel mainPane;
	Role display;
	BorderLayout verticalManager = new BorderLayout();
	BorderLayout paneManager = new BorderLayout();
	JPanel primaryPane;
	JPanel sidePane;
	JPanel captainInfoPane;
	
	
	public GUIController(Role r) {
		display = r;
	}
	
	public void setPanel(GamePanel p) {
		mainPane = p;
		mainPane.setLayout(paneManager);
		
	}

	public void makeGUI() {
		
		switch(display){
		case CAPTAIN:
			makeCaptain();
			break;
		case ENGINE:
			makeEngineer();
			break;
		case FIRST:
			makeFirstOfficer();
			break;
		case NETWORK:
			makeNetwork();
			break;
		case RADIO:
			makeRadio();
			break;
		default:
			
			break;
		
		}
		System.out.println("Painting!");
		mainPane.repaint();
		
		
	}

	private void makeCaptain() {
		makeAndAddSidePanel(mainPane.xo, mainPane.itl);
		makeAndAddMainPanel(mainPane.cap, true);
		
		captainInfoPane = new JPanel();
		captainInfoPane.setLayout(verticalManager);
		captainInfoPane.add(mainPane.rad);
		captainInfoPane.add(mainPane.egr);
		primaryPane.add(captainInfoPane, BorderLayout.EAST);
		
	}

	private void makeEngineer() {
		makeAndAddSidePanel(mainPane.xo, mainPane.cap);
		makeAndAddMainPanel(mainPane.egr, false);
	}

	private void makeFirstOfficer() {
		makeAndAddSidePanel(sidePane, mainPane.hth , mainPane.rad, mainPane.cap, BorderLayout.LINE_START);
		makeAndAddMainPanel(mainPane.xo, true);
		
	}

	private void makeNetwork() {
		mainPane.add(mainPane.net, BorderLayout.CENTER);
		
	}

	private void makeRadio() {
		makeAndAddSidePanel(mainPane.itl, mainPane.cap);
		makeAndAddMainPanel(mainPane.rad, false);
		
	}
	
	private void makeAndAddSidePanel(ShipPanel a, ShipPanel b){
		makeAndAddSidePanel(sidePane, null, a , b, BorderLayout.LINE_START);
	}
	
	
	private void makeAndAddSidePanel(JPanel x, ShipPanel a, ShipPanel b, ShipPanel c, String placement) {
		x = new JPanel();
		x.setLayout(verticalManager);
		if (a != null) x.add(a);
		x.add(b);
		x.add(c);
		
		mainPane.add(x, placement);
		
	}
	
	private void makeAndAddMainPanel(ShipPanel a, boolean health){
		primaryPane = new JPanel();
		primaryPane.setLayout(paneManager);
		
		primaryPane.add(a, BorderLayout.CENTER);
		if (health) primaryPane.add(mainPane.hth, BorderLayout.NORTH);
		primaryPane.add(mainPane.net, BorderLayout.SOUTH);
		
		mainPane.add(primaryPane, BorderLayout.EAST);
		
	}

}
