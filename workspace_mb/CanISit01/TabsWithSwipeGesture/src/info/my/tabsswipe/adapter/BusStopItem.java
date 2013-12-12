package info.my.tabsswipe.adapter;

public class BusStopItem {
	private String text;
	private String text2;

	public BusStopItem(String txt, String txt2) {
		text = txt;
		text2 = txt2;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String txt) {
		text = txt;
	}
	
	public String getText2() {
		return text2;
	}
	
	public void setText2(String txt) {
		text2 = txt;
	}
	
}
