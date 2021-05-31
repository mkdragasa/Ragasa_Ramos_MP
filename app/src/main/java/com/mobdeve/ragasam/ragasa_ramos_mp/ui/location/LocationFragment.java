package com.mobdeve.ragasam.ragasa_ramos_mp.ui.location;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.mobdeve.ragasam.ragasa_ramos_mp.R;

import java.util.List;
import java.util.Locale;

public class LocationFragment extends Fragment {
    private static final int REQUEST_CODE = 1;
    private LocationViewModel locationModel;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;

    GoogleMap map;
    double currentLat = 0, currentLong = 0;
    private Context context;
    private Activity mActivity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        locationModel =
                new ViewModelProvider(this).get(LocationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_location, container, false);
        context = inflater.getContext();
        mActivity = this.getActivity();

        /*
        final TextView textView = root.findViewById(R.id.text_location);
        locationModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        }); */

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        getLocation();

        return root;
    }

    private void getLocation(){

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationManager locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);

            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                try {
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            Location location = task.getResult();
                            if (location != null){
                                currentLat = location.getLatitude();
                                currentLong = location.getLongitude();

                                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                                    @Override
                                    public void onMapReady(@NonNull GoogleMap googleMap) {
                                        map = googleMap;
                                        LatLng latLng = new LatLng(currentLat, currentLong);
                                        MarkerOptions options = new MarkerOptions().position(latLng).title("You are here");
                                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                                        map.addMarker(options);
                                    }
                                });

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
                                        Geocoder geocoder = new Geocoder(mActivity, Locale.getDefault());
                                        try {
                                            currentLat = location1.getLatitude();
                                            currentLong = location1.getLongitude();

                                            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                                                @Override
                                                public void onMapReady(@NonNull GoogleMap googleMap) {
                                                    map = googleMap;
                                                    LatLng latLng = new LatLng(currentLat, currentLong);
                                                    MarkerOptions options = new MarkerOptions().position(latLng).title("You are here");
                                                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                                                    map.addMarker(options);
                                                }
                                            });
                                            Toast.makeText(mActivity, "Success in requesting location.", Toast.LENGTH_LONG).show();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Toast.makeText(mActivity, "Cannot request location.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                };
                                fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                            }
                        }
                    });


                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(mActivity, "Cannot request permission.", Toast.LENGTH_LONG).show();
                }
                // else of location
            } else {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            // else of permission
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
        }

    }





}