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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class inteam extends Activity {
	int [] ids ={
		R.id.etuname,
		R.id.etphone,
		R.id.etcname,
		R.id.etstime,
		R.id.etetime,
		R.id.etnmem,
		R.id.etsyrs,
	};
	EditText [] settext = new EditText[ids.length];
	String [] tmp = new String[ids.length];
	String tid;
	String uname = LoginSuccess.uname;
	
	public void getinfo (String tid){		
		for (int i=0; i<ids.length; i++){
			settext[i] = (EditText)findViewById(ids[i]);
		}
		
		try{
			Socket socket = new Socket("192.168.137.1", 8888);
			BufferedWriter dout = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream()));
			dout.write("@vteam\n");
			dout.write(tid+'\n');
			dout.flush();
			
			BufferedReader din = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK"));
			for (int i=0; i<ids.length; i++){
				tmp[i] = din.readLine();
			}
			din.close();
			socket.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		for (int i=0; i<ids.length; i++)
			settext[i].setText(tmp[i]);
	}
	
View.OnClickListener itListener  = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			try{
				Socket socket = new Socket("192.168.137.1", 8888);
				BufferedWriter dout = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream()));
				dout.write("@inteam\n");
				dout.write(tid+'\n'+uname+'\n');
				dout.flush();
				socket.close();
			} catch (Exception e){
				e.printStackTrace();
			}
			Toast.makeText(inteam.this, "加入成功", Toast.LENGTH_LONG).show();			
			
			Intent intent = new Intent(inteam.this, LoginSuccess.class);
			startActivity(intent);
		}
		
	};
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inteam);
        
        
        Intent get = getIntent();
        
        tid = get.getExtras().getString("id");
        
        getinfo(tid);
        
        Button enter=(Button)findViewById(R.id.inteam);
        enter.setOnClickListener(itListener);
    }
}