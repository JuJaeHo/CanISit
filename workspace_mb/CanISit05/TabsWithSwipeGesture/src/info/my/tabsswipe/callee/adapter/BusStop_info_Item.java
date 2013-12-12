package info.my.tabsswipe.callee.adapter;

public class BusStop_info_Item {
	private String busNumber;
	private String busType;
	private String busLeftTime;

	public BusStop_info_Item(String busNumber, String busType,
			String busLeftTime) {
		this.busNumber = busNumber;
		this.busType = busType;
		this.busLeftTime = busLeftTime;
	}

	public String getBusNumberText() {
		return busNumber;
	}

	public void setBusNumberText(String txt) {
		this.busNumber = txt;
	}

	public String getBusTypeText() {
		return busType;
	}

	public void setBusTypeText(String txt) {
		this.busType = txt;

	}

	public String getBusLeftTimeText() {
		return busLeftTime;
	}

	public void setBusLeftTimeText(String txt) {
		this.busLeftTime = txt;
	}

}
