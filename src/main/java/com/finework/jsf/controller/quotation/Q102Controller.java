package com.finework.jsf.controller.quotation;


import com.finework.core.ejb.entity.SysMainQuotation;
import com.finework.core.ejb.entity.SysMainQuotation1;
import com.finework.core.ejb.entity.SysMainQuotation2;
import com.finework.core.ejb.entity.SysMainQuotation3;
import com.finework.core.ejb.entity.SysOrganization;
import com.finework.core.util.Constants;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.LoadConfig;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.NumberUtils;
import com.finework.core.util.ReportUtil;
import com.finework.core.util.ThaiBaht;
import com.finework.core.util.UploadUtil;
import com.finework.ejb.facade.CustomerFacade;
import com.finework.ejb.facade.OrganizationFacade;
import com.finework.ejb.facade.QuotationFacade;
import com.finework.ejb.facade.StockFacade;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.SequenceController;
import com.finework.jsf.common.UserInfoController;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Adisorn Jomjanyong
 */

@Named(Q102Controller.CONTROLLER_NAME)
@SessionScoped
public class Q102Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(Q102Controller.class);
    public static final String CONTROLLER_NAME = "q102Controller";

    @Inject
    private QuotationFacade quotationFacade;
    @Inject
    private CustomerFacade customerFacade;
    @Inject
    private StockFacade stockFacade;
    @Inject
    private UserInfoController userInfo;
    @Inject
    private SequenceController sequence;
    @Inject
    private OrganizationFacade organizationFacade;
    
    //private LazyDataModel<SysMainQuotation> lazyBillingModel;
    //
    private List<SysMainQuotation> items;
    private SysMainQuotation selected;
    private List<SysMainQuotation> printSelected;
    
    //detial 
    private SysMainQuotation1 dumpMyDetail_selected1;
    private SysMainQuotation2 dumpMyDetail_selected2;
    private SysMainQuotation3 dumpMyDetail_selected3;
    
    
    //find criteria main
    private String documentno;
    private String subject;
    private Date startDate;
    private Date toDate;
   
     //variable
    private Double total=0.0;
    private Double total_vat=0.0;
    private Double total_net=0.0;
    private String total_th;
    
    private Double total1=0.0;
    private Double total1_vat=0.0;
    private Double total1_net=0.0;
    private String total1_th;
    
    private Double total2=0.0;
    private Double total2_vat=0.0;
    private Double total2_net=0.0;
    private String total2_th;
    
    private Double total3=0.0;
    private Double total3_vat=0.0;
    private Double total3_net=0.0;
    private String total3_th;
    
    private static final String NO_PRODUCT = "no_product.png";

    private UploadedFile file1_1;
    private UploadedFile file1_2;
    private UploadedFile file1_3;
    private UploadedFile file1_4;
    private String newFile1_1 = NO_PRODUCT;
    private String newFile1_2 = NO_PRODUCT;
    private String newFile1_3 = NO_PRODUCT;
    private String newFile1_4 = NO_PRODUCT;

    private UploadedFile file2_1;
    private UploadedFile file2_2;
    private UploadedFile file2_3;
    private UploadedFile file2_4;
    private String newFile2_1 = NO_PRODUCT;
    private String newFile2_2 = NO_PRODUCT;
    private String newFile2_3 = NO_PRODUCT;
    private String newFile2_4 = NO_PRODUCT;

    private UploadedFile file3_1;
    private UploadedFile file3_2;
    private UploadedFile file3_3;
    private UploadedFile file3_4;
    private String newFile3_1 = NO_PRODUCT;
    private String newFile3_2 = NO_PRODUCT;
    private String newFile3_3 = NO_PRODUCT;
    private String newFile3_4 = NO_PRODUCT;
    
    public static Map<String, String> CONFIG;
//    public HashMap imgQuotation1;
//    public HashMap imgQuotation2;
//    public HashMap imgQuotation3;
    
    @PostConstruct
    @Override
    public void init() {
        CONFIG = LoadConfig.loadFileDefault();
        search();
    }
    
    public void next(String path) {
        NaviController nav = (NaviController) JsfUtil.getManagedBeanValue(NaviController.CONTROLLER_NAME);
        nav.next(path);
    }
    
    public void prepareCreate(String page) {
        selected = new SysMainQuotation();
        dumpMyDetail_selected1=new SysMainQuotation1();
        dumpMyDetail_selected2=new SysMainQuotation2();
        dumpMyDetail_selected3=new SysMainQuotation3();
        next(page);
    }
    public void prepareEdit() {
        dumpMyDetail_selected1=new SysMainQuotation1();
        dumpMyDetail_selected2=new SysMainQuotation2();
        dumpMyDetail_selected3=new SysMainQuotation3();
    }
    
     public void prepareEdit(String page) {
         if (null != selected.getSysMainQuotation1List() && selected.getSysMainQuotation1List().size() > 0) {
             Double total_ = 0.0;
             for (SysMainQuotation1 sysdetail : selected.getSysMainQuotation1List()) {
                 total_ = total_ + sysdetail.getAmount();
             }

             this.total1 = total_;
             this.total1_vat = total_ * 0.07;
             this.total1_net = total_ + total1_vat;
         }
         if (null != selected.getSysMainQuotation2List() && selected.getSysMainQuotation2List().size() > 0) {
             Double total_ = 0.0;
             for (SysMainQuotation2 sysdetail : selected.getSysMainQuotation2List()) {
                 total_ = total_ + sysdetail.getAmount();
             }

             total2 = total_;
             this.total2_vat = total_ * 0.07;
             this.total2_net = total_ + total2_vat;
         }

         if (null != selected.getSysMainQuotation3List() && selected.getSysMainQuotation3List().size() > 0) {
             Double total_ = 0.0;
             for (SysMainQuotation3 sysdetail : selected.getSysMainQuotation3List()) {
                 total_ = total_ + sysdetail.getAmount();
             }

             total3 = total_;
             this.total3_vat = total_ * 0.07;
             this.total3_net = total_ + total3_vat;
         }

         this.total = this.total1 + this.total2 + this.total3;
         this.total_vat = this.total1_vat + this.total2_vat + this.total3_vat;
         this.total_net = this.total1_net + this.total2_net + this.total3_net;
         
         next(page);
    }
     
    public void cancel(String path) {
        clearData();
        clearDatatTotal1();
        clearDatatTotal2();
        clearDatatTotal3();
        search();
        next(path);
    }
     
    public void clearData(){
        selected = new SysMainQuotation();
        newFile1_1 = NO_PRODUCT;
        newFile1_2 = NO_PRODUCT;
        newFile1_3 = NO_PRODUCT;
        newFile1_4 = NO_PRODUCT;
        
        newFile2_1 = NO_PRODUCT;
        newFile2_2 = NO_PRODUCT;
        newFile2_3 = NO_PRODUCT;
        newFile2_4 = NO_PRODUCT;
        
        newFile3_1 = NO_PRODUCT;
        newFile3_2 = NO_PRODUCT;
        newFile3_3 = NO_PRODUCT;
        newFile3_4 = NO_PRODUCT;
    }
   
    @Override
    public void create() {
      try {
            
            if (StringUtils.isBlank(selected.getSubject())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "เรื่อง"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            if (StringUtils.isBlank(selected.getInvite())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "เรียน"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
//            if (StringUtils.isBlank(selected.getEmail())) {
//                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "อีเมล์"));
//                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
//                return;
//            }
            
             if (null==selected.getQuotationDate()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่ทำรายการ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            if (StringUtils.isBlank(selected.getRemark())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "หัวข้อ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            if (StringUtils.isBlank(selected.getHeading())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "หมวดเรื่อง"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            if (null== selected.getSysMainQuotation1List()|| selected.getSysMainQuotation1List().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            //insert 
            if (null != selected.getSysMainQuotation1List() && !selected.getSysMainQuotation1List().isEmpty()) {
               if (!StringUtils.equals(NO_PRODUCT, newFile1_1))selected.setQuotationImg1(newFile1_1);
               if (!StringUtils.equals(NO_PRODUCT, newFile1_2))selected.setQuotationImg1_2(newFile1_2);
               if (!StringUtils.equals(NO_PRODUCT, newFile1_3))selected.setQuotationImg1_3(newFile1_3);
               if (!StringUtils.equals(NO_PRODUCT, newFile1_4))selected.setQuotationImg1_4(newFile1_4);
            }
            if (null != selected.getSysMainQuotation2List() && !selected.getSysMainQuotation2List().isEmpty()) {
                if (!StringUtils.equals(NO_PRODUCT, newFile2_1))selected.setQuotationImg2(newFile2_1);
                if (!StringUtils.equals(NO_PRODUCT, newFile2_2))selected.setQuotationImg2_2(newFile2_2);
                if (!StringUtils.equals(NO_PRODUCT, newFile2_3))selected.setQuotationImg2_3(newFile2_3);
                if (!StringUtils.equals(NO_PRODUCT, newFile2_4))selected.setQuotationImg2_4(newFile2_4);
            }
            if (null != selected.getSysMainQuotation3List() && !selected.getSysMainQuotation3List().isEmpty()) {
                if (!StringUtils.equals(NO_PRODUCT, newFile3_1)) selected.setQuotationImg3(newFile3_1);
                if (!StringUtils.equals(NO_PRODUCT, newFile3_2)) selected.setQuotationImg3_2(newFile3_2);
                if (!StringUtils.equals(NO_PRODUCT, newFile3_3)) selected.setQuotationImg3_3(newFile3_3);
                if (!StringUtils.equals(NO_PRODUCT, newFile3_4)) selected.setQuotationImg3_4(newFile3_4);
            }
           
            selected.setTypeForm(1);
            selected.setCreatedBy(userInfo.getAdminUser().getUsername());
            selected.setCreatedDt(DateTimeUtil.getSystemDate());
            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            
            //insertQuotation1
            Double total1=0.0,total_tax1=0.0,total_amount1=0.0;
            List<SysMainQuotation1> detal_add=new ArrayList();
            for(SysMainQuotation1 sysQuotation:selected.getSysMainQuotation1List()){
              sysQuotation.setQuotation1Id(null);//auto generate id on db
              sysQuotation.setQuotationId(selected);
              total1 = total1 + sysQuotation.getAmount();
              detal_add.add(sysQuotation);
            }
            total_tax1 = total1 * 0.07;
            total_amount1 = total1 + total_tax1;

            //insertQuotation2
            Double total2 = 0.0, total_tax2 = 0.0, total_amount2 = 0.0;
            List<SysMainQuotation2> detal_add2 = new ArrayList();
            if (null != selected.getSysMainQuotation2List() && !selected.getSysMainQuotation2List().isEmpty()) {
                for (SysMainQuotation2 sysQuotation : selected.getSysMainQuotation2List()) {
                    sysQuotation.setQuotation2Id(null);//auto generate id on db
                    sysQuotation.setQuotationId(selected);
                    total2 = total2 + sysQuotation.getAmount();
                    detal_add2.add(sysQuotation);
                }
                total_tax2 = total2 * 0.07;
                total_amount2 = total2 + total_tax2;
            }

            //insertQuotation3
            Double total3 = 0.0, total_tax3 = 0.0, total_amount3 = 0.0;
            List<SysMainQuotation3> detal_add3 = new ArrayList();
            if (null != selected.getSysMainQuotation3List() && !selected.getSysMainQuotation3List().isEmpty()) {
                for (SysMainQuotation3 sysQuotation : selected.getSysMainQuotation3List()) {
                    sysQuotation.setQuotation3Id(null);//auto generate id on db
                    sysQuotation.setQuotationId(selected);
                    total3 = total3 + sysQuotation.getAmount();
                    detal_add3.add(sysQuotation);
                }
                total_tax3 = total3 * 0.07;
                total_amount3 = total3 + total_tax3;
            }

            //===================================================
            
            
            selected.setSysMainQuotation1List(detal_add);
            selected.setSysMainQuotation2List(detal_add2);
            selected.setSysMainQuotation3List(detal_add3);
            selected.setTotal(total1+total2+total3);
            selected.setTotalTax(total_tax1+total_tax2+total_tax3);
            selected.setTotalAmount(total_amount1+total_amount2+total_amount3);
            
            runningNoCustomer();
             
            quotationFacade.createSysMainQuotation(selected);
         
            clearData();
            clearDatatTotal1();
            clearDatatTotal2();
            clearDatatTotal3();
            clearDatatTotal();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    

    @Override
    public void edit() {
         try {
           if (StringUtils.isBlank(selected.getSubject())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "เรื่อง"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            if (StringUtils.isBlank(selected.getInvite())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "เรียน"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
//            if (StringUtils.isBlank(selected.getEmail())) {
//                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "อีเมล์"));
//                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
//                return;
//            }
            
             if (null==selected.getQuotationDate()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "วันที่ทำรายการ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            if (StringUtils.isBlank(selected.getRemark())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "หัวข้อ"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            if (StringUtils.isBlank(selected.getHeading())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "หมวดเรื่อง"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            if (null== selected.getSysMainQuotation1List()|| selected.getSysMainQuotation1List().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", "รายละเอียด"));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            
            //insert 
             if (null != selected.getSysMainQuotation1List() && !selected.getSysMainQuotation1List().isEmpty()) {
               if (!StringUtils.equals(NO_PRODUCT, newFile1_1))selected.setQuotationImg1(newFile1_1);
               if (!StringUtils.equals(NO_PRODUCT, newFile1_2))selected.setQuotationImg1_2(newFile1_2);
               if (!StringUtils.equals(NO_PRODUCT, newFile1_3))selected.setQuotationImg1_3(newFile1_3);
               if (!StringUtils.equals(NO_PRODUCT, newFile1_4))selected.setQuotationImg1_4(newFile1_4);
            }
            if (null != selected.getSysMainQuotation2List() && !selected.getSysMainQuotation2List().isEmpty()) {
                if (!StringUtils.equals(NO_PRODUCT, newFile2_1))selected.setQuotationImg2(newFile2_1);
                if (!StringUtils.equals(NO_PRODUCT, newFile2_2))selected.setQuotationImg2_2(newFile2_2);
                if (!StringUtils.equals(NO_PRODUCT, newFile2_3))selected.setQuotationImg2_3(newFile2_3);
                if (!StringUtils.equals(NO_PRODUCT, newFile2_4))selected.setQuotationImg2_4(newFile2_4);
            }
            if (null != selected.getSysMainQuotation3List() && !selected.getSysMainQuotation3List().isEmpty()) {
                if (!StringUtils.equals(NO_PRODUCT, newFile3_1)) selected.setQuotationImg3(newFile3_1);
                if (!StringUtils.equals(NO_PRODUCT, newFile3_2)) selected.setQuotationImg3_2(newFile3_2);
                if (!StringUtils.equals(NO_PRODUCT, newFile3_3)) selected.setQuotationImg3_3(newFile3_3);
                if (!StringUtils.equals(NO_PRODUCT, newFile3_4)) selected.setQuotationImg3_4(newFile3_4);
            }

            selected.setModifiedBy(userInfo.getAdminUser().getUsername());
            selected.setModifiedDt(DateTimeUtil.getSystemDate());
            
            //insertQuotation1
            Double total1=0.0,total_tax1=0.0,total_amount1=0.0;
            List<SysMainQuotation1> detal_add=new ArrayList();
            for(SysMainQuotation1 sysQuotation:selected.getSysMainQuotation1List()){
              sysQuotation.setQuotation1Id(null);//auto generate id on db
              sysQuotation.setQuotationId(selected);
              total1 = total1 + sysQuotation.getAmount();
              detal_add.add(sysQuotation);
            }
            total_tax1 = total1 * 0.07;
            total_amount1 = total1 + total_tax1;

            //insertQuotation2
            Double total2 = 0.0, total_tax2 = 0.0, total_amount2 = 0.0;
            List<SysMainQuotation2> detal_add2 = new ArrayList();
            if (null != selected.getSysMainQuotation2List() && !selected.getSysMainQuotation2List().isEmpty()) {
                for (SysMainQuotation2 sysQuotation : selected.getSysMainQuotation2List()) {
                    sysQuotation.setQuotation2Id(null);//auto generate id on db
                    sysQuotation.setQuotationId(selected);
                    total2 = total2 + sysQuotation.getAmount();
                    detal_add2.add(sysQuotation);
                }
                total_tax2 = total2 * 0.07;
                total_amount2 = total2 + total_tax2;
            }

            //insertQuotation3
            Double total3 = 0.0, total_tax3 = 0.0, total_amount3 = 0.0;
            List<SysMainQuotation3> detal_add3 = new ArrayList();
            if (null != selected.getSysMainQuotation3List() && !selected.getSysMainQuotation3List().isEmpty()) {
                for (SysMainQuotation3 sysQuotation : selected.getSysMainQuotation3List()) {
                    sysQuotation.setQuotation3Id(null);//auto generate id on db
                    sysQuotation.setQuotationId(selected);
                    total3 = total3 + sysQuotation.getAmount();
                    detal_add3.add(sysQuotation);
                }
                total_tax3 = total3 * 0.07;
                total_amount3 = total3 + total_tax3;
            }

            //===================================================
            
            
            selected.setSysMainQuotation1List(detal_add);
            selected.setSysMainQuotation2List(detal_add2);
            selected.setSysMainQuotation3List(detal_add3);
            selected.setTotal(total1+total2+total3);
            selected.setTotalTax(total_tax1+total_tax2+total_tax3);
            selected.setTotalAmount(total_amount1+total_amount2+total_amount3);

            
            quotationFacade.editSysMainQuotation(selected);
         
            clearData();
            clearDatatTotal1();
            clearDatatTotal2();
            clearDatatTotal3();
            clearDatatTotal();
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            next("quotation/q102/index");
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    @Override
    public void delete() {
        try {
            quotationFacade.deleteSysMainQuotation(selected);
            search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4002"));
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
    //total=====================================
    public void checkTotalPrice(){
        clearDatatTotal();
        Double total1=0.0,total2=0.0,total3=0.0;
        Double totaltax1=0.0,totaltax2=0.0,totaltax3=0.0;
        Double totalnet1=0.0,totalnet2=0.0,totalnet3=0.0;
        if (null != selected.getSysMainQuotation1List() && selected.getSysMainQuotation1List().size()>0) {
            Double total_ = 0.0;
            for (SysMainQuotation1 sysdetail : selected.getSysMainQuotation1List()) {
                total_ = total_ + sysdetail.getAmount();
            }
             
             total1 = total_;
             totaltax1 =total_ * 0.07;
             totalnet1 = total_ + totaltax1 ;
        } 
        if (null != selected.getSysMainQuotation2List() && selected.getSysMainQuotation2List().size()>0) {
            Double total_ = 0.0;
            for (SysMainQuotation2 sysdetail : selected.getSysMainQuotation2List()) {
                total_ = total_ + sysdetail.getAmount();
            }
             
             total2 = total_;
             totaltax2 =total_ * 0.07;
             totalnet2 = total_ + totaltax2 ;
        } 
        
        if (null != selected.getSysMainQuotation3List() && selected.getSysMainQuotation3List().size()>0) {
            Double total_ = 0.0;
            for (SysMainQuotation3 sysdetail : selected.getSysMainQuotation3List()) {
                total_ = total_ + sysdetail.getAmount();
            }
             
             total3 = total_;
             totaltax3 =total_ * 0.07;
             totalnet3 = total_ + totaltax3 ;
        } 
        
        this.total=total1+total2+total3;
        this.total_vat=totaltax1+totaltax2+totaltax3;
        this.total_net=totalnet1+totalnet2+totalnet3;
        
    }
     public void clearDatatTotal(){
        this.total_vat=0.0;
        this.total_net=0.0;
        this.total = 0.00;
    }
    
    ///total==1=============================
    public void checkTotalPrice1(){
        Double total_ = 0.0;
        if (null != selected.getSysMainQuotation1List() && selected.getSysMainQuotation1List().size()>0) {
            for (SysMainQuotation1 sysdetail : selected.getSysMainQuotation1List()) {
                total_ = total_ + sysdetail.getAmount();
            }
             
             this.total1 = total_;
             this.total1_vat =total_ * 0.07;
             this.total1_net = total_ + this.total1_vat ;
        } else {
           clearDatatTotal1();
        }
    }
     public void clearDatatTotal1(){
        this.total1_vat=0.0;
        this.total1_net=0.0;
        this.total1 = 0.00;
    }
     
     ///total==2=============================
    public void checkTotalPrice2(){
        Double total_ = 0.0;
        if (null != selected.getSysMainQuotation2List() && selected.getSysMainQuotation2List().size()>0) {
            for (SysMainQuotation2 sysdetail : selected.getSysMainQuotation2List()) {
                total_ = total_ + sysdetail.getAmount();
            }
             
             this.total2 = total_;
             this.total2_vat =total_ * 0.07;
             this.total2_net = total_ + this.total2_vat ;
        } else {
           clearDatatTotal1();
        }
    }
     public void clearDatatTotal2(){
        this.total2_vat=0.0;
        this.total2_net=0.0;
        this.total2 = 0.00;
    }
     
    ///total==3=============================
    public void checkTotalPrice3(){
        Double total_ = 0.0;
        if (null != selected.getSysMainQuotation3List() && selected.getSysMainQuotation3List().size()>0) {
            for (SysMainQuotation3 sysdetail : selected.getSysMainQuotation3List()) {
                total_ = total_ + sysdetail.getAmount();
            }
             
             this.total3 = total_;
             this.total3_vat =total_ * 0.07;
             this.total3_net = total_ + this.total3_vat ;
        } else {
           clearDatatTotal1();
        }
    }
     public void clearDatatTotal3(){
        this.total3_vat=0.0;
        this.total3_net=0.0;
        this.total3 = 0.00;
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
             items=quotationFacade.findSysMainQuotationListByCriteria(documentno,subject,startDate,toDate,1);
//             lazyBillingModel = new LazyBillingDataModel(billingFacade,Constants.BILLING_B110,documentno,StringUtils.trimToEmpty(companyName),startDate,toDate);
//             DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("listForm:billingTable");
//             dataTable.setFirst(0);
             printSelected =new ArrayList<SysMainQuotation>();
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
   //======== Dialog 1====================
    public void addDetail1(){
         try {
             //validate field iteam 
              if (dumpMyDetail_selected1.getUnit()==null
                    ||dumpMyDetail_selected1.getVolume()==null) {
                return;
            }

            if(dumpMyDetail_selected1.getValueUnit()==null && dumpMyDetail_selected1.getValueUnit()<=0){
                return;   
            }
            
            Double total=0.0,instalUnit=0.0;
            instalUnit=(dumpMyDetail_selected1.getInstallUnit()!=null && dumpMyDetail_selected1.getInstallUnit()>0)?dumpMyDetail_selected1.getInstallUnit():0.0;
            if(dumpMyDetail_selected1.getValueUnit()!=null && dumpMyDetail_selected1.getValueUnit()>0){
                total=(dumpMyDetail_selected1.getVolume() * dumpMyDetail_selected1.getValueUnit())+instalUnit;
            }else{
                return;
            }
            
            
            if (selected.getSysMainQuotation1List() == null) 
                 selected.setSysMainQuotation1List(new ArrayList<SysMainQuotation1>());
             
             //dumpMyDetail_selected1.setId(detail_selected.getDetailId());
             dumpMyDetail_selected1.setAmount(total);
             //is match
           /*  List<String> list=new ArrayList();
             for(SysMainQuotationDetail sysBillingDetail:selected.getSysMainQuotationDetailList()){
                 list.add(sysBillingDetail.getDetail());
             }
             if (!list.contains(detail_selected.getDetailDesc())) {
                 selected.getSysMainQuotationDetailList().add(dvDetail_selected);
             }*/
              selected.getSysMainQuotation1List().add(dumpMyDetail_selected1);
           
             checkTotalPrice1();  
             clearData_sysDetail1();
             checkTotalPrice();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    public void deleteDetail1(){
        try {
             //delete total 
             selected.getSysMainQuotation1List().remove(dumpMyDetail_selected1);
             checkTotalPrice1();  
             checkTotalPrice();
             clearData_sysDetail1();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    //======== Dialog 2====================
    public void addDetail2(){
         try {
             //validate field iteam 
              if (dumpMyDetail_selected2.getUnit()==null
                    ||dumpMyDetail_selected2.getVolume()==null) {
                return;
            }

            if(dumpMyDetail_selected2.getValueUnit()==null && dumpMyDetail_selected2.getValueUnit()<=0){
                return;   
            }
            
            Double total=0.0,instalUnit=0.0;
            instalUnit=(dumpMyDetail_selected2.getInstallUnit()!=null && dumpMyDetail_selected2.getInstallUnit()>0)?dumpMyDetail_selected2.getInstallUnit():0.0;
            if(dumpMyDetail_selected2.getValueUnit()!=null && dumpMyDetail_selected2.getValueUnit()>0){
                total=(dumpMyDetail_selected2.getVolume() * dumpMyDetail_selected2.getValueUnit())+instalUnit;
            }else{
                return;
            }
            
            
            if (selected.getSysMainQuotation2List() == null) 
                 selected.setSysMainQuotation2List(new ArrayList<SysMainQuotation2>());
             
             //dumpMyDetail_selected2.setId(detail_selected.getDetailId());
             dumpMyDetail_selected2.setAmount(total);
             //is match
           /*  List<String> list=new ArrayList();
             for(SysMainQuotationDetail sysBillingDetail:selected.getSysMainQuotationDetailList()){
                 list.add(sysBillingDetail.getDetail());
             }
             if (!list.contains(detail_selected.getDetailDesc())) {
                 selected.getSysMainQuotationDetailList().add(dvDetail_selected);
             }*/
              selected.getSysMainQuotation2List().add(dumpMyDetail_selected2);
           
             checkTotalPrice2();  
             clearData_sysDetail2();
             checkTotalPrice();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    public void deleteDetail2(){
        try {
             //delete total 
             selected.getSysMainQuotation2List().remove(dumpMyDetail_selected2);
             checkTotalPrice2();  
             clearData_sysDetail2();
             checkTotalPrice();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    } 
    
   //======== Dialog 3====================
    public void addDetail3(){
         try {
             //validate field iteam 
              if (dumpMyDetail_selected3.getUnit()==null
                    ||dumpMyDetail_selected3.getVolume()==null) {
                return;
            }

            if(dumpMyDetail_selected3.getValueUnit()==null && dumpMyDetail_selected3.getValueUnit()<=0){
                return;   
            }
            
            Double total=0.0,instalUnit=0.0;
            instalUnit=(dumpMyDetail_selected3.getInstallUnit()!=null && dumpMyDetail_selected3.getInstallUnit()>0)?dumpMyDetail_selected3.getInstallUnit():0.0;
            if(dumpMyDetail_selected3.getValueUnit()!=null && dumpMyDetail_selected3.getValueUnit()>0){
                total=(dumpMyDetail_selected3.getVolume() * dumpMyDetail_selected3.getValueUnit())+instalUnit;
            }else{
                return;
            }
            
            
            if (selected.getSysMainQuotation3List() == null) 
                 selected.setSysMainQuotation3List(new ArrayList<SysMainQuotation3>());
             
             //dumpMyDetail_selected3.setId(detail_selected.getDetailId());
             dumpMyDetail_selected3.setAmount(total);
             //is match
           /*  List<String> list=new ArrayList();
             for(SysMainQuotationDetail sysBillingDetail:selected.getSysMainQuotationDetailList()){
                 list.add(sysBillingDetail.getDetail());
             }
             if (!list.contains(detail_selected.getDetailDesc())) {
                 selected.getSysMainQuotationDetailList().add(dvDetail_selected);
             }*/
              selected.getSysMainQuotation3List().add(dumpMyDetail_selected3);
           
             checkTotalPrice3();  
             clearData_sysDetail3();
             checkTotalPrice();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    public void deleteDetail3(){
        try {
             //delete total 
             selected.getSysMainQuotation3List().remove(dumpMyDetail_selected3);
             checkTotalPrice3();  
             clearData_sysDetail3();
             checkTotalPrice();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }  
    
    public String convertPriceToString(Double totalprice){
        if(totalprice==0.0){
            return "";
        }else{
            return new ThaiBaht().getText(totalprice);
        }
    }
 
    public void clearData_sysDetail1(){
        dumpMyDetail_selected1 =new SysMainQuotation1();
    }
    public void clearData_sysDetail2(){
        dumpMyDetail_selected2 =new SysMainQuotation2();
    }
    public void clearData_sysDetail3(){
        dumpMyDetail_selected3 =new SysMainQuotation3();
    }
   //===== end  Dialog=========   
    
    public void action(AjaxBehaviorEvent event){
        if (null != selected.getSysMainQuotation1List() && selected.getSysMainQuotation1List().size() > 0) {
            selected.getSysMainQuotation1List().clear();
            checkTotalPrice1();
            clearData_sysDetail1();
        }
        if (null != selected.getSysMainQuotation2List() && selected.getSysMainQuotation2List().size() > 0) {
            selected.getSysMainQuotation2List().clear();
            checkTotalPrice2();
            clearData_sysDetail2();
        }
        if (null != selected.getSysMainQuotation3List() && selected.getSysMainQuotation3List().size() > 0) {
            selected.getSysMainQuotation3List().clear();
            checkTotalPrice3();
            clearData_sysDetail3();
        }
    }
    
   public void handleKeyEvent(){}
   
    public void runningNoCustomer() {
        String sequence_no=sequence.updateRunningNO(1,Constants.SEQUNCE_NO_QUOTATION2,"yyMM");
        this.selected.setDocumentno(sequence_no);
    }
    
    
    public void handleFileUpload1_1(FileUploadEvent event) {
        file1_1 = event.getFile();
        if (file1_1 != null) {
            try {
                  newFile1_1 = UploadUtil.uploadFile(file1_1, "quotation1", null, UUID.randomUUID().toString());
                  Thread.sleep(1500);
            } catch (Exception ex) {
                LOG.error(ex);
            } 
        }
    }
    public void handleFileUpload1_2(FileUploadEvent event) {
        file1_2 = event.getFile();
        if (file1_2 != null) {
            try {
                  newFile1_2 = UploadUtil.uploadFile(file1_2, "quotation1", null, UUID.randomUUID().toString());
                  Thread.sleep(1500);
            } catch (Exception ex) {
                LOG.error(ex);
            } 
        }
    }
    public void handleFileUpload1_3(FileUploadEvent event) {
        file1_3 = event.getFile();
        if (file1_3 != null) {
            try {
                  newFile1_3 = UploadUtil.uploadFile(file1_3, "quotation1", null, UUID.randomUUID().toString());
                  Thread.sleep(1500);
            } catch (Exception ex) {
                LOG.error(ex);
            } 
        }
    }    
    public void handleFileUpload1_4(FileUploadEvent event) {
        file1_4 = event.getFile();
        if (file1_4 != null) {
            try {
                  newFile1_4 = UploadUtil.uploadFile(file1_4, "quotation1", null, UUID.randomUUID().toString());
                  Thread.sleep(1500);
            } catch (Exception ex) {
                LOG.error(ex);
            } 
        }
    }
    
    
    public void handleFileUpload2_1(FileUploadEvent event) {
        file2_1 = event.getFile();
        if (file2_1 != null) {
            try {
                  newFile2_1 = UploadUtil.uploadFile(file2_1, "quotation1", null, UUID.randomUUID().toString());
                  Thread.sleep(1500);
            } catch (Exception ex) {
                LOG.error(ex);
            } 
        }
    }
    public void handleFileUpload2_2(FileUploadEvent event) {
        file2_2 = event.getFile();
        if (file2_2 != null) {
            try {
                  newFile2_2 = UploadUtil.uploadFile(file2_2, "quotation1", null, UUID.randomUUID().toString());
                  Thread.sleep(1500);
            } catch (Exception ex) {
                LOG.error(ex);
            } 
        }
    }
    public void handleFileUpload2_3(FileUploadEvent event) {
        file2_3 = event.getFile();
        if (file2_3 != null) {
            try {
                  newFile2_3 = UploadUtil.uploadFile(file2_3, "quotation1", null, UUID.randomUUID().toString());
                  Thread.sleep(1500);
            } catch (Exception ex) {
                LOG.error(ex);
            } 
        }
    }
    public void handleFileUpload2_4(FileUploadEvent event) {
        file2_4 = event.getFile();
        if (file2_4 != null) {
            try {
                  newFile2_4 = UploadUtil.uploadFile(file2_4, "quotation1", null, UUID.randomUUID().toString());
                  Thread.sleep(1500);
            } catch (Exception ex) {
                LOG.error(ex);
            } 
        }
    }
    
    public void handleFileUpload3_1(FileUploadEvent event) {
        file3_1 = event.getFile();
        if (file3_1 != null) {
            try {
                  newFile3_1 = UploadUtil.uploadFile(file3_1, "quotation1", null, UUID.randomUUID().toString());
                  Thread.sleep(1500);
            } catch (Exception ex) {
                LOG.error(ex);
            } 
        }
    }
    public void handleFileUpload3_2(FileUploadEvent event) {
        file3_2 = event.getFile();
        if (file3_2 != null) {
            try {
                  newFile3_2 = UploadUtil.uploadFile(file3_2, "quotation1", null, UUID.randomUUID().toString());
                  Thread.sleep(1500);
            } catch (Exception ex) {
                LOG.error(ex);
            } 
        }
    }
    public void handleFileUpload3_3(FileUploadEvent event) {
        file3_3 = event.getFile();
        if (file3_3 != null) {
            try {
                  newFile3_3 = UploadUtil.uploadFile(file3_3, "quotation1", null, UUID.randomUUID().toString());
                  Thread.sleep(1500);
            } catch (Exception ex) {
                LOG.error(ex);
            } 
        }
    }
    public void handleFileUpload3_4(FileUploadEvent event) {
        file3_4 = event.getFile();
        if (file3_4 != null) {
            try {
                  newFile3_4 = UploadUtil.uploadFile(file3_4, "quotation1", null, UUID.randomUUID().toString());
                  Thread.sleep(1500);
            } catch (Exception ex) {
                LOG.error(ex);
            } 
        }
    }


   
    @Override
    public void reportPDF() {
        try {
            ReportUtil report = new ReportUtil();
            List reportList_ = new ArrayList<>();
            List<QuotationReportBean> reportList_main = new ArrayList<>();
            List<QuotationReportBean> reportList1 = new ArrayList<>();
            List<QuotationReportBean> reportList2 = new ArrayList<>();
            List<QuotationReportBean> reportList3 = new ArrayList<>();
            boolean cklist1=false,cklist2=false,cklist3=false;
            SysMainQuotation rpt_sysDelivery=quotationFacade.findByPK(selected.getQuotationId());

            List<SysMainQuotation1> list = quotationFacade.findSysQuotationDetail1ListByCriteria(selected.getQuotationId());
            Double total1=0.0,total1_vat=0.0,total1_net=0.0,total1_=0.0;
            //QuotationReportBean bean_head = new QuotationReportBean();
           // bean_head.setDetail(rpt_sysDelivery.getHeading());
           // reportList1.add(bean_head);
            for (SysMainQuotation1 to : list) {
                QuotationReportBean bean = new QuotationReportBean();
                bean.setSeq((to.getSeq()!=null)?to.getSeq().toString():"");
               // bean.setSendDate(DateTimeUtil.cvtDateForShow(to.getSendDate(), "dd/MM/yyyy", new Locale("th", "TH")));
                bean.setDetail(to.getDetail());
                bean.setVolume(NumberUtils.numberFormat(to.getVolume(),"#,##0.00"));
                bean.setUnit(to.getUnit());
                bean.setValueUnit(NumberUtils.numberFormat(to.getValueUnit(),"#,##0.00"));
                bean.setInstallUnit(NumberUtils.numberFormat(to.getInstallUnit(),"#,##0.00"));
                bean.setAmount(NumberUtils.numberFormat(to.getAmount(),"#,##0.00"));
                total1_ = total1_ + to.getAmount();
                
                reportList1.add(bean);
            }
             for(int i=0;i<(4-list.size());i++)reportList1.add(new QuotationReportBean("", "", "", "", "", "", ""));
             total1 = total1_;
             total1_vat =total1_ * 0.07;
             total1_net = total1_ + total1_vat ;
             cklist1=true;
             
            List<SysMainQuotation2> list2 = quotationFacade.findSysQuotationDetail2ListByCriteria(selected.getQuotationId());
            Double total2 = 0.0, total2_vat = 0.0, total2_net = 0.0, total2_ = 0.0;
            if (list2 != null && !list2.isEmpty()) {
               // reportList2.add(new QuotationReportBean("", "", "", "", "", "", ""));
                for (SysMainQuotation2 to : list2) {
                    QuotationReportBean bean = new QuotationReportBean();
                    bean.setSeq((to.getSeq()!=null)?to.getSeq().toString():"");
                    // bean.setSendDate(DateTimeUtil.cvtDateForShow(to.getSendDate(), "dd/MM/yyyy", new Locale("th", "TH")));
                    bean.setDetail(to.getDetail());
                    bean.setVolume(NumberUtils.numberFormat(to.getVolume(), "#,##0.00"));
                    bean.setUnit(to.getUnit());
                    bean.setValueUnit(NumberUtils.numberFormat(to.getValueUnit(), "#,##0.00"));
                    bean.setInstallUnit(NumberUtils.numberFormat(to.getInstallUnit(), "#,##0.00"));
                    bean.setAmount(NumberUtils.numberFormat(to.getAmount(), "#,##0.00"));
                    total2_ = total2_ + to.getAmount();

                    reportList2.add(bean);
                    
                }
                for(int i=0;i<(4-list2.size());i++)reportList2.add(new QuotationReportBean("", "", "", "", "", "", ""));
                total2 = total2_;
                total2_vat = total2_ * 0.07;
                total2_net = total2_ + total2_vat;
                cklist2=true;
            }
             
            List<SysMainQuotation3> list3 = quotationFacade.findSysQuotationDetail3ListByCriteria(selected.getQuotationId());
            Double total3=0.0,total3_vat=0.0,total3_net=0.0,total3_=0.0;
            if (list3 != null && !list3.isEmpty()) {
                for (SysMainQuotation3 to : list3) {
                   // reportList2.add(new QuotationReportBean("", "", "", "", "", "", ""));
                    QuotationReportBean bean = new QuotationReportBean();
                    bean.setSeq((to.getSeq()!=null)?to.getSeq().toString():"");
                    // bean.setSendDate(DateTimeUtil.cvtDateForShow(to.getSendDate(), "dd/MM/yyyy", new Locale("th", "TH")));
                    bean.setDetail(to.getDetail());
                    bean.setVolume(NumberUtils.numberFormat(to.getVolume(), "#,##0.00"));
                    bean.setUnit(to.getUnit());
                    bean.setValueUnit(NumberUtils.numberFormat(to.getValueUnit(), "#,##0.00"));
                    bean.setInstallUnit(NumberUtils.numberFormat(to.getInstallUnit(), "#,##0.00"));
                    bean.setAmount(NumberUtils.numberFormat(to.getAmount(), "#,##0.00"));
                    total3_ = total3_ + to.getAmount();

                    reportList3.add(bean);

                }
                for(int i=0;i<(4-list3.size());i++)reportList3.add(new QuotationReportBean("", "", "", "", "", "", ""));
                total3 = total3_;
                total3_vat = total3_ * 0.07;
                total3_net = total3_ + total3_vat;
                cklist3=true;
            }
            
            reportList_main.add(new QuotationReportBean("", "", "", "", "", "", ""));
            reportList_.add(reportList_main);
            reportList_.add(reportList1);
            reportList_.add(reportList2);
            reportList_.add(reportList3);
            HashMap map = new HashMap();
            SysOrganization org= organizationFacade.findSysOrganizationByStatus("Y");
            map.put("org_name_th",org.getOrgNameTh());
            map.put("org_name_eng",org.getOrgNameEng());
            map.put("org_address_th",org.getOrgAddressTh());
            map.put("org_tel",org.getOrgTel());
            map.put("org_branch",org.getOrgBranch());
            map.put("org_taxid",org.getOrgTax());
            map.put("org_bank",org.getOrgBank());
            map.put("org_bank_name",org.getOrgBankName());
            map.put("org_recall",org.getOrgRecall());
            
            map.put("documentno",null!=rpt_sysDelivery.getDocumentno()?rpt_sysDelivery.getDocumentno():"-");
            map.put("quotation_date",DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getQuotationDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            map.put("subject",rpt_sysDelivery.getSubject());
            map.put("invite",rpt_sysDelivery.getInvite());
            map.put("email",null!=rpt_sysDelivery.getEmail()?rpt_sysDelivery.getEmail():"-");
            map.put("remark",StringUtils.isNotBlank(rpt_sysDelivery.getRemark())?"                "+rpt_sysDelivery.getRemark():"-");
            map.put("heading",rpt_sysDelivery.getHeading());

            map.put("total1",NumberUtils.numberFormat(total1,"#,##0.00"));
            map.put("total1_vat",NumberUtils.numberFormat(total1_vat,"#,##0.00"));
            map.put("total1_net",NumberUtils.numberFormat(total1_net,"#,##0.00"));
            map.put("total1_char",(total1_net==0.0?"":new ThaiBaht().getText(total1_net)));
            
            map.put("total2",NumberUtils.numberFormat(total2,"#,##0.00"));
            map.put("total2_vat",NumberUtils.numberFormat(total2_vat,"#,##0.00"));
            map.put("total2_net",NumberUtils.numberFormat(total2_net,"#,##0.00"));
            map.put("total2_char",(total2_net==0.0?"":new ThaiBaht().getText(total2_net)));
            
            map.put("total3",NumberUtils.numberFormat(total3,"#,##0.00"));
            map.put("total3_vat",NumberUtils.numberFormat(total3_vat,"#,##0.00"));
            map.put("total3_net",NumberUtils.numberFormat(total3_net,"#,##0.00"));
            map.put("total3_char",(total3_net==0.0?"":new ThaiBaht().getText(total3_net)));
            
            map.put("price_total",NumberUtils.numberFormat(total1_net+total2_net+total3_net,"#,##0.00"));
            map.put("price_char",(total1==0.0?"":new ThaiBaht().getText(total1_net+total2_net+total3_net)));
            
            
            String path=CONFIG.get(LoadConfig._IMAGES_URL).concat("/quotation1/");
            map.put("reportCode", "Q102");
            if (cklist1 && cklist2 && cklist3) {
                report.exportSubReportQ102("q102", new String[]{"Q102Report3"},new String[]{"Q102SubReport1"},3, "Q102", map, reportList_, new String[]{
                    path.concat((null !=rpt_sysDelivery.getQuotationImg1())?rpt_sysDelivery.getQuotationImg1():""), 
                    path.concat((null !=rpt_sysDelivery.getQuotationImg1_2())?rpt_sysDelivery.getQuotationImg1_2():""), 
                    path.concat((null !=rpt_sysDelivery.getQuotationImg1_3())?rpt_sysDelivery.getQuotationImg1_3():""),
                    path.concat((null !=rpt_sysDelivery.getQuotationImg1_4())?rpt_sysDelivery.getQuotationImg1_4():"")},
                    new String[]{path.concat((null !=rpt_sysDelivery.getQuotationImg2())?rpt_sysDelivery.getQuotationImg2():""), 
                    path.concat((null!=rpt_sysDelivery.getQuotationImg2_2())?rpt_sysDelivery.getQuotationImg2_2():""), 
                    path.concat((null!=rpt_sysDelivery.getQuotationImg2_3())?rpt_sysDelivery.getQuotationImg2_3():""),
                    path.concat((null!=rpt_sysDelivery.getQuotationImg2_4())?rpt_sysDelivery.getQuotationImg2_4():"")},
                    new String[]{path.concat((null !=rpt_sysDelivery.getQuotationImg3())?rpt_sysDelivery.getQuotationImg3():""), 
                    path.concat((null!=rpt_sysDelivery.getQuotationImg3_2())?rpt_sysDelivery.getQuotationImg3_2():""), 
                    path.concat((null!=rpt_sysDelivery.getQuotationImg3_3())?rpt_sysDelivery.getQuotationImg3_3():""),
                    path.concat((null!=rpt_sysDelivery.getQuotationImg3_4())?rpt_sysDelivery.getQuotationImg3_4():"")});
            } else if (cklist1 && cklist2) {
               report.exportSubReportQ102("q102", new String[]{"Q102Report2"},new String[]{"Q102SubReport1"},2, "Q102", map, reportList_, new String[]{
                    path.concat((null !=rpt_sysDelivery.getQuotationImg1())?rpt_sysDelivery.getQuotationImg1():""), 
                    path.concat((null !=rpt_sysDelivery.getQuotationImg1_2())?rpt_sysDelivery.getQuotationImg1_2():""), 
                    path.concat((null !=rpt_sysDelivery.getQuotationImg1_3())?rpt_sysDelivery.getQuotationImg1_3():""),
                    path.concat((null !=rpt_sysDelivery.getQuotationImg1_4())?rpt_sysDelivery.getQuotationImg1_4():"")},
                    new String[]{path.concat((null !=rpt_sysDelivery.getQuotationImg2())?rpt_sysDelivery.getQuotationImg2():""), 
                    path.concat((null!=rpt_sysDelivery.getQuotationImg2_2())?rpt_sysDelivery.getQuotationImg2_2():""), 
                    path.concat((null!=rpt_sysDelivery.getQuotationImg2_3())?rpt_sysDelivery.getQuotationImg2_3():""),
                    path.concat((null!=rpt_sysDelivery.getQuotationImg2_4())?rpt_sysDelivery.getQuotationImg2_4():"")},new String[]{});
            } else {
                report.exportSubReportQ102("q102", new String[]{"Q102Report1"},new String[]{"Q102SubReport1"},1, "Q102", map, reportList_, new String[]{
                    path.concat((null !=rpt_sysDelivery.getQuotationImg1())?rpt_sysDelivery.getQuotationImg1():""), 
                    path.concat((null !=rpt_sysDelivery.getQuotationImg1_2())?rpt_sysDelivery.getQuotationImg1_2():""), 
                    path.concat((null !=rpt_sysDelivery.getQuotationImg1_3())?rpt_sysDelivery.getQuotationImg1_3():""),
                    path.concat((null !=rpt_sysDelivery.getQuotationImg1_4())?rpt_sysDelivery.getQuotationImg1_4():"")},new String[]{},new String[]{});
            }
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
    
    public void printPdfMuti(){
         try {
             List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
             Collections.sort(printSelected, new SysMainQuotation());
             for (SysMainQuotation sysQuotation : printSelected) {
                    ReportUtil report = new ReportUtil();
                    List reportList_ = new ArrayList<>();
                    List<QuotationReportBean> reportList_main = new ArrayList<>();
                    List<QuotationReportBean> reportList1 = new ArrayList<>();
                    List<QuotationReportBean> reportList2 = new ArrayList<>();
                    List<QuotationReportBean> reportList3 = new ArrayList<>();
                    boolean cklist1 = false, cklist2 = false, cklist3 = false;
                    SysMainQuotation rpt_sysDelivery=quotationFacade.findByPK(sysQuotation.getQuotationId());

                    List<SysMainQuotation1> list = quotationFacade.findSysQuotationDetail1ListByCriteria(sysQuotation.getQuotationId());
                    Double total1=0.0,total1_vat=0.0,total1_net=0.0,total1_=0.0;
                    //QuotationReportBean bean_head = new QuotationReportBean();
                   // bean_head.setDetail(rpt_sysDelivery.getHeading());
                   // reportList1.add(bean_head);
                    for (SysMainQuotation1 to : list) {
                        QuotationReportBean bean = new QuotationReportBean();
                        bean.setSeq((to.getSeq()!=null)?to.getSeq().toString():"");
                       // bean.setSendDate(DateTimeUtil.cvtDateForShow(to.getSendDate(), "dd/MM/yyyy", new Locale("th", "TH")));
                        bean.setDetail(to.getDetail());
                        bean.setVolume(NumberUtils.numberFormat(to.getVolume(),"#,##0.00"));
                        bean.setUnit(to.getUnit());
                        bean.setValueUnit(NumberUtils.numberFormat(to.getValueUnit(),"#,##0.00"));
                        bean.setInstallUnit(NumberUtils.numberFormat(to.getInstallUnit(),"#,##0.00"));
                        bean.setAmount(NumberUtils.numberFormat(to.getAmount(),"#,##0.00"));
                        total1_ = total1_ + to.getAmount();

                        reportList1.add(bean);
                    }
                     for(int i=0;i<(4-list.size());i++)reportList1.add(new QuotationReportBean("", "", "", "", "", "", ""));
                     total1 = total1_;
                     total1_vat =total1_ * 0.07;
                     total1_net = total1_ + total1_vat ;
                     cklist1=true;

                    List<SysMainQuotation2> list2 = quotationFacade.findSysQuotationDetail2ListByCriteria(sysQuotation.getQuotationId());
                    Double total2 = 0.0, total2_vat = 0.0, total2_net = 0.0, total2_ = 0.0;
                    if (list2 != null && !list2.isEmpty()) {
                       // reportList2.add(new QuotationReportBean("", "", "", "", "", "", ""));
                        for (SysMainQuotation2 to : list2) {
                            QuotationReportBean bean = new QuotationReportBean();
                            bean.setSeq((to.getSeq()!=null)?to.getSeq().toString():"");
                            // bean.setSendDate(DateTimeUtil.cvtDateForShow(to.getSendDate(), "dd/MM/yyyy", new Locale("th", "TH")));
                            bean.setDetail(to.getDetail());
                            bean.setVolume(NumberUtils.numberFormat(to.getVolume(), "#,##0.00"));
                            bean.setUnit(to.getUnit());
                            bean.setValueUnit(NumberUtils.numberFormat(to.getValueUnit(), "#,##0.00"));
                            bean.setInstallUnit(NumberUtils.numberFormat(to.getInstallUnit(), "#,##0.00"));
                            bean.setAmount(NumberUtils.numberFormat(to.getAmount(), "#,##0.00"));
                            total2_ = total2_ + to.getAmount();

                            reportList2.add(bean);
                        }
                        for(int i=0;i<(4-list2.size());i++)reportList2.add(new QuotationReportBean("", "", "", "", "", "", ""));
                        total2 = total2_;
                        total2_vat = total2_ * 0.07;
                        total2_net = total2_ + total2_vat;
                        cklist2=true;
                    }

                    List<SysMainQuotation3> list3 = quotationFacade.findSysQuotationDetail3ListByCriteria(sysQuotation.getQuotationId());
                    Double total3=0.0,total3_vat=0.0,total3_net=0.0,total3_=0.0;
                    if (list3 != null && !list3.isEmpty()) {
                        for (SysMainQuotation3 to : list3) {
                           // reportList2.add(new QuotationReportBean("", "", "", "", "", "", ""));
                            QuotationReportBean bean = new QuotationReportBean();
                            bean.setSeq((to.getSeq()!=null)?to.getSeq().toString():"");
                            // bean.setSendDate(DateTimeUtil.cvtDateForShow(to.getSendDate(), "dd/MM/yyyy", new Locale("th", "TH")));
                            bean.setDetail(to.getDetail());
                            bean.setVolume(NumberUtils.numberFormat(to.getVolume(), "#,##0.00"));
                            bean.setUnit(to.getUnit());
                            bean.setValueUnit(NumberUtils.numberFormat(to.getValueUnit(), "#,##0.00"));
                            bean.setInstallUnit(NumberUtils.numberFormat(to.getInstallUnit(), "#,##0.00"));
                            bean.setAmount(NumberUtils.numberFormat(to.getAmount(), "#,##0.00"));
                            total3_ = total3_ + to.getAmount();

                            reportList3.add(bean);
                        }
                        for(int i=0;i<(4-list3.size());i++)reportList3.add(new QuotationReportBean("", "", "", "", "", "", ""));
                        total3 = total3_;
                        total3_vat = total3_ * 0.07;
                        total3_net = total3_ + total3_vat;
                        cklist3=true;
                    }

                    reportList_main.add(new QuotationReportBean("", "", "", "", "", "", ""));
                    reportList_.add(reportList_main);
                    reportList_.add(reportList1);
                    reportList_.add(reportList2);
                    reportList_.add(reportList3);
                    HashMap map = new HashMap();
                    SysOrganization org= organizationFacade.findSysOrganizationByStatus("Y");
                    map.put("org_name_th",org.getOrgNameTh());
                    map.put("org_name_eng",org.getOrgNameEng());
                    map.put("org_address_th",org.getOrgAddressTh());
                    map.put("org_tel",org.getOrgTel());
                    map.put("org_branch",org.getOrgBranch());
                    map.put("org_taxid",org.getOrgTax());
                    map.put("org_bank",org.getOrgBank());
                    map.put("org_bank_name",org.getOrgBankName());
                    map.put("org_recall",org.getOrgRecall());

                    map.put("documentno",null!=rpt_sysDelivery.getDocumentno()?rpt_sysDelivery.getDocumentno():"-");
                    map.put("quotation_date",DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getQuotationDate(), "dd/MM/yyyy", new Locale("th", "TH")));
                    map.put("subject",rpt_sysDelivery.getSubject());
                    map.put("invite",rpt_sysDelivery.getInvite());
                    map.put("email",null!=rpt_sysDelivery.getEmail()?rpt_sysDelivery.getEmail():"-");
                    map.put("remark",StringUtils.isNotBlank(rpt_sysDelivery.getRemark())?"                "+rpt_sysDelivery.getRemark():"-");
                    map.put("heading",rpt_sysDelivery.getHeading());

                    map.put("total1",NumberUtils.numberFormat(total1,"#,##0.00"));
                    map.put("total1_vat",NumberUtils.numberFormat(total1_vat,"#,##0.00"));
                    map.put("total1_net",NumberUtils.numberFormat(total1_net,"#,##0.00"));
                    map.put("total1_char",(total1_net==0.0?"":new ThaiBaht().getText(total1_net)));

                    map.put("total2",NumberUtils.numberFormat(total2,"#,##0.00"));
                    map.put("total2_vat",NumberUtils.numberFormat(total2_vat,"#,##0.00"));
                    map.put("total2_net",NumberUtils.numberFormat(total2_net,"#,##0.00"));
                    map.put("total2_char",(total2_net==0.0?"":new ThaiBaht().getText(total2_net)));

                    map.put("total3",NumberUtils.numberFormat(total3,"#,##0.00"));
                    map.put("total3_vat",NumberUtils.numberFormat(total3_vat,"#,##0.00"));
                    map.put("total3_net",NumberUtils.numberFormat(total3_net,"#,##0.00"));
                    map.put("total3_char",(total3_net==0.0?"":new ThaiBaht().getText(total3_net)));

                    map.put("price_total",NumberUtils.numberFormat(total1_net+total2_net+total3_net,"#,##0.00"));
                    map.put("price_char",(total1==0.0?"":new ThaiBaht().getText(total1_net+total2_net+total3_net)));
                    

                    JasperPrint print= null;
                    String path = CONFIG.get(LoadConfig._IMAGES_URL).concat("/quotation1/");
                    map.put("reportCode", "Q102");
                    if (cklist1 && cklist2 && cklist3) {
                        print=report.exportSubReportQ102mearge("q102", new String[]{"Q102Report3"},new String[]{"Q102SubReport1"},3, "Q102", map, reportList_, new String[]{
                            path.concat((null !=rpt_sysDelivery.getQuotationImg1())?rpt_sysDelivery.getQuotationImg1():""), 
                            path.concat((null !=rpt_sysDelivery.getQuotationImg1_2())?rpt_sysDelivery.getQuotationImg1_2():""), 
                            path.concat((null !=rpt_sysDelivery.getQuotationImg1_3())?rpt_sysDelivery.getQuotationImg1_3():""),
                            path.concat((null !=rpt_sysDelivery.getQuotationImg1_4())?rpt_sysDelivery.getQuotationImg1_4():"")},
                            new String[]{path.concat((null !=rpt_sysDelivery.getQuotationImg2())?rpt_sysDelivery.getQuotationImg2():""), 
                            path.concat((null!=rpt_sysDelivery.getQuotationImg2_2())?rpt_sysDelivery.getQuotationImg2_2():""), 
                            path.concat((null!=rpt_sysDelivery.getQuotationImg2_3())?rpt_sysDelivery.getQuotationImg2_3():""),
                            path.concat((null!=rpt_sysDelivery.getQuotationImg2_4())?rpt_sysDelivery.getQuotationImg2_4():"")},
                            new String[]{path.concat((null !=rpt_sysDelivery.getQuotationImg3())?rpt_sysDelivery.getQuotationImg3():""), 
                            path.concat((null!=rpt_sysDelivery.getQuotationImg3_2())?rpt_sysDelivery.getQuotationImg3_2():""), 
                            path.concat((null!=rpt_sysDelivery.getQuotationImg3_3())?rpt_sysDelivery.getQuotationImg3_3():""),
                            path.concat((null!=rpt_sysDelivery.getQuotationImg3_4())?rpt_sysDelivery.getQuotationImg3_4():"")});
                    } else if (cklist1 && cklist2) {
                       print=report.exportSubReportQ102mearge("q102", new String[]{"Q102Report2"},new String[]{"Q102SubReport1"},2, "Q102", map, reportList_, new String[]{
                            path.concat((null !=rpt_sysDelivery.getQuotationImg1())?rpt_sysDelivery.getQuotationImg1():""), 
                            path.concat((null !=rpt_sysDelivery.getQuotationImg1_2())?rpt_sysDelivery.getQuotationImg1_2():""), 
                            path.concat((null !=rpt_sysDelivery.getQuotationImg1_3())?rpt_sysDelivery.getQuotationImg1_3():""),
                            path.concat((null !=rpt_sysDelivery.getQuotationImg1_4())?rpt_sysDelivery.getQuotationImg1_4():"")},
                            new String[]{path.concat((null !=rpt_sysDelivery.getQuotationImg2())?rpt_sysDelivery.getQuotationImg2():""), 
                            path.concat((null!=rpt_sysDelivery.getQuotationImg2_2())?rpt_sysDelivery.getQuotationImg2_2():""), 
                            path.concat((null!=rpt_sysDelivery.getQuotationImg2_3())?rpt_sysDelivery.getQuotationImg2_3():""),
                            path.concat((null!=rpt_sysDelivery.getQuotationImg2_4())?rpt_sysDelivery.getQuotationImg2_4():"")},new String[]{});
                    } else {
                        print=report.exportSubReportQ102mearge("q102", new String[]{"Q102Report1"},new String[]{"Q102SubReport1"},1, "Q102", map, reportList_, new String[]{
                            path.concat((null !=rpt_sysDelivery.getQuotationImg1())?rpt_sysDelivery.getQuotationImg1():""), 
                            path.concat((null !=rpt_sysDelivery.getQuotationImg1_2())?rpt_sysDelivery.getQuotationImg1_2():""), 
                            path.concat((null !=rpt_sysDelivery.getQuotationImg1_3())?rpt_sysDelivery.getQuotationImg1_3():""),
                            path.concat((null !=rpt_sysDelivery.getQuotationImg1_4())?rpt_sysDelivery.getQuotationImg1_4():"")},new String[]{},new String[]{});
                    }
                    
                    jasperPrintList.add(print);
             }
             if(!printSelected.isEmpty()){
                String pdfCode="Q102";
                String pdfName = pdfCode.concat("-").concat(DateTimeUtil.dateToString(DateTimeUtil.currentDate(), "yyyyMMddHHmmss"));
                ReportUtil report = new ReportUtil();
                report.exportMearge(pdfName,jasperPrintList);
             }
              init();
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }

    public StockFacade getStockFacade() {
        return stockFacade;
    }

    public void setStockFacade(StockFacade stockFacade) {
        this.stockFacade = stockFacade;
    }

    public UserInfoController getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoController userInfo) {
        this.userInfo = userInfo;
    }

    public List<SysMainQuotation> getItems() {
        return items;
    }

    public void setItems(List<SysMainQuotation> items) {
        this.items = items;
    }

    public SysMainQuotation getSelected() {
        return selected;
    }

    public void setSelected(SysMainQuotation selected) {
        this.selected = selected;
    }

    public List<SysMainQuotation> getPrintSelected() {
        return printSelected;
    }

    public void setPrintSelected(List<SysMainQuotation> printSelected) {
        this.printSelected = printSelected;
    }

    public SysMainQuotation1 getDumpMyDetail_selected1() {
        return dumpMyDetail_selected1;
    }

    public void setDumpMyDetail_selected1(SysMainQuotation1 dumpMyDetail_selected1) {
        this.dumpMyDetail_selected1 = dumpMyDetail_selected1;
    }

   

    public String getDocumentno() {
        return documentno;
    }

    public void setDocumentno(String documentno) {
        this.documentno = documentno;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public Double getTotal2() {
        return total2;
    }

    public void setTotal2(Double total2) {
        this.total2 = total2;
    }

    public Double getTotal2_vat() {
        return total2_vat;
    }

    public void setTotal2_vat(Double total2_vat) {
        this.total2_vat = total2_vat;
    }

    public Double getTotal2_net() {
        return total2_net;
    }

    public void setTotal2_net(Double total2_net) {
        this.total2_net = total2_net;
    }

    public String getTotal2_th() {
        return total2_th;
    }

    public void setTotal2_th(String total2_th) {
        this.total2_th = total2_th;
    }

    public Double getTotal3() {
        return total3;
    }

    public void setTotal3(Double total3) {
        this.total3 = total3;
    }

    public Double getTotal3_vat() {
        return total3_vat;
    }

    public void setTotal3_vat(Double total3_vat) {
        this.total3_vat = total3_vat;
    }

    public Double getTotal3_net() {
        return total3_net;
    }

    public void setTotal3_net(Double total3_net) {
        this.total3_net = total3_net;
    }

    public String getTotal3_th() {
        return total3_th;
    }

    public void setTotal3_th(String total3_th) {
        this.total3_th = total3_th;
    }

    public SysMainQuotation2 getDumpMyDetail_selected2() {
        return dumpMyDetail_selected2;
    }

    public void setDumpMyDetail_selected2(SysMainQuotation2 dumpMyDetail_selected2) {
        this.dumpMyDetail_selected2 = dumpMyDetail_selected2;
    }

    public SysMainQuotation3 getDumpMyDetail_selected3() {
        return dumpMyDetail_selected3;
    }

    public void setDumpMyDetail_selected3(SysMainQuotation3 dumpMyDetail_selected3) {
        this.dumpMyDetail_selected3 = dumpMyDetail_selected3;
    }

    public UploadedFile getFile1_1() {
        return file1_1;
    }

    public void setFile1_1(UploadedFile file1_1) {
        this.file1_1 = file1_1;
    }

    public UploadedFile getFile1_2() {
        return file1_2;
    }

    public void setFile1_2(UploadedFile file1_2) {
        this.file1_2 = file1_2;
    }

    public UploadedFile getFile1_3() {
        return file1_3;
    }

    public void setFile1_3(UploadedFile file1_3) {
        this.file1_3 = file1_3;
    }

    public UploadedFile getFile1_4() {
        return file1_4;
    }

    public void setFile1_4(UploadedFile file1_4) {
        this.file1_4 = file1_4;
    }

    public String getNewFile1_1() {
        return newFile1_1;
    }

    public void setNewFile1_1(String newFile1_1) {
        this.newFile1_1 = newFile1_1;
    }

    public String getNewFile1_2() {
        return newFile1_2;
    }

    public void setNewFile1_2(String newFile1_2) {
        this.newFile1_2 = newFile1_2;
    }

    public String getNewFile1_3() {
        return newFile1_3;
    }

    public void setNewFile1_3(String newFile1_3) {
        this.newFile1_3 = newFile1_3;
    }

    public String getNewFile1_4() {
        return newFile1_4;
    }

    public void setNewFile1_4(String newFile1_4) {
        this.newFile1_4 = newFile1_4;
    }

    public UploadedFile getFile2_1() {
        return file2_1;
    }

    public void setFile2_1(UploadedFile file2_1) {
        this.file2_1 = file2_1;
    }

    public UploadedFile getFile2_2() {
        return file2_2;
    }

    public void setFile2_2(UploadedFile file2_2) {
        this.file2_2 = file2_2;
    }

    public UploadedFile getFile2_3() {
        return file2_3;
    }

    public void setFile2_3(UploadedFile file2_3) {
        this.file2_3 = file2_3;
    }

    public UploadedFile getFile2_4() {
        return file2_4;
    }

    public void setFile2_4(UploadedFile file2_4) {
        this.file2_4 = file2_4;
    }

    public String getNewFile2_1() {
        return newFile2_1;
    }

    public void setNewFile2_1(String newFile2_1) {
        this.newFile2_1 = newFile2_1;
    }

    public String getNewFile2_2() {
        return newFile2_2;
    }

    public void setNewFile2_2(String newFile2_2) {
        this.newFile2_2 = newFile2_2;
    }

    public String getNewFile2_3() {
        return newFile2_3;
    }

    public void setNewFile2_3(String newFile2_3) {
        this.newFile2_3 = newFile2_3;
    }

    public String getNewFile2_4() {
        return newFile2_4;
    }

    public void setNewFile2_4(String newFile2_4) {
        this.newFile2_4 = newFile2_4;
    }

    public UploadedFile getFile3_1() {
        return file3_1;
    }

    public void setFile3_1(UploadedFile file3_1) {
        this.file3_1 = file3_1;
    }

    public UploadedFile getFile3_2() {
        return file3_2;
    }

    public void setFile3_2(UploadedFile file3_2) {
        this.file3_2 = file3_2;
    }

    public UploadedFile getFile3_3() {
        return file3_3;
    }

    public void setFile3_3(UploadedFile file3_3) {
        this.file3_3 = file3_3;
    }

    public UploadedFile getFile3_4() {
        return file3_4;
    }

    public void setFile3_4(UploadedFile file3_4) {
        this.file3_4 = file3_4;
    }

    public String getNewFile3_1() {
        return newFile3_1;
    }

    public void setNewFile3_1(String newFile3_1) {
        this.newFile3_1 = newFile3_1;
    }

    public String getNewFile3_2() {
        return newFile3_2;
    }

    public void setNewFile3_2(String newFile3_2) {
        this.newFile3_2 = newFile3_2;
    }

    public String getNewFile3_3() {
        return newFile3_3;
    }

    public void setNewFile3_3(String newFile3_3) {
        this.newFile3_3 = newFile3_3;
    }

    public String getNewFile3_4() {
        return newFile3_4;
    }

    public void setNewFile3_4(String newFile3_4) {
        this.newFile3_4 = newFile3_4;
    }

    public static Map<String, String> getCONFIG() {
        return CONFIG;
    }

    public static void setCONFIG(Map<String, String> CONFIG) {
        Q102Controller.CONFIG = CONFIG;
    }

    
    public Double getTotal1() {
        return total1;
    }

    public void setTotal1(Double total1) {
        this.total1 = total1;
    }

    public Double getTotal1_vat() {
        return total1_vat;
    }

    public void setTotal1_vat(Double total1_vat) {
        this.total1_vat = total1_vat;
    }

    public Double getTotal1_net() {
        return total1_net;
    }

    public void setTotal1_net(Double total1_net) {
        this.total1_net = total1_net;
    }

    public String getTotal1_th() {
        return total1_th;
    }

    public void setTotal1_th(String total1_th) {
        this.total1_th = total1_th;
    }

    
}