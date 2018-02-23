import java.security.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class AdmRadarServer
{
	ArrayList<PrintWriter> clientOutpurStreams;
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
				reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				writer = new PrintWriter(sock.getOutputStream(),true);
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
				
				Maps m = new Maps();
				m = arp.updateMap();
				oos.writeUnshared(m);
				oos.reset();
				
				String inputLine;
				
				inputLine = reader.readLine();
			
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
						if(Thread.currentThread().isInterrupted())
						{
							inputLine = reader.readLine();
							oos.writeUnshared(null);
							oos.reset();
							break;
						}
						inputLine = reader.readLine();
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
					}
				}
				else if(inputLine.equalsIgnoreCase("First Officer"))
				{
					ship = spaceship.get(i);
					
					oos.writeUnshared(spaceship.get(i));
					oos.reset();
					
					while(true)
					{
						if(Thread.currentThread().isInterrupted())
						{
							inputLine = reader.readLine();
							oos.writeUnshared(null);
							oos.reset();
							break;
						}
						inputLine = reader.readLine();
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
		
		int portNumber = Integer.parseInt(args[0]);
		new AdmRadarServer().go(portNumber);
	}
	
	public void go(int port)
	{
		clientOutpurStreams = new ArrayList<PrintWriter>();
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
				clientOutpurStreams.add(out);
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
		}
	}


	public static dbQuery query(String query) {

		Connection conn = null;

		try {

			String url = "jdbc:mysql://radar.c87i64zdxk4i.us-east-2.rds.amazonaws.com:3306/AdmiralRadar";
			Properties info = new Properties();
			info.put("user", "admin");
			info.put("password", "password1234");

			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = DriverManager.getConnection(url, info);

		} catch (Exception ex) {
			System.out.println("An error occurred while connecting MySQL databse");
			ex.printStackTrace();
			return null;
		}

		if (conn != null) {
			System.out.println("Successfully connected to MySQL database");
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
			while (DBobj.rs.next()) {
				if (user.equals(DBobj.rs.getString("USERNAME"))) {
					if (pw.equals(DBobj.rs.getString("PASSWORD"))) {
						DBobj.close();
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
		} finally {
			DBobj.close();
			return 1;
		}
	}

	/*
	0 - Success
	1 - Invalid Username
	2 - Invalid PIN
	*/
	public static int reset(String user, int pin) {

		dbQuery DBobj = query("SELECT USERNAME, PIN FROM USER");

		try {
			while (DBobj.rs.next()) {
				if (user.equals(DBobj.rs.getString("USERNAME"))) {
					if (pin == DBobj.rs.getInt("PIN")) {
						DBobj.close();
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
		} finally {
			DBobj.close();
			return 1;
		}
	}

	/*
		Credit for encrpytion method goes to Vignesh @ StackOverflow
	 */

	private static String encrypt(String string) {
		try {
			String text = string;
			String key = "raB54321raB54321";
			// Create key and cipher
			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			// encrypt the text
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encrypted = cipher.doFinal(text.getBytes());
			return new String(encrypted);
		} catch (Exception ex) {
			System.out.println("Error in encryption");
			return "ERROR";
		}
	}

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
		} finally {
			DBobj.close();
			return "ERROR";
		}
	}


}