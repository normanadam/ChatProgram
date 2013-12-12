package srv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionThread extends Thread{

	private Socket 	connectionToClient = null;
	private PrintWriter    outStream   = null;
	private BufferedReader inStream    = null;
	private ObjectInputStream sInput;
	private ObjectOutputStream sOutput;
	private String userName;

	public ConnectionThread(Socket s) {
		connectionToClient = s;
		
		try {
			sInput = new ObjectInputStream(connectionToClient.getInputStream());
			sOutput = new ObjectOutputStream(connectionToClient.getOutputStream());
			userName = (String) sInput.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		log("" + userName + " has connected");
		
	}

	public void run() {

		try {
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			close();
		}
	}

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

	private void log(String message){
		System.out.println(this.getName() + message);
	}

	private void logError(String message){
		System.err.println(this.getName() + message);
	}
}
