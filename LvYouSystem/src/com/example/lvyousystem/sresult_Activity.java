package com.example.lvyousystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class sresult_Activity extends Activity {
	ListView lv;
	String [] uname;
	String [] cname;
	String [] tid;
	
	BaseAdapter myAdapter = new BaseAdapter() {
		public int getCount() {
			if (uname != null) {
				return uname.length;
			}
			else{
				return 0;
			}
		}
		
		public Object getItem(int arg0) {
			return null;
		}
		
		public long getItemId(int arg0) {
			return 0;
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout ll = new LinearLayout(sresult_Activity.this);
			ll.setOrientation(LinearLayout.HORIZONTAL);
			
			TextView tv = new TextView(sresult_Activity.this);
			tv.setText(cname[position]);
			tv.setTextSize(32);
			tv.setTextColor(Color.BLACK);
			tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			tv.setGravity(Gravity.CENTER_VERTICAL);
			
			TextView tv2 = new TextView(sresult_Activity.this);
			tv.setText("["+uname[position]+"]");
			tv2.setTextSize(28);
			tv2.setTextColor(Color.BLACK);
			tv2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
			tv2.setGravity(Gravity.BOTTOM|Gravity.RIGHT);
			
			ll.addView(tv);
			ll.addView(tv2);
			return ll;
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sresult);
        
        
        lv = (ListView)findViewById(R.id.lv);
        lv.setAdapter(myAdapter);
        lv.setOnItemClickListener(new OnItemClickListener(){
        	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {  
        		Intent intent = new Intent(sresult_Activity.this, inteam.class);
        		intent.putExtra("id", tid[position]);
        		startActivity(intent);
        	}
        });
    }

	protected void onResume() {
		getBasicInfo();
		myAdapter.notifyDataSetChanged();
		super.onResume();
	}
	
	public void getBasicInfo() {
		String tmp;
		int count;
		
		try{
			Socket socket = new Socket("192.168.137.1", 8888);
			BufferedWriter dout = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream(), "GBK"));
			String s = "@sresult\n"+"@end\n";
			dout.write(s);
			dout.flush();
			
			BufferedReader din = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK"));
			tmp = din.readLine();
			count = Integer.valueOf(tmp).intValue();
			uname = new String[count];
			cname = new String[count];
			tid = new String[count];
			
			for (int i=0; i<count; i++){
				tid[i] = din.readLine();
				uname[i] = din.readLine();
				cname[i] = din.readLine();
			}
			din.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
