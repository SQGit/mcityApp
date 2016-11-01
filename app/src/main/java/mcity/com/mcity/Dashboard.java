package mcity.com.mcity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.sloop.fonts.FontsManager;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Ramya on 03-09-2016.
 */

public class Dashboard extends AppCompatActivity {
    private static int IMG_RESULT = 1;
    String ImageDecode;
    Intent intent;
    String[] FILE;
    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;
   // ProgressBar progressBar;
    Typeface tf;
    public ImageView mRental, mRides, train_icon, mauto, mfood, morder,mshop,mcoupon,mgarage, settings_icon;
    public ImageView license_image;
    TextView desclaimer,site;
   // ProgressBar progressBar;
    String token, uid,imagepath;
    String URL = Data_Service.URL_API + "logout";
    String IMAGEUPLOAD = Data_Service.URL_API + "licenceupload";
    SharedPreferences s_pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);



        mRental = (ImageView) findViewById(R.id.mrental_id);
        mRides = (ImageView) findViewById(R.id.mrites_id);
        train_icon = (ImageView) findViewById(R.id.mtrain_id);

        mauto = (ImageView) findViewById(R.id.mauto);
        mfood = (ImageView) findViewById(R.id.mfood);
        morder = (ImageView) findViewById(R.id.morder);

        mshop = (ImageView) findViewById(R.id.mshop);
        mcoupon = (ImageView) findViewById(R.id.mcoupon);
        mgarage = (ImageView) findViewById(R.id.mgarage);

        desclaimer = (TextView) findViewById(R.id.desclaimer);
        settings_icon = (ImageView) findViewById(R.id.settings_icon);
        //progressBar=(ProgressBar)findViewById(R.id.progressBar);
        site=(TextView) findViewById(R.id.site);

        FontsManager.initFormAssets(this, "mont.ttf");
        FontsManager.changeFonts(this);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sharedPreferences.getString("token", "");
        uid = sharedPreferences.getString("id", "");


        //progressBar.setVisibility(View.GONE);

        mRental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MRental.class);
                startActivity(intent);
                finish();
            }
        });

        site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent =
                        new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sqindia.net/"));
                startActivity(browserIntent);
                finish();
            }
        });


        desclaimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDesclaimerContent();
            }
        });

        mRides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RideSearch.class);
                startActivity(intent);
                finish();
            }
        });


        train_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(),"Under procesing",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), TrainSearch.class);
                startActivity(intent);
                finish();
            }
        });

        mauto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Coming Soon",
                        Toast.LENGTH_LONG).show();
            }
        });

        mfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        morder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });


        mshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        mcoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        mgarage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });




        train_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(),"Under procesing",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), TrainSearch.class);
                startActivity(intent);
                finish();
            }
        });


        settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tag", "DDD");


                PopupMenu popup = new PopupMenu(Dashboard.this, settings_icon);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.opt_menu1, popup.getMenu());

                MenuInflater inflater = popup.getMenuInflater();
                //inflater.inflate(R.menu.opt_menu1, popup.getMenu());
                MenuItem pinMenuItem1 = popup.getMenu().getItem(0);
                MenuItem pinMenuItem2 = popup.getMenu().getItem(1);

                applyFontToMenuItem(pinMenuItem1);
                applyFontToMenuItem(pinMenuItem2);

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {


                        int id = item.getItemId();


                        switch (id) {
                            case R.id.item1:
                               aboutUs();
                                return true;

                            case R.id.item2:
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


    private void aboutUs() {

        LayoutInflater layoutInflater = LayoutInflater.from(Dashboard.this);
        View promptView = layoutInflater.inflate(R.layout.aboutus, null);
        final AlertDialog alertD = new AlertDialog.Builder(Dashboard.this).create();
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





    private void showDesclaimerContent() {

        final Dialog dialog = new Dialog(Dashboard.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        //adding text dynamically
        TextView txt_head2 = (TextView) dialog.findViewById(R.id.txt_head2);
        TextView txt_msg = (TextView) dialog.findViewById(R.id.txt_msg);
        TextView txt_msg2 = (TextView) dialog.findViewById(R.id.txt_msg2);
        Button btn_ok2 = (Button) dialog.findViewById(R.id.btn_ok2);

        Typeface tt = Typeface.createFromAsset(getApplicationContext().getAssets(), "mont.ttf");
        txt_head2.setTypeface(tt);
        txt_msg.setTypeface(tt);
        btn_ok2.setTypeface(tt);
        txt_msg2.setTypeface(tt);

        txt_msg.setText("\n" +
                "This mCity App has no direct or indirect connection with Mahindra Group in any sort or form." +
                "It is completely self funded and wants to provide an Independent medium of Communication. Future growth may depend on Ad revenues." +
                "The purpose of mCity App is to help the locals for the locals." +
                "The data collected with this app will NOT be shared or forwarded.We respect privacy.\n \n \n This App requires an Email configured in your mobile & an Internet Connection. \n\n\n        There are many more ideas and concepts that will be coming in the near future.\n" +
                "Please send your valuable feedback,concerns and suggestions to ");
        txt_msg2.setText("info@sqindia.net");


        txt_msg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "info@sqindia.net", null));

                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "MCity");
                emailIntent.putExtra(Intent.EXTRA_TEXT   , "");
                startActivity(emailIntent);
            }
        });
        btn_ok2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();

    }



    private void exitIcon() {

        LayoutInflater layoutInflater = LayoutInflater.from(Dashboard.this);
        View promptView = layoutInflater.inflate(R.layout.exitdialog, null);
        final AlertDialog alertD = new AlertDialog.Builder(Dashboard.this).create();
        alertD.setCancelable(false);
        Window window = alertD.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView head1 = (TextView) promptView.findViewById(R.id.head1);
        final TextView head2 = (TextView) promptView.findViewById(R.id.head2);
        final ImageView no = (ImageView) promptView.findViewById(R.id.no);
        final ImageView yes = (ImageView) promptView.findViewById(R.id.yes);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "mont.ttf");
        head1.setTypeface(tf);
        head1.setText("Exit");
        head2.setText("Do You want to Logout?");

        yes.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
                SharedPreferences.Editor editor = shared.edit();
                //editor.putString("check","");
                editor.putString("login_status","false");
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
                postMethod.addHeader("Content-Type", "application/x-www-form-urlencoded");

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

    private class ImageUpload extends AsyncTask<String, String, String> {
        @Override


        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... params) {

            String responseString = null;
            ContentBody cbFile = null;
            String jsonStr;
            //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost postMethod = new HttpPost("http://104.197.7.143:3000/api/licenceupload");
                postMethod.addHeader("x-access-token", token);
                Log.e("tag","id..........."+uid);
                postMethod.addHeader("id", uid);
                //postMethod.addHeader("Content-Type", "multipart-form-data");

                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                    File sourceFile = new File(imagepath);
                    cbFile = new FileBody( sourceFile, "image/jpeg" );
                    entity.addPart("file", cbFile);


                Log.e("tag","content "+cbFile);


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
            //progressBar.setVisibility(View.GONE);
            super.onPostExecute(jsonStr);

            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");
                String licence_key= jo.getString("licence");
                Log.e("tag", "<-----Status----->" + status);
                Log.e("tag", "<-----msg----->" + msg);

                if (status.equals("true"))
                {

                    SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor edit = s_pref.edit();
                    edit.putString("file_generate",licence_key);
                    edit.commit();

                    // location_et, lanmark_et, address_et, rentamount_et, depositamount_et, description_et;




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






    @Override
    public void onBackPressed()
    {
        //Toast.makeText(getApplicationContext(),"You can't able to back. You should logout your Account. Otherwise continue your Search",Toast.LENGTH_LONG).show();
        showExit();
    }

    private void showExit() {


            LayoutInflater layoutInflater = LayoutInflater.from(Dashboard.this);
            View promptView = layoutInflater.inflate(R.layout.exitlogin, null);
            final android.app.AlertDialog alertD = new android.app.AlertDialog.Builder(Dashboard.this).create();
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
                    Dashboard.super.onBackPressed();
                    onRestart();
                    Intent i1 = new Intent(Intent.ACTION_MAIN);
                    i1.setAction(Intent.ACTION_MAIN);
                    i1.addCategory(Intent.CATEGORY_HOME);
                    i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i1);
                    alertD.dismiss();
                    finish();
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
    }


