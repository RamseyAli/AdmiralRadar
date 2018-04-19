package visual.common;

import static database.dbQuery.getGlobalMessages;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import pref.GamePreferences;
import pref.VisualPreferences;
import visual.roles.elements.ChatTextPane;
import visual.util.ColorPallate;
import visual.util.FontPallate;
import visual.util.components.ShipPanel;
import visual.util.operations.GUIController;

import static database.dbQuery.*;

public class ChatPane extends ShipPanel implements ActionListener, ComponentListener, MouseListener, MouseMotionListener {

	ChatTextPane global_chat, team_chat;
	JLabel avatar;
	JTextField txtbox;
	Rectangle2D r_buttn;
	
	//Holds user's data
	String[] userData;
	int team;
	String username;
	String avatarS;
	
	
	// Tabbed Pane
	JTabbedPane tab;
	
	
	//button color
	boolean white;

	public ChatPane(GUIController ctr) {
		super( ctr );
		
		
		userData = control.getUserInfo();
		if (userData == null) {	//If I cannot get user's data, such as in the start of the game
			userData = GamePreferences.DUMMYUSER;	//Username, Wins, Losses, Avatar, Team
		}
		team = Integer.parseInt(userData[4]);
		username = userData[0];
		avatarS = userData[3];
		

		global_chat = new ChatTextPane(-1);
		team_chat = new ChatTextPane(team);
		avatar = new JLabel();
		txtbox = new JTextField();
		
		//Listener
		addComponentListener( this );
		addMouseListener(this);
		addMouseMotionListener(this);
		
		//Tab init.
		tab = new JTabbedPane();
		tab.setOpaque( false );
		tab.setVisible(true);
		
		tab.add( "Global" , global_chat );
		tab.add( "Team" , team_chat );
		
		
		add( avatar );
		add( tab );
		add( txtbox );
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw() {
		
		//Load Avatar Image
		Image image = null;
		try {
			int x = (int) ( 400 / 2.25f );
			URL url = new URL(avatarS);
			URLConnection uc;
			uc = url.openConnection();
			uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");	//spoof the image request
			BufferedImage bi = ImageIO.read( uc.getInputStream() );
			image = bi.getScaledInstance( x , (int) ( ( bi.getHeight() * x ) / ( (float) bi.getWidth() ) ) , Image.SCALE_DEFAULT );
		}
		catch (Exception e) {
			try {
				image = new ImageIcon( (GamePreferences.RESOURCES_PATH + "Error_duck.png").replaceAll( "%20" , " " ) ).getImage();
				
			} catch (Exception ex) {
				System.out.println("OK boss. We have a problem here.");	//Should never have a problem loading a resouce image!!
			}
		}
	
		avatar.setAlignmentX( Component.CENTER_ALIGNMENT );
		
		int w_m = (int) ( getWidth() * ( 5.0 / 6 ) );

		Rectangle2D r_outer = new Rectangle2D.Double( ( getWidth() - w_m ) , 0 , w_m , getHeight() );

		g.setColor( ColorPallate.CHAT_PANEL_BORDER );
		g.fill( r_outer );
		swell( r_outer , -2 * VisualPreferences.GENERAL_BORDER , -2 * VisualPreferences.GENERAL_BORDER );

		g.setColor( ColorPallate.CHAT_PANEL );
		
		g.fill( r_outer );

		tab.setSize((int) ( r_outer.getWidth() ) , (int) ( r_outer.getHeight() - avatar.getHeight()  - ( txtbox.getHeight()*2.15) ));
		
		image = image.getScaledInstance((int) (getWidth() / 1.5) , (int) (getWidth() / 1.5), Image.SCALE_DEFAULT);
		avatar.setIcon( new ImageIcon( image ) );
		avatar.setLocation((int) ( r_outer.getCenterX() - tab.getWidth() / 2.4 ) , (int) ( r_outer.getY() + (int) ( r_outer.getCenterX() - tab.getWidth() / 1.4 ) ));
		avatar.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		//avatar.setAlignmentX( Component.RIGHT_ALIGNMENT );
		//avatar.setAlignmentY( Component.CENTER_ALIGNMENT );
		
		tab.setLocation( (int) ( r_outer.getCenterX() - tab.getWidth() / 2 ) , (int) ( r_outer.getY() + avatar.getHeight() + avatar.getY() ) );
		tab.setOpaque(false);
		
		//Textbox
		txtbox.setLocation(tab.getX(), tab.getY() + tab.getHeight());
		txtbox.setSize(tab.getWidth(), txtbox.getHeight());
		
		
		//Send Button
		r_buttn = new Rectangle2D.Double(txtbox.getX(), txtbox.getY() + txtbox.getHeight(), txtbox.getWidth(), txtbox.getHeight() );
		if (white) {
			g.setColor(ColorPallate.HEALTH_LIGHT);
		} else {
			g.setColor(ColorPallate.QUIT_BUTTON );
		}
		g.fill( r_buttn );
		
		///CENTERING
		    // Get the FontMetrics
		    FontMetrics metrics = g.getFontMetrics(FontPallate.fonts[2]);
		    // Determine the X coordinate for the text
		    int x = (int) (r_buttn.getX() + (r_buttn.getWidth() - metrics.stringWidth("SEND")) / 2);
		    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
		    int y = (int) (r_buttn.getY() + ((r_buttn.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent());
		    // Set the font
		    g.setFont(FontPallate.fonts[2]);
		    // Draw the String
		    g.setColor(ColorPallate.QUIT_TEXT );
		    
		    
		    g.drawString("SEND", x, y);
	    ///

	}
	
	@Override
    public void componentResized(ComponentEvent e) {
		draw();
    }

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
	    double mx = e.getX();
	    double my = e.getY();
	    if ( r_buttn.contains(mx, my) ) {
	    	white = true;
	        Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR); 
	        setCursor(cursor);
	    } else {
	    	white = false;
	        Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR); 
	        setCursor(cursor);
	    }
	    
	    control.threadSafeRepaint(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (r_buttn.contains(e.getPoint())) {
			String msg = txtbox.getText();
			if (msg.trim().length() == 0) {
				JOptionPane.showMessageDialog(null, "Please enter a non-empty message", "Invalid Message", JOptionPane.ERROR_MESSAGE);
			} else {
				if (tab.getTitleAt(tab.getSelectedIndex()).equals("Global")) {
					sendGlobalMessage(username, txtbox.getText());
				} else {
					sendTeamMessage(username, txtbox.getText(), team);
				}
			}
			txtbox.setText("");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
