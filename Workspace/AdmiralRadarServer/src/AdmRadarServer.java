
import javax.crypto.spec.*;

import game.GameMap;
import game.Position;
import game.Spaceship;

import javax.crypto.*;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class AdmRadarServer
{
	ArrayList<PrintWriter> clientOutputStreams;
	ArrayList<BufferedReader> clientInputStreams;
	ArrayList<Thread> clientThreads;
	ArrayList<Spaceship> spaceship;
	static int nPlayers;

	public class ClientHandler implements Runnable 
	{
		BufferedReader reader;
		Socket sock;
		PrintWriter writer;
		ObjectOutputStream oos;
		ObjectInputStream ois;
		Spaceship ship;
		int i;
		
		public ClientHandler(Socket clientSock,int index)
		{
			i = index;
			try
			{
				sock = clientSock;
				oos = new ObjectOutputStream(sock.getOutputStream());
				ois = new ObjectInputStream(sock.getInputStream());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		public void run()
		{
			try {
				AdmRadarProtocol arp = new AdmRadarProtocol();
				
				GameMap m = new GameMap();
				m = arp.updateMap();
				oos.writeUnshared(m);
				oos.reset();
				
				String inputLine;
				
				inputLine = (String) ois.readUnshared();
				
				if(inputLine.equalsIgnoreCase("Captain"))
				{
					Position p = new Position();
					p = (Position) ois.readUnshared();

					ship = new Spaceship();
					ship.setPos(p);
					spaceship.set(i,ship);
					
					oos.writeUnshared(spaceship.get(i));
					oos.reset();
					
					while(true)
					{
						inputLine = (String) ois.readUnshared();
						ship = spaceship.get(i);
						ship = arp.processCommands(inputLine,ship);
						spaceship.set(i,ship);
						oos.writeUnshared(spaceship.get(i));
						oos.reset();
						
						if (inputLine.equals("exit"))
						{
							nPlayers--;
							stopAllThreads();
							break;
						}
						if(Thread.currentThread().isInterrupted())
						{
							break;
						}
					}
				}
				else if(inputLine.equalsIgnoreCase("First Officer"))
				{
					ship = spaceship.get(i);
					
					oos.writeUnshared(spaceship.get(i));
					oos.reset();
					
					while(true)
					{
						inputLine = (String) ois.readUnshared();
						ship = spaceship.get(i);
						ship = arp.processCommands(inputLine,ship);
						spaceship.set(i,ship);
						oos.writeUnshared(spaceship.get(i));
						oos.reset();
						
						if (inputLine.equals("exit"))
						{
							nPlayers--;
							stopAllThreads();
							break;
						}
						if(Thread.currentThread().isInterrupted())
						{
							break;
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
        
		if (args.length != 1)
		{
			System.err.println("Usage: java AdmRadarServer <port number>");
			System.exit(1);
		}
		
		nPlayers = 0;
		//Database test

		/*	String username = "TEST_USER";
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
		
		int portNumber = Integer.parseInt(args[0]);
		new AdmRadarServer().go(portNumber);
	}
	
	public void go(int port)
	{
		clientOutputStreams = new ArrayList<PrintWriter>();
		clientInputStreams = new ArrayList<BufferedReader>();
		clientThreads = new ArrayList<Thread>();
		spaceship = new ArrayList<Spaceship>();
		
		System.out.println("GAME LOBBY");
		System.out.println("Error: Not enough players");
		System.out.println("Game Mode: Turn Based");
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			
			while(true)
			{
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				clientOutputStreams.add(out);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				clientInputStreams.add(in);
				
				Thread t = new Thread(new ClientHandler(clientSocket,nPlayers%2));
				clientThreads.add(nPlayers,t);
				nPlayers++;
				
				System.out.println("Got a player");
				if(nPlayers < 4)
				{
					System.out.println("Waiting for other players...");
				}
				else if(nPlayers == 4)
				{
					System.out.println("GAME BEGINS");
					Spaceship initial = new Spaceship();
					spaceship.add(0,initial);
					spaceship.add(1,initial);
					for(Thread t1:clientThreads)
					{
						t1.start();
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception caught when trying to listen on port " + port + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
	
	public void stopAllThreads()
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
        }
	
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
	public static int reset(String user, String pw, int pin) {

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
		Credit for encrpytion method goes to Java2S.com
	 */

///

	static class DesEncrypter {
		Cipher ecipher;

		DesEncrypter(SecretKey key) throws Exception {
			ecipher = Cipher.getInstance("DES");
			ecipher.init(Cipher.ENCRYPT_MODE, key);
		}

		@SuppressWarnings("restriction")
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
	public static String avatarURL(String user) {

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
	public static int wins(String user) {

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
	public static int losses(String user) {

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


}