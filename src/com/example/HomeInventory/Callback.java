package com.example.HomeInventory;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 8/9/14
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Callback {
  public void userCallBack(String o);

  public void smartHubCallBack(String o);

  public void inventoryCallBack(String o);

  public void sensorCallBack(String o);
}
