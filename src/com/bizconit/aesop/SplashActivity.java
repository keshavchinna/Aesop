package com.bizconit.aesop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 22/9/14
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class SplashActivity extends Activity {
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash_screen);
    TelephonyManager te = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
    Log.d("test111", "Operator:" + te.getSimOperator());
    Log.d("test111", "serialNumber:" + te.getSimSerialNumber());
    Log.d("test111", "DeviceID:" + te.getDeviceId());
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        callDashboard();
      }
    }, 1500);
  }

  private void callDashboard() {
    Intent intent = new Intent(this, LoginActivity.class);
    startActivity(intent);
  }

}
