package com.MWC.Altsasbacher;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import app.MWC.AsyncImageload.BitmapImageLoader;
import app.tabsample.SmartImageView.SinglephotoAlbumBitmap;

import com.MWC.AltsasbacherAppVO.fotoalbenVO;
public class FotoViewewActivity extends Activity {

	TextView fotocounttext;
	public ArrayList<fotoalbenVO> fotoArrayList=new ArrayList<fotoalbenVO>();
	ExtendedViewPager myPager ;
	 public BitmapImageLoader imageLoader;
	 ProgressBar progressBar1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foto_viewew);
		imageLoader = new BitmapImageLoader(getApplicationContext());
		Bundle extra = getIntent().getExtras();
		int imgpos = extra.getInt("imageposition");

		fotoArrayList = (ArrayList<fotoalbenVO>) getIntent()
				.getSerializableExtra("SINGLEALBUMPHOTS");

		fotocounttext = (TextView) findViewById(R.id.fotoViewercoutnttextview);

		
		if (fotoArrayList.size() > 0) {

			FotoViewerAdapter adapter = new FotoViewerAdapter(this,
					fotoArrayList);
			myPager = (ExtendedViewPager) findViewById(R.id.myfivepanelpager);
			myPager.setAdapter(new TouchImageAdapter());
			myPager.setCurrentItem(imgpos);

			fotocounttext.setText((myPager.getCurrentItem() + 1) + " von "
					+ fotoArrayList.size());

			myPager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int arg0) {
					// TODO Auto-generated method stub
					int i = myPager.getCurrentItem();
					i++;
					fotocounttext.setText((myPager.getCurrentItem() + 1)
							+ " von " + fotoArrayList.size());
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub

				}
			});
		}
		
         Button fotosharebtn = (Button) findViewById(R.id.fotoViewerSharebtn);
		fotosharebtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(fotoArrayList.size()>0)
				{
					fotoalbenVO fotovo=fotoArrayList.get(myPager.getCurrentItem());
					try {
						 ImageView imageView=new ImageView(FotoViewewActivity.this);
						//SinglephotoAlbumBitmap bitmapClass=new SinglephotoAlbumBitmap("http://altsasbacher.de/"+fotovo.albenSource);
						Bitmap bm=imageLoader.getOfflineBitmab("http://altsasbacher.de/"+fotovo.albenSource);
						//Send the file
						if(bm!=null)
						{
						Intent emailIntent = new Intent(Intent.ACTION_SEND);
						emailIntent.setType("text/html");
						emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
						emailIntent.putExtra(Intent.EXTRA_TEXT, "");
						
						String path = Images.Media.insertImage(getContentResolver(), bm,"", null);
					    Uri screenshotUri = Uri.parse(path);
						emailIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
						try {
							startActivity(Intent.createChooser(emailIntent, "Foto E-Mail verschicken.."));
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						}		
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				
				}//end of if
				
			}
		});
		
	}// end of oncreate()
	
	
	  class TouchImageAdapter extends PagerAdapter {

	        @Override
	        public int getCount() {
	        	return fotoArrayList.size();
	        }

	        @Override
	        public View instantiateItem(ViewGroup container, int position) {
	        	
	        	fotoalbenVO fotovo=fotoArrayList.get(position);
	    		
	        	TouchImageView img = new TouchImageView(container.getContext());
	        	
	            try {
	   			 
	            	//imageLoader.DisplayImage("http://altsasbacher.de/"+fotovo.albenSource,img,);
					  progressBar1 = new ProgressBar(FotoViewewActivity.this, null, android.R.attr.progressBarStyle);
					  progressBar1.setVisibility(View.VISIBLE);
					  img.setImageResource(R.drawable.transparentimageicon,"http://altsasbacher.de/"+fotovo.albenSource,img);
					  imageLoader.DisplayImage("http://altsasbacher.de/"+fotovo.albenSource,img,progressBar1);
		            container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		            
					
				} catch (Exception e) {
					// TODO: handle exception
				e.printStackTrace();
				 Bitmap bimtBitmap = BitmapFactory.decodeResource(getResources(),
			                R.drawable.incorrect_problems_icon);
				 img.setImageBitmap(bimtBitmap);
		         container.addView(img, 100, 100);
		            
				}

	            
	            return img;
	        }//end of instantiate()

	        @Override
	        public void destroyItem(ViewGroup container, int position, Object object) {
	            container.removeView((View) object);
	        }

	        @Override
	        public boolean isViewFromObject(View view, Object object) {
	            return view == object;
	        }

	    }
	
}//end of Activity
