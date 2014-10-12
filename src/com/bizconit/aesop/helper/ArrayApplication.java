package com.bizconit.aesop.helper;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import org.json.JSONArray;
import org.json.JSONException;

public class ArrayApplication extends Application {

    static final String TAG = "JavascriptDataDemo";

    int[] data = new int[11];
    int[] data1 = new int[11];
    int[] data3 = new int[11];
    int[] data2 = new int[]{11};

    /**
     * This passes our data out to the JS
     */
    @JavascriptInterface
    public String getData1() {
        Log.d(TAG, "getData() called");
        return a1dToJson(data2, data3).toString();
    }

    @JavascriptInterface
    public String getData() {
        Log.d(TAG, "getData() called");
        return a1dToJson(data, data1).toString();
    }

    /**
     * Allow the JavaScript to pass some data in to us.
     */
    @JavascriptInterface
    public void setData(String newData) throws JSONException {
        Log.d("test2", newData);
        Log.d(TAG, "MainActivity.setData()");
        JSONArray streamer = new JSONArray(newData);
        data = new int[streamer.length()];
        data1 = new int[streamer.length()];
        for (int i = 0; i < streamer.length(); i++) {
            String n = streamer.getString(i);
            String[] sarray = n.replace("[", "").replace("]", "").split(",");
            data[i] = Integer.parseInt(sarray[0]);
            data1[i] = Integer.parseInt(sarray[1]);
        }
    }

    @JavascriptInterface
    public void setData1(String newData) throws JSONException {
        Log.d("test2", newData);
        Log.d(TAG, "MainActivity.setData()");
        JSONArray streamer = new JSONArray(newData);
        data2 = new int[streamer.length()];
        data3 = new int[streamer.length()];
        for (int i = 0; i < streamer.length(); i++) {
            String n = streamer.getString(i);
            String[] sarray = n.replace("[", "").replace("]", "").split(",");
            data2[i] = Integer.parseInt(sarray[0]);
            data3[i] = Integer.parseInt(sarray[1]);
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
        Log.d(TAG, "ArrayApplication.finish()");
        activity.finish();
    }

    /**
     * Sorry for not using the standard org.json.JSONArray but even in Android 4.2 it lacks
     * the JSONArray(Object[]) constructor, making it too painful to use.
     *
     * @param data
     * @param doubles
     */
    public String a1dToJson(int[] data, int[] doubles) {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < doubles.length; i++) {
            sb.append("[");
            int d = data[i];
            int d1 = doubles[i];

            sb.append(d);
            sb.append(",");
            sb.append(d1);
            sb.append("]");
            if ((i + 1) < doubles.length)
                sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}
