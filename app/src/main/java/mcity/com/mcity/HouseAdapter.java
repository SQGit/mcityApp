package mcity.com.mcity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ANANDH on 22-12-2015.
 */
public class HouseAdapter extends BaseAdapter {


    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    Bitmap b;
    HashMap<String, String> resultp = new HashMap<String, String>();
    ImageView loadimage;
    String img1, img2, img3, img4;
    ArrayList<String> loading ;
    String str_cusno, str_owner_no,str_owner_mail;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    public HouseAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView description, bedroom, rentType, furnishedType, address, subtype, bedtv, renttv, subtv, furnishedtv, make_call,
                send_mail, bedtypes, loc, rs, mcity, city;
        SharedPreferences sharedPreferences;
        SmsManager smsManager = SmsManager.getDefault();
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "mont.ttf");
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.search_properties_list_items, parent, false);
        resultp = data.get(position);
        String str_sms;

        loading = new ArrayList<>();

        img1 = resultp.get("path0");
        //Log.e("tag", "PATH1" + img1);
        img2 = resultp.get("path1");
       // Log.e("tag", "PATH2" + img2);
        img3 = resultp.get("path2");
       // Log.e("tag", "PATH3" + img3);
        img4 = resultp.get("path3");
       // Log.e("tag", "PATH4" + img4);


        loading.add(img1);
        loading.add(img2);
        loading.add(img3);
        loading.add(img4);


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
        description = (TextView) itemView.findViewById(R.id.description);
        bedtv = (TextView) itemView.findViewById(R.id.bedroom_tv);
        renttv = (TextView) itemView.findViewById(R.id.rent_tv);
        subtv = (TextView) itemView.findViewById(R.id.subtype_tv);
        furnishedtv = (TextView) itemView.findViewById(R.id.furnishedtype_tv);
        make_call = (TextView) itemView.findViewById(R.id.make_call);
        send_mail = (TextView) itemView.findViewById(R.id.send_mail);
        LinearLayout linearLayout = (LinearLayout) itemView.findViewById(R.id.view);
        final ImageView loadimage = (ImageView) itemView.findViewById(R.id.loadimg);


        address.setText(resultp.get(SearchHouseFilter.monthlyrent));
        bedtypes.setText(resultp.get(SearchHouseFilter.bedroom));
        loc.setText(resultp.get(SearchHouseFilter.location));
        bedroom.setText(resultp.get(SearchHouseFilter.bedroom));
        rentType.setText(resultp.get(SearchHouseFilter.renttype));
        subtype.setText(resultp.get(SearchHouseFilter.furnishedtype));
        furnishedType.setText(resultp.get(SearchHouseFilter.residential));
        description.setText(resultp.get(SearchHouseFilter.description));
        str_owner_no = resultp.get(SearchHouseFilter.mobileno);
        Log.e("tag", "send_sms" + str_owner_no);
        str_owner_mail=resultp.get(SearchHouseFilter.email);
        Log.e("tag", "send_mail" + str_owner_mail);

        String enable_status=resultp.get(SearchHouseFilter.phone);
        Log.e("tag","please check"+enable_status);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        str_cusno = sharedPreferences.getString("mobileno", "");

        final String username=resultp.get(SearchHouseFilter.username);


        Picasso.with(context)
                .load(resultp.get("path0"))
                .into(loadimage);

       // Log.e("tag", "REPORT 1" + loading.get(0));


        address.setTypeface(tf);
        rentType.setTypeface(tf);
        description.setTypeface(tf);
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
        make_call.setTypeface(tf);
        send_mail.setTypeface(tf);
        subtv.setTypeface(tf);


        if(enable_status.equals("enabled"))
        {
            make_call.setText(resultp.get("mobileno"));

            make_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    } else {

                        Log.e("tag","we"+str_owner_no);
                        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                        phoneIntent.setData(Uri.parse("tel:"+str_owner_no));

                        try {
                            v.getContext().startActivity(phoneIntent);
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(v.getContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            });


        }else
        {
            make_call.setText("hidden");
            make_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"You cant able to make call. Please contact through mail...",Toast.LENGTH_SHORT).show();
                }
            });
        }
        Log.e("tag","path_: "+resultp.get("path0"));

        send_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", str_owner_mail, null));

                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "MCity");
                emailIntent.putExtra(Intent.EXTRA_TEXT   , "Hi  "+username+"\n"+ "   Iam interested in your Property. Please contact me.");
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //emailIntent.putExtra(Intent.EXTRA_TEXT, storeval);
                context.startActivity(emailIntent);

            }
        });



        loadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tag", ".....");


                loading = new ArrayList<>();

                resultp = data.get(position);




                for(int i =0;i<4;i++){
                    if(resultp.get("path"+i)!= null){
                        loading.add(resultp.get("path"+i));
                    }
                }

                if (loading.size() > 0) {



                LayoutInflater layoutInflater = LayoutInflater.from(v.getRootView().getContext());
                View promptView = layoutInflater.inflate(R.layout.zoom_layout1, null);

                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                alertbox.setCancelable(true);

                ViewPager mViewPager;
                CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(v.getRootView().getContext(), loading);
                mViewPager = (ViewPager) promptView.findViewById(R.id.pager);
                mViewPager.setAdapter(mCustomPagerAdapter);
                mCustomPagerAdapter.notifyDataSetChanged();
                alertbox.setView(promptView);
                alertbox.show();
            }
                else{

                }
            }
        });

        if (position % 2 == 0) {
            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.bg1));

        } else {
            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.bg2));
        }

        return itemView;
    }


    }

