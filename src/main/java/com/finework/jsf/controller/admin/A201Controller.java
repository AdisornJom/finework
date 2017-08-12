package com.finework.jsf.controller.admin;

import com.finework.ejb.facade.AdminFacade;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MD5Generator;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.StringUtil;
import com.finework.core.ejb.entity.AdminUser;
import com.finework.core.ejb.entity.AdminUserRole;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Aekasit
 */
@ManagedBean(name = A201Controller.CONTROLLER_NAME)
@SessionScoped
public class A201Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(A201Controller.class);
    public static final String CONTROLLER_NAME = "a201Controller";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private AdminFacade adminFacade;
    private List<AdminUser> items;
    private AdminUser selected;

    //find by criteria
    private String username;
    private String firstname;
    private Integer status;

    @PostConstruct
    @Override
    public void init() {
//        try {
//            items = adminFacade.findSysUserList();
//        } catch (Exception ex) {
//            LOG.error(ex);
//        }
        search();
    }

    public void next(String path) {
        NaviController nav = (NaviController) JsfUtil.getManagedBeanValue(NaviController.CONTROLLER_NAME);
        nav.next(path);
    }

    public void search() {
        try {
            items = adminFacade.findAdminUserListByCriteria(username, firstname, status);
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    @Override
    public void create() {
        try {
            if (StringUtils.isBlank(selected.getUsername())
                    || StringUtils.isBlank(selected.getFirstName())
                    || StringUtils.isBlank(selected.getLastName())
                    || StringUtils.isBlank(selected.getEmail())
                    || StringUtils.isBlank(selected.getPassword())
                    || StringUtils.isBlank(selected.getConfirmPassword())
                    || StringUtils.isBlank(selected.getOrganization())
                    || StringUtils.isBlank(selected.getPosition())
                    || StringUtils.isBlank(selected.getMobile())) {

                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.2001"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            if (!StringUtils.equals(selected.getPassword(), selected.getConfirmPassword())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.1018"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            if (adminFacade.isExistUser(selected.getUsername(), selected.getEmail())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.1002", selected.getUsername()));
                 RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            if (!StringUtil.validateEmail(selected.getEmail())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.1003"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            if (!StringUtil.validateUserName(selected.getUsername())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.1008"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            if (!StringUtil.validatePasswd(selected.getPassword())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.1005", "6"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            selected.setPassword(MD5Generator.md5(selected.getPassword()));
            selected.setId(UUID.randomUUID().toString());
            selected.setStatus(1);
            selected.setUsed(1);
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            adminFacade.createAdminUser(selected);

            //refresh data
            clearData();
            items = adminFacade.findSysUserList();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        } catch (NoSuchAlgorithmException ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        } catch (UnsupportedEncodingException ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    @Override
    public void prepareCreate() {
        selected = new AdminUser();
        selected.setRoleId(new AdminUserRole());
        next("admin/a201/create");
    }

    @Override
    public void edit() {
        try {
            if (StringUtils.isBlank(selected.getFirstName())
                    || StringUtils.isBlank(selected.getLastName())
                    || StringUtils.isBlank(selected.getEmail())
                    || StringUtils.isBlank(selected.getOrganization())
                    || StringUtils.isBlank(selected.getPosition())
                    || StringUtils.isBlank(selected.getMobile())) {

                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.2001"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }

            if (!StringUtil.validateEmail(selected.getEmail())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.1003"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }

            if (StringUtils.isNotBlank(selected.getNewPassword()) || StringUtils.isNotBlank(selected.getConfirmPassword())) {
                if (!StringUtils.equals(selected.getNewPassword(), selected.getConfirmPassword())) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.1018"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                    return;
                }

                if (!StringUtil.validatePasswd(selected.getNewPassword())) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.1004"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                    return;
                }

                selected.setPassword(MD5Generator.md5(selected.getNewPassword()));
            }

            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            adminFacade.editAdminUser(selected);
            clearData();
            init();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("admin/a201/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    public void cancel(String path) {
        search();
        next(path);
    }
    @Override
    public void delete() {
        try {
            selected.setUsed(0);
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            adminFacade.editAdminUser(selected);
            //refresh data
            items = adminFacade.findAdminUserListByCriteria(username, firstname, status);
            next("admin/a201/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
    private void clearData() {
        selected=new AdminUser();
        AdminUserRole adUserRole=new AdminUserRole();
        selected.setRoleId(adUserRole);
    }

    public List<AdminUser> getItems() {
        return items;
    }

    public void setItems(List<AdminUser> items) {
        this.items = items;
    }

    public AdminUser getSelected() {
        return selected;
    }

    public void setSelected(AdminUser selected) {
        this.selected = selected;
    }

    public UserInfoController getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoController userInfo) {
        this.userInfo = userInfo;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
