package visual.util.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import visual.common.ChatPane;
import visual.common.HealthPane;
import visual.common.InfoPane;
import visual.common.NetworkPane;
import visual.common.OrdersPane;
import visual.util.operations.GUIController;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel mainPane;

	private GUIController control;
	
	private HealthPane h;
	private ChatPane c;
	private OrdersPane o;
	private InfoPane i;
	
	public GameFrame(Point p , GUIController gc){
		super("Admiral Radar");
		control = gc;
		mainPane = new JPanel();
		mainPane.setLayout(new BorderLayout());
		setContentPane(mainPane);
		
		h = new HealthPane(control);
		c = new ChatPane(control);
		o = new OrdersPane(control);
		i = new InfoPane(control);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		transparent(false);
		setLocation(p.x , p.y);
	}
	
	public GameFrame(GUIController gc){
		this(new Point(100 , 100) , gc);
		
	}
	

	private void transparent(boolean b) {
		setUndecorated(b);
		setSize(new Dimension(640,	400 + (b ? 0 : 22)	));
		if (b) setBackground(new Color(0, 0, 0, 0));
		mainPane.setOpaque(b);
		
	}
	
	public void setPanel(ShipPanel p){
		mainPane.removeAll();
		mainPane.add(p, BorderLayout.CENTER);
		if (!(p instanceof NetworkPane)){
			mainPane.add(h, BorderLayout.NORTH);
			mainPane.add(c, BorderLayout.EAST);
			mainPane.add(o, BorderLayout.SOUTH);
			mainPane.add(i, BorderLayout.WEST);
			
		}

		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	p.repaint();
		    }
		});
		
		
	}

}
