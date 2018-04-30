package graphic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import game.Direction;
import game.GameMap;
import game.Position;
import game.Role;
import game.Spaceship;
import game.Systems;
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

		sp.setHealth(2);

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
									out.sendUser( new User("fake_u", "fake_p") );
									out.sendRole( ro );
								}


								if (ro == Role.CAPTAIN) {
									sp.getShipSystem().chargeSystem(Systems.BOOST );
									sp.getShipSystem().chargeSystem(Systems.BOOST );
									sp.getShipSystem().chargeSystem(Systems.BOOST );
									sp.getShipSystem().chargeSystem(Systems.BOOST );
									sp.getShipSystem().chargeSystem(Systems.BOOST );
									sp.getShipSystem().chargeSystem(Systems.MISSILE );
									sp.getShipSystem().chargeSystem(Systems.MISSILE );
									sp.getShipSystem().chargeSystem(Systems.MISSILE );
									sp.getShipSystem().chargeSystem(Systems.MINE );
									sp.getShipSystem().chargeSystem(Systems.MINE );
									sp.getShipSystem().chargeSystem(Systems.MINE );
									sp.getShipSystem().chargeSystem(Systems.DRONE );
									//	sp.getShipSystem().chargeSystem(Systems.DRONE );
									sp.getShipSystem().chargeSystem(Systems.RADAR );
									sp.getShipSystem().chargeSystem(Systems.RADAR );
									sp.getShipSystem().chargeSystem(Systems.RADAR );
									sp.getShipSystem().disableSystemComponent( 2 );

									sp.getShipMines().addMine( new Position(5,5) );
									sp.getShipMines().addMine( new Position(13,2) );
									//System.out.println( "I GOT HERE!!!!!!!" );

								}


								if (ro == Role.ENGINE) {
									//			sp.getShipSystem().disableSystemComponent( 12 );
									//			sp.getShipSystem().disableSystemComponent( 13 );
									//			sp.getShipSystem().disableSystemComponent( 14 );

									//			sp.getShipSystem().disableSystemComponent( 0 );
									//			sp.getShipSystem().disableSystemComponent( 1 );
									//			sp.getShipSystem().disableSystemComponent( 2 );

									//			sp.getShipSystem().disableSystemComponent( 6 );
									//			sp.getShipSystem().disableSystemComponent( 7 );
									//			sp.getShipSystem().disableSystemComponent( 8 );


								}

								out.reset();
								out.sendSpaceShip( sp );


								if (ro == Role.CAPTAIN) {
									out.sendString( "Your turn" );
								}

								if (ro == Role.ENGINE) {
									

										out.sendDirection( Direction.NORTH );
										String str2 = in.getNextString();
										sp.getShipSystem().disableSystemComponent( Integer.parseInt( str2 )  );
										out.reset();
										out.sendSpaceShip( sp );
										
										out.sendDirection( Direction.NORTH );
										String str3 = in.getNextString();
										sp.getShipSystem().disableSystemComponent( Integer.parseInt( str3 )  );
										out.reset();
										out.sendSpaceShip( sp );
										
										out.sendDirection( Direction.NORTH );
										String str4 = in.getNextString();
										sp.getShipSystem().disableSystemComponent( Integer.parseInt( str4 )  );
										out.reset();
										out.sendSpaceShip( sp );
										
										out.sendDirection( Direction.EAST );
										String str5 = in.getNextString();
										sp.getShipSystem().disableSystemComponent( Integer.parseInt( str5 )  );
										out.reset();
										out.sendSpaceShip( sp );
									
								}

								if (ro == Role.RADIO){
									ArrayList<Direction> dir = new ArrayList<Direction>();
									dir.add( Direction.NORTH );
									out.sendPath( dir );
									Thread.sleep( 2000 );
									dir.add( Direction.WEST );
									out.sendPath( dir );
									Thread.sleep( 2000 );

									while(xa){
										dir.add( Direction.NORTH );
										dir = new ArrayList<Direction>(dir);
										out.sendPath( dir );
										System.out.println( "I just sent " + dir );
										out.flush();
										Thread.sleep( 2000 );

										dir.add( Direction.WEST );
										dir = new ArrayList<Direction>(dir);
										out.sendPath( dir );
										System.out.println( "I just sent " + dir );
										out.flush();
										Thread.sleep( 2000 );


									}
								}
								
								if (ro == Role.FIRST){
									
									out.sendDirection( Direction.NORTH );
									Systems x1 = in.getNextCommand();
									sp.getShipSystem().chargeSystem( x1  ); 
									out.reset();
									out.sendSpaceShip( sp );
									
									out.sendDirection( Direction.NORTH );
									Systems x2 = in.getNextCommand();
									sp.getShipSystem().chargeSystem( x2 ); 
									out.reset();
									out.sendSpaceShip( sp );
									
									out.sendDirection( Direction.NORTH );
									Systems x3 = in.getNextCommand();
									sp.getShipSystem().chargeSystem( x3 ); 
									out.reset();
									out.sendSpaceShip( sp );
									
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
