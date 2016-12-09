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
 * @version November 24, 2016
 */
public class CalculateIOU extends ListActivity {

  // Database connection.
  private Realm realm;
  // Current group.
  private Group group;
  // Users who have spent more than their share.
  private ArrayList<User> under = new ArrayList<>();
  // Users who have spent less than their share.
  private ArrayList<User> over = new ArrayList<>();
  // List to show who owes who money.
  private ArrayList<String> toPrint = new ArrayList<>();
  // Money each person owes.
  private double eachPerson = 0;

  /**
   * Android lifecycle function. Called when activity is opened for the first time.
   * @param savedInstanceState Lifecycle parameter
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.calculate_iou_activity);

    realm= Realm.getDefaultInstance();

    // Gets group name from main activity.
    Intent intent = getIntent();
    String id = intent.getStringExtra(getString(R.string.group_id_field));
    group = realm.where(Group.class).contains(getString(R.string.group_id_field), id).findFirst();

    // Users for current group.
    RealmList<User> users = group.getUsers();

    calculateEachUser(users);

    calculateTotalSpent(users);

    eachPerson = group.getMoneySpent()/users.size();

    makeLists(users);

    payBack();

    if (toPrint.isEmpty()) {
      toPrint.add(getString(R.string.calculate_iou_empty_owe_list));
    }

    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_1, toPrint);

    setListAdapter(adapter);
  }

  /**
   * Calculates how much each user owns given current bills.
   * @param users List of users in the group
   */
  public void calculateEachUser(RealmList<User> users) {

    // Sets all users money spent to 0.
    for (final User user : users) {
      realm.executeTransaction(new Realm.Transaction() {
        @Override
        public void execute(Realm realm) {
          user.setMoneySpent(0);

        }
      });
    }

    for (final Bill bill : group.getBills()) {

      final User sendUser = bill.getSendUser();
      final User receiveUser = bill.getReceiveUser();


      if (bill.getPayBackBill()) {
        realm.executeTransaction(new Realm.Transaction() {
          @Override
          public void execute(Realm realm) {
            sendUser.setMoneySpent(sendUser.getMoneySpent() + bill.getAmount());
            receiveUser.setMoneySpent(receiveUser.getMoneySpent() - bill.getAmount());
          }
        });
      } else {
        realm.executeTransaction(new Realm.Transaction() {
          @Override
          public void execute(Realm realm) {
            sendUser.setMoneySpent(sendUser.getMoneySpent() + bill.getAmount());
          }
        });
      }

    }

  }

  /**
   * Calculates how much the group spent
   * @param users List of users in the group
   */
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

  /**
   * Separates users into over and under groups.
   * @param users List of users in the group
   */
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

  /**
   * Calculates who owes who money.
   */
  public void payBack() {

    int overList = over.size();
    int count = 0;
    for (User underUser : under) {

      double amountOwed = eachPerson - underUser.getMoneySpent();

        for (int i = count; i < overList; i++) {

          final User currentOverUser = over.get(count);

          if(currentOverUser.getMoneyOwed() > amountOwed) {
            currentOverUser.setMoneyOwed(currentOverUser.getMoneyOwed()- amountOwed);
            toPrint.add(underUser.getName() + " owes " + currentOverUser.getName() + " $" +
                String.format("%.2f", amountOwed));
            count = i;
            break;
          }
          else {
            toPrint.add(underUser.getName() + " owes " + currentOverUser.getName() + " $" +
                String.format("%.2f", currentOverUser.getMoneyOwed()));

            realm.executeTransaction(new Realm.Transaction() {
              @Override
              public void execute(Realm realm) {
                currentOverUser.setMoneyOwed(0);
              }
            });
            break;
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
