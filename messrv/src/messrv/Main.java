package messrv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static void main(String[] args) throws IOException {
		ServerSocket s = new ServerSocket(2345);
		System.out.println("New socket: "+s);
		Socket socket = s.accept();
		System.out.println("Connection accepted: "+socket);
		BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
		PrintWriter out = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())), true);
		while (true) {
            String str = in.readLine();
            if (str.equals("END"))
               break;
            System.out.println("Echoing: " + str);
            out.println(str);
         }
	}

}
