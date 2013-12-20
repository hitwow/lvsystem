package com.example.lvyousystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class LoginSuccess extends Activity {
	public static String uname = LoginActivity.uname;
	 
	View.OnClickListener infols = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(LoginSuccess.this, info_Activity.class);
			startActivity(intent);			
		}
	};
	
	View.OnClickListener etmap = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(LoginSuccess.this, MapActivity.class);
			startActivity(intent);
			
		}
	};
	
	View.OnClickListener infoc = new View.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(LoginSuccess.this, cinfo_Activity.class);
			startActivity(intent);	
		}		
	};
	
	View.OnClickListener creteam = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(LoginSuccess.this, cteam_Activity.class);
			startActivity(intent);			
		}
	};
	
	View.OnClickListener scteam = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {	
			Intent intent = new Intent(LoginSuccess.this, scteam_Activity.class);
			startActivity(intent);
		}
	};
	
	
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loginsuc);
		
		Button vinfo=(Button)findViewById(R.id.vself);
        vinfo.setOnClickListener(infols);
        
        Button enmap=(Button)findViewById(R.id.inmap);
        enmap.setOnClickListener(etmap);
        
        Button cinfo=(Button)findViewById(R.id.cself);
        cinfo.setOnClickListener(infoc);
        
        Button cteam=(Button)findViewById(R.id.cteam);
        cteam.setOnClickListener(creteam);
        
        Button steam=(Button)findViewById(R.id.steam);
        steam.setOnClickListener(scteam);
	}
}
