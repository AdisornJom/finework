package com.finework.jsf.controller.transporter;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysLogisticCar;
import com.finework.core.ejb.entity.SysManufactoryReal;
import com.finework.core.ejb.entity.SysMaterial;
import com.finework.core.ejb.entity.SysOrganization;
import com.finework.core.ejb.entity.SysPrepareTransport;
import com.finework.core.ejb.entity.SysPrepareTransportDetail;
import com.finework.core.ejb.entity.SysTransportServices;
import com.finework.core.ejb.entity.SysTransportStaff;
import com.finework.core.ejb.entity.SysTransportation;
import com.finework.core.ejb.entity.SysTransportationDetail;
import com.finework.core.ejb.entity.SysTransportationServiceDetail;
import com.finework.core.ejb.entity.SysTransportationSpecial;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.core.util.Constants;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.NumberUtils;
import com.finework.core.util.ReportUtil;
import com.finework.ejb.facade.ContractorFacade;
import com.finework.ejb.facade.CustomerFacade;
import com.finework.ejb.facade.ForemanFacade;
import com.finework.ejb.facade.ManufactoryFacade;
import com.finework.ejb.facade.OrganizationFacade;
import com.finework.ejb.facade.StockFacade;
import com.finework.ejb.facade.TransporterFacade;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.SequenceController;
import com.finework.jsf.common.UserInfoController;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;

/**
 *
 * @author Adisorn Jomjanyong
 */

@ManagedBean(name = T103Controller.CONTROLLER_NAME)
@SessionScoped
public class T103Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(T103Controller.class);
    public static final String CONTROLLER_NAME = "t103Controller";
    
    @Inject
    private TransporterFacade transporterFacade;
    @Inject
    private ManufactoryFacade manufactoryFacade;
    @Inject
    private ContractorFacade contractorFacade;
    @Inject
    private OrganizationFacade organizationFacade;
    @Inject
    private UserInfoController userInfo;
    @Inject
    private SequenceController sequence;
    @Inject
    private CustomerFacade customerFacade;
    @Inject
    private ForemanFacade foremanFacade;
    @Inject
    private StockFacade stockFacade;
    
    //
    private List<SysTransportation> items;
    private SysTransportation selected;
    private List<SysTransportation> printSelected;

    //detial 
    private SysTransportationDetail transportationDetail_selected;
    private SysTransportationServiceDetail tpServiceDetail_selected;
    private SysTransportationSpecial tpSpecial_selected;
    
    
    //find criteria main
    private String documentno;
    private SysContractor contractor_find;
    private SysWorkunit workunit_find;
    private SysForeman foreman_find;
    private Date startDate;
    private Date toDate;
    private Integer status_find;
    
    //variable
    private boolean option_production;
    private boolean option_service;
    
    //dialog
    List<SysPrepareTransport> prepareTransport_items_dialog;
    List<SysTransportationServiceDetail> tpService_items_dialog;
    List<SysTransportationSpecial> tpSpecial_items_dialog;
    private SysPrepareTransport prepareTransport_find_dialog;
    private List<SysPrepareTransport> selectd_SysPrepareTransport_items_dialog;
    
    //auto commplete dialog
    private SysTransportServices tpServices_selected_dialog;
    private SysPrepareTransport tpPrepare_selected_dialog;
    private SysMaterial sysMaterial_selected_dialog;
    

   
    @PostConstruct
    @Override
    public void init() {
        tpService_items_dialog=new ArrayList();
        tpSpecial_items_dialog=new ArrayList();
        search();
    }
    
    public void next(String path) {
        NaviController nav = (NaviController) JsfUtil.getManagedBeanValue(NaviController.CONTROLLER_NAME);
        nav.next(path);
    }
    
    public void prepareCreate(String page) {
        selected = new SysTransportation();
        transportationDetail_selected=new SysTransportationDetail();
        tpServiceDetail_selected =new SysTransportationServiceDetail();
        tpSpecial_selected=new SysTransportationSpecial();
        next(page);
    }

    public void prepareEdit(String page) {
       // this.contractor_selected=selected.getContractorId();
        
        next(page);
    }
    public void cancel(String path) {
        clearData();
        next(path);
    }
    public void backIndex(String path) {
        init();
        next(path);
    }
    public void clearData(){
         selected = new SysTransportation();
    }

    @Override
    public void create() {
      try {
          if (null == selected.getTransportType()) {
              JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "เลือกประเภทขนส่ง"));
              RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
              return;
          }
          if (null == selected.getForemanId()) {
              JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อผู้ควบคุมงาน(โฟร์แมน)"));
              RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
              return;
          }
          if (null == selected.getWorkunitId()) {
              JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "หน่วยงาน/โครงการ"));
              RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
              return;
          }
          if (null == selected.getTpstaffId()) {
              JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "พนักงานขับรถ"));
              RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
              return;
          }
          if (null == selected.getTpOrderDate()) {
              JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่ทำรายการ"));
              RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
              return;
          }
          if (null == selected.getTpDate()) {
              JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ขนส่งวันที่"));
              RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
              return;
          }
          
          if (Objects.equals(selected.getTransportType(),Constants.TRANSPORTATION_PRODUCTION)) {
              if (null == selected.getLogisticId()) {
                  JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รถขนส่งทะเบียน"));
                  RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                  return;
              }
              if (null == selected.getSysTransportationDetailList() || selected.getSysTransportationDetailList().isEmpty()) {
                  JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายการการผลิต"));
                  RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                  return;
              }else{
                  //insertDetail
                  List<SysTransportationDetail> detal_add = new ArrayList();
                  for (SysTransportationDetail sysTransportationDetail_ : selected.getSysTransportationDetailList()) {
                      sysTransportationDetail_.setTpdetailId(null);//auto generate id on db
                      sysTransportationDetail_.setTransportId(selected);
                      detal_add.add(sysTransportationDetail_);

                      //update status prepare_transport 4=Transport Complete
                      //transporterFacade.updateStatusSysPrepareTransportByprepareTpId(Constants.PREPARE_TRANSPORTER_CARRY, sysTransportationDetail_.getPrepareTpId().getPrepareTpId());
                      SysPrepareTransport sysPrepareTransport=sysTransportationDetail_.getPrepareTpId();
                      sysPrepareTransport.setStatus(Constants.PREPARE_TRANSPORTER_CARRY);
                      transporterFacade.editSysPrepareTransport(sysPrepareTransport);
                      
                      //update manufactory_real Transporter_status 
                      for (SysPrepareTransportDetail sysPrepareTransportDetail_ : sysTransportationDetail_.getPrepareTpId().getSysPrepareTransportDetailList()) {
                          //manufactoryFacade.updateStatusTransporterSysManufactoryReal(Constants.TRANSPORTER_CARRY, sysPrepareTransportDetail_.getFactoryRealId());
                          SysManufactoryReal sysManufactoryReal = sysPrepareTransportDetail_.getFactoryRealId();
                          sysManufactoryReal.setStatusTransporter(Constants.TRANSPORTER_CARRY);
                          manufactoryFacade.editSysManufactoryReal(sysManufactoryReal);
                      }
                  }
                  selected.setSysTransportationDetailList(detal_add);
              }
          }else{
              if (null == selected.getSysTransportationServiceDetailList() || selected.getSysTransportationServiceDetailList().isEmpty()) {
                  JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายการบริการขนส่ง"));
                  RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                  return;
              } else //insertServiceDetail
              if (null != selected.getSysTransportationServiceDetailList() && selected.getSysTransportationServiceDetailList().size() > 0) {
                  List<SysTransportationServiceDetail> expdetal_add = new ArrayList();
                  for (SysTransportationServiceDetail sysTransportationServiceDetail_ : selected.getSysTransportationServiceDetailList()) {
                      sysTransportationServiceDetail_.setTpservicedetailId(null);//auto generate id on db
                      sysTransportationServiceDetail_.setTransportId(selected);
                      expdetal_add.add(sysTransportationServiceDetail_);
                  }
                  selected.setSysTransportationServiceDetailList(expdetal_add);
              }
          }
          
          if (null != selected.getSysTransportationSpecialList()&& !selected.getSysTransportationSpecialList().isEmpty()) {
              if (selected.getSysTransportationSpecialList().size() > 0) {
                  List<SysTransportationSpecial> spcialdetal_add = new ArrayList();
                  for (SysTransportationSpecial sysTransportationSpcial_ : selected.getSysTransportationSpecialList()) {
                      sysTransportationSpcial_.setTpspecialId(null);//auto generate id on db
                      sysTransportationSpcial_.setTransportId(selected);
                      spcialdetal_add.add(sysTransportationSpcial_);
                  }
                  selected.setSysTransportationSpecialList(spcialdetal_add);
              }
          }
            
             //insert Manufactory
            selected.setStatus(Constants.TRANSPORTATION_ON_PROCESS);
           /* selected.setForemanId(foreman_selected);
            selected.setWorkunitId(workunit_selected);
            selected.setLogisticId(logisticCar_selected);
            selected.setTpstaffId(transportStaff_selected);
            selected.setTpcarstaffId1(transportCarStaff1_selected);
            selected.setTpcarstaffId2(transportCarStaff2_selected);*/
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
                    
            runningNoCustomer();
            
            transporterFacade.createSysTransportation(selected);

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
    public void edit() {
         try {
             if (null == selected.getTpstaffId()) {
                 JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "พนักงานขับรถ"));
                 RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                 return;
             }
             if (null == selected.getTpDate()) {
                 JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ขนส่งวันที่"));
                 RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                 return;
             }

              //select data old
             SysTransportation sysTransportation = transporterFacade.findSysTransportation(selected.getTransportId());

             if (Objects.equals(selected.getTransportType(), Constants.TRANSPORTATION_PRODUCTION)) {
                 if (null == selected.getLogisticId()) {
                     JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รถขนส่งทะเบียน"));
                     RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                     return;
                 }
                 if (null == selected.getSysTransportationDetailList() || selected.getSysTransportationDetailList().isEmpty()) {
                     JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายการการผลิต"));
                     RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                     return;
                 } else {
                     //update status prepare_transport 3=Transport Send เตรียขนส่ง
                     for (SysTransportationDetail detail : sysTransportation.getSysTransportationDetailList()) {
                           transporterFacade.updateStatusSysPrepareTransportByprepareTpId(Constants.PREPARE_TRANSPORTER_PREPARE, detail.getPrepareTpId().getPrepareTpId());
//                         SysPrepareTransport sysPrepareTransport = detail.getPrepareTpId();
//                         sysPrepareTransport.setStatus(Constants.PREPARE_TRANSPORTER_PREPARE);
//                         transporterFacade.editSysPrepareTransport(sysPrepareTransport);
                     }
                     //deleteDetail
                     transporterFacade.deleteTransportationTpIdOnDetail(selected.getTransportId());

                     //insertDetail
                     List<SysTransportationDetail> detal_add = new ArrayList();
                     for (SysTransportationDetail sysTransportationDetail_ : selected.getSysTransportationDetailList()) {
                         sysTransportationDetail_.setTpdetailId(null);//auto generate id on db
                         sysTransportationDetail_.setTransportId(selected);
                         detal_add.add(sysTransportationDetail_);
                         
                         //update status prepare_transport 4=Transport Complete
                         transporterFacade.updateStatusSysPrepareTransportByprepareTpId(Constants.PREPARE_TRANSPORTER_CARRY, sysTransportationDetail_.getPrepareTpId().getPrepareTpId());
//                         SysPrepareTransport sysPrepareTransport = sysTransportationDetail_.getPrepareTpId();
//                         sysPrepareTransport.setStatus(Constants.PREPARE_TRANSPORTER_CARRY);
//                         sysTransportationDetail_.setPrepareTpId(sysPrepareTransport);                        
                        // transporterFacade.editSysPrepareTransport(sysPrepareTransport);//object ยังเป็น object เก่า พอ merge ข้อมูลเก่าก็ยังติดมาด้วย\
                     }
                     selected.setSysTransportationDetailList(detal_add);
                 }

                
             } else{ // selected.setLogisticId(logisticCar_selected);                 
                 if (null == selected.getSysTransportationServiceDetailList() || selected.getSysTransportationServiceDetailList().isEmpty()) {
                     JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายการบริการขนส่ง"));
                     RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                     return;
                 } else {
                     //deleteServiceDetail
                     transporterFacade.deleteTransportationServiceTpIdOnDetail(selected.getTransportId());

                     //insertServiceDetail
                     if (null != selected.getSysTransportationServiceDetailList() && selected.getSysTransportationServiceDetailList().size() > 0) {
                         List<SysTransportationServiceDetail> expdetal_add = new ArrayList();
                         for (SysTransportationServiceDetail sysTransportationServiceDetail_ : selected.getSysTransportationServiceDetailList()) {
                             sysTransportationServiceDetail_.setTpservicedetailId(null);//auto generate id on db
                             sysTransportationServiceDetail_.setTransportId(selected);
                             expdetal_add.add(sysTransportationServiceDetail_);
                         }
                         selected.setSysTransportationServiceDetailList(expdetal_add);
                     }
                 }
             }
             
             
             //deleteSpcialDetail
             transporterFacade.deleteTransportationSpecialTpIdOnDetail(selected.getTransportId());
             if (null != selected.getSysTransportationSpecialList() && !selected.getSysTransportationSpecialList().isEmpty()) {
                 if (selected.getSysTransportationSpecialList().size() > 0) {
                     List<SysTransportationSpecial> spcialdetal_add = new ArrayList();
                     for (SysTransportationSpecial sysTransportationSpcial_ : selected.getSysTransportationSpecialList()) {
                         sysTransportationSpcial_.setTpspecialId(null);//auto generate id on db
                         sysTransportationSpcial_.setTransportId(selected);
                         spcialdetal_add.add(sysTransportationSpcial_);
                     }
                     selected.setSysTransportationSpecialList(spcialdetal_add);
                 }
             }
            
     
            //update Transportation
             selected.setModifiedBy(userInfo.getAdminUser().getUsername());
             selected.setModifiedDt(DateTimeUtil.getSystemDate()); 
            
            transporterFacade.editSysTransportation(selected);
         
            
            clearData();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("transporter/t103/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
 
    @Override
    public void delete() {
        try {
             //select data old
             SysTransportation sysTransportation = transporterFacade.findSysTransportation(selected.getTransportId());
             
            //update status prepare_transport 3=Transport Send เตรียขนส่ง
            for (SysTransportationDetail detail : sysTransportation.getSysTransportationDetailList()) {
               // transporterFacade.updateStatusSysPrepareTransportByprepareTpId(Constants.PREPARE_TRANSPORTER_PREPARE, detail.getPrepareTpId().getPrepareTpId());
                SysPrepareTransport sysPrepareTransport = detail.getPrepareTpId();
                sysPrepareTransport.setStatus(Constants.PREPARE_TRANSPORTER_PREPARE);
                transporterFacade.editSysPrepareTransport(sysPrepareTransport);
            }

            transporterFacade.deleteSysTransportation(selected);
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4002"));
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
 
    public void search() {
        try {
              
               if (null == startDate) {
                    GregorianCalendar cal =(GregorianCalendar) GregorianCalendar.getInstance(Locale.US);
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    startDate = cal.getTime();
                }
                if (null == toDate) {
                    GregorianCalendar calEnd =(GregorianCalendar) GregorianCalendar.getInstance(Locale.US);
                    calEnd.set(Calendar.HOUR_OF_DAY, 23);
                    calEnd.set(Calendar.MINUTE, 59);
                    calEnd.set(Calendar.SECOND, 59);
                    toDate = calEnd.getTime();
                }
                
              // items=transporterFacade.findSysTransportationListByCriteria(foreman_find,documentno,workunit_find,status_find, startDate, toDate);
              items=transporterFacade.findSysTransportationListByCriteria(foreman_find,documentno,workunit_find,Constants.TRANSPORTATION_ON_PROCESS, startDate, toDate);
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }
     
    //==== Strat Group ==========
      public void searchUserGroup() {
        try {
             //group_items=settingFacadeLocal.findUserGroupList();
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    //==== End Group ============
     
   //===== start  Dialog=========  
      
    public void dialogClose(CloseEvent event) {
        clearData_sysTransportationDetail();
    }
    
    public void searchPrepareTransport(){
          try {         
             /* if(null!=foreman_selected && null!=workunit_selected){
                   prepareTransport_items_dialog = transporterFacade.findSysPrepareTransportListByCriteria(null!=foreman_selected?foreman_selected:null,
                       null!=prepareTransport_find_dialog?prepareTransport_find_dialog.getDocumentNo():null,null!=workunit_selected?workunit_selected:null,Constants.PREPARE_TRANSPORTER_PREPARE, null, null);
              }*/
              
                if(null!=selected.getForemanId() && null!=selected.getWorkunitId()){
                   prepareTransport_items_dialog = transporterFacade.findSysPrepareTransportListByCriteria(null!=selected.getForemanId()?selected.getForemanId():null,
                       null!=prepareTransport_find_dialog?prepareTransport_find_dialog.getDocumentNo():null,null!=selected.getWorkunitId()?selected.getWorkunitId():null,Constants.PREPARE_TRANSPORTER_PREPARE, null, null);
              }
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }  
      
    public void addDetail(){
         try {

             if (selected.getSysTransportationDetailList() == null) {
                 selected.setSysTransportationDetailList(new ArrayList<SysTransportationDetail>());
             }

             //is match
             List<Integer> sysTransport_list = new ArrayList();
             int seq = 0;
             for (SysTransportationDetail sysTransportationDetail : selected.getSysTransportationDetailList()) {
                 sysTransport_list.add(sysTransportationDetail.getPrepareTpId().getPrepareTpId());
             }

             for (SysPrepareTransport sysPrepareTransport : selectd_SysPrepareTransport_items_dialog) {
                 if (!sysTransport_list.contains(sysPrepareTransport.getPrepareTpId())) {
                     SysTransportationDetail sysTransportationDetail = new SysTransportationDetail();
                     sysTransportationDetail.setTpdetailId(seq);
                     sysTransportationDetail.setPrepareTpId(sysPrepareTransport);

                     seq += 1;
                     selected.getSysTransportationDetailList().add(sysTransportationDetail);
                     sysTransport_list.add(sysPrepareTransport.getPrepareTpId());
                 }
             }
             clearData_sysTransportationDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    
    
     
    public void deleteDetail(){
        try {
             selected.getSysTransportationDetailList().remove(transportationDetail_selected);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    } 
    
    /// Expensese Detail
     public void dialogServiceClose(CloseEvent event) {
        clearData_sysTransportationDetail();
    }
      
    public void addDialogServiceDetail(){
       //validate field iteam 
        if (tpServices_selected_dialog == null) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายการบริการขนส่ง"));
            RequestContext.getCurrentInstance().scrollTo("dialog");
            return;
        }

        Random rand = new Random();
        int n = rand.nextInt(500) + 1;
        tpServiceDetail_selected.setTpservicedetailId(n);
        tpServiceDetail_selected.setTpserviceId(tpServices_selected_dialog);

        tpService_items_dialog.add(tpServiceDetail_selected);
        
        clearData_sysTransportationDetail();
    }  
    
    public void addServiceDetail(){
         try {
             if (selected.getSysTransportationServiceDetailList() == null) {
                 selected.setSysTransportationServiceDetailList(new ArrayList<SysTransportationServiceDetail>());
             }

             for (SysTransportationServiceDetail sysTransportationServiceDetail : tpService_items_dialog) {
                 selected.getSysTransportationServiceDetailList().add(sysTransportationServiceDetail);
             }

             clearData_sysTransportationDetail();
             tpService_items_dialog=new ArrayList<>();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    
    public void deleteDialogServiceDetail(){
        try {
              tpService_items_dialog.remove(tpServiceDetail_selected);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    } 
    
    public void deleteServiceDetail(){
        try {
             selected.getSysTransportationServiceDetailList().remove(tpServiceDetail_selected);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
    
    /// Special Detail
     public void dialogSpecialClose(CloseEvent event) {
        clearData_sysTransportationDetail();
    }
      
    public void addDialogSpecialDetail(){
       //validate field iteam 
        if (StringUtils.isBlank(tpSpecial_selected.getDetail())) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายการ"));
            RequestContext.getCurrentInstance().scrollTo("dialogSpecial");
            return;
        }

        Random rand = new Random();
        int n = rand.nextInt(500) + 1;
        tpSpecial_selected.setTpspecialId(n);

        tpSpecial_items_dialog.add(tpSpecial_selected);
        
        clearData_sysTransportationDetail();
    }  
    
    public void addSpcialDetail(){
         try {
             if (selected.getSysTransportationSpecialList() == null) {
                 selected.setSysTransportationSpecialList(new ArrayList<SysTransportationSpecial>());
             }

             for (SysTransportationSpecial sysTransportationSpecialDetail : tpSpecial_items_dialog) {
                 selected.getSysTransportationSpecialList().add(sysTransportationSpecialDetail);
             }

             clearData_sysTransportationDetail();
            tpSpecial_items_dialog=new ArrayList<>();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    
    public void deleteDialogSpcialDetail(){
        try {
              tpSpecial_items_dialog.remove(tpSpecial_selected);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    } 
    
    public void deleteSpcialDetail(){
        try {
             selected.getSysTransportationSpecialList().remove(tpSpecial_selected);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
   //===== end  Dialog=========   
    
    public void prepareEdit() {
        transportationDetail_selected=new SysTransportationDetail();
    } 
    
    public void prepareSpecialEdit(){
        tpSpecial_selected =new SysTransportationSpecial();
    }
   public void handleKeyEvent(){}
   
   
    public void runningNoCustomer() {
        String sequence_no=sequence.updateRunningNO(1,Constants.SEQUNCE_NO_TRANSPORTATION,"yyMM");
        this.selected.setDocumentNo(sequence_no);
    } 
   
 //Auto Complete==========================================================================  
     //Auto complete Foreman
    public List<SysForeman> completeForeman(String query) {
         List<SysForeman> filteredSysForeman = new ArrayList<>();
       try {
            List<SysForeman> allForemans = foremanFacade.findSysForemanList();
            for (SysForeman sysForeman:allForemans) {
               if(sysForeman.getForemanNickname()!=null && sysForeman.getForemanNickname().length()>0){
                if(sysForeman.getForemanNickname().toLowerCase().startsWith(query)) {
                    filteredSysForeman.add(sysForeman);
                }
               }
            }
            //remove
            if(null!=selected.getSysTransportationDetailList())
               selected.getSysTransportationDetailList().clear();
            
            if(!Objects.equals(Constants.TRANSPORTATION_SERVICE,selected.getTransportType())){
                if(null!=selected.getSysTransportationServiceDetailList())
                   selected.getSysTransportationServiceDetailList().clear();
            }
            
            clearData_sysPrepareTransportDetail();
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredSysForeman;
    }
    
    //Auto complete workunit
   public List<SysWorkunit> completeWorkunit(String query) {
         List<SysWorkunit> filteredWorkunit= new ArrayList<>();
       try {
            List<SysWorkunit> allWorkunit = customerFacade.findSysWorkunitList();
            for (SysWorkunit sysWorkunit:allWorkunit) {
               if(sysWorkunit.getWorkunitName()!=null && sysWorkunit.getWorkunitName().length()>0){
                if(sysWorkunit.getWorkunitName().toLowerCase().startsWith(query)) {
                    filteredWorkunit.add(sysWorkunit);
                }
               }
            }
            
            //remove
            if(null!=selected.getSysTransportationDetailList())
               selected.getSysTransportationDetailList().clear();
            
           if (!Objects.equals(Constants.TRANSPORTATION_SERVICE, selected.getTransportType())) {
               if (null != selected.getSysTransportationServiceDetailList()) {
                   selected.getSysTransportationServiceDetailList().clear();
               }
           }
             
            clearData_sysPrepareTransportDetail();
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredWorkunit;
    }
   
    //Auto complete workunit search
   public List<SysWorkunit> completeSelectWorkunit(String query) {
         List<SysWorkunit> filteredWorkunit= new ArrayList<>();
       try {
            SysWorkunit sysWorkunit_ =new SysWorkunit();
            sysWorkunit_.setWorkunitId(null);
            sysWorkunit_.setWorkunitName("-");
            filteredWorkunit.add(sysWorkunit_);
            List<SysWorkunit> allWorkunit = customerFacade.findSysWorkunitList();
            for (SysWorkunit sysWorkunit:allWorkunit) {
               if(sysWorkunit.getWorkunitName()!=null && sysWorkunit.getWorkunitName().length()>0){
                if(sysWorkunit.getWorkunitName().toLowerCase().startsWith(query)) {
                    filteredWorkunit.add(sysWorkunit);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredWorkunit;
    }
   
   //Auto logistic_car
    public List<SysLogisticCar> completeLogisticCar(String query) {
         List<SysLogisticCar> filteredSysLogisticCar = new ArrayList<>();
       try {
            List<SysLogisticCar> allLogisticCars = transporterFacade.findSysLogisticCarList();
            for (SysLogisticCar sysLogisticCar:allLogisticCars) {
               if(sysLogisticCar.getLogisticRegisterCar()!=null && sysLogisticCar.getLogisticRegisterCar().length()>0){
                if(sysLogisticCar.getLogisticRegisterCar().toLowerCase().startsWith(query)) {
                    filteredSysLogisticCar.add(sysLogisticCar);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredSysLogisticCar;
    }
    
    //Auto transporter_staff
    public List<SysTransportStaff> completeTransportStaff(String query) {
         List<SysTransportStaff> filteredSysTransportStaff = new ArrayList<>();
       try {
            List<SysTransportStaff> allTransportStaffs = transporterFacade.findSysTransportStaffList(Constants.TRANSPORT_STAFF);
            for (SysTransportStaff sysTransportStaff:allTransportStaffs) {
               if(sysTransportStaff.getTransportstaffNickname()!=null && sysTransportStaff.getTransportstaffNickname().length()>0){
                if(sysTransportStaff.getTransportstaffNickname().toLowerCase().startsWith(query)) {
                    filteredSysTransportStaff.add(sysTransportStaff);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredSysTransportStaff;
    }
    
     //Auto transporter_follow_staff
    public List<SysTransportStaff> completeTransportFollowStaff(String query) {
         List<SysTransportStaff> filteredSysTransportStaff = new ArrayList<>();
       try {
            List<SysTransportStaff> allTransportStaffs = transporterFacade.findSysTransportStaffList(Constants.TRANSPORT_FOLLOW_STAFF);
            for (SysTransportStaff sysTransportStaff:allTransportStaffs) {
               if(sysTransportStaff.getTransportstaffNickname()!=null && sysTransportStaff.getTransportstaffNickname().length()>0){
                if(sysTransportStaff.getTransportstaffNickname().toLowerCase().startsWith(query)) {
                    filteredSysTransportStaff.add(sysTransportStaff);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredSysTransportStaff;
    }
    
    //Auto prepareTransport
    public List<SysPrepareTransport> completePrepareTransport(String query) {
         List<SysPrepareTransport> filteredSysPrepareTransports = new ArrayList<>();
       try {
            List<SysPrepareTransport> allprepareTransports = transporterFacade.findSysPrepareTransportListByCriteria(selected.getForemanId(), null, selected.getWorkunitId(), Constants.PREPARE_TRANSPORTER_PREPARE, null, null);
            for (SysPrepareTransport sysPrepareTransport:allprepareTransports) {
               if(sysPrepareTransport.getDocumentNo()!=null && sysPrepareTransport.getDocumentNo().length()>0){
                if(sysPrepareTransport.getDocumentNo().toLowerCase().startsWith(query)) {
                    filteredSysPrepareTransports.add(sysPrepareTransport);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredSysPrepareTransports;
    }
   
      //Auto transporter_follow_staff
    public List<SysTransportServices> completeTransportService(String query) {
         List<SysTransportServices> filteredSysTransportServices = new ArrayList<>();
       try {
            List<SysTransportServices> allTransportServices = transporterFacade.findSysTransportServicesList();
            for (SysTransportServices sysTransportServices:allTransportServices) {
               if(sysTransportServices.getTpserviceDesc()!=null && sysTransportServices.getTpserviceDesc().length()>0){
                if(sysTransportServices.getTpserviceDesc().toLowerCase().startsWith(query)) {
                    filteredSysTransportServices.add(sysTransportServices);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredSysTransportServices;
    }
    
    
     //Auto complete Detail
    public List<SysMaterial> completeMaterial(String query) {
         List<SysMaterial> filteredMaterial = new ArrayList<>();
       try {
            List<SysMaterial> allMaterial = stockFacade.findSysMaterialList();
            for (SysMaterial sysMaterial:allMaterial) {
               if(sysMaterial.getMaterialDesc()!=null && sysMaterial.getMaterialDesc().length()>0){
                if(sysMaterial.getMaterialDesc().toLowerCase().startsWith(query)) {
                    filteredMaterial.add(sysMaterial);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredMaterial;
    }
   
 
 //End Auto Complete==========================================================================    
    
    public void clearData_sysPrepareTransportDetail(){   
        prepareTransport_items_dialog=new ArrayList<>();
        selectd_SysPrepareTransport_items_dialog=new ArrayList<>();
        prepareTransport_find_dialog=new SysPrepareTransport();
        tpService_items_dialog=new ArrayList<>();
        tpSpecial_items_dialog=new ArrayList<>();
    }
     public void clearData_sysTransportationDetail(){
        transportationDetail_selected = new SysTransportationDetail();
        prepareTransport_find_dialog=new SysPrepareTransport();
        selectd_SysPrepareTransport_items_dialog = new ArrayList();
       
        tpServiceDetail_selected=new SysTransportationServiceDetail();
        tpServices_selected_dialog=new SysTransportServices();
        tpSpecial_selected=new SysTransportationSpecial();
       
    }
    
    
    public void selectRadioItem() {
        this.option_production=false;
        this.option_service=false;
        Integer selectedItem = this.selected.getTransportType(); 
        if(Objects.equals(Constants.TRANSPORTATION_PRODUCTION, selectedItem)){
            this.option_production=true;
        }else if(Objects.equals(Constants.TRANSPORTATION_SERVICE, selectedItem)){
            this.option_service=true;
        }
    }
   
    @Override
    public void reportPDF() {
        try {
            ReportUtil report = new ReportUtil();
            List reportList_ = new ArrayList<>();
            List<TransporterReportBean> reportList_main = new ArrayList<>();
            List<TransporterReportBean> reportList = new ArrayList<>();
            SysTransportation rpt_sysTransportation=transporterFacade.findSysTransportation(selected.getTransportId());
            
            int intRunningNo=0;
            Double totalVolume=0.0;
            if (ObjectUtils.equals(Constants.TRANSPORTATION_PRODUCTION, rpt_sysTransportation.getTransportType())) {
                List<SysTransportationDetail> list = rpt_sysTransportation.getSysTransportationDetailList();
                for (SysTransportationDetail tpDetail : list) {
                    for (SysPrepareTransportDetail to : tpDetail.getPrepareTpId().getSysPrepareTransportDetailList()) {
                        TransporterReportBean bean = new TransporterReportBean();
                        bean.setSeq(String.valueOf(intRunningNo+=1));
                        String typeStr = "";
                        if (null != to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getCalculateType()) {
                            switch (to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getCalculateType()) {
                                case 1:
                                    typeStr = "(จำนวน)";
                                    break;
                                case 2:
                                    typeStr = "(ความยาว/เมตร)(" + to.getFactoryRealId().getManufactoryDetailId().getLength() + ")";
                                    break;
                                default:
                                    typeStr = "(ชุด)";
                                    break;
                            }
                        }

                        bean.setDetail(to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getManufacturingDesc() + " " + typeStr);
                        Double volumn = 0.0;//, priceUnit = 0.0;
                        volumn = null != to.getFactoryRealId().getVolumeReal() ? to.getFactoryRealId().getVolumeReal() : 0.0;
                        totalVolume +=volumn;
                        //  priceUnit = null != to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice() ? to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice() : 0.0;
 
                        // bean.setDocno(to.getFactoryRealId().getManufactoryDetailId().getFactoryId().getDocumentno());
                        bean.setPlot(to.getFactoryRealId().getManufactoryDetailId().getPlot());
                        bean.setVolumn(NumberUtils.numberFormat(volumn, "#,##0.00"));
                        bean.setUnit(null != to.getFactoryRealId().getManufactoryDetailId().getUnit() ? to.getFactoryRealId().getManufactoryDetailId().getUnit() : "");
                        bean.setAmount(NumberUtils.numberFormat(0.0, "#,##0.00"));

                        reportList.add(bean);
                    }
                }
            }else{
                for (SysTransportationServiceDetail to : rpt_sysTransportation.getSysTransportationServiceDetailList()) {
                    TransporterReportBean bean = new TransporterReportBean();
                    bean.setSeq(String.valueOf(intRunningNo+=1));
                    bean.setDetail(to.getTpserviceId().getTpserviceDesc());
                    
                    Double volumn = 0.0;//, priceUnit = 0.0;
                    volumn = null != to.getVolume() ? to.getVolume() : 0.0;
                    totalVolume +=volumn;
                    bean.setVolumn(NumberUtils.numberFormat(volumn, "#,##0.00"));

                    bean.setAmount(NumberUtils.numberFormat((null != to.getAmount()) ? to.getAmount() : 0.0, "#,##0.00"));
                    
                    reportList.add(bean);
                }
            }
            
            //Report Special
            if (null != selected.getSysTransportationSpecialList() && !selected.getSysTransportationSpecialList().isEmpty()) {
                if (selected.getSysTransportationSpecialList().size() > 0) {
                    TransporterReportBean bean = new TransporterReportBean();
                    bean.setDetail("รายการพิเศษ");
                    reportList.add(bean);
                    for (SysTransportationSpecial to : selected.getSysTransportationSpecialList()) {
                        TransporterReportBean beandetail = new TransporterReportBean();
                        beandetail.setBillNo((StringUtils.isNoneBlank(to.getBillTransportDocno())) ?"("+to.getBillTransportDocno()+")": "");
                        beandetail.setSeq(String.valueOf(intRunningNo+=1));
                        beandetail.setDetail(to.getDetail()+" "+beandetail.getBillNo());
                        beandetail.setPlot((null != to.getPlot()) ? to.getPlot() : "");
                        
                        Double volumn = 0.0;//, priceUnit = 0.0;
                        volumn = null != to.getVolume() ? to.getVolume() : 0.0;
                        totalVolume +=volumn;
                        beandetail.setVolumn(NumberUtils.numberFormat(volumn, "#,##0.00"));
                        
                        beandetail.setUnit(null != to.getUnit() ? to.getUnit() : "");
                        beandetail.setAmount(NumberUtils.numberFormat((null != to.getAmount()) ? to.getAmount() : 0.0, "#,##0.00"));

                        reportList.add(beandetail);
                    }
                }
            }

            reportList_main.add(new TransporterReportBean("", "", "","", "", "", "", ""));
            reportList_.add(reportList_main);
            reportList_.add(reportList);
            HashMap map = new HashMap();
            SysOrganization org= organizationFacade.findSysOrganizationByStatus("Y");
            map.put("org_name_th",org.getOrgNameTh());
            map.put("org_name_eng",org.getOrgNameEng());
            map.put("org_address_th",org.getOrgAddressTh());
            map.put("org_address_en",org.getOrgAddressEn());
            map.put("org_tel",org.getOrgTel());
            map.put("org_branch",org.getOrgBranch());
            map.put("org_taxid",org.getOrgTax());
            map.put("org_bank",org.getOrgBank());
            map.put("org_bank_name",org.getOrgBankName());
            map.put("org_recall",org.getOrgRecall());
            
            map.put("documentno",rpt_sysTransportation.getDocumentNo());
            map.put("send_date",DateTimeUtil.cvtDateForShow(rpt_sysTransportation.getTpDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            map.put("workunit",rpt_sysTransportation.getWorkunitId().getWorkunitName());
            map.put("forman",rpt_sysTransportation.getForemanId().getForemanNameTh());
            map.put("car_staff",(null != rpt_sysTransportation.getTpstaffId()) ? rpt_sysTransportation.getTpstaffId().getTransportstaffNameTh() : "");
            map.put("car_follow_staff1",(null != rpt_sysTransportation.getTpcarstaffId1()) ? rpt_sysTransportation.getTpcarstaffId1().getTransportstaffNameTh() : "");
            map.put("car_follow_staff2", (null != rpt_sysTransportation.getTpcarstaffId2()) ? rpt_sysTransportation.getTpcarstaffId2().getTransportstaffNameTh() : "");
            map.put("logistic_car_type", (null != rpt_sysTransportation.getLogisticId()) ? rpt_sysTransportation.getLogisticId().getLogisticCarType() : "");
            map.put("logistic_car_id",(null != rpt_sysTransportation.getLogisticId()) ? rpt_sysTransportation.getLogisticId().getLogisticRegisterCar() : ""); 
            map.put("remark",StringUtils.isNotBlank(rpt_sysTransportation.getRemark())?rpt_sysTransportation.getRemark():"...........................................................................................................................................");
            
            map.put("total_volume",NumberUtils.numberFormat((null != totalVolume) ? totalVolume : 0.0, "#,##0.00"));
            map.put("total_seq", "รวม "+ intRunningNo+" รายการ");
            
            map.put("reportCode", "T103");
            if (ObjectUtils.equals(Constants.TRANSPORTATION_PRODUCTION, rpt_sysTransportation.getTransportType())) {
                report.exportSubReport("t103", new String[]{"T103Report","T103SubReport"}, "T103", map, reportList_);
            }else{
                report.exportSubReport("t103", new String[]{"T103ServiceReport","T103ServiceSubReport"}, "T103", map, reportList_);
            }
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
   
    
    public void printPdfMuti(){
         try {
             List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
             Collections.sort(printSelected, new SysTransportation());
             for (SysTransportation sysTransportation : printSelected) {
                 ReportUtil report = new ReportUtil();
                 List reportList_ = new ArrayList<>();
                 List<TransporterReportBean> reportList_main = new ArrayList<>();
                 List<TransporterReportBean> reportList = new ArrayList<>();
                 SysTransportation rpt_sysTransportation = transporterFacade.findSysTransportation(sysTransportation.getTransportId());

                 int intRunningNo = 0;
                 Double totalVolume = 0.0;
                 if (ObjectUtils.equals(Constants.TRANSPORTATION_PRODUCTION, rpt_sysTransportation.getTransportType())) {
                     List<SysTransportationDetail> list = rpt_sysTransportation.getSysTransportationDetailList();
                     for (SysTransportationDetail tpDetail : list) {
                         for (SysPrepareTransportDetail to : tpDetail.getPrepareTpId().getSysPrepareTransportDetailList()) {
                             TransporterReportBean bean = new TransporterReportBean();
                             bean.setSeq(String.valueOf(intRunningNo += 1));
                             String typeStr = "";
                             if (null != to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getCalculateType()) {
                                 switch (to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getCalculateType()) {
                                     case 1:
                                         typeStr = "(จำนวน)";
                                         break;
                                     case 2:
                                         typeStr = "(ความยาว/เมตร)(" + to.getFactoryRealId().getManufactoryDetailId().getLength() + ")";
                                         break;
                                     default:
                                         typeStr = "(ชุด)";
                                         break;
                                 }
                             }

                             bean.setDetail(to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getManufacturingDesc() + " " + typeStr);
                             Double volumn = 0.0;//, priceUnit = 0.0;
                             volumn = null != to.getFactoryRealId().getVolumeReal() ? to.getFactoryRealId().getVolumeReal() : 0.0;
                             totalVolume += volumn;
                             //  priceUnit = null != to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice() ? to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice() : 0.0;

                             // bean.setDocno(to.getFactoryRealId().getManufactoryDetailId().getFactoryId().getDocumentno());
                             bean.setPlot(to.getFactoryRealId().getManufactoryDetailId().getPlot());
                             bean.setVolumn(NumberUtils.numberFormat(volumn, "#,##0.00"));
                             bean.setUnit(null != to.getFactoryRealId().getManufactoryDetailId().getUnit() ? to.getFactoryRealId().getManufactoryDetailId().getUnit() : "");
                             bean.setAmount(NumberUtils.numberFormat(0.0, "#,##0.00"));

                             reportList.add(bean);
                         }
                     }
                 } else {
                     for (SysTransportationServiceDetail to : rpt_sysTransportation.getSysTransportationServiceDetailList()) {
                         TransporterReportBean bean = new TransporterReportBean();
                         bean.setSeq(String.valueOf(intRunningNo += 1));
                         bean.setDetail(to.getTpserviceId().getTpserviceDesc());

                         Double volumn = 0.0;//, priceUnit = 0.0;
                         volumn = null != to.getVolume() ? to.getVolume() : 0.0;
                         totalVolume += volumn;
                         bean.setVolumn(NumberUtils.numberFormat(volumn, "#,##0.00"));

                         bean.setAmount(NumberUtils.numberFormat((null != to.getAmount()) ? to.getAmount() : 0.0, "#,##0.00"));

                         reportList.add(bean);
                     }
                 }

                 //Report Special
                 if (null != sysTransportation.getSysTransportationSpecialList() && !sysTransportation.getSysTransportationSpecialList().isEmpty()) {
                     if (sysTransportation.getSysTransportationSpecialList().size() > 0) {
                         TransporterReportBean bean = new TransporterReportBean();
                         bean.setDetail("รายการพิเศษ");
                         reportList.add(bean);
                         for (SysTransportationSpecial to : sysTransportation.getSysTransportationSpecialList()) {
                             TransporterReportBean beandetail = new TransporterReportBean();
                             beandetail.setBillNo((StringUtils.isNoneBlank(to.getBillTransportDocno())) ?"("+to.getBillTransportDocno()+")": "");
                             beandetail.setSeq(String.valueOf(intRunningNo += 1));
                             beandetail.setDetail(to.getDetail() + " " + beandetail.getBillNo());
                             beandetail.setPlot((null != to.getPlot()) ? to.getPlot() : "");

                             Double volumn = 0.0;//, priceUnit = 0.0;
                             volumn = null != to.getVolume() ? to.getVolume() : 0.0;
                             totalVolume += volumn;
                             beandetail.setVolumn(NumberUtils.numberFormat(volumn, "#,##0.00"));

                             beandetail.setUnit(null != to.getUnit() ? to.getUnit() : "");
                             beandetail.setAmount(NumberUtils.numberFormat((null != to.getAmount()) ? to.getAmount() : 0.0, "#,##0.00"));

                             reportList.add(beandetail);
                         }
                     }
                 }

                 reportList_main.add(new TransporterReportBean("", "","", "", "", "", "", ""));
                 reportList_.add(reportList_main);
                 reportList_.add(reportList);
                 HashMap map = new HashMap();
                 SysOrganization org = organizationFacade.findSysOrganizationByStatus("Y");
                 map.put("org_name_th", org.getOrgNameTh());
                 map.put("org_name_eng", org.getOrgNameEng());
                 map.put("org_address_th", org.getOrgAddressTh());
                 map.put("org_address_en", org.getOrgAddressEn());
                 map.put("org_tel", org.getOrgTel());
                 map.put("org_branch", org.getOrgBranch());
                 map.put("org_taxid", org.getOrgTax());
                 map.put("org_bank", org.getOrgBank());
                 map.put("org_bank_name", org.getOrgBankName());
                 map.put("org_recall", org.getOrgRecall());

                 map.put("documentno", rpt_sysTransportation.getDocumentNo());
                 map.put("send_date", DateTimeUtil.cvtDateForShow(rpt_sysTransportation.getTpDate(), "dd/MM/yyyy", new Locale("th", "TH")));
                 map.put("workunit", rpt_sysTransportation.getWorkunitId().getWorkunitName());
                 map.put("forman", rpt_sysTransportation.getForemanId().getForemanNameTh());
                 map.put("car_staff", (null != rpt_sysTransportation.getTpstaffId()) ? rpt_sysTransportation.getTpstaffId().getTransportstaffNameTh() : "");
                 map.put("car_follow_staff1", (null != rpt_sysTransportation.getTpcarstaffId1()) ? rpt_sysTransportation.getTpcarstaffId1().getTransportstaffNameTh() : "");
                 map.put("car_follow_staff2", (null != rpt_sysTransportation.getTpcarstaffId2()) ? rpt_sysTransportation.getTpcarstaffId2().getTransportstaffNameTh() : "");
                 map.put("logistic_car_type", (null != rpt_sysTransportation.getLogisticId()) ? rpt_sysTransportation.getLogisticId().getLogisticCarType() : "");
                 map.put("logistic_car_id", (null != rpt_sysTransportation.getLogisticId()) ? rpt_sysTransportation.getLogisticId().getLogisticRegisterCar() : "");
                 map.put("remark", StringUtils.isNotBlank(rpt_sysTransportation.getRemark()) ? rpt_sysTransportation.getRemark() : "...........................................................................................................................................");

                 map.put("total_volume", NumberUtils.numberFormat((null != totalVolume) ? totalVolume : 0.0, "#,##0.00"));
                 map.put("total_seq", "รวม " + intRunningNo + " รายการ");

                 map.put("reportCode", "T103");
                 JasperPrint print=null;
                 if (ObjectUtils.equals(Constants.TRANSPORTATION_PRODUCTION, rpt_sysTransportation.getTransportType())) {
                      print = report.exportSubReport_Template_mearge("template.jpg", "t103", new String[]{"T103Report", "T103SubReport"}, "T103", map, reportList_);
                 } else {
                      print = report.exportSubReport_Template_mearge("template.jpg", "t103", new String[]{"T103ServiceReport", "T103ServiceSubReport"}, "T103", map, reportList_);
                 }
                 jasperPrintList.add(print);
             }
             if(!printSelected.isEmpty()){
                String pdfCode="T103";
                String pdfName = pdfCode.concat("-").concat(DateTimeUtil.dateToString(DateTimeUtil.currentDate(), "yyyyMMddHHmmss"));
                ReportUtil report = new ReportUtil();
                report.exportMearge(pdfName,jasperPrintList);
             }
              init();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public UserInfoController getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoController userInfo) {
        this.userInfo = userInfo;
    }

    public boolean isOption_production() {
        return option_production;
    }

    public void setOption_production(boolean option_production) {
        this.option_production = option_production;
    }

    public boolean isOption_service() {
        return option_service;
    }

    public void setOption_service(boolean option_service) {
        this.option_service = option_service;
    }


    public String getDocumentno() {
        return documentno;
    }

    public void setDocumentno(String documentno) {
        this.documentno = documentno;
    }

    public SequenceController getSequence() {
        return sequence;
    }

    public void setSequence(SequenceController sequence) {
        this.sequence = sequence;
    }

    public ContractorFacade getContractorFacade() {
        return contractorFacade;
    }

    public void setContractorFacade(ContractorFacade contractorFacade) {
        this.contractorFacade = contractorFacade;
    }

    public List<SysTransportation> getItems() {
        return items;
    }

    public void setItems(List<SysTransportation> items) {
        this.items = items;
    }

    public SysTransportation getSelected() {
        return selected;
    }

    public void setSelected(SysTransportation selected) {
        this.selected = selected;
    }

    public List<SysTransportation> getPrintSelected() {
        return printSelected;
    }

    public void setPrintSelected(List<SysTransportation> printSelected) {
        this.printSelected = printSelected;
    }

    public SysTransportationDetail getTransportationDetail_selected() {
        return transportationDetail_selected;
    }

    public void setTransportationDetail_selected(SysTransportationDetail transportationDetail_selected) {
        this.transportationDetail_selected = transportationDetail_selected;
    }

    
    public SysContractor getContractor_find() {
        return contractor_find;
    }

    public void setContractor_find(SysContractor contractor_find) {
        this.contractor_find = contractor_find;
    }

    public SysPrepareTransport getPrepareTransport_find_dialog() {
        return prepareTransport_find_dialog;
    }

    public void setPrepareTransport_find_dialog(SysPrepareTransport prepareTransport_find_dialog) {
        this.prepareTransport_find_dialog = prepareTransport_find_dialog;
    }

    public List<SysPrepareTransport> getSelectd_SysPrepareTransport_items_dialog() {
        return selectd_SysPrepareTransport_items_dialog;
    }

    public void setSelectd_SysPrepareTransport_items_dialog(List<SysPrepareTransport> selectd_SysPrepareTransport_items_dialog) {
        this.selectd_SysPrepareTransport_items_dialog = selectd_SysPrepareTransport_items_dialog;
    }

    public List<SysTransportationServiceDetail> getTpService_items_dialog() {
        return tpService_items_dialog;
    }

    public void setTpService_items_dialog(List<SysTransportationServiceDetail> tpService_items_dialog) {
        this.tpService_items_dialog = tpService_items_dialog;
    }

    

    public SysTransportationServiceDetail getTpServiceDetail_selected() {
        return tpServiceDetail_selected;
    }

    public void setTpServiceDetail_selected(SysTransportationServiceDetail tpServiceDetail_selected) {
        this.tpServiceDetail_selected = tpServiceDetail_selected;
    }


  
    public SysWorkunit getWorkunit_find() {
        return workunit_find;
    }

    public void setWorkunit_find(SysWorkunit workunit_find) {
        this.workunit_find = workunit_find;
    }

    public SysForeman getForeman_find() {
        return foreman_find;
    }

    public void setForeman_find(SysForeman foreman_find) {
        this.foreman_find = foreman_find;
    }

    public Integer getStatus_find() {
        return status_find;
    }

    public void setStatus_find(Integer status_find) {
        this.status_find = status_find;
    }

    public List<SysPrepareTransport> getPrepareTransport_items_dialog() {
        return prepareTransport_items_dialog;
    }

    public void setPrepareTransport_items_dialog(List<SysPrepareTransport> prepareTransport_items_dialog) {
        this.prepareTransport_items_dialog = prepareTransport_items_dialog;
    }

    public SysTransportServices getTpServices_selected_dialog() {
        return tpServices_selected_dialog;
    }

    public void setTpServices_selected_dialog(SysTransportServices tpServices_selected_dialog) {
        this.tpServices_selected_dialog = tpServices_selected_dialog;
    }

    public SysPrepareTransport getTpPrepare_selected_dialog() {
        return tpPrepare_selected_dialog;
    }

    public void setTpPrepare_selected_dialog(SysPrepareTransport tpPrepare_selected_dialog) {
        this.tpPrepare_selected_dialog = tpPrepare_selected_dialog;
    }

    public SysMaterial getSysMaterial_selected_dialog() {
        return sysMaterial_selected_dialog;
    }

    public void setSysMaterial_selected_dialog(SysMaterial sysMaterial_selected_dialog) {
        this.sysMaterial_selected_dialog = sysMaterial_selected_dialog;
    }

    public SysTransportationSpecial getTpSpecial_selected() {
        return tpSpecial_selected;
    }

    public void setTpSpecial_selected(SysTransportationSpecial tpSpecial_selected) {
        this.tpSpecial_selected = tpSpecial_selected;
    }

    public List<SysTransportationSpecial> getTpSpecial_items_dialog() {
        return tpSpecial_items_dialog;
    }

    public void setTpSpecial_items_dialog(List<SysTransportationSpecial> tpSpecial_items_dialog) {
        this.tpSpecial_items_dialog = tpSpecial_items_dialog;
    }
    
    
    
   
}