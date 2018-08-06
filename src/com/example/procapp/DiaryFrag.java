package com.example.procapp;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemLongClickListener;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;

/**
 * Create the Diary list and graph view
 * @author Tristan Cunningham
 *
 */
public class DiaryFrag extends Fragment{
	
	static Diary diary = new Diary();
	static SimpleAdapter ard;
	static ListView lv;
	static View view;
	static int[] colours = {Color.RED, Color.CYAN, Color.GREEN, Color.BLUE, Color.MAGENTA}; 
	static boolean create = true;
	static GraphView graphView;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setRetainInstance(true);
		View rootView = inflater.inflate(R.layout.diary,
					container, false);
		view = rootView;
		
		if(create){		// If initial setup load diary saved data file
			create = false;
			
			try {
				diary.Load(view.getContext().getApplicationContext().openFileInput("procData"));
			} catch (StreamCorruptedException e) {
				Log.i("File load", "Corrupt");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				Log.i("File load", "class not found");
				e.printStackTrace();
			} catch (IOException e) {
				Log.i("File load", "IO exception");
				e.printStackTrace();
			} 
			
			if(diary.Count() == 0){		// If no data exists, Create test data
				Random rnd = new Random();
				for(int i = 0; i < 12; i++){
					diary.AddEntry(new DiaryEntry(Drugs.BUPRENORPHINE, rnd.nextInt(600), i));
					diary.AddEntry(new DiaryEntry(Drugs.MORPHINE, rnd.nextInt(600), i));
					diary.AddEntry(new DiaryEntry(Drugs.OXYCODONE, rnd.nextInt(600), i));
				}
			}
		}
		
		CreateGraph();
		UpdateList();
		
		ListView list = (ListView) rootView.findViewById(R.id.listView1);
		
		list.setOnItemLongClickListener(new OnItemLongClickListener() { // On long hold, open delete entry popup
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3)  {
            	
            	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            	
            	builder.setTitle("Delete entry?");
            	builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            	           public void onClick(DialogInterface dialog, int id) {
            	        	   try {
								RemoveItem(arg2);
							} catch (IOException e) {
								e.printStackTrace();
							}
            	           }
            	       });
            	builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            	           public void onClick(DialogInterface dialog, int id) {

            	           }
            	       });
            	AlertDialog dialog = builder.create();
            	dialog.show();
            	
            	return false;
            }

        });
		
		return rootView;
	}
	
	
	/**
	 * Create and setup graph view on this fragment
	 */
	static void CreateGraph(){
			
		graphView = new LineGraphView(
				view.getContext()// context
		      , "" // heading
		);
		
		UpdateGraph();
		
		
		String[] weekdays = new String[] {"S", "M", "T", "W", "T","F","S"};
		String[] weekdaysEnd = new String[14];
		Time time = new Time(); time.setToNow();
		
		for(int i = 0; i < weekdaysEnd.length; i++){ // Get the last two week Letters from today
			if(i < (weekdaysEnd.length / 2)){
				int d = time.weekDay - (6 - i);
				
				if(d < 0){
				d = 6 - (Math.abs(d + 1));
				}
				
				weekdaysEnd[i] = weekdays[d];
			} else {
			int d = i - 7;
				weekdaysEnd[i] = weekdaysEnd[d];
			}				
		}
					
		graphView.setHorizontalLabels(weekdaysEnd);
		graphView.setShowLegend(true);
		graphView.getGraphViewStyle().setTextSize(15);
		graphView.getGraphViewStyle().setLegendWidth(180);
		graphView.setScrollable(true);
		graphView.setScalable(true);
			 
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.linearLayout1);
		layout.addView(graphView);
	}
	
	/**
	 * Update the graph to add new diary entry data
	 */
	static void UpdateGraph(){
		graphView.removeAllSeries();

		for(int i = 0; i < Drugs.values().length; i++){
			
			graphView.addSeries(new GraphViewSeries(Drugs.values()[i].toString()
					,new GraphViewSeriesStyle(colours[i],2)
					,diary.GetChartData(Drugs.values()[i])));
		}
		
		graphView.redrawAll();			
	}
	
	/**
	 * Update list to show new diary entrys
	 */
	static void UpdateList(){
		lv = (ListView) view.findViewById(R.id.listView1);
		ard = new SimpleAdapter(view.getContext(),diary.GetAllAsString(),android.R.layout.two_line_list_item,new String[] { "line1","line2" },new int[] {android.R.id.text1, android.R.id.text2});
		lv.setAdapter(ard);
	}
	
	/**
	 * Add new diary entry, updating graph and list while saving the data to local file
	 * @param Drugs drug
	 * @param Int amount
	 * @throws IOException On file save error
	 */
	public static void AddItem(Drugs drug, int ammount) throws IOException{
		diary.AddEntry(new DiaryEntry(drug, ammount));
		UpdateList();
		UpdateGraph();
		view.getContext();
		diary.Save(view.getContext().getApplicationContext().openFileOutput("procData",Context.MODE_PRIVATE));
	}		
	
	/**
	 * Remove diary entry, saving the the diary entrys once deleted also updating graph and list
	 * @param Int index
	 * @throws IOException On file save error
	 */
	public static void RemoveItem(int index) throws IOException{
		diary.DeleteEntry(index);
		UpdateList();
		UpdateGraph();
		view.getContext();
		diary.Save(view.getContext().getApplicationContext().openFileOutput("procData", Context.MODE_PRIVATE));
	}
}