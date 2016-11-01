package mcity.com.mcity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sloop.fonts.FontsManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostPG extends Activity implements AdapterView.OnItemSelectedListener{

	ImageView submit;
	String URL = Data_Service.URL_API + "postforroom";
	String uid, token, location, landmark, address, rent, description, str_residential, str_gender,room,spin_val,str_pho_enable;
	EditText location_et, lanmark_et, address_et, rentamount_et, description_et;
	String[] city = {"Sylvan County", "Aqualily", "Iris Court", "Nova"};
	private RadioGroup radioResidentialGroup, radioGenderGroup;
	private RadioButton radioresidentalButton, radiogGnderButton;
	CheckBox phone_enable;
	Spinner spinner;
	Typeface tf;
	List<String> categories;
	ProgressBar progressBar;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_pg);

		//select_city = (AutoCompleteTextView) findViewById(R.id.location_et);
		//location_et = (EditText) findViewById(R.id.location_et);
		lanmark_et = (EditText) findViewById(R.id.landmark_et);
		address_et = (EditText) findViewById(R.id.Address_et);
		//rentamount_et = (EditText) findViewById(R.id.rent_et);
		description_et = (EditText) findViewById(R.id.description_et);
		submit = (ImageView) findViewById(R.id.submit);
		rentamount_et = (EditText) findViewById(R.id.rent_et);
		spinner=(Spinner)findViewById(R.id.spinner);
		radioResidentialGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		radioGenderGroup= (RadioGroup) findViewById(R.id.radioGroup2) ;
		progressBar=(ProgressBar)findViewById(R.id.progressBar);
		phone_enable=(CheckBox)findViewById(R.id.phone_enable);
		FontsManager.initFormAssets(this, "mont.ttf");
		FontsManager.changeFonts(this);
		tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "mont.ttf");

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		token = sharedPreferences.getString("token", "");
		uid = sharedPreferences.getString("id", "");
		progressBar.setVisibility(View.GONE);
		spinner.setOnItemSelectedListener(this);

		// Spinner Drop down elements
		categories = new ArrayList<String>();
		categories.add("Select Location");
		categories.add("Aqualily");
		categories.add("Iris Court");
		categories.add("Nova");
		categories.add("Sylvan County");
		categories.add("Others");





		// Creating adapter for spinner
		ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);

		// Drop down layout style - list view with radio button
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		spinner.setAdapter(dataAdapter);
		setSpinner1();



		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				spin_val = spinner.getItemAtPosition(position).toString();
				Log.e("tag", "123" + spin_val);

				setLocation();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});



		submit.setOnClickListener(new View.OnClickListener()

		{
			@Override
			public void onClick (View v){


				int selectedId1 = radioResidentialGroup.getCheckedRadioButtonId();
				int selectedId2 = radioGenderGroup.getCheckedRadioButtonId();
				radioresidentalButton = (RadioButton) findViewById(selectedId1);
				radiogGnderButton = (RadioButton) findViewById(selectedId2);

				Log.e("tag", "working");
				location = spinner.getSelectedItem().toString();
				landmark = lanmark_et.getText().toString();
				address = address_et.getText().toString();
				str_residential = radioresidentalButton.getText().toString();
				Log.e("tag", "1" + str_residential);
				str_gender = radiogGnderButton.getText().toString();
				Log.e("tag", "2" + str_gender);
				rent = rentamount_et.getText().toString();
				description = description_et.getText().toString();
				Log.e("tag", "gcm1" + token);
				Log.e("tag", "gcm2" + uid);

				if(str_gender.equals("I Have a PG/Hostel")) {
					room = "pg";
				}
				else
				{
					room = "room";
				}



				if (phone_enable.isChecked()) {
					str_pho_enable = "enabled";
				} else {
					str_pho_enable = "Hidden Contact";
				}
				Log.e("tag","oops"+str_pho_enable);



			if (Util.Operations.isOnline(PostPG.this)) {
				if (!location.isEmpty() && !landmark.isEmpty() && !address.isEmpty() && !str_residential.isEmpty() && !str_gender.isEmpty() && !rent.isEmpty() && !description.isEmpty()) {

					new PostHouseAsync().execute();


				} else {
					Toast.makeText(getApplicationContext(), "Invalid Fields..", Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "No Internet Connectivity", Toast.LENGTH_LONG).show();
			}




			}
		});




	}

	private void setLocation() {


		if (spin_val.equals("Aqualily")) {
			lanmark_et.setText("Opposite BMW Factory");
			address_et.setText("Mahindra City");
		} else {
			if (spin_val.equals("Iris Court")) {
				lanmark_et.setText("Close to Paranur Station");
				address_et.setText("Mahindra City");
			} else {
				if (spin_val.equals("Nova")) {
					lanmark_et.setText("Close to Paranur Station");
					address_et.setText("Mahindra City");

				} else if (spin_val.equals("Sylvan County")){

					lanmark_et.setText("Close to Canopy");
					address_et.setText("Mahindra City");
				}
				else
				{
					lanmark_et.setText("");
					address_et.setText("");
				}
			}
		}


	}






	private void setSpinner1() {

		final CustomAdapter arrayAdapter = new CustomAdapter(this, android.R.layout.simple_spinner_item, categories) {
			@Override
			public boolean isEnabled(int position) {
				if (position == 0) {
					return false;
				} else {
					return true;
				}
			}


			@Override
			public View getDropDownView(int position, View convertView,
										ViewGroup parent) {
				View view = super.getDropDownView(position, convertView, parent);
				TextView tv = (TextView) view;

				tv.setTypeface(tf);

				if (position == 0) {
					tv.setTextColor(Color.RED);
				} else {
					tv.setTextColor(Color.BLACK);
				}
				return view;
			}
		};
		spinner.setAdapter(arrayAdapter);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	class PostHouseAsync extends AsyncTask<String, Void, String> {


		public PostHouseAsync() {
			String json = "", jsonStr = "";


		}

		protected void onPreExecute() {
			submit.setVisibility(View.GONE);
			progressBar.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		protected String doInBackground(String... params) {

			String json = "", jsonStr = "";
			String id = "";
			try {
				Log.e("tag","id"+uid);
				Log.e("tag","token"+token);
				//location,landmark,address,roomtype,monthlyrent,gender,description
				JSONObject jsonObject = new JSONObject();
				jsonObject.accumulate("location", location);
				jsonObject.accumulate("landmark", landmark);
				jsonObject.accumulate("address", address);
				jsonObject.accumulate("roomtype",room );
				jsonObject.accumulate("monthlyrent", rent);
				jsonObject.accumulate("gender", str_gender);
				jsonObject.accumulate("description", description);
				jsonObject.accumulate("phone", str_pho_enable);


				Log.e("tag","1"+location);

				json = jsonObject.toString();

				return jsonStr = HttpUtils.makeRequest1(URL, json, uid, token);
			} catch (Exception e) {
				Log.d("InputStream", e.getLocalizedMessage());
			}
			return null;

		}

		@Override
		protected void onPostExecute(String jsonStr) {
			Log.e("tag", "<-----result---->" + jsonStr);
			submit.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			super.onPostExecute(jsonStr);
			try {
				JSONObject jo = new JSONObject(jsonStr);
				String status = jo.getString("status");
				String msg = jo.getString("message");
				Log.d("tag", "<-----Status----->" + status);
				if (status.equals("true")) {
					Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
					lanmark_et.setText("");
					address_et.setText("");
					description_et.setText("");
					rentamount_et.setText("");



				} else {
					Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onBackPressed()
	{
		Intent i = new Intent(PostPG.this,Dashboard.class);
		startActivity(i);
		finish();
	}
}