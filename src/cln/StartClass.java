package cln;

import java.io.IOException;
import java.net.InetAddress;

public class StartClass {

	public static void main(String[] args) {
		
		String userName = "anonymous";

		try {
			ClientConnection cl1 = new ClientConnection("127.0.0.1", 52000, userName);

		} catch (IOException e) {
			System.out.println("Error connecting to server");
			e.printStackTrace();
		}
		
		
	}

}
