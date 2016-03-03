package com.MWC.Altsasbacher;

import java.util.ArrayList;

import com.MWC.AltsasbacherAppVO.FSJBerichteVO;
import com.MWC.AltsasbacherAppVO.LendertvVO;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import app.MWC.AsyncImageload.ImageLoader;
import app.tabsample.SmartImageView.NormalSmartImageView;
import app.tabsample.SmartImageView.SinglephotoAlbumBitmap;

public class FSJBerichteAdapter extends BaseAdapter {
	public Activity context;
	public ArrayList<FSJBerichteVO> list;
	public ProgressBar myProgressBar;
	ImageLoader imageLoader;
	public FSJBerichteAdapter(Activity context,ArrayList<FSJBerichteVO> list) {
		// TODO Auto-generated constructor stub
		super();
		this.context=context;
		this.list=list;
		imageLoader=new ImageLoader(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater =context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.fsjberichteadapterxml, null,
				true);
		
		FSJBerichteVO fsjvo=list.get(position);

		TextView fsjtextview=(TextView)rowView.findViewById(R.id.fsjdatetextview);
		ImageView imageView=(ImageView)rowView.findViewById(R.id.RSSFEEDIMAGEVIEW);
        TextView fsjtitleview=(TextView)rowView.findViewById(R.id.fsjtitletextview);
         myProgressBar=(ProgressBar)rowView.findViewById(R.id.RSSFEEDPROGRESSBAR);
        
		//lenderimage.setImageResource(lendertvvo.lendertvimageid);
		 
		 try {
			 
			 if(fsjvo.image_url!=null&&!fsjvo.image_url.equalsIgnoreCase(""))
				 imageLoader.DisplayImage(fsjvo.image_url, imageView, myProgressBar);
			         else
			         {
			        	 //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,0);
			        	 imageView.setVisibility(View.GONE);
			        	 myProgressBar.setVisibility(View.GONE);
			         }
				
			} catch (Exception e) {
				// TODO: handle exception
			e.printStackTrace();
//			ImageView image=img;
//			image.setImageResource(R.drawable.incorrect_problems_icon);
//			myProgressBar.setVisibility(View.INVISIBLE);
			}
			fsjtextview.setText(fsjvo.pubDate);
			fsjtitleview.setText(stripHtml(fsjvo.title));
			
			fsjtitleview.setBackgroundColor(getColorWithAlpha(Color.WHITE, 0.8f));

		return rowView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }
	
	public String stripHtml(String html) {
	    return Html.fromHtml(html).toString();
	}
	
}
