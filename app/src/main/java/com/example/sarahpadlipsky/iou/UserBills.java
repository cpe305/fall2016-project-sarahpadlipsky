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
 * @version October 28, 2016
 */


public class UserBills extends ListActivity {

  private Realm realm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.user_bills_activity);

    realm = Realm.getDefaultInstance();

    // Gets group name from main activity.
    Intent intent = getIntent();
    String email = intent.getStringExtra("email");

    User user = realm.where(User.class).contains("email", email).findFirst();

    TextView text = (TextView) findViewById(R.id.titleAccount);
    text.setText(user.getName() + "'s Transactions");

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
            newActivity.putExtra("billID", currentBill.getId());
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
