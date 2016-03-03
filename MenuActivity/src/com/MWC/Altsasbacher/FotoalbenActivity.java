package com.MWC.Altsasbacher;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import app.MWC.AsyncImageload.ImageLoader;

import com.MWC.AltsasbacherAppVO.fotoalbenVO;

public class FotoalbenActivity extends Activity {

	public ArrayList<fotoalbenVO> fotoalbenArrayList = new ArrayList<fotoalbenVO>();
	public static GridView albengridview;
	TextView fotocounttext;
	public ProgressBar myProgressBar = null;
	SinglealbenfotoAdapter adapter;
	XMLParser parser = new XMLParser();
	Document doc;
	String XML = "";
	private DisplayMetrics metrics;
	int panelWidth = 0;
	int gap = 0;
	NodeList nodelist;
	public ProgressDialog pDialog;
	public SharedPreferences sharedPrefs;
	public Editor editor;
	ImageLoader imageLoader;

	public void displayDialog() {
		pDialog = new ProgressDialog(MenuActivity.myactivity);
		pDialog.setMessage("Bitte warten..");
		pDialog.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fotoalben);

		imageLoader = new ImageLoader(getApplicationContext());
		// imageLoader.clearCache();

		//String str = "\u00F6";
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		panelWidth = (int) ((metrics.widthPixels) * 0.34);
		// gap= (int) ((metrics.widthPixels) * 0.03);
		// fotoalbenArrayList
		// =(ArrayList<fotoalbenVO>)getIntent().getSerializableExtra("ALBUMPHOTS");
		myProgressBar = (ProgressBar) findViewById(R.id.Photo_3_GridprogressBar);
		fotocounttext = (TextView) findViewById(R.id.fotocoutnttextview);

		// albengridview = (GridView) findViewById(R.id.singlealbengridview);

		sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(MenuActivity.myactivity);
		editor = sharedPrefs.edit();

		new get_Photo_List().execute();

	}// end of onCreate()

	class get_Photo_List extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			myProgressBar.setVisibility(View.VISIBLE);
		}

		protected String doInBackground(String... aurl) {

			try {
				XML = parser
						.getXmlFromUrl("http://altsasbacher.de/Fotogalerien/"
								+ FotoalbenFragement.AlbenOrderName
								+ "/config.xml");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return "";
		}

		@SuppressLint("NewApi")
		protected void onPostExecute(String str_resp) {
			doc = parser.getDomElement(XML); // getting
			fotoalbenArrayList = new ArrayList<fotoalbenVO>();
			fotoalbenArrayList.clear();
			if (doc != null) {

				// nl = doc.getElementsByTagName("timetable");
				nodelist = doc.getElementsByTagName("item");

				// looping through all item nodes <item>
				for (int i = 0; i < nodelist.getLength(); i++) {

					Element e = (Element) nodelist.item(i);

					fotoalbenVO photoVO = new fotoalbenVO();

					photoVO.AlbenThumb = parser.getValue(e, "thumb");
					photoVO.albenSource = parser.getValue(e, "source");
					fotoalbenArrayList.add(photoVO);
				} // for
			}// if

			if (AppUtils.isNetworkAvailable(MenuActivity.myactivity)) {
				editor = sharedPrefs.edit();
				int i = 0;
				for (i = 0; i < fotoalbenArrayList.size(); i++)
					editor.putString(FotoalbenFragement.AlbenOrderName + i,
							fotoalbenArrayList.get(i).albenSource);

				editor.putInt(FotoalbenFragement.AlbenOrderName,
						fotoalbenArrayList.size());
				editor.commit();
			}

			else {
				int len = sharedPrefs.getInt(FotoalbenFragement.AlbenOrderName,
						(int) 0);

				if (len > 0) {
					int i;
					for (i = 0; i < len; i++) {
						if (sharedPrefs.getString(
								FotoalbenFragement.AlbenOrderName + i, null) != null) {
							fotoalbenVO vo = new fotoalbenVO();
							vo.albenSource = sharedPrefs.getString(
									FotoalbenFragement.AlbenOrderName + i, "");
							fotoalbenArrayList.add(vo);
						}
					}

				} else {
					fotoalbenArrayList = null;
				}

			}

			if (fotoalbenArrayList != null) {
				fotocounttext.setText(fotoalbenArrayList.size() + " Fotos");
				setListValues(fotoalbenArrayList);
				// adapter = new SinglealbenfotoAdapter(
				// FotoalbenActivity.this, fotoalbenArrayList);
				// albengridview.setAdapter(adapter);

			}

			// albengridview.setOnItemClickListener(new OnItemClickListener() {
			//
			// @Override
			// public void onItemClick(AdapterView<?> arg0, View v,
			// int position, long arg3) {
			// Intent intent = new Intent(FotoalbenActivity.this,
			// FotoViewewActivity.class);
			// intent.putExtra("SINGLEALBUMPHOTS", fotoalbenArrayList);
			// intent.putExtra("imageposition", position);
			// startActivity(intent);
			// }
			// });
			myProgressBar.setVisibility(View.INVISIBLE);
		}// post

	}// ascinc

	// **********************************************************
	void setListValues(ArrayList<fotoalbenVO> fotoArrayList) {
		try {

			RelativeLayout mainlayout = (RelativeLayout) findViewById(R.id.mainlayout);
			mainlayout.removeAllViews();
			ProgressBar progressBar1 ;
			ProgressBar progressBar2 ;
			ProgressBar progressBar3 ;
			for (int i = 0; i < fotoArrayList.size(); i++) {

				final fotoalbenVO fotovo = fotoArrayList.get(i);

				Boolean is2img = false, is3img = false;

				// ******************************************
				RelativeLayout layout = new RelativeLayout(this);
				layout.setId(100 + i);
				RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				layoutParams.topMargin = 2;
				if (i != 0)
					layoutParams.addRule(RelativeLayout.BELOW, 97 + i);
				layout.setLayoutParams(layoutParams);

				RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
						panelWidth, panelWidth - 50);
				RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(
						13, 20);
				RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
						panelWidth, panelWidth - 50);
				RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(
						panelWidth, panelWidth - 50);
				RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				
				 progressBar1 = new ProgressBar(this, null, android.R.attr.progressBarStyle);
				 progressBar2 = new ProgressBar(this, null, android.R.attr.progressBarStyle);
				 progressBar3 = new ProgressBar(this, null, android.R.attr.progressBarStyle);
				 
				 progressBar1.setVisibility(View.VISIBLE);
				 progressBar2.setVisibility(View.VISIBLE);
				 progressBar3.setVisibility(View.VISIBLE);
				 
				RelativeLayout.LayoutParams params7 = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				
				params7.addRule(RelativeLayout.CENTER_IN_PARENT);
				
				RelativeLayout layout2 = new RelativeLayout(this);
				layout2.setId(2000 + i);
				RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				//layout2.setLayoutParams(layoutParams2);
				
				final int  k=i;
				ImageView imageView = new ImageView(FotoalbenActivity.this);
				imageView.setId(1);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setBackgroundColor(Color.parseColor("#4a688f"));
				imageLoader.DisplayImage("http://altsasbacher.de/"
						+ fotovo.albenSource, imageView, progressBar1);
				layout2.addView(imageView,params1);
				layout2.addView(progressBar1,params7);
				
				RelativeLayout layout3 = new RelativeLayout(this);
				layout3.setId(3000 + i);
				RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				layoutParams3.addRule(RelativeLayout.RIGHT_OF, layout2.getId());
				
				ImageView imageView2 = new ImageView(FotoalbenActivity.this);
				imageView2.setId(2);
				imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
				params2.leftMargin = 2;
				imageView2.setBackgroundColor(Color.parseColor("#4a688f"));
				// imageLoader.DisplayImage("http://altsasbacher.de/"+fotovo.albenSource,
				// imageView, myProgressBar);
				layout3.addView(imageView2,params2);
				layout3.addView(progressBar2,params7);
				
				RelativeLayout layout4 = new RelativeLayout(this);
				layout4.setId(4000 + i);
				RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				layoutParams4.addRule(RelativeLayout.RIGHT_OF, layout3.getId());
				i++;
				final int  m=i;
				fotoalbenVO fotovo2 = new fotoalbenVO();
				if (i < fotoArrayList.size()) {
					is2img = true;
					fotovo2 = fotoArrayList.get(i);
					imageLoader.DisplayImage("http://altsasbacher.de/"
							+ fotovo2.albenSource, imageView2, progressBar2);
				}
				
				ImageView imageView3 = new ImageView(FotoalbenActivity.this);
				imageView3.setId(3);
				imageView3.setScaleType(ImageView.ScaleType.CENTER_CROP);
				//params3.addRule(RelativeLayout.RIGHT_OF, imageView2.getId());
				params3.leftMargin = 2;
				imageView3.setBackgroundColor(Color.parseColor("#4a688f"));
				layout4.addView(imageView3,params3);
				layout4.addView(progressBar3,params7);
				i++;
				final int  n=i;
				fotoalbenVO fotovo3 = new fotoalbenVO();
				if (i < fotoArrayList.size()) {
					is3img = true;
					fotovo3 = fotoArrayList.get(i);
					imageLoader.DisplayImage("http://altsasbacher.de/"
							+ fotovo3.albenSource, imageView3, progressBar3);
				}

				layout.addView(layout2, layoutParams2);
				if (is2img)
					layout.addView(layout3, layoutParams3);
				if (is3img)
					layout.addView(layout4, layoutParams4);
				// layout.addView(tv2, params2);
				// layout.addView(tv3, params3);
				// layout.addView(tv4, params4);
				// *******************************************
				mainlayout.addView(layout, layoutParams);

				imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						 Intent intent = new Intent(FotoalbenActivity.this,
						 FotoViewewActivity.class);
						 intent.putExtra("SINGLEALBUMPHOTS", fotoalbenArrayList);
						 intent.putExtra("imageposition", k);
						 startActivity(intent);

					}
				});

				imageView2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						 Intent intent = new Intent(FotoalbenActivity.this,
								 FotoViewewActivity.class);
								 intent.putExtra("SINGLEALBUMPHOTS", fotoalbenArrayList);
								 intent.putExtra("imageposition", m);
								 startActivity(intent);
					}
				});
				imageView3.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						 Intent intent = new Intent(FotoalbenActivity.this,
								 FotoViewewActivity.class);
								 intent.putExtra("SINGLEALBUMPHOTS", fotoalbenArrayList);
								 intent.putExtra("imageposition", n);
								 startActivity(intent);
					}
				});

			}// end of for
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}// end of list values
		// **********************************************************

}// end of Activity
