package com.finework.jsf.controller.inventory;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.ejb.entity.SysMaterial;
import com.finework.core.ejb.entity.SysMaterialExpenses;
import com.finework.core.ejb.entity.SysMaterialExpensesDetail;
import com.finework.ejb.facade.ContractorFacade;
import com.finework.ejb.facade.StockFacade;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Adisorn j.
 */
@ManagedBean(name = I103Controller.CONTROLLER_NAME)
@SessionScoped
public class I103Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(I103Controller.class);
    public static final String CONTROLLER_NAME = "i103Controller";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private StockFacade stockFacade;
    @Inject
    private ContractorFacade contractorFacade;
    
    private List<SysMaterialExpenses> items;
    private SysMaterialExpenses selected;

    //find by criteria
    private String contractorName;
    private String status;
    private Date startDate;
    private Date toDate;
    
    //auto complete
    private SysMaterial material_selected;
    private SysContractor contractor_selected;
    
    //detial 
    private SysMaterialExpensesDetail dvDetail_selected;


    @PostConstruct
    @Override
    public void init() {
        try {
            search();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    public void next(String path) {
        NaviController nav = (NaviController) JsfUtil.getManagedBeanValue(NaviController.CONTROLLER_NAME);
        nav.next(path);
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
            
               items = stockFacade.findSysMaterialExpensesListByCriteria(contractorName, status, startDate, toDate);
               
            //recheck
            RecheckStockController recheckStockController = (RecheckStockController) JsfUtil.getManagedBeanValue(RecheckStockController.CONTROLLER_NAME);
            recheckStockController.init();
            RequestContext.getCurrentInstance().update("headerForm:conter");
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    @Override
    public void create() {
        try {
            
             if (null==contractor_selected) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ผู้รับเหมา"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            
            if (!StringUtils.isNotBlank(selected.getStatus())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ประเภท"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            if (null==selected.getExpensesDate()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่เบิกจ่ายวัสดุภัณฑ์"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            } 
             
            if (null== selected.getSysMaterialExpensesDetailList() || selected.getSysMaterialExpensesDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            selected.setContractorId(contractor_selected);
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());

            

            List<SysMaterialExpensesDetail> detal_add=new ArrayList();
            for(SysMaterialExpensesDetail sysMaterialExpensesDetail:selected.getSysMaterialExpensesDetailList()){
                sysMaterialExpensesDetail.setExpensesDetailId(null);//auto generate id on db
                sysMaterialExpensesDetail.setExpensesId(selected);
                detal_add.add(sysMaterialExpensesDetail);
                
                //update datetime receipts stock
                SysMaterial sysMaterialOnhand=stockFacade.findSysMaterial(sysMaterialExpensesDetail.getMaterialId());
                sysMaterialOnhand.setExpensesLastDate(DateTimeUtil.getSystemDate());
                sysMaterialOnhand.setModifiedBy(userInfo.getAdminUser().getUsername());
                sysMaterialOnhand.setModifiedDt(DateTimeUtil.getSystemDate());
                stockFacade.editSysMaterial(sysMaterialOnhand);
            }
            selected.setSysMaterialExpensesDetailList(detal_add);
            
            stockFacade.createSysMaterialExpenses(selected);
            
        /*    //update stock Quantity
            SysMaterial sysMaterialOnhand=stockFacade.findSysMaterial(selected.getMaterialId());
            Double total= sysMaterialOnhand.getQuantity()+selected.getQuantity();
                    
            SysMaterial sysMaterial=new SysMaterial();
            sysMaterial=selected.getMaterialId();*/

            //refresh data
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
    public void prepareCreate() {
        selected = new SysMaterialExpenses();
        next("inventory/i103/create");
    }
    
     public void prepareEdit(String page) {
        next(page);
    }

    @Override
    public void edit() {
        try {
          /*   if (null==supplier_selected) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ผู้จัดส่งวัตถุดิบ(Supplier)"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            } 
             
            if (selected.getQuantity()<=0) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.2002", "จำนวนให้ถูกต้อง"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            if (null==selected.getReceiptsDate()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่รับ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            } 
            
            if (!StringUtils.isNotBlank(selected.getStatus())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "สถานะ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }*/
            
         /*   //check quantity main
            SysMaterial sysMaterial =stockFacade.findSysMaterial(selected.getMaterialId());
            if (sysMaterial.getQuantity()< selected.getQuantity()) { 
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "สถานะ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }*/
            
         /*   selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            stockFacade.editSysMaterial(selected);
            clearData();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("inventory/i102/index");
            */
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    public void cancel(String path) {
        search();
        next(path);
    }
    
    
    @Override
    public void delete() {
        try {
           
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
    
    private void clearData() {
        selected=new SysMaterialExpenses();
        contractor_selected = new SysContractor();
    }

     //===== start  Dialog=========  
    public void addDetail(){
         try {
             //validate field iteam 
              if (material_selected==null
                    ||dvDetail_selected.getUnit()==null
                    ||dvDetail_selected.getQuantity()==null) {
                return;
            }
            if (dvDetail_selected.getQuantity()<=0 ) {
               return;
            }

            if (selected.getSysMaterialExpensesDetailList() == null) 
                 selected.setSysMaterialExpensesDetailList(new ArrayList<SysMaterialExpensesDetail>());
             
             dvDetail_selected.setMaterialId(material_selected);

             //is match
             List<String> list=new ArrayList();
             for(SysMaterialExpensesDetail sysBillingDetail:selected.getSysMaterialExpensesDetailList()){
                 list.add(sysBillingDetail.getMaterialId().getMaterialDesc());
             }
             if (!list.contains(material_selected.getMaterialDesc())) {
                 selected.getSysMaterialExpensesDetailList().add(dvDetail_selected);
             }
             clearData_sysMaterialExpensesDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    public void deleteDetail(){
        try {
             //delete total 
             selected.getSysMaterialExpensesDetailList().remove(dvDetail_selected);  
             clearData_sysMaterialExpensesDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    public void clearData_sysMaterialExpensesDetail(){
        dvDetail_selected =new SysMaterialExpensesDetail();
        material_selected=new SysMaterial();
    }
    
    public String getTotal() {
        Double total = 0.0;
        if (selected.getSysMaterialExpensesDetailList() != null) {
            for (SysMaterialExpensesDetail expenses : selected.getSysMaterialExpensesDetailList()) {
                total += expenses.getQuantity();
            }
        }
 
        return new DecimalFormat("###,###.###").format(total);
    }
    
    
     public void prepareCreate(String page) {
        selected = new SysMaterialExpenses();
        dvDetail_selected=new SysMaterialExpensesDetail();
        next(page);
    }
    
    
//Auto Complete==========================================================================  
   //Auto complete Material
   public List<SysMaterial> completeMaterial(String query) {
         List<SysMaterial> filteredMaterial = new ArrayList<>();
       try {
            List<SysMaterial> allMaterial = stockFacade.findSysMaterialList();
            for (SysMaterial sysMaterial:allMaterial) {
               if(sysMaterial.getMaterialDesc()!=null && sysMaterial.getMaterialDesc().length()>0){
                if(sysMaterial.getMaterialDesc().toLowerCase().startsWith(query)) {
                    filteredMaterial.add(sysMaterial);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredMaterial;
    }
   
    //Auto complete Contractor
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
    
    
    public UserInfoController getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoController userInfo) {
        this.userInfo = userInfo;
    }

    public List<SysMaterialExpenses> getItems() {
        return items;
    }

    public void setItems(List<SysMaterialExpenses> items) {
        this.items = items;
    }

    public SysMaterialExpenses getSelected() {
        return selected;
    }

    public void setSelected(SysMaterialExpenses selected) {
        this.selected = selected;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
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

    

    public StockFacade getStockFacade() {
        return stockFacade;
    }

    public void setStockFacade(StockFacade stockFacade) {
        this.stockFacade = stockFacade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SysMaterial getMaterial_selected() {
        return material_selected;
    }

    public void setMaterial_selected(SysMaterial material_selected) {
        this.material_selected = material_selected;
    }

    public SysContractor getContractor_selected() {
        return contractor_selected;
    }

    public void setContractor_selected(SysContractor contractor_selected) {
        this.contractor_selected = contractor_selected;
    }

    public SysMaterialExpensesDetail getDvDetail_selected() {
        return dvDetail_selected;
    }

    public void setDvDetail_selected(SysMaterialExpensesDetail dvDetail_selected) {
        this.dvDetail_selected = dvDetail_selected;
    }

   

    
}
