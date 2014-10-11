package com.bizconit.aesop.model;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 8/9/14
 * Time: 5:54 PM
 * To change this template use File | Settings | File Templates.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

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
