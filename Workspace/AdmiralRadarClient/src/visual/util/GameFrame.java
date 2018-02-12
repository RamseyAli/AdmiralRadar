package visual.util;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 8239573944084731241L;
	
	private GamePanel mainPane;
	
	public GameFrame(GamePanel p){
		mainPane = p;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		transparent(false);
		setContentPane(mainPane);

		setLocation(400,400);
		setSize(new Dimension(500,300));
		setVisible(true);
		
		
		
		
	}
	
	private void transparent(boolean b) {
		setUndecorated(b);
		if (b) setBackground(new Color(0, 0, 0, 0));
		mainPane.setOpaque(b);
		
	}

	void update(){
		
		mainPane.repaint();
		
	}
}
