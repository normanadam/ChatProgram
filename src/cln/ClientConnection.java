package cln;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientConnection {

	private Socket connectionToServer = null;
	private PrintWriter outStream     = null;
	private BufferedReader inStream   = null;
	private String userName;
	ObjectInputStream sInput;
	ObjectOutputStream sOutput;
	
	public ClientConnection(String serverAddress, int serverPort, String userName) throws IOException{
		userName = this.userName;
		sInput = new ObjectInputStream(connectionToServer.getInputStream());
		sOutput = new ObjectOutputStream(connectionToServer.getOutputStream());
		
		connect(serverAddress, serverPort);
		
	}
	
	// set up a connection to a specified server and port
	public void connect(String serverAddress, int serverPort) throws IOException{

		//Set up a socket and connect it to our server
		connectionToServer = new Socket(serverAddress, serverPort);
		
		sOutput.writeObject(userName);

	}
	
	// close the streams and the socket
	public void close(){
		log("Closing connection.");
		try{
			inStream.close();
			outStream.close();
			connectionToServer.close();
		}catch(IOException e){
			logError("Failed to properly close the connection. " + e.getMessage());
		}
		
		log("Connection closed.");
	}
	
	// write a message to the stream going to the server
	public void send(String message){
		log("Sending...");
		outStream.println(message);
		outStream.flush();
		log("Message sent.");
	}
	
	// read a message from the stream coming from the server
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
	
	private void log(String message){
		System.out.println("Client " + 
				Thread.currentThread().getName() + 
				": " + message);
	}

	private void logError(String message){
		System.err.println("Client: " + message);
	}

	// this is what happens when the Thread is run:
	// the client will send 3 messages, the last one being "bye"
	// then, it is going to close down
	public void run() {
		send("* msg 1 *");
		String receivedMessage = receive();
		System.out.println(receivedMessage);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		send("* msg 2 *");
		receivedMessage = receive();
		System.out.println(receivedMessage);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		send("bye");
		receivedMessage = receive();
		System.out.println(receivedMessage);
		close();
	}
	
}
