package com.android.gsnstr.mycontacts;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // описываем базу
    private MyContactsDatabase myContactsDatabase;
    //где храняться данные
    private ArrayList<Contact> contactArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // производим инициализацию базы
        myContactsDatabase = Room.databaseBuilder(getApplicationContext(),MyContactsDatabase.class,"ContactsDB").build();
        // загрузка данных
        loadContacts();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void loadContacts() {
        // загружаем данные с базы
       new GetAllContactsAsyncTask().execute();
    }

    private void deleteContacts(Contact contact) {
        // удаляем элемент с базы
        new DeleteContactAsyncTask().execute(contact);
    }


    private void AddContacts(Contact contact) {
        // добавляем  элемент в базу 
        new AddContactAsyncTask().execute(contact);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class GetAllContactsAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            // загружаем данные с базы
            contactArrayList = (ArrayList<Contact>) myContactsDatabase.getContactDao().getAllContacts();

            return null;
        }
    }

    private class DeleteContactAsyncTask extends AsyncTask<Contact, Void, Void>{

        @Override
        protected Void doInBackground(Contact... contacts) {
            // производим удаление
            myContactsDatabase.getContactDao().deleteContact(contacts[0]);
            return null;
        }
    }

    private class AddContactAsyncTask extends AsyncTask<Contact, Void, Void>{

        @Override
        protected Void doInBackground(Contact... contacts) {
            // производим добавление
            myContactsDatabase.getContactDao().InsertContact(contacts[0]);
            return null;
        }
    }
}