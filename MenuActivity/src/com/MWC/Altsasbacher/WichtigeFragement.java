package com.MWC.Altsasbacher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.MWC.AltsasbacherAppVO.WichtigeListVo;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class WichtigeFragement extends Fragment {
	 private View parentView;
	 public View mview;
	    private ListView wichlistView;
	    List<ParseObject> message = new ArrayList<ParseObject>();
       public ArrayList<WichtigeListVo> wichtigeArrayList;
       public static final int DIALOG_DOWNLOAD_PROGRESS1 = 1;
   	private ProgressDialog mProgressDialog;

   	protected Dialog onCreateDialog(int id) {
   		switch (id) {
   		case DIALOG_DOWNLOAD_PROGRESS1:
   			mProgressDialog = new ProgressDialog(MenuActivity.myactivity);
   			mProgressDialog.setMessage("Bitte warten..");
   			mProgressDialog.setCancelable(false);
   			mProgressDialog.show();

   			return mProgressDialog;

   		default:
   			return null;
   		}
   	}
   	public void onBackPressed() {
		
	}
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    	parentView = inflater.inflate(R.layout.wichtigelistviewxml, container, false);
	        wichlistView   = (ListView) parentView.findViewById(R.id.wichlistView);
	        
	        Parse.initialize(MenuActivity.myactivity, AppUtils.ApplicationId,
					AppUtils.ClientKey);
	    	if(!AppUtils.isNetworkAvailable(MenuActivity.myactivity))
			{
				AppUtils.ShowAlertDialog();
			}
	        initView();
	        return parentView;
	    }

	    private void initView(){
	
	    	if (AppUtils.isNetworkAvailable(MenuActivity.myactivity)) 
				new getParseDataAsync().execute();
				else
					new getLocalParseDataAsync().execute();

	    }
	    
	    public void getData() {
			
			ParseQuery<ParseObject> query = ParseQuery.getQuery("WichtigeMeldungen");
			query.orderByDescending("updatedAt");
			try {
				   message = query.find();
				   ParseObject.pinAllInBackground(message);
			} catch (ParseException e) {
				e.printStackTrace();
			}
	    	}
	    
	    public void getLocalDataStore() {
	    	
	    	ParseQuery<ParseObject> query = ParseQuery.getQuery("WichtigeMeldungen");
	    	query.orderByDescending("updatedAt");
	    	query.fromLocalDatastore();
	    	try {
	    	
	    		message = query.find();
	    		
	    	} catch (ParseException e) {
	    		e.printStackTrace();
	    	}
	        	}
	    
	    
	    public class getParseDataAsync extends AsyncTask<String, String, String>
		{
			
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
			}

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				getData();
				return null;
			}
			
			protected void onPostExecute(String str_resp) {
			
				wichtigeArrayList=new ArrayList<WichtigeListVo>();
				wichtigeArrayList.clear();
				for (int i = 0; i < message.size(); i++) {
					
					SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy - hh:mm");
      			     WichtigeListVo wichVo=new WichtigeListVo();
      
      			  wichVo.uppertitle=message.get(i).getString("Kurzueberschrift");
               	 wichVo.lowertext=message.get(i).getString("Kurzbeschreibung");
               	 wichVo.upperdatetime=(formatter.format(message.get(i).getUpdatedAt()));
               	 wichtigeArrayList.add(wichVo);
				}
				
				
				WichtigeAdapter adapter=new WichtigeAdapter(MenuActivity.myactivity,wichtigeArrayList);
		    	wichlistView.setAdapter(adapter);
				
		    	if (mProgressDialog != null)
					mProgressDialog.dismiss();
				
			}//end of postexecute()
			
		}//end of Async getParseDataAsync class

	    
	    public class getLocalParseDataAsync extends AsyncTask<String, String, String>
		{
			
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				onCreateDialog(DIALOG_DOWNLOAD_PROGRESS1);
			}

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				getLocalDataStore();
				return null;
			}
			
			protected void onPostExecute(String str_resp) {
			
				wichtigeArrayList=new ArrayList<WichtigeListVo>();
				wichtigeArrayList.clear();
				for (int i = 0; i < message.size(); i++) {
					
					SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy - hh:mm");
      			     WichtigeListVo wichVo=new WichtigeListVo();
      
      			  wichVo.uppertitle=message.get(i).getString("Kurzueberschrift");
               	 wichVo.lowertext=message.get(i).getString("Kurzbeschreibung");
               	 wichVo.upperdatetime=(formatter.format(message.get(i).getUpdatedAt()));
               	 wichtigeArrayList.add(wichVo);
				}
				
				WichtigeAdapter adapter=new WichtigeAdapter(MenuActivity.myactivity,wichtigeArrayList);
		    	wichlistView.setAdapter(adapter);
				
		    	if (mProgressDialog != null)
					mProgressDialog.dismiss();
				
			}//end of postexecute()
			
		}//end of Async getLocalParseDataAsync class
	    
	    
	}//end of fragement
