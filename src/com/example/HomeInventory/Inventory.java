package com.example.HomeInventory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 8/9/14
 * Time: 7:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Inventory {
  @SerializedName("id")
  @Expose
  private String id;
  @Expose
  private String smarthub_id;
  @Expose
  private String __updatedAt;
  @Expose
  private int value;
  @Expose
  private String product_name;
  @Expose
  private String product_type;

  public String getInserted_at() {
    return inserted_at;
  }

  public void setInserted_at(String inserted_at) {
    this.inserted_at = inserted_at;
  }

  @Expose
  private String inserted_at;

  public String get__updatedAt() {
    return __updatedAt;
  }

  public void set__updatedAt(String __updatedAt) {
    this.__updatedAt = __updatedAt;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Inventory{" +
        "id='" + id + '\'' +
        ", smarthub_id='" + smarthub_id + '\'' +
        ", value=" + value +
        ", product_name='" + product_name + '\'' +
        ", product_type='" + product_type + '\'' +
        '}';
  }

  public String getSmarthub_id() {
    return smarthub_id;
  }

  public void setSmarthub_id(String smarthub_id) {
    this.smarthub_id = smarthub_id;
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

  public String getProduct_type() {
    return product_type;
  }

  public void setProduct_type(String product_type) {
    this.product_type = product_type;
  }
}
