package visual.util.operations;

import java.awt.Component;

import game.Role;
import network.ConnectionManager;
import visual.roles.CaptainPane;
import visual.roles.EngineerPane;
import visual.roles.ExecutivePane;
import visual.roles.NetworkPane;
import visual.roles.RadioPane;
import visual.roles.ShipPanel;
import visual.util.components.GameFrame;

public class GUIFactory {

	
	
	
	private GUIController nexus;
	
	private GameFrame f;
	private ShipPanel h;
	private ConnectionManager n;
	
	public GUIFactory() {
		
		nexus = new GUIController(this);
		
		
		f = new GameFrame();
		h = new NetworkPane(nexus);
		n = new ConnectionManager(nexus);
		nexus.setConnector(n);
		
	}

	public void beginGUI() {
		f.setSize(1000,600);
		f.setPanel(h);
		f.repaint();
		h.repaint();
		f.setVisible(true);
		
	}

	public void setGameRole(Role r) {
		switch(r){
		case CAPTAIN:
			h = new CaptainPane(nexus);
			break;
		case ENGINE:
			h = new EngineerPane(nexus);
			break;
		case FIRST:
			h = new ExecutivePane(nexus);
			break;
		case NETWORK:
			break;
		case RADIO:
			h = new RadioPane(nexus);
			break;
		default:
			break; 
		}
		
		f.setPanel(h);
		f.repaint();
		h.repaint();
		f.setVisible(true);
		
	}

	public GameFrame getFrame() {
		return f;
	}


	
	
	
}
