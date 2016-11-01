package mcity.com.mcity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import android.support.v4.view.ViewPager;
import android.telephony.SmsManager;
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

import com.squareup.picasso.Picasso;

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
public class MyHouseAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    Bitmap b;
    HashMap<String, String> resultp = new HashMap<String, String>();
    ArrayList<String> loading ;
    String main_id, sub_id,token,userid;
    String URL = Data_Service.URL_API + "removerent";
    String tokenid,fieldid;

    public MyHouseAdapter(Context ctx, ArrayList<HashMap<String, String>> arraylist) {
        this.ctx = ctx;
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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        token = sharedPreferences.getString("token", "");
        Log.e("tag","@@@1"+token);
        userid = sharedPreferences.getString("id","");
        Log.e("tag","@@@2"+userid);

        TextView deletehouse, bedroom, rentType, furnishedType, address, subtype, bedtv, renttv, subtv, furnishedtv, make_call,
                send_mail, bedtypes, loc, rs, mcity, city;


        Typeface tf = Typeface.createFromAsset(ctx.getAssets(), "mont.ttf");
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.myhouse_adapter, parent, false);
        resultp = data.get(position);
        String str_sms;

        loading = new ArrayList<>();

        deletehouse = (TextView) itemView.findViewById(R.id.deletehouse);
        address = (TextView) itemView.findViewById(R.id.rent);
        subtype = (TextView) itemView.findViewById(R.id.subtype);
        rentType = (TextView) itemView.findViewById(R.id.renttype);
        furnishedType = (TextView) itemView.findViewById(R.id.furnishedtype);
        loc = (TextView) itemView.findViewById(R.id.loc);
        rs = (TextView) itemView.findViewById(R.id.rs);
        mcity = (TextView) itemView.findViewById(R.id.mcity);
        city = (TextView) itemView.findViewById(R.id.city);
        bedtypes = (TextView) itemView.findViewById(R.id.bedtypes);
        bedroom = (TextView) itemView.findViewById(R.id.bedroom);
        bedtv = (TextView) itemView.findViewById(R.id.bedroom_tv);
        renttv = (TextView) itemView.findViewById(R.id.rent_tv);
        subtv = (TextView) itemView.findViewById(R.id.subtype_tv);
        furnishedtv = (TextView) itemView.findViewById(R.id.furnishedtype_tv);
        LinearLayout linearLayout = (LinearLayout) itemView.findViewById(R.id.view);


        address.setText(resultp.get(HouseHistory.monthlyrent));
        bedtypes.setText(resultp.get(HouseHistory.bedroom));
        loc.setText(resultp.get(HouseHistory.location));
        bedroom.setText(resultp.get(HouseHistory.bedroom));
        rentType.setText(resultp.get(HouseHistory.renttype));
        subtype.setText(resultp.get(HouseHistory.furnishedtype));
        furnishedType.setText(resultp.get(HouseHistory.residential));



        address.setTypeface(tf);
        rentType.setTypeface(tf);
        //description.setTypeface(tf);
        bedtypes.setTypeface(tf);
        loc.setTypeface(tf);
        rs.setTypeface(tf);
        mcity.setTypeface(tf);
        city.setTypeface(tf);
        furnishedType.setTypeface(tf);
        bedroom.setTypeface(tf);
        subtype.setTypeface(tf);
        bedtv.setTypeface(tf);
        renttv.setTypeface(tf);
        furnishedtv.setTypeface(tf);
        subtv.setTypeface(tf);



        deletehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultp = data.get(position);

                tokenid=resultp.get(HouseHistory.id_main);
                fieldid=resultp.get(HouseHistory.id_sub);
                Log.e("tag","test1"+main_id);
                Log.e("tag","test2"+sub_id);

                deletehome();

            }
        });




        if (position % 2 == 0) {
            linearLayout.setBackgroundColor(ctx.getResources().getColor(R.color.bg1));

        } else {
            linearLayout.setBackgroundColor(ctx.getResources().getColor(R.color.bg2));
        }

        return itemView;
    }

    private void deletehome() {
        LayoutInflater layoutInflater = LayoutInflater.from(this.ctx);
        View promptView = layoutInflater.inflate(R.layout.exitdialog, null);
        final AlertDialog alert_msg1 = new AlertDialog.Builder(this.ctx).create();
        alert_msg1.setCancelable(false);
        Window window = alert_msg1.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView head1 = (TextView) promptView.findViewById(R.id.head1);
        final TextView head2 = (TextView) promptView.findViewById(R.id.head2);
        final ImageView no = (ImageView) promptView.findViewById(R.id.no);
        final ImageView yes = (ImageView) promptView.findViewById(R.id.yes);

        Typeface tf = Typeface.createFromAsset(ctx.getAssets(), "mont.ttf");
        head1.setTypeface(tf);
        head1.setText("Exit");
        head2.setText("Do You want to Logout?");

        yes.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                new DeleteHouseAsync().execute();
                alert_msg1.dismiss();

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert_msg1.dismiss();
            }
        });
        alert_msg1.setView(promptView);
        alert_msg1.show();
    }




    private class DeleteHouseAsync extends AsyncTask<String, String, String> {
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
                postMethod.addHeader("id", userid);
                postMethod.addHeader("Content-Type", "application/x-www-form-urlencoded");


                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("id1", tokenid);
                jsonObject.accumulate("id2", fieldid);
                json = jsonObject.toString();

                return jsonStr = HttpUtils.makeRequest1(URL, json, userid, token);
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
                    Intent intent = new Intent(ctx,MPostHistory.class);
                    ctx.startActivity(intent);

                    Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();


                }

                else {

                    Log.e("tag", "error");
                    Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}



