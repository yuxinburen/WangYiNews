package com.davie.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter{

	private List<Fragment> list;
	private String [] arrTab;

	public ViewPagerAdapter(FragmentManager fm, List<Fragment> list,
			String[] arrTab) {
		super(fm);
		this.list = list;
		this.arrTab = arrTab;
	}

	@Override
	public Fragment getItem(int position) {
		return list.get(position);
	}

	@Override
	public int getCount() {
		return list.size();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return arrTab[position];
	}
}