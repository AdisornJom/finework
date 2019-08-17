package com.finework.jsf.controller.inventory;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysMaterial;
import com.finework.core.ejb.entity.SysMaterialExpensesDetail;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.ejb.facade.ContractorFacade;
import com.finework.ejb.facade.StockFacade;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

@ManagedBean(name = I106Controller.CONTROLLER_NAME)
@SessionScoped
public class I106Controller extends BaseController
{
  private static final Logger LOG = Logger.getLogger(I106Controller.class);
  public static final String CONTROLLER_NAME = "i106Controller";

   @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

  @Inject
  private StockFacade stockFacade;

  @Inject
  private ContractorFacade contractorFacade;
  private List<SysMaterialExpensesDetail> items;
  private String contractorName;
  private String status;
  private Date startDate;
  private Date toDate;
  private SysMaterial material_selected;
  private SysContractor contractor_selected;

  @PostConstruct
  public void init()
  {
    try
    {
      search();
    } catch (Exception ex) {
      JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
      LOG.error(ex);
    }
  }

  public void next(String path) {
    NaviController nav = (NaviController)JsfUtil.getManagedBeanValue("naviController");
    nav.next(path);
  }

  public void search() {
    try {
      if (null == this.startDate) {
        GregorianCalendar cal = (GregorianCalendar)GregorianCalendar.getInstance(Locale.US);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(5, 1);
        this.startDate = cal.getTime();
      }
      if (null == this.toDate) {
        GregorianCalendar calEnd = (GregorianCalendar)GregorianCalendar.getInstance(Locale.US);
        calEnd.set(11, 23);
        calEnd.set(12, 59);
        calEnd.set(13, 59);
        this.toDate = calEnd.getTime();
      }

      this.items = this.stockFacade.findSysMaterialExpensesDetailListByCriteria(this.contractorName, this.status, this.startDate, this.toDate);

      RecheckStockController recheckStockController = (RecheckStockController)JsfUtil.getManagedBeanValue("recheckStockController");
      recheckStockController.init();
      RequestContext.getCurrentInstance().update("headerForm:conter");
    } catch (Exception ex) {
      LOG.error(ex);
    }
  }

  public List<SysMaterial> completeMaterial(String query)
  {
    List filteredMaterial = new ArrayList();
    try {
      List<SysMaterial> allMaterial = this.stockFacade.findSysMaterialList();
      for (SysMaterial sysMaterial : allMaterial) {
        if ((sysMaterial.getMaterialDesc() != null) && (sysMaterial.getMaterialDesc().length() > 0) && 
          (sysMaterial.getMaterialDesc().toLowerCase().startsWith(query)))
          filteredMaterial.add(sysMaterial);
      }
    }
    catch (Exception ex)
    {
      LOG.error(ex);
    }
    return filteredMaterial;
  }

  public List<SysContractor> completeContractor(String query)
  {
    List filteredSysContractor = new ArrayList();
    try {
      List<SysContractor> allContractors = this.contractorFacade.findSysContractorList();
      for (SysContractor sysContractor : allContractors) {
        if ((sysContractor.getContractorNickname() != null) && (sysContractor.getContractorNickname().length() > 0) && 
          (sysContractor.getContractorNickname().toLowerCase().startsWith(query)))
          filteredSysContractor.add(sysContractor);
      }
    }
    catch (Exception ex)
    {
      LOG.error(ex);
    }
    return filteredSysContractor;
  }

  public UserInfoController getUserInfo()
  {
    return this.userInfo;
  }

  public void setUserInfo(UserInfoController userInfo) {
    this.userInfo = userInfo;
  }

  public String getContractorName() {
    return this.contractorName;
  }

  public void setContractorName(String contractorName) {
    this.contractorName = contractorName;
  }

  public Date getStartDate()
  {
    return this.startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getToDate() {
    return this.toDate;
  }

  public void setToDate(Date toDate) {
    this.toDate = toDate;
  }

  public StockFacade getStockFacade()
  {
    return this.stockFacade;
  }

  public void setStockFacade(StockFacade stockFacade) {
    this.stockFacade = stockFacade;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public SysMaterial getMaterial_selected() {
    return this.material_selected;
  }

  public void setMaterial_selected(SysMaterial material_selected) {
    this.material_selected = material_selected;
  }

  public SysContractor getContractor_selected() {
    return this.contractor_selected;
  }

  public void setContractor_selected(SysContractor contractor_selected) {
    this.contractor_selected = contractor_selected;
  }

  public List<SysMaterialExpensesDetail> getItems() {
    return this.items;
  }

  public void setItems(List<SysMaterialExpensesDetail> items) {
    this.items = items;
  }
}