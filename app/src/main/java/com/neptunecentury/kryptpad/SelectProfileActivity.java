package com.neptunecentury.kryptpad;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.neptunecentury.kryptpadapi.ApiProfile;
import com.neptunecentury.kryptpadapi.ApiProfileResult;
import com.neptunecentury.kryptpadapi.AsyncTaskComplete;
import com.neptunecentury.kryptpadapi.KryptPadApi;

import java.util.ArrayList;

public class SelectProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Create profile", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });

        // Create list adapter to listen to changes in data
        final ArrayAdapter<ApiProfile> adapter = new ArrayAdapter<ApiProfile>(this, android.R.layout.simple_list_item_1);

        // Set the adapter to our list view
        ListView profiles = (ListView) findViewById(R.id.profiles);
        profiles.setAdapter(adapter);


        // Get list of profiles
        KryptPadApi.GetProfilesAsync task = new KryptPadApi.GetProfilesAsync(new AsyncTaskComplete() {
            @Override
            public void complete(Object data, String error) {
                ApiProfileResult profileResult = (ApiProfileResult) data;

                // Create array adapter for our items
                //ArrayList list = new ArrayList();
                for (int x = 0; x < profileResult.profiles.length; x++) {
                    //list.add(profileResult.profiles[x]);
                    adapter.add(profileResult.profiles[x]);
                }

            }
        });

        task.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.select_profile_menu, menu);
        return true;
    }

    /***
     * Listen to the menu for clicks
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.create_profile:
                showMsg("You clicked create profile");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

}
