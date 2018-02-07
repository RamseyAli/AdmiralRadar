package visual.util;

import general.Role;

public class GUITestMain {

	public static void main(String[] args) throws InterruptedException {
		
		
		GUIController c = new GUIController(Role.CAPTAIN);
		GamePanel p = new GamePanel(c);
		GameFrame x = new GameFrame(p);
		
		Thread.sleep(10000);

		System.out.println("Beep");
		
		System.exit(1);
	
		

	}

}
