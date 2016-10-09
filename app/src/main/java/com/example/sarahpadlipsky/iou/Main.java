package com.example.sarahpadlipsky.iou;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//Main shows list of groups that user is apart of.
public class Main extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //TODO: temporary list to be replaced with list from SQLite
        List<String> temporaryList = new ArrayList<String>();
        temporaryList.add("Christmas Gifts");
        temporaryList.add("Apartment");
        temporaryList.add("Spring Break Trip");

        //TODO: Change type String to Groups
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, temporaryList);

        ListView listView = getListView();
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    //TODO: Make sure it loads the correct group.
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent newActivity = new Intent(view.getContext(), GroupActivity.class);
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
