package com.finework.jsf.controller.transporter;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysManufactory;
import com.finework.core.ejb.entity.SysManufactoryReal;
import com.finework.core.ejb.entity.SysPrepareTransport;
import com.finework.core.ejb.entity.SysPrepareTransportDetail;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.core.util.Constants;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.ThaiBaht;
import com.finework.ejb.facade.ContractorFacade;
import com.finework.ejb.facade.CustomerFacade;
import com.finework.ejb.facade.ForemanFacade;
import com.finework.ejb.facade.ManufactoryFacade;
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
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Adisorn Jomjanyong
 */

@ManagedBean(name = T101Controller.CONTROLLER_NAME)
@SessionScoped
public class T101Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(T101Controller.class);
    public static final String CONTROLLER_NAME = "t101Controller";
    
    @Inject
    private TransporterFacade transporterFacade;
    @Inject
    private ManufactoryFacade manufactoryFacade;
    @Inject
    private ContractorFacade contractorFacade;
    @Inject
    private UserInfoController userInfo;
    @Inject
    private SequenceController sequence;
    @Inject
    private CustomerFacade customerFacade;
    @Inject
    private ForemanFacade foremanFacade;
    
    private LazyDataModel<SysPrepareTransport> lazyManutransporterModel;
    
    //
    private List<SysPrepareTransport> items;
    private SysPrepareTransport selected;
    private List<SysPrepareTransport> printSelected;

    //detial 
    private SysPrepareTransportDetail prepareTransport_selected;
    
    
    //find criteria main
    private String documentno;
    private SysContractor contractor_find;
    private SysWorkunit workunit_find;
    private SysForeman foreman_find;
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
    private SysWorkunit workunit_selected;
    private SysForeman foreman_selected;
    
    //find dialog
    List<SysManufactoryReal> manufactoryReal_items_dialog;
    private SysManufactory manufactory_selected;
    //selectMuti
    private List<SysManufactoryReal> manufactoryRealSelected;
   
    @PostConstruct
    @Override
    public void init() {
     //   sysManutransporterDetailList=new ArrayList();
        
        search();
    }
    
    public void next(String path) {
        NaviController nav = (NaviController) JsfUtil.getManagedBeanValue(NaviController.CONTROLLER_NAME);
        nav.next(path);
    }
    
    public void prepareCreate(String page) {
        selected = new SysPrepareTransport();
     //   manufactoryRealSelected=new SysPrepareTransportDetail();
//        runningNoCustomer();
        next(page);
    }

    public void prepareEdit(String page) {
       // this.total=selected.getRealTotalPrice();
       // this.realTotalPrice=selected.getRealTotalPrice();
      //  this.contractor_selected=selected.getContractorId();
        this.foreman_selected=selected.getForemanId();
        this.workunit_selected=selected.getWorkunitId();        next(page);
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
         selected = new SysPrepareTransport();
         workunit_selected=new SysWorkunit();
         foreman_selected=new SysForeman();
    }
    
     
   
    @Override
    public void create() {
      try {

            if (null==foreman_selected) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อเล่นผู้ควบคุมงาน(โฟร์แมน)"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            if (null == workunit_selected) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "หน่วยงาน/โครงการ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (null==selected.getPrepareTpDate()){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่ทำรายการ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (null==selected.getTpDate()){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่ต้องการส่ง"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
           
            if (null==selected.getSysPrepareTransportDetailList() || selected.getSysPrepareTransportDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            //insert Manutransporter
            selected.setForemanId(foreman_selected);
            selected.setWorkunitId(workunit_selected);
            selected.setStatus(Constants.PREPARE_TRANSPORTER_INVESTIGATE);
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            
            //insertDetail
            List<SysPrepareTransportDetail> detal_add=new ArrayList();
            for(SysPrepareTransportDetail sysPrepareTransportDetail_:selected.getSysPrepareTransportDetailList()){
                sysPrepareTransportDetail_.setPrepareTpdetailId(null);//auto generate id on db
                sysPrepareTransportDetail_.setPrepareTpId(selected);
                detal_add.add(sysPrepareTransportDetail_);
            }

            selected.setSysPrepareTransportDetailList(detal_add);

            runningNoCustomer();
            
            transporterFacade.createSysPrepareTransport(selected);
            
            //update manufactory_real status on_process
             for(SysPrepareTransportDetail sysPrepareTransportDetail_:selected.getSysPrepareTransportDetailList()){
                 manufactoryFacade.updateStatusTransporterSysManufactoryReal(Constants.TRANSPORTER_INVESTIGATE,sysPrepareTransportDetail_.getFactoryRealId());
             }
            
         
            clearData();
         //   clearDatatTotal();
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
        /*    if("ADMIN".equals(userInfo.getAdminUser().getRoleId().getRoleName())){
                if (null == foreman_selected) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อเล่นผู้ควบคุมงาน(โฟร์แมน)"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                    return;
                }
                if (null == workunit_selected) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "หน่วยงาน/โครงการ"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                    return;
                }
            }
             */
            
            if (null==selected.getSysPrepareTransportDetailList() || selected.getSysPrepareTransportDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }  
            
             //update manufactory_real status normal
             List<SysPrepareTransportDetail> prepareDetail=transporterFacade.findSysPrepareTransportDetailByPrepareID(selected.getPrepareTpId());
             for(SysPrepareTransportDetail sysPrepareTransportDetail_:prepareDetail){
                 manufactoryFacade.updateStatusTransporterSysManufactoryReal(Constants.TRANSPORTER_NORMAL,sysPrepareTransportDetail_.getFactoryRealId());
             }

            //deleteDetail
             transporterFacade.deletePrepareTpIdOnDetail(selected.getPrepareTpId());
             
            //insertDetail
            List<SysPrepareTransportDetail> detal_edit=new ArrayList();
            for(SysPrepareTransportDetail sysPrepareTransportDetail_:selected.getSysPrepareTransportDetailList()){
                sysPrepareTransportDetail_.setPrepareTpdetailId(null);//auto generate id on db
                sysPrepareTransportDetail_.setPrepareTpId(selected);
                detal_edit.add(sysPrepareTransportDetail_);
            }
            
            //update Factory
          /*  if("ADMIN".equals(userInfo.getAdminUser().getRoleId().getRoleName())){
              selected.setForemanId(foreman_selected);
              selected.setWorkunitId(workunit_selected);
            }*/
            selected.setSysPrepareTransportDetailList(detal_edit);
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate()); 
            
            transporterFacade.editSysPrepareTransport(selected);
            
            //update manufactory_real status on_process
             for(SysPrepareTransportDetail sysPrepareTransportDetail_:selected.getSysPrepareTransportDetailList()){
                 manufactoryFacade.updateStatusTransporterSysManufactoryReal(Constants.TRANSPORTER_INVESTIGATE,sysPrepareTransportDetail_.getFactoryRealId());
             }
         
            clearData();
           // clearDatatTotal();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("transporter/t101/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    @Override
    public void delete() {
        try {
             //update manufactory_real status on_process
             for(SysPrepareTransportDetail sysPrepareTransportDetail_:selected.getSysPrepareTransportDetailList()){
                 manufactoryFacade.updateStatusTransporterSysManufactoryReal(Constants.TRANSPORTER_NORMAL,sysPrepareTransportDetail_.getFactoryRealId());
             }
             
            transporterFacade.deleteSysPrepareTransport(selected);     
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
                
              items=transporterFacade.findSysPrepareTransportListByCriteria(foreman_find,documentno,workunit_find,Constants.PREPARE_TRANSPORTER_INVESTIGATE, startDate, toDate);
              
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
     public void addDetail(){
         try {
             if (selected.getSysPrepareTransportDetailList() == null) {
                 selected.setSysPrepareTransportDetailList(new ArrayList<SysPrepareTransportDetail>());
             }

              //is match
             List<Integer> sysFactory_list = new ArrayList();
             int seq = 0;
             for (SysPrepareTransportDetail sysPrepareTransportDetail : selected.getSysPrepareTransportDetailList()) {
                 sysFactory_list.add(sysPrepareTransportDetail.getFactoryRealId().getFactoryRealId());
             }
             for (SysManufactoryReal sysManufactoryReal : manufactoryRealSelected) {
                 if (!sysFactory_list.contains(sysManufactoryReal.getFactoryRealId())) {
                     SysPrepareTransportDetail bean = new SysPrepareTransportDetail();
                     bean.setPrepareTpdetailId(seq);
                     bean.setFactoryRealId(sysManufactoryReal);

                     seq += 1;
                     selected.getSysPrepareTransportDetailList().add(bean);
                     sysFactory_list.add(sysManufactoryReal.getFactoryRealId());
                 }
             }

             //checkTotalPrice();
             clearData_syspreparetransporterDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }    
   
    
     public void clearData_syspreparetransporterDetail(){
        prepareTransport_selected = new SysPrepareTransportDetail();
        manufactory_selected = new SysManufactory();
        manufactoryReal_items_dialog = new ArrayList();
        manufactoryRealSelected=  new ArrayList();
    }
     
    public void deleteDetail(){
        try {
             //delete total 
             selected.getSysPrepareTransportDetailList().remove(prepareTransport_selected);
            // checkTotalPrice();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
 /*   
    public void checkTotalPrice(){
        this.total=0.0;
        this.total_vat=0.0;
        this.total_volume=0.0;
        this.total_divide_equipment = 0.00;
        this.total_ream=0.00;
        this.total_net=0.00;
        
        Double total_ = 0.0;
        if (null != selected.getSysManutransporterDetailList()) {
            for (SysManutransporterDetail sysdetail : selected.getSysManutransporterDetailList()) {
                 Double volumeReal=0.0;
                 if(null !=sysdetail.getVolume_real() && sysdetail.getVolume_real()>0){
                     volumeReal=sysdetail.getVolume_real();
                 }
                 
                 Double unitpirce=null!=sysdetail.getManufacturingId().getUnitPrice()?sysdetail.getManufacturingId().getUnitPrice():0.0;
                 //total_=total_+(volumeReal* sysdetail.getManufacturingId().getUnitPrice());
                if (2 == sysdetail.getManufacturingId().getCalculateType()) {
                    Double length = null != sysdetail.getLength()? sysdetail.getLength() : 0.0;
                  //  Double wage = null != sysdetail.getWage() ? sysdetail.getWage() : 0.0;
                    total_ += volumeReal * length * unitpirce;
                } else {
                    total_ += unitpirce * volumeReal;
                }
            }
             this.total = total_;
             this.total_vat = this.total  * 0.03;
             this.total_volume=this.total-this.total_vat;
            // this.total_net=  this.total_volume-(this.total_divide_equipment+this.total_ream);
           //  Double totaldiscount=this.total-this.total_discount;
           //  this.total_vat =totaldiscount * 0.07;
           //  this.total_net = totaldiscount + this.total_vat ;
          //   this.realTotalPrice=this.total_net;
          //  this.realTotalPrice= total_;
        } else {
           clearDatatTotal();
        }
    }*/
    
    public void checkRealTotalPrice(){
        if(this.total_net>0.00){
//            Double billreal=this.total_net-this.total_net--;
//            this.total_vat=this.total_vat-billreal;
//            this.total_net = this.total_net - billreal ;
            
            this.total_net=  this.total_volume-(this.total_divide_equipment+this.total_ream);
        }
    }
    
   //===== end  Dialog=========   
    
    public void prepareEdit() {
       // manufactoryRealSelected=new SysManufactoryReal();
    } 
   public void handleKeyEvent(){}
   
   
    public void runningNoCustomer() {
        String sequence_no=sequence.updateRunningNO(1,Constants.SEQUNCE_NO_PREPARE_TRANSPORTER,"yyMM");
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
   
   //Auto factory
    public List<SysManufactory> completeManufactory(String query) {
         List<SysManufactory> filteredSysManufactory= new ArrayList<>();
       try {     
           if(null!=foreman_selected ){
                List<SysManufactory> allSSysManufactory = manufactoryFacade.findSysManufactoryListByCriteria(null,null, null,foreman_selected, null, null);
                for (SysManufactory manufactory:allSSysManufactory) {
                   if(manufactory.getDocumentno()!=null && manufactory.getDocumentno().length()>0){
                    if(manufactory.getDocumentno().toLowerCase().startsWith(query.toLowerCase())) {
                        filteredSysManufactory.add(manufactory);
                    }
                   }
                }
           }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredSysManufactory;
    }
 
 //End Auto Complete==========================================================================    
    
     public void searchManufactory(){
          try {     
              if(null!=foreman_selected && null!=workunit_selected){
                    manufactoryReal_items_dialog = manufactoryFacade.findTranSporterSysManufactoryRealListByCriteria(null!=manufactory_selected?manufactory_selected.getDocumentno():null,
                     null!=foreman_selected?foreman_selected:null,null!=workunit_selected?workunit_selected:null,null,null, Constants.TRANSPORTER_NORMAL);
              }
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }  
    
    /*
    @Override
    public void reportPDF() {
        try {
            ReportUtil report = new ReportUtil();
            List reportList_ = new ArrayList<>();
            List<FactoryReportBean> reportList_main = new ArrayList<>();
            List<FactoryReportBean> reportList = new ArrayList<>();
          //  SysManutransporter rpt_sysManutransporter=manutransporterFacade.findByPK(selected.getFactoryId());
            SysMa rpt_sysManutransporter=null;
            
            int intRunningNo=1;
            Double total_target=0.0;
           List<SysManutransporterDetail> list = rpt_sysManutransporter.getSysManutransporterDetailList();
            for (SysManutransporterDetail to : list) {
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
            
            map.put("documentno",rpt_sysManutransporter.getDocumentno());
            map.put("producer",userInfo.getAdminUser().getFirstName()+" "+userInfo.getAdminUser().getLastName());
            map.put("contractor_name",rpt_sysManutransporter.getContractorId().getContractorNameTh());
            map.put("contractor_address",rpt_sysManutransporter.getContractorId().getContractorAddress());
            map.put("send_date",DateTimeUtil.cvtDateForShow(rpt_sysManutransporter.getFactoryDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            map.put("taxid",null!=rpt_sysManutransporter.getContractorId().getTaxId()?rpt_sysManutransporter.getContractorId().getTaxId():"-");
            map.put("remark",StringUtils.isNotBlank(rpt_sysManutransporter.getRemark())?rpt_sysManutransporter.getRemark():"...........................................................................................................................................");
            
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
   */
   
   
   /*
    public void printPdfMuti(){
         try {
             List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
             Collections.sort(printSelected, new SysPaymentManutransporter());
             for (SysPaymentManutransporter sysPaymentManutransporter : printSelected) {
                    ReportUtil report = new ReportUtil();
                    List reportList_ = new ArrayList<>();
                    List<FactoryReportBean> reportList_main = new ArrayList<>();
                    List<FactoryReportBean> reportList = new ArrayList<>();
                    SysPaymentManutransporter rpt_sysManutransporter = paymentManutransporterFacade.findByPK(sysPaymentManutransporter.getPaymentFactoryId());

                    int intRunningNo = 1;
                    List<SysPaymentManutransporterDetail> list = rpt_sysManutransporter.getSysPaymentManutransporterDetailList();
                    for (SysPaymentManutransporterDetail to : list) {
                        FactoryReportBean bean = new FactoryReportBean();
                        bean.setSeq(String.valueOf(intRunningNo++));
                        bean.setDetail(to.getFactoryRealId().getManutransporterDetailId().getManufacturingId().getManufacturingDesc());
                        Double volumn = 0.0, priceUnit = 0.0;
                        volumn = null != to.getFactoryRealId().getVolumeReal() ? to.getFactoryRealId().getVolumeReal() : 0.0;
                        priceUnit = null != to.getFactoryRealId().getManutransporterDetailId().getManufacturingId().getUnitPrice() ? to.getFactoryRealId().getManutransporterDetailId().getManufacturingId().getUnitPrice() : 0.0;

                        bean.setVolumn(NumberUtils.numberFormat(volumn, "#,##0.00"));
                        bean.setUnit(null != to.getFactoryRealId().getManutransporterDetailId().getUnit() ? to.getFactoryRealId().getManutransporterDetailId().getUnit() : "");
                        bean.setPriceUnit(NumberUtils.numberFormat(priceUnit, "#,##0.00"));
                        bean.setPriceTotal(NumberUtils.numberFormat(volumn * priceUnit, "#,##0.00"));

                        reportList.add(bean);
                    }
                    reportList_main.add(new FactoryReportBean("", "", "", "", "", "", "", "", "", ""));
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

                    map.put("documentno", rpt_sysManutransporter.getDocumentNo());
                    map.put("contractor_name", rpt_sysManutransporter.getContractorId().getContractorNameTh());
                    map.put("contractor_address", rpt_sysManutransporter.getContractorId().getContractorAddress());
                    map.put("send_date", DateTimeUtil.cvtDateForShow(rpt_sysManutransporter.getFactoryDate(), "dd/MM/yyyy", new Locale("th", "TH")));
                    map.put("taxid", null != rpt_sysManutransporter.getContractorId().getTaxId() ? rpt_sysManutransporter.getContractorId().getTaxId() : "-");
                    map.put("remark", StringUtils.isNotBlank(rpt_sysManutransporter.getRemark()) ? rpt_sysManutransporter.getRemark() : "...........................................................................................................................................");

                    map.put("total", NumberUtils.numberFormat(rpt_sysManutransporter.getFacTotal(), "#,##0.00"));
                    map.put("total_vat", NumberUtils.numberFormat(rpt_sysManutransporter.getFacVat(), "#,##0.00"));
                    map.put("total_volume", NumberUtils.numberFormat(rpt_sysManutransporter.getFacVolume(), "#,##0.00"));
                    map.put("total_divide_equipment", NumberUtils.numberFormat(rpt_sysManutransporter.getFacDivideEquipment(), "#,##0.00"));
                    map.put("total_ream", NumberUtils.numberFormat(rpt_sysManutransporter.getFacReam(), "#,##0.00"));
                    map.put("total_net", NumberUtils.numberFormat(rpt_sysManutransporter.getFacNet(), "#,##0.00"));
                    map.put("price_char", (rpt_sysManutransporter.getFacNet() == 0.0 ? "" : new ThaiBaht().getText(rpt_sysManutransporter.getFacNet())));

                    map.put("reportCode", "P104");
                    JasperPrint print= report.exportSubReport_Template_mearge("template.jpg","p105", new String[]{"P104Report","P104SubReport"}, "P104", map, reportList_);
                    jasperPrintList.add(print);
                    
                    //add print form
//                    SysPrintBilling sysPrintBilling =new SysPrintBilling();
//                    sysPrintBilling.setBillingId(sysBilling);
//                    sysPrintBilling.setUserId(userInfo.getAdminUser());
//                    sysPrintBilling.setCreatedBy(userInfo.getAdminUser().getUsername());
//                    sysPrintBilling.setCreatedDt(DateTimeUtil.getSystemDate());
//                    billingFacade.createSysPrintBilling(sysPrintBilling);

             }
             if(!printSelected.isEmpty()){
                String pdfCode="P104";
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
   
   
    
      public String convertPriceToString(Double totalprice){
        if(totalprice==0.0){
            return "";
        }else{
            return new ThaiBaht().getText(totalprice);
        }
    }
      
     public void calculrateVat() {
      //  checkTotalPrice();
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
   

    public SysContractor getContractor_find() {
        return contractor_find;
    }

    public void setContractor_find(SysContractor contractor_find) {
        this.contractor_find = contractor_find;
    }


    public SysWorkunit getWorkunit_find() {
        return workunit_find;
    }

    public void setWorkunit_find(SysWorkunit workunit_find) {
        this.workunit_find = workunit_find;
    }

    public SysWorkunit getWorkunit_selected() {
        return workunit_selected;
    }

    public void setWorkunit_selected(SysWorkunit workunit_selected) {
        this.workunit_selected = workunit_selected;
    }

    public SysForeman getForeman_find() {
        return foreman_find;
    }

    public void setForeman_find(SysForeman foreman_find) {
        this.foreman_find = foreman_find;
    }

    public SysForeman getForeman_selected() {
        return foreman_selected;
    }

    public void setForeman_selected(SysForeman foreman_selected) {
        this.foreman_selected = foreman_selected;
    }

    public List<SysManufactoryReal> getManufactoryRealSelected() {
        return manufactoryRealSelected;
    }

    public void setManufactoryRealSelected(List<SysManufactoryReal> manufactoryRealSelected) {
        this.manufactoryRealSelected = manufactoryRealSelected;
    }

    public List<SysPrepareTransport> getItems() {
        return items;
    }

    public void setItems(List<SysPrepareTransport> items) {
        this.items = items;
    }

    public SysPrepareTransport getSelected() {
        return selected;
    }

    public void setSelected(SysPrepareTransport selected) {
        this.selected = selected;
    }

    public SysPrepareTransportDetail getPrepareTransport_selected() {
        return prepareTransport_selected;
    }

    public void setPrepareTransport_selected(SysPrepareTransportDetail prepareTransport_selected) {
        this.prepareTransport_selected = prepareTransport_selected;
    }

    public List<SysPrepareTransport> getPrintSelected() {
        return printSelected;
    }

    public void setPrintSelected(List<SysPrepareTransport> printSelected) {
        this.printSelected = printSelected;
    }

    public SysManufactory getManufactory_selected() {
        return manufactory_selected;
    }

    public void setManufactory_selected(SysManufactory manufactory_selected) {
        this.manufactory_selected = manufactory_selected;
    }

    public List<SysManufactoryReal> getManufactoryReal_items_dialog() {
        return manufactoryReal_items_dialog;
    }

    public void setManufactoryReal_items_dialog(List<SysManufactoryReal> manufactoryReal_items_dialog) {
        this.manufactoryReal_items_dialog = manufactoryReal_items_dialog;
    }


   
}