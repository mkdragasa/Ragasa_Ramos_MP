package com.mobdeve.ragasam.ragasa_ramos_mp.ui.contacts;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.mobdeve.ragasam.ragasa_ramos_mp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsViewHolder> {

    private ArrayList<Contact> contactsArrayList;
    Context context;
    Activity activity;

    public ContactsAdapter(ArrayList<Contact> data, Context context, Activity activity){
        this.contactsArrayList = data;
        this.context = context;
        this.activity = activity;

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

        String message = this.contactsArrayList.get(position).getMessage();
        String number = this.contactsArrayList.get(position).getContactNo();

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
                    if(ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                        try {
                            SmsManager mySmsManager = SmsManager.getDefault();

                                mySmsManager.sendTextMessage(number, null, message, null, null);

                            Toast.makeText(activity, "Message sent.", Toast.LENGTH_LONG).show();
                        } catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(activity, "Failed to send message.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                       ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS},1);
                    }

                } else {
                    Toast.makeText(activity, "Cannot retrieve contact number.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.contactsArrayList.size();
    }

    /*
    private String getLocation(){

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        double currentLat = 0, currentLong = 0;
        
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                try {
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            Log.d("App", "LOCATION: "+location);
                            if (location != null){
                                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    String addressLine = addresses.get(0).getAddressLine(0);

                                    Toast.makeText(activity, "Success in getting location.", Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(activity, "Cannot get location.", Toast.LENGTH_LONG).show();
                                }

                            }
                            else{
                                //Log.d("App", "Requesting location..");
                                LocationRequest mLocationRequest = LocationRequest.create();
                                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                mLocationRequest.setInterval(10000);
                                mLocationRequest.setFastestInterval(1000);
                                mLocationRequest.setNumUpdates(1);
                                LocationCallback mLocationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        //Log.d("App", "CALLBACK Requesting location..");
                                        Location location1 = locationResult.getLastLocation();
                                        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
                                        try {
                                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                            address = addresses.get(0).getAddressLine(0);

                                            Toast.makeText(activity, "Success in getting location.", Toast.LENGTH_LONG).show();
                                        } catch (Exception e) {
                                            e.printStackTrace();
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
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

       }
        */


}
