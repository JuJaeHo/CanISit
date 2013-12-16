package info.my.tabsswipe.db;

public class DirInfo_db {

	// private variables
	int _id;
	String _name;

	// Empty constructor
	public DirInfo_db() {

	}

	// constructor
	public DirInfo_db(int id, String name) {
		this._id = id;
		this._name = name;

	}

	// constructor
	public DirInfo_db(String name) {
		this._name = name;

	}

	public int getID() {
		return this._id;
	}

	public void setID(int id) {
		this._id = id;
	}

	public String getName() {
		return this._name;
	}

	public void setName(String Name) {
		this._name = Name;
	}

}
