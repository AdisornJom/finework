package com.finework.jsf.controller.factory;

import com.finework.core.ejb.entity.ManufacturingsTO;
import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysManufactory;
import com.finework.core.ejb.entity.SysManufactoryDetail;
import com.finework.core.ejb.entity.SysManufactoryReal;
import com.finework.core.ejb.entity.SysManufacturing;
import com.finework.core.util.Constants;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.NumberUtils;
import com.finework.core.util.ThaiBaht;
import com.finework.ejb.facade.ContractorFacade;
import com.finework.ejb.facade.ForemanFacade;
import com.finework.ejb.facade.ManufactoryFacade;
import com.finework.ejb.facade.StockFacade;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.SequenceController;
import com.finework.jsf.common.UserInfoController;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Adisorn Jomjanyong
 */

@ManagedBean(name = P102Controller.CONTROLLER_NAME)
@SessionScoped
public class P102Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(P102Controller.class);
    public static final String CONTROLLER_NAME = "p102Controller";
    
    @Inject
    private ManufactoryFacade manufactoryFacade;
    @Inject
    private ContractorFacade contractorFacade;
    @Inject
    private StockFacade stockFacade;
    @Inject
    private UserInfoController userInfo;
    @Inject
    private SequenceController sequence;
    @Inject
    private ForemanFacade foremanFacade;
    
    private LazyDataModel<SysManufactory> lazyManufactoryModel;
    
    //
    private List<SysManufactoryReal> items;
    private SysManufactoryReal selected;
    private List<SysManufactoryReal> printSelected;
    
    
    //find criteria main
    private String documentno;
    private SysContractor contractor_find;
    private SysForeman foreman_find;
    private Date startDate;
    private Date toDate;
    
    //variable
    private Double total=0.0;
    private Double total_vat=0.0;
    private Double total_volume=0.0;
    private Double total_divide_equipment=0.0;
    private Double total_ream=0.0;
    private Double total_net;
    private String total_th;
    
    private int draftNo;
    
    //Recive on Detail
    //private List<SelectItem> manufacturings;
    private List<ManufacturingsTO> manufacturings;
    private ManufacturingsTO manufacturingsTO;
    private String manufactoryDocumentno;
    private Integer manufactoryDetailId;
    private SysManufactory  sysManufoactory;
    
    //find_dialog
    private String documentNoBill;
    private SysManufactory manufactory_selected;
    private List<SysManufactory> manufactory_items_dialog;
    private List<SysManufactory> selectd_manufactory_items_dialog;
    
    //auto complete
    private SysManufacturing manufacturing_selected;
    
  
   
    @PostConstruct
    @Override
    public void init() {
        search();
    }
    
    public void next(String path) {
        NaviController nav = (NaviController) JsfUtil.getManagedBeanValue(NaviController.CONTROLLER_NAME);
        nav.next(path);
    }
    
    public void prepareCreate(String page) {
        selected = new SysManufactoryReal();
        manufacturings= new ArrayList();
        manufactory_selected=new SysManufactory();
        manufactoryDocumentno="";
        next(page);
    }

    public void prepareEdit(String page) {
        next(page);
    }
    public void cancel(String path) {
        clearData();
        next(path);
    }
    public void backIndex(String path) {
        init();
        next(path);
    }
    public void clearData(){
         selected = new SysManufactoryReal();
         this.manufactoryDocumentno="";
         manufacturings= new ArrayList();
    }
   
    @Override
    public void create() {
      try {
            for(ManufacturingsTO bean:manufacturings){
               if(null !=bean.getSuccessDate() || null !=bean.getRealNo()){
                   if (null == bean.getSuccessDate()) {
                       JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่ทำสำเร็จ ลำดับที่:"+(bean.getSeq()+1)));
                       RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                       return;
                   }

                   if (null == bean.getRealNo() || bean.getRealNo() <= 0) {
                       JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "จำนวนที่ทำจริง ลำดับที่:"+(bean.getSeq()+1)));
                       RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                       return;
                   }
               }
            }
            
            boolean ckSave=false;
            for(ManufacturingsTO bean:manufacturings){
                if(null !=bean.getSuccessDate() || null !=bean.getRealNo()){
                   SysManufactoryReal realBean=new SysManufactoryReal();
                 //insert ManufactoryReal
                   realBean.setFactoryId(sysManufoactory);

                   //find detail manufactoryDetail
                   for (SysManufactoryDetail detail : sysManufoactory.getSysManufactoryDetailList()) {
                       if (bean.getManufactoryDetailId().equals(detail.getManufactoryDetailId())) {
                           realBean.setManufactoryDetailId(detail);
                       }
                   }
                   realBean.setSuccessDate(bean.getSuccessDate());
                   realBean.setVolumeReal(bean.getRealNo());
                   realBean.setRemark(bean.getRemark());
                   realBean.setStatus(1);
                   realBean.setStatusTransporter(Constants.TRANSPORTER_NORMAL);
                   realBean.setCreatedBy(userInfo.getAdminUser().getUsername());
                   realBean.setCreatedDt(DateTimeUtil.getSystemDate());
                   realBean.setModifiedBy(userInfo.getAdminUser().getUsername());
                   realBean.setModifiedDt(DateTimeUtil.getSystemDate());

                   manufactoryFacade.createSysManufactoryReal(realBean);
                   ckSave=true;
                }

              }
              if(ckSave){
                 JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
                 RequestContext.getCurrentInstance().scrollTo("listForm:create_msg"); 
              }
              clearData();
              search();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
  

    @Override
    public void edit() {
         try {
            /*
            if (null == selected.getRealDate()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่ทำ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }*/

            if (null == selected.getSuccessDate()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่ทำสำเร็จ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }

            if (null == selected.getVolumeReal() && selected.getVolumeReal() <= 0) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "จำนวนที่ทำจริง"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            //update Billing
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate()); 
            
            manufactoryFacade.editSysManufactoryReal(selected);
         
            clearData();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("factory/p102/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    @Override
    public void delete() {
        try {
            manufactoryFacade.deleteSysManufactoryReal(selected);
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4002"));
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
 
    public void search() {
        try {
              
               if (null == startDate) {
                    GregorianCalendar cal =(GregorianCalendar) GregorianCalendar.getInstance(Locale.US);
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    startDate = cal.getTime();
                }
                if (null == toDate) {
                    GregorianCalendar calEnd =(GregorianCalendar) GregorianCalendar.getInstance(Locale.US);
                    calEnd.set(Calendar.HOUR_OF_DAY, 23);
                    calEnd.set(Calendar.MINUTE, 59);
                    calEnd.set(Calendar.SECOND, 59);
                    toDate = calEnd.getTime();
                }
                
              items=manufactoryFacade.findSysManufactoryRealListByCriteria(documentno, contractor_find, foreman_find,startDate, toDate,0);
              
//             lazyBillingModel = new LazyBillingDataModel(billingFacade,Constants.CREDIT_NOTE,documentno,StringUtils.trimToEmpty(companyName),startDate,toDate);
//             DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("listForm:billingTable");
//             dataTable.setFirst(0);
            // printSelected =new ArrayList<SysPayment>();
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }
     
    //==== Strat Group ==========
      public void searchUserGroup() {
        try {
             //group_items=settingFacadeLocal.findUserGroupList();
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    //==== End Group ============
     
   //===== start  Dialog=========  
     public void searchManufactory(){
          try {
             manufactory_items_dialog = manufactoryFacade.findSysManufactoryList(documentNoBill,null!=manufactory_selected?manufactory_selected.getContractorId():null);
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }  
     
      
    public void addDetail(){
         try {
                        
             //validate field iteam 
            if (manufactory_selected==null) {
                return;
            }
            this.manufactoryDocumentno=manufactory_selected.getDocumentno();
            this.sysManufoactory=manufactory_selected;
            manufacturings = new ArrayList<>();
            int seq=0;
            if(null!=manufactory_selected.getSysManufactoryDetailList() && manufactory_selected.getSysManufactoryDetailList().size()>0){
                for(SysManufactoryDetail detail:manufactory_selected.getSysManufactoryDetailList()){
                    String calculateType="";
                    if (null != detail.getManufacturingId().getCalculateType()) {
                        switch (detail.getManufacturingId().getCalculateType()) {
                            case 1:
                                calculateType = Constants.CALCULATE_QUANTITY;
                                break;
                            case 2:
                                calculateType = Constants.CALCULATE_LENGTH+"("+NumberUtils.numberFormat(detail.getLength(), "#,##0.00")+")";
                                break;
                            default:
                                calculateType = Constants.CALCULATE_SET;
                                break;
                        }
                    }
                  ManufacturingsTO bean =new ManufacturingsTO();
                  bean.setSeq(seq);
                  bean.setManufactoryDetailId(detail.getManufactoryDetailId());
                  bean.setManufactoryDesc(detail.getManufacturingId().getManufacturingDesc()+"("+calculateType+")");
                  //bean.setSuccessDate(DateTimeUtil.currentDate());
                  //bean.setRealNo(BigDecimal.ZERO);
                 // bean.setRemark("");
                  manufacturings.add(seq,bean);
                  seq+=1;
                }
            }

            clearData_sysManufactoryDetail();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
         
         clearData_sysManufactoryDetail();
    }  
    
    public void clearDetail(){
        try {
             //delete total 
            ManufacturingsTO beanEdit= manufacturings.get(manufacturingsTO.getSeq());
            ManufacturingsTO bean =new ManufacturingsTO();
            bean.setSeq(beanEdit.getSeq());
            bean.setManufactoryDetailId(beanEdit.getManufactoryDetailId());
            bean.setManufactoryDesc(beanEdit.getManufactoryDesc());
            manufacturings.set(manufacturingsTO.getSeq(), bean);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
   

    public void clearData_sysManufactoryDetail(){
        manufactory_items_dialog=new ArrayList<>();
        selectd_manufactory_items_dialog=new ArrayList<>();
        manufactory_selected=new SysManufactory();
        documentNoBill="";
    }
     
    
   //===== end  Dialog=========   
    
   public void handleKeyEvent(){}
   
   
    public void dialogClose(CloseEvent event) {
        clearData_sysManufactoryDetail();
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
   
    //Auto manufacturing
    public List<SysManufacturing> completeManufacturing(String query) {
         List<SysManufacturing> filteredsysManufacturing= new ArrayList<>();
       try {
            List<SysManufacturing> allSysManufacturing = stockFacade.findSysManufacturingList();
            for (SysManufacturing sysManufacturing:allSysManufacturing) {
               if(sysManufacturing.getManufacturingDesc()!=null && sysManufacturing.getManufacturingDesc().length()>0){
                if(sysManufacturing.getManufacturingDesc().toLowerCase().startsWith(query)) {
                    filteredsysManufacturing.add(sysManufacturing);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredsysManufacturing;
    }
    
     //Auto complete customer
//     public List<SysManufactory> completeManufactory(String query) {
//         List<SysManufactory> filteredSysManufactory = new ArrayList<>();
//       try {
//            List<String> sysManufactory_list=new ArrayList();
//            List<SysManufactory> allSysManufactory = manufactoryFacade.findSysManufactoryList();
//            for (SysManufactory sysManufactory:allSysManufactory) {
//               if(sysManufactory.getContractorId().getContractorNickname()!=null && sysManufactory.getContractorId().getContractorNickname().length()>0){
//                if(sysManufactory.getContractorId().getContractorNickname().toLowerCase().startsWith(query)) {
//                    if (!sysManufactory_list.contains(sysManufactory.getContractorId().getContractorNickname())) {
//                        filteredSysManufactory.add(sysManufactory);
//                        sysManufactory_list.add(sysManufactory.getContractorId().getContractorNickname());
//                    }
//                    
//                }
//               }
//            }
//         } catch (Exception ex) {
//            LOG.error(ex);
//        }
//        return filteredSysManufactory;
//    }
    
     public List<SysManufactory> completeManufactory(String query) {
         List<SysManufactory> filteredSysManufactory = new ArrayList<>();
       try {
            filteredSysManufactory = manufactoryFacade.findSysManufactoryList(query);
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredSysManufactory;
    }

     //Auto complete Foreman
    public List<SysForeman> completeForeman(String query) {
         List<SysForeman> filteredSysForeman = new ArrayList<>();
       try {
            List<SysForeman> allForemans = foremanFacade.findSysForemanList();
            for (SysForeman sysForeman:allForemans) {
               if(sysForeman.getForemanNickname()!=null && sysForeman.getForemanNickname().length()>0){
                if(sysForeman.getForemanNickname().toLowerCase().startsWith(query)) {
                    filteredSysForeman.add(sysForeman);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredSysForeman;
    }
 
 //End Auto Complete==========================================================================   
     
     
    
    
      public String convertPriceToString(Double totalprice){
        if(totalprice==0.0){
            return "";
        }else{
            return new ThaiBaht().getText(totalprice);
        }
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

    public String getTotal_th() {
        return total_th;
    }

    public void setTotal_th(String total_th) {
        this.total_th = total_th;
    }

    public UserInfoController getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoController userInfo) {
        this.userInfo = userInfo;
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

    public String getDocumentno() {
        return documentno;
    }

    public void setDocumentno(String documentno) {
        this.documentno = documentno;
    }

    public SequenceController getSequence() {
        return sequence;
    }

    public void setSequence(SequenceController sequence) {
        this.sequence = sequence;
    }

    public Double getTotal_volume() {
        return total_volume;
    }

    public void setTotal_volume(Double total_volume) {
        this.total_volume = total_volume;
    }

    public Double getTotal_divide_equipment() {
        return total_divide_equipment;
    }

    public void setTotal_divide_equipment(Double total_divide_equipment) {
        this.total_divide_equipment = total_divide_equipment;
    }

    public Double getTotal_ream() {
        return total_ream;
    }

    public void setTotal_ream(Double total_ream) {
        this.total_ream = total_ream;
    }

    

    public int getDraftNo() {
        return draftNo;
    }

    public void setDraftNo(int draftNo) {
        this.draftNo = draftNo;
    }

    public ContractorFacade getContractorFacade() {
        return contractorFacade;
    }

    public void setContractorFacade(ContractorFacade contractorFacade) {
        this.contractorFacade = contractorFacade;
    }

    public LazyDataModel<SysManufactory> getLazyManufactoryModel() {
        return lazyManufactoryModel;
    }

    public void setLazyManufactoryModel(LazyDataModel<SysManufactory> lazyManufactoryModel) {
        this.lazyManufactoryModel = lazyManufactoryModel;
    }

    public List<SysManufactoryReal> getItems() {
        return items;
    }

    public void setItems(List<SysManufactoryReal> items) {
        this.items = items;
    }

    public SysManufactoryReal getSelected() {
        return selected;
    }

    public void setSelected(SysManufactoryReal selected) {
        this.selected = selected;
    }

    public List<SysManufactoryReal> getPrintSelected() {
        return printSelected;
    }

    public void setPrintSelected(List<SysManufactoryReal> printSelected) {
        this.printSelected = printSelected;
    }

    public SysContractor getContractor_find() {
        return contractor_find;
    }

    public void setContractor_find(SysContractor contractor_find) {
        this.contractor_find = contractor_find;
    }

    public SysManufacturing getManufacturing_selected() {
        return manufacturing_selected;
    }

    public void setManufacturing_selected(SysManufacturing manufacturing_selected) {
        this.manufacturing_selected = manufacturing_selected;
    }

    public SysManufactory getManufactory_selected() {
        return manufactory_selected;
    }

    public void setManufactory_selected(SysManufactory manufactory_selected) {
        this.manufactory_selected = manufactory_selected;
    }

    public String getDocumentNoBill() {
        return documentNoBill;
    }

    public void setDocumentNoBill(String documentNoBill) {
        this.documentNoBill = documentNoBill;
    }

    public List<SysManufactory> getManufactory_items_dialog() {
        return manufactory_items_dialog;
    }

    public void setManufactory_items_dialog(List<SysManufactory> manufactory_items_dialog) {
        this.manufactory_items_dialog = manufactory_items_dialog;
    }

    public List<SysManufactory> getSelectd_manufactory_items_dialog() {
        return selectd_manufactory_items_dialog;
    }

    public void setSelectd_manufactory_items_dialog(List<SysManufactory> selectd_manufactory_items_dialog) {
        this.selectd_manufactory_items_dialog = selectd_manufactory_items_dialog;
    }

    public List<ManufacturingsTO> getManufacturings() {
        return manufacturings;
    }

    public void setManufacturings(List<ManufacturingsTO> manufacturings) {
        this.manufacturings = manufacturings;
    }

    public String getManufactoryDocumentno() {
        return manufactoryDocumentno;
    }

    public void setManufactoryDocumentno(String manufactoryDocumentno) {
        this.manufactoryDocumentno = manufactoryDocumentno;
    }

   

    public Integer getManufactoryDetailId() {
        return manufactoryDetailId;
    }

    public void setManufactoryDetailId(Integer manufactoryDetailId) {
        this.manufactoryDetailId = manufactoryDetailId;
    }

    public SysManufactory getSysManufoactory() {
        return sysManufoactory;
    }

    public void setSysManufoactory(SysManufactory sysManufoactory) {
        this.sysManufoactory = sysManufoactory;
    }

    public ManufacturingsTO getManufacturingsTO() {
        return manufacturingsTO;
    }

    public void setManufacturingsTO(ManufacturingsTO manufacturingsTO) {
        this.manufacturingsTO = manufacturingsTO;
    }

    public SysForeman getForeman_find() {
        return foreman_find;
    }

    public void setForeman_find(SysForeman foreman_find) {
        this.foreman_find = foreman_find;
    }
    
    
}