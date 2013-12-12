package info.my.tabsswipe.callee;

import info.androidhive.tabsswipe.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BusStop_info extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busstop_info);

		/*
		 * get intent
		 */
		Intent intent = getIntent();
		final String arsId = intent.getExtras().get("arsId").toString();
		final String stId = intent.getExtras().get("stId").toString();
		final String stNm = intent.getExtras().get("stNm").toString();
		final String tmX = intent.getExtras().get("tmX").toString();
		final String tmY = intent.getExtras().get("tmY").toString();

		/*
		 * set Title
		 */
		if (arsId.length() == 5)
			setTitle("(" + arsId.subSequence(0, 2) + "-" + arsId.substring(2)
					+ ")" + stNm);
		else
			setTitle("(" + arsId + ")" + stNm);

//		// == list view ===================================
//		ArrayList<BusStop_info_Item> arrayItems = new ArrayList<BusStop_info_Item>();
//		arrayItems.add(new BusStop_info_Item("153", "blue bus",
//				"about 3min 20sec"));
//		arrayItems.add(new BusStop_info_Item("1711", "green bus",
//				"about 8min 1sec"));
//		arrayItems.add(new BusStop_info_Item("7211", "green bus",
//				"about 4min 1sec"));
//		arrayItems.add(new BusStop_info_Item("171", "blue bus", "about 10sec"));
//
//		BusStop_info_Adapter adapter = new BusStop_info_Adapter(
//				getApplicationContext(), R.layout.list_item_busstop_info,
//				arrayItems);
//
//		final ListView list = (ListView) findViewById(R.id.listview);
//		list.setAdapter(adapter);
//
//		list.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View v, int position,
//					long id) {
//				// TODO Auto-generated method stub
//				TextView busNumber = (TextView) v.findViewById(R.id.busNumber);
//				TextView busType = (TextView) v.findViewById(R.id.busType);
//				TextView busLeftTime = (TextView) v
//						.findViewById(R.id.busLeftTime);
//
//				String bus_number = busNumber.getText().toString();
//				String bus_type = busType.getText().toString();
//				String bus_leftTime = busLeftTime.getText().toString();
//
//				// Toast.makeText(v.getContext(),
//				// bus_number + ", " + bus_type + ", " + bus_leftTime,
//				// Toast.LENGTH_LONG).show();
//
//				LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
//						.getSystemService(LAYOUT_INFLATER_SERVICE);
//				View popupView = layoutInflater.inflate(
//						R.layout.list_item_busstop_info_popup, null);
//				final PopupWindow popupWindow = new PopupWindow(popupView,
//						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//
//				ImageView popupImg = (ImageView) popupView
//						.findViewById(R.id.popupImg);
//				popupImg.setImageResource(R.drawable.ic_launcher);
//
//				Button btnDismiss = (Button) popupView
//						.findViewById(R.id.dismiss);
//				btnDismiss.setOnClickListener(new Button.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						popupWindow.dismiss();
//					}
//				});
//
//				popupWindow.showAtLocation(list, Gravity.CENTER, 0, 0);
//
//				// Intent intent = new Intent(v.getContext(),
//				// BusStop_info.class);
//				//
//				// intent.putExtra("stop_name", stop_name);
//				// intent.putExtra("stop_number", stop_number);
//				//
//				// startActivity(intent);
//
//			}
//
//		});
//		// =================================== list view ==
		// this.setData();
	}

	public void setData() {
		// //IntentCallee.java
		// Intent intent = getIntent();
		//
		// String name = intent.getExtras().get("stop_name").toString();
		// String age = intent.getExtras().get("stop_number").toString();
		//
		// TextView nameText = (TextView) findViewById(R.id.Name);
		// nameText.setText(name);
		//
		// TextView ageText = (TextView) findViewById(R.id.Age);
		// ageText.setText(age);
	}
}