import java.io.*;
import java.net.*;

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
}