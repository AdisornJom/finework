package com.finework.jsf.controller.stock;

import com.finework.core.ejb.entity.SysCustomer;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.ejb.entity.SysSequence;
import com.finework.core.util.DateTimeUtil;
import com.finework.ejb.facade.CustomerFacade;
import com.finework.ejb.facade.SequenceFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Adisorn j.
 */
@ManagedBean(name = S202Controller.CONTROLLER_NAME)
@SessionScoped
public class S202Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(S202Controller.class);
    public static final String CONTROLLER_NAME = "s202Controller";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private SequenceFacade sequenceFacade;
    @Inject
    private CustomerFacade customerFacade;
    
    private List<SysSequence> items;
    private SysSequence selected;
    
    
    //find by criteria
    private String custName;

    @PostConstruct
    @Override
    public void init() {
        try {
            items = sequenceFacade.findSysSequenceList();
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }
    
    public List<SysCustomer> completeCustomer(String query) {
         List<SysCustomer> filteredCustomers = new ArrayList<>();
       try {
           // List<SysCustomer> allCustomers = sequenceFacade.findCustNotSequenceList();
            List<SysCustomer> allCustomers =customerFacade.findSysCustomerList();
            for (SysCustomer sysCustomer:allCustomers) {
               if(sysCustomer.getCustomerNameTh()!=null && sysCustomer.getCustomerNameTh().length()>0){
                if(sysCustomer.getCustomerNameTh().toLowerCase().startsWith(query)) {
                    filteredCustomers.add(sysCustomer);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredCustomers;
    }
    
    public SysCustomer findCustomerById(Integer id){
       SysCustomer customer = null;
       try {
            SysCustomer criteria=new SysCustomer();
            criteria.setCustomerId(id);
            customer = (SysCustomer)customerFacade.findSysCustomer(criteria);
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return customer;
    }
    
    
    

    public void next(String path) {
        NaviController nav = (NaviController) JsfUtil.getManagedBeanValue(NaviController.CONTROLLER_NAME);
        nav.next(path);
    }

    public void search() {
        try {
            items = sequenceFacade.findSysSequenceListByCriteria(custName);
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    @Override
    public void create() {
        try {
            if (selected.getIncrementno()==null 
                    ||selected.getCurrentnext()==null) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.2001"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            if (selected.getIncrementno()<=0 ) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002",": เลขจำนวนเต็ม"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
//            if (selected.getCurrentnext()<=0 ) {
//                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002",": เลขจำนวนเต็ม"));
//                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
//                return;
//            }
            
//            SysSequence  sysSequence=  sequenceFacade.findSysSequenceByCustomerIdRunningType(cust_selected.getCustomerId(),selected.getRunningType());
//            if(sysSequence!=null){
//                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002",": ประเภท RuningNo ของบริษัทด้งกล่าวใหม่"));
//                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
//                return;
//            }

            SysSequence  sysSequence=  sequenceFacade.findSysSequenceByCustomerIdRunningType(null,selected.getRunningType());
            if(sysSequence!=null){
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002",": ประเภท RuningNo ด้งกล่าวใหม่"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            selected.setYear(Integer.parseInt(DateTimeUtil.cvtDateForShow(DateTimeUtil.currentDate(),"yyyy",new Locale("th", "TH"))));
            selected.setStartnewyear((selected.getStartnewyear()==null)?"Y":selected.getStartnewyear());
            selected.setRunningno(0);
            selected.setStatus("Y");

            selected.setCustomerId(new SysCustomer(1));
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate()); 
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            sequenceFacade.createSequence(selected);

            //refresh data
            clearData();
            init();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    @Override
    public void prepareCreate() {
        selected = new SysSequence();
        next("stock/s202/create");
    }
    
    @Override
    public void edit() {
        try {
            
            if (selected.getIncrementno()==null 
                    ||selected.getCurrentnext()==null) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.2001"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
            
            if (selected.getIncrementno()<=0 ) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002",": เลขจำนวนเต็ม"));
                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
                return;
            }
//            if (selected.getCurrentnext()<=0 ) {
//                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002",": เลขจำนวนเต็ม"));
//                RequestContext.getCurrentInstance().scrollTo("listForm:edit_msg");
//                return;
//            }
            
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            sequenceFacade.editSequence(selected);
            clearData();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("stock/s202/index");
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

            sequenceFacade.deleteSequence(selected);
            //refresh data
            search();
            next("stock/s202/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
    private void clearData() {
        selected=new SysSequence();
      //  cust_selected =new SysCustomer();
    }

    public UserInfoController getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoController userInfo) {
        this.userInfo = userInfo;
    }

    public List<SysSequence> getItems() {
        return items;
    }

    public void setItems(List<SysSequence> items) {
        this.items = items;
    }

    public SysSequence getSelected() {
        return selected;
    }

    public void setSelected(SysSequence selected) {
        this.selected = selected;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

//    public List<SysCustomer> getCust_items() {
//        return cust_items;
//    }
//
//    public void setCust_items(List<SysCustomer> cust_items) {
//        this.cust_items = cust_items;
//    }

//    public SysCustomer getCust_selected() {
//        return cust_selected;
//    }
//
//    public void setCust_selected(SysCustomer cust_selected) {
//        this.cust_selected = cust_selected;
//    }

 
 
}
