package com.finework.core.utils;

import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class SmtpMailUtil
{
  static Map<String, String> CONFIG;

  public static void send(String to, String subject, String message)
    throws EmailException
  {
    CONFIG = LoadConfig.loadFileDefault();
    HtmlEmail email = new HtmlEmail();
    email.setStartTLSEnabled(true);
    email.setHostName((String)CONFIG.get("smtp.host"));
    email.setSmtpPort(NumberUtils.toInt((String)CONFIG.get("smtp.port")));
    email.setAuthenticator(new DefaultAuthenticator((String)CONFIG.get("smtp.user"), (String)CONFIG.get("smtp.pass")));
    email.setFrom((String)CONFIG.get("smtp.user"));
    email.setSubject(subject);
    email.setHtmlMsg(message);
    email.setCharset("utf-8");
    email.addTo(to);
    email.send();
  }
}