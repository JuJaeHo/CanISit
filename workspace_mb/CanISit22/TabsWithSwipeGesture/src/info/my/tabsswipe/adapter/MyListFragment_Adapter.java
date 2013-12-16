package info.my.tabsswipe.adapter;

import info.androidhive.tabsswipe.R;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MyListFragment_Adapter extends BaseAdapter {

	Context context;
	int itemlayout;
	ArrayList<MyListFragment_Item> arrayItems;
	LayoutInflater inflater;

	public MyListFragment_Adapter(Context c, int sublayout,
			ArrayList<MyListFragment_Item> list) {
		this.context = c;
		this.itemlayout = sublayout;
		this.arrayItems = list;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrayItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		final int pos = position;

		if (convertView == null) {
			convertView = inflater.inflate(itemlayout, parent, false);
		}

		TextView dirName = (TextView) convertView
				.findViewById(R.id.dirName_inMylist);

		String drNm = arrayItems.get(pos).getDirName();
		int drId = arrayItems.get(pos).getDirId();

		dirName.setText(drId + ". " + drNm);

		return convertView;
	}
};
