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
import io.realm.RealmResults;

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

  private AlertDialog alertDialog;
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

    alertDialog = new AlertDialog.Builder(AddGroupActivity.this).create();
    alertDialog.setTitle("User does not exist!");
    alertDialog.setMessage("Please check your spelling and try again. Remember the user must have logged in before to be added to a group.");
    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
        new DialogInterface.OnClickListener() {
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
    public void submitGroup(View view) {
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

          for (User userInList : userList) {
            User user = realm.where(User.class).equalTo("email",
                userInList.getEmail()).findFirst();

            Group group = realm.createObject(Group.class);
            group.setName(groupName);
            group.setDescription(groupDescription);
            for (User currentUser : userList) {
              group.addUser(currentUser);
            }
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
    public void submitUser(View view) {
      // Gets new user name.
      EditText userEditField = (EditText) findViewById(R.id.addUser);
      final String userName = userEditField.getText().toString();


      // Submits user to database.
      realm.executeTransaction(new Realm.Transaction() {
        @Override
        public void execute(Realm realm) {

          User user = realm.where(User.class).equalTo("email", userName).findFirst();

          if (user == null) {
            alertDialog.show();
          } else {
            System.out.println("IT WAS NOT NULL");
            userList.add(user);
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
}