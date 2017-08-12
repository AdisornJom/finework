package com.finework.jsf.controller.stock;

import com.finework.core.ejb.entity.SysLogisticCar;
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
@ManagedBean(name = S207Controller.CONTROLLER_NAME)
@SessionScoped
public class S207Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(S207Controller.class);
    public static final String CONTROLLER_NAME = "s207Controller";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private TransporterFacade transporterFacade;
    private List<SysLogisticCar> items;
    private SysLogisticCar selected;
    


    //find by criteria
    private String logisticRegisterCar;
    private String logisticCarType;
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
            items = transporterFacade.findSysLogisticCarListByCriteria(logisticRegisterCar, logisticCarType, "Y");
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    @Override
    public void create() {
        try {            
            if (StringUtils.isBlank(selected.getLogisticRegisterCar())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "เลขทะเบียนรถ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (StringUtils.isBlank(selected.getLogisticCarType())){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ประเภทรถ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (StringUtils.isBlank(selected.getLogisticBand())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ยี่ห้อ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            if(selected.getTransportType()==null){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ประเภทรถขนส่ง"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if(selected.getTransportCost()==null){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ลักษณะขนส่ง"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }else{
                if(Objects.equals(Constants.TRANSPORT_COST_ROUND, selected.getTransportCost())){
                     if(selected.getCharterFlights()==null || selected.getCharterFlights()<0){
                         JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "เหมาต่อเที่ยว"));
                         RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                         return;
                     }
                }else{
                    if(selected.getTransportShort()==null || selected.getTransportShort()<0){
                         JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ขนส่งระยะใกล้"));
                         RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                         return;
                    }
                    if(selected.getTransportLong()==null || selected.getTransportLong()<0){
                         JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ขนส่งระยไกล"));
                         RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                         return;
                    }
                }
            }
                
                
            selected.setStatus("Y");
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            transporterFacade.createSysLogisticCar(selected);

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
        selected = new SysLogisticCar();
        next("stock/s207/create");
    }

    @Override
    public void edit() {
        try {
            if (StringUtils.isBlank(selected.getLogisticRegisterCar())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "เลขทะเบียนรถ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            if (StringUtils.isBlank(selected.getLogisticCarType())){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ประเภทรถ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            if (StringUtils.isBlank(selected.getLogisticBand())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ยี่ห้อ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            
            if(selected.getTransportType()==null){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ประเภทรถขนส่ง"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            if(selected.getTransportCost()==null){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ลักษณะขนส่ง"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }else{
                if(Objects.equals(Constants.TRANSPORT_COST_ROUND, selected.getTransportCost())){
                     if(selected.getCharterFlights()==null || selected.getCharterFlights()<0){
                         JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "เหมาต่อเที่ยว"));
                         RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                         return;
                     }
                }else{
                    if(selected.getTransportShort()==null || selected.getTransportShort()<0){
                         JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ขนส่งระยะใกล้"));
                         RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                         return;
                    }
                    if(selected.getTransportLong()==null || selected.getTransportLong()<0){
                         JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ขนส่งระยไกล"));
                         RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                         return;
                    }
                }
            }
            
            
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            transporterFacade.editSysLogisticCar(selected);
            clearData();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("stock/s207/index");
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
            transporterFacade.editSysLogisticCar(selected);
            //refresh data
            search();
            next("stock/s207/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
    private void clearData() {
        selected=new SysLogisticCar();
    }
    
    public UserInfoController getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoController userInfo) {
        this.userInfo = userInfo;
    }

    public List<SysLogisticCar> getItems() {
        return items;
    }

    public void setItems(List<SysLogisticCar> items) {
        this.items = items;
    }

    public SysLogisticCar getSelected() {
        return selected;
    }

    public void setSelected(SysLogisticCar selected) {
        this.selected = selected;
    }

    public String getLogisticRegisterCar() {
        return logisticRegisterCar;
    }

    public void setLogisticRegisterCar(String logisticRegisterCar) {
        this.logisticRegisterCar = logisticRegisterCar;
    }

    public String getLogisticCarType() {
        return logisticCarType;
    }

    public void setLogisticCarType(String logisticCarType) {
        this.logisticCarType = logisticCarType;
    }

   
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

   
}
