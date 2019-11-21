package com.finework.jsf.controller.cust;

import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.facade.CustomerFacade;
import java.util.List;
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
@ManagedBean(name = C201Controller.CONTROLLER_NAME)
@SessionScoped
public class C201Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(C201Controller.class);
    public static final String CONTROLLER_NAME = "c201Controller";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private CustomerFacade customFacade;
    private List<SysWorkunit> items;
    private SysWorkunit selected;

    //find by criteria
    private String workUnitName;
    private Integer status;

    @PostConstruct
    @Override
    public void init() {
//        try {
//            items = customFacade.findSysWorkunitList();
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
            items = customFacade.findSysWorkunitListByCriteria(workUnitName, "Y");
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    @Override
    public void create() {
        try {
            if (StringUtils.isBlank(selected.getWorkunitCode())) {
                JsfUtil.addFacesErrorMessage("กรุณากรอกข้อมูล รหัสหน่วยงาน");
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            
            if (StringUtils.isBlank(selected.getWorkunitName())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.2001"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            selected.setStatus("Y");
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            customFacade.createSysWorkunit(selected);

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
        selected = new SysWorkunit();
        next("cus/c201/create");
    }

    @Override
    public void edit() {
        try {
            if (StringUtils.isBlank(selected.getWorkunitCode())) {
                JsfUtil.addFacesErrorMessage("กรุณากรอกข้อมูล รหัสหน่วยงาน");
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            
            if (StringUtils.isBlank(selected.getWorkunitName())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.2001"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            customFacade.editSysWorkunit(selected);
            clearData();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("cus/c201/index");
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
            customFacade.editSysWorkunit(selected);
            //refresh data
            search();
            next("cus/c201/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
    private void clearData() {
        selected=new SysWorkunit();
    }

    public UserInfoController getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoController userInfo) {
        this.userInfo = userInfo;
    }

    public List<SysWorkunit> getItems() {
        return items;
    }

    public void setItems(List<SysWorkunit> items) {
        this.items = items;
    }

    public SysWorkunit getSelected() {
        return selected;
    }

    public void setSelected(SysWorkunit selected) {
        this.selected = selected;
    }

    public String getWorkUnitName() {
        return workUnitName;
    }

    public void setWorkUnitName(String workUnitName) {
        this.workUnitName = workUnitName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
