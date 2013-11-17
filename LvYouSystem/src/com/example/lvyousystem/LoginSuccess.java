package com.example.lvyousystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginSuccess extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loginsuc);
		Button search=(Button)findViewById(R.id.search);
        search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent(LoginSuccess.this, Searchres.class);
				startActivity(intent1);
			}
        });
        
        Button inmap=(Button)findViewById(R.id.inmap);
        inmap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent2 = new Intent(LoginSuccess.this, MapActivity.class);
				startActivity(intent2);
			}
        });
	}
}
