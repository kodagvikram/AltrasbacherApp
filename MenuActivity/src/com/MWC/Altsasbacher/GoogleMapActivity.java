package com.MWC.Altsasbacher;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.MWC.AltsasbacherAppVO.AltsasbachertagVO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapActivity extends Activity {
//	 static final LatLng HAMBURG = new LatLng(53.558, 9.927);
//	  static final LatLng KIEL = new LatLng(53.551, 9.993);
	  
	  public  LatLng LOCATIONMARK;
	  private GoogleMap map;
	  public AltsasbachertagVO altstagVo;
	  public TextView tagtitle,tagtime,taglocation,tagdes;
	  
	  @SuppressLint("NewApi")
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
		  try {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_google_map);
			
		  tagtitle=(TextView)findViewById(R.id.mapEventTitletextview);
	      tagtime=(TextView)findViewById(R.id.mapTimetextview);
	      taglocation=(TextView)findViewById(R.id.mapLocationtextview);
	      tagdes=(TextView)findViewById(R.id.maplocationinfotextview);
	      
	       altstagVo=AltsasbachertagFragment.ststicaltstagVo;
	       
	       if(altstagVo.taglocation!=null)
	       {
	       LOCATIONMARK=new LatLng(altstagVo.xcoordinate,altstagVo.ycoordinate);
	       tagtitle.setText(altstagVo.tagtitle);
	       tagtime.setText(altstagVo.tagstartdate);
	       taglocation.setText(" - "+altstagVo.taglocation);
	       tagdes.setText(altstagVo.taglongdes);
		    
	       
	    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
	        .getMap();
	    map.setMapType(GoogleMap.MAP_TYPE_HYBRID); 
	    
//	    Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
//	        .title("Hamburg"));
//	    
//	    Marker kiel = map.addMarker(new MarkerOptions()
//	        .position(KIEL)
//	        .title("Kiel")
//	        .snippet("Kiel is cool")
//	        .icon(BitmapDescriptorFactory
//	            .fromResource(R.drawable.ic_launcher)));
	    
	    Marker Location = map.addMarker(new MarkerOptions()
        .position(LOCATIONMARK)
        .title(altstagVo.taglocation)
        .snippet(altstagVo.tagtitle)
        );

	    // Move the camera instantly to hamburg with a zoom of 15.
	    map.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATIONMARK, 15));
	    // Zoom in, animating the camera.
	    map.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
	       }//end of if
	  
	    } catch (Exception e) {
			// TODO: handle exception
		}
	    
	    }//end of oncreate()

	} //end of class

