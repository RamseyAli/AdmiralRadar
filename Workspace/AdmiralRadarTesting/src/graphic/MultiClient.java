package graphic;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;

import logic.AdmRadarServer;
import pref.GamePreferences;
import visual.roles.NetworkPane;
import visual.util.operations.GUIFactory;

@SuppressWarnings("unused")
public class MultiClient {

	public static class ARClient {

		public GUIFactory factory;

		public ARClient(String n , Rectangle r) {
			// Create GUI Factory
			factory = new GUIFactory(n);

			// Create and Show GUI
			new Thread( () -> factory.beginGUI( r ) ).start();

		}
	}

	public static class ARServer {

		public AdmRadarServer server;

		public ARServer() {

			// Start Server in new Thread
			new Thread( () -> server = new AdmRadarServer() ).start();
			//new Thread( () -> server = new ObjectiveMultithreadedTestServer() ).start();

		}
	}

	public static void main(String[] args) throws InterruptedException {

	
		// Make sure the Graphics Fullscreen doesnt crash
		GamePreferences.setFullscreen( false );

		// Make Clients
		ARClient[] clients = new ARClient[8];
		final int S = 700;
		final int S2 = (int) ( S * 0.7 );
		for (int i = 0; i < 4; i++)
			clients[i] = new ARClient("Client " + i ,  new Rectangle( (int) (S * ( i % 2 ) * 1.1) , (int) ( S2 * 1.2 ) * ( i / 2 ) , S , S2 ) );

		// Make a Server
		//@SuppressWarnings("unused")
		//ARServer serve = new ARServer();

		//System.out.println( "Server Running" );

	}
}