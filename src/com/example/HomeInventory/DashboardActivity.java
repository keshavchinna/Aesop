package com.example.HomeInventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 8/9/14
 * Time: 7:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class DashboardActivity extends Activity implements Callback, View.OnClickListener {

  ListView listView;
  private TextView showFamilyMembers;
  private TextView itemName;
  private ProgressBar itemProgressBar;
  private SmartHub[] smartHubs;
  private Inventory[] inventories;
  private LinearLayout rootLinearLayout;
  private ProgressBar inventoryLoading;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.inventory_dashboard);
    getActionBar().setTitle("Inventory Data");
    getWidgetIds();
    applyActions();
    User user = new Gson().fromJson(getIntent().getStringExtra("user"), User.class);
    Log.d("test2", "userID:" + user.getId());
    inventoryLoading.setVisibility(View.VISIBLE);
    new WebserviceHelper(this, "smarthub").execute("https://aesop.azure-mobile.net/tables/smarthub?" +
        "$filter=(user_id+eq+'" + user.getId() + "')");
  }

  private void populateInventoryData(Inventory[] inventories) {
//    for (int j = 0; j < smartHubs.length; j++) {
    int k = 0;
    LinearLayout customLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_layout, null);
    TextView smartHubName = (TextView) customLayout.findViewById(R.id.smart_hub_name);
    smartHubName.setText(smartHubs[k++].getLocation());
    for (int i = 0; i < inventories.length; i++) {
      RelativeLayout v = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.custom, null);
      itemName = (TextView) v.findViewById(R.id.item_name);
      itemProgressBar = (ProgressBar) v.findViewById(R.id.item_progress);
      itemProgressBar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          callItemDetails();
        }
      });
      itemName.setText(inventories[i].getProduct_name());
      itemProgressBar.setProgress(inventories[i].getValue());
      if (inventories[i].getValue() < 20)
        itemProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.red_color));
      customLayout.addView(v);
    }
    rootLinearLayout.addView(customLayout);
    LinearLayout space = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.space, null);
    rootLinearLayout.addView(space);
//    }
  }

  private void applyActions() {
    showFamilyMembers.setOnClickListener(this);
  }

  private void getWidgetIds() {
    showFamilyMembers = (TextView) findViewById(R.id.show_family_members_list);
    rootLinearLayout = (LinearLayout) findViewById(R.id.linear);
    inventoryLoading = (ProgressBar) findViewById(R.id.inventory_loading);
  }

  private void callItemDetails() {
    Intent intent = new Intent(this, UsageActivity.class);
    startActivity(intent);
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    switch (id) {
      case R.id.show_family_members_list:
        callFamilyMembersActivity();
        break;
    }
  }

  private void callFamilyMembersActivity() {
    Intent intent = new Intent(this, FamilyMembersActivity.class);
    startActivity(intent);
  }

  @Override
  public void userCallBack(String o) {
  }

  @Override
  public void smartHubCallBack(String json) {
    if (json != null) {
      if (json.isEmpty()) {
        Log.d("test2", "SmartHUbJson:" + json);
        inventoryLoading.setVisibility(View.GONE);
        smartHubs = new Gson().fromJson(json, SmartHub[].class);
        for (SmartHub smartHub : smartHubs) {
          Log.d("test2", "SmartHub size:" + smartHubs.length);
          new WebserviceHelper(this, "inventory").execute("https://aesop.azure-mobile.net/tables/inventory?" +
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
      inventories = new Gson().fromJson(json, Inventory[].class);
      populateInventoryData(inventories);
    } else {
      Toast.makeText(this, "Problem connection to server", Toast.LENGTH_SHORT).show();
    }
  }

 /* private class SmartHubAdapter extends BaseAdapter {
    private SmartHub[] smartHubs;
    private Context context;

    public SmartHubAdapter(SmartHub[] smartHubs, Context context) {
      this.smartHubs = smartHubs;
      this.context = context;
    }

    @Override
    public int getCount() {
      return smartHubs.length;
    }

    @Override
    public Object getItem(int position) {
      return position;
    }

    @Override
    public long getItemId(int position) {
      return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
      TextView textView = new TextView(context);
      textView.setText(smartHubs[position].getName());
      textView.setClickable(true);
      textView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(context, ProductsActivity.class);
          intent.putExtra("smarthub", new Gson().toJson(smartHubs[position]));
          startActivity(intent);
        }
      });
      return textView;
    }
  }
*/

}
