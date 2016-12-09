package com.example.sarahpadlipsky.iou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Represents the activity that displays a user's bills.
 * @author sarahpadlipsky
 * @version December 3, 2016
 */

public class UserBills extends Activity {

  // Database connection.
  private Realm realm;
  // Current user bills;
  ArrayList<Bill> currentUserBills = new ArrayList<>();
  // Current user bills;
  ArrayList<Bill> currentUserBillsPayBack = new ArrayList<>();

  /**
   * Android lifecycle function. Called when activity is opened for the first time.
   * @param savedInstanceState Lifecycle parameter
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.user_bills_activity);

    realm = Realm.getDefaultInstance();

    // Gets group name from main activity.
    Intent intent = getIntent();
    String groupID = intent.getStringExtra(getString(R.string.group_id_field));
    String email = intent.getStringExtra(getString(R.string.user_email_field));

    Group group = realm.where(Group.class).contains(getString(R.string.group_id_field),
        groupID).findFirst();

    User user = realm.where(User.class).contains(getString(R.string.user_email_field),
        email).findFirst();

    TextView text = (TextView) findViewById(R.id.titleAccount);
    text.setText(getString(R.string.user_bills_title, user.getName()));

    for (Bill currentBill : group.getBills()) {
      if (currentBill.getSendUser().getEmail().equals(user.getEmail())) {

        if (currentBill.getPayBackBill()) {
          currentUserBillsPayBack.add(currentBill);
        }
        else {
          currentUserBills.add(currentBill);
        }
      }
    }

    // Sets TextView for regular bills.
    TextView textViewRegular = (TextView) findViewById(R.id.regularBill);
    if (currentUserBills.isEmpty()) {
      textViewRegular.setText(getString(R.string.user_bills_regular_empty));
    }
    else {
      textViewRegular.setText(getString(R.string.user_bills_regular_full));
    }

    // Sets TextView IOUs.
    TextView textViewPayBack = (TextView) findViewById(R.id.payBackBill);
    if (currentUserBillsPayBack.isEmpty()) {
      textViewPayBack.setText(getString(R.string.user_bills_payback_empty));
    }
    else {
      textViewPayBack.setText(getString(R.string.user_bills_payback_full));
    }


    ListView mListView1 = (ListView)findViewById(R.id.listView1);
    ListView mListView2 = (ListView)findViewById(R.id.listView2);

    mListView1.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
        currentUserBills));
    mListView2.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
        currentUserBillsPayBack));

    ListUtils.setDynamicHeight(mListView1);
    ListUtils.setDynamicHeight(mListView2);

    mListView1.setOnItemClickListener(
        new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Bill currentBill = (Bill) parent.getItemAtPosition(position);
            Intent newActivity = new Intent(view.getContext(), ViewBill.class);
            // Send group name to next intent for querying purposes.
            newActivity.putExtra(getString(R.string.bill_id_field), currentBill.getId());
            realm.close();
            startActivity(newActivity);
          }
        });

    mListView2.setOnItemClickListener(
        new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Bill currentBill = (Bill) parent.getItemAtPosition(position);
            Intent newActivity = new Intent(view.getContext(), ViewPayBackBill.class);
            // Send group name to next intent for querying purposes.
            newActivity.putExtra(getString(R.string.bill_id_field), currentBill.getId());
            realm.close();
            startActivity(newActivity);
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
   * Takes care of dynamically adjusting the height of list views.
   */
  public static class ListUtils {
    /**
     * Dynamically sets the height of given ListView.
     * @param mListView ListView to adjust height of.
     */
     static void setDynamicHeight(ListView mListView) {
      ListAdapter mListAdapter = mListView.getAdapter();
      if (mListAdapter == null) {
        // when adapter is null
        return;
      }
      int height = 0;
      int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
      for (int i = 0; i < mListAdapter.getCount(); i++) {
        View listItem = mListAdapter.getView(i, null, mListView);
        listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
        height += listItem.getMeasuredHeight();
      }
      ViewGroup.LayoutParams params = mListView.getLayoutParams();
      params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
      mListView.setLayoutParams(params);
      mListView.requestLayout();
    }
  }

}
