package cln;

import java.io.IOException;
import java.net.InetAddress;

public class StartClass {

	public static void main(String[] args) {

		try {
			//create two client connections
			ClientConnection cl1 = new ClientConnection("127.0.0.1", 52000);
			//ClientConnection cl2 = new ClientConnection("127.0.0.1", 52000);
			
			
			// create and start Threads using those connections
			Thread t1 = new Thread(cl1);
			t1.start();
			//Thread t2 = new Thread(cl2);
			//t2.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
