package ru.mfz.mes;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable{
static private Socket connection;
static private ObjectOutputStream output;
static private ObjectInputStream input;

	public static void connect()  {
		try {
				connection = new Socket(InetAddress.getByName("127.0.0.1"), 2121);
				output = new ObjectOutputStream(connection.getOutputStream());
				input = new ObjectInputStream(connection.getInputStream());
				System.out.println("connected");
				Client.sendData(Main.message.getText());
				System.out.println("sent");
				System.out.println((String)input.readObject());
		} catch(IOException | ClassNotFoundException e) {}
	}
	static void sendData(Object obj) {
		try {
			output.flush();
			output.writeObject(obj);
		} catch (IOException e) {	
		} catch (HeadlessException e) {}
	}
	@Override
	public void run() {
		connect();		
	}

}
