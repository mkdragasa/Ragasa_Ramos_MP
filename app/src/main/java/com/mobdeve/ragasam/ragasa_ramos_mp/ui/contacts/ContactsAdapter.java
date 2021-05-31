package com.mobdeve.ragasam.ragasa_ramos_mp.ui.contacts;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mobdeve.ragasam.ragasa_ramos_mp.MyDatabaseHelper;
import com.mobdeve.ragasam.ragasa_ramos_mp.NavigationBar;
import com.mobdeve.ragasam.ragasa_ramos_mp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsViewHolder> implements AddContactsDialog.AddContactsDialogListener {

    private final int REQUEST_CODE = 1;
    private FragmentManager fragmentManager;
    private ArrayList<Contact> contactsArrayList;
    private Context context;
    private Activity activity;
    private static String addressLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String updateName, updateNumber, updateMessage, updateID;
    private boolean updateLocation;
    private MyDatabaseHelper myDB;


    public ContactsAdapter(ArrayList<Contact> data, Context context, Activity activity, FragmentManager fragmentManager){
        this.contactsArrayList = data;
        this.context = context;
        this.activity = activity;
        this.fragmentManager = fragmentManager;
        myDB = MyDatabaseHelper.newInstance(activity);

    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_layout, parent,false);
        ContactsViewHolder viewHolder = new ContactsViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        holder.setName(this.contactsArrayList.get(position).getContactName());
        holder.setNumber(this.contactsArrayList.get(position).getContactNo());
        holder.setMessage(this.contactsArrayList.get(position).getMessage());

        String _id = this.contactsArrayList.get(position).getContactID();
        String name = this.contactsArrayList.get(position).getContactName();
        String message = this.contactsArrayList.get(position).getMessage();
        String number = this.contactsArrayList.get(position).getContactNo();
        boolean shareLocation = this.contactsArrayList.get(position).getShareLocation();

        // call contact
        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number.trim().length() > 0) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        try {
                            String dial = "tel:" + number;
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse(dial));
                            context.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(activity, "Failed to make phone call.", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }

                } else if(number.trim().length() == 0){
                    Toast.makeText(activity, "No contact number added.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity, "Cannot retrieve contact number.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // message contact
        holder.textBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!message.equals("") && number.length() > 0){
                    if(shareLocation){
                        sendMessageLocation(message, number);
                    } else {
                        sendSMS(message, number);
                    }

                } else {
                    Toast.makeText(activity, "Cannot retrieve contact number.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // edit contact
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("name", name);
                args.putString("number", number);
                args.putString("message", message);
                args.putBoolean("isShare", shareLocation);
                args.putString("ID", _id);

                AddContactsDialog addDialog = new AddContactsDialog();
                addDialog.setArguments(args);
                addDialog.setListener(ContactsAdapter.this::applyTexts);
                addDialog.show(fragmentManager,"Edit Contact");

            }
        });


    }

    @Override
    public int getItemCount() {
        return this.contactsArrayList.size();
    }


    private void sendMessageLocation(String textMessage, String number){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        addressLocation = "";

        // check permissions
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){

            LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                try {
                    // Get Location of User
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location != null){
                                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    addressLocation = addresses.get(0).getAddressLine(0);

                                    // Send SMS to User
                                    try {
                                        SmsManager mySmsManager = SmsManager.getDefault();
                                        String newMessage;

                                        newMessage = textMessage  + "\n" + "Location: " +addressLocation;
                                        mySmsManager.sendTextMessage(number, null, newMessage, null, null);

                                        Toast.makeText(activity, "Message sent.", Toast.LENGTH_LONG).show();
                                    } catch (Exception e){
                                        e.printStackTrace();
                                        Toast.makeText(activity, "Failed to send message.", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    Toast.makeText(activity, "Cannot get location.", Toast.LENGTH_LONG).show();
                                }

                            }
                            else{
                                LocationRequest mLocationRequest = LocationRequest.create();
                                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                mLocationRequest.setInterval(10000);
                                mLocationRequest.setFastestInterval(1000);
                                mLocationRequest.setNumUpdates(1);
                                LocationCallback mLocationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        Location location1 = locationResult.getLastLocation();
                                        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
                                        try {
                                            List<Address> addresses2 = geocoder.getFromLocation(location1.getLatitude(), location1.getLongitude(), 1);
                                            addressLocation = addresses2.get(0).getAddressLine(0);

                                            Toast.makeText(activity, "Success in getting location.", Toast.LENGTH_LONG).show();
                                            try {
                                                SmsManager mySmsManager = SmsManager.getDefault();
                                                String newMessage;

                                                newMessage = textMessage  + "\n" + "Location: " +addressLocation;
                                                mySmsManager.sendTextMessage(number, null, newMessage, null, null);

                                                Toast.makeText(activity, "Message sent.", Toast.LENGTH_LONG).show();
                                            } catch (Exception e){
                                                e.printStackTrace();
                                                Toast.makeText(activity, "Failed to send message.", Toast.LENGTH_LONG).show();
                                            }

                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                            Toast.makeText(activity, "Cannot get location.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                };
                                fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                            }
                        }
                    });


                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(activity, "Cannot request permission.", Toast.LENGTH_LONG).show();
                }
                // else of location
            } else {
                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            // else of permission
        } else {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,  Manifest.permission.SEND_SMS}, REQUEST_CODE);
            sendMessageLocation(textMessage, number);
        }
    }

       private void sendSMS(String textMessage, String number){
           if(ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
               try {
                   SmsManager mySmsManager = SmsManager.getDefault();
                   mySmsManager.sendTextMessage(number, null, textMessage, null, null);
                   Toast.makeText(activity, "Message sent.", Toast.LENGTH_LONG).show();
               } catch (Exception e){
                   e.printStackTrace();
                   Toast.makeText(activity, "Failed to send message.", Toast.LENGTH_LONG).show();
               }
           } else {
               ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS},1);
               sendSMS(textMessage, number);
           }
       }


    @Override
    public void applyTexts(String name, String number, String message, boolean shareLocation, String id) {
       updateName = name;
       updateNumber = number;
       updateMessage = message;
       updateLocation = shareLocation;
       updateID = id;

       myDB.updateContact(id, name, number, message, shareLocation);
       for(int i = 0; i < contactsArrayList.size(); i++){
           if(contactsArrayList.get(i).getContactID().equals(name)){
               contactsArrayList.get(i).updateContact(name, number, message, shareLocation);
           }
       }

        Log.d("SafeApp","CHANGED");

    }



}
