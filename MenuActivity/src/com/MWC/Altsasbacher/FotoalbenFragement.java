package com.MWC.Altsasbacher;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.MWC.AltsasbacherAppVO.FotoAlbenTitelGalleryVO;
import com.MWC.AltsasbacherAppVO.fotoalbenVO;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class FotoalbenFragement extends Fragment {
	 private View parentView;
	 public View mview;
	    private GridView albengridview;
public ArrayList<fotoalbenVO> albenArrayList;
public ArrayList<FotoAlbenTitelGalleryVO> albenTitleArrayList;
public ArrayList<Object> albenobjectArrayList; 
public static String AlbenOrderName="";
public ProgressBar myProgressBar=null;	    
List<ParseObject> parseObj = new ArrayList<ParseObject>();
public ProgressDialog pDialog=null;
public SharedPreferences sharedPrefs;
public void displayDialog() {
	pDialog = new ProgressDialog(MenuActivity.myactivity);
	pDialog.setMessage("Bitte warten..");
	pDialog.show();
}

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	    	parentView = inflater.inflate(R.layout.fotoalbenxml, container, false);
	        albengridview  = (GridView) parentView.findViewById(R.id.fotoalbengridview);
	       myProgressBar=(ProgressBar)parentView.findViewById(R.id.PhotoAlbumprogressBar);
	       sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MenuActivity.myactivity);
	   	if(!AppUtils.isNetworkAvailable(MenuActivity.myactivity))
		{
			AppUtils.ShowAlertDialog();
		}	       
	        initView();
	        return parentView;
	    }

	    private void initView(){
	
	    	 new getParseDataAsync().execute();
	    	
	    }//end of initview
	    
	    public class getParseDataAsync extends AsyncTask<String, String, String> {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				myProgressBar.setVisibility(View.VISIBLE);
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

			@SuppressLint("NewApi") protected void onPostExecute(String str_resp) {

				albenTitleArrayList = new ArrayList<FotoAlbenTitelGalleryVO>();
				
				albenTitleArrayList.clear();
				
				for (int i = 0; i < parseObj.size(); i++) {

					FotoAlbenTitelGalleryVO fotoVO = new FotoAlbenTitelGalleryVO();

					fotoVO.FotoanbenTitle = parseObj.get(i).getString("AlbumTitel");
					fotoVO.FotoanbenGallary = parseObj.get(i).getString("Ordnername");
					albenTitleArrayList.add(fotoVO);

				}//end of for
				FotoalbenAdapter adapter=new FotoalbenAdapter(MenuActivity.myactivity,albenTitleArrayList);
		    	albengridview.setAdapter(adapter);
		    	albengridview.deferNotifyDataSetChanged();
		        albengridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		            @Override
		            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		                
		            	AlbenOrderName=albenTitleArrayList.get(i).FotoanbenGallary;
		            	if (AppUtils.isNetworkAvailable(MenuActivity.myactivity)) 
		    			{
		            		//albenArr=(ArrayList<fotoalbenVO>) albenobjectArrayList.get(i);
			                Intent intent=new Intent(MenuActivity.myactivity.getApplicationContext(),FotoalbenActivity.class);
					        //intent.putExtra("ALBUMPHOTS", albenArrayList);
					        startActivity(intent); 	
		    			}
		            	else
		            	{
		            		int len=sharedPrefs.getInt(FotoalbenFragement.AlbenOrderName,(int)0);
		    				
		    				if(len>0)
		    				{
		    					//albenArr=(ArrayList<fotoalbenVO>) albenobjectArrayList.get(i);
				                Intent intent=new Intent(MenuActivity.myactivity.getApplicationContext(),FotoalbenActivity.class);
						        //intent.putExtra("ALBUMPHOTS", albenArrayList);
						        startActivity(intent); 	
		    				}
		            	}//else
		                
		            }
		        });
	           
		        myProgressBar.setVisibility(View.INVISIBLE);

			}// end of postexecute()
		}// end of Async getParseDataAsync class
	    

	    public void getData() {

			ParseQuery<ParseObject> query = ParseQuery.getQuery("Fotos");
			 query.orderByDescending("createdAt");
			try {
				parseObj = query.find();
				ParseObject.pinAllInBackground(parseObj);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		public void getLocalDataStore() {

			ParseQuery<ParseObject> query = ParseQuery.getQuery("Fotos");
			query.orderByDescending("createdAt");
			query.fromLocalDatastore();
			try {

				parseObj = query.find();

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}//end of Fragement
