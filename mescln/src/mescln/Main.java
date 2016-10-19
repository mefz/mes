package mescln;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class Main extends JFrame {
	
	JTextField txtfld = new JTextField(25);
	JButton button = new JButton("Send");
	JTextArea tarea = new JTextArea(7,30);
	JScrollPane scroll = new JScrollPane(tarea);
	eHandler handler = new eHandler();

	public Main() {
		setLayout(new FlowLayout());
		setSize(400,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		this.add(txtfld);
		this.add(button);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tarea.setEditable(false);
		this.add(scroll);
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
		        String str =(String)in.readObject()+"\n";
		        tarea.setText(tarea.getText()+str);
			} catch (Exception e) {
				System.out.println("Communication error: "+e.getMessage());
				tarea.setText("Connection failed");
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
