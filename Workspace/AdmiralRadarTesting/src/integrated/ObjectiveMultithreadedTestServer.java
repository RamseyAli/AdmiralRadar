package integrated;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import game.GameMap;
import game.Position;
import game.Role;
import game.Spaceship;
import net.MyPacketInputStream;
import net.MyPacketOutputStream;
import ops.User;
import pref.GamePreferences;

public class ObjectiveMultithreadedTestServer {

	class RadarSocket implements Runnable{

		MyPacketInputStream ois;
		MyPacketOutputStream oos;
		int c;
		MyInt key;
		int collector = 0;

		public RadarSocket(Socket s, int i, MyInt o){

			try {
				oos = new MyPacketOutputStream( s.getOutputStream() ,true , "Server" );
				ois = new MyPacketInputStream(  "Server"  , s.getInputStream() );
			}
			catch (IOException e) {
				e.printStackTrace();
			}

			key = o;
			c = i;
		}

		public void run() {

			User u;


			//Logs In
			try {
				u = (User) ois.getNextUser();
				u.loginSuccessful( 0 );
				u.setAvatar( "http://www.withanaccent.com/wp-content/uploads/2012/07/avatar-aang.jpg" );
				oos.sendUser( u );

				ois.getNextString();

			}
			catch (IOException e1) {
				e1.printStackTrace();
			}


			//Holds the thread until the game starts.
			try {
				synchronized (key) {
					if (ear.size() != 8) key.wait();
					else key.notifyAll();
				}

			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}


			//Distribute Roles and Map
			try {
				oos.sendMap( map );
				switch(c % 4){
					case 0: oos.sendRole( Role.CAPTAIN ); break;
					case 1: oos.sendRole( Role.FIRST );  break;
					case 2: oos.sendRole( Role.ENGINE ); break;
					case 3: oos.sendRole( Role.RADIO ); break;
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}

			//If the thread connects to a Captain, get their position at the start of the game. 
			if ((c % 4) == 0){
				try {
					Position p = ois.getNextPosition();
					ship[c / 4].toGameStartCondition(p);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}

			synchronized(key){
				try{
					if (key.getX() < 3) {
						System.err.println(key.getName() + key.getX() + " Pausing " + c );
						
						key.setX( key.getX() + 1 );
						key.wait();

					} else{
						System.out.println( "Launching " + key.getName() + " : " + c);
						key.notifyAll();
					}
					
			//		Thread.sleep( 1000 );
				}
				catch (Exception e){
					e.printStackTrace();
				}
				
			}

			
			System.out.println( "Memories: " + c );

			//Wait Until the Captains have set their start positions


			//			while(game){
			//
			//				//Everybody gets a spaceship, then waits for the captain
			//				try {
			//					System.out.println( "Sending Ship To: " + c );
			//					oos.sendSpaceShip( ship[c / 4] );
			//					synchronized (key) {
			//						switch(c % 4){
			//							case 0:  break;
			//							case 1:  key.wait(); break;
			//							case 2:  key.wait(); break;
			//							case 3:  key.wait(); break;
			//						}
			//					}
			//				}
			//				catch (IOException | InterruptedException e) {
			//					e.printStackTrace();
			//				}
			//
			//
			//				if ((c % 4) == 0){
			//					//Does the Captain use a system?
			//					switch(ois.getClassOfNext()){
			//						case STRING:
			//							//Captain has used a system
			//							//TODO: SYSTEM LOGIC
			//							//DO NOT BREAK OUT OF THIS CASE: After sending String, Client sends Direction
			//						case DIRECTION:
			//							//Captain is sending a direction
			//							//TODO: DIRECTION LOGIC
			//							break;
			//						default: break;
			//
			//					}
			//					System.out.println( "CON:" + ois.getClassOfNext() );
			//
			//				}
			//				synchronized(key){
			//					key.notifyAll();
			//				}
			//
			//				//Send ship w/ direction to all members of friendly team
			//				try {
			//					oos.sendSpaceShip( ship[c / 4] );
			//					//				if ((c / 4))
			//				}
			//				catch (IOException e) {
			//					// TODO Auto-generated catch block
			//					e.printStackTrace();
			//				}

			//Send ONLY direction to enemy RO




			//				//What system does the FO charge and parts does the EO disable?
			//				Spaceship sf = null , se = null;
			//				try {
			//					oos.sendMap( map );
			//					switch(c % 4){
			//						case 0: key.wait(); break;
			//						case 1:  sf = ois.getNextSpaceship(); break;
			//						case 2:  se = ois.getNextSpaceship(); break;
			//						case 3:  key.wait(); break;
			//					}
			//					if ((sf == null)||(se == null)) key.wait();
			//					else {
			//						completeShipActions(ship[c / 4] , sf, se);
			//						key.notifyAll();
			//					}
			//
			//
			//					oos.sendSpaceShip( ship[c / 4] ); //Send ships to everybody!!
			//				}
			//				catch (IOException | InterruptedException e) {
			//					e.printStackTrace();
			//				}


			//Pause for a few seconds for chat / RO actions / slow the game down a bit
			try {
				Thread.sleep( 1000 );
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}



		}//NEXT TURN






		private void completeShipActions(Spaceship main , Spaceship first, Spaceship engine) {
			// TODO Merge actions of First and Engineering Officers. Should also analyze EO's circuits. Apply these ALL to the "main" Spaceship object.

		}
	}
	
	class MyInt  {
		private int x;
		private String name;
		
		public MyInt(int i, String n){
			setX( i );
			name = n;
		}
		
		public int getX() {
			return x;
		}
		
		public void setX(int x) {
			this.x = x;
		}
		
		public String getName(){
			return name;
		}
		
		
	}

	private MyInt team1 , team2;
	private ArrayList<RadarSocket> ear = new ArrayList<RadarSocket>();

	private GameMap map;
	private Spaceship[] ship = new Spaceship[2];
	private boolean game = true; //game is running

	public ObjectiveMultithreadedTestServer(){
		map = new GameMap();
		team1 = new MyInt(0, "Team Key 1 ");
		team2 = new MyInt(0, "Team Key 2 ");
		ship[0] = new Spaceship();
		ship[1] = new Spaceship();

		begin();
	}


	@SuppressWarnings("resource")
	public void begin(){

		RadarSocket rsa;
		int i = 0;

		try {
			ServerSocket ss = new ServerSocket( GamePreferences.getPort() );
			while (ear.size() <= 8){

				rsa = new RadarSocket(ss.accept(), i++ , ear.size() < 4 ? team1 : team2);
				ear.add( rsa );
				new Thread( rsa ).start();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
