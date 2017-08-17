package com.finework.jsf.controller.transporter;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysManufactoryReal;
import com.finework.core.ejb.entity.SysPrepareTransport;
import com.finework.core.ejb.entity.SysPrepareTransportDetail;
import com.finework.core.ejb.entity.SysTransportStaff;
import com.finework.core.ejb.entity.SysTransportation;
import com.finework.core.ejb.entity.SysTransportationDetail;
import com.finework.core.ejb.entity.SysTransportationServiceDetail;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.core.util.Constants;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.ejb.facade.ContractorFacade;
import com.finework.ejb.facade.CustomerFacade;
import com.finework.ejb.facade.ForemanFacade;
import com.finework.ejb.facade.ManufactoryFacade;
import com.finework.ejb.facade.OrganizationFacade;
import com.finework.ejb.facade.TransporterFacade;
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
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import net.sf.jasperreports.engine.util.ObjectUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Adisorn Jomjanyong
 */

@ManagedBean(name = T104Controller.CONTROLLER_NAME)
@SessionScoped
public class T104Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(T104Controller.class);
    public static final String CONTROLLER_NAME = "t104Controller";
    
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
    
    //
    private List<SysTransportation> items;
    private SysTransportation selected;
    private List<SysTransportation> printSelected;

    //detial 
    private SysTransportationDetail transportationDetail_selected;
    private SysTransportationServiceDetail tpServiceDetail_selected;
    
    
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
    }

    public void prepareEdit(String page) {
       // this.contractor_selected=selected.getContractorId();
        
        next(page);
    }
    public void cancel(String path) {
        clearData();
        search();
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
      
    }
    


    @Override
    public void edit() {
         try {
             if (null == selected.getWorkunitId()) {
                 JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "หน่วยงาน/โครงการ"));
                 RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                 return;
             }
             if (null == selected.getStatus()) {
                 JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "สถานะขนส่ง"));
                 RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                 return;
             }
             if (null == selected.getTpDateComplete()) {
                 JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ขนส่งสำเร็จวันที่"));
                 RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                 return;
             }
             if(selected.getTpOTTimevalue()){
                 if(ObjectUtils.equals(new Integer(0), selected.getTpOtTimeHours())){
                     JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชั่วโมง"));
                     RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                     return;
                 }
             }
             if(Objects.equals(Constants.TRANSPORTATION_PRODUCTION,selected.getTransportType())){
                 //update manufactory_real Transporter_status 
                 for (SysTransportationDetail sysTransportationDetail_ : selected.getSysTransportationDetailList()) {
                     for (SysPrepareTransportDetail sysPrepareTransportDetail_ : sysTransportationDetail_.getPrepareTpId().getSysPrepareTransportDetailList()) {
                        // manufactoryFacade.updateStatusTransporterSysManufactoryReal(Constants.TRANSPORTER_COMPLETE, sysPrepareTransportDetail_.getFactoryRealId());
                         SysManufactoryReal sysManufactoryReal = sysPrepareTransportDetail_.getFactoryRealId();
                         sysManufactoryReal.setStatusTransporter(Constants.TRANSPORTER_COMPLETE);
                         manufactoryFacade.editSysManufactoryReal(sysManufactoryReal);
                     }

                     //update status SysPrepareTransport complete or not complete
                     if (Objects.equals(Constants.TRANSPORTATION_COMPLETE, selected.getStatus())) {
                         //transporterFacade.updateStatusSysPrepareTransportByprepareTpId(Constants.PREPARE_TRANSPORTER_CARRY, sysTransportationDetail_.getPrepareTpId().getPrepareTpId());
                         SysPrepareTransport sysPrepareTransport = sysTransportationDetail_.getPrepareTpId();
                         sysPrepareTransport.setStatus(Constants.PREPARE_TRANSPORTER_CARRY);
                         transporterFacade.editSysPrepareTransport(sysPrepareTransport);
                     } else if (Objects.equals(Constants.TRANSPORTATION_CANCEL, selected.getStatus())) {
                        // transporterFacade.updateStatusSysPrepareTransportByprepareTpId(Constants.PREPARE_TRANSPORTER_NOT_COMPLETE, sysTransportationDetail_.getPrepareTpId().getPrepareTpId());
                         SysPrepareTransport sysPrepareTransport = sysTransportationDetail_.getPrepareTpId();
                         sysPrepareTransport.setStatus(Constants.PREPARE_TRANSPORTER_NOT_COMPLETE);
                         transporterFacade.editSysPrepareTransport(sysPrepareTransport);
                     }
                 }
             }

             selected.setModifiedBy(userInfo.getAdminUser().getUsername());
             selected.setModifiedDt(DateTimeUtil.getSystemDate());

             transporterFacade.editSysTransportation(selected);

             clearData();
             search();
             JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
             next("transporter/t104/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
 
    @Override
    public void delete() {
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
                
               items=transporterFacade.findSysTransportationListByCriteria(foreman_find,documentno,workunit_find,status_find, startDate, toDate);
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
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredSysForeman;
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
   
    
    public void ot() {
        selected.setTpOTTimevalue(false);
        selected.setTpOtTimeHours(0);
    }

    public void ottime() {
        selected.setTpOt(false);
    }
  /*  
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
            List<SysTransportationDetail> list = rpt_sysPaymentFac.getSysTransportationDetailList();
            for (SysTransportationDetail to : list) {
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
            JasperPrint print= report.exportSubReport_Template_mearge("template.jpg","t104", new String[]{"P105Report","P105SubReport"}, "Process", map, reportList_);
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
                JasperPrint printExpenses= report.exportSubReport_Template_mearge("template.jpg","t104", new String[]{"P105ExpensesReport","P105ExpensesSubReport"}, "Processๅ", mapExpenses, reportListExpenses_);
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
                List<SysTransportationDetail> list = rpt_sysPaymentFac.getSysTransportationDetailList();
                for (SysTransportationDetail to : list) {
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
                JasperPrint print= report.exportSubReport_Template_mearge("template.jpg","t104", new String[]{"P105Report","P105SubReport"}, "Process", map, reportList_);
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
                    JasperPrint printExpenses= report.exportSubReport_Template_mearge("template.jpg","t104", new String[]{"P105ExpensesReport","P105ExpensesSubReport"}, "Process1", mapExpenses, reportListExpenses_);
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
    
    */


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
   
}