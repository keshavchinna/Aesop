package com.bizconit.aesop.helper;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.webkit.JavascriptInterface;
import org.json.JSONArray;
import org.json.JSONException;

public class ArrayApplication extends Application {

  static final String TAG = "JavascriptDataDemo";
  int[] inventoryXaxis;
  int[] inventoryYaxis;
  int[] consumptionYaxis;
  int[] consumptionXaxis;
  int[] xAxisValues;
  String[] xAxisLabels;

  @JavascriptInterface
  public String getConsumptionData() {
    return arraysToJson(consumptionXaxis, consumptionYaxis).toString();
  }

  @JavascriptInterface
  public String getInventoryData() {
    return arraysToJson(inventoryXaxis, inventoryYaxis).toString();
  }

  @JavascriptInterface
  public String getXaxisLabels() {
    return setXaxisLabelsToJson(xAxisValues, xAxisLabels).toString();

  }

  @JavascriptInterface
  public void setInventoryData(String newData) throws JSONException {
    JSONArray streamer = new JSONArray(newData);
    inventoryXaxis = new int[streamer.length()];
    inventoryYaxis = new int[streamer.length()];
    for (int i = 0; i < streamer.length(); i++) {
      String n = streamer.getString(i);
      String[] sarray = n.replace("[", "").replace("]", "").split(",");
      inventoryXaxis[i] = Integer.parseInt(sarray[0]);
      inventoryYaxis[i] = Integer.parseInt(sarray[1]);
    }
  }

  @JavascriptInterface
  public void setConsumptionData(String newData) throws JSONException {
    JSONArray streamer = new JSONArray(newData);
    consumptionXaxis = new int[streamer.length()];
    consumptionYaxis = new int[streamer.length()];
    for (int i = 0; i < streamer.length(); i++) {
      String n = streamer.getString(i);
      String[] sarray = n.replace("[", "").replace("]", "").split(",");
      consumptionXaxis[i] = Integer.parseInt(sarray[0]);
      consumptionYaxis[i] = Integer.parseInt(sarray[1]);
    }
  }

  @JavascriptInterface
  public void setXaxisLabelsData(String newData) throws JSONException {
    JSONArray streamer = new JSONArray(newData);
    xAxisValues = new int[streamer.length()];
    xAxisLabels = new String[streamer.length()];
    for (int i = 0; i < streamer.length(); i++) {
      String n = streamer.getString(i);
      String[] sarray = n.replace("[", "").replace("]", "").split(",");
      xAxisValues[i] = Integer.parseInt(sarray[0]);
      xAxisLabels[i] = sarray[1];
    }
  }

  private Activity activity;

  public Context getActivity() {
    return activity;
  }

  public void setActivity(Activity app) {
    this.activity = app;
  }

  @JavascriptInterface
  public void finish() {
    activity.finish();
  }

  /**
   * Sorry for not using the standard org.json.JSONArray but even in Android 4.2 it lacks
   * the JSONArray(Object[]) constructor, making it too painful to use.
   *
   * @param xaxisValues
   * @param yaxisValues
   */
  public String arraysToJson(int[] xaxisValues, int[] yaxisValues) {
    StringBuffer jsonData = new StringBuffer();
    jsonData.append("[");
    for (int i = 0; i < xaxisValues.length; i++) {
      jsonData.append("[");
      int key = xaxisValues[i];
      int value = yaxisValues[i];
      jsonData.append(key);
      jsonData.append(",");
      jsonData.append(value);
      jsonData.append("]");
      if ((i + 1) < xaxisValues.length)
        jsonData.append(",");
    }
    jsonData.append("]");
    return jsonData.toString();
  }

  public String setXaxisLabelsToJson(int[] xaxisValues, String[] xaxisLabels) {
    StringBuffer jsonData = new StringBuffer();
    jsonData.append("[");
    for (int i = 0; i < xaxisValues.length; i++) {
      jsonData.append("[");
      int key = xaxisValues[i];
      String value = xaxisLabels[i];
      jsonData.append(key);
      jsonData.append(",");
      jsonData.append(value);
      jsonData.append("]");
      if ((i + 1) < xaxisValues.length)
        jsonData.append(",");
    }
    jsonData.append("]");
    return jsonData.toString();
  }
}
