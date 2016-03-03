package com.MWC.Altsasbacher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
public class FifthFormFormulareActivity extends Activity {

	private Button createPDF, openPDF;
	EditText edittext, edittextname, edittextstrab, edittextort,
			edittexttelefon, edittextemail, edittext7, edittext8, edittext10,
			edittext11, edittext13;
	ArrayList<String> list = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fifth_form_formulare);
		
		Button Emptyprintbtn = (Button) findViewById(R.id.EnptyPDFbtn);
		Emptyprintbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CopyPdffromAccettosdcard();
				String path=Environment.getExternalStorageDirectory() + "/AltsasbacherPDF/"
						+"CommoveamusSchulerinfo.pdf";
				//PRINTPDF(path);
				Toast.makeText(FifthFormFormulareActivity.this, "PDF Datei gespeichert unter"+path, Toast.LENGTH_LONG).show();
				openPdf2();
			}
		});
		
		Button EdittedPrintbtn = (Button) findViewById(R.id.EditedPDFbtn);
		EdittedPrintbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		
		Button EdittedSavebtn = (Button) findViewById(R.id.SavePDFbtn);
		EdittedSavebtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CopyPdffromAccettosdcard();
				String path=Environment.getExternalStorageDirectory() + "/AltsasbacherPDF/"
						+"CommoveamusSchulerinfo.pdf";
				Toast.makeText(FifthFormFormulareActivity.this, "PDF Datei gespeichert unter"+path, Toast.LENGTH_LONG).show();
				openPdf();
			}
		});

		
	}//end of oncreate
	
	void openPdf2() {
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String path=Environment.getExternalStorageDirectory() + "/AltsasbacherPDF/";
		File file = new File(path, "CommoveamusSchulerinfo.pdf");

		intent.setDataAndType(Uri.fromFile(file), "application/pdf");
		startActivity(intent);
		
	}
	
	void openPdf() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String path=Environment.getExternalStorageDirectory() + "/AltsasbacherPDF/";
		File file = new File(path, "CommoveamusSchulerinfo.pdf");

		intent.setDataAndType(Uri.fromFile(file), "application/pdf");
		startActivity(intent);
	}
	public void PRINTPDF(String Path)
	{
		try {
			if (!AppUtils.isNetworkAvailable(this)) {
		          AppUtils.ShowAlertDialog();
			  } else {
				  
				  File file = new File(Path);
					Intent printIntent = new Intent(FifthFormFormulareActivity.this,
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
					if(files[i].toString().equals("CommoveamusSchulerinfo.pdf"))
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
}
