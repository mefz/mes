package ru.mfz.mescli;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class Client implements Runnable{
static private Socket connection;
static private ObjectOutputStream output;
static private ObjectInputStream input;
private String msg, out;
Context context;

public Client(String msg, Context context) {
	this.msg = msg;
	this.context = context;
	
}

public void connect()  {
	try {		
			connection = new Socket(InetAddress.getByName("192.168.1.120"), 2121);//10.0.0.2
			output = new ObjectOutputStream(connection.getOutputStream());
			input = new ObjectInputStream(connection.getInputStream());
			Log.d("Title", "connected");
	} catch(IOException e) {Log.d("Connection failed!", e.toString());}
			/*Client.sendData(MainActivity.msg.getText().toString());
			Log.d("Title", "sent");
			Log.d("Title", (String)input.readObject());
	} catch(IOException e) {Log.d("Title", e.toString());
	} catch(ClassNotFoundException e) {Log.d("Title", e.toString());}*/
}
public void sendData(String str) {
	try {
		output.flush();
		output.writeObject(str);
		Log.d("Data", "Sent");
	} catch (IOException e) {Log.d("Send failed!", e.toString());}
}
public void recData() {
	try{
		MainActivity.h.obtainMessage(0, input.readObject()).sendToTarget();
	} catch(Exception e) {Log.d("Out failed!", e.toString());}
}
@Override
public void run() {
	connect();
	sendData(msg);
	recData();
}

}
