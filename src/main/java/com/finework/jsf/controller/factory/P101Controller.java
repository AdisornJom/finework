package com.finework.jsf.controller.factory;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysForeman;
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
import com.finework.ejb.facade.ForemanFacade;
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
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Adisorn Jomjanyong
 */

@ManagedBean(name = P101Controller.CONTROLLER_NAME)
@SessionScoped
public class P101Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(P101Controller.class);
    public static final String CONTROLLER_NAME = "p101Controller";
    
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
    @Inject
    private ForemanFacade foremanFacade;
    
    private LazyDataModel<SysManufactory> lazyManufactoryModel;
    
    //
    private List<SysManufactory> items;
    private SysManufactory selected;
    private List<SysManufactory> printSelected;

    //detial 
    private SysManufactoryDetail facDetail_selected;
    //dialog
    private List<SysManufactoryDetail> sysManufactoryDetailList;
    private  SysManufactoryDetail facDetail_dialog;
    
    
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
    private SysContractor contractor_selected;
    private SysManufacturing manufacturing_selected;
    private SysWorkunit workunit_selected;
    private SysForeman foreman_selected;
    

   
    @PostConstruct
    @Override
    public void init() {
        sysManufactoryDetailList=new ArrayList();
        
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
        this.foreman_selected=selected.getForemanId();
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
         foreman_selected=new SysForeman();
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
            if (null==foreman_selected) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อเล่นผู้ควบคุมงาน(โฟร์แมน)"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (null==selected.getSysManufactoryDetailList() || selected.getSysManufactoryDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            //insert Manufactory
            selected.setContractorId(contractor_selected);
            selected.setForemanId(foreman_selected);
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            
            //insertDetail
            List<SysManufactoryDetail> detal_add=new ArrayList();
            for(SysManufactoryDetail sysManufactoryDetail_:selected.getSysManufactoryDetailList()){
                sysManufactoryDetail_.setManufactoryDetailId(null);//auto generate id on db
                sysManufactoryDetail_.setFactoryId(selected);
                detal_add.add(sysManufactoryDetail_);
            }

            selected.setSysManufactoryDetailList(detal_add);

            runningNoCustomer();
            
            manufactoryFacade.createSysManufactory(selected);
         
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
            //check ว่า รายการการผลิตถูกนำไปกระทำไปหรือยัง
            int countFactoryReal=manufactoryFacade.findSysManufactoryRealListByFactoryId(selected.getFactoryId());
            if(countFactoryReal>0){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2025", "ใบงานเลยขที่นี้ ได้ถูกนำไป 'ปฏิบัติงาน' เรียบร้อยค่ะ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
             
            if("ADMIN".equals(userInfo.getAdminUser().getRoleId().getRoleName())){
                if (null==contractor_selected) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ผู้รับเหมา"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                    return;
                }
                if (null == foreman_selected) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อเล่นผู้ควบคุมงาน(โฟร์แมน)"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                    return;
                }
            }
            
            if (null==selected.getSysManufactoryDetailList() || selected.getSysManufactoryDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }      

            //deleteDetail
             manufactoryFacade.deleteManufactoryIdOnDetail(selected.getFactoryId());
             
            //insertDetail
            List<SysManufactoryDetail> detal_edit=new ArrayList();
            for(SysManufactoryDetail sysManufactoryDetail_:selected.getSysManufactoryDetailList()){
                sysManufactoryDetail_.setManufactoryDetailId(null);//auto generate id on db
                sysManufactoryDetail_.setFactoryId(selected);
                detal_edit.add(sysManufactoryDetail_);
            }
            
            //update Factory
            if("ADMIN".equals(userInfo.getAdminUser().getRoleId().getRoleName())){
              selected.setContractorId(contractor_selected);    
              selected.setForemanId(foreman_selected);
            }
            selected.setSysManufactoryDetailList(detal_edit);
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate()); 
            
            manufactoryFacade.editSysManufactory(selected);
         
            clearData();
            clearDatatTotal();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("factory/p101/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    @Override
    public void delete() {
        try {
             //check ว่า รายการการผลิตถูกนำไปกระทำไปหรือยัง
            int countFactoryReal=manufactoryFacade.findSysManufactoryRealListByFactoryId(selected.getFactoryId());
            if(countFactoryReal>0){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2025", "ใบงานเลยขที่นี้ ได้ถูกนำไป 'ปฏิบัติงาน' เรียบร้อยค่ะ"));
                RequestContext.getCurrentInstance().scrollTo("delete_msg");
                return;
            }
             
            manufactoryFacade.deleteSysManufactory(selected);
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
                
              items=manufactoryFacade.findSysManufactoryListByCriteria(documentno,workunit_find, contractor_find,null, startDate, toDate);
              
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
    public void addDetailDialog(){
        
        //validate field iteam 
        if (manufacturing_selected == null) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายการผลิต"));
            RequestContext.getCurrentInstance().scrollTo("dialog");
            return;
        }
        if (facDetail_selected.getVolumeTarget() == null || facDetail_selected.getVolumeTarget() <= 0) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "จำนวนที่สั่งผลิต"));
            RequestContext.getCurrentInstance().scrollTo("dialog");
            return;
        }
        
        
        facDetail_dialog = new SysManufactoryDetail();
        Random rand = new Random();
        int n = rand.nextInt(500) + 1;
        facDetail_dialog.setManufactoryDetailId(n);
        facDetail_dialog.setManufacturingId(manufacturing_selected);
        facDetail_dialog.setWorkunitId(workunit_selected);
        facDetail_dialog.setVolumeTarget(facDetail_selected.getVolumeTarget());
        facDetail_dialog.setPlot(facDetail_selected.getPlot());
        facDetail_dialog.setLength(facDetail_selected.getLength());
        facDetail_dialog.setUnit(facDetail_selected.getUnit());

        sysManufactoryDetailList.add(facDetail_dialog);
    }
     public void addDetail(){
         try {
             if (selected.getSysManufactoryDetailList() == null) {
                 selected.setSysManufactoryDetailList(new ArrayList<SysManufactoryDetail>());
             }

             for (SysManufactoryDetail sysManufactoryDetail : sysManufactoryDetailList) {
                 selected.getSysManufactoryDetailList().add(sysManufactoryDetail);
             }

             //checkTotalPrice();
             clearData_sysManufactoryDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }    
    public void addDetail_old(){
         try {
                        
             //validate field iteam 
            if (manufacturing_selected==null
                    ||facDetail_selected.getVolumeTarget()==null) {
                return;
            }
            if (facDetail_selected.getVolumeTarget()<=0 ) {
               return;
            }

            if (selected.getSysManufactoryDetailList() == null) 
                 selected.setSysManufactoryDetailList(new ArrayList<SysManufactoryDetail>());
             
//              //is match
//             List<Integer> sysManufactory_list=new ArrayList();
//             for(SysManufactoryDetail sysManufactoryDetail:selected.getSysManufactoryDetailList()){
//                 sysManufactory_list.add(sysManufactoryDetail.getManufacturingId().getManufacturingId());
//             }
//
//             if (!sysManufactory_list.contains(manufacturing_selected.getManufacturingId())) {
//                 facDetail_selected.setManufacturingId(manufacturing_selected);
//                 facDetail_selected.setWorkunitId(workunit_selected);
//                 selected.getSysManufactoryDetailList().add(facDetail_selected);
//
//                 sysManufactory_list.add(manufacturing_selected.getManufacturingId());
//             }
                 Random rand = new Random();
                 int  n = rand.nextInt(500) + 1;
                 facDetail_selected.setManufactoryDetailId(n);
                 facDetail_selected.setManufacturingId(manufacturing_selected);
                 facDetail_selected.setWorkunitId(workunit_selected);
                 selected.getSysManufactoryDetailList().add(facDetail_selected);
             
             
            // checkTotalPrice();
             clearData_sysManufactoryDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    
     public void clearData_sysManufactoryDetail(){
        facDetail_selected = new SysManufactoryDetail();
        manufacturing_selected=new SysManufacturing();
        workunit_selected=new SysWorkunit();
        sysManufactoryDetailList=new ArrayList();
    }
     
    public void deleteDetail(){
        try {
             //delete total 
             selected.getSysManufactoryDetailList().remove(facDetail_selected);
            // checkTotalPrice();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    public void deleteDialogDetail(){
        try {
             //delete total 
             sysManufactoryDetailList.remove(facDetail_selected);
            // checkTotalPrice();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
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
    }
    
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
        facDetail_selected=new SysManufactoryDetail();
    } 
   public void handleKeyEvent(){}
   
   
    public void runningNoCustomer() {
        String sequence_no=sequence.updateRunningNO(1,Constants.SEQUNCE_NO_MANUFACTORY,"yyMM");
        this.selected.setDocumentno(sequence_no);
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
    
    //Auto complete Contrator
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
            
          //clear
          facDetail_selected = new SysManufactoryDetail();
          manufacturing_selected=new SysManufacturing();
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
   /*
    public void printPdfMuti(){
         try {
             List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
             Collections.sort(printSelected, new SysPaymentManufactory());
             for (SysPaymentManufactory sysPaymentManufactory : printSelected) {
                    ReportUtil report = new ReportUtil();
                    List reportList_ = new ArrayList<>();
                    List<FactoryReportBean> reportList_main = new ArrayList<>();
                    List<FactoryReportBean> reportList = new ArrayList<>();
                    SysPaymentManufactory rpt_sysManufactory = paymentManufactoryFacade.findByPK(sysPaymentManufactory.getPaymentFactoryId());

                    int intRunningNo = 1;
                    List<SysPaymentManufactoryDetail> list = rpt_sysManufactory.getSysPaymentManufactoryDetailList();
                    for (SysPaymentManufactoryDetail to : list) {
                        FactoryReportBean bean = new FactoryReportBean();
                        bean.setSeq(String.valueOf(intRunningNo++));
                        bean.setDetail(to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getManufacturingDesc());
                        Double volumn = 0.0, priceUnit = 0.0;
                        volumn = null != to.getFactoryRealId().getVolumeReal() ? to.getFactoryRealId().getVolumeReal() : 0.0;
                        priceUnit = null != to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice() ? to.getFactoryRealId().getManufactoryDetailId().getManufacturingId().getUnitPrice() : 0.0;

                        bean.setVolumn(NumberUtils.numberFormat(volumn, "#,##0.00"));
                        bean.setUnit(null != to.getFactoryRealId().getManufactoryDetailId().getUnit() ? to.getFactoryRealId().getManufactoryDetailId().getUnit() : "");
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

                    map.put("documentno", rpt_sysManufactory.getDocumentNo());
                    map.put("contractor_name", rpt_sysManufactory.getContractorId().getContractorNameTh());
                    map.put("contractor_address", rpt_sysManufactory.getContractorId().getContractorAddress());
                    map.put("send_date", DateTimeUtil.cvtDateForShow(rpt_sysManufactory.getFactoryDate(), "dd/MM/yyyy", new Locale("th", "TH")));
                    map.put("taxid", null != rpt_sysManufactory.getContractorId().getTaxId() ? rpt_sysManufactory.getContractorId().getTaxId() : "-");
                    map.put("remark", StringUtils.isNotBlank(rpt_sysManufactory.getRemark()) ? rpt_sysManufactory.getRemark() : "...........................................................................................................................................");

                    map.put("total", NumberUtils.numberFormat(rpt_sysManufactory.getFacTotal(), "#,##0.00"));
                    map.put("total_vat", NumberUtils.numberFormat(rpt_sysManufactory.getFacVat(), "#,##0.00"));
                    map.put("total_volume", NumberUtils.numberFormat(rpt_sysManufactory.getFacVolume(), "#,##0.00"));
                    map.put("total_divide_equipment", NumberUtils.numberFormat(rpt_sysManufactory.getFacDivideEquipment(), "#,##0.00"));
                    map.put("total_ream", NumberUtils.numberFormat(rpt_sysManufactory.getFacReam(), "#,##0.00"));
                    map.put("total_net", NumberUtils.numberFormat(rpt_sysManufactory.getFacNet(), "#,##0.00"));
                    map.put("price_char", (rpt_sysManufactory.getFacNet() == 0.0 ? "" : new ThaiBaht().getText(rpt_sysManufactory.getFacNet())));

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

    public SysManufactoryDetail getFacDetail_dialog() {
        return facDetail_dialog;
    }

    public void setFacDetail_dialog(SysManufactoryDetail facDetail_dialog) {
        this.facDetail_dialog = facDetail_dialog;
    }

    public List<SysManufactoryDetail> getSysManufactoryDetailList() {
        return sysManufactoryDetailList;
    }

    public void setSysManufactoryDetailList(List<SysManufactoryDetail> sysManufactoryDetailList) {
        this.sysManufactoryDetailList = sysManufactoryDetailList;
    }

   

   
}