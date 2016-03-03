package com.MWC.Altsasbacher;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

public class LendertvVideoActivity extends Activity {
	private GestureDetector mGestureDetector;
	private ScrollView mScrollView;	
	
	private FrameLayout mTargetView;
	private FrameLayout mContentView;
	private CustomViewCallback mCustomViewCallback;	
	private View mCustomView;
	private MyChromeClient mClient;
    public String htmlString="";
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lendertv_video);
	
	String Videoname=LendertvFragement.lenderinfoVO.enclosure;
	htmlString=	"<video id='video' width='"+330+"' height='320' controls>	<source src='"+Videoname+"' type='video/mp4'> </video>";

		WebView mWebView = (WebView) findViewById(R.id.webView1);
		mClient = new MyChromeClient();
		mWebView.setWebChromeClient(mClient);
		//mWebView.loadUrl("file:///android_asset/test.html");
		
		mWebView.loadDataWithBaseURL("", htmlString , "text/html", "utf-8", "");
		
		mContentView = (FrameLayout) findViewById(R.id.main_content);
		mTargetView = (FrameLayout)findViewById(R.id.target_view);

	}
	
	class MyChromeClient extends WebChromeClient {

		@Override
		public void onShowCustomView(View view, CustomViewCallback callback) {		
		    mCustomViewCallback = callback;
			mTargetView.addView(view);
			mCustomView = view;
			mContentView.setVisibility(View.GONE);
			mTargetView.setVisibility(View.VISIBLE);
			mTargetView.bringToFront();
		}

		@Override
		public void onHideCustomView() {
			if (mCustomView == null)
				return;

			mCustomView.setVisibility(View.GONE);
			mTargetView.removeView(mCustomView);
			mCustomView = null;
			mTargetView.setVisibility(View.GONE);			
			mCustomViewCallback.onCustomViewHidden();			
			mContentView.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void onBackPressed(){
		
			finish();
		
	}
}
