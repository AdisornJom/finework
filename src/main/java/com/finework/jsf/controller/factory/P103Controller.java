package com.finework.jsf.controller.factory;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysManufactory;
import com.finework.core.ejb.entity.SysManufactoryDetail;
import com.finework.core.ejb.entity.SysManufacturing;
import com.finework.core.util.Constants;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.ThaiBaht;
import com.finework.ejb.facade.ContractorFacade;
import com.finework.ejb.facade.ManufactoryFacade;
import com.finework.ejb.facade.StockFacade;
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

@ManagedBean(name = P103Controller.CONTROLLER_NAME)
@SessionScoped
public class P103Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(P103Controller.class);
    public static final String CONTROLLER_NAME = "p103Controller";
    
    @Inject
    private ManufactoryFacade manufactoryFacade;
    @Inject
    private ContractorFacade contractorFacade;
    @Inject
    private StockFacade stockFacade;
    @Inject
    private UserInfoController userInfo;
    @Inject
    private SequenceController sequence;
    
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
            if (null==selected.getSysManufactoryDetailList() || selected.getSysManufactoryDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            //insert Manufactory
            selected.setContractorId(contractor_selected);
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
            
            //update Billing
            selected.setSysManufactoryDetailList(detal_edit);
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate()); 
            
            manufactoryFacade.editSysManufactory(selected);
         
            clearData();
            clearDatatTotal();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("factory/p103/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    @Override
    public void delete() {
        try {
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
     
   //===== start  Dialog=========  
      
    public void addDetail(){
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
             
              //is match
             List<Integer> sysManufactory_list=new ArrayList();
             for(SysManufactoryDetail sysManufactoryDetail:selected.getSysManufactoryDetailList()){
                 sysManufactory_list.add(sysManufactoryDetail.getManufacturingId().getManufacturingId());
             }

             if (!sysManufactory_list.contains(manufacturing_selected.getManufacturingId())) {
                 facDetail_selected.setManufacturingId(manufacturing_selected);
                 selected.getSysManufactoryDetailList().add(facDetail_selected);

                 sysManufactory_list.add(manufacturing_selected.getManufacturingId());
             }
             
             
             checkTotalPrice();
             clearData_sysManufactoryDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    
     public void clearData_sysManufactoryDetail(){
        facDetail_selected = new SysManufactoryDetail();
        manufacturing_selected=new SysManufacturing();
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

 
 //End Auto Complete==========================================================================    
    
    
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

   
}