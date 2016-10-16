package com.example.sarahpadlipsky.iou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Represents the activity that displays a group's information.
 * @author sarahpadlipsky
 * @version October 16, 2016
 */

public class GroupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_activity);

        Intent intent = getIntent();
        String name = intent.getStringExtra(getString(R.string.group_name));

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Group> list = realm.where(Group.class).equalTo(getString(R.string.group_name),
                name).findAll();
        Group group = list.get(0);
        TextView text = (TextView) findViewById(R.id.groupName);
        //TEMPORARY
        text.setText("This is the group information for " + group.getName());

    }
}
