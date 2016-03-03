package com.MWC.Altsasbacher;


import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import app.MWC.AsyncImageload.ImageLoader;
import app.tabsample.SmartImageView.NormalSmartImageView;
import app.tabsample.SmartImageView.SinglephotoAlbumBitmap;

import com.MWC.AltsasbacherAppVO.fotoalbenVO;
import com.imagezoom.ImageAttacher;
import com.imagezoom.ImageAttacher.OnMatrixChangedListener;
import com.imagezoom.ImageAttacher.OnPhotoTapListener;

public class FotoViewerAdapter  extends PagerAdapter{
	Activity context;
	 public ArrayList<fotoalbenVO> fotoArrayList;
	 
	 ImageView imageDetail;
	 Matrix matrix = new Matrix();
	 Matrix savedMatrix = new Matrix();
	 PointF startPoint = new PointF();
	 PointF midPoint = new PointF();
	 float oldDist = 1f;
	 static final int NONE = 0;
	 static final int DRAG = 1;
	 static final int ZOOM = 2;
	 int mode = NONE;
	 public ImageLoader imageLoader;

	 public FotoViewerAdapter(Activity context, ArrayList<fotoalbenVO> fotoArrayList) {
	  this.fotoArrayList = fotoArrayList;
	  this.context = context;
	  imageLoader = new ImageLoader(context.getApplicationContext());
	 }

	 public int getCount() {
	  return fotoArrayList.size();
	 }

	 public Object instantiateItem(View collection, int position) {
		 
		 LayoutInflater inflater =context.getLayoutInflater();
			View rowView = inflater.inflate(R.layout.fotoviewerxml, null,
					true);
			
			fotoalbenVO fotovo=fotoArrayList.get(position);
			
			 TouchImageView img1 = new TouchImageView(context);
	         img1.setImageResource(R.drawable.transparentimageicon);
			
		NormalSmartImageView img=(NormalSmartImageView)rowView.findViewById(R.id.singlefoto);
		ProgressBar myProgressBar=(ProgressBar)rowView.findViewById(R.id.SingelphotoAlbumViewerprogressBar);
		

		 try {
			 
			 
				if (AppUtils.isNetworkAvailable(MenuActivity.myactivity)) 
				{
					 imageLoader.DisplayImage("http://altsasbacher.de/"+fotovo.albenSource,img,myProgressBar); 
					
					//img.setSingleFotoImageURL("http://altsasbacher.de/"+fotovo.albenSource,myProgressBar,img);
						
				}
				else
				{
					SinglephotoAlbumBitmap bitmapClass=new SinglephotoAlbumBitmap("http://altsasbacher.de/"+fotovo.albenSource);
					Bitmap bm=bitmapClass.getBitmap(MenuActivity.myactivity);
					if(bm!=null)
					{
						ImageView image=img;
						BitmapDrawable ob = new BitmapDrawable(MenuActivity.myactivity.getResources(), bm);
						//image.setBackgroundDrawable(ob);
						image.setImageBitmap(bm);
						myProgressBar.setVisibility(View.INVISIBLE);
					}
					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			e.printStackTrace();
			ImageView image=img;
			image.setImageResource(R.drawable.incorrect_problems_icon);
			myProgressBar.setVisibility(View.INVISIBLE);
			}
		
	  ((ViewPager) collection).addView(rowView, 0);
	  
	  return rowView;
	 }

	 @Override
	 public void destroyItem(View arg0, int arg1, Object arg2) {
	  ((ViewPager) arg0).removeView((View) arg2);
	 }

	 @Override
	 public boolean isViewFromObject(View arg0, Object arg1) {
	  return arg0 == ((View) arg1);
	 }

	 @Override
	 public Parcelable saveState() {
	  return null;
	 }
	 
	 public void usingSimpleImage(ImageView imageView) {
	        ImageAttacher mAttacher = new ImageAttacher(imageView);
	        ImageAttacher.MAX_ZOOM = 2.0f; // Double the current Size
	        ImageAttacher.MIN_ZOOM = 0.5f; // Half the current Size
	        MatrixChangeListener mMaListener = new MatrixChangeListener();
	        mAttacher.setOnMatrixChangeListener(mMaListener);
	        PhotoTapListener mPhotoTap = new PhotoTapListener();
	        mAttacher.setOnPhotoTapListener(mPhotoTap);
	    }

	    private class PhotoTapListener implements OnPhotoTapListener {

	        @Override
	        public void onPhotoTap(View view, float x, float y) {
	        }
	    }

	    private class MatrixChangeListener implements OnMatrixChangedListener {

	        @Override
	        public void onMatrixChanged(RectF rect) {

	        }
	    }
	}