package info.my.tabsswipe.adapter;

import info.my.tabsswipe.BusNumberFragment;
import info.my.tabsswipe.BusStopFragment;
import info.my.tabsswipe.MyListFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		// Test version; change both My List and Bus Stop
		 switch (index) {
		 case 0:
		 // Top Rated fragment activity
		 return new MyListFragment();
		 case 1:
		 // Games fragment activity
		 return new BusStopFragment();
		 case 2:
		 // Movies fragment activity
		 return new BusNumberFragment();
		 }
//		switch (index) {
//		case 0:
//			// Top Rated fragment activity
//			return new BusStopFragment();
//
//		case 1:
//			// Games fragment activity
//			return new MyListFragment();
//		case 2:
//			// Movies fragment activity
//			return new BusNumberFragment();
//		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
