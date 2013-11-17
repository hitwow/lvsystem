package com.example.lvyousystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SupSuccess extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_supsuc);
		Button ret=(Button)findViewById(R.id.ret);
        ret.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent3 = new Intent(SupSuccess.this, MainActivity.class);
				startActivity(intent3);
			}
        });
	}
}
