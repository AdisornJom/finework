/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.jsf.controller.log;


import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.UserInfoController;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.enterprise.context.SessionScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author Aekasit
 */
@ManagedBean(name = AccessLogController.CONTROLLER_NAME)
@SessionScoped
public class AccessLogController extends BaseController {
 
    public static final String CONTROLLER_NAME = "accessLogController";
    private static final Logger LOG = Logger.getLogger(AccessLogController.class);

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController info;
 /*   @EJB
    private UserFacadeLocal userFacade;
    private List<UserLog> items;

    @PostConstruct
    @Override
    public void init() {
        try {
//            items = userFacade.findLogList(info.getUser());
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    public List<UserLog> getItems() {
        return items;
    }

    public void setItems(List<UserLog> items) {
        this.items = items;
    }

    public UserInfoController getInfo() {
        return info;
    }

    public void setInfo(UserInfoController info) {
        this.info = info;
    }
*/

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
