package com.finework.core.utils;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.apache.commons.lang3.StringUtils;

public class StringUtil
{
  private static final String USERNAME = "[a-zA-Z0-9]{3,14}";
  private static final String ACCOUNT_NBR = "[0-9]{5,20}";
  private static final String PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
  private static final String EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

  public static boolean validateEmail(String email)
  {
    return email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
  }

  public static boolean validateBankAccountNbr(String acc) {
    acc = StringUtils.trimToEmpty(acc).replaceAll("-", "");
    return acc.matches("[0-9]{5,20}");
  }

  public static boolean validatePasswd(String passwd) {
    return passwd.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$");
  }

  public static boolean validateUserName(String username)
  {
    return username.matches("[a-zA-Z0-9]{3,14}");
  }

  public static String replaceStringNull(String field)
  {
    if (field == null)
      return null;
    if (field.equals("(null)")) {
      return null;
    }
    return field;
  }

  public static String numberFormat(int id, String patt) {
    NumberFormat formatter = new DecimalFormat(patt);
    return formatter.format(id);
  }
  public static String numberFormat(BigDecimal value, String patt) {
    DecimalFormat formatter = new DecimalFormat(patt);
    return formatter.format(value);
  }
  public static String numberFormat(Double value, String patt) {
    DecimalFormat formatter = new DecimalFormat(patt);
    return formatter.format(value);
  }

  public static boolean haveData(String param) {
    if ((null == param) || (param.trim().length() < 1)) {
      return false;
    }
    return true;
  }

  public static String customFormat(String pattern, int value)
  {
    DecimalFormat myFormatter = new DecimalFormat(pattern);
    return myFormatter.format(value);
  }

  public static void main(String[] args)
  {
    System.out.println(validatePasswd("ao7mj1"));
  }
}