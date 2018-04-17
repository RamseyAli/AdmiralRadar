package graphic;

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
		TestServerSam ts = new TestServerSam( Role.ENGINE );
		Thread t = new Thread( ts );
		t.start();

	//	( (NetworkPane) factory.getShipPanel() ).cxt.doClick();
	//	( (NetworkPane) factory.getShipPanel() ).log.doClick();
	//	( (NetworkPane) factory.getShipPanel() ).ready.doClick();

		Thread.sleep( 100000 );
		System.exit( 1 );

	}

}
