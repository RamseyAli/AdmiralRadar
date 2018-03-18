package visual.util.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel mainPane;
	
	
	public GameFrame(){
		super("Admiral Radar");
		mainPane = new JPanel();
		mainPane.setLayout(new BorderLayout());
		setContentPane(mainPane);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		transparent(false);
		setLocation(100 , 100);
		
	}
	
	private void transparent(boolean b) {
		setUndecorated(b);
		setSize(new Dimension(640,	400 + (b ? 0 : 22)	));
		if (b) setBackground(new Color(0, 0, 0, 0));
		mainPane.setOpaque(b);
		
	}
	
	public void setPanel(JPanel p){
		System.out.println(mainPane.getSize());
		mainPane.removeAll();
		mainPane.add(p, BorderLayout.CENTER);
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	p.repaint();
		    }
		});
		
		
	}

}