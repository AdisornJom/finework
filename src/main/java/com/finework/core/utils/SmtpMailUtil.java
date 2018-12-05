/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.core.utils;

import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author Aekasit
 */
public class SmtpMailUtil {

    static Map<String, String> CONFIG;

    public static void send(String to, String subject, String message) throws EmailException {
        CONFIG = LoadConfig.loadFileDefault();
        HtmlEmail email = new HtmlEmail();
        email.setStartTLSEnabled(true);
        email.setHostName(CONFIG.get(LoadConfig._SMTP_HOST));
        email.setSmtpPort(NumberUtils.toInt(CONFIG.get(LoadConfig._SMTP_PORT)));
        email.setAuthenticator(new DefaultAuthenticator(CONFIG.get(LoadConfig._SMTP_USER), CONFIG.get(LoadConfig._SMTP_PASS)));
        email.setFrom(CONFIG.get(LoadConfig._SMTP_USER));
        email.setSubject(subject);
        email.setHtmlMsg(message);
        email.setCharset("utf-8");
        email.addTo(to);
        email.send();
    }

}
