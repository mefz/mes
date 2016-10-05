package ru.mfz.messrv;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static void main(String[] args) {
		Socket connection;
		ObjectOutputStream output;
		ObjectInputStream input;
		ServerSocket server;
		Object fromClient;
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("server terminated");
				} 
		});
		
			try {
				server = new ServerSocket(2121, 5);
				System.out.println("listening to port 2121");
				while(true){
					connection = server.accept();
					System.out.println("new connection acccepted");
					output = new ObjectOutputStream(connection.getOutputStream());
					input = new ObjectInputStream(connection.getInputStream());
					fromClient = input.readObject();
					System.out.println((String)fromClient);
					output.flush();
					output.writeObject("echo "+fromClient);
					System.out.println("sent back");
				}
			} catch(IOException | ClassNotFoundException e) {}
	}
}
