package visual.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import pref.VisualPreferences;
import visual.roles.elements.ChatTextPane;
import visual.util.ColorPallate;
import visual.util.components.ShipPanel;
import visual.util.operations.GUIController;

public class ChatPane extends ShipPanel implements ActionListener {

	ChatTextPane chat;

	public ChatPane(GUIController ctr) {
		super( ctr );

		chat = new ChatTextPane();
		add( chat );
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
