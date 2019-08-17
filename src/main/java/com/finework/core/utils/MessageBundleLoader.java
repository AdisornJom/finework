package com.finework.core.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class MessageBundleLoader
{
  public static final String DEFAULT_MESSAGE_PATH = "messages";
  public static final String APP_MESSAGE_PATH = "applications";
  public static final String CONFIG_PATH = "config";

  public static String getConfigProperties(String key)
  {
    try
    {
      if (key == null) {
        return null;
      }
      ResourceBundle messages = ResourceBundle.getBundle("config");
      return messages.getString(key); } catch (Exception e) {
    }
    return null;
  }

  public static String getMessage(String key)
  {
    if (key == null) {
      return null;
    }

    try
    {
      Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
      ResourceBundle res = ResourceBundle.getBundle("messages", locale);
      if (res == null) {
        res = ResourceBundle.getBundle("messages", locale);
      }

      return res.getString(key); } catch (Exception e) {
    }
    return null;
  }

  public static String getMessage(String resourcePath, String key)
  {
    if (key == null)
      return null;
    try
    {
      Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
      ResourceBundle res = ResourceBundle.getBundle(resourcePath, locale);
      return res.getString(key); } catch (Exception e) {
    }
    return null;
  }

  public static String getMessageFormat(String key, Object[] args)
  {
    try {
      if (key == null) {
        return null;
      }
      Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
      ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
      return MessageFormat.format(messages.getString(key), args); } catch (Exception e) {
    }
    return "";
  }
}