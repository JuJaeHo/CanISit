package info.my.tabsswipe;

import info.androidhive.tabsswipe.R;
import info.my.tabsswipe.adapter.BusStopFragment_Adapter;
import info.my.tabsswipe.adapter.BusStopFragment_Item;
import info.my.tabsswipe.callee.BusStop_info;

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

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * 1) BusStopFragment_Item, BusStopFragment_Adapter, list_item_busstopfragment
 * 2) Callee: BusStop_info
 * 
 * 131213 Added / (1)hide keyboard by pressing return key(enter key). /
 * (2)"at least two characters" issue solved(back button->can't parsing) And
 * occurred problem(needless parsing).
 * 
 * Have to / (1)hide keyboard when scrolling. Similarly, when moving the
 * layout(fragment). (2)SQLite issues
 * 
 * @author 누군가의
 * 
 */
public class BusStopFragment extends Fragment {
	EditText srchText;
	BackgroundTask task;
	ArrayList<BusStopFragment_Item> stNmList = new ArrayList<BusStopFragment_Item>();
	BusStopFragment_Item stNmInfo;
	View rootView;
	InputMethodManager imm;

	// getStationByName, stSrch=국민
	// getStationByUid, arsId=08110
	// static final private String GETSTATIONBYUID =
	
	String GETSTATIONBYNAME = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByName?ServiceKey=";
	String SERVICEKEY = "";
	String QUERY = "&stSrch=";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater
				.inflate(R.layout.fragment_busstop, container, false);

		// //////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////
		/**
		 * LIMITED NUMBER OF SERVICE REQUESTS EXCEEDS ERROR for temporary test
		 * set.
		 */
		// Button tempBtn = (Button) rootView.findViewById(R.id.tempBtn);
		// tempBtn.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// String arsId = "08110";
		// String stNm = "국민대학교앞";
		// String stId = "";
		// String tmX = "";
		// String tmY = "";
		//
		// Intent intent = new Intent(v.getContext(), BusStop_info.class);
		// intent.putExtra("arsId", arsId);
		// intent.putExtra("stId", stId);
		// intent.putExtra("stNm", stNm);
		// intent.putExtra("tmX", tmX);
		// intent.putExtra("tmY", tmY);
		// startActivity(intent);
		// }
		// });
		// //////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////

		srchText = (EditText) rootView.findViewById(R.id.srchText);

		srchText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					// hide virtual keyboard
					imm = (InputMethodManager) rootView.getContext()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);

					return true;
				}

				return false;
			}
		});
		srchText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

				// if (count > 1) { // need more than two length of string FOR
				// SRCH
				Log.d("on", s.toString());
				Log.d("start", Integer.toString(start));
				Log.d("before", Integer.toString(before));
				Log.d("count", Integer.toString(count));

				String queryKey = s.toString();

				String requestURL;
				try {
					requestURL = GETSTATIONBYNAME
							+ URLEncoder.encode(SERVICEKEY, "UTF-8") + QUERY
							+ URLEncoder.encode(queryKey, "UTF-8");

					task = new BackgroundTask();
					task.execute(requestURL);

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// }
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
		// ArrayList<BusStopItem> arrayItems = new ArrayList<BusStopItem>();
		// arrayItems.add(new BusStopItem("Kookmin Univ.", "15-303"));
		// BusStopAdapter adapter = new BusStopAdapter(rootView.getContext(),
		// R.layout.list_item_busstops, arrayItems);
		// ListView list = (ListView) rootView.findViewById(R.id.listView);
		// list.setAdapter(adapter);
		// list.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View v, int position,
		// long id) {
		// // TODO Auto-generated method stub
		// TextView txt1 = (TextView) v.findViewById(R.id.itemtext1);
		// TextView txt2 = (TextView) v.findViewById(R.id.itemtext2);
		// String stop_name = txt1.getText().toString();
		// String stop_number = txt2.getText().toString();
		// Intent intent = new Intent(v.getContext(), BusStop_info.class);
		// intent.putExtra("stop_name", stop_name);
		// intent.putExtra("stop_number", stop_number);
		// startActivity(intent);
		// }
		// });

		return rootView;
	}

	/**
	 * Task
	 * 
	 * @author DOITANDROID 저자님
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

						// Log.d("WHATIS", startTag);

						// 검색한 결과가 나오지 않는다면, flag 이용해서 case, while 탈출
						// 4: No Result, 3: At least two characters for
						// retrieval
						if (startTag.equals("headerCd")) {
							String temp = parser.nextText();
							if (temp.equals("4")) {
								Log.d("isParsing", "[ERROR(" + temp
										+ ")] No Result.");
								flag = 1;
								break;
							} else if (temp.equals("3")) {
								Log.d("isParsing", "[ERROR(" + temp
										+ ")] At least two characters");
								flag = 1;
								break;
							} else {
								Log.d("isParsing", "[NUMBER(" + temp
										+ ")] message");
							}
						}

						if (startTag.equals("itemList")) {
							stNmInfo = new BusStopFragment_Item();
							Log.d("START",
									"==========Start of itemList===========");
						}

						if (stNmInfo != null) {
							if (startTag.equals("arsId")) {
								stNmInfo.setArsId(parser.nextText());
								Log.d("UniqueId", stNmInfo.getArsId());
							}
							if (startTag.equals("stId")) {
								stNmInfo.setStId(parser.nextText());
								Log.d("StopId", stNmInfo.getStId());
							}
							if (startTag.equals("stNm")) {
								stNmInfo.setStNm(parser.nextText());
								Log.d("StopName", stNmInfo.getStNm());
							}
							if (startTag.equals("tmX")) {
								stNmInfo.setTmX(parser.nextText());
								Log.d("X", stNmInfo.getTmX());
							}
							if (startTag.equals("tmY")) {
								stNmInfo.setTmY(parser.nextText());
								Log.d("Y", stNmInfo.getTmY());
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

		/**
		 * 검색된 정류소가 많다면, 다 로딩 된 후에 띄어짐 하나씩으로 고치셈
		 */
		protected void onPostExecute(Integer result) {
			BusStopFragment_Adapter adapter = new BusStopFragment_Adapter(
					rootView.getContext(), R.layout.list_item_busstopfragment,
					stNmList);
			ListView listViewbusstop = (ListView) rootView
					.findViewById(R.id.listViewbusstop);
			listViewbusstop.setAdapter(adapter);

			listViewbusstop.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View v,
						int position, long id) {
					// TODO Auto-generated method stub
					// Toast.makeText(rootView.getContext(), "SUCCESS",
					// Toast.LENGTH_LONG).show();
					String arsId = stNmList.get(position).getArsId();
					String stId = stNmList.get(position).getStId();
					String stNm = stNmList.get(position).getStNm();
					String tmX = stNmList.get(position).getTmX();
					String tmY = stNmList.get(position).getTmY();

					Intent intent = new Intent(v.getContext(),
							BusStop_info.class);
					intent.putExtra("arsId", arsId);
					intent.putExtra("stId", stId);
					intent.putExtra("stNm", stNm);
					intent.putExtra("tmX", tmX);
					intent.putExtra("tmY", tmY);
					startActivity(intent);
				}
			});

		}

		protected void onCancelled() {
		}
	}
}
