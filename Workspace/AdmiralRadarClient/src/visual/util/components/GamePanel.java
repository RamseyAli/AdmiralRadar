package visual.util.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import game.Role;
import visual.roles.*;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	Role display;
	JPanel primaryPane;
	JPanel sidePane;
	JPanel captainInfoPane;

	NetworkPane net;
	RadioPane rad;
	ExecutivePane xo;
	EngineerPane egr;
	CaptainPane cap;
	HealthPane hth;
	AlertsPane itl;

	public GamePanel(Role r){

		setLayout(new BorderLayout());

		display = r;
		//	net = new NetworkPane();
		//	rad = new RadioPane();
		//	xo = new ExecutivePane();
		//	egr = new EngineerPane();
		//	cap = new CaptainPane();
		//	itl = new AlertsPane();
		//	hth = new HealthPane();

	}

	public void makeGUI() {
		removeAll();
		primaryPane = null;
		sidePane = null;
		captainInfoPane = null;


		System.out.println("Building GUI" + display);
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

	}

	private void makeCaptain() {
		makeAndAddSidePanel(xo, itl);
		makeAndAddMainPanel(cap, true);

		cap.setConfigurationType(PanelPosition.MIDDLESMALL, getSize());

		captainInfoPane = new JPanel();
		captainInfoPane.setLayout(new BoxLayout(captainInfoPane, BoxLayout.Y_AXIS));

		rad.setConfigurationType(PanelPosition.SIDESQUARE, getSize());
		egr.setConfigurationType(PanelPosition.SIDESQUARE, getSize());

		captainInfoPane.add(rad);
		captainInfoPane.add(egr);


		primaryPane.add(captainInfoPane, BorderLayout.EAST);

	}

	private void makeEngineer() {
		makeAndAddSidePanel(xo, cap);
		makeAndAddMainPanel(egr, true);
	}

	private void makeFirstOfficer() {
		makeAndAddSidePanel(sidePane, hth , rad, cap, BorderLayout.LINE_START);
		makeAndAddMainPanel(xo, false);

	}

	private void makeNetwork() {
		add(net, BorderLayout.CENTER);

	}

	private void makeRadio() {
		makeAndAddSidePanel(itl, cap);
		makeAndAddMainPanel(rad, false);

	}

	private void makeAndAddSidePanel(ShipPanel a, ShipPanel b){
		makeAndAddSidePanel(sidePane, null, a , b, BorderLayout.WEST);
	}


	private void makeAndAddSidePanel(JPanel y, ShipPanel a, ShipPanel b, ShipPanel c, String placement) {
		JPanel x = new JPanel();
		x.setLayout(new BoxLayout(x, BoxLayout.Y_AXIS));
		c.setConfigurationType(PanelPosition.SIDERECT, getSize());


		if (a != null) {
			a.setConfigurationType(PanelPosition.SIDETOP , getSize());
			b.setConfigurationType(PanelPosition.SIDESQUARE, getSize());
			x.add(a);
		}

		else b.setConfigurationType(PanelPosition.SIDERECT, getSize());	

		x.add(b);
		x.add(c);

		add(x, placement);

	}

	private void makeAndAddMainPanel(ShipPanel a, boolean health){
		primaryPane = new JPanel();
		primaryPane.setLayout(new BorderLayout());
		primaryPane.setBackground(Color.BLACK);
		primaryPane.setVisible(true);
		primaryPane.setOpaque(true);


		primaryPane.add(a, BorderLayout.CENTER);
		primaryPane.add(net, BorderLayout.SOUTH);


		net.setConfigurationType(PanelPosition.BANNER, getSize());

		if (health) {
			primaryPane.add(hth, BorderLayout.NORTH);
			a.setConfigurationType(PanelPosition.MIDDLESHORT, getSize());
			hth.setConfigurationType(PanelPosition.BANNER, getSize());
		} else a.setConfigurationType(PanelPosition.MIDDLEBIG, getSize());


		add(primaryPane, BorderLayout.CENTER);

	}

	public void repaint(){
		if (display != null) makeGUI();
	}

	public GamePanel swapGUI(Role r){
		GameFrame f = findGF();

		f.setVisible(false);
		GamePanel p = new GamePanel(r);

		f.setPanel(p);
		f.validate();
		p.repaint();


		f.setVisible(true);

		return p;


	}

	private GameFrame findGF(){
		Component c = getParent();
		System.out.println("LAS: " + c);
		while(!c.toString().contains("GameFrame")){
			c = c.getParent();
			System.out.println("LA: " + c);
		}
		return (GameFrame) c;
	}


}
