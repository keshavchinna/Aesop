package com.bizconit.aesop;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ehc on 30/9/14.
 */
public class FamilyTabsPagerAdapter extends FragmentPagerAdapter {

  public FamilyTabsPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int index) {

    switch (index) {
      case 0:
        return new FamilyHomeFragment();
      case 1:
        return new FamilyOfficeFragment();
    }

    return null;
  }

  @Override
  public int getCount() {
    // get item count - equal to number of tabs
    return 2;
  }

}
