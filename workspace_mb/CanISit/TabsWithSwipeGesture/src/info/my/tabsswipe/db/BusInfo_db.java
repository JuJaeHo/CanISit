package info.my.tabsswipe.db;

public class BusInfo_db {

	// private variables
	private int _id;
	private int _dirNum;
	private String _info;
	private String _arsId;
	private String _rtNm;
	private String _stNm;
	private String _fullContent;

	// Empty constructor
	public BusInfo_db() {

	}

	// constructor
	public BusInfo_db(int id, int dirNum, String info, String arsId,
			String rtNm, String stNm) {
		this._id = id;
		this._dirNum = dirNum;
		this._info = info;
		this._arsId = arsId;
		this._rtNm = rtNm;
		this._stNm = stNm;
	}

	// constructor
	public BusInfo_db(String info, int dirNum, String arsId, String rtNm,
			String stNm) {
		this._dirNum = dirNum;
		this._info = info;
		this._arsId = arsId;
		this._rtNm = rtNm;
		this._stNm = stNm;
	}

	public String getFullContent() {
		return _dirNum + " " + _info + " " + _arsId + " " + _rtNm + "-" + _stNm;
	}

	public int getID() {
		return this._id;
	}

	public void setID(int id) {
		this._id = id;
	}

	public int getDirNum() {
		return this._dirNum;
	}

	public void setDirNum(int dirNum) {
		this._dirNum = dirNum;
	}

	public String getInfo() {
		return this._info;
	}

	public void setInfo(String info) {
		this._info = info;
	}

	public String getArsId() {
		return this._arsId;
	}

	public void setArsId(String arsId) {
		this._arsId = arsId;
	}

	public String getRtNm() {
		return this._rtNm;
	}

	public void setRtNm(String rtNm) {
		this._rtNm = rtNm;
	}

	public String getStNm() {
		return this._stNm;
	}

	public void setStNm(String stNm) {
		this._stNm = stNm;
	}

}
