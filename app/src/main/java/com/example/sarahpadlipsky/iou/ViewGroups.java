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
import io.realm.RealmResults;

/**
 * Represents the main activity that shows the list of groups that the user is apart of.
 * @author sarahpadlipsky
 * @version October 28, 2016
 */
public class ViewGroups extends ListActivity {

  // Database connection
  private Realm realm;

  /**
   * Android lifecycle function. Called when activity is opened for the first time.
   * @param savedInstanceState Lifecycle parameter
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.view_groups_activity);
    realm = Realm.getDefaultInstance();

    // Gets current user.
    User user = CurrentUser.getCurrentUser();
    ArrayAdapter<Group> adapter = new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_1, user.getGroups());

    // Sets click listener for list items.
    ListView listView = getListView();
    listView.setOnItemClickListener(
        new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Group currentGroup = (Group) parent.getItemAtPosition(position);
            Intent newActivity = new Intent(view.getContext(), ViewGroup.class);
            // Send group name to next intent for querying purposes.
            newActivity.putExtra(getString(R.string.group_name), currentGroup.getName());
            realm.close();
            startActivity(newActivity);
          }
        });
    setListAdapter(adapter);
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
    //Sets title of main page
    TextView text = (TextView) findViewById(R.id.username);
    User user = CurrentUser.getCurrentUser();
    text.setText(user.getName() + getString(R.string.main_title));
  }

  /**
   * Android lifecycle function. Called when activity is paused.
   */
  @Override
  protected void onStop() {
    super.onStop();
    realm.close();
  }

  /**
   * On-Click method for "Add Group" button"
   */
  public void createGroup(View view) {
    Intent newActivity = new Intent(this, AddGroupActivity.class);
    startActivity(newActivity);
  }

  /**
   * On-Click method for "Add Group" button"
   */
  public void login(View view) {
    Intent newActivity = new Intent(this, Login.class);
    startActivity(newActivity);
  }

  // TODO: Delete method.
  /**
   * Delete method to clear groups and users while developing.
   */
  private void delete() {
    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        RealmResults<Group> result = realm.where(Group.class).findAll();
        result.deleteAllFromRealm();
      }
    });

    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        RealmResults<User> result = realm.where(User.class).findAll();
        result.deleteAllFromRealm();
      }
    });
  }
}
