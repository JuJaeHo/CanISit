package info.my.tabsswipe.callee.adapter;

public class BusStop_info_Item {
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

	// Rest values
	private String busRouteId;
	private String busType1, busType2;
	private String firstTm;
	private String gpsX, gpsY;
	private String isArrive1, isArrive2;
	private String isLast1, isLast2;
	private String lastTm;
	private String nextBus;
	private String plainNo1, plainNo2;
	private String repTm1, repTm2; // ONLY repTm1 OR repTm1,repTm2
	private String routeType;
	private String sectOrd1, sectOrd2;
	private String stId;
	private String staOrd;
	private String stationNm1, stationNm2;
	private String stationTp;
	private String term;
	private String traSpd1, traSpd2;
	private String vehId1, vehId2;

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

	/*
	 * Rest values' GETTER AND SETTER
	 */

	public void setBusRouteId(String str) {
		this.busRouteId = str;
	}

	public String getBusRouteId() {
		return this.busRouteId;
	}

	public void setBusType1(String str) {
		this.busType1 = str;
	}

	public String getBusType1() {
		return this.busType1;
	}

	public void setBusType2(String str) {
		this.busType2 = str;
	}

	public String getBusType2() {
		return this.busType2;
	}

	public void setFirstTm(String str) {
		this.firstTm = str;
	}

	public String getFirstTm() {
		return this.firstTm;
	}

	public void setGpsX(String str) {
		this.gpsX = str;
	}

	public String getGpsX() {
		return this.gpsX;
	}

	public void setGpsY(String str) {
		this.gpsY = str;
	}

	public String getGpsY() {
		return this.gpsY;
	}

	public void setIsArrive1(String str) {
		this.isArrive1 = str;
	}

	public String getIsArrive1() {
		return this.isArrive1;
	}

	public void setIsArrive2(String str) {
		this.isArrive2 = str;
	}

	public String getIsArrive2() {
		return this.isArrive2;
	}

	public void setIsLast1(String str) {
		this.isLast1 = str;
	}

	public String getIsLast1() {
		return this.isLast1;
	}

	public void setIsLast2(String str) {
		this.isLast2 = str;
	}

	public String getIsLast2() {
		return this.isLast2;
	}

	public void setLastTm(String str) {
		this.lastTm = str;
	}

	public String getLastTm() {
		return this.lastTm;
	}

	public void setNextBus(String str) {
		this.nextBus = str;
	}

	public String getNextBus() {
		return this.nextBus;
	}

	public void setPlainNo1(String str) {
		this.plainNo1 = str;
	}

	public String getPlainNo1() {
		return this.plainNo1;
	}

	public void setPlainNo2(String str) {
		this.plainNo2 = str;
	}

	public String getPlainNo2() {
		return this.plainNo2;
	}

	public void setRepTm1(String str) {
		this.repTm1 = str;
	}

	public String getRepTm1() {
		return this.repTm1;
	}

	public void setRepTm2(String str) {
		this.repTm2 = str;
	}

	public String getRepTm2() {
		return this.repTm2;
	}

	public void setRouteType(String str) {
		this.routeType = str;
	}

	public String getRouteType() {
		return this.routeType;
	}

	public void setSectOrd1(String str) {
		this.sectOrd1 = str;
	}

	public String getSectOrd1() {
		return this.sectOrd1;
	}

	public void setSectOrd2(String str) {
		this.sectOrd2 = str;
	}

	public String getSectOrd2() {
		return this.sectOrd2;
	}

	public void setStId(String str) {
		this.stId = str;
	}

	public String getStId() {
		return this.stId;
	}

	public void setStaOrd(String str) {
		this.staOrd = str;
	}

	public String getStaOrd() {
		return this.staOrd;
	}

	public void setStationNm1(String str) {
		this.stationNm1 = str;
	}

	public String getStationNm1() {
		return this.stationNm1;
	}

	public void setStationNm2(String str) {
		this.stationNm2 = str;
	}

	public String getStationNm2() {
		return this.stationNm2;
	}

	public void setStationTp(String str) {
		this.stationTp = str;
	}

	public String getStationTp() {
		return this.stationTp;
	}

	public void setTerm(String str) {
		this.term = str;
	}

	public String getTerm() {
		return this.term;
	}

	public void setTraSpd1(String str) {
		this.traSpd1 = str;
	}

	public String getTraSpd1() {
		return this.traSpd1;
	}

	public void setTraSpd2(String str) {
		this.traSpd2 = str;
	}

	public String getTraSpd2() {
		return this.traSpd2;
	}

	public void setVehId1(String str) {
		this.vehId1 = str;
	}

	public String getVehId1() {
		return this.vehId1;
	}

	public void setVehId2(String str) {
		this.vehId2 = str;
	}

	public String getVehId2() {
		return this.vehId2;
	}
}
