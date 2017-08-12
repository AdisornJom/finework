/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.jsf.controller.transporter;

import com.finework.core.util.DateTimeUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.joda.time.format.ISODateTimeFormat.date;

/**
 *
 * @author Lenovo
 */
public class NewClass {
    
    public static void main(String[] args){
//        String dd="10-08-2017 00:00:00";
//        
//        String start="08-08-2017 09:09:02";
//        String end="10-08-2017 09:09:02";
//        Date datetime=DateTimeUtil.stringToDate(dd, "dd-MM-yyyy HH:mm:ss");
//        Date startdate=DateTimeUtil.stringToDate(start, "dd-MM-yyyy HH:mm:ss");
//        Date enddate=DateTimeUtil.stringToDate(end, "dd-MM-yyyy HH:mm:ss");
//        
//        if((datetime.after(startdate) || datetime.equals(startdate))  && (datetime.before(enddate) || datetime.equals(enddate))){
//            System.out.println("aaa");
//        }else{
//           System.out.println("bbb");
//        }
       
        String dd="10-08-2017 20:00:01";
        
        String start="08-08-2017 09:09:02";

        Date datetime=DateTimeUtil.stringToDate(dd, "dd-MM-yyyy HH:mm:ss");
        Date startdate=DateTimeUtil.stringToDate(start, "dd-MM-yyyy HH:mm:ss");
        
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        String time=sdf.format(datetime);
        String timestart=sdf.format(startdate);
        System.out.println("time>>>>>"+time);
        int time_=Integer.parseInt(time);
        int timestart_=Integer.parseInt(timestart);
        
        if(time_ >200000){
            System.out.println("aaa");
        }else{
           System.out.println("bbb");
        }
        
    }
}
