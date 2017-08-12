/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.jsf.controller.report;

import com.finework.core.util.DateTimeUtil;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author Lenovo
 */
public class NewClass {
    public static void main(String[] args) throws ParseException{
       Date start=DateTimeUtil.stringToDate("01/01/2017", "dd/MM/yyyy");
       Date end= DateTimeUtil.stringToDate("01/05/2017", "dd/MM/yyyy");
       System.out.println("Date:>>"+getMonthsDifference(start,end));
       System.out.println("getMonthsBetweenDates:>>"+getMonthsBetweenDates(start,end));
       System.out.println("getNumberOfMonths:>>"+getNumberOfMonths(start,end));
       
//      DateTime dateOfBirth = new DateTime(1988, 7, 4, 0, 0, GregorianChronology.getInstance());
//      DateTime currentDate = new DateTime();
//      Days diffInDays = Days.daysBetween(dateOfBirth, currentDate);
//       System.out.println("diffInDays:>>"+diffInDays);
//      Hours diffInHours = Hours.hoursBetween(dateOfBirth, currentDate);
//       System.out.println("diffInHours:>>"+diffInHours);
//      Minutes diffInMinutes = Minutes.minutesBetween(dateOfBirth, currentDate);
//       System.out.println("diffInMinutes:>>"+diffInMinutes);
//      Seconds seconds = Seconds.secondsBetween(dateOfBirth, currentDate);
//       System.out.println("seconds:>>"+seconds);
       
       
        //DateTime x = new DateTime().withDate(2009,12,20); // doomsday lol
//        DateTime x = new DateTime(end.getTime());
//
//        Months d = Months.monthsBetween( new DateTime(), x);
//        int monthsDiff = d.getMonths();
//        System.out.println("monthsDiff:>>"+monthsDiff);
    }
    
    public static int getNumberOfMonths(Date fromDate, Date toDate) {
    int monthCount = 0;
    Calendar cal = Calendar.getInstance(Locale.US);
    cal.setTime(fromDate);
    int c1date = cal.get(Calendar.DATE);
    int c1month = cal.get(Calendar.MONTH);
    int c1year = cal.get(Calendar.YEAR);
    cal.setTime(toDate);
    int c2date = cal.get(Calendar.DATE);
    int c2month = cal.get(Calendar.MONTH);
    int c2year = cal.get(Calendar.YEAR);
    //System.out.println(" c1date:"+c1date+" month:"+c1month+" year:"+c1year);
    //System.out.println(" c2date:"+c2date+" month:"+c2month+" year:"+c2year);
    GregorianCalendar grCal = new GregorianCalendar(Locale.US);
    boolean isLeapYear1 = grCal.isLeapYear(c1year);
    boolean isLeapYear2 = grCal.isLeapYear(c2year);
    monthCount = ((c2year - c1year) * 12) + (c2month - c1month);
    if(isLeapYear2 && c2month == 1 && c2date == 29){
        monthCount = monthCount+ ((c1date == 28)?0:1);
    }else if(isLeapYear1 && c1month == 1 && c1date == 29){
        monthCount = monthCount+ ((c2date == 28)?0:1);
    }else{
        monthCount = monthCount+ ((c2date >= c1date)?0:1);
    }
    return monthCount;

}
    
    public static int getMonthsBetweenDates(Date startDate, Date endDate) {
        if (startDate.getTime() > endDate.getTime()) {
            Date temp = startDate;
            startDate = endDate;
            endDate = temp;
        }
        Calendar startCalendar = Calendar.getInstance(Locale.US);
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance(Locale.US);
        endCalendar.setTime(endDate);

        int yearDiff = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int monthsBetween = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH) + 12 * yearDiff;

        if (endCalendar.get(Calendar.DAY_OF_MONTH) >= startCalendar.get(Calendar.DAY_OF_MONTH)) {
            monthsBetween = monthsBetween + 1;
        }
        
        return monthsBetween;

    }

    
    public static final int getMonthsDifference(Date date1, Date date2) {
        int m1 = date1.getYear() * 12 + date1.getMonth();
        int m2 = date2.getYear() * 12 + date2.getMonth();
        return m2 - m1 + 1;
    }

}
