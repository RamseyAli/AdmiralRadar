import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import game.GameMap;
import game.Position;
import game.Role;
import game.Spaceship;
import net.MyPacketInputStream;
import net.MyPacketOutputStream;
import ops.User;

public class TestClient {
	
	static GameMap map;
	static Spaceship ship;
	static Role role;
	static final int PORT = 12019;
	static final String HOST = "localhost";
	
	// Member Functions //
	/*private void connect_Server(String hostName, int portNumber, String username, String password) throws Exception {
		//primeSocket = new Socket(hostName, portNumber);
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
	} // TODO: Database stuff*/
	
	public static void main(String[] args) throws IOException {

		/*if (args.length != 2) {
			System.err.println(
					"Usage: java AdmRadarClient <host name> <port number>");
			System.exit(1);
		}*/

		try (
				Socket arSocket = new Socket(HOST,PORT);
				MyPacketOutputStream mpo = new MyPacketOutputStream(arSocket.getOutputStream());
				MyPacketInputStream mpi = new MyPacketInputStream(arSocket.getInputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		) {
			while(true)
			{
				String username = br.readLine();
				String password = br.readLine();
				User u = new User(username,password);
				mpo.sendUser(u);
	
				u = mpi.getNextUser();
				System.out.println("Details:");
				System.out.println(u.getUsername());
				System.out.println(u.getEncryptedPassword());
				System.out.println(u.getWins());
				System.out.println(u.getLosses());
				System.out.println(u.getAvatar());

				int success = u.getResult();
				if(success == 0)
				{
					System.out.println("Logged in");
					while(true)
					{
						System.out.println("What do you want to do?");
						System.out.println("1: Set details");
						System.out.println("2: Ready for game");
						System.out.println("3: Exit");
						int action = Integer.parseInt(br.readLine());
						if(action == 1)
						{
							System.out.println("You selected to set details");
							u.setNewPassword("PASSWORD");
							u.setAvatar("Avatar Kora");
							mpo.sendUser(u);
							u = mpi.getNextUser();
							System.out.println("Details:");
							System.out.println(u.getUsername());
							System.out.println(u.getEncryptedPassword());
							System.out.println(u.getWins());
							System.out.println(u.getLosses());
							System.out.println(u.getAvatar());
						}
						else if(action == 2)
						{
							mpo.sendString("READY");
														
							String strInput,strOutput;
							
							while(mpi.getClassOfNext().equals(String.class))
							{
								System.out.println(mpi.getNextString());
							}
							
							map = mpi.getNextMap();
							map.printAsteroids();
							
							role = mpi.getNextRole();
							
							if(role == Role.CAPTAIN)
							{
								int x,y;
								System.out.println(mpi.getNextString());
								System.out.println("Enter x");
								x = Integer.parseInt(br.readLine());
								System.out.println("Enter y");
								y = Integer.parseInt(br.readLine());
								Position pos = new Position();
								pos.setPosition(x, y);
								mpo.sendPosition(pos);
							}
							
							ship = mpi.getNextSpaceship();
							
							if(role == Role.RADIO)
							{
								while(true)
								{
									System.out.println("Not implemented yet");
									break;
								}
							}
							else
							{
								while(true)
								{
									strInput = mpi.getNextString();
									if(strInput.equals("Waiting for turn"))
									{
										System.out.println(strInput);
									}
									else if(strInput.equals("Game Ended"))
									{
										System.out.println(strInput);
										System.out.println("Your Team Won");
										break;
									}
									else
									{
										System.out.println(strInput);
										strOutput = br.readLine();
										mpo.sendString(strOutput);
										ship = mpi.getNextSpaceship();
										if(ship == null)
										{
											System.out.println("Game Ended");
											System.out.println("You Lost");
											break;
										}
									}
								}
							}
						}
						else
						{
							break;
						}
					}
				}
				else if(success == 1)
				{
					System.out.println("Invallid Username");
				}
				else
				{
					System.out.println("Invalid Passsword");
				}
			}
			/*BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

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
				GameMap map1 = new GameMap();
				map1 = (GameMap) is.readUnshared();

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
				GameMap map1 = new GameMap();
				map1 = (GameMap) is.readUnshared();

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
			}*/
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + HOST);
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			System.err.println("Couldn't get I/O for the connection to " + HOST);
			e.printStackTrace();
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