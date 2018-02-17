import java.io.*;
import java.net.*;

public class AdmRadarClient {
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
            BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

	    fromServer = in.readLine();
		System.out.println("Server: " + fromServer);

                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
		}
	    
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye."))
                    break;
		fromServer = in.readLine();
		System.out.println("Server: " + fromServer);

                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
}