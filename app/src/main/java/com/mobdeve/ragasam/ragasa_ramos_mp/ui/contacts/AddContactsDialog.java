package com.mobdeve.ragasam.ragasa_ramos_mp.ui.contacts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.mobdeve.ragasam.ragasa_ramos_mp.MyDatabaseHelper;
import com.mobdeve.ragasam.ragasa_ramos_mp.R;

public class AddContactsDialog extends AppCompatDialogFragment {

    private EditText name, number, message;
    private Switch shareLocation;
    private AddContactsDialogListener listener;
    private String _id;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_contacts_dialog,null);

        name = view.findViewById(R.id.et_ContactName);
        number = view.findViewById(R.id.et_ContactNumber);
        message = view.findViewById(R.id.et_CustomMessage);
        shareLocation = view.findViewById(R.id.switch_ShareLocation);
        _id = "";

        Bundle mArgs = getArguments();
       if(mArgs != null){
           _id = mArgs.getString("ID");
           name.setText(mArgs.getString("name"));
           number.setText(mArgs.getString("number"));
           message.setText(mArgs.getString("message"));
           shareLocation.setChecked(mArgs.getBoolean("isShare"));
           builder.setTitle("Edit Contact");
       } else {
           builder.setTitle("Add Contact");
       }

        builder.setView(view)
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String person = name.getText().toString();
                        String contactNo = number.getText().toString();
                        String text = message.getText().toString();
                        boolean isShare = shareLocation.isChecked();
                        listener.applyTexts(person, contactNo, text, isShare, _id);

                    }
                });


        return builder.create();
    }

    public interface AddContactsDialogListener {
        void applyTexts(String name, String number, String message, boolean shareLocation, String _id);
    }

    public void setListener(AddContactsDialogListener listener){
        this.listener = listener;
    }


}
