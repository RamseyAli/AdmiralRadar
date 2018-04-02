package visual.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import util.Preferences;
import visual.util.components.ShipPanel;
import visual.util.operations.GUIController;

public class NetworkPane extends ShipPanel implements ActionListener{


	//Panel Declarations
	JPanel x, con , usrBtnPnl , userTab , gameTab; 

	//Connector Declarations
	JComboBox<String> svr;
	DefaultComboBoxModel<String> model;
	JLabel cxnStatus;

	//Connection Interactor Declarations
	JTextField usr;
	JPasswordField pwd;
	public JButton cxt , clr , log, ready, reg;

	//Tabbed Pane
	JTabbedPane tab;

	//User Info Panel Declarations
	JLabel username, wins, losses, avatar;
	JButton avatarButton;
	
	//Game Info Panel Declarations
	JLabel gameStatus;


	//1 - Start
	//2 - Error
	//3 - Connected
	//4 - Login Fail
	//5 - Logged in
	int state = 0;


	public NetworkPane(GUIController cx) {
		super(cx);

		//Panel Instantiations
		x = new JPanel();
		con = new JPanel();
		userTab = new JPanel();
		gameTab = new JPanel();
		usrBtnPnl = new JPanel();
		tab = new JTabbedPane();

		//Layout Management
		con.setLayout(new BoxLayout(con , BoxLayout.X_AXIS));
		usrBtnPnl.setLayout(new BoxLayout(usrBtnPnl , BoxLayout.X_AXIS));
		userTab.setLayout(new BoxLayout(userTab , BoxLayout.Y_AXIS));
		gameTab.setLayout(new BoxLayout(gameTab , BoxLayout.Y_AXIS));
		x.setLayout(new BoxLayout(x , BoxLayout.Y_AXIS));
		usrBtnPnl.setAlignmentX(Component.CENTER_ALIGNMENT);

		//Connection Label
		cxnStatus = new JLabel("Default");
		cxnStatus.setAlignmentX(Component.CENTER_ALIGNMENT);

		cxnStatus.setForeground(Color.RED);

		//Combo Box
		svr = new JComboBox<>(Preferences.getIPs());
		svr.setEditable(true);
		model = (DefaultComboBoxModel<String>) svr.getModel();

		//Login / Connection Components
		usr = new JTextField("TEST_USER" , 20);
		pwd = new JPasswordField("newpassword1234", 20);
		
		cxt = new JButton("Connect");
		cxt.addActionListener(this);
		
		clr = new JButton("Clear");
		clr.addActionListener(this);
		
		log = new JButton("Login");
		log.addActionListener(this);
		log.setEnabled(false);
		
		reg = new JButton("Register");
		reg.addActionListener(this);
		reg.setEnabled(false);

		//Server Connection Line
		con.add(svr);
		con.add(clr);
		con.add(cxt);
		
		//User Buttons Line
		usrBtnPnl.add(log);
		usrBtnPnl.add(reg);

		//Game Tab
		ready = new JButton("Ready to Play");
		gameStatus = new JLabel("Not Ready");
		
		gameTab.add(ready);
		gameTab.add(gameStatus);
		
		ready.setAlignmentX(Component.CENTER_ALIGNMENT);
		ready.addActionListener(this);
		gameStatus.setAlignmentX(Component.CENTER_ALIGNMENT);

		//User Tab
		username = new JLabel();
		wins = new JLabel();
		losses = new JLabel();
		avatar = new JLabel();
		avatarButton = new JButton("Change Avatar");
		avatarButton.addActionListener(this);

		username.setAlignmentX(Component.CENTER_ALIGNMENT);
		wins.setAlignmentX(Component.CENTER_ALIGNMENT);
		losses.setAlignmentX(Component.CENTER_ALIGNMENT);
		avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
		avatarButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		userTab.add(avatar);
		userTab.add(username);
		userTab.add(wins);
		userTab.add(losses);
		userTab.add(avatarButton);

		//Tabbed Panes
		tab.add("Game", gameTab);
		tab.add("User", userTab);


		//Final Assembly
		x.add(con);
		x.add(usr);
		x.add(pwd);
		x.add(usrBtnPnl);
		x.add(cxnStatus);
		x.add(tab);
		add(x);
		setState(1);
	}

	private void updateUserInfoPanel(){
		String[] userData = control.getUserInfo();

		username.setText("Username: " + userData[0]);
		wins.setText("Wins: " + userData[1]);
		losses.setText("Losses: " + userData[2]);

		//Load Avatar Image
		try {
			int x = (int) ( usr.getWidth() / 2.25f);

			BufferedImage bi = ImageIO.read(new URL(userData[3]));
			Image imageIcon = bi.getScaledInstance(x, (int) ((bi.getHeight()*x) / ((float) bi.getWidth())), Image.SCALE_DEFAULT);
			avatar.setIcon(new ImageIcon(imageIcon));

		} catch (MalformedURLException e) {
			avatar = new JLabel("Image URL Failure");
			e.printStackTrace();
		} catch (IOException e) {
			avatar = new JLabel("Image Load Failure");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public void drawLogin(){



	}

	public void drawSelection(){

	}

	public void drawInGame(){

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cxt){
			String s = (String) svr.getSelectedItem();
			if (!Preferences.getIPArrayList().contains(s)) {
				model.addElement(s);
				Preferences.addIP(s);
			}

			try {
				setState(control.connect(InetAddress.getByName(s)));
			}catch (IOException e1) {
				setState(2);
			}
		}
		else if (e.getSource() == clr){
			Preferences.clearIPs();
			model.removeAllElements();
		}
		else if (e.getSource() == log){
			setState(control.login(usr.getText(), new String(pwd.getPassword())));
			
		}
		else if (e.getSource() == avatarButton){
			control.setAvatar(JOptionPane.showInputDialog("Enter URL for new Avatar"));
			updateUserInfoPanel();

		}
		else if (e.getSource() == reg){
			setState(control.newUser(JOptionPane.showInputDialog("Enter URL for new Avatar") , usr.getText(), new String(pwd.getPassword())));

		}
		else if (e.getSource() == ready){
			setState(control.ready());
			updateUserInfoPanel();
			
		}

		repaint();

		control.threadSafeRepaint(this);

	}

	// 1 - No Connection
	// 2 - Connection Failure
	// 3 - Connected to Server
	// 4 - Not Logged In To Server
	// 5 - Logged In 
	// 6 - Waiting
	public void setState(int i){
		switch(i){
		case 1: {
			state = 1;
			cxnStatus.setForeground(Color.RED);
			cxnStatus.setText("Not Connected");
			log.setEnabled(false);
			reg.setEnabled(false);
			cxt.setEnabled(true);
			tab.setEnabled(true);
			ready.setEnabled(false);

		} break;

		case 2: {
			state = 2;
			cxnStatus.setForeground(Color.RED);
			cxnStatus.setText("Connection Failure");
			log.setEnabled(false);
			reg.setEnabled(false);
			cxt.setEnabled(true);
			tab.setEnabled(false);
			ready.setEnabled(false);

		} break;

		case 3: {
			state = 3;
			cxnStatus.setForeground(Color.BLUE);
			cxnStatus.setText("Connected. Please Log In.");
			reg.setEnabled(true);
			log.setEnabled(true);
			cxt.setEnabled(false);
			tab.setEnabled(false);
			ready.setEnabled(false);

		} break;

		case 4: {
			state = 4;
			cxnStatus.setForeground(Color.RED);
			cxnStatus.setText("Username / Password Do Not Match");
			reg.setEnabled(true);
			log.setEnabled(true);
			cxt.setEnabled(false);
			tab.setEnabled(false);
			ready.setEnabled(false);

		} break;

		case 5: {
			state = 5;
			cxnStatus.setForeground(Color.GREEN);
			cxnStatus.setText("Logged In");
			reg.setEnabled(false);
			log.setEnabled(false);
			cxt.setEnabled(false);
			tab.setEnabled(true);
			ready.setEnabled(true);
			updateUserInfoPanel();

		} break;
		
		case 6: {
			state = 6;
			cxnStatus.setForeground(Color.GREEN);
			cxnStatus.setText("Logged In");
			reg.setEnabled(false);
			log.setEnabled(false);
			cxt.setEnabled(false);
			tab.setEnabled(false);
			ready.setEnabled(false);
			gameStatus.setText("Waiting");
			updateUserInfoPanel();

		} break;
		}
	}

	public void changeMessageReceived(int i){
		setState(i);
		
		updateUserInfoPanel();
		repaint();
		control.threadSafeRepaint(this);

	}
	
	public void setServerMessageText(String s){
		gameStatus.setText(s);
		
		updateUserInfoPanel();
		repaint();
		control.threadSafeRepaint(this);
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

}
