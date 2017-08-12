package com.finework.jsf.controller.report;

import com.finework.jsf.controller.factory.*;
import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysManufactory;
import com.finework.core.ejb.entity.SysManufactoryDetail;
import com.finework.core.ejb.entity.SysManufacturing;
import com.finework.core.ejb.entity.SysOrganization;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.core.util.Constants;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.NumberUtils;
import com.finework.core.util.ReportUtil;
import com.finework.core.util.ThaiBaht;
import com.finework.ejb.facade.ContractorFacade;
import com.finework.ejb.facade.CustomerFacade;
import com.finework.ejb.facade.ManufactoryFacade;
import com.finework.ejb.facade.OrganizationFacade;
import com.finework.ejb.facade.StockFacade;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.SequenceController;
import com.finework.jsf.common.UserInfoController;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Adisorn Jomjanyong
 */

@ManagedBean(name = R108Controller.CONTROLLER_NAME)
@SessionScoped
public class R108Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(R108Controller.class);
    public static final String CONTROLLER_NAME = "r108Controller";
    
    @Inject
    private ManufactoryFacade manufactoryFacade;
    @Inject
    private ContractorFacade contractorFacade;
    @Inject
    private OrganizationFacade organizationFacade;
    @Inject
    private StockFacade stockFacade;
    @Inject
    private UserInfoController userInfo;
    @Inject
    private SequenceController sequence;
    @Inject
    private CustomerFacade customerFacade;
    
    private LazyDataModel<SysManufactory> lazyManufactoryModel;
    
    //
    private List<SysManufactory> items;
    private SysManufactory selected;
    private List<SysManufactory> printSelected;

    //detial 
    private SysManufactoryDetail facDetail_selected;
    
    
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
    private Double total_net;
    private String total_th;
    
    private int draftNo;
    
    
    //auto complete
    private SysContractor contractor_selected;
    private SysManufacturing manufacturing_selected;
    private SysWorkunit workunit_selected;
    

   
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
        selected = new SysManufactory();
        facDetail_selected=new SysManufactoryDetail();
//        runningNoCustomer();
        next(page);
    }

    public void prepareEdit(String page) {
       // this.total=selected.getRealTotalPrice();
       // this.realTotalPrice=selected.getRealTotalPrice();
        this.contractor_selected=selected.getContractorId();
        checkTotalPrice();
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
        this.total_ream=0.00;
        this.total_net=0.00;
    }
     
    public void clearData(){
         selected = new SysManufactory();
         contractor_selected=new SysContractor();

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

              items=manufactoryFacade.findSysManufactoryListRealByCriteria(documentno, null,contractor_find,null, startDate, toDate);
              
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
     
  
    
     public void clearData_sysManufactoryDetail(){
        facDetail_selected = new SysManufactoryDetail();
        manufacturing_selected=new SysManufacturing();
        workunit_selected=new SysWorkunit();
    }
    
   
    public void checkTotalPrice(){
        this.total=0.0;
        this.total_vat=0.0;
        this.total_volume=0.0;
        this.total_divide_equipment = 0.00;
        this.total_ream=0.00;
        this.total_net=0.00;
        
        Double total_ = 0.0;
        if (null != selected.getSysManufactoryDetailList()) {
            for (SysManufactoryDetail sysdetail : selected.getSysManufactoryDetailList()) {
                 Double volumeReal=0.0;
                 if(null !=sysdetail.getVolume_real() && sysdetail.getVolume_real()>0){
                     volumeReal=sysdetail.getVolume_real();
                 }
                 
                Double unitpirce = null != sysdetail.getManufacturingId().getUnitPrice() ? sysdetail.getManufacturingId().getUnitPrice() : 0.0;

                if (2 == sysdetail.getManufacturingId().getCalculateType()) {
                    Double length = null != sysdetail.getLength() ? sysdetail.getLength() : 0.0;
                    total_ += volumeReal * length * unitpirce;
                } else {
                    total_ += unitpirce * volumeReal;
                }
            }
             this.total = total_;
             this.total_vat = this.total  * 0.03;
             this.total_volume=this.total-this.total_vat;
        } else {
           clearDatatTotal();
        }
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
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredSysContractor;
    }
   
    //Auto manufacturing
    public List<SysManufacturing> completeManufacturing(String query) {
         List<SysManufacturing> filteredsysManufacturing= new ArrayList<>();
       try {
            List<SysManufacturing> allSysManufacturing = stockFacade.findSysManufacturingList();
            for (SysManufacturing sysManufacturing:allSysManufacturing) {
               if(sysManufacturing.getManufacturingDesc()!=null && sysManufacturing.getManufacturingDesc().length()>0){
                if(sysManufacturing.getManufacturingDesc().toLowerCase().startsWith(query)) {
                    filteredsysManufacturing.add(sysManufacturing);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredsysManufacturing;
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
   
    
 //End Auto Complete==========================================================================    
    
    @Override
    public void reportPDF() {
        try {
            ReportUtil report = new ReportUtil();
            List reportList_ = new ArrayList<>();
            List<FactoryReportBean> reportList_main = new ArrayList<>();
            List<FactoryReportBean> reportList = new ArrayList<>();
            SysManufactory rpt_sysManufactory=manufactoryFacade.findByPK(selected.getFactoryId());
            
            int intRunningNo=1;
            Double total_target=0.0;
           List<SysManufactoryDetail> list = rpt_sysManufactory.getSysManufactoryDetailList();
            for (SysManufactoryDetail to : list) {
                FactoryReportBean bean = new FactoryReportBean();
                bean.setSeq(String.valueOf(intRunningNo++));
                String calculateType="";
                if (null != to.getManufacturingId().getCalculateType()) {
                    switch (to.getManufacturingId().getCalculateType()) {
                        case 1:
                            calculateType = Constants.CALCULATE_QUANTITY;
                            break;
                        case 2:
                            calculateType = Constants.CALCULATE_LENGTH;
                            break;
                        default:
                            calculateType = Constants.CALCULATE_SET;
                            break;
                    }
                }

                String detail=to.getManufacturingId().getManufacturingDesc()+" <strong><u>*("+calculateType+")</u></strong>";
                if(2== to.getManufacturingId().getCalculateType()){
                    detail=detail+"<strong><u>("+NumberUtils.numberFormat(to.getLength(), "#,##0.00")+")</u></strong>";
                   //detail="<style isBold='true' isUnderline='true'> (" +to.getManufacturingId().getManufacturingDesc()+NumberUtils.numberFormat(to.getLength(), "#,##0.00") + ")</style>";
                }
                
                bean.setDetail(detail);
                bean.setPlot(null!=to.getPlot()?to.getPlot():"");
                bean.setWorkunit(null!=to.getWorkunitId()?to.getWorkunitId().getWorkunitName():"");
                Double volumn_target=0.0;
                volumn_target=null!=to.getVolumeTarget()?to.getVolumeTarget():0.0;
                total_target=total_target+volumn_target;
                bean.setVolumnTarget(NumberUtils.numberFormat(volumn_target, "#,##0.00"));
                
                reportList.add(bean);
            }
            reportList_main.add(new FactoryReportBean("", "", "", "",""));
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
            
            map.put("documentno",rpt_sysManufactory.getDocumentno());
            map.put("producer",userInfo.getAdminUser().getFirstName()+" "+userInfo.getAdminUser().getLastName());
            map.put("contractor_name",rpt_sysManufactory.getContractorId().getContractorNameTh());
            map.put("contractor_address",rpt_sysManufactory.getContractorId().getContractorAddress());
            map.put("send_date",DateTimeUtil.cvtDateForShow(rpt_sysManufactory.getFactoryDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            map.put("taxid",null!=rpt_sysManufactory.getContractorId().getTaxId()?rpt_sysManufactory.getContractorId().getTaxId():"-");
            map.put("remark",StringUtils.isNotBlank(rpt_sysManufactory.getRemark())?rpt_sysManufactory.getRemark():"...........................................................................................................................................");
            
            map.put("total_target",NumberUtils.numberFormat(total_target,"#,##0.00"));

            map.put("reportCode", "P101");
            report.exportSubReport_Template("template.jpg","p101", new String[]{"P101Report","P101SubReport"}, "ManuFactory", map, reportList_);
            
//            //add print form
//            SysPrintBilling sysPrintBilling =new SysPrintBilling();
//            sysPrintBilling.setBillingId(selected);
//            sysPrintBilling.setUserId(userInfo.getAdminUser());
//            sysPrintBilling.setCreatedBy(userInfo.getAdminUser().getUsername());
//            sysPrintBilling.setCreatedDt(DateTimeUtil.getSystemDate());
//            billingFacade.createSysPrintBilling(sysPrintBilling);
            
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

    public LazyDataModel<SysManufactory> getLazyManufactoryModel() {
        return lazyManufactoryModel;
    }

    public void setLazyManufactoryModel(LazyDataModel<SysManufactory> lazyManufactoryModel) {
        this.lazyManufactoryModel = lazyManufactoryModel;
    }

    public List<SysManufactory> getItems() {
        return items;
    }

    public void setItems(List<SysManufactory> items) {
        this.items = items;
    }

    public SysManufactory getSelected() {
        return selected;
    }

    public void setSelected(SysManufactory selected) {
        this.selected = selected;
    }

    public List<SysManufactory> getPrintSelected() {
        return printSelected;
    }

    public void setPrintSelected(List<SysManufactory> printSelected) {
        this.printSelected = printSelected;
    }

    public SysManufactoryDetail getFacDetail_selected() {
        return facDetail_selected;
    }

    public void setFacDetail_selected(SysManufactoryDetail facDetail_selected) {
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

    public SysWorkunit getWorkunit_selected() {
        return workunit_selected;
    }

    public void setWorkunit_selected(SysWorkunit workunit_selected) {
        this.workunit_selected = workunit_selected;
    }

   
}