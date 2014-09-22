package com.example.HomeInventory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 19/9/14
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class UsageActivity extends Activity implements View.OnClickListener, Callback {
  String[] usageDates = {"Present", "sep 19 2014", "sep 18 2014", "sep 17 2014", "sep 16 2014"};
  String[] amountPercentage = {"20%", "36%", "48%", "65%", "85%"};
  String[] usedPercentage = {"", "16%", "12%", "17%", "13%"};
  private ListView itemUsageListView;
  private Button orderButton;
  private Inventory[] inventories;
  private TextView noDataFound;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.usage_view);
    getActionBar().setTitle(getIntent().getStringExtra("productName"));
    new WebserviceHelper(getApplicationContext(), this, "productDetails").execute("https://aesop.azure-mobile.net/tables/inventory?" +
        "$filter=(sensor_id+eq+'" + getIntent().getStringExtra("sensorId") + "')&__systemProperties=updatedAt&$orderby=__updatedAt%20desc");
    itemUsageListView = (ListView) findViewById(R.id.item_usage_list_view);
    orderButton = (Button) findViewById(R.id.order_button);
    noDataFound = (TextView) findViewById(R.id.no_data_found);
    orderButton.setOnClickListener(this);

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

  @Override
  public void userCallBack(String o) {
  }

  @Override
  public void smartHubCallBack(String o) {
  }

  @Override
  public void inventoryCallBack(String json) {
    Log.d("test2", "Inventory: " + json.toString());
    if (json != null && !json.isEmpty()) {
      if (!json.isEmpty()) {
        orderButton.setVisibility(View.VISIBLE);
        inventories = new Gson().fromJson(json, Inventory[].class);
        if (inventories.length > 0)
          itemUsageListView.setAdapter(new ItemUsageListAdapter(getApplicationContext()));
        else {
          orderButton.setVisibility(View.GONE);
          noDataFound.setVisibility(View.VISIBLE);
          /*Toast toast = Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT);
          toast.setGravity(Gravity.CENTER, 0, 0);
          toast.show();*/
        }
      } else {
        orderButton.setVisibility(View.GONE);
        Toast toast = Toast.makeText(this, "No Sensors Found", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
      }
    }
    //inventoryLoading.setVisibility(View.GONE);
  }

  @Override
  public void sensorCallBack(String o) {
  }

  private class ItemUsageListAdapter extends BaseAdapter {
    Context context;

    public ItemUsageListAdapter(Context applicationContext) {
      context = applicationContext;
    }

    @Override
    public int getCount() {
      return inventories.length;
    }

    @Override
    public Object getItem(int position) {
      return inventories[position];
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
      //TextView usedPercent = (TextView) view.findViewById(R.id.used_percent);
      Log.d("test3", "updatedAt: " + inventories[position].get__updatedAt());
      date.setText(inventories[position].get__updatedAt());
      amountPercent.setText("" + inventories[position].getValue());
      //  usedPercent.setText("30%");
      return view;
    }
  }
}
