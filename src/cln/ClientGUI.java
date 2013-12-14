package cln;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import java.awt.TextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ClientGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel = new JPanel();
	private JTextArea taPort = new JTextArea();
	private JTextField tfPort = new JTextField();
	private JTextArea taAddress = new JTextArea();
	private JTextField txtField1 = new JTextField();
	private JTextField tfAddress = new JTextField();
	private ClientConnection client = new ClientConnection();

	private Button bConnect = new Button("Connect");
	private Button bDisconnect = new Button("Disconnect");
	private TextArea textArea = new TextArea();

	private String username = "";
	private String address = "";
	private int port = 0;
	boolean usernameInput = false;

	public ClientGUI(){

		setTitle("ClientChat Beta v.1");
		setBounds(100, 100, 450, 320);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(panel, BorderLayout.CENTER);

		panel.setLayout(null);

		txtField1.setText("Enter username");
		textArea.setText(""
				+ "Hello!"
				+ System.lineSeparator() + System.lineSeparator()
				+ "Welcome to ClientChat Beta v.1"
				+ System.lineSeparator() + System.lineSeparator()
				+ "Start off by entering your username"
				);
		taAddress.setText("Address");
		taPort.setText("Port");

		// TEXTFIELD TOP
		txtField1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String chatText = txtField1.getText();

				if (usernameInput != true){
					usernameInput = true;
					username = txtField1.getText();
					client.setUsername(username);
					
					textArea.setText(""
							+ "Username: " + username 
							+ System.lineSeparator() + System.lineSeparator());
					textArea.append("Enter address and port to login." + System.lineSeparator());
					
					txtField1.setText("");

				} else{
					textArea.append(username + " says: " + chatText + "\n");
				}
			}
		});
		
		//wipes the text in the top text field on first click
		txtField1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (usernameInput == false){
					txtField1.setText("");
				}
			}
		});

		txtField1.setHorizontalAlignment(SwingConstants.LEFT);
		txtField1.setColumns(10);
		txtField1.setBounds(10, 25, 414, 20);

		textArea.setEditable(false);
		textArea.setBounds(10, 66, 414, 160);

		bConnect.setActionCommand("");
		bConnect.setBounds(278, 247, 70, 22);

		bDisconnect.setBounds(354, 247, 70, 22);

		tfAddress.setColumns(10);
		tfAddress.setBounds(10, 249, 110, 20);

		taAddress.setBackground(UIManager.getColor("Button.background"));
		taAddress.setEditable(false);
		taAddress.setBounds(10, 228, 86, 22);

		tfPort.setColumns(10);
		tfPort.setBounds(131, 249, 60, 20);

		taPort.setEditable(false);
		taPort.setBackground(UIManager.getColor("Button.background"));
		taPort.setBounds(130, 228, 86, 22);

		panel.add(txtField1);
		panel.add(textArea);
		panel.add(bConnect);
		panel.add(bDisconnect);
		panel.add(tfAddress);
		panel.add(taAddress);
		panel.add(tfPort);
		panel.add(taPort);


		bConnect.setVisible(true);
		bDisconnect.setVisible(true);


		// LOGIN BUTTON
		bConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				address = tfAddress.getText();

				try {
					port = Integer.parseInt(tfPort.getText());
				} catch (NumberFormatException nfe){
					//no need to do anything
				}

				if (username.equals(null)){
					textArea.append("Please enter username");
				}

				if (address.equals(null) || port == 0){
					textArea.append("Please enter address and port.");
				} else {
					try {
						client.connect(address, port);
						textArea.append("Connected to: " + address + ":" + port);
						bConnect.setVisible(false);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

		});

		// LOGOUT BUTTON
		bDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (address.equals(null) || port == 0){
					//nothing to do here
				} else {
					try {
						client.disconnect();
						textArea.append(
								System.lineSeparator() + 
								"Disconnected from " + address + ":" + port + 
								System.lineSeparator());
						address = "";
						port = 0;
						bConnect.setVisible(true);
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}


			}
		});
	}

}
