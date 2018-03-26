package visual.util;

import visual.util.operations.GUIFactory;

public class GUITestMain {

	public static void main(String[] args) throws InterruptedException {
		
		//Create GUI Factory
		GUIFactory factory = new GUIFactory();
		
		//Create and Show GUI
		new Thread(() ->  factory.beginGUI()).start();
		
		
		//Start local test server
		TestServerSam ts = new TestServerSam();
		Thread t = new Thread(ts);
		//t.run();
		
		Thread.sleep(1000000);
		System.exit(1);
		
		

	}

}
