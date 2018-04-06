package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

import game.Position;
import game.Role;
import net.MyPacketInputStream;
import net.MyPacketOutputStream;
import net.ObjEnum;
import ops.User;
import pref.GamePreferences;
import visual.util.operations.GUIController;

public class ConnectionManager {


	Socket s;
	PrintWriter out;
	BufferedReader in;
	MyPacketOutputStream oos;
	MyPacketInputStream ois;

	GUIController interrupt;


	public ConnectionManager(GUIController nexus){
		interrupt = nexus;

	}

	public int connectToServer(InetAddress svr){

		try {
			s = new Socket(svr, GamePreferences.getPort());

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

		User u = null;
		try {
			u = new User(user, hash);
			oos.sendUser(u);
			System.out.println("Client has sent u");
			u = ois.getNextUser();
			System.out.println("Client has received a U");

			interrupt.setUser(u);

			switch(u.getResult()){
			case -1: return 2;
			case 0: return 5;
			case 1: return 4;
			case 2: return 4;
			}



		} catch (IOException e) {
			return 2;
		}

		return 2;


	}
	
	public int registerUserWithServer(String uRL, String user, String password) {
		// TODO Auto-generated method stub
		return 0;
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

		interrupt.getUser().setAvatar(s);

		try {
			oos.sendUser(interrupt.getUser());
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

		boolean stop = false;
		while(!stop){
			if (ois.getClassOfNext() == ObjEnum.MAP){
				
				try {
					interrupt.setMap(ois.getNextMap());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			else if (ois.getClassOfNext() == ObjEnum.ROLE){
				stop = true;
				try {
					Role r = null;
					if ((r = ois.getNextRole()) != null){
						interrupt.setRole(r);
						System.out.println( r+ " Window is NOW:"  + interrupt.getFactory().getShipPanel().getClass().getName());
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
					e.printStackTrace();
				}
			}
			else if (ois.getClassOfNext() == ObjEnum.STRING){
				try {
					System.out.println("STRING RECEIVED! " + ois.getNextString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if (ois.getClassOfNext() == ObjEnum.SPACESHIP){
				try {
					
					interrupt.setSpaceship(ois.getNextSpaceship());
					interrupt.globalRepaint();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void radioOfficerNetworkLoop() throws IOException  {
		// Listens for RO commands

	}

	private void firstOfficerNetworkLoop() throws IOException  {
		// Listen for FO Info

	}

	private void engineerNetworkLoop() throws IOException  {
		// Listen for ENGR info

	}

	private void captainNetworkLoop() throws IOException {
		
		Position start = interrupt.getFactory().getInitialPositionFromCaptain();
		oos.sendPosition(start);

	}



}
