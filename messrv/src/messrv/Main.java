package messrv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Main extends Thread {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	public Main(Socket s) {
		socket = s;
		try {
			in = new BufferedReader(new InputStreamReader(
			        socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(
			        new OutputStreamWriter(socket.getOutputStream())), true);
		} catch (IOException e) {
			System.out.println("Buffer error: "+e);
		}
		start();
	}
	public void run(){
		try {
			while(true) {
				String str = in.readLine();
				if (str.equals("END"))
					break;
			    System.out.println("Echoing: " + str);
			    out.println(str);
			}
		} catch (IOException e) {
			System.out.println(e);
		} 
		finally {
			try {
				socket.close();
				System.out.println("Closed socket "+socket);
			} catch (IOException e) {
				System.out.println("Error closing socket "+e);
			}
		}
		
		
	}

	public static void main(String[] args) throws IOException {
		ServerSocket s = new ServerSocket(2345);
		System.out.println("New socket: "+s);			
		while (true) {
			Socket socket = s.accept();
			System.out.println("Connection accepted: "+socket);
            new Main(socket);
         }
	}

}
