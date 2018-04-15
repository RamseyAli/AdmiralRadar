package visual.common;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import pref.VisualPreferences;
import visual.roles.elements.ChatTextPane;
import visual.util.ColorPallate;
import visual.util.components.ShipPanel;
import visual.util.operations.GUIController;

public class ChatPane extends ShipPanel implements ActionListener {

	ChatTextPane chat;
	JLabel avatar;

	public ChatPane(GUIController ctr) {
		super( ctr );

		chat = new ChatTextPane();
		avatar = new JLabel();
		/*
		int x = 1;
			
		try {
			String[] userData = ctr.getUserInfo();
			URL url = new URL(userData[3]);
			URLConnection uc;
			uc = url.openConnection();
			uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");	//spoof the image request
			BufferedImage bi = ImageIO.read( uc.getInputStream() );
			Image imageIcon = bi.getScaledInstance( x , (int) ( ( bi.getHeight() * x ) / ( (float) bi.getWidth() ) ) ,
					Image.SCALE_DEFAULT );
			avatar.setIcon( new ImageIcon( imageIcon ) );
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		avatar.setAlignmentX( Component.CENTER_ALIGNMENT );
		
		add( avatar );
		add( chat );*/
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

		int w_m = (int) ( getWidth() * ( 5.0 / 6 ) );

		Rectangle2D r_outer = new Rectangle2D.Double( ( getWidth() - w_m ) , 0 , w_m , getHeight() );

		g.setColor( ColorPallate.CHAT_PANEL_BORDER );
		g.fill( r_outer );
		swell( r_outer , -2 * VisualPreferences.GENERAL_BORDER , -2 * VisualPreferences.GENERAL_BORDER );

		g.setColor( ColorPallate.CHAT_PANEL );
		
		g.fill( r_outer );

		chat.setSize( (int) ( r_outer.getWidth() * 0.9 ) , (int) ( r_outer.getHeight() * 0.8 ) );
		chat.setLocation( (int) ( r_outer.getCenterX() - chat.getWidth() / 2 ) , (int) ( r_outer.getY() + 40 ) );

	}

}
