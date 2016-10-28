package com.example.sarahpadlipsky.iou;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Represents the activity that displays a group's information.
 * @author sarahpadlipsky
 * @version October 16, 2016
 */

public class GroupActivity extends ListActivity {

    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_activity);

        Intent intent = getIntent();
        String name = intent.getStringExtra(getString(R.string.group_name));

        realm = Realm.getDefaultInstance();
        RealmResults<Group> list = realm.where(Group.class).equalTo(getString(R.string.group_name),
                name).findAll();
        Group group = list.get(0);
        TextView text = (TextView) findViewById(R.id.groupName);
        //TEMPORARY
        text.setText(group.getName());

        TextView groupDescription = (TextView) findViewById(R.id.groupDescription);
        groupDescription.setText(group.getDescription());

        ArrayAdapter<User> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, group.getUsers());

        RealmList<User> groupList = group.getUsers();

        System.out.println("LIST OF GROUPS " + groupList);

        setListAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    protected void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
    }

}
