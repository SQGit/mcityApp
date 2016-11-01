package mcity.com.mcity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ANANDH on 22-12-2015.
 */
public class HotelAdapter extends BaseAdapter {


    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    Bitmap b;
    HashMap<String, String> resultp = new HashMap<String, String>();
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    String str_owner_no,str_owner_mail;

    public HotelAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
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
        TextView loc,roomTypeval,address,rent,chennai,mcity,rs,subtype, roomtype, description,rentType,gender,furnishedType,monthrent,bedtv,renttv,subtv,furnishedtv,sendsms,viewcontact;
        TextView make_call, send_mail;
        //ImageView country_img;
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "mont.ttf");


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.search_properties_list_item, parent, false);
        resultp = data.get(position);


        loc = (TextView) itemView.findViewById(R.id.loc);
        address = (TextView) itemView.findViewById(R.id.city);
        rent=(TextView) itemView.findViewById(R.id.rents);
        subtype=(TextView) itemView.findViewById(R.id.subtype);
        chennai=(TextView) itemView.findViewById(R.id.chennai);
        rs=(TextView) itemView.findViewById(R.id.rs);
        mcity = (TextView) itemView.findViewById(R.id.mcity);
        gender =(TextView)itemView.findViewById(R.id.gender);
        //roomtype = (TextView) itemView.findViewById(R.id.bedroom);
        description = (TextView) itemView.findViewById(R.id.description);
        //bedtv = (TextView) itemView.findViewById(R.id.bedroom_tv);
        renttv = (TextView) itemView.findViewById(R.id.rent_tv);
        subtv = (TextView) itemView.findViewById(R.id.subtype_tv);
        furnishedtv = (TextView) itemView.findViewById(R.id.furnishedtype_tv);
        LinearLayout linearLayout = (LinearLayout) itemView.findViewById(R.id.view);
        roomTypeval=(TextView) itemView.findViewById(R.id.roomTypeval);
        make_call = (TextView) itemView.findViewById(R.id.make_call);
        send_mail = (TextView) itemView.findViewById(R.id.send_mail);


        loc.setText(resultp.get(SearchPGFilter.location));
        //address.setText(resultp.get(SearchPGFilter.address));
        rent.setText(resultp.get(SearchPGFilter.monthlyrent));
        //roomtype.setText(resultp.get(SearchPGFilter.roomtype));
        description.setText(resultp.get(SearchPGFilter.description));
        subtype.setText(resultp.get(SearchPGFilter.monthlyrent));
        gender.setText(resultp.get(SearchPGFilter.gender));
        roomTypeval.setText(resultp.get(SearchPGFilter.roomtype));
        str_owner_no = resultp.get(SearchPGFilter.mobileno);
        Log.e("tag", "send_sms" + str_owner_no);
        str_owner_mail=resultp.get(SearchPGFilter.email);
        Log.e("tag", "send_mail" + str_owner_mail);
        String enable_status=resultp.get(SearchPGFilter.phone);
        Log.e("tag","please check"+enable_status);


        final String username=resultp.get(SearchPGFilter.username);
        Log.e("tag","please check"+enable_status);

        //address.setTypeface(tf);
        chennai.setTypeface(tf);
        description.setTypeface(tf);
        //roomtype.setTypeface(tf);
        loc.setTypeface(tf);
        rent.setTypeface(tf);
        rs.setTypeface(tf);
        mcity.setTypeface(tf);
        subtype.setTypeface(tf);
       // bedtv.setTypeface(tf);
        renttv.setTypeface(tf);
        furnishedtv.setTypeface(tf);
        gender.setTypeface(tf);
        subtv.setTypeface(tf);
        roomTypeval.setTypeface(tf);




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
                        phoneIntent.putExtra(Intent.EXTRA_SUBJECT, "Mcity");
                        phoneIntent.setData(Uri.parse("tel:"+str_owner_no));
                        phoneIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

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



}