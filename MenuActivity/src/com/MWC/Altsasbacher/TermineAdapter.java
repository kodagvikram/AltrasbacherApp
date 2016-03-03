package com.MWC.Altsasbacher;

import java.util.ArrayList;

import com.MWC.AltsasbacherAppVO.TermineListVO;
import com.MWC.AltsasbacherAppVO.WichtigeListVo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TermineAdapter extends BaseAdapter {
	public Activity context;
	public ArrayList<TermineListVO> list;
	
	public TermineAdapter(Activity context,ArrayList<TermineListVO> list) {
		// TODO Auto-generated constructor stub
		super();
		this.context=context;
		this.list=list;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater =context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.terminelistxml, null,
				true);
		
		TermineListVO terminevo=list.get(position);
		
		TextView uppertitleview=(TextView)rowView.findViewById(R.id.terminelisttitle);
		TextView upperdateview=(TextView)rowView.findViewById(R.id.terminelistdatetime);
		
		uppertitleview.setText(terminevo.terminetitle);
		upperdateview.setText(terminevo.terminedatetime);
		
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

