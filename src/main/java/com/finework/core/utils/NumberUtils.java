package com.finework.core.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtils
{
  public static String numberFormat(BigDecimal input, String format)
  {
    String output = "0";
    if (input != null)
      output = new DecimalFormat(format).format(input);
    else {
      output = new DecimalFormat(format).format(0.0D);
    }
    return output;
  }

  public static String numberFormat(Double input, String format) {
    String output = "0";
    if (input != null)
      output = new DecimalFormat(format).format(input);
    else {
      output = new DecimalFormat(format).format(0.0D);
    }
    return output;
  }

  public static String numberFormat(Integer input, String format) {
    String output = "0";
    if (input != null)
      output = new DecimalFormat(format).format(input);
    else {
      output = new DecimalFormat(format).format(0.0D);
    }
    return output;
  }
}