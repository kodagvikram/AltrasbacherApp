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

public class ThirdFormFormulareActivity extends Activity {
	private Button createPDF, openPDF;
	EditText edittext1, edittext2, edittext3, edittext4, edittext5, edittext6,
			edittext7, edittext8, edittext9, edittext10, edittext11,
			edittext12, edittext13, edittext14, edittext15, edittext16,
			edittext17, edittext18, edittext19, edittext20, edittext21,
			edittext22, edittext23, edittext24, edittext25, edittext26,
			edittext27, edittext28, edittext29, edittext30, edittext31,
			edittext32, edittext33, edittext34, edittext35, edittext36,
			edittext37, edittext38, edittext39, edittext40, edittext41,
			edittext42, edittext43, edittext44, edittext45;;

	ArrayList<String> list = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third_form_formulare);

		edittext1 = (EditText) findViewById(R.id.edittextname);
		edittext2 = (EditText) findViewById(R.id.edittextGeburtsdatum);
		edittext3 = (EditText) findViewById(R.id.edittextAnschrift);
		edittext4 = (EditText) findViewById(R.id.edittextKlasse);
		edittext5 = (EditText) findViewById(R.id.edittextEmail1);
		edittext6 = (EditText) findViewById(R.id.edittextTelefon);

		edittext7 = (EditText) findViewById(R.id.edittextname1);
		edittext8 = (EditText) findViewById(R.id.edittextGeburtsdatum1);
		edittext9 = (EditText) findViewById(R.id.edittextAnschrift1);
		edittext10 = (EditText) findViewById(R.id.edittextKlasse1);
		edittext11 = (EditText) findViewById(R.id.edittextEmail11);
		edittext12 = (EditText) findViewById(R.id.edittextTelefon1);

		edittext13 = (EditText) findViewById(R.id.edittextname2);
		edittext14 = (EditText) findViewById(R.id.edittextGeburtsdatum2);
		edittext15 = (EditText) findViewById(R.id.edittextAnschrift2);
		edittext16 = (EditText) findViewById(R.id.edittextKlasse2);
		edittext17 = (EditText) findViewById(R.id.edittextEmail2);
		edittext18 = (EditText) findViewById(R.id.edittextTelefon2);

		edittext19 = (EditText) findViewById(R.id.edittextViewheader5);
		edittext20 = (EditText) findViewById(R.id.edittextViewheader6);

		edittext21 = (EditText) findViewById(R.id.edittextViewheader7);
		edittext22 = (EditText) findViewById(R.id.edittextViewheader8);
		edittext23 = (EditText) findViewById(R.id.edittextViewheader9);
		edittext24 = (EditText) findViewById(R.id.edittextViewheader10);

		edittext25 = (EditText) findViewById(R.id.edittextViewAusgaben);
		edittext26 = (EditText) findViewById(R.id.edittextViewFahrtkosten);
		edittext27 = (EditText) findViewById(R.id.edittextViewVerpflegung);
		edittext28 = (EditText) findViewById(R.id.edittextViewUnterkunft);
		edittext29 = (EditText) findViewById(R.id.edittextViewSonstiges);
		edittext30 = (EditText) findViewById(R.id.edittextViewSummeAusgaben);
		edittext31 = (EditText) findViewById(R.id.edittextViewZuschusse);
		edittext32 = (EditText) findViewById(R.id.edittextViewTeilnehmerbeitrage);
		edittext33 = (EditText) findViewById(R.id.edittextViewSpenden);
		edittext34 = (EditText) findViewById(R.id.edittextViewSonstiges1);
		edittext35 = (EditText) findViewById(R.id.edittextViewSummeAusgaben1);
		edittext36 = (EditText) findViewById(R.id.edittextViewheader13);
		edittext37 = (EditText) findViewById(R.id.edittextViewheader14);
		edittext38 = (EditText) findViewById(R.id.edittextort1);
		edittext39 = (EditText) findViewById(R.id.edittextort2);
		edittext40 = (EditText) findViewById(R.id.edittextort3);
		edittext41 = (EditText) findViewById(R.id.edittextua1);
		edittext42 = (EditText) findViewById(R.id.edittextua2);
		edittext43 = (EditText) findViewById(R.id.edittextua3);


		Button Emptyprintbtn = (Button) findViewById(R.id.EnptyPDFbtn);
		Emptyprintbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CopyPdffromAccettosdcard();
				String path=Environment.getExternalStorageDirectory() + "/AltsasbacherPDF/"
						+"Commoveamusforderantrag.pdf";
				//PRINTPDF(path);
				Toast.makeText(ThirdFormFormulareActivity.this, "PDF Datei gespeichert unter"+path, Toast.LENGTH_LONG).show();
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
				list.add(edittext8.getText().toString());
				list.add(edittext9.getText().toString());
				list.add(edittext10.getText().toString());
				list.add(edittext11.getText().toString());
				list.add(edittext12.getText().toString());
				list.add(edittext13.getText().toString());
				list.add(edittext14.getText().toString());
				list.add(edittext15.getText().toString());
				list.add(edittext16.getText().toString());
				list.add(edittext17.getText().toString());
				list.add(edittext18.getText().toString());
				list.add(edittext19.getText().toString());
				list.add(edittext20.getText().toString());
				list.add(edittext21.getText().toString());
				list.add(edittext22.getText().toString());
				list.add(edittext23.getText().toString());
				list.add(edittext24.getText().toString());
				list.add(edittext25.getText().toString());
				list.add(edittext26.getText().toString());
				list.add(edittext27.getText().toString());
				list.add(edittext28.getText().toString());
				list.add(edittext29.getText().toString());
				list.add(edittext30.getText().toString());
				list.add(edittext31.getText().toString());
				list.add(edittext32.getText().toString());
				list.add(edittext33.getText().toString());
				list.add(edittext34.getText().toString());
				list.add(edittext35.getText().toString());
				list.add(edittext36.getText().toString());
				list.add(edittext37.getText().toString());
				list.add(edittext38.getText().toString());
				list.add(edittext39.getText().toString());
				list.add(edittext40.getText().toString());
				list.add(edittext41.getText().toString());
				list.add(edittext42.getText().toString());
				list.add(edittext43.getText().toString());

				//deletePDF();
				createPDF();
				openPdf();
				list.clear();
			}
		});
		
	}

	public void createPDF() {

		String FILE = Environment.getExternalStorageDirectory().toString()
				+ "/PDF/" + "Commoveamusforderantrag.pdf";

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
		Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD
				| Font.UNDERLINE, BaseColor.GRAY);
		Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
		Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

		// Start New Paragraph
		Paragraph prHead = new Paragraph();
		// Set Font in this Paragraph
		prHead.setFont(titleFont);
		// Add item into Paragraph
		prHead.add("");

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

		
		Paragraph prPersinalInfo13 = new Paragraph();

		try {
			

			//document.open();

			Drawable d = getResources().getDrawable(R.drawable.commoveamus);

			BitmapDrawable bitDw = ((BitmapDrawable) d);

			Bitmap bmp = bitDw.getBitmap();

			ByteArrayOutputStream stream = new ByteArrayOutputStream();

			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

			Image image = Image.getInstance(stream.toByteArray());
			 image.setAlignment(Image.RIGHT);
			//image.setAbsolutePosition(340, 750);

			float documentWidth = document.getPageSize().getWidth()
					- document.leftMargin() - document.rightMargin();
			float documentHeight = document.getPageSize().getHeight()
					- document.topMargin() - document.bottomMargin();
			image.scaleToFit(documentWidth-400, documentHeight-150);

			Log.e("Doc - Image  = Height", document.getPageSize().getHeight()
					+ " - " + image.getScaledHeight());
			//prPersinalInfo13.setAlignment(Element.ALIGN_RIGHT);
			prPersinalInfo13.setAlignment(Element.ALIGN_TOP);
			
			prPersinalInfo13.add(image);

			document.add(prPersinalInfo13);

			//document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String o="\u00F6";
		String u="\u00FC";
		String se="\u00EB";
		String a="\u00E4";
		
		String O="\u00D6";
		String U="\u00DC";
		String E="\u00CB";
		String A="\u00C4";
		// Now Start another New Paragraph
		Paragraph prPersinalInfo = new Paragraph();
		prPersinalInfo.setFont(smallBold);
		prPersinalInfo
				.add("\n\n\nAntragsformular COMMOVEAMUS\nEin Initiativfonds des Altsasbachervereins\n\nI. Antragsteller  ");

		prPersinalInfo.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo);
		// document.add(myTable);

		Paragraph prPersinalInfodetail = new Paragraph();
		prPersinalInfodetail.setFont(smallBold);
		prPersinalInfodetail.add("\n1.");
		prPersinalInfodetail.add("\n Name : " + list.get(0));
		prPersinalInfodetail
				.add("                                               Geburtsdatum  : "
						+ list.get(1));
		prPersinalInfodetail
				.add("\n               ______________________                                                 ____________________________");

		prPersinalInfodetail.add("\n\n Anschrift  : " + list.get(2));
		prPersinalInfodetail
				.add("                                                Klasse  : "
						+ list.get(3));
		prPersinalInfodetail
				.add("\n                    ______________________                                              ____________________________");

		prPersinalInfodetail.add("\n\n Email   : " + list.get(4));
		prPersinalInfodetail
				.add("                                                Telefon  : "
						+ list.get(5));
		prPersinalInfodetail
				.add("\n                 ______________________                                               ____________________________");

		prPersinalInfodetail.setAlignment(Element.ALIGN_BASELINE);

		document.add(prPersinalInfodetail);
		// document.add(myTable);

		Paragraph prPersinalInfodetail1 = new Paragraph();
		prPersinalInfodetail1.setFont(smallBold);
		prPersinalInfodetail1.add("\n\n2.");
		prPersinalInfodetail1.add("\n Name : " + list.get(6));
		prPersinalInfodetail1
				.add("                                               Geburtsdatum  : "
						+ list.get(7));
		prPersinalInfodetail1
				.add("\n               ______________________                                                 ____________________________");

		prPersinalInfodetail1.add("\n\n Anschrift  : " + list.get(8));
		prPersinalInfodetail1
				.add("                                                Klasse  : "
						+ list.get(9));
		prPersinalInfodetail1
				.add("\n                    ______________________                                              ____________________________");

		prPersinalInfodetail1.add("\n\n Email   : " + list.get(10));
		prPersinalInfodetail1
				.add("                                                Telefon  : "
						+ list.get(11));
		prPersinalInfodetail1
				.add("\n                 ______________________                                               ____________________________");

		prPersinalInfodetail1.setAlignment(Element.ALIGN_BASELINE);

		document.add(prPersinalInfodetail1);
		// document.add(myTable);

		Paragraph prPersinalInfodetail2 = new Paragraph();
		prPersinalInfodetail2.setFont(smallBold);
		prPersinalInfodetail2.add("\n\n3.");
		prPersinalInfodetail2.add("\n Name : " + list.get(12));
		prPersinalInfodetail2
				.add("                                               Geburtsdatum  : "
						+ list.get(13));
		prPersinalInfodetail2
				.add("\n               ______________________                                                 ____________________________");

		prPersinalInfodetail2.add("\n\n Anschrift  : " + list.get(14));
		prPersinalInfodetail2
				.add("                                                Klasse  : "
						+ list.get(15));
		prPersinalInfodetail2
				.add("\n                    ______________________                                              ____________________________");

		prPersinalInfodetail2.add("\n\n Email   : " + list.get(16));
		prPersinalInfodetail2
				.add("                                                Telefon  : "
						+ list.get(17));
		prPersinalInfodetail2
				.add("\n                 ______________________                                               ____________________________");

		prPersinalInfodetail2.setAlignment(Element.ALIGN_BASELINE);

		document.add(prPersinalInfodetail2);
		// document.add(myTable);

		Paragraph prPersinalInfo1 = new Paragraph();
		prPersinalInfo1.setFont(smallBold);
		prPersinalInfo1
				.add("\n\n(Bei weiteren Antragstellern entsprechende Daten bitte auf separatem Blatt beif"+u+"gen!)\n\n");

		prPersinalInfo1.setAlignment(Element.ALIGN_CENTER);

		document.add(prPersinalInfo1);

		Paragraph prPersinalInfo2 = new Paragraph();
		prPersinalInfo2.setFont(smallBold);
		prPersinalInfo2.add("\n\nII. Beschreibung des Projekts:\n\n\n"
				+ list.get(18));
		document.add(myTable);

		prPersinalInfo2.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo2);

		Paragraph prPersinalInfo3 = new Paragraph();
		prPersinalInfo3.setFont(smallBold);
		prPersinalInfo3
				.add("\n\n\nIII. Motivation f"+u+"r das Projekt \n Was motiviert dich/euch pers"+o+"nlich, dieses Projekt durchzuf"+u+"hren?\n"
						+ list.get(19) + "\n\n");
		document.add(myTable);

		prPersinalInfo3.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo3);

		Paragraph prPersinalInfo4 = new Paragraph();
		prPersinalInfo4.setFont(smallBold);
		prPersinalInfo4
				.add("\nWelche Ziele soll das Projekt haben? Welche Wirkung willst du mit dem Projekt erzielen? \n"
						+ list.get(20) + "\n\n");
		document.add(myTable);

		prPersinalInfo4.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo4);

		Paragraph prPersinalInfo5 = new Paragraph();
		prPersinalInfo5.setFont(smallBold);
		prPersinalInfo5
				.add("\nWas erwartest du von dem Projekt f"+u+"r dich und andere? \n"
						+ list.get(21) + "\n\n");
		document.add(myTable);

		prPersinalInfo5.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo5);

		Paragraph prPersinalInfo6 = new Paragraph();
		prPersinalInfo6.setFont(smallBold);
		prPersinalInfo6
				.add("\n\nIV.  Durchf"+u+"hrung (ungef"+a+"hrer zeitlicher Ablauf: Start, Abschluss, ...)  \n"
						+ list.get(22) + "\n\n");
		document.add(myTable);

		prPersinalInfo6.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo6);

		Paragraph prPersinalInfo7 = new Paragraph();
		prPersinalInfo7.setFont(smallBold);
		prPersinalInfo7
				.add("\n\nV.  Wie m"+o+"chtest du das Projekt (w"+a+"hrend oder nach der Durchf"+u+"hrung) an der Heimschule Lender pr"+a+"sentieren? \n"
						+ list.get(23) + "\n\n");
		document.add(myTable);

		prPersinalInfo7.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo7);

		Paragraph prPersinalInfo8 = new Paragraph();
		prPersinalInfo8.setFont(smallBold);
		prPersinalInfo8.add("\n\nVI. Finanzielle Planung   \n");
		prPersinalInfo8
				.add("\nAusgaben                                                                     evtl. Einnahmen\n\n ");
		prPersinalInfo8.add("\nMaterial :" + list.get(24));
		prPersinalInfo8
				.add("                                                                     Zusch"+u+"sse :"
						+ list.get(30));
		prPersinalInfo8
				.add("\n                           ______________                                                                    ___________________");
		prPersinalInfo8.add("\nFahrtkosten :" + list.get(25));
		prPersinalInfo8
				.add("                                                               Teilnehmerbeitr"+a+"ge  :"
						+ list.get(31));
		prPersinalInfo8
				.add("\n                           ______________                                                                    ___________________");

		prPersinalInfo8.add("\nVerpflegung : " + list.get(26));
		prPersinalInfo8
				.add("                                                               Spenden  :"
						+ list.get(32));
		prPersinalInfo8
				.add("\n                           ______________                                                                    ___________________");

		prPersinalInfo8.add("\nUnterkunft :  " + list.get(27));
		prPersinalInfo8
				.add("                                                               Sonstiges  :  "
						+ list.get(33));
		prPersinalInfo8
				.add("\n                           ______________                                                                    ___________________");

		prPersinalInfo8.add("\nSonstiges  :  " + list.get(28));
		prPersinalInfo8.add("\n                           _______________");
		prPersinalInfo8.add("\n\nSumme Ausgaben   :  " + list.get(29));
		prPersinalInfo8
				.add("\n                                         _______________");

		prPersinalInfo8
				.add("\n\n\n\nH"+o+"he des beantragten F"+o+"rderbetrages (Ausgaben minus evtl. Einnahmen): "
						+ list.get(34));
		prPersinalInfo8
				.add("\n                                                           ____________________");

		prPersinalInfo8.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo8);

		Paragraph prPersinalInfo9 = new Paragraph();
		prPersinalInfo9.setFont(smallBold);
		prPersinalInfo9.add("\n\nVII. Sonstige ben"+o+"tigte Hilfen\n\n\n"
				+ list.get(35));

		prPersinalInfo9.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo9);
		document.add(myTable);

		Paragraph prPersinalInfo10 = new Paragraph();
		prPersinalInfo10.setFont(smallBold);

		prPersinalInfo10
				.add("\n\n     "
						+ list.get(37)
						+ "                                                                                                   "
						+ list.get(40) + "");
		prPersinalInfo10
				.add("\n______________                                                              ______________________________");

		prPersinalInfo10
				.add("\n  Ort, Datum                                                                         Unterschrift Antragsteller 1");

		prPersinalInfo10
				.add("\n\n     "
						+ list.get(38)
						+ "                                                                                                   "
						+ list.get(41) + "");
		prPersinalInfo10
				.add("\n______________                                                              ______________________________");

		prPersinalInfo10
				.add("\n  Ort, Datum                                                                         Unterschrift Antragsteller 2");

		prPersinalInfo10
				.add("\n\n    "
						+ list.get(39)
						+ "                                                                                                   "
						+ list.get(42) + "");
		prPersinalInfo10
				.add("\n______________                                                              ______________________________");

		prPersinalInfo10
				.add("\n  Ort, Datum                                                                         Unterschrift Antragsteller 3");

		prPersinalInfo10.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo10);

		Paragraph prPersinalInfo11 = new Paragraph();
		prPersinalInfo11.setFont(smallBold);
		prPersinalInfo11
				.add("\n\nAnsprechpartner f"+u+"r Fragen: \n Gerd Sarcher \nLehrer an der Heimschule Lender\n 2. Vorstand der Vereinigung der Altsasbacher e.V. ");

		prPersinalInfo11.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo11);

		Paragraph prPersinalInfo12 = new Paragraph();

		try {

			//document.open();

			Drawable d = getResources().getDrawable(R.drawable.commoveamus);

			BitmapDrawable bitDw = ((BitmapDrawable) d);

			Bitmap bmp = bitDw.getBitmap();

			ByteArrayOutputStream stream = new ByteArrayOutputStream();

			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

			Image image = Image.getInstance(stream.toByteArray());
			 image.setAlignment(Image.RIGHT);
			//image.setAbsolutePosition(350, 300);

			float documentWidth = document.getPageSize().getWidth()
					- document.leftMargin() - document.rightMargin();
			float documentHeight = document.getPageSize().getHeight()
					- document.topMargin() - document.bottomMargin();
			image.scaleToFit(documentWidth-400, documentHeight-150);

			Log.e("Doc - Image  = Height", document.getPageSize().getHeight()
					+ " - " + image.getScaledHeight());

			prPersinalInfo12.add(image);

			document.add(prPersinalInfo12);

			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Create new Page in PDF
		document.newPage();

		openPdf();
	}
	
	void openPdf2() {
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String path=Environment.getExternalStorageDirectory() + "/AltsasbacherPDF/";
		File file = new File(path, "Commoveamusforderantrag.pdf");

		intent.setDataAndType(Uri.fromFile(file), "application/pdf");
		startActivity(intent);
		
	}

	void openPdf() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/PDF";

		File file = new File(path, "Commoveamusforderantrag.pdf");

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
					Intent printIntent = new Intent(ThirdFormFormulareActivity.this,
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
					if(files[i].toString().equals("Commoveamusforderantrag.pdf"))
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
