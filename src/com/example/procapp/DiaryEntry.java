package com.example.procapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 
 * @author Tristan Cunningham
 *
 */
public class DiaryEntry implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Drugs drug = Drugs.BUPRENORPHINE;
	private int ammount = 0;
	private Calendar date = Calendar.getInstance();
	
	/**
	 * Create a new DiaryEntry
	 * @param Drugs drug
	 * @param int amount
	 */
	public DiaryEntry(Drugs drug, int ammount) {
		super();
		this.drug = drug;
		this.ammount = ammount;
	}	
	
	/**
	 * Create a new DiaryEntry over riding create day
	 * @param Drugs drug
	 * @param int amount
	 * @param int overRideDay
	 */
	public DiaryEntry(Drugs drug, int ammount, int overRideDay) {
		super();
		this.drug = drug;
		this.ammount = ammount;
		this.date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH) - overRideDay);
	}
	
	/**
	 * Return the drug of the entry
	 * @return Drugs drug
	 */
	public Drugs getDrug() {
		return this.drug;
	}

	/**
	 * Get the diary drug amount
	 * @return int amount
	 */
	public int getAmmount() {
		return this.ammount;
	}

	/**
	 * Return the day of the week this was created
	 * @return int Day (0-6)
	 */
	public int GetDay(){
		return this.date.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * Return the Month and day of the month this was created
	 * @return int[0] = Month (0-11), int[1] = Day of the month (0-31)
	 */
	public int[] GetDateMD(){
		int[] i = new int[2];
		i[0] = this.date.get(Calendar.MONTH);
		i[1] = this.date.get(Calendar.DAY_OF_MONTH);
		return i;
	}
	
	/**
	 * Return a formatted string of the date and time this was created
	 * Returned as "Day name (short), DD/MM/YY hours:minuets:seconds AM/PM"
	 * @return String formattedDate
	 */
	public String DateFormatted(){
		SimpleDateFormat simDate = new SimpleDateFormat("E, dd/MM/yy hh:mm:ss a");
		return simDate.format(date.getTime());
	}
}
