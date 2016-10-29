package com.example.sarahpadlipsky.iou;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Represents the activity that takes care of creating a group.
 * @author sarahpadlipsky
 * @version October 11, 2016
 */

public class AddGroupActivity extends ListActivity {

    // Database connection.
    private Realm realm;
    // List of users for new activity.
    private RealmList<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group_activity);

        realm = Realm.getDefaultInstance();

        userList = new RealmList<>();

        ArrayAdapter<User>  adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, userList);

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

    /**
     * Android lifecycle function. Called when activity is paused.
     */
    @Override
    protected void onStop() {
        super.onStop();
        realm.close();
    }

    /**
     * On-Click method for "Add Group" button"
     * @param view View of the current activity
     */
    public void submitGroup(View view) {
        // Gets the name of the group.
        EditText nameEditField = (EditText) findViewById(R.id.nameOfGroup);
        final String groupName = nameEditField.getText().toString();
        // Gets the description of the group.
        EditText descriptionEditField = (EditText) findViewById(R.id.descriptionOfGroup);
        final String groupDescription = (String) descriptionEditField.getText().toString();

        // Submits information to database.
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<User> list = realm.where(User.class).equalTo("isCurrentUser", true).findAll();
                User user = list.get(0);

                Group group = realm.createObject(Group.class);
                group.setName(groupName);
                group.setDescription(groupDescription);
                for (User currentUser: userList) {
                    group.addUser(currentUser);
                }
                user.addGroup(group);

            }
        });

        // Sends back to group list.
        Intent newActivity = new Intent(this, Main.class);
        startActivity(newActivity);
    }

    /**
     * On-Click method for "Add User" button"
     * @param view View of the current activity
     */
    public void submitUser(View view) {
        // Gets new user name.
        EditText userEditField = (EditText) findViewById(R.id.addUser);
        final String userName = userEditField.getText().toString();

        // Submits user to database.
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                long num = realm.where(User.class).count();
                // TODO: Should this check if it should update instead?
                User newUser = realm.createObject(User.class,Long.toString(num));
                newUser.setName(userName);
                userList.add(newUser);

            }
        });

        // Updates list adapter.
        ArrayAdapter<User>  adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, userList);
        setListAdapter(adapter);
        // Clears user field.
        userEditField.setText("");
    }
}