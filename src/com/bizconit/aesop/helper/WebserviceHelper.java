package com.bizconit.aesop.helper;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import com.bizconit.aesop.R;
import com.bizconit.aesop.model.Inventory;
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
        StringBuffer string = new StringBuffer();
        String line = null;
        if (Network.isConnected(context)) {
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
                BufferedReader result = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line = result.readLine()) != null) {
                    string.append(line);
                }
                line = new String(string);
            } catch (Exception e) {
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
            showToastMessage();
        } else {
            switch (tableName) {
                case "user":
                    callback.userCallBack((String) o);
                    break;
                case "smarthub":
                    callback.smartHubCallBack((String) o);
                    break;
                case "inventory":
                    populateData((String) o);
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

    private void showToastMessage() {
        Toast toast = Toast.makeText(context, "Please Check Network Connection", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void populateData(String o) {
        if ((String) o != null) {
            if (!((String) o).isEmpty()) {
                Inventory[] inventories = new Gson().fromJson((String) o, Inventory[].class);
                if (inventories.length > 0) {
                    progressBar.setProgress(inventories[0].getValue());
                    progressBar.setText(inventories[0].getValue() + "%");
                    progressBar.setTextColor(Color.WHITE);
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
    }
}
