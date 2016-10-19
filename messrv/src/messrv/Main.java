package messrv;
//
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Main extends Thread {
	private Socket socket;
	ObjectInputStream input;
	ObjectOutputStream output;
	Object obj;
	
	public Main(Socket s) throws IOException {
		socket = s;
		start();
	}
	public void run() {		
		try {
				output = new ObjectOutputStream(socket.getOutputStream());
				input = new ObjectInputStream(socket.getInputStream());
			  	String str = (String)input.readObject();
			    output.flush();
			    output.writeObject("got "+str);
		} catch (IOException | ClassNotFoundException e) {
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
		ServerSocket s = new ServerSocket(2345, 5);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					s.close();
				} catch (IOException e) {
					System.out.println("Can not close socket:"+e);
				}
				System.out.println("server terminated");
				} 
});
		System.out.println("Listening to: "+s);			
		while (true) {
			Socket socket = s.accept();
			System.out.println("Connection accepted: "+socket);
            new Main(socket);
         }
	}

}
