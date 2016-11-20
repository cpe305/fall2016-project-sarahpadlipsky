package com.example.sarahpadlipsky.iou;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.math.BigDecimal;

import io.realm.RealmList;

class ViewGroupAdapter extends BaseAdapter {

  // Current list of users.
  private RealmList<User> users;
  // Current layout inflater.
  private static LayoutInflater inflater = null;

  public ViewGroupAdapter(Context context, RealmList<User> data) {
    this.users = data;
    inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  /**
   * @return The number of users
   */
  @Override
  public int getCount() {
    return users.size();
  }

  /**
   * @return A user at given position
   */
  @Override
  public User getItem(int position) {
    return users.get(position);
  }

  /**
   * @return Given position of item
   */
  @Override
  public long getItemId(int position) {
    return position;
  }

  /**
   * Makes current view for the items in the list view
   * @param position Position of given item
   * @param convertView Current view
   * @param parent Refers to the parent
   */
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View vi = convertView;
    if (vi == null)
      vi = inflater.inflate(R.layout.view_groups_linear_layout, null);
    TextView userName = (TextView) vi.findViewById(R.id.userName);
    TextView moneyOwed = (TextView) vi.findViewById(R.id.moneyOwed);

    userName.setText(users.get(position).getName());
    String moneySpent = Double.toString(users.get(position).getMoneySpent());
    BigDecimal parsed = new BigDecimal(moneySpent).setScale(2,BigDecimal.ROUND_FLOOR);
    final double cost = parsed.doubleValue();

    moneyOwed.setText("$" + String.valueOf(cost));

    return vi;
  }

}