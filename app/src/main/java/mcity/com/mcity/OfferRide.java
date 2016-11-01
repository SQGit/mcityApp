package mcity.com.mcity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
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
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * Created by Admin on 27-09-2016.
 */
public class OfferRide extends Activity {
    String URL = Data_Service.URL_API + "postforride";
    private static int IMG_RESULT = 1;
    private TextView date, go_date, return_date;
    ImageView submit, capture;
    Button add, sub;
    int num = 0;
    int TAKE_PHOTO_CODE = 0;
    CheckBox checkBox, midway_drop, phone_enable, round_trip, luggage;
    private int year;
    private int month;
    private int day;
    Intent intent;
    public ImageView license_image,settings_icon;
    public static int count = 0;
    EditText weight, ticket;
    LinearLayout triplayout, place_layout, date_layout, luggage_layout, backarrow, time_lin, date_lin, submit_linr, settings_linr;
    TextView price_val, val;
    private RadioGroup trip;
    private RadioButton select_trip;
    String str_midway, str_pho_enable, token, uid, merge, lic_activation, file_check, imagepath, round_godate, round_returndate, round_luggage;
    TextView txt_date, txt_timer;
    String str_from, str_to, str_date, str_price, str_time;
    static final int DATE_PICKER_ID = 1111;
    static final int DATE_PICKER_ID2 = 2222;
    static final int DATE_PICKER_ID3 = 3333;
    ProgressBar progressBar;
    Spinner edt_from, edt_to;
    List<String> fromadd;
    List<String> toadd;
    Typeface tf;
    String image_key;
    private static final int REQUEST_CODE = 1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    ArrayList<String> selectedPhotos = new ArrayList<>();
      Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_ride);

        submit = (ImageView) findViewById(R.id.submit);
        date = (TextView) findViewById(R.id.select_date);
        checkBox = (CheckBox) findViewById(R.id.other_det);
        luggage = (CheckBox) findViewById(R.id.luggage);
        midway_drop = (CheckBox) findViewById(R.id.midway_drop);
        phone_enable = (CheckBox) findViewById(R.id.phone_enable);
        round_trip = (CheckBox) findViewById(R.id.round_trip);
        luggage_layout = (LinearLayout) findViewById(R.id.luggage_layout);
        triplayout = (LinearLayout) findViewById(R.id.triplayout);
        submit_linr = (LinearLayout) findViewById(R.id.submit_linr);
        settings_linr = (LinearLayout) findViewById(R.id.settings_linr);
        date_lin = (LinearLayout) findViewById(R.id.date_lin);
        weight = (EditText) findViewById(R.id.weight);
        time_lin = (LinearLayout) findViewById(R.id.time_lin);
        ticket = (EditText) findViewById(R.id.ticket);
        backarrow = (LinearLayout) findViewById(R.id.back_arrow);
        date_layout = (LinearLayout) findViewById(R.id.date_layout);
        edt_from = (Spinner) findViewById(R.id.edt_from);
        edt_to = (Spinner) findViewById(R.id.edt_to);
        go_date = (TextView) findViewById(R.id.go_date);
        txt_timer = (TextView) findViewById(R.id.timer);
        txt_date = (TextView) findViewById(R.id.select_date);
        return_date = (TextView) findViewById(R.id.return_date);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        settings_icon = (ImageView) findViewById(R.id.settings_icon);


        add = (Button) findViewById(R.id.add);
        sub = (Button) findViewById(R.id.sub);
        //price_val=(TextView)findViewById(R.id.price_val);
        // val=(TextView)findViewById(R.id.val);

        FontsManager.initFormAssets(this, "mont.ttf");
        FontsManager.changeFonts(this);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        edit = sharedPreferences.edit();


        triplayout.setVisibility(View.GONE);
        sub.setEnabled(false);
        date_layout.setVisibility(View.GONE);
        luggage_layout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        submit_linr.setVisibility(View.VISIBLE);
        settings_linr.setVisibility(View.GONE);
        // Get current date by calender


        fromadd = new ArrayList<String>();
        fromadd.add("Select Location");
        fromadd.add("Aqualily/BMW");
        fromadd.add("Canopy/Sylvan County");
        fromadd.add("Iris Court");
        fromadd.add("Nova");
        fromadd.add("MRV");
        fromadd.add("Infosys Main Gate");
        fromadd.add("Zero Point");


        toadd = new ArrayList<String>();
        toadd.add("Select Location");
        toadd.add("Tambaram");
        toadd.add("Chengalpattu");
        toadd.add("Tnagar");
        toadd.add("Central Station");
        toadd.add("Chennai Airport");
        toadd.add("Paranur Station");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fromadd);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        edt_from.setAdapter(dataAdapter);


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, toadd);

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        edt_to.setAdapter(dataAdapter1);
        setSpinner1();
        setSpinner2();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sharedPreferences.getString("token", "");
        uid = sharedPreferences.getString("id", "");
        int a = sharedPreferences.getInt("licence_activation", 0);  //get from login class
        //file_check=sharedPreferences.getString("file_generate",""); //get from dashboard
        lic_activation = String.valueOf(a);


        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // Show current date

        date.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(year).append("/").append(month + 1).append("/")
                .append(day).append(""));

        go_date.setText(new StringBuilder()
                .append(year).append("/").append(month + 1).append("/")
                .append(day).append(""));

        // Button listener to show date picker dialog

        time_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);


                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(OfferRide.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override

                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        // txt_timer.setText(new DecimalFormat("##").format(selectedHour+ ":" + selectedMinute));

                        int hourInt = selectedHour;
                        String hourConverted = "" + hourInt;
                        if (hourInt < 10) {
                            hourConverted = "0" + hourConverted;
                        }


                        int minuteInt = selectedMinute;
                        String minuteConverted = "" + minuteInt;
                        if (minuteInt < 10) {
                            minuteConverted = "0" + minuteConverted;
                        }

                        txt_timer.setText(hourConverted + ":" + minuteConverted);
                        Log.e("tag", "dddd" + hourConverted);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }

        });


        settings_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tag", "DDD");


                PopupMenu popup = new PopupMenu(OfferRide.this, settings_icon);
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

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RideSearch.class);
                startActivity(i);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (num == 0) {
                    sub.setEnabled(false);

                } else {
                    sub.setEnabled(true);
                }
                sub.setClickable(true);
                num++;
                weight.setText(Integer.toString(num));
                // val.setText(Integer.toString(num));
            }
        });


        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gg = weight.getText().toString();

                if (gg.equals("0")) {
                    sub.setClickable(false);
                } else {
                    sub.setClickable(true);
                    num--;
                    weight.setText(Integer.toString(num));
                }


            }
        });


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    triplayout.setVisibility(View.VISIBLE);
                    Log.e("tag", "aaa");
                } else {
                    triplayout.setVisibility(View.GONE);

                    Log.e("tag", "bbb");
                    // use the same way here with 'project' and 'ProjectName'
                }
            }
        });


        luggage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    luggage_layout.setVisibility(View.VISIBLE);
                    Log.e("tag", "aaa");
                } else {
                    luggage_layout.setVisibility(View.GONE);

                    Log.e("tag", "bbb");
                    // use the same way here with 'project' and 'ProjectName'
                }
            }
        });


        round_trip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    date_layout.setVisibility(View.VISIBLE);
                    Log.e("tag", "aaa");
                } else {
                    date_layout.setVisibility(View.GONE);

                    Log.e("tag", "bbb");
                    // use the same way here with 'project' and 'ProjectName'
                }
            }
        });

        midway_drop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    str_midway = "Midway Available";
                } else {
                    str_midway = "Midway Unavailable";
                    // use the same way here with 'project' and 'ProjectName'
                }
            }
        });

        phone_enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    str_pho_enable = "enabled";
                } else {
                    str_pho_enable = "disabled";
                    // use the same way here with 'project' and 'ProjectName'
                }
            }
        });
        date_lin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // On button click show datepicker dialog
                showDialog(DATE_PICKER_ID);

            }

        });

        go_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_ID2);
            }

        });


        return_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_ID3);
            }

        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_from = edt_from.getSelectedItem().toString();
                str_to = edt_to.getSelectedItem().toString();
                str_date = txt_date.getText().toString();
                str_time = txt_timer.getText().toString();
                merge = str_date + " T " + str_time;
                str_price = ticket.getText().toString();


                round_godate = go_date.getText().toString();
                round_returndate = return_date.getText().toString();
                round_luggage = weight.getText().toString();

//**********Midway Condition
                if (midway_drop.isChecked()) {
                    str_midway = "Midwaydrop Available";
                } else {
                    str_midway = "Midwaydrop Unavailable";
                }


//**********Phone Condition
                if (phone_enable.isChecked()) {
                    str_pho_enable = "enabled";
                } else {
                    str_pho_enable = "Hidden Contact";
                }


                if (Util.Operations.isOnline(OfferRide.this)) {
                    if (!str_from.isEmpty() && !str_to.isEmpty() && !str_date.isEmpty() && !str_time.isEmpty() && !str_price.isEmpty()) {

                        if (!(sharedPreferences.getString("file_generate","").equals(""))) {
                            Log.e("tag","print1");
                            new PostRideAsync(str_from, str_to, merge, str_price, str_midway, str_pho_enable, round_godate, round_returndate, round_luggage).execute();
                        }
                        else
                        {

                            Log.e("tag","print2");
                            takeLicenseproof();
                        }
                    } else
                    {
                        Toast.makeText(getApplicationContext(), "Invalid Fields..", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connectivity", Toast.LENGTH_LONG).show();
                }
            }


        });

    }

    private void exitIcon() {


        LayoutInflater layoutInflater = LayoutInflater.from(OfferRide.this);
        View promptView = layoutInflater.inflate(R.layout.exitdialog, null);
        final AlertDialog alertD = new AlertDialog.Builder(OfferRide.this).create();
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
                SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(OfferRide.this);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("check","");
                editor.clear();
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

        LayoutInflater layoutInflater = LayoutInflater.from(OfferRide.this);
        View promptView = layoutInflater.inflate(R.layout.aboutus, null);
        final AlertDialog alertD = new AlertDialog.Builder(OfferRide.this).create();
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

    private void takeLicenseproof() {


      dialog = new Dialog(OfferRide.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.driving_license);
        final TextView head1 = (TextView) dialog.findViewById(R.id.head1);
        final TextView takephoto = (TextView) dialog.findViewById(R.id.takephoto);
        //final TextView choose_gallery = (TextView) dialog.findViewById(R.id.choose_gallery);
        license_image = (ImageView) dialog.findViewById(R.id.license_image);
        final ImageView submit = (ImageView) dialog.findViewById(R.id.submit);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "mont.ttf");
        head1.setTypeface(tf);
        //choose_gallery.setTypeface(tf);
        takephoto.setTypeface(tf);

        head1.setText("Please Upload your License\n Proof by Either taking the Photo or \n Selecting from Gallery");


        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
                File newdir = new File(dir);
                newdir.mkdirs();
                count++;
                imagepath = dir + count + ".jpg";
                Log.e("tag", "*********" + imagepath);

                File newfile = new File(imagepath);
                try {
                    newfile.createNewFile();
                } catch (IOException e) {
                }

                Uri outputFileUri = Uri.fromFile(newfile);
                Log.e("tag", "1234" + outputFileUri);
               // license_image.setImageURI(Uri.parse(imagepath));
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);

            }
        });






      /*  choose_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, IMG_RESULT);
            }
        });
*/

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageUpload().execute();


            }
        });
        dialog.show();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("tag_photddo", "worked");
        Log.e("tag","choose"+ data);

        license_image.setImageURI(Uri.parse(imagepath));

        List<String> photos = null;
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && requestCode == TAKE_PHOTO_CODE) {
            Log.e("tag","choose"+ data);
            Log.e("tag_photo", "worked");
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {

                selectedPhotos.addAll(photos);
            }

            Uri uri = Uri.fromFile(new File(selectedPhotos.get(0)));

            Log.e("tag0", selectedPhotos.get(0));
            Log.e("tag1", "" + imagepath);

            license_image.setImageURI(Uri.parse(imagepath));

           /* SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(OfferRide.this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user_profile", selectedPhotos.get(0));
            editor.commit();*/

            //new UploadImageToServer(selectedPhotos.get(0)).execute();



        }
    }



    //************* change option menu typeface settings page.
    private void applyFontToMenuItem(MenuItem mi)
    {
        Typeface font = Typeface.createFromAsset(getAssets(), "mont.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    private void setSpinner2() {

        final CustomAdapter arrayAdapter1 = new CustomAdapter(this, android.R.layout.simple_spinner_item, toadd) {
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
        edt_to.setAdapter(arrayAdapter1);
    }

    private void setSpinner1() {

        final CustomAdapter arrayAdapter = new CustomAdapter(this, android.R.layout.simple_spinner_item, fromadd) {
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
        edt_from.setAdapter(arrayAdapter);
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:


                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month, day);

            case DATE_PICKER_ID2:
                return new DatePickerDialog(this, pickerListener1, year, month, day);


            case DATE_PICKER_ID3:
                return new DatePickerDialog(this, pickerListener3, year, month, day);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            int monthInt = month + 1;
            String monthConverted = "" + monthInt;
            if (monthInt < 10) {
                monthConverted = "0" + monthConverted;
            }


            int dayInt = day;
            String dayConverted = "" + dayInt;
            if (dayInt < 10) {
                dayConverted = "0" + dayConverted;
            }
            Log.e("tag", "999" + dayConverted);


            // Show selected date
            date.setText(new StringBuilder().append(year)
                    .append("/").append(monthConverted).append("/").append(dayConverted)
                    .append(""));


        }
    };


    private DatePickerDialog.OnDateSetListener pickerListener1 = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            int monthInt = month + 1;
            String monthConverted = "" + monthInt;
            if (monthInt < 10) {
                monthConverted = "0" + monthConverted;
            }


            int dayInt = day;
            String dayConverted = "" + dayInt;
            if (dayInt < 10) {
                dayConverted = "0" + dayConverted;
            }
            Log.e("tag", "999" + dayConverted);

            // Show selected date
            go_date.setText(new StringBuilder().append(year)
                    .append("/").append(monthConverted).append("/").append(dayConverted)
                    .append(""));
        }
    };

    private DatePickerDialog.OnDateSetListener pickerListener3 = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;


            int monthInt = month + 1;
            String monthConverted = "" + monthInt;
            if (monthInt < 10) {
                monthConverted = "0" + monthConverted;
            }


            int dayInt = day;
            String dayConverted = "" + dayInt;
            if (dayInt < 10) {
                dayConverted = "0" + dayConverted;
            }
            Log.e("tag", "999" + dayConverted);
            // Show selected date
            return_date.setText(new StringBuilder().append(year)
                    .append("/").append(monthConverted).append("/").append(dayConverted)
                    .append(""));

        }
    };


    private class ImageUpload extends AsyncTask<String, String, String> {
        @Override


        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
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
                Log.e("tag", "id..........." + uid);
                postMethod.addHeader("id", uid);
                //postMethod.addHeader("Content-Type", "multipart-form-data");

                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                File sourceFile = new File(imagepath);
                cbFile = new FileBody(sourceFile, "image/jpeg");
                entity.addPart("file", cbFile);

                Log.e("tag", "content " + cbFile);
                postMethod.setEntity(entity);

                HttpResponse response = client.execute(postMethod);
                HttpEntity r_entity = response.getEntity();
                int statusCode = response.getStatusLine().getStatusCode();
                Log.e("tag", "res " + response.getStatusLine().toString());
                if (statusCode == 200) {
                    responseString = EntityUtils.toString(r_entity);

                    JSONObject result1 = new JSONObject(responseString);
                    String status = result1.getString("status");
                    Log.e("tag", "status..........." + status);

                    if (status.equals("true")) {
                        Log.e("tag", "Success...........");
                    }
                } else {
                    responseString = "Error occurred! Http Status Code: " + statusCode;
                    Log.e("tag", "failure...........");
                }

            } catch (Exception e) {
                responseString = e.toString();
            }
            return responseString;
        }


        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----result---->" + jsonStr);
            super.onPostExecute(jsonStr);
            progressBar.setVisibility(View.GONE);
            dialog.dismiss();
            Intent driving = new Intent(getApplicationContext(), OfferRide.class);
            startActivity(driving);
            finish();

            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");
                String licence_key = jo.getString("licence");
                Log.e("tag", "<-----Status----->" + status);
                Log.e("tag", "<-----msg----->" + msg);

                if (status.equals("true")) {


                    edit.putString("file_generate", licence_key);
                    //edit.putString("i_key","stop");
                    edit.commit();

                    new PostRideAsync(str_from, str_to, merge, str_price, str_midway, str_pho_enable, round_godate, round_returndate, round_luggage).execute();

                    // location_et, lanmark_et, address_et, rentamount_et, depositamount_et, description_et;

                    Log.e("tag", "s...........");


                } else {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    Log.e("tag", "no...........");

                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    private class PostRideAsync extends AsyncTask<String, String, String> {

        String str_from, str_to, merge, str_price, str_midway, str_pho_enable, round_godate, round_returndate, round_luggage;

        public PostRideAsync(String str_from, String str_to, String merge, String str_price, String str_midway, String str_pho_enable, String round_godate, String round_returndate, String round_luggage) {

            this.str_from = str_from;
            this.str_to = str_to;
            this.merge = merge;
            this.str_price = str_price;
            this.str_midway = str_midway;
            this.str_pho_enable = str_pho_enable;
            this.round_godate = round_godate;
            this.round_returndate = round_returndate;
            this.round_luggage = round_luggage;

        }

        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);

        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            String id = "";
            try {
                Log.e("tag", "id" + uid);
                Log.e("tag", "token" + token);
                //location,landmark,address,roomtype,monthlyrent,gender,description
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("from", str_from);
                jsonObject.accumulate("to", str_to);
                jsonObject.accumulate("date", merge);
                jsonObject.accumulate("price", str_price);
                jsonObject.accumulate("midwaydrop", str_midway);
                jsonObject.accumulate("phone", str_pho_enable);


                jsonObject.accumulate("godate", round_godate);
                jsonObject.accumulate("returndate", round_returndate);
                jsonObject.accumulate("extraluggage", round_luggage);


                Log.e("tag", "checking 1" + str_from);
                Log.e("tag", "checking 2" + str_to);
                Log.e("tag", "checking 3" + merge);
                Log.e("tag", "checking 4" + str_price);
                Log.e("tag", "checking 5" + str_midway);
                Log.e("tag", "checking 6" + str_pho_enable);



                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(URL, json, uid, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return jsonStr;
        }


        @Override
        protected void onPostExecute(String jsonstr) {
            Log.e("tag", "<-----result---->" + jsonstr);
            super.onPostExecute(jsonstr);
            progressBar.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);

            try {
                JSONObject jo = new JSONObject(jsonstr);
                String status = jo.getString("status");
                String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);
                if (status.equals("true")) {

                    Toast.makeText(getApplicationContext(), "Your post has been added successfully", Toast.LENGTH_SHORT).show();
                    edt_from.clearFocus();
                    edt_to.clearFocus();
                    txt_date.setText("");
                    txt_timer.setText("");
                    ticket.setText("");
                    midway_drop.setChecked(false);
                }

               /* else if(status.equals("false")) {
                    Toast.makeText(getApplicationContext(), "Wait for your Licence Verification", Toast.LENGTH_LONG).show();

                }*/

                else{

                    Toast.makeText(getApplicationContext(), "Some error occured please check", Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(OfferRide.this, RideSearch.class);
        startActivity(i);
        finish();
        // super.onBackPressed();
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







