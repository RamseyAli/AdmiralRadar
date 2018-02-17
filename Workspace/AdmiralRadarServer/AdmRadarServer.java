import java.net.*;
import java.io.*;

public class AdmRadarServer {
    public static void main(String[] args) throws IOException {
        
        if (args.length != 1) {
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
            outputLine = arp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = arp.processInput(inputLine);
                out.println(outputLine);
		outputLine = arp.processInput(null);
		out.println(outputLine);
                if (outputLine.equals("Bye."))
                    break;
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}