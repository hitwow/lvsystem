package com.example.lvyousystem;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.widget.EditText;


public class info_Activity extends Activity {
	int [] ids ={
		R.id.etuname,
		R.id.ettname,
		R.id.etage,
		R.id.etwork,
		R.id.etphone,
		R.id.etemail,
	};
	String uname = LoginSuccess.uname;
	
	public void getinfo (){
		EditText [] settext = new EditText[6];
		String [] tmp = new String[6];
		
		for (int i=0; i<6; i++){
			settext[i] = (EditText)findViewById(ids[i]);
		}
		
		try{
			Socket socket = new Socket("192.168.137.1", 8888);
			BufferedWriter dout = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream()));
			dout.write("@vinfo\n");
			dout.write(uname+'\n');
			dout.flush();
			
			BufferedReader din = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK"));
			for (int i=0; i<6; i++){
				tmp[i] = din.readLine();
			}
			din.close();	
		} catch (Exception e){
			e.printStackTrace();
		}
		
		for (int i=0; i<6; i++)
			settext[i].setText(tmp[i]);
	}
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getinfo();        
    }
}
