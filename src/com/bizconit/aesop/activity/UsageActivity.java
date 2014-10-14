package com.bizconit.aesop.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bizconit.aesop.R;
import com.bizconit.aesop.helper.Callback;
import com.bizconit.aesop.helper.WebserviceHelper;
import com.bizconit.aesop.model.Inventory;
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
  private Button graphButton;
  private int[] values;
  private String[] dates;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.usage_view);
    productName = getIntent().getStringExtra("productName");
    getActionBar().setTitle(productName);
    getWidgets();
    new WebserviceHelper(getApplicationContext(), this, "productDetails").execute("https://aesop.azure-mobile.net/tables/inventory?" +
        "$filter=(sensor_id+eq+'" + getIntent().getStringExtra("sensorId") + "')&__systemProperties=updatedAt&$orderby=inserted_at%20desc");
  }

  private void getWidgets() {
    itemUsageListView = (ListView) findViewById(R.id.item_usage_list_view);
    orderButton = (Button) findViewById(R.id.order_button);
    graphButton = (Button) findViewById(R.id.view_graph);
    noDataFound = (TextView) findViewById(R.id.no_data_found);
    usageDataLoading = (ProgressBar) findViewById(R.id.usage_loading);
    usageDataLoading.setVisibility(View.VISIBLE);
    orderButton.setOnClickListener(this);
    graphButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    switch (id) {
      case R.id.order_button:
        callOrdersPortals();
        break;
      case R.id.view_graph:
        callGraphActivity();
        break;
    }
  }

  private void callGraphActivity() {
    Intent intent = new Intent(this, GraphActivity.class);
    intent.putExtra("productName", productName);
    intent.putExtra("values", values);
    intent.putExtra("dates", dates);
    startActivity(intent);
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
    if (json != null && !json.isEmpty()) {
      orderButton.setVisibility(View.VISIBLE);
      inventories = new Gson().fromJson(json, Inventory[].class);
      if (inventories.length > 0) {
        usageDataLoading.setVisibility(View.GONE);
        getInventoryData();
        itemUsageListView.setAdapter(new ItemUsageListAdapter(getApplicationContext()));
      } else {
        usageDataLoading.setVisibility(View.GONE);
        orderButton.setVisibility(View.GONE);
        noDataFound.setVisibility(View.VISIBLE);
      }
    } else {
      orderButton.setVisibility(View.GONE);
      usageDataLoading.setVisibility(View.GONE);
      showToastMessage("No Sensors Found");
    }
  }

  private void getInventoryData() {
    int i = 0;
    int size = inventories.length;
    values = new int[size];
    dates = new String[size];
    for (Inventory inventory : inventories) {
      int percentage = (int) (100 * inventory.getValue() / 1000);
      values[i] = percentage;
      dates[i++] = getDateInString(getPublishedAt(inventory.getInserted_at().replace("Z", "")));
    }
  }

  private void showToastMessage(String message) {
    Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.CENTER, 0, 0);
    toast.show();
  }

  @Override
  public void sensorCallBack(String o) {
  }

  public String getDateInString(java.util.Date date) {
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
    String timeStamp = "";
    try {
      timeStamp = format.format(date);
    } catch (Exception e) {
    }
    return timeStamp;
  }

  public java.util.Date getPublishedAt(String str) {
    java.util.Date timeStamp = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    try {
      str = str.replace("00:00", "0000");
      timeStamp = format.parse(str);

    } catch (Exception e) {
    }
    return timeStamp;
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
      date.setText(getDateInString(getPublishedAt(inventories[position].getInserted_at().replace("Z", ""))));
      int percentage = (int) (100 * inventories[position].getValue() / 1000);
      amountPercent.setText("" + percentage + "%");
      return view;
    }
  }
}
