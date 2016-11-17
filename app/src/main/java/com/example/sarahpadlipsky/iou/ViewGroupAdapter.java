package com.example.sarahpadlipsky.iou;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import io.realm.RealmList;

class ViewGroupAdapter extends BaseAdapter {

  Context context;
  RealmList<User> data;
  private static LayoutInflater inflater = null;

  public ViewGroupAdapter(Context context, RealmList<User> data) {
    this.context = context;
    this.data = data;
    inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public User getItem(int position) {
    return data.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View vi = convertView;
    if (vi == null)
      vi = inflater.inflate(R.layout.view_groups_linear_layout, null);
    TextView userName = (TextView) vi.findViewById(R.id.userName);
    TextView moneyOwed = (TextView) vi.findViewById(R.id.moneyOwed);

    userName.setText(data.get(position).getName());
    moneyOwed.setText(Double.toString(data.get(position).getMoneySpent()));

    return vi;
  }

}