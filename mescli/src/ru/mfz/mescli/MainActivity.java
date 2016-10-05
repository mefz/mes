package ru.mfz.mescli;

import android.R.anim;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	public static EditText msg;
	public static TextView rec;
	private Context context;
	public static Handler h;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button sendButton = (Button)findViewById(R.id.button1);
		msg = (EditText)findViewById(R.id.editText1);
		rec = (TextView)findViewById(R.id.textView1);
		context = this;
		
		h = new Handler() {
			public void handleMessage(Message msgback) {
				rec.setText(msgback.obj.toString());
			}
		};
		sendButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Log.d(msg.getText().toString(), "Pressed");
				new Thread(new Client(msg.getText().toString(), context)).start();			
			}
		});
		
	}
}
