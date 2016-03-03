package com.MWC.Altsasbacher;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FormulareFrontAdapter extends ArrayAdapter<String> {
	private final Activity context;
	private final String[] web;
	private final Integer[] imageId;

	public FormulareFrontAdapter(Activity context,
	String[] web, Integer[] imageId) {
	super(context, R.layout.formulareadapterxml, web);
	this.context = context;
	this.web = web;
	this.imageId = imageId;
	 
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.formulareadapterxml, null, true);
	TextView txtTitle = (TextView) rowView.findViewById(R.id.formularfronttextview);
	 
	ImageView imageView = (ImageView) rowView.findViewById(R.id.formularefrontimageview);
	txtTitle.setText(web[position]);
	 
	imageView.setImageResource(imageId[position]);
	return rowView;
	}
	}