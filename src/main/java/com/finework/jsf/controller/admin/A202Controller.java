package com.finework.jsf.controller.admin;

import com.finework.ejb.facade.AdminFacade;
import com.finework.jsf.common.BaseController;
import com.finework.core.ejb.entity.AdminUserLog;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author Aekasit
 */
@ManagedBean(name = A202Controller.CONTROLLER_NAME)
@SessionScoped
public class A202Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(A202Controller.class);
    public static final String CONTROLLER_NAME = "a202Controller";
   
    @Inject
    private AdminFacade adminFacade;
    private List<AdminUserLog> items;
    
    //find by criteria
    private String username;
    private String firstname;

    @PostConstruct
    @Override
    public void init() {
    }

    public void search() {
        try {
           // items = adminFacade.findSysUserLogList();
            items = adminFacade.findSysUserLogListByCriteria(username, firstname);
            Collections.sort(items, new AdminUserLog());
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    public List<AdminUserLog> getItems() {
        return items;
    }

    public void setItems(List<AdminUserLog> items) {
        this.items = items;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    
}
