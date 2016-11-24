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

    // Database connection.
    private Realm realm;
    // Current group.
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
        String id = intent.getStringExtra(getString(R.string.group_id_field));

        realm = Realm.getDefaultInstance();
        RealmResults<Group> list = realm.where(Group.class).equalTo(
            getString(R.string.group_id_field), id).findAll();
        group = list.get(0);
        // Sets group name in view.
        TextView text = (TextView) findViewById(R.id.groupName);
        text.setText(group.getName());
        // Sets description in view.
        TextView groupDescription = (TextView) findViewById(R.id.groupDescription);
        groupDescription.setText(group.getDescription());

        ListView listview = (ListView) findViewById(android.R.id.list);
        listview.setAdapter(new ViewGroupAdapter(this, group.getUsers()));

        listview.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    User currentUser = (User) parent.getItemAtPosition(position);
                    Intent newActivity = new Intent(view.getContext(), UserBills.class);
                    // Send group name to next intent for querying purposes.
                    newActivity.putExtra(getString(R.string.user_email_field),
                        currentUser.getEmail());
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
     */
    public void payBack() {
        Intent newActivity = new Intent(this, PayBill.class);
        startActivity(newActivity);
    }

    /**
     * On-Click method for "Calculate IOU" button"
     */
    public void calculateIOU() {
        Intent newActivity = new Intent(this, CalculateIOU.class);
        startActivity(newActivity);
    }

    /**
     * On-Click method for "Add Bill" button"
     */
    public void addBill() {
        Intent newActivity = new Intent(this, NewBill.class);

        newActivity.putExtra(getString(R.string.group_id_field), group.getId());
        startActivity(newActivity);
    }
    /**
     * On-Click method for various buttons"
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payBack:
                payBack();
                break;
            case R.id.IOU:
                calculateIOU();
                break;
            case R.id.addBill:
                addBill();
                break;
            default:
                break;
        }
    }

}
