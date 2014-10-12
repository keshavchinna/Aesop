package com.bizconit.aesop.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
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
    private int[] values;
    private String productName;
    private GraphView.GraphViewData[] graphData;

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);
        dates = getIntent().getStringArrayExtra("dates");
        values = getIntent().getIntArrayExtra("values");
        productName = getIntent().getStringExtra("productName");
        getActionBar().setTitle(productName);

        final WebView webView = (WebView) this.findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        Application application = getApplication();
        webView.addJavascriptInterface(application, "android");
        ((ArrayApplication) application).setActivity(this);
        String s = ((ArrayApplication) application).a1dToJson(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, new int[]{45, 87, 32, 85, 75, 41, 36, 87, 25, 65});
        String s1 = ((ArrayApplication) application).a1dToJson(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, new int[]{40, 25, 86, 32, 45, 20, 92, 56, 95, 50});
        Log.d("test2", "String S: " + s);
        try {
            ((ArrayApplication) application).setData(s);
            ((ArrayApplication) application).setData1(s1);
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
    /*
LinearLayout rootView = (LinearLayout) findViewById(R.id.root);
graphData = new GraphView.GraphViewData[values.length];
        for (int i = 0; i < values.length; i++) {
        graphData[i] = new GraphView.GraphViewData(i + 1, values[i]);
        }
        GraphViewSeries exampleSeries1 = new GraphViewSeries("Consumption", new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(90, 250, 00), 3), new GraphView.GraphViewData[]{
        new GraphView.GraphViewData(1, 52)
        , new GraphView.GraphViewData(2, 36)
        , new GraphView.GraphViewData(3, 45)
        , new GraphView.GraphViewData(4, 68)
        , new GraphView.GraphViewData(5, 76)
        , new GraphView.GraphViewData(6, 32)
        , new GraphView.GraphViewData(7, 42)
        , new GraphView.GraphViewData(8, 88)
        , new GraphView.GraphViewData(9, 89)
        });
        GraphViewSeries.GraphViewSeriesStyle seriesStyle = new GraphViewSeries.GraphViewSeriesStyle();
        GraphViewSeries exampleSeries2 = new GraphViewSeries("Consumption", new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(153, 51, 255), 3), new GraphView.GraphViewData[]{
        new GraphView.GraphViewData(1, 26)
        , new GraphView.GraphViewData(2, 35)
        , new GraphView.GraphViewData(3, 45)
        , new GraphView.GraphViewData(4, 87)
        , new GraphView.GraphViewData(5, 36)
        , new GraphView.GraphViewData(6, 20)
        , new GraphView.GraphViewData(7, 85)
        , new GraphView.GraphViewData(8, 92)
        , new GraphView.GraphViewData(9, 46)
        });
        GraphViewSeries exampleSeries = new GraphViewSeries(productName, new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(200, 50, 00), 3), graphData);
        LineGraphView graphView = new LineGraphView(this, productName + " Inventory");

        graphView.addSeries(exampleSeries);
        graphView.addSeries(exampleSeries1);
        graphView.addSeries(exampleSeries2);

        graphView.setManualYAxisBounds(100, 0);
        graphView.setHorizontalLabels(dates);
        graphView.setDrawDataPoints(true);
        graphView.setScalable(true);
        graphView.setShowLegend(true);
        graphView.setLegendAlign(GraphView.LegendAlign.TOP);
        graphView.setScrollable(true);
        graphView.getGraphViewStyle().setVerticalLabelsAlign(Paint.Align.LEFT);
        graphView.setDataPointsRadius(5.0f);
        graphView.setDrawBackground(true);
        graphView.getGraphViewStyle().setGridColor(Color.RED);
//    graphView.setBackgroundColor(Color.GREEN);
        graphView.getGraphViewStyle().setTextSize(14);
        graphView.getGraphViewStyle().getGridStyle().drawHorizontal();
        graphView.getGraphViewStyle().setNumHorizontalLabels(dates.length);
        graphView.getGraphViewStyle().setNumVerticalLabels(11);
        rootView.addView(graphView);*/
}


