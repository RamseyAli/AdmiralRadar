
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import game.GameMap;
import game.Position;
import game.Role;
import game.Spaceship;
import net.MyPacketInputStream;
import net.MyPacketOutputStream;
import ops.User;

public class AdmRadarServer
{
	ArrayList<MyPacketOutputStream> clientOutputStreams;
	ArrayList<MyPacketInputStream> clientInputStreams;
	ArrayList<Spaceship> gameShip;
	static int nPlayers;
	static boolean gameOngoing;
	static boolean []moveComplete = new boolean[2];
	ServerSocket serverSocket;
	static int turn;
	
	public class ClientHandler implements Runnable 
	{
		Socket sock;
		MyPacketOutputStream mpos;
		MyPacketInputStream mpis;
		int teamNo;
		int turnNo;
		GameMap map;
		Role role;
		Spaceship ship;
		
		public ClientHandler(Socket clientSock)
		{
			try
			{
				teamNo = -1;
				turnNo = -1;
				sock = clientSock;
				mpos = new MyPacketOutputStream(sock.getOutputStream());
				clientOutputStreams.add(mpos);
				mpis = new MyPacketInputStream(sock.getInputStream());
				clientInputStreams.add(mpis);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		public void run()
		{
			try {
				while(true)
				{
					
					Object inputObject;
					if((inputObject = mpis.getNextUser()) != null)
					{
						//@SuppressWarnings("unchecked")
						
						User u = (User)inputObject;
						String username = u.getUsername();
						String encPassword = u.getEncryptedPassword();
						
						int success = login(username,encPassword);
						u.loginSuccessful(success);
						
						if(success == 0)
						{
							u.setWins(getWins(username));
							u.setLoss(getLosses(username));
							u.setAvatar(getURL(username));
							
							mpos.sendUser(u);
							
							while(true)
							{
								Class<?> temp = mpis.getClassOfNext();
								if(temp.equals(User.class))
								{
									inputObject = mpis.getNextUser();
									u = (User)inputObject;
									resetPW(username,u.getEncryptedPassword(),8242);
									setURL(username,u.getAvatar());
									mpos.sendUser(u);
								}
								else if(temp.equals(String.class))
								{
									inputObject = mpis.getNextString();
									//String s = (String)inputObject;
									if(nPlayers == 0)
									{
										System.out.println("GAME LOBBY");
										System.out.println("Error: Not enough players");
										System.out.println("Game Mode: Turn Based");
									}
									
									teamNo = nPlayers/4;
									turnNo = nPlayers;
									System.out.println("team no: "+teamNo+" turn no: "+turnNo);
									nPlayers++;
									
									while(nPlayers < 8)
									{
										mpos.sendString("WAITING");
									}
									
									AdmRadarProtocol arp = new AdmRadarProtocol();
									
									map = new GameMap();
									map = arp.updateMap();				
									mpos.sendMap(map);
									
									if(turnNo == 7)
									{
										System.out.println("GAME BEGINS");
										gameOngoing = true;
									}
									
									if(turnNo  == 0 || turnNo == 4)
									{
										role = Role.CAPTAIN;
										mpos.sendRole(role);
										mpos.sendString("Enter initial location");
										Position pos = mpis.getNextPosition();
										ship = gameShip.get(teamNo);
										ship.setPos(pos);
										gameShip.set(teamNo, ship);
										gameShip.get(teamNo).printShip();
									}
									else if(turnNo == 1 || turnNo == 5)
									{
										role = Role.FIRST;
										mpos.sendRole(role);
									}
									else if(turnNo == 2 || turnNo == 6)
									{
										role = Role.ENGINE;
										mpos.sendRole(role);
									}
									else if(turnNo == 3 || turnNo == 7)
									{
										role = Role.RADIO;
										mpos.sendRole(role);
									}
									
									ship = gameShip.get(teamNo);
									mpos.sendSpaceShip(ship);
									mpos.reset();
									
									while(gameOngoing)
									{
										if(role == Role.RADIO)
										{
											break;
										}
										else
										{
											String str = "Your turn";
										
											if(turn == turnNo)
											{
												System.out.println(turn);
												
												if(turnNo == 0)
												{
													moveComplete[teamNo] = false;
												}
												
												if(turnNo == 4)
												{
													moveComplete[teamNo] = false;
												}
												
												ship = gameShip.get(teamNo);
												
												if(ship != null && (turnNo == 1 || turnNo == 2 || turnNo == 5 || turnNo == 6))
													str = ship.getDirection();
												
												mpos.sendString(str);
												
												String action = mpis.getNextString();
												arp.processCommands(action, ship);
												gameShip.set(teamNo, ship);
												
												if(turnNo == 2 || turnNo == 6)
												{
													if(gameShip.get(teamNo) == null)
													{
														gameOngoing = false;
														System.out.println("GAME ENDED");
													}
													moveComplete[teamNo] = true;
												}
												
												turn++;
												if(turn == 3)
												{
													turn++;
												}
												else if(turn == 7)
												{
													turn = 0;
												}
												
												while(!moveComplete[teamNo])
												{
													//Do nothing
													System.out.print("");
												}
												
												ship = gameShip.get(teamNo);
												ship.printShip();
												
												mpos.sendSpaceShip(ship);
												mpos.reset();
												
											}
											else
											{
												if(!gameOngoing)
													mpos.sendString("Game Ended");
												else
													mpos.sendString("Waiting for turn");
											}
										}
									}
								}
								else
								{
									mpos.sendString("Naughty");
								}
							}
						}
						else
						{
							mpos.sendUser(u);
						}
					}
					
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) throws IOException
	{
        
		/*if (args.length != 1)
		{
			System.err.println("Usage: java AdmRadarServer <port number>");
			System.exit(1);
		}
		
		//Database test

			String username = "TEST_USER";
			String password = "test";
			System.out.println("Logging in with... Username: TEST_USER | Password: TEST_PASSWORD");
			int result = login(username, password);
			if(result == 0) {
				System.out.println("Welcome " + username + "!");
				int wins = wins(username);
				int losses = losses(username);

				if (wins != -1 && losses != -1) {
					System.out.println("Your stats are " + wins + " Win(s) and " + losses + " Loss(es).");
				} else {
					System.out.println("ERROR: Stats not loaded properly");
				}

			} else {
				if (result == 1) {
					System.out.println("ERROR: Login Failed - Invalid username");
				} else {
					System.out.println("ERROR: Login Failed - Invalid password");
				}
			}
			System.out.println("What would you like the new password to be?");
			Scanner reader = new Scanner(System.in);
			String new_pw = reader.nextLine();
			System.out.println("What is your PIN?");
			int pin = reader.nextInt();
			result = reset(username, new_pw, pin);
			if (result == 0) {
				System.out.println("Password changed successfully!");
			} else {
				if (result == 1) {
					System.out.println("ERROR: Reset Failed - Invalid Username");
				} else {
					System.out.println("ERROR: Reset Failed - Invalid PIN");
				}
			}*/
		moveComplete[0] = false;
		moveComplete[1] = false;
		gameOngoing = false;
		turn = 0;
		nPlayers = 0;
		int portNumber = 2069;
		new AdmRadarServer().go(portNumber);
	}
	
	public void go(int port)
	{
		clientOutputStreams = new ArrayList<MyPacketOutputStream>();
		clientInputStreams = new ArrayList<MyPacketInputStream>();
		gameShip = new ArrayList<Spaceship>();
		Spaceship initial = new Spaceship();
		gameShip.add(0,initial);
		gameShip.add(1,initial);
		
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("AdmiralRaderServer running on port: " + port);
			while(true)
			{
				Socket clientSocket = serverSocket.accept();
				System.out.println("Got a client");
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
			}	
		} catch (Exception e) {
			System.out.println("Exception caught when trying to listen on port " + port + " or listening for a connection");
			System.out.println(e.getMessage());
			if (serverSocket != null && !serverSocket.isClosed())
			{
		        try
		        {
		            serverSocket.close();
		        } catch (IOException ex)
		        {
		            ex.printStackTrace(System.err);
		        }
		    }
		}
	}
	
	/*public void stopAllThreads()
	{
		for (Thread t1 : clientThreads)
		{
			if (t1.isAlive())
			{
				try
				{
					t1.interrupt();
				} catch (Exception e) {}
			}
		}
		System.out.println("GAME ENDED");
        }*/
	
	public static class dbQuery {
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;

		public dbQuery(Connection conn, Statement statement, ResultSet rs) {
			this.conn = conn;
			this.rs = rs;
		}

		public boolean close() {
			/*
			try {
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
			*/
			return true;
		}

	}


	public static dbQuery query(String query) {

		Connection conn = null;

		try {

			String url = "jdbc:mysql://radar.c87i64zdxk4i.us-east-2.rds.amazonaws.com:3306/AdmiralRadar?verifyServerCertificate=false&useSSL=true&requireSSL=true";
			Properties info = new Properties();
			info.put("user", "api_user");
			info.put("password", "password1234");

			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection(url, info);

		} catch (Exception ex) {
			System.out.println("An error occurred while connecting MySQL databse");
			ex.printStackTrace();
			return null;
		}

		if (conn != null) {
			//System.out.println("Successfully connected to MySQL database");
		} else {
			System.out.println("Could not connect to MySQL database");
			return null;
		}

		//Connection established, run query...
		Statement statement = null;

		try {

			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			dbQuery obj = new dbQuery(conn, statement, rs);
			return obj;

		} catch (Exception e) {
			System.out.println("Could not execute query on MySQL database");
			e.printStackTrace();
			return null;
		}
	}

	/*
	0 - Success
	1 - Invalid Username
	2 - Invalid Password
	*/
	public static int login(String user, String pw) {

		dbQuery DBobj = query("SELECT USERNAME, PASSWORD FROM USER");

		try {
			byte[] decodedKey = Base64.getDecoder().decode("p5vVBP2rSX8="); //using a pre-set hardcoded key, so we're not generating new keys with every server run.
			SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");
			DesEncrypter encrypter = new DesEncrypter(key);
			pw = encrypter.encrypt(pw);
		} catch (Exception ex) {
			pw = pw; //do nothing (pw not encrypted)
		}

		try {
			while (DBobj.rs.next()) {
				if (user.equals(DBobj.rs.getString("USERNAME"))) {
					if (pw.equals(DBobj.rs.getString("PASSWORD"))) {
						DBobj.close();
						return 0;
					} else {
						//System.out.println("PW: " + pw + " | " + DBobj.rs.getString("PASSWORD"));
						DBobj.close();
						return 2;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("There's an issue retrieving info. from query results");
		}
		DBobj.close();
		return 1;
	}

	/*
	0 - Success
	1 - Invalid Username
	2 - Invalid PIN
	*/
	public static int resetPW(String user, String pw, int pin) {

		dbQuery DBobj = query("SELECT USERNAME, PIN FROM USER");

		try {
			while (DBobj.rs.next()) {
				if (user.equals(DBobj.rs.getString("USERNAME"))) {
					if (pin == DBobj.rs.getInt("PIN")) {

						//encrypt the new pw
						try {
							byte[] decodedKey = Base64.getDecoder().decode("p5vVBP2rSX8="); //using a pre-set hardcoded key, so we're not generating new keys with every server run.
							SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");
							DesEncrypter encrypter = new DesEncrypter(key);
							pw = encrypter.encrypt(pw);
						} catch (Exception ex) {
							pw = pw; //do nothing (pw not encrypted)
						}

						String query = "UPDATE USER SET PASSWORD = ? WHERE USERNAME = ?";
						PreparedStatement preparedStmt = DBobj.conn.prepareStatement(query);
						preparedStmt.setString   (1, pw);
						preparedStmt.setString(2, user);

						System.out.println("Prepared Statement: " + preparedStmt);

						// execute the java preparedstatement
						preparedStmt.executeUpdate();

						return 0;

					} else {
						DBobj.close();
						return 2;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("There's an issue retrieving info. from query results");
		}
		DBobj.close();
		return 1;

	}
	
	/*
	0 - Success
	1 - Misc. Failure
	*/
	public static int setURL(String user, String url) {
		
		dbQuery DBobj = query("SELECT USERNAME, PIN FROM USER");	//just to init obj.
		String query = "UPDATE USER SET AVATAR = ? WHERE USERNAME = ?";
	
		try {
			PreparedStatement preparedStmt = DBobj.conn.prepareStatement(query);
			preparedStmt.setString(1, url);
			preparedStmt.setString(2, user);
			preparedStmt.executeUpdate();
			//System.out.println("Prepared Statement: " + preparedStmt);
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		
		return 0;

	}

	/*
		Credit for encrpytion method goes to Java2S.com
	 */

///

	static class DesEncrypter {
		Cipher ecipher;

		DesEncrypter(SecretKey key) throws Exception {
			ecipher = Cipher.getInstance("DES");
			ecipher.init(Cipher.ENCRYPT_MODE, key);
		}

		//@SuppressWarnings("restriction")
		public String encrypt(String str) throws Exception {
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes("UTF8");
			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);
			// Encode bytes to base64 to get a string
			return new sun.misc.BASE64Encoder().encode(enc);
		}

	}


	///
	/*
	[url returned] - Success
	ERROR - Invalid username / Misc.
	*/
	public static String getURL(String user) {

		dbQuery DBobj = query("SELECT USERNAME, AVATAR FROM USER");

		try {
			while (DBobj.rs.next()) {
				if (user.equals(DBobj.rs.getString("USERNAME"))) {
					return DBobj.rs.getString("AVATAR");
				} else {
					return "ERROR";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("There's an issue retrieving info. from query results");
		}
		DBobj.close();
		return "ERROR";

	}

	/*
	[# Wins] - Success
	-1 - Invalid Username
	*/
	public static int getWins(String user) {

		dbQuery DBobj = query("SELECT USERNAME, WINS FROM USER");

		try {
			while (DBobj.rs.next()) {
				if (user.equals(DBobj.rs.getString("USERNAME"))) {
					DBobj.close();
					return DBobj.rs.getInt("WINS");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("There's an issue retrieving info. from query results");
		}
		DBobj.close();
		return -1;

	}

	/*
	[# Losses] - Success
	-1 - Invalid Username
	*/
	public static int getLosses(String user) {

		dbQuery DBobj = query("SELECT USERNAME, LOSSES FROM USER");

		try {
			while (DBobj.rs.next()) {
				if (user.equals(DBobj.rs.getString("USERNAME"))) {
					DBobj.close();
					return DBobj.rs.getInt("LOSSES");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("There's an issue retrieving info. from query results");
		}
		DBobj.close();
		return -1;

	}
	
	/*
	 * Returns a List of formatted messages
	 */
	public static List<String> getTeamMessages(int teamID) {
		dbQuery DBobj = query("SELECT USERNAME, TIMESTAMP, MESSAGE, TEAM_ID FROM TEAM_CHAT");
		
		List<String> messages = new ArrayList<String>();
		
		try {
			while (DBobj.rs.next()) {
				
				int userTeam = DBobj.rs.getInt("TEAM_ID");
				String username = DBobj.rs.getString("USERNAME");
				String time = DBobj.rs.getString("TIMESTAMP").substring(11, 18);
				String message = DBobj.rs.getString("MESSAGE");
				
				if (teamID == userTeam) {
					messages.add(username + " [" + time + "]: " + message);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("There's an issue retrieving info. from query results");
		}
		DBobj.close();
		return messages;
	}
	
	/*
	 * Returns a List of formatted messages
	 */
	public static List<String> getGlobalMessages() {
		dbQuery DBobj = query("SELECT USERNAME, TIMESTAMP, MESSAGE FROM GLOBAL_CHAT");
		
		List<String> messages = new ArrayList<String>();
		
		try {
			while (DBobj.rs.next()) {
				
				String username = DBobj.rs.getString("USERNAME");
				String time = DBobj.rs.getString("TIMESTAMP").substring(11, 18);
				String message = DBobj.rs.getString("MESSAGE");
				
				messages.add(username + " [" + time + "]: " + message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("There's an issue retrieving info. from query results");
		}
		DBobj.close();
		return messages;
		
	}
	
	/*
	 * Returns a true boolean on successful message entry
	 */
	public static boolean sendTeamMessage(String username, String message, int teamID) {	
		dbQuery DBobj = query("SELECT USERNAME, PIN FROM USER");	//just to init obj.
		String query = "INSERT INTO TEAM_CHAT (USERNAME, MESSAGE, TEAM_ID) VALUES (?,?,?)";
	
		try {
			PreparedStatement preparedStmt = DBobj.conn.prepareStatement(query);
			preparedStmt.setString(1, username);
			preparedStmt.setString(2, message);
			preparedStmt.setInt(3, teamID);
			preparedStmt.executeUpdate();
			//System.out.println("Prepared Statement: " + preparedStmt);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		DBobj.close();
		return true;
	}
	
	/*
	 * Returns a true boolean on successful message entry
	 */
	public static boolean sendGlobalMessage(String username, String message) {
		dbQuery DBobj = query("SELECT USERNAME, PIN FROM USER");	//just to init obj.
		String query = "INSERT INTO GLOBAL_CHAT (USERNAME, MESSAGE) VALUES (?,?)";
	
		try {
			PreparedStatement preparedStmt = DBobj.conn.prepareStatement(query);
			preparedStmt.setString(1, username);
			preparedStmt.setString(2, message);
			preparedStmt.executeUpdate();
			//System.out.println("Prepared Statement: " + preparedStmt);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		DBobj.close();
		return true;
	}
	
	/*
	 * Returns a true boolean on successful chat wipe
	 */
	public static boolean clearTeamMessages() {
		dbQuery DBobj = query("SELECT USERNAME, PIN FROM USER");	//just to init obj.
		String query = "TRUNCATE TEAM_CHAT";
	
		try {
			PreparedStatement preparedStmt = DBobj.conn.prepareStatement(query);
			preparedStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		DBobj.close();
		return true;
	}
	
	/*
	 * Returns a true boolean on successful chat wipe
	 */
	public static boolean clearGlobalMessages() {
		dbQuery DBobj = query("SELECT USERNAME, PIN FROM USER");	//just to init obj.
		String query = "TRUNCATE GLOBAL_CHAT";
	
		try {
			PreparedStatement preparedStmt = DBobj.conn.prepareStatement(query);
			preparedStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		DBobj.close();
		return true;
	}
	
	/*
	 *  >0 - the user's team_id
	 *  0 - this user is not assigned to a team
	 *  -1 - this user could not be found
	 */
	public static int getTeamID(String username) {
		dbQuery DBobj = query("SELECT TEAM_ID, USERNAME FROM USER");
		
		try {
			while (DBobj.rs.next()) {
				
				String user = DBobj.rs.getString("USERNAME");
				int team_id = DBobj.rs.getInt("TEAM_ID");
				
				if (user.equals(username)) {
					if (team_id != 0) {
						return team_id;
					} else {
						return 0;
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("There's an issue retrieving info. from query results");
		}
		
		DBobj.close();
		return -1;
	}
	
	/*
	 * Will return true boolean upon successful update - if false, check that username and teamID exist
	 */
	public static boolean setTeamID(String username, int teamID) {
		dbQuery DBobj = query("SELECT USERNAME, PIN FROM USER");	//just to init obj.
		String query = "UPDATE USER SET TEAM_ID = ? WHERE USERNAME = ?";
	
		try {
			PreparedStatement preparedStmt = DBobj.conn.prepareStatement(query);
			preparedStmt.setInt(1, teamID);
			preparedStmt.setString(2, username);
			preparedStmt.executeUpdate();
			//System.out.println("Prepared Statement: " + preparedStmt);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		DBobj.close();
		return true;
	}
	
	/*
	 * Will return true boolean upon success TeamID reset
	 */
	public static boolean resetTeamID(String username) {
		dbQuery DBobj = query("SELECT USERNAME, PIN FROM USER");	//just to init obj.
		String query = "UPDATE USER SET TEAM_ID = NULL WHERE USERNAME = ?";
	
		try {
			PreparedStatement preparedStmt = DBobj.conn.prepareStatement(query);
			preparedStmt.setString(1, username);
			preparedStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		DBobj.close();
		return true;
	}
	
	/*
	 *  >=0 - Returns value of team's health
	 *  -1 - Could not find team with this ID
	 */
	public static int getTeamHealth(int teamID) {
		dbQuery DBobj = query("SELECT HEALTH FROM TEAM");
		
		try {
			while (DBobj.rs.next()) {
				
				int ID = DBobj.rs.getInt("ID");
				
				if (ID == teamID) {
					return ID;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("There's an issue retrieving info. from query results");
		}
		
		DBobj.close();
		return -1;
	}
	
	/*
	 * Will return true boolean upon successful health update
	 */
	public static boolean setTeamHealth(int teamID, int health) {
		dbQuery DBobj = query("SELECT USERNAME, PIN FROM USER");	//just to init obj.
		String query = "UPDATE TEAM SET HEALTH = ? WHERE ID = ?";
	
		try {
			PreparedStatement preparedStmt = DBobj.conn.prepareStatement(query);
			preparedStmt.setInt(1, health);
			preparedStmt.setInt(2, teamID);
			preparedStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		DBobj.close();
		return true;
	}	
	
}