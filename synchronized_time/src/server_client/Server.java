package server_client;


//import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
//import java.io.InputStreamReader;
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
//				BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
				String str="",str2="";
				str=din.readUTF();  
				if(str.equals("Request Time")) {
					str2 = timeFormat.format(Calendar.getInstance().getTime());  
					dout.writeUTF(str2);  
					dout.flush();
				}
				
//				ClientHandler clientHandler = new ClientHandler(socket);
//				Thread thread = new Thread(clientHandler);
//				thread.start();
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
//public class Server extends Thread {
//	private ServerSocket serverSocket;
//
//	public Server(int port){
//		try {
//			this.serverSocket = new ServerSocket(port);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void run(){
//		while (true) {
//			try {
//				Socket server = serverSocket.accept();
//				DataInputStream in = new DataInputStream(server.getInputStream());
////				System.out.println(in);
//				DataOutputStream out = new DataOutputStream(server.getOutputStream());
////				System.out.println(out);
//				int i = in.readInt();
//				for (int j = 0; j < i; j++) {
//					Thread.sleep((long) (100 + new Random().nextInt(51)));
//					out.writeLong(System.currentTimeMillis());
//				}
//				server.close();
//			} catch (UnknownHostException ex) {
//				Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//			} catch (IOException | InterruptedException ex) {
//				Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//			}
//		}
//	}
//
//	public static void main(String[] args){
//		int port = 5000;
//		Thread t = new Server(port);
//		t.start();
//	}
//
//}