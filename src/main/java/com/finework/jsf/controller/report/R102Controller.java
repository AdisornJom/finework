package com.finework.jsf.controller.report;

import com.finework.jsf.controller.inventory.*;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.ejb.entity.SysMaterial;
import com.finework.core.ejb.entity.SysMaterialClassify;
import com.finework.core.util.StringUtil;
import com.finework.ejb.facade.StockFacade;
import com.finework.jsf.model.LazyMaterialDataModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Adisorn j.
 */
@ManagedBean(name = R102Controller.CONTROLLER_NAME)
@SessionScoped
public class R102Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(R102Controller.class);
    public static final String CONTROLLER_NAME = "r102Controller";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private StockFacade stockFacade;
    private List<SysMaterial> items;
    private SysMaterial selected;
    
    
    private LazyDataModel<SysMaterial> lazyMaterialModel;
    
    //auto complete
    private SysMaterialClassify sysMaterialClassify;
    

    //find by criteria
    private String classifyName;
    private SysMaterialClassify selectClassify;
    private String itemName;
    private Integer status;
    private String totalPriceImport;
    private String totalQuantity;
    private String totalBalance;


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
            
            lazyMaterialModel = new LazyMaterialDataModel(stockFacade, classifyName,itemName, "Y");
            DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("listForm:equipment");
            dataTable.setFirst(0);
            

            List<SysMaterial>  items = stockFacade.findSysMaterialListByCriteria(classifyName,itemName, "Y");
            Double totalPrice_ = 0.0;
            Double totalQuantity_ =0.0;
            Double totalBalance_ = 0.0;
            for(SysMaterial sysMaterial:items){
                totalPrice_=totalPrice_+sysMaterial.getPrice();
                totalQuantity_=totalQuantity_+sysMaterial.getQuantity();
                totalBalance_=totalBalance_+sysMaterial.getBalance();
            }
            totalPriceImport=StringUtil.numberFormat(totalPrice_, "#,##0.00");
            totalQuantity=StringUtil.numberFormat(totalQuantity_, "#,##0.00");
            totalBalance=StringUtil.numberFormat(totalBalance_, "#,##0.00");
            
            //recheck
            RecheckStockController recheckStockController = (RecheckStockController) JsfUtil.getManagedBeanValue(RecheckStockController.CONTROLLER_NAME);
            recheckStockController.init();
            RequestContext.getCurrentInstance().update("headerForm:conter");
        } catch (Exception ex) {
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

    public String getTotalPriceImport() {
        return totalPriceImport;
    }

    public void setTotalPriceImport(String totalPriceImport) {
        this.totalPriceImport = totalPriceImport;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public LazyDataModel<SysMaterial> getLazyMaterialModel() {
        return lazyMaterialModel;
    }

    public void setLazyMaterialModel(LazyDataModel<SysMaterial> lazyMaterialModel) {
        this.lazyMaterialModel = lazyMaterialModel;
    }


    
    
}
