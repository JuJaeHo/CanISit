package info.my.tabsswipe.callee;

import info.androidhive.tabsswipe.R;
import info.my.tabsswipe.callee.adapter.BusStop_info_Adapter;
import info.my.tabsswipe.callee.adapter.BusStop_info_Item;
import info.my.tabsswipe.db.BusDatabaseHandler;
import info.my.tabsswipe.db.BusInfo_db;
import info.my.tabsswipe.db.DirDatabaseHandler;
import info.my.tabsswipe.db.DirInfo_db;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

public class BusStop_info extends Activity {
	BackgroundTask task;

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
		final String arsId = intent.getExtras().get("arsId").toString();
		final String stId = intent.getExtras().get("stId").toString();
		final String stNm = intent.getExtras().get("stNm").toString();
		final String tmX = intent.getExtras().get("tmX").toString();
		final String tmY = intent.getExtras().get("tmY").toString();

		/*
		 * Setting the Title
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

	}

	/**
	 * Task
	 * 
	 * @author DOITANDROID 저자님
	 */
	class BackgroundTask extends AsyncTask<String, Integer, Integer> {

		// alertDialog Variables
		Dialog custom;
		ListView listDir;
		ArrayAdapter<String> dialogAdapter;
		View dialogView;
		CheckedTextView chckText;

		// XML parsing Variables
		ArrayList<BusStop_info_Item> rtNmList = new ArrayList<BusStop_info_Item>();
		BusStop_info_Item rtNmInfo;

		// CheckedTextView Variables
		ListView dirView;
		Button getResult;
		ChckAdapter myArrayAdapter;

		// SQLite Handle Variables for Directory
		DirDatabaseHandler db;
		List<DirInfo_db> contacts;
		String dirName;

		// SQLite Handle Variables for Bus informations
		BusDatabaseHandler bsdb;
		List<BusInfo_db> bscontacts;
		String bsinform;

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
		 * Problem: If find many BusStop, loading work takes too many times. So,
		 * while typing the text, XML parsing is on process.
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
						int position, long arg3) {
					// TODO Auto-generated method stub
					String rtNm = rtNmList.get(position).getRtNm();
					String arsId = rtNmList.get(position).getArsId();
					String stNm = rtNmList.get(position).getStNm();
					showAlertDialog(arsId, rtNm, stNm, position);
				}
			});
		}

		private void showAlertDialog(String arsId, String rtNm, String stNm,
				int pos) {
			custom = new Dialog(BusStop_info.this); // 위에 변수 선언함
			custom.setContentView(R.layout.dialog_activity_busstop_info);

			custom.setTitle("(" + rtNm + ")저장할 폴더를 선택하세요.");
			final String _rtNm = rtNm;
			final String _arsId = arsId;
			final String _stNm = stNm;

			// Setting the directory list and Adapter
			initDirectoryList();

			/*
			 * Setting the button functions
			 */
			// Save Button
			Button saveBtn = (Button) custom.findViewById(R.id.saveBtn);
			saveBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String valResult = "[";
					int[] posResult;

					/*
					 * Getting Result of Checked Item's Position and Name.
					 */
					// getCheckedItemPositions
					List<Integer> chckedPos = myArrayAdapter
							.getCheckedItemPositions();
					posResult = new int[chckedPos.size()];
					for (int i = 0; i < chckedPos.size(); i++) {
						posResult[i] = chckedPos.get(i);
					}

					// getCheckedItems
					List<String> resultList = myArrayAdapter.getCheckedItems();
					for (int i = 0; i < resultList.size(); i++) {
						if (resultList.size() - 1 == i)
							valResult += String.valueOf(resultList.get(i)
									+ "]에 \"" + _rtNm + "\" 버스를 저장했습니다.");
						else
							valResult += String.valueOf(resultList.get(i))
									+ "/";
					}

					/**
					 * working for bus informations database
					 */
					bsdb = new BusDatabaseHandler(BusStop_info.this);

					// bsdb.addContact(new BusInfo_db("ㅎ", 0, "12249", "7211",
					// "불광역.불광1동주민센터"));
					// bsdb.addContact(new BusInfo_db("ㅎ", 0, "08110", "153",
					// "국민대학교앞"));
					// bsdb.addContact(new BusInfo_db("ㅎ", 1, "08110", "7211",
					// "국민대학교앞"));
					// bsdb.addContact(new BusInfo_db("ㅎ", 1, "13114", "7728",
					// "연세대학교앞"));

					/*
					 * Save the bus informations.
					 */
					for (int i : posResult)
						bsdb.addContact(new BusInfo_db("ㅋ", i, _arsId, _rtNm,
								_stNm));

					/**
					 * CRUD Operations
					 * */
					// Inserting Contacts
					Log.d("Insert: ", "Inserting ..");
					// db.addContact(new Contact("ABC", "9100000000"));

					// Reading all contacts
					Log.d("Reading: ", "Reading all contacts..");
					bscontacts = bsdb.getAllContacts();

					for (BusInfo_db cn : bscontacts) {
						String log = "dirNum: " + cn.getDirNum() + ", info: "
								+ cn.getInfo() + ", arsId: " + cn.getArsId()
								+ ", rtNm: " + cn.getRtNm() + ", stNm: "
								+ cn.getStNm();
						// Writing Contacts to log
						Log.d("Name: ", log);
					}
					
//					 for (BusInfo_db cn : bscontacts) {
//					 bsdb.deleteContact(cn);
//					 }

					myArrayAdapter.getCheckedItemPositions().toString();
					Toast.makeText(getApplicationContext(), valResult,
							Toast.LENGTH_LONG).show();
					
					custom.dismiss();
				}
			});

			// Create Button
			Button createBtn = (Button) custom.findViewById(R.id.createBtn);
			createBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// ////////////////////////////////////////////////////////////////
					// ////////////////////////////////////////////////////////////////
					/**
					 * ALIVE!
					 */
					 db.addContact(new DirInfo_db("새 폴더"));
					 db.addContact(new DirInfo_db("집에서 일산가는 버스들"));
					 db.addContact(new DirInfo_db("신촌행"));
					 db.addContact(new DirInfo_db("일산에서 집으로"));
					 db.addContact(new DirInfo_db("학교엣 집으로"));
					 db.addContact(new DirInfo_db("집에서 학교"));
					 db.addContact(new DirInfo_db("부산여행"));
					 db.addContact(new DirInfo_db("친구집가는버스"));
					 db.addContact(new DirInfo_db("김포버스"));
					 db.addContact(new DirInfo_db("인천친구"));
					 db.addContact(new DirInfo_db("놀러"));
					// ////////////////////////////////////////////////////////////////
					// ////////////////////////////////////////////////////////////////
					contacts = db.getAllContacts();

					dirItems.clear();
					for (int i = 0; i < contacts.size(); i++) {
						dirName = contacts.get(i).getName();
						dirItems.add(dirName);
					}

					myArrayAdapter = new ChckAdapter(BusStop_info.this,
							R.layout.list_item_dialog, android.R.id.text1,
							dirItems);

					dirView.setAdapter(myArrayAdapter);

					Toast.makeText(getApplicationContext(), "Created",
							Toast.LENGTH_LONG).show();
				}
			});

			// Cancel Button
			Button cancelBtn = (Button) custom.findViewById(R.id.cancelBtn);
			cancelBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// /////////////////////////////////////////////////
					// /////////////////////////////////////////////////
					/*
					 * Delete All
					 */
					 for (DirInfo_db ct : contacts)
					 db.deleteContact(ct);
					
					 contacts = db.getAllContacts();
					
					 dirItems.clear();
					 for (int i = 0; i < contacts.size(); i++) {
					 dirName = contacts.get(i).getName();
					 dirItems.add(dirName);
					 }
					
					 myArrayAdapter = new ChckAdapter(BusStop_info.this,
					 R.layout.list_item_dialog, android.R.id.text1,
					 dirItems);
					
					 dirView.setAdapter(myArrayAdapter);
					// ///////////////////////////////////////////////////
					// ///////////////////////////////////////////////////
					Toast.makeText(getApplicationContext(), "Canceled",
							Toast.LENGTH_LONG).show();
					custom.dismiss();
				}
			});

			custom.show();
		}

		/*
		 * Setting the listView of directory list in alertDialog
		 */
		private void initDirectoryList() {

			db = new DirDatabaseHandler(BusStop_info.this);

			/**
			 * CRUD Operations
			 * */
			// Inserting Contacts
			Log.d("Insert: ", "Inserting ..");
			// db.addContact(new Contact("ABC", "9100000000"));

			// Reading all contacts
			Log.d("Reading: ", "Reading all contacts..");
			contacts = db.getAllContacts();

			for (DirInfo_db cn : contacts) {
				String log = "id: " + cn.getID() + ", info: " + cn.getName();
				// Writing Contacts to log
				Log.d("Name: ", log);
			}

			dirItems.clear();
			for (int i = 0; i < contacts.size(); i++) {
				dirName = contacts.get(i).getName();
				dirItems.add(dirName);
			}

			/*
			 * Setting the adapter
			 */
			dirView = (ListView) custom.findViewById(R.id.listDirectory);

			myArrayAdapter = new ChckAdapter(BusStop_info.this,
					R.layout.list_item_dialog, android.R.id.text1, dirItems);

			dirView.setAdapter(myArrayAdapter);

			dirView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					myArrayAdapter.toggleChecked(position);
				}
			});

		}

		protected void onCancelled() {

		}

		/**
		 * Adapter Class for CheckedTextView
		 * 
		 * @author Android-er
		 * 
		 */
		private class ChckAdapter extends ArrayAdapter<String> {

			private HashMap<Integer, Boolean> myChecked = new HashMap<Integer, Boolean>();

			public ChckAdapter(Context context, int resource,
					int textViewResourceId, List<String> objects) {
				super(context, resource, textViewResourceId, objects);

				for (int i = 0; i < objects.size(); i++) {
					myChecked.put(i, false);
				}
			}

			public void toggleChecked(int position) {
				if (myChecked.get(position)) {
					myChecked.put(position, false);
				} else {
					myChecked.put(position, true);
				}

				notifyDataSetChanged();
			}

			public List<Integer> getCheckedItemPositions() {
				List<Integer> checkedItemPositions = new ArrayList<Integer>();

				for (int i = 0; i < myChecked.size(); i++) {
					if (myChecked.get(i)) {
						(checkedItemPositions).add(i);
					}
				}

				return checkedItemPositions;
			}

			public List<String> getCheckedItems() {
				List<String> checkedItems = new ArrayList<String>();

				for (int i = 0; i < myChecked.size(); i++) {
					if (myChecked.get(i)) {
						(checkedItems).add(dirItems.get(i));
					}
				}

				return checkedItems;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View row = convertView;

				if (row == null) {
					LayoutInflater inflater = getLayoutInflater();
					row = inflater.inflate(R.layout.list_item_dialog, parent,
							false);
				}

				CheckedTextView checkedTextView = (CheckedTextView) row
						.findViewById(R.id.directory_name);
				checkedTextView.setText(dirItems.get(position));

				Boolean checked = myChecked.get(position);
				if (checked != null) {
					checkedTextView.setChecked(checked);
				}

				return row;
			}

		} // End of ChckAdapter Class
	} // End of BackgroudTask Class
} // End of BusStop_info Class