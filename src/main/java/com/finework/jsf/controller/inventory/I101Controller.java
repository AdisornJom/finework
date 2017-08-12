package com.finework.jsf.controller.inventory;

import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.ejb.entity.SysMaterial;
import com.finework.core.ejb.entity.SysMaterialClassify;
import com.finework.core.util.UploadUtil;
import com.finework.ejb.facade.StockFacade;
import com.finework.jsf.model.LazyMaterialDataModel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
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
@ManagedBean(name = I101Controller.CONTROLLER_NAME)
@SessionScoped
public class I101Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(I101Controller.class);
    public static final String CONTROLLER_NAME = "i101Controller";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private StockFacade stockFacade;
    private List<SysMaterial> items;
    private SysMaterial selected;
    
    private LazyDataModel<SysMaterial> lazyMaterialModel;
    
    //auto complete
    private SysMaterialClassify sysMaterialClassify;
    
    private UploadedFile file;
    private static final String NO_PRODUCT = "no_product.png";
    private String newFile = NO_PRODUCT;
    private String newFileDimension = NO_PRODUCT;
    private String newFileDetail = NO_PRODUCT;

    //find by criteria
    private String classifyName;
    private SysMaterialClassify selectClassify;
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
            classifyName="";
            if(null!=selectClassify){
                classifyName=selectClassify.getClassifyDesc();
            }
            //items = stockFacade.findSysMaterialListByCriteria(classifyName,itemName, "Y");
            lazyMaterialModel = new LazyMaterialDataModel(stockFacade, classifyName,itemName, "Y");
            DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("listForm:equipment");
            dataTable.setFirst(0);
            
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
            if (StringUtils.isBlank(selected.getMaterialDesc())) {

                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.2001"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            selected.setClassifyId(sysMaterialClassify);
            selected.setMaterialImg(newFile);
            selected.setMaterialImgDimension(newFileDimension);
            selected.setMaterialImgDetail(newFileDetail);
            selected.setStatus("Y");
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            stockFacade.createSysMaterial(selected);

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
        selected = new SysMaterial();
        next("inventory/i101/create");
    }
    
     public void prepareEdit(String page) {
        next(page);
    }

    @Override
    public void edit() {
        try {
            if (StringUtils.isBlank(selected.getMaterialDesc())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.2001"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            if (!StringUtils.equals(NO_PRODUCT, newFile)) {
                selected.setMaterialImg(newFile);
            }
            
            if (!StringUtils.equals(NO_PRODUCT, newFileDimension)) {
                selected.setMaterialImgDimension(newFileDimension);
            }
            
            if (!StringUtils.equals(NO_PRODUCT, newFileDetail)) {
                selected.setMaterialImgDetail(newFileDetail);
            }

            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            stockFacade.editSysMaterial(selected);
            clearData();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("inventory/i101/index");
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
            stockFacade.editSysMaterial(selected);
            //refresh data
            search();
            next("inventory/i101/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
     //Auto complete customer
   public List<SysMaterialClassify> completeClassify(String query) {
         List<SysMaterialClassify> filteredClassify= new ArrayList<>();
       try {
            SysMaterialClassify sysHousePlanBlank =new SysMaterialClassify();
            sysHousePlanBlank.setClassifyId(null);
            sysHousePlanBlank.setClassifyDesc("-");
            filteredClassify.add(sysHousePlanBlank);
            List<SysMaterialClassify> allHousePlan = stockFacade.findSysMaterialClassifyList();
            for (SysMaterialClassify housePlan:allHousePlan) {
               if(housePlan.getClassifyDesc()!=null && housePlan.getClassifyDesc().length()>0){
                if(housePlan.getClassifyDesc().toLowerCase().startsWith(query)) {
                    filteredClassify.add(housePlan);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredClassify;
    }
    
    private void clearData() {
        newFile = NO_PRODUCT;
        newFileDimension=NO_PRODUCT;
        newFileDetail=NO_PRODUCT;
        selected=new SysMaterial();
    }
    
     public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        if (file != null) {
            try {
                  newFile = UploadUtil.uploadFile(file, "product", null, UUID.randomUUID().toString());
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
                  newFileDimension = UploadUtil.uploadFile(file, "product_dimension", null, UUID.randomUUID().toString());
                  Thread.sleep(1500);
            } catch (Exception ex) {
                LOG.error(ex);
            } 
        }
    }
            
    public void handleFileUpload2(FileUploadEvent event) {
        file = event.getFile();
        if (file != null) {
            try {
                  newFileDetail = UploadUtil.uploadFile(file, "product_detail", null, UUID.randomUUID().toString());
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

    public List<SysMaterial> getItems() {
        return items;
    }

    public void setItems(List<SysMaterial> items) {
        this.items = items;
    }

    public SysMaterial getSelected() {
        return selected;
    }

    public void setSelected(SysMaterial selected) {
        this.selected = selected;
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

    public String getNewFileDetail() {
        return newFileDetail;
    }

    public void setNewFileDetail(String newFileDetail) {
        this.newFileDetail = newFileDetail;
    }
    
    

    public StockFacade getStockFacade() {
        return stockFacade;
    }

    public void setStockFacade(StockFacade stockFacade) {
        this.stockFacade = stockFacade;
    }

    public SysMaterialClassify getSysMaterialClassify() {
        return sysMaterialClassify;
    }

    public void setSysMaterialClassify(SysMaterialClassify sysMaterialClassify) {
        this.sysMaterialClassify = sysMaterialClassify;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public SysMaterialClassify getSelectClassify() {
        return selectClassify;
    }

    public void setSelectClassify(SysMaterialClassify selectClassify) {
        this.selectClassify = selectClassify;
    }

    public LazyDataModel<SysMaterial> getLazyMaterialModel() {
        return lazyMaterialModel;
    }

    public void setLazyMaterialModel(LazyDataModel<SysMaterial> lazyMaterialModel) {
        this.lazyMaterialModel = lazyMaterialModel;
    }

    
}
