package com.mobdeve.ragasam.ragasa_ramos_mp.ui.contacts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.mobdeve.ragasam.ragasa_ramos_mp.R;

public class AddContactsDialog extends AppCompatDialogFragment {
    private EditText Name, Number, Message;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_contacts_dialog,null);

        builder.setView(view)
                .setTitle("Add Contacts")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        Name = view.findViewById(R.id.et_ContactName);
        Number = view.findViewById(R.id.et_ContactNumber);
        Message = view.findViewById(R.id.et_CustomMessage);


        return builder.create();
    }
}
