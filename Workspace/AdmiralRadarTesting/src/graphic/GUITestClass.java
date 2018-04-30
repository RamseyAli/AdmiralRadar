package graphic;

import javax.swing.JOptionPane;

import game.Role;
import pref.GamePreferences;
import visual.roles.NetworkPane;
import visual.util.operations.GUIFactory;

public class GUITestClass {

	public static void main(String[] args) throws InterruptedException {

		GamePreferences.setFullscreen( false );
		// Create GUI Factory
		GUIFactory factory = new GUIFactory();

		// Create and Show GUI
		new Thread( () -> factory.beginGUI() ).start();

		// Start local test server
		String[] options = {"Captain",
				"First Officer",
		"Engineer",
		"Radio Officer"};
		
		Role rx = Role.NETWORK;

		switch(JOptionPane.showOptionDialog(null,
				"Which Demo would you like to see?",
				"Demo Options",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				null)){
					case 0:
						rx = Role.CAPTAIN;
						break;
					case 1:
						rx = Role.FIRST;
						break;
					case 2:
						rx = Role.ENGINE;
						break;
					case 3:
						rx = Role.RADIO;
						break;
					default: break;
		}
		
		TestServerSam ts = new TestServerSam( rx );
		Thread t = new Thread( ts );
		t.start();

		( (NetworkPane) factory.getShipPanel() ).svr.setSelectedIndex( 2 );
		( (NetworkPane) factory.getShipPanel() ).cxt.doClick();
		( (NetworkPane) factory.getShipPanel() ).log.doClick();
		( (NetworkPane) factory.getShipPanel() ).ready.doClick();

		Thread.sleep( 100000 );
	//	System.exit( 1 );

	}

}
