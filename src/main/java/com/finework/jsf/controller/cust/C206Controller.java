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
@ManagedBean(name = C206Controller.CONTROLLER_NAME)
@SessionScoped
public class C206Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(C206Controller.class);
    public static final String CONTROLLER_NAME = "c206Controller";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private TransporterFacade transportStaffFacade;
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
            items = transportStaffFacade.findSysTransportStaffListByCriteria(transportStaffName, Constants.TRANSPORT_STAFF, "Y");
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    @Override
    public void create() {
        try {         
            
            if (StringUtils.isBlank(selected.getTransportstaffNameTh())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อพนักงานขับรถ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (StringUtils.isBlank(selected.getTransportstaffNickname())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อเล่นพนักงานขับรถ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (StringUtils.isBlank(selected.getTransportstaffAddress())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ที่อยู่พนักงานขับรถ"));
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
            if (null == selected.getTransportType()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ประเภทพนักงาน"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }else{
                if (Objects.equals(selected.getTransportType(), Constants.TRANSPORT_TYPE_INTERNAL)) {
                    if (null == selected.getSalary() || selected.getSalary() <= 0) {
                        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "เงินเดือน"));
                        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                        return;
                    }
                    if (null == selected.getEarningPerday() || selected.getEarningPerday() <= 0) {
                        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายได้ต่อวัน"));
                        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                        return;
                    }
                    if (null == selected.getAllowance() || selected.getAllowance() <= 0) {
                        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "เบี้ยเลี้ยง"));
                        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                        return;
                    }
                }
            }
 
            selected.setTransportstaffType(Constants.TRANSPORT_STAFF);
            selected.setStatus("Y");
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            transportStaffFacade.createSysTransportStaff(selected);

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
        next("cus/c206/create");
    }

    @Override
    public void edit() {
        try {
            if (StringUtils.isBlank(selected.getTransportstaffNameTh())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อพนักงานขับรถ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            if (StringUtils.isBlank(selected.getTransportstaffNickname())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อเล่นพนักงานขับรถ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            if (StringUtils.isBlank(selected.getTransportstaffAddress())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ที่อยู่พนักงานขับรถ"));
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
            if (null == selected.getTransportType()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ประเภทพนักงาน"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            } else if (Objects.equals(Constants.TRANSPORT_TYPE_INTERNAL,selected.getTransportType())) {
                if (null == selected.getSalary() || selected.getSalary() <= 0) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "เงินเดือน"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                    return;
                }
                if (null == selected.getEarningPerday()|| selected.getEarningPerday() <= 0) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายได้ต่อวัน"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                    return;
                }
                if (null == selected.getAllowance() || selected.getAllowance() <= 0) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "เบี้ยเลี้ยง"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                    return;
                }
            }
            
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            transportStaffFacade.editSysTransportStaff(selected);
            clearData();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("cus/c206/index");
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
            transportStaffFacade.editSysTransportStaff(selected);
            //refresh data
            search();
            next("cus/c206/index");
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
