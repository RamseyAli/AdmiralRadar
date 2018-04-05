package integrated;

import java.awt.Rectangle;

import logic.AdmRadarServer;
import pref.GamePreferences;
import visual.roles.NetworkPane;
import visual.util.operations.GUIFactory;

public class Testapus {

	public static class ARClient{

		public GUIFactory factory;

		public ARClient(Rectangle r){
			//Create GUI Factory
			factory = new GUIFactory();

			//Create and Show GUI
			new Thread(() ->  factory.beginGUI(r)).start();


		}
	}

	public static class ARServer{

		public AdmRadarServer server;

		public ARServer(){

			//Start Server in new Thread
			new Thread(() -> server = new AdmRadarServer()).start();

		}
	}

	public static void main(String[] args) throws InterruptedException {

		//Make sure the Graphics Fullscreen doesnt crash
		GamePreferences.setFullscreen(false);
		
		//Make a shitton of Clients
		ARClient[] clients = new ARClient[8];
		final int S = 400;
		final int S2 = (int) (S * 0.9);
		for (int i = 0; i < 8; i++) clients[i] = new ARClient(new Rectangle(S * (i % 4), (int) (S2 * 1.2) * (i / 4), S , S2));

		//Make a Server
		@SuppressWarnings("unused")
		ARServer serve = new ARServer();

		System.out.println("Server Running");

		//Connect to Server
		for (int i = 0; i < 8; i++) 
			((NetworkPane) clients[i].factory.getShipPanel()).cxt.doClick();
		
		
		System.out.println("Connected!");
		
		Thread.sleep(100);
		
		//Log In
		for (int i = 0; i < 8; i++) {
			((NetworkPane) clients[i].factory.getShipPanel()).log.doClick();
		}
		
		Thread.sleep(100);
		
		//Set Ready
		for (int i = 0; i < 8; i++) 
			((NetworkPane) clients[i].factory.getShipPanel()).ready.doClick();
		

		//Report Success
		System.out.println("We Are Moving!");
		
		Thread.sleep(20000);
		
		System.exit(0);

	}

}
