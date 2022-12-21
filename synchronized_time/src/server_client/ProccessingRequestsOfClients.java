package server_client;

import java.io.IOException;
import java.net.ServerSocket;

public class ProccessingRequestsOfClients {
	public static void main(String[] args) {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(9999);
			ServerRun serverRun = new ServerRun(serverSocket);
			ServerClockRun serverClockRun = new ServerClockRun();
			Thread thread = new Thread(serverClockRun);
			thread.start();
			for (int i = 0; i <= 3; i++) {
				Thread myThreadForServerRun = new Thread(serverRun);
				myThreadForServerRun.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
