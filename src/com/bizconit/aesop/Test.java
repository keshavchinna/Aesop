package com.bizconit.aesop;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 22/9/14
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {
  public static void main(String[] args) {
    Date date = getPublishedAt("2014-08-18T13:54:45.936");
   // System.out.println(date.getTime());
    //System.out.println(new Date(date.getTime()));
    // System.out.println(new Date();
   System.out.println(getDateTimeLocation());

  }

  public static java.util.Date getPublishedAt(String str) {
    java.util.Date timeStamp = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    // format.setTimeZone(TimeZone.getTimeZone("GMT"));
    try {
      // str = str.replace("00:00", "0000");
      timeStamp = format.parse(str);
    } catch (Exception e) {
      System.out.println("Exception:" + e.getMessage());
    }
    return timeStamp;
  }

  private static String getDateTimeLocation() {
    PrettyTime timeStamp = new PrettyTime();
    return timeStamp.format(new Date(System.currentTimeMillis()-(System.currentTimeMillis()-getPublishedAt("2014-08-18T13:54:45.936").getTime())));
  }

}
