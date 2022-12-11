package server_client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Server{
	private ServerSocket serverSocket;
	private SimpleDateFormat timeFormat;
	private DataInputStream din;
	private DataOutputStream dout;
	
	public Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	public void startServer() {
		try {
			setTimeFormat(new SimpleDateFormat("HH:mm:ss"));
			while(!serverSocket.isClosed()) {
				Socket socket = serverSocket.accept();
				System.out.println("A new client has connected!");
				din = new DataInputStream(socket.getInputStream());  
				dout = new DataOutputStream(socket.getOutputStream());  
				String str="",str2="";
				str=din.readUTF();  
				if(str.equals("Request Time")) {
					str2 = timeFormat.format(Calendar.getInstance().getTime());  
					dout.writeUTF(str2);  
					dout.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeServerSocket() {
		try {
			if(serverSocket != null) {
				serverSocket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public SimpleDateFormat getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(SimpleDateFormat timeFormat) {
		this.timeFormat = timeFormat;
	}

	public DataInputStream getDin() {
		return din;
	}

	public void setDin(DataInputStream din) {
		this.din = din;
	}

	public DataOutputStream getDout() {
		return dout;
	}

	public void setDout(DataOutputStream dout) {
		this.dout = dout;
	}

	public static void main(String[] args) {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(9999);
			Server server = new Server(serverSocket);
			server.startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
