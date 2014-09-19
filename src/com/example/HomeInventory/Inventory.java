package com.example.HomeInventory;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 8/9/14
 * Time: 7:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Inventory {

    private int id;
    private int smarthub_id;
    private int sensor_id;
    private int value;
    private String product_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSmarthub_id() {
        return smarthub_id;
    }

    public void setSmarthub_id(int smarthub_id) {
        this.smarthub_id = smarthub_id;
    }

    public int getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(int sensor_id) {
        this.sensor_id = sensor_id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
