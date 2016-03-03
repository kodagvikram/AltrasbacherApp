package com.MWC.Altsasbacher;

import org.w3c.dom.Text;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AppFirstPage extends Activity {

	Button alreadymember, wanttomember;
	public SharedPreferences sharedPrefs;
	public Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appfirstpage);
		sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(AppFirstPage.this);
		editor = sharedPrefs.edit();
		// Button
		String o="\u00F6";
		String u="\u00FC";
		String se="\u00EB";
		String a="\u00E4";
		
		String O="\u00D6";
		String U="\u00DC";
		String E="\u00CB";
		String A="\u00C4";
		
		
		if (sharedPrefs.getString("VALLIDUSER","").equalsIgnoreCase("valid"))
		{
			try {
				
			
			Intent intent=new Intent(AppFirstPage.this, MenuActivity.class);
			startActivity(intent);
			finish();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		
		alreadymember = (Button) findViewById(R.id.alreadymember);
		wanttomember = (Button) findViewById(R.id.wantmember);
	   Button btn=(Button)findViewById(R.id.email);

	   wanttomember.setText("Ich m"+o+"chte Mitglied werden");
	   
		alreadymember.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i =new Intent(AppFirstPage.this, AlreadyMemberEmail.class);
				startActivity(i);
			}
		});

		wanttomember.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i =new Intent(AppFirstPage.this, WantMember.class);
				startActivity(i);

			}
		});
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gmailintent();
			}
		});

	}//end of oncreate
	
	public void gmailintent() {

		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL, new String[] { "info@altsasbacher.de" });
		i.putExtra(Intent.EXTRA_SUBJECT, " ");
		i.putExtra(Intent.EXTRA_TEXT, "Send from android mobile ");

		try {
			this.startActivity(Intent.createChooser(i, "Wählen Sie einen E-Mail Dienst"));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(this, "Es ist keine E-Mail Software installiert.",
					Toast.LENGTH_SHORT).show();
		}

	}// end of gmailintent
}//end of Activity
