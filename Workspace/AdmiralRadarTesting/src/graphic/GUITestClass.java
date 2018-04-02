package graphic;

import game.Role;
import visual.common.NetworkPane;
import visual.util.operations.GUIFactory;

public class GUITestClass {

	public static void main(String[] args) throws InterruptedException {
		
		//Create GUI Factory
		GUIFactory factory = new GUIFactory();
		
		//Create and Show GUI
		new Thread(() ->  factory.beginGUI()).start();
		
		
		//Start local test server
		TestServerSam ts = new TestServerSam(Role.CAPTAIN);
		Thread t = new Thread(ts);
		t.start();
		
		((NetworkPane) factory.getShipPanel()).cxt.doClick();
		((NetworkPane) factory.getShipPanel()).log.doClick();
		((NetworkPane) factory.getShipPanel()).ready.doClick();
		
		Thread.sleep(30000);
		System.exit(1);
		
		

	}

}
