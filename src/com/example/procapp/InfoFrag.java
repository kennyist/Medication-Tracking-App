package com.example.procapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

/**
 * Create and setup Info view
 * @author Tristan Cunningham
 *
 */
public class InfoFrag extends Fragment {
	
	ExpandableListAdapter  adapter;
	ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listData;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setRetainInstance(true);
		View rootView = inflater.inflate(R.layout.infoscreen,
					container, false);

	    listData = new HashMap<String, List<String>>();
	    listDataHeader = new ArrayList<String>();
	    ArrayList<String> l = new ArrayList<String>();
	    l.add("Blah, something here");
	    
	    for(int i = 0; i < 20; i++){
	    	if(i < 7){
	    		listDataHeader.add("Drug "+i);
	    	} else if(i < 14){
	    		int j = -7 + i;
	    		listDataHeader.add("illness "+j);
	    	} else {
	    		int j = -14 + i;
	    		listDataHeader.add("other "+j);
	    	}
	    	
	    	listData.put(listDataHeader.get(i), l);
	    }      
		
		expListView = (ExpandableListView) rootView.findViewById(R.id.explistview);
		
		adapter = new ExpandableListAdapter(getActivity(), listDataHeader, listData);
		
		expListView.setAdapter(adapter);

		return rootView;
	}
}
