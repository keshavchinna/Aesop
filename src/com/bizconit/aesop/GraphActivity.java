package com.bizconit.aesop;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 29/9/14
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class GraphActivity extends Activity {
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.graph_layout);
    LinearLayout rootView = (LinearLayout) findViewById(R.id.root);
    GraphViewSeries inventory = new GraphViewSeries("Inventory", new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(200, 50, 00), 3), new GraphView.GraphViewData[]{
        new GraphView.GraphViewData(1, 2.0d)
        , new GraphView.GraphViewData(2, 1.5d)
        , new GraphView.GraphViewData(3, 2.5d)
        , new GraphView.GraphViewData(4, 1.2d)
    });

    GraphViewSeries consumption = new GraphViewSeries("Consumption", new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(90, 250, 00), 3),
        new GraphView.GraphViewData[]{
            new GraphView.GraphViewData(1, 1.6d)
            , new GraphView.GraphViewData(2, 1.5d)
            , new GraphView.GraphViewData(3, 2.0d)
            , new GraphView.GraphViewData(4, 1.0d)
        });
    LineGraphView graphView = new LineGraphView(this, "My Line Graph");
// add data
    graphView.addSeries(inventory);
    graphView.setBackgroundColor(Color.CYAN);
    graphView.addSeries(consumption);
    graphView.setDrawBackground(true);
    // graphView.addSeries(seriesRnd);
// optional - set view port, start=2, size=10
    graphView.setViewPort(0, 3);
    graphView.setScalable(true);
// optional - legend
    graphView.setLegendAlign(GraphView.LegendAlign.BOTTOM);
    graphView.setLegendWidth(300);
    graphView.setShowLegend(true);


    rootView.addView(graphView);


  }
}
