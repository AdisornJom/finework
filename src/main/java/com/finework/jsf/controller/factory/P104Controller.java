package com.finework.jsf.controller.factory;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysManufactory;
import com.finework.core.ejb.entity.SysManufactoryDetail;
import com.finework.core.ejb.entity.SysExpensesManufactory;
import com.finework.core.ejb.entity.SysExpensesManufactoryDeduction;
import com.finework.core.ejb.entity.SysExpensesManufactoryDetail;
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
import javax.inject.Named;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;

/**
 *
 * @author Adisorn Jomjanyong
 */

@ManagedBean(name = P104Controller.CONTROLLER_NAME)
@SessionScoped
public class P104Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(P104Controller.class);
    public static final String CONTROLLER_NAME = "p104Controller";
    
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
    
   // private LazyDataModel<SysExpensesManufactory> lazyManufactoryModel;
    
    //
    private List<SysExpensesManufactory> items;
    private SysExpensesManufactory selected;
    private List<SysExpensesManufactory> printSelected;
    
    
    //find criteria main
    private String documentno;
    private Integer expensesType;
    private SysContractor contractor_find;
    private SysExpensesManufactoryDeduction expDeduction_find;
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
    
     //detial 
    private SysExpensesManufactoryDetail blExpenses_selected;
    
    //Recive on Detail
    private List<SelectItem> manufacturings;
    private String manufactoryDocumentno;
    private Integer manufactoryDetailId;
    private SysManufactory  sysManufoactory;
    
    //find_dialog
    private String documentNoBill;
    private SysManufactory manufactory_selected;
    private List<SysManufactory> manufactory_items_dialog;
    private List<SysManufactory> selectd_manufactory_items_dialog;
    
    //auto complete
    private SysManufacturing manufacturing_selected;
    private SysContractor contractor_selected;
    private SysExpensesManufactoryDeduction expDeduction_selected;
    
  
   
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
        selected = new SysExpensesManufactory();
        manufacturings= new ArrayList();
        manufactory_selected=new SysManufactory();
        manufactoryDocumentno="";
        
        blExpenses_selected=new SysExpensesManufactoryDetail();
        next(page);
    }
    
    public void prepareEdit() {
         blExpenses_selected=new SysExpensesManufactoryDetail();
    } 

    public void prepareEdit(String page) {
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
         selected = new SysExpensesManufactory();
         this.manufactoryDocumentno="";
         manufacturings= new ArrayList();
         
         contractor_selected=new SysContractor();
         expDeduction_selected =new SysExpensesManufactoryDeduction();
    }
   
    @Override
    public void create() {
      try {
            if (null == sysManufoactory) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ใบงานเลขที่"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            if (null == selected.getExpensesDate()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่ทำเบิก"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

//            if (null == contractor_selected) {
//                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อเล่นผู้รับเหมา"));
//                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
//                return;
//            }
            
            
            ///expenses_type
             if (null == expDeduction_selected) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายการค่าใช้จ่าย"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            
//             if (null==selected.getSysExpensesManufactoryDetailList() || selected.getSysExpensesManufactoryDetailList().isEmpty()) {
//                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
//                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
//                return;
//            }

            //insert ManufactoryReal
            selected.setFactoryId(sysManufoactory);
            
             //find detail manufactoryDetail
//            for(SysExpensesManufactoryDetail detail:selected.getSysExpensesManufactoryDetailList()){
//                  detail.setExpensesId(selected);
//            }
            
            selected.setContractorId(sysManufoactory.getContractorId());
            selected.setDeductionId(expDeduction_selected);
            
            selected.setExpensesStatus(1);
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());

            runningNoCustomer();
            
            manufactoryFacade.createSysExpensesManufactor(selected);
         
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
             
              //check ว่า รายการการผลิตถูกนำไปกระทำไปหรือยัง
            SysExpensesManufactory sysExpensesManufactory=manufactoryFacade.findSysExpensesManufactoryById(selected.getExpensesId());
            if(1 !=sysExpensesManufactory.getExpensesStatus().intValue()){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2025", "ใบเบิกตัวนี้ ได้ถูกนำไป 'บันทึกการจ่ายเงิน' เรียบร้อยค่ะ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
//             if (null==selected.getSysExpensesManufactoryDetailList() || selected.getSysExpensesManufactoryDetailList().isEmpty()) {
//                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
//                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
//                return;
//            }
            
             
            //deleteDetail
            // manufactoryFacade.deleteExpensesManufactoryIdOnDetail(selected.getExpensesId());
             
            //find detail manufactoryDetail
//            for(SysExpensesManufactoryDetail detail:selected.getSysExpensesManufactoryDetailList()){
//                  detail.setExpensesDetailId(null);
//                  detail.setExpensesId(selected);
//            }
             
            //update 
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate()); 
            
            manufactoryFacade.editSysExpensesManufactory(selected);
         
            clearData();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("factory/p104/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    @Override
    public void delete() {
        try {
            //check ว่า รายการการผลิตถูกนำไปกระทำไปหรือยัง
            SysExpensesManufactory sysExpensesManufactory=manufactoryFacade.findSysExpensesManufactoryById(selected.getExpensesId());
            if(1 !=sysExpensesManufactory.getExpensesStatus().intValue()){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2025", "ใบเบิกตัวนี้ ได้ถูกนำไป 'บันทึกการจ่ายเงิน' เรียบร้อยค่ะ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            manufactoryFacade.deleteSysExpensesManufactory(selected);
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
                
              items=manufactoryFacade.findSysExpensesManufactoryListByCriteria(documentno,expDeduction_find, contractor_find, startDate, toDate);
              
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
     public void searchManufactory(){
          try {
             manufactory_items_dialog = manufactoryFacade.findSysManufactoryList(documentNoBill,null!=manufactory_selected?manufactory_selected.getContractorId():null);
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }  
     
      
    public void addManufactory(){
         try {
                        
             //validate field iteam 
            if (manufactory_selected==null) {
                return;
            }
            this.manufactoryDocumentno=manufactory_selected.getDocumentno();
            this.sysManufoactory=manufactory_selected;
            manufacturings = new ArrayList<>();
            if(null!=manufactory_selected.getSysManufactoryDetailList() && manufactory_selected.getSysManufactoryDetailList().size()>0){
                for(SysManufactoryDetail detail:manufactory_selected.getSysManufactoryDetailList())
                    manufacturings.add(new SelectItem(detail.getManufactoryDetailId(),detail.getManufacturingId().getManufacturingDesc()));
            }

            clearData_sysManufactoryDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
         
         clearData_sysManufactoryDetail();
    }  
   

    public void clearData_sysManufactoryDetail(){
        manufactory_items_dialog=new ArrayList<>();
        selectd_manufactory_items_dialog=new ArrayList<>();
        manufactory_selected=new SysManufactory();
        documentNoBill="";
    }
    
    public void addDetail(){
         try {
             //validate field iteam 
             if (blExpenses_selected.getDetail()==null
                    ||blExpenses_selected.getAmount()==null) {
                return;
            }
            if (blExpenses_selected.getAmount()<=0 ) {
               return;
            }
            if (blExpenses_selected.getAmount()<=0 ) {
              return;
            }


            if (selected.getSysExpensesManufactoryDetailList() == null) 
                 selected.setSysExpensesManufactoryDetailList(new ArrayList<SysExpensesManufactoryDetail>());
             
             //is match
             List<String> list=new ArrayList();
             for(SysExpensesManufactoryDetail sysExpensesManufactoryDetail:selected.getSysExpensesManufactoryDetailList()){
                 list.add(sysExpensesManufactoryDetail.getDetail());
             }
             if (!list.contains(blExpenses_selected.getDetail())) {
                 selected.getSysExpensesManufactoryDetailList().add(blExpenses_selected);
             }

             clearData_sysExpensesManufactoryDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    public void deleteDetail(){
        try {
             //delete total 
             selected.getSysExpensesManufactoryDetailList().remove(blExpenses_selected);
             clearData_sysExpensesManufactoryDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
     
     public void clearData_sysExpensesManufactoryDetail(){
        blExpenses_selected =new SysExpensesManufactoryDetail();
    }
    
   //===== end  Dialog=========   
    
   public void handleKeyEvent(){}
   
   
    public void factoryDialogClose(CloseEvent event) {
        clearData_sysManufactoryDetail();
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
    
     //Auto complete customer
     public List<SysManufactory> completeManufactory(String query) {
         List<SysManufactory> filteredSysManufactory = new ArrayList<>();
       try {
            List<String> sysManufactory_list=new ArrayList();
            List<SysManufactory> allSysManufactory = manufactoryFacade.findSysManufactoryList();
            for (SysManufactory sysManufactory:allSysManufactory) {
               if(sysManufactory.getContractorId().getContractorNickname()!=null && sysManufactory.getContractorId().getContractorNickname().length()>0){
                if(sysManufactory.getContractorId().getContractorNickname().toLowerCase().startsWith(query)) {
                    if (!sysManufactory_list.contains(sysManufactory.getContractorId().getContractorNickname())) {
                        filteredSysManufactory.add(sysManufactory);
                        sysManufactory_list.add(sysManufactory.getContractorId().getContractorNickname());
                    }
                    
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredSysManufactory;
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
     
     public void runningNoCustomer() {
        String sequence_no=sequence.updateRunningNO(1,Constants.SEQUNCE_NO_EXPENSES_MANUFACTORY,"yyMM");
        this.selected.setDocumentNo(sequence_no);
    } 
     
    
    
      public String convertPriceToString(Double totalprice){
        if(totalprice==0.0){
            return "";
        }else{
            return new ThaiBaht().getText(totalprice);
        }
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

    public SysManufacturing getManufacturing_selected() {
        return manufacturing_selected;
    }

    public void setManufacturing_selected(SysManufacturing manufacturing_selected) {
        this.manufacturing_selected = manufacturing_selected;
    }

    public SysManufactory getManufactory_selected() {
        return manufactory_selected;
    }

    public void setManufactory_selected(SysManufactory manufactory_selected) {
        this.manufactory_selected = manufactory_selected;
    }

    public String getDocumentNoBill() {
        return documentNoBill;
    }

    public void setDocumentNoBill(String documentNoBill) {
        this.documentNoBill = documentNoBill;
    }

    public List<SysManufactory> getManufactory_items_dialog() {
        return manufactory_items_dialog;
    }

    public void setManufactory_items_dialog(List<SysManufactory> manufactory_items_dialog) {
        this.manufactory_items_dialog = manufactory_items_dialog;
    }

    public List<SysManufactory> getSelectd_manufactory_items_dialog() {
        return selectd_manufactory_items_dialog;
    }

    public void setSelectd_manufactory_items_dialog(List<SysManufactory> selectd_manufactory_items_dialog) {
        this.selectd_manufactory_items_dialog = selectd_manufactory_items_dialog;
    }

    public List<SelectItem> getManufacturings() {
        return manufacturings;
    }

    public void setManufacturings(List<SelectItem> manufacturings) {
        this.manufacturings = manufacturings;
    }

    public String getManufactoryDocumentno() {
        return manufactoryDocumentno;
    }

    public void setManufactoryDocumentno(String manufactoryDocumentno) {
        this.manufactoryDocumentno = manufactoryDocumentno;
    }

   

    public Integer getManufactoryDetailId() {
        return manufactoryDetailId;
    }

    public void setManufactoryDetailId(Integer manufactoryDetailId) {
        this.manufactoryDetailId = manufactoryDetailId;
    }

    public SysManufactory getSysManufoactory() {
        return sysManufoactory;
    }

    public void setSysManufoactory(SysManufactory sysManufoactory) {
        this.sysManufoactory = sysManufoactory;
    }

    public List<SysExpensesManufactory> getItems() {
        return items;
    }

    public void setItems(List<SysExpensesManufactory> items) {
        this.items = items;
    }

    public SysExpensesManufactory getSelected() {
        return selected;
    }

    public void setSelected(SysExpensesManufactory selected) {
        this.selected = selected;
    }

    public List<SysExpensesManufactory> getPrintSelected() {
        return printSelected;
    }

    public void setPrintSelected(List<SysExpensesManufactory> printSelected) {
        this.printSelected = printSelected;
    }

    public Integer getExpensesType() {
        return expensesType;
    }

    public void setExpensesType(Integer expensesType) {
        this.expensesType = expensesType;
    }

    public SysContractor getContractor_selected() {
        return contractor_selected;
    }

    public void setContractor_selected(SysContractor contractor_selected) {
        this.contractor_selected = contractor_selected;
    }

    public SysExpensesManufactoryDetail getBlExpenses_selected() {
        return blExpenses_selected;
    }

    public void setBlExpenses_selected(SysExpensesManufactoryDetail blExpenses_selected) {
        this.blExpenses_selected = blExpenses_selected;
    }

    public SysExpensesManufactoryDeduction getExpDeduction_selected() {
        return expDeduction_selected;
    }

    public void setExpDeduction_selected(SysExpensesManufactoryDeduction expDeduction_selected) {
        this.expDeduction_selected = expDeduction_selected;
    }

    public SysExpensesManufactoryDeduction getExpDeduction_find() {
        return expDeduction_find;
    }

    public void setExpDeduction_find(SysExpensesManufactoryDeduction expDeduction_find) {
        this.expDeduction_find = expDeduction_find;
    }

   
}