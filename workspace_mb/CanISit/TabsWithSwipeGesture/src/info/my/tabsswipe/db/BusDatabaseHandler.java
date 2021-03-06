package info.my.tabsswipe.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BusDatabaseHandler extends SQLiteOpenHelper {
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "myListManagers_db";

	// Contacts table name
	private static final String TABLE_BUSINFORMS = "busInforms";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_DIRNUM = "dirNum";
	private static final String KEY_INFO = "info";
	private static final String KEY_ARSID = "arsId";
	private static final String KEY_RTNM = "rtNm";
	private static final String KEY_STNM = "stNm";

	public BusDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_BUSINFORMS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_DIRNUM + " TEXT,"
				+ KEY_INFO + " TEXT," + KEY_ARSID + " TEXT," + KEY_RTNM
				+ " TEXT," + KEY_STNM + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUSINFORMS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	public // Adding new contact
	void addContact(BusInfo_db contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_DIRNUM, contact.getDirNum()); // Contact Name
		values.put(KEY_INFO, contact.getInfo()); // Contact Name
		values.put(KEY_ARSID, contact.getArsId()); // Contact Phone
		values.put(KEY_RTNM, contact.getRtNm()); // Contact Phone
		values.put(KEY_STNM, contact.getStNm()); // Contact Phone

		// Inserting Row
		db.insert(TABLE_BUSINFORMS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	BusInfo_db getContact(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_BUSINFORMS, new String[] { KEY_ID,
				KEY_INFO, KEY_ARSID, KEY_RTNM, KEY_STNM }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		BusInfo_db contact = new BusInfo_db(Integer.parseInt(cursor.getString(0)),
				Integer.parseInt(cursor.getString(1)), cursor.getString(2),
				cursor.getString(3), cursor.getString(4), cursor.getString(5));
		// return contact
		return contact;
	}

	// Getting All Contacts
	public List<BusInfo_db> getAllContacts() {
		List<BusInfo_db> contactList = new ArrayList<BusInfo_db>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_BUSINFORMS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				BusInfo_db contact = new BusInfo_db();
				contact.setID(Integer.parseInt(cursor.getString(0)));
				contact.setDirNum(Integer.parseInt(cursor.getString(1)));
				contact.setInfo(cursor.getString(2));
				contact.setArsId(cursor.getString(3));
				contact.setRtNm(cursor.getString(4));
				contact.setStNm(cursor.getString(5));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}

	// Updating single contact
	public int updateContact(BusInfo_db contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_DIRNUM, contact.getDirNum());
		values.put(KEY_INFO, contact.getInfo());
		values.put(KEY_ARSID, contact.getArsId());
		values.put(KEY_RTNM, contact.getRtNm());
		values.put(KEY_STNM, contact.getStNm());

		// updating row
		return db.update(TABLE_BUSINFORMS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
	}

	// Deleting single contact
	public void deleteContact(BusInfo_db contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_BUSINFORMS, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
		db.close();
	}

	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_BUSINFORMS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
