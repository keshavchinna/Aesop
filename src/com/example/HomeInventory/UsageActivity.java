package com.example.HomeInventory;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 19/9/14
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class UsageActivity extends Activity {
  String[] usageDates = {"sep 19 2014", "36%", "used 15%"};
  private ListView itemUsageListView;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.usage_view);
    getActionBar().setTitle("Family Members");
    itemUsageListView = (ListView) findViewById(R.id.item_usage_list_view);
    itemUsageListView.setAdapter(new ItemUsageListAdapter(getApplicationContext()));
  }

  private class ItemUsageListAdapter extends BaseAdapter {
    Context context;

    public ItemUsageListAdapter(Context applicationContext) {
      context = applicationContext;
    }

    @Override
    public int getCount() {
      return usageDates.length;
    }

    @Override
    public Object getItem(int position) {
      return usageDates[position];
    }

    @Override
    public long getItemId(int position) {
      return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View view = inflater.inflate(R.layout.family_members_child_layout, null);
      TextView name = (TextView) view.findViewById(R.id.family_member_name);
      name.setText(usageDates[position]);
      return view;
    }
  }
}
