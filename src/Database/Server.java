package Database;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
	int port;

	public Server(int port) {
		this.port = port;
	}

	public void run() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		} // Start, listen on port 80
		while (true) {
			try {
				Socket s = serverSocket.accept(); // Wait for a client to
													// connect
				new API_Handler(s); // Handle the client in a separate thread
			} catch (Exception x) {
				System.out.println(x);
			}
		}
	}
}
