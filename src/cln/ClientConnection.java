package cln;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection {

	private Socket socket = null;
	private PrintWriter outStream   = null;
	private BufferedReader inStream    = null;
	private String username = "";

	public ClientConnection() {
	}
	
	protected void setUsername(String username){
		this.username = username;
	}

	public void connect(String serverAddress, int serverPort) throws IOException{
		socket = new Socket(serverAddress, serverPort);
		initializeStreams(socket);
		send(username);
	}
	
	public void initializeStreams(Socket s) throws IOException{
		outStream = new PrintWriter(s.getOutputStream(), true);
		inStream  = new BufferedReader(new InputStreamReader(s.getInputStream()));
	}

	public void disconnect() throws IOException{
		outStream.close();
		inStream.close();
		socket.close();
	}
	
	public void send(String s){
		outStream.println(s);
		outStream.flush();
	}
	
	public String receive(){
		
		String message = "";
		
		try {
			message = inStream.readLine();
		} catch (IOException e) {
			log("Failed to receive message" + e.getMessage());
		}

		return message;
	}
	
	public void log(String s){
		System.out.println(s);
	}

}
