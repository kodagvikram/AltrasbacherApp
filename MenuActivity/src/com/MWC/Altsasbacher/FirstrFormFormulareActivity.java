package com.MWC.Altsasbacher;

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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
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
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
public class FirstrFormFormulareActivity extends Activity {
	private Button createPDF, openPDF;
	EditText edittext, edittextname, edittextstrab, edittextort,
			edittexttelefon, edittextemail, edittext7, edittext8, edittext10,
			edittext11, edittext13;
	ArrayList<String> list = new ArrayList<String>();
	public final static int BUFFER_SIZE = 1024;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firstr_form_formulare);

		edittext = (EditText) findViewById(R.id.edittext);
		edittextname = (EditText) findViewById(R.id.edittextname);
		edittextstrab = (EditText) findViewById(R.id.edittextstrabe);
		edittextort = (EditText) findViewById(R.id.edittextort);
		edittexttelefon = (EditText) findViewById(R.id.edittexttelefon);
		edittextemail = (EditText) findViewById(R.id.edittextemail);
		edittext7 = (EditText) findViewById(R.id.edittext7);
		//edittext8 = (EditText) findViewById(R.id.edittext8);
		edittext10 = (EditText) findViewById(R.id.edittext10);
		edittext11 = (EditText) findViewById(R.id.edittext11);
		edittext13 = (EditText) findViewById(R.id.edittext13);
		
		edittext.setTextSize(11);
		edittextname.setTextSize(11);
		edittextstrab.setTextSize(11);
		edittextort.setTextSize(11);
		edittexttelefon.setTextSize(11);
		edittextemail.setTextSize(11);
		edittext7.setTextSize(11);
		edittext10.setTextSize(11);
		edittext11.setTextSize(11);
		edittext13.setTextSize(11);
		
	Button Emptyprintbtn = (Button) findViewById(R.id.EnptyPDFbtn);
		Emptyprintbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CopyPdffromAccettosdcard();
				String path=Environment.getExternalStorageDirectory() + "/AltsasbacherPDF/"
						+"Beitrittseklarung.pdf";
				//PRINTPDF(path);
				Toast.makeText(FirstrFormFormulareActivity.this, "PDF Datei gespeichert unter"+path, Toast.LENGTH_LONG).show();
				openPdf2();
			}
		});
		
		Button EdittedPrintbtn = (Button) findViewById(R.id.EditedPDFbtn);
		EdittedPrintbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				list.add("     "+edittext.getText().toString());
				list.add("     "+edittextname.getText().toString());
				list.add("     "+edittextstrab.getText().toString());
				list.add("          "+edittextort.getText().toString());
				list.add("     "+edittexttelefon.getText().toString());
				list.add("       "+edittextemail.getText().toString());
				list.add("     "+edittext7.getText().toString());
				list.add("     ");
				list.add("    "+edittext10.getText().toString());
				list.add("                 "+edittext11.getText().toString());
				list.add("                    "+edittext13.getText().toString());
				//deletePDF();
				//createPDF();
				//list.clear();
				//String FILE = Environment.getExternalStorageDirectory().toString()
			//			+ "/PDF/" + "Beitrittseklarung.pdf";
		//		PRINTPDF(FILE);
				createPDF();
				openPdf();
				list.clear();
			}
		});
		

		
	}

//	public void deletePDF()
//	{
//		String FILE = Environment.getExternalStorageDirectory().toString()
//				+ "/PDF/" + "Beitrittseklarung.pdf";
//		File file = new File(FILE);
//		if (file.exists())
//		 {
//            file.delete();
//		}
//	}
	public void createPDF() {

		String FILE = Environment.getExternalStorageDirectory().toString()
				+ "/PDF/" + "Beitrittseklarung.pdf";

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
		String o="\u00F6";
		String u="\u00FC";
		String se="\u00EB";
		String a="\u00E4";
		
		String O="\u00D6";
		String U="\u00DC";
		String E="\u00CB";
		String A="\u00C4";
		
		Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD,
				 BaseColor.BLACK);
		Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
		Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

		
		Paragraph prPersinalInfot = new Paragraph();
		prPersinalInfot.setFont(titleFont);
		prPersinalInfot.add("Vereinigung der Altsasbacher und F"+o+"rderverein e.V."); 

		prPersinalInfot.setAlignment(Element.ALIGN_CENTER);

		document.add(prPersinalInfot);
		
		
		// Start New Paragraph
		Paragraph prHead = new Paragraph();
		// Set Font in this Paragraph
		prHead.setFont(titleFont);
		// Add item into Paragraph
		prHead.add("\nBeitrittserkl"+a+"rung\n\n");

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

		// Now Start another New Paragraph
		Paragraph prPersinalInfo = new Paragraph();
		prPersinalInfo.setFont(smallBold);
		prPersinalInfo.add("Hiermit erkl"+a+"re ich meinen Beitritt zur Vereinigung der Altsasbacher und F"+o+"rderverein e.V.\n\nDer satzungsgem"+a+"ße Mindestbeitrag betr"+a+"gt f"+u+"r im Erwerbsleben stehende Mitglieder 25\nEUR pro Jahr. Der Beitrag f"+u+"r Sch"+u+"ler, Auszubildende und Rentner betr"+a+"gt 10 EUR pro Jahr.\n Mein Beitrag "+u+"ber "+list.get(0)+"   EUR wird j"+a+"hrlich zum 05. Dezember per Lastschrift eingezogen.");

		prPersinalInfo.setAlignment(Element.ALIGN_LEFT);

		document.add(prPersinalInfo);
		// document.add(myTable);

		Paragraph prProfile = new Paragraph();
		prProfile.setFont(smallBold);
		
		prProfile.add("\n Name : " + list.get(1)+"\n                   ____________________________ ");
		
        prProfile.add("\n\n Straße: " + list.get(2)+"\n                   ____________________________ ");
        
		prProfile.add("\n\n Ort: " + list.get(3)+"\n                   ____________________________ ");
		prProfile.add("\n\n Telefon: " + list.get(4)+"\n                   ____________________________ ");
		prProfile.add("\n\n eMail: " + list.get(5)+"\n                   ____________________________ ");
        
		prProfile.setFont(normal);

		prProfile.setFont(smallBold);
		document.add(prProfile);

		Paragraph prProfile1 = new Paragraph();
		prProfile1.setFont(smallBold);
		prProfile1.add("\n SEPA-Lastschriftmandat");
		prProfile1.setAlignment(Element.ALIGN_CENTER);
		prProfile1.setFont(titleFont);
		prProfile1.setFont(catFont);
		document.add(prProfile1);

		Paragraph prProfile2 = new Paragraph();
		prProfile2.setFont(smallBold);
		prProfile2.add("Wiederkehrende Zahlungen/Recurrent Payments\n");
		prProfile2.setAlignment(Element.ALIGN_CENTER);
		prProfile2.setFont(normal);
		document.add(prProfile2);

		// Now Start another New Paragraph
		Paragraph prPersinalInfo2 = new Paragraph();
		prPersinalInfo2.setFont(smallBold);
		prPersinalInfo2.add("Gl"+a+"ubiger-Identifikationsnummer: DE27ZZZ00000725722 Mandatsreferenz:"+list.get(6)+"\n                                                                                                                                       ____________ "+"\n\nIch erm"+a+"chtige (Wir erm"+a+"chtigen) die Vereinigung der Altsasbacher und F"+o+"rderverein e. V.,\nZahlungen von meinem (unserem) Konto mittels Lastschrift einzuziehen. Zugleich weise ich\nmein (weisen wir unser) Kreditinstitut an, die von der Vereinigung der Altsasbacher und\nF"+o+"rderverein e.V. auf mein (unser) Konto gezogenen Lastschriften einzul"+o+"sen.\n\nHinweis: Ich kann innerhalb von acht Wochen, beginnend mit dem Belastungsdatum, die\nErstattung des belasteten Betrages verlangen. Es gelten dabei die mit meinem Kreditinstitut\nvereinbarten Bedingungen.");
		prPersinalInfo2.setAlignment(Element.ALIGN_LEFT);
		document.add(prPersinalInfo2);
		// document.add(myTable);

		Paragraph prProfile3 = new Paragraph();
		prProfile3.setFont(smallBold);
		prProfile3.add("\n Kreditinstitut: "+list.get(8)+"\n                             ____________________________ ");
		prProfile3.add("\n IBAN: "+list.get(9)+"\n                            ____________________________ ");
		prProfile3.add("\n BIC:"+list.get(10)+"\n                           ____________________________ \n\n");
        prProfile3.setFont(normal);
        prProfile3.setFont(smallBold);
		document.add(prProfile3);

		Paragraph prProfile4 = new Paragraph();
		prProfile4.setFont(smallBold);
		document.add(myTable);
		prProfile4.add("(Datum, Ort) (Unterschrift)");
		prProfile4.setFont(normal);
		prProfile4.setFont(smallBold);
		document.add(prProfile4);
		
		// Create new Page in PDF
		document.newPage();
	}

	void openPdf() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/PDF";

		File file = new File(path, "Beitrittseklarung.pdf");
        intent.setType(CallLog.Calls.CONTENT_TYPE);
		intent.setDataAndType(Uri.fromFile(file), "application/pdf");
		startActivity(intent);
		
		
		
		
		
		//finish();
	}
	
	void openPdf2() {
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String path=Environment.getExternalStorageDirectory() + "/AltsasbacherPDF/";
		File file = new File(path, "Beitrittseklarung.pdf");

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
					Intent printIntent = new Intent(FirstrFormFormulareActivity.this,
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

			byte[] buffer = new byte[BUFFER_SIZE];
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
