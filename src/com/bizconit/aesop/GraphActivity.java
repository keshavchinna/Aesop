package com.bizconit.aesop;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.example.homeinventory.R;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;

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
    // init example series data
    GraphViewSeries exampleSeries = new GraphViewSeries(new GraphView.GraphViewData[]{
        new GraphView.GraphViewData(10, 22.0d)
        , new GraphView.GraphViewData(20, 11.5d)
        , new GraphView.GraphViewData(30, 29.5d)
        , new GraphView.GraphViewData(40, 100.0d)
    });

    GraphView graphView = new BarGraphView(this, "Inventory Consumption");
    graphView.setVerticalLabels(new String[]{"100", "90", "80", "70", "60", "50", "40", "30", "20", "10", "0"});
    graphView.setHorizontalLabels(new String[]{"10-AUG-14", "15-AUG-14", "20-AUG-14", "25-AUG-14", "30-AUG-14"});
    graphView.setHorizontalScrollBarEnabled(true);
//    graphView.getGraphViewStyle().setGridColor(Color.GREEN);
//    graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.YELLOW);
//    graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
//    graphView.getGraphViewStyle().setTextSize(getResources().getDimension(R.dimen.big));
    graphView.getGraphViewStyle().setNumHorizontalLabels(5);
    graphView.getGraphViewStyle().setNumVerticalLabels(11);


    graphView.addSeries(exampleSeries); // data
    rootView.addView(graphView);


  }
}
