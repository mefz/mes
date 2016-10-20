package mescln;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultEditorKit;

public class Main extends JFrame {
	
	JTextField txtfld = new JTextField(25);
	JButton button = new JButton("Send");
	JTextArea tarea = new JTextArea(7,30);
	JScrollPane scroll = new JScrollPane(tarea);
	eHandler handler = new eHandler();
	JMenuItem menuItem = new JMenuItem(new DefaultEditorKit.CopyAction());

	public Main() {
		setLayout(new FlowLayout());
		setSize(400,200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		this.add(txtfld);
		this.add(button);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tarea.setEditable(false);
		tarea.setLineWrap(true);
		this.add(scroll);
		setVisible(true);
		button.addActionListener(handler);		
		menuItem.setText("Copy");
		menuItem.setMnemonic(KeyEvent.VK_C);
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
		        String str =(String)in.readObject();
		        addLine(str);
			} catch (Exception e) {
				System.out.println("Communication error: "+e.getMessage());
				addLine("Connection failed");
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
		
		public void addLine(String newLine) {
			System.out.println(tarea.getCaretPosition());
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
