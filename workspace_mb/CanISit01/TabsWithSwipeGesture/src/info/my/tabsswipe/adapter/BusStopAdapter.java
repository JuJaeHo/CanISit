package info.my.tabsswipe.adapter;

import info.androidhive.tabsswipe.R;
import info.my.tabsswipe.callee.BusStop_info;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BusStopAdapter extends BaseAdapter {
	Context context;
	int itemlayout;
	ArrayList<BusStopItem> arrayItems;
	LayoutInflater inflater;

	public BusStopAdapter(Context c, int sublayout, ArrayList<BusStopItem> list) {
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
		final View cvV = convertView;
		if (convertView == null) {
			convertView = inflater.inflate(itemlayout, parent, false);
		}

		final TextView text = (TextView) convertView
				.findViewById(R.id.itemtext1);
		final TextView text1 = (TextView) convertView
				.findViewById(R.id.itemtext2);
		Button btn = (Button) convertView.findViewById(R.id.itembutton1);

		text.setText(arrayItems.get(position).getText());
		text1.setText(arrayItems.get(position).getText2());
		btn.setText("Go");
		btn.setOnClickListener(new Button.OnClickListener() { // 리스트삭제하기

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// arrayItems.remove(pos);
				// notifyDataSetChanged();

//				Intent intent = new Intent(v.getContext(),
//						BusStop.class);
//				intent.putExtra("name", text.getText());
//				intent.putExtra("age", text1.getText());
////				Toast.makeText(context, text.getText() + " " + text1.getText(),
////						Toast.LENGTH_LONG).show();
//				v.getContext().startActivity(intent);
			}
		});

		return convertView;
	}
}
