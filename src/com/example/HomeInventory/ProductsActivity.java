package com.example.HomeInventory;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 8/9/14
 * Time: 7:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductsActivity extends Activity implements Callback {
  ListView listView;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.dashboard);
    listView = (ListView) findViewById(R.id.list_smarthub);
    SmartHub smartHub = new Gson().fromJson(getIntent().getStringExtra("smarthub"), SmartHub.class);
    new WebserviceHelper(this, "product").execute("http://premapp.azure-mobile.net/tables/inventory?" +
        "$filter=(smarthub_id+eq+" + smartHub.getId() + ")");

  }

  /*@Override
  public void callback(String json) {

    if (json != null) {
      Inventory[] inventories = new Gson().fromJson(json, Inventory[].class);
      listView.setAdapter(new SmartHubAdapter(inventories, this));
    } else {
      Toast.makeText(this, "Problem connection to server", Toast.LENGTH_SHORT).show();
    }
  }
*/
  @Override
  public void userCallBack(String o) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void smartHubCallBack(String o) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void inventoryCallBack(String o) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  private class SmartHubAdapter extends BaseAdapter {
    private Inventory[] inventories;
    private Context context;

    public SmartHubAdapter(Inventory[] inventories, Context context) {
      this.inventories = inventories;
      this.context = context;
    }

    @Override
    public int getCount() {
      return inventories.length;  //To change body of implemented methods use File | Settings | File Templates.
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
      textView.setText(inventories[position].getProduct_name() + " ------- " +
          inventories[position].getValue());
      textView.setClickable(true);
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, ProductsActivity.class);
//                    intent.putExtra("smarthub", new Gson().toJson(smartHubs[position]));
//                    startActivity(intent);
//                }
//            });
      return textView;
    }
  }
}
