package com.MWC.Altsasbacher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ParseException;
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

import com.MWC.AltsasbacherAppVO.FSJBerichteVO;

public class FSJBerichteFragement extends Fragment {
	private View parentView;
	public View mview;
	private ListView fsjListView;
	public ArrayList<FSJBerichteVO> fsjArrayList = new ArrayList<FSJBerichteVO>();
	public static FSJBerichteVO fsjvoobject;
	public ProgressBar myProgressBar;
	XMLParser parser = new XMLParser();
	String xml = "";
	NodeList nl;
	Document doc;
	String RSS_FEED_URL = "http://altsasbacher.de/?cat=7&feed=rss2";

	public SharedPreferences sharedPrefs;
	public Editor editor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		parentView = inflater.inflate(R.layout.fsjberichtefrontxml, container,
				false);
		fsjListView = (ListView) parentView
				.findViewById(R.id.fsjberichteListview);
		myProgressBar = (ProgressBar) parentView
				.findViewById(R.id.RSSFEED_Progressbar);
		RSS_FEED_URL = MenuActivity.RSSFEEDURL;

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

	private void initView() {

		// getListData();
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
			 try {
			doc = parser.getDomElement(xml); // getting
			if (doc != null) {
				nl = doc.getElementsByTagName("item");
				fsjArrayList.clear();

				for (int i = 0; i < nl.getLength(); i++) {

					Element e = (Element) nl.item(i);
					NodeList nl_se = e.getChildNodes();

					Node previewElement = nl_se.item(23);
					
					try {
						
						Node previewElement2 = nl_se.item(25);
						if(previewElement2!=null)
							previewElement=previewElement2;
						
					} catch (Exception e2) {
						// TODO: handle exception
					}
					
					Node desriptionElement = nl_se.item(17);
					NodeList desri = desriptionElement.getChildNodes();
					NamedNodeMap attr;
					FSJBerichteVO rVo = new FSJBerichteVO();
					if (previewElement != null) {
						attr = previewElement.getAttributes();
						if(attr.getLength()>0)
						{
						System.out.println("imagenodevalue "
								+ attr.item(0).getNodeValue());
						rVo.media_thumbnail = attr.item(0).getNodeValue();
						rVo.image_url = attr.item(0).getNodeValue();
						}
					}

					rVo.link = parser.getValue(e, "link");
					rVo.title = parser.getValue(e, "title");
					rVo.pubDate = parser.getValue(e, "pubDate");
					rVo.content_encoded = desri.item(0).getNodeValue();
					
					String dateStr = rVo.pubDate.replace(" +0000", "");
				    
					SimpleDateFormat readFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss",Locale.US);

					SimpleDateFormat writeFormat = new SimpleDateFormat( "dd-MMM-yyyy");
					SimpleDateFormat writeFormat2 = new SimpleDateFormat( "dd MMM yyyy HH:mm");
				    Date date = null;
				   
				       date = readFormat.parse(dateStr);
				   

				    String formattedDate = "";
				    if( date != null ) {
				    	rVo.pubDate = writeFormat.format( date );
				    	rVo.formatedate = writeFormat2.format( date );
				    	
				    }
					
					fsjArrayList.add(rVo);
					

				}// end of for
				
			}// end of if
			

			if (AppUtils.isNetworkAvailable(MenuActivity.myactivity)) {
				editor = sharedPrefs.edit();
				int i = 0;
				for (i = 0; i < fsjArrayList.size(); i++) {
					editor.putString(MenuActivity.RSSFEEDURL + "Title" + i,
							fsjArrayList.get(i).title);
					editor.putString(MenuActivity.RSSFEEDURL + "pubDate" + i,
							fsjArrayList.get(i).pubDate);
					editor.putString(MenuActivity.RSSFEEDURL + "ImageURL" + i,
							fsjArrayList.get(i).image_url);
					editor.putString(MenuActivity.RSSFEEDURL + "Description"
							+ i, fsjArrayList.get(i).content_encoded);
					
					editor.putString(MenuActivity.RSSFEEDURL + "Dateformate"
							+ i, fsjArrayList.get(i).formatedate);
					
				}
				editor.putInt(MenuActivity.RSSFEEDURL, fsjArrayList.size());
				editor.commit();
			}

			else {
				int len = sharedPrefs.getInt(MenuActivity.RSSFEEDURL, (int) 0);

				if (len > 0) {
					int i;
					for (i = 0; i < len; i++) {
						FSJBerichteVO vo = new FSJBerichteVO();

						if (sharedPrefs.getString(MenuActivity.RSSFEEDURL
								+ "Title" + i, null) != null
								|| sharedPrefs.getString(
										MenuActivity.RSSFEEDURL + "Title" + i,
										null) != "")
							vo.title = sharedPrefs.getString(
									MenuActivity.RSSFEEDURL + "Title" + i, "");

						if (sharedPrefs.getString(MenuActivity.RSSFEEDURL
								+ "pubDate" + i, null) != null
								|| sharedPrefs.getString(
										MenuActivity.RSSFEEDURL + "Title" + i,
										null) != "")
							vo.pubDate = sharedPrefs
									.getString(MenuActivity.RSSFEEDURL
											+ "pubDate" + i, "");

						if (sharedPrefs.getString(MenuActivity.RSSFEEDURL
								+ "ImageURL" + i, null) != null
								|| sharedPrefs.getString(
										MenuActivity.RSSFEEDURL + "Title" + i,
										null) != "")
							vo.image_url = sharedPrefs.getString(
									MenuActivity.RSSFEEDURL + "ImageURL" + i,
									"");

						if (sharedPrefs.getString(MenuActivity.RSSFEEDURL
								+ "Description" + i, null) != null
								|| sharedPrefs.getString(
										MenuActivity.RSSFEEDURL + "Title" + i,
										null) != "")
							vo.content_encoded = sharedPrefs
									.getString(MenuActivity.RSSFEEDURL
											+ "Description" + i, "");

						if (sharedPrefs.getString(MenuActivity.RSSFEEDURL + "Dateformate"
								+ i, null) != null
								|| sharedPrefs.getString(
										MenuActivity.RSSFEEDURL + "Dateformate"
												+ i,
										null) != "")
							vo.content_encoded = sharedPrefs
									.getString(MenuActivity.RSSFEEDURL + "Dateformate"
											+ i, "");
						
						fsjArrayList.add(vo);
					}

				} else {
					fsjArrayList = null;
				}

			}

			if (fsjArrayList != null) {
				FSJBerichteAdapter adapter = new FSJBerichteAdapter(
						MenuActivity.myactivity, fsjArrayList);
				fsjListView.setAdapter(adapter);
				fsjListView
						.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> adapterView,
									View view, int i, long l) {
								fsjvoobject = fsjArrayList.get(i);
								Intent intent = new Intent(MenuActivity.myactivity
										.getApplicationContext(),
										FSJBerichteinfoActivity.class);
								startActivity(intent);
							}
						});

			  }
						myProgressBar.setVisibility(View.GONE);
			 } catch ( ParseException e1 ) {
			        e1.printStackTrace();
			    } catch (java.text.ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
		}// end of post
	}// end of async

}// end of class
