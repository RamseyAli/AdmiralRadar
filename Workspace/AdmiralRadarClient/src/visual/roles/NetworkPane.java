package visual.roles;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NetworkPane extends ShipPanel {
	
	
	//0 - Login
	//1 - Game Select
	//2 - In Game Chat
	private int state = 0;
	
	
	public NetworkPane() {
		super(Color.ORANGE);
		
	//	add(new JTextField(20));
	//	add(new JTextField(20));
	//	add(new JTextField(20));
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public void draw() {
		
		switch(state){
			case 0: drawLogin(); break;
			case 1: drawSelection(); break;
			case 2: drawInGame(); break;
		}
		
	}
	
	public void drawLogin(){
		
		
		
	}
	
	public void drawSelection(){
		
	}
	
	public void drawInGame(){
		
	}

	public void setState(int i) {
		if ((state != i)&&
		((i == 0)||(i == 1)||(i == 2))	)
			state = i;
		repaint();
	}
	
	public int getState() {
		return state;
	}
	
}
