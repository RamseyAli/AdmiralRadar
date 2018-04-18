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
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

	ChatTextPane chat;
	JLabel avatar;
	JTextField txtbox;
	Rectangle2D r_buttn;
	boolean white;

	public ChatPane(GUIController ctr) {
		super( ctr );

		chat = new ChatTextPane();
		avatar = new JLabel();
		txtbox = new JTextField();
		
		//Listener
		addComponentListener( this );
		addMouseListener(this);
		addMouseMotionListener(this);
		
		add( avatar );
		add( chat );
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
			image = new ImageIcon( (GamePreferences.RESOURCES_PATH + "Error_duck.png").replaceAll( "%20" , " " ) ).getImage();
			
		} catch (Exception ex) {
			System.out.println("OK boss. We have a problem here.");
		}
	
		avatar.setAlignmentX( Component.CENTER_ALIGNMENT );
		
		int w_m = (int) ( getWidth() * ( 5.0 / 6 ) );

		Rectangle2D r_outer = new Rectangle2D.Double( ( getWidth() - w_m ) , 0 , w_m , getHeight() );

		g.setColor( ColorPallate.CHAT_PANEL_BORDER );
		g.fill( r_outer );
		swell( r_outer , -2 * VisualPreferences.GENERAL_BORDER , -2 * VisualPreferences.GENERAL_BORDER );

		g.setColor( ColorPallate.CHAT_PANEL );
		
		g.fill( r_outer );

		chat.setSize( (int) ( r_outer.getWidth() * 0.9 ) , (int) ( r_outer.getHeight() - avatar.getHeight()  - ( txtbox.getHeight()*2.5) ) );
		
		image = image.getScaledInstance((int) (getWidth() / 1.5) , (int) (getWidth() / 1.5), Image.SCALE_DEFAULT);
		avatar.setIcon( new ImageIcon( image ) );
		avatar.setLocation((int) ( r_outer.getCenterX() - chat.getWidth() / 2.2 ) , (int) ( r_outer.getY() + (int) ( r_outer.getCenterX() - chat.getWidth() / 1.4 ) ));
		avatar.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		//avatar.setAlignmentX( Component.RIGHT_ALIGNMENT );
		//avatar.setAlignmentY( Component.CENTER_ALIGNMENT );
		
		chat.setLocation( (int) ( r_outer.getCenterX() - chat.getWidth() / 2 ) , (int) ( r_outer.getY() + avatar.getHeight() + avatar.getY() ) );
		
		//Textbox
		txtbox.setLocation(chat.getX(), chat.getY() + chat.getHeight());
		txtbox.setSize(chat.getWidth(), txtbox.getHeight());
		
		
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
		
		//Repaint Chat
		control.threadSafeRepaint(chat);

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
			sendGlobalMessage("XXX", txtbox.getText());
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
