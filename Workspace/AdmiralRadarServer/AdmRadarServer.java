import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class AdmRadarServer
{
	public static void main(String[] args) throws IOException
	{
        
		if (args.length != 1)
		{
			System.err.println("Usage: java AdmRadarServer <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);

		try ( 
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket clientSocket = serverSocket.accept();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());
		) {
			String inputLine, outputLine;
			
			// Initiate conversation with client
			AdmRadarProtocol arp = new AdmRadarProtocol();
			
			Maps m = new Maps();
			m = arp.updateMap();
			os.writeObject(m);
			
			outputLine = arp.processMessages(null);
			out.println(outputLine);
			
			while(true)
			{
				inputLine = in.readLine();
				outputLine = arp.processMessages(inputLine);
				out.println(outputLine);
				if (inputLine.equals("exit"))
					break;
				
				outputLine = arp.processMessages(null);
				out.println(outputLine);
			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}


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
					if (user.equals(DBobj.rs.getString("PASSWORD"))) {
						return 0;
					} else {
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


}