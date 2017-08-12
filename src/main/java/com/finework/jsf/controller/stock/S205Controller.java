package com.finework.jsf.controller.stock;

import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.ejb.entity.SysHousePlan;
import com.finework.core.ejb.entity.SysManufacturing;
import com.finework.core.util.UploadUtil;
import com.finework.ejb.facade.StockFacade;
import com.finework.jsf.model.LazyManufacturinglDataModel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Adisorn j.
 */
@ManagedBean(name = S205Controller.CONTROLLER_NAME)
@SessionScoped
public class S205Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(S205Controller.class);
    public static final String CONTROLLER_NAME = "s205Controller";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private StockFacade stockFacade;
    private SysManufacturing selected;
    
    private UploadedFile file;
    private static final String NO_PRODUCT = "no_product.png";
    private String newFile = NO_PRODUCT;
    private String newFileDimension = NO_PRODUCT;
    
    private LazyDataModel<SysManufacturing> lazyManufacturingModel;

    //find by criteria
    private String itemName;
    private Integer status;
    

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
            lazyManufacturingModel = new LazyManufacturinglDataModel(stockFacade,itemName,"Y");
            DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("listForm:manufacturingTable");
            dataTable.setFirst(0);
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    @Override
    public void create() {
        try {
            if (StringUtils.isBlank(selected.getManufacturingDesc())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อรายการผลิต"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (null==selected.getUnitPrice() || selected.getUnitPrice()<=0 ) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ราคาต่อหน่วย"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            selected.setStatus("Y");
            selected.setManufacturingImg(newFile);
            selected.setManufacturingImgDimension(newFileDimension);
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            stockFacade.createSysManufacturing(selected);

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
        selected = new SysManufacturing();
        next("stock/s205/create");
    }
    
     public void prepareEdit(String page) {
        next(page);
    }

    @Override
    public void edit() {
        try {
            if (StringUtils.isBlank(selected.getManufacturingDesc())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ชื่อรายการผลิต"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            if (null==selected.getUnitPrice() || selected.getUnitPrice()<=0 ) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "ราคาต่อหน่วย"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            
             if (!StringUtils.equals(NO_PRODUCT, newFile)) {
                selected.setManufacturingImg(newFile);
            }
            
            if (!StringUtils.equals(NO_PRODUCT, newFileDimension)) {
                selected.setManufacturingImgDimension(newFileDimension);
            }

            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            stockFacade.editSysManufacturing(selected);
            clearData();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("stock/s205/index");
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
            selected.setStatus("N");
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            stockFacade.editSysManufacturing(selected);
            //refresh data
            search();
            next("stock/s205/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
     //Auto complete customer
   public List<SysHousePlan> completeHousePlan(String query) {
         List<SysHousePlan> filteredHousePlan= new ArrayList<>();
       try {
            SysHousePlan sysHousePlanBlank =new SysHousePlan();
            sysHousePlanBlank.setDetailId(null);
            sysHousePlanBlank.setDetailDesc("-");
            filteredHousePlan.add(sysHousePlanBlank);
            List<SysHousePlan> allHousePlan = stockFacade.findSysHousePlanList();
            for (SysHousePlan housePlan:allHousePlan) {
               if(housePlan.getDetailDesc()!=null && housePlan.getDetailDesc().length()>0){
                if(housePlan.getDetailDesc().toLowerCase().startsWith(query)) {
                    filteredHousePlan.add(housePlan);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredHousePlan;
    }
    
    private void clearData() {
        newFile = NO_PRODUCT;
        newFileDimension=NO_PRODUCT;
        selected=new SysManufacturing();
    }
    
     public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        if (file != null) {
            try {
                  newFile = UploadUtil.uploadFile(file, "manufacturing", null, UUID.randomUUID().toString());
                  Thread.sleep(1500);
            } catch (Exception ex) {
                LOG.error(ex);
            } 
        }
    }
     
    public void handleFileUpload1(FileUploadEvent event) {
        file = event.getFile();
        if (file != null) {
            try {
                  newFileDimension = UploadUtil.uploadFile(file, "manufacturing_dimension", null, UUID.randomUUID().toString());
                  Thread.sleep(1500);
            } catch (Exception ex) {
                LOG.error(ex);
            } 
        }
    }
    
    public UserInfoController getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoController userInfo) {
        this.userInfo = userInfo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    

    public StockFacade getStockFacade() {
        return stockFacade;
    }

    public void setStockFacade(StockFacade stockFacade) {
        this.stockFacade = stockFacade;
    }

    public LazyDataModel<SysManufacturing> getLazyManufacturingModel() {
        return lazyManufacturingModel;
    }

    public void setLazyManufacturingModel(LazyDataModel<SysManufacturing> lazyManufacturingModel) {
        this.lazyManufacturingModel = lazyManufacturingModel;
    }
    
    public SysManufacturing getSelected() {
        return selected;
    }

    public void setSelected(SysManufacturing selected) {
        this.selected = selected;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getNewFile() {
        return newFile;
    }

    public void setNewFile(String newFile) {
        this.newFile = newFile;
    }

    public String getNewFileDimension() {
        return newFileDimension;
    }

    public void setNewFileDimension(String newFileDimension) {
        this.newFileDimension = newFileDimension;
    }
    
}
