package visual.roles;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLProtocolException;
import javax.swing.BorderFactory;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import pref.GamePreferences;
import visual.util.ColorPallate;
import visual.util.components.ShipPanel;
import visual.util.operations.GUIController;

public class NetworkPane extends ShipPanel implements ActionListener, ComponentListener {

	// Panel Declarations
	JPanel x, con, usrBtnPnl, userTab, gameTab;

	// Connector Declarations
	JComboBox<String>				svr;
	DefaultComboBoxModel<String>	model;
	JLabel							cxnStatus;

	// Connection Interactor Declarations
	JTextField		usr;
	JPasswordField	pwd;
	public JButton	cxt, clr, log, ready, reg;

	// Tabbed Pane
	JTabbedPane tab;

	// User Info Panel Declarations
	JLabel	username, wins, losses, avatar, logo;
	JButton	avatarButton, resetButton;

	// Game Info Panel Declarations
	JLabel gameStatus;

	// 1 - Start
	// 2 - Error
	// 3 - Connected
	// 4 - Login Fail
	// 5 - Logged in
	int state = 0;

	public NetworkPane(GUIController cx) {
		super( cx );

		// Panel Instantiations
		x = new JPanel();
		con = new JPanel();
		userTab = new JPanel();
		gameTab = new JPanel();
		usrBtnPnl = new JPanel();
		tab = new JTabbedPane();
		
		//Listener
		addComponentListener( this );

		setOpaque( false );
		x.setBackground( ColorPallate.PRIMARY_TRANSPARENT_GRAY );
		x.setBorder( new EmptyBorder( 10 , 10 , 10 , 10 ) );

		x.setOpaque(false);
		con.setOpaque( false );
		usrBtnPnl.setOpaque( false );
		userTab.setOpaque( false );
		gameTab.setOpaque( false );
		tab.setOpaque( false );

		// Layout Management
		con.setLayout( new BoxLayout( con , BoxLayout.X_AXIS ) );
		usrBtnPnl.setLayout( new BoxLayout( usrBtnPnl , BoxLayout.X_AXIS ) );
		userTab.setLayout( new BoxLayout( userTab , BoxLayout.Y_AXIS ) );
		gameTab.setLayout( new BoxLayout( gameTab , BoxLayout.Y_AXIS ) );
		x.setLayout( new BoxLayout( x , BoxLayout.Y_AXIS ) );
		usrBtnPnl.setAlignmentX( Component.CENTER_ALIGNMENT );

		// Connection Label
		cxnStatus = new JLabel( "Default" );
		cxnStatus.setAlignmentX( Component.CENTER_ALIGNMENT );

		cxnStatus.setForeground( Color.RED );
		
		//Bolding
		cxnStatus.setFont(cxnStatus.getFont().deriveFont(Font.BOLD));

		// Combo Box
		svr = new JComboBox<>( GamePreferences.getIPs() );
		svr.setEditable( true );
		svr.setCursor(new Cursor(Cursor.HAND_CURSOR));
		model = (DefaultComboBoxModel<String>) svr.getModel();

		// Login / Connection Components
		usr = new JTextField( "TEST_USER" , 20 );
		usr.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pwd = new JPasswordField( "newpassword1234" , 20 );
		pwd.setCursor(new Cursor(Cursor.HAND_CURSOR));

		cxt = new JButton( "Connect" );
		cxt.addActionListener( this );
		cxt.setCursor(new Cursor(Cursor.HAND_CURSOR));

		clr = new JButton( "Clear" );
		clr.setCursor(new Cursor(Cursor.HAND_CURSOR));
		clr.addActionListener( this );

		log = new JButton( "Login" );
		log.addActionListener( this );
		log.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		reg = new JButton( "Register" );
		reg.addActionListener( this );
		reg.setCursor(new Cursor(Cursor.HAND_CURSOR));

		// Server Connection Line
		con.add( svr );
		con.add( clr );
		con.add( cxt );

		// User Buttons Line
		usrBtnPnl.add( log );
		usrBtnPnl.add( reg );

		// Game Tab
		ready = new JButton( "Ready to Play" );
		ready.setCursor(new Cursor(Cursor.HAND_CURSOR));
		gameStatus = new JLabel( "Not Ready" );
		gameStatus.setForeground( Color.WHITE );

		gameTab.add( ready );
		gameTab.add( gameStatus );

		ready.setAlignmentX( Component.CENTER_ALIGNMENT );
		ready.addActionListener( this );
		gameStatus.setAlignmentX( Component.CENTER_ALIGNMENT );

		// User Tab
		username = new JLabel();
		wins = new JLabel();
		losses = new JLabel();
		avatar = new JLabel();
		logo = new JLabel();
		
		avatar.setOpaque( false );
		logo.setOpaque(false);
		avatarButton = new JButton( "Change Avatar" );
		avatarButton.addActionListener( this );
		avatarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		resetButton = new JButton( "Reset Password " );
		resetButton.addActionListener( this );
		resetButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		username.setForeground(Color.WHITE);
		wins.setForeground(Color.WHITE);
		losses.setForeground(Color.WHITE);

		username.setAlignmentX( Component.CENTER_ALIGNMENT );
		wins.setAlignmentX( Component.CENTER_ALIGNMENT );
		losses.setAlignmentX( Component.CENTER_ALIGNMENT );
		avatar.setAlignmentX( Component.CENTER_ALIGNMENT );
		logo.setAlignmentX( Component.CENTER_ALIGNMENT );
		avatarButton.setAlignmentX( Component.CENTER_ALIGNMENT );
		resetButton.setAlignmentX( Component.CENTER_ALIGNMENT );
		
		
		userTab.add( avatar );
		userTab.add( username );
		userTab.add( wins );
		userTab.add( losses );
		userTab.add( avatarButton );
		userTab.add( resetButton );

		// Tabbed Panes
		tab.add( "Game" , gameTab );
		tab.add( "User" , userTab );

		// Final Assembly
		x.add(logo);
		x.add( con );
		x.add( usr );
		x.add( pwd );
		x.add( usrBtnPnl );
		x.add( cxnStatus );
		x.add( tab );
		add( x );
		setState( 1 );

	}

	private void updateUserInfoPanel() {
		String[] userData = control.getUserInfo();

		username.setText( "<html><strong>Username: </strong>" + userData[0] + "</html>");
		username.setHorizontalAlignment(SwingConstants.CENTER);
		wins.setText( "<html><strong>Wins: </strong>" + userData[1] + "</html>");
		wins.setHorizontalAlignment(SwingConstants.CENTER);
		losses.setText( "<html><strong>Losses: </strong>" + userData[2] + "</html>");
		losses.setHorizontalAlignment(SwingConstants.CENTER);

		// Load Avatar Image
		try {
			int x = (int) ( 400 / 2.25f );
			System.out.println( "Getting URL for " + userData[0] + ": " + userData[3] );
			URL url = new URL(userData[3]);
			URLConnection uc;
			uc = url.openConnection();
			uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");	//spoof the image request
			BufferedImage bi = ImageIO.read( uc.getInputStream() );
			Image imageIcon = bi.getScaledInstance( x , (int) ( ( bi.getHeight() * x ) / ( (float) bi.getWidth() ) ) ,
					Image.SCALE_DEFAULT );
			avatar.setIcon( new ImageIcon( imageIcon ) );
			
			avatar.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			
		}
		catch (Exception e) {
			//SSL decryption issue - use duck avatar
			try {
				int x = (int) ( 400 / 2.25f );
				Image imageIcon = new ImageIcon( (GamePreferences.RESOURCES_PATH + "Error_duck.png").replaceAll( "%20" , " " ) ).getImage();
				BufferedImage bufferedImage = new BufferedImage(imageIcon.getWidth(null), imageIcon.getHeight(null), BufferedImage.TYPE_INT_RGB);
			    Graphics g = bufferedImage.createGraphics();
			    g.drawImage(imageIcon, 0, 0, null);
			    g.dispose();
			    
			    Image imageIconFinal = bufferedImage.getScaledInstance( x , (int) ( ( bufferedImage.getHeight() * x ) / ( (float) bufferedImage.getWidth() ) ) ,
						Image.SCALE_DEFAULT );
				avatar.setIcon( new ImageIcon( imageIconFinal ) );
				
				avatar.setBorder(BorderFactory.createLineBorder(Color.WHITE));
				
				
			} catch (Exception ex) {
				System.out.println("OK boss. We have a problem here.");
			}
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void drawLogin() {

	}

	public void drawSelection() {

	}

	public void drawInGame() {

	}
	
	@Override
    public void componentResized(ComponentEvent e) {
		int w = this.getWidth();
		int h = this.getHeight();
		
		try {
			Image imageIcon = new ImageIcon( (GamePreferences.RESOURCES_PATH + "logo.png").replaceAll( "%20" , " " ) ).getImage();
			imageIcon = imageIcon.getScaledInstance( (int)(w*0.55) , (int)(h*0.45),  Image.SCALE_DEFAULT);
			
			logo.setIcon( new ImageIcon( imageIcon ) );
		} catch (Exception ex) {
			
		}
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cxt) {
			String s = (String) svr.getSelectedItem();
			if (!GamePreferences.getIPArrayList().contains( s )) {
				model.addElement( s );
				GamePreferences.addIP( s );
			}

			try {
				setState( control.connect( InetAddress.getByName( s ) ) );
			}
			catch (IOException e1) {
				setState( 2 );
			}
		} else if (e.getSource() == clr) {
			GamePreferences.clearIPs();
			model.removeAllElements();
		}
		else if (e.getSource() == log){
			setState(control.login(usr.getText(), new String(pwd.getPassword())));
			
		}
		else if (e.getSource() == avatarButton){
			String StringURL = JOptionPane.showInputDialog("Enter URL for new Avatar");
			
			//if they did not press the 'Cancel' button'
			if (StringURL != null) {
				//URL needs to have http(s):// to be considered a valid URL
				if (!StringURL.contains("http://") && !StringURL.contains("https://")) {
					StringURL = "http://" + StringURL;	//shouldn't assume https, thus use http for default
				}
				try {
					URL url = new URL(StringURL);
					URLConnection uc;
					uc = url.openConnection();
					uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");	//spoof the image request
					Image image = ImageIO.read(uc.getInputStream());
					if(image != null) {	//if not null, it was a valid image url
						control.setAvatar(StringURL);
						updateUserInfoPanel();
					}else{
						JOptionPane.showMessageDialog(null, "Please provide a valid direct image URL!", "Invalid Avatar", JOptionPane.ERROR_MESSAGE); 
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Please provide a valid URL!", "Invalid Avatar", JOptionPane.ERROR_MESSAGE); 
				}
			}

		}
		else if (e.getSource() == resetButton) {
			String[] userData = control.getUserInfo();
			String strPIN = JOptionPane.showInputDialog("Enter your PIN:");
			
			//if they did not press the 'Cancel' button'
			if (strPIN != null) {
				
				int intPIN = -1;
				try {
					intPIN = Integer.parseInt(strPIN);
					if (strPIN.length() != 4) {
						intPIN = -2; //PIN is not 4 digits
					}
				} catch (Exception ex) {
					intPIN = -1; //Invalid Integer
				}
				if (intPIN == -1) {
					JOptionPane.showMessageDialog(null, "Please provide a valid number!", "Invalid PIN", JOptionPane.ERROR_MESSAGE);
				} else if (intPIN == -2) {
					JOptionPane.showMessageDialog(null, "Please provide a 4-digit PIN!", "Invalid PIN", JOptionPane.ERROR_MESSAGE);
				} else {
					JPasswordField pf = new JPasswordField();
					int okCxl = JOptionPane.showConfirmDialog(null, pf, "New Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	
					if (okCxl == JOptionPane.OK_OPTION) {
					  String password = new String(pf.getPassword());
					  int result = control.resetPassword(intPIN, userData[0], password);
					  if (result == -1) {
							JOptionPane.showMessageDialog(null, "The PIN provided does not match the User's PIN!", "Invalid PIN", JOptionPane.ERROR_MESSAGE);
						} else if (result == -2) {
							JOptionPane.showMessageDialog(null, "There was an error resetting the password", "Misc. Error", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "Your password was changed successfully!", "Success", JOptionPane.PLAIN_MESSAGE);
						}
					}
				}
			}

		} else if (e.getSource() == reg) {
			String StringURL = JOptionPane.showInputDialog("Enter URL for new Avatar");
			
			//if they did not press the 'Cancel' button'
			if (StringURL != null) {
				//URL needs to have http(s):// to be considered a valid URL
				if (!StringURL.contains("http://") && !StringURL.contains("https://")) {
					StringURL = "http://" + StringURL;	//shouldn't assume https, thus use http for default
				}
				try {
					URL url = new URL(StringURL);
					URLConnection uc;
					uc = url.openConnection();
					uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");	//spoof the image request
					Image image = ImageIO.read(uc.getInputStream());
					if(image == null) {	//if not null, it was a valid image url
						
						int result = control.newUser( StringURL , usr.getText() , new String( pwd.getPassword() ) );
						if (result == -1) {
							JOptionPane.showMessageDialog(null, "This username is already in use!", "Invalid Username", JOptionPane.ERROR_MESSAGE);
						} else if (result == -2) {
							JOptionPane.showMessageDialog(null, "There is an error in the registration process!", "Error", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "Registration Successful! Your recovery PIN is: " + String.format("%04d", result), "Success", JOptionPane.PLAIN_MESSAGE);
						}
						
					}else{
						JOptionPane.showMessageDialog(null, "Please provide a valid direct image URL!", "Invalid Avatar", JOptionPane.ERROR_MESSAGE); 
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Please provide a valid URL!", "Invalid Avatar", JOptionPane.ERROR_MESSAGE); 
				}
			}

		} else if (e.getSource() == ready) {
			setState( control.ready() );
			updateUserInfoPanel();

		}

		repaint();

		control.threadSafeRepaint( this );

	}

	// 1 - No Connection
	// 2 - Connection Failure
	// 3 - Connected to Server
	// 4 - Not Logged In To Server
	// 5 - Logged In
	// 6 - Waiting
	public void setState(int i) {
		switch (i) {
			case 1: {
				state = 1;
				cxnStatus.setForeground( Color.RED );
				cxnStatus.setText( "Not Connected" );
				log.setVisible( false );
				reg.setVisible( false );
				cxt.setVisible( true );
				tab.removeAll();
				ready.setVisible( false );
				clr.setVisible(true);
				pwd.setVisible(false);
				usr.setVisible(false);
				svr.setVisible(true);
			}
				break;

			case 2: {
				state = 2;
				cxnStatus.setForeground( Color.RED );
				cxnStatus.setText( "Connection Failure" );
				log.setVisible( false );
				reg.setVisible( false );
				cxt.setVisible( true );
				tab.removeAll();
				ready.setVisible( false );
				clr.setVisible(true);
				pwd.setVisible(false);
				usr.setVisible(false);
				svr.setVisible(true);
			}
				break;

			case 3: {
				state = 3;
				cxnStatus.setForeground( Color.BLUE );
				cxnStatus.setText( "Connected. Please Log In." );
				reg.setVisible( true );
				log.setVisible( true );
				cxt.setVisible( false );
				tab.removeAll();
				ready.setVisible( false );
				clr.setVisible(false);
				pwd.setVisible(true);
				usr.setVisible(true);
				svr.setVisible(false);
			}
				break;

			case 4: {
				state = 4;
				cxnStatus.setForeground( Color.RED );
				cxnStatus.setText( "Username / Password Do Not Match" );
				reg.setVisible( true );
				log.setVisible( true );
				cxt.setVisible( false );
				tab.removeAll();
				ready.setVisible( false );
				clr.setVisible(false);
				pwd.setVisible(true);
				usr.setVisible(true);
				svr.setVisible(false);
			}
				break;

			case 5: {
				state = 5;
				cxnStatus.setForeground( Color.GREEN );
				cxnStatus.setText( "Logged In" );
				reg.setVisible( false );
				log.setVisible( false );
				cxt.setVisible( false );
				tab.add( "Game" , gameTab );
				tab.add( "User" , userTab );
				ready.setVisible( true );
				updateUserInfoPanel();
				clr.setVisible(false);
				pwd.setVisible(false);
				usr.setVisible(false);
				svr.setVisible(false);
			}
				break;

			case 6: {
				state = 6;
				cxnStatus.setForeground( Color.GREEN );
				cxnStatus.setText( "Logged In" );
				reg.setVisible( false );
				log.setVisible( false );
				cxt.setVisible( false );
				tab.add( "Game" , gameTab );
				tab.add( "User" , userTab );
				ready.setVisible( false );
				gameStatus.setText( "Waiting" );
				updateUserInfoPanel();
				clr.setVisible(false);
				pwd.setVisible(false);
				usr.setVisible(false);
				svr.setVisible(false);
			}
				break;
		}
	}

	public void changeMessageReceived(int i) {
		setState( i );

		updateUserInfoPanel();
		repaint();
		control.threadSafeRepaint( this );

	}

	public void setServerMessageText(String s) {
		gameStatus.setText( s );

		updateUserInfoPanel();
		repaint();
		control.threadSafeRepaint( this );
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	public void setUsername(String s) {
		usr.setText( s );

	}
	
	public void setPassword(String s) {
		pwd.setText( s );

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		//Unused
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		//Unused
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		//Unused
		
	}

}
