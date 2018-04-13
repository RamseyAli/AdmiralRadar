package visual.util.operations;

import java.awt.Rectangle;
import java.awt.Toolkit;

import game.Role;
import network.ConnectionManager;
import pref.GamePreferences;
import game.Position;
import visual.roles.*;
import visual.util.components.GameFrame;
import visual.util.components.ShipPanel;

public class GUIFactory {

	private GUIController nexus;

	private GameFrame			f;
	private ShipPanel			h;
	private ConnectionManager	n;

	public GUIFactory() {

		nexus = new GUIController( this );

		f = new GameFrame( nexus );
		h = new NetworkPane( nexus );
		n = new ConnectionManager( nexus );
		nexus.setConnector( n );

	}

	public GUIFactory(String n2) {
		this();
		
		f.setTitle( n2 );
		n.setName(n2);
	}

	public void beginGUI(Rectangle r) {


		
		f.setSize( r.getSize() );
		f.setLocation( r.getLocation() );

		f.setPanel( h );

		System.out.println( "xx" );
		
		f.repaint();
		
		System.out.println( "x2" );
		
		h.repaint();
		
		System.out.println( "x3" );
		
		f.setVisible( true );
		
		System.out.println( "xx" );

	}

	public void beginGUI() {
		if (GamePreferences.isFullscreen())
			beginGUI( new Rectangle( 0 , 0 , (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() ,
					(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() ) );

		else beginGUI( new Rectangle( 100 , 100 , 1000 , 600 ) );

	}

	public void setGameRole(Role r) {
		if (nexus.getRole() != r) nexus.setRole( r );

		switch (r) {
			case CAPTAIN:
				h = new CaptainPane( nexus );
				break;
			case ENGINE:
				h = new EngineerPane( nexus );
				break;
			case FIRST:
				h = new ExecutivePane( nexus );
				break;
			case NETWORK:
				break;
			case RADIO:
				h = new RadioPane( nexus );
				break;
			default:
				break;
		}

		f.setPanel( h );
		f.repaint();
		h.repaint();
		f.setVisible( true );

	}

	public GameFrame getFrame() {
		return f;
	}

	public ShipPanel getShipPanel() {
		return h;
	}

	public Position getInitialPositionFromCaptain() {
		if (!( h instanceof CaptainPane )) {
			throw new IllegalArgumentException();
		} else return ( (CaptainPane) h ).getStartLocation();

	}

	public void refresh() {
		
		f.refresh();
	}

}
