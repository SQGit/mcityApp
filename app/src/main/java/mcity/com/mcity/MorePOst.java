package mcity.com.mcity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.PopupMenu;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sloop.fonts.FontsManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Admin on 16-10-2016.
 */
public class MorePOst extends Activity {

    ProgressBar progressBar;
    ListView searchlist;
    RideAdapter1 rideAdapter;
    ArrayList<HashMap<String, String>> searchridelist;
    ArrayList<HashMap<String, String>> normal_searchridelist;
    String token, uid,pathnew;
    String URL = Data_Service.URL_API + "searchforride";
    LinearLayout back_arrow,progress_linr;
    String key_open,str_from,str_to;
    String URL1 = Data_Service.URL_API + "searchforridefilter";
    ImageView settings_icon;


    static String phone = "phone";
    static String from = "from";
    static String to = "to";
    static String date = "date";
    static String mobileno = "mobileno";
    static String email = "email";
    static String price = "price";
    static String midwaydrop="midwaydrop";
    static String username="username";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.more_post);

        searchlist = (ListView) findViewById(R.id.searchlist);
        back_arrow=(LinearLayout) findViewById(R.id.back_arrow);
        progress_linr=(LinearLayout) findViewById(R.id.progress_linr);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        settings_icon = (ImageView) findViewById(R.id.settings_icon);

        FontsManager.initFormAssets(this, "mont.ttf");
        FontsManager.changeFonts(this);

        searchridelist = new ArrayList<>();
        normal_searchridelist=new ArrayList<>();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sharedPreferences.getString("token", "");
        uid = sharedPreferences.getString("id", "");
        key_open=sharedPreferences.getString("view_post","");
        Log.e("tag","key_open"+key_open);
        str_from=sharedPreferences.getString("from","");
        str_to=sharedPreferences.getString("to","");

        progress_linr.setVisibility(View.GONE);


        /*if(key_open.equals("filter"))
        {
            Log.e("tag","option1");
            new filterAsync(str_from,str_to).execute();

        }
        else if(key_open.equals(""))
        {
            Log.e("tag","option2");
         new RideSearchAsync().execute();

        }*/


        if(sharedPreferences.getString("view_post","").equals("")){
            new RideSearchAsync().execute();
            Log.e("tagg","normal");
        }
        else{

            str_from=sharedPreferences.getString("from","");
            str_to=sharedPreferences.getString("to","");
            new filterAsync(str_from,str_to).execute();

            Log.e("tagg","filter"+str_to+str_from);

        }

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),RideSearch.class);
                startActivity(i);
                finish();
            }
        });

        settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tag", "DDD");


                PopupMenu popup = new PopupMenu(MorePOst.this, settings_icon);
                //Inflating the Popup using xml file
                // popup.getMenuInflater().inflate(R.menu.opt_menu, popup.getMenu());

                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.opt_menu, popup.getMenu());
                MenuItem pinMenuItem1 = popup.getMenu().getItem(0);
                MenuItem pinMenuItem2 = popup.getMenu().getItem(1);
                MenuItem pinMenuItem3 = popup.getMenu().getItem(2);

                applyFontToMenuItem(pinMenuItem1);
                applyFontToMenuItem(pinMenuItem2);
                applyFontToMenuItem(pinMenuItem3);

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();


                        switch (id) {
                            case R.id.item1:
                                aboutUs();
                                return true;

                            case R.id.item2:
                                Intent intent=new Intent(getApplicationContext(),MyRideHistory.class);
                                startActivity(intent);
                                finish();
                                return true;


                            case R.id.item3:
                                exitIcon();
                                return true;


                        }
                        return true;
                    }

                });

                popup.show();//showing popup menu

            }
        });

    }

    //************* change option menu typeface settings page.
    private void applyFontToMenuItem(MenuItem mi)
    {
        Typeface font = Typeface.createFromAsset(getAssets(), "mont.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    private void exitIcon() {

        LayoutInflater layoutInflater = LayoutInflater.from(MorePOst.this);
        View promptView = layoutInflater.inflate(R.layout.exitdialog, null);
        final AlertDialog alertD = new AlertDialog.Builder(MorePOst.this).create();
        alertD.setCancelable(false);
        Window window = alertD.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView head1 = (TextView) promptView.findViewById(R.id.head1);
        final TextView head2 = (TextView) promptView.findViewById(R.id.head2);
        final ImageView no = (ImageView) promptView.findViewById(R.id.no);
        final ImageView yes = (ImageView) promptView.findViewById(R.id.yes);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "mont.ttf");
        head1.setTypeface(tf);


        yes.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(MorePOst.this);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("check","");
                editor.commit();
                logoutMethod();
                alertD.dismiss();



            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.dismiss();
            }
        });
        alertD.setView(promptView);
        alertD.show();
    }

    private void logoutMethod() {
        new Logout().execute();

    }


    private void aboutUs() {

        LayoutInflater layoutInflater = LayoutInflater.from(MorePOst.this);
        View promptView = layoutInflater.inflate(R.layout.aboutus, null);
        final AlertDialog alertD = new AlertDialog.Builder(MorePOst.this).create();
        alertD.setCancelable(false);
        Window window = alertD.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView head1 = (TextView) promptView.findViewById(R.id.txt_head2);
        final TextView txt_msg = (TextView) promptView.findViewById(R.id.txt_msg);
        final Button yes = (Button) promptView.findViewById(R.id.btn_ok2);


        txt_msg.setText("\n" +"    SQIndia is a total Information Technology Company based out of Guduvanchery.SQIndia has its own Software Development Centre and provides Technology Consulting Services to its clients in India,US,UK and Singapore.Some of its Elite Customers include Mahindra,TVS,Nissan,ZOHO.\n\n              SQIndia also operates 2 Exclusive Lenovo Outlets - Guduvanchery and Chengalpet.SQIndia also has a MultiBranded Mobile showroom with a LIVE DEMO counters.\n\n          The aspirations to grow and serve people in all aspects has always been part of the Company's motto. Mr.Gopi who is the CEO/Founder of the Organization has spent more than a decade in the US and strives to make things easy and accessible for a common man."
        );

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "mont.ttf");
        head1.setTypeface(tf);
        txt_msg.setTypeface(tf);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.dismiss();
            }
        });


        alertD.setView(promptView);
        alertD.show();

    }

    private class RideSearchAsync extends AsyncTask<String, String, String> {
        @Override


        protected void onPreExecute() {
            super.onPreExecute();
            progress_linr.setVisibility(View.VISIBLE);

            //progressBar.setVisibility(View.VISIBLE);

        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";
            String id = "";
            try {

                //location,landmark,address,roomtype,monthlyrent,gender,description
                JSONObject jsonObject = new JSONObject();
                json = jsonObject.toString();

                return jsonStr = HttpUtils.makeRequest1(URL, json, uid, token);
            } catch (Exception e) {
                Log.e("InputStream", e.getLocalizedMessage());
            }
            return null;

        }



        @Override
        protected void onPostExecute(String jsonstr) {
            Log.e("tag", "<-----111111111--------->" + jsonstr);
            super.onPostExecute(jsonstr);
            progressBar.setVisibility(View.GONE);

            if (jsonstr.equals("")) {

                Toast.makeText(getApplicationContext(),"Check Network Connection",Toast.LENGTH_SHORT).show();

            } else {

                try {

                    JSONObject jo = new JSONObject(jsonstr);
                    String status = jo.getString("status");
                    JSONArray data1 = jo.getJSONArray("message");
                    Log.e("tag","...#...1"+data1);





                    if (data1.length() > 0) {
                        for (int i1 = 0; i1 < data1.length(); i1++) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            JSONObject jsonObject = data1.getJSONObject(i1);


                            String postforride = jsonObject.getString("postforride");

                            JSONObject pos_rent = new JSONObject(postforride);
                            Log.e("tag","pos_rent"+  pos_rent);

                            map.put("mobileno", jsonObject.getString("mobileno"));
                            map.put("email", jsonObject.getString("email"));
                            map.put("username", jsonObject.getString("username"));

                            map.put("from", pos_rent.getString("from"));
                            map.put("to", pos_rent.getString("to"));
                            map.put("date", pos_rent.getString("date"));
                            map.put("price", pos_rent.getString("price"));
                            map.put("phone", pos_rent.getString("phone"));
                            map.put("midwaydrop", pos_rent.getString("midwaydrop"));


                            JSONArray img_ar = jsonObject.getJSONArray("licence");

                            Log.e("tag","pos_rent_image"+  img_ar);

                            if(img_ar.length()>0){

                                for(int i =0;i<img_ar.length();i++){

                                    JSONObject img_obj =img_ar.getJSONObject(i);

                                    pathnew = "http://104.197.7.143:3000/licence/" + img_obj.getString("filename");

                                    Log.e("tag", "data: " + pathnew);

                                    map.put("path", pathnew);


                                }
                            }

                            normal_searchridelist.add(map);
                            Log.e("tag","<---contactList---->"+  normal_searchridelist);


                        }


                    } else
                    {
                        Toast.makeText(getApplicationContext(),"Your desired house/PG is not available Now..",Toast.LENGTH_SHORT).show();
                    }


                    Log.e("tag","normal_searchridelist"+normal_searchridelist);

                    rideAdapter = new RideAdapter1(MorePOst.this, normal_searchridelist);
                    searchlist.setAdapter(rideAdapter);


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(MorePOst.this,RideSearch.class);
        startActivity(i);
        finish();
    }

    private class filterAsync extends AsyncTask<String,String,String>{

        String source,end;
        public filterAsync(String source, String end) {
            this.source = source;
            this.end = end;
        }


        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            String id = "";
            try {
                Log.e("tag","id"+uid);
                Log.e("tag","token"+token);
                //location,landmark,address,roomtype,monthlyrent,gender,description
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("from", source);
                jsonObject.accumulate("to", end);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(URL1, json, uid, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonstr) {
            Log.e("tag", "<-----111111111--------->" + jsonstr);
            super.onPostExecute(jsonstr);
            progressBar.setVisibility(View.GONE);

            if (jsonstr.equals("")) {

                Toast.makeText(getApplicationContext(),"Check Network Connection",Toast.LENGTH_SHORT).show();

            } else {

                try {

                    JSONObject jo = new JSONObject(jsonstr);
                    String status = jo.getString("status");
                    JSONArray data1 = jo.getJSONArray("message");
                    Log.e("tag","...#...1"+data1);





                    if (data1.length() > 0) {
                        for (int i1 = 0; i1 < data1.length(); i1++) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            JSONObject jsonObject = data1.getJSONObject(i1);


                            String postforride = jsonObject.getString("postforride");

                            JSONObject pos_rent = new JSONObject(postforride);
                            Log.e("tag","pos_rent"+  pos_rent);

                            map.put("mobileno", jsonObject.getString("mobileno"));
                            map.put("email", jsonObject.getString("email"));
                            map.put("username", jsonObject.getString("username"));

                            map.put("from", pos_rent.getString("from"));
                            map.put("to", pos_rent.getString("to"));
                            map.put("date", pos_rent.getString("date"));
                            map.put("price", pos_rent.getString("price"));
                            map.put("midwaydrop", pos_rent.getString("midwaydrop"));


                            JSONArray img_ar = jsonObject.getJSONArray("licence");

                            Log.e("tag","pos_rent_image"+  img_ar);

                            if(img_ar.length()>0){

                                for(int i =0;i<img_ar.length();i++){

                                    JSONObject img_obj =img_ar.getJSONObject(i);

                                    pathnew = "http://104.197.7.143:3000/licence/" + img_obj.getString("filename");

                                    Log.e("tag", "data: " + pathnew);

                                    map.put("path", pathnew);


                                }
                            }

                            searchridelist.add(map);
                            Log.e("tag","<---contactList---->"+  searchridelist);


                        }


                    } else
                    {
                        Toast.makeText(getApplicationContext(),"Your desired house/PG is not available Now..",Toast.LENGTH_SHORT).show();
                    }


                    Log.e("tag","normal_searchridelist"+searchridelist);

                    rideAdapter = new RideAdapter1(MorePOst.this, searchridelist);
                    searchlist.setAdapter(rideAdapter);


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private class Logout extends AsyncTask<String, String, String> {
        @Override


        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);

        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost postMethod = new HttpPost("http://104.197.7.143:3000/api/logout");
                postMethod.addHeader("x-access-token", token);
                postMethod.addHeader("id", uid);
                postMethod.addHeader("Content-Type", "multipart-form-data");

                HttpResponse response = client.execute(postMethod);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                Log.e("tag","res "+response.getStatusLine().toString());
                if (statusCode == 200) {
                    json = EntityUtils.toString(r_entity);

                    JSONObject result1 = new JSONObject(json);
                    String status = result1.getString("status");
                    Log.e("tag","status..........."+status);

                    if (status.equals("true")) {
                        Log.e("tag","Success...........");
                    }
                } else {
                    json = "Error occurred! Http Status Code: " + statusCode;
                    Log.e("tag","failure...........");
                }

            } catch (Exception e) {
                json = e.toString();
            }
            return json;



        }


        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----result---->" + jsonStr);
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
                    Intent exit=new Intent(getApplicationContext(),Login.class);
                    startActivity(exit);
                    finish();


                    Log.e("tag","s...........");
                }
                else
                {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    Log.e("tag","no...........");

                }
            }
            catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}