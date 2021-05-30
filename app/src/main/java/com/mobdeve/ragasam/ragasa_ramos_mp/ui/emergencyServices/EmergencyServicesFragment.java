package com.mobdeve.ragasam.ragasa_ramos_mp.ui.emergencyServices;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.ragasam.ragasa_ramos_mp.Authority;
import com.mobdeve.ragasam.ragasa_ramos_mp.EmergencyServicesSettings;
import com.mobdeve.ragasam.ragasa_ramos_mp.MyDatabaseHelper;
import com.mobdeve.ragasam.ragasa_ramos_mp.R;
import com.mobdeve.ragasam.ragasa_ramos_mp.SafetyGuidelines;

import java.util.ArrayList;

public class EmergencyServicesFragment extends Fragment {
    MyDatabaseHelper myDB;

    Activity mActivity;
    private Context context;
    ImageButton fireBtn, hospitalBtn, policeBtn;
    Button settingsBtn;

    ArrayList<Authority> authorityList;
    private static final int REQUEST_CODE = 1;
    private final String POLICE_KEY = "Police";
    private final String FIRE_KEY = "Fire";
    private final String HOSPITAL_KEY = "Hospital";
    private final int CHECK_KEY = -1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        context = inflater.getContext();
        authorityList = new ArrayList<>();

        View root = inflater.inflate(R.layout.fragment_emergency_services, container, false);
        myDB = MyDatabaseHelper.newInstance(mActivity);

        return root;
    }

    private void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        boolean isDefault;
        while (cursor.moveToNext()){
            isDefault = false;
            if(cursor.getString(3).equals("1")){
                isDefault = true;
            }
            Authority newAuthority = new Authority(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2), isDefault);
            authorityList.add(newAuthority);
        }

    }

    public void onStart() {
        super.onStart();
        storeDataInArrays();
        fireBtn =  (ImageButton) mActivity.findViewById(R.id.ib_fire);
        fireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getPosition(FIRE_KEY);
                if(pos == CHECK_KEY){
                    Toast.makeText(context,"No contact number added.", Toast.LENGTH_SHORT).show();
                } else {
                    String fireNumber = authorityList.get(pos).getContactNo();
                    callNumber(fireNumber);
                }

            }
        });

        hospitalBtn =  (ImageButton) mActivity.findViewById(R.id.ib_hospital);
        hospitalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getPosition(HOSPITAL_KEY);
                if(pos == CHECK_KEY){
                    Toast.makeText(context,"No contact number added.", Toast.LENGTH_SHORT).show();
                } else {
                    String hospitalNumber = authorityList.get(pos).getContactNo();
                    callNumber(hospitalNumber);
                }

            }
        });

        policeBtn =  (ImageButton) mActivity.findViewById(R.id.ib_police);
        policeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getPosition(POLICE_KEY);
                if(pos == CHECK_KEY){
                    Toast.makeText(context,"No contact number added.", Toast.LENGTH_SHORT).show();
                } else {
                    String policeNumber = authorityList.get(pos).getContactNo();
                    callNumber(policeNumber);
                }


            }
        });

        settingsBtn =  (Button) mActivity.findViewById(R.id.btn_settings);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, EmergencyServicesSettings.class);

                startActivity(intent);
            }
        });


    }


    private void callNumber(String number){

        if(number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                try {
                    String dial = "tel:" + number;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(dial));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mActivity, "Failed to make phone call.", Toast.LENGTH_LONG).show();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);
            }

        } else if(number.trim().length() == 0){
            Toast.makeText(mActivity, "No contact number added.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mActivity, "Cannot retrieve contact number.", Toast.LENGTH_LONG).show();
        }
    }

    private int getPosition(String name){
        int i;
        int index = 0;
        boolean isFound = false;
        String authorityName;
        for (i = 0; i < authorityList.size(); i++){
            authorityName = authorityList.get(i).getName();
            if(authorityName.equals(name)){
                isFound = true;
                index = i;
            }
        }

        if (!isFound)
            index = CHECK_KEY;
        return index;
    }

}