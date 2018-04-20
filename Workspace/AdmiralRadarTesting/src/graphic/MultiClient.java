package graphic;

import game.Role;
import pref.GamePreferences;
import visual.roles.NetworkPane;
import visual.util.operations.GUIFactory;

@SuppressWarnings("unused")
public class MultiClient {
	private static final int NUM = 8;
	public static int i;
	public static void main(String[] args) throws InterruptedException {
		
		GamePreferences.setFullscreen( false );
		// Create GUI Factory
		GUIFactory facs[] = new GUIFactory[NUM];
		
		for (i = 0; i < NUM; ++i)
			facs[i] = new GUIFactory();

		// Create and Show GUI
		for (i = 0; i < NUM; ++i)
			new Thread( () -> facs[i].beginGUI() ).start();

	}

}
