package visual.util.operations;

import java.awt.Rectangle;
import java.awt.Toolkit;

import game.Role;
import network.ConnectionManager;
import util.Preferences;
import game.Position;
import visual.roles.*;
import visual.util.components.GameFrame;
import visual.util.components.ShipPanel;

public class GUIFactory {




	private GUIController nexus;

	private GameFrame f;
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
		if (Preferences.isFullscreen())
			beginGUI(new Rectangle(0,0, (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() , (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		
		else beginGUI(new Rectangle(100,100,1000,600));

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

	public Position getInitialPositionFromCaptain() {
		if (!(h instanceof CaptainPane)) throw new IllegalArgumentException();
		else return ((CaptainPane) h).getStartLocation();

	}




}
