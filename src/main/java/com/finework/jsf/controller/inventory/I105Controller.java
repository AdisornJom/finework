package com.finework.jsf.controller.inventory;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.ejb.entity.SysMaterial;
import com.finework.core.ejb.entity.SysMaterialClassify;
import com.finework.core.ejb.entity.SysSuppliers;
import com.finework.core.ejb.to.ReportStockI105TO;
import com.finework.core.util.UploadUtil;
import com.finework.ejb.facade.ContractorFacade;
import com.finework.ejb.facade.StockFacade;
import com.finework.jsf.model.LazyMaterialDataModel;
import com.finework.jsf.model.LazyReportI105DataModel;
import java.util.ArrayList;
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
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Adisorn j.
 */
@ManagedBean(name = I105Controller.CONTROLLER_NAME)
@SessionScoped
public class I105Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(I105Controller.class);
    public static final String CONTROLLER_NAME = "i105Controller";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private StockFacade stockFacade;
    @Inject
    private ContractorFacade contractorFacade;
    
    private ReportStockI105TO selected;
    private LazyDataModel<ReportStockI105TO> lazyStockI105Model;

    //find by criteria
    //auto complete
    private SysMaterial material_selected;
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
            lazyStockI105Model = new LazyReportI105DataModel(stockFacade, material_selected,contractor_selected);
            DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("listForm:prepairStock");
            dataTable.setFirst(0);
            
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
    }
    
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

    public ReportStockI105TO getSelected() {
        return selected;
    }

    public void setSelected(ReportStockI105TO selected) {
        this.selected = selected;
    }

    public LazyDataModel<ReportStockI105TO> getLazyStockI105Model() {
        return lazyStockI105Model;
    }

    public void setLazyStockI105Model(LazyDataModel<ReportStockI105TO> lazyStockI105Model) {
        this.lazyStockI105Model = lazyStockI105Model;
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

   
    
}
