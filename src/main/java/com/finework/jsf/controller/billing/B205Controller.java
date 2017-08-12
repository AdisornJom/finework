package com.finework.jsf.controller.billing;

import com.finework.core.ejb.entity.SysBilling;
import com.finework.core.ejb.entity.SysPaymentDetail;
import com.finework.core.ejb.entity.SysDetail;
import com.finework.core.ejb.entity.SysPayment;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.core.util.Constants;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.ThaiBaht;
import com.finework.ejb.facade.BillingFacade;
import com.finework.ejb.facade.CustomerFacade;
import com.finework.ejb.facade.PaymentFacade;
import com.finework.ejb.facade.StockFacade;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.SequenceController;
import com.finework.jsf.common.UserInfoController;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Adisorn Jomjanyong
 */

@Named(B205Controller.CONTROLLER_NAME)
@SessionScoped
public class B205Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(B205Controller.class);
    public static final String CONTROLLER_NAME = "b205Controller";
    
    @Inject
    private PaymentFacade paymentFacade;
    @Inject
    private BillingFacade billingFacade;
    @Inject
    private CustomerFacade customerFacade;
    @Inject
    private StockFacade stockFacade;
    @Inject
    private UserInfoController userInfo;
    @Inject
    private SequenceController sequence;
    
    private LazyDataModel<SysPayment> lazyBillingModel;
    
    //
    private List<SysPayment> items;
    private SysPayment selected;
    private List<SysPayment> printSelected;

    //detial 
    private SysPaymentDetail blDetail_selected;
    
    
    //find criteria main
    private String documentno;
    private String companyName;
    private SysWorkunit workunit_find;
    private Date startDate;
    private Date toDate;
    
    //variable
    private Double total=0.0;
    private Double total_discount=0.0;
    private Double total_vat=0.0;
    private Double total_net=0.0;
    private Double realTotalPrice;
    private String total_th;
    
    private int draftNo;
    
    
    //auto complete
    //private SysCustomer cust_selected;
    private SysWorkunit workunit_selected;
    private SysDetail detail_selected;
    
    //find dialog
    private String documentNoBill;
    private SysBilling billing_selected;

    private List<SysBilling> billing_items_dialog;
    private List<SysBilling> selectd_billing_items_dialog;

   
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
        selected = new SysPayment();
        blDetail_selected=new SysPaymentDetail();
//        runningNoCustomer();
        next(page);
    }
    public void prepareCreateDraft(String page) {
        selected = new SysPayment();
        blDetail_selected=new SysPaymentDetail();
        
        String sequence_no=sequence.runningNO(1,Constants.SEQUNCE_NO_GOOD_RECEIPT_SALE_INVOICE);
        this.selected.setDocumentno(sequence_no);
        next(page);
    }
    
    public void prepareEdit(String page) {
        this.total=selected.getRealTotalPrice();
       // this.realTotalPrice=selected.getRealTotalPrice();
        this.workunit_selected=selected.getWorkunitId();
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
        this.total_discount=0.0;
        this.total_vat=0.0;
        this.total_net=0.0;
        this.total = 0.00;
        this.realTotalPrice=0.00;
    }
     
    public void clearData(){
         selected = new SysPayment();
         workunit_selected=new SysWorkunit();
    }
   
    @Override
    public void create() {
      try {

            if (null==selected.getPaymentDate()){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่ทำรายการ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (null==workunit_selected) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "หน่วยงาน"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (null==selected.getSysPaymentDetailList() || selected.getSysPaymentDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            //insert Payment
            selected.setPaymentType(Constants.PAYMENT_B205);
            selected.setWorkunitId(workunit_selected);
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            
            //insertDetail
            Double total_detail=0.0;
            List<SysPaymentDetail> detal_add=new ArrayList();
            for(SysPaymentDetail sysPaymentDetail_:selected.getSysPaymentDetailList()){
                sysPaymentDetail_.setId(null);//auto generate id on db
                sysPaymentDetail_.setPaymentId(selected);
                total_detail=total_detail+sysPaymentDetail_.getTotalPrice();
                detal_add.add(sysPaymentDetail_);
                
                SysBilling sysBilling= sysPaymentDetail_.getBillingId();
                sysBilling.setReceiveMoney(1); //save receive money complete
                billingFacade.editSysBilling(sysBilling);
            }

            selected.setSysPaymentDetailList(detal_add);
            selected.setRealTotalPrice(total_detail);
            
            runningNoCustomer();
            
            paymentFacade.createSysPayment(selected);
         
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
            
            if (null==selected.getSysPaymentDetailList() || selected.getSysPaymentDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }      
            
            //find old detail
            List<SysPaymentDetail> paymentDetailList=paymentFacade.findSysPaymentDetailList(selected.getPaymentId());
            for(SysPaymentDetail detail:paymentDetailList){
                SysBilling sysBilling= detail.getBillingId();
                sysBilling.setReceiveMoney(null);
                billingFacade.editSysBilling(sysBilling);
            }
            
            //deleteDetail
             paymentFacade.deletePaymentIdOnDetail(selected.getPaymentId());
             
            //insertDetail
            Double total_detail=0.0;
            List<SysPaymentDetail> detal_edit=new ArrayList();
            for(SysPaymentDetail sysBillDetail_:selected.getSysPaymentDetailList()){
                sysBillDetail_.setId(null);//auto generate id on db
                sysBillDetail_.setPaymentId(selected);
                total_detail=total_detail+sysBillDetail_.getTotalPrice();
                detal_edit.add(sysBillDetail_);
                
                SysBilling sysBilling= sysBillDetail_.getBillingId();
                sysBilling.setReceiveMoney(1); //save receive money complete
                billingFacade.editSysBilling(sysBilling);
            }
            
            //update Billing
            selected.setSysPaymentDetailList(detal_edit);
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate()); 
            
            selected.setRealTotalPrice(total_detail);
            paymentFacade.editSysPayment(selected);
         
            clearData();
            clearDatatTotal();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("billing/b205/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    @Override
    public void delete() {
        try {
            paymentFacade.deleteSysPayment(selected);
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
                
              items=paymentFacade.findSysPaymentListByCriteria(Constants.PAYMENT_B205,documentno,workunit_find,startDate,toDate);
              
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
        clearData_sysPaymentDetail();
    }
    public void searchBilling(){
          try {
             billing_items_dialog = billingFacade.findSysBillingCustomerWorkunitListByCriteria(
                    Constants.BILLING_GOOD_RECEIPT, Constants.BILLING_SALES_INVOICE,documentNoBill, null!=billing_selected?billing_selected.getCustomerId():null,workunit_selected);
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }
    public void addDetail(){
         try {
             
             if (selected.getSysPaymentDetailList()== null) 
                   selected.setSysPaymentDetailList(new ArrayList<SysPaymentDetail>());
             
              //is match
             List<Integer> sysBilling_list=new ArrayList();
             for(SysPaymentDetail sysPaymentDetail:selected.getSysPaymentDetailList()){
                 sysBilling_list.add(sysPaymentDetail.getBillingId().getBillingId());
             }

              for (SysBilling sysBilling : selectd_billing_items_dialog) {
                 if (!sysBilling_list.contains(sysBilling.getBillingId())) {
                    blDetail_selected=new SysPaymentDetail();
                    blDetail_selected.setId(sysBilling.getBillingId());
                    blDetail_selected.setBillingId(sysBilling);
                    blDetail_selected.setTotalPrice(sysBilling.getRealTotalPrice());
                    selected.getSysPaymentDetailList().add(blDetail_selected);
                    
                    sysBilling_list.add(sysBilling.getBillingId());
                 }
             }
             
             checkTotalPrice();
             clearData_sysPaymentDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    public void deleteDetail(){
        try {
             //delete total 
             selected.getSysPaymentDetailList().remove(blDetail_selected);
             clearData_sysPaymentDetail();
             checkTotalPrice();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    
    public void checkTotalPrice(){
        Double total_ = 0.0;
        if (null != selected.getSysPaymentDetailList() && selected.getSysPaymentDetailList().size()>0) {
            for (SysPaymentDetail sysdetail : selected.getSysPaymentDetailList()) {
                total_ = total_ + sysdetail.getTotalPrice();
            }
             this.total = total_;
           //  Double totaldiscount=this.total-this.total_discount;
           //  this.total_vat =totaldiscount * 0.07;
           //  this.total_net = totaldiscount + this.total_vat ;
          //   this.realTotalPrice=this.total_net;
            this.realTotalPrice= total_;
        } else {
           clearDatatTotal();
        }
    }
    
    public void checkRealTotalPrice(){
        if(this.realTotalPrice>0.00){
            Double billreal=this.total_net-this.realTotalPrice;
            this.total_vat=this.total_vat-billreal;
            this.total_net = this.total_net - billreal ;
        }
    }
    
    public void clearData_sysPaymentDetail(){
        billing_items_dialog=new ArrayList<>();
        selectd_billing_items_dialog=new ArrayList<>();
        billing_selected=new SysBilling();
        documentNoBill="";
    }
   //===== end  Dialog=========   
    
    public void prepareEdit() {
        blDetail_selected=new SysPaymentDetail();
    } 
   public void handleKeyEvent(){}
   
 //Auto Complete==========================================================================  
   //Auto complete customer
   public List<SysBilling> completeBilling(String query) {
         List<SysBilling> filteredSysBilling = new ArrayList<>();
       try {
            List<SysBilling> allSysBilling = billingFacade.findSysBillingWorkunitList(
                    Constants.BILLING_GOOD_RECEIPT, Constants.BILLING_SALES_INVOICE, workunit_selected);
            for (SysBilling sysBilling:allSysBilling) {
               if(sysBilling.getCustomerId().getCustomerNameTh()!=null && sysBilling.getCustomerId().getCustomerNameTh().length()>0){
                if(sysBilling.getCustomerId().getCustomerNameTh().toLowerCase().startsWith(query)) {
                    filteredSysBilling.add(sysBilling);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredSysBilling;
    }
   
    public void runningNoCustomer() {
        String sequence_no=sequence.updateRunningNO(1,Constants.SEQUNCE_NO_B205,"yyMM");
        this.selected.setDocumentno(sequence_no);
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
            selected.getSysPaymentDetailList().clear();
            clearData_sysPaymentDetail();
            checkTotalPrice();
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
   
    //Auto complete Detail
    public List<SysDetail> completeDetail(String query) {
         List<SysDetail> filteredSysDetail= new ArrayList<>();
       try {
            List<SysDetail> allSysDetail = stockFacade.findSysDetailList();
            for (SysDetail sysSysDetail:allSysDetail) {
               if(sysSysDetail.getDetailDesc()!=null && sysSysDetail.getDetailDesc().length()>0){
                if(sysSysDetail.getDetailDesc().toLowerCase().startsWith(query)) {
                    filteredSysDetail.add(sysSysDetail);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredSysDetail;
    }

 
 //End Auto Complete==========================================================================    
    
    
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
    

    public List<SysPayment> getItems() {
        return items;
    }

    public void setItems(List<SysPayment> items) {
        this.items = items;
    }

    public SysPayment getSelected() {
        return selected;
    }

    public void setSelected(SysPayment selected) {
        this.selected = selected;
    }

    public SysPaymentDetail getBlDetail_selected() {
        return blDetail_selected;
    }

    public void setBlDetail_selected(SysPaymentDetail blDetail_selected) {
        this.blDetail_selected = blDetail_selected;
    }

  

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public SysBilling getBilling_selected() {
        return billing_selected;
    }

    public void setBilling_selected(SysBilling billing_selected) {
        this.billing_selected = billing_selected;
    }

   
    
   

    public SysWorkunit getWorkunit_selected() {
        return workunit_selected;
    }

    public void setWorkunit_selected(SysWorkunit workunit_selected) {
        this.workunit_selected = workunit_selected;
    }

    public SysDetail getDetail_selected() {
        return detail_selected;
    }

    public void setDetail_selected(SysDetail detail_selected) {
        this.detail_selected = detail_selected;
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

    public Double getTotal_discount() {
        return total_discount;
    }

    public void setTotal_discount(Double total_discount) {
        this.total_discount = total_discount;
    }


    public LazyDataModel<SysPayment> getLazyBillingModel() {
        return lazyBillingModel;
    }

    public Double getRealTotalPrice() {
        return realTotalPrice;
    }

    public void setRealTotalPrice(Double realTotalPrice) {
        this.realTotalPrice = realTotalPrice;
    }

    public List<SysPayment> getPrintSelected() {
        return printSelected;
    }

    public void setPrintSelected(List<SysPayment> printSelected) {
        this.printSelected = printSelected;
    }

    public int getDraftNo() {
        return draftNo;
    }

    public void setDraftNo(int draftNo) {
        this.draftNo = draftNo;
    }

    public String getDocumentNoBill() {
        return documentNoBill;
    }

    public void setDocumentNoBill(String documentNoBill) {
        this.documentNoBill = documentNoBill;
    }

    public SysWorkunit getWorkunit_find() {
        return workunit_find;
    }

    public void setWorkunit_find(SysWorkunit workunit_find) {
        this.workunit_find = workunit_find;
    }


    

    public List<SysBilling> getBilling_items_dialog() {
        return billing_items_dialog;
    }

    public void setBilling_items_dialog(List<SysBilling> billing_items_dialog) {
        this.billing_items_dialog = billing_items_dialog;
    }

    public List<SysBilling> getSelectd_billing_items_dialog() {
        return selectd_billing_items_dialog;
    }

    public void setSelectd_billing_items_dialog(List<SysBilling> selectd_billing_items_dialog) {
        this.selectd_billing_items_dialog = selectd_billing_items_dialog;
    }


   
}