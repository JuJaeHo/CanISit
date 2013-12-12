package info.my.tabsswipe.callee.adapter;

import info.androidhive.tabsswipe.R;
import info.my.tabsswipe.callee.BusStop_info_popup;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BusStop_info_Adapter extends BaseAdapter {
	Context context;
	int itemlayout;
	ArrayList<BusStop_info_Item> arrayItems;
	LayoutInflater inflater;

	//
	BusStop_info_popup myPopup;

	//
	public BusStop_info_Adapter(Context c, int sublayout,
			ArrayList<BusStop_info_Item> list) {
		this.context = c;
		this.itemlayout = sublayout;
		this.arrayItems = list;
		//
		myPopup = new BusStop_info_popup(c);
		//

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

		if (convertView == null) {
			convertView = inflater.inflate(itemlayout, parent, false);
		}

		final int pos = position;
		final View cvV = convertView;

		final TextView busNumber = (TextView) convertView
				.findViewById(R.id.busNumber);
		final TextView busType = (TextView) convertView
				.findViewById(R.id.busType);
		final TextView busLeftTime = (TextView) convertView
				.findViewById(R.id.busLeftTime);
		ImageView btn = (ImageView) convertView.findViewById(R.id.itembutton1);

		busNumber.setText(arrayItems.get(position).getRtNm());
		if (arrayItems.get(position).getStationNm1() == null)
			busType.setText("현재위치: " + "p");
		else
			busType.setText("현재위치: " + arrayItems.get(position).getStationNm1());
		// if (arrayItems.get(position).getTraTime1().equals("0sec"))
		// busLeftTime.setText(";-(");
		// else
		busLeftTime.setText(arrayItems.get(position).getTraTime1());

		btn.setOnClickListener(new ImageButton.OnClickListener() { // 리스트삭제하기

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				// arrayItems.remove(pos);
				// notifyDataSetChanged();

				// Intent intent = new Intent(v.getContext(),
				// BusStop.class);
				// intent.putExtra("name", text.getText());
				// intent.putExtra("age", text1.getText());
				Toast.makeText(
						context,
						busNumber.getText() + "/" + busType.getText() + "/"
								+ busLeftTime.getText()
								+ "; This is in the adapter class",
						Toast.LENGTH_LONG).show();
				// v.getContext().startActivity(intent);

			}
		});

		return convertView;
	}
}
