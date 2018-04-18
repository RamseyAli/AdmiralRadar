package integrated;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;

import logic.AdmRadarServer;
import pref.GamePreferences;
import visual.roles.NetworkPane;
import visual.util.operations.GUIFactory;

public class BabyTestapus {

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
		@SuppressWarnings("unused")
		ARServer serve = new ARServer();

		System.out.println( "Server Running" );

		// Connect to Server
		for (int i = 0; i < 4; i++)
			( (NetworkPane) clients[i].factory.getShipPanel() ).cxt.doClick();

		System.out.println( "Connected!" );

		Thread.sleep( 100 );

		// Log In
		for (int i = 0; i < 4; i++) {
			( (NetworkPane) clients[i].factory.getShipPanel() ).setUsername( "alohomora" );//"USER" + (i+1) );
			( (NetworkPane) clients[i].factory.getShipPanel() ).setPassword( "password" + (i+1) );
			( (NetworkPane) clients[i].factory.getShipPanel() ).log.doClick();

		}

		Thread.sleep( 100 );

		// Set Ready
		for (int i = 0; i < 4; i++)
			( (NetworkPane) clients[i].factory.getShipPanel() ).ready.doClick();

		// Report Success
		System.out.println( "We Are Moving!" );



		//Click on start positions
		Robot r = null;
		try {
			r = new Robot();
		}
		catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




		r.mouseMove( 150 , 220 );
		r.delay( 1000 );
		r.mousePress( InputEvent.BUTTON1_DOWN_MASK );
		r.mouseRelease( InputEvent.BUTTON1_DOWN_MASK );
		
		Point px = clients[4].factory.getShipPanel().getLocationOnScreen();
		
		r.mouseMove( px.x + 150 , px.y + 180 );
		r.delay( 1000 );
		r.mousePress( InputEvent.BUTTON1_DOWN_MASK );
		r.mouseRelease( InputEvent.BUTTON1_DOWN_MASK );


		

	}
}