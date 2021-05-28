package com.mobdeve.ragasam.ragasa_ramos_mp.ui.emergencyServices;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mobdeve.ragasam.ragasa_ramos_mp.EmergencyServicesSettings;
import com.mobdeve.ragasam.ragasa_ramos_mp.R;
import com.mobdeve.ragasam.ragasa_ramos_mp.SafetyGuidelines;

public class EmergencyServicesFragment extends Fragment {

    Activity context;
    ImageButton fireBtn, earthquakeBtn, floodBtn;
    Button settingsBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();

        View root = inflater.inflate(R.layout.fragment_emergency_services, container, false);


        return root;
    }

    public void onStart() {
        super.onStart();

        fireBtn =  (ImageButton) context.findViewById(R.id.ib_fire);
        fireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Fire Button", Toast.LENGTH_SHORT).show();
            }
        });

        earthquakeBtn =  (ImageButton) context.findViewById(R.id.ib_earthquake);
        earthquakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Earthquake Button", Toast.LENGTH_SHORT).show();

            }
        });

        floodBtn =  (ImageButton) context.findViewById(R.id.ib_flood);
        floodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Flood Button", Toast.LENGTH_SHORT).show();
            }
        });

        settingsBtn =  (Button) context.findViewById(R.id.btn_settings);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Settings Button", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, EmergencyServicesSettings.class);

                startActivity(intent);
            }
        });


    }


}