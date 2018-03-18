package visual.util;

import visual.util.operations.GUIFactory;

public class GUITestMain {

	public static void main(String[] args) throws InterruptedException {
		
		//Create GUI Factory
		GUIFactory factory = new GUIFactory();
		
		//Create and Show GUI
		new Thread(() ->  factory.beginGUI()).start();
		
		
		
		
		
		Thread.sleep(20000);
		System.exit(1);
		
		

	}

}
