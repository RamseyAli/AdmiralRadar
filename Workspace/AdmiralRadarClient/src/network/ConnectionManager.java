package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

import game.Role;
import net.MyPacket;
import net.MyPacketInputStream;
import net.MyPacketOutputStream;
import ops.User;
import util.Preferences;
import visual.util.operations.GUIController;

public class ConnectionManager {

	
	Socket s;
	PrintWriter out;
	BufferedReader in;
	MyPacketOutputStream oos;
	MyPacketInputStream ois;
	
	GUIController interrupt;
	
	User u;
	
	
	public ConnectionManager(GUIController nexus){
		interrupt = nexus;
		
	}
	
	public int connectToServer(InetAddress svr){
		
		try {
			s = new Socket(svr, Preferences.getPort());
			
			out 	= 	new PrintWriter(s.getOutputStream(), true);
			oos 	= 	new MyPacketOutputStream(s.getOutputStream());
            in 		= 	new BufferedReader(new InputStreamReader(s.getInputStream()));
            ois 	= 	new MyPacketInputStream(s.getInputStream());

    		
		} catch (IOException e) {
			System.out.println(e);
			return 2;
		}
		
		return 3;
		
	}
	
	
	public int loginToServer(String user, String hash){
		
		try {
			oos.sendUser(new User(user, hash));
			
			
			@SuppressWarnings("unchecked")
			MyPacket<User> x = (MyPacket<User>) ois.readObject();
			
			interrupt.setUser(x.getObject());
			
			switch(x.getObject().getResult()){
			case -1: return 2;
			case 0: return 5;
			case 1: return 4;
			case 2: return 4;
			}
			
			
			
		} catch (IOException e) {
			return 2;
		} catch (ClassNotFoundException e) {
			return 2;
		}
		
		
		return 2;
	
		
	}
	
	public void disconnect(){
		JOptionPane.showMessageDialog(interrupt.getGUIFrame(), "Connection Lost. You Loose. Bye!");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	public void newAvatar(String s) {
		u.setAvatar(s);
		try {
			oos.sendUser(u);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public int ready() {
		try {
			oos.sendString("READY");
			new Thread(() ->  waitForStart()).start();
			return 6;
		} catch (IOException e) {
			return 5;
		}
		
	}

	private void waitForStart() {
		
		try {
			Role r = null;
			if ((r = ois.getNextRole()) != null){
				interrupt.getFactory().setGameRole(r);
				switch(r){
				case CAPTAIN: captainNetworkLoop();
					break;
				case ENGINE: engineerNetworkLoop();
					break;
				case FIRST: firstOfficerNetworkLoop();
					break;
				case NETWORK:
					break;
				case RADIO: radioOfficerNetworkLoop();
					break;
				default:
					break;
				
				}
			}
			
		} catch (IOException e) {
		}
	}

	private void radioOfficerNetworkLoop() {
		// Listens for RO commands
		
	}

	private void firstOfficerNetworkLoop() {
		// Listen for FO Info
		
	}

	private void engineerNetworkLoop() {
		// Listen for ENGR info
		
	}

	private void captainNetworkLoop() {
		// Listen for CPT info
		
	}
	
	
	
}
