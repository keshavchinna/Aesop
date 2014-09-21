package com.example.HomeInventory;

import android.os.AsyncTask;
import android.util.Log;

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
  Callback callback;

  public WebserviceHelper(Callback callback, String tableName) {
    this.callback = callback;
    this.tableName = tableName;
  }

  @Override
  protected Object doInBackground(Object... params) {
    String url = (String) params[0];
    Log.d("test2:", "Url: " + url);
    StringBuffer string = new StringBuffer();
    String line = null;
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
    return line;
  }

  @Override
  protected void onPostExecute(Object o) {
    super.onPostExecute(o);
    switch (tableName) {
      case "user":
        callback.userCallBack((String) o);
        break;
      case "smarthub":
        callback.smartHubCallBack((String) o);
        break;
      case "inventory":
        callback.inventoryCallBack((String) o);
        break;
    }
  }
}
