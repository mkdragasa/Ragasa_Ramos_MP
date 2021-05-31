package com.mobdeve.ragasam.ragasa_ramos_mp.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.mobdeve.ragasam.ragasa_ramos_mp.Authority;
import com.mobdeve.ragasam.ragasa_ramos_mp.MyDatabaseHelper;
import com.mobdeve.ragasam.ragasa_ramos_mp.R;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mobdeve.ragasam.ragasa_ramos_mp.SafetyGuidelines;
import com.mobdeve.ragasam.ragasa_ramos_mp.ui.contacts.Contact;
import com.mobdeve.ragasam.ragasa_ramos_mp.ui.contacts.Contacts;
import com.mobdeve.ragasam.ragasa_ramos_mp.ui.contacts.ContactsFragment;
import com.mobdeve.ragasam.ragasa_ramos_mp.ui.emergencyServices.EmergencyServicesFragment;
import com.mobdeve.ragasam.ragasa_ramos_mp.ui.guidelines.GuidelinesFragment;
import com.mobdeve.ragasam.ragasa_ramos_mp.ui.location.LocationFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private static final int REQUEST_CODE = 1;
    private final String EMERGENCY_HOTLINE = "111"; // change to 911

    private MyDatabaseHelper myDB;
    private HomeViewModel homeViewModel;
    private ImageButton ib_call, ib_text;
    private CardView contacts_cv, emergencyServices_cv,guidelines_cv, location_cv;
    private FusedLocationProviderClient fusedLocationProviderClient;
    Context context;
    private Activity mActivity;
    private String emergencyNumber;
    private ArrayList<Contact> contactsArrayList;
    private static String addressLocation;
    private static int sentCount;



    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        context = inflater.getContext();
        mActivity = this.getActivity();
        myDB = MyDatabaseHelper.newInstance(mActivity);

        // Initialize views
        initializeViews(root);

        storeData();
        // Set OnClickListeners
        setOnClickListeners();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        return root;
    }


    private void initializeViews(View root) {
        ib_call = root.findViewById(R.id.ib_call);
        ib_text = root.findViewById(R.id.ib_text);
        contacts_cv = root.findViewById(R.id.cv_Contacts);
        emergencyServices_cv = root.findViewById(R.id.cv_EmergencyServices);
        guidelines_cv = root.findViewById(R.id.cv_SafetyGuidelines);
        location_cv = root.findViewById(R.id.cv_Location);
    }

    private void storeData(){
        Cursor cursor = myDB.readAllData();
        emergencyNumber = EMERGENCY_HOTLINE;

        while (cursor.moveToNext()) {
            if (cursor.getString(3).equals("1"))
                emergencyNumber = cursor.getString(2);
        }

        contactsArrayList = new ArrayList<>();
        cursor = myDB.readAllContacts();
        boolean shareLocation;
        while (cursor.moveToNext()){
            Log.d("SafeApp", "in HOME FRAGMENT db: "+ cursor.getString(1));
            shareLocation = false;
            if(cursor.getString(4).equals("1")){
                shareLocation = true;
            }

            Contact newContact = new Contact(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), shareLocation);
            contactsArrayList.add(newContact);
        }

    }

    private void setOnClickListeners(){
        ib_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMessages();

            }
        });

        ib_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNumber();
            }
        });

        contacts_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.nav_contacts);

            }
        });

        emergencyServices_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.nav_emergency_services);
            }
        });

        guidelines_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.safetyGuidelines);
            }
        });

        location_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.nav_location);
            }
        });
    }



    private int sendSMS(String message, String number){
        int count = 0;

        if(!message.equals("") && number.length() > 0) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                try {
                    SmsManager mySmsManager = SmsManager.getDefault();
                    //mySmsManager.sendTextMessage(number, null, message, null, null);
                    Log.d("SafeApp","message is in HOME FRAGMENT no location "+message);
                    count = 1;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, REQUEST_CODE);
                sendSMS(message, number);
            }

        }

        return count;

    }

    private void callNumber(){

        if(emergencyNumber.trim().length() > 0){
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                try {
                    String dial = "tel:" + emergencyNumber;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(dial));
                    startActivity(intent);
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(mActivity, "Failed to make phone call.", Toast.LENGTH_LONG).show();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);
            }

        } else {
            Toast.makeText(mActivity, "Cannot retrieve contact number.", Toast.LENGTH_LONG).show();
        }
    }

    private void sendMessages(){
        sentCount = 0;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        addressLocation = "";

        // check permissions
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            LocationManager locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);

            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                try {
                    // Get Location of User
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location != null){
                                try {
                                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    addressLocation = addresses.get(0).getAddressLine(0);

                                    // Send SMS to User
                                    try {
                                        SmsManager mySmsManager = SmsManager.getDefault();
                                        String newMessage, message, number;
                                        boolean shareLocation = false;
                                        int i = 0;

                                        for (i = 0; i < contactsArrayList.size(); i++){
                                            message = contactsArrayList.get(i).getMessage();
                                            number = contactsArrayList.get(i).getContactNo();
                                            shareLocation = contactsArrayList.get(i).getShareLocation();
                                            if(!message.equals("") && number.length() > 0){
                                                if(shareLocation){
                                                    newMessage = message  + "\n" + "Location: " +addressLocation;
                                                    mySmsManager.sendTextMessage(number, null, newMessage, null, null);
                                                } else {
                                                    mySmsManager.sendTextMessage(number, null, message, null, null);
                                                }
                                                sentCount++;
                                            }
                                        }

                                        Toast.makeText(mActivity, "Sent "+sentCount+" messages.", Toast.LENGTH_LONG).show();

                                    } catch (Exception e){
                                        e.printStackTrace();
                                    }

                                } catch (Exception ex) {
                                    ex.printStackTrace();
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
                                        try {
                                            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                                            List<Address> addresses = geocoder.getFromLocation(location1.getLatitude(), location1.getLongitude(), 1);
                                            addressLocation = addresses.get(0).getAddressLine(0);
                                            try {
                                                SmsManager mySmsManager = SmsManager.getDefault();
                                                String newMessage, message, number;
                                                boolean shareLocation = false;
                                                int i = 0;

                                                for (i = 0; i < contactsArrayList.size(); i++){
                                                    message = contactsArrayList.get(i).getMessage();
                                                    number = contactsArrayList.get(i).getContactNo();
                                                    shareLocation = contactsArrayList.get(i).getShareLocation();
                                                    if(!message.equals("") && number.length() > 0){
                                                        if(shareLocation){
                                                            newMessage = message  + "\n" + "Location: " +addressLocation;
                                                            mySmsManager.sendTextMessage(number, null, newMessage, null, null);
                                                        } else {
                                                            mySmsManager.sendTextMessage(number, null, message, null, null);
                                                        }
                                                        sentCount++;
                                                    }
                                                }

                                                Toast.makeText(mActivity, "Sent "+sentCount+" messages.", Toast.LENGTH_LONG).show();

                                            } catch (Exception e){
                                                e.printStackTrace();
                                            }

                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                };
                                fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                            }
                        }
                    });


                } catch (Exception e){
                    e.printStackTrace();
                }
                // else of location
            } else {
                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            // else of permission
        } else {
            ActivityCompat.requestPermissions(mActivity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,  Manifest.permission.SEND_SMS}, REQUEST_CODE);
        }
    }

}