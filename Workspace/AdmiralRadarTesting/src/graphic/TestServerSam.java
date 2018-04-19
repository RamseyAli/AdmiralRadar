package graphic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import game.Direction;
import game.GameMap;
import game.Role;
import game.Spaceship;
import net.MyPacketInputStream;
import net.MyPacketOutputStream;
import ops.User;
import pref.GamePreferences;

public class TestServerSam implements Runnable {

	Role ro;

	public TestServerSam(Role r) {
		ro = r;
	}

	@Override
	public void run() {

		int portNumber = GamePreferences.getPort();
		boolean listening = true;
		
		boolean xa = true;
		Spaceship sp = new Spaceship();

		try (ServerSocket serverSocket = new ServerSocket( portNumber )) {
			while (listening) {
				System.out.println( "Loop!" + serverSocket.getInetAddress() );

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
								
								out.sendSpaceShip( sp );
								
								ArrayList<Direction> dir = new ArrayList<Direction>();
								dir.add( Direction.NORTH );
								out.sendPath( dir );
								Thread.sleep( 2000 );
								dir.add( Direction.WEST );
								out.sendPath( dir );
								Thread.sleep( 2000 );
								
								while(xa){
									dir.add( Direction.NORTH );
									dir.add( Direction.EAST );
									dir = new ArrayList<Direction>(dir);
									out.sendPath( dir );
									System.out.println( "I just sent " + dir );
									out.flush();
									Thread.sleep( 2000 );
									
									
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
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
