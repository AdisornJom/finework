package com.finework.jsf.common;

import com.finework.ejb.facade.AdminFacade;
import com.finework.ejb.facade.CoreFacade;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.DeviceUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MD5Generator;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.StringUtil;
import com.finework.core.ejb.entity.AdminUser;
import java.security.Principal;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;


import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@SessionScoped
@Named(UserInfoController.CONTROLLER_NAME)
public class UserInfoController extends BaseController {

    private static final Logger LOG = Logger.getLogger(UserInfoController.class);
    public static final String CONTROLLER_NAME = "userInfoController";

    @Inject
    private AdminFacade adminFacade;

    @Inject
    private CoreFacade coreFacade;

    private AdminUser adminUser;
    private String ipAddr;
    private String browser;
    private String pass1;
    private String pass2;
    private String device;

    @PostConstruct
    @Override
    public void init() {
        try {
            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            adminUser = coreFacade.findUserByUsername(principal.getName());
            HttpServletRequest req = (HttpServletRequest) JsfUtil.getFacesContext().getExternalContext().getRequest();
            ipAddr = req.getRemoteAddr();
            browser = DeviceUtil.getBrowser(req);
            device = DeviceUtil.isDevice((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    @Override
    public void edit() {
        try {
            if (StringUtils.isBlank(adminUser.getFirstName())
                    || StringUtils.isBlank(adminUser.getLastName())
                    || StringUtils.isBlank(adminUser.getEmail())
                    || StringUtils.isBlank(adminUser.getPosition())
                    || StringUtils.isBlank(adminUser.getMobile())) {

                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.2001"));
                return;
            }

            if (!StringUtil.validateEmail(adminUser.getEmail())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.1003"));
                return;
            }

            if (StringUtils.isNotBlank(adminUser.getNewPassword()) || StringUtils.isNotBlank(adminUser.getConfirmPassword())) {
                if (!StringUtils.equals(adminUser.getNewPassword(), adminUser.getConfirmPassword())) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.1018"));
                    return;
                }

                if (!StringUtil.validatePasswd(adminUser.getNewPassword())) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.1004"));
                    return;
                }

                adminUser.setPassword(MD5Generator.md5(adminUser.getNewPassword()));
            }

            adminUser.setModifiedBy(adminUser.getUsername());
            adminUser.setModifiedDt(DateTimeUtil.getSystemDate());
            adminFacade.editAdminUser(adminUser);
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));

        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

}
