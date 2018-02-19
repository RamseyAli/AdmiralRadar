package visual.util;

import javax.swing.JFrame;

import general.Role;
import visual.roles.ExecutivePane;
import visual.roles.HealthPane;
import visual.roles.ShipPanel;

public class GUITestMain {

	public static void main(String[] args) throws InterruptedException {
		
		boolean a = true;
		
		if (a){
			GameFrame f = new GameFrame();
			ShipPanel h = new ExecutivePane();
			f.setSize(400,400);
			f.setPanel(h);
			f.repaint();
			h.repaint();
			f.setVisible(true);
		}
		
		else{
		
		GameFrame f = new GameFrame();
		GamePanel p = new GamePanel(Role.CAPTAIN);
		
		f.setPanel(p);
		f.setVisible(true);
		
		
		Thread.sleep(1000);
		
		}
		Thread.sleep(20000);
		
		System.exit(1);
		
		

	}

}
