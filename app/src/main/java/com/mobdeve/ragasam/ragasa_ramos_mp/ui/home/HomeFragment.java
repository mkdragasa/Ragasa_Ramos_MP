package com.mobdeve.ragasam.ragasa_ramos_mp.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.mobdeve.ragasam.ragasa_ramos_mp.R;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageButton ib_call, ib_text;
    private static final int REQUEST_CODE = 1;
    FusedLocationProviderClient fusedLocationProviderClient;
    private Context context;
    private Activity mActivity;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        context = inflater.getContext();
        mActivity = this.getActivity();
        // Initialize views
        initializeViews(root);
        // Set OnClickListeners
        setOnClickListeners();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        return root;
    }


    private void initializeViews(View root) {
        ib_call = root.findViewById(R.id.ib_call);
        ib_text = root.findViewById(R.id.ib_text);
    }

    private void setOnClickListeners() {
        ib_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS();
            }
        });

        ib_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNumber();
            }
        });
    }

    private void sendSMS(){
        //***CHANGE THIS****
        String message = "Test message";
        // test array only - add numbers to test
        String[] numberList = {"09278744265"};

        if(!message.equals("") && numberList.length > 0){
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                try {
                    SmsManager mySmsManager = SmsManager.getDefault();
                    int counter = 0;

                    // loop through the contact list
                    for (counter = 0; counter < numberList.length; counter++){
                        mySmsManager.sendTextMessage(numberList[counter], null, message, null, null);
                    }

                    Toast.makeText(mActivity, "Message sent.", Toast.LENGTH_LONG).show();
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(mActivity, "Failed to send message.", Toast.LENGTH_LONG).show();
                }
            } else {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, REQUEST_CODE);
            }

        } else {
            Toast.makeText(mActivity, "Cannot retrieve contact number.", Toast.LENGTH_LONG).show();
        }

    }

    private void callNumber(){
        String number = "09278744265";

        if(number.trim().length() > 0){
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                try {
                    String dial = "tel:" + number;
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
}