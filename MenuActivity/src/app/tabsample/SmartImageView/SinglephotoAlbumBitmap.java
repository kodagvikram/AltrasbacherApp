package app.tabsample.SmartImageView;

import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

import com.MWC.Altsasbacher.SinglealbenfotoAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.View;
import android.widget.ProgressBar;


public class SinglephotoAlbumBitmap  implements SmartImage {
	private static final int CONNECT_TIMEOUT = 5000;
	private static final int READ_TIMEOUT = 10000;
 
	private static WebImageCache webImageCache;

	private String url;
	  public ProgressBar myProgressBar; 
	public SinglephotoAlbumBitmap(String url) {
		this.url = url;
		this.myProgressBar=myProgressBar;
	}

	public Bitmap getBitmap(Context context) {
		// Don't leak context
		//myProgressBar.setVisibility(View.VISIBLE);
		if (webImageCache == null) {
			webImageCache = new WebImageCache(context);
		}

		// Try getting bitmap from cache first
		Bitmap bitmap = null;
		if (url != null) {
			bitmap = webImageCache.get(url);
			if (bitmap == null) {
				try {
					bitmap = getBitmapFromUrl(url);	
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				if (bitmap != null) {
					webImageCache.put(url, bitmap);
					bitmap = null;
				}
			}
		}
		

		 if(bitmap!=null)
		 {
			 
			 int srcWidth = bitmap.getWidth();
			 int srcHeight = bitmap.getHeight();
			 
			 if(srcWidth>1050||srcHeight>1050)
			 {
			 int dstWidth = (int)(srcWidth*0.8f);
			 int dstHeight = (int)(srcHeight*0.8f);
			 Bitmap dstBitmap = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, true);
			 
//			 int width = bitmap.getWidth();
//			    int height = bitmap.getHeight();
//			    float scaleWidth = ((float) 540) / width;
//			    float scaleHeight = ((float)280) / height;
//			    // create a matrix for the manipulation
//			    Matrix matrix = new Matrix();
//			    // resize the bit map
//			    matrix.postScale(scaleWidth, scaleHeight);
//			    // recreate the new Bitmap
//			    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
//			   
			    
			    bitmap= dstBitmap;
			 }//end of if
		 }
		 //myProgressBar.setVisibility(View.GONE);
		return bitmap;
	}

	public Bitmap getBitmapFromUrl(String url) {
		Bitmap bitmap = null;
		try {
			URLConnection conn = new URL(url).openConnection();
			conn.setConnectTimeout(CONNECT_TIMEOUT);
			conn.setReadTimeout(READ_TIMEOUT);
			bitmap = BitmapFactory
					.decodeStream((InputStream) conn.getContent());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 
		 int srcWidth = bitmap.getWidth();
		 int srcHeight = bitmap.getHeight();
		 if(srcWidth>1050||srcHeight>1050)
		 {
		 int dstWidth = (int)(srcWidth*0.8f);
		 int dstHeight = (int)(srcHeight*0.8f);
		 Bitmap dstBitmap = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, true);
		 
//		 int width = bitmap.getWidth();
//		    int height = bitmap.getHeight();
//		    float scaleWidth = ((float) 540) / width;
//		    float scaleHeight = ((float)280) / height;
//		    // create a matrix for the manipulation
//		    Matrix matrix = new Matrix();
//		    // resize the bit map
//		    matrix.postScale(scaleWidth, scaleHeight);
//		    // recreate the new Bitmap
//		    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
//		   
		    
		    bitmap= dstBitmap;
		 }
		return bitmap;
	}
	
	
	public Bitmap getBitmapFromCatche(Context context) {
		// Don't leak context
		if (webImageCache == null) {
			webImageCache = new WebImageCache(context);
		}

		// Try getting bitmap from cache first
		Bitmap bitmap = null;
		if (url != null) {
			
			try {
				bitmap = webImageCache.get(url);	
			} catch (Exception e) {
				// TODO: handle exception
				bitmap=null;
			}
			
		}
		
		
		return bitmap;
	}

	public static void removeFromCache(String url) {
		if (webImageCache != null) {
			webImageCache.remove(url);
		}
	}
}
