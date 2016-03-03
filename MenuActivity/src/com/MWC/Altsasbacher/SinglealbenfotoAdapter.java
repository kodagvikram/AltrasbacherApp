package com.MWC.Altsasbacher;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import app.MWC.AsyncImageload.ImageLoader;
import app.tabsample.SmartImageView.NormalSmartImageView;
import app.tabsample.SmartImageView.SinglephotoAlbumBitmap;

import com.MWC.AltsasbacherAppVO.fotoalbenVO;
import com.squareup.picasso.Picasso;

public class SinglealbenfotoAdapter extends BaseAdapter {
	public Activity context;
	public ArrayList<fotoalbenVO> list;
	 public  ImageLoader imageLoader; 
	// public ProgressBar ImageProgressBar=null;
	// public View myView=null;

	public SinglealbenfotoAdapter(Activity context, ArrayList<fotoalbenVO> list) {
		// TODO Auto-generated constructor stub
		super();
		this.context = context;
		this.list = list;
		 imageLoader = new ImageLoader(context.getApplicationContext());
	}

	public SinglealbenfotoAdapter() {

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater
				.inflate(R.layout.singlealbenfotsxml, null, true);
		// myView=rowView;

		fotoalbenVO fotovo = list.get(position);
		ProgressBar ImageProgressBar = (ProgressBar) rowView
				.findViewById(R.id.SingleImageprogressBar);
		NormalSmartImageView albenimage = (NormalSmartImageView) rowView
				.findViewById(R.id.singlealbenImageview);

		try {
			if (AppUtils.isNetworkAvailable(MenuActivity.myactivity)) {

				 imageLoader.DisplayImage("http://altsasbacher.de/"+fotovo.albenSource,albenimage,ImageProgressBar);
				// albenimage.setSingleFotoImageURL("http://altsasbacher.de/"+fotovo.albenSource,ImageProgressBar,albenimage);
			} else {
				SinglephotoAlbumBitmap bitmapClass = new SinglephotoAlbumBitmap(
						"http://altsasbacher.de/" + fotovo.albenSource);
				Bitmap bm = bitmapClass.getBitmap(MenuActivity.myactivity);
				if (bm != null) {
					ImageView image = albenimage;
					BitmapDrawable ob = new BitmapDrawable(
							MenuActivity.myactivity.getResources(), bm);
					image.setBackgroundDrawable(ob);
					ImageProgressBar.setVisibility(View.INVISIBLE);
				} else {
					ImageView image = albenimage;
					image.setImageResource(R.drawable.incorrect_problems_icon);
					ImageProgressBar.setVisibility(View.INVISIBLE);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ImageView image = albenimage;
			image.setImageResource(R.drawable.incorrect_problems_icon);
			ImageProgressBar.setVisibility(View.INVISIBLE);
		}

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
