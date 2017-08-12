package com.finework.jsf.controller.stock;

import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.ejb.entity.SysDetail;
import com.finework.core.ejb.entity.SysHousePlan;
import com.finework.core.util.UploadUtil;
import com.finework.ejb.facade.StockFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Adisorn j.
 */
@ManagedBean(name = S201Controller.CONTROLLER_NAME)
@SessionScoped
public class S201Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(S201Controller.class);
    public static final String CONTROLLER_NAME = "s201Controller";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private StockFacade stockFacade;
    private List<SysDetail> items;
    private SysDetail selected;
    
    private UploadedFile file;
    private static final String NO_PRODUCT = "no_product.png";
    private String newFile = NO_PRODUCT;
    private String newFileDimension = NO_PRODUCT;

    //find by criteria
    private String itemName;
    private Integer status;
    
    //combo
    private SysHousePlan sysHousePlan;

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
            items = stockFacade.findSysDetailListByCriteria(itemName, "Y");
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    @Override
    public void create() {
        try {
            if (StringUtils.isBlank(selected.getDetailDesc())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.2001"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            selected.setDetailImg(newFile);
            selected.setDetailImgDimension(newFileDimension);
            selected.setStatus("Y");
            selected.setHousePlanId(sysHousePlan);
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            stockFacade.createSysDetail(selected);

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
        selected = new SysDetail();
        selected.setHousePlanId(new SysHousePlan());
        next("stock/s201/create");
    }
    
     public void prepareEdit(String page) {
        next(page);
    }

    @Override
    public void edit() {
        try {
            if (StringUtils.isBlank(selected.getDetailDesc())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.2001"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            if (!StringUtils.equals(NO_PRODUCT, newFile)) {
                selected.setDetailImg(newFile);
            }
            
            if (!StringUtils.equals(NO_PRODUCT, newFileDimension)) {
                selected.setDetailImgDimension(newFileDimension);
            }
            
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            stockFacade.editSysDetail(selected);
            clearData();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("stock/s201/index");
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
            stockFacade.editSysDetail(selected);
            //refresh data
            search();
            next("stock/s201/index");
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
        selected=new SysDetail();
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

    public UserInfoController getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoController userInfo) {
        this.userInfo = userInfo;
    }

    public List<SysDetail> getItems() {
        return items;
    }

    public void setItems(List<SysDetail> items) {
        this.items = items;
    }

    public SysDetail getSelected() {
        return selected;
    }

    public void setSelected(SysDetail selected) {
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

    public StockFacade getStockFacade() {
        return stockFacade;
    }

    public void setStockFacade(StockFacade stockFacade) {
        this.stockFacade = stockFacade;
    }

    public SysHousePlan getSysHousePlan() {
        return sysHousePlan;
    }

    public void setSysHousePlan(SysHousePlan sysHousePlan) {
        this.sysHousePlan = sysHousePlan;
    }
    
}
