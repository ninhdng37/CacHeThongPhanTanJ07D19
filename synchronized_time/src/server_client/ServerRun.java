package server_client;

import java.net.ServerSocket;

public class ServerRun implements Runnable{
	private ServerSocket serverSocket;
	private Server server;
	public ServerRun(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.server = new Server(this.serverSocket);
	}
	@Override
	public void run() {
			this.server.startServer();
	}
}
