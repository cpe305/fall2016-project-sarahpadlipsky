package com.example.sarahpadlipsky.iou;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Represents the activity that shows the calculated IOU.
 * @author sarahpadlipsky
 * @version October 30, 2016
 */
public class CalculateIOU extends ListActivity {

  // Database connection.
  private Realm realm;
  private Group group;
  private ArrayList<User> under = new ArrayList<>();
  private ArrayList<User> over = new ArrayList<>();
  private ArrayList<String> toPrint = new ArrayList<>();
  private double eachPerson = 0;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.calculate_iou_activity);

    realm= Realm.getDefaultInstance();

    // Gets group name from main activity.
    Intent intent = getIntent();
    String id = intent.getStringExtra(getString(R.string.group_id_field));
    group = realm.where(Group.class).contains(getString(R.string.group_id_field), id).findFirst();

    RealmList<User> users = group.getUsers();

    calculateTotalSpent(users);

    eachPerson = group.getMoneySpent()/users.size();

    makeLists(users);

    payBack();

    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_1, toPrint);

    setListAdapter(adapter);
  }

  public void calculateTotalSpent(RealmList<User> users) {

    double totalAmount = 0;
    for (User user : users) {
      totalAmount += user.getMoneySpent();
    }

    final double forRealm = totalAmount;

    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        group.setMoneySpent(forRealm);
      }
    });
  }

  public void makeLists(RealmList<User> users) {

    for (final User user : users) {

      if (user.getMoneySpent() >= eachPerson) {

        realm.executeTransaction(new Realm.Transaction() {
          @Override
          public void execute(Realm realm) {
            user.setMoneyOwed(user.getMoneySpent() - eachPerson);
          }
        });
        over.add(user);
      }
      else {
        under.add(user);
      }
    }
  }

  public void payBack() {

    int overList = over.size();
    int count = 0;
    for (User underUser : under ) {

      double amountOwed = eachPerson - underUser.getMoneySpent();

        for (int i = count; i < overList; i++) {

          final User currentOverUser = over.get(count);

          if(currentOverUser.getMoneyOwed() > amountOwed) {
            currentOverUser.setMoneyOwed(currentOverUser.getMoneyOwed()- amountOwed);
            toPrint.add(underUser.getName() + " owes " + currentOverUser.getName() + " $" + amountOwed);
            count = i;
            break;
          }
          else {
            amountOwed -= currentOverUser.getMoneyOwed();
            toPrint.add(underUser.getName() + " owes " + currentOverUser.getName() + " $" + currentOverUser.getMoneyOwed());

            realm.executeTransaction(new Realm.Transaction() {
              @Override
              public void execute(Realm realm) {
                currentOverUser.setMoneyOwed(0);

              }
            });
          }
      }

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
