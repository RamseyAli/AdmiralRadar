package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

import audio.SoundManager;
import game.Direction;
import game.Position;
import game.Role;
import game.Spaceship;
import game.Systems;
import security.DesEncrypter;
import net.MyPacketInputStream;
import net.MyPacketOutputStream;
import net.ObjEnum;
import ops.User;
import pref.GamePreferences;
import visual.util.operations.GUIController;

public class ConnectionManager {

	Socket					s;
	PrintWriter				out;
	BufferedReader			in;
	MyPacketOutputStream	oos;
	MyPacketInputStream		ois;
	String					name;

	GUIController interrupt;

	public ConnectionManager(GUIController nexus) {
		interrupt = nexus;
		name = "Default Name";

	}

	public int connectToServer(InetAddress svr) {

		try {
			s = new Socket( svr , GamePreferences.getPort() );

			out = new PrintWriter( s.getOutputStream() , true );
			oos = new MyPacketOutputStream( s.getOutputStream() , true , name);
			in = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
			ois = new MyPacketInputStream(name, s.getInputStream() );

		}
		catch (IOException e) {
			System.out.println( e );
			return 2;
		}

		return 3;

	}

	public int loginToServer(String user, String hash) {

		hash = encrypt(hash);
		
		User u = null;
		try {
			u = new User( user , hash );
			oos.sendUser( u );
			System.out.println( "Client has sent u" );
			u = ois.getNextUser();
			System.out.println( "Client has received a U" );

			interrupt.setUser( u );

			switch (u.getResult()) {
				case -1:
					return 2;
				case 0:
					return 5;
				case 1:
					return 4;
				case 2:
					return 4;
			}

		}
		catch (IOException e) {
			return 2;
		}

		return 2;

	}
	
	public int registerUserWithServer(String uRL, String user, String hash) {
		
		hash = encrypt(hash);

		User u = null;
		
		try {
			u = new User(user, hash);
			u.setAvatar(uRL);
			oos.sendUser(u);
			System.out.println("Client has sent u");
			u = ois.getNextUser();
			System.out.println("Client has received a U");

			interrupt.setUser(u);

			if (u.getPin() == -1) {
				//Username in-use
				return -1;
			} else if (u.getPin() == -2) {
				//Misc. error
				return -2;
			} else {
				//auto-gen PIN
				return u.getPin();
			}



		} catch (IOException e) {
			return 2;
		}
		
	}

	public void disconnect() {
		JOptionPane.showMessageDialog( interrupt.getGUIFrame() , "Connection Lost. You Loose. Bye!" );
		try {
			Thread.sleep( 5000 );
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit( 0 );
	}

	public void newAvatar(String s) {

		interrupt.getUser().setAvatar( s );

		try {
			oos.sendUser( interrupt.getUser() );
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/*
	 * 0 - Success
	 * -1 - ERROR: Invalid PIN
	 * -2 - ERROR: Misc.
	 */
	public int newPassword(int PIN, String user, String password) {

		String hash = encrypt(password);
		
		int storedPIN = interrupt.getUser().getPin();
		
		if (storedPIN != PIN) {
			return -1;
		}
		
		interrupt.getUser().setNewPassword(hash);

		try {
			oos.sendUser( interrupt.getUser() );
		}
		catch (IOException e) {
			e.printStackTrace();
			return -2;
		}
		
		return 0;

	}

	public int ready() {
		try {
			oos.sendString( "READY" );
			new Thread( () -> waitForStart() ).start();
			return 6;
		}
		catch (IOException e) {
			return 5;
		}

	}

	private void waitForStart() {

		boolean stop = false;
		while (!stop) {
			if (ois.getClassOfNext() == ObjEnum.MAP) {

				try {
					interrupt.setMap( ois.getNextMap() );
				}
				catch (IOException e) {
					e.printStackTrace();
				}

			} else if (ois.getClassOfNext() == ObjEnum.ROLE) {
				stop = true;
				try {
					Role r = null;
					if (( r = ois.getNextRole() ) != null) {
						interrupt.setRole( r );
						switch (r) {
							case CAPTAIN:
								captainNetworkLoop();
								break;
							case ENGINE:
								engineerNetworkLoop();
								break;
							case FIRST:
								firstOfficerNetworkLoop();
								break;
							case NETWORK:
								break;
							case RADIO:
								radioOfficerNetworkLoop();
								break;
							default:
								break;

						}
					}

				}
				catch (IOException e) {
					e.printStackTrace();
				}
			} else if (ois.getClassOfNext() == ObjEnum.STRING) {
				try {
					System.out.println( "STRING RECEIVED! " + ois.getNextString() );
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			} else if (ois.getClassOfNext() == ObjEnum.SPACESHIP) {
				try {

					interrupt.setSpaceship( ois.getNextSpaceship() );
					interrupt.globalRefresh();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void radioOfficerNetworkLoop() throws IOException {
		// Listens for RO commands

	}

	private void firstOfficerNetworkLoop() throws IOException {
		interrupt.setSpaceship( ois.getNextSpaceship() );
		interrupt.getSpaceship().setDirection( ois.getNextDirection() );
		System.out.println( " Gumdrop " );
		interrupt.refreshFrame();
	//	interrupt.globalRefresh();

	}

	private void engineerNetworkLoop() throws IOException {
		interrupt.setSpaceship( ois.getNextSpaceship() );
		interrupt.getSpaceship().setDirection( ois.getNextDirection() );
		interrupt.refreshFrame();

	}

	private void captainNetworkLoop() throws IOException {

		Position start = interrupt.getFactory().getInitialPositionFromCaptain();
		oos.sendPosition( start );
		System.out.println( ois.getClassOfNext() );
		System.out.println( ois.getNextString() );
		System.out.println( ois.getClassOfNext() );
		interrupt.setSpaceship( ois.getNextSpaceship() );

	}

	public void setName(String n2) {
		name = n2;
		
	}

	public void sendDirectionCommand(Direction d) {
		try {
			oos.sendString( "No" );
			oos.sendDirection( d );
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void sendShip(Spaceship s) {
		try {
			oos.sendSpaceShip( s );
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void sendChargeCommand(Systems name2) {
		try {
			oos.sendSystem( name2 );
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String encrypt(String password) {
		String hash = "";
		try {
			byte[] decodedKey = Base64.getDecoder().decode( "p5vVBP2rSX8=" );
			// using a pre-set key, so we're not generating new keys with every server run.
			SecretKey key = new SecretKeySpec( decodedKey , 0 , decodedKey.length , "DES" );
			DesEncrypter encrypter = new DesEncrypter( key );
			hash = encrypter.encrypt( password );
		}
		catch (Exception ex) {
			// do nothing (pw not encrypted)
		}
		return hash;
	}

}
