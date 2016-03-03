package com.MWC.Altsasbacher;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

 public class LenderTVLoadUrl extends Activity {
	
	public WebView mywebView;
	String url="",titletext="";
	public AlertDialog myAlertDialog;
	public ProgressBar myProgressBar;
	public TextView uppertext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lendertv_urlload);
		
		mywebView=(WebView)findViewById(R.id.LenderTvUrlwebview);
		Button backword=(Button)findViewById(R.id.backword);
		Button forward=(Button)findViewById(R.id.forward);
		Button Sharebtn=(Button)findViewById(R.id.LenderTvSharebtn);
		myProgressBar=(ProgressBar)findViewById(R.id.WebviewprogressBar);
		uppertext=(TextView)findViewById(R.id.LENDERTVUPPERtextview);
		
		if(!AppUtils.isNetworkAvailable(MenuActivity.myactivity))
		{
			AppUtils.ShowAlertDialog();
		}
		
		try {
		
		
		Intent intent = getIntent();
   		if (null != intent) 
   		{
		url =intent.getStringExtra("LENTERTVURL");
		titletext=intent.getStringExtra("LENTERTVTEXT");
   		}
   		if (url.endsWith(".pdf")) {
   		    try {
   		         String urlEncoded = URLEncoder.encode(url, "UTF-8");
   		         url = "http://docs.google.com/viewer?url=" + urlEncoded;
   		    } catch (UnsupportedEncodingException e) {
   		         e.printStackTrace();
   		    }

   		}
   		
   		

    	 WebSettings webSettings = mywebView.getSettings();
         webSettings.setJavaScriptEnabled(true);
         
    	mywebView.loadUrl(url);
    	mywebView.setWebViewClient(new WebViewClient() {
    	    @Override
    	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
    	        view.loadUrl(url);
    	        return true;
    	    }
    	});
    	
		} catch (Exception e) {
		   e.printStackTrace();
		}
		
		mywebView.setWebChromeClient(new WebChromeClient() {
	           public void onProgressChanged(WebView view, int progress) {
	        	   //LenderTVLoadUrl.this.setTitle("Loading...");
	        	   LenderTVLoadUrl.this.setProgress(progress * 100);
	        	   myProgressBar.setVisibility(View.VISIBLE);
	        	   uppertext.setText("Loading...");


	               if(progress == 100)
	               {
	            	   myProgressBar.setVisibility(View.INVISIBLE);
	            	   uppertext.setText(titletext);

	            	 //  LenderTVLoadUrl.this.setTitle(R.string.app_name);
	               }
	            }
	         });
		
		
		Sharebtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					ShowDialog();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
		});
		
		backword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if (mywebView.canGoBack()) {
						mywebView.goBack();

					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
		});
		
        forward.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try {
					if (mywebView.canGoForward()) {
						mywebView.goForward();

						}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
		});
		
		
	}//end of oncreate()

	
	public void ShowDialog()
	{
		 

        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        
       
        final View Viewlayout = inflater.inflate(R.layout.activity_lendertv_dialog,
                (ViewGroup) findViewById(R.id.Lendertv_dialog_Layout));       

        //TextView  txtPerc = (TextView)Viewlayout.findViewById(R.id.Urltextview); // txtItem1
		final Button openbtn=(Button)Viewlayout.findViewById(R.id.Openbutton);
		final Button copybtn=(Button)Viewlayout.findViewById(R.id.Copybutton);
		final Button cancelbtn=(Button)Viewlayout.findViewById(R.id.Cancelbutton);
		
		
		//popDialog.setIcon(android.R.drawable.btn_star_big_on);
		popDialog.setTitle(url);
		
		popDialog.setView(Viewlayout);
		//txtPerc.setText(url);
		openbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try {
					myAlertDialog.dismiss();
					if (!url.startsWith("http://") && !url.startsWith("https://"))
						   url = "http://" + url;
					
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					startActivity(browserIntent);	
				} catch (Exception e) {
					// TODO: handle exception
					myAlertDialog.dismiss();
					e.printStackTrace();
				}
				
			}
		});
		
		copybtn.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("NewApi") @Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try {
					myAlertDialog.dismiss();
					ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
					ClipData clip = ClipData.newPlainText("label",url);
					clipboard.setPrimaryClip(clip);
					
				} catch (Exception e) {
					// TODO: handle exception
					myAlertDialog.dismiss();
					e.printStackTrace();
				}
			}
		});
		
		cancelbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myAlertDialog.dismiss();
			}
		});


		myAlertDialog=popDialog.create();
		myAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    WindowManager.LayoutParams wmlp = myAlertDialog.getWindow().getAttributes();
	    wmlp.gravity = Gravity.BOTTOM;
	    //wmlp.x = 100;   //x position
	    //wmlp.y = 100;   //y position
		myAlertDialog.show();
        myAlertDialog.getWindow().setLayout((int)(MenuActivity.devicewidth),(int)(MenuActivity.deviceheight*40)/100);
	}//end of showdialog

	
}//end of Activity
