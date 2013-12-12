package info.my.callee;

import info.androidhive.tabsswipe.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BusStopCallee extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intentcallee);

		Button button = (Button) findViewById(R.id.Close);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		this.setData();
	}

	public void setData() {
		// IntentCallee.java
		Intent intent = getIntent();

		String name = intent.getExtras().get("name").toString();
		String age = intent.getExtras().get("age").toString();

		TextView nameText = (TextView) findViewById(R.id.Name);
		nameText.setText(name);

		TextView ageText = (TextView) findViewById(R.id.Age);
		ageText.setText(age);
	}

}
