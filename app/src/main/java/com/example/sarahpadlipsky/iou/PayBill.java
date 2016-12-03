package com.example.sarahpadlipsky.iou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import io.realm.Realm;

/**
 * Represents the activity that allows a user to pay an IOU.
 * @author sarahpadlipsky
 * @version October 30, 2016
 */
public class PayBill extends Activity implements AdapterView.OnItemSelectedListener {

  private Realm realm;
  private Group group;
  private User user;
  /**
   * Android lifecycle function. Called when activity is opened for the first time.
   * @param savedInstanceState Lifecycle parameter
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.pay_bill_activity);

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


}
