package info.my.tabsswipe;

import info.androidhive.tabsswipe.R;
import info.my.tabsswipe.adapter.BusStopAdapter;
import info.my.tabsswipe.adapter.BusStopItem;
import info.my.tabsswipe.callee.BusStop_info;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BusStopFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_busstop, container,
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
				R.layout.list_item_busstops, arrayItems);

		ListView list = (ListView) rootView.findViewById(R.id.listview);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				TextView txt1 = (TextView) v.findViewById(R.id.itemtext1);
				TextView txt2 = (TextView) v.findViewById(R.id.itemtext2);

				String stop_name = txt1.getText().toString();
				String stop_number = txt2.getText().toString();
				
//				Toast.makeText(v.getContext(), stop_name + ", " + stop_number,
//						Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent(v.getContext(), BusStop_info.class);

				intent.putExtra("stop_name", stop_name);
				intent.putExtra("stop_number", stop_number);

				startActivity(intent);
			}

		});
		// =================================== list view ==

		return rootView;
	}
}
