package visual.util.operations;

import network.ConnectionManager;
import visual.roles.NetworkPane;
import visual.roles.ShipPanel;
import visual.util.components.GameFrame;

public class GUIFactory {

	
	
	
	private GUIController nexus;
	
	private GameFrame f;
	private ShipPanel h;
	private ConnectionManager n;
	
	public GUIFactory() {
		
		nexus = new GUIController();
		
		
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


	
	
	
}
