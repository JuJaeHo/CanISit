package com.example.sqlitetestforbus;

public class BusInfo {

	// private variables
	int _id;
	String _info;
	String _arsId;
	String _rtNm;
	String _stNm;

	// Empty constructor
	public BusInfo() {

	}
	// constructor
	public BusInfo(int id, String rtNm, String stNm) {
		this._id = id;
		this._rtNm = rtNm;
		this._stNm = stNm;
	}

	// constructor
	public BusInfo(String rtNm, String stNm) {
		this._rtNm = rtNm;
		this._stNm = stNm;
	}

//	// constructor
//	public BusInfo(int id, String info, String arsId, String rtNm, String stNm) {
//		this._id = id;
//		this._info = info;
//		this._arsId = arsId;
//		this._rtNm = rtNm;
//		this._stNm = stNm;
//
//		this._info = _info + " " + rtNm + "-" + stNm;
//	}
//
//	// constructor
//	public BusInfo(String info, String arsId, String rtNm, String stNm) {
//		this._info = info;
//		this._arsId = arsId;
//		this._rtNm = rtNm;
//		this._stNm = stNm;
//
//		this._info = _info + " " + rtNm + "-" + stNm;
//	}

	public int getID() {
		return this._id;
	}

	public void setID(int id) {
		this._id = id;
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
