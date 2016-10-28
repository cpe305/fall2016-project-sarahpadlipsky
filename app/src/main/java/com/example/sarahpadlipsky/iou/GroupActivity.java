package com.example.sarahpadlipsky.iou;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Represents the activity that displays a group's information.
 * @author sarahpadlipsky
 * @version October 28, 2016
 */

public class GroupActivity extends ListActivity {

    // Database connection
    private Realm realm;

    /**
     * Android lifecycle function. Called when activity is opened for the first time.
     * @param savedInstanceState Lifecycle parameter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_activity);

        // Gets group name from main activity.
        Intent intent = getIntent();
        String name = intent.getStringExtra(getString(R.string.group_name));

        realm = Realm.getDefaultInstance();
        RealmResults<Group> list = realm.where(Group.class).equalTo(getString(R.string.group_name),
                name).findAll();
        Group group = list.get(0);
        // Sets group name in view.
        TextView text = (TextView) findViewById(R.id.groupName);
        text.setText(group.getName());
        // Sets description in view.
        TextView groupDescription = (TextView) findViewById(R.id.groupDescription);
        groupDescription.setText(group.getDescription());

        ArrayAdapter<User> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, group.getUsers());

        setListAdapter(adapter);
    }

    /**
     * Android lifecycle function. Called when activity is closed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    /**
     * Android lifecycle function. Called when activity is re-opened.
     */
    @Override
    protected void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
    }

}
