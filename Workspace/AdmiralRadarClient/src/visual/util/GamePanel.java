package visual.util;


import javax.swing.JPanel;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private GUIController control;
	
	NetworkPane
	
	public GamePanel(GUIController c){
		control = c;
		control.setPanel(this);
		
		control.makeGUI();
		
	}


}
