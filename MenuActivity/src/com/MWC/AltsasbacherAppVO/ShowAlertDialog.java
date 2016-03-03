package com.MWC.AltsasbacherAppVO;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.MWC.Altsasbacher.MenuActivity;

public class ShowAlertDialog {

	public  static void ShowAlert()
	{
		AlertDialog.Builder builder1 = new AlertDialog.Builder(MenuActivity.myactivity);
	    builder1.setMessage("Bitte mit dem Internet verbinden um fortzufahren");
	    builder1.setCancelable(true);
	    builder1.setPositiveButton("Yes",
	            new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	            dialog.cancel();
	        }
	    });
	    builder1.setNegativeButton("No",
	            new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	            dialog.cancel();
	        }
	    });

	    AlertDialog alert11 = builder1.create();
	    alert11.show();
	}
	
}
