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

    Callback callback;

    public WebserviceHelper(Callback callback) {
        this.callback = callback;
    }


    @Override
    protected Object doInBackground(Object... params) {
        String url = (String) params[0];
        Log.d("test1:", "Url: " + url);
        StringBuffer string = new StringBuffer();
        String line = null;
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            BufferedReader result = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));

            while ((line = result.readLine()) != null) {
                string.append(line);
            }
            line = new String(string);
            Log.d("test1:", line);
        } catch (Exception e) {
            line = null;
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return line;
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        callback.callback((String) o);
    }
}
