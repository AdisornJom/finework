package com.finework.jsf.controller.transporter;

import com.finework.core.ejb.entity.SysExpensesManufactoryDeduction;
import com.finework.core.ejb.entity.SysTransportStaff;
import com.finework.core.ejb.entity.SysTransportationExpDetail;
import com.finework.core.ejb.entity.SysTranspostationExp;
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

@ManagedBean(name = T106Controller.CONTROLLER_NAME)
@SessionScoped
public class T106Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(T106Controller.class);
    public static final String CONTROLLER_NAME = "t106Controller";
    
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
    private List<SysTranspostationExp> items;
    private SysTranspostationExp selected;
    private List<SysTranspostationExp> printSelected;

    //detial 
    private SysTransportationExpDetail expDetail_selected;
    //dialog
    private List<SysTransportationExpDetail> sysTransportationExpDetailList;
    private  SysTransportationExpDetail expDetail_dialog;
    
    
    //find criteria main
    private String tpstaff_name;
    private SysTransportStaff tpstaff_find;
    private Date startDate;
    private Date toDate;
    
    //auto complete
    private SysExpensesManufactoryDeduction expDeduction_selected;
    
    //variable
    private Double total=0.0;
   
    @PostConstruct
    @Override
    public void init() {
        sysTransportationExpDetailList=new ArrayList();
         
        search();
    }
    
    public void next(String path) {
        NaviController nav = (NaviController) JsfUtil.getManagedBeanValue(NaviController.CONTROLLER_NAME);
        nav.next(path);
    }
    
    public void prepareCreate(String page) {
        selected = new SysTranspostationExp();
        expDetail_selected=new SysTransportationExpDetail();
        next(page);
    }

    public void prepareEdit(String page) {
        expDetail_selected=new SysTransportationExpDetail();
        checkTotalPrice();
        next(page);
    }
    public void cancel(String path) {
        clearData();
        clearDatatTotal();
        init();
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
         selected = new SysTranspostationExp();
         expDeduction_selected =new SysExpensesManufactoryDeduction();
    }
   
    
    @Override
    public void create() {
        try {
            if (null == selected.getTransportstaffId()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อเล่นพนักงาน"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            if (null == selected.getExptpDate()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่ทำรายการ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            if (null == selected.getSysTransportationExpDetailList() || selected.getSysTransportationExpDetailList().isEmpty()) {
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
            List<SysTransportationExpDetail> detal_add = new ArrayList();
            for (SysTransportationExpDetail sysManufactoryDetail_ : selected.getSysTransportationExpDetailList()) {
                sysManufactoryDetail_.setExptpdetailId(null);//auto generate id on db
                sysManufactoryDetail_.setExptpId(selected);
                detal_add.add(sysManufactoryDetail_);
            }

            selected.setSysTransportationExpDetailList(detal_add);

            transporterFacade.createSysTranspostationExp(selected);

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
             if (null == selected.getExptpDate()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่ทำรายการ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
             }
            
             if (null==selected.getSysTransportationExpDetailList() || selected.getSysTransportationExpDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }      

            //deleteDetail
             transporterFacade.deleteTransportationExpIdOnDetail(selected.getExptpId());
             
            //insertDetail
            List<SysTransportationExpDetail> detal_edit=new ArrayList();
            for(SysTransportationExpDetail sysTransportationExpDetail_:selected.getSysTransportationExpDetailList()){
                sysTransportationExpDetail_.setExptpdetailId(null);//auto generate id on db
                sysTransportationExpDetail_.setExptpId(selected);
                detal_edit.add(sysTransportationExpDetail_);
            }
            
            selected.setSysTransportationExpDetailList(detal_edit);
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate()); 
            
            transporterFacade.editSysTranspostationExp(selected);
         
            clearData();
            clearDatatTotal();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("transporter/t106/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }


    @Override
    public void delete() {
        try {
            transporterFacade.deleteSysTranspostationExp(selected);
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
                
              items=transporterFacade.findSysTranspostationExpListByCriteria(tpstaff_find, startDate, toDate);
              for(SysTranspostationExp exp:items){
                 Double total_=0.0;
                 for(SysTransportationExpDetail expDetail:exp.getSysTransportationExpDetailList()){
                     total_+=(null !=expDetail.getAmount()?expDetail.getAmount():0.0);
                 }
                 exp.setTotalExp(total_);
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
        if (expDeduction_selected == null) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ประเภทค่าใช้จ่าย"));
            RequestContext.getCurrentInstance().scrollTo("dialog");
            return;
        }
        if (expDetail_selected.getAmount() == null || expDetail_selected.getAmount() <= 0) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "จำนวนเงิน"));
            RequestContext.getCurrentInstance().scrollTo("dialog");
            return;
        }
        
        
        expDetail_dialog = new SysTransportationExpDetail();
        Random rand = new Random();
        int n = rand.nextInt(500) + 1;
        expDetail_dialog.setExptpdetailId(n);
        expDetail_dialog.setExptpId(selected);
        expDetail_dialog.setDeductionId(expDeduction_selected);
        expDetail_dialog.setAmount(expDetail_selected.getAmount());

        sysTransportationExpDetailList.add(expDetail_dialog);
        
        expDetail_selected = new SysTransportationExpDetail();
        expDeduction_selected=new SysExpensesManufactoryDeduction();
    }
     public void addDetail(){
         try {
             if (selected.getSysTransportationExpDetailList() == null) {
                 selected.setSysTransportationExpDetailList(new ArrayList<SysTransportationExpDetail>());
             }

             for (SysTransportationExpDetail tpExpDetail : sysTransportationExpDetailList) {
                 selected.getSysTransportationExpDetailList().add(tpExpDetail);
             }

             checkTotalPrice();
             clearData_sysTransportationExpDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }    
    
     public void clearData_sysTransportationExpDetail(){
        expDetail_selected = new SysTransportationExpDetail();
        sysTransportationExpDetailList=new ArrayList();
        expDeduction_selected=new SysExpensesManufactoryDeduction();
    }
     
    public void deleteDetail(){
        try {
             //delete  
             selected.getSysTransportationExpDetailList().remove(expDetail_selected);
             checkTotalPrice();
             
             expDetail_selected = new SysTransportationExpDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    public void deleteDialogDetail(){
        try {
             sysTransportationExpDetailList.remove(expDetail_selected);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    } 
    
    public void checkTotalPrice(){
        this.total=0.0;

        Double total_ = 0.0;
        if (null != selected.getSysTransportationExpDetailList()) {
            for (SysTransportationExpDetail sysdetail : selected.getSysTransportationExpDetailList()) {
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
        expDetail_selected=new SysTransportationExpDetail();
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

    public List<SysTranspostationExp> getItems() {
        return items;
    }

    public void setItems(List<SysTranspostationExp> items) {
        this.items = items;
    }

    public SysTranspostationExp getSelected() {
        return selected;
    }

    public void setSelected(SysTranspostationExp selected) {
        this.selected = selected;
    }

    public List<SysTranspostationExp> getPrintSelected() {
        return printSelected;
    }

    public void setPrintSelected(List<SysTranspostationExp> printSelected) {
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

    public SysTransportationExpDetail getExpDetail_selected() {
        return expDetail_selected;
    }

    public void setExpDetail_selected(SysTransportationExpDetail expDetail_selected) {
        this.expDetail_selected = expDetail_selected;
    }

    public List<SysTransportationExpDetail> getSysTransportationExpDetailList() {
        return sysTransportationExpDetailList;
    }

    public void setSysTransportationExpDetailList(List<SysTransportationExpDetail> sysTransportationExpDetailList) {
        this.sysTransportationExpDetailList = sysTransportationExpDetailList;
    }

    
    public SysTransportationExpDetail getExpDetail_dialog() {
        return expDetail_dialog;
    }

    public void setExpDetail_dialog(SysTransportationExpDetail expDetail_dialog) {
        this.expDetail_dialog = expDetail_dialog;
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

    public UserInfoController getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoController userInfo) {
        this.userInfo = userInfo;
    }

    public SysExpensesManufactoryDeduction getExpDeduction_selected() {
        return expDeduction_selected;
    }

    public void setExpDeduction_selected(SysExpensesManufactoryDeduction expDeduction_selected) {
        this.expDeduction_selected = expDeduction_selected;
    }
}