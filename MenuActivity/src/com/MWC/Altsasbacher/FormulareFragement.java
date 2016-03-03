package com.MWC.Altsasbacher;

import java.util.ArrayList;

import com.MWC.AltsasbacherAppVO.WichtigeListVo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class FormulareFragement extends Fragment {
	private View parentView;
	public View mview;
	private ListView formuleListView;

	String[] web = { "Beitrittserklärung", "Förderantrag",
			"Commoveamus\nFörderantrag " ,"Antrag auf\nFahkostenerstattung","Commoveamus\nSchülerinfo"};
	Integer[] imageId = { R.drawable.beitrittserklarungimage, R.drawable.foerderantragimage, R.drawable.commoantragimage, R.drawable.fahrkostenerstattungimage, R.drawable.schulerinfoimage };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		parentView = inflater.inflate(R.layout.formularfragementxml, container,
				false);
		formuleListView = (ListView) parentView
				.findViewById(R.id.formularefrontListview);

		initView();
		return parentView;
	}

	private void initView() {

		FormulareFrontAdapter adapter = new FormulareFrontAdapter(
				MenuActivity.myactivity, web, imageId);
		formuleListView.setAdapter(adapter);
		formuleListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	public void onItemClick(AdapterView<?> parent, View view,
		int position, long id) {
		
				 //Toast.makeText(MenuActivity.myactivity.getApplicationContext(), "itemclicked ="+position, Toast.LENGTH_LONG).show();
				 if(position==0)
				 {
					  Intent intent=new Intent(MenuActivity.myactivity.getApplicationContext(),FirstrFormFormulareActivity.class);
			         // intent.putExtra("ALBUMPHOTS", albenArrayList);
			           startActivity(intent); 	
				 }
				 else if(position==1)
				 {
					  Intent intent=new Intent(MenuActivity.myactivity.getApplicationContext(),SecondFormFormulareActivity.class);
			         // intent.putExtra("ALBUMPHOTS", albenArrayList);
			           startActivity(intent); 	
				 }
				 else if(position==2)
				 {
					  Intent intent=new Intent(MenuActivity.myactivity.getApplicationContext(),ThirdFormFormulareActivity.class);
			         // intent.putExtra("ALBUMPHOTS", albenArrayList);
			           startActivity(intent); 	
				 }
				 else if(position==3)
				 {
					  Intent intent=new Intent(MenuActivity.myactivity.getApplicationContext(),ForthFormFormullareActivity.class);
			         // intent.putExtra("ALBUMPHOTS", albenArrayList);
			           startActivity(intent); 	
				 }
				 else if(position==4)
				 {
					  Intent intent=new Intent(MenuActivity.myactivity.getApplicationContext(),FifthFormFormulareActivity.class);
			         // intent.putExtra("ALBUMPHOTS", albenArrayList);
			           startActivity(intent); 	
				 }
					}
				});
	}

}
