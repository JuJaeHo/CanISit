package info.my.tabsswipe.callee;

import info.androidhive.tabsswipe.R;
import info.my.tabsswipe.callee.adapter.BusStop_info_Adapter;
import info.my.tabsswipe.callee.adapter.BusStop_info_Item;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class BusStop_info extends Activity {
	BackgroundTask task;
	ArrayList<BusStop_info_Item> rtNmList = new ArrayList<BusStop_info_Item>();
	BusStop_info_Item rtNmInfo;

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

		// // == list view ===================================
		// ArrayList<BusStop_info_Item> arrayItems = new
		// ArrayList<BusStop_info_Item>();
		// arrayItems.add(new BusStop_info_Item("153", "blue bus",
		// "about 3min 20sec"));
		// arrayItems.add(new BusStop_info_Item("1711", "green bus",
		// "about 8min 1sec"));
		// arrayItems.add(new BusStop_info_Item("7211", "green bus",
		// "about 4min 1sec"));
		// arrayItems.add(new BusStop_info_Item("171", "blue bus",
		// "about 10sec"));
		//
		// BusStop_info_Adapter adapter = new BusStop_info_Adapter(
		// getApplicationContext(), R.layout.list_item_busstop_info,
		// arrayItems);
		//
		// final ListView list = (ListView) findViewById(R.id.listview);
		// list.setAdapter(adapter);
		//
		// list.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View v, int position,
		// long id) {
		// // TODO Auto-generated method stub
		// TextView busNumber = (TextView) v.findViewById(R.id.busNumber);
		// TextView busType = (TextView) v.findViewById(R.id.busType);
		// TextView busLeftTime = (TextView) v
		// .findViewById(R.id.busLeftTime);
		//
		// String bus_number = busNumber.getText().toString();
		// String bus_type = busType.getText().toString();
		// String bus_leftTime = busLeftTime.getText().toString();
		//
		// // Toast.makeText(v.getContext(),
		// // bus_number + ", " + bus_type + ", " + bus_leftTime,
		// // Toast.LENGTH_LONG).show();
		//
		// LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
		// .getSystemService(LAYOUT_INFLATER_SERVICE);
		// View popupView = layoutInflater.inflate(
		// R.layout.list_item_busstop_info_popup, null);
		// final PopupWindow popupWindow = new PopupWindow(popupView,
		// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//
		// ImageView popupImg = (ImageView) popupView
		// .findViewById(R.id.popupImg);
		// popupImg.setImageResource(R.drawable.ic_launcher);
		//
		// Button btnDismiss = (Button) popupView
		// .findViewById(R.id.dismiss);
		// btnDismiss.setOnClickListener(new Button.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// popupWindow.dismiss();
		// }
		// });
		//
		// popupWindow.showAtLocation(list, Gravity.CENTER, 0, 0);
		//
		// // Intent intent = new Intent(v.getContext(),
		// // BusStop_info.class);
		// //
		// // intent.putExtra("stop_name", stop_name);
		// // intent.putExtra("stop_number", stop_number);
		// //
		// // startActivity(intent);
		//
		// }
		//
		// });
		// // =================================== list view ==
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

	/**
	 * Task
	 * 
	 * @author DOITANDROID 저자님
	 */
	class BackgroundTask extends AsyncTask<String, Integer, Integer> {
		protected void onPreExecute() {
			rtNmList.clear();
		}

		protected Integer doInBackground(String... params) {
			try {
				// Log.d("CONNECT", params[0]);
				InputStream is = new URL(params[0]).openStream();
				XmlPullParserFactory factory = XmlPullParserFactory
						.newInstance();
				XmlPullParser parser = factory.newPullParser();

				parser.setInput(is, null);
				int eventType = parser.getEventType();

				// Log.d("STREAM", "SUCCESS");

				int flag = 0;
				while (eventType != XmlPullParser.END_DOCUMENT) {
					if (flag == 1) {
						flag = 0;
						break;
					}
					switch (eventType) {
					case XmlPullParser.START_TAG:
						String startTag = parser.getName();

						Log.d("WHATIS", startTag);

						// 검색한 결과가 나오지 않는다면, flag 이용해서 case, while 탈출
						if (startTag.equals("headerCd")) {
							String temp = parser.nextText();
							if (temp.equals("4")) {
								Log.d("isParsing", temp + "No Result.");
								flag = 1;
								break;
							}
						}

						if (startTag.equals("itemList")) {
							rtNmInfo = new BusStop_info_Item();
							Log.d("START",
									"==========Start of itemList===========");
						}

						if (rtNmInfo != null) {
							if (startTag.equals("arsId")) {
								rtNmInfo.setArsId(parser.nextText());
								Log.d("UniqueId", rtNmInfo.getArsId());
							}
							if (startTag.equals("busRouteId")) {
								rtNmInfo.setBusRouteId(parser.nextText());
								Log.d("busRouteId", rtNmInfo.getBusRouteId());
							}
							if (startTag.equals("busType1")) {
								rtNmInfo.setBusType1(parser.nextText());
								Log.d("busType1", rtNmInfo.getBusType1());
							}
							if (startTag.equals("busType2")) {
								rtNmInfo.setBusType2(parser.nextText());
								Log.d("busType1", rtNmInfo.getBusType2());
							}
							if (startTag.equals("stId")) {
								rtNmInfo.setStId(parser.nextText());
								Log.d("stId", rtNmInfo.getStId());
							}
							if (startTag.equals("stNm")) {
								rtNmInfo.setStNm(parser.nextText());
								Log.d("stNm", rtNmInfo.getStNm());
							}
							if (startTag.equals("rtNm")) {
								rtNmInfo.setRtNm(parser.nextText());
								Log.d("stNm", rtNmInfo.getRtNm());
							}
							if (startTag.equals("traTime1")) {
								rtNmInfo.setTraTime1(parser.nextText());
								Log.d("traTime1", rtNmInfo.getTraTime1());
							}
							if (startTag.equals("traTime2")) {
								rtNmInfo.setTraTime2(parser.nextText());
								Log.d("traTime2", rtNmInfo.getTraTime2());
							}

							
						}
						break;
					case XmlPullParser.END_TAG:
						String endTag = parser.getName();
						if (endTag.equals("itemList")) {
							rtNmList.add(rtNmInfo);
							Log.d("END", "=============");
						}
					} // switch

					eventType = parser.next();
				} // while
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		protected void onProgressUpdate(Integer... values) {
		}

		/**
		 * 검색된 정류소가 많다면, 다 로딩 된 후에 띄어짐 하나씩으로 고치셈
		 */
		protected void onPostExecute(Integer result) {
			BusStop_info_Adapter adapter = new BusStop_info_Adapter(
					getApplicationContext(), R.layout.list_item_busstop_info,
					rtNmList);
			ListView listViewbusstop = (ListView) findViewById(R.id.listViewbusinfo);
			listViewbusstop.setAdapter(adapter);
		}

		protected void onCancelled() {
		}
	}
}