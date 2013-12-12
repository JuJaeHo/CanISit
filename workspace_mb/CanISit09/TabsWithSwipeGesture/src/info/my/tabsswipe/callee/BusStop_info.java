package info.my.tabsswipe.callee;

import info.androidhive.tabsswipe.R;
import info.my.tabsswipe.callee.adapter.BusStop_info_Adapter;
import info.my.tabsswipe.callee.adapter.BusStop_info_Item;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class BusStop_info extends Activity {
	BackgroundTask task;
	ArrayList<BusStop_info_Item> rtNmList = new ArrayList<BusStop_info_Item>();
	BusStop_info_Item rtNmInfo;

	// getStationByName, stSrch=국민
	// getStationByUid, arsId=08110
	// static final private String GETSTATIONBYUID =
	// "http://ws.bus.go.kr/api/rest/stationinfo/getStationByUid?ServiceKey=&arsId=";
	String GETSTATIONBYUID = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByUid?ServiceKey=";
	String SERVICEKEY = "";
	String QUERY = "&arsId=";

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

		/*
		 * Request URL and XML Parsing
		 */
		String queryKey = arsId;
		String requestURL;
		try {
			requestURL = GETSTATIONBYUID
					+ URLEncoder.encode(SERVICEKEY, "UTF-8") + QUERY
					+ URLEncoder.encode(queryKey, "UTF-8");

			task = new BackgroundTask();
			task.execute(requestURL);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// // == list view ===================================
		// ArrayList<BusStop_info_Item> arrayItems = new
		// ArrayList<BusStop_info_Item>();
		// arrayItems.add(new BusStop_info_Item("153", "blue bus",
		// "about 3min 20sec"));
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

	// public void setData() {
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
	// }

	/**
	 * Task
	 * 
	 * @author DOITANDROID 저자님
	 */
	class BackgroundTask extends AsyncTask<String, Integer, Integer> {
		Dialog custom;// for dialog

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

						// Log.d("WHATIS", startTag);

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
								Log.d("busType2", rtNmInfo.getBusType2());
							}
							if (startTag.equals("firstTm")) {
								rtNmInfo.setFirstTm(parser.nextText());
								Log.d("firstTm", rtNmInfo.getFirstTm());
							}
							if (startTag.equals("gpsX")) {
								rtNmInfo.setGpsX(parser.nextText());
								Log.d("gpsX", rtNmInfo.getGpsX());
							}
							if (startTag.equals("gpsY")) {
								rtNmInfo.setGpsY(parser.nextText());
								Log.d("gpsY", rtNmInfo.getGpsY());
							}
							if (startTag.equals("isArrive1")) {
								rtNmInfo.setIsArrive1(parser.nextText());
								Log.d("isArrive1", rtNmInfo.getBusType2());
							}
							if (startTag.equals("isArrive2")) {
								rtNmInfo.setIsArrive2(parser.nextText());
								Log.d("isArrive2", rtNmInfo.getIsArrive2());
							}
							if (startTag.equals("isLast1")) {
								rtNmInfo.setIsLast1(parser.nextText());
								Log.d("isLast1", rtNmInfo.getIsLast1());
							}
							if (startTag.equals("isLast2")) {
								rtNmInfo.setIsLast2(parser.nextText());
								Log.d("isLast2", rtNmInfo.getIsLast2());
							}
							if (startTag.equals("lastTm")) {
								rtNmInfo.setLastTm(parser.nextText());
								Log.d("lastTm", rtNmInfo.getLastTm());
							}
							if (startTag.equals("nextBus")) {
								rtNmInfo.setNextBus(parser.nextText());
								Log.d("nextBus", rtNmInfo.getNextBus());
							}
							if (startTag.equals("plainNo1")) {
								rtNmInfo.setPlainNo1(parser.nextText());
								Log.d("plainNo1", rtNmInfo.getPlainNo1());
							}
							if (startTag.equals("plainNo2")) {
								rtNmInfo.setPlainNo2(parser.nextText());
								Log.d("plainNo2", rtNmInfo.getPlainNo2());
							}
							if (startTag.equals("repTm1")) {
								rtNmInfo.setRepTm1(parser.nextText());
								Log.d("repTm1", rtNmInfo.getRepTm1());
							}
							if (startTag.equals("repTm2")) {
								rtNmInfo.setRepTm2(parser.nextText());
								Log.d("repTm2", rtNmInfo.getRepTm2());
							}
							if (startTag.equals("routeType")) {
								rtNmInfo.setRouteType(parser.nextText());
								Log.d("routeType", rtNmInfo.getRouteType());
							}
							if (startTag.equals("rtNm")) {
								rtNmInfo.setRtNm(parser.nextText());
								Log.d("rtNm", rtNmInfo.getRtNm());
							}
							if (startTag.equals("sectOrd1")) {
								rtNmInfo.setSectOrd1(parser.nextText());
								Log.d("sectOrd1", rtNmInfo.getSectOrd1());
							}
							if (startTag.equals("sectOrd2")) {
								rtNmInfo.setSectOrd2(parser.nextText());
								Log.d("sectOrd2", rtNmInfo.getSectOrd2());
							}
							if (startTag.equals("stId")) {
								rtNmInfo.setStId(parser.nextText());
								Log.d("stId", rtNmInfo.getStId());
							}
							if (startTag.equals("stNm")) {
								rtNmInfo.setStNm(parser.nextText());
								Log.d("stNm", rtNmInfo.getStNm());
							}
							if (startTag.equals("staOrd")) {
								rtNmInfo.setStaOrd(parser.nextText());
								Log.d("staOrd", rtNmInfo.getStaOrd());
							}
							if (startTag.equals("stationNm1")) {
								rtNmInfo.setStationNm1(parser.nextText());
								Log.d("stationNm1", rtNmInfo.getStationNm1());
							}
							if (startTag.equals("stationNm2")) {
								rtNmInfo.setStationNm2(parser.nextText());
								Log.d("stationNm2", rtNmInfo.getStationNm2());
							}
							if (startTag.equals("stationTp")) {
								rtNmInfo.setStationTp(parser.nextText());
								Log.d("stationTp", rtNmInfo.getStationTp());
							}
							if (startTag.equals("term")) {
								rtNmInfo.setTerm(parser.nextText());
								Log.d("term", rtNmInfo.getTerm());
							}
							if (startTag.equals("traSpd1")) {
								rtNmInfo.setTraSpd1(parser.nextText());
								Log.d("traSpd1", rtNmInfo.getTraSpd1());
							}
							if (startTag.equals("traSpd2")) {
								rtNmInfo.setTraSpd2(parser.nextText());
								Log.d("traSpd2", rtNmInfo.getTraSpd2());
							}
							if (startTag.equals("traTime1")) {
								rtNmInfo.setTraTime1(parser.nextText());
								Log.d("traTime1", rtNmInfo.getTraTime1());
							}
							if (startTag.equals("traTime2")) {
								rtNmInfo.setTraTime2(parser.nextText());
								Log.d("traTime2", rtNmInfo.getTraTime2());
							}
							if (startTag.equals("vehId1")) {
								rtNmInfo.setVehId1(parser.nextText());
								Log.d("vehId1", rtNmInfo.getVehId1());
							}
							if (startTag.equals("vehId2")) {
								rtNmInfo.setVehId2(parser.nextText());
								Log.d("vehId2", rtNmInfo.getVehId2());
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
			/*
			 * Set Adapter
			 */
			BusStop_info_Adapter adapter = new BusStop_info_Adapter(
					getApplicationContext(), R.layout.list_item_busstop_info,
					rtNmList);
			ListView listViewbusinfo = (ListView) findViewById(R.id.listViewbusinfo);
			listViewbusinfo.setAdapter(adapter);

			/*
			 * Load AlertDialog
			 */

			listViewbusinfo.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					/*
					 * 1.
					 */
					// AlertDialog dialog = createDialogBox();
					// dialog.show();

					/*
					 * 2.
					 */
					custom = new Dialog(BusStop_info.this); // 위에 변수 선언함
					custom.setContentView(R.layout.dialog_activity_busstop_info);

					custom.setTitle("저장할 폴더를 선택하세요.");
					EditText test1 = (EditText) custom.findViewById(R.id.test1);
					EditText test2 = (EditText) custom.findViewById(R.id.test2);
					Button b1 = (Button) custom.findViewById(R.id.b1);
					b1.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							custom.dismiss();
						}
					});
					Button b2 = (Button) custom.findViewById(R.id.b2);
					b2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							custom.dismiss();
						}
					});

					custom.show();
				}
			});

		}

		protected void onCancelled() {
		}

	}

	// /*
	// * AlertDialog.. 2)방법
	// */
	// private AlertDialog createDialogBox() {
	// AlertDialog.Builder builder = new AlertDialog.Builder(this);
	//
	// builder.setTitle("안내");
	// builder.setMessage("종료하시겠습니까?");
	// builder.setIcon(R.drawable.ic_launcher);
	//
	// // 예 버튼 설정
	// builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int whichButton) {
	// // msg = "예 버튼이 눌렀습니다. " +
	// // Integer.toString(whichButton);
	// // txtMsg.setText(msg);
	// }
	// });
	//
	// // 취소 버튼 설정
	// builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int whichButton) {
	// // msg = "취소 버튼이 눌렸습니다. " +
	// // Integer.toString(whichButton);
	// // txtMsg.setText(msg);
	// }
	// });
	//
	// // 아니오 버튼 설정
	// builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int whichButton) {
	// // msg = "아니오 버튼이 눌렸습니다. " +
	// // Integer.toString(whichButton);
	// // txtMsg.setText(msg);
	// }
	// });
	//
	// // 빌더 객체의 create() 메소드 호출하면 대화상자 객체 생성
	// AlertDialog dialog = builder.create();
	//
	// return dialog;
	// }
}