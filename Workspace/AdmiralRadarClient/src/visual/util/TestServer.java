package visual.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import net.MyPacket;
import net.MyPacketInputStream;
import net.MyPacketOutputStream;
import ops.User;

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
						MyPacketOutputStream out = new MyPacketOutputStream(s.getOutputStream());
						MyPacketInputStream in = new MyPacketInputStream(s.getInputStream());
						) {
					Object inputLine;
					
					while (true){
						if((inputLine = in.getNextUser()) != null) {
							@SuppressWarnings("unchecked")
							
								User u = (User)inputLine;
							
								if(u.getResult() == 0)
								{
									u.setWins(new Random().nextInt() % 20);
									u.setLoss(new Random().nextInt() % 20);
									u.setAvatar("http://www.withanaccent.com/wp-content/uploads/2012/07/avatar-aang.jpg");
								}
							out.sendUser(u);
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


