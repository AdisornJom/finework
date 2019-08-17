package com.finework.jsf.common;

import com.finework.core.util.LoadConfig;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.apache.log4j.Logger;

@ManagedBean(name="commonController")
@ApplicationScoped
public class CommonController
  implements Serializable
{
  private static final Logger LOG = Logger.getLogger(CommonController.class);
  public static final String CONTROLLER_NAME = "commonController";
  public static Map<String, String> CONFIG;
  public String IMAGES_URL;

  @PostConstruct
  private void init()
  {
    CONFIG = LoadConfig.loadFileDefault();
    this.IMAGES_URL = ((String)CONFIG.get("images.url"));
  }

  public static Map<String, String> getCONFIG() {
    return CONFIG;
  }

  public static void setCONFIG(Map<String, String> CONFIG) {
    CONFIG = CONFIG;
  }

  public String getIMAGES_URL() {
    return this.IMAGES_URL;
  }

  public void setIMAGES_URL(String IMAGES_URL) {
    this.IMAGES_URL = IMAGES_URL;
  }
}