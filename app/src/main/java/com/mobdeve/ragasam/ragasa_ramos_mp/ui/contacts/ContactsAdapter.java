package com.mobdeve.ragasam.ragasa_ramos_mp.ui.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.ragasam.ragasa_ramos_mp.R;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsViewHolder> {

    private ArrayList<Contacts> contactsArrayList;

    public ContactsAdapter(ArrayList<Contacts> data){
        this.contactsArrayList = data;

    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=  LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_layout, parent,false);
        ContactsViewHolder viewHolder = new ContactsViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        holder.setName(this.contactsArrayList.get(position).getName());
        holder.setNumber(this.contactsArrayList.get(position).getNumber());
        holder.setMessage(this.contactsArrayList.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return this.contactsArrayList.size();
    }
}
