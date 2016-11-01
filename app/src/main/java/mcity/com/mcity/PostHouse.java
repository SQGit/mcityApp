package mcity.com.mcity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.sloop.fonts.FontsManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import nl.changer.polypicker.Config;
import nl.changer.polypicker.ImagePickerActivity;
import nl.changer.polypicker.utils.ImageInternalFetcher;

public class PostHouse extends Activity implements AdapterView.OnItemSelectedListener {
    ImageView submit;
    String URL = Data_Service.URL_API + "postforrent";
    String id, token, path, landmark, address, rentAmount, depositAmount, description, img, str_pho_enable;
    String root1,root0, root2, root3, str_location, str_landmark, str_address, str_residential, str_residential1,str_bedroom,str_bedroom1,str_bedroom2, str_renttype, str_furnished_type, str_monthrent, str_deposit, str_description, str_city;
    EditText location_et, lanmark_et, address_et, rentamount_et, depositamount_et, description_et;
    TextView msg;
    private RadioGroup radioResidentialGroup1,radioResidentialGroupcut,radioBedroomothers,radioResidentialGroup2, radioBedroomGroup,radioBedroomGroupbar1,radioBedroomGroupbar2, radiorentGroup, radioFurnishedGroup;
    private RadioButton radioresidentalButton1,radioresidentalButtoncut,radioresidentalButton2, radioBedroomButton,radiobar1,radiobar2, radiorentButton, radioFurnishedButton;
    Button camera;
    String[] city = {"Sylvan County", "Aqualily", "Iris Court", "Nova"};
    //@@@@@@@@@@@@@@@@
    private static final String TAG = PostHouse.class.getSimpleName();
    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    private static final int INTENT_REQUEST_GET_N_IMAGES = 14;
    private ViewGroup mSelectedImagesContainer;
    HashSet<Uri> mMedia = new HashSet<Uri>();
    ArrayList<Uri> image_path = new ArrayList<>();
    String[] imagearray;
    Spinner spinner;
    LinearLayout restrict1, restrict2,restrict3,withoutbar,withbar1,withbar2,progress_linear,button_liner,others;
    String spin_val;
    SharedPreferences sharedpreferences;
    ImageView thumbnail;
    SharedPreferences sharedPreferences;
    private ProgressDialog dialog;
    ArrayList<String> mdatas;
    int selectedId1,selectedId2,selectedId3,selectedId4,selectedId5,selectedId6,selectedId7,other_bed,selectedIdcut,selrent;
    ProgressBar progressBar;
    Typeface tf;
    List<String> categories;
    CheckBox phone_enable;


    public static final String MyPREFERENCES = "MyPrefs";
    //@@@@@@@@@@@@@@@@


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_house);

        lanmark_et = (EditText) findViewById(R.id.landmark_et);
        address_et = (EditText) findViewById(R.id.Address_et);
        rentamount_et = (EditText) findViewById(R.id.rent_et);
        depositamount_et = (EditText) findViewById(R.id.deposit_et);
        description_et = (EditText) findViewById(R.id.description_et);
        submit = (ImageView) findViewById(R.id.post_submit);
        msg = (TextView) findViewById(R.id.msg);
        spinner = (Spinner) findViewById(R.id.spinner);
        restrict1 = (LinearLayout) findViewById(R.id.restrict1);
        restrict2 = (LinearLayout) findViewById(R.id.restrict2);
        restrict3= (LinearLayout) findViewById(R.id.restrict3);
        withoutbar = (LinearLayout) findViewById(R.id.withoutbar);
        withbar1 = (LinearLayout) findViewById(R.id.withbar1);
        withbar2 = (LinearLayout) findViewById(R.id.   withbar2);
        others=(LinearLayout)findViewById(R.id.others);
        radioResidentialGroup1 = (RadioGroup) findViewById(R.id.radioResidentialGroup1);
        radioResidentialGroup2 = (RadioGroup) findViewById(R.id.radioResidentialGroup2);
        radioResidentialGroupcut= (RadioGroup) findViewById(R.id.radioResidentialGroupcut);
        radioBedroomothers= (RadioGroup) findViewById(R.id.radioBedroomothers);
        radioBedroomGroup = (RadioGroup) findViewById(R.id.radioBedroomGroup);
        radioBedroomGroupbar1 = (RadioGroup) findViewById(R.id.radioBedroomGroupbar1);
        radioBedroomGroupbar2 = (RadioGroup) findViewById(R.id.radioBedroomGroupbar2);
        radiorentGroup = (RadioGroup) findViewById(R.id.radiorentGroup);
        radioFurnishedGroup = (RadioGroup) findViewById(R.id.radioFurnishedGroup);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        button_liner=(LinearLayout)findViewById(R.id.button_liner);
        phone_enable=(CheckBox)findViewById(R.id.phone_enable);

        mSelectedImagesContainer = (ViewGroup) findViewById(R.id.selected_photos_container);
        FontsManager.initFormAssets(this, "mont.ttf");
        FontsManager.changeFonts(this);

        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "mont.ttf");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sharedPreferences.getString("token", "");
        id = sharedPreferences.getString("id", "");

        msg.setVisibility(View.VISIBLE);
        restrict1.setVisibility(View.VISIBLE);
        restrict2.setVisibility(View.GONE);
        restrict3.setVisibility(View.GONE);
        withoutbar.setVisibility(View.VISIBLE);
        withbar1.setVisibility(View.GONE);
        withbar2.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        submit.setVisibility(View.VISIBLE);
        others.setVisibility(View.GONE);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);


        View getImages = findViewById(R.id.get_images);
        getImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getImagesView();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, city);


        mdatas = new ArrayList<>();


        spinner.setOnItemSelectedListener(this);


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


                setLayout();
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
                                          Log.e("tag", "12345");

                                          selrent = radiorentGroup.getCheckedRadioButtonId();//rent
                                          radiorentButton=(RadioButton)findViewById(selrent);
                                          selectedId5 = radioFurnishedGroup.getCheckedRadioButtonId();//furnish
                                          radioFurnishedButton = (RadioButton) findViewById(selectedId5);

                                          str_landmark = lanmark_et.getText().toString();
                                          str_address = address_et.getText().toString();
                                          str_renttype = radiorentButton.getText().toString();
                                          str_furnished_type = radioFurnishedButton.getText().toString();
                                          str_monthrent = rentamount_et.getText().toString();
                                          str_deposit = depositamount_et.getText().toString();
                                          str_description = description_et.getText().toString();



                                          if(spin_val.equals("Others"))
                                          {
                                              selectedId3 = radioResidentialGroup1.getCheckedRadioButtonId();
                                              radioresidentalButton1 = (RadioButton) findViewById(selectedId3);
                                              str_residential = radioresidentalButton1.getText().toString();

                                              other_bed = radioBedroomothers.getCheckedRadioButtonId();
                                              radioBedroomButton = (RadioButton) findViewById(other_bed);
                                              str_bedroom = radioBedroomButton.getText().toString();


                                              if (str_bedroom.equals("2 BHK")) {
                                                  root2 = "2bhk";
                                              } else if (str_bedroom.equals("3 BHK")) {
                                                  root2 = "3bhk";
                                              } else if (str_bedroom.equals("4 BHK")) {
                                                  root2 = "4bhk";
                                              }
                                              else
                                              {
                                                  root2 = "1bhk";
                                              }



                                              if(str_residential.equals("Apartment")){
                                                  root1 = "Apartment";
                                              }
                                              else if(str_residential.equals("Villa")){
                                                  root1 = "Villa";
                                              }
                                              else
                                              {
                                                  root1 = "Duplex";
                                              }

                                          }



                                          else if(spin_val.equals("Aqualily"))
                                          {
                                              selectedId3 = radioBedroomGroup.getCheckedRadioButtonId();
                                              selectedId1 = radioResidentialGroup1.getCheckedRadioButtonId();
                                              radioresidentalButton1 = (RadioButton) findViewById(selectedId1);
                                              str_residential = radioresidentalButton1.getText().toString();

                                              radioBedroomButton = (RadioButton) findViewById(selectedId3);
                                              str_bedroom = radioBedroomButton.getText().toString();


                                              if (str_bedroom.equals("2 BHK")) {
                                                  root2 = "2bhk";
                                              } else if (str_bedroom.equals("3 BHK")) {
                                                  root2 = "3bhk";
                                              } else {
                                                  root2 = "4bhk";
                                              }



                                              if(str_residential.equals("Apartment")){
                                                  root1 = "Apartment";
                                              }
                                              else if(str_residential.equals("Villa")){
                                                  root1 = "Villa";
                                              }
                                              else
                                              {
                                                  root1 = "Duplex";
                                              }

                                          }


                                          else if(spin_val.equals("Sylvan County"))
                                          {
                                              selectedId3 = radioBedroomGroup.getCheckedRadioButtonId();
                                              lanmark_et.setText("Opposite BMW Factory");
                                              address_et.setText("Mahindra City");
                                              selectedId1 = radioResidentialGroup1.getCheckedRadioButtonId();
                                              radioresidentalButton1 = (RadioButton) findViewById(selectedId1);
                                              str_residential = radioresidentalButton1.getText().toString();

                                              radioBedroomButton = (RadioButton) findViewById(selectedId3);
                                              str_bedroom = radioBedroomButton.getText().toString();



                                              if (str_bedroom.equals("2 BHK")) {
                                                  root2 = "2bhk";
                                              } else if (str_bedroom.equals("3 BHK")) {
                                                  root2 = "3bhk";
                                              } else {
                                                  root2 = "4bhk";
                                              }


                                              if(str_residential.equals("Apartment")){
                                                  root1 = "Apartment";
                                              }
                                              else if(str_residential.equals("Villa")){
                                                  root1 = "Villa";
                                              }
                                              else
                                              {
                                                  root1 = "Duplex";
                                              }


                                          }
                                          else if(spin_val.equals("Nova"))
                                          {
                                              selectedId2 = radioResidentialGroup2.getCheckedRadioButtonId();
                                              selectedId6=radioBedroomGroupbar1.getCheckedRadioButtonId();

                                              radioresidentalButton2 = (RadioButton) findViewById(selectedId2);
                                              radiobar1=(RadioButton)findViewById(selectedId6);
                                              str_bedroom = radiobar1.getText().toString();
                                              str_residential=radioresidentalButton2.getText().toString();


                                              if(str_bedroom.equals("1/1.5 BHK")){
                                                  root2 = "1/1.5bhk";
                                              } else if(str_bedroom.equals("2/2.5 BHK")){
                                                  root2 = "2/2.5bhk";
                                              } else
                                                  {
                                                      root2 = "studio";
                                                  }

                                              str_residential = radioresidentalButton2.getText().toString();
                                              Log.e("tag", "zzz2" + str_residential1);

                                              if (str_residential.equals("Apartment")) {
                                                  root1 = "Apartment";
                                              }
                                              else
                                                  root1 = "Duplex";

                                          }





                                          else if(spin_val.equals("Iris Court"))
                                          {
                                              selectedIdcut = radioResidentialGroupcut.getCheckedRadioButtonId();
                                              radioresidentalButtoncut = (RadioButton) findViewById(selectedIdcut);
                                              selectedId7=radioBedroomGroupbar2.getCheckedRadioButtonId();
                                              str_residential = radioresidentalButtoncut.getText().toString();

                                              radiobar2=(RadioButton)findViewById(selectedId7);
                                              str_bedroom = radiobar2.getText().toString();




                                              if(str_bedroom.equals("2/2.5 BHK")){
                                                  root2 = "2/2.5bhk";
                                              }else
                                                  root2 = "3bhk";



                                              if (str_residential.equals("Apartment"))
                                              {
                                                  root1 = "Apartment";
                                              }


                                          }

                                          InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                          imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);












                                          if (str_furnished_type.equals("Fully Furnished")) {
                                              root3 = "Furnished";
                                          } else if (str_furnished_type.equals("Semi Furnished")) {
                                              root3 = "Semi-furnished";
                                          } else
                                              root3 = "Unfurnished";





                                          if (phone_enable.isChecked()) {
                                              str_pho_enable = "enabled";
                                          } else {
                                              str_pho_enable = "Hidden Contact";
                                          }
                                            Log.e("tag","oops"+str_pho_enable);

                                          if (Util.Operations.isOnline(PostHouse.this)) {
                                              if (!spin_val.isEmpty() && !str_landmark.isEmpty() && !str_address.isEmpty() && !str_monthrent.isEmpty() && !str_deposit.isEmpty() && !str_description.isEmpty()) {

                                                 new PostHouseAsync(spin_val, str_landmark, str_address, root1, root2, str_renttype,
                                                          root3, str_monthrent, str_deposit, str_description, str_city,str_pho_enable).execute();


                                              } else {
                                                  Toast.makeText(getApplicationContext(), "Invalid Fields..", Toast.LENGTH_LONG).show();
                                              }
                                          } else {
                                              Toast.makeText(getApplicationContext(), "No Internet Connectivity", Toast.LENGTH_LONG).show();
                                          }

                                    }
                                  }
        );
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

    private void setLayout() {


        if(spin_val.equals("Others")) {
            restrict1.setVisibility(View.VISIBLE);
            restrict2.setVisibility(View.GONE);
            restrict3.setVisibility(View.GONE);
            withbar1.setVisibility(View.GONE);
            withbar2.setVisibility(View.GONE);
            withoutbar.setVisibility(View.GONE);
            others.setVisibility(View.VISIBLE);
        }


        else if (spin_val.equals("Aqualily")) {
            restrict1.setVisibility(View.VISIBLE);
            restrict2.setVisibility(View.GONE);
            restrict3.setVisibility(View.GONE);
            withbar1.setVisibility(View.GONE);
            withbar2.setVisibility(View.GONE);
            withoutbar.setVisibility(View.VISIBLE);
            others.setVisibility(View.GONE);
        } else {
            if (spin_val.equals("Sylvan County")) {
                restrict1.setVisibility(View.VISIBLE);
                restrict2.setVisibility(View.GONE);
                restrict3.setVisibility(View.GONE);
                withbar1.setVisibility(View.GONE);
                withbar2.setVisibility(View.GONE);
                withoutbar.setVisibility(View.VISIBLE);
                others.setVisibility(View.GONE);

            } else {
                if (spin_val.equals("Nova")) {
                    Log.e("tag","sds");
                    restrict1.setVisibility(View.GONE);
                    restrict2.setVisibility(View.VISIBLE);
                    restrict3.setVisibility(View.GONE);
                    withbar1.setVisibility(View.VISIBLE);
                    withbar2.setVisibility(View.GONE);
                    withoutbar.setVisibility(View.GONE);
                    others.setVisibility(View.GONE);
                } else {

                    restrict1.setVisibility(View.GONE);
                    restrict2.setVisibility(View.GONE);
                    restrict3.setVisibility(View.VISIBLE);
                    withbar1.setVisibility(View.GONE);
                    withbar2.setVisibility(View.VISIBLE);
                    withoutbar.setVisibility(View.GONE);
                    others.setVisibility(View.GONE);

                }
            }
        }
    }






    private void getImagesView() {

        Intent intent = new Intent(getApplicationContext(), ImagePickerActivity.class);
        Config config = new Config.Builder()
                .setTabBackgroundColor(R.color.white)    // set tab background color. Default white.

                .setSelectionLimit(6)    // set photo selection limit. Default unlimited selection.
                .build();
        ImagePickerActivity.setConfig(config);
        startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);

       /* Intent intent_image = new Intent(PostHouse.this, ImagePickerActivity.class);
        startActivityForResult(intent_image, INTENT_REQUEST_GET_IMAGES);*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resuleCode, Intent intent) {
        super.onActivityResult(requestCode, resuleCode, intent);
        msg.setVisibility(View.GONE);

        if (resuleCode == Activity.RESULT_OK) {
            if (requestCode == INTENT_REQUEST_GET_IMAGES || requestCode == INTENT_REQUEST_GET_N_IMAGES) {
                Parcelable[] parcelableUris = intent.getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

                if (parcelableUris == null) {
                    return;
                }

                // Java doesn't allow array casting, this is a little hack
                Uri[] uris = new Uri[parcelableUris.length];
                System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);

                if (uris != null) {

                    for (Uri uri : uris) {
                        Log.e("tag", " uri: " + uri);
                        //**************************************************
                        path = uri.toString();
                        //**************************************************
                        mMedia.add(uri);

                        mdatas.add(String.valueOf(uri));
                        //path=String.valueOf(uri);

                        Log.d("tag", "choosed file" + mMedia);
                        StringBuilder builder = new StringBuilder();
                        for (Uri value : mMedia) {
                            builder.append(value + "#####");

                        }
                        String text = builder.toString();
                        imagearray=text.split("\\#\\#\\#\\#\\#");




                    }
                    showMedia();
                }
            }
        }
    }



    private void showMedia() {
        // Remove all views before
        // adding the new ones.
        mSelectedImagesContainer.removeAllViews();

        Iterator<Uri> iterator = mMedia.iterator();
        ImageInternalFetcher imageFetcher = new ImageInternalFetcher(this, 500);
        while (iterator.hasNext()) {
            Uri uri = iterator.next();

            // showImage(uri);
            Log.i(TAG, " uri: " + uri);
            if (mMedia.size() >= 1) {
                mSelectedImagesContainer.setVisibility(View.VISIBLE);
            }

            View imageHolder = LayoutInflater.from(this).inflate(R.layout.media_layout, null);
            thumbnail = (ImageView) imageHolder.findViewById(R.id.media_image);

            if (!uri.toString().contains("content://")) {
                uri = Uri.fromFile(new File(uri.toString()));
            }

            imageFetcher.loadImage(uri, thumbnail);

            mSelectedImagesContainer.addView(imageHolder);


            int wdpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics());
            int htpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics());
            //thumbnail.setLayoutParams(new FrameLayout.LayoutParams(wdpx, htpx));
            thumbnail.getLayoutParams().width = 250;
            thumbnail.getLayoutParams().height = 250;
            thumbnail.setAdjustViewBounds(true);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private class PostHouseAsync extends AsyncTask<String,String,String>{

        String spin_val,str_landmark,str_address,str_residential,str_bedroom,str_renttype,str_furnished_type,str_monthrent,str_deposit,str_description,str_city,str_pho_enable;


        public PostHouseAsync(String spin_val, String str_landmark, String str_address, String str_residential, String str_bedroom, String str_renttype, String str_furnished_type, String str_monthrent, String str_deposit, String str_description, String str_city, String str_pho_enable) {
            progressBar.setVisibility(View.VISIBLE);
            this.spin_val = spin_val;
            this.str_landmark = str_landmark;
            this.str_address = str_address;
            this.str_residential = str_residential;
            this.str_bedroom = str_bedroom;
            this.str_renttype = str_renttype;
            this.str_furnished_type = str_furnished_type;
            this.str_monthrent = str_monthrent;
            this.str_deposit = str_deposit;
            this.str_description = str_description;
            this.str_city = str_city;
            this.str_pho_enable=str_pho_enable;
        }


        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);

        }
        @Override
        protected String doInBackground(String... params) {

            String responseString = null;
            ContentBody cbFile = null;
            String jsonStr;
            //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost postMethod = new HttpPost("http://104.197.7.143:3000/api/postforrent");
                postMethod.addHeader("x-access-token", token);
                postMethod.addHeader("id", id);
                postMethod.addHeader("location", spin_val);
                postMethod.addHeader("landmark", str_landmark);
                postMethod.addHeader("address", str_address);
                postMethod.addHeader("city", "Mahindra City");
                postMethod.addHeader("furnishedtype", str_furnished_type);
                postMethod.addHeader("bedroom",root2);
                postMethod.addHeader("renttype", str_renttype);
                postMethod.addHeader("monthlyrent", str_monthrent);
                postMethod.addHeader("deposit", str_deposit);
                postMethod.addHeader("description", str_description);
                postMethod.addHeader("residential", root1);
                postMethod.addHeader("phone", str_pho_enable);

                Log.e("tag","checking 1"  +spin_val);
                Log.e("tag","checking 2"  +str_landmark);
                Log.e("tag","checking 3"  +str_address);
                Log.e("tag","checking 4"  +str_furnished_type);
                Log.e("tag","checking 5"  +root2);
                Log.e("tag","checking 6"  +str_renttype);
                Log.e("tag","checking 7"  +str_monthrent);
                Log.e("tag","checking 8"  +str_deposit);
                Log.e("tag","checking 9"  +str_description);
                Log.e("tag","checking 10"  +root1);
                Log.e("tag","checking 11"  +str_pho_enable);





                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                for( int i =0;i<mdatas.size();i++){
                    File sourceFile = new File(String.valueOf(mdatas.get(i)));
                    cbFile = new FileBody( sourceFile, "image/jpeg" );
                    entity.addPart("file", cbFile);
                }



                postMethod.setEntity(entity);

                HttpResponse response = client.execute(postMethod);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                Log.e("tag","res "+response.getStatusLine().toString());
                if (statusCode == 200) {
                    responseString = EntityUtils.toString(r_entity);

                    JSONObject result1 = new JSONObject(responseString);
                    String status = result1.getString("status");
                    Log.e("tag","status..........."+status);

                    if (status.equals("true")) {
                        Log.e("tag","Success...........");
                    }
                } else {
                    responseString = "Error occurred! Http Status Code: " + statusCode;
                    Log.e("tag","failure...........");
            }

            } catch (Exception e) {
                responseString = e.toString();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----result---->" + jsonStr);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);
            super.onPostExecute(jsonStr);

            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");
                Log.e("tag", "<-----Status----->" + status);
                Log.e("tag", "<-----msg----->" + msg);

                if (status.equals("true"))
                {

                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    lanmark_et.setText("");
                    address_et.setText("");
                    rentamount_et.setText("");
                    depositamount_et.setText("");
                    description_et.setText("");
                    mSelectedImagesContainer.removeAllViews();




                }
                else
                {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();


                }
            }
            catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(PostHouse.this,Dashboard.class);
        startActivity(i);
        finish();
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        System.out.println(" OUTPUT -->" + result);
        return result;

    }
}