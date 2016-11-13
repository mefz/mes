package messrv;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Main extends Thread {
	private Socket socket;
	ObjectInputStream input;
	ObjectOutputStream output;
	Object obj;
	static Stat stat;
	
	public Main(Socket s) throws IOException {
		socket = s;		
		start();
	}
	public synchronized void run() {		
		try {
				output = new ObjectOutputStream(socket.getOutputStream());
				input = new ObjectInputStream(socket.getInputStream());
			  	String str = (String)input.readObject();	
			  	stat.lmodel.addElement(currentThread().getName());
			    IO(str);
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
	
	public void IO(String in) throws IOException {
		output.flush();
		System.out.println("Incoming: "+in);
		if (!in.trim().isEmpty()) {
			output.writeObject("got "+in);
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://192.168.1.120:3306/mes", "remote", "lcd");
				Statement stm = con.createStatement();
				String statement = "INSERT INTO tab (mes) VALUES ('"+in+"')";
				stm.executeUpdate(statement);
				ResultSet res = stm.executeQuery("SELECT * FROM tab");
				while (res.next()) {
					System.out.println("DB>"+res.getString(2));
				}
				res.close();
				stm.close();
				con.close();
			} catch (Exception e) {
				System.out.println("IO:MySQL error."+e);
			}	
			
		} else {
			output.writeObject("got Empty");
			return;
		}
		if (in.equals("srv.exit")) {
			output.writeObject("terminating server");
			System.exit(0);
		}
	}

	public static void main(String[] args) throws IOException {	
		stat = new Stat();
		ServerSocket s = new ServerSocket(2345, 5);
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					s.close();
				} catch (IOException e) {
					System.out.println("Can't close socket:"+e.getMessage());
				}
				System.out.println("server terminated");
				} 
});
		System.out.println("Listening to: "+s);			
		while (!s.isClosed()) {
			try {
				Socket socket = s.accept();
				System.out.println("Connection accepted: "+socket);
				new Main(socket);
			} catch (Exception e) {
				System.out.println(e);
			}
         }
	}

}
