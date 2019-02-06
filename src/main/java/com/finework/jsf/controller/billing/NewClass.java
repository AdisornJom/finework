/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.jsf.controller.billing;

import com.finework.core.util.DateTimeUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

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

//        Calendar BangkokTime = new GregorianCalendar();
//        Calendar c2 = Calendar.getInstance();
//       // BangkokTime.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
//        BangkokTime.setTimeInMillis(System.currentTimeMillis());
//       // BangkokTime.getTime();
//        System.out.println(new SimpleDateFormat("dd/MM/yyyy",new Locale("th", "TH")).format(BangkokTime.getTime()));

//       HashMap<String,String> months_ = new HashMap<>();
//        TextStyle ts = TextStyle.FULL;
//        Locale l = new Locale("th", "TH");
//        for ( Month mo : Month.values () ) {
//            NumberFormat numberFormatter = new DecimalFormat("00");
//            String result = numberFormatter.format(mo.getValue ());
//            String monthName = mo.getDisplayName ( ts ,l);
//            months_.put(result, monthName);
//           // System.out.println ( "month: " + month + " | monthNumber: " + result + " | monthName: " + monthName );
//        }
//        TreeMap<String, String> sorted = new TreeMap<>(); 
//        sorted.putAll(months_); 
//        for (Map.Entry<String, String> entry : sorted.entrySet())  
//            System.out.println("Key = " + entry.getKey() +  
//                         ", Value = " + entry.getValue());
//        }

    
//// fast but correct
//        Object[] data = {"1"};
//        System.out.println(String.format("%1$05d", 1));
//        DecimalFormat myFormatter = new DecimalFormat("0000");
//        String output = myFormatter.format(1);
//        System.out.println(1 + "  " + "0000" + "  " + output);
//
//        String result = String.format("%1$05d %2$05d", 1, 1 + 10);
//        System.out.println(result);

        Calendar cal1 = new GregorianCalendar(Locale.US);
        cal1.setTime(DateTimeUtil.getSystemDate());
        cal1.set(Calendar.DAY_OF_MONTH, 1);
        cal1.add(Calendar.MONTH, -1);
        System.out.println(cal1.getTime());

        Calendar cal2 = new GregorianCalendar(Locale.US);
        cal2.setTime(DateTimeUtil.getSystemDate());
        cal2.set(Calendar.DAY_OF_MONTH, cal2.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal2.add(Calendar.MONTH, +1);
        System.out.println(cal2.getTime());
    }
}
