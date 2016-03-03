package com.MWC.Altsasbacher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class SecondFormFormulareActivity extends Activity {

	private Button createPDF, openPDF;
	CheckBox edittext1;
	CheckBox edittext2;
	EditText edittext3, edittext4, edittext5, edittext6, edittext7, edittext8,
			edittext9, edittext10, edittext11, edittext12, edittext13,
			edittext14, edittext15, edittext16, edittext17, edittext18,
			edittext19, edittext20, edittext21, edittext22, edittext23,
			edittext24, edittext25, edittext26, edittext27;
	ArrayList<String> list = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second_form_formulare);

		edittext1 = (CheckBox) findViewById(R.id.edittext1);
		edittext2 = (CheckBox) findViewById(R.id.edittext2);
		edittext3 = (EditText) findViewById(R.id.edittext3);
		edittext4 = (EditText) findViewById(R.id.edittext4);
		edittext5 = (EditText) findViewById(R.id.edittext5);
		edittext6 = (EditText) findViewById(R.id.edittext6);
		edittext7 = (EditText) findViewById(R.id.edittext7);
		edittext8 = (EditText) findViewById(R.id.edittext66);
		edittext9 = (EditText) findViewById(R.id.edittext77);
		edittext10 = (EditText) findViewById(R.id.edittext8);
		edittext11 = (EditText) findViewById(R.id.edittext9);
		edittext12 = (EditText) findViewById(R.id.edittext10);
		edittext13 = (EditText) findViewById(R.id.edittext11);
		edittext14 = (EditText) findViewById(R.id.edittext12);
		edittext15 = (EditText) findViewById(R.id.edittextGesamtkosten);
		edittext16 = (EditText) findViewById(R.id.edittextDritter);
		edittext17 = (EditText) findViewById(R.id.edittextNettobelastung);
		edittext18 = (EditText) findViewById(R.id.edittextBeantragte);
		edittext19 = (EditText) findViewById(R.id.edittextder);
		edittext20 = (EditText) findViewById(R.id.edittextder3);
		edittext21 = (EditText) findViewById(R.id.edittextAntragseingang);
		edittext22 = (EditText) findViewById(R.id.edittextSitzung);
		edittext23 = (EditText) findViewById(R.id.edittextZuwen);
		edittext24 = (EditText) findViewById(R.id.edittextAuszahlung);
		edittext25 = (EditText) findViewById(R.id.edittextBemerkungen);

		
		Button Emptyprintbtn = (Button) findViewById(R.id.EnptyPDFbtn);
		Emptyprintbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CopyPdffromAccettosdcard();
				String path=Environment.getExternalStorageDirectory() + "/AltsasbacherPDF/"
						+"forderantrag.pdf";
				//PRINTPDF(path);
				Toast.makeText(SecondFormFormulareActivity.this, "PDF Datei gespeichert unter"+path, Toast.LENGTH_LONG).show();
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

				//deletePDF();
				createPDF();
				openPdf();
				list.clear();
			}
		});
	}

	public void createPDF() {

		String FILE = Environment.getExternalStorageDirectory().toString()
				+ "/PDF/" + "forderantrag.pdf";

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

		Toast.makeText(this, "PDF Datei wurde generiert unter: " + FILE,
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
		
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
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
		
		BaseFont urName = null;
		try {
			urName = BaseFont.createFont("assets/fonts/NotoSans-Bold.ttf", "UTF-8",BaseFont.EMBEDDED);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Font urFontName = new Font(urName, 12,Font.BOLD);
		
		Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
		Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD,
				 BaseColor.BLACK);
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

		// document.add(myTable);

		PdfPTable table = new PdfPTable(1);

		// t.setBorderColor(BaseColor.GRAY);
		// t.setPadding(4);
		// t.setSpacing(4);
		// t.setBorderWidth(1);
		String str = "\u00F6";
		
		//table.addCell(new PdfPCell(new Phrase("\nF "+O+" R D E R A N T R A G                                                        ",urFontName)));
		table.addCell(new PdfPCell(new Phrase("\nF "+O+" R D E R A N T R A G                                   "				+ list.get(0)
				+ ": F"+o+"rderverein"
				+ "\n\nVereinigung der Altsasbacher e.V.                               "
				+ list.get(1) + ": Stiftung \n\n",catFont)));
		//table.addCell("Gemeinnützige Organisation zur Förderung von Bildung und Erziehung an der Heimschule Lender in Sasbach");
		//table.addCell(new PdfPCell(new Phrase("Gemeinnützige Organisation zur Förderung von Bildung und Erziehung an der Heimschule Lender in Sasbach",urFontName)));
		table.addCell("Gemeinn"+u+"tzige Organisation zur F"+o+"rderung von Bildung und Erziehung an der Heimschule Lender in Sasbach");
		
		document.add(table);

		PdfPTable table7 = new PdfPTable(1);
		table7.addCell(new PdfPCell(new Phrase("\nAngaben des Antragstellers:",catFont)));
		document.add(table7);

		PdfPTable table2 = new PdfPTable(2);
		table2.addCell("Fachschaft/Klasse/AG/....." + list.get(2) + "\n\n ");
		table2.addCell("Ansprechpartner/-in" + list.get(5) + "\n\n");
		table2.addCell("" + list.get(3) + "\n\n ");
		table2.addCell(" Tel. / Fax/E-Mail-Adresse" + list.get(6) + "\n\n");
		table2.addCell("" + list.get(4) + "\n\n ");
		table2.addCell(" KontoverbindungIBAN: " + list.get(7) + "\n\n BIC : "
				+ list.get(9) + "\n\n Bank :" + list.get(8) + "\n\n");
		document.add(table2);

		PdfPTable table8 = new PdfPTable(1);
		table8.addCell(new PdfPCell(new Phrase("\nKurzdarstellung des Projekts:",catFont)));

		document.add(table8);

		// document.add(myTable);

		PdfPTable table3 = new PdfPTable(2);
		table3.addCell("Bezeichnung des Projekts" + list.get(10) + "\n\n ");
		table3.addCell("Projektverantwortliche/r" + list.get(11) + "\n\n");
		table3.addCell("\n\n ");

		table3.addCell("Zeitraum der Durchf"+u+"hrung\nBeginn:" + list.get(12)
				+ "\n\nvoraussichtliches Ende:" + list.get(13) + "\n\n");
		document.add(table3);

		PdfPTable table9 = new PdfPTable(1);
		table9.addCell(new PdfPCell(new Phrase("\nFinanzierungs"+u+"bersicht:",catFont)));

		document.add(table9);

		String str1=list.get(14);
		String str2=list.get(15);
		String str3=list.get(16);
		String str4=list.get(17);
		String str5=list.get(18);
		
		//int maxlength=str1.length();
		
		int maxlength = Collections.max(Arrays.asList(str1.length(), str2.length(), str3.length(),
				str4.length(), str5.length()));
		str1=spaces(str1, maxlength-str1.length());
		str2=spaces(str2, maxlength-str2.length());
		str3=spaces(str3, maxlength-str3.length());
		str4=spaces(str4, maxlength-str4.length());
		str5=spaces(str5, maxlength-str5.length());
		
		PdfPTable table4 = new PdfPTable(1);
		table4.addCell("   Gesamtkosten des Projekts :"
				+ str1
				+ "    €"
				+ "\n         abzgl. Zusch"+u+"sse Dritter : "
				+ str2
				+ "  € "
				+ "\n                       Nettobelastung :   "
				+ str3
				+ " € (lt. Anlage – detaillierte Aufstellung))"
				+ "\n\n         Beantragte Zuwendung :   "
				+ str4
				+ " €  Unterschrift Projektverantwortliche/r"
				+ "\n Der / Die Fachabteilungsleiter\n//in ist informiert und stimmt\n                       dem Projekt zu: "
				+ str5
				+ "   Unterschrift Fachabteilungsleiter/in \n\n");
		document.add(table4);

		PdfPTable table10 = new PdfPTable(1);
		table10.addCell(new PdfPCell(new Phrase("\n Erl"+a+"uterungen zum Projekt:",catFont)));

		document.add(table10);

		PdfPTable table5 = new PdfPTable(1);
		table5.addCell("(ggf. weiteres Blatt beilegen)\n- Darstellung der Beweggr"+u+"nde, Vorbildlichkeit des Projekts, zeitlicher und finanzieller Einsatz der Beteiligten (Sch"+u+"ler, Lehrer,\nEhrenamtliche)\n- P"+a+"dagogische Begr"+u+"ndung:"
				+ "\n  " + list.get(19) + "\n");
		document.add(table5);

		PdfPTable table6 = new PdfPTable(1);

		table6.addCell("Entscheid:(durch den Vorstand des Altsasbachervereins auszuf"+u+"llen) "
				+ "\nAntragseingang             :"
				+ list.get(20)
				+ "       Sitzung am  :"
				+ list.get(21)
				+ "    Zustimmung"
				+ "\nH"+o+"he der Zuwen-dung   :"
				+ list.get(22)
				+ "       Auszahlung  :"
				+ list.get(23)
				//+ "    Zustimmung"
				+ "\n\n Bemerkungen:" + list.get(24) + "\n\n");
		document.add(table6);

		// Create new Page in PDF
		document.newPage();
	}
	
	void openPdf2() {
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String path=Environment.getExternalStorageDirectory() + "/AltsasbacherPDF/";
		File file = new File(path, "forderantrag.pdf");

		intent.setDataAndType(Uri.fromFile(file), "application/pdf");
		startActivity(intent);
		
	}

	void openPdf() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/PDF";

		File file = new File(path, "forderantrag.pdf");

		intent.setDataAndType(Uri.fromFile(file), "application/pdf");
		startActivity(intent);
	}
	private  String spaces(String str, int numberOfSpaces)
	{
	    //Loop as many times as specified; each time add a space to the string
	    for(int i=0; i < numberOfSpaces; i++)
	    {
	        str=str+" ";
	    }

	    return str;
	}
	
	public void PRINTPDF(String Path)
	{
		try {
			if (!AppUtils.isNetworkAvailable(this)) {
		          AppUtils.ShowAlertDialog();
			  } else {
				  
				  File file = new File(Path);
					Intent printIntent = new Intent(SecondFormFormulareActivity.this,
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
					if(files[i].toString().equals("forderantrag.pdf"))
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
