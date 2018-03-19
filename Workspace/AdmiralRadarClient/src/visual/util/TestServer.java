package visual.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer implements Runnable{




	@Override
	public void run() {

		int portNumber = 2069;
		boolean listening = true;

		try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
			while (listening) {
				Socket s = serverSocket.accept();

				try (
						PrintWriter out = new PrintWriter(s.getOutputStream(), true);
						BufferedReader in = new BufferedReader(
								new InputStreamReader(
										s.getInputStream()));
						) {
					String inputLine;

					while ((inputLine = in.readLine()) != null) {
						out.println(inputLine);
					}
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}


		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
			System.exit(-1);
		}
	}

}


