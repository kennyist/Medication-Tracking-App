package com.example.procapp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import android.text.format.Time;
import com.jjoe64.graphview.GraphView.GraphViewData;

/**
 * A container class for Diary Entrys
 * @author Tristan Cunningham
 *
 */
public class Diary {
	ArrayList<DiaryEntry> entrys;
	
	/**
	 * Initialise the diary object
	 * Containing all Diary entry
	 */
	public Diary() {
		super();
		this.entrys = new ArrayList<DiaryEntry>();
	}
	
	/**
	 * Add diary entry to the top of the list
	 * @param DiaryEntry item
	 */
	public void AddEntry(DiaryEntry item){
		entrys.add(0, item);
	}
	
	/**
	 * Get a DiaryEntry from the List by index
	 * @param int index
	 * @return DiaryEntry object
	 */
	public DiaryEntry GetEntryAt(int index){
		return this.entrys.get(index);
	}
	
	/**
	 * Delete diary entry at the input index
	 * @param int index
	 */
	public void DeleteEntry(int i){
		entrys.remove(i);
	}
	
	/**
	 * Get the total number of Diary Entrys stored in the list
	 * @return
	 */
	public int Count(){
		return this.entrys.size();
	}
	
	/**
	 * Convert all entrys to a String Arraylist object for listview
	 * @return ArrayList object
	 */
	public ArrayList<HashMap<String, String>> GetAllAsString(){
		ArrayList<HashMap<String, String>> l = new ArrayList<HashMap<String, String>>();
		
		for (DiaryEntry e : entrys) {	// For each diary entry add it to a list hashmap
			HashMap<String, String> ht = new HashMap<String, String>();
			ht.put("line1",e.DateFormatted());
			ht.put("line2",e.getDrug() +" - "+ e.getAmmount()+"Mg");
			l.add(ht);
		}
		return l;
	}
	
	/**
	 * Get all entrys for the selected drug and convert their date and amount data to graph data
	 * @param Drugs drug
	 * @return GraphviewData[] object
	 */
	public GraphViewData[] GetChartData(Drugs drug){
		GraphViewData[] gd = new GraphViewData[14];
		
		Time time = new Time();
		time.setToNow();
		
		int d = time.monthDay, m = time.month;
		
		for(int i = 0; i < 14; i++){
			int total = 0;
			
			for (DiaryEntry de : entrys) {
				if(de.getDrug() == drug){
					int[] dedm = de.GetDateMD();
					if(dedm[0] == m && dedm[1] == d){
						total += de.getAmmount();	// If drug is equal and date is in range, add to total
					}
				}
			}
			
			gd[13 - i] = new GraphViewData(13 - i, total);	// Add to view data in reverse
			
			d--;
			if(d == 0){ 	// Date validating
				d = 31;
				m--;
				if(m == 0){
					m = 12;
				}
			}
		}
		
		return gd;
	}
	
	
	/**
	 * Load DiaryEntrys from saved file
	 * @param file input stream
	 * @throws StreamCorruptedException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void Load(FileInputStream fis) throws StreamCorruptedException, IOException, ClassNotFoundException{
		ObjectInputStream is = new ObjectInputStream(fis);
		ArrayList<DiaryEntry> de = (ArrayList<DiaryEntry>) is.readObject();
		entrys.addAll(de);
		is.close();
	}
	
	/**
	 * Save all DiaryEntrys to local file
	 * @param file input stream
	 * @throws IOException
	 */
	public void Save(FileOutputStream fis) throws IOException{
		ObjectOutputStream os = new ObjectOutputStream(fis);
		os.writeObject(entrys);
		os.close();
	}
}
