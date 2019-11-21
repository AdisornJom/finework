package com.finework.jsf.controller.billing;


import com.finework.core.ejb.entity.SysBilling;
import com.finework.core.ejb.entity.SysBillingDetail;
import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysDetail;
import com.finework.core.ejb.entity.SysOrganization;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.core.util.Constants;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.NumberUtils;
import com.finework.core.util.ReportUtil;
import com.finework.core.util.ThaiBaht;
import com.finework.ejb.facade.BillingFacade;
import com.finework.ejb.facade.CustomerFacade;
import com.finework.ejb.facade.OrganizationFacade;
import com.finework.ejb.facade.StockFacade;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.SequenceController;
import com.finework.jsf.common.UserInfoController;
import com.finework.jsf.model.LazyBillingDataModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Adisorn Jomjanyong
 */

@Named(B104Controller.CONTROLLER_NAME)
@SessionScoped
public class B104Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(B104Controller.class);
    public static final String CONTROLLER_NAME = "b104Controller";

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
    @Inject
    private OrganizationFacade organizationFacade;
    
    private LazyDataModel<SysBilling> lazyBillingModel;
    //
    private List<SysBilling> items;
    private SysBilling selected;
    
    //detial 
    private SysBillingDetail dvDetail_selected;
    
    
    //find criteria main
    private String documentno;
    private String companyName;
    private Date startDate;
    private Date toDate;
   
     //variable
    private Double total=0.0;
    private Double total_discount=0.0;
    private Double total_vat=0.0;
    private Double total_net=0.0;
    private Double realTotalPrice;
    private String total_th;
    
    //auto complete
    private SysCustomer cust_selected;
    private SysWorkunit workunit_selected;
    private SysDetail detail_selected;
   
    
   
    @PostConstruct
    @Override
    public void init() {
        //criteria
//        Calendar cal = new GregorianCalendar(Locale.US);
//        cal.setTime(DateTimeUtil.getSystemDate());
//        cal.add(Calendar.DAY_OF_MONTH, -7);
//        startDate = cal.getTime();
//        toDate = DateTimeUtil.getSystemDate();
        
//        try {
//             //items=deliveryFacade.findSysBillingList();
//             items=billingFacade.findSysBillingList(Constants.BILLING_DELIVERY);
//        } catch (Exception ex) {
//            LOG.error(ex);
//        }
        search();
    }
    
    public void next(String path) {
        NaviController nav = (NaviController) JsfUtil.getManagedBeanValue(NaviController.CONTROLLER_NAME);
        nav.next(path);
    }
    
    public void prepareCreate(String page) {
        selected = new SysBilling();
        dvDetail_selected=new SysBillingDetail();
//        runningNoCustomer();
        next(page);
    }
    public void prepareEdit() {
        dvDetail_selected=new SysBillingDetail();
    }
    
     public void prepareEdit(String page) {
        this.total_discount=null != selected.getBillDiscount()?selected.getBillDiscount():0;
        this.total = null !=selected.getBillTotal()?selected.getBillTotal():0;
        this.total_vat =null !=selected.getBillVat()?selected.getBillVat():0;
        this.total_net = null !=selected.getBillTotalPrice()?selected.getBillTotalPrice():0;
        this.realTotalPrice=null !=selected.getRealTotalPrice()?selected.getRealTotalPrice():0;
        next(page);
    }
     
    public void cancel(String path) {
        clearData();
        clearDatatTotal();
        search();
        next(path);
    }
     
    public void clearData(){
         selected = new SysBilling();
         cust_selected=new SysCustomer();
         workunit_selected=new SysWorkunit();
        // blDetail_list=new ArrayList<>();
    }
   
    @Override
    public void create() {
      try {
            
            if (null==cust_selected) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อลูกค้า"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
//             if (null==selected.getDocumentno() || StringUtils.isBlank(selected.getDocumentno())) {
//                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "เลขที่"));
//                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
//                return;
//            }
            if (null==selected.getSendDate()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่ทำรายการ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            if (null==workunit_selected) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "หน่วยงาน"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (null== selected.getSysBillingDetailList() || selected.getSysBillingDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            //insert Billinge
            selected.setBillingType(Constants.BILLING_DELIVERY);
            selected.setCustomerId(cust_selected);
            selected.setWorkunitId(workunit_selected);
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            
            //insertDetail
            Double total_detail=0.0;
            List<SysBillingDetail> detal_add=new ArrayList();
            for(SysBillingDetail sysBillDetail_:selected.getSysBillingDetailList()){
                sysBillDetail_.setId(null);//auto generate id on db
                sysBillDetail_.setBillingId(selected);
                total_detail=total_detail+sysBillDetail_.getTotalPrice();
                detal_add.add(sysBillDetail_);
            }
            selected.setSysBillingDetailList(detal_add);
            selected.setBillDiscount(this.total_discount);
            selected.setBillTotal(this.total);
            selected.setBillVat(this.total_vat);
            selected.setBillTotalPrice(this.total_net);
            selected.setRealTotalPrice(this.realTotalPrice);
            
//            //update running no.
//             String sequence_no=sequence.updateRunningNO(null,Constants.SEQUNCE_NO_DELIVERY);
//             selected.setDocumentno(sequence_no);
             runningNoCustomer();
             
             billingFacade.createSysBilling(selected);
         
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
            
            if (null== selected.getSysBillingDetailList() || selected.getSysBillingDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            //deleteDetail
             billingFacade.deleteBillingIdOnDetail(selected.getBillingId());
            
            //insertDetail
            Double total_detail=0.0;
            List<SysBillingDetail> detal_edit=new ArrayList();
            for(SysBillingDetail sysBillDetail_:selected.getSysBillingDetailList()){
                sysBillDetail_.setId(null);//auto generate id on db
                sysBillDetail_.setBillingId(selected);
                total_detail=total_detail+sysBillDetail_.getTotalPrice();
                detal_edit.add(sysBillDetail_);
            }
            
            //update Billing
            selected.setSysBillingDetailList(detal_edit);
            selected.setBillDiscount(this.total_discount);
            selected.setBillTotal(this.total);
            selected.setBillVat(this.total_vat);
            selected.setBillTotalPrice(this.total_net);
            selected.setRealTotalPrice(this.realTotalPrice);
            
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());   
            billingFacade.editSysBilling(selected);
         
            clearData();
            clearDatatTotal();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("billing/b104/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    @Override
    public void delete() {
        try {
            billingFacade.deleteSysBilling(selected);
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4002"));
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
     public void checkTotalPrice(){
        Double total_ = 0.0;
        if (null != selected.getSysBillingDetailList() && selected.getSysBillingDetailList().size() > 0) {
            for (SysBillingDetail sysdetail : selected.getSysBillingDetailList()) {
                total_ = total_ + sysdetail.getTotalPrice();
            }
             
             this.total = total_;
             Double totaldiscount=this.total-this.total_discount;
             this.total_vat =totaldiscount * 0.07;
             this.total_net = totaldiscount + this.total_vat ;
             this.realTotalPrice=this.total_net;
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
     public void clearDatatTotal(){
        this.total_discount=0.0;
        this.total_vat=0.0;
        this.total_net=0.0;
        this.total = 0.00;
        this.realTotalPrice=0.00;
    }

    @Override
    public void reportPDF() {
        try {
            ReportUtil report = new ReportUtil();
            List reportList_ = new ArrayList<>();
            List<BillingReportBean> reportList_main = new ArrayList<>();
            List<BillingReportBean> reportList = new ArrayList<>();
            SysBilling rpt_sysDelivery=billingFacade.findByPK(selected.getBillingId());
            
            int intRunningNo=1;
            List<SysBillingDetail> list = rpt_sysDelivery.getSysBillingDetailList();
            for (SysBillingDetail to : list) {
                BillingReportBean bean = new BillingReportBean();
                bean.setSeq(String.valueOf(intRunningNo++));
                bean.setBillNo(to.getBillNo());
                bean.setDetail(to.getDetail()+(StringUtils.isNotBlank(to.getBillConvert())?"|แปลง:"+to.getBillConvert():""));
                //bean.setConvertno(to.getBillConvert());
                bean.setVolumn(NumberUtils.numberFormat(to.getVolume(),"#,##0.00"));
                bean.setUnit(to.getUnit());
                bean.setPriceUnit(NumberUtils.numberFormat(to.getPrice(),"#,##0.00"));
                bean.setPriceTotal(NumberUtils.numberFormat(to.getTotalPrice(),"#,##0.00"));
                
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
            map.put("org_tel",org.getOrgTel());
            map.put("org_bank",org.getOrgBank());
            map.put("org_bank_name",org.getOrgBankName());
            map.put("org_recall",org.getOrgRecall());
            
            map.put("delivery_no",null!=rpt_sysDelivery.getDocumentno()?rpt_sysDelivery.getDocumentno():"-");
            map.put("cust_name",rpt_sysDelivery.getCustomerId().getCustomerNameTh());
            map.put("cust_address",rpt_sysDelivery.getCustomerId().getCustomerAddress());
            map.put("send_date",DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getSendDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            map.put("workunit",rpt_sysDelivery.getWorkunitId().getWorkunitName());
            map.put("price",NumberUtils.numberFormat(rpt_sysDelivery.getBillTotal(),"#,##0.00"));
            map.put("price_discount",NumberUtils.numberFormat(rpt_sysDelivery.getBillDiscount(),"#,##0.00"));
            map.put("price_total_discount",NumberUtils.numberFormat((null==rpt_sysDelivery.getBillTotal()?0.00:rpt_sysDelivery.getBillTotal())-(null==rpt_sysDelivery.getBillDiscount()?0.00:rpt_sysDelivery.getBillDiscount()),"#,##0.00"));
            map.put("price_vat",NumberUtils.numberFormat(rpt_sysDelivery.getBillVat(),"#,##0.00"));
            map.put("price_total",NumberUtils.numberFormat(rpt_sysDelivery.getBillTotalPrice(),"#,##0.00"));
            map.put("price_char",(rpt_sysDelivery.getBillTotalPrice()==0.0?"":new ThaiBaht().getText(rpt_sysDelivery.getBillTotalPrice())));
           // map.put("bill_date",DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getBillDateLast(), "dd/MM/yyyy", new Locale("th", "TH")));
            
            map.put("reportCode", "B104");
            //report.exportSubReport("b104", new String[]{"B104Report","B104SubReport"}, "Delivery", map, reportList_);
            report.exportSubReport_Heading("delivery_bill.png","b104", new String[]{"B104Report","B104SubReport"}, "Delivery", map, reportList_); 
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    
    public void search() {
        try {
               if (null != startDate) {
                    //Init start date
                    Calendar c1 = Calendar.getInstance();
                    c1.setTime(startDate);
                    c1.set(Calendar.HOUR_OF_DAY, 0);
                    c1.set(Calendar.MINUTE, 0);
                    c1.set(Calendar.SECOND, 0);
                    startDate = c1.getTime();
                }
                if (null != toDate) {
                    //Init to date
                    Calendar c2 = Calendar.getInstance();
                    c2.setTime(toDate);
                    c2.set(Calendar.HOUR_OF_DAY, 23);
                    c2.set(Calendar.MINUTE, 59);
                    c2.set(Calendar.SECOND, 59);
                    toDate = c2.getTime();
                }
            // items=deliveryFacade.findSysBillingListByCriteria(companyName,startDate,toDate);
            // items=billingFacade.findSysBillingListByCriteria(Constants.BILLING_DELIVERY,documentno,companyName,startDate,toDate);
             lazyBillingModel = new LazyBillingDataModel(billingFacade,Constants.BILLING_DELIVERY,documentno,StringUtils.trimToEmpty(companyName),startDate,toDate);
             DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("listForm:billingTable");
             dataTable.setFirst(0);
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
    public void addDetail(){
         try {
             //validate field iteam 
              if (detail_selected==null
                    ||dvDetail_selected.getBillConvert()==null
                    ||dvDetail_selected.getUnit()==null
                    ||dvDetail_selected.getPrice()==null
                    ||dvDetail_selected.getVolume()==null) {
                return;
            }
            if (dvDetail_selected.getPrice()<=0 ) {
               return;
            }
            if (dvDetail_selected.getVolume()<=0 ) {
              return;
            }

            if (selected.getSysBillingDetailList() == null) 
                 selected.setSysBillingDetailList(new ArrayList<SysBillingDetail>());
             
             dvDetail_selected.setId(detail_selected.getDetailId());
             dvDetail_selected.setDetail(detail_selected.getDetailDesc());
             dvDetail_selected.setTotalPrice(dvDetail_selected.getPrice() * dvDetail_selected.getVolume().doubleValue());
             //is match
             List<String> list=new ArrayList();
             for(SysBillingDetail sysBillingDetail:selected.getSysBillingDetailList()){
                 list.add(sysBillingDetail.getDetail());
             }
             if (!list.contains(detail_selected.getDetailDesc())) {
                 selected.getSysBillingDetailList().add(dvDetail_selected);
             }
             checkTotalPrice();  
             clearData_sysDeliveryDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    public void deleteDetail(){
        try {
             //delete total 
             selected.getSysBillingDetailList().remove(dvDetail_selected);
             checkTotalPrice();  
             clearData_sysDeliveryDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    
    public Double checkTotalPrice(List<SysBillingDetail> sysBLDetail){
        Double total_ = 0.0;
        if(null !=sysBLDetail){
            for (SysBillingDetail sysdetail : sysBLDetail) {
                total_ = total_ + sysdetail.getTotalPrice();
            }
            this.total = total_;
        }else{
            this.total = 0.00;
        }
        
        return total_;
    }
    
    
    public String convertPriceToString(Double totalprice){
        if(totalprice==0.0){
            return "";
        }else{
            return new ThaiBaht().getText(totalprice);
        }
    }
 
    public void clearData_sysDeliveryDetail(){
        dvDetail_selected =new SysBillingDetail();
        detail_selected=new SysDetail();
    }
   //===== end  Dialog=========   
    
    
   public void handleKeyEvent(){}
   
   
   
 //Auto Complete==========================================================================  
   //Auto complete customer
   public List<SysCustomer> completeCustomer(String query) {
         List<SysCustomer> filteredCustomers = new ArrayList<>();
       try {
            SysCustomer sysCustomer_ =new SysCustomer();
            sysCustomer_.setCustomerId(null);
            sysCustomer_.setCustomerNameTh("-");
            filteredCustomers.add(sysCustomer_);
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
//        String sequence_no=sequence.runningNO(this.cust_selected.getCustomerId(),Constants.SEQUNCE_NO_DELIVERY);
//        this.selected.setDocumentno(sequence_no);
        
        String sequence_no=sequence.updateRunningNO(1,Constants.SEQUNCE_NO_DELIVERY,"yyMM");
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

    public List<SysBilling> getItems() {
        return items;
    }

    public void setItems(List<SysBilling> items) {
        this.items = items;
    }

    public SysBilling getSelected() {
        return selected;
    }

    public void setSelected(SysBilling selected) {
        this.selected = selected;
    }

    public SysBillingDetail getDvDetail_selected() {
        return dvDetail_selected;
    }

    public void setDvDetail_selected(SysBillingDetail dvDetail_selected) {
        this.dvDetail_selected = dvDetail_selected;
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

    public String getDocumentno() {
        return documentno;
    }

    public void setDocumentno(String documentno) {
        this.documentno = documentno;
    }

    public LazyDataModel<SysBilling> getLazyBillingModel() {
        return lazyBillingModel;
    }

    public Double getTotal_discount() {
        return total_discount;
    }

    public void setTotal_discount(Double total_discount) {
        this.total_discount = total_discount;
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

    public Double getRealTotalPrice() {
        return realTotalPrice;
    }

    public void setRealTotalPrice(Double realTotalPrice) {
        this.realTotalPrice = realTotalPrice;
    }

}