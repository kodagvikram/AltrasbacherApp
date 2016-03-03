package com.MWC.Altsasbacher;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class AlreadyMemberEmail extends Activity {

	EditText emailedt;
	Button close;
	public static final int DIALOG_DOWNLOAD_PROGRESS1 = 1;
	private ProgressDialog mProgressDialog;
	String responseString = "";
	public static String PINString = "";


	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_DOWNLOAD_PROGRESS1:
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage("Bitte warten..");
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();

			return mProgressDialog;

		default:
			return null;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.already_member_emailverify);

		generatePIN();
		close = (Button) findViewById(R.id.clearable_button_clear);
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				emailedt.setText("");

			}
		});

		emailedt = (EditText) findViewById(R.id.emailedt);
		emailedt.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView view, int actionId,
					KeyEvent event) {
				int result = actionId & EditorInfo.IME_MASK_ACTION;
				switch (result) {
				case EditorInfo.IME_ACTION_DONE:
					// done stuff
					final String email = emailedt.getText().toString();
					if (!isValidEmail(email)) {
						emailedt.setError("Ungültige E-Mail Adresse");
						emailedt.requestFocus();

					}
					if (emailedt.getText().toString().equals("")) {
						final AlertDialog alertDialog = new AlertDialog.Builder(
								AlreadyMemberEmail.this).create();
						alertDialog.setTitle("Achtung!");
						alertDialog.setMessage("Bitte geben Sie Ihre bei den Altsasbachern hinterlegte E-Mail Adresse in das Textfeld ein");
						alertDialog.setButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

										alertDialog.dismiss();
									}
								});
						alertDialog.show();
					} else if (isValidEmail(email)) {
						if(AppUtils.isNetworkAvailable(AlreadyMemberEmail.this))
						new myTask_newfoodpost_call().execute("");
						else
							AppUtils.ShowAlertDialog();
					}
					break;
				}
				return false;
			}

		});

	}

	class myTask_newfoodpost_call extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
		}

		protected String doInBackground(String... aurl) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;

			try {

				response = httpclient.execute(new HttpGet(
						"http://altsasbacher.de/PHP/reademail.php?addr="
								+ emailedt.getText().toString() + "&num="
								+ PINString));
				StatusLine statusLine = response.getStatusLine();

				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					responseString = out.toString();

					out.close();

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return null;

		}

		protected void onPostExecute(String str_resp) {

			if (responseString.equals("")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						AlreadyMemberEmail.this);
				builder.setMessage("Die angegebene E-Mail Adresse wurde nicht in unserem System gefunden. Bitte überprüfen Sie Ihre Eingabe und kontaktieren gegebenenfalls den Administrator.")
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
			} else if (responseString.equals("Mail Sent.")) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						AlreadyMemberEmail.this);
				builder.setMessage(
						"Die angegebene E-Mail Adresse wurde in unserem System gefunden")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// do things
										Intent i = new Intent(
												AlreadyMemberEmail.this,
												CodeGenertor.class);
										startActivity(i);
										dialog.dismiss();
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			}
			if (mProgressDialog != null)
				mProgressDialog.dismiss();
		}
	}

	public void generatePIN() {

		// generate a 4 digit integer 1000 <10000
		int randomPIN = (int) (Math.random() * 9000) + 1000;

		// Store integer in a string
		PINString = String.valueOf(randomPIN);

	}

	// validating email id
	private boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
