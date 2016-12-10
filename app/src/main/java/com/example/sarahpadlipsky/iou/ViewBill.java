package com.example.sarahpadlipsky.iou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.math.BigDecimal;

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
    String id = intent.getStringExtra(getString(R.string.bill_id_field));

    Bill bill = realm.where(Bill.class).contains(getString(R.string.bill_id_field), id).findFirst();

    // Sets the bill's name.
    TextView text = (TextView) findViewById(R.id.billName);
    text.setText(bill.getName());
    // Sets the bill's description.
    TextView descriptionText = (TextView) findViewById(R.id.descriptionOfBillForViewBill);
    descriptionText.setText(getString(R.string.view_bill_description, bill.getDescription()));
    // Sets the bill's user.
    TextView userText = (TextView) findViewById(R.id.userOfBillForViewBill);
    userText.setText(getString(R.string.view_bill_user, bill.getSendUser()));
    // Sets the bill's cost.
    TextView costText = (TextView) findViewById(R.id.amountOfBillForViewBill);
    BigDecimal parsed = BigDecimal.valueOf((long) bill.getAmount(), 0);

    final double cost = parsed.doubleValue();
    costText.setText(getString(R.string.view_bill_cost, cost));

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
