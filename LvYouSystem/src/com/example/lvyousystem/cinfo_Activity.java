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


public class cinfo_Activity extends Activity {
	int []textIds = {
			R.id.etuname,
			R.id.etpswd,
			R.id.etcfpswd,
			R.id.ettname,
			R.id.etage,
			R.id.etwork,
			R.id.etphone,
			R.id.etemail,
	};
	EditText [] textArray;
	
	public void updateinfo(String [] strArray){
		try {
			Socket socket = new Socket("192.168.137.1", 8888);
			BufferedWriter dout = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream(), "GBK"));
			String s = "@update\n"+strArray[0]+'\n'+strArray[1]+'\n'+strArray[3]+'\n'+strArray[4]+'\n'+strArray[5]+'\n'+strArray[6]+'\n'+strArray[7]+'\n'+"@end";
			dout.write(s);
			dout.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}
		Toast.makeText(cinfo_Activity.this, "修改成功", Toast.LENGTH_LONG).show();
	}
	
	View.OnClickListener myListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			String [] strArray = new String[textArray.length];
			for(int i=0;i<strArray.length;i++){
				strArray[i] = textArray[i].getText().toString().trim();
			}
			if(strArray[0].equals("")){
				Toast.makeText(cinfo_Activity.this, "请输入用户名!", Toast.LENGTH_LONG).show();
			}
			else{
				if(!strArray[1].equals(strArray[2])){
					Toast.makeText(cinfo_Activity.this, "两次输入密码不一致！", Toast.LENGTH_LONG).show();
				}
				else{
					updateinfo(strArray);
				}				
			}
		}
	};	

	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinfo);
        
        textArray = new EditText[textIds.length];
        for (int i=0; i<textIds.length; i++){
        	textArray[i] = (EditText)findViewById(textIds[i]);
        }
        
        Button sub=(Button)findViewById(R.id.submit);
        sub.setOnClickListener(myListener);

        for (EditText et:textArray){
        	et.getEditableText().clear();
        }        
    }

}
