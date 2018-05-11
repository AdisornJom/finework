package com.finework.jsf.controller.wht;

import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysOrganization;
import com.finework.core.ejb.entity.SysWht;
import com.finework.core.ejb.entity.SysWhtDetail;
import com.finework.core.util.Constants;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.NumberUtils;
import com.finework.core.util.ReportUtil;
import com.finework.core.util.ThaiBaht;
import com.finework.ejb.facade.CustomerFacade;
import com.finework.ejb.facade.OrganizationFacade;
import com.finework.ejb.facade.WhtFacade;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.SequenceController;
import com.finework.jsf.common.UserInfoController;
import com.finework.jsf.controller.billing.BillingReportBean;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Adisorn Jomjanyong
 */

@Named(W101Controller.CONTROLLER_NAME)
@SessionScoped
public class W101Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(W101Controller.class);
    public static final String CONTROLLER_NAME = "w101Controller";
    
    @Inject
    private WhtFacade whtFacade;
    @Inject
    private CustomerFacade customerFacade;
    @Inject
    private UserInfoController userInfo;
    @Inject
    private SequenceController sequence;
    @Inject
    private OrganizationFacade organizationFacade;
    
    //
    private List<SysWht> items;
    private SysWht selected;
    private List<SysWht> printSelected;
    
   // private LazyDataModel<SysWht> lazyWhtModel;
    
    //detial 
    private SysWhtDetail whtDetail_selected;
    
    
    //find criteria main
    private String documentNo;
    private SysCustomer cust_find;
    private Date startDate;
    private Date toDate;
    
    //variable
    private String total_th;
    private Double totalDetial=0.0;
    private Double totalVatDetail=0.0;
    private Double total=0.0;
    
    //auto complete
    private SysCustomer cust_selected;
   
    
   
    @PostConstruct
    @Override
    public void init() {
           //Init start date 1st
            Calendar c1 = Calendar.getInstance();
            c1.set(Calendar.DAY_OF_MONTH, 1);
            c1.set(Calendar.HOUR_OF_DAY, 0);
            c1.set(Calendar.MINUTE, 0);
            c1.set(Calendar.SECOND, 0);
            startDate = c1.getTime();
            //Init end date to current date
            Calendar c2 = Calendar.getInstance();
            c2.set(Calendar.HOUR_OF_DAY, 23);
            c2.set(Calendar.MINUTE, 59);
            c2.set(Calendar.SECOND, 59);
            toDate = c2.getTime();
            
           search();
    }
    
    public void next(String path) {
        NaviController nav = (NaviController) JsfUtil.getManagedBeanValue(NaviController.CONTROLLER_NAME);
        nav.next(path);
    }
    
    public void prepareCreate(String page) {
        selected = new SysWht();
        whtDetail_selected=new SysWhtDetail();
//        runningNoCustomer();
        next(page);
    }
    public void prepareEdit() {
        whtDetail_selected=new SysWhtDetail();
    }
    public void cancel(String path) {
        clearData();
        search();
        next(path);
    }
     
    public void clearData(){
         selected = new SysWht();
         cust_selected=new SysCustomer();
    }
    
    @Override
    public void create() {
      try {

            if (null==cust_selected) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อลูกค้า"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
             
            if (null==selected.getWhtDate()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่ออกหนังสือ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            if (null==selected.getSysWhtDetailList() || selected.getSysWhtDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            if (null==selected.getPaymentOutStatus()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ผู้จ่ายเงิน"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }else{
                if(Objects.equals(4, selected.getPaymentOutStatus())){
                    if (null==selected.getPaymentOutDesc() || selected.getPaymentOutDesc().length()==0) {
                        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ระบุ รายละเอียด"));
                        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                        return;
                    }
                }
            }
                
            //insert Billinge
            String pndtype="";
            if(Objects.equals(cust_selected.getCustomerType(),1)){
                pndtype="pnd53";
            }else{
                pndtype="pnd3";
            }
            selected.setBookNumber(DateTimeUtil.cvtDateForShow(DateTimeUtil.getSystemDate(), "MM", new Locale("th", "TH")));
            selected.setPndType(pndtype);
            selected.setCustomerId(cust_selected);
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            
            //insertDetail
            Double total_detail=0.0,totalVatDetail=0.0,total=0.0;
            List<SysWhtDetail> detal_add=new ArrayList();
            for(SysWhtDetail sysWhtDetail_:selected.getSysWhtDetailList()){
                sysWhtDetail_.setId(null);//auto generate id on db
                sysWhtDetail_.setWhtId(selected);
                total_detail=total_detail+sysWhtDetail_.getAmount();
                totalVatDetail=totalVatDetail+sysWhtDetail_.getAmountVat();
                total=total+sysWhtDetail_.getTotalAmountVat();
                detal_add.add(sysWhtDetail_);
            }
            selected.setSysWhtDetailList(detal_add);
            
            selected.setWhtTotal(total_detail);
            selected.setWhtVat(totalVatDetail);
            selected.setWhtVatTotal(total);
            
            runningNoCustomer();
                         
            whtFacade.createSysWht(selected);
         
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
            
            if (null==selected.getSysWhtDetailList() || selected.getSysWhtDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            //deleteDetail
             whtFacade.deleteWhtIdOnDetail(selected.getWhtId());
            
            //insertDetail
            Double total_detail=0.0,totalVatDetail=0.0,total=0.0;
            List<SysWhtDetail> detal_add=new ArrayList();
            for(SysWhtDetail sysWhtDetail_:selected.getSysWhtDetailList()){
                sysWhtDetail_.setId(null);//auto generate id on db
                sysWhtDetail_.setWhtId(selected);
                total_detail=total_detail+sysWhtDetail_.getAmount();
                totalVatDetail=totalVatDetail+sysWhtDetail_.getAmountVat();
                total=total+sysWhtDetail_.getTotalAmountVat();
                detal_add.add(sysWhtDetail_);
            }
            selected.setWhtTotal(total_detail);
            selected.setWhtVat(totalVatDetail);
            selected.setWhtVatTotal(total);
            
            //update Billing
            selected.setSysWhtDetailList(detal_add);
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());   

            whtFacade.editSysWht(selected);
         
            clearData();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("wht/w101/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    @Override
    public void delete() {
        try {
            whtFacade.deleteSysWht(selected);
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4002"));
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
    
    @Override
    public void reportPDF() {
        try {
            ReportUtil report = new ReportUtil();
            HashMap map = new HashMap();
            SysOrganization org= organizationFacade.findSysOrganizationByStatus("Y");
            SysWht rpt_sysWht=whtFacade.findByPK(selected.getWhtId());
            
            FacesContext context= FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
            map.put("BASE_WEB",servletContext.getRealPath(""));
            
            map.put("wht_month_date",null!=rpt_sysWht.getBookNumber()?rpt_sysWht.getBookNumber():"");
            map.put("document_no",null!=rpt_sysWht.getDocumentno()?rpt_sysWht.getDocumentno():"");
            
            map.put("org_name",org.getOrgNameTh());
            map.put("orginfo_taxid",org.getOrgTax().replaceAll("\\-", ""));
            map.put("org_addr",org.getOrgAddressTh());
           
            map.put("partner_name",rpt_sysWht.getCustomerId().getCustomerNameTh());
            map.put("partner_taxid",rpt_sysWht.getCustomerId().getTaxId().replaceAll("\\-", ""));
            map.put("partner_addr",rpt_sysWht.getCustomerId().getCustomerAddress());
            map.put("partner_type",rpt_sysWht.getPndType());
            
            for (SysWhtDetail sysDetail : rpt_sysWht.getSysWhtDetailList()) {
                if (Objects.equals(sysDetail.getMoneyType(), 1)) {
                    map.put("V5_1_1", DateTimeUtil.cvtDateForShow(sysDetail.getDateType(), "dd/MM/yy", new Locale("th", "TH")));
                    map.put("V5_1_2", NumberUtils.numberFormat(sysDetail.getAmount(), "#,##0.00"));
                    map.put("V5_1_3", NumberUtils.numberFormat(sysDetail.getAmountVat(), "#,##0.00"));
                } else if (Objects.equals(sysDetail.getMoneyType(), 2)) {
                    map.put("V5_2_1", DateTimeUtil.cvtDateForShow(sysDetail.getDateType(), "dd/MM/yy", new Locale("th", "TH")));
                    map.put("V5_2_2",NumberUtils.numberFormat(sysDetail.getAmount(), "#,##0.00"));
                    map.put("V5_2_3",NumberUtils.numberFormat(sysDetail.getAmountVat(), "#,##0.00"));
                } else if (Objects.equals(sysDetail.getMoneyType(), 3)) {
                    map.put("V5_3_1", DateTimeUtil.cvtDateForShow(sysDetail.getDateType(), "dd/MM/yy", new Locale("th", "TH")));
                    map.put("V5_3_2",NumberUtils.numberFormat(sysDetail.getAmount(), "#,##0.00"));
                    map.put("V5_3_3",NumberUtils.numberFormat(sysDetail.getAmountVat(), "#,##0.00"));
                } else if (Objects.equals(sysDetail.getMoneyType(), 4)) {
                    map.put("V5_4_1", DateTimeUtil.cvtDateForShow(sysDetail.getDateType(), "dd/MM/yy", new Locale("th", "TH")));
                    map.put("V5_4_2",NumberUtils.numberFormat(sysDetail.getAmount(), "#,##0.00"));
                    map.put("V5_4_3",NumberUtils.numberFormat(sysDetail.getAmount(), "#,##0.00"));
                } else if (Objects.equals(sysDetail.getMoneyType(), 5)) {
                    map.put("V5_5_1", DateTimeUtil.cvtDateForShow(sysDetail.getDateType(), "dd/MM/yy", new Locale("th", "TH")));
                    map.put("V5_5_2",NumberUtils.numberFormat(sysDetail.getAmount(), "#,##0.00"));
                    map.put("V5_5_3",NumberUtils.numberFormat(sysDetail.getAmountVat(), "#,##0.00"));
                } else if (Objects.equals(sysDetail.getMoneyType(), 6)) {
                    map.put("V5_6_1", DateTimeUtil.cvtDateForShow(sysDetail.getDateType(), "dd/MM/yy", new Locale("th", "TH")));
                    map.put("V5_6_2",NumberUtils.numberFormat(sysDetail.getAmount(), "#,##0.00"));
                    map.put("V5_6_3",NumberUtils.numberFormat(sysDetail.getAmountVat(), "#,##0.00"));
                } else if (Objects.equals(sysDetail.getMoneyType(), 7)) {
                    map.put("V5_7_1", DateTimeUtil.cvtDateForShow(sysDetail.getDateType(), "dd/MM/yy", new Locale("th", "TH")));
                    map.put("other",null!=sysDetail.getMeneyDesc()?sysDetail.getMeneyDesc():"");
                    map.put("V5_7_2",NumberUtils.numberFormat(sysDetail.getAmount(), "#,##0.00"));
                    map.put("V5_7_3",NumberUtils.numberFormat(sysDetail.getAmountVat(), "#,##0.00"));
                }
            }
            map.put("total1",NumberUtils.numberFormat(rpt_sysWht.getWhtTotal(),"#,##0.00"));
            map.put("total2",NumberUtils.numberFormat(rpt_sysWht.getWhtVat(),"#,##0.00"));
            map.put("txttotal",convertPriceToString(rpt_sysWht.getWhtVatTotal()));
            map.put("payment_out_status",String.valueOf(rpt_sysWht.getPaymentOutStatus()));
            map.put("payment_other",null!=rpt_sysWht.getPaymentOutDesc()?rpt_sysWht.getPaymentOutDesc():"");
            map.put("date_dd",DateTimeUtil.cvtDateForShow(rpt_sysWht.getWhtDate(), "dd", new Locale("th", "TH")));
            map.put("date_mm",DateTimeUtil.cvtDateForShow(rpt_sysWht.getWhtDate(), "MM", new Locale("th", "TH")));
            map.put("date_yyyy",DateTimeUtil.cvtDateForShow(rpt_sysWht.getWhtDate(), "yyyy", new Locale("th", "TH")));

            List reportList_ = new ArrayList<>();
            List<BillingReportBean> reportList_main = new ArrayList<>();
            reportList_main.add(new BillingReportBean("", "", "", "", "", "", "", "","","","",""));
            reportList_.add(reportList_main);
            
            map.put("reportCode", "W101");
            report.exportWHT("W101","50TV", "WHT", map,reportList_);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    public void printPdfMuti(){

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

              items=whtFacade.findSysWhtListByCriteriat(documentNo,cust_find,startDate,toDate);
          /*  lazyWhtModel = new LazyBillingDataModel(whtFacade,Constants.BILLING_NOVAT,null,StringUtils.trimToEmpty(customer),startDate,toDate);
            DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("listForm:billingTable");
            dataTable.setFirst(0);*/
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
              if (whtDetail_selected.getDateType()==null
                    ||whtDetail_selected.getAmount()==null) {
                return;
            }
            if (whtDetail_selected.getAmount()<=0 ) {
               return;
            }
            if (whtDetail_selected.getAmount()<=0 ) {
              return;
            }

            if (selected.getSysWhtDetailList() == null) 
                 selected.setSysWhtDetailList(new ArrayList<>());
            
             whtDetail_selected.setAmountVat(whtDetail_selected.getAmount()*0.03);
             whtDetail_selected.setTotalAmountVat(whtDetail_selected.getAmount()-whtDetail_selected.getAmountVat());
             //is match
             List<Integer> list=new ArrayList();
             for(SysWhtDetail sysWhtDetail:selected.getSysWhtDetailList()){
                 list.add(sysWhtDetail.getMoneyType());
             }
             if (!list.contains(whtDetail_selected.getMoneyType())) {
                 selected.getSysWhtDetailList().add(whtDetail_selected);
             }
                
             clearData_sysWhtDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    public void deleteDetail(){
        try {
             //delete total 
             selected.getSysWhtDetailList().remove(whtDetail_selected);
             clearData_sysWhtDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    
    public Double checkTotalVat(List<SysWhtDetail> sysBLDetail){
        Double totalDetail_ = 0.0;
        if(null !=sysBLDetail && sysBLDetail.size()>0){
            for (SysWhtDetail sysdetail : sysBLDetail) {
                totalDetail_ = totalDetail_ + sysdetail.getAmount();
            }
            this.totalDetial=totalDetail_;
            this.totalVatDetail=(totalDetail_==0.0)?0.0:(totalDetail_*0.03);
            this.total=this.totalDetial-this.totalVatDetail;
        }else{
            this.totalDetial=0.0;
            this.totalVatDetail=(totalDetail_==0.0)?0.0:(totalDetail_*0.03);
            this.total=0.0;
        }
        
        return totalDetial;
    }
    
    public String convertPriceToString(Double totalprice){
        if(totalprice==0.0){
            return "";
        }else{
            return new ThaiBaht().getText(totalprice);
        }
    }
 
    public void clearData_sysWhtDetail(){
        whtDetail_selected =new SysWhtDetail();
    }
   //===== end  Dialog=========   
    
    
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
//        String sequence_no=sequence.runningNO(this.cust_selected.getCustomerId(),Constants.SEQUNCE_NO_DELIVERY);
//        this.selected.setDocumentno(sequence_no);
        
        String sequence_no=sequence.updateRunningNO(1,Constants.SEQUNCE_WHT,"yyMM");
        this.selected.setDocumentno(sequence_no);
    }
 
 //End Auto Complete==========================================================================    

    public List<SysWht> getItems() {
        return items;
    }

    public void setItems(List<SysWht> items) {
        this.items = items;
    }

    public SysWht getSelected() {
        return selected;
    }

    public void setSelected(SysWht selected) {
        this.selected = selected;
    }

    public SysWhtDetail getBlDetail_selected() {
        return whtDetail_selected;
    }

    public void setBlDetail_selected(SysWhtDetail whtDetail_selected) {
        this.whtDetail_selected = whtDetail_selected;
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

    public SequenceController getSequence() {
        return sequence;
    }

    public void setSequence(SequenceController sequence) {
        this.sequence = sequence;
    }

    public SysWhtDetail getWhtDetail_selected() {
        return whtDetail_selected;
    }

    public void setWhtDetail_selected(SysWhtDetail whtDetail_selected) {
        this.whtDetail_selected = whtDetail_selected;
    }

    public SysCustomer getCust_find() {
        return cust_find;
    }

    public void setCust_find(SysCustomer cust_find) {
        this.cust_find = cust_find;
    }

    public Double getTotalDetial() {
        return totalDetial;
    }

    public void setTotalDetial(Double totalDetial) {
        this.totalDetial = totalDetial;
    }

    public Double getTotalVatDetail() {
        return totalVatDetail;
    }

    public void setTotalVatDetail(Double totalVatDetail) {
        this.totalVatDetail = totalVatDetail;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public List<SysWht> getPrintSelected() {
        return printSelected;
    }

    public void setPrintSelected(List<SysWht> printSelected) {
        this.printSelected = printSelected;
    }
}