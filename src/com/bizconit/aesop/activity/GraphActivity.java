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
    getIntentValues();
    getActionBar().setTitle(productName);
    final WebView webView = (WebView) this.findViewById(R.id.webview);
    Application application = setWebviewProperties(webView);
    getConsumptionValues();
    int[] index = getXaxisValues();
    ((ArrayApplication) application).setActivity(this);
    setGraphData((ArrayApplication) application, index);
    webView.setWebViewClient(getClient(webView));
    webView.loadUrl("file:///android_asset/my.html");
  }

  private void setGraphData(ArrayApplication application, int[] index) {
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
  }

  private int[] getXaxisValues() {
    int[] index = new int[inventory.length];
    for (int i = 0; i < inventory.length; i++) {
      index[i] = i;
    }
    return index;
  }

  private WebViewClient getClient(final WebView webView) {
    return new WebViewClient() {
      @Override
      public void onPageStarted(WebView view, String url, Bitmap favicon) {
        webView.loadUrl("javascript:showData()");
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        webView.loadUrl("javascript:showData()");

      }
    };
  }

  private void getConsumptionValues() {
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
  }

  private Application setWebviewProperties(WebView webView) {
    WebSettings webSettings = webView.getSettings();
    webSettings.setJavaScriptEnabled(true);
    Application application = getApplication();
    webView.requestFocusFromTouch();
    webView.setWebViewClient(new WebViewClient());
    webView.setWebChromeClient(new WebChromeClient());
    webView.addJavascriptInterface(application, "android");
    return application;
  }

  private void getIntentValues() {
    dates = getIntent().getStringArrayExtra("dates");
    inventory = getIntent().getIntArrayExtra("values");
    productName = getIntent().getStringExtra("productName");
  }
}


