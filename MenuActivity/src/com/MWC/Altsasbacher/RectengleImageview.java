package com.MWC.Altsasbacher;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
public class RectengleImageview extends ImageView {
	 public RectengleImageview(Context context) {
	        super(context);
	    }

	    public RectengleImageview(Context context, AttributeSet attrs) {
	        super(context, attrs);
	    }

	    public RectengleImageview(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	    }

	    @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()-150); //Snap to width
	    }
	}