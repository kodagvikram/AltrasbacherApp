package com.MWC.Altsasbacher;

import java.util.ArrayList;

import com.MWC.AltsasbacherAppVO.AltsasbachertagVO;
import com.MWC.AltsasbacherAppVO.TermineListVO;
import com.MWC.AltsasbacherAppVO.WichtigeListVo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AltsasbachertagAdapter extends BaseAdapter {
	public Activity context;
	public ArrayList<AltsasbachertagVO> list;
	
	public AltsasbachertagAdapter(Activity context,ArrayList<AltsasbachertagVO> list) {
		// TODO Auto-generated constructor stub
		super();
		this.context=context;
		this.list=list;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater =context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.altstagadapterlistxml, null,
				true);
		
		AltsasbachertagVO altstagvo=list.get(position);
		
		TextView uppertitleview=(TextView)rowView.findViewById(R.id.altstagEventTitletextview);
		TextView upperdateview=(TextView)rowView.findViewById(R.id.altstagTimetextview);
		TextView altslocationtextview=(TextView)rowView.findViewById(R.id.altstagLocationtextview);
		
		uppertitleview.setText(altstagvo.tagtitle);
		if(altstagvo.tagenddate.equals(altstagvo.tagstartdate))
		upperdateview.setText(altstagvo.tagenddate);
		else
		upperdateview.setText(altstagvo.tagstartdate+" - "+altstagvo.tagenddate+" ");	
		
		altslocationtextview.setText(" - "+altstagvo.taglocation);
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

