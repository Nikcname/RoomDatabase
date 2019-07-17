package com.forevermore.nikcname.roomdatabase;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.forevermore.nikcname.roomdatabase.adapters.ContactAdapter;
import com.forevermore.nikcname.roomdatabase.dataholders.AppDatabase;
import com.forevermore.nikcname.roomdatabase.dataholders.ContactData;
import com.forevermore.nikcname.roomdatabase.fragments.CreateContact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewMain;
    RecyclerView.Adapter adapterMain;
    RecyclerView.LayoutManager layoutManagerMain;
    List<ContactData> contacts;
    FloatingActionButton fab;
    CreateContact createContact;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contacts = new ArrayList<>();

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "contacts-database").allowMainThreadQueries().build();

        List<ContactData> allContacts = db.getContactDao().getAllContactData();
        contacts.addAll(allContacts);
        createContact = new CreateContact();
        recyclerViewMain = findViewById(R.id.recycler_main);
        layoutManagerMain = new LinearLayoutManager(this);
        recyclerViewMain.setLayoutManager(layoutManagerMain);
        adapterMain = new ContactAdapter(contacts);
        recyclerViewMain.setAdapter(adapterMain);
        fab = findViewById(R.id.button_fab);
        registerForContextMenu(recyclerViewMain);

    }

    @Override
    protected void onStart() {
        super.onStart();

        ((ContactAdapter)adapterMain).setListener(new ContactAdapter.CallbackContactAdapter() {
            @Override
            public void itemPlace(int position) {
                Log.d("SSS", String.valueOf(position));
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createContact.show(getSupportFragmentManager(), "create_contact");
            }
        });

        createContact.setListener(new CreateContact.CallbackCreateContact() {
            @Override
            public void newContact(ContactData newContactData) {
                db.getContactDao().insertAll(newContactData);
                contacts.add(newContactData);
                adapterMain.notifyDataSetChanged();
            }
        });

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        position = ((ContactAdapter)adapterMain).getPos();

        switch (item.getItemId()) {
            case R.id.edit_item:
                CreateContact editContactDialog = CreateContact.newInstance(contacts.get(position));
                editContactDialog.show(getSupportFragmentManager(), "edit_dialog");
                final int finalPosition = position;
                editContactDialog.setListener(new CreateContact.CallbackCreateContact() {
                    @Override
                    public void newContact(ContactData newContactData) {
                        int k = contacts.get(finalPosition).getId();
                        contacts.remove(finalPosition);
                        newContactData.setId(k);
                        contacts.add(finalPosition, newContactData);
                        db.getContactDao().updateContact(newContactData);
                        adapterMain.notifyDataSetChanged();
                    }
                });
                break;
            case R.id.delete_item:
                db.getContactDao().delete(contacts.get(position));
                contacts.remove(position);
                adapterMain.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
