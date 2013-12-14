package srv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionThread implements Runnable{

	private Socket 	socket = null;
	private PrintWriter outStream = null;
	private BufferedReader inStream = null;
	private String username = "";

	public ConnectionThread(Socket s) {
		socket = s;
	}

	public void run() {
		
		try {
			outStream = new PrintWriter(socket.getOutputStream(), true);
			inStream  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		username = receive();
		log(username + " has connected");
		

	}
	
	public void send(String s) {
		outStream.println(s);
		outStream.flush();
	}
	
	public String receive(){
		String receivedMessage = "";
		try {
			receivedMessage = inStream.readLine();
		} catch (IOException e) {
			log("Failed to receive message. " + e.getMessage());
		}

		return receivedMessage;
	}
	
	public void closeConnection(){
		try {
			inStream.close();
			outStream.close();
			socket.close();
		}catch(IOException e){
			log("Failed to properly close the connection. " + e.getMessage());
		}
	}
	
	public void log(String s) {
		System.out.println(s);
	}


}
