package com.MWC.Altsasbacher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.MWC.AltsasbacherAppVO.AltsasbachertagVO;
import com.MWC.AltsasbacherAppVO.ShowAlertDialog;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.PushService;
import com.parse.SaveCallback;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MenuActivity extends FragmentActivity implements
		View.OnClickListener {

	public ResideMenu resideMenu;

	public static ResideMenu staticsresideMenu;
	public MenuActivity mContext;
	public static Activity myactivity;
	TextView title;
	public static String RSSFEEDURL = null;
	public Button termineattachbtn;
	public ImageView termineattachmentbtnimage;
	public Button googlemapiconbtn;
	public Button webviewbackbtn;
	public Button webviewforwardbtn;
	public static long deviceheight;
	public static long devicewidth;
	private ResideMenuItem wichtigeMeldungen;
	private ResideMenuItem termine;
	private ResideMenuItem lenderTV;
	private ResideMenuItem fotoalben;
	private ResideMenuItem altsasbachertage;
	private ResideMenuItem altsasbachernetz;
	private ResideMenuItem formulare;
	private ResideMenuItem kontakt;

	private ResideMenuItem neuigkeiten;
	private ResideMenuItem allgemein;
	private ResideMenuItem fsjBerichte;
	private ResideMenuItem dersasbacher;
	private ResideMenuItem diealtsasbachertage;
	private ResideMenuItem projekte;
	private ResideMenuItem historisches;
	public SharedPreferences sharedPrefs;
	/**
	 * Called when the activity is first created.
	 * 
	 */
	@Override
	public void onBackPressed() {
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		setContentView(R.layout.main);
		mContext = this;
		myactivity = MenuActivity.this;
		sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(MenuActivity.myactivity);
		try {
			Parse.enableLocalDatastore(getApplicationContext());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		Parse.initialize(this, "bEsMWScddlBYLy0JwRq3cFGSDnvF8mqkoTRixiMG", "d5fDVwFNkmY6VOMXiZlUFpIF6ENJriHNviScK29m");
		  ParseInstallation.getCurrentInstallation().saveInBackground();
		  
		  PushService.setDefaultPushCallback(this, MenuActivity.class);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		deviceheight = metrics.heightPixels;
		devicewidth = metrics.widthPixels;

		termineattachbtn = (Button) findViewById(R.id.termineattachmentbtn);
		termineattachmentbtnimage = (ImageView) findViewById(R.id.termineattachmentbtnimage);
		title = (TextView) findViewById(R.id.menutitletextview);
		setUpMenu();
		if (savedInstanceState == null)
			changeFragment(new WichtigeFragement());

		termineattachbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
				
				if (AppUtils.isNetworkAvailable(MenuActivity.myactivity)) {
					if (TermineFragement.staticterVo.terminetitle != null||TermineFragement.staticterVo.terminetitle != "") {
						InputStream in = null;
						OutputStream out = null;
						try {
							in = getResources()
									.openRawResource(
											TermineFragement.staticterVo.terminebackimageid);
							out = new FileOutputStream(
									new File(Environment
											.getExternalStorageDirectory(),
											"image.png"));
							copyFile(in, out);
							in.close();
							in = null;
							out.flush();
							out.close();
							out = null;
						} catch (Exception e) {
							Log.e("tag", e.getMessage());
							e.printStackTrace();
						}

						String Extratext = "Veranstaltungsname: "
								+ TermineFragement.staticterVo.terminetitle
								+ "\nWann: "
								+ TermineFragement.staticterVo.terminedatetime
								+ "\nWo :  "
								+ TermineFragement.staticterVo.terminelocation;

						// Send the file
						Intent emailIntent = new Intent(Intent.ACTION_SEND);
						emailIntent.setType("text/html");

						emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
						emailIntent.putExtra(Intent.EXTRA_TEXT, Extratext);
						// Uri uri = Uri.fromFile(new
						// File(Environment.getExternalStorageDirectory(),
						// "image.png"));
						// emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
						String path = Images.Media.insertImage(
								getContentResolver(),
								TermineFragement.Imagebitmap, "", null);
						Uri screenshotUri = Uri.parse(path);
						emailIntent
								.putExtra(Intent.EXTRA_STREAM, screenshotUri);
						try {
							startActivity(Intent.createChooser(emailIntent,
									"Per E-Mail teilen.."));
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}// end of if
				}// end of network check
				else {
					AppUtils.ShowAlertDialog();

				}
				} catch (Exception e) {
					// TODO: handle exception
				e.printStackTrace();
				}
			}
		});// end of on click

		googlemapiconbtn = (Button) findViewById(R.id.google_map_icon);
		googlemapiconbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(MenuActivity.myactivity
						.getApplicationContext(),
						GoogleMapAllpointsActivity.class);
				intent.putExtra("GOOGLEPOINTS",
						AltsasbachertagFragment.googleallpointslist);
				startActivity(intent);
			}
		});// end of onclick

		webviewbackbtn = (Button) findViewById(R.id.Web_view_Backbtn);
		webviewbackbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (AltsasbachernetzFragement.mywebView.canGoBack()) {
					AltsasbachernetzFragement.mywebView.goBack();

				}
			}
		});

		webviewforwardbtn = (Button) findViewById(R.id.Web_view_forwardbtn);
		webviewforwardbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (AltsasbachernetzFragement.mywebView.canGoForward()) {
					AltsasbachernetzFragement.mywebView.goForward();

				}
			}
		});

	}// end of oncreate

	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

	private void setUpMenu() {

		// attach to current activity;
		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.drawable.projectbackground);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(menuListener);
		// valid scale factor is between 0.0f and 1.0f. leftmenu'width is
		// 150dip.
		resideMenu.setScaleValue(0.6f);

		// create menu items;
		wichtigeMeldungen = new ResideMenuItem(this,
				R.drawable.wichtigemeldungenicon, "Wichtige Meldungen");
		termine = new ResideMenuItem(this, R.drawable.termineicon, "Termine");
		altsasbachertage = new ResideMenuItem(this,
				R.drawable.altsasbachernetz, "Altsasbachertage");
		fotoalben = new ResideMenuItem(this, R.drawable.fotoalbenicon,
				"Fotoalben");
		lenderTV = new ResideMenuItem(this, R.drawable.lendertvicon, "LenderTV");
		altsasbachernetz = new ResideMenuItem(this,
				R.drawable.speechbubblesicon, "Altsasbachernetz");
		formulare = new ResideMenuItem(this, R.drawable.formulareicon,
				"Formulare");
		kontakt = new ResideMenuItem(this, R.drawable.kontakticon, "Kontakt");

		neuigkeiten = new ResideMenuItem(this, R.drawable.transparentimageicon,
				"Neuigkeiten");
		allgemein = new ResideMenuItem(this, R.drawable.transparentimageicon,
				"Allgemein");
		fsjBerichte = new ResideMenuItem(this, R.drawable.transparentimageicon,
				"FSJ Berichte");
		dersasbacher = new ResideMenuItem(this,
				R.drawable.transparentimageicon, "Der Sasbacher");
		diealtsasbachertage = new ResideMenuItem(this,
				R.drawable.transparentimageicon, "Die Altsasbachertage");
		projekte = new ResideMenuItem(this, R.drawable.transparentimageicon,
				"Projekte");
		historisches = new ResideMenuItem(this,
				R.drawable.transparentimageicon, "Historisches");

		wichtigeMeldungen.setOnClickListener(this);
		termine.setOnClickListener(this);
		altsasbachertage.setOnClickListener(this);
		fotoalben.setOnClickListener(this);
		lenderTV.setOnClickListener(this);
		altsasbachernetz.setOnClickListener(this);
		formulare.setOnClickListener(this);
		kontakt.setOnClickListener(this);

		neuigkeiten.setOnClickListener(this);
		allgemein.setOnClickListener(this);
		fsjBerichte.setOnClickListener(this);
		dersasbacher.setOnClickListener(this);
		diealtsasbachertage.setOnClickListener(this);
		projekte.setOnClickListener(this);
		historisches.setOnClickListener(this);

		resideMenu.addMenuItem(wichtigeMeldungen, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(termine, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(altsasbachertage, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(fotoalben, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(lenderTV, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(altsasbachernetz, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(formulare, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(kontakt, ResideMenu.DIRECTION_LEFT);

		resideMenu.addMenuItem(neuigkeiten, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(allgemein, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(fsjBerichte, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(dersasbacher, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(diealtsasbachertage, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(projekte, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(historisches, ResideMenu.DIRECTION_RIGHT);

		// You can disable a direction by setting ->
		// resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

		findViewById(R.id.title_bar_left_menu).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
					}
				});
		findViewById(R.id.title_bar_right_menu).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
					}
				});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.dispatchTouchEvent(ev);
	}

	@Override
	public void onClick(View view) {

		if (view == wichtigeMeldungen) {
			title.setText("Wichtige Meldungen");
			makevisible();
			makeinvisible();
			changeFragment(new WichtigeFragement());
		} else if (view == termine) {
			title.setText("Termine");
			makevisible();
			makeinvisible();
			termineattachbtn.setVisibility(View.VISIBLE);
			termineattachmentbtnimage.setVisibility(View.VISIBLE);
			changeFragment(new TermineFragement());

		} else if (view == altsasbachertage) {
			title.setText("Altsasbachertage");
			makeinvisible();
			makevisible();
			googlemapiconbtn.setVisibility(View.VISIBLE);
			changeFragment(new AltsasbachertagFragment());

		} else if (view == fotoalben) {
			title.setText("Fotoalben");
			makeinvisible();
			makevisible();
			changeFragment(new FotoalbenFragement());

		} else if (view == lenderTV) {
			title.setText("LenderTV");
			makeinvisible();
			makevisible();
			changeFragment(new LendertvFragement());

		} else if (view == altsasbachernetz) {
			makeinvisible();
			findViewById(R.id.title_bar_right_menu).setVisibility(View.GONE);
			title.setText("Altsasbachernetz");
			webviewbackbtn.setVisibility(View.VISIBLE);
			webviewforwardbtn.setVisibility(View.VISIBLE);

			changeFragment(new AltsasbachernetzFragement());

		} else if (view == formulare) {
			title.setText("Formulare");
			makeinvisible();
			makevisible();
			changeFragment(new FormulareFragement());

		}

		else if (view == kontakt) {
			// makeinvisible();
			gmailintent();

		} else if (view == fsjBerichte || view == neuigkeiten
				|| view == allgemein || view == dersasbacher
				|| view == diealtsasbachertage || view == projekte
				|| view == historisches) {

			if ((view == fsjBerichte)) {
				title.setText("FSJ Berichte");
				RSSFEEDURL = "http://www.altsasbacher.de/?cat=14&feed=rss2";
			} else if ((view == neuigkeiten)) {
				title.setText("Neuigkeiten");
				RSSFEEDURL = "http://www.altsasbacher.de/?cat=7&feed=rss2";
			} else if ((view == allgemein)) {
				title.setText("Allgemein");
				RSSFEEDURL = "http://www.altsasbacher.de/?cat=1&feed=rss2";
			} else if ((view == dersasbacher)) {
				title.setText("Der Sasbacher");
				RSSFEEDURL = "http://www.altsasbacher.de/?cat=9&feed=rss2";
			} else if ((view == diealtsasbachertage)) {
				title.setText("Die Altsasbachertage");
				RSSFEEDURL = "http://www.altsasbacher.de/?cat=13&feed=rss2";
			} else if ((view == projekte)) {
				title.setText("Projekte");
				RSSFEEDURL = "http://www.altsasbacher.de/?cat=6&feed=rss2";
			} else if ((view == historisches)) {
				title.setText("Historisches");
				RSSFEEDURL = "http://www.altsasbacher.de/?cat=8&feed=rss2";
			}
			if (RSSFEEDURL != null) {

			
				if(AppUtils.isNetworkAvailable(MenuActivity.myactivity))
				{
					makeinvisible();
					makevisible();
					changeFragment(new FSJBerichteFragement());		
				}
				else
				{
					int len = sharedPrefs.getInt(MenuActivity.RSSFEEDURL, (int) 0);
					if(len>0)
					{
						makeinvisible();
						makevisible();
						changeFragment(new FSJBerichteFragement());		
					}
				
				}
				
			
			}

		}

		resideMenu.closeMenu();
	}// end of onclick

	public void makeinvisible() {
		termineattachbtn.setVisibility(View.GONE);
		termineattachmentbtnimage.setVisibility(View.GONE);
		googlemapiconbtn.setVisibility(View.GONE);
		webviewbackbtn.setVisibility(View.GONE);
		webviewforwardbtn.setVisibility(View.GONE);
	}

	public void makevisible() {
		findViewById(R.id.title_bar_right_menu).setVisibility(View.VISIBLE);
	}

	public void gmailintent() {

		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL, new String[] { "info@altsasbacher.de" });
		i.putExtra(Intent.EXTRA_SUBJECT, " ");
		i.putExtra(Intent.EXTRA_TEXT, "Send from android mobile ");

		try {
			mContext.startActivity(Intent.createChooser(i, "Kontakt aufnehmen.."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(mContext, "There are no email clients installed.",
					Toast.LENGTH_SHORT).show();
		}

	}// end of gmailintent

	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
		@Override
		public void openMenu() {
			// Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT)
			// .show();
		}

		@Override
		public void closeMenu() {
			// Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT)
			// .show();
		}
	};

	public void changeFragment(Fragment targetFragment) {
		resideMenu.clearIgnoredViewList();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_fragment, targetFragment, "fragment")
				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();
	}

	// What good method is to access resideMenu？
	public ResideMenu getResideMenu() {
		return resideMenu;
	}
}
