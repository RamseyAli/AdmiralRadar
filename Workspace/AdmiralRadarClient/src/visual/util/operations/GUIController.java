package visual.util.operations;

import java.awt.Component;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import game.Direction;
import game.GameMap;
import game.Position;
import game.Role;
import game.Spaceship;
import network.ConnectionManager;
import ops.User;
import visual.roles.NetworkPane;
import visual.util.components.ShipPanel;

public class GUIController {

	ArrayList<ShipPanel>	toUpdate	= new ArrayList<ShipPanel>();
	ConnectionManager		cm;
	GUIFactory				fac;

	private User		u;
	private GameMap		m;
	private Role		r	= Role.NETWORK;
	private Spaceship	s;
	private Position	sp;

	public GUIController(GUIFactory guiFactory) {
		fac = guiFactory;
	}

	// 2 - Connection Failure
	// 3 - Connected to Server
	// 4 - Logged In To Server
	public int login(String user, String pswd) {
		return cm.loginToServer( user , pswd );
	}

	public int newUser(String URL, String user, String password) {
		return cm.registerUserWithServer( URL , user , password );
	}

	// 2 - Connection Failure
	// 3 - Connected to Server
	// 4 - Logged In To Server
	public int connect(InetAddress url) throws IOException {
		return cm.connectToServer( url );
	}

	public void threadSafeRepaint(JComponent jc) {
		SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				jc.repaint();
			}
		} );
	}

	// [username, win, loss, avatar]
	public String[] getUserInfo() {

		return new String[] { u.getUsername() , "" + u.getWins() , "" + u.getLosses() , u.getAvatar() };
	}

	public void setAvatar(String s) {
		cm.newAvatar( s );
	}

	public void addToUpdatePuddle(ShipPanel sp) {
		toUpdate.add( sp );
	}

	public void removeFromUpdatePuddle(ShipPanel sp) {
		toUpdate.remove( sp );
	}

	public void updatePuddle() {

	}

	public void setConnector(ConnectionManager n) {
		cm = n;
	}

	public void setUser(User usr) {
		u = usr;
	}

	public int ready() {
		return cm.ready();
	}

	public GUIFactory getFactory() {
		return fac;
	}

	public Component getGUIFrame() {
		return fac.getFrame();
	}

	public void setStatusMessage(String s) {
		System.out.println( "SetMessage: " + s );
		try {
			if (fac.getShipPanel().getClass() == Class.forName( "visual.roles.NetworkPane" ))
				( (NetworkPane) fac.getShipPanel() ).setServerMessageText( s );
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public User getUser() {
		return u;
	}

	public void setMap(GameMap nextMap) {
		m = nextMap;
	}

	public GameMap getMap() {
		return m;
	}

	public void setStartLocation(Position start) {
		sp = start;

	}

	public Position getStartLocation() {
		return sp;

	}

	public Role getRole() {
		return r;
	}

	public void setRole(Role r) {
		this.r = r;
		fac.setGameRole( r );
	}

	public Spaceship getSpaceship() {
		if (s == null) return new Spaceship();
		return s;
	}

	public void setSpaceship(Spaceship s) {
		System.out.println( fac.getFrame().getTitle() + ": I have a spaceship!" );
		this.s = s;
		globalRefresh();

	}

	public void globalRefresh() {
		fac.refresh();
		threadSafeRepaint( fac.getShipPanel() );

	}

	public void flyInDirection(Direction south) {
		cm.sendDirectionCommand(south);
		
	}

	public void charge(String name) {
		s.getShipSystem().chargeSystem( name );
		cm.sendChargeCommand(name);
		
	}

	public void refreshFrame() {
		fac.getFrame().threadSafeRepaint();
		
	}

}
