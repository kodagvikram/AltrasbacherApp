package com.MWC.Altsasbacher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WantMember extends Activity {

	Button openpdf,sendpdf,print;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.want_member);
		
		String o="\u00F6";
		String u="\u00FC";
		String se="\u00EB";
		String a="\u00E4";
		
		String O="\u00D6";
		String U="\u00DC";
		String E="\u00CB";
		String A="\u00C4";
		
		TextView textView1=(TextView)findViewById(R.id.beitritext);
		textView1.setText("Beitrittserkl"+a+"rung");
		
		TextView textView2=(TextView)findViewById(R.id.beitritdescription);
		textView2.setText("Vielen Dank f"+u+"r Ihr Interesse. Wie m"+o+"chten\n Sie Ihre Beitrittserkl"+a+"rung einreichen?");
		
		openpdf = (Button)findViewById(R.id.pdfeditor);
		openpdf.setText("In der App ausf"+u+"llen");
		sendpdf = (Button)findViewById(R.id.pdfviaemail);
		print = (Button)findViewById(R.id.print);
		
		openpdf.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					
				
			Intent intent=new Intent(WantMember.this, FirstrFormFormulareActivity.class);
			startActivity(intent);
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			}
		});
		
		print.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					
				CopyPdffromAccettosdcard();
				String path=Environment.getExternalStorageDirectory() + "/AltsasbacherPDF/"
						+"Beitrittseklarung.pdf";
				PRINTPDF(path);
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			}
		});
		
		sendpdf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SendEmail();
			}
		});
		
	}//end of oncreate()
	
	public void SendEmail()
	{
		try {
			
		
		CopyPdffromAccettosdcard();
		String path=Environment.getExternalStorageDirectory() + "/AltsasbacherPDF/"
				+"Beitrittseklarung.pdf";
                      String[] mailto = {""};
                        Uri uri = Uri.fromFile(new File(path));
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, mailto);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                        //emailIntent.putExtra(Intent.EXTRA_TEXT, ViewAllAccountFragment.selectac+" PDF Report");
                        emailIntent.setType("application/pdf");
                        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        startActivity(Intent.createChooser(emailIntent, "Welchen Dienst möchten Sie nutzen?"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		}//end of send email
	
	public void PRINTPDF(String Path)
	{
		try {
			if (!AppUtils.isNetworkAvailable(this)) {
		          AppUtils.ShowAlertDialog();
			  } else {
				  
				  File file = new File(Path);
					Intent printIntent = new Intent(WantMember.this,
							PrintDialogActivity.class);
					printIntent.setDataAndType(Uri.fromFile(file),
							"application/pdf");
					printIntent.putExtra("title", "Android print demo");
					startActivity(printIntent);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}//end of printPDF
	

	public void CopyPdffromAccettosdcard()
	{
		try {
			AssetManager assetFiles = getAssets();
			// MyHtmlFiles is the name of folder from inside our assets folder
			String[] files = assetFiles.list("AltsasbacherPDF");
			// Initialize streams
			InputStream in = null;
			OutputStream out = null;

			for (int i = 0; i < files.length; i++) {

				if (files[i].toString().equalsIgnoreCase("images")
						|| files[i].toString().equalsIgnoreCase("js")) {

				} else {
					
					if(files[i].toString().equals("Beitrittseklarung.pdf"))
					{
						in = assetFiles.open("AltsasbacherPDF/" + files[i]);
						String root = Environment.getExternalStorageDirectory().toString();
						File myDir = new File(root + "/AltsasbacherPDF");
						myDir.mkdirs();
						
						out = new FileOutputStream(
								Environment.getExternalStorageDirectory() + "/AltsasbacherPDF/"
										+ files[i]);
						copyAssetFiles(in, out);
					}

					

				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}//end of copy pdf from asset to sdcard
	
	private static void copyAssetFiles(InputStream in, OutputStream out) {
		try {

			byte[] buffer = new byte[FirstrFormFormulareActivity.BUFFER_SIZE];
			int read;

			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}

			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}//end of Activity
