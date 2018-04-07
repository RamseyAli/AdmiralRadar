package general; // Something Pano needs to run, (maybe) comment out if your're not Pano

import java.io.*;
import java.net.*;

import game.*;

public class AdmRadarClient {
	// Variables //
	int				portNum	= 12019;
	private Socket	primeSocket;

	private PrintWriter			outStream;
	private BufferedReader		inStream;
	private ObjectOutputStream	objOutput;
	private ObjectInputStream	objInput;

	private Spaceship teamShip; // team's own spaceship, not opponents'

	// Member Functions //
	private void connect_Server(String hostName, int portNumber, String username, String password) throws IOException {
		primeSocket = new Socket( hostName , portNumber );

		outStream = new PrintWriter( primeSocket.getOutputStream() , true );
		inStream = new BufferedReader( new InputStreamReader( primeSocket.getInputStream() ) );
		objOutput = new ObjectOutputStream( primeSocket.getOutputStream() );
		objInput = new ObjectInputStream( primeSocket.getInputStream() );
	}

	// Returns TRUE if successfully connected to the Server //
	public boolean login(String ipAddress, String username, String password) throws IOException {
		try {
			connect_Server( ipAddress , portNum , username , password );
		}
		catch (Exception ex) {
			// Connection failed, close everything //
			if (outStream != null) outStream.close();
			if (inStream != null) inStream.close();
			if (objOutput != null) objOutput.close();
			if (objInput != null) objInput.close();

			return false;
		}

		return true;
	} // TODO: Database stuff

	public void sendCommands(String commandText) {
		outStream.println( commandText );
	}

	public void sendMessages(String msg) {
		outStream.println( msg );
	}

	public void getMessages() throws IOException {
		inStream.readLine();
	}

	public Spaceship getShipObject() {
		return teamShip;
	}

	static int callClientTypeGUI() {
		return 0;
	}

	public static void new_main(String[] args) throws IOException {
		if (args.length != 2) {
			System.err.println( "Usage: java AdmRadarClient <host name> <port number>" );
			System.exit( 1 );
		}

		AdmRadarClient mainClient = new AdmRadarClient();

		String hostName = args[0];
		String username = args[2];
		String password = args[3];

		try {
			mainClient.login( hostName , username , password );

			BufferedReader stdIn = new BufferedReader( new InputStreamReader( System.in ) );

			String fromUser;

			GameMap map1 = new GameMap();
			map1 = (GameMap) mainClient.objInput.readUnshared();

			map1.printAsteroids();

			System.out.println( "Enter your location x,y : " );
			fromUser = stdIn.readLine();

			String[] coordinates = fromUser.split( "," );
			int a = Integer.parseInt( coordinates[0] );
			int b = Integer.parseInt( coordinates[1] );

			Position p = new Position();
			p.setPosition( a , b );

			mainClient.objOutput.writeObject( p );

			Spaceship teamShip = (Spaceship) mainClient.objInput.readUnshared();

			while (teamShip != null) {
				fromUser = stdIn.readLine();
				if (fromUser != null) {
					System.out.println( "Client: " + fromUser );
					mainClient.outStream.println( fromUser );
				}
				teamShip = (Spaceship) mainClient.objInput.readUnshared();
				if (teamShip != null) {
					Position temp = teamShip.getPosition();
					System.out.println( "Ship at x = " + temp.getX() + " y = " + temp.getY() );
					System.out.println( "Ship path :" + teamShip.getPath() );
				}
			}
		}
		catch (UnknownHostException ex) {
			System.err.println( "Don't know about host " + hostName );
			System.exit( 1 );
		}
		catch (Exception e) {
			System.err.println( "Couldn't get I/O for the connection to " + hostName );
			System.exit( 1 );
		}

	}

	public class commandHandler implements Runnable {
		public void run() {

		}
	}

	// Temporary Test Main //
	public static void main(String[] args) throws IOException {

		if (args.length != 2) {
			System.err.println( "Usage: java AdmRadarClient <host name> <port number>" );
			System.exit( 1 );
		}

		String hostName = args[0];
		int portNumber = Integer.parseInt( args[1] );

		try (Socket arSocket = new Socket( hostName , portNumber );
				PrintWriter out = new PrintWriter( arSocket.getOutputStream() , true );
				BufferedReader in = new BufferedReader( new InputStreamReader( arSocket.getInputStream() ) );
				ObjectOutputStream os = new ObjectOutputStream( arSocket.getOutputStream() );
				ObjectInputStream is = new ObjectInputStream( arSocket.getInputStream() );) {
			BufferedReader stdIn = new BufferedReader( new InputStreamReader( System.in ) );

			String fromUser;
			System.out.println( "Enter role :" );
			fromUser = stdIn.readLine();
			if (fromUser != null) {
				System.out.println( "Player is a " + fromUser );
				out.println( fromUser );
			}

			if (fromUser.equals( "Captain" )) {
				// fromServer = in.readLine();
				// System.out.println(fromServer);

				GameMap map1 = new GameMap();
				map1 = (GameMap) is.readUnshared();

				map1.printAsteroids();

				System.out.println( "Enter your location x,y :" );
				fromUser = stdIn.readLine();

				String[] coordinates = fromUser.split( "," );
				int a = Integer.parseInt( coordinates[0] );
				int b = Integer.parseInt( coordinates[1] );

				Position p = new Position();
				p.setPosition( a , b );

				os.writeObject( p );

				Spaceship teamShip = (Spaceship) is.readUnshared();

				while (teamShip != null) {
					fromUser = stdIn.readLine();
					if (fromUser != null) {
						System.out.println( "Player: " + fromUser );
						out.println( fromUser );
					}
					teamShip = (Spaceship) is.readUnshared();
					if (teamShip != null) {
						Position temp = teamShip.getPosition();
						System.out.println( "Ship at x = " + temp.getX() + " y = " + temp.getY() );
						System.out.println( "Ship path :" + teamShip.getPath() );
					}
				}
			} else if (fromUser.equals( "First Officer" )) {
				GameMap map1 = new GameMap();
				map1 = (GameMap) is.readUnshared();

				map1.printAsteroids();

				System.out.println( "Waiting for Captain" );

				Spaceship teamShip = (Spaceship) is.readUnshared();

				while (teamShip != null) {
					fromUser = stdIn.readLine();
					if (fromUser != null) {
						System.out.println( "Player: " + fromUser );
						out.println( fromUser );
					}
					teamShip = (Spaceship) is.readUnshared();
					if (teamShip != null) {
						Position tempP = teamShip.getPosition();
						System.out.println( "Ship at x = " + tempP.getX() + " y = " + tempP.getY() );
						ShipSystems tempS = teamShip.getShipSystem();
						tempS.printSystems();
					}
					System.out.println( "Waiting for Captain" );
				}
			}
		}
		catch (UnknownHostException e) {
			System.err.println( "Don't know about host " + hostName );
			System.exit( 1 );
		}
		catch (Exception e) {
			System.err.println( "Couldn't get I/O for the connection to " + hostName );
			System.exit( 1 );
		}
	}
}