package com.finework.jsf.controller.report;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import com.finework.core.util.JsfUtil;
import com.finework.ejb.facade.ContractorFacade;
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
import org.apache.log4j.Logger;

/**
 *
 * @author Adisorn j.
 */
@ManagedBean(name = RRBK105Controller.CONTROLLER_NAME)
@SessionScoped
public class RRBK105Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(RRBK105Controller.class);
    public static final String CONTROLLER_NAME = "rbr105Controller";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private ContractorFacade contractorFacade;
    private List<SysContractor> items;
    private SysContractor selected;
    


    //find by criteria
    private String contractorName;
    private Integer status;
    private Date startDate;
    private Date toDate;

    @PostConstruct
    @Override
    public void init() {
        search();
    }

    public void next(String path) {
        NaviController nav = (NaviController) JsfUtil.getManagedBeanValue(NaviController.CONTROLLER_NAME);
        nav.next(path);
    }

    public void search() {
        try {
            if (null == startDate) {
                GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance(Locale.US);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                startDate = cal.getTime();
            }
            if (null == toDate) {
                GregorianCalendar calEnd = (GregorianCalendar) GregorianCalendar.getInstance(Locale.US);
                calEnd.set(Calendar.HOUR_OF_DAY, 23);
                calEnd.set(Calendar.MINUTE, 59);
                calEnd.set(Calendar.SECOND, 59);
                toDate = calEnd.getTime();
            }

            items = contractorFacade.findSysContractorListByCriteria(contractorName, "Y");
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }


    public void cancel(String path) {
        search();
        next(path);
    }
    
    public UserInfoController getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoController userInfo) {
        this.userInfo = userInfo;
    }

    public List<SysContractor> getItems() {
        return items;
    }

    public void setItems(List<SysContractor> items) {
        this.items = items;
    }

    public SysContractor getSelected() {
        return selected;
    }

    public void setSelected(SysContractor selected) {
        this.selected = selected;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

   

   
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

   
}
