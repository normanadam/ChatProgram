package srv;

import java.io.IOException;

public class StartClass {

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
			server.waitForConnection();
		} catch (IOException e) {
			System.err.println("Failed to setup connection. " + e.getMessage());
			System.exit(1);
		}
	
		/*
		server.send("********** Hello from the server! **********");
		
		String receivedMessage = server.receive();
		System.out.println(receivedMessage);
		*/
		
		// echo messages to server
		//server.echo();
		// close the server
		//server.close();
	}

}
