package com.finework.jsf.controller.inventory;

import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.ejb.entity.SysMaterial;
import com.finework.core.ejb.entity.SysMaterialClassify;
import com.finework.ejb.facade.StockFacade;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Adisorn j.
 */
@ManagedBean(name = RecheckStockController.CONTROLLER_NAME)
@SessionScoped
public class RecheckStockController extends BaseController {

    private static final Logger LOG = Logger.getLogger(RecheckStockController.class);
    public static final String CONTROLLER_NAME = "recheckStockController";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private StockFacade stockFacade;
    private List<SysMaterial> items;
    private SysMaterial selected;
    
    //auto complete
    private SysMaterialClassify sysMaterialClassify;
    
    private UploadedFile file;
    private static final String NO_PRODUCT = "no_product.png";
    private String newFile = NO_PRODUCT;
    private String newFileDimension = NO_PRODUCT;

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
            items = stockFacade.findSysMaterialListExpireByCriteria(classifyName,itemName, "Y");
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    @Override
    public void create() {
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
    }

    public void cancel(String path) {
        search();
        next(path);
    }
    
    
    @Override
    public void delete() {
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

    
}
