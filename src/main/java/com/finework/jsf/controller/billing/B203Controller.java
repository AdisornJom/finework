package com.finework.jsf.controller.billing;

import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysOrganization;
import com.finework.core.ejb.entity.SysPaymentDetail;
import com.finework.core.ejb.entity.SysPayment;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.core.util.Constants;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.NumberUtils;
import com.finework.core.util.ReportUtil;
import com.finework.core.util.ThaiBaht;
import com.finework.ejb.facade.CustomerFacade;
import com.finework.ejb.facade.OrganizationFacade;
import com.finework.ejb.facade.PaymentFacade;
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
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;

/**
 *
 * @author Adisorn Jomjanyong
 */

@Named(B203Controller.CONTROLLER_NAME)
@SessionScoped
public class B203Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(B203Controller.class);
    public static final String CONTROLLER_NAME = "b203Controller";
    
    @Inject
    private PaymentFacade paymentFacade;
    @Inject
    private CustomerFacade customerFacade;
    @Inject
    private UserInfoController userInfo;
    @Inject
    private OrganizationFacade organizationFacade;
    @Inject
    private SequenceController sequence;
    
    //
    private List<SysPayment> items;
    private SysPayment selected;
    private List<SysPayment> printSelected;

    //detial 
    private SysPaymentDetail pyDetail_selected;
    
    
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
    private SysWorkunit workunit_selected;
    private SysCustomer cust_selected;
   
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
        pyDetail_selected=new SysPaymentDetail();
//        runningNoCustomer();
        next(page);
    }
    public void prepareCreateDraft(String page) {
        selected = new SysPayment();
        pyDetail_selected=new SysPaymentDetail();
        
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
            selected.setPaymentType(Constants.PAYMENT_B203);
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
            next("billing/b203/index");
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
                
               items=paymentFacade.findSysPaymentListByCriteria(Constants.PAYMENT_B203,documentno,workunit_find,startDate,toDate);

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

    public void addDetail(){
         try {
             
              //validate field iteam 
            if (cust_selected==null
                    ||pyDetail_selected.getTotalPrice()==null) {
                return;
            }
            if (pyDetail_selected.getTotalPrice()<=0 ) {
               return;
            }

            if (selected.getSysPaymentDetailList() == null) 
                 selected.setSysPaymentDetailList(new ArrayList<SysPaymentDetail>());
             
              //is match
             List<Integer> sysCustomer_list=new ArrayList();
             for(SysPaymentDetail sysPaymentDetail:selected.getSysPaymentDetailList()){
                 sysCustomer_list.add(sysPaymentDetail.getCustomerId().getCustomerId());
             }

             if (!sysCustomer_list.contains(cust_selected.getCustomerId())) {
                 pyDetail_selected.setCustomerId(cust_selected);
                 pyDetail_selected.setTotalPrice(pyDetail_selected.getTotalPrice());
                 selected.getSysPaymentDetailList().add(pyDetail_selected);

                 sysCustomer_list.add(cust_selected.getCustomerId());
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
             selected.getSysPaymentDetailList().remove(pyDetail_selected);
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
        pyDetail_selected = new SysPaymentDetail();
        cust_selected=new SysCustomer();
    }
   //===== end  Dialog=========   
    
    public void prepareEdit() {
        pyDetail_selected=new SysPaymentDetail();
    } 
   public void handleKeyEvent(){}
   
 //Auto Complete==========================================================================  
   //Auto complete customer
   public List<SysCustomer> completeCustomer(String query) {
       List<SysCustomer> filteredCustomers = new ArrayList<>();
       try {
            List<SysCustomer> allCustomers = customerFacade.findSysCustomerList();
            for (SysCustomer sysCustomer:allCustomers) {
               if(sysCustomer.getCustomerNameTh()!=null && sysCustomer.getCustomerNameTh().length()>0){
                if(sysCustomer.getCustomerNameTh().toLowerCase().startsWith(query)) {
                    filteredCustomers.add(sysCustomer);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredCustomers;
    }
   
    public void runningNoCustomer() {
        String sequence_no=sequence.updateRunningNO(1,Constants.SEQUNCE_NO_B203,"yyMM");
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
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredWorkunit;
    }
   
    //Auto complete workunit
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
 
 //End Auto Complete==========================================================================    
     
   @Override
    public void reportPDF() {
        try {
            ReportUtil report = new ReportUtil();
            List reportList_ = new ArrayList<>();
            List<PaymentReportBean> reportList_main = new ArrayList<>();
            List<PaymentReportBean> reportList = new ArrayList<>();
            SysPayment rpt_sysPayment=paymentFacade.findByPK(selected.getPaymentId());
            
            
            List<SysPaymentDetail> list = rpt_sysPayment.getSysPaymentDetailList();
            for (SysPaymentDetail to : list) {
                PaymentReportBean bean = new PaymentReportBean();
                bean.setCustomer(null!=to.getCustomerId()?to.getCustomerId().getCustomerNameTh():"");
                bean.setPrice(null!=to.getTotalPrice()?NumberUtils.numberFormat(to.getTotalPrice(),"#,##0.00"):"");
                bean.setPrice_txt(null!=to.getTotalPrice()?"(   "+convertPriceToString(to.getTotalPrice())+"   )":"");
                
                reportList.add(bean);
            }
            
            reportList_main.add(new PaymentReportBean("", "", ""));
            reportList_.add(reportList_main);
            reportList_.add(reportList);
            HashMap map = new HashMap();
            SysOrganization org= organizationFacade.findSysOrganizationByStatus("Y");
            map.put("org_name_th",org.getOrgNameTh());
            map.put("org_name_eng",org.getOrgNameEng());
            map.put("org_address_th",org.getOrgAddressTh());
            map.put("org_address_th_detail","ที่อยู่ "+org.getOrgAddressTh());
            map.put("org_address_en",org.getOrgAddressEn());
            map.put("org_tel",org.getOrgTel());
            map.put("org_branch",org.getOrgBranch());
            map.put("org_taxid",org.getOrgTax());
            map.put("org_bank",org.getOrgBank());
            map.put("org_bank_name",org.getOrgBankName());
            map.put("org_recall",org.getOrgRecall());
            
            map.put("documentno",rpt_sysPayment.getDocumentno());
            map.put("payment_date",DateTimeUtil.cvtDateForShow(rpt_sysPayment.getPaymentDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            map.put("workunit",null!=rpt_sysPayment.getWorkunitId()?rpt_sysPayment.getWorkunitId().getWorkunitName():"-");
            map.put("remark",StringUtils.isNotBlank(rpt_sysPayment.getRemark())?rpt_sysPayment.getRemark():"...........................................................................................");
           
            
            map.put("reportCode", "B203");
            report.exportSubReport_Template("template.jpg","b203", new String[]{"B203Report","B203SubReport"}, "Payment", map, reportList_); 
            
//            //add print form
//            SysPrintBilling sysPrintBilling =new SysPrintBilling();
//            sysPrintBilling.setBillingId(selected);
//            sysPrintBilling.setUserId(userInfo.getAdminUser());
//            sysPrintBilling.setCreatedBy(userInfo.getAdminUser().getUsername());
//            sysPrintBilling.setCreatedDt(DateTimeUtil.getSystemDate());
//            billingFacade.createSysPrintBilling(sysPrintBilling);
//            
            init();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
    public void printPdfMuti(){
         try {
             List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
             Collections.sort(printSelected, new SysPayment());
             for (SysPayment sysBilling : printSelected) {
                    ReportUtil report = new ReportUtil();
                    List reportList_ = new ArrayList<>();
                    List<PaymentReportBean> reportList_main = new ArrayList<>();
                    List<PaymentReportBean> reportList = new ArrayList<>();
                    SysPayment rpt_sysPayment=paymentFacade.findByPK(sysBilling.getPaymentId());

                    List<SysPaymentDetail> list = rpt_sysPayment.getSysPaymentDetailList();
                    for (SysPaymentDetail to : list) {
                        PaymentReportBean bean = new PaymentReportBean();
                        bean.setCustomer(null!=to.getCustomerId()?to.getCustomerId().getCustomerNameTh():"");
                        bean.setPrice(null!=to.getTotalPrice()?NumberUtils.numberFormat(to.getTotalPrice(),"#,##0.00"):"");
                        bean.setPrice_txt(null!=to.getTotalPrice()?"(   "+convertPriceToString(to.getTotalPrice())+"   )":"");

                        reportList.add(bean);
                    }

                    reportList_main.add(new PaymentReportBean("", "", ""));
                    reportList_.add(reportList_main);
                    reportList_.add(reportList);
                    HashMap map = new HashMap();
                    SysOrganization org= organizationFacade.findSysOrganizationByStatus("Y");
                    map.put("org_name_th",org.getOrgNameTh());
                    map.put("org_name_eng",org.getOrgNameEng());
                    map.put("org_address_th",org.getOrgAddressTh());
                    map.put("org_address_th_detail","ที่อยู่ "+org.getOrgAddressTh());
                    map.put("org_address_en",org.getOrgAddressEn());
                    map.put("org_tel",org.getOrgTel());
                    map.put("org_branch",org.getOrgBranch());
                    map.put("org_taxid",org.getOrgTax());
                    map.put("org_bank",org.getOrgBank());
                    map.put("org_bank_name",org.getOrgBankName());
                    map.put("org_recall",org.getOrgRecall());

                    map.put("documentno",rpt_sysPayment.getDocumentno());
                    map.put("payment_date",DateTimeUtil.cvtDateForShow(rpt_sysPayment.getPaymentDate(), "dd/MM/yyyy", new Locale("th", "TH")));
                    map.put("workunit",null!=rpt_sysPayment.getWorkunitId()?rpt_sysPayment.getWorkunitId().getWorkunitName():"-");
                    map.put("remark",StringUtils.isNotBlank(rpt_sysPayment.getRemark())?rpt_sysPayment.getRemark():"...........................................................................................");

                    map.put("reportCode", "B203");
                    JasperPrint print= report.exportSubReport_Template_mearge("template.jpg","b203", new String[]{"B203Report","B203SubReport"}, "Payment", map, reportList_);
                    jasperPrintList.add(print);
                   
//                    //add print form
//                    SysPrintBilling sysPrintBilling =new SysPrintBilling();
//                    sysPrintBilling.setBillingId(sysBilling);
//                    sysPrintBilling.setUserId(userInfo.getAdminUser());
//                    sysPrintBilling.setCreatedBy(userInfo.getAdminUser().getUsername());
//                    sysPrintBilling.setCreatedDt(DateTimeUtil.getSystemDate());
//                    billingFacade.createSysPrintBilling(sysPrintBilling);

             }
             if(!printSelected.isEmpty()){
                String pdfCode="Payment";
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

    public SysPaymentDetail getPyDetail_selected() {
        return pyDetail_selected;
    }

    public void setPyDetail_selected(SysPaymentDetail pyDetail_selected) {
        this.pyDetail_selected = pyDetail_selected;
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

    public SysCustomer getCust_selected() {
        return cust_selected;
    }

    public void setCust_selected(SysCustomer cust_selected) {
        this.cust_selected = cust_selected;
    }

    

    public SysWorkunit getWorkunit_selected() {
        return workunit_selected;
    }

    public void setWorkunit_selected(SysWorkunit workunit_selected) {
        this.workunit_selected = workunit_selected;
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

    public SysWorkunit getWorkunit_find() {
        return workunit_find;
    }

    public void setWorkunit_find(SysWorkunit workunit_find) {
        this.workunit_find = workunit_find;
    }


   
}