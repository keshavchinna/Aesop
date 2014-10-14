

package com.bizconit.aesop.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.bizconit.aesop.support.FamilyTabsPagerAdapter;
import com.bizconit.aesop.R;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 30/9/14
 * Time: 12:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class FamilyMemberDataActivity extends FragmentActivity implements
    ActionBar.TabListener {
  private ViewPager viewPager;
  private FamilyTabsPagerAdapter mAdapter;
  private ActionBar actionBar;
  private String[] tabs = {"Home", "Office"};
  private Menu menu;
  private int smartHubPosition;
  private String userName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.show_family_members_data);
    viewPager = (ViewPager) findViewById(R.id.pager1);
    userName = getIntent().getStringExtra("familyMemberName");
    mAdapter = new FamilyTabsPagerAdapter(getSupportFragmentManager());
    viewPager.setAdapter(mAdapter);
    setActionBarProperties();
    viewPager.setOnPageChangeListener(getListener());
  }

  private void setActionBarProperties() {
    actionBar = getActionBar();
    actionBar.setHomeButtonEnabled(false);
    actionBar.setTitle(userName);
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    for (String tab_name : tabs) {
      actionBar.addTab(actionBar.newTab().setText(tab_name)
          .setTabListener(this));
    }
  }

  private ViewPager.OnPageChangeListener getListener() {
    return new ViewPager.OnPageChangeListener() {

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
    };
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
    menu.getItem(0).setVisible(false);
    return true;
  }

  @Override
  public boolean onMenuItemSelected(int featureId, MenuItem item) {
    int id = item.getItemId();
    switch (id) {
    }
    return super.onMenuItemSelected(featureId, item);
  }
}
