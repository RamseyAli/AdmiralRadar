package visual.roles;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import visual.util.Preferences;
import visual.util.operations.GUIController;

public class NetworkPane extends ShipPanel {
	
	
	JPanel x,con; 
	JComboBox<String> svr;
	JTextField usr;
	JTextField pwd;
	JButton cxt , clr , log;
	
	//0 - Login
	//1 - Game Select
	//2 - In Game Chat
	private int state = 0;
	
	
	public NetworkPane(GUIController cx) {
		super(cx);
		
		x = new JPanel();
		con = new JPanel();
		
		svr = new JComboBox<>(Preferences.getIPs());
		svr.setEditable(true);
		usr = new JTextField("Username" , 20);
		pwd = new JTextField("Password", 20);
		cxt = new JButton("Connect");
		cxt.addActionListener(control.login());
		clr = new JButton("Clear");
		log = new JButton("Login");
		
		
		con.setLayout(new BoxLayout(con , BoxLayout.X_AXIS));
		x.setLayout(new BoxLayout(x , BoxLayout.Y_AXIS));
		
		con.add(svr);
		con.add(clr);
		con.add(cxt);
		
		x.add(con);
		x.add(usr);
		x.add(pwd);
		x.add(log);
		add(x);
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
