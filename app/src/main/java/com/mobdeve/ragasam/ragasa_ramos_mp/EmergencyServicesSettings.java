package com.mobdeve.ragasam.ragasa_ramos_mp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EmergencyServicesSettings extends AppCompatActivity {

    private TextView hospitalTv, policeTv, fireTv, defaultTv;
    private Button hospitalActionBtn, hospitalDefaultBtn, policeActionBtn, policeDefaultBtn, fireActionBtn,
            fireDefaultBtn, nationalDefaultBtn;
    AlertDialog dialog;
    EditText editNum;

    private final String ADD = "ADD";
    private final String EDIT = "EDIT";

    private String numbers[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_services_settings);

        numbers = new String[]{"", "", ""};

        init();
        setOnClickListeners();

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(8,0,8,0);
        editNum = new EditText(this);
        editNum.setHint("Phone Number");
        editNum.setInputType(InputType.TYPE_CLASS_PHONE);
        editNum.setLayoutParams(lp);
        editNum.setWidth(15);


        container.addView(editNum, lp);

        dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("Input Phone Number");
            dialog.setView(container);

    }

    private void init(){
        defaultTv = findViewById(R.id.tv_DefaultNum);
        hospitalTv = findViewById(R.id.tv_hospitalNum);
        policeTv = findViewById(R.id.tv_policeNum);
        fireTv = findViewById(R.id.tv_fireNum);
        hospitalActionBtn = findViewById(R.id.btn_HospitalAction);
        hospitalDefaultBtn = findViewById(R.id.btn_HospitalDefault);
        policeActionBtn = findViewById(R.id.btn_PoliceAction);
        policeDefaultBtn = findViewById(R.id.btn_Policedefault);
        fireActionBtn = findViewById(R.id.btn_FireAction);
        fireDefaultBtn = findViewById(R.id.btn_Firedefault);
        nationalDefaultBtn = findViewById(R.id.btn_hotlineDefault);
    }

    private void setOnClickListeners() {
        // for adding or editing phone number
        policeActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNum.setText(policeTv.getText());
                setOLCDialog(policeTv, policeActionBtn, 0);
                dialog.show();
            }
        });

        policeDefaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultTv.setText(numbers[0]);
                policeDefaultBtn.setEnabled(false);
                nationalDefaultBtn.setEnabled(true);
                fireDefaultBtn.setEnabled(true);
                hospitalDefaultBtn.setEnabled(true);
            }
        });

        fireActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNum.setText(fireTv.getText());
                setOLCDialog(fireTv, fireActionBtn, 1);
                dialog.show();
            }
        });

        fireDefaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultTv.setText(numbers[1]);
                fireDefaultBtn.setEnabled(false);
                nationalDefaultBtn.setEnabled(true);
                policeDefaultBtn.setEnabled(true);
                hospitalDefaultBtn.setEnabled(true);
            }
        });

        hospitalActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNum.setText(hospitalTv.getText());
                setOLCDialog(hospitalTv, hospitalActionBtn, 2);
                dialog.show();
                Log.d("SafetyApp", "DONE");
            }
        });

        hospitalDefaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultTv.setText(numbers[2]);
                hospitalDefaultBtn.setEnabled(false);
                nationalDefaultBtn.setEnabled(true);
                policeDefaultBtn.setEnabled(true);
                fireDefaultBtn.setEnabled(true);
            }
        });

        nationalDefaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultTv.setText("911");
                nationalDefaultBtn.setEnabled(false);
                policeDefaultBtn.setEnabled(true);
                fireDefaultBtn.setEnabled(true);
                hospitalDefaultBtn.setEnabled(true);
            }
        });

    }

    private void setOLCDialog(TextView tv, Button bt, int i){
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv.setText(editNum.getText());
                Log.d("SafetyApp", "Dialog: "+dialog);
                if(!(tv.getText().toString().trim().length() == 0)){
                    bt.setText(EDIT);
                } else {
                    bt.setText(ADD);
                }
                numbers[i] = tv.getText().toString();
            }
        });

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

        // modify text of buttons
        if(numbers[0].length() == 0){
            Log.d("SafetyApp", "Text is empty.");
            hospitalActionBtn.setText(ADD);
        } else {
            Log.d("SafetyApp", "Text is: "+hospitalTv.getText().toString());
            hospitalActionBtn.setText(EDIT);
        }

        if(numbers[1].length() == 0){
            policeActionBtn.setText(ADD);
        } else {
            policeActionBtn.setText(EDIT);
        }

        if(numbers[2].length() == 0){
            fireActionBtn.setText(ADD);
        } else {
            fireActionBtn.setText(EDIT);
        }

    }
}