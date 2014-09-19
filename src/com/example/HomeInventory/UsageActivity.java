package com.example.HomeInventory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 19/9/14
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class UsageActivity extends Activity implements View.OnClickListener {
  String[] usageDates = {"Present", "sep 19 2014", "sep 18 2014", "sep 17 2014", "sep 16 2014"};
  String[] amountPercentage = {"20%", "36%", "28%", "35%", "25%"};
  String[] usedPercentage = {"", "10%", "15%", "18%", "13%"};
  private ListView itemUsageListView;
  private Button orderButton;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.usage_view);
    getActionBar().setTitle("Coffee");
    itemUsageListView = (ListView) findViewById(R.id.item_usage_list_view);
    orderButton = (Button) findViewById(R.id.order_button);
    orderButton.setOnClickListener(this);
    itemUsageListView.setAdapter(new ItemUsageListAdapter(getApplicationContext()));
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    switch (id) {
      case R.id.order_button:
        callOrdersPortals();
        break;
    }
  }

  private void callOrdersPortals() {
    Intent intent = new Intent(this, AvailablePortals.class);
    startActivity(intent);
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
      View view = inflater.inflate(R.layout.usage_child_layout, null);
      TextView date = (TextView) view.findViewById(R.id.date);
      TextView amountPercent = (TextView) view.findViewById(R.id.amount_percent);
      TextView usedPercent = (TextView) view.findViewById(R.id.used_percent);
      date.setText(usageDates[position]);
      amountPercent.setText(amountPercentage[position]);
      usedPercent.setText(usedPercentage[position]);
      return view;
    }
  }
}
