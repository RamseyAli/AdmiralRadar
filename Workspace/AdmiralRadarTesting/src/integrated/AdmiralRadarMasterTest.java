package integrated;

import java.awt.Rectangle;

import visual.util.operations.GUIFactory;

public class AdmiralRadarMasterTest {

	public static class ARClient{
		
		public ARClient(Rectangle r){
			//Create GUI Factory
			GUIFactory factory = new GUIFactory();
			
			//Create and Show GUI
			new Thread(() ->  factory.beginGUI(r)).start();
		}
	}
	
	public static void main(String[] args) {
		
		//Make a shitton of Clients
		ARClient[] clients = new ARClient[8];
		final int S = 400;
		final int S2 = (int) (S * 1.3);
		for (int i = 0; i < 8; i++) clients[i] = new ARClient(new Rectangle(S * (i % 4), S2 * (i / 4), S , S2));
	
		//Make a Server
		AdmRadarServer server = new AdmRadarServer();
		
		//Report Success
		System.out.println("We Are Moving!");
	
	}

}
