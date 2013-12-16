package info.my.tabsswipe.callee;

import info.androidhive.tabsswipe.R;
import info.my.tabsswipe.callee.adapter.MyList_info_Adapter;
import info.my.tabsswipe.callee.adapter.MyList_info_Item;
import info.my.tabsswipe.db.BusInfo_db;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MyList_info extends Activity {
	BackgroundTask task;
	List<BusInfo_db> tempCon;
	BusInfo_db busItem;

	List<ParseItems> piList; // same tempCon & busItem
	ParseItems pi; // same tempCon & busItem

	// getStationByName, stSrch=국민
	// getStationByUid, arsId=08110
	String GETSTATIONBYUID = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByUid?ServiceKey=";
	String SERVICEKEY = "";
	String QUERY = "&arsId=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busstop_info);

		/*
		 * Getting intent
		 */
		Intent intent = getIntent();
		final String title = intent.getExtras().get("title").toString();
		setTitle(title);

		final String total = intent.getExtras().get("total").toString();
		int sizeOf = Integer.parseInt(total);

		// Log.d("intent", (sizeOf/4) + ": Number of items");

		tempCon = new ArrayList<BusInfo_db>();
		tempCon.clear();
		piList = new ArrayList<MyList_info.ParseItems>();
		piList.clear();
		for (int i = 0; i < sizeOf; i += 4) {
			busItem = new BusInfo_db();

			String info = intent.getExtras().get(Integer.toString(i))
					.toString();
			String arsId = intent.getExtras().get(Integer.toString(i + 1))
					.toString();
			String rtNm = intent.getExtras().get(Integer.toString(i + 2))
					.toString();
			String stNm = intent.getExtras().get(Integer.toString(i + 3))
					.toString();

			busItem.setInfo(info);
			busItem.setArsId(arsId);
			busItem.setRtNm(rtNm);
			busItem.setStNm(stNm);
			pi = new ParseItems(arsId, rtNm);

			tempCon.add(busItem);
			piList.add(pi);
		}

		for (BusInfo_db items : tempCon) {
			String aaa = "";
			aaa += "info: " + items.getInfo() + ", arsId: " + items.getArsId()
					+ ", rtNm: " + items.getRtNm() + ", stNm:"
					+ items.getStNm() + "\n";
			Log.d("in callee", aaa);
		}

		/*
		 * workings et
		 */
		String temp = "";
		int diffNum = 0;
		for (int i = 0; i < piList.size(); i++) {
			if (!temp.equals(piList.get(i).getArsId_p())) {
				diffNum++;
			}
			temp = piList.get(i).getArsId_p();
		}
		// Log.d("Diffrent Number", Integer.toString(diffNum));
		temp = "";
		int kk = 0;
		int[] _arsId = new int[diffNum];
		for (int i = 0; i < piList.size(); i++) {
			if (!temp.equals(piList.get(i).getArsId_p())) {
				_arsId[kk++] = i;
			}
			temp = piList.get(i).getArsId_p();
		}
		// Log.d("where", _arsId[0] + " " + _arsId[1] + " " + _arsId[2]);
//		List<ParseItems> ppi = new ArrayList<MyList_info.ParseItems>();
//		ppi = piList.subList(_arsId[0], _arsId[1]);
//		Log.d("ss", ppi.get(0).getArsId_p() + ppi.get(0).getRtNm());
//		Log.d("ss", ppi.get(1).getArsId_p() + ppi.get(1).getRtNm());
//		ppi = piList.subList(_arsId[1], _arsId[2]);
//		Log.d("ss", ppi.get(0).getArsId_p() + ppi.get(0).getRtNm());
//		ppi = piList.subList(_arsId[2], _arsId[3]);
//		Log.d("ss", ppi.get(0).getArsId_p() + ppi.get(0).getRtNm());
//		Log.d("ss", ppi.get(1).getArsId_p() + ppi.get(1).getRtNm());

		/*
		 * Request URL and XML Parsing
		 */
		String queryKey = "08110";
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

	}

	/**
	 * Items for XML Parsing
	 * 
	 * @author JJH
	 * 
	 */
	class ParseItems {
		private String arsId;
		private String rtNm;

		ParseItems(String arsId, String rtNm) {
			this.arsId = arsId;
			this.rtNm = rtNm;
		}

		public String getArsId_p() {
			return this.arsId;
		}

		public String getRtNm() {
			return this.rtNm;
		}

	}

	/**
	 * Task
	 * 
	 * @author DOITANDROID 저자님
	 */
	class BackgroundTask extends AsyncTask<String, Integer, Integer> {

		// XML parsing Variables
		ArrayList<MyList_info_Item> rtNmList = new ArrayList<MyList_info_Item>();
		MyList_info_Item rtNmInfo;

		private ArrayList<String> dirItems = new ArrayList<String>();

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
							rtNmInfo = new MyList_info_Item();
							Log.d("START",
									"==========Start of itemList===========");
						}

						if (rtNmInfo != null) {
							if (startTag.equals("arsId")) {
								rtNmInfo.setArsId(parser.nextText());
								Log.d("UniqueId", rtNmInfo.getArsId());
							}
							if (startTag.equals("rtNm")) {
								rtNmInfo.setRtNm(parser.nextText());
								Log.d("rtNm", rtNmInfo.getRtNm());
							}
							if (startTag.equals("stNm")) {
								rtNmInfo.setStNm(parser.nextText());
								Log.d("stNm", rtNmInfo.getStNm());
							}
							if (startTag.equals("stationNm1")) {
								rtNmInfo.setStationNm1(parser.nextText());
								Log.d("stationNm1", rtNmInfo.getStationNm1());
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
		 * Problem: If find many BusStop, loading work takes too many times. So,
		 * while typing the text, XML parsing is on process.
		 */
		protected void onPostExecute(Integer result) {
			/*
			 * Set Adapter
			 */
			MyList_info_Adapter adapter = new MyList_info_Adapter(
					getApplicationContext(), R.layout.list_item_busstop_info,
					rtNmList);
			ListView listViewbusinfo = (ListView) findViewById(R.id.listViewbusinfo);
			listViewbusinfo.setAdapter(adapter);

			/*
			 * Load AlertDialog
			 */
			listViewbusinfo.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					Toast.makeText(v.getContext(), "추가작업은 버스 검색을 이용하세요.",
							Toast.LENGTH_LONG).show();
					// TODO Auto-generated method stub
					// String rtNm = rtNmList.get(position).getRtNm();
					// String arsId = rtNmList.get(position).getArsId();
					// String stNm = rtNmList.get(position).getStNm();
					// showAlertDialog(arsId, rtNm, stNm, position);
				}
			});
		}

		protected void onCancelled() {

		}

	} // End of BackgroudTask Class
} // End of BusStop_info Class