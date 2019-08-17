package com.finework.core.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;

public class LoadConfig
{
  private static final Logger LOG = Logger.getLogger(LoadConfig.class);
  private static Map<String, String> FILE_CONFIG;
  private static Map<String, String> DB_CONFIG;
  public static final String MA_STATUS = "MA_STATUS";
  public static final String MA_DESC = "MA_DESC";
  public static final String MA_START = "MA_START";
  public static final String MA_END = "MA_END";
  public static final String _IMAGES_URL = "images.url";
  public static final String _SMTP_HOST = "smtp.host";
  public static final String _SMTP_PORT = "smtp.port";
  public static final String _SMTP_USER = "smtp.user";
  public static final String _SMTP_PASS = "smtp.pass";
  public static final String _FTP_HOST = "ftp.host";
  public static final String _FTP_USER = "ftp.user";
  public static final String _FTP_PASS = "ftp.pass";
  public static final String _FTP_PORT = "ftp.port";
  public static final String FILE_DEFAULT = "config.properties";
  public static final String LOTTO_URL = "lotto.url";
  public static final String LOTTO_AGENT = "lotto.agent";
  public static final String LOTTO_TOKEN = "lotto.token";
  public static final String LOTTO_HOST = "lotto.host";
  public static final String LOTTO_MINB = "lotto.minb";
  public static final String LOTTO_MAXB = "lotto.maxb";
  public static final String LOTTO_COMM = "lotto.comm";
  public static final String LOTTO_PAY = "lotto.pay";
  public static final String RCGAME_HOST = "rcgame.host";
  public static final String RCGAME_HASH = "rcgame.hash";
  public static final String RCGAME_UID = "rcgame.uid";
  public static final String RCGAME_KEY = "rcgame.key";
  public static final String RCGAME_URL = "rcgame.url";
  public static final String STAR_URL = "star.url";
  public static final String STAR_AGENT = "star.agent";
  public static final String STAR_SECRET = "star.secret";
  public static final String TOP_URL = "top.url";
  public static final String TOP_AGENT = "top.agent";
  public static final String TOP_SECRET = "top.secret";
  public static final String TOP_HOST = "top.host";
  public static final String TOP_PATH = "top.path";
  public static final String VEN_HOST = "ven.host";
  public static final String VEN_URL = "ven.url";
  public static final String VEN_MERCHANT = "ven.merchant";
  public static final String VEN_HASH = "ven.hash";
  public static final String WYNN_URL = "wynn.url";
  public static final String WYNN_HOST = "wynn.host";
  public static final String WYNN_CONSUMER_ID = "wynn.consumer_id";
  public static final String WYNN_CONSUMER_PASSWORD = "wynn.consumer_password";
  public static final String WYNN_AGENT = "wynn.agent";

  public static Map<String, String> loadFileDefault()
  {
    try
    {
      Properties p;
      Iterator localIterator;
      if (FILE_CONFIG == null) {
        FILE_CONFIG = new HashMap();
        p = new Properties();
        p.load(LoadConfig.class.getClassLoader().getResourceAsStream("config.properties"));
        Set keySet = p.keySet();
        for (localIterator = keySet.iterator(); localIterator.hasNext(); ) { Object key = localIterator.next();
          FILE_CONFIG.put((String)key, (String)p.get(key));
        }
      }

      return FILE_CONFIG;
    } catch (IOException ex) {
      LOG.error(ex);
    }return null;
  }

  public static Map<String, String> loadFile(String path)
  {
    try
    {
      Properties p;
      Iterator localIterator;
      if (FILE_CONFIG == null) {
        FILE_CONFIG = new HashMap();
        p = new Properties();
        p.load(LoadConfig.class.getClassLoader().getResourceAsStream(path));
        Set keySet = p.keySet();
        for (localIterator = keySet.iterator(); localIterator.hasNext(); ) { Object key = localIterator.next();
          FILE_CONFIG.put((String)key, (String)p.get(key));
        }
      }
      return FILE_CONFIG;
    } catch (IOException ex) {
      LOG.error(ex);
    }return null;
  }

  public static void main(String[] args)
  {
    LoadConfig config = new LoadConfig();
    loadFile("top-sport.properties");
  }
}