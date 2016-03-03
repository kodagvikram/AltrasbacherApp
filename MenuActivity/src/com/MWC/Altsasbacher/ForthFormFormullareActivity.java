package com.MWC.Altsasbacher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import android.app.Activity;
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
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
public class ForthFormFormullareActivity extends Activity {

	private Button createPDF, openPDF;
	EditText edittext1,edittext2,edittext3,edittext4,edittext5,edittext6,edittext7,edittext8,edittext9,edittext10,edittext11;
	ArrayList<String> list = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forth_form_formullare);
		
		
		edittext1 = (EditText) findViewById(R.id.edittextname);
		edittext2 = (EditText) findViewById(R.id.edittextans);
		edittext3 = (EditText) findViewById(R.id.edittextbank);
		edittext4 = (EditText) findViewById(R.id.edittextiban);
		edittext5 = (EditText) findViewById(R.id.edittextZweck);
		edittext6 = (EditText) findViewById(R.id.edittextProjekt);
		edittext7 = (EditText) findViewById(R.id.edittextVorstand);
		
		
		Button Emptyprintbtn = (Button) findViewById(R.id.EnptyPDFbtn);
		Emptyprintbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CopyPdffromAccettosdcard();
				String path=Environment.getExternalStorageDirectory() + "/AltsasbacherPDF/"
						+"Antragauf.pdf";
				//PRINTPDF(path);
				Toast.makeText(ForthFormFormullareActivity.this, "PDF Datei gespeichert unter"+path, Toast.LENGTH_LONG).show();
				openPdf2();
			}
		});
		

		
		Button EdittedSavebtn = (Button) findViewById(R.id.SavePDFbtn);
		EdittedSavebtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				list.add(edittext1.getText().toString());
				list.add(edittext2.getText().toString());
				list.add(edittext3.getText().toString());
				list.add(edittext4.getText().toString());
				list.add(edittext5.getText().toString());
				list.add(edittext6.getText().toString());
				list.add(edittext7.getText().toString());
				//deletePDF();
				createPDF();
				openPdf();
				list.clear();
			}
		});

	}
	public void createPDF() {

		String FILE = Environment.getExternalStorageDirectory().toString()
				+ "/PDF/" + "Antragauf.pdf";

		// Add Permission into Manifest.xml
		// <uses-permission
		// android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

		// Create New Blank Document
		Document document = new Document(PageSize.A4);

		// Create Directory in External Storage
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/PDF");
		myDir.mkdirs();

		// Create Pdf Writer for Writting into New Created Document
		try {
			PdfWriter.getInstance(document, new FileOutputStream(FILE));

			// Open Document for Writting into document
			document.open();

			// User Define Method
			addMetaData(document);
			addTitlePage(document);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Close Document after writting all content
		document.close();

		Toast.makeText(this, "PDF Datei wurde generiert unter:" + FILE,
				Toast.LENGTH_LONG).show();
	}

	// Set PDF document Properties
	public void addMetaData(Document document)

	{
		document.addTitle("RESUME");
		document.addSubject("Person Info");
		document.addKeywords("Personal,	Education, Skills");
		document.addAuthor("TAG");
		document.addCreator("TAG");
	}

	public void addTitlePage(Document document) throws DocumentException {
		// Font Style for Document
		Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD
				| Font.UNDERLINE, BaseColor.GRAY);
		Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
		Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

		// Start New Paragraph
		Paragraph prHead = new Paragraph();
		// Set Font in this Paragraph
		prHead.setFont(titleFont);
		// Add item into Paragraph

		// Create Table into Document with 1 Row
		PdfPTable myTable = new PdfPTable(1);
		// 100.0f mean width of table is same as Document size
		myTable.setWidthPercentage(100.0f);

		// Create New Cell into Table
		PdfPCell myCell = new PdfPCell(new Paragraph());
		myCell.setBorder(Rectangle.BOTTOM);

		// Add Cell into Table
		myTable.addCell(myCell);

		prHead.setFont(catFont);
		prHead.add("");
		prHead.setAlignment(Element.ALIGN_CENTER);

		// Add all above details into Document
		document.add(prHead);
		// document.add(myTable);

		// document.add(myTable);
		String o="\u00F6";
		String u="\u00FC";
		String se="\u00EB";
		String a="\u00E4";
		
		String O="\u00D6";
		String U="\u00DC";
		String E="\u00CB";
		String A="\u00C4";

		Paragraph prPersinalInfonext = new Paragraph();
		prPersinalInfonext.setFont(smallBold);
		prPersinalInfonext
				.add("\n\n\n\n\n\n\n");
		prPersinalInfonext.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfonext);
		// document.add(myTable);
		
		
		Paragraph prPersinalInfo14 = new Paragraph();

		try {

			//document.open();

			Drawable d = getResources().getDrawable(R.drawable.logobw);

			BitmapDrawable bitDw = ((BitmapDrawable) d);

			Bitmap bmp = bitDw.getBitmap();

			ByteArrayOutputStream stream = new ByteArrayOutputStream();

			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

			Image image = Image.getInstance(stream.toByteArray());
			 image.setAlignment(Image.RIGHT);
			//image.setAbsolutePosition(360, 740);

			float documentWidth = document.getPageSize().getWidth()
					- document.leftMargin() - document.rightMargin();
			float documentHeight = document.getPageSize().getHeight()
					- document.topMargin() - document.bottomMargin();
			image.scaleToFit(documentWidth-400, documentHeight-150);

			Log.e("Doc - Image  = Height", document.getPageSize().getHeight()
					+ " - " + image.getScaledHeight());

			prPersinalInfo14.add(image);

			document.add(prPersinalInfo14);

			//document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		
		Paragraph prPersinalInfo = new Paragraph();
		prPersinalInfo.setFont(titleFont);
		// prPersinalInfo.setAlignment(Element.ALIGN_CENTER);
		prPersinalInfo.setAlignment(Element.ALIGN_MIDDLE);
		//((PdfContentByte) prPersinalInfo).moveText(10,20);
		PdfPTable table = new PdfPTable(1);
		Chunk chunk = new Chunk("\n\n\nAufwandspauschale\n\n Gerne erstatten wir Ihnen f"+u+"r Ihren ehrenamtlichen Einsatz an der  Heimschule Lender eine Aufwandspauschale von 40 EUR.\n\n Bitte helfen Sie uns bei einer unb"+u+"rokratischen Abwicklichung und f"+u+"llen das  Antragsformular aus, indem Sie die gelb unterlegten Felder erfassen.\n\n Mailen oder faxen Sie das Formular an:\n Wir werden Ihnen den Betrag auf Ihr angegebenes Konto "+u+"berweisen. Bitte haben Sie daf"+u+"r Verst"+a+"ndnis, das Barauszahlungen nicht m"+o+"glich sind. \n\n Vielen Dank f"+u+"r Ihr Engagement an unserer Schule!");
		Phrase severityChunk = new Phrase(chunk);
		PdfPCell cell = new PdfPCell(severityChunk);
		cell.setPadding(10);
		
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		prPersinalInfo.add(table);
		// Now Start another New Paragraph
		//table.addCell("\n\n\n\nAufwandspauschale\n\n Gerne erstatten wir Ihnen f�r Ihren ehrenamtlichen Einsatz an der  Heimschule Lender eine Aufwandspauschale von 40 EUR.\n\n Bitte helfen Sie uns bei einer unb�rokratischen Abwicklichung und f�llen das  Antragsformular aus, indem Sie die gelb unterlegten Felder erfassen.\n\n Mailen oder faxen Sie das Formular an:\n Wir werden Ihnen den Betrag auf Ihr angegebenes Konto �berweisen. Bitte haben Sie daf�r Verst�ndnis, das Barauszahlungen nicht m�glich sind. \n\n Vielen Dank f�r Ihr Engagement an unserer Schule!");
		document.add(table);
		
		
		document.newPage();		
		
		Paragraph prPersinalInfo15 = new Paragraph();

		try {

			//document.open();

			Drawable d = getResources().getDrawable(R.drawable.logobw);

			BitmapDrawable bitDw = ((BitmapDrawable) d);

			Bitmap bmp = bitDw.getBitmap();

			ByteArrayOutputStream stream = new ByteArrayOutputStream();

			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

			Image image2 = Image.getInstance(stream.toByteArray());
			 image2.setAlignment(Image.RIGHT);
			//image2.setAbsolutePosition(360, 450);

			float documentWidth = document.getPageSize().getWidth()
					- document.leftMargin() - document.rightMargin();
			float documentHeight = document.getPageSize().getHeight()
					- document.topMargin() - document.bottomMargin();
			image2.scaleToFit(documentWidth-400, documentHeight-150);

			Log.e("Doc - Image  = Height", document.getPageSize().getHeight()
					+ " - " + image2.getScaledHeight());

			prPersinalInfo15.add(image2);

			document.add(prPersinalInfo15);

			//document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		Paragraph prPersinalInfo1 = new Paragraph();
		prPersinalInfo1.setFont(smallBold);
		prPersinalInfo1
				.add("Vereinigung der Altsasbacher und F"+o+"rderverein");

		prPersinalInfo1.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo1);
		// document.add(myTable);
		Paragraph prPersinalInfo2 = new Paragraph();
		prPersinalInfo2.setFont(smallBold);
		prPersinalInfo2
				.add("\nAnsprechpartner:                                                 Gerd Sarcher");

		prPersinalInfo2
				.add("\nEmail:                                                                   gsarcher@t-online.de");

		prPersinalInfo2.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo2);
		// document.add(myTable);

		
		
		
		Paragraph prPersinalInfo3 = new Paragraph();
		prPersinalInfo3.setFont(smallBold);
		prPersinalInfo3
				.add("\n\n\nAntrag auf Fahrtkostenerstattung / Zuschuß\n\n Antragsteller");

		prPersinalInfo3.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo3);
		// document.add(myTable);

		
		Paragraph prPersinalInfo4 = new Paragraph();
		prPersinalInfo3.setFont(smallBold);
		prPersinalInfo4.add("\nName                                     :" + list.get(0));
		prPersinalInfo4.add("\n\nAnschrift                                 :" + list.get(1));
		prPersinalInfo4.add("\n\nBankverbindung                     :" + list.get(2));
		prPersinalInfo4.add("\n\nBank/IBAN/BIC                       :" + list.get(3));
		
		prPersinalInfo4.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo4);
		// document.add(myTable);
		
		
		
		
		Paragraph prPersinalInfo5 = new Paragraph();
		prPersinalInfo5.setFont(smallBold);
		prPersinalInfo5
				.add("\n\nAnlaß");

		prPersinalInfo5.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo5);
		// document.add(myTable);
		
		
		Paragraph prPersinalInfo6 = new Paragraph();
		prPersinalInfo3.setFont(smallBold);
		prPersinalInfo6.add("\nZweck der Reise                     :" + list.get(4));
		prPersinalInfo6.add("\n\nProjekt/Veranstaltung             :" + list.get(5)+"\n\n");
		prPersinalInfo6.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo6);
		// document.add(myTable);
		
		
		


		PdfPTable table7= new PdfPTable(2);

		table7.addCell("Auslagenpauschale / Fahrtkostenzuschuß ");
		table7.addCell("            Antrag");
		document.add(table7);
		
		
		PdfPTable table8 = new PdfPTable(2);

		table8.addCell("F"+u+"r das Projekt / f"+u+"r die Teilnahme an der Veranstaltung beantrage \nch eine Auslagenpauschale in H"+o+"he von");
		table8.addCell("            50,00 €");
		document.add(table8);
		//document.add(myTable);
		
		

		PdfPTable table9 = new PdfPTable(2);

		table9.addCell("Gesamtbetrag: 50,00 €");
		table9.addCell("            50,00 €");
		document.add(table9);

		Paragraph prPersinalInfo9 = new Paragraph();
		prPersinalInfo9.setFont(smallBold);
		//prPersinalInfo9.add("                                                                                                                                                           50,00 �");
		// prPersinalInfo9.add("\n\n\nGesamtbetrag:                                                                                                                                  50,00 �");

		prPersinalInfo9.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo9);
		
		Paragraph prPersinalInfo16 = new Paragraph();
		prPersinalInfo16.setFont(smallBold);
		prPersinalInfo16.add("\n\n\n\nWas Sie uns noch mitteilen wollen:");
	
		prPersinalInfo16.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo16);
		// document.add(myTable);
		
		Paragraph prPersinalInfo11 = new Paragraph();
		prPersinalInfo11.setFont(smallBold);
		prPersinalInfo11.add("\n\nEntscheid");
	
		prPersinalInfo11.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo11);
		// document.add(myTable);
		
		
		
		Paragraph prPersinalInfo12 = new Paragraph();
		prPersinalInfo11.setFont(smallBold);
		prPersinalInfo12.add("\n\nDer Antrag wird genehmigt / nicht genehmigt.\n Die Kosten in H"+o+"he von 50,-- € werden "+u+"bernommen.");
		prPersinalInfo12.add("\n\nSasbach, den                                                              Vorstand  :"+ list.get(6));
		prPersinalInfo12.add("\n                                                                                                      _______________");

		prPersinalInfo12.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo12);
		// document.add(myTable); 
		

		Paragraph prPersinalInfo13 = new Paragraph();
		prPersinalInfo13.setFont(smallBold);
		prPersinalInfo13.add("\n\nBuchhaltungsvermerk:");
	
		prPersinalInfo13.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo13);
		// document.add(myTable);
		

		
		
		// Create new Page in PDF
		document.newPage();
		
		
		
	}
	
	void openPdf2() {
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String path=Environment.getExternalStorageDirectory() + "/AltsasbacherPDF/";
		File file = new File(path, "Antragauf.pdf");

		intent.setDataAndType(Uri.fromFile(file), "application/pdf");
		startActivity(intent);
		
	}

	void openPdf() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/PDF";

		File file = new File(path, "Antragauf.pdf");

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
					Intent printIntent = new Intent(ForthFormFormullareActivity.this,
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
					if(files[i].toString().equals("Antragauf.pdf"))
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
