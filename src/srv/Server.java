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
	private Socket 	connectionToClient = null;
	private ArrayList<ConnectionThread> cList;

	// use a ServerSocket to initialize listening 
	// to the port specified
	public void init(int port) throws IOException{
		serverSocket = new ServerSocket(port);
	}

	public void start() throws IOException{
		while (true){
			connectionToClient = serverSocket.accept();
			initializeThread(connectionToClient);
		}
	}

	public void initializeThread(Socket s) {
		ConnectionThread cThread = new ConnectionThread(s);
		cList.add(cThread);
		cThread.start();
	}

	public static void main(String[] args) {
		Server server = new Server();

		// SETUP SERVER
		try {
			server.init(52000);
		} catch (IOException e) {
			System.err.println("Failed to setup a server socket. " + e.getMessage());
			System.exit(1);
		}

		try {
			server.start();
		} catch (IOException e) {
			System.err.println("Failed to setup connection. " + e.getMessage());
			System.exit(1);
		}
	}
}
