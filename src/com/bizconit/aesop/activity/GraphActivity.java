package com.bizconit.aesop.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.bizconit.aesop.R;
import com.bizconit.aesop.helper.ArrayApplication;
import com.jjoe64.graphview.GraphView;
import org.json.JSONException;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 29/9/14
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class GraphActivity extends Activity {
  private String[] dates;
  private int[] inventory;
  private int[] consumption;
  private String productName;
  private GraphView.GraphViewData[] graphData;

  @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.graph_layout);
    dates = getIntent().getStringArrayExtra("dates");
    inventory = getIntent().getIntArrayExtra("values");
    productName = getIntent().getStringExtra("productName");
    getActionBar().setTitle(productName);
    final WebView webView = (WebView) this.findViewById(R.id.webview);
    WebSettings webSettings = webView.getSettings();
    webSettings.setJavaScriptEnabled(true);
    Application application = getApplication();
    webView.addJavascriptInterface(application, "android");
    consumption = new int[inventory.length];
    for (int i = 0; i < inventory.length; i++) {
      if (i > 0) {
        int data = inventory[i] - inventory[i - 1];
        if (data > 0) {
          consumption[i] = data;
        } else {
          consumption[i] = 100-inventory[i-1];
        }
      }
    }
    int[] index = new int[inventory.length];
    for (int i = 0; i < inventory.length; i++) {
      index[i] = i;
    }
    ((ArrayApplication) application).setActivity(this);
    String inventoryJson = ((ArrayApplication) application).arraysToJson(index, inventory);
    String consumptionJson = ((ArrayApplication) application).arraysToJson(index, consumption);
    String datesJson = ((ArrayApplication) application).setXaxisLabelsToJson(index, dates);
    try {
      ((ArrayApplication) application).setInventoryData(inventoryJson);
      ((ArrayApplication) application).setConsumptionData(consumptionJson);
      ((ArrayApplication) application).setXaxisLabelsData(datesJson);

    } catch (JSONException e) {
      e.printStackTrace();
    }
    webView.requestFocusFromTouch();
    webView.setWebViewClient(new WebViewClient());
    webView.setWebChromeClient(new WebChromeClient());
    webView.setWebViewClient(new WebViewClient() {
      @Override
      public void onPageStarted(WebView view, String url, Bitmap favicon) {
        webView.loadUrl("javascript:showData()");
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        webView.loadUrl("javascript:showData()");

      }
    });
    webView.loadUrl("file:///android_asset/my.html");
  }
}


