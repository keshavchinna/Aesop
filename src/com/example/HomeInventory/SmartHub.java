package com.example.HomeInventory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 8/9/14
 * Time: 7:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmartHub {

  @SerializedName("id")
  @Expose
  private String id;
  @Expose
  private String user_id;
  @Expose
  private String name;
  @Expose
  private String location;

  @Override
  public String toString() {
    return "SmartHub{" +
        "id='" + id + '\'' +
        ", user_id='" + user_id + '\'' +
        ", name='" + name + '\'' +
        ", location='" + location + '\'' +
        '}';
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
