package ru.mfz.mes;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Main extends JFrame implements Runnable {
	static JTextField message = new JTextField(25);
	JButton send = new JButton("Send to server");
	eHandler handler = new eHandler();

	public static void main(String[] args) {
		new Thread(new Main("Hello World!")).start();
	}
	public Main(String title){
		super(title);
		setLayout(new FlowLayout());
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		this.add(message);
		this.add(send);
		send.addActionListener(handler);
	}
	public class eHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==send){
				System.out.println("pressed");
				new Thread(new Client()).start();
			}
			
		}
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}

