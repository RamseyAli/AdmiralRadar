package graphic;

import game.Role;
import pref.GamePreferences;
import visual.roles.NetworkPane;
import visual.util.operations.GUIFactory;

@SuppressWarnings("unused")
public class ManualGUI {

	public static void main(String[] args) throws InterruptedException {

		GamePreferences.setFullscreen( false );
		// Create GUI Factory
		GUIFactory factory = new GUIFactory();

		// Create and Show GUI
		new Thread( () -> factory.beginGUI() ).start();

	}

}
