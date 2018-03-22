package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import net.MyPacket;
import net.MyPacketInputStream;
import net.MyPacketOutputStream;
import ops.User;
import visual.util.Preferences;
import visual.util.operations.GUIController;

public class ConnectionManager implements Runnable{

	
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
	
	private void main(){
		while(true){
			
			
			
			
			
			
			
			
		}
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
		
		//Enable Socket Listening Loop
		new Thread(this).start();
		
		return 2;
	
		
	}
	
	public void disconnect(){
		//	interrupt.disconnected();
	}


	@Override
	public void run() {
		main();	
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
			return 6;
		} catch (IOException e) {
			return 5;
		}
		
	}
	
	
	
}
