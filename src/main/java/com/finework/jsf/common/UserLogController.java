package com.finework.jsf.common;

import com.finework.ejb.facade.CoreFacade;
import com.finework.core.ejb.entity.AdminUserLog;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;

/**
 *
 * @author Aekasit
 */
@SessionScoped
@Named(UserLogController.CONTROLLER_NAME)
public class UserLogController extends BaseController {

    private static final Logger LOG = Logger.getLogger(UserLogController.class);
    public static final String CONTROLLER_NAME = "userLogController";

    @Inject
    private CoreFacade coreFacade;
    @Inject
    private UserInfoController info;
    //
    private List<AdminUserLog> items;

    @PostConstruct
    @Override
    public void init() {
        try {
            items = coreFacade.findSysUserLogList(info.getAdminUser());
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

}
