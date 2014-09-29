package com.bizconit.aesop;

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
import com.example.homeinventory.R;
import com.google.gson.Gson;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 19/9/14
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class UsageActivity extends Activity implements View.OnClickListener, Callback {
  private ListView itemUsageListView;
  private Button orderButton;
  private Inventory[] inventories;
  private TextView noDataFound;
  private ProgressBar usageDataLoading;
  private String productName;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.usage_view);
    productName = getIntent().getStringExtra("productName");
    getActionBar().setTitle(productName);
    itemUsageListView = (ListView) findViewById(R.id.item_usage_list_view);
    orderButton = (Button) findViewById(R.id.order_button);
    noDataFound = (TextView) findViewById(R.id.no_data_found);
    usageDataLoading = (ProgressBar) findViewById(R.id.usage_loading);
    usageDataLoading.setVisibility(View.VISIBLE);
    new WebserviceHelper(getApplicationContext(), this, "productDetails").execute("https://aesop.azure-mobile.net/tables/inventory?" +
        "$filter=(sensor_id+eq+'" + getIntent().getStringExtra("sensorId") + "')&__systemProperties=updatedAt&$orderby=inserted_at%20desc");
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
    Intent intent = new Intent(this, AvailablePortalsActivity.class);
    intent.putExtra("productName", productName);
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
      orderButton.setVisibility(View.VISIBLE);
      inventories = new Gson().fromJson(json, Inventory[].class);
      if (inventories.length > 0) {
        usageDataLoading.setVisibility(View.GONE);
        itemUsageListView.setAdapter(new ItemUsageListAdapter(getApplicationContext()));
      } else {
        usageDataLoading.setVisibility(View.GONE);
        orderButton.setVisibility(View.GONE);
        noDataFound.setVisibility(View.VISIBLE);
      }
    } else {
      orderButton.setVisibility(View.GONE);
      usageDataLoading.setVisibility(View.GONE);
      Toast toast = Toast.makeText(this, "No Sensors Found", Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
      toast.show();
    }
  }
  //inventoryLoading.setVisibility(View.GONE);

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

    public java.util.Date getPublishedAt(String str) {
      java.util.Date timeStamp = new Date();
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
      // format.setTimeZone(TimeZone.getTimeZone("IST"));
      try {
        str = str.replace("00:00", "0000");
        timeStamp = format.parse(str);

      } catch (Exception e) {
      }
      return timeStamp;
    }

    private String getDateTimeLocation(Date date) {
      PrettyTime timeStamp = new PrettyTime();
      return timeStamp.format(new Date(System.currentTimeMillis() - (System.currentTimeMillis() - date.getTime())));

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View view = inflater.inflate(R.layout.usage_child_layout, null);
      TextView date = (TextView) view.findViewById(R.id.date);
      TextView amountPercent = (TextView) view.findViewById(R.id.amount_percent);
//      date.setText(getPublishedAt(inventories[position].get__updatedAt()) + "");
      Log.d("test4", "date:" + inventories[position].get__updatedAt());
      date.setText(getDateTimeLocation(getPublishedAt(inventories[position].getInserted_at().replace("Z", ""))));
      amountPercent.setText("" + inventories[position].getValue());
      return view;
    }
  }
}
