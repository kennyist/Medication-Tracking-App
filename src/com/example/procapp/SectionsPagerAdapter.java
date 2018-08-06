package com.example.procapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 
 * @author Tristan Cunningham / generated Template
 *
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

	public SectionsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		if(position == 0){
			Fragment fragment = new DiaryFrag();
			return fragment;
		} else if (position == 1){
			Fragment fragment = new InfoFrag();
			return fragment;
		} else{
			Fragment fragment = new AboutFrag();
			return fragment;
		}
	}

	@Override
	public int getCount() {
		// Show 3 total pages.
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return "Diary";
		case 1:
			return "Info";
		case 2:
			return "About";
		}
		return null;
	}
}