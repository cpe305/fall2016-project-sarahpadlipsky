package com.example.sarahpadlipsky.iou;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import io.realm.Realm;

/**
 * Represents the activity that displays a user's bills.
 * @author sarahpadlipsky
 * @version November 23, 2016
 */

public class UserBills extends ListActivity {

  // Database connection.
  private Realm realm;

  /**
   * Android lifecycle function. Called when activity is opened for the first time.
   * @param savedInstanceState Lifecycle parameter
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.user_bills_activity);

    realm = Realm.getDefaultInstance();

    // Gets group name from main activity.
    Intent intent = getIntent();
    String email = intent.getStringExtra(getString(R.string.user_email_field));

    User user = realm.where(User.class).contains(getString(R.string.user_email_field),
        email).findFirst();

    TextView text = (TextView) findViewById(R.id.titleAccount);
    text.setText(getString(R.string.user_bills_title, user.getName()));

    ArrayAdapter<Bill> adapter = new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_1, user.getBills());

    setListAdapter(adapter);

    ListView listview = getListView();
    listview.setOnItemClickListener(
        new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Bill currentBill = (Bill) parent.getItemAtPosition(position);
            Intent newActivity = new Intent(view.getContext(), ViewBill.class);
            // Send group name to next intent for querying purposes.
            newActivity.putExtra(getString(R.string.bill_id_field), currentBill.getId());
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


}
