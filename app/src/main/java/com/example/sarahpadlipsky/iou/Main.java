package com.example.sarahpadlipsky.iou;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Represents the main activity that shows the list of groups that the user is apart of.
 * @author sarahpadlipsky
 * @version October 16, 2016
 */

public class Main extends ListActivity {

    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
                                     @Override
                                     public void execute(Realm realm) {
                                         //TODO: Groups will be added during the AddGroupActivity phase
                                         // TEMPORARY
                                         RealmResults<User> list = realm.where(User.class).equalTo
                                                 ("isCurrentUser", true).findAll();
                                         User user = list.get(0);
                                         User user2 = new User();
                                         user2.setName("Bob");
                                         user2.setMoneySpent(15.00);
                                         user.setMoneySpent(20.00);
                                         Group group = realm.createObject(Group.class);
                                         group.setName("Temporary Group");
                                         Group group2 = realm.createObject(Group.class);
                                         group2.setName("Mom's Birthday");
                                         group2.setDescription("Money pulled together to get Mom's birthday gift");
                                         group2.addUser(user);
                                         group2.addUser(user2);
                                         user.addGroup(group);
                                         user.addGroup(group2);
                                         realm.copyToRealmOrUpdate(user);

                                     }
                                 });
        RealmResults<User> list = realm.where(User.class).equalTo("isCurrentUser", true).findAll();
        User user = list.get(0);
        ArrayAdapter<Group> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, user.getGroups());

        ListView listView = getListView();
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    //TODO: Make sure it loads the correct group.
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Group currentGroup = (Group) parent.getItemAtPosition(position);
                        Intent newActivity = new Intent(view.getContext(), GroupActivity.class);
                        // Send group name to next intent for querying purposes.
                        newActivity.putExtra(getString(R.string.group_name), currentGroup.getName());
                        realm.close();
                        startActivity(newActivity);
                    }
                });

        setListAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
        //Sets title of main page
        TextView text = (TextView) findViewById(R.id.username);
        RealmResults<User> list = realm.where(User.class).equalTo("isCurrentUser", true).findAll();
        User user = list.get(0);
        String username = user.getName();
        text.setText(username + getString(R.string.main_title));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    // On-Click method for "Add Group" button.
    public void createGroup(View view) {
        Intent newActivity = new Intent(this, AddGroupActivity.class);
        startActivity(newActivity);
    }

    // Delete method to clear database while developing.
    // TODO: Delete method.
    private void delete() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Group> result = realm.where(Group.class).equalTo("name", "Temporary Group").findAll();
                result.deleteAllFromRealm();
            }
        });

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Group> result = realm.where(Group.class).equalTo("name", "Mom's Birthday").findAll();
                result.deleteAllFromRealm();
            }
        });
    }
}
