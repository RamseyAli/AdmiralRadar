package visual.util;


import general.Role;
import visual.roles.CaptainPane;
import visual.roles.ShipPanel;

public class GUITestMain {

	public static void main(String[] args) throws InterruptedException {
		
		boolean a = true;
		
		if (a){
			GameFrame f = new GameFrame();
			ShipPanel h = new CaptainPane();
			f.setSize(1000,600);
			f.setPanel(h);
			f.repaint();
			h.repaint();
			f.setVisible(true);
		}
		
		else{
		
		GameFrame f = new GameFrame();
		GamePanel p = new GamePanel(Role.FIRST);
		
		f.setPanel(p);
		f.setVisible(true);
		
		
		Thread.sleep(1000);
		
		}
		Thread.sleep(40000);
		
		System.exit(1);
		
		

	}

}
