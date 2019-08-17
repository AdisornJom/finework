package com.finework.core.utils;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class DateTimeUtil
{
  public static final String pattern = "dd-MMM-yyyy";
  public static final String PATTERN_DB = "yyyy-MM-dd";
  public static final String PATTERN_DT_DB = "yyyy-MM-dd HH:mm:ss";
  public static final String SELECT_FROM_TIME = " 00:00:00";
  public static final String SELECT_TO_TIME = " 23:59:59";
  public static final String pattern1 = "dd-MM-yyyy";
  public static final String pattern2 = "dd/MM/yyyy";
  public static final long day = 86400000L;
  public static final long m = 60000L;
  public static final String decodeBase = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public static String dateToString(Date date, String pattern)
  {
    if (StringUtils.isBlank(pattern)) {
      pattern = "dd-MM-yyyy";
    }
    return date != null ? new SimpleDateFormat(pattern, Locale.US).format(date) : null;
  }

  public static Date stringToDate(String date, String pattern) {
    try {
      if (StringUtils.isBlank(pattern)) {
        pattern = "dd-MM-yyyy";
      }
      return date != null ? new SimpleDateFormat(pattern, Locale.US).parse(date.trim()) : null;
    } catch (ParseException localParseException) {
    }
    return null;
  }

  public static String convertLongToDateStr(long l)
  {
    DateTime dt = new DateTime(l, DateTimeZone.UTC);
    String h = dt.getHourOfDay() == 0 ? "" : String.valueOf(dt.getHourOfDay() + "h ");
    String m = dt.getMinuteOfHour() == 0 ? "" : String.valueOf(dt.getMinuteOfHour() + "m ");
    String s = dt.getSecondOfMinute() == 0 ? "" : String.valueOf(dt.getSecondOfMinute() + "s ");
    return h + m + s + dt.getMillisOfSecond() + "ms";
  }

  public static Date getSystemDate() {
    Calendar c = new GregorianCalendar(Locale.US);
    return c.getTime();
  }

  public static Date getDateLong(String l) {
    if (l == null) {
      return null;
    }
    return new Date(Long.parseLong(l));
  }

  public static Date getDateLong(long l) {
    if ((l == 0L) || (l < 0L)) {
      return null;
    }

    return new Date(l);
  }

  public static Date currentDate() {
    Calendar c = new GregorianCalendar(new Locale("th", "TH"));
    c.setTimeInMillis(System.currentTimeMillis());
    return c.getTime();
  }

  public static Date currentDate(int d) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    Locale locale = facesContext.getViewRoot().getLocale();
    Calendar c = new GregorianCalendar(locale);
    c.setTimeInMillis(System.currentTimeMillis() - d * 86400000L);
    return c.getTime();
  }

  public static Calendar calendarDate() {
    Calendar c = new GregorianCalendar(Locale.US);
    c.setTimeInMillis(System.currentTimeMillis());
    return c;
  }

  public static long currentTimeMillis() {
    return System.currentTimeMillis();
  }

  public static String dateToStringDB(Date d)
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    return sdf.format(d);
  }

  public static String strCurrentDate() {
    Calendar c = new GregorianCalendar(Locale.US);
    c.setTimeInMillis(System.currentTimeMillis());
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    return sdf.format(c.getTime());
  }

  public static String strCurrentDateOnPatternDate(String patternDate) {
    Calendar c = new GregorianCalendar(Locale.US);
    c.setTimeInMillis(System.currentTimeMillis());
    SimpleDateFormat sdf = new SimpleDateFormat(patternDate, Locale.US);
    return sdf.format(c.getTime());
  }

  public static TimeZone getTimeZone() {
    TimeZone t = TimeZone.getTimeZone("Asia/Bangkok");
    return t;
  }

  public static long calculateDays(Date dateEarly, Date dateLater) {
    return (dateLater.getTime() - dateEarly.getTime()) / 86400000L + 1L;
  }

  public static boolean checkDate(Date dateFrom, Date dateTo) {
    boolean valError = false;
    if ((dateFrom == null) || (dateTo == null)) {
      return false;
    }
    if (dateFrom.getTime() > dateTo.getTime()) {
      valError = true;
    }
    return valError;
  }

  public static String cvtDateForShow(Date sDateValue, String pattern) {
    if (sDateValue == null) {
      return "";
    }

    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    String strDateLocal = formatter.format(sDateValue);
    return strDateLocal;
  }

  public static String cvtDateForShow(Date sDateValue, String pattern, Locale locale) {
    if (sDateValue == null) {
      return "";
    }

    SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
    String strDateLocal = formatter.format(sDateValue);
    return strDateLocal;
  }

  public static String getDateTimeCode(int dTime) {
    int suffix = 0;
    String tempst = "";
    while (dTime > 61) {
      suffix = dTime % 62;
      dTime /= 62;
      tempst = decodec(suffix) + tempst;
    }
    tempst = decodec(dTime) + tempst;
    return tempst;
  }

  private static String decodec(int dd) {
    return "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(dd, dd + 1);
  }

  private static int encodec(String dd) {
    return "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(dd);
  }

  public static void main(String[] args)
  {
    System.out.println(cvtDateForShow(getSystemDate(), "dd/MM/yy", new Locale("th", "TH")));
  }

  public static int getNumberOfMonths(Date fromDate, Date toDate)
  {
    int monthCount = 0;
    Calendar cal = Calendar.getInstance(Locale.US);
    cal.setTime(fromDate);
    int c1date = cal.get(5);
    int c1month = cal.get(2);
    int c1year = cal.get(1);
    cal.setTime(toDate);
    int c2date = cal.get(5);
    int c2month = cal.get(2);
    int c2year = cal.get(1);

    GregorianCalendar grCal = new GregorianCalendar(Locale.US);
    boolean isLeapYear1 = grCal.isLeapYear(c1year);
    boolean isLeapYear2 = grCal.isLeapYear(c2year);
    monthCount = (c2year - c1year) * 12 + (c2month - c1month);
    if ((isLeapYear2) && (c2month == 1) && (c2date == 29))
      monthCount += (c1date == 28 ? 0 : 1);
    else if ((isLeapYear1) && (c1month == 1) && (c1date == 29))
      monthCount += (c2date == 28 ? 0 : 1);
    else {
      monthCount += (c2date >= c1date ? 0 : 1);
    }
    return monthCount;
  }
}