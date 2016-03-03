package com.MWC.Altsasbacher;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import app.tabsample.SmartImageView.NormalSmartImageView;

import com.MWC.AltsasbacherAppVO.*;
public class LendertvFragement  extends Fragment{
	private View parentView;
	 public View mview;
	    private ListView lendertvView;
  public ArrayList<LendertvVO> lendertvArrayList=new ArrayList<LendertvVO>();
  public static LendertvVO lenderinfoVO;
  public ProgressBar myProgressBar;
  XMLParser parser = new XMLParser();
	String xml = "";
	NodeList nl;
	Document doc;
	String RSS_FEED_URL = "http://www.lendertv.de/rss/";
	ListView itcItems;
	public SharedPreferences sharedPrefs;
	public Editor editor;

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	    	parentView = inflater.inflate(R.layout.lendertvxml, container, false);
	        lendertvView = (ListView) parentView.findViewById(R.id.lendertvListview);
	    	myProgressBar=(ProgressBar)parentView.findViewById(R.id.LendertvProgressbar);
	        lendertvArrayList=new ArrayList<LendertvVO>();
	    	sharedPrefs = PreferenceManager
					.getDefaultSharedPreferences(MenuActivity.myactivity);
			editor = sharedPrefs.edit();
			if(!AppUtils.isNetworkAvailable(MenuActivity.myactivity))
			{
				AppUtils.ShowAlertDialog();
			}
	        initView();
	        return parentView;
	    }

	    private void initView(){
	
	    	//getListData();
	    	new myTask_Rss_Feed().execute();
	    }

	    class myTask_Rss_Feed extends AsyncTask<String, Void, String> {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				myProgressBar.setVisibility(View.VISIBLE);
			}

			@Override
			protected String doInBackground(String... aurl) {
				try {
					xml = parser.getXmlFromUrl(RSS_FEED_URL);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				return "";

			}

			@Override
			protected void onPostExecute(String str_resp) {
				doc = parser.getDomElement(xml); // getting
				if (doc != null) {
					nl = doc.getElementsByTagName("item");
					lendertvArrayList.clear();
					
//					SimpleDateFormat tempformatter = new SimpleDateFormat(
//							"dd.MM.yyyy HH:mm");
					
//					TimeZone utc = TimeZone.getTimeZone("etc/UTC");
//					DateFormat inputFormat = new SimpleDateFormat("dd MMM, yyyy HH:mm",
//					                                              Locale.US);
//					inputFormat.setTimeZone(utc);
//					DateFormat outputFormat = new SimpleDateFormat("dd MMM, yyyy hh:mm aa",
//					                                              Locale.US);
//					outputFormat.setTimeZone(utc);
//					Date date;
					
					// looping through all item nodes <item>
					for (int i = 0; i < nl.getLength(); i++) {

						Element e = (Element) nl.item(i);
						NodeList nl_se = e.getChildNodes();
						Node previewElement = nl_se.item(7);
						Node enclosureElement = nl_se.item(9);
						NamedNodeMap video = enclosureElement.getAttributes();
						NamedNodeMap attr = previewElement.getAttributes();
						System.out.println("imagenodevalue "
								+ attr.item(0).getNodeValue() + video.item(0).getNodeValue());
						LendertvVO rVo = new  LendertvVO();
						rVo.media_thumbnail =attr.item(0).getNodeValue();
						rVo.preview = attr.item(0).getNodeValue();
						rVo.link = parser.getValue(e, "link");
						rVo.title = parser.getValue(e, "title");
						rVo.enclosure = video.item(0).getNodeValue();
						rVo.pubDate = parser.getValue(e, "pubDate");
						
						rVo.description = parser.getValue(e, "description");
						rVo.description = rVo.description.replaceAll("/team-","http://www.lendertv.de/team-");
						
						lendertvArrayList.add(rVo);

					}//end of for
				}//end of if
	
				if (AppUtils.isNetworkAvailable(MenuActivity.myactivity)) {
					editor = sharedPrefs.edit();
					int i = 0;
					for (i = 0; i < lendertvArrayList.size(); i++) {
						editor.putString("RSSTitle" + i,
								lendertvArrayList.get(i).title);
						editor.putString("RSSpubDate" + i,
								lendertvArrayList.get(i).pubDate);
						editor.putString("RSSImageURL" + i,
								lendertvArrayList.get(i).preview);
						editor.putString("RSSDescription"
								+ i, lendertvArrayList.get(i).description);
						editor.putString( "RSSVideoURl"
								+ i, lendertvArrayList.get(i).enclosure);

					}
					editor.putInt("LENDERTVPREF", lendertvArrayList.size());
					editor.commit();
				}

				else {
					int len = sharedPrefs.getInt("LENDERTVPREF", (int) 0);

					if (len > 0) {
						int i;
						for (i = 0; i < len; i++) {
							LendertvVO vo = new LendertvVO();

							if (sharedPrefs.getString("RSSTitle" + i, null) != null
									|| sharedPrefs.getString(
											"RSSTitle" + i,
											null) != "")
								vo.title = sharedPrefs.getString(
										 "RSSTitle" + i, "");

							if (sharedPrefs.getString("RSSpubDate" + i, null) != null
									|| sharedPrefs.getString(
											"RSSpubDate" + i,
											null) != "")
								vo.pubDate = sharedPrefs
										.getString("RSSpubDate" + i, "");

							if (sharedPrefs.getString("RSSImageURL" + i, null) != null
									|| sharedPrefs.getString(
											"RSSImageURL" + i,
											null) != "")
								vo.preview = sharedPrefs.getString(
										"RSSImageURL" + i,
										"");

							if (sharedPrefs.getString("RSSDescription"
									+ i, null) != null
									|| sharedPrefs.getString(
											"RSSDescription"
													+ i,
											null) != "")
								vo.description = sharedPrefs
										.getString("RSSDescription"
												+ i, "");
							

							if (sharedPrefs.getString( "RSSVideoURl"
									+ i, null) != null
									|| sharedPrefs.getString(
											 "RSSVideoURl"
														+ i,
											null) != "")
								vo.enclosure = sharedPrefs
										.getString( "RSSVideoURl"
												+ i, "");

							lendertvArrayList.add(vo);
						}

					} else {
						lendertvArrayList = null;
					}

				}

				if(lendertvArrayList!=null)
				{
					LendertvAdapter adapter=new LendertvAdapter(MenuActivity.myactivity,lendertvArrayList);
			    	lendertvView.setAdapter(adapter);
			    	lendertvView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			            @Override
			            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
			                lenderinfoVO=lendertvArrayList.get(i);
			                Intent intent=new Intent(MenuActivity.myactivity.getApplicationContext(),LenterTvInformationActivity.class);
			                startActivity(intent); 	
			            }
			        });
	
				}
				else
				{
					AppUtils.ShowAlertDialog();
				}
		    	myProgressBar.setVisibility(View.GONE);
			}//end of post
		}//end of async

	}//end of class
