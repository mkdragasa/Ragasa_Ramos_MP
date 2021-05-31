package com.mobdeve.ragasam.ragasa_ramos_mp.ui.contacts;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.ragasam.ragasa_ramos_mp.Authority;
import com.mobdeve.ragasam.ragasa_ramos_mp.MyDatabaseHelper;
import com.mobdeve.ragasam.ragasa_ramos_mp.R;

import java.util.ArrayList;

public class ContactsFragment extends Fragment implements AddContactsDialog.AddContactsDialogListener {

    MyDatabaseHelper myDB;
    Context context;
    Activity mActivity;
    private RecyclerView recyclerView;
    private ContactsAdapter contactsAdapter;
    private ArrayList<Contact> contactsArrayList;
    private Button addContactBtn;



    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        context = inflater.getContext();
        View root = inflater.inflate(R.layout.fragment_contacts, container, false);
        myDB = MyDatabaseHelper.newInstance(mActivity);

        return root;
    }

    public void onStart() {
        super.onStart();
        this.recyclerView = mActivity.findViewById(R.id.rv_contacts);
        contactsArrayList = new ArrayList<>();
        storeDataInArray();

        //Layout Manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        this.recyclerView.setLayoutManager(linearLayoutManager);

        //Adapter
        contactsAdapter = new ContactsAdapter(this.contactsArrayList, getContext(), getActivity(), getFragmentManager());
        this.recyclerView.setAdapter(this.contactsAdapter);

        addContactBtn = (Button) mActivity.findViewById(R.id.btn_addContact);

        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

         ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                String ID = contactsArrayList.get(position).getContactID();
                myDB.deleteOneContact(ID);
                contactsArrayList.remove(position);
                contactsAdapter.notifyDataSetChanged();

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(this.recyclerView);

    }

    private void storeDataInArray(){
        Cursor cursor = myDB.readAllContacts();
        boolean shareLocation;
        while (cursor.moveToNext()){
            shareLocation = false;
            if(cursor.getString(4).equals("1")){
                shareLocation = true;
            }

            Contact newContact = new Contact(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), shareLocation);
            contactsArrayList.add(newContact);
        }

    }

    public void openDialog(){
        AddContactsDialog addDialog = new AddContactsDialog();
        addDialog.setListener(ContactsFragment.this);
        addDialog.show(getFragmentManager() ,"Add Contacts");
    }

    @Override
    public void applyTexts(String name, String number, String message, boolean shareLocation, String _id) {

        long result = myDB.addContact(name, number, message, shareLocation);
        Contact newContact = new Contact(String.valueOf(result),
                name,
                number, message, shareLocation);
        contactsArrayList.add(newContact);

        contactsAdapter.notifyDataSetChanged();

    }


}