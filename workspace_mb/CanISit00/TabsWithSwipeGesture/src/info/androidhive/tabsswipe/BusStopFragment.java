package info.androidhive.tabsswipe;

import info.my.adapter.BusStopAdapter;
import info.my.adapter.BusStopItem;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class BusStopFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_bus_stop, container,
				false);
		EditText edit = (EditText) rootView.findViewById(R.id.edittext);

		// == list view ===================================
		ArrayList<BusStopItem> arrayItems = new ArrayList<BusStopItem>();
		// arrayItems.add(new MyItem("153", "about 3min 20sec"));
		// arrayItems.add(new MyItem("1711", "about 8min 1sec"));
		// arrayItems.add(new MyItem("7211", "about 4min 1sec"));
		// arrayItems.add(new MyItem("171", "about 10sec"));
		arrayItems.add(new BusStopItem("Kookmin Univ.", "15-303"));
		arrayItems.add(new BusStopItem("Ilsan station", "22-521"));
		arrayItems.add(new BusStopItem("Shinchon Ave.", "1-123"));
		arrayItems.add(new BusStopItem("harlem St.", "7-347"));
		arrayItems.add(new BusStopItem("Spring Lake", "27-321"));
		arrayItems.add(new BusStopItem("Havana", "93-528"));
		arrayItems.add(new BusStopItem("Rainbow Dr.", "34-509"));
		arrayItems.add(new BusStopItem("Holiday Center", "76-168"));
		arrayItems.add(new BusStopItem("Reese Dr.", "21-445"));
		arrayItems.add(new BusStopItem("Fenley Ave.", "17-905"));
		arrayItems.add(new BusStopItem("Augusta St.", "8-112"));
		arrayItems.add(new BusStopItem("Tazewell Government CU", "2-632"));
		arrayItems.add(new BusStopItem("Florence Ave.", "4-876"));
		arrayItems.add(new BusStopItem("Capri St.", "9-554"));

		BusStopAdapter adapter = new BusStopAdapter(rootView.getContext(),
				R.layout.busstoplayout, arrayItems);

		ListView list = (ListView) rootView.findViewById(R.id.listview);
		list.setAdapter(adapter);
		// =================================== list view ==

		return rootView;
	}
}
