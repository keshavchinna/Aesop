package com.bizconit.aesop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import com.google.gson.Gson;
import com.jjoe64.graphview.*;

import java.text.SimpleDateFormat;
import java.util.Date;

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

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.graph_layout);
    dates = getIntent().getStringArrayExtra("dates");
    values = getIntent().getIntArrayExtra("values");
    productName = getIntent().getStringExtra("productName");
    getActionBar().setTitle(productName);
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
    rootView.addView(graphView);
  }
}
