package com.bizconit.aesop;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.bizconit.aesop.model.Inventory;
import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 30/9/14
 * Time: 12:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class DashBoard extends FragmentActivity implements ActionBar.TabListener {
  private ViewPager viewPager;
  private TabsPagerAdapter mAdapter;
  private ActionBar actionBar;
  private String[] tabs = {"Home", "Office"};
  private Inventory.User user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.dash_board);
    viewPager = (ViewPager) findViewById(R.id.pager);
    user = new Gson().fromJson(getIntent().getStringExtra("user"), Inventory.User.class);
    mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
    viewPager.setAdapter(mAdapter);
    applyActionbarProperties();
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

  private void applyActionbarProperties() {
    actionBar = getActionBar();
    actionBar.setHomeButtonEnabled(true);
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#B55856")));
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
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
    MenuInflater inflater = new MenuInflater(getBaseContext());
    inflater.inflate(R.menu.dashboard_menu, menu);
    return true;
  }

  @Override
  public boolean onMenuItemSelected(int featureId, MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case R.id.family_group:
        callFamilyMembersActivity();
        break;
      case android.R.id.home:
        this.finish();
        break;
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


