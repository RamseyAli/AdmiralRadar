package graphic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import game.GameMap;
import game.Role;
import net.MyPacketInputStream;
import net.MyPacketOutputStream;
import ops.User;

public class TestServerSam implements Runnable {

	Role ro;

	public TestServerSam(Role r) {
		ro = r;
	}

	@Override
	public void run() {

		int portNumber = 2069;
		boolean listening = true;

		try (ServerSocket serverSocket = new ServerSocket( portNumber )) {
			while (listening) {
				System.out.println( "Loop!" );
				Socket s = serverSocket.accept();
				System.out.println( "New Connection" );

				try (MyPacketOutputStream out = new MyPacketOutputStream( s.getOutputStream() );
						MyPacketInputStream in = new MyPacketInputStream( s.getInputStream() );) {
					User u;

					while (true) {

						switch (in.getClassOfNext()) {
							case MAP:
								break;
							case POSITION:
								break;
							case ROLE:
								break;
							case SPACESHIP:
								break;
							case STRING: {
								String str = in.getNextString();
								System.out.println( "SERVER READS:" + str );

								if (str.equals( "READY" )) {
									out.sendMap( new GameMap() );
									out.sendRole( ro );
								}
								break;
							}
							case USER: {
								u = in.getNextUser();

								u.loginSuccessful( 0 );

								u.setWins( new Random().nextInt() % 20 );
								u.setLoss( new Random().nextInt() % 20 );
								u.setAvatar( "http://www.withanaccent.com/wp-content/uploads/2012/07/avatar-aang.jpg" );

								out.sendUser( u );
								out.flush();

								break;
							}
							default:
								break;

						}

					}

				}

			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
