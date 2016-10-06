package mescln;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import mescln.Main.eHandler;

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

		@Override
		public void run() {
			try {
				Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 2345);
				System.out.println("Socket: "+socket);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			    out.println(txtfld.getText());
		        String str = in.readLine();
		        System.out.println(str); 
			} catch (Exception e) {System.out.println("Communication error: "+e);}
			
		}
		
	}

	public static void main(String[] args) throws IOException {
		Main window = new Main();
		
	}

}
