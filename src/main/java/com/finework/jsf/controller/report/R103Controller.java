package com.finework.jsf.controller.report;

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
import com.finework.jsf.model.LazyMaterialNotMovingDataModel;
import com.finework.jsf.model.LazySorter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Adisorn j.
 */
@ManagedBean(name = R103Controller.CONTROLLER_NAME)
@SessionScoped
public class R103Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(R103Controller.class);
    public static final String CONTROLLER_NAME = "r103Controller";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private StockFacade stockFacade;
    private List<SysMaterial> items;
    private SysMaterial selected;
    
    private LazyDataModel<SysMaterial> lazyMaterialNotMovingDataModel;
    
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
            items = stockFacade.findSysMaterialListNotmoving(classifyName,itemName, "Y");
            Collections.sort(items, new Comparator<SysMaterial>() {
                @Override
                public int compare(SysMaterial o1, SysMaterial o2) {
                     return o2.getRangeMonth()-o1.getRangeMonth() ;
                }
            });
          //  lazyMaterialNotMovingDataModel = new LazyMaterialNotMovingDataModel(stockFacade, classifyName,itemName, "Y");
          //  DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("listForm:equipment");
          //  dataTable.setFirst(0);
            
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    @Override
    public void create() {
    }

    @Override
    public void prepareCreate() {

    }
    
     public void prepareEdit(String page) {
        next(page);
    }

    @Override
    public void edit() {
       
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
            next("factory/r103/index");
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

    public LazyDataModel<SysMaterial> getLazyMaterialNotMovingDataModel() {
        return lazyMaterialNotMovingDataModel;
    }

    public void setLazyMaterialNotMovingDataModel(LazyDataModel<SysMaterial> lazyMaterialNotMovingDataModel) {
        this.lazyMaterialNotMovingDataModel = lazyMaterialNotMovingDataModel;
    }

}
