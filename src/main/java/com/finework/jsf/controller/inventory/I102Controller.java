package com.finework.jsf.controller.inventory;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.ejb.entity.SysMaterial;
import com.finework.core.ejb.entity.SysMaterialReceipts;
import com.finework.core.ejb.entity.SysSuppliers;
import com.finework.ejb.facade.ContractorFacade;
import com.finework.ejb.facade.StockFacade;
import com.finework.ejb.facade.SuppliersFacade;
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
@ManagedBean(name = I102Controller.CONTROLLER_NAME)
@SessionScoped
public class I102Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(I102Controller.class);
    public static final String CONTROLLER_NAME = "i102Controller";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private StockFacade stockFacade;
    @Inject
    private SuppliersFacade suppliersFacade;
    @Inject
    private ContractorFacade contractorFacade;
    
    private List<SysMaterialReceipts> items;
    private SysMaterialReceipts selected;

    //find by criteria
    private String materialName;
    private String supplierName;
    private String status;
    private Date startDate;
    private Date toDate;
    
    //auto complete
    private SysMaterial material_selected;
    private SysSuppliers supplier_selected;
    private SysContractor contractor_selected;


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
            
               items = stockFacade.findSysMaterialReceiptsListByCriteria(materialName, supplierName, status, startDate, toDate);
               
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
            
             if (null==material_selected) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วัสดุภัณฑ์"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
             
            if(null==supplier_selected && null==contractor_selected){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ผู้จัดส่งวัตถุดิบ(Supplier) หรือ ชื่อเล่นผู้รับเหมา"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }else{
                if (null != supplier_selected && null != contractor_selected) {
                    JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ควรเลือก ผู้จัดส่งวัตถุดิบ(Supplier) หรือ ชื่อเล่นผู้รับเหมา อย่างใดอย่างหนึ่ง"));
                    RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                    return;
                }
            }
            
            
            if (!StringUtils.isNotBlank(selected.getStatus())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ประเภท"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
             
            if (null==selected.getQuantity() || selected.getQuantity()<=0) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "จำนวนให้ถูกต้อง"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            if (null==selected.getReceiptsDate()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่รับวัสดุภัณฑ์เข้า"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            } 
            
            selected.setMaterialId(material_selected);
            selected.setSupplierId(supplier_selected);
            selected.setContractorId(contractor_selected);
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            stockFacade.createSysMaterialReceipts(selected);
            
        /*    //update stock Quantity
            SysMaterial sysMaterialOnhand=stockFacade.findSysMaterial(selected.getMaterialId());
            Double total= sysMaterialOnhand.getQuantity()+selected.getQuantity();
                    
            SysMaterial sysMaterial=new SysMaterial();
            sysMaterial=selected.getMaterialId();*/
        
           //update datetime receipts stock
            SysMaterial sysMaterialOnhand=stockFacade.findSysMaterial(selected.getMaterialId());
            sysMaterialOnhand.setReceiptsLastDate(DateTimeUtil.getSystemDate());
            sysMaterialOnhand.setModifiedBy(userInfo.getAdminUser().getUsername());
            sysMaterialOnhand.setModifiedDt(DateTimeUtil.getSystemDate());
            stockFacade.editSysMaterial(sysMaterialOnhand);
            

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
        selected = new SysMaterialReceipts();
        next("inventory/i102/create");
    }
    
     public void prepareEdit(String page) {
        next(page);
    }

    @Override
    public void edit() {
        try {
             if (null==supplier_selected) {
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
            }
            
         /*   //check quantity main
            SysMaterial sysMaterial =stockFacade.findSysMaterial(selected.getMaterialId());
            if (sysMaterial.getQuantity()< selected.getQuantity()) { 
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "สถานะ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }*/
            
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            stockFacade.editSysMaterialReceipts(selected);
            
            clearData();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("inventory/i102/index");
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
        selected=new SysMaterialReceipts();
        material_selected=new SysMaterial();
        supplier_selected=new SysSuppliers();
        contractor_selected=new SysContractor();
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
   
    //Auto complete Supplier
   public List<SysSuppliers> completeSupplier(String query) {
         List<SysSuppliers> filteredSuppliers = new ArrayList<>();
       try {
            SysSuppliers sysSupplier_=new SysSuppliers();
            sysSupplier_.setSupplierId(null);
            sysSupplier_.setSupplierNameTh("-");
            filteredSuppliers.add(sysSupplier_);
            List<SysSuppliers> allSuppliers = suppliersFacade.findSysSuppliersList();
            for (SysSuppliers sysSupplier:allSuppliers) {
               if(sysSupplier.getSupplierNameTh()!=null && sysSupplier.getSupplierNameTh().length()>0){
                if(sysSupplier.getSupplierNameTh().toLowerCase().startsWith(query)) {
                    filteredSuppliers.add(sysSupplier);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredSuppliers;
    }
   //Auto complete Contractor
   public List<SysContractor> completeContractor(String query) {
         List<SysContractor> filteredSysContractor = new ArrayList<>();
       try {
            SysContractor sysContractor_=new SysContractor();
            sysContractor_.setContractorId(null);
            sysContractor_.setContractorNickname("-");
            filteredSysContractor.add(sysContractor_);
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

    public List<SysMaterialReceipts> getItems() {
        return items;
    }

    public void setItems(List<SysMaterialReceipts> items) {
        this.items = items;
    }

    public SysMaterialReceipts getSelected() {
        return selected;
    }

    public void setSelected(SysMaterialReceipts selected) {
        this.selected = selected;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public SysSuppliers getSupplier_selected() {
        return supplier_selected;
    }

    public void setSupplier_selected(SysSuppliers supplier_selected) {
        this.supplier_selected = supplier_selected;
    }

    public SysContractor getContractor_selected() {
        return contractor_selected;
    }

    public void setContractor_selected(SysContractor contractor_selected) {
        this.contractor_selected = contractor_selected;
    }

   

    
}
