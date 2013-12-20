package com.example.lvyousystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {
	String gname;
	String gpsw;
	EditText ename;
	EditText epsw;
	public static String uname;
	
	public void check (){
		String tmp = null;
		try{
			Socket socket = new Socket("192.168.137.1", 8888);
			BufferedWriter dout = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream()));
			dout.write("@login\n");
			dout.write(gname+'\n');
			dout.flush();
			
			BufferedReader din = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			tmp = din.readLine();
			din.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		
		if (gpsw.equals(tmp)){
			Intent intent = new Intent(LoginActivity.this, LoginSuccess.class);
			uname = gname;
			startActivity(intent);
		}
		else{
			Toast.makeText(LoginActivity.this, "√‹¬Î¥ÌŒÛ", Toast.LENGTH_LONG).show();
		}
	}
	
	View.OnClickListener liListener  = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ename = (EditText)findViewById(R.id.etuname);
			gname = ename.getText().toString().trim();
			epsw = (EditText)findViewById(R.id.etpswd);
			gpsw = epsw.getText().toString().trim();
			
			if (gname.equals("")){
				Toast.makeText(LoginActivity.this, "«Î ‰»Î”√ªß√˚£°", Toast.LENGTH_LONG).show();
			}
			else{
				if (gpsw.equals("")){
					Toast.makeText(LoginActivity.this, "«Î ‰»Î√‹¬Î", Toast.LENGTH_LONG).show();
				}
				else{
					check();
				}
			}
			
		}
	};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Button enter=(Button)findViewById(R.id.enter);
        enter.setOnClickListener(liListener);
	}
	
}
