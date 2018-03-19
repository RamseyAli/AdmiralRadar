

import java.io.*;
import java.net.*;

import game.Maps;
import game.Position;
import game.ShipSystems;
import game.Spaceship;

public class AdmRadarClient {
	static final int PORT = 12019;
	// Variables //
	private Socket primeSocket;

	private PrintWriter outStream;
	private BufferedReader inStream;
	private ObjectOutputStream objOutput;
	private ObjectInputStream objInput;
	
	private Spaceship teamShip; // team's own spaceship, not opponents'
	
	// Member Functions //
	private void connect_Server(String hostName, int portNumber, String username, String password) throws Exception {
		primeSocket = new Socket(hostName, portNumber);
		
		outStream = new PrintWriter(primeSocket.getOutputStream(), true);
		inStream = new BufferedReader(new InputStreamReader(primeSocket.getInputStream()));
		objOutput = new ObjectOutputStream(primeSocket.getOutputStream());
		objInput = new ObjectInputStream(primeSocket.getInputStream());
	}
	
	public void login(String ipAddress, String username, String password) {
		int portNum = PORT;
		try {
			connect_Server(ipAddress, portNum, username, password);
		}
		catch (Exception ex)
		{
			// Connection failed, do something
		}
	} // TODO: Database stuff

	public void sendCommands(String commandText) {
		outStream.println(commandText);
	}
	
	public void sendMessages(String msg) {
		outStream.println(msg);
	}
	public void getMessages() throws IOException {
		inStream.readLine();
	}
	public Spaceship getShipObject(){
		return teamShip;
	}
	static int callClientTypeGUI(){
		return 0;
	}

	public static void new_main(String[] args) throws IOException {
		if (args.length != 2) {
			System.err.println(
				"Usage: java AdmRadarClient <host name> <port number>");
			System.exit(1);
		}
		
		String hostName = args[0];
		int portNumber = Integer.parseInt(args[1]);
	}
	
	// Temporary Test Main //
	public static void main(String[] args) throws IOException {

		if (args.length != 2) {
			System.err.println(
					"Usage: java AdmRadarClient <host name> <port number>");
			System.exit(1);
		}

		String hostName = args[0];
		int portNumber = Integer.parseInt(args[1]);

		try (
				Socket arSocket = new Socket(hostName, portNumber);
				ObjectOutputStream os = new ObjectOutputStream(arSocket.getOutputStream());
				ObjectInputStream is = new ObjectInputStream(arSocket.getInputStream());
		) {
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

			String fromUser;
			System.out.println("Enter role :");
			fromUser = stdIn.readLine();
			if (fromUser != null)
			{
				System.out.println("Player is a " + fromUser);
			}
			
			String fromServer;
			
			if(fromUser.equals("Captain"))
			{
				Maps map1 = new Maps();
				map1 = (Maps) is.readUnshared();

				map1.printAsteroids();
				
				os.writeUnshared(fromUser);
				os.reset();
				
				System.out.println("Enter your location x,y :");
				fromUser = stdIn.readLine();

				String[] coordinates = fromUser.split(",");
				int a = Integer.parseInt(coordinates[0]);
				int b = Integer.parseInt(coordinates[1]);

				Position p = new Position();
				p.setPosition(a,b);

				os.writeUnshared(p);
				os.reset();

				Spaceship teamShip = (Spaceship) is.readUnshared();

				while (teamShip != null)
				{
					fromUser = stdIn.readLine();
					if (fromUser != null)
					{
						System.out.println("Player: " + fromUser);
						os.writeUnshared(fromUser);
						os.reset();
					}
					teamShip = (Spaceship) is.readUnshared();
					if(teamShip != null)
					{
						Position temp = teamShip.getPosition();
						System.out.println("Ship at x = "+temp.x+" y = "+temp.y);
						System.out.println("Ship path :"+teamShip.getPath());
					}
				}
			}
			else if(fromUser.equals("First Officer"))
			{
				Maps map1 = new Maps();
				map1 = (Maps) is.readUnshared();

				map1.printAsteroids();

				System.out.println("Waiting for Captain");

				Spaceship teamShip = (Spaceship) is.readUnshared();

				while (teamShip != null)
				{
					fromUser = stdIn.readLine();
					if (fromUser != null)
					{
						System.out.println("Player: " + fromUser);
						os.writeUnshared(fromUser);
						os.reset();
					}
					teamShip = (Spaceship) is.readUnshared();
					if(teamShip != null)
					{
						Position tempP = teamShip.getPosition();
						System.out.println("Ship at x = "+tempP.x+" y = "+tempP.y);
						ShipSystems tempS = teamShip.getShipSystem();
						tempS.printSystems();
					}
					System.out.println("Waiting for Captain");
				}
			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (Exception e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
	}

	/*
*	IRC Chat Test
*	Base code is from http://archive.oreilly.com/pub/h/1966
*	TO DO: Catch exceptions internally instead of throwing them
*	NOTE: This function will only read an IRC chat ONCE - it must be looped for proper functionality.
 */
	public static void readIRC() throws Exception {
		// The server to connect to and our details.
		String server = "irc.freenode.net";
		String nick = "simple_bot";
		String login = "simple_bot";

		// The channel which the bot will join.
		String channel = "#irchacks";

		// Connect directly to the IRC server.
		Socket socket = new Socket(server, 6667);
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(socket.getOutputStream( )));
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(socket.getInputStream( )));

		// Log on to the server.
		writer.write("NICK " + nick + "\r\n");
		writer.write("USER " + login + " 8 * : Java IRC Hacks Bot\r\n");
		writer.flush( );

		// Read lines from the server until it tells us we have connected.
		String line = null;
		while ((line = reader.readLine( )) != null) {
			if (line.indexOf("004") >= 0) {
				// We are now logged in.
				break;
			}
			else if (line.indexOf("433") >= 0) {
				System.out.println("Nickname is already in use.");
				return;
			}
		}

		// Join the channel.
		writer.write("JOIN " + channel + "\r\n");
		writer.flush( );

		// Keep reading lines from the server.
		while ((line = reader.readLine( )) != null) {
			if (line.toLowerCase( ).startsWith("PING ")) {
				// We must respond to PINGs to avoid being disconnected.
				writer.write("PONG " + line.substring(5) + "\r\n");
				writer.write("PRIVMSG " + channel + " :I got pinged!\r\n");
				writer.flush( );
			}
			else {
				// Print the raw line received by the bot.
				System.out.println(line);
			}
		}
	}
}