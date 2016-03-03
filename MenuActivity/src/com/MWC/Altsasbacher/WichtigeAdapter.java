package com.MWC.Altsasbacher;
import java.util.ArrayList;
import com.MWC.AltsasbacherAppVO.*;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
public class WichtigeAdapter extends BaseAdapter {
	
		public Activity context;
		public ArrayList<WichtigeListVo> list;
		
		public WichtigeAdapter(Activity context,ArrayList<WichtigeListVo> list) {
			// TODO Auto-generated constructor stub
			super();
			this.context=context;
			this.list=list;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			LayoutInflater inflater =context.getLayoutInflater();
			View rowView = inflater.inflate(R.layout.wichtigemeldungenlistxml, null,
					true);
			
			WichtigeListVo wichtigelist=list.get(position);
			
			TextView uppertitleview=(TextView)rowView.findViewById(R.id.uppertitletextview);
			TextView upperdateview=(TextView)rowView.findViewById(R.id.upperdatetimetextview);
			TextView lowertextview=(TextView)rowView.findViewById(R.id.lowertextview);
			
			uppertitleview.setText(wichtigelist.uppertitle);
			upperdateview.setText(wichtigelist.upperdatetime);
			lowertextview.setText(wichtigelist.lowertext);
			
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
