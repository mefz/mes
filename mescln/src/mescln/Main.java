package mescln;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;

public class Main extends JFrame {
	
	JTextField txtfld = new JTextField(25);
	JButton button = new JButton("Send");
	eHandler handler = new eHandler();

	public Main() {
		setLayout(new FlowLayout());
		setSize(500,300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		this.add(txtfld);
		this.add(button);
		setVisible(true);
		button.addActionListener(handler);
	}
	
	public class eHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==button) {
				System.out.println("Button pressed");
				new Thread(new Connection()).start();
			}
			
		}
		
	}
	
	public class Connection implements Runnable {
		Socket socket;

		@Override
		public void run() {
			try {
				socket = new Socket(InetAddress.getByName("127.0.0.1"), 2345);
				System.out.println("Socket: "+socket);
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			    out.writeObject(txtfld.getText());
		        String str = (String)in.readObject();
		        System.out.println(str); 
			} catch (Exception e) {
				System.out.println("Communication error: "+e);
			}
			finally {
				try {
					socket.close();
					System.out.println("Closed socket: "+socket);
				} catch (IOException e) {
					System.out.println("Error closing socket"+e);
				}
			}
			
		}
		
	}

	public static void main(String[] args) throws IOException {
		new Main();
		
	}

}
