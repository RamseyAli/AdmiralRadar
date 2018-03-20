package visual.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import net.LoggerInner;
import net.MyPacket;

public class TestServer implements Runnable{




	@Override
	public void run() {

		int portNumber = 2069;
		boolean listening = true;

		try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
			while (listening) {
				System.out.println("Loop!");
				Socket s = serverSocket.accept();
				System.out.println("New Connection");

				try (
						ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
						ObjectInputStream in = new ObjectInputStream(s.getInputStream());
						) {
					Object inputLine;
					
					while (true){
						if((inputLine = in.readObject()) != null) {
							@SuppressWarnings("unchecked")
							MyPacket<LoggerInner> mp = ((MyPacket<LoggerInner>) inputLine);
							
								mp.getObject().loginSuccessful((mp.getObject().getUsername().equals("Username"))&&
										(mp.getObject().getEncryptedPassword().equals("Password")));
								
							out.writeObject(inputLine);
							out.flush();
						}
					}
					
				} catch (IOException e) {
					s.close();
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					s.close();
					e.printStackTrace();
				}
			}


		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
			System.exit(-1);
		}
	}

}


