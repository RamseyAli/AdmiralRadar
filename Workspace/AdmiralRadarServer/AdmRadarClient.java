import java.io.*;
import java.net.*;

public class AdmRadarClient {
	private Socket primeSocket;
	private PrintWriter outStream;
	private BufferedReader inStream;
	
	private void connect_Server(String hostName, int portNumber) throws Exception {
		primeSocket = new Socket(hostName, portNumber);
		
		outStream = new PrintWriter(primeSocket.getOutputStream(), true);
		inStream = new BufferedReader(new InputStreamReader(primeSocket.getInputStream()));
	}
	
	public void login() {} // TODO: Database stuff

	public void sendCommands() {

	}
	
	public void sendMessages(String msg) {
		outStream.println(msg);
	}
	public void getMessages() throws IOException {
		inStream.readLine();
	}
	public void getShipObject(){
		
	}
	static int callClientTypeGUI(){
		return 0;
	}

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
			PrintWriter out = new PrintWriter(arSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(arSocket.getInputStream()));
			ObjectOutputStream os = new ObjectOutputStream(arSocket.getOutputStream());
			ObjectInputStream is = new ObjectInputStream(arSocket.getInputStream());
		) {
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			
			String fromUser;
			
			Maps map1 = new Maps();
			map1 = (Maps) is.readUnshared();
			
			map1.printAsteroids();
			
			System.out.println("Enter your location x,y : ");
			fromUser = stdIn.readLine();
			
			String[] coordinates = fromUser.split(",");
			int a = Integer.parseInt(coordinates[0]);
			int b = Integer.parseInt(coordinates[1]);
			
			Position p = new Position();
			p.setPosition(a,b);
			
			os.writeObject(p);
						
			Spaceship teamShip = (Spaceship) is.readUnshared();
			
			while (teamShip != null)
			{
				fromUser = stdIn.readLine();
				if (fromUser != null)
				{
					System.out.println("Client: " + fromUser);
					out.println(fromUser);
				}
				teamShip = (Spaceship) is.readUnshared();
				if(teamShip != null)
				{
					teamShip.printPosition();
					System.out.print("Ship path : ");
					System.out.println(teamShip.getPath());
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