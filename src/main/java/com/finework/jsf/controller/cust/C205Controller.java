package com.finework.jsf.controller.cust;

import com.finework.core.ejb.entity.SysForeman;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MD5Generator;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.StringUtil;
import com.finework.ejb.facade.ForemanFacade;
import java.util.List;
import java.util.Objects;
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
 * @author Adisorn j.
 */
@ManagedBean(name = C205Controller.CONTROLLER_NAME)
@SessionScoped
public class C205Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(C205Controller.class);
    public static final String CONTROLLER_NAME = "c205Controller";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private ForemanFacade foremanFacade;
    private List<SysForeman> items;
    private SysForeman selected;

    //find by criteria
    private String foremanName;
    private Integer status;

    @PostConstruct
    @Override
    public void init() {
        search();
    }

    public void next(String path) {
        NaviController nav = (NaviController) JsfUtil.getManagedBeanValue(NaviController.CONTROLLER_NAME);
        nav.next(path);
    }

    public void search() {
        try {
            items = foremanFacade.findSysForemanListByCriteria(foremanName, "Y");
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    @Override
    public void create() {
        try {
            if (StringUtils.isNotBlank(selected.getNewPassword()) || StringUtils.isNotBlank(selected.getConfirmPassword())) {
                if (StringUtils.isBlank(selected.getUsername())) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อผู้ใช้"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                    return;
                } else if (!StringUtil.validateUserName(selected.getUsername())) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.1008"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                    return;
                } else if (foremanFacade.isExistUser(selected.getUsername())) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.1002", selected.getUsername()));
                    RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                    return;
                }

                /*
            validate password*/
                if (StringUtils.isBlank(selected.getNewPassword())) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รหัสผ่าน"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                    return;
                }
                if (StringUtils.isBlank(selected.getConfirmPassword())) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ยืนยันรหัสผ่าน"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                    return;
                }
                if (!StringUtil.validatePasswd1(selected.getNewPassword())) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.1005", "6"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                    return;
                }
                if (!StringUtils.equals(selected.getNewPassword(), selected.getConfirmPassword())) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.1018"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                    return;
                }

                selected.setPassword(MD5Generator.md5(MD5Generator.md5(selected.getNewPassword())));
            }

            if (StringUtils.isBlank(selected.getForemanNameTh())
                    || StringUtils.isBlank(selected.getForemanAddress())) {

                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.2001"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            selected.setStatus("Y");
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            foremanFacade.createSysForeman(selected);

            //refresh data
            clearData();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    @Override
    public void prepareCreate() {
        selected = new SysForeman();
        next("cus/c205/create");
    }

    @Override
    public void edit() {
        try {
            if (StringUtils.isNotBlank(selected.getUsername())) {
                if (!StringUtil.validateUserName(selected.getUsername())) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.1008"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                    return;
                } else {
                    SysForeman sysForeman = foremanFacade.findSysForeman(selected.getForemanId());
                    if (!Objects.equals(selected.getUsername(), sysForeman.getUsername())) {
                        if (foremanFacade.isExistUser(selected.getUsername())) {
                            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.1002", selected.getUsername()));
                            RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                            return;
                        }
                    }
                }
            }
            if (StringUtils.isNotBlank(selected.getNewPassword()) || StringUtils.isNotBlank(selected.getConfirmPassword())) {
                if (StringUtils.isBlank(selected.getUsername())) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อผู้ใช้"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                    return;
                } else if (!StringUtil.validateUserName(selected.getUsername())) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.1008"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                    return;
                }
                /*validate password*/
                if (StringUtils.isBlank(selected.getNewPassword())) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รหัสผ่าน"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                    return;
                }
                if (StringUtils.isBlank(selected.getConfirmPassword())) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ยืนยันรหัสผ่าน"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                    return;
                }
                if (!StringUtil.validatePasswd1(selected.getNewPassword())) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.1005", "6"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                    return;
                }
                if (!StringUtils.equals(selected.getNewPassword(), selected.getConfirmPassword())) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.1018"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                    return;
                }

                selected.setPassword(MD5Generator.md5(MD5Generator.md5(selected.getNewPassword())));
            }

            if (StringUtils.isBlank(selected.getForemanNameTh())
                    || StringUtils.isBlank(selected.getForemanAddress())) {

                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.2001"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            };
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            foremanFacade.editSysForeman(selected);
            clearData();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("cus/c205/index");
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
            selected.setStatus("N");
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            foremanFacade.editSysForeman(selected);
            //refresh data
            search();
            next("cus/c205/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    private void clearData() {
        selected = new SysForeman();
    }

    public UserInfoController getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoController userInfo) {
        this.userInfo = userInfo;
    }

    public List<SysForeman> getItems() {
        return items;
    }

    public void setItems(List<SysForeman> items) {
        this.items = items;
    }

    public SysForeman getSelected() {
        return selected;
    }

    public void setSelected(SysForeman selected) {
        this.selected = selected;
    }

    public String getForemanName() {
        return foremanName;
    }

    public void setForemanName(String foremanName) {
        this.foremanName = foremanName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
