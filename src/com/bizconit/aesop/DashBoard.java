package com.bizconit.aesop;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 30/9/14
 * Time: 12:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class DashBoard extends FragmentActivity implements
    ActionBar.TabListener {
  private ViewPager viewPager;
  private TabsPagerAdapter mAdapter;
  private ActionBar actionBar;
  private String[] tabs = {"Home", "Office"};
  private TextView itemName;
  private TextProgressBar itemProgressBar;
  private SmartHub[] smartHubs;
  private Inventory[] inventories;
  private LinearLayout rootLinearLayout;
  private ProgressBar inventoryLoading;
  private Menu menu;
  private int smartHubPosition;
  private User user;
  private Sensor[] sensors;
  private String userId;
  private SharedPreferences smartHubPref;
  private SharedPreferences.Editor editor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.dash_board);
    viewPager = (ViewPager) findViewById(R.id.pager);
    actionBar = getActionBar();
    user = new Gson().fromJson(getIntent().getStringExtra("user"), User.class);
    mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
    viewPager.setAdapter(mAdapter);
    actionBar.setHomeButtonEnabled(false);
    actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#B55856")));
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    for (String tab_name : tabs) {
      actionBar.addTab(actionBar.newTab().setText(tab_name)
          .setTabListener(this));
    }

    viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

      @Override
      public void onPageSelected(int position) {
        actionBar.setSelectedNavigationItem(position);
      }

      @Override
      public void onPageScrolled(int arg0, float arg1, int arg2) {
      }

      @Override
      public void onPageScrollStateChanged(int arg0) {
      }
    });
  }


  @Override
  public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
  }

  @Override
  public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
    viewPager.setCurrentItem(tab.getPosition());
  }

  @Override
  public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    this.menu = menu;
    MenuInflater inflater = new MenuInflater(getBaseContext());
    inflater.inflate(R.menu.dashboard_menu, menu);
    return true;
  }

  @Override
  public boolean onMenuItemSelected(int featureId, MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case R.id.refresh:
        //refreshData();
        break;
      case R.id.family_group:
        callFamilyMembersActivity();
        break;
    }
    if (item.getItemId() == R.id.refresh) {

    }
    return super.onMenuItemSelected(featureId, item);
  }


  private void callFamilyMembersActivity() {
    Intent intent = new Intent(this, FamilyMembersActivity.class);
    String[] familyMembers = null;
    if (user.getFamily_members() != null) {
      familyMembers = user.getFamily_members().split(",");
    } else
      familyMembers = new String[0];
    intent.putExtra("family_members", familyMembers);
    startActivity(intent);
  }


}


