package com.example.sarahpadlipsky.iou;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Represents the activity that takes care of creating a group.
 * @author sarahpadlipsky
 * @version October 29, 2016
 */
public class AddGroupActivity extends ListActivity {

  // Database connection.
  private Realm realm;
  // List of users for new activity.
  private RealmList<User> userList;

  // Alert if the user does not exist in the database.
  private AlertDialog alertDialogDB;

  // Alert if the user already exsits in the list.
  private AlertDialog alertDialogList;

  /**
   * Android lifecycle function. Called when activity is opened for the first time.
   * @param savedInstanceState Lifecycle parameter
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_group_activity);

    realm = Realm.getDefaultInstance();

    userList = new RealmList<>();
    userList.add(CurrentUser.getCurrentUser());

    ArrayAdapter<User>  adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, userList);

    setListAdapter(adapter);

    alertDialogDB = new AlertDialog.Builder(AddGroupActivity.this).create();
    alertDialogDB.setTitle(getString(R.string.user_does_not_exist_title));
    alertDialogDB.setMessage(getString(R.string.user_does_not_exist_message));
    alertDialogDB.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
          }
        });

    alertDialogList = new AlertDialog.Builder(AddGroupActivity.this).create();
    alertDialogList.setTitle(getString(R.string.user_already_exists_title));
    alertDialogList.setMessage(getString(R.string.user_already_exists_message));
    alertDialogList.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
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

    /**
     * On-Click method for "Add Group" button"
     */
    public void submitGroup() {
      // Gets the name of the group.
      EditText nameEditField = (EditText) findViewById(R.id.nameOfGroup);
      final String groupName = nameEditField.getText().toString();
      // Gets the description of the group.
      EditText descriptionEditField = (EditText) findViewById(R.id.descriptionOfGroup);
      final String groupDescription = descriptionEditField.getText().toString();

      // Submits information to database.
      realm.executeTransaction(new Realm.Transaction() {
        @Override
        public void execute(Realm realm) {

          String num = Long.toString(realm.where(Group.class).count());
          Group group = realm.createObject(Group.class);
          group.setName(groupName);
          group.setDescription(groupDescription);
          group.setId(num);
          for (User currentUser : userList) {
            group.addUser(currentUser);
          }

          for (User userInList : userList) {
            User user = realm.where(User.class).equalTo(getString(R.string.user_email_field),
                userInList.getEmail()).findFirst();
            user.addGroup(group);
          }
        }
      });

      // Sends back to group list.
      Intent newActivity = new Intent(this, ViewGroups.class);
      startActivity(newActivity);
    }

    /**
     * On-Click method for "Add User" button"
     */
    public void submitUser() {
      // Gets new user name.
      EditText userEditField = (EditText) findViewById(R.id.addUser);
      final String userName = userEditField.getText().toString();


      // Submits user to database.
      realm.executeTransaction(new Realm.Transaction() {
        @Override
        public void execute(Realm realm) {
          User user = realm.where(User.class).equalTo(getString(R.string.user_email_field),
              userName).findFirst();
          // If the user does not exist:
          if (user == null) {
            alertDialogDB.show();
          } else {
            boolean alreadyExists = false;
            // Check if the user has been added.
            for (User currentUser : userList) {
              if (user.equals(currentUser)) {
                alreadyExists = true;
              }
            }
            if (!alreadyExists)
              userList.add(user);
            else
              alertDialogList.show();
          }
        }
      });
      // Updates list adapter.
      ArrayAdapter<User>  adapter = new ArrayAdapter<>(this,
          android.R.layout.simple_list_item_1, userList);
      setListAdapter(adapter);
      // Clears user field.
      userEditField.setText("");
    }

  /**
   * On-Click method for various buttons"
   */
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.submitGroup:
        submitGroup();
        break;
      case R.id.submitUser:
        submitUser();
        break;
      default:
        break;
    }
  }
}