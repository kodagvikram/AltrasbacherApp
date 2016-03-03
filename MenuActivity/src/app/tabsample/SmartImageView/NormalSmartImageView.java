package app.tabsample.SmartImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.MWC.Altsasbacher.FotoViewewActivity;
import com.MWC.Altsasbacher.FotoalbenActivity;
import com.MWC.Altsasbacher.FotoalbenFragement;
import com.MWC.Altsasbacher.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class NormalSmartImageView extends ImageView {
	private static final int LOADING_THREADS = 4;
	private static ExecutorService threadPool = Executors
			.newFixedThreadPool(LOADING_THREADS);

	private SmartImageTask currentTask;

	public NormalSmartImageView(Context context) {
		super(context);
		this.setBackgroundResource(0);
	}

	
	public NormalSmartImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NormalSmartImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	// Helpers to set image by URL
	public void setImageUrl(String url) {
		setImage(new WebImage(url));
	}
	
	// Helpers to set image by URL//****************own function
		public void setSingleFotoImageURL(String url,ProgressBar myProgressBar,ImageView imageView) {
			//myProgressBar.setVisibility(View.VISIBLE);
			try {
				setBackgroundImageURL( new SinglephotoAlbumBitmap(url), myProgressBar,imageView);	
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			//myProgressBar.setVisibility(View.INVISIBLE);
		}
	
		// Helpers to set image by URL//****************own function
				public void setLenderTVImageURL(String url,ProgressBar myProgressBar,ImageView imageView) {
					//myProgressBar.setVisibility(View.VISIBLE);
					try {
						setLenderTVBackgroundImageURL( new SinglephotoAlbumBitmap(url), myProgressBar,imageView);	
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
					//myProgressBar.setVisibility(View.INVISIBLE);
				}
		
		
		// Helpers to set image by URL//****************own function Foto Alben
				public void setPhotoAlbummageURL(String url,ProgressBar myProgressBar) {
					setImage2( new FotoAlbumImageBitmap(url),myProgressBar);
				}

				// Helpers to set image by URL//****************own function Foto Alben
				public void setPhotoViwermageURL(String url) {
					try {
						setImage( new PhotoAlbumViewerBitmap(url));
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
				}
				
				// Helpers to set image by URL//****************own function Foto Alben
				public void setBackgroundImageURL(String url,ProgressBar myProgressBar,ImageView myimage) {
					setBackgroundImageURL( new WebImage(url),myProgressBar,myimage);
				}
				
	public void setImageUrl(String url,
			SmartImageTask.OnCompleteListener completeListener) {
		setImage(new WebImage(url), completeListener);
	}

	public void setImageUrl(String url, final Integer fallbackResource) {
		setImage(new WebImage(url), fallbackResource);
	}

	public void setImageUrl(String url, final Integer fallbackResource,
			SmartImageTask.OnCompleteListener completeListener) {
		setImage(new WebImage(url), fallbackResource, completeListener);
	}

	public void setImageUrl(String url, final Integer fallbackResource,
			final Integer loadingResource) {
		setImage(new WebImage(url), fallbackResource, loadingResource);
	}

	public void setImageUrl(String url, final Integer fallbackResource,
			final Integer loadingResource,
			SmartImageTask.OnCompleteListener completeListener) {
		setImage(new WebImage(url), fallbackResource, loadingResource,
				completeListener);
	}

	// Helpers to set image by contact address book id
	public void setImageContact(long contactId) {
		setImage(new ContactImage(contactId));
	}

	public void setImageContact(long contactId, final Integer fallbackResource) {
		setImage(new ContactImage(contactId), fallbackResource);
	}

	public void setImageContact(long contactId, final Integer fallbackResource,
			final Integer loadingResource) {
		setImage(new ContactImage(contactId), fallbackResource,
				fallbackResource);
	}

	// Set image using SmartImage object
	public void setImage(final SmartImage image) {
		
		try {
			setImage(image, null, null, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	// Set image using SmartImage object
		public void setImage2(final SmartImage image,ProgressBar myProgressBar) {
			setImage2(image, null, null, null,myProgressBar);
		}

		public void setBackgroundImageURL(final SmartImage image,ProgressBar myProgressBar,ImageView myimage) {//******************Background image
			try {
			
				setBackgroundImageURL(image, null, null, null,myProgressBar,myimage);
			} catch (Exception e) {
				// TODO: handle exception
			e.printStackTrace();
			}
			
		}
		
		public void setLenderTVBackgroundImageURL(final SmartImage image,ProgressBar myProgressBar,ImageView myimage) {//******************Background image
			try {
			
				setLenderTVBackgroundImageURL(image, null, null, null,myProgressBar,myimage);
			} catch (Exception e) {
				// TODO: handle exception
			e.printStackTrace();
			}
			
		}

	
	public void setImage(final SmartImage image,
			final SmartImageTask.OnCompleteListener completeListener) {
		setImage(image, null, null, completeListener);
	}

	public void setImage(final SmartImage image, final Integer fallbackResource) {
		setImage(image, fallbackResource, fallbackResource, null);
	}

	public void setImage(final SmartImage image,
			final Integer fallbackResource,
			SmartImageTask.OnCompleteListener completeListener) {
		setImage(image, fallbackResource, fallbackResource, completeListener);
	}

	public void setImage(final SmartImage image,
			final Integer fallbackResource, final Integer loadingResource) {
		setImage(image, fallbackResource, loadingResource, null);
	}

	public void setImage(final SmartImage image,
			final Integer fallbackResource, final Integer loadingResource,
			final SmartImageTask.OnCompleteListener completeListener) {
		
		try {
			
		
		// Set a loading resource
		if (loadingResource != null) {
			setImageResource(loadingResource);
		}

		// Cancel any existing tasks for this image view
		if (currentTask != null) {
			currentTask.cancel();
			currentTask = null;
		}

		// Set up the new task
		currentTask = new SmartImageTask(getContext(), image);
		currentTask
				.setOnCompleteHandler(new SmartImageTask.OnCompleteHandler() {
					@Override
					public void onComplete(Bitmap bitmap) {
						if (bitmap != null) {
							setImageBitmap(bitmap);
							bitmap = null;
						} else {
							// Set fallback resource
							if (fallbackResource != null) {
								setImageResource(fallbackResource);
							}
						}

						if (completeListener != null) {
							completeListener.onComplete();
							
						}
					}
				});

		// Run the task in a threadpool
		threadPool.execute(currentTask);
		
		} catch (Exception e) {
			// TODO: handle exception
		e.printStackTrace();
		}
	}
	
	//*********************************************************************
	public void setImage2(final SmartImage image,
			final Integer fallbackResource, final Integer loadingResource,
			final SmartImageTask.OnCompleteListener completeListener,final ProgressBar myProgressBar) {
		// Set a loading resource
		myProgressBar.setVisibility(View.VISIBLE);
		if (loadingResource != null) {
			setImageResource(loadingResource);
		}

		// Cancel any existing tasks for this image view
		if (currentTask != null) {
			currentTask.cancel();
			currentTask = null;
		}

		// Set up the new task
		currentTask = new SmartImageTask(getContext(), image);
		currentTask
				.setOnCompleteHandler(new SmartImageTask.OnCompleteHandler() {
					@Override
					public void onComplete(Bitmap bitmap) {
						
						if (bitmap != null) {
							setImageBitmap(bitmap);
							bitmap = null;
						} else {
							// Set fallback resource
							if (fallbackResource != null) {
								setImageResource(fallbackResource);
							}
						}

						if (completeListener != null) {
							completeListener.onComplete();
							
						}
						myProgressBar.setVisibility(View.INVISIBLE);
					}
					
				});

		// Run the task in a threadpool
		threadPool.execute(currentTask);
	}

//************************************************************************
	//*********************************************************************
		public void setBackgroundImageURL(final SmartImage image,
				final Integer fallbackResource, final Integer loadingResource,
				final SmartImageTask.OnCompleteListener completeListener,final ProgressBar myProgressBar,final ImageView myimageimage) {
			// Set a loading resource
			
			try {
				
			myProgressBar.setVisibility(View.VISIBLE);
			if (loadingResource != null) {
				setImageResource(loadingResource);
			}

			// Cancel any existing tasks for this image view
			if (currentTask != null) {
				currentTask.cancel();
				currentTask = null;
			}
			
			// Set up the new task
			currentTask = new SmartImageTask(getContext(),image);
			currentTask
					.setOnCompleteHandler(new SmartImageTask.OnCompleteHandler() {
						@Override
						public void onComplete(Bitmap bitmap) {
							
							if (bitmap != null) {
								
								BitmapDrawable ob = new BitmapDrawable(
										getResources(), bitmap);
								//myimageimage
								//		.setBackgroundDrawable(ob);
								//setBackgroundDrawable(ob);
								int srcWidth = bitmap.getWidth();
								 int srcHeight = bitmap.getHeight();
								 
								 if(srcWidth>500||srcHeight>500)
								 {
									 setImageBitmap(bitmap);	 
								 }
								 else
									myimageimage
												.setBackgroundDrawable(ob);
								
								bitmap = null;
							} else {
								// Set fallback resource
								if (fallbackResource != null) {
									setImageResource(fallbackResource);
								}
								//myimageimage.setBackgroundResource(R.drawable.incorrect_problems_icon);
							}

							if (completeListener != null) {
								completeListener.onComplete();
								
							}
							myProgressBar.setVisibility(View.INVISIBLE);
						}
						
					});

			// Run the task in a threadpool
			threadPool.execute(currentTask);
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		
		}

	//************************************************************************
	
		//************************************************************************
		//*********************************************************************
			public void setLenderTVBackgroundImageURL(final SmartImage image,
					final Integer fallbackResource, final Integer loadingResource,
					final SmartImageTask.OnCompleteListener completeListener,final ProgressBar myProgressBar,final ImageView myimageimage) {
				// Set a loading resource
				
				try {
					
				myProgressBar.setVisibility(View.VISIBLE);
				if (loadingResource != null) {
					setImageResource(loadingResource);
				}

				// Cancel any existing tasks for this image view
				if (currentTask != null) {
					currentTask.cancel();
					currentTask = null;
				}
				
				// Set up the new task
				currentTask = new SmartImageTask(getContext(),image);
				currentTask
						.setOnCompleteHandler(new SmartImageTask.OnCompleteHandler() {
							@Override
							public void onComplete(Bitmap bitmap) {
								
								if (bitmap != null) {
									
									BitmapDrawable ob = new BitmapDrawable(
											getResources(), bitmap);
									//myimageimage
									//		.setBackgroundDrawable(ob);
									//setBackgroundDrawable(ob);
									
										myimageimage
													.setBackgroundDrawable(ob);
									
									bitmap = null;
								} else {
									// Set fallback resource
									if (fallbackResource != null) {
										setImageResource(fallbackResource);
									}
									//myimageimage.setBackgroundResource(R.drawable.incorrect_problems_icon);
								}

								if (completeListener != null) {
									completeListener.onComplete();
									
								}
								myProgressBar.setVisibility(View.INVISIBLE);
							}
							
						});

				// Run the task in a threadpool
				threadPool.execute(currentTask);
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			
			}

		//************************************************************************
	
	public static void cancelAllTasks() {
		threadPool.shutdownNow();
		threadPool = Executors.newFixedThreadPool(LOADING_THREADS);
	}
}