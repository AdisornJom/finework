/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.jsf.controller.billing;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author Lenovo
 */
public class NewClass {
    public static void main(String[] args){
//        BigDecimal a = new BigDecimal(198*0.07);
//System.out.println("asdfasdfafda=>"+a.doubleValue());
//      BigDecimal b = a.setScale(2, BigDecimal.ROUND_UP);
//        System.out.println("aaaaaaaaaaaaaaaaa=>"+b);
//      BigDecimal c = a.setScale(2, BigDecimal.ROUND_CEILING);
//        System.out.println("bbbbbbbbbbbbbbbbb=>"+c);
//        
//      BigDecimal d = a.setScale(2, BigDecimal.ROUND_DOWN);
//        System.out.println("ccccccccccccccccc=>"+d);
//      BigDecimal  e=a.setScale(2, BigDecimal.ROUND_FLOOR);
//        System.out.println("ddddddddddddddddd=>"+e);
//      BigDecimal  f=a.setScale(2, BigDecimal.ROUND_HALF_DOWN);
//        System.out.println("eeeeeeeeeeeeeeeee=>"+f);
//     BigDecimal  g= a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
//        System.out.println("fffffffffffffffff=>"+g);
//        
//     BigDecimal h=  a.setScale(2, BigDecimal.ROUND_HALF_UP);
//        System.out.println("ggggggggggggggggg=>"+h);

        Calendar BangkokTime = new GregorianCalendar();
        Calendar c2 = Calendar.getInstance();
       // BangkokTime.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
        BangkokTime.setTimeInMillis(System.currentTimeMillis());
       // BangkokTime.getTime();
        System.out.println(new SimpleDateFormat("dd/MM/yyyy",new Locale("th", "TH")).format(BangkokTime.getTime()));

// fast but correct
Object[] data = {"1" };
        System.out.println(String.format("%1$05d", 1));
         DecimalFormat myFormatter = new DecimalFormat("0000");
      String output = myFormatter.format(1);
      System.out.println(1 + "  " + "0000" + "  " + output);
        
        String result = String.format("%1$05d %2$05d", 1, 1 + 10);
	    System.out.println(result);
    }
}
