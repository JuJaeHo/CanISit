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

public class StationByNameInfo_Adapter extends BaseAdapter {

	Context context;
	int itemlayout;
	ArrayList<StationByNameInfo> arrayItems;
	LayoutInflater inflater;

	public StationByNameInfo_Adapter(Context c, int sublayout,
			ArrayList<StationByNameInfo> list) {
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

		/*
		 * List의 버스이름, 버스번호 설정
		 */
		String stNm = arrayItems.get(pos).getStNm();
		String arsId = arrayItems.get(pos).getArsId();

		// 보이기 위해서 쪼개줌. 08110 -> 08-110
		busStopName.setText(stNm);
		if(arsId.length()==5)
			busStopNum.setText(arsId.subSequence(0, 2) + "-" + arsId.substring(2));
		else
			busStopNum.setText(arsId+"은(는) 없는 정류장인가요? 이것 때문에 자꾸 에러나요.");

		return convertView;
	}
}
;