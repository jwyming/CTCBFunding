package com.eds.ctcb;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: dz06tx
 * Date: 2007-9-29
 * Time: 9:53:42
 * To change this template use File | Settings | File Templates.
 */
public class KelvinTest {
  public static void main(String[] args) {
    Calendar cal = Calendar.getInstance();
    System.out.println("day1 = " + cal.get(Calendar.DAY_OF_MONTH));
    System.out.println("day2 = " + cal.get(Calendar.DATE));
    System.out.println("day3 = " + cal.get(Calendar.DAY_OF_YEAR));
      System.out.println(cal.get(Calendar.MONTH));
  }

}
