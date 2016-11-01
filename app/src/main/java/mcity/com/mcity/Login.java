package mcity.com.mcity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sloop.fonts.FontsManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ramya on 03-09-2016.
 */

public class Login extends AppCompatActivity {
    Typeface tf;
    ImageView submit;
    LinearLayout register,hide_pwd;
    EditText email, pwd;
    String mobileno, password,emailValue;
    TextView reg,otp;
    String URL = Data_Service.URL_API + "logout";
    String token, uid,get_email;
    public static String URL_REGISTER = Data_Service.URL_API+"login";
    public static String URL_OTP = Data_Service.URL+"otpgenerate";
    ProgressBar progressBar;
    SharedPreferences s_pref;
    String check,signupstatus;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        FontsManager.initFormAssets(this, "mont.ttf");
        FontsManager.changeFonts(this);

        s_pref = PreferenceManager.getDefaultSharedPreferences(Login.this);
        check = s_pref.getString("check", "");
        Log.e("tag","lllll"+check);

        //pwd.setVisibility(View.GONE);

        register=(LinearLayout) findViewById(R.id.registerlv);
        email=(EditText) findViewById(R.id.email);
        pwd=(EditText) findViewById(R.id.pwd_et);
        reg=(TextView) findViewById(R.id.text);
        otp=(TextView)findViewById(R.id.otp);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        hide_pwd=(LinearLayout)findViewById(R.id.hide_pwd);
        progressBar.setVisibility(View.GONE);

        String textsignup = "<font color=#000000>Dont Have an Account?</font><font color=#E51C39> Register!</font>";
        reg.setText(Html.fromHtml(textsignup));

        //get_email=email.getText().toString();

        submit=(ImageView) findViewById(R.id.submittv);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sharedPreferences.getString("token", "");
        uid = sharedPreferences.getString("id", "");
        signupstatus=sharedPreferences.getString("signup", "");

        Log.e("tag","status"+signupstatus);



        hide_pwd.setVisibility(View.VISIBLE);

        if(signupstatus.equals("true"))
        {
            emailValue = sharedPreferences.getString("email", "");

            email.setText(emailValue);
            pwd.setEnabled(true);
            submit.setVisibility(View.VISIBLE);
            otp.setVisibility(View.VISIBLE);
            otp.setFocusableInTouchMode(true);
            otp.setFocusable(true);
            otp.requestFocus();
            SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor edit = s_pref.edit();
            edit.putString("signup", "false");
            edit.commit();


        }





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                get_email = email.getText().toString();
                Log.e("tag","un"+get_email);
                password = pwd.getText().toString();
                Log.e("tag","pwd"+password);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("check","login_success");
                editor.putString("mobileno",mobileno);
                editor.commit();

                if (Util.Operations.isOnline(Login.this)) {

                    if (!get_email.isEmpty() && !password.isEmpty()) {




                            new MyActivityAsync(get_email, password).execute();

                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Fields..", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connectivity", Toast.LENGTH_SHORT).show();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
                finish();

            }
        });



        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                get_email = email.getText().toString();
                Log.e("tag","##"+get_email);

                if(email.length()>0)
                {
                    new Otp().execute();
                }
                else
                {
                   Toast.makeText(Login.this,"Invalid Email",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    class MyActivityAsync extends AsyncTask<String, Void, String> {

        String  email, password;

        public MyActivityAsync(String email, String password) {
            String json = "", jsonStr = "";
            this.email = email;
            this.password = password;

        }

        protected void onPreExecute()
        {
            progressBar.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);

            super.onPreExecute();

        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("email", email);
                jsonObject.accumulate("password", password);
                json = jsonObject.toString();

                return jsonStr = HttpUtils.makeRequest(URL_REGISTER, json);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----result---->" + jsonStr);
            progressBar.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);
            super.onPostExecute(jsonStr);
            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");
                JSONArray ja=jo.getJSONArray("licence");





                    if(ja.length()>0){

                        for(int i =0;i<ja.length();i++){

                            JSONObject img_obj =ja.getJSONObject(i);

                           String pathnew = "http://104.197.7.143:3000/licence/" + img_obj.getString("filename");

                            Log.e("tag", "data: " + pathnew);

                            s_pref = PreferenceManager.getDefaultSharedPreferences(Login.this);
                            editor = s_pref.edit();
                            editor.putString("file_generate", pathnew);
                            //edit.putString("i_key","stop");
                            editor.commit();


                        }
                    }


                Log.e("tag", "<-----############----->" + ja.length());
                Log.d("tag", "<-----Status----->" + status);
                if (status.equals("true"))
                {

                    String id=jo.getString("id");
                    String token=jo.getString("token");
                    int s=ja.length();


                    SharedPreferences s_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor edit = s_pref.edit();
                    edit.putString("id", id);
                    edit.putString("token", token);
                    edit.putInt("licence_activation",s);
                    edit.putString("login_status","true");
                    edit.commit();
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),msg+"\n      Please generate OTP",Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }





    private class Otp extends AsyncTask<String, String, String> {
        @Override


        protected void onPreExecute()
        {
            progressBar.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);

            super.onPreExecute();

        }


        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("email", get_email);
                json = jsonObject.toString();

                return jsonStr = HttpUtils.makeRequest(URL_OTP, json);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return jsonStr;

        }


        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----result---->" + jsonStr);
            progressBar.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);
            super.onPostExecute(jsonStr);

            pwd.setEnabled(true);

           // submit.setVisibility(View.VISIBLE);

            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");



                Log.e("tag", "<-----Status----->" + status);
                Log.e("tag", "<-----msg----->" + msg);

                if (status.equals("true"))
                {

                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    otp.setVisibility(View.INVISIBLE);

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
    public void onBackPressed() {
finish();
        //showExit();

        // super.onBackPressed();
    }



/*
    private void showExit() {

        LayoutInflater layoutInflater = LayoutInflater.from(Login.this);
        View promptView = layoutInflater.inflate(R.layout.exitlogin, null);
        final android.app.AlertDialog alertD = new android.app.AlertDialog.Builder(Login.this).create();
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
                Intent i1 = new Intent(Intent.ACTION_MAIN);
                i1.setAction(Intent.ACTION_MAIN);
                i1.addCategory(Intent.CATEGORY_HOME);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i1);
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

    }*/
}
