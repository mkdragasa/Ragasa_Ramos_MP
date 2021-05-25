package com.mobdeve.ragasam.ragasa_ramos_mp.ui.emergencyServices;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobdeve.ragasam.ragasa_ramos_mp.R;

public class EmergencyServicesFragment extends Fragment {

    private EmergencyServicesViewModel EmergencyServicesModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EmergencyServicesModel =
                new ViewModelProvider(this).get(EmergencyServicesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_emergency_services, container, false);
        final TextView textView = root.findViewById(R.id.text_emergency_services);
        EmergencyServicesModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


}