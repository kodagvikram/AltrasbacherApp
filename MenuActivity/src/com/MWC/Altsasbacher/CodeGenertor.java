package com.MWC.Altsasbacher;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class CodeGenertor extends Activity {

	EditText codeedt;
	public SharedPreferences sharedPrefs;
	public Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.code_generate);

		String o="\u00F6";
		String u="\u00FC";
		String se="\u00EB";
		String a="\u00E4";
		
		String O="\u00D6";
		String U="\u00DC";
		String E="\u00CB";
		String A="\u00C4";
		
		TextView textView1=(TextView)findViewById(R.id.codetitletext);
		textView1.setText("Code best"+a+"tigen");
		
		sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(CodeGenertor.this);
		editor = sharedPrefs.edit();
		
		codeedt = (EditText) findViewById(R.id.codeedt);

		codeedt.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView view, int actionId,
					KeyEvent event) {
				int result = actionId & EditorInfo.IME_MASK_ACTION;
				switch (result) {
				case EditorInfo.IME_ACTION_DONE:
					// done stuff
					try {
						final String Code = codeedt.getText().toString();
						if (Code.equals(AlreadyMemberEmail.PINString)) {
							editor = sharedPrefs.edit();
								editor.putString("VALLIDUSER","valid");
							editor.commit();
							Intent intent=new Intent(CodeGenertor.this, MenuActivity.class);
							startActivity(intent);
							finish();
						}
						else
						{
							editor = sharedPrefs.edit();
							editor.putString("VALLIDUSER","valid");
						   editor.commit();
							AlertDialog.Builder builder = new AlertDialog.Builder(
									CodeGenertor.this);
							builder.setMessage(
									"Der eingegebene Code ist leider falsch. Bitte überprüfen Sie Ihre Eingabe.")
									.setCancelable(false)
									.setPositiveButton("OK",
											new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,
														int id) {
													// do things
													dialog.dismiss();
												}
											});
							AlertDialog alert = builder.create();
							alert.show();
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

					break;
				}
				return false;
			}

		});

	}

}
