package com.MWC.Altsasbacher;

import java.util.ArrayList;

import com.MWC.AltsasbacherAppVO.FotoAlbenTitelGalleryVO;
import com.MWC.AltsasbacherAppVO.WichtigeListVo;
import com.MWC.AltsasbacherAppVO.fotoalbenVO;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import app.MWC.AsyncImageload.ImageLoader;
import app.tabsample.SmartImageView.NormalSmartImageView;
import app.tabsample.SmartImageView.WebImage;

public class FotoalbenAdapter extends BaseAdapter {
	public Activity context;
	public ArrayList<fotoalbenVO> list;
	public ArrayList<Object> albenObjectlist;
	public ArrayList<FotoAlbenTitelGalleryVO> albenTitleArrayList;
	ImageLoader imageLoader;
	public FotoalbenAdapter(Activity context,ArrayList<FotoAlbenTitelGalleryVO> albenTitleArrayList) {
		// TODO Auto-generated constructor stub
		super();
		this.context=context;
		this.albenTitleArrayList=albenTitleArrayList;
		imageLoader=new ImageLoader(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater =context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.fotoalbengridlistxml, null,
				true);
		
		FotoAlbenTitelGalleryVO fotoVo=albenTitleArrayList.get(position);
		ProgressBar myProgressBar=(ProgressBar)rowView.findViewById(R.id.GridprogressBar);
		TextView uppertitleview=(TextView)rowView.findViewById(R.id.fotoalbentitletextview);
		ImageView albenimage=(ImageView)rowView.findViewById(R.id.fotoalbenImageview);
		ImageView albenimage2=(ImageView)rowView.findViewById(R.id.fotoalbenImageview2);
		
		
		String str=fotoVo.FotoanbenTitle;
		
		if(str!=null)
		{
			if(fotoVo.FotoanbenTitle.length()>40)
				 str=fotoVo.FotoanbenTitle.substring(0,(Math.min(fotoVo.FotoanbenTitle.length(), 40)))+"...";
			
			uppertitleview.setText(str);
	
		}
		
		try {
			myProgressBar.setVisibility(View.VISIBLE);
			imageLoader.DisplayImage("http://altsasbacher.de/Fotogalerien/"+fotoVo.FotoanbenGallary+"/images/1.jpg",albenimage,myProgressBar);
			imageLoader.DisplayImage("http://altsasbacher.de/Fotogalerien/"+fotoVo.FotoanbenGallary+"/images/2.jpg",albenimage2,myProgressBar);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}//*********************************************

				
				return rowView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return albenTitleArrayList.size();
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
