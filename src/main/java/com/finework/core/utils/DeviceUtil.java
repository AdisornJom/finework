package com.finework.core.utils;

import javax.servlet.http.HttpServletRequest;

public class DeviceUtil
{
  public static String MOBILE = "Mobile";
  public static String DESKTOP = "Desktop";

  public static String isDevice(HttpServletRequest request) {
    String deviceType = request.getHeader("User-Agent");

    if ((deviceType.contains("Mobile")) || (deviceType.contains("Android"))) {
      return MOBILE;
    }
    return DESKTOP;
  }

  public static String getBrowser(HttpServletRequest request)
  {
    String deviceType = request.getHeader("User-Agent");

    if (deviceType.contains("MSIE"))
      return "Internet Explorer";
    if (deviceType.contains("Firefox"))
      return "Firefox";
    if (deviceType.contains("Chrome"))
      return "Chrome";
    if (deviceType.contains("Safari"))
      return "Safari";
    if (deviceType.contains("Presto")) {
      return "Opera";
    }
    return "Unknow";
  }
}