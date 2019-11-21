package com.finework.jsf.controller.quotation;

import com.finework.core.ejb.entity.SysDetail;
import com.finework.core.ejb.entity.SysMainQuotation;
import com.finework.core.ejb.entity.SysMainQuotation1;
import com.finework.core.ejb.entity.SysMainQuotation2;
import com.finework.core.ejb.entity.SysMainQuotation3;
import com.finework.core.ejb.entity.SysOrganization;
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
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named(Q101Controller.CONTROLLER_NAME)
@SessionScoped
public class Q101Controller extends BaseController{
    
  private static final Logger LOG = Logger.getLogger(Q101Controller.class);
  public static final String CONTROLLER_NAME = "q101Controller";

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
  private List<SysMainQuotation> items;
  private SysMainQuotation selected;
  private List<SysMainQuotation> printSelected;
  private SysMainQuotation1 dumpMyDetail_selected1;
  private SysMainQuotation2 dumpMyDetail_selected2;
  private SysMainQuotation3 dumpMyDetail_selected3;
  private String documentno;
  private String subject;
  private Date startDate;
  private Date toDate;
  private Double total = 0.0;
  private Double total_discount = 0.0;
  private Double total_vat = 0.0;
  private Double total_net = 0.0;
  private String total_th;
  private Double total1 = 0.0;
  private Double total1_discount = 0.0;
  private Double total1_vat = 0.0;
  private Double total1_net = 0.0;
  private String total1_th;
  private Double total2 = 0.0;
  private Double total2_discount = 0.0;
  private Double total2_vat = 0.0;
  private Double total2_net = 0.0;
  private String total2_th;
  private Double total3 = 0.0;
  private Double total3_discount = 0.0;
  private Double total3_vat = 0.0;
  private Double total3_net = 0.0;
  private String total3_th;
  private static final String NO_PRODUCT = "no_product.png";
  private UploadedFile file1;
  private String newFile1 = "no_product.png";
  private UploadedFile file2;
  private String newFile2 = "no_product.png";
  private UploadedFile file3;
  private String newFile3 = "no_product.png";
  public static Map<String, String> CONFIG;
  
  private List<SysDetail> allSysDetails;

  @PostConstruct
  public void init(){
      try {
          CONFIG = LoadConfig.loadFileDefault();
          search();
          this.allSysDetails = stockFacade.findSysDetailList();
      } catch (Exception ex) {
          JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
          LOG.error(ex);
      }
  }

  public void next(String path) {
    NaviController nav = (NaviController)JsfUtil.getManagedBeanValue("naviController");
    nav.next(path);
  }

  public void prepareCreate(String page) {
    this.selected = new SysMainQuotation();
    this.dumpMyDetail_selected1 = new SysMainQuotation1();
    this.dumpMyDetail_selected2 = new SysMainQuotation2();
    this.dumpMyDetail_selected3 = new SysMainQuotation3();
    next(page);
  }
  public void prepareEdit() {
    this.dumpMyDetail_selected1 = new SysMainQuotation1();
    this.dumpMyDetail_selected2 = new SysMainQuotation2();
    this.dumpMyDetail_selected3 = new SysMainQuotation3();
  }

  public void prepareEdit(String page) {
    if ((null != this.selected.getSysMainQuotation1List()) && (this.selected.getSysMainQuotation1List().size() > 0)) {
      Double total_ = 0.0;
      for (SysMainQuotation1 sysdetail : this.selected.getSysMainQuotation1List()) {
        total_ = Double.valueOf(total_ + sysdetail.getAmount());
      }

      this.total1 = total_;
      this.total1_discount = Double.valueOf(null != this.selected.getTotal1Discount() ? this.selected.getTotal1Discount() : 0.0);
      this.total1_vat = Double.valueOf((this.total1 - this.total1_discount) * 0.07);
      this.total1_net = Double.valueOf(this.total1 - this.total1_discount + this.total1_vat);
    }
    if ((null != this.selected.getSysMainQuotation2List()) && (this.selected.getSysMainQuotation2List().size() > 0)) {
      Double total_ = 0.0;
      for (SysMainQuotation2 sysdetail : this.selected.getSysMainQuotation2List()) {
        total_ = Double.valueOf(total_ + sysdetail.getAmount());
      }

      this.total2 = total_;
      this.total2_discount = Double.valueOf(null != this.selected.getTotal2Discount() ? this.selected.getTotal2Discount() : 0.0);
      this.total2_vat = Double.valueOf((this.total2 - this.total2_discount) * 0.07);
      this.total2_net = Double.valueOf(this.total2 - this.total2_discount + this.total2_vat);
    }

    if ((null != this.selected.getSysMainQuotation3List()) && (this.selected.getSysMainQuotation3List().size() > 0)) {
      Double total_ = 0.0;
      for (SysMainQuotation3 sysdetail : this.selected.getSysMainQuotation3List()) {
        total_ = Double.valueOf(total_ + sysdetail.getAmount());
      }

      this.total3 = total_;
      this.total3_discount = Double.valueOf(null != this.selected.getTotal3Discount() ? this.selected.getTotal3Discount() : 0.0);
      this.total3_vat = Double.valueOf((this.total3 - this.total3_discount) * 0.07);
      this.total3_net = Double.valueOf(this.total3 - this.total3_discount + this.total3_vat);
    }

    this.total = Double.valueOf(this.total1 + this.total2 + this.total3);
    this.total_discount = Double.valueOf(this.total1_discount + this.total2_discount + this.total3_discount);
    this.total_vat = Double.valueOf(this.total1_vat + this.total2_vat + this.total3_vat);
    this.total_net = Double.valueOf(this.total1_net + this.total2_net + this.total3_net);

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

  public void clearData() {
    this.selected = new SysMainQuotation();
    this.newFile1 = "no_product.png";
    this.newFile2 = "no_product.png";
    this.newFile3 = "no_product.png";
    this.total1_discount = 0.0;
    this.total2_discount = 0.0;
    this.total3_discount = 0.0;
  }

  public void create()
  {
    try
    {
      if (StringUtils.isBlank(this.selected.getSubject())) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "เรื่อง" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if (StringUtils.isBlank(this.selected.getInvite())) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "เรียน" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if (null == this.selected.getQuotationDate()) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "วันที่ทำรายการ" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if (StringUtils.isBlank(this.selected.getHeading())) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "หมวดเรื่อง" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if ((null == this.selected.getSysMainQuotation1List()) || (this.selected.getSysMainQuotation1List().isEmpty())) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "รายละเอียด" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if ((null != this.selected.getSysMainQuotation1List()) && (!this.selected.getSysMainQuotation1List().isEmpty()) && 
        (!StringUtils.equals("no_product.png", this.newFile1))) {
        this.selected.setQuotationImg1(this.newFile1);
      }

      if ((null != this.selected.getSysMainQuotation2List()) && (!this.selected.getSysMainQuotation2List().isEmpty()) && 
        (!StringUtils.equals("no_product.png", this.newFile2))) {
        this.selected.setQuotationImg2(this.newFile2);
      }

      if ((null != this.selected.getSysMainQuotation3List()) && (!this.selected.getSysMainQuotation3List().isEmpty()) && 
        (!StringUtils.equals("no_product.png", this.newFile3))) {
        this.selected.setQuotationImg3(this.newFile3);
      }

      this.selected.setTypeForm(Integer.valueOf(2));
      this.selected.setCreatedBy(this.userInfo.getAdminUser().getUsername());
      this.selected.setCreatedDt(DateTimeUtil.getSystemDate());
      this.selected.setModifiedBy(this.userInfo.getAdminUser().getUsername());
      this.selected.setModifiedDt(DateTimeUtil.getSystemDate());

      Double total1 = 0.0; Double total1_discount = 0.0; Double total_tax1 = 0.0; Double total_amount1 = 0.0;
      List detal_add = new ArrayList();
      for (SysMainQuotation1 sysQuotation : this.selected.getSysMainQuotation1List()) {
        sysQuotation.setQuotation1Id(null);
        sysQuotation.setQuotationId(this.selected);
        total1 = Double.valueOf(total1 + sysQuotation.getAmount());
        detal_add.add(sysQuotation);
      }
      total1_discount = Double.valueOf(total1 - this.total1_discount);
      total_tax1 = Double.valueOf(total1_discount * 0.07);
      total_amount1 = Double.valueOf(total1_discount + total_tax1);

      Double total2 = 0.0; Double total2_discount = 0.0; Double total_tax2 = 0.0; Double total_amount2 = 0.0;
      List detal_add2 = new ArrayList();
      if ((null != this.selected.getSysMainQuotation2List()) && (!this.selected.getSysMainQuotation2List().isEmpty())) {
        for (SysMainQuotation2 sysQuotation : this.selected.getSysMainQuotation2List()) {
          sysQuotation.setQuotation2Id(null);
          sysQuotation.setQuotationId(this.selected);
          total2 = Double.valueOf(total2 + sysQuotation.getAmount());
          detal_add2.add(sysQuotation);
        }
        total2_discount = Double.valueOf(total2 - this.total2_discount);
        total_tax2 = Double.valueOf(total2_discount * 0.07);
        total_amount2 = Double.valueOf(total2_discount + total_tax2);
      }

      Double total3 = 0.0; Double total3_discount = 0.0; Double total_tax3 = 0.0; Double total_amount3 = 0.0;
      List detal_add3 = new ArrayList();
      if ((null != this.selected.getSysMainQuotation3List()) && (!this.selected.getSysMainQuotation3List().isEmpty())) {
        for (SysMainQuotation3 sysQuotation : this.selected.getSysMainQuotation3List()) {
          sysQuotation.setQuotation3Id(null);
          sysQuotation.setQuotationId(this.selected);
          total3 = Double.valueOf(total3 + sysQuotation.getAmount());
          detal_add3.add(sysQuotation);
        }
        total3_discount = Double.valueOf(total3 - this.total3_discount);
        total_tax3 = Double.valueOf(total3_discount * 0.07);
        total_amount3 = Double.valueOf(total3_discount + total_tax3);
      }

      this.selected.setSysMainQuotation1List(detal_add);
      this.selected.setSysMainQuotation2List(detal_add2);
      this.selected.setSysMainQuotation3List(detal_add3);
      this.selected.setTotal(Double.valueOf(total1 + total2 + total3));
      this.selected.setTotal1Discount(this.total1_discount);
      this.selected.setTotal2Discount(this.total2_discount);
      this.selected.setTotal3Discount(this.total3_discount);
      this.selected.setTotalDiscount(Double.valueOf(this.total1_discount + this.total2_discount + this.total3_discount));
      this.selected.setTotalTax(Double.valueOf(total_tax1 + total_tax2 + total_tax3));
      this.selected.setTotalAmount(Double.valueOf(total_amount1 + total_amount2 + total_amount3));

      runningNoCustomer(DateTimeUtil.getSystemDate());

      this.quotationFacade.createSysMainQuotation(this.selected);

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

  public void edit()
  {
    try
    {
      this.selected.setQuotationId(null);

      if (StringUtils.isBlank(this.selected.getSubject())) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "เรื่อง" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if (StringUtils.isBlank(this.selected.getInvite())) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "เรียน" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if (null == this.selected.getQuotationDate()) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "วันที่ทำรายการ" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if (StringUtils.isBlank(this.selected.getHeading())) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "หมวดเรื่อง" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if ((null == this.selected.getSysMainQuotation1List()) || (this.selected.getSysMainQuotation1List().isEmpty())) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "รายละเอียด" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if ((null != this.selected.getSysMainQuotation1List()) && (!this.selected.getSysMainQuotation1List().isEmpty()) && 
        (!StringUtils.equals("no_product.png", this.newFile1))) {
        this.selected.setQuotationImg1(this.newFile1);
      }

      if ((null != this.selected.getSysMainQuotation2List()) && (!this.selected.getSysMainQuotation2List().isEmpty()) && 
        (!StringUtils.equals("no_product.png", this.newFile2))) {
        this.selected.setQuotationImg2(this.newFile2);
      }

      if ((null != this.selected.getSysMainQuotation3List()) && (!this.selected.getSysMainQuotation3List().isEmpty()) && 
        (!StringUtils.equals("no_product.png", this.newFile3))) {
        this.selected.setQuotationImg3(this.newFile3);
      }

      this.selected.setModifiedBy(this.userInfo.getAdminUser().getUsername());
      this.selected.setModifiedDt(DateTimeUtil.getSystemDate());

      Double total1 = 0.0; Double total1_discount = 0.0; Double total_tax1 = 0.0; Double total_amount1 = 0.0;
      List detal_add = new ArrayList();
      for (SysMainQuotation1 sysQuotation : this.selected.getSysMainQuotation1List()) {
        sysQuotation.setQuotation1Id(null);
        sysQuotation.setQuotationId(this.selected);
        total1 = Double.valueOf(total1 + sysQuotation.getAmount());
        detal_add.add(sysQuotation);
      }
      total1_discount = Double.valueOf(total1 - this.total1_discount);
      total_tax1 = Double.valueOf(total1_discount * 0.07);
      total_amount1 = Double.valueOf(total1_discount + total_tax1);

      Double total2 = 0.0; Double total2_discount = 0.0; Double total_tax2 = 0.0; Double total_amount2 = 0.0;
      List detal_add2 = new ArrayList();
      if ((null != this.selected.getSysMainQuotation2List()) && (!this.selected.getSysMainQuotation2List().isEmpty())) {
        for (SysMainQuotation2 sysQuotation : this.selected.getSysMainQuotation2List()) {
          sysQuotation.setQuotation2Id(null);
          sysQuotation.setQuotationId(this.selected);
          total2 = Double.valueOf(total2 + sysQuotation.getAmount());
          detal_add2.add(sysQuotation);
        }
        total2_discount = Double.valueOf(total2 - this.total2_discount);
        total_tax2 = Double.valueOf(total2_discount * 0.07);
        total_amount2 = Double.valueOf(total2_discount + total_tax2);
      }

      Double total3 = 0.0; Double total3_discount = 0.0; Double total_tax3 = 0.0; Double total_amount3 = 0.0;
      List detal_add3 = new ArrayList();
      if ((null != this.selected.getSysMainQuotation3List()) && (!this.selected.getSysMainQuotation3List().isEmpty())) {
        for (SysMainQuotation3 sysQuotation : this.selected.getSysMainQuotation3List()) {
          sysQuotation.setQuotation3Id(null);
          sysQuotation.setQuotationId(this.selected);
          total3 = Double.valueOf(total3 + sysQuotation.getAmount());
          detal_add3.add(sysQuotation);
        }
        total3_discount = Double.valueOf(total3 - this.total3_discount);
        total_tax3 = Double.valueOf(total3_discount * 0.07);
        total_amount3 = Double.valueOf(total3_discount + total_tax3);
      }

      this.selected.setSysMainQuotation1List(detal_add);
      this.selected.setSysMainQuotation2List(detal_add2);
      this.selected.setSysMainQuotation3List(detal_add3);
      this.selected.setSysGeneralQuotationDetailList(null);
      this.selected.setTotal(Double.valueOf(total1 + total2 + total3));
      this.selected.setTotal1Discount(this.total1_discount);
      this.selected.setTotal2Discount(this.total2_discount);
      this.selected.setTotal3Discount(this.total3_discount);
      this.selected.setTotalDiscount(Double.valueOf(this.total1_discount + this.total2_discount + this.total3_discount));
      this.selected.setTotalTax(Double.valueOf(total_tax1 + total_tax2 + total_tax3));
      this.selected.setTotalAmount(Double.valueOf(total_amount1 + total_amount2 + total_amount3));

      runningNoChildCustomer(this.selected.getDocumentno());
      this.quotationFacade.createSysMainQuotation(this.selected);

      clearData();
      clearDatatTotal1();
      clearDatatTotal2();
      clearDatatTotal3();
      clearDatatTotal();
      search();
      JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
      next("quotation/q101/index");
    } catch (Exception ex) {
      JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
      LOG.error(ex);
    }
  }

  public void delete()
  {
    try {
      this.quotationFacade.deleteSysMainQuotation(this.selected);
      search();
      JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4002"));
    } catch (Exception ex) {
      JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
      LOG.error(ex);
    }
  }

  public void checkTotalPrice()
  {
    clearDatatTotal();
    Double total1 = 0.0; Double total2 = 0.0; Double total3 = 0.0;
    Double total1_discount = 0.0; Double total2_discount = 0.0; Double total3_discount = 0.0;
    Double totaltax1 = 0.0; Double totaltax2 = 0.0; Double totaltax3 = 0.0;
    Double totalnet1 = 0.0; Double totalnet2 = 0.0; Double totalnet3 = 0.0;
    if ((null != this.selected.getSysMainQuotation1List()) && (this.selected.getSysMainQuotation1List().size() > 0)) {
      Double total_ = 0.0;
      for (SysMainQuotation1 sysdetail : this.selected.getSysMainQuotation1List()) {
        total_ = Double.valueOf(total_ + sysdetail.getAmount());
      }

      total1 = total_;
      total1_discount = Double.valueOf(total_ - this.total1_discount);
      totaltax1 = Double.valueOf(total1_discount * 0.07);
      totalnet1 = Double.valueOf(total1_discount + totaltax1);
    }
    if ((null != this.selected.getSysMainQuotation2List()) && (this.selected.getSysMainQuotation2List().size() > 0)) {
      Double total_ = 0.0;
      for (SysMainQuotation2 sysdetail : this.selected.getSysMainQuotation2List()) {
        total_ = Double.valueOf(total_ + sysdetail.getAmount());
      }

      total2 = total_;
      total2_discount = Double.valueOf(total_ - this.total1_discount);
      totaltax2 = Double.valueOf(total2_discount * 0.07);
      totalnet2 = Double.valueOf(total2_discount + totaltax2);
    }

    if ((null != this.selected.getSysMainQuotation3List()) && (this.selected.getSysMainQuotation3List().size() > 0)) {
      Double total_ = 0.0;
      for (SysMainQuotation3 sysdetail : this.selected.getSysMainQuotation3List()) {
        total_ = Double.valueOf(total_ + sysdetail.getAmount());
      }

      total3 = total_;
      total3_discount = Double.valueOf(total_ - this.total3_discount);
      totaltax3 = Double.valueOf(total3_discount * 0.07);
      totalnet3 = Double.valueOf(total3_discount + totaltax3);
    }
    this.total_discount = Double.valueOf(this.total1_discount + this.total2_discount + this.total3_discount);
    this.total = Double.valueOf(total1 + total2 + total3);
    this.total_vat = Double.valueOf(totaltax1 + totaltax2 + totaltax3);
    this.total_net = Double.valueOf(totalnet1 + totalnet2 + totalnet3);
  }

  public void clearDatatTotal() {
    this.total_discount = 0.0;
    this.total_vat = 0.0;
    this.total_net = 0.0;
    this.total = 0.0;
  }

  public void checkTotalPrice1()
  {
    Double total_ = 0.0;
    if ((null != this.selected.getSysMainQuotation1List()) && (this.selected.getSysMainQuotation1List().size() > 0)) {
      for (SysMainQuotation1 sysdetail : this.selected.getSysMainQuotation1List()) {
        total_ = Double.valueOf(total_ + sysdetail.getAmount());
      }

      this.total1 = total_;
      Double totaldiscount = Double.valueOf(total_ - this.total1_discount);
      this.total1_vat = Double.valueOf(totaldiscount * 0.07);
      this.total1_net = Double.valueOf(totaldiscount + this.total1_vat);
    } else {
      clearDatatTotal1();
    }
    checkTotalPrice();
  }
  public void clearDatatTotal1() {
    this.total1_discount = 0.0;
    this.total1_vat = 0.0;
    this.total1_net = 0.0;
    this.total1 = 0.0;
  }

  public void checkTotalPrice2()
  {
    Double total_ = 0.0;
    if ((null != this.selected.getSysMainQuotation2List()) && (this.selected.getSysMainQuotation2List().size() > 0)) {
      for (SysMainQuotation2 sysdetail : this.selected.getSysMainQuotation2List()) {
        total_ = Double.valueOf(total_ + sysdetail.getAmount());
      }

      this.total2 = total_;
      Double totaldiscount = Double.valueOf(total_ - this.total2_discount);
      this.total2_vat = Double.valueOf(totaldiscount * 0.07);
      this.total2_net = Double.valueOf(totaldiscount + this.total2_vat);
    } else {
      clearDatatTotal2();
    }
    checkTotalPrice();
  }
  public void clearDatatTotal2() {
    this.total2_discount = 0.0;
    this.total2_vat = 0.0;
    this.total2_net = 0.0;
    this.total2 = 0.0;
  }

  public void checkTotalPrice3()
  {
    Double total_ = 0.0;
    if ((null != this.selected.getSysMainQuotation3List()) && (this.selected.getSysMainQuotation3List().size() > 0)) {
      for (SysMainQuotation3 sysdetail : this.selected.getSysMainQuotation3List()) {
        total_ = Double.valueOf(total_ + sysdetail.getAmount());
      }

      this.total3 = total_;
      Double totaldiscount = Double.valueOf(total_ - this.total3_discount);
      this.total3_vat = Double.valueOf(totaldiscount * 0.07);
      this.total3_net = Double.valueOf(totaldiscount + this.total3_vat);
    } else {
      clearDatatTotal3();
    }
    checkTotalPrice();
  }
  public void clearDatatTotal3() {
    this.total3_discount = 0.0;
    this.total3_vat = 0.0;
    this.total3_net = 0.0;
    this.total3 = 0.0;
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
      this.items = this.quotationFacade.findSysMainQuotationListByCriteria(this.documentno, this.subject, this.startDate, this.toDate, Integer.valueOf(2));

      this.printSelected = new ArrayList();
    } catch (Exception ex) {
      LOG.error(ex);
    }
  }

  public void searchUserGroup()
  {
  }

  public void addDetail1()
  {
    try
    {
      if ((this.dumpMyDetail_selected1.getUnit() == null) || 
        (this.dumpMyDetail_selected1
        .getVolume() == null)) {
        return;
      }

      if ((this.dumpMyDetail_selected1.getValueUnit() == null) && (this.dumpMyDetail_selected1.getValueUnit() <= 0.0)) {
        return;
      }

      Double total = 0.0; Double instalUnit = 0.0;
      instalUnit = Double.valueOf((this.dumpMyDetail_selected1.getInstallUnit() != null) && (this.dumpMyDetail_selected1.getInstallUnit() > 0.0) ? this.dumpMyDetail_selected1.getInstallUnit() : 0.0);
      if ((this.dumpMyDetail_selected1.getValueUnit() != null) && (this.dumpMyDetail_selected1.getValueUnit() > 0.0))
        total = Double.valueOf(this.dumpMyDetail_selected1.getVolume() * this.dumpMyDetail_selected1.getValueUnit() + instalUnit);
      else {
        return;
      }

      if (this.selected.getSysMainQuotation1List() == null) {
        this.selected.setSysMainQuotation1List(new ArrayList());
      }

      this.dumpMyDetail_selected1.setAmount(total);

      this.selected.getSysMainQuotation1List().add(this.dumpMyDetail_selected1);

      checkTotalPrice1();
      clearData_sysDetail1();
      checkTotalPrice();
    } catch (Exception ex) {
      JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
      LOG.error(ex);
    }
  }

  public void deleteDetail1() {
    try {
      this.selected.getSysMainQuotation1List().remove(this.dumpMyDetail_selected1);
      checkTotalPrice1();
      checkTotalPrice();
      clearData_sysDetail1();
    } catch (Exception ex) {
      JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
      LOG.error(ex);
    }
  }

  public void addDetail2()
  {
    try {
      if ((this.dumpMyDetail_selected2.getUnit() == null) || 
        (this.dumpMyDetail_selected2
        .getVolume() == null)) {
        return;
      }

      if ((this.dumpMyDetail_selected2.getValueUnit() == null) && (this.dumpMyDetail_selected2.getValueUnit() <= 0.0)) {
        return;
      }

      Double total = 0.0; Double instalUnit = 0.0;
      instalUnit = Double.valueOf((this.dumpMyDetail_selected2.getInstallUnit() != null) && (this.dumpMyDetail_selected2.getInstallUnit() > 0.0) ? this.dumpMyDetail_selected2.getInstallUnit() : 0.0);
      if ((this.dumpMyDetail_selected2.getValueUnit() != null) && (this.dumpMyDetail_selected2.getValueUnit() > 0.0))
        total = Double.valueOf(this.dumpMyDetail_selected2.getVolume() * this.dumpMyDetail_selected2.getValueUnit() + instalUnit);
      else {
        return;
      }

      if (this.selected.getSysMainQuotation2List() == null) {
        this.selected.setSysMainQuotation2List(new ArrayList());
      }

      this.dumpMyDetail_selected2.setAmount(total);

      this.selected.getSysMainQuotation2List().add(this.dumpMyDetail_selected2);

      checkTotalPrice2();
      clearData_sysDetail2();
      checkTotalPrice();
    } catch (Exception ex) {
      JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
      LOG.error(ex);
    }
  }

  public void deleteDetail2() {
    try {
      this.selected.getSysMainQuotation2List().remove(this.dumpMyDetail_selected2);
      checkTotalPrice2();
      clearData_sysDetail2();
      checkTotalPrice();
    } catch (Exception ex) {
      JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
      LOG.error(ex);
    }
  }

  public void addDetail3()
  {
    try
    {
      if ((this.dumpMyDetail_selected3.getUnit() == null) || 
        (this.dumpMyDetail_selected3
        .getVolume() == null)) {
        return;
      }

      if ((this.dumpMyDetail_selected3.getValueUnit() == null) && (this.dumpMyDetail_selected3.getValueUnit() <= 0.0)) {
        return;
      }

      Double total = 0.0; Double instalUnit = 0.0;
      instalUnit = Double.valueOf((this.dumpMyDetail_selected3.getInstallUnit() != null) && (this.dumpMyDetail_selected3.getInstallUnit() > 0.0) ? this.dumpMyDetail_selected3.getInstallUnit() : 0.0);
      if ((this.dumpMyDetail_selected3.getValueUnit() != null) && (this.dumpMyDetail_selected3.getValueUnit() > 0.0))
        total = Double.valueOf(this.dumpMyDetail_selected3.getVolume() * this.dumpMyDetail_selected3.getValueUnit() + instalUnit);
      else {
        return;
      }

      if (this.selected.getSysMainQuotation3List() == null) {
        this.selected.setSysMainQuotation3List(new ArrayList());
      }

      this.dumpMyDetail_selected3.setAmount(total);

      this.selected.getSysMainQuotation3List().add(this.dumpMyDetail_selected3);

      checkTotalPrice3();
      clearData_sysDetail3();
      checkTotalPrice();
    } catch (Exception ex) {
      JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
      LOG.error(ex);
    }
  }

  public void deleteDetail3() {
    try {
      this.selected.getSysMainQuotation3List().remove(this.dumpMyDetail_selected3);
      checkTotalPrice3();
      clearData_sysDetail3();
      checkTotalPrice();
    } catch (Exception ex) {
      JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
      LOG.error(ex);
    }
  }

  public String convertPriceToString(Double totalprice) {
    if (totalprice == 0.0) {
      return "";
    }
    return new ThaiBaht().getText(totalprice);
  }

  public void clearData_sysDetail1()
  {
    this.dumpMyDetail_selected1 = new SysMainQuotation1();
  }
  public void clearData_sysDetail2() {
    this.dumpMyDetail_selected2 = new SysMainQuotation2();
  }
  public void clearData_sysDetail3() {
    this.dumpMyDetail_selected3 = new SysMainQuotation3();
  }

  public void action(AjaxBehaviorEvent event)
  {
    if ((null != this.selected.getSysMainQuotation1List()) && (this.selected.getSysMainQuotation1List().size() > 0)) {
      this.selected.getSysMainQuotation1List().clear();
      checkTotalPrice1();
      clearData_sysDetail1();
    }
    if ((null != this.selected.getSysMainQuotation2List()) && (this.selected.getSysMainQuotation2List().size() > 0)) {
      this.selected.getSysMainQuotation2List().clear();
      checkTotalPrice2();
      clearData_sysDetail2();
    }
    if ((null != this.selected.getSysMainQuotation3List()) && (this.selected.getSysMainQuotation3List().size() > 0)) {
      this.selected.getSysMainQuotation3List().clear();
      checkTotalPrice3();
      clearData_sysDetail3();
    }
  }

  public void handleKeyEvent()
  {
  }

  public void runningNoCustomer(Date date)
  {
    String yearMonth = DateTimeUtil.cvtDateForShow(date, "yyMM", new Locale("th", "TH"));
    String sequence_no = this.sequence.runningNoNewQuoation(yearMonth);
    this.selected.setDocumentno(sequence_no);
  }

  public void runningNoChildCustomer(String documentno) {
    String sequence_no = this.sequence.runningNoNewChildQuoation(documentno);
    this.selected.setChildDocumentno(sequence_no);
  }

  public void handleFileUpload1(FileUploadEvent event)
  {
    this.file1 = event.getFile();
    if (this.file1 != null)
      try {
        this.newFile1 = UploadUtil.uploadFile(this.file1, "quotation", null, UUID.randomUUID().toString());
        Thread.sleep(1500L);
      } catch (Exception ex) {
        LOG.error(ex);
      }
  }

  public void handleFileUpload2(FileUploadEvent event) {
    this.file2 = event.getFile();
    if (this.file2 != null)
      try {
        this.newFile2 = UploadUtil.uploadFile(this.file2, "quotation", null, UUID.randomUUID().toString());
        Thread.sleep(1500L);
      } catch (Exception ex) {
        LOG.error(ex);
      }
  }

  public void handleFileUpload3(FileUploadEvent event) {
    this.file3 = event.getFile();
    if (this.file3 != null)
      try {
        this.newFile3 = UploadUtil.uploadFile(this.file3, "quotation", null, UUID.randomUUID().toString());
        Thread.sleep(1500L);
      } catch (Exception ex) {
        LOG.error(ex);
      }
  }

  public void reportPDF()
  {
    try
    {
      ReportUtil report = new ReportUtil();
      List reportList_ = new ArrayList();
      List reportList_main = new ArrayList();
      List reportList1 = new ArrayList();
      List reportList2 = new ArrayList();
      List reportList3 = new ArrayList();
      boolean cklist1 = false; boolean cklist2 = false; boolean cklist3 = false;
      SysMainQuotation rpt_sysDelivery = this.quotationFacade.findByPK(this.selected.getQuotationId());

      List<SysMainQuotation1> list = this.quotationFacade.findSysQuotationDetail1ListByCriteria(this.selected.getQuotationId());
      Double total1 = 0.0; Double total1_discount = 0.0; Double total1_vat = 0.0; Double total1_net = 0.0; Double total1_ = 0.0;

      for (SysMainQuotation1 to : list) {
        QuotationReportBean bean = new QuotationReportBean();
        bean.setSeq(to.getSeq() != null ? to.getSeq().toString() : "");

        bean.setDetail(to.getDetail());
        bean.setVolume(NumberUtils.numberFormat(to.getVolume(), "#,##0.00"));
        bean.setUnit(to.getUnit());
        bean.setValueUnit(NumberUtils.numberFormat(to.getValueUnit(), "#,##0.00"));
        bean.setInstallUnit(NumberUtils.numberFormat(to.getInstallUnit(), "#,##0.00"));
        bean.setAmount(NumberUtils.numberFormat(to.getAmount(), "#,##0.00"));
        total1_ = Double.valueOf(total1_ + to.getAmount());

        reportList1.add(bean);
      }
      for (int i = 0; i < 4 - list.size(); i++) reportList1.add(new QuotationReportBean("", "", "", "", "", "", ""));
      total1 = total1_;
      total1_discount = Double.valueOf(total1 - (null != rpt_sysDelivery.getTotal1Discount() ? rpt_sysDelivery.getTotal1Discount() : 0.0));
      total1_vat = Double.valueOf(total1_discount * 0.07);
      total1_net = Double.valueOf(total1_discount + total1_vat);
      cklist1 = true;

      List<SysMainQuotation2> list2 = this.quotationFacade.findSysQuotationDetail2ListByCriteria(this.selected.getQuotationId());
      Double total2 = 0.0; Double total2_discount = 0.0; Double total2_vat = 0.0; Double total2_net = 0.0; Double total2_ = 0.0;
      if ((list2 != null) && (!list2.isEmpty()))
      {
        for (SysMainQuotation2 to : list2) {
          QuotationReportBean bean = new QuotationReportBean();
          bean.setSeq(to.getSeq() != null ? to.getSeq().toString() : "");

          bean.setDetail(to.getDetail());
          bean.setVolume(NumberUtils.numberFormat(to.getVolume(), "#,##0.00"));
          bean.setUnit(to.getUnit());
          bean.setValueUnit(NumberUtils.numberFormat(to.getValueUnit(), "#,##0.00"));
          bean.setInstallUnit(NumberUtils.numberFormat(to.getInstallUnit(), "#,##0.00"));
          bean.setAmount(NumberUtils.numberFormat(to.getAmount(), "#,##0.00"));
          total2_ = Double.valueOf(total2_ + to.getAmount());

          reportList2.add(bean);
        }

        for (int i = 0; i < 4 - list2.size(); i++) reportList2.add(new QuotationReportBean("", "", "", "", "", "", ""));
        total2 = total2_;
        total2_discount = Double.valueOf(total2 - (null != rpt_sysDelivery.getTotal2Discount() ? rpt_sysDelivery.getTotal2Discount() : 0.0));
        total2_vat = Double.valueOf(total2_discount * 0.07);
        total2_net = Double.valueOf(total2_discount + total2_vat);
        cklist2 = true;
      }

      List<SysMainQuotation3> list3 = this.quotationFacade.findSysQuotationDetail3ListByCriteria(this.selected.getQuotationId());
      Double total3 = 0.0; Double total3_discount = 0.0; Double total3_vat = 0.0; Double total3_net = 0.0; Double total3_ = 0.0;
      if ((list3 != null) && (!list3.isEmpty())) {
        for (SysMainQuotation3 to : list3)
        {
          QuotationReportBean bean = new QuotationReportBean();
          bean.setSeq(to.getSeq() != null ? to.getSeq().toString() : "");

          bean.setDetail(to.getDetail());
          bean.setVolume(NumberUtils.numberFormat(to.getVolume(), "#,##0.00"));
          bean.setUnit(to.getUnit());
          bean.setValueUnit(NumberUtils.numberFormat(to.getValueUnit(), "#,##0.00"));
          bean.setInstallUnit(NumberUtils.numberFormat(to.getInstallUnit(), "#,##0.00"));
          bean.setAmount(NumberUtils.numberFormat(to.getAmount(), "#,##0.00"));
          total3_ = Double.valueOf(total3_ + to.getAmount());

          reportList3.add(bean);
        }

        for (int i = 0; i < 4 - list3.size(); i++) reportList3.add(new QuotationReportBean("", "", "", "", "", "", ""));
        total3 = total3_;
        total3_discount = Double.valueOf(total3 - (null != rpt_sysDelivery.getTotal3Discount() ? rpt_sysDelivery.getTotal3Discount() : 0.0));
        total3_vat = Double.valueOf(total3_discount * 0.07);
        total3_net = Double.valueOf(total3_discount + total3_vat);
        cklist3 = true;
      }

      reportList_main.add(new QuotationReportBean("", "", "", "", "", "", ""));
      reportList_.add(reportList_main);
      reportList_.add(reportList1);
      reportList_.add(reportList2);
      reportList_.add(reportList3);
      HashMap map = new HashMap();
      SysOrganization org = this.organizationFacade.findSysOrganizationByStatus("Y");
      map.put("org_name_th", org.getOrgNameTh());
      map.put("org_name_eng", org.getOrgNameEng());
      map.put("org_address_th", org.getOrgAddressTh());
      map.put("org_tel", org.getOrgTel());
      map.put("org_branch", org.getOrgBranch());
      map.put("org_taxid", org.getOrgTax());
      map.put("org_bank", org.getOrgBank());
      map.put("org_bank_name", org.getOrgBankName());
      map.put("org_recall", org.getOrgRecall());

      map.put("documentno", null != rpt_sysDelivery.getDocumentno() ? rpt_sysDelivery.getDocumentno() : "-");
      map.put("quotation_date", DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getQuotationDate(), "dd/MM/yyyy", new Locale("th", "TH")));
      map.put("subject", rpt_sysDelivery.getSubject());
      map.put("invite", rpt_sysDelivery.getInvite());
      map.put("email", null != rpt_sysDelivery.getEmail() ? rpt_sysDelivery.getEmail() : "-");
      map.put("remark", StringUtils.isNotBlank(rpt_sysDelivery.getRemark()) ? "                " + rpt_sysDelivery.getRemark() : "-");
      map.put("heading", rpt_sysDelivery.getHeading());

      map.put("total1", NumberUtils.numberFormat(total1, "#,##0.00"));
      map.put("total1_discount", NumberUtils.numberFormat(Double.valueOf(null != rpt_sysDelivery.getTotal1Discount() ? rpt_sysDelivery.getTotal1Discount() : 0.0), "#,##0.00"));
      map.put("total1_vat", NumberUtils.numberFormat(total1_vat, "#,##0.00"));
      map.put("total1_net", NumberUtils.numberFormat(total1_net, "#,##0.00"));
      map.put("total1_char", total1_net == 0.0 ? "" : new ThaiBaht().getText(total1_net));

      map.put("total2", NumberUtils.numberFormat(total2, "#,##0.00"));
      map.put("total2_discount", NumberUtils.numberFormat(Double.valueOf(null != rpt_sysDelivery.getTotal2Discount() ? rpt_sysDelivery.getTotal2Discount() : 0.0), "#,##0.00"));
      map.put("total2_vat", NumberUtils.numberFormat(total2_vat, "#,##0.00"));
      map.put("total2_net", NumberUtils.numberFormat(total2_net, "#,##0.00"));
      map.put("total2_char", total2_net == 0.0 ? "" : new ThaiBaht().getText(total2_net));

      map.put("total3", NumberUtils.numberFormat(total3, "#,##0.00"));
      map.put("total3_discount", NumberUtils.numberFormat(Double.valueOf(null != rpt_sysDelivery.getTotal3Discount() ? rpt_sysDelivery.getTotal3Discount() : 0.0), "#,##0.00"));
      map.put("total3_vat", NumberUtils.numberFormat(total3_vat, "#,##0.00"));
      map.put("total3_net", NumberUtils.numberFormat(total3_net, "#,##0.00"));
      map.put("total3_char", total3_net == 0.0 ? "" : new ThaiBaht().getText(total3_net));

      map.put("price_total", NumberUtils.numberFormat(Double.valueOf(total1_net + total2_net + total3_net), "#,##0.00"));
      map.put("price_char", total1 == 0.0 ? "" : new ThaiBaht().getText(total1_net + total2_net + total3_net));
      map.put("approve", null != rpt_sysDelivery.getApprove() ? rpt_sysDelivery.getApprove() : "-");

      String path = ((String)CONFIG.get("images.url")).concat("/quotation/");
      map.put("reportCode", "Q101");
      if ((cklist1) && (cklist2) && (cklist3))
        report.exportSubReportQ101("q101", new String[] { "Q101Report3", "Q101SubReport1", "Q101SubReport2", "Q101SubReport3" }, "Q101", map, reportList_, new String[] { 
            (null!=rpt_sysDelivery.getQuotationImg1())? path + rpt_sysDelivery.getQuotationImg1():null, 
            (null!=rpt_sysDelivery.getQuotationImg2())? path + rpt_sysDelivery.getQuotationImg2():null,
            (null!=rpt_sysDelivery.getQuotationImg3())? path + rpt_sysDelivery.getQuotationImg3():null });
      else if ((cklist1) && (cklist2))
        report.exportSubReportQ101("q101", new String[] { "Q101Report2", "Q101SubReport2_1", "Q101SubReport2_2" }, "Q101", map, reportList_, new String[] { 
            (null!=rpt_sysDelivery.getQuotationImg1())? path + rpt_sysDelivery.getQuotationImg1():null, 
            (null!=rpt_sysDelivery.getQuotationImg2())? path + rpt_sysDelivery.getQuotationImg2():null, "" });
      else
          report.exportSubReportQ101("q101", new String[] { "Q101Report1", "Q101SubReport1" }, "Q101", map, reportList_, 
                  new String[] { (null!=rpt_sysDelivery.getQuotationImg1())? path + rpt_sysDelivery.getQuotationImg1():null, "", "" });
    }
    catch (Exception ex) {
      JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
      LOG.error(ex);
    }
  }

  public void printPdfMuti()
  {
    try {
      List jasperPrintList = new ArrayList();
      Collections.sort(this.printSelected, new SysMainQuotation());
      for (SysMainQuotation sysQuotation : this.printSelected) {
        ReportUtil report = new ReportUtil();
        List reportList_ = new ArrayList();
        List reportList_main = new ArrayList();
        List reportList1 = new ArrayList();
        List reportList2 = new ArrayList();
        List reportList3 = new ArrayList();
        boolean cklist1 = false; boolean cklist2 = false; boolean cklist3 = false;
        SysMainQuotation rpt_sysDelivery = this.quotationFacade.findByPK(sysQuotation.getQuotationId());

        List<SysMainQuotation1> list = this.quotationFacade.findSysQuotationDetail1ListByCriteria(sysQuotation.getQuotationId());
        Double total1 = 0.0; Double total1_vat = 0.0; Double total1_net = 0.0; Double total1_ = 0.0;

        for (SysMainQuotation1 to : list) {
          QuotationReportBean bean = new QuotationReportBean();
          bean.setSeq(to.getSeq() != null ? to.getSeq().toString() : "");

          bean.setDetail(to.getDetail());
          bean.setVolume(NumberUtils.numberFormat(to.getVolume(), "#,##0.00"));
          bean.setUnit(to.getUnit());
          bean.setValueUnit(NumberUtils.numberFormat(to.getValueUnit(), "#,##0.00"));
          bean.setInstallUnit(NumberUtils.numberFormat(to.getInstallUnit(), "#,##0.00"));
          bean.setAmount(NumberUtils.numberFormat(to.getAmount(), "#,##0.00"));
          total1_ = Double.valueOf(total1_ + to.getAmount());

          reportList1.add(bean);
        }
        for (int i = 0; i < 4 - list.size(); i++) reportList1.add(new QuotationReportBean("", "", "", "", "", "", ""));
        total1 = total1_;
        total1_vat = Double.valueOf(total1_ * 0.07);
        total1_net = Double.valueOf(total1_ + total1_vat);
        cklist1 = true;

        List<SysMainQuotation2> list2 = this.quotationFacade.findSysQuotationDetail2ListByCriteria(sysQuotation.getQuotationId());
        Double total2 = 0.0; Double total2_vat = 0.0; Double total2_net = 0.0; Double total2_ = 0.0;
        if ((list2 != null) && (!list2.isEmpty()))
        {
          for (SysMainQuotation2 to : list2) {
            QuotationReportBean bean = new QuotationReportBean();
            bean.setSeq(to.getSeq() != null ? to.getSeq().toString() : "");

            bean.setDetail(to.getDetail());
            bean.setVolume(NumberUtils.numberFormat(to.getVolume(), "#,##0.00"));
            bean.setUnit(to.getUnit());
            bean.setValueUnit(NumberUtils.numberFormat(to.getValueUnit(), "#,##0.00"));
            bean.setInstallUnit(NumberUtils.numberFormat(to.getInstallUnit(), "#,##0.00"));
            bean.setAmount(NumberUtils.numberFormat(to.getAmount(), "#,##0.00"));
            total2_ = Double.valueOf(total2_ + to.getAmount());

            reportList2.add(bean);
          }
          for (int i = 0; i < 4 - list2.size(); i++) reportList2.add(new QuotationReportBean("", "", "", "", "", "", ""));
          total2 = total2_;
          total2_vat = Double.valueOf(total2_ * 0.07);
          total2_net = Double.valueOf(total2_ + total2_vat);
          cklist2 = true;
        }

        List<SysMainQuotation3> list3 = this.quotationFacade.findSysQuotationDetail3ListByCriteria(sysQuotation.getQuotationId());
        Double total3 = 0.0; Double total3_vat = 0.0; Double total3_net = 0.0; Double total3_ = 0.0;
        if ((list3 != null) && (!list3.isEmpty())) {
          for (SysMainQuotation3 to : list3)
          {
            QuotationReportBean bean = new QuotationReportBean();
            bean.setSeq(to.getSeq() != null ? to.getSeq().toString() : "");

            bean.setDetail(to.getDetail());
            bean.setVolume(NumberUtils.numberFormat(to.getVolume(), "#,##0.00"));
            bean.setUnit(to.getUnit());
            bean.setValueUnit(NumberUtils.numberFormat(to.getValueUnit(), "#,##0.00"));
            bean.setInstallUnit(NumberUtils.numberFormat(to.getInstallUnit(), "#,##0.00"));
            bean.setAmount(NumberUtils.numberFormat(to.getAmount(), "#,##0.00"));
            total3_ = Double.valueOf(total3_ + to.getAmount());

            reportList3.add(bean);
          }
          for (int i = 0; i < 4 - list3.size(); i++) reportList3.add(new QuotationReportBean("", "", "", "", "", "", ""));
          total3 = total3_;
          total3_vat = Double.valueOf(total3_ * 0.07);
          total3_net = Double.valueOf(total3_ + total3_vat);
          cklist3 = true;
        }

        reportList_main.add(new QuotationReportBean("", "", "", "", "", "", ""));
        reportList_.add(reportList_main);
        reportList_.add(reportList1);
        reportList_.add(reportList2);
        reportList_.add(reportList3);
        HashMap map = new HashMap();
        SysOrganization org = this.organizationFacade.findSysOrganizationByStatus("Y");
        map.put("org_name_th", org.getOrgNameTh());
        map.put("org_name_eng", org.getOrgNameEng());
        map.put("org_address_th", org.getOrgAddressTh());
        map.put("org_tel", org.getOrgTel());
        map.put("org_branch", org.getOrgBranch());
        map.put("org_taxid", org.getOrgTax());
        map.put("org_bank", org.getOrgBank());
        map.put("org_bank_name", org.getOrgBankName());
        map.put("org_recall", org.getOrgRecall());

        map.put("documentno", null != rpt_sysDelivery.getDocumentno() ? rpt_sysDelivery.getDocumentno() : "-");
        map.put("quotation_date", DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getQuotationDate(), "dd/MM/yyyy", new Locale("th", "TH")));
        map.put("subject", rpt_sysDelivery.getSubject());
        map.put("invite", rpt_sysDelivery.getInvite());
        map.put("email", null != rpt_sysDelivery.getEmail() ? rpt_sysDelivery.getEmail() : "-");
        map.put("remark", StringUtils.isNotBlank(rpt_sysDelivery.getRemark()) ? "                " + rpt_sysDelivery.getRemark() : "-");
        map.put("heading", rpt_sysDelivery.getHeading());

        map.put("total1", NumberUtils.numberFormat(total1, "#,##0.00"));
        map.put("total1_vat", NumberUtils.numberFormat(total1_vat, "#,##0.00"));
        map.put("total1_net", NumberUtils.numberFormat(total1_net, "#,##0.00"));
        map.put("total1_char", total1_net == 0.0 ? "" : new ThaiBaht().getText(total1_net));

        map.put("total2", NumberUtils.numberFormat(total2, "#,##0.00"));
        map.put("total2_vat", NumberUtils.numberFormat(total2_vat, "#,##0.00"));
        map.put("total2_net", NumberUtils.numberFormat(total2_net, "#,##0.00"));
        map.put("total2_char", total2_net == 0.0 ? "" : new ThaiBaht().getText(total2_net));

        map.put("total3", NumberUtils.numberFormat(total3, "#,##0.00"));
        map.put("total3_vat", NumberUtils.numberFormat(total3_vat, "#,##0.00"));
        map.put("total3_net", NumberUtils.numberFormat(total3_net, "#,##0.00"));
        map.put("total3_char", total3_net == 0.0 ? "" : new ThaiBaht().getText(total3_net));

        map.put("price_total", NumberUtils.numberFormat(Double.valueOf(total1_net + total2_net + total3_net), "#,##0.00"));
        map.put("price_char", total1 == 0.0 ? "" : new ThaiBaht().getText(total1_net + total2_net + total3_net));
        map.put("approve", null != rpt_sysDelivery.getApprove() ? rpt_sysDelivery.getApprove() : "-");

        JasperPrint print = null;
        String path = ((String)CONFIG.get("images.url")).concat("/quotation/");
        map.put("reportCode", "Q101");
        if ((cklist1) && (cklist2) && (cklist3))
          print = report.exportSubReportQ101mearge("q101", new String[] { "Q101Report3", "Q101SubReport1", "Q101SubReport2", "Q101SubReport3" }, "Q101", map, reportList_, new String[] { 
             (null != rpt_sysDelivery.getQuotationImg1()) ? path + rpt_sysDelivery.getQuotationImg1() : null,
             (null != rpt_sysDelivery.getQuotationImg2()) ? path + rpt_sysDelivery.getQuotationImg2() : null,
             (null != rpt_sysDelivery.getQuotationImg3()) ? path + rpt_sysDelivery.getQuotationImg3() : null});
        else if ((cklist1) && (cklist2))
          print = report.exportSubReportQ101mearge("q101", new String[] { "Q101Report2", "Q101SubReport2_1", "Q101SubReport2_2" }, "Q101", map, reportList_, new String[] { 
             (null != rpt_sysDelivery.getQuotationImg1()) ? path + rpt_sysDelivery.getQuotationImg1() : null,
             (null != rpt_sysDelivery.getQuotationImg2()) ? path + rpt_sysDelivery.getQuotationImg2() : null, "" });
        else {
          print = report.exportSubReportQ101mearge("q101", new String[] { "Q101Report1", "Q101SubReport1" }, "Q101", map, reportList_, new String[] { 
             (null != rpt_sysDelivery.getQuotationImg1()) ? path + rpt_sysDelivery.getQuotationImg1() : null, "", "" });
        }
        jasperPrintList.add(print);
      }
      if (!this.printSelected.isEmpty()) {
        String pdfCode = "Q101";
        String pdfName = pdfCode.concat("-").concat(DateTimeUtil.dateToString(DateTimeUtil.currentDate(), "yyyyMMddHHmmss"));
        ReportUtil report = new ReportUtil();
        report.exportMearge(pdfName, jasperPrintList);
      }
      init();
    } catch (Exception ex) {
      JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
      LOG.error(ex);
    }
  }

  public StockFacade getStockFacade() {
    return this.stockFacade;
  }

  public void setStockFacade(StockFacade stockFacade) {
    this.stockFacade = stockFacade;
  }

  public UserInfoController getUserInfo() {
    return this.userInfo;
  }

  public void setUserInfo(UserInfoController userInfo) {
    this.userInfo = userInfo;
  }

  public List<SysMainQuotation> getItems() {
    return this.items;
  }

  public void setItems(List<SysMainQuotation> items) {
    this.items = items;
  }

  public SysMainQuotation getSelected() {
    return this.selected;
  }

  public void setSelected(SysMainQuotation selected) {
    this.selected = selected;
  }

  public List<SysMainQuotation> getPrintSelected() {
    return this.printSelected;
  }

  public void setPrintSelected(List<SysMainQuotation> printSelected) {
    this.printSelected = printSelected;
  }

  public SysMainQuotation1 getDumpMyDetail_selected1() {
    return this.dumpMyDetail_selected1;
  }

  public void setDumpMyDetail_selected1(SysMainQuotation1 dumpMyDetail_selected1) {
    this.dumpMyDetail_selected1 = dumpMyDetail_selected1;
  }

  public String getDocumentno()
  {
    return this.documentno;
  }

  public void setDocumentno(String documentno) {
    this.documentno = documentno;
  }

  public String getSubject() {
    return this.subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public Date getStartDate() {
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

  public Double getTotal() {
    return this.total;
  }

  public void setTotal(Double total) {
    this.total = total;
  }

  public Double getTotal_vat() {
    return this.total_vat;
  }

  public void setTotal_vat(Double total_vat) {
    this.total_vat = total_vat;
  }

  public Double getTotal_net() {
    return this.total_net;
  }

  public void setTotal_net(Double total_net) {
    this.total_net = total_net;
  }

  public String getTotal_th() {
    return this.total_th;
  }

  public void setTotal_th(String total_th) {
    this.total_th = total_th;
  }

  public Double getTotal2() {
    return this.total2;
  }

  public void setTotal2(Double total2) {
    this.total2 = total2;
  }

  public Double getTotal2_vat() {
    return this.total2_vat;
  }

  public void setTotal2_vat(Double total2_vat) {
    this.total2_vat = total2_vat;
  }

  public Double getTotal2_net() {
    return this.total2_net;
  }

  public void setTotal2_net(Double total2_net) {
    this.total2_net = total2_net;
  }

  public String getTotal2_th() {
    return this.total2_th;
  }

  public void setTotal2_th(String total2_th) {
    this.total2_th = total2_th;
  }

  public Double getTotal3() {
    return this.total3;
  }

  public void setTotal3(Double total3) {
    this.total3 = total3;
  }

  public Double getTotal3_vat() {
    return this.total3_vat;
  }

  public void setTotal3_vat(Double total3_vat) {
    this.total3_vat = total3_vat;
  }

  public Double getTotal3_net() {
    return this.total3_net;
  }

  public void setTotal3_net(Double total3_net) {
    this.total3_net = total3_net;
  }

  public String getTotal3_th() {
    return this.total3_th;
  }

  public void setTotal3_th(String total3_th) {
    this.total3_th = total3_th;
  }

  public SysMainQuotation2 getDumpMyDetail_selected2() {
    return this.dumpMyDetail_selected2;
  }

  public void setDumpMyDetail_selected2(SysMainQuotation2 dumpMyDetail_selected2) {
    this.dumpMyDetail_selected2 = dumpMyDetail_selected2;
  }

  public SysMainQuotation3 getDumpMyDetail_selected3() {
    return this.dumpMyDetail_selected3;
  }

  public void setDumpMyDetail_selected3(SysMainQuotation3 dumpMyDetail_selected3) {
    this.dumpMyDetail_selected3 = dumpMyDetail_selected3;
  }

  public String getNewFile1() {
    return this.newFile1;
  }

  public void setNewFile1(String newFile1) {
    this.newFile1 = newFile1;
  }

  public String getNewFile2() {
    return this.newFile2;
  }

  public void setNewFile2(String newFile2) {
    this.newFile2 = newFile2;
  }

  public String getNewFile3() {
    return this.newFile3;
  }

  public void setNewFile3(String newFile3) {
    this.newFile3 = newFile3;
  }

  public Double getTotal1() {
    return this.total1;
  }

  public void setTotal1(Double total1) {
    this.total1 = total1;
  }

  public Double getTotal1_vat() {
    return this.total1_vat;
  }

  public void setTotal1_vat(Double total1_vat) {
    this.total1_vat = total1_vat;
  }

  public Double getTotal1_net() {
    return this.total1_net;
  }

  public void setTotal1_net(Double total1_net) {
    this.total1_net = total1_net;
  }

  public String getTotal1_th() {
    return this.total1_th;
  }

  public void setTotal1_th(String total1_th) {
    this.total1_th = total1_th;
  }

  public Double getTotal_discount() {
    return this.total_discount;
  }

  public void setTotal_discount(Double total_discount) {
    this.total_discount = total_discount;
  }

  public Double getTotal1_discount() {
    return this.total1_discount;
  }

  public void setTotal1_discount(Double total1_discount) {
    this.total1_discount = total1_discount;
  }

  public Double getTotal2_discount() {
    return this.total2_discount;
  }

  public void setTotal2_discount(Double total2_discount) {
    this.total2_discount = total2_discount;
  }

  public Double getTotal3_discount() {
    return this.total3_discount;
  }

  public void setTotal3_discount(Double total3_discount) {
    this.total3_discount = total3_discount;
  }

    public List<SysDetail> getAllSysDetails() {
        return allSysDetails;
    }

    public void setAllSysDetails(List<SysDetail> allSysDetails) {
        this.allSysDetails = allSysDetails;
    }
  
  
}