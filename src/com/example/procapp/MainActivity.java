package com.example.procapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @author Tristan Cunningham / generated Template
 *
 */
public class MainActivity extends FragmentActivity implements OnClickListener {

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	Diary current;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		mViewPager.setOnPageChangeListener(
	    	new ViewPager.SimpleOnPageChangeListener() {
	        	@Override
	            public void onPageSelected(int position) {
	              	if(position == 0){
	            		getActionBar().show();
	            	} else {
	            		getActionBar().hide();
	            	}
	            }
	   });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.action_compose:
	        	AddDialog ad = new AddDialog();
	        	ad.show(getFragmentManager(), "sarf");
	        	return true;
	        	
	        default:
	        	return true;
	    }
	}
		
	@Override
	public void onClick(View v) {
	}
}
