package com.MWC.Altsasbacher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import app.MWC.AsyncImageload.ImageLoader;
import app.tabsample.SmartImageView.NormalSmartImageView;
import app.tabsample.SmartImageView.SinglephotoAlbumBitmap;

import com.MWC.AltsasbacherAppVO.FSJBerichteVO;

@SuppressLint("NewApi")
public class FSJBerichteinfoActivity extends Activity {

	public TextView Fsjtitletextview;
	public RelativeLayout Fsjlayout;
	public Button fsjsharebtn, settingbtn;
	public FSJBerichteVO fsjBerichteVO;
	public AlertDialog myAlertDialog;
	private SeekBar brightbar;
	private int brightness;
	private ContentResolver cResolver;
	private Window window;
	TextView txtPerc;
    ImageLoader imageLoader;
	public WebView webview = null;
	public ProgressBar myProgressBar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fsjberichteinfo);
		imageLoader=new ImageLoader(this);

		fsjBerichteVO = FSJBerichteFragement.fsjvoobject;

		// Fsjlayout=(RelativeLayout)findViewById(R.id.FJSinfolayout);
		fsjsharebtn = (Button) findViewById(R.id.fsjsharebtn);
		settingbtn = (Button) findViewById(R.id.fsjsettingbtn);
		ImageView smartImageView = (ImageView) findViewById(R.id.RSSFEED_INFO_IMAGEVIEW);
		myProgressBar = (ProgressBar) findViewById(R.id.RSSFEED_PROGRESS);

		// if(fsjBerichteVO.image_url!=null||fsjBerichteVO.image_url!="")
		// smartImageView.setBackgroundImageURL(fsjBerichteVO.image_url,myProgressBar,
		// smartImageView);

		try {
			if (fsjBerichteVO.image_url != null
					&&!fsjBerichteVO.image_url.equalsIgnoreCase(""))
			{
				myProgressBar.setVisibility(View.VISIBLE);
			imageLoader.DisplayImage(fsjBerichteVO.image_url,smartImageView, myProgressBar);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			// ImageView image=img;
			// image.setImageResource(R.drawable.incorrect_problems_icon);
			// myProgressBar.setVisibility(View.INVISIBLE);
		}

		try {

			// *********************************************************************************************
			String name = "";
			String details = "";
			String pubDate = "";
			String video = "";
			String preview = "";

			webview = (WebView) findViewById(R.id.RSSFEEDWEBVIEW);
			webview.getSettings().setJavaScriptEnabled(true);
			webview.getSettings().setAllowFileAccess(true);
			// webview.getSettings().setPluginsEnabled(true);
			webview.getSettings().setBuiltInZoomControls(false);

			Intent intent = getIntent();
			if (null != intent) {

				name = "<h3>" + fsjBerichteVO.title + "</h3>";
				details = fsjBerichteVO.content_encoded;
				pubDate = fsjBerichteVO.formatedate;

				details = details.replaceAll("&amp;nbsp;", " ");
				details = details.replaceAll("&amp;#8220;", "“");
				details = details.replaceAll("&amp;#8221;", "”");
				details = details.replaceAll("&amp;#8211;", "–");
				details = details.replaceAll("&amp;#8230;", "„…");
				details = details.replaceAll("&amp;#8242;", "′");
				details = details.replaceAll("&amp;#8217;", "’");
				details = details.replaceAll("&amp;#8243;", "″");
				details = details.replaceAll("&amp;#82", " ");
			}
			webview.loadDataWithBaseURL(null, (name + pubDate + details),
					"text/html", "utf-8", null);
			webview.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					// view.loadUrl(url);
					try {
						Intent intent = new Intent(MenuActivity.myactivity
								.getApplicationContext(), LenderTVLoadUrl.class);
						intent.putExtra("LENTERTVURL", url);
						intent.putExtra("LENTERTVTEXT", " ");
						startActivity(intent);

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

					return true;
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// *********************************************************************************************

		fsjsharebtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {
					String url = "";

					if (fsjBerichteVO.image_url != null
							|| fsjBerichteVO.image_url != "")
						url = fsjBerichteVO.image_url;
					
					Bitmap bm = imageLoader.getOfflineBitmab(url);

					if (bm != null) {
						// Send the file
						Intent emailIntent = new Intent(Intent.ACTION_SEND);
						emailIntent.setType("text/html");
						emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
						emailIntent.putExtra(Intent.EXTRA_TEXT, "");
						String path = Images.Media.insertImage(
								getContentResolver(), bm, "", null);
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

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}// end of trycatch

			}
		});

		settingbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowDialog();
			}
		});
	}// end of oncreate()

	public String stripHtml(String html) {
		return Html.fromHtml(html).toString();
	}

	public void ShowDialog() {
		
		try {
			
		

		final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
		final LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);

		final View Viewlayout = inflater.inflate(R.layout.activity_dialog,
				(ViewGroup) findViewById(R.id.layout_dialog));

		txtPerc = (TextView) Viewlayout.findViewById(R.id.Uppertitletextview); // txtItem1
		final TextView item2 = (TextView) Viewlayout
				.findViewById(R.id.Lowertextview); // txtItem2
		final Button btndecrice = (Button) Viewlayout
				.findViewById(R.id.decreasebtn);
		final Button btnincrice = (Button) Viewlayout
				.findViewById(R.id.increasebtn);
		final Button btncancel = (Button) Viewlayout
				.findViewById(R.id.CANCLEBTN);


		// popDialog.setIcon(android.R.drawable.btn_star_big_on);
		// popDialog.setTitle("Please Select Rank 1-100 ");
		popDialog.setView(Viewlayout);

		brightbar = (SeekBar) Viewlayout.findViewById(R.id.seekBar1);
		cResolver = getContentResolver();
		window = getWindow();
		brightbar.setMax(255);
		brightbar.setKeyProgressIncrement(1);
		try {
			brightness = System.getInt(cResolver, System.SCREEN_BRIGHTNESS);
		} catch (SettingNotFoundException e) {
			Log.e("Error", "Cannot access system brightness");
			e.printStackTrace();
		}

		brightbar.setProgress(brightness);
		brightbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onStopTrackingTouch(SeekBar seekBar) {
				System.putInt(cResolver, System.SCREEN_BRIGHTNESS, brightness);
				LayoutParams layoutpars = window.getAttributes();
				layoutpars.screenBrightness = brightness / (float) 255;
				window.setAttributes(layoutpars);
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (progress <= 20) {
					brightness = 20;
				} else // brightness is greater than 20
				{
					brightness = progress;
				}
				float perc = (brightness / (float) 255) * 100;
				// txtPerc.setText((int)perc +"%");
			}
		});

		// Button OK
		// popDialog.setPositiveButton("OK",
		// new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int which) {
		// dialog.dismiss();
		// }
		//
		// });

		btndecrice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textSmaller();
			}
		});

		btnincrice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textBigger();
			}
		});
		
		btncancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myAlertDialog.dismiss();
			}
		});

		myAlertDialog = popDialog.create();
		myAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		WindowManager.LayoutParams wmlp = myAlertDialog.getWindow()
				.getAttributes();
		wmlp.gravity = Gravity.RIGHT;
		// wmlp.x = 100; //x position
		// wmlp.y = 100; //y position
		myAlertDialog.show();
		myAlertDialog.getWindow().setLayout(
				(int) (MenuActivity.devicewidth / 4) * 3,
				(int) (MenuActivity.deviceheight / 4) * 3);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}// end of showdialog

	private void textSmaller() {

		WebSettings settings = webview.getSettings();
		settings.setTextZoom(settings.getTextZoom() - 10);
	}

	private void textBigger() {

		WebSettings settings = webview.getSettings();
		settings.setTextZoom(settings.getTextZoom() + 10);
	}

}// end of class
