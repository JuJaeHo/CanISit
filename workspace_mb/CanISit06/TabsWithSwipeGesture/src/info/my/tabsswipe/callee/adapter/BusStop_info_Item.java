package info.my.tabsswipe.callee.adapter;

public class BusStop_info_Item {
	private String stNm; // bus stop name
	private String arsId; // bus stop uid
	private String rtNm;// bus number
	private String traTime1; // leftTime1
	private String traTime2; // leftTime2

	public void setBusNumber(String str) {
		rtNm = str;
	}

	public String getBusNumber() {
		return rtNm;
	}

	public void setBusStopName(String str) {
		stNm = str;
	}

	public String getBusStopName() {
		return stNm;
	}

	public void setBusStopUId(String str) {
		arsId = str;
	}

	public String getBusStopUId() {
		return arsId;
	}

	public void setLeftTime1(String str) {
		traTime1 = str;
	}

	public String getLeftTime1() {
		String hour = "";
		String min = "";
		String sec = "";
		int temp;

		int t = Integer.parseInt(traTime1);

		if (t / 3600 > 0) {
			temp = t / 3600;
			hour = Integer.toString(temp);
			t = t - temp * 3600;

			temp = t % 60;
			min = Integer.toString(temp);
			t = t - temp * 60;

			temp = t;
			sec = Integer.toString(temp);

			return hour + "hour " + min + "min " + sec + "sec";
		} else if (t / 60 > 0) {
			temp = t / 60;
			min = Integer.toString(temp);
			t = t - temp * 60;

			temp = t;
			sec = Integer.toString(temp);

			return min + "min " + sec + "sec";
		} else {
			temp = t;
			sec = Integer.toString(temp);

			return sec + "sec";
		}
	}

	public void setLeftTime2(String str) {
		traTime2 = str;
	}

	public String getLeftTime2() {
		String hour = "";
		String min = "";
		String sec = "";
		int temp;

		int t = Integer.parseInt(traTime2);

		if (t / 3600 > 0) {
			temp = t / 3600;
			hour = Integer.toString(temp);
			t = t - temp * 3600;

			temp = t % 60;
			min = Integer.toString(temp);
			t = t - temp * 60;

			temp = t;
			sec = Integer.toString(temp);

			return hour + "hour " + min + "min " + sec + "sec";
		} else if (t / 60 > 0) {
			temp = t / 60;
			min = Integer.toString(temp);
			t = t - temp * 60;

			temp = t;
			sec = Integer.toString(temp);

			return min + "min " + sec + "sec";
		} else {
			temp = t;
			sec = Integer.toString(temp);

			return sec + "sec";
		}
	}
}
