package info.my.tabsswipe;

import info.androidhive.tabsswipe.R;
import info.my.tabsswipe.adapter.MyListFragment_Adapter;
import info.my.tabsswipe.adapter.MyListFragment_Item;
import info.my.tabsswipe.callee.MyList_info;
import info.my.tabsswipe.db.BusDatabaseHandler;
import info.my.tabsswipe.db.BusInfo_db;
import info.my.tabsswipe.db.DirDatabaseHandler;
import info.my.tabsswipe.db.DirInfo_db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MyListFragment extends Fragment {

	ArrayList<MyListFragment_Item> dirList = new ArrayList<MyListFragment_Item>();
	private MyListFragment_Item dirItem;
	View rootView;

	// SQLite Handle Variables for Directory
	DirDatabaseHandler db;
	List<DirInfo_db> contacts;

	// SQLite Handle Variables for bus
	BusDatabaseHandler bsdb;
	List<BusInfo_db> bscontacts;
	List<BusInfo_db> tempCon;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_mylist, container, false);

		db = new DirDatabaseHandler(rootView.getContext());

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

		dirList.clear();
		for (int i = 0; i < contacts.size(); i++) {
			dirItem = new MyListFragment_Item();
			dirItem.setDirId(contacts.get(i).getID());
			dirItem.setDirName(contacts.get(i).getName());

			dirList.add(dirItem);
		}

		MyListFragment_Adapter adapter = new MyListFragment_Adapter(
				rootView.getContext(), R.layout.list_item_mylistfragment,
				dirList);

		ListView listViewmylist = (ListView) rootView
				.findViewById(R.id.listDir_inMylist);
		listViewmylist.setAdapter(adapter);

		listViewmylist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final int sqlPos = position;

				bsdb = new BusDatabaseHandler(rootView.getContext());

				/**
				 * CRUD Operations
				 * */
				// Inserting Contacts
				Log.d("Insert: ", "Inserting ..");
				// db.addContact(new Contact("ABC", "9100000000"));

				// Reading all contacts
				Log.d("Reading: ", "Reading all contacts..");
				bscontacts = bsdb.getAllContacts();

				Log.d("sqlReadinMyList",
						"=====START==================================================");

				// for (BusInfo_db cn : bscontacts) {
				// String log = "dirNum: " + cn.getDirNum() + ", info: "
				// + cn.getInfo() + ", arsId: " + cn.getArsId()
				// + ", rtNm: " + cn.getRtNm() + ", stNm: "
				// + cn.getStNm();
				// // Writing Contacts to log
				// Log.d("beforeSortingInMyList: ", log);
				// }

				tempCon = new ArrayList<BusInfo_db>();
				tempCon.clear();
				for (int i = 0; i < bscontacts.size(); i++)
					if (bscontacts.get(i).getDirNum() == sqlPos)
						tempCon.add(bscontacts.get(i));

				Collections.sort(tempCon, sortByArsId);

				// Log.d("sqlReadinMyList",
				// "===========================================================");

				for (BusInfo_db cn : tempCon) {
					String log = "dirNum: " + cn.getDirNum() + ", info: "
							+ cn.getInfo() + ", arsId: " + cn.getArsId()
							+ ", rtNm: " + cn.getRtNm() + ", stNm: "
							+ cn.getStNm();
					// Writing Contacts to log
					Log.d("afterSortingInMyList: ", log);
				}

				Log.d("sqlReadinMyList",
						"===================================================END=====");

				Intent intent = new Intent(rootView.getContext(),
						MyList_info.class);

				int j = 0;
				String title = dirList.get(sqlPos).getDirName();
				intent.putExtra("title", title);
				intent.putExtra("total", tempCon.size() * 4);
				for (int i = 0; i < tempCon.size() * 4; i += 4) {
					intent.putExtra(Integer.toString(i), tempCon.get(j)
							.getInfo());
					intent.putExtra(Integer.toString(i + 1), tempCon.get(j)
							.getArsId());
					intent.putExtra(Integer.toString(i + 2), tempCon.get(j)
							.getRtNm());
					intent.putExtra(Integer.toString(i + 3), tempCon.get(j)
							.getStNm());
					j++;
				}

				startActivity(intent);
				// Toast.makeText(rootView.getContext(),
				// "I want to see my list at (" + sqlPos + ").",
				// Toast.LENGTH_LONG).show();
			}
		});

		return rootView;
	}

	/**
	 * Custom Comparator by arsId
	 */
	Comparator<BusInfo_db> sortByArsId = new Comparator<BusInfo_db>() {

		@Override
		public int compare(BusInfo_db lhs, BusInfo_db rhs) {
			// TODO Auto-generated method stub
			return (lhs.getArsId()).compareTo(rhs.getArsId());
		}
	};
}
