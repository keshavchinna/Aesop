package com.example.HomeInventory;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 8/9/14
 * Time: 5:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebserviceHelper extends AsyncTask {

  private final String tableName;
  private final Context context;
  Callback callback;
  private String productType;
  private TextProgressBar progressBar;
  private boolean netWorkConnected = true;

  public WebserviceHelper(Context context, Callback callback, String tableName) {
    this.context = context;
    this.callback = callback;
    this.tableName = tableName;
  }

  public WebserviceHelper(Context applicationContext, TextProgressBar itemProgressBar, String tableName, String productType) {
    this.context = applicationContext;
    this.tableName = tableName;
    progressBar = itemProgressBar;
    this.productType = productType;
  }

  @Override
  protected Object doInBackground(Object... params) {
    String url = (String) params[0];
    Log.d("test2:", "Url: " + url);
    StringBuffer string = new StringBuffer();
    String line = null;
    if (Network.isConnected(context)) {
      try {

        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        Log.d("test2:", "after open connection");
        BufferedReader result = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        Log.d("test2:", "result" + result);
        while ((line = result.readLine()) != null) {
          string.append(line);
        }
        line = new String(string);
        Log.d("test2:", "line:" + line);
      } catch (Exception e) {
        Log.d("test2", "error:" + e.getMessage());
        Log.d("test2", "error:" + e.getCause());
        line = null;
        e.printStackTrace();
      }

    } else {
      netWorkConnected = false;
    }
    return line;
  }

  @Override
  protected void onPostExecute(Object o) {
    super.onPostExecute(o);
    if (netWorkConnected == false) {
      Toast toast = Toast.makeText(context, "Please Check Network Connection", Toast.LENGTH_SHORT);
      toast.setGravity(Gravity.CENTER, 0, 0);
      toast.show();
    } else {
      switch (tableName) {
        case "user":
          callback.userCallBack((String) o);
          break;
        case "smarthub":
          callback.smartHubCallBack((String) o);
          break;
        case "inventory":

          if ((String) o != null) {
            if (!((String) o).isEmpty()) {
              Inventory[] inventories = new Gson().fromJson((String) o, Inventory[].class);
              if (inventories.length > 0) {
                Log.d("test3", "sensorValue: " + inventories[0].getValue());

                progressBar.setProgress(inventories[0].getValue());
                progressBar.setText(inventories[0].getValue() + "%");
                progressBar.setTextColor(Color.WHITE);
                /*if (inventories[0].getValue() == 0)
                  progressBar.setText(inventories[0].getValue() + "%");*/
                if (productType.equalsIgnoreCase("unit")) {
                  if (inventories[0].getValue() < 10)
                    progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.red_color));
                } else {
                  if (inventories[0].getValue() < 20)
                    progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.red_color));
                }
              }
            }
          }
          break;
        case "productDetails":
          callback.inventoryCallBack((String) o);
          break;
        case "sensor":
          callback.sensorCallBack((String) o);
          break;
      }
    }
  }
}
