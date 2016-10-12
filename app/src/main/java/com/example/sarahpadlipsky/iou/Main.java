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
import java.util.List;

/**
 * Represents the main activity that shows the list of groups that the user is apart of.
 * @author sarahpadlipsky
 * @version October 11, 2016
 */

public class Main extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: SuperUser will be defined in log-in phase
        SuperUser superUser = new SuperUser();
        //TODO: Groups will be added during the AddGroupActivity phase
        Group tempGroup = new Group();
        tempGroup.setName("Christmas Break");
        Group tempGroup2 = new Group();
        tempGroup2.setName("Apartment 1");
        Group tempGroup3 = new Group();
        tempGroup3.setName("Mom's Birthday Gift");
        superUser.addGroup(tempGroup);
        superUser.addGroup(tempGroup2);
        superUser.addGroup(tempGroup3);

        ArrayAdapter<Group> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, superUser.getGroups());

        ListView listView = getListView();
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    //TODO: Make sure it loads the correct group.
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent newActivity = new Intent(view.getContext(), GroupActivity.class);
                        newActivity.putExtra("Position", position);
                        startActivity(newActivity);
                    }
                });

        setListAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Sets title of main page
        TextView text = (TextView) findViewById(R.id.username);
        //TODO: get username from log-in
        String username = "Sarah";
        text.setText(username + "'s Groups");
    }


    //On-Click method for "Add Group" button.
    public void createGroup(View view) {
        Intent newActivity = new Intent(this, AddGroupActivity.class);
        startActivity(newActivity);
    }

}
