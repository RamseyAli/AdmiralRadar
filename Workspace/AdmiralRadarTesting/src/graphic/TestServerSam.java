package graphic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import game.Role;
import net.MyPacket;
import net.MyPacketInputStream;
import net.MyPacketOutputStream;
import ops.User;

public class TestServerSam implements Runnable{

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
					User u;

					while (true){

						if((u = in.getNextUser()) != null) {

							if(u.getUsername().equals("Username"))
							{
								if(u.getEncryptedPassword().equals("Password"))
									u.loginSuccessful(0);
								else
									u.loginSuccessful(2);
							}
							else
							{
								u.loginSuccessful(1);
							}


							u.setWins(new Random().nextInt() % 20);
							u.setLoss(new Random().nextInt() % 20);
							u.setAvatar("http://www.withanaccent.com/wp-content/uploads/2012/07/avatar-aang.jpg");

							out.sendUser(u);
							out.flush();

							if((u = in.getNextUser()) != null) {

								if(u.getResult() == 0)
								{
									u.setWins(new Random().nextInt() % 20);
									u.setLoss(new Random().nextInt() % 20);
									u.setAvatar("http://www.withanaccent.com/wp-content/uploads/2012/07/avatar-aang.jpg");
								}
								out.sendUser(u);

							}

							String st;
							if((st = in.getNextString()) != null) {
								if (st.equals("READY")){
									out.sendRole(Role.ENGINE);
								}
							}

						}

					} } catch (IOException e) {
						try {
							s.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						e.printStackTrace();
					}
			}

		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	}

}
