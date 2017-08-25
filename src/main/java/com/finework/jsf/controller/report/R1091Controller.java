package com.finework.jsf.controller.report;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysManufactoryReal;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.StringUtil;
import com.finework.core.util.ThaiBaht;
import com.finework.ejb.facade.ContractorFacade;
import com.finework.ejb.facade.ManufactoryFacade;
import java.util.ArrayList;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Adisorn j.
 */
@ManagedBean(name = R1091Controller.CONTROLLER_NAME)
@SessionScoped
public class R1091Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(R1091Controller.class);
    public static final String CONTROLLER_NAME = "r1091Controller";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private ContractorFacade contractorFacade;
    @Inject
    private ManufactoryFacade manufactoryFacade;
    
    private List<SysContractor> items;
    private SysContractor selected;

    //find by criteria
    private SysContractor contractor_find;
    private Date startDate;
    private Date toDate;
    
    private Double total=0.0;
    private Double total_vat=0.0;
    private Double total_net;
    private String total_th;

     //footer summary
    private String totalSummary;
    
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
            items=new ArrayList();
            
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

            //items = contractorFacade.findSysContractorListByCriteria(contractorName, "Y");
            List<SysContractor> contractorItems = contractorFacade.findSysContractorListByCriteria(contractor_find);
            Double totalSum=0.0;
            for (SysContractor contractor : contractorItems) {
                List<SysManufactoryReal> realItems = manufactoryFacade.findSysManufactoryRealListByCriteria(null, contractor, null, startDate, toDate, 1);//1=ยังไม่ได้จ่ายเงิน
                Double totalOutstanding = 0.0;
                for (SysManufactoryReal sysManufactoryReal : realItems) {
                    Double unitpirce = null != sysManufactoryReal.getManufactoryDetailId().getManufacturingId().getUnitPrice() ? sysManufactoryReal.getManufactoryDetailId().getManufacturingId().getUnitPrice() : 0.0;

                    Double volumeReal = 0.0;
                    if (null != sysManufactoryReal.getVolumeReal() && sysManufactoryReal.getVolumeReal() > 0) {
                        volumeReal = sysManufactoryReal.getVolumeReal();
                    }

                    Double total = 0.0;
                    //1(จำนวน)x55(ค่าแรง/เมตร)x3.5(ความยาว)=192.5
                    if (2 == sysManufactoryReal.getManufactoryDetailId().getManufacturingId().getCalculateType()) {
                        Double length = null != sysManufactoryReal.getManufactoryDetailId().getLength() ? sysManufactoryReal.getManufactoryDetailId().getLength() : 0.0;
                        total = volumeReal * length * unitpirce;
                    } else {
                        total = unitpirce * volumeReal;
                    }
                    totalOutstanding += total;
                    sysManufactoryReal.setTotal_real(total);
                }
                totalSum+=totalOutstanding;
                contractor.setTotalOutstanding(totalOutstanding);
                contractor.setSysManufactoryReals(realItems);
                if(totalOutstanding>0.0)items.add(contractor);
            }
            totalSummary=StringUtil.numberFormat(totalSum, "#,##0.00");
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

     public void prepareEdit(String page) {
        checkTotalPrice();
        next(page);
    }
    
    public void cancel(String path) {
        search();
        next(path);
    }
    
    public void checkTotalPrice(){
        this.total=0.0;
        this.total_vat=0.0;
        this.total_net=0.00;
        
        Double totalOutstanding = 0.0;
        if (null != selected.getSysManufactoryReals()) {
            for (SysManufactoryReal sysManufactoryReal : selected.getSysManufactoryReals()) {
                if(1==sysManufactoryReal.getStatus()){
                    Double volumeReal = 0.0;
                    if (null != sysManufactoryReal.getVolumeReal() && sysManufactoryReal.getVolumeReal() > 0) {
                        volumeReal = sysManufactoryReal.getVolumeReal();
                    }

                    Double unitpirce = null != sysManufactoryReal.getManufactoryDetailId().getManufacturingId().getUnitPrice() ? sysManufactoryReal.getManufactoryDetailId().getManufacturingId().getUnitPrice() : 0.0;
                    Double total_ = 0.0;
                    if (2 == sysManufactoryReal.getManufactoryDetailId().getManufacturingId().getCalculateType()) {
                        Double length = null != sysManufactoryReal.getManufactoryDetailId().getLength() ? sysManufactoryReal.getManufactoryDetailId().getLength() : 0.0;
                        total_ = volumeReal * length * unitpirce;
                    } else {
                        total_ = unitpirce * volumeReal;
                    }
                    totalOutstanding += total_;
                }
                
            }
            
             this.total = totalOutstanding;
             this.total_vat = this.total  * 0.03;
        } else {
           clearDatatTotal();
        }
    }
    public void clearDatatTotal(){
        this.total=0.0;
        this.total_vat=0.0;
        this.total_net=0.00;
    }
    
    //Auto Complete==========================================================================  
    //Auto complete Contrator
    public List<SysContractor> completeContractor(String query) {
         List<SysContractor> filteredSysContractor = new ArrayList<>();
       try {
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
    
    public String convertPriceToString(Double totalprice){
        if(totalprice==0.0){
            return "";
        }else{
            return new ThaiBaht().getText(totalprice);
        }
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

    public SysContractor getContractor_find() {
        return contractor_find;
    }

    public void setContractor_find(SysContractor contractor_find) {
        this.contractor_find = contractor_find;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotal_vat() {
        return total_vat;
    }

    public void setTotal_vat(Double total_vat) {
        this.total_vat = total_vat;
    }

    public Double getTotal_net() {
        return total_net;
    }

    public void setTotal_net(Double total_net) {
        this.total_net = total_net;
    }

    public String getTotal_th() {
        return total_th;
    }

    public void setTotal_th(String total_th) {
        this.total_th = total_th;
    }

    public String getTotalSummary() {
        return totalSummary;
    }

    public void setTotalSummary(String totalSummary) {
        this.totalSummary = totalSummary;
    }


   
}
