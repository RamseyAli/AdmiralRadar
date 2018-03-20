package visual.util.operations;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import network.ConnectionManager;
import visual.roles.ShipPanel;

public class GUIController {
	
	private String avURL = "http://www.withanaccent.com/wp-content/uploads/2012/07/avatar-aang.jpg";
	ArrayList<ShipPanel> toUpdate = new ArrayList<ShipPanel>();
	ConnectionManager cm;
	
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
		
		return new String[] {"Aang", "1" , " 20" , avURL};
	}

	public void setAvatar(String s) {		
		avURL = s;		
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

}
