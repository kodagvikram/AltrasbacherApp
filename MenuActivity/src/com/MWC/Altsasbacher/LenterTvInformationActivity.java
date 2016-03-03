package com.MWC.Altsasbacher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ParseException;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import app.MWC.AsyncImageload.ImageLoader;
import app.tabsample.SmartImageView.NormalSmartImageView;
import app.tabsample.SmartImageView.SinglephotoAlbumBitmap;

import com.MWC.AltsasbacherAppVO.LendertvVO;

public class LenterTvInformationActivity extends Activity {

	public TextView lendertitletextview;
	//public NormalSmartImageView lenderinfoImageview;
	public Button lenderplaybtn,lendertvsharebtn;
	  public LendertvVO lendertvVO=new LendertvVO();
	  public WebView webview=null;
      public ProgressBar myProgressBar=null;
	  public ImageLoader imageLoader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lenter_tv_information);
		imageLoader=new ImageLoader(this);
		
		 lendertvVO=LendertvFragement.lenderinfoVO;
		 ImageView	lenderinfoImageview=(ImageView)findViewById(R.id.LendertvBackgroundImage);
            lenderplaybtn=(Button)findViewById(R.id.lenderinfoplaybtn);
           lendertvsharebtn=(Button)findViewById(R.id.lendertvsharebtn);
           myProgressBar=(ProgressBar)findViewById(R.id.LenderTVProgressBar);
           
          // lenderinfoImageview.setBackgroundImageURL(lendertvVO.preview,myProgressBar, lenderinfoImageview);;
          // lenderinfoImageview.setBackgroundResource(LendertvFragement.lenderinfoVO.lendertvimageid);
          
           try {
        	   
        	   imageLoader.DisplayImage(lendertvVO.preview, lenderinfoImageview, myProgressBar);
   			
   		} catch (Exception e) {
   			// TODO: handle exception
   		e.printStackTrace();
//   		ImageView image=img;
//   		image.setImageResource(R.drawable.incorrect_problems_icon);
//   		myProgressBar.setVisibility(View.INVISIBLE);
   		}
           
           
           //*********************************************************************************************
           String name = "";
   		String details = "";
   		String pubDate = "";
   		String video = "";
   		String preview = "";

   		webview = (WebView) findViewById(R.id.LenderTVwebView);
   		webview.getSettings().setJavaScriptEnabled(true);
   		webview.getSettings().setAllowFileAccess(true);
   		//webview.getSettings().setPluginsEnabled(true);
   		webview.getSettings().setBuiltInZoomControls(false);

   		Intent intent = getIntent();
   		if (null != intent) {
   			name = "<font face='HelveticaNeue-Light' size='8'>"+lendertvVO.title+"</font>";
   			details = lendertvVO.description;
   			pubDate = lendertvVO.pubDate.replace(" +0200", "");
   			//pubDate=pubDate.replaceAll(" +0200", " ");
		    
   			DateFormat readFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");

		    //DateFormat writeFormat = new SimpleDateFormat( "dd-MMM-yyyy");
		    DateFormat writeFormat2 = new SimpleDateFormat( "dd MMM yyyy HH:mm");
		    //writeFormat2.setTimeZone(TimeZone.getTimeZone("UTC"));
		    Date date = null;
		    try {
		       date = readFormat.parse( pubDate);
		    } catch ( ParseException e1 ) {
		        e1.printStackTrace();
		    } catch (java.text.ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		    String formattedDate = "";
		    if( date != null ) {
		    	//rVo.pubDate = writeFormat.format( date );
		    	pubDate = writeFormat2.format( date );
		    	
		    }
   			
   		}
   		webview.loadDataWithBaseURL(null,"<br>"+name+"<br>"+pubDate +"<br>"+stripHtml(details),"text/html", "utf-8",null);
   		webview.setWebViewClient(new WebViewClient() {
    	    @Override
    	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
    	        //view.loadUrl(url);
    	        try {
    	        	Intent intent=new Intent(MenuActivity.myactivity.getApplicationContext(),LenderTVLoadUrl.class);
    	        	intent.putExtra("LENTERTVURL",url);
    	        	intent.putExtra("LENTERTVTEXT","LenderTv.de- Das Schulfernsehe...");
                    startActivity(intent); 	
     	
				} catch (Exception e) {
					// TODO: handle exception
			    e.printStackTrace();
				}
    	           	        
    	        return true;
    	    }
    	});
           //*********************************************************************************************
           
           lenderplaybtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (AppUtils.isNetworkAvailable(MenuActivity.myactivity)) 
				{
                  if(LendertvFragement.lenderinfoVO.enclosure!=null)
                  {
                	  Intent intent=new Intent(LenterTvInformationActivity.this,LendertvVideoActivity.class);
      				startActivity(intent);	  
                  }
				}//end of if
				else
				{
					//AppUtils.ShowAlertDialog();
				}
			}
		});
           
           lendertvsharebtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			        gmailintent();	
			}
		});
           
	}//end of on create
	
public String stripHtml(String html) {
	    return Html.fromHtml(html).toString();
	}

public void gmailintent() {
		
		
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL,
		new String[] { "Kontakt@LenderTV.de" });
		i.putExtra(Intent.EXTRA_SUBJECT, lendertvVO.title);
		i.putExtra(Intent.EXTRA_TEXT, "sent from android phone ");
		
		try {
		this.startActivity(Intent.createChooser(i, "Feedback zur Sendung geben.."));
		} catch (android.content.ActivityNotFoundException ex) {
		Toast.makeText(this, "There are no email clients installed.",
		Toast.LENGTH_SHORT).show();
		}

	}// end of gmailintent

	
	
}
