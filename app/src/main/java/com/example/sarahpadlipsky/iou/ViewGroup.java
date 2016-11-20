package com.example.sarahpadlipsky.iou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Represents the activity that displays a group's information.
 * @author sarahpadlipsky
 * @version October 28, 2016
 */

public class ViewGroup extends Activity {

    // Database connection
    private Realm realm;
    private ListView listview;
    private Group group;

    /**
     * Android lifecycle function. Called when activity is opened for the first time.
     * @param savedInstanceState Lifecycle parameter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_group_activity);

        // Gets group name from main activity.
        Intent intent = getIntent();
        String id = intent.getStringExtra(getString(R.string.group_id));

        realm = Realm.getDefaultInstance();
        RealmResults<Group> list = realm.where(Group.class).equalTo(getString(R.string.group_id),
                id).findAll();
        group = list.get(0);
        // Sets group name in view.
        TextView text = (TextView) findViewById(R.id.groupName);
        text.setText(group.getName());
        // Sets description in view.
        TextView groupDescription = (TextView) findViewById(R.id.groupDescription);
        groupDescription.setText(group.getDescription());

        listview = (ListView) findViewById(android.R.id.list);
        listview.setAdapter(new ViewGroupAdapter(this, group.getUsers()));

        listview.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    User currentUser = (User) parent.getItemAtPosition(position);
                    Intent newActivity = new Intent(view.getContext(), UserBills.class);
                    // Send group name to next intent for querying purposes.
                    newActivity.putExtra("email", currentUser.getEmail());
                    realm.close();
                    startActivity(newActivity);
                }
            });
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
     * On-Click method for "Pay Back" button"
     * @param view Necessary paramter for onClick function.
     */
    public void payBack(View view) {
        Intent newActivity = new Intent(this, PayBill.class);
        startActivity(newActivity);
    }

    /**
     * On-Click method for "Calculate IOU" button"
     * @param view Necessary paramter for onClick function.
     */
    public void calculateIOU(View view) {
        Intent newActivity = new Intent(this, CalculateIOU.class);
        startActivity(newActivity);
    }

    /**
     * On-Click method for "Add Bill" button"
     * @param view Necessary paramter for onClick function.
     */
    public void addBill(View view) {
        Intent newActivity = new Intent(this, NewBill.class);

        newActivity.putExtra("groupID", group.getId());
        startActivity(newActivity);
    }

}
