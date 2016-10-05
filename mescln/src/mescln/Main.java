package mescln;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		Socket socket = new Socket("127.0.0.1", 2345);
		System.out.println("Socket: "+socket);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket
	               .getInputStream()));
	    PrintWriter out = new PrintWriter(new BufferedWriter(
	               new OutputStreamWriter(socket.getOutputStream())), true);
	    Scanner input = new Scanner(System.in);
	    String one = input.nextLine();
	    out.println(one);
        String str = in.readLine();
        System.out.println(str);     
	}

}
