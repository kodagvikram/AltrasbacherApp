package com.MWC.Altsasbacher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.MWC.Altsasbacher.WichtigeFragement.getLocalParseDataAsync;
import com.MWC.Altsasbacher.WichtigeFragement.getParseDataAsync;
import com.MWC.AltsasbacherAppVO.AltsasbachertagVO;
import com.MWC.AltsasbacherAppVO.GoogleMapAllpointsVO;
import com.MWC.AltsasbacherAppVO.TermineListVO;
import com.MWC.AltsasbacherAppVO.WichtigeListVo;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class AltsasbachertagFragment extends Fragment {
	private View parentView;
	public View mview;
	private ListView altstagView, altstagView2;
	public Button infobtn;
	public RelativeLayout terminelayout;
	public TextView infotextview, titletextview, datetextview,
			locationtextview, timetextview, datetextview2;
	public ScrollView myscrollview;
	public AltsasbachertagVO altstagVo;
	public static AltsasbachertagVO ststicaltstagVo=new AltsasbachertagVO();
	public  ArrayList<AltsasbachertagVO> altstagArrayList=new ArrayList<AltsasbachertagVO>(), altstagArrayList2=new ArrayList<AltsasbachertagVO>();
	public static ArrayList<GoogleMapAllpointsVO> googleallpointslist=new ArrayList<GoogleMapAllpointsVO>();

	List<ParseObject> parseObj = new ArrayList<ParseObject>();
	public ProgressDialog pDialog;

	public void displayDialog() {
		pDialog = new ProgressDialog(MenuActivity.myactivity);
		pDialog.setMessage("Bitte warten..");
		pDialog.show();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		parentView = inflater.inflate(R.layout.altsasbachertagxml, container,
				false);

		if(!AppUtils.isNetworkAvailable(MenuActivity.myactivity))
		{
			AppUtils.ShowAlertDialog();
		}
		
		altstagView = (ListView) parentView.findViewById(R.id.altstaglistview);
		altstagView2 = (ListView) parentView
				.findViewById(R.id.altstaglistview2);
		datetextview = (TextView) parentView
				.findViewById(R.id.altstagDatetextview);
		datetextview2 = (TextView) parentView
				.findViewById(R.id.altstagDatetextview2);
		myscrollview = (ScrollView) parentView
				.findViewById(R.id.Mapmainscrollview);

		Parse.initialize(MenuActivity.myactivity, AppUtils.ApplicationId,
				AppUtils.ClientKey);

		initView();

		return parentView;
	}

	private void initView() {

		new getParseDataAsync().execute();
		// else
		// new getLocalParseDataAsync().execute();
	}

	public class getParseDataAsync extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			displayDialog();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			if (AppUtils.isNetworkAvailable(MenuActivity.myactivity))
				getData();
			else
				getLocalDataStore();

			return null;
		}

		protected void onPostExecute(String str_resp) {

			altstagArrayList = new ArrayList<AltsasbachertagVO>();
			altstagArrayList2 = new ArrayList<AltsasbachertagVO>();
			
			googleallpointslist.clear();
			altstagArrayList.clear();
			altstagArrayList2.clear();
			
			for (int i = 0; i < parseObj.size(); i++) {

				// String d=parseObj.get(i).getString("Uhrzeitvon");
				// String s=new
				// SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(d);

				SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
				formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
				SimpleDateFormat tempformatter = new SimpleDateFormat(
						"dd.MM.yyyy");
				tempformatter.setTimeZone(TimeZone.getTimeZone("UTC"));
				
				AltsasbachertagVO altvo = new AltsasbachertagVO();
				GoogleMapAllpointsVO allpointsVO=new GoogleMapAllpointsVO();

				altvo.tagtitle = parseObj.get(i).getString("Titel");
				altvo.tagshortdes = parseObj.get(i).getString(
						"Kurzbeschreibung");
				altvo.taglongdes = parseObj.get(i).getString("Beschreibung");
				altvo.taglocation = parseObj.get(i).getString("Ort");
				altvo.xcoordinate = parseObj.get(i).getDouble("Mapx");
				altvo.ycoordinate = parseObj.get(i).getDouble("Mapy");
				System.out.println(parseObj.get(i).getDate("Uhrzeitvon"));

				altvo.tagstartdate = (formatter.format(parseObj.get(i).getDate(
						"Uhrzeitvon")));
				System.out.println(altvo.tagstartdate);
				altvo.tagenddate = (formatter.format(parseObj.get(i).getDate(
						"Uhrzeitbis")));

				if ((tempformatter.format(parseObj.get(i).getDate("Uhrzeitvon"))).equals(tempformatter.format(parseObj.get(0).getDate("Uhrzeitvon"))))
				{
					datetextview.setText("Samstag, "+tempformatter.format(parseObj.get(i).getDate("Uhrzeitvon")));
					addMapPoint(altvo,true);
					altstagArrayList.add(altvo);
				}
				else
				{
					datetextview2.setText("Sonntag, "+tempformatter.format(parseObj.get(i).getDate("Uhrzeitvon")));
					addMapPoint(altvo,false);
					altstagArrayList2.add(altvo);
				}

			}
			
            System.out.println(googleallpointslist);
           // System.out.println(altstagArrayList2);
			AltsasbachertagAdapter adapter = new AltsasbachertagAdapter(
					MenuActivity.myactivity, altstagArrayList);
			altstagView.setAdapter(adapter);
			altstagView.setScrollContainer(false);
			updateListViewHeight(altstagView);
			 myscrollview.smoothScrollTo(0, 0);
			 altstagView.setSelectionAfterHeaderView();
			altstagView
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> adapterView,
								View view, int i, long l) {

							if (altstagArrayList.size() > 0) {
								AltsasbachertagVO altvo = altstagArrayList
										.get(i);
								ststicaltstagVo = altstagArrayList.get(i);
								Intent intent = new Intent(
										MenuActivity.myactivity
												.getApplicationContext(),
										GoogleMapActivity.class);
								intent.putExtra("locationtitle", altvo.tagtitle);
								intent.putExtra("time", altvo.tagtime);
								intent.putExtra("locations", altvo.taglocation);
								intent.putExtra("extrainfo", altvo.taginfo);
								startActivity(intent);
							}

						}
					});

			AltsasbachertagAdapter adapter2 = new AltsasbachertagAdapter(
					MenuActivity.myactivity, altstagArrayList2);
			altstagView2.setAdapter(adapter2);
			altstagView2.setScrollContainer(false);
			updateListViewHeight(altstagView2);
			 myscrollview.smoothScrollTo(0, 0);
			altstagView2
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> adapterView,
								View view, int i, long l) {

							if (altstagArrayList2.size() > 0) {
								AltsasbachertagVO altvo = altstagArrayList2
										.get(i);
								ststicaltstagVo = altstagArrayList2.get(i);
								Intent intent = new Intent(
										MenuActivity.myactivity
												.getApplicationContext(),
										GoogleMapActivity.class);
								intent.putExtra("locationtitle", altvo.tagtitle);
								intent.putExtra("time", altvo.tagtime);
								intent.putExtra("locations", altvo.taglocation);
								intent.putExtra("extrainfo", altvo.taginfo);
								startActivity(intent);
							}
						}
					});

			if (pDialog == null || pDialog.isShowing()) {
				pDialog.dismiss();
			}
			
			 myscrollview.smoothScrollTo(0, -20);
			 altstagView.setSelectionAfterHeaderView();

		}// end of postexecute()

	}// end of Async getParseDataAsync class

	public void getData() {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Altsasbachertage");
		// query.orderByDescending("createdAt");
		try {
			parseObj = query.find();
			ParseObject.pinAllInBackground(parseObj);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void getLocalDataStore() {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Altsasbachertage");
		// query.orderByDescending("createdAt");
		query.fromLocalDatastore();
		try {

			parseObj = query.find();

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void addMapPoint(AltsasbachertagVO altvo,boolean test)
	{
		if(googleallpointslist.size()<1)
		{
			GoogleMapAllpointsVO allpointsVO=new GoogleMapAllpointsVO();
			if(test==true)
			{
			allpointsVO.location=altvo.taglocation;
			
			if(altvo.tagstartdate.equals(altvo.tagenddate))
			allpointsVO.eventTitle=" - am Samstag um  "+altvo.tagenddate+" Uhr: "+altvo.tagtitle;
			else
				allpointsVO.eventTitle=" - am Samstag von  "+altvo.tagstartdate+" Uhr bis  "+altvo.tagenddate+" Uhr: "+altvo.tagtitle;
			
			allpointsVO.mapx=altvo.xcoordinate;
			allpointsVO.mapy=altvo.ycoordinate;
			googleallpointslist.add(allpointsVO);
			}
			else
			{
				allpointsVO.location=altvo.taglocation;
				
				if(altvo.tagstartdate.equals(altvo.tagenddate))
				allpointsVO.eventTitle=" - am Sonntag um  "+altvo.tagenddate+" Uhr: "+altvo.tagtitle;
				else
					allpointsVO.eventTitle=" - am Sonntag von  "+altvo.tagstartdate+" Uhr bis  "+altvo.tagenddate+" Uhr: "+altvo.tagtitle;
				
				allpointsVO.mapx=altvo.xcoordinate;
				allpointsVO.mapy=altvo.ycoordinate;
				googleallpointslist.add(allpointsVO);
			}//end of else
			
		}//end of if for when arraylist 0
		else
			{
			           boolean temp=false;
			           for(int j=0;j<googleallpointslist.size();j++)
						{
							GoogleMapAllpointsVO allpointsVO2=googleallpointslist.get(j);
							
							if(allpointsVO2.mapx==altvo.xcoordinate&&allpointsVO2.mapy==altvo.ycoordinate)
							{
								temp=true;
								if(test==true)
								{
									if(altvo.tagstartdate.equals(altvo.tagenddate))
										googleallpointslist.get(j).eventTitle=googleallpointslist.get(j).eventTitle+"\n - am Samstag um  "+altvo.tagenddate+" Uhr: "+altvo.tagtitle;
										else
											googleallpointslist.get(j).eventTitle=googleallpointslist.get(j).eventTitle+"\n - am Samstag von  "+altvo.tagstartdate+" Uhr bis  "+altvo.tagenddate+" Uhr: "+altvo.tagtitle;
								}
								else
								{
									if(altvo.tagstartdate.equals(altvo.tagenddate))
										googleallpointslist.get(j).eventTitle=googleallpointslist.get(j).eventTitle+"\n - am Sonntag um  "+altvo.tagenddate+" Uhr: "+altvo.tagtitle;
										else
										googleallpointslist.get(j).eventTitle=googleallpointslist.get(j).eventTitle+"\n - am Sonntag von  "+altvo.tagstartdate+" Uhr bis  "+altvo.tagenddate+" Uhr: "+altvo.tagtitle;
								}
							}
							
						}//end of for
			           
			           if(temp==false)
			           {
			        	   GoogleMapAllpointsVO allpointsVO=new GoogleMapAllpointsVO();
			   			if(test==true)
			   			{
			   			allpointsVO.location=altvo.taglocation;
			   			
			   			if(altvo.tagstartdate.equals(altvo.tagenddate))
			   			allpointsVO.eventTitle=" - am Samstag um  "+altvo.tagenddate+" Uhr: "+altvo.tagtitle;
			   			else
			   				allpointsVO.eventTitle=" - am Samstag von  "+altvo.tagstartdate+" Uhr bis  "+altvo.tagenddate+" Uhr: "+altvo.tagtitle;
			   			
			   			allpointsVO.mapx=altvo.xcoordinate;
			   			allpointsVO.mapy=altvo.ycoordinate;
			   			googleallpointslist.add(allpointsVO);
			   			}
			   			else
			   			{
			   				allpointsVO.location=altvo.taglocation;
			   				
			   				if(altvo.tagstartdate.equals(altvo.tagenddate))
			   				allpointsVO.eventTitle=" - am Sonntag um  "+altvo.tagenddate+" Uhr: "+altvo.tagtitle;
			   				else
			   					allpointsVO.eventTitle=" - am Sonntag von  "+altvo.tagstartdate+" Uhr bis  "+altvo.tagenddate+" Uhr: "+altvo.tagtitle;
			   				
			   				allpointsVO.mapx=altvo.xcoordinate;
			   				allpointsVO.mapy=altvo.ycoordinate;
			   				googleallpointslist.add(allpointsVO);
			   			}//end of else
			           }
			
			}//end Else for when arraylist 0
		
	}//end of addMapPoint()
	

	public static void updateListViewHeight(ListView myListView) {
		ListAdapter myListAdapter = myListView.getAdapter();
		if (myListAdapter == null) {
			return;
		}
		// get listview height
		int totalHeight = 0;
		int adapterCount = myListAdapter.getCount();
		for (int size = 0; size < adapterCount; size++) {
			View listItem = myListAdapter.getView(size, null, myListView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		// Change Height of ListView
		ViewGroup.LayoutParams params = myListView.getLayoutParams();
		params.height = totalHeight
				+ (myListView.getDividerHeight() * (adapterCount - 1));
		myListView.setLayoutParams(params);
	}// end of updatelistheight

}// end of fragement
