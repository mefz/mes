package mescln;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
		setResizable(false);
		setTitle("MEScln");
		this.add(txtfld);
		this.add(button);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tarea.setEditable(false);
		tarea.setLineWrap(true);
		this.add(scroll);
		setVisible(true);
		button.addActionListener(handler);
		
		txtfld.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
					preSend();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}			
		});
		
	}
	
	public class eHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==button) {
				System.out.println("Button pressed");
				preSend();
			}
			
		}
		
	}
	
	public void preSend(){		
		if (txtfld.getText().equals("exit")){
			System.exit(0);
		} else if (txtfld.getText().equals("cls")){
			tarea.setText(null);
			txtfld.setText(null);
		} else {
			new Thread(new Connection()).start();
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
			    System.out.println("Sent: "+txtfld.getText());
		        String str =(String)in.readObject();
		        System.out.println("Received: "+str);
		        addLine(str);
			} catch (Exception e) {
				System.out.println("Communication error: "+e.getMessage());
				addLine("Connection failed");
			} finally {
				txtfld.setText(null);
				try {					
					socket.close();
					System.out.println("Closed socket: "+socket);
				} catch (NullPointerException | IOException e) {
					System.out.println("Error closing socket "+e);
				}	
			}
			
		}
		
		public void addLine(String newLine) {
			if (tarea.getCaretPosition()!=0) {
				tarea.append("\n");
			}
			tarea.append(newLine);
			tarea.setCaretPosition(tarea.getDocument().getLength());
		}
		
	}

	public static void main(String[] args) throws IOException {
		new Main();
		
	}

}
