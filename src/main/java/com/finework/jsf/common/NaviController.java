package com.finework.jsf.common;

import com.finework.core.util.JsfUtil;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author MR Aekasit Sengnualnim (Aek) System Analyst
 *
 * Progress Software Co.,Ltd Fl. 6-7 306 Supha Road, Phomphlab
 * Phomphlabsattupai, Bangkok 10100 Thailand Tel :+66 (0) 2867 5020 Mobile : +66
 * 91004 1009 Skype : s.aekasit MSN : aekasit.se@gmail.com Email :
 * aekasit.s@kasikornbank.com Website http://www.kasikornbank.com
 *
 * @create 01-10-2014 11:15:10 AM
 */
@SessionScoped
@Named(NaviController.CONTROLLER_NAME)
public class NaviController extends BaseController {

    public static final String CONTROLLER_NAME = "naviController";
    private String page;
    @Inject
    private LogController logController;

    @PostConstruct
    @Override
    public void init() {
        page = "dashboard/index";
    }

    public void next(String page) {
        this.page = page;
    }

//    public void next(String page, String controller) {
//        Map m = JsfUtil.getFacesContext().getExternalContext().getSessionMap();
//        if (!StringUtils.isBlank(controller)) {
//            if (m.containsKey(controller)) {
//                m.remove(controller);
//            }
//        }
//        this.page = page;
//    }

//    public void next(String page, String controller, String codePage) {
//        logController.access(codePage);
//
//        Map m = JsfUtil.getFacesContext().getExternalContext().getSessionMap();
//        if (!StringUtils.isBlank(controller)) {
//            if (m.containsKey(controller)) {
//                m.remove(controller);
//            }
//        }
//
//        this.page = page;
//    }
    
     public void next(String page, String controller, String codePage) {
        logController.access(codePage);

        Map<String, Object> m = JsfUtil.getFacesContext().getExternalContext().getSessionMap();
        if (!StringUtils.isBlank(controller)) {
            for (Map.Entry<String, Object> entry : m.entrySet()) {
                String key = entry.getKey();
                if(key.contains(controller)){
                    m.remove(key);
                    break;
            }
        }
        }

        this.page = page;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

}
