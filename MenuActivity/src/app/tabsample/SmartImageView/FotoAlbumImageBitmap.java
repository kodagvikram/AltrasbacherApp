package app.tabsample.SmartImageView;
import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class FotoAlbumImageBitmap implements SmartImage {
	private static final int CONNECT_TIMEOUT = 5000;
	private static final int READ_TIMEOUT = 10000;

	private static WebImageCache webImageCache;

	private String url;

	public FotoAlbumImageBitmap(String url) {
		this.url = url;
	}

	public Bitmap getBitmap(Context context) {
		// Don't leak context
		if (webImageCache == null) {
			webImageCache = new WebImageCache(context);
		}

		// Try getting bitmap from cache first
		Bitmap bitmap = null;
		if (url != null) {
			bitmap = webImageCache.get(url);
			if (bitmap == null) {
				bitmap = getBitmapFromUrl(url);
				if (bitmap != null) {
					webImageCache.put(url, bitmap);
					bitmap = null;
				}
			}
		}
		

		 if(bitmap!=null)
		 {
			 int width = bitmap.getWidth();
			    int height = bitmap.getHeight();
			    float scaleWidth = ((float) 640) / width;
			    float scaleHeight = ((float) 382) / height;
			    // create a matrix for the manipulation
			    Matrix matrix = new Matrix();
			    // resize the bit map
			    matrix.postScale(scaleWidth, scaleHeight);
			    // recreate the new Bitmap
			    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
			    bitmap= resizedBitmap;
		 }
		
		return bitmap;
	}

	private Bitmap getBitmapFromUrl(String url) {
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
		
		int width = bitmap.getWidth();
	    int height = bitmap.getHeight();
	    float scaleWidth = ((float) 640) / width;
	    float scaleHeight = ((float)382) / height;
	    // create a matrix for the manipulation
	    Matrix matrix = new Matrix();
	    // resize the bit map
	    matrix.postScale(scaleWidth, scaleHeight);
	    // recreate the new Bitmap
	    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
	    bitmap= resizedBitmap;
		
		return bitmap;
	}

	public static void removeFromCache(String url) {
		if (webImageCache != null) {
			webImageCache.remove(url);
		}
	}
}
