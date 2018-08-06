package com.example.procapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * Create the application info and credits view
 * @author Tristan Cunningham
 *
 */
public class AboutFrag extends Fragment  {
	
	SimpleAdapter ard;
	ListView lv;
	
	/**
	 * Create the application info and credits view
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.about,
					container, false);
		
		ArrayList<HashMap<String, String>> l = new ArrayList<HashMap<String, String>>();
		
		for (int i = 0; i < 4; i++) {
			HashMap<String, String> ht = new HashMap<String, String>();
			ht.put("line1","name "+i);
			ht.put("line2","Credited tasks");
			l.add(ht);
		}
		
		lv = (ListView) rootView.findViewById(R.id.listView1);
		ard = new SimpleAdapter(rootView.getContext(),l,android.R.layout.two_line_list_item,new String[] { "line1","line2" },new int[] {android.R.id.text1, android.R.id.text2});
		
		
		return rootView;
	}
}
