package srv;

// import java.io.BufferedReader;
import java.io.IOException;
// import java.io.InputStreamReader;
// import java.io.OutputStreamWriter;
// import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	private ServerSocket serverSocket  = null;
	private Socket socket = null;
	private ArrayList<ConnectionThread> cList;

	// use a ServerSocket to initialize listening 
	// to the port specified
	public void initializeServer(int port) throws IOException{
		serverSocket = new ServerSocket(port);
		cList = new ArrayList<ConnectionThread>();
	}

	public void waitForConnections() throws IOException{
		
		while (true){
			System.out.println("Waiting for connections..");
			socket = serverSocket.accept();
			initializeThread(socket);
		}
		
	}

	public void initializeThread(Socket s) {
		Thread t = new Thread(new ConnectionThread(socket));
		t.start();
	}
	
	public void close(){
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void log(String s){
		System.out.println("Server: " + s);
	}

	public static void main(String[] args) {
		
		Server server = new Server();

		try {
			server.initializeServer(52000);
		} catch (IOException e) {
			System.err.println("Failed to setup a server socket. " + e.getMessage());
			System.exit(1);
		}

		try {
			server.waitForConnections();
		} catch (IOException e) {
			System.err.println("Failed to setup connection. " + e.getMessage());
			System.exit(1);
		}
		
		server.close();
	}
}
