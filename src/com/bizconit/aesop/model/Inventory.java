package com.bizconit.aesop.model;

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
  private float value;
  @Expose
  private String product_name;
  @Expose
  private String product_type;
  @Expose
  private String weight;
  @Expose
  private float temperature;

  public String getWeight() {
    return weight;
  }

  public void setWeight(String weight) {
    this.weight = weight;
  }


  public float getTemperature() {
    return temperature;
  }

  public void setTemperature(float temperature) {
    this.temperature = temperature;
  }


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

  public float getValue() {
    return value;
  }

  public void setValue(float value) {
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

  public static class User {

    @SerializedName("id")
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String pin;
    @Expose
    private String family_members;

    public String getFamily_members() {
      return family_members;
    }

    public void setFamily_members(String family_members) {
      this.family_members = family_members;
    }

    @Override
    public String toString() {
      return "User{" +
          "id='" + id + '\'' +
          ", name='" + name + '\'' +
          ", pin='" + pin + '\'' +
          '}';
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getPin() {
      return pin;
    }

    public void setPin(String pin) {
      this.pin = pin;
    }

  }
}
