package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import net.LoggerInner;
import net.MyPacket;
import net.MyPacketInputStream;
import net.MyPacketOutputStream;
import visual.util.Preferences;
import visual.util.operations.GUIController;

public class ConnectionManager implements Runnable{

	
	Socket s;
	PrintWriter out;
	BufferedReader in;
	MyPacketOutputStream oos;
	MyPacketInputStream ois;
	
	GUIController interrupt;
	
	boolean loggedIn = false;
	
	public ConnectionManager(GUIController nexus){
		interrupt = nexus;
		
	}
	
	private void main(){
		while(true){
			if (loggedIn){
				
				
				
				
				
				
				
				
				
			}		
		}
	}
	
	public int connectToServer(InetAddress svr){
		
		try {
			s = new Socket(svr, Preferences.getPort());
			
			out 	= 	new PrintWriter(s.getOutputStream(), true);
			oos 	= 	new MyPacketOutputStream(s.getOutputStream());
            in 		= 	new BufferedReader(new InputStreamReader(s.getInputStream()));
            ois 	= 	new MyPacketInputStream(s.getInputStream());
    		//Enable Socket Listening Loop
    		new Thread(this).start();
    		
		} catch (IOException e) {
			System.out.println(e);
			return 2;
		}
		
		return 3;
		
	}
	
	
	public int loginToServer(String user, String hash){
		
		try {
			oos.sendLoggerInner(new LoggerInner(user, hash));
			
			
			@SuppressWarnings("unchecked")
			MyPacket<LoggerInner> x = (MyPacket<LoggerInner>) ois.readObject();
			System.out.println("MK:" + x.getObject().getResult());
			switch(x.getObject().getResult()){
			case -1: return 2;
			case 0: return 4;
			case 1: return 5;
			}
			
			
			
		} catch (IOException e) {
			return 2;
		} catch (ClassNotFoundException e) {
			return 2;
		}
		return 2;
	
		
	}
	
	public void disconnect(){
		
	//	interrupt.disconnected();
	}


	@Override
	public void run() {
		
		main();
		
		
	}
	
	
	
}
