package com.bizconit.aesop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
public class SmartHubHomeFragment extends Fragment implements Callback, AdapterView.OnItemClickListener {
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
  private ListView sensorsDataListView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.inventory_dashboard, null);
    getWidgetIds(view);
    setHasOptionsMenu(true);
    userId = getActivity().getIntent().getStringExtra("userID");
    userName = getActivity().getIntent().getStringExtra("userName");
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
    noSensorsFound.setVisibility(View.GONE);
    rootLinearLayout.removeAllViewsInLayout();
    callSmartHubWebservice(userId);
  }

  private void getWidgetIds(View view) {
    sensorsDataListView = (ListView) view.findViewById(R.id.sensors_data_list_view);
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
    if (sensors.length == 0) {
      noSensorsFound.setVisibility(View.VISIBLE);
    } else {
      noSensorsFound.setVisibility(View.GONE);
      Log.d("test2", "sensorsLength: " + sensors.length);
      sensorsDataListView.setAdapter(new SensorsDataListAdapter(getActivity()));
      sensorsDataListView.setOnItemClickListener(this);
    }
  }


  private void callItemDetails(int position) {
    Intent intent = new Intent(getActivity(), UsageActivity.class);
    intent.putExtra("productName", sensors[position].getProduct_name());
    intent.putExtra("sensorId", sensors[position].getId());
    startActivity(intent);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    callItemDetails(position);
  }

  private class SensorsDataListAdapter extends BaseAdapter {
    Context context;

    public SensorsDataListAdapter(Context context) {
      this.context = context;
    }

    @Override
    public int getCount() {
      return sensors.length;
    }

    @Override
    public Object getItem(int position) {
      return sensors[position];
    }

    @Override
    public long getItemId(int position) {
      return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      Log.d("test", "position:" + position);

      if (convertView == null) {
        Log.d("test", "inside null");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.sensors_data_list_view_child, parent, false);
      }
      TextView itemName = (TextView) convertView.findViewById(R.id.item_name);
      TextProgressBar itemProgressBar = (TextProgressBar) convertView.findViewById(R.id.item_progress);
      itemName.setText(sensors[position].getProduct_name());
      new WebserviceHelper(getActivity().getApplicationContext(), itemProgressBar, "inventory", sensors[0].getProduct_type()).execute("https://aesop.azure-mobile.net/tables/inventory?" +
          "$filter=(sensor_id+eq+'" + sensors[position].getId() + "')&__systemProperties=updatedAt&$orderby=inserted_at%20desc");
      return convertView;
    }
  }
}
