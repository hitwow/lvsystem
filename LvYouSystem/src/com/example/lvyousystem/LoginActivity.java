package com.example.lvyousystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Button enter=(Button)findViewById(R.id.enter);
        enter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent4 = new Intent(LoginActivity.this, LoginSuccess.class);
				startActivity(intent4);
			}
        });
	}
}
