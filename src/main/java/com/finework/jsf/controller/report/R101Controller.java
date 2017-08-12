package com.finework.jsf.controller.report;


import com.finework.jsf.controller.billing.*;
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
import com.finework.ejb.facade.OrganizationFacade;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import com.finework.jsf.model.LazyBillingReportDataModel;
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
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Adisorn Jomjanyong
 */

@Named(R101Controller.CONTROLLER_NAME)
@SessionScoped
public class R101Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(R101Controller.class);
    public static final String CONTROLLER_NAME = "r101Controller";

    @Inject
    private BillingFacade billingFacade;
    @Inject
    private UserInfoController userInfo;
    @Inject
    private OrganizationFacade organizationFacade;
    //
    private List<SysBilling> items;
    private SysBilling selected;
    
    private LazyDataModel<SysBilling> lazyBillingReportModel;
    
    //detial 
    private SysBillingDetail dvDetail_selected;
    
    
    //find criteria main
    private String billType;
    private String documentno;
    private String companyName;
    private Date startDate;
    private Date toDate;
   
    
    //variable
    private Double total=0.0;
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
     
    public void clearData(){
         selected = new SysBilling();
    }
   
    @Override
    public void create() {
    }

    @Override
    public void edit() {  
    }

    @Override
    public void delete() {
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
                bean.setSendDate(null!=to.getSendDate()?DateTimeUtil.cvtDateForShow(to.getSendDate(), "dd/MM/yyyy", new Locale("th", "TH")):"");
                bean.setDetailCode(null!=to.getDetailCode()?to.getDetailCode():"");
                if(Constants.BILLING_B105.equals(selected.getBillingType())){
                  bean.setDetail(to.getDetail()+(StringUtils.isNotBlank(to.getBillConvert())?"|แปลง:"+to.getBillConvert():""));
                }else{
                  bean.setDetail(to.getDetail()); 
                }
                bean.setConvertno(null!=to.getBillConvert()?to.getBillConvert():"");
                bean.setHouseNo(null!=to.getHouseNo()?to.getHouseNo():"");
                bean.setVolumn(null!=to.getVolume()?NumberUtils.numberFormat(to.getVolume(),"#,##0.00"):"");
                bean.setUnit(null!=to.getUnit()?to.getUnit():"");
                bean.setPriceUnit(null!=to.getPrice()?NumberUtils.numberFormat(to.getPrice(),"#,##0.00"):"");
                bean.setPriceTotal(NumberUtils.numberFormat(to.getTotalPrice(),"#,##0.00"));
                
                reportList.add(bean);
            }
            
            if(Constants.BILLING_NOVAT.equals(selected.getBillingType())){
                printB101PDF(report, reportList_, reportList_main, reportList, rpt_sysDelivery);
            }else if(Constants.BILLING_CHECK.equals(selected.getBillingType())){
                printB102PDF(report, reportList_, reportList_main, reportList, rpt_sysDelivery);
            }else if(Constants.BILLING_ACC.equals(selected.getBillingType())){
                printB103PDF(report, reportList_, reportList_main, reportList, rpt_sysDelivery);
            }else if(Constants.BILLING_DELIVERY.equals(selected.getBillingType())){
                printB104PDF(report, reportList_, reportList_main, reportList, rpt_sysDelivery);
            }else if(Constants.BILLING_GOOD_RECEIPT.equals(selected.getBillingType())){
                printB201PDF(report, reportList_, reportList_main, reportList, rpt_sysDelivery);
            }else if(Constants.BILLING_SALES_INVOICE.equals(selected.getBillingType())){
                printB202PDF(report, reportList_, reportList_main, reportList, rpt_sysDelivery);
            }else if(Constants.BILLING_B105.equals(selected.getBillingType())){
                printB105PDF(report, reportList_, reportList_main, reportList, rpt_sysDelivery);
            }else if(Constants.BILLING_B106.equals(selected.getBillingType())){
                printB106PDF(report, reportList_, reportList_main, reportList, rpt_sysDelivery);
            }else if(Constants.BILLING_B107.equals(selected.getBillingType())){
                printB107PDF(report, reportList_, reportList_main, reportList, rpt_sysDelivery);
            }else if(Constants.BILLING_B108.equals(selected.getBillingType())){
                printB108PDF(report, reportList_, reportList_main, reportList, rpt_sysDelivery);
            }else if(Constants.BILLING_B109.equals(selected.getBillingType())){
                printB109PDF(report, reportList_, reportList_main, reportList, rpt_sysDelivery);
            }else if(Constants.BILLING_B110.equals(selected.getBillingType())){
                printB110PDF(report, reportList_, reportList_main, reportList, rpt_sysDelivery);
            }
            
            
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    public void printB101PDF(ReportUtil report,List reportList_,
            List<BillingReportBean> reportList_main,List<BillingReportBean> reportList, SysBilling rpt_sysbilling){
        try {            
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
            
            map.put("cust_name",rpt_sysbilling.getCustomerId().getCustomerNameTh());
            map.put("cust_address",rpt_sysbilling.getCustomerId().getCustomerAddress());
            map.put("send_date",DateTimeUtil.cvtDateForShow(rpt_sysbilling.getSendDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            map.put("workunit",rpt_sysbilling.getWorkunitId().getWorkunitName());
            map.put("price",NumberUtils.numberFormat(rpt_sysbilling.getBillTotal(),"#,##0.00"));
            map.put("price_vat",NumberUtils.numberFormat(rpt_sysbilling.getBillVat(),"#,##0.00"));
            map.put("price_total",NumberUtils.numberFormat(rpt_sysbilling.getBillTotalPrice(),"#,##0.00"));
            map.put("price_char",(rpt_sysbilling.getBillTotalPrice()==0.0?"":new ThaiBaht().getText(rpt_sysbilling.getBillTotalPrice())));
            map.put("bill_date",DateTimeUtil.cvtDateForShow(rpt_sysbilling.getBillDateLast(), "dd/MM/yyyy", new Locale("th", "TH")));
            
            map.put("reportCode", "B101");
            report.exportSubReport("b101", new String[]{"B101Report","B101SubReport"}, "BILL", map, reportList_);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    public void printB102PDF(ReportUtil report,List reportList_,
            List<BillingReportBean> reportList_main,List<BillingReportBean> reportList, SysBilling rpt_sysbilling){
         try {
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
            
            map.put("cust_name",rpt_sysbilling.getCustomerId().getCustomerNameTh());
            map.put("cust_address",rpt_sysbilling.getCustomerId().getCustomerAddress());
            map.put("send_date",DateTimeUtil.cvtDateForShow(rpt_sysbilling.getSendDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            map.put("workunit",rpt_sysbilling.getWorkunitId().getWorkunitName());
            map.put("price",NumberUtils.numberFormat(rpt_sysbilling.getBillTotal(),"#,##0.00"));
            map.put("price_vat",NumberUtils.numberFormat(rpt_sysbilling.getBillVat(),"#,##0.00"));
            map.put("price_total",NumberUtils.numberFormat(rpt_sysbilling.getBillTotalPrice(),"#,##0.00"));
            map.put("price_char",(rpt_sysbilling.getBillTotalPrice()==0.0?"":new ThaiBaht().getText(rpt_sysbilling.getBillTotalPrice())));
            map.put("bill_date",DateTimeUtil.cvtDateForShow(rpt_sysbilling.getBillDateLast(), "dd/MM/yyyy", new Locale("th", "TH")));
            
            map.put("reportCode", "B102");
            report.exportSubReport("b102", new String[]{"B102Report","B102SubReport"}, "BILL", map, reportList_);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        } 
     }
    public void printB103PDF(ReportUtil report,List reportList_,
            List<BillingReportBean> reportList_main,List<BillingReportBean> reportList, SysBilling rpt_sysbilling){
        try {
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
            
            map.put("cust_name",rpt_sysbilling.getCustomerId().getCustomerNameTh());
            map.put("cust_address",rpt_sysbilling.getCustomerId().getCustomerAddress());
            map.put("send_date",DateTimeUtil.cvtDateForShow(rpt_sysbilling.getSendDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            map.put("workunit",rpt_sysbilling.getWorkunitId().getWorkunitName());
            map.put("price",NumberUtils.numberFormat(rpt_sysbilling.getBillTotal(),"#,##0.00"));
            map.put("price_vat",NumberUtils.numberFormat(rpt_sysbilling.getBillVat(),"#,##0.00"));
            map.put("price_total",NumberUtils.numberFormat(rpt_sysbilling.getBillTotalPrice(),"#,##0.00"));
            map.put("price_char",(rpt_sysbilling.getBillTotalPrice()==0.0?"":new ThaiBaht().getText(rpt_sysbilling.getBillTotalPrice())));
            map.put("bill_date",DateTimeUtil.cvtDateForShow(rpt_sysbilling.getBillDateLast(), "dd/MM/yyyy", new Locale("th", "TH")));
            
            map.put("reportCode", "B103");
            report.exportSubReport("b103", new String[]{"B103Report","B103SubReport"}, "BILL", map, reportList_);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    public void printB104PDF(ReportUtil report,List reportList_,
            List<BillingReportBean> reportList_main,List<BillingReportBean> reportList, SysBilling rpt_sysDelivery){
        try{
            reportList_main.add(new BillingReportBean("", "", "", "", "", "", "", "","","","",""));
            reportList_.add(reportList_main);
            reportList_.add(reportList);
            HashMap map = new HashMap();
            map.put("delivery_no",rpt_sysDelivery.getDocumentno());
            map.put("cust_name",rpt_sysDelivery.getCustomerId().getCustomerNameTh());
            map.put("cust_address",rpt_sysDelivery.getCustomerId().getCustomerAddress());
            map.put("send_date",DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getSendDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            map.put("workunit",rpt_sysDelivery.getWorkunitId().getWorkunitName());
            map.put("price",NumberUtils.numberFormat(rpt_sysDelivery.getBillTotal(),"#,##0.00"));
            map.put("price_vat",NumberUtils.numberFormat(rpt_sysDelivery.getBillVat(),"#,##0.00"));
            map.put("price_total",NumberUtils.numberFormat(rpt_sysDelivery.getBillTotalPrice(),"#,##0.00"));
            map.put("price_char",(rpt_sysDelivery.getBillTotalPrice()==0.0?"":new ThaiBaht().getText(rpt_sysDelivery.getBillTotalPrice())));
           // map.put("bill_date",DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getBillDateLast(), "dd/MM/yyyy", new Locale("th", "TH")));
            
            map.put("reportCode", "B104");
            report.exportSubReport("b104", new String[]{"B104Report","B104SubReport"}, "Delivery", map, reportList_);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    public void printB105PDF(ReportUtil report,List reportList_,
            List<BillingReportBean> reportList_main,List<BillingReportBean> reportList, SysBilling rpt_sysDelivery){
        try{
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
            map.put("houseplan",null!=selected.getHousePlanId().getDetailDesc()?selected.getHousePlanId().getDetailDesc():"-");
            map.put("nursery",null!=selected.getNursery()?selected.getNursery():"-");
            
            map.put("price",NumberUtils.numberFormat(rpt_sysDelivery.getBillTotal(),"#,##0.00"));
            map.put("price_discount",NumberUtils.numberFormat(rpt_sysDelivery.getBillDiscount(),"#,##0.00"));
            map.put("price_total_discount",NumberUtils.numberFormat((null==rpt_sysDelivery.getBillTotal()?0.00:rpt_sysDelivery.getBillTotal())-(null==rpt_sysDelivery.getBillDiscount()?0.00:rpt_sysDelivery.getBillDiscount()),"#,##0.00"));
            map.put("price_vat",NumberUtils.numberFormat(rpt_sysDelivery.getBillVat(),"#,##0.00"));
            map.put("price_total",NumberUtils.numberFormat(rpt_sysDelivery.getBillTotalPrice(),"#,##0.00"));
            map.put("price_char",(rpt_sysDelivery.getBillTotalPrice()==0.0?"":new ThaiBaht().getText(rpt_sysDelivery.getBillTotalPrice())));
           // map.put("bill_date",DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getBillDateLast(), "dd/MM/yyyy", new Locale("th", "TH")));
            
            map.put("reportCode", "B105");
            report.exportSubReport("b105", new String[]{"B105Report","B105SubReport"}, "B105", map, reportList_);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    public void printB106PDF(ReportUtil report,List reportList_,
            List<BillingReportBean> reportList_main,List<BillingReportBean> reportList, SysBilling rpt_sysDelivery){
        try{
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
            map.put("branch",null!=rpt_sysDelivery.getCustomerId().getBranch()?rpt_sysDelivery.getCustomerId().getBranch():"-");
            map.put("taxid",null!=rpt_sysDelivery.getCustomerId().getTaxId()?rpt_sysDelivery.getCustomerId().getTaxId():"-");
            map.put("nursery",null!=selected.getNursery()?selected.getNursery():"-");
            
            map.put("price",NumberUtils.numberFormat(rpt_sysDelivery.getBillTotal(),"#,##0.00"));
            map.put("price_discount",NumberUtils.numberFormat(rpt_sysDelivery.getBillDiscount(),"#,##0.00"));
            map.put("price_total_discount",NumberUtils.numberFormat((null==rpt_sysDelivery.getBillTotal()?0.00:rpt_sysDelivery.getBillTotal())-(null==rpt_sysDelivery.getBillDiscount()?0.00:rpt_sysDelivery.getBillDiscount()),"#,##0.00"));
            map.put("price_vat",NumberUtils.numberFormat(rpt_sysDelivery.getBillVat(),"#,##0.00"));
            map.put("price_total",NumberUtils.numberFormat(rpt_sysDelivery.getBillTotalPrice(),"#,##0.00"));
            map.put("price_char",(rpt_sysDelivery.getBillTotalPrice()==0.0?"":new ThaiBaht().getText(rpt_sysDelivery.getBillTotalPrice())));
           // map.put("bill_date",DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getBillDateLast(), "dd/MM/yyyy", new Locale("th", "TH")));
            
            map.put("reportCode", "B106");
            report.exportSubReport("b106", new String[]{"B106Report","B106SubReport"}, "B106", map, reportList_);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
    public void printB107PDF(ReportUtil report,List reportList_,
            List<BillingReportBean> reportList_main,List<BillingReportBean> reportList, SysBilling rpt_sysDelivery){
        try{
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
            map.put("branch",null!=rpt_sysDelivery.getCustomerId().getBranch()?rpt_sysDelivery.getCustomerId().getBranch():"-");
            map.put("taxid",null!=rpt_sysDelivery.getCustomerId().getTaxId()?rpt_sysDelivery.getCustomerId().getTaxId():"-");
           // map.put("nursery",null!=selected.getNursery()?selected.getNursery():"-");
            map.put("remark",StringUtils.isNotBlank(rpt_sysDelivery.getRemark())?rpt_sysDelivery.getRemark():"..............................................................................................");
            
            map.put("price",NumberUtils.numberFormat(rpt_sysDelivery.getBillTotal(),"#,##0.00"));
            map.put("price_discount",NumberUtils.numberFormat(rpt_sysDelivery.getBillDiscount(),"#,##0.00"));
            map.put("price_total_discount",NumberUtils.numberFormat((null==rpt_sysDelivery.getBillTotal()?0.00:rpt_sysDelivery.getBillTotal())-(null==rpt_sysDelivery.getBillDiscount()?0.00:rpt_sysDelivery.getBillDiscount()),"#,##0.00"));
            map.put("price_vat",NumberUtils.numberFormat(rpt_sysDelivery.getBillVat(),"#,##0.00"));
            map.put("price_total",NumberUtils.numberFormat(rpt_sysDelivery.getBillTotalPrice(),"#,##0.00"));
            map.put("price_char",(rpt_sysDelivery.getBillTotalPrice()==0.0?"":new ThaiBaht().getText(rpt_sysDelivery.getBillTotalPrice())));
           // map.put("bill_date",DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getBillDateLast(), "dd/MM/yyyy", new Locale("th", "TH")));
            
            map.put("reportCode", "B107");
            report.exportSubReport("b107", new String[]{"B107Report","B107SubReport"}, "B107", map, reportList_);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
    public void printB108PDF(ReportUtil report,List reportList_,
            List<BillingReportBean> reportList_main,List<BillingReportBean> reportList, SysBilling rpt_sysDelivery){
        try{
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
            
            map.put("price",NumberUtils.numberFormat(rpt_sysDelivery.getBillTotal(),"#,##0.00"));
            map.put("price_discount",NumberUtils.numberFormat(rpt_sysDelivery.getBillDiscount(),"#,##0.00"));
            map.put("price_total_discount",NumberUtils.numberFormat((null==rpt_sysDelivery.getBillTotal()?0.00:rpt_sysDelivery.getBillTotal())-(null==rpt_sysDelivery.getBillDiscount()?0.00:rpt_sysDelivery.getBillDiscount()),"#,##0.00"));
            map.put("price_vat",NumberUtils.numberFormat(rpt_sysDelivery.getBillVat(),"#,##0.00"));
            map.put("price_total",NumberUtils.numberFormat(rpt_sysDelivery.getBillTotalPrice(),"#,##0.00"));
            map.put("price_char",(rpt_sysDelivery.getBillTotalPrice()==0.0?"":new ThaiBaht().getText(rpt_sysDelivery.getBillTotalPrice())));
           // map.put("bill_date",DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getBillDateLast(), "dd/MM/yyyy", new Locale("th", "TH")));
            
            map.put("reportCode", "B108");
            report.exportSubReport("b108", new String[]{"B108Report","B108SubReport"}, "B108", map, reportList_);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
    public void printB109PDF(ReportUtil report,List reportList_,
            List<BillingReportBean> reportList_main,List<BillingReportBean> reportList, SysBilling rpt_sysDelivery){
        try{
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
            map.put("billingdate",DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getBillingDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            
            map.put("product_send_date",null!=rpt_sysDelivery.getProductSendDate()?DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getProductSendDate(), "dd/MM/yyyy", new Locale("th", "TH")):"-");

            map.put("price",NumberUtils.numberFormat(rpt_sysDelivery.getBillTotal(),"#,##0.00"));
            map.put("price_discount",NumberUtils.numberFormat(rpt_sysDelivery.getBillDiscount(),"#,##0.00"));
            map.put("price_total_discount",NumberUtils.numberFormat((null==rpt_sysDelivery.getBillTotal()?0.00:rpt_sysDelivery.getBillTotal())-(null==rpt_sysDelivery.getBillDiscount()?0.00:rpt_sysDelivery.getBillDiscount()),"#,##0.00"));
            map.put("price_vat",NumberUtils.numberFormat(rpt_sysDelivery.getBillVat(),"#,##0.00"));
            map.put("price_total",NumberUtils.numberFormat(rpt_sysDelivery.getBillTotalPrice(),"#,##0.00"));
            map.put("price_char",(rpt_sysDelivery.getBillTotalPrice()==0.0?"":new ThaiBaht().getText(rpt_sysDelivery.getBillTotalPrice())));
           // map.put("bill_date",DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getBillDateLast(), "dd/MM/yyyy", new Locale("th", "TH")));
            
            map.put("reportCode", "B109");
            report.exportSubReport("b109", new String[]{"B109Report","B109SubReport"}, "B109", map, reportList_);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    public void printB110PDF(ReportUtil report,List reportList_,
            List<BillingReportBean> reportList_main,List<BillingReportBean> reportList, SysBilling rpt_sysDelivery){
        try{
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
            map.put("billingdate",DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getBillingDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            
            map.put("price",NumberUtils.numberFormat(rpt_sysDelivery.getBillTotal(),"#,##0.00"));
            map.put("price_discount",NumberUtils.numberFormat(rpt_sysDelivery.getBillDiscount(),"#,##0.00"));
            map.put("price_total_discount",NumberUtils.numberFormat((null==rpt_sysDelivery.getBillTotal()?0.00:rpt_sysDelivery.getBillTotal())-(null==rpt_sysDelivery.getBillDiscount()?0.00:rpt_sysDelivery.getBillDiscount()),"#,##0.00"));
            map.put("price_vat",NumberUtils.numberFormat(rpt_sysDelivery.getBillVat(),"#,##0.00"));
            map.put("price_total",NumberUtils.numberFormat(rpt_sysDelivery.getBillTotalPrice(),"#,##0.00"));
            map.put("price_char",(rpt_sysDelivery.getBillTotalPrice()==0.0?"":new ThaiBaht().getText(rpt_sysDelivery.getBillTotalPrice())));
           // map.put("bill_date",DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getBillDateLast(), "dd/MM/yyyy", new Locale("th", "TH")));
            
            map.put("reportCode", "B110");
            report.exportSubReport("b110", new String[]{"B110Report","B110SubReport"}, "B110", map, reportList_);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
    public void printB201PDF(ReportUtil report,List reportList_,
            List<BillingReportBean> reportList_main,List<BillingReportBean> reportList, SysBilling rpt_sysbilling){
        try {
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
            
            map.put("documentno",rpt_sysbilling.getDocumentno());
            map.put("cust_name",rpt_sysbilling.getCustomerId().getCustomerNameTh());
            map.put("cust_address",rpt_sysbilling.getCustomerId().getCustomerAddress());
            map.put("send_date",DateTimeUtil.cvtDateForShow(rpt_sysbilling.getSendDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            map.put("workunit",null!=rpt_sysbilling.getWorkunitId()?rpt_sysbilling.getWorkunitId().getWorkunitName():"-");
            map.put("branch",null!=rpt_sysbilling.getCustomerId().getBranch()?rpt_sysbilling.getCustomerId().getBranch():"-");
            map.put("taxid",null!=rpt_sysbilling.getCustomerId().getTaxId()?rpt_sysbilling.getCustomerId().getTaxId():"-");
            
            map.put("price",NumberUtils.numberFormat(rpt_sysbilling.getBillTotal(),"#,##0.00"));
            map.put("price_vat",NumberUtils.numberFormat(rpt_sysbilling.getBillVat(),"#,##0.00"));
            map.put("price_discount",NumberUtils.numberFormat(rpt_sysbilling.getBillDiscount(),"#,##0.00"));
            map.put("price_total",NumberUtils.numberFormat(rpt_sysbilling.getBillTotalPrice(),"#,##0.00"));
           // map.put("price_total",NumberUtils.numberFormat(rpt_sysbilling.getRealTotalPrice(),"#,##0.00"));
            map.put("price_char",(rpt_sysbilling.getBillTotalPrice()==0.0?"":new ThaiBaht().getText(rpt_sysbilling.getBillTotalPrice())));
            //map.put("price_char",(rpt_sysbilling.getBillTotalPrice()==0.0?"":new ThaiBaht().getText(rpt_sysbilling.getRealTotalPrice())));
            map.put("bill_date",DateTimeUtil.cvtDateForShow(rpt_sysbilling.getBillDateLast(), "dd/MM/yyyy", new Locale("th", "TH")));
            
            map.put("reportCode", "B201");
            report.exportSubReport_Template("template.jpg","b201", new String[]{"B201Report","B201SubReport"}, "Good_Reciept", map, reportList_);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    public void printB202PDF(ReportUtil report,List reportList_,
            List<BillingReportBean> reportList_main,List<BillingReportBean> reportList, SysBilling rpt_sysbilling){
        try {
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
            
            map.put("documentno",rpt_sysbilling.getDocumentno());
            map.put("cust_name",rpt_sysbilling.getCustomerId().getCustomerNameTh());
            map.put("cust_address",rpt_sysbilling.getCustomerId().getCustomerAddress());
            map.put("send_date",DateTimeUtil.cvtDateForShow(rpt_sysbilling.getSendDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            map.put("workunit",null!=rpt_sysbilling.getWorkunitId()?rpt_sysbilling.getWorkunitId().getWorkunitName():"-");
            map.put("branch",null!=rpt_sysbilling.getCustomerId().getBranch()?rpt_sysbilling.getCustomerId().getBranch():"-");
            map.put("taxid",null!=rpt_sysbilling.getCustomerId().getTaxId()?rpt_sysbilling.getCustomerId().getTaxId():"-");
            
            map.put("price",NumberUtils.numberFormat(rpt_sysbilling.getBillTotal(),"#,##0.00"));
            map.put("price_vat",NumberUtils.numberFormat(rpt_sysbilling.getBillVat(),"#,##0.00"));
            map.put("price_discount",NumberUtils.numberFormat(rpt_sysbilling.getBillDiscount(),"#,##0.00"));
            map.put("price_total",NumberUtils.numberFormat(rpt_sysbilling.getBillTotalPrice(),"#,##0.00"));
            //map.put("price_total",NumberUtils.numberFormat(rpt_sysbilling.getRealTotalPrice(),"#,##0.00"));
            map.put("price_char",(rpt_sysbilling.getBillTotalPrice()==0.0?"":new ThaiBaht().getText(rpt_sysbilling.getBillTotalPrice())));
            //map.put("price_char",(rpt_sysbilling.getBillTotalPrice()==0.0?"":new ThaiBaht().getText(rpt_sysbilling.getRealTotalPrice())));
            map.put("bill_date",DateTimeUtil.cvtDateForShow(rpt_sysbilling.getBillDateLast(), "dd/MM/yyyy", new Locale("th", "TH")));

            map.put("reportCode", "B202");
            report.exportSubReport_Template("template.jpg","b202", new String[]{"B202Report","B202SubReport"}, "Sales_Invoice", map, reportList_);
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
            
            List<String> listNotBillingType=new ArrayList();
            listNotBillingType.add(Constants.CREDIT_NOTE);
            lazyBillingReportModel = new LazyBillingReportDataModel(billingFacade,listNotBillingType,billType,documentno,StringUtils.trimToEmpty(companyName),startDate,toDate);
            DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("listForm:billingTable");
            dataTable.setFirst(0);
        } catch (Exception ex) {
            LOG.error(ex);
        }
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

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public LazyDataModel<SysBilling> getLazyBillingReportModel() {
        return lazyBillingReportModel;
    }

    public void setLazyBillingReportModel(LazyDataModel<SysBilling> lazyBillingReportModel) {
        this.lazyBillingReportModel = lazyBillingReportModel;
    }

    

}