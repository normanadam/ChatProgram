package srv;

// import java.io.BufferedReader;
import java.io.IOException;
// import java.io.InputStreamReader;
// import java.io.OutputStreamWriter;
// import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket serverSocket  = null;
	private Socket 	connectionToClient = null;
	// private PrintWriter    outStream   = null;
	// private BufferedReader inStream    = null;

	// use a ServerSocket to initialize listening 
	// to the port specified
	public void init(int port) throws IOException{
		log("Setting up server socket...");
		serverSocket = new ServerSocket(port);
		log("Server socket setup complete.");
	}

	// waitForConnection will stay in the call to serverSocket.accept()
	// until a client connects
	// then it initializes the streams used for communication with the client
	public void waitForConnection() throws IOException{
		while (true){
			log("Waiting for connections...");
			connectionToClient = serverSocket.accept();
			initializeThread(connectionToClient);
			// outStream = new PrintWriter(new OutputStreamWriter(connectionToClient.getOutputStream()));
			// inStream  = new BufferedReader(new InputStreamReader(connectionToClient.getInputStream()));
			log("Connection to client established.");
		}
	}

	public void initializeThread(Socket s) {
		ConnectionThread cT = new ConnectionThread(s);
		cT.start();
	}

	/*
	// close the streams and the sockets
	public void close(){
		log("Closing connection.");
		try{
			// log data about connection before closing
			this.logConnectionInfo();

			// close and clean up
			inStream.close();
			outStream.close();
			connectionToClient.close();
			serverSocket.close();
		}catch(IOException e){
			logError("Failed to properly close the connection. " + e.getMessage());
		}

		log("Connection closed.");
	}

	// write message to the stream going to the client
	public void send(String message){
		log("Sending...");
		outStream.println(message);
		outStream.flush();
		log("Message sent.");
	}

	// echo method
	// use receive to wait for input from client
	// when it we get it, use send to echo it back
	// stop when received message "bye"
	public void echo(){
		String in = "";
		while (!in.equals("bye")){
			in = receive();
			send(in);
		}
	}

	// read a message from the stream coming in from the client
	public String receive(){
		log("Receiving...");

		String receivedMessage = "";
		try {
			receivedMessage = inStream.readLine();
			log("Message received!");
		} catch (IOException e) {
			logError("Receive failed! " + e.getMessage());
		}

		return receivedMessage;
	}

	private void logConnectionInfo(){
		log("Connection info:");
		System.out.println("\t" + connectionToClient);
	}

	 */

	private void log(String message){
		System.out.println("Server: " + message);
	}

	/*
	private void logError(String message){
		System.err.println("Server: " + message);
	}
	 */
}
