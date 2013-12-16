package info.my.tabsswipe.adapter;

import info.androidhive.tabsswipe.R;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BusStopFragment_Adapter extends BaseAdapter {

	Context context;
	int itemlayout;
	ArrayList<BusStopFragment_Item> arrayItems;
	LayoutInflater inflater;

	public BusStopFragment_Adapter(Context c, int sublayout,
			ArrayList<BusStopFragment_Item> list) {
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

		TextView busStopName = (TextView) convertView
				.findViewById(R.id.busStopName);
		TextView busStopNum = (TextView) convertView
				.findViewById(R.id.busStopNum);
		Button btn = (Button) convertView.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "This is in BusStopFragment_Adapter",
						Toast.LENGTH_LONG).show();
			}
		});

		/*
		 * List의 버스이름, 버스번호 설정
		 */
		String stNm = arrayItems.get(pos).getStNm();
		String arsId = arrayItems.get(pos).getArsId();

		// 보이기 위해서 쪼개줌. 08110 -> 08-110
		busStopName.setText(stNm);
		if (arsId.length() == 5)
			busStopNum.setText(arsId.subSequence(0, 2) + "-"
					+ arsId.substring(2));
		else
			busStopNum.setText(arsId + "은(는) 경기도 API를 승인 받아야 하는 듯?");

		return convertView;
	}
};
