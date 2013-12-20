package info.my.tabsswipe.callee.adapter;

public class MyList_info_Item {
	/*
	 * arsId busRouteId busType1 busType2 firstTm gpsX gpsY isArrive1 isArrive2
	 * isLast1 isLast2 lastTm nextBus plainNo1 plainNo2 repTm1 routeType rtNm
	 * sectOrd1 sectOrd2 stId stNm staOrd stationNm1 stationNm2 stationTp term
	 * traSpd1 traSpd2 traTime1 traTime2 vehId1 vehId2
	 */
	// Important values
	private String stNm; // bus stop's name
	private String arsId; // bus stop's unique id
	private String rtNm;// bus number
	private String traTime1, traTime2; // leftTime
	private String stationNm1;

	/*
	 * Important values' GETTER AND SETTER
	 */
	public void setRtNm(String str) {
		this.rtNm = str;
	}

	public String getRtNm() {
		return this.rtNm;
	}

	public void setStNm(String str) {
		this.stNm = str;
	}

	public String getStNm() {
		return this.stNm;
	}

	public void setArsId(String str) {
		this.arsId = str;
	}

	public String getArsId() {
		return this.arsId;
	}

	public void setTraTime1(String str) {
		traTime1 = str;
	}

	public String getTraTime1() {
		String hour = "";
		String min = "";
		String sec = "";
		int temp;

		int t = Integer.parseInt(traTime1);

		if (t / 3600 > 0) {
			temp = t / 3600;
			hour = Integer.toString(temp);
			t = t - temp * 3600;

			temp = t / 60;
			min = Integer.toString(temp);
			t = t - temp * 60;

			temp = t;
			sec = Integer.toString(temp);

			return hour + "시간 " + min + "분 " + sec + "초";
		} else if (t / 60 > 0) {
			temp = t / 60;
			min = Integer.toString(temp);
			t = t - temp * 60;

			temp = t;
			sec = Integer.toString(temp);

			return min + "분 " + sec + "초";
		} else {
			temp = t;
			sec = Integer.toString(temp);

			return sec + "초";
		}
	}

	public void setTraTime2(String str) {
		traTime2 = str;
	}

	public String getTraTime2() {
		String hour = "";
		String min = "";
		String sec = "";
		int temp;

		int t = Integer.parseInt(traTime2);

		if (t / 3600 > 0) {
			temp = t / 3600;
			hour = Integer.toString(temp);
			t = t - temp * 3600;

			temp = t / 60;
			min = Integer.toString(temp);
			t = t - temp * 60;

			temp = t;
			sec = Integer.toString(temp);

			return hour + "시간 " + min + "분 " + sec + "초";
		} else if (t / 60 > 0) {
			temp = t / 60;
			min = Integer.toString(temp);
			t = t - temp * 60;

			temp = t;
			sec = Integer.toString(temp);

			return min + "분 " + sec + "초";
		} else {
			temp = t;
			sec = Integer.toString(temp);

			return sec + "초";
		}
	}

	/*
	 * Rest values' GETTER AND SETTER
	 */

	public void setStationNm1(String str) {
		this.stationNm1 = str;
	}

	public String getStationNm1() {
		return this.stationNm1;
	}

}
