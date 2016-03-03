package com.MWC.Altsasbacher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class AltsasbachernetzFragement extends Fragment {
	private View parentView;
	 public View mview;
	   public WebView browser;
	   public ProgressBar myProgressBar;
	    public  static	WebView mywebView;
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	    	parentView = inflater.inflate(R.layout.webview_altsasbachernetz_xml, container, false);
	    	
	    	if(!AppUtils.isNetworkAvailable(MenuActivity.myactivity))
			{
				AppUtils.ShowAlertDialog();
			}
	    	String url = "http://www.altsasbachernetz.de/";

	    	 mywebView = (WebView)parentView. findViewById(R.id.webview);
	    	 myProgressBar=(ProgressBar)parentView.findViewById(R.id.progressBar1);
	    	 
	    	//Button back=(Button)parentView.findViewById(R.id.BackBtn);
	    	//Button forward=(Button)parentView.findViewById(R.id.ForwardBtn);
	
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
	    	
	    	mywebView.setWebChromeClient(new WebChromeClient() {
		           public void onProgressChanged(WebView view, int progress) {
		        	   //LenderTVLoadUrl.this.setTitle("Loading...");
                      MenuActivity.myactivity.setProgress(progress * 100);
		        	   myProgressBar.setVisibility(View.VISIBLE);


		               if(progress == 100)
		               {
		            	   myProgressBar.setVisibility(View.INVISIBLE);

		            	 //  LenderTVLoadUrl.this.setTitle(R.string.app_name);
		               }
		            }
		         });
	    	
	        return parentView;
	    }//end of oncreate
	  
	}
