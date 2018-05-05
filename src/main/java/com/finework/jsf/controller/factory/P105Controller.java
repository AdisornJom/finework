package com.finework.jsf.controller.factory;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysExpensesManufactory;
import com.finework.core.ejb.entity.SysManufactory;
import com.finework.core.ejb.entity.SysManufactoryReal;
import com.finework.core.ejb.entity.SysManufacturing;
import com.finework.core.ejb.entity.SysOrganization;
import com.finework.core.ejb.entity.SysPaymentManufactory;
import com.finework.core.ejb.entity.SysPaymentManufactoryDetail;
import com.finework.core.ejb.entity.SysPaymentManufactoryExpdetail;
import com.finework.core.util.Constants;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.NumberUtils;
import com.finework.core.util.ReportUtil;
import com.finework.core.util.ThaiBaht;
import com.finework.ejb.facade.ContractorFacade;
import com.finework.ejb.facade.ManufactoryFacade;
import com.finework.ejb.facade.OrganizationFacade;
import com.finework.ejb.facade.PaymentManufactoryFacade;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.SequenceController;
import com.finework.jsf.common.UserInfoController;
import com.finework.jsf.controller.billing.BillingReportBean;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Adisorn Jomjanyong
 */

@ManagedBean(name = P105Controller.CONTROLLER_NAME)
@SessionScoped
public class P105Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(P105Controller.class);
    public static final String CONTROLLER_NAME = "p105Controller";
    
    @Inject
    private PaymentManufactoryFacade paymentManufactoryFacade;
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
    
    private LazyDataModel<SysPaymentManufactory> lazyPaymentManufactoryModel;
    
    //
    private List<SysPaymentManufactory> items;
    private SysPaymentManufactory selected;
    private List<SysPaymentManufactory> printSelected;

    //detial 
    private SysPaymentManufactoryDetail facDetail_selected;
    private SysPaymentManufactoryExpdetail facExpDetail_selected;
    
    
    //find criteria main
    private String documentno;
    private SysContractor contractor_find;
    private Date startDate;
    private Date toDate;
    
    //variable
    private Double total=0.0;
    private Double total_vat=0.0;
    private Double total_volume=0.0;
    private Double total_divide_equipment=0.0;
    private Double total_ream=0.0;
    private Double total_net=0.0;
    private String total_th;
    
    private int draftNo;
    
    //find dialog
    List<SysManufactoryReal> manufactoryReal_items_dialog;
    List<SysExpensesManufactory> manufactoryExpenses_items_dialog;
    private SysManufactory manufactory_selected;
   // private Date dialog_startDate;
  //  private Date dialog_toDate;
    private List<SysManufactoryReal> selectd_manufactoryReal_items_dialog;
    private List<SysExpensesManufactory> selectd_manufactoryExpenses_items_dialog;
    
    
    //auto complete
    private SysContractor contractor_selected;
    private SysManufacturing manufacturing_selected;
    

   
    @PostConstruct
    @Override
    public void init() {
        search();
    }
    
    public void next(String path) {
        NaviController nav = (NaviController) JsfUtil.getManagedBeanValue(NaviController.CONTROLLER_NAME);
        nav.next(path);
    }
    
    public void prepareCreate(String page) {
        selected = new SysPaymentManufactory();
        facDetail_selected=new SysPaymentManufactoryDetail();
//        runningNoCustomer();
        next(page);
    }

    public void prepareEdit(String page) {
        this.contractor_selected=selected.getContractorId();
        
        this.total = selected.getFacTotal();
        this.total_vat = selected.getFacVat();
        this.total_volume =selected.getFacVolume();
        this.total_divide_equipment = null != selected.getFacDivideEquipment()?selected.getFacDivideEquipment():0;
      //  this.total_ream =null != selected.getFacReam()?selected.getFacReam():0;
        this.total_net = selected.getFacNet();
        
        next(page);
    }
    public void cancel(String path) {
        clearData();
        clearDatatTotal();
        next(path);
    }
    public void backIndex(String path) {
        init();
        next(path);
    }
    public void clearDatatTotal(){
        this.total=0.0;
        this.total_vat=0.0;
        this.total_volume=0.0;
        this.total_divide_equipment = 0.00;
        //this.total_ream=0.00;
        this.total_net=0.00;
    }
     
    public void clearData(){
         selected = new SysPaymentManufactory();
         contractor_selected=new SysContractor();
//         this.dialog_startDate=null;
//         this.dialog_toDate=null;
    }

    @Override
    public void create() {
      try {

            if (null==selected.getFactoryDate()){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่ทำรายการ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (null==contractor_selected) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ผู้รับเหมา"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (null == selected.getPaymentStartDate()) {
                //remove
                if (null != selected.getSysPaymentManufactoryDetailList()) {
                    selected.getSysPaymentManufactoryDetailList().clear();
                }

                if (null != selected.getSysPaymentManufactoryExpdetailList()) {
                    selected.getSysPaymentManufactoryExpdetailList().clear();
                }

                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "จ่ายช่วงวันที่"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (null == selected.getPaymentEndDate()) {
                 //remove
                if (null != selected.getSysPaymentManufactoryDetailList()) {
                    selected.getSysPaymentManufactoryDetailList().clear();
                }

                if (null != selected.getSysPaymentManufactoryExpdetailList()) {
                    selected.getSysPaymentManufactoryExpdetailList().clear();
                }
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "จ่ายช่วงถึงวันที่"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (null==selected.getSysPaymentManufactoryDetailList() || selected.getSysPaymentManufactoryDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            //insert Manufactory
            selected.setContractorId(contractor_selected);
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            
            //insertDetail
            List<SysPaymentManufactoryDetail> detal_add=new ArrayList();
            for(SysPaymentManufactoryDetail sysPaymentDetail_:selected.getSysPaymentManufactoryDetailList()){
                sysPaymentDetail_.setManufactoryDetailId(null);//auto generate id on db
                sysPaymentDetail_.setPaymentFactoryId(selected);
                detal_add.add(sysPaymentDetail_);
                
                manufactoryFacade.updateStatusSysManufactoryRealByfactoryRealId(2,sysPaymentDetail_.getFactoryRealId().getFactoryRealId());
//               SysManufactoryReal sysManufactoryReal=sysPaymentDetail_.getFactoryRealId(); 
//               sysManufactoryReal.setStatus(2);
//               manufactoryFacade.editSysManufactoryReal(sysManufactoryReal);
            }
            selected.setSysPaymentManufactoryDetailList(detal_add);
            
            //insertExpensesDetail
            if(null !=selected.getSysPaymentManufactoryExpdetailList() && selected.getSysPaymentManufactoryExpdetailList().size()>0){
                List<SysPaymentManufactoryExpdetail> expdetal_add=new ArrayList();
                for(SysPaymentManufactoryExpdetail sysPaymentExpDetail_:selected.getSysPaymentManufactoryExpdetailList()){
                    sysPaymentExpDetail_.setManufactoryExpdetailId(null);//auto generate id on db
                    sysPaymentExpDetail_.setPaymentFactoryId(selected);
                    expdetal_add.add(sysPaymentExpDetail_);

                    manufactoryFacade.updateStatusSysManufactoryExpensesByexpensesId(2,sysPaymentExpDetail_.getExpensesId().getExpensesId());
//                    SysExpensesManufactory  SysExpensesManufactory=sysPaymentExpDetail_.getExpensesId();
//                    SysExpensesManufactory.setExpensesStatus(2);
//                    manufactoryFacade.editSysExpensesManufactory(SysExpensesManufactory);
                }
                selected.setSysPaymentManufactoryExpdetailList(expdetal_add);
            }
            
            selected.setFacTotal(total);
            selected.setFacVat(total_vat);
            selected.setFacVolume(total_volume);
            selected.setFacDivideEquipment(total_divide_equipment);
           // selected.setFacReam(total_ream);
            selected.setFacNet(total_net);
                    
            runningNoCustomer();
            
            paymentManufactoryFacade.createSysPaymentManufactory(selected);

            clearData();
            clearDatatTotal();
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
            
            if (null==selected.getSysPaymentManufactoryDetailList() || selected.getSysPaymentManufactoryDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            //select data old
            SysPaymentManufactory sysPaymentManufactory= paymentManufactoryFacade.findSysPaymentManufactory(selected);
                    
            //update factoryReal status => 1="normal"            
            for(SysPaymentManufactoryDetail detail:sysPaymentManufactory.getSysPaymentManufactoryDetailList()){
                manufactoryFacade.editSysManufactoryReal(1,detail.getFactoryRealId()); 
            }

            //deleteDetail
             paymentManufactoryFacade.deletePaymentManufactoryIdOnDetail(selected.getPaymentFactoryId());
             
             //insertDetail
            List<SysPaymentManufactoryDetail> detal_add=new ArrayList();
            for(SysPaymentManufactoryDetail sysPaymentDetail_:selected.getSysPaymentManufactoryDetailList()){
                sysPaymentDetail_.setManufactoryDetailId(null);//auto generate id on db
                sysPaymentDetail_.setPaymentFactoryId(selected);
                detal_add.add(sysPaymentDetail_);
                
//               manufactoryFacade.updateStatusSysManufactoryRealByfactoryRealId(2,sysPaymentDetail_.getFactoryRealId().getFactoryRealId());
//               SysManufactoryReal sysManufactoryReal=sysPaymentDetail_.getFactoryRealId(); 
//               sysManufactoryReal.setStatus(2);
//               manufactoryFacade.editSysManufactoryReal(sysManufactoryReal);
            }
            selected.setSysPaymentManufactoryDetailList(detal_add);
            
             //update
            for(SysPaymentManufactoryDetail sysPaymentDetail_:selected.getSysPaymentManufactoryDetailList()){
                manufactoryFacade.updateStatusSysManufactoryRealByfactoryRealId(2,sysPaymentDetail_.getFactoryRealId().getFactoryRealId());
//               SysManufactoryReal sysManufactoryReal=sysPaymentDetail_.getFactoryRealId(); 
//               sysManufactoryReal.setStatus(2);
//               manufactoryFacade.editSysManufactoryReal(sysManufactoryReal);
            }
            
            
            
    //========================================================================================================================        
            
            //update factoryExpenses status => 1="normal"
            for(SysPaymentManufactoryExpdetail detail:sysPaymentManufactory.getSysPaymentManufactoryExpdetailList()){
                manufactoryFacade.updateStatusSysManufactoryExpensesByexpensesId(1,detail.getExpensesId().getExpensesId());
//                SysExpensesManufactory SysExpensesManufactory = detail.getExpensesId();
//                SysExpensesManufactory.setExpensesStatus(1);
//                manufactoryFacade.editSysExpensesManufactory(SysExpensesManufactory);
            }

            //deleteDetail factoryExpenses
            paymentManufactoryFacade.deletePaymentManufactoryExpensesIdOnDetail(selected.getPaymentFactoryId());
             
            List<SysPaymentManufactoryExpdetail> expdetal_add=new ArrayList();
            //insertDetail factoryExpenses
            if(null !=selected.getSysPaymentManufactoryExpdetailList() && selected.getSysPaymentManufactoryExpdetailList().size()>0){
                expdetal_add=new ArrayList();
                for(SysPaymentManufactoryExpdetail sysPaymentExpDetail_:selected.getSysPaymentManufactoryExpdetailList()){
                    sysPaymentExpDetail_.setManufactoryExpdetailId(null);//auto generate id on db
                    sysPaymentExpDetail_.setPaymentFactoryId(selected);
                    expdetal_add.add(sysPaymentExpDetail_);

                    manufactoryFacade.updateStatusSysManufactoryExpensesByexpensesId(2,sysPaymentExpDetail_.getExpensesId().getExpensesId());
//                    SysExpensesManufactory  SysExpensesManufactory=sysPaymentExpDetail_.getExpensesId();
//                    SysExpensesManufactory.setExpensesStatus(2);
//                    manufactoryFacade.editSysExpensesManufactory(SysExpensesManufactory);
                }
            }
             selected.setSysPaymentManufactoryExpdetailList(expdetal_add);
    //======================================================================================================================        
            //update paymentFactory
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate()); 

            selected.setFacTotal(total);
            selected.setFacVat(total_vat);
            selected.setFacVolume(total_volume);
            selected.setFacDivideEquipment(total_divide_equipment);
           // selected.setFacReam(total_ream);
            selected.setFacNet(total_net);
            
            paymentManufactoryFacade.editSysPaymentManufactory(selected);
         
            
            clearData();
            clearDatatTotal();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("factory/p105/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    @Override
    public void delete() {
        try {
            paymentManufactoryFacade.deleteSysPaymentManufactory(selected);
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
                
              items=paymentManufactoryFacade.findSysPaymentManufactoryListByCriteria(documentno, contractor_find, startDate, toDate);
              
//             lazyBillingModel = new LazyBillingDataModel(billingFacade,Constants.CREDIT_NOTE,documentno,StringUtils.trimToEmpty(companyName),startDate,toDate);
//             DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("listForm:billingTable");
//             dataTable.setFirst(0);
            // printSelected =new ArrayList<SysPayment>();
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
        clearData_sysPaymentManufactoryDetail();
    }
    
    public void searchManufactory(){
          try {                                               
//             manufactoryReal_items_dialog = manufactoryFacade.findSysManufactoryRealListByCriteria(null!=manufactory_selected?manufactory_selected.getDocumentno():null,
//                     null!=contractor_selected?contractor_selected:null,dialog_startDate,dialog_toDate,1);
               manufactoryReal_items_dialog = manufactoryFacade.findSysManufactoryRealListByCriteria(null!=manufactory_selected?manufactory_selected.getDocumentno():null,
                     null!=contractor_selected?contractor_selected:null,null,selected.getPaymentStartDate(),selected.getPaymentEndDate(),1);
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }  
      
    public void addDetail(){
         try {

              if (selected.getSysPaymentManufactoryDetailList()== null) 
                     selected.setSysPaymentManufactoryDetailList(new ArrayList<SysPaymentManufactoryDetail>());

                //is match
               List<Integer> sysFactory_list=new ArrayList();
               for(SysPaymentManufactoryDetail sysPaymentManufactoryDetail:selected.getSysPaymentManufactoryDetailList()){
                   //find จำนวนที่ทำจริง ใน db manufactory_Real
                   Double volumeReal = sysPaymentManufactoryDetail.getFactoryRealId().getVolumeReal();

                   Double valumeReal_ = 0.0;
                   if (null != volumeReal && volumeReal > 0) {
                       valumeReal_ = volumeReal;
                   }

                   sysPaymentManufactoryDetail.setVolume_real(valumeReal_);
                   
                   Double unitpirce = null != sysPaymentManufactoryDetail.getFactoryRealId().getManufactoryDetailId() ? sysPaymentManufactoryDetail.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice()  : 0.0;

                   Double total = 0.0;
                   //1(จำนวน)x55(ค่าแรง/เมตร)x3.5(ความยาว)=192.5
                   if (2 == sysPaymentManufactoryDetail.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getCalculateType()) {
                       Double length = null != sysPaymentManufactoryDetail.getFactoryRealId().getManufactoryDetailId().getLength() ? sysPaymentManufactoryDetail.getFactoryRealId().getManufactoryDetailId().getLength() : 0.0;
                       total += valumeReal_ * length * unitpirce;
                   } else {
                       total += unitpirce * valumeReal_;
                   }
                   sysPaymentManufactoryDetail.setTotal_volume_real(total);

                   sysFactory_list.add(sysPaymentManufactoryDetail.getFactoryRealId().getFactoryRealId());
               }

                for (SysManufactoryReal sysManufactoryReal : selectd_manufactoryReal_items_dialog) {
                   if (!sysFactory_list.contains(sysManufactoryReal.getFactoryRealId())) {
                       Double volumeReal = sysManufactoryReal.getVolumeReal();

                       Double valumeReal_ = 0.0;
                       if (null != volumeReal && volumeReal > 0) {
                           valumeReal_ = volumeReal;
                       }

                       facDetail_selected=new SysPaymentManufactoryDetail();
                       facDetail_selected.setManufactoryDetailId(sysManufactoryReal.getFactoryRealId());
                       facDetail_selected.setFactoryRealId(sysManufactoryReal);
                       facDetail_selected.setVolume_real(valumeReal_);
                      
                       Double unitpirce = null != sysManufactoryReal.getManufactoryDetailId().getManufacturingId() ? sysManufactoryReal.getManufactoryDetailId().getManufacturingId().getUnitPrice() : 0.0;
                      
                       Double total = 0.0;
                       //1(จำนวน)x55(ค่าแรง/เมตร)x3.5(ความยาว)=192.5
                       if (2 == sysManufactoryReal.getManufactoryDetailId().getManufacturingId().getCalculateType()) {
                           Double length = null != sysManufactoryReal.getManufactoryDetailId().getLength() ? sysManufactoryReal.getManufactoryDetailId().getLength() : 0.0;
                           total += valumeReal_ * length * unitpirce;
                       } else {
                           total += unitpirce * valumeReal_;
                       }
                      
                      //facDetail_selected.setTotal_volume_real(valumeReal_ * sysManufactoryReal.getManufactoryDetailId().getManufacturingId().getUnitPrice());
                      facDetail_selected.setTotal_volume_real(total);
                      selected.getSysPaymentManufactoryDetailList().add(facDetail_selected);

                      sysFactory_list.add(sysManufactoryReal.getFactoryRealId());
                   }
               }

               checkTotalPrice();
               clearData_sysPaymentManufactoryDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    
     public void clearData_sysPaymentManufactoryDetail(){
        facDetail_selected = new SysPaymentManufactoryDetail();
        manufactory_selected=new SysManufactory();
        manufactoryReal_items_dialog = new ArrayList();
        manufactoryExpenses_items_dialog = new ArrayList();
    }
     
    public void deleteDetail(){
        try {
             //delete total 
             selected.getSysPaymentManufactoryDetailList().remove(facDetail_selected);
             checkTotalPrice();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    
    /// Expensese Detail
     public void dialogExpClose(CloseEvent event) {
        clearData_sysPaymentManufactoryDetail();
    }
    
    public void searchManufactoryExpenses(){
          try {                                               
//             manufactoryExpenses_items_dialog = manufactoryFacade.findSysManufactoryExpensesListByCriteria(null!=manufactory_selected?manufactory_selected.getDocumentno():null,
//                     null!=contractor_selected?contractor_selected:null,dialog_startDate,dialog_toDate,1);
                manufactoryExpenses_items_dialog = manufactoryFacade.findSysManufactoryExpensesListByCriteria(null!=manufactory_selected?manufactory_selected.getDocumentno():null,
                     null!=contractor_selected?contractor_selected:null,selected.getPaymentStartDate(),selected.getPaymentEndDate(),1);
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }  
      
    public void addExpensesDetail(){
         try {

              if (selected.getSysPaymentManufactoryExpdetailList()== null) 
                     selected.setSysPaymentManufactoryExpdetailList(new ArrayList<SysPaymentManufactoryExpdetail>());

                //is match
               List<Integer> sysFactoryExpenses_list=new ArrayList();
               for(SysPaymentManufactoryExpdetail sysPaymentManufactoryExpdetail:selected.getSysPaymentManufactoryExpdetailList()){
                   //find จำนวนค่าใช้จ่าย ใน db manufactory_Expenses_detail
//                   Double totalExp=0.0;
//                   for(SysExpensesManufactoryDetail  sysExpensesManufactoryDetail: sysPaymentManufactoryExpdetail.getExpensesId().getSysExpensesManufactoryDetailList()){
//                       totalExp=totalExp+sysExpensesManufactoryDetail.getAmount();
//                   }
//                   sysPaymentManufactoryExpdetail.getExpensesId().setTotal_expenses(totalExp);

                   sysFactoryExpenses_list.add(sysPaymentManufactoryExpdetail.getExpensesId().getExpensesId());
               }

                for (SysExpensesManufactory sysExpensesManufactory : selectd_manufactoryExpenses_items_dialog) {
                   if (!sysFactoryExpenses_list.contains(sysExpensesManufactory.getExpensesId())) {
//                        Double totalExp=0.0;
//                        for(SysExpensesManufactoryDetail  sysExpensesManufactoryDetail: sysExpensesManufactory.getSysExpensesManufactoryDetailList()){
//                            totalExp=totalExp+sysExpensesManufactoryDetail.getAmount();
//                        }

                        facExpDetail_selected=new SysPaymentManufactoryExpdetail();
                        facExpDetail_selected.setManufactoryExpdetailId(sysExpensesManufactory.getExpensesId());
                        facExpDetail_selected.setExpensesId(sysExpensesManufactory);
                        //facExpDetail_selected.getExpensesId().setTotal_expenses(totalExp);
                        selected.getSysPaymentManufactoryExpdetailList().add(facExpDetail_selected);

                        sysFactoryExpenses_list.add(sysExpensesManufactory.getExpensesId());
                   }
               }

               checkTotalPrice();
               clearData_sysPaymentManufactoryDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    
    public void deleteDetailExp(){
        try {
             //delete total 
             selected.getSysPaymentManufactoryExpdetailList().remove(facExpDetail_selected);
             checkTotalPrice();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    
    public void checkTotalPrice(){
        Double total_ = 0.0,totalExp=0.0;
        if (null != selected.getSysPaymentManufactoryDetailList() && selected.getSysPaymentManufactoryDetailList().size()>0) {
            for (SysPaymentManufactoryDetail sysdetail : selected.getSysPaymentManufactoryDetailList()) {
                 Double volumeReal=0.0;
                 if(null !=sysdetail.getVolume_real() && sysdetail.getVolume_real()>0){
                     volumeReal=sysdetail.getVolume_real();
                 }
                 
                Double unitpirce = null != sysdetail.getFactoryRealId().getManufactoryDetailId() ? sysdetail.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice()  : 0.0;

                //1(จำนวน)x55(ค่าแรง/เมตร)x3.5(ความยาว)=192.5
                if (2 == sysdetail.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getCalculateType()) {
                    Double length = null != sysdetail.getFactoryRealId().getManufactoryDetailId().getLength() ? sysdetail.getFactoryRealId().getManufactoryDetailId().getLength() : 0.0;
                    total_ += volumeReal * length * unitpirce;
                } else {
                    total_ += unitpirce * volumeReal;
                }
                // total_=total_+(volumeReal* sysdetail.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice());
            }   
        }
        if (null != selected.getSysPaymentManufactoryExpdetailList()) {
            for (SysPaymentManufactoryExpdetail sysExpdetail : selected.getSysPaymentManufactoryExpdetailList()) {    
                 totalExp +=sysExpdetail.getExpensesId().getAmount();
//                 for(SysExpensesManufactoryDetail sysExpensesManufactoryDetail:sysExpdetail.getExpensesId().getSysExpensesManufactoryDetailList()){
//                     totalExp=totalExp+sysExpensesManufactoryDetail.getAmount();
//                 }
            }
        }
        
        if((null != selected.getSysPaymentManufactoryDetailList() && selected.getSysPaymentManufactoryDetailList().size()>0 ) ||
              (null != selected.getSysPaymentManufactoryExpdetailList() && selected.getSysPaymentManufactoryExpdetailList().size()>0) ){
             this.total = total_;
             this.total_vat = this.total  * 0.03;
             this.total_volume=this.total-this.total_vat;
             this.total_divide_equipment=totalExp;
             this.total_net=  this.total_volume-(totalExp);
        } else {
           clearDatatTotal();
        }
    }
    
//    public void checkRealTotalPrice(){
//        if(this.total_net>0.00){
//            this.total_net=  this.total_volume-(this.total_divide_equipment+this.total_ream);
//        }
//    }
    
   //===== end  Dialog=========   
    
    public void prepareEdit() {
        facDetail_selected=new SysPaymentManufactoryDetail();
    } 
   public void handleKeyEvent(){}
   
   
    public void runningNoCustomer() {
        String sequence_no=sequence.updateRunningNO(1,Constants.SEQUNCE_NO_PAYMENT_MANUFACTORY,"yyMM");
        this.selected.setDocumentNo(sequence_no);
    } 
   
 //Auto Complete==========================================================================  
    //Auto complete Contrator
    public List<SysContractor> completeContractor(String query) {
         List<SysContractor> filteredSysContractor = new ArrayList<>();
       try {
            List<SysContractor> allContractors = contractorFacade.findSysContractorList();
            for (SysContractor sysContractor:allContractors) {
               if(sysContractor.getContractorNickname()!=null && sysContractor.getContractorNickname().length()>0){
                if(sysContractor.getContractorNickname().toLowerCase().startsWith(query)) {
                    filteredSysContractor.add(sysContractor);
                }
               }
            }
             
            //remove
            if(null!=selected.getSysPaymentManufactoryDetailList())
               selected.getSysPaymentManufactoryDetailList().clear();
            
            if(null!=selected.getSysPaymentManufactoryExpdetailList())
               selected.getSysPaymentManufactoryExpdetailList().clear();
            
            clearData_sysManufactoryDetail();
            checkTotalPrice();
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredSysContractor;
    }
   
    //Auto factory
    public List<SysManufactory> completeManufactory(String query) {
         List<SysManufactory> filteredSysManufactory= new ArrayList<>();
       try {                                                
           
//           List<SysManufactoryReal> manufactoryReal= manufactoryFacade.findSysManufactoryRealListByCriteria(null,null!=contractor_selected?contractor_selected:null,null,null,1);
//            for (SysManufactoryReal sysManufactoryReal:manufactoryReal) {
//               if(sysManufactoryReal.getManufactoryDetailId().getFactoryId().getDocumentno()!=null && sysManufactoryReal.getManufactoryDetailId().getFactoryId().getDocumentno().length()>0){
//                if(sysManufactoryReal.getManufactoryDetailId().getFactoryId().getDocumentno().toLowerCase().startsWith(query)) {
//                    filteredSysManufactory.add(sysManufactoryReal.getManufactoryDetailId().getFactoryId());
//                }
//               }
//            }
//            
            List<SysManufactory> allSSysManufactory = manufactoryFacade.findSysManufactoryListRealByCriteria(null,null, contractor_selected,null, null, null);
            for (SysManufactory manufactory:allSSysManufactory) {
               if(manufactory.getDocumentno()!=null && manufactory.getDocumentno().length()>0){
                if(manufactory.getDocumentno().toLowerCase().startsWith(query.toLowerCase())) {
                    filteredSysManufactory.add(manufactory);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredSysManufactory;
    }

 
 //End Auto Complete==========================================================================    
    
    public void clearData_sysManufactoryDetail(){        
        manufactoryReal_items_dialog=new ArrayList<>();
        manufactoryExpenses_items_dialog=new ArrayList<>();
        
        selectd_manufactoryReal_items_dialog=new ArrayList<>();
        selectd_manufactoryExpenses_items_dialog=new ArrayList<>();
        
        
        manufactory_selected=new SysManufactory();
    }
    
      public String convertPriceToString(Double totalprice){
        if(totalprice==0.0){
            return "";
        }else{
            return new ThaiBaht().getText(totalprice);
        }
    }
      
     public void calculrateVat() {
        checkTotalPrice();
    }
     
     @Override
    public void reportPDF() {
        try {
            List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
            ReportUtil report = new ReportUtil();
            List reportList_ = new ArrayList<>();
            List<BillingReportBean> reportList_main = new ArrayList<>();
            List<BillingReportBean> reportList = new ArrayList<>();
            List reportListExpenses_ = new ArrayList<>();
            List<BillingReportBean> reportListExpenses_main = new ArrayList<>();
            List<BillingReportBean> reportListExpenses = new ArrayList<>();
            SysPaymentManufactory rpt_sysPaymentFac=paymentManufactoryFacade.findSysPaymentManufactoryById(selected.getPaymentFactoryId());
            
            int intRunningNo=1;
            List<SysPaymentManufactoryDetail> list = rpt_sysPaymentFac.getSysPaymentManufactoryDetailList();
            for (SysPaymentManufactoryDetail to : list) {
                BillingReportBean bean = new BillingReportBean();
                bean.setSeq(String.valueOf(intRunningNo++));
                String typeStr="";
                if(to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getCalculateType()==1){
                    typeStr="(จำนวน)";
                } else if (to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getCalculateType() == 2) {
                    typeStr = "(ความยาว/เมตร)(" + to.getFactoryRealId().getManufactoryDetailId().getLength() + ")";
                } else {
                    typeStr = "(ชุด)";
                }

                Double volumeReal = to.getFactoryRealId().getVolumeReal();
                Double valumeReal_ = 0.0;
                if (null != volumeReal && volumeReal > 0) {
                    valumeReal_ = volumeReal;
                }

                Double unitpirce = null != to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice() ? to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice() : 0.0;

                Double total = 0.0;
                //1(จำนวน)x55(ค่าแรง/เมตร)x3.5(ความยาว)=192.5
                if (2 == to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getCalculateType()) {
                    Double length = null != to.getFactoryRealId().getManufactoryDetailId().getLength() ? to.getFactoryRealId().getManufactoryDetailId().getLength() : 0.0;
                    total += valumeReal_ * length * unitpirce;
                } else {
                    total += unitpirce * valumeReal_;
                }

                bean.setDetail(to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getManufacturingDesc() + " " + typeStr);
                Double volumn = 0.0, priceUnit = 0.0;
                volumn = null != to.getFactoryRealId().getVolumeReal() ? to.getFactoryRealId().getVolumeReal() : 0.0;
                priceUnit = null != to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice() ? to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice() : 0.0;

                bean.setDocno(to.getFactoryRealId().getManufactoryDetailId().getFactoryId().getDocumentno());
                bean.setVolumn(NumberUtils.numberFormat(volumn, "#,##0.00"));
                bean.setUnit(null != to.getFactoryRealId().getManufactoryDetailId().getUnit() ? to.getFactoryRealId().getManufactoryDetailId().getUnit() : "");
                bean.setPriceUnit(NumberUtils.numberFormat(priceUnit, "#,##0.00"));
                bean.setPriceTotal(NumberUtils.numberFormat(total, "#,##0.00"));

                reportList.add(bean);
            }

            reportList_main.add(new BillingReportBean("", "", "", "", "", "", "", "", "", "", "",""));
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
            
            map.put("documentno",rpt_sysPaymentFac.getDocumentNo());
            map.put("contractor_name",rpt_sysPaymentFac.getContractorId().getContractorNameTh());
            map.put("contractor_address",rpt_sysPaymentFac.getContractorId().getContractorAddress());
            map.put("send_date",DateTimeUtil.cvtDateForShow(rpt_sysPaymentFac.getFactoryDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            map.put("taxid",null!=rpt_sysPaymentFac.getContractorId().getTaxId()?rpt_sysPaymentFac.getContractorId().getTaxId():"-");
            map.put("remark",StringUtils.isNotBlank(rpt_sysPaymentFac.getRemark())?rpt_sysPaymentFac.getRemark():"...........................................................................................................................................");
            
            map.put("total",NumberUtils.numberFormat(rpt_sysPaymentFac.getFacTotal(),"#,##0.00"));
            map.put("total_vat",NumberUtils.numberFormat(rpt_sysPaymentFac.getFacVat(),"#,##0.00"));
            map.put("total_volume",NumberUtils.numberFormat(rpt_sysPaymentFac.getFacVolume(),"#,##0.00"));
            map.put("total_expenses",NumberUtils.numberFormat(rpt_sysPaymentFac.getFacDivideEquipment(),"#,##0.00"));
            //map.put("total_divide_equipment",NumberUtils.numberFormat(rpt_sysPaymentFac.getFacDivideEquipment(),"#,##0.00"));
            //map.put("total_ream",NumberUtils.numberFormat(rpt_sysPaymentFac.getFacReam(),"#,##0.00"));
            map.put("total_net",NumberUtils.numberFormat(rpt_sysPaymentFac.getFacNet(),"#,##0.00"));
            map.put("price_char",(rpt_sysPaymentFac.getFacNet()==0.0?"":new ThaiBaht().getText(rpt_sysPaymentFac.getFacNet())));
            
            map.put("reportCode", "P105");
            JasperPrint print= report.exportSubReport_Template_mearge("template.jpg","p105", new String[]{"P105Report","P105SubReport"}, "Process", map, reportList_);
            jasperPrintList.add(print);
            
            //expenses
            if(null !=rpt_sysPaymentFac.getSysPaymentManufactoryExpdetailList() && rpt_sysPaymentFac.getSysPaymentManufactoryExpdetailList().size()>0){
                int intRunningNoExpenses=1;
                double total_category=0;
                List<SysPaymentManufactoryExpdetail> listExp = rpt_sysPaymentFac.getSysPaymentManufactoryExpdetailList();
                for (SysPaymentManufactoryExpdetail to : listExp) {
                    BillingReportBean bean = new BillingReportBean();
                    bean.setSeq(String.valueOf(intRunningNoExpenses++));
                    bean.setDetail(null!=to.getExpensesId().getDocumentNo()?to.getExpensesId().getDocumentNo():"");
                    bean.setCategory(to.getExpensesId().getDeductionId().getDeductionDesc());
                    bean.setSendDate(DateTimeUtil.cvtDateForShow(to.getExpensesId().getExpensesDate(), "dd/MM/yyyy", new Locale("th", "TH")));
                    double totalExp= (null!=to.getExpensesId().getAmount())?to.getExpensesId().getAmount():0.0;
                    bean.setPriceTotal(NumberUtils.numberFormat(totalExp,"#,##0.00"));
                    total_category=total_category+totalExp;
//                    double totalExp= (null!=to.getExpensesId().getTotal_expenses())?to.getExpensesId().getTotal_expenses():0.0;
//                    bean.setPriceTotal(NumberUtils.numberFormat(totalExp,"#,##0.00"));
//                    total_category=total_category+totalExp;

                    reportListExpenses.add(bean);
                }
                reportListExpenses_main.add(new BillingReportBean("", "", "", "", "", "", "", "","","","",""));
                reportListExpenses_.add(reportListExpenses_main);
                reportListExpenses_.add(reportListExpenses);
                HashMap mapExpenses = new HashMap();
                mapExpenses.put("contractor_name",rpt_sysPaymentFac.getContractorId().getContractorNameTh());
                mapExpenses.put("total_expenses",NumberUtils.numberFormat(total_category,"#,##0.00"));
                mapExpenses.put("price_char",(total_category==0.0?"":new ThaiBaht().getText(total_category)));

                mapExpenses.put("reportCode", "P105");
                JasperPrint printExpenses= report.exportSubReport_Template_mearge("template.jpg","p105", new String[]{"P105ExpensesReport","P105ExpensesSubReport"}, "Processๅ", mapExpenses, reportListExpenses_);
                jasperPrintList.add(printExpenses);
            }
            String pdfCode = "P105";
            String pdfName = pdfCode.concat("-").concat(DateTimeUtil.dateToString(DateTimeUtil.currentDate(), "yyyyMMddHHmmss"));
            report.exportMearge(pdfName, jasperPrintList);
                
                
//            //add print form
//            SysPrintBilling sysPrintBilling =new SysPrintBilling();
//            sysPrintBilling.setBillingId(selected);
//            sysPrintBilling.setUserId(userInfo.getAdminUser());
//            sysPrintBilling.setCreatedBy(userInfo.getAdminUser().getUsername());
//            sysPrintBilling.setCreatedDt(DateTimeUtil.getSystemDate());
//            billingFacade.createSysPrintBilling(sysPrintBilling);
            
          //  init();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
   
    public void printPdfMuti(){
         try {
             List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
             Collections.sort(printSelected, new SysPaymentManufactory());
             for (SysPaymentManufactory sysPaymentManufactory : printSelected) {
                  //SysPaymentManufactory rpt_sysPaymentFac = paymentManufactoryFacade.findSysPaymentManufactoryById(sysPaymentManufactory.getPaymentFactoryId());
               // List<JasperPrint> jasperPrintList_main = new ArrayList<JasperPrint>();
                ReportUtil report = new ReportUtil();
                List reportList_ = new ArrayList<>();
                List<BillingReportBean> reportList_main = new ArrayList<>();
                List<BillingReportBean> reportList = new ArrayList<>();
                List reportListExpenses_ = new ArrayList<>();
                List<BillingReportBean> reportListExpenses_main = new ArrayList<>();
                List<BillingReportBean> reportListExpenses = new ArrayList<>();
                SysPaymentManufactory rpt_sysPaymentFac=paymentManufactoryFacade.findSysPaymentManufactoryById(sysPaymentManufactory.getPaymentFactoryId());

                int intRunningNo=1;
                List<SysPaymentManufactoryDetail> list = rpt_sysPaymentFac.getSysPaymentManufactoryDetailList();
                for (SysPaymentManufactoryDetail to : list) {
                    BillingReportBean bean = new BillingReportBean();
                    bean.setSeq(String.valueOf(intRunningNo++));
                    String typeStr = "";
                    if (to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getCalculateType() == 1) {
                        typeStr = "(จำนวน)";
                    } else if (to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getCalculateType() == 2) {
                        typeStr = "(ความยาว/เมตร)(" + to.getFactoryRealId().getManufactoryDetailId().getLength() + ")";
                    } else {
                        typeStr = "(ชุด)";
                    }

                    Double volumeReal = to.getFactoryRealId().getVolumeReal();
                    Double valumeReal_ = 0.0;
                    if (null != volumeReal && volumeReal > 0) {
                        valumeReal_ = volumeReal;
                    }

                    Double unitpirce = null != to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice() ? to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice() : 0.0;

                    Double total = 0.0;
                    //1(จำนวน)x55(ค่าแรง/เมตร)x3.5(ความยาว)=192.5
                    if (2 == to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getCalculateType()) {
                        Double length = null != to.getFactoryRealId().getManufactoryDetailId().getLength() ? to.getFactoryRealId().getManufactoryDetailId().getLength() : 0.0;
                        total += valumeReal_ * length * unitpirce;
                    } else {
                        total += unitpirce * valumeReal_;
                    }                    

                    bean.setDetail(to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getManufacturingDesc() + " " + typeStr);
                    Double volumn=0.0,priceUnit=0.0;
                    volumn=null!=to.getFactoryRealId().getVolumeReal()?to.getFactoryRealId().getVolumeReal():0.0;
                    priceUnit=null!=to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice()?to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice():0.0;

                    bean.setVolumn(NumberUtils.numberFormat(volumn,"#,##0.00"));
                    bean.setUnit(null!=to.getFactoryRealId().getManufactoryDetailId().getUnit()?to.getFactoryRealId().getManufactoryDetailId().getUnit():"");
                    bean.setPriceUnit(NumberUtils.numberFormat(priceUnit,"#,##0.00"));
                    bean.setPriceTotal(NumberUtils.numberFormat(total,"#,##0.00"));

                    reportList.add(bean);
                }
                reportList_main.add(new BillingReportBean("", "", "", "", "", "", "", "","","","",""));
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

                map.put("documentno",rpt_sysPaymentFac.getDocumentNo());
                map.put("contractor_name",rpt_sysPaymentFac.getContractorId().getContractorNameTh());
                map.put("contractor_address",rpt_sysPaymentFac.getContractorId().getContractorAddress());
                map.put("send_date",DateTimeUtil.cvtDateForShow(rpt_sysPaymentFac.getFactoryDate(), "dd/MM/yyyy", new Locale("th", "TH")));
                map.put("taxid",null!=rpt_sysPaymentFac.getContractorId().getTaxId()?rpt_sysPaymentFac.getContractorId().getTaxId():"-");
                map.put("remark",StringUtils.isNotBlank(rpt_sysPaymentFac.getRemark())?rpt_sysPaymentFac.getRemark():"...........................................................................................................................................");

                map.put("total",NumberUtils.numberFormat(rpt_sysPaymentFac.getFacTotal(),"#,##0.00"));
                map.put("total_vat",NumberUtils.numberFormat(rpt_sysPaymentFac.getFacVat(),"#,##0.00"));
                map.put("total_volume",NumberUtils.numberFormat(rpt_sysPaymentFac.getFacVolume(),"#,##0.00"));
                map.put("total_expenses",NumberUtils.numberFormat(rpt_sysPaymentFac.getFacDivideEquipment(),"#,##0.00"));
                //map.put("total_divide_equipment",NumberUtils.numberFormat(rpt_sysPaymentFac.getFacDivideEquipment(),"#,##0.00"));
                //map.put("total_ream",NumberUtils.numberFormat(rpt_sysPaymentFac.getFacReam(),"#,##0.00"));
                map.put("total_net",NumberUtils.numberFormat(rpt_sysPaymentFac.getFacNet(),"#,##0.00"));
                map.put("price_char",(rpt_sysPaymentFac.getFacNet()==0.0?"":new ThaiBaht().getText(rpt_sysPaymentFac.getFacNet())));

                map.put("reportCode", "P105");
                JasperPrint print= report.exportSubReport_Template_mearge("template.jpg","p105", new String[]{"P105Report","P105SubReport"}, "Process", map, reportList_);
                jasperPrintList.add(print);

                //expenses
                if(null !=rpt_sysPaymentFac.getSysPaymentManufactoryExpdetailList() && rpt_sysPaymentFac.getSysPaymentManufactoryExpdetailList().size()>0){
                    int intRunningNoExpenses=1;
                    double total_category=0;
                    List<SysPaymentManufactoryExpdetail> listExp = rpt_sysPaymentFac.getSysPaymentManufactoryExpdetailList();
                    for (SysPaymentManufactoryExpdetail to : listExp) {
                        BillingReportBean bean = new BillingReportBean();
                        bean.setSeq(String.valueOf(intRunningNoExpenses++));
                        bean.setDetail(null!=to.getExpensesId().getDocumentNo()?to.getExpensesId().getDocumentNo():"");
                        bean.setCategory(to.getExpensesId().getDeductionId().getDeductionDesc());
                        bean.setSendDate(DateTimeUtil.cvtDateForShow(to.getExpensesId().getExpensesDate(), "dd/MM/yyyy", new Locale("th", "TH")));
                        double totalExp = (null != to.getExpensesId().getAmount()) ? to.getExpensesId().getAmount() : 0.0;
                        bean.setPriceTotal(NumberUtils.numberFormat(totalExp, "#,##0.00"));
                        total_category = total_category + totalExp;
//                        double totalExp= (null!=to.getExpensesId().getTotal_expenses())?to.getExpensesId().getTotal_expenses():0.0;
//                        bean.setPriceTotal(NumberUtils.numberFormat(totalExp,"#,##0.00"));
//                        total_category=total_category+totalExp;

                        reportListExpenses.add(bean);
                    }
                    reportListExpenses_main.add(new BillingReportBean("", "", "", "", "", "", "", "","","","",""));
                    reportListExpenses_.add(reportListExpenses_main);
                    reportListExpenses_.add(reportListExpenses);
                    HashMap mapExpenses = new HashMap();
                    mapExpenses.put("contractor_name",rpt_sysPaymentFac.getContractorId().getContractorNameTh());
                    mapExpenses.put("total_expenses",NumberUtils.numberFormat(total_category,"#,##0.00"));
                    mapExpenses.put("price_char",(total_category==0.0?"":new ThaiBaht().getText(total_category)));

                    mapExpenses.put("reportCode", "P105");
                    JasperPrint printExpenses= report.exportSubReport_Template_mearge("template.jpg","p105", new String[]{"P105ExpensesReport","P105ExpensesSubReport"}, "Process1", mapExpenses, reportListExpenses_);
                    jasperPrintList.add(printExpenses);
                }

                    //add print form
//                    SysPrintBilling sysPrintBilling =new SysPrintBilling();
//                    sysPrintBilling.setBillingId(sysBilling);
//                    sysPrintBilling.setUserId(userInfo.getAdminUser());
//                    sysPrintBilling.setCreatedBy(userInfo.getAdminUser().getUsername());
//                    sysPrintBilling.setCreatedDt(DateTimeUtil.getSystemDate());
//                    billingFacade.createSysPrintBilling(sysPrintBilling);

             }
             if(!printSelected.isEmpty()){
                String pdfCode="P105";
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

   

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getTotal_th() {
        return total_th;
    }

    public void setTotal_th(String total_th) {
        this.total_th = total_th;
    }

    public UserInfoController getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoController userInfo) {
        this.userInfo = userInfo;
    }

    public Double getTotal_vat() {
        return total_vat;
    }

    public void setTotal_vat(Double total_vat) {
        this.total_vat = total_vat;
    }

    public Double getTotal_net() {
        return total_net;
    }

    public void setTotal_net(Double total_net) {
        this.total_net = total_net;
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

    public Double getTotal_volume() {
        return total_volume;
    }

    public void setTotal_volume(Double total_volume) {
        this.total_volume = total_volume;
    }

    public Double getTotal_divide_equipment() {
        return total_divide_equipment;
    }

    public void setTotal_divide_equipment(Double total_divide_equipment) {
        this.total_divide_equipment = total_divide_equipment;
    }

    public Double getTotal_ream() {
        return total_ream;
    }

    public void setTotal_ream(Double total_ream) {
        this.total_ream = total_ream;
    }

    

    public int getDraftNo() {
        return draftNo;
    }

    public void setDraftNo(int draftNo) {
        this.draftNo = draftNo;
    }

    public ContractorFacade getContractorFacade() {
        return contractorFacade;
    }

    public void setContractorFacade(ContractorFacade contractorFacade) {
        this.contractorFacade = contractorFacade;
    }

    public LazyDataModel<SysPaymentManufactory> getLazyPaymentManufactoryModel() {
        return lazyPaymentManufactoryModel;
    }

    public void setLazyPaymentManufactoryModel(LazyDataModel<SysPaymentManufactory> lazyPaymentManufactoryModel) {
        this.lazyPaymentManufactoryModel = lazyPaymentManufactoryModel;
    }

    public List<SysPaymentManufactory> getItems() {
        return items;
    }

    public void setItems(List<SysPaymentManufactory> items) {
        this.items = items;
    }

    public SysPaymentManufactory getSelected() {
        return selected;
    }

    public void setSelected(SysPaymentManufactory selected) {
        this.selected = selected;
    }

    public List<SysPaymentManufactory> getPrintSelected() {
        return printSelected;
    }

    public void setPrintSelected(List<SysPaymentManufactory> printSelected) {
        this.printSelected = printSelected;
    }

    public SysPaymentManufactoryDetail getFacDetail_selected() {
        return facDetail_selected;
    }

    public void setFacDetail_selected(SysPaymentManufactoryDetail facDetail_selected) {
        this.facDetail_selected = facDetail_selected;
    }

    public SysContractor getContractor_find() {
        return contractor_find;
    }

    public void setContractor_find(SysContractor contractor_find) {
        this.contractor_find = contractor_find;
    }

    public SysContractor getContractor_selected() {
        return contractor_selected;
    }

    public void setContractor_selected(SysContractor contractor_selected) {
        this.contractor_selected = contractor_selected;
    }

    public SysManufacturing getManufacturing_selected() {
        return manufacturing_selected;
    }

    public void setManufacturing_selected(SysManufacturing manufacturing_selected) {
        this.manufacturing_selected = manufacturing_selected;
    }

//    public Date getDialog_startDate() {
//        return dialog_startDate;
//    }
//
//    public void setDialog_startDate(Date dialog_startDate) {
//        this.dialog_startDate = dialog_startDate;
//    }
//
//    public Date getDialog_toDate() {
//        return dialog_toDate;
//    }
//
//    public void setDialog_toDate(Date dialog_toDate) {
//        this.dialog_toDate = dialog_toDate;
//    }

    public List<SysManufactoryReal> getManufactoryReal_items_dialog() {
        return manufactoryReal_items_dialog;
    }

    public void setManufactoryReal_items_dialog(List<SysManufactoryReal> manufactoryReal_items_dialog) {
        this.manufactoryReal_items_dialog = manufactoryReal_items_dialog;
    }

    public SysManufactory getManufactory_selected() {
        return manufactory_selected;
    }

    public void setManufactory_selected(SysManufactory manufactory_selected) {
        this.manufactory_selected = manufactory_selected;
    }

    public List<SysManufactoryReal> getSelectd_manufactoryReal_items_dialog() {
        return selectd_manufactoryReal_items_dialog;
    }

    public void setSelectd_manufactoryReal_items_dialog(List<SysManufactoryReal> selectd_manufactoryReal_items_dialog) {
        this.selectd_manufactoryReal_items_dialog = selectd_manufactoryReal_items_dialog;
    }

    public List<SysExpensesManufactory> getManufactoryExpenses_items_dialog() {
        return manufactoryExpenses_items_dialog;
    }

    public void setManufactoryExpenses_items_dialog(List<SysExpensesManufactory> manufactoryExpenses_items_dialog) {
        this.manufactoryExpenses_items_dialog = manufactoryExpenses_items_dialog;
    }

    public List<SysExpensesManufactory> getSelectd_manufactoryExpenses_items_dialog() {
        return selectd_manufactoryExpenses_items_dialog;
    }

    public void setSelectd_manufactoryExpenses_items_dialog(List<SysExpensesManufactory> selectd_manufactoryExpenses_items_dialog) {
        this.selectd_manufactoryExpenses_items_dialog = selectd_manufactoryExpenses_items_dialog;
    }

    public SysPaymentManufactoryExpdetail getFacExpDetail_selected() {
        return facExpDetail_selected;
    }

    public void setFacExpDetail_selected(SysPaymentManufactoryExpdetail facExpDetail_selected) {
        this.facExpDetail_selected = facExpDetail_selected;
    }

   
}