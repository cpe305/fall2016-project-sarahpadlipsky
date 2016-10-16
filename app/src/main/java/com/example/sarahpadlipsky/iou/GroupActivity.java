package com.example.sarahpadlipsky.iou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Represents the activity that displays a group's information.
 * @author sarahpadlipsky
 * @version October 11, 2016
 */

public class GroupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_activity);

        Intent intent = getIntent();
        int position = intent.getIntExtra(getString(R.string.database_position), -1);

        //TODO: Access group from database.
    }
}
