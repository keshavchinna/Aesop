package com.example.HomeInventory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
  String[] smartHubs = {"Home", "Office"};
  String[] items = {"Milk", "Coffee", "Sugar"};
  private TextView showFamilyMembers;
  private TextView itemName;
  private ProgressBar itemProgressBar;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.inventory_dashboard);
    //listView = (ListView) findViewById(R.id.list_smarthub);
    showFamilyMembers = (TextView) findViewById(R.id.show_family_members_list);
    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);
    for (int j = 0; j <2; j++) {
      LinearLayout customLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_layout, null);
      TextView smartHubName = (TextView) customLayout.findViewById(R.id.smart_hub_name);
      smartHubName.setText(smartHubs[j]);
      for (int i = 0; i < 2; i++) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.custom, null);
        itemName = (TextView) v.findViewById(R.id.item_name);
        itemProgressBar = (ProgressBar) v.findViewById(R.id.item_progress);
        itemProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.green_color));
        itemProgressBar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            callItemDetails();
          }
        });
        itemName.setText(items[i]);
        customLayout.addView(v);
      }
      linearLayout.addView(customLayout);
      LinearLayout space = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.space, null);
      linearLayout.addView(space);
    }
    showFamilyMembers.setOnClickListener(this);
    getActionBar().setTitle("Inventory Data");
    User user = new Gson().fromJson(getIntent().getStringExtra("user"), User.class);
    new WebserviceHelper(this).execute("http://premapp.azure-mobile.net/tables/smarthub?" +
        "$filter=(user_id+eq+" + user.getID() + ")");
  }

  private void callItemDetails() {
    Intent intent = new Intent(this, UsageActivity.class);
    startActivity(intent);
  }

  @Override
  public void callback(String json) {
    if (json != null) {
      SmartHub[] smartHubs = new Gson().fromJson(json, SmartHub[].class);
      //listView.setAdapter(new SmartHubAdapter(smartHubs, this));
    } else {
      Toast.makeText(this, "Problem connection to server", Toast.LENGTH_SHORT).show();
    }
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

  private class SmartHubAdapter extends BaseAdapter {
    private SmartHub[] smartHubs;
    private Context context;

    public SmartHubAdapter(SmartHub[] smartHubs, Context context) {
      this.smartHubs = smartHubs;
      this.context = context;
    }

    @Override
    public int getCount() {
      return smartHubs.length;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getItem(int position) {
      return position;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getItemId(int position) {
      return 0;  //To change body of implemented methods use File | Settings | File Templates.
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


}
