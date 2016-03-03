package com.MWC.Altsasbacher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import app.tabsample.SmartImageView.WebImageCache;

import com.MWC.AltsasbacherAppVO.TermineListVO;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class TermineFragement extends Fragment {
	private View parentView;
	public View mview;
	private ListView termineView;
	public Button infobtn, speichbtn, teilnehmenbtn;
	public RelativeLayout terminelayout;
	public RelativeLayout terminelayout2;
	public static ScrollView myscrollview;
	public long deviceheight;
	public int textviewheight;
	public TextView infotextview, titletextview, datetimetextview,
			extratextview;
	public TermineListVO terVo=new TermineListVO();
	public static TermineListVO staticterVo;
	public ArrayList<TermineListVO> termineArrayList=new ArrayList<TermineListVO>();
	public ImageView backimageview;
	public static int datePosition = 0;
	public ProgressBar myprogrssbarBar,myProgressBar2;
	public String ObjectId = "", tempbuttontext = "";
	public SharedPreferences pref;
	public SharedPreferences sharedPreferences;
	public long personcount = 0;
	public static Bitmap Imagebitmap;
    public Bitmap Localbitmap=null;
	public static WebImageCache webImageCache;
	// public Editor editor;

	List<ParseObject> parseObj = new ArrayList<ParseObject>();
	List<ParseObject> UpdatedparseObj = new ArrayList<ParseObject>();
	public ProgressDialog pDialog;

	public void displayDialog() {
		pDialog = new ProgressDialog(MenuActivity.myactivity);
		pDialog.setMessage("Bitte warten..");
		pDialog.show();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.terminexml, container, false);

		if(!AppUtils.isNetworkAvailable(MenuActivity.myactivity))
		{
			AppUtils.ShowAlertDialog();
		}
		myprogrssbarBar = (ProgressBar)parentView.findViewById(R.id.progressBar1);
		myprogrssbarBar.setVisibility(View.GONE);
		myProgressBar2 = (ProgressBar)parentView.findViewById(R.id.progressBar2);
		myProgressBar2.setVisibility(View.GONE);

		pref = MenuActivity.myactivity.getApplicationContext()
				.getSharedPreferences("MyPref", Context.MODE_PRIVATE);

		sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(MenuActivity.myactivity
						.getApplicationContext());

		deviceheight = MenuActivity.deviceheight;
		deviceheight = (deviceheight * 65) / 100;
		textviewheight = (int) (MenuActivity.deviceheight * 35) / 100;
		// textviewheight=textviewheight;

		//termineView = (ListView) parentView.findViewById(R.id.terminelistview);
		titletextview = (TextView) parentView
				.findViewById(R.id.terminetTtletextview);
		datetimetextview = (TextView) parentView
				.findViewById(R.id.termineDatetimetextview);
		extratextview = (TextView) parentView
				.findViewById(R.id.terminEextraextview);
		infotextview = (TextView) parentView
				.findViewById(R.id.termineInformationextview);

		backimageview = (ImageView) parentView
				.findViewById(R.id.BackimageImageview);
		terminelayout = (RelativeLayout) parentView
				.findViewById(R.id.terminemaillayout);
		terminelayout2 = (RelativeLayout) parentView
				.findViewById(R.id.listtextmaillayout);
		

		terminelayout.getLayoutParams().height = textviewheight;
		terminelayout2.getLayoutParams().height = textviewheight;

		infobtn = (Button) parentView.findViewById(R.id.informationbtn);
		speichbtn = (Button) parentView.findViewById(R.id.speichernbtn);
		teilnehmenbtn = (Button) parentView.findViewById(R.id.teilnehmenbtn);

		setButtombackcolor();

		myscrollview = (ScrollView) parentView
				.findViewById(R.id.mainscrollview);


		Parse.initialize(MenuActivity.myactivity, AppUtils.ApplicationId,
				AppUtils.ClientKey);
		initView();

		infobtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setButtombackcolor();
				infobtn.setBackgroundColor(getColorWithAlpha(Color.WHITE, 0.1f));

				RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) terminelayout
						.getLayoutParams();
				ResizeAnimation a = new ResizeAnimation(terminelayout);
				a.setDuration(250);
				a.setParams(lp.height, (int) deviceheight);
				terminelayout.startAnimation(a);

				RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) terminelayout2
						.getLayoutParams();
				ResizeAnimation a2 = new ResizeAnimation(terminelayout2);
				a2.setDuration(250);
				a2.setParams(lp2.height, (int) deviceheight);
				terminelayout2.startAnimation(a2);

				if (termineArrayList.size() > 0) {
					 TermineListVO terVo=termineArrayList.get(datePosition);
					
					String upToNCharacters = terVo.termineinfo;
					if(terVo.termineinfo!=null)
					{
						upToNCharacters = upToNCharacters.substring(0,
								Math.min(upToNCharacters.length(), 500));
					}
					else
						upToNCharacters = "";
					
					infotextview.setText(upToNCharacters);
				}
			}
		});// end of information

		speichbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (termineArrayList.size() > 0) {
					setButtombackcolor();
					speichbtn.setBackgroundColor(getColorWithAlpha(Color.WHITE,
							0.1f));
					terVo = termineArrayList.get(datePosition);
					Calendar cal = Calendar.getInstance();
					Calendar cal2 = Calendar.getInstance();
					String givenDateString = terVo.termineextratext;
					try {
						cal.setTime(new SimpleDateFormat("dd.MM.yyyy HH:mm")
								.parse(givenDateString));
						cal2.setTime(new SimpleDateFormat("dd.MM.yyyy HH:mm")
								.parse(terVo.termineExtraEnddate));
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}// end of catch
					Intent intent = new Intent(Intent.ACTION_EDIT);
					intent.setType("vnd.android.cursor.item/event");
					intent.putExtra("beginTime", cal.getTimeInMillis());
					intent.putExtra("allDay", false);
					intent.putExtra("rrule", "FREQ=YEARLY");
					intent.putExtra("endTime", cal2.getTimeInMillis());
					intent.putExtra("title", terVo.terminetitle);
					intent.putExtra("description", "");
					intent.putExtra("eventLocation", terVo.terminelocation);
					startActivity(intent);
				}// end of if
			}
		});// end of calanderevent

		teilnehmenbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				tempbuttontext = teilnehmenbtn.getText().toString();
				
				new getUpdatedParseDataAsync().execute();
			}
		});// end of teilnehmenbtn

		return parentView;
	}// end of oncreate()

	private void initView() {

		// termineArrayList = getListData();
		new getParseDataAsync().execute();

	}

	public class getParseDataAsync extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			myProgressBar2.setVisibility(View.VISIBLE);
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

			termineArrayList = new ArrayList<TermineListVO>();
			// altstagArrayList2 = new ArrayList<AltsasbachertagVO>();

			termineArrayList.clear();
			for (int i = 0; i < parseObj.size(); i++) {

				SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
				formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
				SimpleDateFormat tempformatter = new SimpleDateFormat(
						"dd.MM.yyyy HH:mm");
				tempformatter.setTimeZone(TimeZone.getTimeZone("UTC"));
				
				SimpleDateFormat tempformatter2 = new SimpleDateFormat(
						"dd.MM.yyyy");
				//tempformatter2.setTimeZone(TimeZone.getTimeZone("UTC"));

				TermineListVO terVO = new TermineListVO();

				terVO.terminetitle = parseObj.get(i).getString("Titel");
				terVO.termineinfo = parseObj.get(i).getString("Beschreibung");
				terVO.terminelocation = parseObj.get(i).getString("Ort");
				terVO.termineparticipateNo = parseObj.get(i).getLong(
						"AnzahlTeilnehmer");
				terVO.termineObjectId = parseObj.get(i).getObjectId();
				
				terVO.termineStartdate = (formatter.format(parseObj.get(i)
						.getDate("Uhrzeitvon")));
				terVO.termineEnddate = (formatter.format(parseObj.get(i)
						.getDate("Uhrzeitbis")));
				
				terVO.termineextratext = (tempformatter.format(parseObj.get(i)
						.getDate("Uhrzeitvon")));
				terVO.termineExtraEnddate = (tempformatter.format(parseObj.get(
						i).getDate("Uhrzeitbis")));

				if ((tempformatter2.format(parseObj.get(i)
						.getDate("Uhrzeitvon"))).equals(tempformatter2
						.format(parseObj.get(i).getDate("Uhrzeitbis")))) {
					
					terVO.terminedatetime = tempformatter2.format(parseObj
							.get(i).getDate("Uhrzeitvon"))
							+ " - "
							+ terVO.termineStartdate
							+ " Uhr bis "
							+ terVO.termineEnddate + " Uhr";
				} else {
					terVO.terminedatetime = tempformatter2.format(parseObj
							.get(i).getDate("Uhrzeitvon"))
							+ " - "
							+ terVO.termineStartdate
							+ " Uhr bis "
							+ terVO.termineEnddate + " Uhr";
				}

				int k = sharedPreferences.getInt(terVO.terminetitle, 0);
				System.out.println("\n*****" + k);
				if (k == 0) {
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putInt(terVO.terminetitle, 0);
					editor.commit();
				}
				termineArrayList.add(terVO);
			}

			if (termineArrayList.size() > 0) {
				terVo = termineArrayList.get(0);
				staticterVo = termineArrayList.get(0);
				myprogrssbarBar.setVisibility(View.VISIBLE);
				//backimageview.setVisibility(View.INVISIBLE);

				changeButtontext();
				
				if (webImageCache == null) {
					webImageCache = new WebImageCache(MenuActivity.myactivity);
				}
				
				try {
					if(terVo.termineObjectId!="")
					Localbitmap= webImageCache.get(terVo.termineObjectId);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				if(Localbitmap!=null)
				{
					try{
					BitmapDrawable ob = new BitmapDrawable(
							getResources(), Localbitmap);
					Imagebitmap=Localbitmap;
					backimageview
							.setBackgroundDrawable(ob);
					Localbitmap=null;
					myprogrssbarBar
							.setVisibility(View.INVISIBLE);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}//end of local bitmap
				else
				{
					if (AppUtils.isNetworkAvailable(MenuActivity.myactivity))
					{
						try {
						ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
								"Termine");
//						if (AppUtils.isNetworkAvailable(MenuActivity.myactivity))
//						query.fromLocalDatastore();
						
						query.getInBackground(terVo.termineObjectId,
								new GetCallback<ParseObject>() {
									public void done(ParseObject object,
											ParseException e) {
										ParseFile fileObject = (ParseFile) object
												.get("Bild");
										fileObject
												.getDataInBackground(new GetDataCallback() {
													public void done(byte[] data,
															ParseException e) {
														try {
															BitmapDrawable ob = null;	
															Bitmap bmp;
														if (e == null) {
															try {
																
															
															Log.d("test",
																	"We've got data in data.");
															 bmp = BitmapFactory
																	.decodeByteArray(
																			data, 0,
															
																			data.length);
															
															if(bmp!=null)
															{
																try{
														    ob = new BitmapDrawable(
																	getResources(), bmp);
															webImageCache.put(terVo.termineObjectId, bmp);
															Localbitmap=null;
															Imagebitmap=bmp;
															if(bmp!=null)
															backimageview
																	.setBackgroundDrawable(ob);
																} catch (Exception e2) {
																	// TODO: handle exception
																}
															}
															myprogrssbarBar
																	.setVisibility(View.INVISIBLE);
															} catch (Exception e2) {
																// TODO: handle exception
																e.printStackTrace();
															}
														//	backimageview
															//		.setVisibility(View.VISIBLE);

														} else {
															Log.d("test",
																	"There was a problem downloading the data.");
														}
														
															
														} catch (Exception e2) {
															// TODO: handle exception
														}
													}
												});
									}
								});
						
						} catch (Exception e) {
							// TODO: handle exception
						}
					}//end of if for internet
					
					
					else
					{
						final AlertDialog alertDialog = new AlertDialog.Builder(
		                        MenuActivity.myactivity).create();
		        alertDialog.setTitle("Keine Internetverbindung");
		        alertDialog.setMessage("Bitte mit dem Internet verbinden um fortzufahren");
		        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int which) {
		                	alertDialog.dismiss();
		                }
		        });
		        alertDialog.show();
					}
				}//end of else
				// terminelayout.setBackgroundResource(terVo.terminebackimageid);				
				if (terVo.terminetitle.length() > 28) {
					String upToNCharacters = terVo.terminetitle;
					upToNCharacters = upToNCharacters.substring(0,
							Math.min(upToNCharacters.length(), 30));
					upToNCharacters = upToNCharacters + "...";
					titletextview.setTextSize(25);
					titletextview.setText(upToNCharacters);
				} else {
					titletextview.setTextSize(29);
					titletextview.setText(terVo.terminetitle);
				}

				String upToNCharacters = terVo.terminedatetime;
				upToNCharacters = upToNCharacters.substring(0,
						Math.min(upToNCharacters.length(), 42));
				upToNCharacters = upToNCharacters + "...";
				datetimetextview.setText(upToNCharacters);

				extratextview.setText(terVo.terminelocation);
				infotextview.setText("");
				
			}// end of if

			// changeButtontext(); //*********************************Change
			// button text
             myscrollview.setEnabled(false);
			  setListValues(termineArrayList);
			 myscrollview.smoothScrollTo(0, 0);
			myProgressBar2.setVisibility(View.INVISIBLE);
			//.setFocusable(true);

		}// end of postexecute()

	}// end of Async getParseDataAsync class

	// ***********************************************************************************************
	public class getUpdatedParseDataAsync extends
			AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//parseObj.clear();
			//displayDialog();
			teilnehmenbtn.setText("Einen Moment..");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
//			if (AppUtils.isNetworkAvailable(MenuActivity.myactivity))
//			{
				try {
					terVo=termineArrayList.get(datePosition);
					ParseQuery<ParseObject> query = ParseQuery.getQuery("Termine");
			        query.whereEqualTo("Titel",terVo.terminetitle);
			        query.findInBackground(new FindCallback<ParseObject>() {
			            @Override
			            public void done(List<ParseObject> userList, ParseException e) {
			               
			                 if (e == null) {
			                     if (userList.size()>0) {

			                     //for (int i = 0; i < userList.size(); i++) {
			                         ParseObject p = userList.get(0);  
			                         personcount=p.getLong("AnzahlTeilnehmer");
			                      //email = p.getString("email");
			                      //password  = p.getString("password");
			                       // } 
			                     }

			                    }
			                 else {
			                        Log.d("score", "Error: " + e.getMessage());
			                       // Alert.alertOneBtn(getActivity(),"Something went wrong!");
			                    }   
			            }
			        });
					
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				
			//}

			return null;
		}//end of do in background

		protected void onPostExecute(String str_resp) {

			if (termineArrayList.size() > 0)
			terVo=termineArrayList.get(datePosition);
			
			if (tempbuttontext.equals("Teilnehmen")) {
				if (termineArrayList.size() > 0) {

					personcount++;

					if (personcount < 1)
						personcount = 1;

					ObjectId = terVo.termineObjectId;

					int k = sharedPreferences.getInt(terVo.terminetitle, 0);
					System.out.println("\n*****" + k);
					if (k == 0) {
						SharedPreferences.Editor editor = sharedPreferences
								.edit();
						editor.putInt(terVo.terminetitle, 1);
						editor.commit();
					}
					// new getUpdatedParseDataAsync().execute();
				}// end of inner if
			}// end of if
			else if (tempbuttontext.equals("Absagen")) {
				if (termineArrayList.size() > 0) {

					

					personcount --;

					if (personcount < 1)
						personcount = 0;

					ObjectId = terVo.termineObjectId;

					int k = sharedPreferences.getInt(terVo.terminetitle, 0);
					System.out.println("\n*****" + k);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putInt(terVo.terminetitle, 0);
					editor.commit();

					// new getUpdatedParseDataAsync().execute();
				}// end of inner if
			}// end of else

			
			new putParseDataAsync().execute();
			

		}// end of postexecute()

	}// end of Async putParseDataAsync class

	public class putParseDataAsync extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// displayDialog();
			teilnehmenbtn.setText("Einen Moment..");

		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			UpdateData();
			return null;
		}

		protected void onPostExecute(String str_resp) {

			changeButtontext();
			

		}

	}// end of Async putParseDataAsync class

	public void UpdateData() {

		ParseQuery query = new ParseQuery("Termine");
		query.getInBackground(ObjectId, new GetCallback() {
			public void done(final ParseObject object, ParseException e) {
				if (e == null) {

					object.put("AnzahlTeilnehmer", personcount);
					object.saveInBackground(new SaveCallback() {
						public void done(ParseException e) {

							object.put("AnzahlTeilnehmer", personcount);

						}
					});

				}

				else {
					e.printStackTrace();
				}

			}
		});

	}// end of update()

	public void getData() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Termine");
		// query.orderByDescending("createdAt");
		
		try {
			parseObj = query.find();
			 ParseObject.pinAllInBackground(parseObj);
			 
			//
			// UpdatedparseObj = query.find();
			//
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}// end of getdata

	public void getLocalDataStore() {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Termine");
		// query.orderByDescending("createdAt");
		query.fromLocalDatastore();
		try {

			parseObj = query.find();
			// UpdatedparseObj=query.find();

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

//	public static void updateListViewHeight(ListView myListView) {
//		try {
//		
//		ListAdapter myListAdapter = myListView.getAdapter();
//		if (myListAdapter == null) {
//			return;
//		}
//		// get listview height
//		int totalHeight = 0;
//		int adapterCount = myListAdapter.getCount();
//		for (int size = 0; size < adapterCount; size++) {
//			View listItem = myListAdapter.getView(size, null, myListView);
//			listItem.measure(0, 0);
//			totalHeight += listItem.getMeasuredHeight();
//		}
//		// Change Height of ListView
//		ViewGroup.LayoutParams params = myListView.getLayoutParams();
//		params.height = totalHeight
//				+ (myListView.getDividerHeight() * (adapterCount - 1));
//		myListView.setLayoutParams(params);
//		myscrollview.fullScroll(ScrollView.FOCUS_UP);
//		//myscrollview.requestFocus();
//		
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
	
	//**********************************************************
		void setListValues(final ArrayList<TermineListVO> termineArrayList)
		{
			try {
				
	    RelativeLayout mainlayout = (RelativeLayout) parentView.findViewById(R.id.ParentLayout);
	    mainlayout.removeAllViews();
	    for (int i = 0; i <termineArrayList.size(); i++) {
				
				final TermineListVO terminevo=termineArrayList.get(i);

				// ******************************************
				RelativeLayout layout= new RelativeLayout(MenuActivity.myactivity);
				layout.setId(100+i);
				RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				layoutParams.topMargin=10;
				//layoutParams.bottomMargin=100;
				if(i!=0)
				layoutParams.addRule(RelativeLayout.BELOW,99+i);
				
				layout.setBackgroundResource(R.drawable.belowlight_border);
				layout.setLayoutParams(layoutParams);
				//layout.setBackgroundResource(R.drawable.lightborder);

				RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

				//params1.setMargins(5,25,5,55);
//				int maxLength = 3;
//				params1.leftMargin=10;
//				
//				params2.leftMargin=10;
				
				final TextView tv1 = new TextView(MenuActivity.myactivity);
				params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				params1.leftMargin=35;
				tv1.setId(1000+i);
				tv1.setPadding(5, 15, 5, 5);
				tv1.setTextSize(15);
			    //tv1.setTypeface(null, Typeface.BOLD);
				tv1.setTextColor(Color.parseColor("#000000"));
				tv1.setText(terminevo.terminetitle);
				
				 final TextView tv2 = new TextView(MenuActivity.myactivity);
					params2.addRule(RelativeLayout.BELOW,tv1.getId());
					params2.leftMargin=35;
					tv2.setId(2000+i);
					tv2.setTextSize(10);
					tv2.setPadding(5, 10, 5, 20);
					tv2.setTextColor(Color.parseColor("#939393"));
					 tv2.setText(terminevo.terminedatetime);
				
				
				layout.addView(tv1, params1);
				layout.addView(tv2, params2);
				
				// *******************************************
				mainlayout.addView(layout,layoutParams);
				
				final int k=i;
				layout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						datePosition = k;
						setButtombackcolor();

						RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) terminelayout
								.getLayoutParams();
						ResizeAnimation a = new ResizeAnimation(
								terminelayout);
						a.setDuration(250);
						a.setParams(lp.height, textviewheight);
						terminelayout.startAnimation(a);

						RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) terminelayout2
								.getLayoutParams();
						ResizeAnimation a2 = new ResizeAnimation(
								terminelayout2);
						a2.setDuration(250);
						a2.setParams(lp2.height, textviewheight);
						terminelayout2.startAnimation(a2);

						if (termineArrayList.size() > 0) {
							terVo = termineArrayList.get(k);
							staticterVo = termineArrayList.get(k);

							changeButtontext();

							myprogrssbarBar.setVisibility(View.VISIBLE);
							//backimageview.setVisibility(View.INVISIBLE);
							if (webImageCache == null) {
								webImageCache = new WebImageCache(MenuActivity.myactivity);
							}
							
							     try{
								if(terVo.termineObjectId!="")
								Localbitmap= webImageCache.get(terVo.termineObjectId);
							     } catch (Exception e2) {
										// TODO: handle exception
							    	 e2.printStackTrace();
									}
							if(Localbitmap!=null)
							{
								try{
								BitmapDrawable ob = new BitmapDrawable(
										getResources(), Localbitmap);
								Imagebitmap=Localbitmap;
								backimageview
										.setBackgroundDrawable(ob);
								Localbitmap=null;
								myprogrssbarBar
										.setVisibility(View.INVISIBLE);
								 } catch (Exception e2) {
										// TODO: handle exception
							    	 e2.printStackTrace();
									}
							}//end of local bitmap
							else
							{
								try {
								if (AppUtils.isNetworkAvailable(MenuActivity.myactivity))
								{
									
									ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
											"Termine");
//									if (AppUtils.isNetworkAvailable(MenuActivity.myactivity))
//									query.fromLocalDatastore();
									
									query.getInBackground(terVo.termineObjectId,
											new GetCallback<ParseObject>() {
												public void done(ParseObject object,
														ParseException e) {
													ParseFile fileObject = (ParseFile) object
															.get("Bild");
													fileObject
															.getDataInBackground(new GetDataCallback() {
																public void done(byte[] data,
																		ParseException e) {
																	try {
																		BitmapDrawable ob = null;
																		Bitmap bmp;
																	if (e == null) {
																		
																		try {
																		Log.d("test",
																				"We've got data in data.");
																	     bmp = BitmapFactory
																				.decodeByteArray(
																						data, 0,
																						data.length);
																	
																		if(bmp!=null)
																		{	
																			try{
																	       ob = new BitmapDrawable(
																				getResources(), bmp);
																		
																		webImageCache.put(terVo.termineObjectId, bmp);
																		Localbitmap=null;
																		Imagebitmap=bmp;
																		if(bmp!=null)
																		backimageview
																				.setBackgroundDrawable(ob);
																		myprogrssbarBar
																				.setVisibility(View.INVISIBLE);
																			} catch (Exception e2) {
																				// TODO: handle exception
																				e2.printStackTrace();
																			}
																		}
																	//	backimageview
																		//		.setVisibility(View.VISIBLE);
																		} catch (Exception e2) {
																			// TODO: handle exception
																			e2.printStackTrace();
																		}

																	} else {
																		Log.d("test",
																				"There was a problem downloading the data.");
																	}
																	} catch (Exception e2) {
																		// TODO: handle exception
																		e.printStackTrace();
																	}
																}
															});
												}
											});
								}//end of if for internet
								else
								{
									final AlertDialog alertDialog = new AlertDialog.Builder(
					                        MenuActivity.myactivity).create();
					        alertDialog.setTitle("Keine Internetverbindung");
					        alertDialog.setMessage("Bitte mit dem Internet verbinden um fortzufahren");
					        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					                public void onClick(DialogInterface dialog, int which) {
					                	alertDialog.dismiss();
					                }
					        });
					        alertDialog.show();
								}
								
									
								} catch (Exception e) {
									// TODO: handle exception
							     e.printStackTrace();
								}
							}//end of else
							// terminelayout.setBackgroundResource(terVo.terminebackimageid);
							if (terVo.terminetitle.length() > 28) {
								String upToNCharacters = terVo.terminetitle;
								upToNCharacters = upToNCharacters.substring(0,
										Math.min(upToNCharacters.length(), 30));
								upToNCharacters = upToNCharacters + "...";
								titletextview.setTextSize(25);
								titletextview.setText(upToNCharacters);
							} else {
								titletextview.setTextSize(29);
								titletextview.setText(terVo.terminetitle);
							}
							
							

							String upToNCharacters = terVo.terminedatetime;
							upToNCharacters = upToNCharacters.substring(0,
									Math.min(upToNCharacters.length(), 42));
							upToNCharacters = upToNCharacters + "...";
							datetimetextview.setText(upToNCharacters);
							extratextview.setText(terVo.terminelocation);
							infotextview.setText("");
						}
						 myscrollview.smoothScrollTo(0, 0);
						
						
					}//end of onclick
				});//********************end of onclick listner
				
			}// end of for
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}//end of list values
		//**********************************************************
	
	

	public static int getColorWithAlpha(int color, float ratio) {
		int newColor = 0;
		int alpha = Math.round(Color.alpha(color) * ratio);
		int r = Color.red(color);
		int g = Color.green(color);
		int b = Color.blue(color);
		newColor = Color.argb(alpha, r, g, b);
		return newColor;
	}

	public void setButtombackcolor() {
		infobtn.setBackgroundColor(getColorWithAlpha(Color.BLACK, 0.9f));
		speichbtn.setBackgroundColor(getColorWithAlpha(Color.BLACK, 0.9f));
		teilnehmenbtn.setBackgroundColor(getColorWithAlpha(Color.BLACK, 0.9f));
	}

	public void changeButtontext() {
		if (termineArrayList.size() > 0) {
			terVo = termineArrayList.get(datePosition);
			int k = sharedPreferences.getInt(terVo.terminetitle, 0);
			System.out.println("\n*****" + k);
			if (k == 0) {
				teilnehmenbtn.setText("Teilnehmen");
			} else {
				teilnehmenbtn.setText("Absagen");
			}

		}// end of inner if

	}

}
