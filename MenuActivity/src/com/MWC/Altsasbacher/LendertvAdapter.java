package com.MWC.Altsasbacher;

import java.util.ArrayList;

import com.MWC.AltsasbacherAppVO.LendertvVO;
import com.MWC.AltsasbacherAppVO.fotoalbenVO;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

public class LendertvAdapter  extends BaseAdapter{
	public Activity context;
	public ArrayList<LendertvVO> list;
	public ProgressBar myProgressBar;
	public ImageLoader imageLoader;
	public LendertvAdapter(Activity context,ArrayList<LendertvVO> list) {
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
		View rowView = inflater.inflate(R.layout.lendertvlistxml, null,
				true);
		
		LendertvVO lendertvvo=list.get(position);
		
		ImageView lenderimage=(ImageView)rowView.findViewById(R.id.lendertvimageview);
		//RelativeLayout imglayout=(RelativeLayout)rowView.findViewById(R.id.lendertvimagelayout);
        TextView lenderview=(TextView)rowView.findViewById(R.id.lendertvtextview);
		myProgressBar=(ProgressBar)rowView.findViewById(R.id.LendertvProgressbar);
		
        try {
						
			imageLoader.DisplayImage(lendertvvo.preview, lenderimage, myProgressBar);
			
		} catch (Exception e) {
			// TODO: handle exception
		e.printStackTrace();
//		ImageView image=img;
//		image.setImageResource(R.drawable.incorrect_problems_icon);
//		myProgressBar.setVisibility(View.INVISIBLE);
		}
        
		lenderview.setText(lendertvvo.title);
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

}
