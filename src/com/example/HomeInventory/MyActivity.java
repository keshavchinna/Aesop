package com.example.HomeInventory;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
//import com.google.gson.Gson;
//import com.parse.ParseAnalytics;

public class MyActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.inventory_dashboard);
    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);
    for (int j = 0; j < 5; j++) {
      LinearLayout customLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_layout, null);
      TextView smartHubName = (TextView) customLayout.findViewById(R.id.smart_hub_name);
      smartHubName.setText("Smart Hub " + (j + 1));
      for (int i = 0; i < 2; i++) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom, null);
        customLayout.addView(v);
      }
      linearLayout.addView(customLayout);
      LinearLayout space = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.space, null);
      linearLayout.addView(space);
    }
   /*LinearLayout v1= (LinearLayout) LayoutInflater.from(this).inflate(R.layout.temp,null);
        linearLayout.addView(v1);
*/
  }
}
