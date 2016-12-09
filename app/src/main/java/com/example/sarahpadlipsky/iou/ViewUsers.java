package com.example.sarahpadlipsky.iou;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Represents the activity that displays all users.
 * @author sarahpadlipsky
 * @version December 8, 2016
 */
public class ViewUsers extends Activity {

  // Database connection.
  private Realm realm;
  // Current user.
  private User currentUser;
  // Current group.
  private Group group;
  //Unique list of users.
  HashSet<User> uniqueUsers = new HashSet<>();
  RealmList<User> listOfUniqueUsers = new RealmList<>();
  // Users who have spent more than their share.
  private ArrayList<User> under = new ArrayList<>();
  // Users who have spent less than their share.
  private ArrayList<User> over = new ArrayList<>();
  HashMap<String, Double> hashMap = new HashMap<>();
  // Money each person owes.
  private double eachPerson = 0;

  /**
   * Android lifecycle function. Called when activity is opened for the first time.
   * @param savedInstanceState Lifecycle parameter
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.view_users_activity);
    realm = Realm.getDefaultInstance();

    currentUser = CurrentUser.getCurrentUser();
    getUniqueUsers();

    ListView listView =  (ListView) findViewById(R.id.list);

    Iterator iterator = uniqueUsers.iterator();

    while (iterator.hasNext()){
      listOfUniqueUsers.add((User)iterator.next());
    }

    populateHashMap();

    for (Group currentGroup: currentUser.getGroups()) {
      group = currentGroup;
      RealmList<User> users = currentGroup.getUsers();
      calculateEachUser(users);
      calculateTotalSpent(users);
      eachPerson = group.getMoneySpent()/users.size();
      makeLists(users);
      payBack();
    }

    for (final User user : listOfUniqueUsers) {
      final double amount = hashMap.get(user.getName());
      realm.executeTransaction(new Realm.Transaction() {
        @Override
        public void execute(Realm realm) {
          user.setMoneySpent(amount);
        }
      });

    }

    ViewGroupAdapter adapter = new ViewGroupAdapter(this, listOfUniqueUsers);

    listView.setAdapter(adapter);

  }

  /**
   * Populates HashMap.
   */
  public void populateHashMap() {
    for (User user : listOfUniqueUsers) {
      hashMap.put(user.getName(), 0.0);
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

        if (currentOverUser.getMoneyOwed() > amountOwed) {
          currentOverUser.setMoneyOwed(currentOverUser.getMoneyOwed() - amountOwed);

          if (underUser.equals(currentUser)) {
            double amount = hashMap.get(currentOverUser.getName());
            hashMap.put(currentOverUser.getName(), amount + amountOwed);
          } else if (currentOverUser.equals(currentUser)) {

            double amount = hashMap.get(underUser.getName());
            hashMap.put(underUser.getName(), amount - amountOwed);
          }
          count = i;
          break;
        } else {
          amountOwed -= currentOverUser.getMoneyOwed();
          if (underUser.equals(currentUser)) {
            Double amount = hashMap.get(currentOverUser.getName());
            hashMap.put(currentOverUser.getName(), amount + currentOverUser.getMoneyOwed());
          } else if (currentOverUser.equals(currentUser)) {
            Double amount = hashMap.get(underUser.getName());
            hashMap.put(underUser.getName(), amount - currentOverUser.getMoneyOwed());
          }
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
   * Gets list of unique users that the current user interacts with.
   */
  public void getUniqueUsers() {

    for (Group currentGroup : currentUser.getGroups()) {

      RealmList<User> users = currentGroup.getUsers();

      for (User user : users) {
        if (!user.equals(currentUser))
          uniqueUsers.add(user);
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
