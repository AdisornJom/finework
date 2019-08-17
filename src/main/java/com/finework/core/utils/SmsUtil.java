package com.finework.core.utils;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class SmsUtil
{
  public static void main(String[] args)
  {
    SmsUtil smsUtil = new SmsUtil();
    smsUtil.get();
  }

  public void get() {
    try {
      String content = StringUtils.newStringUtf8("Hi Thailand".getBytes());

      URI uri = new URIBuilder().setScheme("https").setHost("etracker.cc").setPath("/mes/send")
        .setParameter("user", "DGAPI")
        .setParameter("pass", "DG863API")
        .setParameter("type", "0")
        .setParameter("from", "bet365goal")
        .setParameter("to", "66811131113")
        .setParameter("text", "Hello K.1 pls call to Somjed.com")
        .setParameter("servid", "DGA001")
        .build();

      HttpGet httpGet = new HttpGet(uri);

      httpGet.setHeader("Content-Type", "application/json");

      System.out.println(content);

      CloseableHttpClient httpclient = HttpClients.createDefault();
      CloseableHttpResponse response1 = httpclient.execute(httpGet);
      HttpEntity entity = response1.getEntity();
      String str1 = IOUtils.toString(entity.getContent());
    }
    catch (Exception ex) {
      Logger.getLogger(SmsUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void post() {
    try {
      HttpClient client = HttpClientBuilder.create().build();
      HttpPost post = new HttpPost("https://www.etracker.cc/mes/send");

      post.setHeader("Content-Type", "application/json");

      List param = new ArrayList();
      param.add(new BasicNameValuePair("user", "DGAPI"));
      param.add(new BasicNameValuePair("pass", "DG863API"));
      param.add(new BasicNameValuePair("type", "0"));
      param.add(new BasicNameValuePair("from", "bet365goal"));
      param.add(new BasicNameValuePair("to", "66910041009"));
      param.add(new BasicNameValuePair("text", "test"));
      param.add(new BasicNameValuePair("servid", "DGA001"));
      post.setEntity(new UrlEncodedFormEntity(param));

      HttpResponse response = client.execute(post);
      HttpEntity entity = response.getEntity();
      String str = IOUtils.toString(entity.getContent());
    }
    catch (IOException ex) {
      Logger.getLogger(SmsUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}