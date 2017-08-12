package com.finework.jsf.controller.billing;


import com.finework.core.ejb.entity.SysBilling;
import com.finework.core.ejb.entity.SysBillingDetail;
import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysDetail;
import com.finework.core.ejb.entity.SysHousePlan;
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
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Adisorn Jomjanyong
 */

@Named(B111Controller.CONTROLLER_NAME)
@SessionScoped
public class B111Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(B111Controller.class);
    public static final String CONTROLLER_NAME = "b111Controller";

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
    private List<SysBilling> printSelected;
    
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
            search();
    }
    
    public void next(String path) {
        NaviController nav = (NaviController) JsfUtil.getManagedBeanValue(NaviController.CONTROLLER_NAME);
        nav.next(path);
    }
    
    public void prepareCreate(String page) {
        selected = new SysBilling();
        dvDetail_selected=new SysBillingDetail();
        SysHousePlan sysHousePlan = new SysHousePlan();
//        runningNoCustomer();
        next(page);
    }
    public void prepareEdit() {
        dvDetail_selected=new SysBillingDetail();
       // SysHousePlan sysHousePlan = new SysHousePlan();
    }
    
     public void prepareEdit(String page) {
        this.total_discount=null != selected.getBillDiscount()?selected.getBillDiscount():0;
        this.total = null !=selected.getBillTotal()?selected.getBillTotal():0;
        this.total_vat =null !=selected.getBillVat()?selected.getBillVat():0;
        this.total_net = null !=selected.getBillTotalPrice()?selected.getBillTotalPrice():0;
        this.realTotalPrice=selected.getRealTotalPrice();
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
    }
   
    @Override
    public void create() {
      try {
            
            if (null==cust_selected) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อลูกค้า"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

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
//            
//            if (StringUtils.isBlank(selected.getNursery())) {
//                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "แปลง"));
//                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
//                return;
//            }
            
            //insert Billinge
            selected.setBillingType(Constants.BILLING_B111);
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
            
//            if (StringUtils.isBlank(selected.getNursery())) {
//                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "แปลง"));
//                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
//                return;
//            }
            
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
            next("billing/b111/index");
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
        if (null != selected.getSysBillingDetailList() && selected.getSysBillingDetailList().size()>0) {
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
                bean.setBillNo(null!=to.getBillNo()?to.getBillNo():"");
                bean.setDetailCode(null!=to.getDetailCode()?to.getDetailCode():"");
                bean.setDetail(to.getDetail());
                bean.setHouseNo(null!=to.getHouseNo()?to.getHouseNo():"");
                bean.setConvertno(null!=to.getBillConvert()?to.getBillConvert():"");
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
            map.put("org_branch",org.getOrgBranch());
            map.put("org_taxid",org.getOrgTax());
            map.put("org_bank",org.getOrgBank());
            map.put("org_bank_name",org.getOrgBankName());
            map.put("org_recall",org.getOrgRecall());
            
            map.put("delivery_no",null!=rpt_sysDelivery.getDocumentno()?rpt_sysDelivery.getDocumentno():"-");
            map.put("cust_name",rpt_sysDelivery.getCustomerId().getCustomerNameTh());
            map.put("cust_address",rpt_sysDelivery.getCustomerId().getCustomerAddress());
            map.put("send_date",DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getSendDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            map.put("workunit",rpt_sysDelivery.getWorkunitId().getWorkunitName());
            map.put("branch",null!=rpt_sysDelivery.getCustomerId().getBranch()?rpt_sysDelivery.getCustomerId().getBranch():"-");
            map.put("taxid",null!=rpt_sysDelivery.getCustomerId().getTaxId()?rpt_sysDelivery.getCustomerId().getTaxId():"-");
            map.put("remark",StringUtils.isNotBlank(rpt_sysDelivery.getRemark())?rpt_sysDelivery.getRemark():"................................................................................................");
            
        /*    map.put("price",NumberUtils.numberFormat(rpt_sysDelivery.getBillTotal(),"#,##0.00"));
            map.put("price_discount",NumberUtils.numberFormat(rpt_sysDelivery.getBillDiscount(),"#,##0.00"));
            map.put("price_total_discount",NumberUtils.numberFormat((null==rpt_sysDelivery.getBillTotal()?0.00:rpt_sysDelivery.getBillTotal())-(null==rpt_sysDelivery.getBillDiscount()?0.00:rpt_sysDelivery.getBillDiscount()),"#,##0.00"));
            map.put("price_vat",NumberUtils.numberFormat(rpt_sysDelivery.getBillVat(),"#,##0.00"));
            map.put("price_total",NumberUtils.numberFormat(rpt_sysDelivery.getBillTotalPrice(),"#,##0.00"));
            map.put("price_char",(rpt_sysDelivery.getBillTotalPrice()==0.0?"":new ThaiBaht().getText(rpt_sysDelivery.getBillTotalPrice())));
         */
           // map.put("bill_date",DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getBillDateLast(), "dd/MM/yyyy", new Locale("th", "TH")));
            
            map.put("reportCode", "B111");
            report.exportSubReport("b111", new String[]{"B111Report","B111SubReport"}, "B111", map, reportList_);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
public void printPdfMuti(){
         try {
             List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
             Collections.sort(printSelected, new SysBilling());
             for (SysBilling sysBilling : printSelected) {
                    ReportUtil report = new ReportUtil();
                    List reportList_ = new ArrayList<>();
                    List<BillingReportBean> reportList_main = new ArrayList<>();
                    List<BillingReportBean> reportList = new ArrayList<>();
                    SysBilling rpt_sysbilling=billingFacade.findByPK(sysBilling.getBillingId());

                    int intRunningNo=1;
                    List<SysBillingDetail> list = rpt_sysbilling.getSysBillingDetailList();
                    for (SysBillingDetail to : list) {
                        BillingReportBean bean = new BillingReportBean();
                        bean.setSeq(String.valueOf(intRunningNo++));
                        bean.setBillNo(null!=to.getBillNo()?to.getBillNo():"");
                        bean.setDetailCode(null!=to.getDetailCode()?to.getDetailCode():"");
                        bean.setDetail(to.getDetail());
                        bean.setHouseNo(null!=to.getHouseNo()?to.getHouseNo():"");
                        bean.setConvertno(null!=to.getBillConvert()?to.getBillConvert():"");
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
                    map.put("org_branch",org.getOrgBranch());
                    map.put("org_taxid",org.getOrgTax());
                    map.put("org_bank",org.getOrgBank());
                    map.put("org_bank_name",org.getOrgBankName());
                    map.put("org_recall",org.getOrgRecall());

                    map.put("delivery_no",null!=rpt_sysbilling.getDocumentno()?rpt_sysbilling.getDocumentno():"-");
                    map.put("cust_name",rpt_sysbilling.getCustomerId().getCustomerNameTh());
                    map.put("cust_address",rpt_sysbilling.getCustomerId().getCustomerAddress());
                    map.put("send_date",DateTimeUtil.cvtDateForShow(rpt_sysbilling.getSendDate(), "dd/MM/yyyy", new Locale("th", "TH")));
                    map.put("workunit",rpt_sysbilling.getWorkunitId().getWorkunitName());
                    map.put("branch",null!=rpt_sysbilling.getCustomerId().getBranch()?rpt_sysbilling.getCustomerId().getBranch():"-");
                    map.put("taxid",null!=rpt_sysbilling.getCustomerId().getTaxId()?rpt_sysbilling.getCustomerId().getTaxId():"-");
                    map.put("remark",StringUtils.isNotBlank(rpt_sysbilling.getRemark())?rpt_sysbilling.getRemark():"................................................................................................");

               /*     map.put("price",NumberUtils.numberFormat(rpt_sysbilling.getBillTotal(),"#,##0.00"));
                    map.put("price_discount",NumberUtils.numberFormat(rpt_sysbilling.getBillDiscount(),"#,##0.00"));
                    map.put("price_total_discount",NumberUtils.numberFormat((null==rpt_sysbilling.getBillTotal()?0.00:rpt_sysbilling.getBillTotal())-(null==rpt_sysbilling.getBillDiscount()?0.00:rpt_sysbilling.getBillDiscount()),"#,##0.00"));
                    map.put("price_vat",NumberUtils.numberFormat(rpt_sysbilling.getBillVat(),"#,##0.00"));
                    map.put("price_total",NumberUtils.numberFormat(rpt_sysbilling.getBillTotalPrice(),"#,##0.00"));
                    map.put("price_char",(rpt_sysbilling.getBillTotalPrice()==0.0?"":new ThaiBaht().getText(rpt_sysbilling.getBillTotalPrice())));
                */
                   // map.put("bill_date",DateTimeUtil.cvtDateForShow(rpt_sysbilling.getBillDateLast(), "dd/MM/yyyy", new Locale("th", "TH")));

                    map.put("reportCode", "B111");
                    JasperPrint print= report.exportSubReport_Template_mearge("template.jpg","b111", new String[]{"B111Report","B111SubReport"}, "B111", map, reportList_);
                    jasperPrintList.add(print);
                  
             }
             if(!printSelected.isEmpty()){
                String pdfCode="B111";
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
                
             items=billingFacade.findSysBillingListByCriteria(Constants.BILLING_B111,documentno,companyName,startDate,toDate);
//             lazyBillingModel = new LazyBillingDataModel(billingFacade,Constants.BILLING_B111,documentno,StringUtils.trimToEmpty(companyName),startDate,toDate);
//             DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("listForm:billingTable");
//             dataTable.setFirst(0);
             printSelected =new ArrayList<SysBilling>();
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
                    ||dvDetail_selected.getUnit()==null
                  //  ||dvDetail_selected.getPrice()==null
                    ||dvDetail_selected.getVolume()==null) {
                return;
            }
//            if (dvDetail_selected.getPrice()<=0 ) {
//               return;
//            }
            
            Double price=0.0;
            if(dvDetail_selected.getPrice()!=null){
                price=dvDetail_selected.getPrice();
            }
            if (dvDetail_selected.getVolume()<=0 ) {
              return;
            }

            if (selected.getSysBillingDetailList() == null) 
                 selected.setSysBillingDetailList(new ArrayList<SysBillingDetail>());
             
             dvDetail_selected.setId(detail_selected.getDetailId());
             dvDetail_selected.setDetail(detail_selected.getDetailDesc());
             dvDetail_selected.setTotalPrice(price * dvDetail_selected.getVolume().doubleValue());
             //is match
             List<String> list=new ArrayList();
             for(SysBillingDetail sysBillingDetail:selected.getSysBillingDetailList()){
                 list.add(sysBillingDetail.getDetail());
             }
             if (!list.contains(detail_selected.getDetailDesc())) {
                 selected.getSysBillingDetailList().add(dvDetail_selected);
             }
             //checkTotalPrice();  
             clearData_sysDetail();
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
             clearData_sysDetail();
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
 
    public void clearData_sysDetail(){
        dvDetail_selected =new SysBillingDetail();
        detail_selected=new SysDetail();
    }
   //===== end  Dialog=========   
    
    public void action(AjaxBehaviorEvent event){
        if(null!=selected.getSysBillingDetailList() && selected.getSysBillingDetailList().size()>0){
         selected.getSysBillingDetailList().clear();
         checkTotalPrice();  
         clearData_sysDetail();
        }
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
        String sequence_no=sequence.updateRunningNO(1,Constants.SEQUNCE_NO_B105_B111,"yyMM");
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
    
      public void checkRealTotalPrice(){
       // if(this.realTotalPrice>0.00){
            Double billreal=this.total_net-this.realTotalPrice;
            this.total_vat=this.total_vat-billreal;
            this.total_net = this.total_net - billreal ;
       // }
    }
    
  //Auto complete housePlan
   public List<SysHousePlan> completeHousePlan(String query) {
         List<SysHousePlan> filteredHousePlan= new ArrayList<>();
       try {
            List<SysHousePlan> allHousePlan = stockFacade.findSysHousePlanList();
            for (SysHousePlan housePlan:allHousePlan) {
               if(housePlan.getDetailDesc()!=null && housePlan.getDetailDesc().length()>0){
                if(housePlan.getDetailDesc().toLowerCase().startsWith(query)) {
                    filteredHousePlan.add(housePlan);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredHousePlan;
    }

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
     public List<SysBilling> getPrintSelected() {
        return printSelected;
    }

    public void setPrintSelected(List<SysBilling> printSelected) {
        this.printSelected = printSelected;
    }
    
    public Double getRealTotalPrice() {
        return realTotalPrice;
    }

    public void setRealTotalPrice(Double realTotalPrice) {
        this.realTotalPrice = realTotalPrice;
    }

}