package com.example.HomeInventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 22/9/14
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShowFamilyMemberData extends Activity implements Callback {

  ListView listView;
  private TextView showFamilyMembers;
  private TextView itemName;
  private ProgressBar itemProgressBar;
  private SmartHub[] smartHubs;
  private Inventory[] inventories;
  private LinearLayout rootLinearLayout;
  private ProgressBar inventoryLoading;
  private Menu menu;
  private int smartHubPosition;
  private User user;
  private Sensor[] sensors;
  private String userId;
  private String userName;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.show_family_members_data);
    getWidgetIds();
    userId = getIntent().getStringExtra("userID");
    userName = getIntent().getStringExtra("familyMemberName");
    getActionBar().setTitle(userName);
    inventoryLoading.setVisibility(View.VISIBLE);
    callSmartHubWebservice(userId);
  }

  private void callSmartHubWebservice(String user) {
    new WebserviceHelper(getApplicationContext(), this, "smarthub").execute("https://aesop.azure-mobile.net/tables/smarthub?" +
        "$filter=(user_id+eq+'" + user + "')");
  }

  private void populateInventoryData(final Sensor[] sensors) {
//    for (int j = 0; j < smartHubs.length; j++) {

    LinearLayout customLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_layout, null);
    TextView smartHubName = (TextView) customLayout.findViewById(R.id.smart_hub_name);
    smartHubName.setText(smartHubs[smartHubPosition].getName().toUpperCase() + " (" + smartHubs[smartHubPosition].getLocation().toUpperCase() + ")");
    smartHubPosition++;
    for (int i = 0; i < sensors.length; i++) {
      final int temp = i;
      RelativeLayout v = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.custom, null);
      itemName = (TextView) v.findViewById(R.id.item_name);
      itemProgressBar = (ProgressBar) v.findViewById(R.id.item_progress);
      final int finalI = i;
      itemProgressBar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          callItemDetails(sensors[temp]);
        }
      });
      itemName.setText(sensors[i].getProduct_name());
      new WebserviceHelper(getApplicationContext(), itemProgressBar, "inventory", sensors[0].getProduct_type()).execute("https://aesop.azure-mobile.net/tables/inventory?" +
          "$filter=(sensor_id+eq+'" + sensors[i].getId() + "')&__systemProperties=updatedAt&$orderby=__updatedAt%20desc");

      /*itemProgressBar.setProgress(inventories[0].getValue());
      if (inventories[0].getValue() < 20)
        itemProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.red_color));*/
      customLayout.addView(v);
    }
    rootLinearLayout.addView(customLayout);
    LinearLayout space = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.space, null);
    rootLinearLayout.addView(space);
//    }
  }

  private void getWidgetIds() {
    rootLinearLayout = (LinearLayout) findViewById(R.id.family_members_layout);
    inventoryLoading = (ProgressBar) findViewById(R.id.family_inventory_loading);
  }

  private void callItemDetails(Sensor sensor) {
    Intent intent = new Intent(this, UsageActivity.class);
    intent.putExtra("productName", sensor.getProduct_name());
    intent.putExtra("sensorId", sensor.getId());
    startActivity(intent);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    this.menu = menu;
    MenuInflater inflater = new MenuInflater(getBaseContext());
    inflater.inflate(R.menu.dashboard_menu, menu);
    return true;
  }

  @Override
  public boolean onMenuItemSelected(int featureId, MenuItem item) {
    if (item.getItemId() == R.id.refresh) {
      inventoryLoading.setVisibility(View.VISIBLE);
      smartHubPosition = 0;
      rootLinearLayout.removeAllViewsInLayout();
      callSmartHubWebservice(userId);
    }
    return super.onMenuItemSelected(featureId, item);
  }

  @Override
  public void userCallBack(String o) {
  }

  @Override
  public void smartHubCallBack(String json) {
    if (json != null) {
      if (!json.isEmpty()) {
        Log.d("test2", "SmartHUbJson:" + json);
        smartHubs = new Gson().fromJson(json, SmartHub[].class);
        for (SmartHub smartHub : smartHubs) {
          Log.d("test2", "SmartHub size:" + smartHubs.length);
          new WebserviceHelper(getApplicationContext(), this, "sensor").execute("https://aesop.azure-mobile.net/tables/sensor?" +
              "$filter=(smarthub_id+eq+'" + smartHub.getId() + "')");
        }
      } else {
        Toast toast = Toast.makeText(this, "No SmartHubs Found", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        inventoryLoading.setVisibility(View.GONE);
      }
    } else {
      inventoryLoading.setVisibility(View.GONE);
      Toast toast = Toast.makeText(this, "Problem Connecting to Server", Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
      toast.show();
    }
  }

  @Override
  public void inventoryCallBack(String json) {
    Log.d("test2", "Inventory: " + json.toString());
    if (json != null) {
      if (!json.isEmpty()) {
        inventories = new Gson().fromJson(json, Inventory[].class);
      } else {
        Toast toast = Toast.makeText(this, "No Sensors Found", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
      }
    } else {
      Toast toast = Toast.makeText(this, "Problem Connecting to Server", Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
      toast.show();
    }
    inventoryLoading.setVisibility(View.GONE);
  }

  @Override
  public void sensorCallBack(String o) {
    Log.d("test2", "sensor: " + o.toString());
    if (o != null) {
      if (!o.isEmpty()) {
        sensors = new Gson().fromJson(o, Sensor[].class);
       /* for (Sensor sensor : sensors) {
          new WebserviceHelper(getApplicationContext(), this, "inventory").execute("https://aesop.azure-mobile.net/tables/inventory?" +
              "$filter=(sensor_id+eq+'" + sensor.getId() + "')");
        }*/
        populateInventoryData(sensors);
      } else {
        Toast toast = Toast.makeText(this, "Problem Connecting to Server", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
      }
      inventoryLoading.setVisibility(View.GONE);
    }
  }
}