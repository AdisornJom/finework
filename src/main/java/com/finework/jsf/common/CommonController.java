package com.finework.jsf.common;

import com.finework.core.util.LoadConfig;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.apache.log4j.Logger;

/**
 *
 * @author Aekasit
 */
@ManagedBean(name = CommonController.CONTROLLER_NAME)
@ApplicationScoped
public class CommonController implements Serializable {

    private static final Logger LOG = Logger.getLogger(CommonController.class);
    public static final String CONTROLLER_NAME = "commonController";

    public static Map<String, String> CONFIG;
    public String IMAGES_URL;

    @PostConstruct
    private void init() {
        CONFIG = LoadConfig.loadFileDefault();
        IMAGES_URL = CONFIG.get(LoadConfig._IMAGES_URL);
    }

    public static Map<String, String> getCONFIG() {
        return CONFIG;
    }

    public static void setCONFIG(Map<String, String> CONFIG) {
        CommonController.CONFIG = CONFIG;
    }

    public String getIMAGES_URL() {
        return IMAGES_URL;
    }

    public void setIMAGES_URL(String IMAGES_URL) {
        this.IMAGES_URL = IMAGES_URL;
    }

}
