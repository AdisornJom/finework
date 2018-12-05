package com.finework.jsf.controller.job;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysCreatejob;
import com.finework.core.ejb.entity.SysCreatejobDetail;
import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysManufactory;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.core.util.Constants;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.ThaiBaht;
import com.finework.ejb.facade.CreateJobFacade;
import com.finework.ejb.facade.CustomerFacade;
import com.finework.ejb.facade.ForemanFacade;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.SequenceController;
import com.finework.jsf.common.UserInfoController;
import com.finework.jsf.model.LazyCreateJobDataModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Adisorn Jomjanyong
 */

@ManagedBean(name = J101Controller.CONTROLLER_NAME)
@SessionScoped
public class J101Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(J101Controller.class);
    public static final String CONTROLLER_NAME = "j101Controller";
    
    @Inject
    private CreateJobFacade createJobFacade;
    @Inject
    private CustomerFacade customerFacade;
    @Inject
    private UserInfoController userInfo;
    @Inject
    private SequenceController sequence;
    @Inject
    private ForemanFacade foremanFacade;
    
    private LazyDataModel<SysCreatejob> lazyCreateJobModel;
    
    //
    private List<SysCreatejob> items;
    private SysCreatejob selected;

    //detial 
    private SysCreatejobDetail createJob_selected;
    
    
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
    
   
    @PostConstruct
    @Override
    public void init() {
        //startDate
        Calendar cal = new GregorianCalendar(Locale.US);
        cal.setTime(DateTimeUtil.getSystemDate());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        startDate = cal.getTime();
        //toDate
        toDate = DateTimeUtil.getSystemDate();
        
        search();
    }
    
    public void next(String path) {
        NaviController nav = (NaviController) JsfUtil.getManagedBeanValue(NaviController.CONTROLLER_NAME);
        nav.next(path);
    }
    
    public void prepareCreate(String page) {
        selected = new SysCreatejob();
        next(page);
    }

    public void prepareEdit(String page) {
        //this.foreman_selected=selected.getForemanId();
        //this.workunit_selected=selected.getWorkunitId();      
        next(page);
    }
    public void cancel(String path) {
        clearData();
        next(path);
    }
    public void backIndex(String path) {
        init();
        next(path);
    }
     
    public void clearData(){
         selected = new SysCreatejob();
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
            if (null==selected.getJobDate()){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่ทำรายการ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (null==selected.getJobStartdate()){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่เริ่มปฏิบัติงาน"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (null==selected.getJobEnddate()){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่สิ้นสุดปฺฏิบัติงาน"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
        /*   
            if (null==selected.getSysCreatejobDetailList() || selected.getSysCreatejobDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            */

            //insert Manutransporter
            selected.setForemanId(foreman_selected);
            selected.setWorkunitId(workunit_selected);
            selected.setJobType(Constants.CREATEJOB_UPLOAD_IMG);
            selected.setStatus(Constants.COMMON_OPEN_STATUS);
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            
            /*
            //insertDetail
            List<SysCreatejobDetail> detal_add=new ArrayList();
            for(SysCreatejobDetail sysCreatejobDetail_:selected.getSysCreatejobDetailList()){
                sysCreatejobDetail_.setId(null);//auto generate id on db
                sysCreatejobDetail_.setJobId(selected);
                detal_add.add(sysCreatejobDetail_);
            }

            selected.setSysCreatejobDetailList(detal_add);
            */

            runningNoCustomer();
            
            createJobFacade.createSysCreateJob(selected);
            
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
            if (null==selected.getJobStartdate()){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่เริ่มปฏิบัติงาน"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (null==selected.getJobEnddate()){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่สิ้นสุดปฺฏิบัติงาน"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
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
             
            
            if (null==selected.getSysCreatejobDetailList()|| selected.getSysCreatejobDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }  
            */
/*
            //deleteDetail
             createJobFacade.deletePrepareTpIdOnDetail(selected.getJobId());
             
            //insertDetail
            List<SysCreatejobDetail> detal_edit=new ArrayList();
            for(SysCreatejobDetail sysCreatejobDetail_:selected.getSysCreatejobDetailList()){
                sysCreatejobDetail_.setId(null);//auto generate id on db
                sysCreatejobDetail_.setJobId(selected);
                detal_edit.add(sysCreatejobDetail_);
            }
            
            
            selected.setSysCreatejobDetailList(detal_edit);*/
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate()); 
            
            createJobFacade.editSysCreateJob(selected);
            
            clearData();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("job/j101/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    @Override
    public void delete() {
        try {
             
//            createJobFacade.deleteSysPrepareTransport(selected);     
//            search();
//            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4002"));
            selected.setStatus(Constants.COMMON_CLOSE_STATUS);
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            createJobFacade.editSysCreateJob(selected);
            //refresh data
            search();
            next("job/j101/index");
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
                
             // items=createJobFacade.findSysPrepareTransportListByCriteria(foreman_find,documentno,workunit_find,Constants.COMMON_OPEN_STATUS, startDate, toDate);
              
             lazyCreateJobModel = new LazyCreateJobDataModel(createJobFacade,foreman_find,documentno,workunit_find,Constants.COMMON_OPEN_STATUS, startDate, toDate);
             DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("listForm:createJobTable");
             dataTable.setFirst(0);
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
       /*  try {
             if (selected.getSysCreatejobDetailList()== null) {
                 selected.setSysCreatejobDetailList(new ArrayList());
             }

              //is match
             List<Integer> sysFactory_list = new ArrayList();
             int seq = 0;
             for (SysCreatejobDetail sysPrepareTransportDetail : selected.getSysCreatejobDetailList()) {
                 sysFactory_list.add(sysPrepareTransportDetail.getId());
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
        }*/
    }    
   
    
     public void clearData_syspreparetransporterDetail(){
      /*  createJob_selected = new SysPrepareTransportDetail();
        manufactory_selected = new SysManufactory();
        manufactoryReal_items_dialog = new ArrayList();
        manufactoryRealSelected=  new ArrayList();
         */
    }
     
    public void deleteDetail(){
        try {
             //delete total 
             selected.getSysCreatejobDetailList().remove(createJob_selected);
            // checkTotalPrice();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
 
   //===== end  Dialog=========   
    
    public void prepareEdit() {
       // manufactoryRealSelected=new SysManufactoryReal();
    } 
   public void handleKeyEvent(){}
   
   
    public void runningNoCustomer() {
        String sequence_no=sequence.updateRunningNO(1,Constants.SEQUNCE_NO_CREATE_JOB,"yyMM");
        this.selected.setDocumentNo(sequence_no);
    } 
   
 //Auto Complete==========================================================================  
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
   
  
   
 //End Auto Complete==========================================================================    
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

    public LazyDataModel<SysCreatejob> getLazyCreateJobModel() {
        return lazyCreateJobModel;
    }

    public void setLazyCreateJobModel(LazyDataModel<SysCreatejob> lazyCreateJobModel) {
        this.lazyCreateJobModel = lazyCreateJobModel;
    }

    public List<SysCreatejob> getItems() {
        return items;
    }

    public void setItems(List<SysCreatejob> items) {
        this.items = items;
    }

    public SysCreatejob getSelected() {
        return selected;
    }

    public void setSelected(SysCreatejob selected) {
        this.selected = selected;
    }

    public SysCreatejobDetail getCreateJob_selected() {
        return createJob_selected;
    }

    public void setCreateJob_selected(SysCreatejobDetail createJob_selected) {
        this.createJob_selected = createJob_selected;
    }

}