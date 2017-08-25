package com.finework.jsf.controller.transporter;

import com.finework.core.ejb.entity.SysExpensesManufactoryDeduction;
import com.finework.core.ejb.entity.SysTransportStaff;
import com.finework.core.ejb.entity.SysTransportStaffSpecialDetail;
import com.finework.core.ejb.entity.SysTransportStaffSpecial;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.ThaiBaht;
import com.finework.ejb.facade.ContractorFacade;
import com.finework.ejb.facade.ManufactoryFacade;
import com.finework.ejb.facade.OrganizationFacade;
import com.finework.ejb.facade.StockFacade;
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
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Adisorn Jomjanyong
 */

@ManagedBean(name = T108Controller.CONTROLLER_NAME)
@SessionScoped
public class T108Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(T108Controller.class);
    public static final String CONTROLLER_NAME = "t108Controller";
    
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
    private TransporterFacade transporterFacade;
    
  //  private LazyDataModel<SysTransportStaff> lazyManufactoryModel;
    
    //
    private List<SysTransportStaffSpecial> items;
    private SysTransportStaffSpecial selected;
    private List<SysTransportStaffSpecial> printSelected;

    //detial 
    private SysTransportStaffSpecialDetail specialDetail_selected;
    //dialog
    private List<SysTransportStaffSpecialDetail> sysTransportationSpecialDetailList;
    private  SysTransportStaffSpecialDetail specialDetail_dialog;
    
    
    //find criteria main
    private String tpstaff_name;
    private SysTransportStaff tpstaff_find;
    private Date startDate;
    private Date toDate;
    
    //variable
    private Double total=0.0;
   
    @PostConstruct
    @Override
    public void init() {
        sysTransportationSpecialDetailList=new ArrayList();
         
        search();
    }
    
    public void next(String path) {
        NaviController nav = (NaviController) JsfUtil.getManagedBeanValue(NaviController.CONTROLLER_NAME);
        nav.next(path);
    }
    
    public void prepareCreate(String page) {
        selected = new SysTransportStaffSpecial();
        specialDetail_selected=new SysTransportStaffSpecialDetail();
        next(page);
    }

    public void prepareEdit(String page) {
        specialDetail_selected=new SysTransportStaffSpecialDetail();
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
    }
     
    public void clearData(){
         selected = new SysTransportStaffSpecial();
    }
   
    
    @Override
    public void create() {
        try {
            if (null == selected.getTransportstaffId()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อเล่นพนักงาน"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            if (null == selected.getSpecialtpDate()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่ทำรายการ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            if (null == selected.getSysTransportStaffSpecialDetailList() || selected.getSysTransportStaffSpecialDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            //insert exp transporter
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());

            //insertDetail
            List<SysTransportStaffSpecialDetail> detal_add = new ArrayList();
            for (SysTransportStaffSpecialDetail sysManufactoryDetail_ : selected.getSysTransportStaffSpecialDetailList()) {
                sysManufactoryDetail_.setSpecialtpdetailId(null);//auto generate id on db
                sysManufactoryDetail_.setSpecialtpId(selected);
                detal_add.add(sysManufactoryDetail_);
            }

            selected.setSysTransportStaffSpecialDetailList(detal_add);

            transporterFacade.createSysTransportStaffSpecial(selected);

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
             if (null == selected.getSpecialtpDate()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่ทำรายการ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
             }
            
             if (null==selected.getSysTransportStaffSpecialDetailList() || selected.getSysTransportStaffSpecialDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }      

            //deleteDetail
             transporterFacade.deleteTransportationExpIdOnDetail(selected.getSpecialtpId());
             
            //insertDetail
            List<SysTransportStaffSpecialDetail> detal_edit=new ArrayList();
            for(SysTransportStaffSpecialDetail sysTransportationSpecialDetail_:selected.getSysTransportStaffSpecialDetailList()){
                sysTransportationSpecialDetail_.setSpecialtpdetailId(null);//auto generate id on db
                sysTransportationSpecialDetail_.setSpecialtpId(selected);
                detal_edit.add(sysTransportationSpecialDetail_);
            }
            
            selected.setSysTransportStaffSpecialDetailList(detal_edit);
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate()); 
            
            transporterFacade.editSysTransportStaffSpecial(selected);
         
            clearData();
            clearDatatTotal();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("transporter/t108/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }


    @Override
    public void delete() {
        try {
            transporterFacade.deleteSysTransportStaffSpecial(selected);
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
                
              items=transporterFacade.findSysTransportStaffSpecialListByCriteria(tpstaff_find, startDate, toDate);
              for(SysTransportStaffSpecial special:items){
                 Double total_=0.0;
                 for(SysTransportStaffSpecialDetail specialDetail:special.getSysTransportStaffSpecialDetailList()){
                     total_+=(null !=specialDetail.getAmount()?specialDetail.getAmount():0.0);
                 }
                 special.setTotalSpcial(total_);
              }
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
        if (specialDetail_selected.getSpecialDesc() == null) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายการพิเศษ"));
            RequestContext.getCurrentInstance().scrollTo("dialog");
            return;
        }
        if (specialDetail_selected.getAmount() == null || specialDetail_selected.getAmount() <= 0) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "จำนวนเงิน"));
            RequestContext.getCurrentInstance().scrollTo("dialog");
            return;
        }
        
        
        specialDetail_dialog = new SysTransportStaffSpecialDetail();
        Random rand = new Random();
        int n = rand.nextInt(500) + 1;
        specialDetail_dialog.setSpecialtpdetailId(n);
        specialDetail_dialog.setSpecialtpId(selected);
        specialDetail_dialog.setSpecialDesc(specialDetail_selected.getSpecialDesc());
        specialDetail_dialog.setAmount(specialDetail_selected.getAmount());

        sysTransportationSpecialDetailList.add(specialDetail_dialog);
        
        specialDetail_selected = new SysTransportStaffSpecialDetail();
    }
     public void addDetail(){
         try {
             if (selected.getSysTransportStaffSpecialDetailList() == null) {
                 selected.setSysTransportStaffSpecialDetailList(new ArrayList<SysTransportStaffSpecialDetail>());
             }

             for (SysTransportStaffSpecialDetail tpExpDetail : sysTransportationSpecialDetailList) {
                 selected.getSysTransportStaffSpecialDetailList().add(tpExpDetail);
             }

             checkTotalPrice();
             clearData_sysTransportationSpecialDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }    
    
     public void clearData_sysTransportationSpecialDetail(){
        specialDetail_selected = new SysTransportStaffSpecialDetail();
        sysTransportationSpecialDetailList=new ArrayList();
    }
     
    public void deleteDetail(){
        try {
             //delete  
             selected.getSysTransportStaffSpecialDetailList().remove(specialDetail_selected);
             checkTotalPrice();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    public void deleteDialogDetail(){
        try {
             sysTransportationSpecialDetailList.remove(specialDetail_selected);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    } 
    
    public void checkTotalPrice(){
        this.total=0.0;

        Double total_ = 0.0;
        if (null != selected.getSysTransportStaffSpecialDetailList()) {
            for (SysTransportStaffSpecialDetail sysdetail : selected.getSysTransportStaffSpecialDetailList()) {
                 if(null !=sysdetail.getAmount() && sysdetail.getAmount()>0){
                     total_ +=sysdetail.getAmount();
                 }
            }
             this.total = total_;
        } else {
           clearDatatTotal();
        }
    }
    
   //===== end  Dialog=========   
    
    public void prepareEdit() {
        specialDetail_selected=new SysTransportStaffSpecialDetail();
    } 
   public void handleKeyEvent(){}
   
   
 //Auto Complete==========================================================================  
    //Auto transporter_staff
    public List<SysTransportStaff> completeTransportStaff(String query) {
         List<SysTransportStaff> filteredSysTransportStaff = new ArrayList<>();
       try {
            List<SysTransportStaff> allTransportStaffs = transporterFacade.findSysTransportStaffList();
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
    
     //Auto complete expenses Deduction
     public List<SysExpensesManufactoryDeduction> completeExpensesDeduction(String query) {
         List<SysExpensesManufactoryDeduction> filteredExpenDeduction = new ArrayList<>();
       try {
            List<SysExpensesManufactoryDeduction> allExpensesDeduction = manufactoryFacade.findSysExpensesManufactoryDeductionList();
            for (SysExpensesManufactoryDeduction sysExpensesDeduction :allExpensesDeduction) {
               if(sysExpensesDeduction.getDeductionDesc()!=null && sysExpensesDeduction.getDeductionDesc().length()>0){
                if(sysExpensesDeduction.getDeductionDesc().toLowerCase().startsWith(query)) {
                    filteredExpenDeduction.add(sysExpensesDeduction);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredExpenDeduction;
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
    

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

    public StockFacade getStockFacade() {
        return stockFacade;
    }

    public void setStockFacade(StockFacade stockFacade) {
        this.stockFacade = stockFacade;
    }

    public List<SysTransportStaffSpecial> getItems() {
        return items;
    }

    public void setItems(List<SysTransportStaffSpecial> items) {
        this.items = items;
    }

    public SysTransportStaffSpecial getSelected() {
        return selected;
    }

    public void setSelected(SysTransportStaffSpecial selected) {
        this.selected = selected;
    }

    public List<SysTransportStaffSpecial> getPrintSelected() {
        return printSelected;
    }

    public void setPrintSelected(List<SysTransportStaffSpecial> printSelected) {
        this.printSelected = printSelected;
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

    public SysTransportStaffSpecialDetail getSpecialDetail_selected() {
        return specialDetail_selected;
    }

    public void setSpecialDetail_selected(SysTransportStaffSpecialDetail specialDetail_selected) {
        this.specialDetail_selected = specialDetail_selected;
    }

    public List<SysTransportStaffSpecialDetail> getSysTransportationSpecialDetailList() {
        return sysTransportationSpecialDetailList;
    }

    public void setSysTransportationSpecialDetailList(List<SysTransportStaffSpecialDetail> sysTransportationSpecialDetailList) {
        this.sysTransportationSpecialDetailList = sysTransportationSpecialDetailList;
    }

    public SysTransportStaffSpecialDetail getSpecialDetail_dialog() {
        return specialDetail_dialog;
    }

    public void setSpecialDetail_dialog(SysTransportStaffSpecialDetail specialDetail_dialog) {
        this.specialDetail_dialog = specialDetail_dialog;
    }

    public String getTpstaff_name() {
        return tpstaff_name;
    }

    public void setTpstaff_name(String tpstaff_name) {
        this.tpstaff_name = tpstaff_name;
    }

    public SysTransportStaff getTpstaff_find() {
        return tpstaff_find;
    }

    public void setTpstaff_find(SysTransportStaff tpstaff_find) {
        this.tpstaff_find = tpstaff_find;
    }

}