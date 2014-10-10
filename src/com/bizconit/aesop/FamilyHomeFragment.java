package com.bizconit.aesop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 8/9/14
 * Time: 10:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class FamilyHomeFragment extends Fragment implements Callback {
  private TextView forgotPassword;
  private Button loginButton;
  private TextView showFamilyMembers;
  private TextView itemName;
  private TextProgressBar itemProgressBar;
  private SmartHub[] smartHubs;
  private Inventory[] inventories;
  private LinearLayout rootLinearLayout;
  private ProgressBar inventoryLoading;
  private Menu menu;
  private int smartHubPosition;
  private User user;
  private Sensor[] sensors;
  private String userId;
  private TextView noSensorsFound;
  private String userName;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.inventory_dashboard, null);
    getWidgetIds(view);
    setHasOptionsMenu(true);
    userId =getActivity().getIntent().getStringExtra("userID");
    userName =getActivity().getIntent().getStringExtra("familyMemberName");
    getActivity().getActionBar().setTitle(userName);
    inventoryLoading.setVisibility(View.VISIBLE);
    callSmartHubWebservice(userId);
    return view;
  }

  private void callSmartHubWebservice(String userId) {
    new WebserviceHelper(getActivity().getApplicationContext(), this, "smarthub").execute("https://aesop.azure-mobile.net/tables/smarthub?" +
        "$filter=(user_id+eq+'" + userId + "')");
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    Log.d("test4", "in fragment");
    int id = item.getItemId();
    switch (id) {
      case R.id.refresh:
        refreshData();
        break;
    }
    return true;
  }

  private void refreshData() {
    inventoryLoading.setVisibility(View.VISIBLE);
    smartHubPosition = 0;
    rootLinearLayout.removeAllViewsInLayout();
    callSmartHubWebservice(userId);
  }

  private void getWidgetIds(View view) {
    inventoryLoading = (ProgressBar) view.findViewById(R.id.inventory_loading);
    noSensorsFound = (TextView) view.findViewById(R.id.no_sensors_found);
  }


  @Override
  public void userCallBack(String o) {
  }

  @Override
  public void smartHubCallBack(String json) {
    if (json != null) {
      if (!json.isEmpty()) {
        smartHubs = new Gson().fromJson(json, SmartHub[].class);
        int i = 0;
        for (SmartHub smartHub : smartHubs) {
          if (smartHub.getLocation().equalsIgnoreCase("home")) {
            smartHubPosition = i;
            new WebserviceHelper(getActivity().getApplicationContext(), this, "sensor").execute("https://aesop.azure-mobile.net/tables/sensor?" +
                "$filter=(smarthub_id+eq+'" + smartHub.getId() + "')");
            break;
          }
          i++;
        }
      } else {
        Toast toast = Toast.makeText(getActivity(), "No SmartHubs Found", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        inventoryLoading.setVisibility(View.GONE);
      }
    } else {
      inventoryLoading.setVisibility(View.GONE);
      Toast toast = Toast.makeText(getActivity(), "Problem Connecting to Server", Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
      toast.show();
    }
  }

  @Override
  public void inventoryCallBack(String json) {
    if (json != null) {
      if (!json.isEmpty()) {
        inventories = new Gson().fromJson(json, Inventory[].class);
      } else {
        Toast toast = Toast.makeText(getActivity(), "No Sensors Found", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
      }
    } else {
      Toast toast = Toast.makeText(getActivity(), "Problem Connecting to Server", Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
      toast.show();
    }
    inventoryLoading.setVisibility(View.GONE);
  }

  @Override
  public void sensorCallBack(String o) {
    if (o != null) {
      if (!o.isEmpty()) {
        sensors = new Gson().fromJson(o, Sensor[].class);
        populateInventoryData(sensors);
      } else {
        Toast toast = Toast.makeText(getActivity(), "Problem Connecting to Server", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
      }
      inventoryLoading.setVisibility(View.GONE);
    }
  }

  private void populateInventoryData(final Sensor[] sensors) {
    LinearLayout customLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.custom_layout, null);
    TextView smartHubName = (TextView) customLayout.findViewById(R.id.smart_hub_name);
    smartHubName.setText(smartHubs[smartHubPosition].getName().toUpperCase());
    //smartHubPosition++;
    for (int i = 0; i < sensors.length; i++) {
      final int temp = i;
      RelativeLayout v = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.sensors_data_list_view_child, null);
      itemName = (TextView) v.findViewById(R.id.item_name);
      itemProgressBar = (TextProgressBar) v.findViewById(R.id.item_progress);
      final int finalI = i;
      itemProgressBar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          callItemDetails(sensors[temp]);
        }
      });
      itemName.setText(sensors[i].getProduct_name());
      new WebserviceHelper(getActivity().getApplicationContext(), itemProgressBar, "inventory", sensors[0].getProduct_type()).execute("https://aesop.azure-mobile.net/tables/inventory?" +
          "$filter=(sensor_id+eq+'" + sensors[i].getId() + "')&__systemProperties=updatedAt&$orderby=inserted_at%20desc");
      customLayout.addView(v);
    }
    if (sensors.length == 0) {
      noSensorsFound.setVisibility(View.VISIBLE);
      rootLinearLayout.addView(customLayout, 0);
    } else {
      noSensorsFound.setVisibility(View.GONE);
      rootLinearLayout.addView(customLayout);
      LinearLayout space = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.space, null);
      rootLinearLayout.addView(space);
    }
  }

  private void callItemDetails(Sensor sensor) {
    Intent intent = new Intent(getActivity(), UsageActivity.class);
    intent.putExtra("productName", sensor.getProduct_name());
    intent.putExtra("sensorId", sensor.getId());
    startActivity(intent);
  }
}
