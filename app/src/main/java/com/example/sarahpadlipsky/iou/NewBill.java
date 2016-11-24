package com.example.sarahpadlipsky.iou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.math.BigDecimal;

import io.realm.Realm;

/**
 * Represents a new bill that a user has made.
 * @author sarahpadlipsky
 * @version November 19, 2016
 */
public class NewBill extends Activity implements AdapterView.OnItemSelectedListener {

  // Database connection.
  private Realm realm;
  // Current group that is receiving the new bill.
  private Group group;
  // Current user who paid the new bill.
  private User user;

  /**
   * Android lifecycle function. Called when activity is opened for the first time.
   * @param savedInstanceState Lifecycle parameter
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.new_bill_activity);

    realm = Realm.getDefaultInstance();

    // Gets group name from main activity.
    Intent intent = getIntent();
    String id = intent.getStringExtra(getString(R.string.group_id_field));

    group = realm.where(Group.class).contains(getString(R.string.group_id_field), id).findFirst();

    Spinner spinner = (Spinner)findViewById(R.id.spinner);

    ArrayAdapter<User> adapter = new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_1, group.getUsers());

    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(this);
  }

  /**
   * On-Click method for items in drop down
   * @param parent Necessary parameter for function
   * @param v Current view
   * @param position Position of the item clicked
   * @param id id of the item clicked
   */
  @Override
  public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

    user = group.getUsers().get(position);
  }

  /**
   * If nothing is clicked, picks the first item
   * @param adapterView Necessary parameter for function
   */
  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

    user = group.getUsers().get(0);

  }

  /**
   * On-Click method for "Add Bill" button"
   */
  public void addBill() {
    // Gets the name of the bill.
    EditText billEditField = (EditText) findViewById(R.id.billName);
    final String billName = billEditField.getText().toString();
    // Gets the description of the bill.
    EditText descriptionEditField = (EditText) findViewById(R.id.descriptionOfBill);
    final String billDescription = descriptionEditField.getText().toString();
    // Gets the cost of the bill.
    EditText costEditField = (EditText) findViewById(R.id.costOfBill);
    final String costDescription = costEditField.getText().toString();
    BigDecimal parsed = new BigDecimal(costDescription).setScale(2,BigDecimal.ROUND_FLOOR);
    final double cost = parsed.doubleValue();


    // Submits information to database.
    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {

        long num = realm.where(Bill.class).count();
        Bill bill = realm.createObject(Bill.class);
        bill.setName(billName);
        bill.setDescription(billDescription);
        bill.setAmount(cost);
        bill.setId(Long.toString(num));
        bill.setUser(user);

        user.addBill(bill);

      }
    });

    Intent newActivity = new Intent(this, ViewGroup.class);
    newActivity.putExtra(getString(R.string.group_id_field), group.getId());
    startActivity(newActivity);
  }

  /**
   * On-Click method for various buttons"
   */
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.addBill:
        addBill();
        break;
      default:
        break;
    }
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
