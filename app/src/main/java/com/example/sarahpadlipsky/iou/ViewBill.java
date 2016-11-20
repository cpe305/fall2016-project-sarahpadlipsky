package com.example.sarahpadlipsky.iou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import io.realm.Realm;

/**
 * Represents the activity that displays a single bill.
 * @author sarahpadlipsky
 * @version November 19, 2016
 */

public class ViewBill extends Activity {

  // Database connection
  private Realm realm;

  /**
   * Android lifecycle function. Called when activity is opened for the first time.
   * @param savedInstanceState Lifecycle parameter
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.view_bill_activity);

    realm = Realm.getDefaultInstance();

    // Gets group name from main activity.
    Intent intent = getIntent();
    String id = intent.getStringExtra("billID");

    Bill bill = realm.where(Bill.class).contains("billID", id).findFirst();

    TextView text = (TextView) findViewById(R.id.billName);
    text.setText(bill.getName());

    TextView descriptionText = (TextView) findViewById(R.id.descriptionOfBillForViewBill);
    descriptionText.setText("Description: " + bill.getDescription());

    TextView userText = (TextView) findViewById(R.id.userOfBillForViewBill);
    userText.setText("User: " + bill.getUser());

    TextView costText = (TextView) findViewById(R.id.amountOfBillForViewBill);
    costText.setText("Cost: " + bill.getAmount());

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
