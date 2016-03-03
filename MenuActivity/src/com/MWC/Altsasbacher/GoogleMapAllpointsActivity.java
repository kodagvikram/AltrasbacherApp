package com.MWC.Altsasbacher;

import java.util.ArrayList;

import com.MWC.AltsasbacherAppVO.AltsasbachertagVO;
import com.MWC.AltsasbacherAppVO.GoogleMapAllpointsVO;
import com.MWC.AltsasbacherAppVO.fotoalbenVO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class GoogleMapAllpointsActivity extends Activity {

	 public  LatLng LOCATIONMARK;
	  private GoogleMap map;
	  public  ArrayList<GoogleMapAllpointsVO> googleallpointslist=new ArrayList<GoogleMapAllpointsVO>();
	  
	  @SuppressLint("NewApi")
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
		  try {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_google_map_allpoints);
	    
			
		
	    googleallpointslist=(ArrayList<GoogleMapAllpointsVO>)getIntent().getSerializableExtra("GOOGLEPOINTS");
	      
	       
	       if(googleallpointslist.size()>0)
	       {
	    	   
	       LOCATIONMARK=new LatLng(googleallpointslist.get(0).mapx,googleallpointslist.get(0).mapy);
	       
	    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapAllpoints))
	        .getMap();
	    map.setMapType(GoogleMap.MAP_TYPE_HYBRID); 
	    for(int i=0;i<googleallpointslist.size();i++)
	    {
	    	GoogleMapAllpointsVO allpointvo=googleallpointslist.get(i);
	    	
	    	 createMarker(allpointvo.mapx,allpointvo.mapy,allpointvo.location,allpointvo.eventTitle);
	    }
	    
	    
	    map.setInfoWindowAdapter(new InfoWindowAdapter() {

	        @Override
	        public View getInfoWindow(Marker arg0) {
	            return null;
	        }

	        @Override
	        public View getInfoContents(Marker marker) {

	            View v = getLayoutInflater().inflate(R.layout.samplegooglemapxml, null);

	            TextView info= (TextView) v.findViewById(R.id.Titletextview);
	            TextView info2= (TextView) v.findViewById(R.id.sinppettextview);

	            info.setText(marker.getTitle().toString());
	            info2.setText(marker.getSnippet().toString());

	            return v;
	        }
	    });
	    
	    
	    // Move the camera instantly to hamburg with a zoom of 15.
	    	  map.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATIONMARK, 15));
	           
	    	  //map.animateCamera(CameraUpdateFactory.zoomTo(10));
	                    // Zoom in, animating the camera.
	             map.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
	       }//end of if
	    } catch (Exception e) {
			// TODO: handle exception
		}
	    }//end of oncreate()
	  
	  protected Marker createMarker(double latitude, double longitude,String title,String snippet) {

	        return map.addMarker(new MarkerOptions()
	         .position(new LatLng(latitude,longitude))
	         .title(title)
	         .snippet(snippet)
	         );
	    }

	} //end of class

