package com.mobdeve.ragasam.ragasa_ramos_mp.ui.contacts;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.ragasam.ragasa_ramos_mp.R;

public class ContactsViewHolder extends RecyclerView.ViewHolder {

    private TextView name, number, message;
    private Button callBtn, editBtn, textBtn;

    public ContactsViewHolder(@NonNull View itemView) {
        super(itemView);

        this.name = itemView.findViewById(R.id.tv_ContactName);
        this.number = itemView.findViewById(R.id.tv_ContactNumber);
        this.message = itemView.findViewById(R.id.tv_ContactMessage);
    }

    public void setName(String name){
        this.name.setText(name);
    }

    public void setNumber(String number){
        this.number.setText(number);
    }

    public void setMessage(String message){
        this.message.setText(message);
    }

}
