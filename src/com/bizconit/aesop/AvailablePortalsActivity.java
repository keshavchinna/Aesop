package com.bizconit.aesop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.homeinventory.R;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 19/9/14
 * Time: 6:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class AvailablePortalsActivity extends Activity implements AdapterView.OnItemClickListener {
  private ListView availablePortalsListView;
  private String[] portalsNames = {"Amazon", "easyMandi", "Home Shop18"};
  private int[] portalImages = {R.drawable.amazon, R.drawable.easymandi_logo, R.drawable.home_shop};
  private String[] portalUrls = {"http://www.amazon.in/", "http://www.easymandi.com/", "http://www.homeshop18.com/"};
  private float[] mrps = {10, 12, 9, 5, 7, 2, 4.5f, 3.95f};
  private TextView portalName;
  private ImageView portalImage;
  private TextView productMrp;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.portals_layout);
    getActionBar().setTitle("Order - " + getIntent().getStringExtra("productName"));
    getWidgetIds();
    applyActions();
    availablePortalsListView.setAdapter(new PortalsAdapter(getApplicationContext()));
  }

  private void applyActions() {
    availablePortalsListView.setOnItemClickListener(this);
  }

  private void getWidgetIds() {
    availablePortalsListView = (ListView) findViewById(R.id.available_portals_listView);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(portalUrls[position]));
    startActivity(intent);
  }

  private class PortalsAdapter extends BaseAdapter {
    private final Context context;

    public PortalsAdapter(Context applicationContext) {
      context = applicationContext;
    }

    @Override
    public int getCount() {
      return portalsNames.length;
    }

    @Override
    public Object getItem(int position) {
      return portalsNames[position];
    }

    @Override
    public long getItemId(int position) {
      return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View view = inflater.inflate(R.layout.portals_child_layout, null);
      portalName = (TextView) view.findViewById(R.id.portal_name);
      portalImage = (ImageView) view.findViewById(R.id.portal_image);
      productMrp = (TextView) view.findViewById(R.id.mrp);
      portalImage.setBackgroundResource(portalImages[position]);
      portalName.setText(portalsNames[position]);
      productMrp.setText(mrps[new Random().nextInt(mrps.length)] + " $");
      return view;
    }
  }
}
