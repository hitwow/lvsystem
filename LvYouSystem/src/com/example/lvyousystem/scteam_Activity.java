package com.example.lvyousystem;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class scteam_Activity extends Activity {
	int []textIds = {
			R.id.etcname,
			R.id.etstime,
			R.id.etetime,
			R.id.etnmem,
	};
	EditText [] textArray;
	String uname = LoginSuccess.uname;
	
	public void searchteam(String [] strArray){		
		try {
			Socket socket = new Socket("192.168.137.1", 8888);
			BufferedWriter dout = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream(), "GBK"));
			String s = "@scteam\n"+strArray[0]+'\n'+strArray[1]+'\n'+strArray[2]+'\n'+strArray[3]+'\n'+"@end\n";
			dout.write(s);
			dout.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		Intent intent = new Intent(scteam_Activity.this, sresult_Activity.class);
		startActivity(intent);
	}
	
	View.OnClickListener myListener = new View.OnClickListener() {
		public void onClick(View v) {
			String [] strArray = new String[textArray.length];
			for(int i=0;i<strArray.length;i++){
				strArray[i] = textArray[i].getText().toString().trim();
			}
			if(strArray[0].equals("")||strArray[1].equals("")||strArray[2].equals("")||strArray[3].equals("")){
				Toast.makeText(scteam_Activity.this, "信息不完整，请继续输入", Toast.LENGTH_LONG).show();
			}
			else{				
				searchteam(strArray);
			}
		}
	};
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steam);
        
        textArray = new EditText[textIds.length];
        for (int i=0; i<textIds.length; i++){
        	textArray[i] = (EditText)findViewById(textIds[i]);
        }
        
        Button sct = (Button)findViewById(R.id.search);
        sct.setOnClickListener(myListener);
        
        for (EditText et:textArray){
        	et.getEditableText().clear();
        }
	}
}
