package com.mobdeve.ragasam.ragasa_ramos_mp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EmergencyServicesSettings extends AppCompatActivity {
    MyDatabaseHelper myDB;
    ArrayList<Authority> authorityList;
    private TextView hospitalTv, policeTv, fireTv, defaultTv;
    private Button hospitalActionBtn, hospitalDefaultBtn, policeActionBtn, policeDefaultBtn, fireActionBtn,
            fireDefaultBtn, nationalDefaultBtn;
    AlertDialog dialog;
    EditText editNum;

    private final String ADD = "ADD";
    private final String EDIT = "EDIT";
    private final String POLICE_KEY = "Police";
    private final String FIRE_KEY = "Fire";
    private final String HOSPITAL_KEY = "Hospital";
    private final int CHECK_KEY = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_services_settings);

        authorityList = new ArrayList<>();
        myDB = new MyDatabaseHelper(EmergencyServicesSettings.this);
        init();
        storeDataInArrays();
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
                int indexPolice = getPosition(POLICE_KEY);
                if (myDB.checkIfDataExists("my_authority", "authority_name", POLICE_KEY)){
                    if (authorityList.get(indexPolice).getContactNo().length() == 0){
                        Toast.makeText(EmergencyServicesSettings.this, "Enter phone number.", Toast.LENGTH_SHORT).show();
                    } else {
                        String policeID = authorityList.get(indexPolice).getID();
                        myDB.updateAuthorityDefault(policeID, true);
                        myDB.updateRecords(policeID, false);
                        if(indexPolice != CHECK_KEY){
                            updateDefault(POLICE_KEY);
                            authorityList.get(indexPolice).updateIsDefault(true);
                        }
                        defaultTv.setText(authorityList.get(indexPolice).getContactNo());
                        policeDefaultBtn.setEnabled(false);
                        nationalDefaultBtn.setEnabled(true);
                        fireDefaultBtn.setEnabled(true);
                        hospitalDefaultBtn.setEnabled(true);
                    }

                } else {
                    Toast.makeText(EmergencyServicesSettings.this, "Enter phone number.", Toast.LENGTH_SHORT).show();
                }

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
                int indexFire = getPosition(FIRE_KEY);
                if (myDB.checkIfDataExists("my_authority", "authority_name", FIRE_KEY)){
                    if (authorityList.get(indexFire).getContactNo().length() == 0){
                        Toast.makeText(EmergencyServicesSettings.this, "Enter phone number.", Toast.LENGTH_SHORT).show();
                    } else {
                        String fireID = authorityList.get(indexFire).getID();
                        myDB.updateAuthorityDefault(fireID, true);
                        myDB.updateRecords(fireID, false);
                        if(indexFire != CHECK_KEY){
                            updateDefault(FIRE_KEY);
                            authorityList.get(indexFire).updateIsDefault(true);
                        }
                        defaultTv.setText(authorityList.get(indexFire).getContactNo());
                        fireDefaultBtn.setEnabled(false);
                        nationalDefaultBtn.setEnabled(true);
                        policeDefaultBtn.setEnabled(true);
                        hospitalDefaultBtn.setEnabled(true);
                    }
                } else {
                    Toast.makeText(EmergencyServicesSettings.this, "Enter phone number.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        hospitalActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNum.setText(hospitalTv.getText());
                setOLCDialog(hospitalTv, hospitalActionBtn, 2);
                dialog.show();
            }
        });

        hospitalDefaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int indexHospital = getPosition(HOSPITAL_KEY);
                if (myDB.checkIfDataExists("my_authority", "authority_name", HOSPITAL_KEY)){
                    if (authorityList.get(indexHospital).getContactNo().length() == 0){
                        Toast.makeText(EmergencyServicesSettings.this, "Enter phone number.", Toast.LENGTH_SHORT).show();
                    } else {
                        String hospitalID = authorityList.get(indexHospital).getID();
                        myDB.updateAuthorityDefault(hospitalID, true);
                        myDB.updateRecords(hospitalID, false);
                        if(indexHospital != CHECK_KEY){
                            updateDefault(HOSPITAL_KEY);
                            authorityList.get(indexHospital).updateIsDefault(true);
                        }
                        defaultTv.setText(authorityList.get(indexHospital).getContactNo());
                        hospitalDefaultBtn.setEnabled(false);
                        nationalDefaultBtn.setEnabled(true);
                        policeDefaultBtn.setEnabled(true);
                        fireDefaultBtn.setEnabled(true);
                    }
                } else {
                    Toast.makeText(EmergencyServicesSettings.this, "Please phone number first.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        nationalDefaultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultTv.setText("911");
                myDB.updateAllRecords(false);
                updateDefault("911");
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
                if(!(tv.getText().toString().trim().length() == 0)){
                    bt.setText(EDIT);
                } else {
                    bt.setText(ADD);
                }

                if(i == 0){
                    String textPolice = editNum.getText().toString().trim();
                    if (myDB.checkIfDataExists("my_authority", "authority_name", POLICE_KEY)){
                        int indexPolice = getPosition(POLICE_KEY);
                        String policeID = authorityList.get(indexPolice).getID();
                        myDB.updateAuthorityNumber(policeID, textPolice);
                        if(indexPolice != CHECK_KEY){
                            authorityList.get(indexPolice).updateNumber(editNum.getText().toString().trim());
                        }
                    } else {
                        long result = myDB.addAuthority(POLICE_KEY, textPolice, false);
                        Authority newAuthority = new Authority(String.valueOf(result),
                                POLICE_KEY,
                                textPolice, false);
                        authorityList.add(newAuthority);

                    }
                } else if(i == 1){
                    String textFire = editNum.getText().toString().trim();
                    if (myDB.checkIfDataExists("my_authority", "authority_name", FIRE_KEY)){
                        int indexFire = getPosition(FIRE_KEY);
                        String fireID = authorityList.get(indexFire).getID();
                        myDB.updateAuthorityNumber(fireID, textFire);
                        if(indexFire != CHECK_KEY)
                            authorityList.get(indexFire).updateNumber(editNum.getText().toString().trim());

                    } else {
                        long result = myDB.addAuthority("Fire", textFire, false);
                        Authority newAuthority = new Authority(String.valueOf(result),
                                FIRE_KEY,
                                textFire, false);
                        authorityList.add(newAuthority);
                    }
                }  else if(i == 2){
                    String textHospital = editNum.getText().toString().trim();
                    if (myDB.checkIfDataExists("my_authority", "authority_name", HOSPITAL_KEY)){
                        int indexHospital = getPosition(HOSPITAL_KEY);
                        String hospitalID = authorityList.get(indexHospital).getID();
                        myDB.updateAuthorityNumber(hospitalID, textHospital);
                        if(indexHospital != CHECK_KEY)
                            authorityList.get(indexHospital).updateNumber(editNum.getText().toString().trim());
                    } else {
                        long result = myDB.addAuthority(HOSPITAL_KEY, textHospital, false);
                        Authority newAuthority = new Authority(String.valueOf(result),
                                HOSPITAL_KEY,
                                textHospital, false);
                        authorityList.add(newAuthority);

                    }
                }

            }
        });

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    private void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        boolean isDefault, isDefaultNone = true;
        String defaultNumber = "";

        while (cursor.moveToNext()){
            isDefault = false;
            if(cursor.getString(3).equals("1")){
                isDefault = true;
                isDefaultNone = false;
                defaultNumber = cursor.getString(1);
            }
            Authority newAuthority = new Authority(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2), isDefault);
            authorityList.add(newAuthority);
                if (isDefault){
                    int pos = getPosition(defaultNumber);
                    defaultTv.setText(authorityList.get(pos).getContactNo());

                    // disable button for the default number
                    if(defaultNumber.equals(FIRE_KEY)){
                        fireDefaultBtn.setEnabled(false);
                    } else if(defaultNumber.equals(POLICE_KEY)){
                        policeDefaultBtn.setEnabled(false);
                    } else if(defaultNumber.equals(HOSPITAL_KEY)){
                        hospitalDefaultBtn.setEnabled(false);
                    }
                }
        }

        // disable button for the 911 as default
        if (isDefaultNone){
            nationalDefaultBtn.setEnabled(false);
        }

    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    */

    private boolean checkIfEmpty(String name){
        int i;
        int index = 0;
        boolean isEmpty = false;
        String authorityNumber, authorityName;

        for (i = 0; i < authorityList.size(); i++){
            authorityName = authorityList.get(i).getName();
            if(authorityName.equals(name)){
                authorityNumber = authorityList.get(i).getContactNo();
                if(authorityNumber.length() == 0){
                    isEmpty = true;
                }
            }
        }

        return isEmpty;
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

    private void updateDefault(String name){
        int i;
        String authorityName;

        for (i = 0; i < authorityList.size(); i++){
            authorityName = authorityList.get(i).getName();
            if(!(authorityName.equals(name))){
                if(authorityList.get(i).getIsDefault()){
                    authorityList.get(i).updateIsDefault(false);
                }
            }
        }
    }

    @Override
    protected void onStart(){
        super.onStart();

        int indexHospital = getPosition(HOSPITAL_KEY);
        int indexFire = getPosition(FIRE_KEY);
        int indexPolice = getPosition(POLICE_KEY);

        // modify text of buttons and text views
        if(indexHospital == CHECK_KEY){
            hospitalActionBtn.setText(ADD);
        } else {
            if(checkIfEmpty(HOSPITAL_KEY)){
                hospitalActionBtn.setText(ADD);
            } else {
                hospitalActionBtn.setText(EDIT);
                hospitalTv.setText(authorityList.get(indexHospital).getContactNo());
            }
        }

        if(indexFire == CHECK_KEY){
            fireActionBtn.setText(ADD);
        } else {
            if(checkIfEmpty(FIRE_KEY)){
                fireActionBtn.setText(ADD);
            } else {
                fireActionBtn.setText(EDIT);
                fireTv.setText(authorityList.get(indexFire).getContactNo());
            }

        }

        if(indexPolice == CHECK_KEY){
            policeActionBtn.setText(ADD);
        } else {
            if(checkIfEmpty(POLICE_KEY)){
                policeActionBtn.setText(ADD);
            } else {
                policeActionBtn.setText(EDIT);
                policeTv.setText(authorityList.get(indexPolice).getContactNo());
            }

        }

    }

    protected void onDestroy(){
        super.onDestroy();
        finish();
    }
}