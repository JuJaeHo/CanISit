package com.example.sqlitetestforbus;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ListView lv;
	ArrayAdapter<String> adapter;
	EditText inputSearch;
	// ArrayList<HashMap<String,String>> productList;
	EditText inputAdd;
	Button addBtn;
	Button delBtn;
	DatabaseHandler db;
	List<BusInfo> contacts;
	String[] informs;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		db = new DatabaseHandler(this);

		/**
		 * CRUD Operations
		 * */
		// Inserting Contacts
		Log.d("Insert: ", "Inserting ..");
		// db.addContact(new Contact("ABC", "9100000000"));

		// Reading all contacts
		Log.d("Reading: ", "Reading all contacts..");
		contacts = db.getAllContacts();

		for (BusInfo cn : contacts) {
			String log = "info: " + cn.getInfo() + " ,arsId: " + cn.getArsId()
					+ " ,rtNm: " + cn.getRtNm() + " ,stNm: " + cn.getStNm();
			// Writing Contacts to log
			Log.d("Name: ", log);
		}

		informs = new String[contacts.size()];
		for (int i = 0; i < contacts.size(); i++) {
			informs[i] = contacts.get(i).getStNm();
		}

		lv = (ListView) findViewById(R.id.list_view);

		adapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.bus_info, informs);
		lv.setAdapter(adapter);

		// inputSearch = (EditText) findViewById(R.id.inputSearch);

		/**
		 * Add List to store
		 */
		inputAdd = (EditText) findViewById(R.id.inputAdd);
		addBtn = (Button) findViewById(R.id.add_btn);
		delBtn = (Button) findViewById(R.id.del_btn);

		addBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String fullInputStr = inputAdd.getText().toString();

				Log.d("beforeAddContact", "HERE");
				// db.addContact(new BusInfo("[학교갈꺼임]", "08110", "153",
				// "국민대학교앞"));
				db.addContact(new BusInfo("153", "국민대학교앞"));
				Log.d("afterAddContact", "HERE");
				contacts = db.getAllContacts();

				informs = new String[contacts.size()];
				for (int i = 0; i < contacts.size(); i++) {
					informs[i] = contacts.get(i).getStNm();
				}

				adapter = new ArrayAdapter<String>(getApplicationContext(),
						R.layout.list_item, R.id.bus_info, informs);
				lv.setAdapter(adapter);
			}
		});

		delBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (BusInfo ct : contacts)
					db.deleteContact(ct);

				contacts = db.getAllContacts();

				informs = new String[contacts.size()];
				for (int i = 0; i < contacts.size(); i++) {
					informs[i] = contacts.get(i).getStNm();
				}

				adapter = new ArrayAdapter<String>(getApplicationContext(),
						R.layout.list_item, R.id.bus_info, informs);
				lv.setAdapter(adapter);
			}
		});

	}
}