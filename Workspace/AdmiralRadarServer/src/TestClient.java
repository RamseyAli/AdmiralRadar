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
	static final int PORT = 2069;
	static final String HOST = "localhost";
	
	
	public static void main(String[] args) throws IOException {
		
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
							System.out.println("Enter new password(or same old password)");
							String temp = br.readLine();
							u.setNewPassword(temp);
							System.out.println("Enter new avatar url(or same old url)");
							temp = br.readLine();
							temp = "http://www.withanaccent.com/wp-content/uploads/2012/07/avatar-aang.jpg";
							u.setAvatar(temp);
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
								mpi.getNextString();
								//System.out.println(mpi.getNextString());
							}
							
							map = mpi.getNextMap();
							map.printAsteroids();
							
							role = mpi.getNextRole();
							
							if(role == Role.CAPTAIN)
							{
								System.out.println("You are Captain");
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
							else if(role == Role.FIRST)
							{
								System.out.println("You are First Officer");
							}
							else if(role == Role.ENGINE)
							{
								System.out.println("You are Engineer");
							}
							else
							{
								System.out.println("You are Radio Officer");
							}
							
							ship = mpi.getNextSpaceship();
							
							if(role == Role.RADIO)
							{
								int n = 0;
								while(true)
								{
									String str = mpi.getNextString();
									if(!str.equals("Game ended"))
									{
										//if(n == 4)
											//System.out.println(str);
										n++;
										if(n == 5)
											n = 0;
									}
									else									
									{
										System.out.println(str);
										break;
									}
								}
							}
							else
							{
								while(true)
								{
									strInput = mpi.getNextString();
									if(strInput.equals("Waiting for turn"))
									{
										//System.out.println(strInput);
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
										/*if(role == Role.CAPTAIN)
										{
											strInput = mpi.getNextString();
											System.out.println(strInput);
											strOutput = br.readLine();
											mpo.sendString(strOutput);
											if(!strOutput.equalsIgnoreCase("No"))
											{
												strInput = mpi.getNextString();
												System.out.println(strInput);
											}
											
											System.out.println("Enter next direction");
										}*/
										strOutput = br.readLine();
										mpo.sendString(strOutput);
										ship = mpi.getNextSpaceship();
										if(ship.getPosition().x == -1 && ship.getPosition().y == -1)
										{
											System.out.println("Game Ended");
											System.out.println("You Lost");
											break;
										}
										else
										{
											ship.printShip();
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
					System.out.println("Invalid Username");
				}
				else
				{
					System.out.println("Invalid Passsword");
				}
			}
			
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + HOST);
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			System.err.println("Couldn't get I/O for the connection to " + HOST);
			e.printStackTrace(System.err);
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