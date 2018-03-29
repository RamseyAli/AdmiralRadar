package visual.util.operations;

import java.awt.Component;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import network.ConnectionManager;
import ops.User;
import visual.roles.NetworkPane;
import visual.roles.ShipPanel;

public class GUIController {
	
	ArrayList<ShipPanel> toUpdate = new ArrayList<ShipPanel>();
	ConnectionManager cm;
	GUIFactory fac;
	User u;
	
	public GUIController(GUIFactory guiFactory) {
		fac = guiFactory;
	}

	// 2 - Connection Failure
	// 3 - Connected to Server
	// 4 - Logged In To Server
	public int login(String user, String pswd) {
		return cm.loginToServer(user, pswd);
	}

	// 2 - Connection Failure
	// 3 - Connected to Server
	// 4 - Logged In To Server
	public int connect(InetAddress url) throws IOException{
		return cm.connectToServer(url);	
	}
	
	
	public void threadSafeRepaint(JComponent jc){
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		        jc.repaint();
		    }
		});
	}

	//[username, win, loss, avatar]
	public String[] getUserInfo() {
		
		return new String[] {u.getUsername() , "" + u.getWins() , "" + u.getLosses() , u.getAvatar() };
	}

	public void setAvatar(String s) {		
		cm.newAvatar(s);
	}

	public void addToUpdatePuddle(ShipPanel sp) {
		toUpdate.add(sp);		
	}
	
	public void removeFromUpdatePuddle(ShipPanel sp) {
		toUpdate.remove(sp);
	}
	
	public void updatePuddle(){
	
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
	
	public GUIFactory getFactory(){
		return fac;
	}

	public Component getGUIFrame() {
		return fac.getFrame();
	}

	public void setStatusMessage(String s) {
		System.out.println("SetMessage: " + s);
		try {
			if (fac.getShipPanel().getClass() == Class.forName("visual.roles.NetworkPane"))
			((NetworkPane) fac.getShipPanel()).setServerMessageText(s);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
