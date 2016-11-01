package mcity.com.mcity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Admin on 25-10-2016.
 */
public class MyPgAdapter extends BaseAdapter {


    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    Bitmap b;
    HashMap<String, String> resultp = new HashMap<String, String>();
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    String str_owner_no,str_owner_mail,token,uid,main_id,sub_id;
    String URL = Data_Service.URL_API + "removeroom";

    public MyPgAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;


    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        token = sharedPreferences.getString("token", "");
        Log.e("tag","@@@1"+token);
        uid = sharedPreferences.getString("id","");
        Log.e("tag","@@@2"+uid);


        TextView loc,roomTypeval,address,rent,chennai,mcity,rs,subtype, roomtype, description,rentType,gender,furnishedType,monthrent,bedtv,renttv,subtv,furnishedtv,sendsms,viewcontact;
        TextView delete, send_mail;
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "mont.ttf");


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.mypgadapter, parent, false);
        resultp = data.get(position);


        loc = (TextView) itemView.findViewById(R.id.loc);
        address = (TextView) itemView.findViewById(R.id.city);
        rent=(TextView) itemView.findViewById(R.id.rents);
        subtype=(TextView) itemView.findViewById(R.id.subtype);
        chennai=(TextView) itemView.findViewById(R.id.chennai);
        rs=(TextView) itemView.findViewById(R.id.rs);
        mcity = (TextView) itemView.findViewById(R.id.mcity);
        gender =(TextView)itemView.findViewById(R.id.gender);
        delete = (TextView)itemView.findViewById(R.id.delete);

        renttv = (TextView) itemView.findViewById(R.id.rent_tv);
        subtv = (TextView) itemView.findViewById(R.id.subtype_tv);
        furnishedtv = (TextView) itemView.findViewById(R.id.furnishedtype_tv);
        LinearLayout linearLayout = (LinearLayout) itemView.findViewById(R.id.view);
        roomTypeval=(TextView) itemView.findViewById(R.id.roomTypeval);


        loc.setText(resultp.get(SearchPGFilter.location));
        rent.setText(resultp.get(SearchPGFilter.monthlyrent));
        subtype.setText(resultp.get(SearchPGFilter.monthlyrent));
        gender.setText(resultp.get(SearchPGFilter.gender));
        roomTypeval.setText(resultp.get(SearchPGFilter.roomtype));


        String enable_status=resultp.get(SearchPGFilter.phone);
        Log.e("tag","please check"+enable_status);


        chennai.setTypeface(tf);
        loc.setTypeface(tf);
        rent.setTypeface(tf);
        rs.setTypeface(tf);
        mcity.setTypeface(tf);
        subtype.setTypeface(tf);
        renttv.setTypeface(tf);
        furnishedtv.setTypeface(tf);
        gender.setTypeface(tf);
        subtv.setTypeface(tf);
        roomTypeval.setTypeface(tf);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultp = data.get(position);

                main_id=resultp.get(PGHistory.main_id);
                sub_id=resultp.get(PGHistory.sub_id);
                Log.e("tag","test1"+main_id);
                Log.e("tag","test2"+sub_id);

                deletepg();

            }
        });



        if (position % 2 == 0) {
            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.bg1));

        } else {
            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.bg2));
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return itemView;
    }

    private void deletepg() {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.exitdialog, null);
        final android.app.AlertDialog alertD = new android.app.AlertDialog.Builder(context).create();
        alertD.setCancelable(false);
        Window window = alertD.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView head1 = (TextView) promptView.findViewById(R.id.head1);
        final TextView head2 = (TextView) promptView.findViewById(R.id.head2);
        final ImageView no = (ImageView) promptView.findViewById(R.id.no);
        final ImageView yes = (ImageView) promptView.findViewById(R.id.yes);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "mont.ttf");
        head1.setTypeface(tf);
        head1.setText("Delete Post...");
        head2.setText("Do You want to delete this post?");

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeletePgAsync().execute();
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



    private class DeletePgAsync extends AsyncTask<String, String, String> {
        @Override


        protected void onPreExecute() {
            super.onPreExecute();
            //progress.setVisibility(View.VISIBLE);
            Log.e("tag", "two");


        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";
            String id = "";
            try {

                HttpClient client = new DefaultHttpClient();
                HttpPost postMethod = new HttpPost();
                postMethod.addHeader("x-access-token", token);
                postMethod.addHeader("id", uid);
                postMethod.addHeader("Content-Type", "application/x-www-form-urlencoded");


                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("id1", main_id);
                jsonObject.accumulate("id2", sub_id);
                json = jsonObject.toString();

                return jsonStr = HttpUtils.makeRequest1(URL, json, uid, token);
            } catch (Exception e) {
                Log.e("InputStream", e.getLocalizedMessage());
            }
            return null;

        }


        @Override
        protected void onPostExecute(String jsonstr) {
            Log.e("tag", "<-----result---->" + jsonstr);
            //progress.setVisibility(View.GONE);
            super.onPostExecute(jsonstr);

            try {
                JSONObject jo = new JSONObject(jsonstr);
                String status = jo.getString("status");
                String msg = jo.getString("message");
                Log.e("tag", "<-----Status----->" + status);
                Log.e("tag", "<-----msg----->" + msg);

                if (status.equals("true")) {

                    Log.e("tag", "success");
                    Intent intent = new Intent(context,MPostHistory.class);
                    context.startActivity(intent);

                }

                else {

                    Log.e("tag", "error");


                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}

