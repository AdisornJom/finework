package com.finework.jsf.controller.cust;

import com.finework.core.ejb.entity.SysTransportStaff;
import com.finework.core.util.Constants;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.ejb.facade.TransporterFacade;
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
@ManagedBean(name = C207Controller.CONTROLLER_NAME)
@SessionScoped
public class C207Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(C207Controller.class);
    public static final String CONTROLLER_NAME = "c207Controller";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private TransporterFacade transporterFacade;
    private List<SysTransportStaff> items;
    private SysTransportStaff selected;
    


    //find by criteria
    private String transportStaffName;
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
            items = transporterFacade.findSysTransportStaffListByCriteria(transportStaffName, Constants.TRANSPORT_FOLLOW_STAFF, "Y");
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    @Override
    public void create() {
        try {            
            if (StringUtils.isBlank(selected.getTransportstaffNameTh())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อพนักงานติดรถ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (StringUtils.isBlank(selected.getTransportstaffNickname())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อเล่นพนักงานติดรถ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (StringUtils.isBlank(selected.getTransportstaffAddress())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ที่อยู่พนักงานติดรถ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (StringUtils.isBlank(selected.getTel())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "เบอร์โทร"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (StringUtils.isBlank(selected.getTransportstaffLineid())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "LINE ID"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            selected.setTransportstaffType(Constants.TRANSPORT_FOLLOW_STAFF);
            selected.setStatus("Y");
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            transporterFacade.createSysTransportStaff(selected);

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
        selected = new SysTransportStaff();
        next("cus/c207/create");
    }

    @Override
    public void edit() {
        try {
            if (StringUtils.isBlank(selected.getTransportstaffNameTh())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อพนักงานติดรถ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            if (StringUtils.isBlank(selected.getTransportstaffNickname())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อเล่นพนักงานติดรถ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            if (StringUtils.isBlank(selected.getTransportstaffAddress())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ที่อยู่พนักงานติดรถ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            if (StringUtils.isBlank(selected.getTel())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "เบอร์โทร"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            if (StringUtils.isBlank(selected.getTransportstaffLineid())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "LINE ID"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            transporterFacade.editSysTransportStaff(selected);
            clearData();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("cus/c207/index");
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
            transporterFacade.editSysTransportStaff(selected);
            //refresh data
            search();
            next("cus/c207/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
    private void clearData() {
        selected=new SysTransportStaff();
    }

   
    
    
    public UserInfoController getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoController userInfo) {
        this.userInfo = userInfo;
    }

   

    public String getForemanName() {
        return transportStaffName;
    }

    public void setForemanName(String transportStaffName) {
        this.transportStaffName = transportStaffName;
    }

    public List<SysTransportStaff> getItems() {
        return items;
    }

    public void setItems(List<SysTransportStaff> items) {
        this.items = items;
    }

    public SysTransportStaff getSelected() {
        return selected;
    }

    public void setSelected(SysTransportStaff selected) {
        this.selected = selected;
    }

    public String getTransportStaffName() {
        return transportStaffName;
    }

    public void setTransportStaffName(String transportStaffName) {
        this.transportStaffName = transportStaffName;
    }

    

   
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

   
}
