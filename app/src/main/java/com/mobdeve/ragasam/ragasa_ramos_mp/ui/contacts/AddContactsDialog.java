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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.d("SafeApp", "DIALOG OPENED");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_contacts_dialog,null);

        name = view.findViewById(R.id.et_ContactName);
        number = view.findViewById(R.id.et_ContactNumber);
        message = view.findViewById(R.id.et_CustomMessage);
        shareLocation = view.findViewById(R.id.switch_ShareLocation);

        builder.setView(view)
                .setTitle("Add Contacts")
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
                        listener.applyTexts(person, contactNo, text, isShare);

                    }
                });

        return builder.create();
    }

    public interface AddContactsDialogListener {
        void applyTexts(String name, String number, String message, boolean shareLocation);
    }

    public void setListener(AddContactsDialogListener listener){
        this.listener = listener;
    }

    public void setUpdateListener(AddContactsDialogListener listener, String name, String contactNo, String message, boolean isShare){
        this.listener = listener;
        this.name.setText(name);
        this.number.setText(contactNo);
        this.message.setText(message);
        this.shareLocation.setChecked(isShare);
    }
}
