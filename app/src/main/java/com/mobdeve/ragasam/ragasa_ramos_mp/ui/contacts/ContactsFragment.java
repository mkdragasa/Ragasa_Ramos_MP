package com.mobdeve.ragasam.ragasa_ramos_mp.ui.contacts;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.ragasam.ragasa_ramos_mp.R;

import java.util.ArrayList;

public class ContactsFragment extends Fragment {

    Activity context;
    private RecyclerView recyclerView;
    private ContactsAdapter contactsAdapter;
    private ArrayList<Contacts> contactsArrayList = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View root = inflater.inflate(R.layout.fragment_contacts, container, false);


        return root;
    }

    public void onStart() {
        super.onStart();

        this.recyclerView = context.findViewById(R.id.rv_contacts);
        generateData();
        //Layout Manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        this.recyclerView.setLayoutManager(linearLayoutManager);

        //Adapter
        contactsAdapter = new ContactsAdapter(this.contactsArrayList);
        this.recyclerView.setAdapter(this.contactsAdapter);


    }

    public void generateData(){

        contactsArrayList = new ArrayList<>();

        Contacts contacts = new Contacts();
        contacts.setName("Kyla");
        contacts.setNumber("09171231234");
        contacts.setMessage("1 Message");
        contactsArrayList.add(0, contacts);

        contacts = new Contacts();
        contacts.setName("Alex");
        contacts.setNumber("09171231234");
        contacts.setMessage("2 Message");
        contactsArrayList.add(0, contacts);

        contacts = new Contacts();
        contacts.setName("Milo");
        contacts.setNumber("09171231234");
        contacts.setMessage("3 Message");
        contactsArrayList.add(0, contacts);

        contacts = new Contacts();
        contacts.setName("Frankie");
        contacts.setNumber("09171231234");
        contacts.setMessage("4 Message");
        contactsArrayList.add(0, contacts);

        contacts = new Contacts();
        contacts.setName("Hershey");
        contacts.setNumber("09171231234");
        contacts.setMessage("5 Message");
        contactsArrayList.add(0, contacts);

        contacts = new Contacts();
        contacts.setName("Cookie");
        contacts.setNumber("09171231234");
        contacts.setMessage("6 Message");
        contactsArrayList.add(0, contacts);

    }
}