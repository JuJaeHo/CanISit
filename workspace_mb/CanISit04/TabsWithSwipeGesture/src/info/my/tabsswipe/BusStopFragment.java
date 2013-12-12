package info.my.tabsswipe;

import info.androidhive.tabsswipe.R;
import info.my.tabsswipe.adapter.StationByNameInfo;
import info.my.tabsswipe.adapter.StationByNameInfo_Adapter;

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

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

public class BusStopFragment extends Fragment {

	EditText srchText;
	BackgroundTask task;
	ArrayList<StationByNameInfo> stNmList = new ArrayList<StationByNameInfo>();
	StationByNameInfo stNmInfo;
	View rootView;

	// getStationByName, stSrch=국민
	// getStationByUid, arsId=08110
	// static final private String GETSTATIONBYUID =
	// "http://ws.bus.go.kr/api/rest/stationinfo/getStationByUid?ServiceKey=&arsId=";
	String GETSTATIONBYNAME = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByName?ServiceKey=";
	String SERVICEKEY = "";
	String QUERY = "&stSrch=";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater
				.inflate(R.layout.fragment_busstop, container, false);

		srchText = (EditText) rootView.findViewById(R.id.srchText);
		srchText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

				if (count > 1) { // need more than two length of string FOR
					// SRCH
					Log.d("s", s.toString());
					Log.d("start", Integer.toString(start));
					Log.d("before", Integer.toString(before));
					Log.d("count", Integer.toString(count));

					String queryKey = s.toString();

					String requestURL;
					try {
						requestURL = GETSTATIONBYNAME
								+ URLEncoder.encode(SERVICEKEY, "UTF-8")
								+ QUERY + URLEncoder.encode(queryKey, "UTF-8");

						task = new BackgroundTask();
						task.execute(requestURL);

					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		// final EditText edit = (EditText)
		// rootView.findViewById(R.id.srchText);
		//
		// // == list view ===================================
		// ArrayList<BusStopItem> arrayItems = new ArrayList<BusStopItem>();
		// arrayItems.add(new BusStopItem("Kookmin Univ.", "15-303"));
		// arrayItems.add(new BusStopItem("Ilsan station", "22-521"));
		// arrayItems.add(new BusStopItem("Shinchon Ave.", "1-123"));
		// arrayItems.add(new BusStopItem("harlem St.", "7-347"));
		// arrayItems.add(new BusStopItem("Spring Lake", "27-321"));
		// arrayItems.add(new BusStopItem("Havana", "93-528"));
		// arrayItems.add(new BusStopItem("Rainbow Dr.", "34-509"));
		// arrayItems.add(new BusStopItem("Holiday Center", "76-168"));
		// arrayItems.add(new BusStopItem("Reese Dr.", "21-445"));
		// arrayItems.add(new BusStopItem("Fenley Ave.", "17-905"));
		// arrayItems.add(new BusStopItem("Augusta St.", "8-112"));
		// arrayItems.add(new BusStopItem("Tazewell Government CU", "2-632"));
		// arrayItems.add(new BusStopItem("Florence Ave.", "4-876"));
		// arrayItems.add(new BusStopItem("Capri St.", "9-554"));
		//
		// BusStopAdapter adapter = new BusStopAdapter(rootView.getContext(),
		// R.layout.list_item_busstops, arrayItems);
		//
		// ListView list = (ListView) rootView.findViewById(R.id.listView);
		// list.setAdapter(adapter);
		//
		// list.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View v, int position,
		// long id) {
		// // TODO Auto-generated method stub
		// TextView txt1 = (TextView) v.findViewById(R.id.itemtext1);
		// TextView txt2 = (TextView) v.findViewById(R.id.itemtext2);
		//
		// String stop_name = txt1.getText().toString();
		// String stop_number = txt2.getText().toString();
		//
		// // Toast.makeText(v.getContext(), stop_name + ", " +
		// // stop_number,
		// // Toast.LENGTH_LONG).show();
		//
		// Intent intent = new Intent(v.getContext(), BusStop_info.class);
		//
		// intent.putExtra("stop_name", stop_name);
		// intent.putExtra("stop_number", stop_number);
		//
		// startActivity(intent);
		// }
		//
		// });
		// // =================================== list view ==

		return rootView;
	}

	/**
	 * ���ο� Task ��ü�� ����
	 */
	class BackgroundTask extends AsyncTask<String, Integer, Integer> {
		protected void onPreExecute() {
			stNmList.clear();
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
								Log.d("파싱여부", temp + "결과가 없습니다.");
								flag = 1;
								break;
							}
						}

						if (startTag.equals("itemList")) {
							stNmInfo = new StationByNameInfo();
							Log.d("START",
									"==========Start of itemList===========");
						}

						if (stNmInfo != null) {
							if (startTag.equals("arsId")) {
								stNmInfo.setArsId(parser.nextText());
								Log.d("정류소고유번호", stNmInfo.getArsId());
							}
							if (startTag.equals("stId")) {
								stNmInfo.setStId(parser.nextText());
								Log.d("정류소ID", stNmInfo.getStId());
							}
							if (startTag.equals("stNm")) {
								stNmInfo.setStNm(parser.nextText());
								Log.d("버스정류소이름", stNmInfo.getStNm());
							}
							if (startTag.equals("tmX")) {
								stNmInfo.setTmX(parser.nextText());
								Log.d("x좌표", stNmInfo.getTmX());
							}
							if (startTag.equals("tmY")) {
								stNmInfo.setTmY(parser.nextText());
								Log.d("y좌표", stNmInfo.getTmY());
							}
						}
						break;
					case XmlPullParser.END_TAG:
						String endTag = parser.getName();
						if (endTag.equals("itemList")) {
							stNmList.add(stNmInfo);
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

		protected void onPostExecute(Integer result) {
			StationByNameInfo_Adapter adapter = new StationByNameInfo_Adapter(
					rootView.getContext(),
					R.layout.list_item_stationbyname_info, stNmList);
			ListView list = (ListView) rootView
					.findViewById(R.id.listViewbusstop);
			list.setAdapter(adapter);
		}

		protected void onCancelled() {
		}
	}
}
