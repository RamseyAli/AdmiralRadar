package visual.util.operations;

import java.awt.BorderLayout;
import java.awt.Rectangle;

import javax.swing.JPanel;

import game.Role;
import network.ConnectionManager;
import visual.common.NetworkPane;
import visual.roles.*;
import visual.util.components.GameFrame;
import visual.util.components.ShipPanel;

public class GUIFactory {

	
	
	
	private GUIController nexus;
	
	private GameFrame f;
	private JPanel m;
	private ShipPanel h;
	private ConnectionManager n;
	
	public GUIFactory() {
		
		nexus = new GUIController(this);
		
		
		f = new GameFrame(nexus);
		h = new NetworkPane(nexus);
		n = new ConnectionManager(nexus);
		nexus.setConnector(n);
		
	}
	
	public void beginGUI(Rectangle r) {
		f.setSize(r.getSize());
		f.setLocation(r.getLocation());
		f.setPanel(h);

		
		
		f.repaint();
		h.repaint();
		f.setVisible(true);
		
	}

	public void beginGUI() {
		beginGUI(new Rectangle(100,100,1000,600));
		
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

	public ShipPanel getShipPanel(){
		return h;
	}

	
	
	
}
