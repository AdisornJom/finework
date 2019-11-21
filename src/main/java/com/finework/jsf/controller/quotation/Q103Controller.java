package com.finework.jsf.controller.quotation;

import com.finework.core.ejb.entity.AdminUser;
import com.finework.core.ejb.entity.SysMainQuotation;
import com.finework.core.util.DateTimeUtil;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.LoadConfig;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.ThaiBaht;
import com.finework.core.util.UploadUtil;
import com.finework.core.utils.FTPUtil;
import com.finework.ejb.facade.CustomerFacade;
import com.finework.ejb.facade.OrganizationFacade;
import com.finework.ejb.facade.QuotationFacade;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.SequenceController;
import com.finework.jsf.common.UserInfoController;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named("q103Controller")
@SessionScoped
public class Q103Controller extends BaseController
{
  private static final Logger LOG = Logger.getLogger(Q103Controller.class);
  public static final String CONTROLLER_NAME = "q103Controller";

  @Inject
  private QuotationFacade quotationFacade;

  @Inject
  private CustomerFacade customerFacade;

  @Inject
  private UserInfoController userInfo;

  @Inject
  private SequenceController sequence;

  @Inject
  private OrganizationFacade organizationFacade;
  private List<SysMainQuotation> items;
  private SysMainQuotation selected;
  private List<SysMainQuotation> printSelected;
  private String documentno;
  private String subject;
  private Date startDate;
  private Date toDate;
  private static final String NO_PRODUCT = "no_product.png";
  private UploadedFile file1;
  private String newFile1 = "no_product.png";
  private UploadedFile file2;
  private String newFile2 = "no_product.png";
  private UploadedFile file3;
  private String newFile3 = "no_product.png";
  public static Map<String, String> CONFIG;
  private Double draftNo;
  private Date selectDtDraft;
  private Date minDtDraft;
  private Date maxDtDraft;

  @PostConstruct
  public void init()
  {
    CONFIG = LoadConfig.loadFileDefault();

    Calendar cal1 = new GregorianCalendar(Locale.US);
    cal1.setTime(DateTimeUtil.getSystemDate());
    cal1.set(5, 1);
    cal1.add(2, -12);
    this.minDtDraft = cal1.getTime();

    Calendar cal2 = new GregorianCalendar(Locale.US);
    cal2.setTime(DateTimeUtil.getSystemDate());
    cal2.set(5, cal2.getActualMaximum(5));
    cal2.add(2, 12);
    this.maxDtDraft = cal2.getTime();

    this.selectDtDraft = DateTimeUtil.getSystemDate();

    search();
  }

  public void next(String path) {
    NaviController nav = (NaviController)JsfUtil.getManagedBeanValue("naviController");
    nav.next(path);
  }

  public void prepareCreate(String page) {
    this.selected = new SysMainQuotation();
    next(page);
  }

  public void prepareEdit()
  {
  }

  public void prepareEdit(String page) {
    next(page);
  }

  public void cancel(String path) {
    clearData();
    search();
    next(path);
  }

  public void clearData() {
    this.selected = new SysMainQuotation();
    this.newFile1 = "no_product.png";
    this.newFile2 = "no_product.png";
    this.newFile3 = "no_product.png";
    this.file1 = null; this.file2 = null; this.file3 = null;
  }

  public void create()
  {
    try {
      if (StringUtils.isBlank(this.selected.getDocumentno())) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "เลขที่" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if (StringUtils.isBlank(this.selected.getSubject())) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "เรื่อง" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if (null == this.selected.getQuotationDate()) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "วันที่ทำรายการ" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if (StringUtils.isBlank(this.selected.getRemark())) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "หัวข้อ" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if (StringUtils.isBlank(this.selected.getHeading())) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "หมวดเรื่อง" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if (StringUtils.equals("no_product.png", this.newFile1)) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "upload ไฟล์ (1)" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if (!StringUtils.equals("no_product.png", this.newFile1)) {
        this.selected.setQuotationImg1(this.newFile1);
      }

      if (!StringUtils.equals("no_product.png", this.newFile2)) {
        this.selected.setQuotationImg2(this.newFile2);
      }

      if (!StringUtils.equals("no_product.png", this.newFile3)) {
        this.selected.setQuotationImg3(this.newFile3);
      }

      this.selected.setTypeForm(Integer.valueOf(3));
      this.selected.setCreatedBy(this.userInfo.getAdminUser().getUsername());
      this.selected.setCreatedDt(DateTimeUtil.getSystemDate());
      this.selected.setModifiedBy(this.userInfo.getAdminUser().getUsername());
      this.selected.setModifiedDt(DateTimeUtil.getSystemDate());

      runningNoCustomer(DateTimeUtil.getSystemDate());

      this.quotationFacade.createSysMainQuotation(this.selected);

      clearData();
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
//      if (StringUtils.isBlank(this.selected.getDocumentno())) {
//        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "เลขที่" }));
//        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
//        return;
//      }

      if (StringUtils.isBlank(this.selected.getSubject())) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "เรื่อง" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if (null == this.selected.getQuotationDate()) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "วันที่ทำรายการ" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if (StringUtils.isBlank(this.selected.getRemark())) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "หัวข้อ" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if (StringUtils.isBlank(this.selected.getHeading())) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "หมวดเรื่อง" }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      if (!StringUtils.equals("no_product.png", this.newFile1)) {
        this.selected.setQuotationImg1(this.newFile1);
      }

      if (!StringUtils.equals("no_product.png", this.newFile2)) {
        this.selected.setQuotationImg2(this.newFile2);
      }

      if (!StringUtils.equals("no_product.png", this.newFile3)) {
        this.selected.setQuotationImg3(this.newFile3);
      }

      this.selected.setTypeForm(Integer.valueOf(3));
      this.selected.setModifiedBy(this.userInfo.getAdminUser().getUsername());
      this.selected.setModifiedDt(DateTimeUtil.getSystemDate());

      this.quotationFacade.editSysMainQuotation(this.selected);

      clearData();
      search();
      JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
      next("quotation/q103/index");
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
      this.items = this.quotationFacade.findSysMainQuotationListByCriteria(this.documentno, this.subject, this.startDate, this.toDate, Integer.valueOf(3));
    }
    catch (Exception ex) {
      LOG.error(ex);
    }
  }

  public void handleFileDownload(String filename) {
    String METHOD_NAME = "handleFileDownload";
    try {
      FTPUtil.downloadDocument("quotationUpload".concat("//").concat(filename));
    } catch (Exception ex) {
      LOG.error(METHOD_NAME + ":" + ex.getMessage());
    }
  }

  public void searchUserGroup()
  {
  }

  public String convertPriceToString(Double totalprice)
  {
    if (totalprice.doubleValue() == 0.0D) {
      return "";
    }
    return new ThaiBaht().getText(totalprice);
  }

  public void handleKeyEvent()
  {
  }

  public void prepareCreateDraft(String page)
  {
    this.selected = new SysMainQuotation();

    String yearMonth = DateTimeUtil.cvtDateForShow(this.selectDtDraft, "yyMM", new Locale("th", "TH"));
    String sequence_no = this.sequence.runningNoNewQuoation(yearMonth);
    this.selected.setDocumentno(sequence_no);
    next(page);
  }
  public void selectDateDraft() {
    String yearMonth = DateTimeUtil.cvtDateForShow(this.selectDtDraft, "yyMM", new Locale("th", "TH"));
    String sequence_no = this.sequence.runningNoNewQuoation(yearMonth);
    this.selected.setDocumentno(sequence_no);
  }

  public void createDraft() {
    try {
      if ((this.draftNo.doubleValue() < 1.0D) || (this.draftNo.doubleValue() > 99.0D)) {
        JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "เลขที่ ต้องการจอง จำนวน(ช่วง 1 ถึง 99) " }));
        RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        return;
      }

      for (int i = 0; i < this.draftNo.doubleValue(); i++)
      {
        this.selected = new SysMainQuotation();
        this.selected.setTypeForm(Integer.valueOf(3));
        this.selected.setQuotationDate(this.selectDtDraft);
        this.selected.setCreatedBy(this.userInfo.getAdminUser().getUsername());
        this.selected.setCreatedDt(DateTimeUtil.getSystemDate());
        this.selected.setModifiedBy(this.userInfo.getAdminUser().getUsername());
        this.selected.setModifiedDt(DateTimeUtil.getSystemDate());

        runningNoCustomer(this.selectDtDraft);

        this.quotationFacade.createSysMainQuotation(this.selected);
      }
      this.draftNo = Double.valueOf(0.0D);
      clearData();
      search();
      next("quotation/q103/index");
    } catch (Exception ex) {
      JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
      LOG.error(ex);
    }
  }

  public void runningNoCustomer(Date date) {
    String yearMonth = DateTimeUtil.cvtDateForShow(date, "yyMM", new Locale("th", "TH"));
    String sequence_no = this.sequence.runningNoNewQuoation(yearMonth);
    this.selected.setDocumentno(sequence_no);
  }

  public void handleFileUpload1(FileUploadEvent event)
  {
    this.file1 = event.getFile();
    if (this.file1 != null)
      try {
        this.newFile1 = UploadUtil.uploadFile(this.file1, "quotationUpload", null, UUID.randomUUID().toString());
        Thread.sleep(1500L);
      } catch (Exception ex) {
        LOG.error(ex);
      }
  }

  public void handleFileUpload2(FileUploadEvent event) {
    this.file2 = event.getFile();
    if (this.file2 != null)
      try {
        this.newFile2 = UploadUtil.uploadFile(this.file2, "quotationUpload", null, UUID.randomUUID().toString());
        Thread.sleep(1500L);
      } catch (Exception ex) {
        LOG.error(ex);
      }
  }

  public void handleFileUpload3(FileUploadEvent event) {
    this.file3 = event.getFile();
    if (this.file3 != null)
      try {
        this.newFile3 = UploadUtil.uploadFile(this.file3, "quotationUpload", null, UUID.randomUUID().toString());
        Thread.sleep(1500L);
      } catch (Exception ex) {
        LOG.error(ex);
      }
  }

  public void reportPDF()
  {
  }

  public void printPdfMuti()
  {
  }

  public QuotationFacade getQuotationFacade()
  {
    return this.quotationFacade;
  }

  public void setQuotationFacade(QuotationFacade quotationFacade) {
    this.quotationFacade = quotationFacade;
  }

  public CustomerFacade getCustomerFacade() {
    return this.customerFacade;
  }

  public void setCustomerFacade(CustomerFacade customerFacade) {
    this.customerFacade = customerFacade;
  }

  public SequenceController getSequence() {
    return this.sequence;
  }

  public void setSequence(SequenceController sequence) {
    this.sequence = sequence;
  }

  public OrganizationFacade getOrganizationFacade() {
    return this.organizationFacade;
  }

  public void setOrganizationFacade(OrganizationFacade organizationFacade) {
    this.organizationFacade = organizationFacade;
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

  public String getDocumentno() {
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

  public UploadedFile getFile1() {
    return this.file1;
  }

  public void setFile1(UploadedFile file1) {
    this.file1 = file1;
  }

  public String getNewFile1() {
    return this.newFile1;
  }

  public void setNewFile1(String newFile1) {
    this.newFile1 = newFile1;
  }

  public UploadedFile getFile2() {
    return this.file2;
  }

  public void setFile2(UploadedFile file2) {
    this.file2 = file2;
  }

  public String getNewFile2() {
    return this.newFile2;
  }

  public void setNewFile2(String newFile2) {
    this.newFile2 = newFile2;
  }

  public UploadedFile getFile3() {
    return this.file3;
  }

  public void setFile3(UploadedFile file3) {
    this.file3 = file3;
  }

  public String getNewFile3() {
    return this.newFile3;
  }

  public void setNewFile3(String newFile3) {
    this.newFile3 = newFile3;
  }

  public Date getSelectDtDraft() {
    return this.selectDtDraft;
  }

  public void setSelectDtDraft(Date selectDtDraft) {
    this.selectDtDraft = selectDtDraft;
  }

  public Date getMinDtDraft() {
    return this.minDtDraft;
  }

  public void setMinDtDraft(Date minDtDraft) {
    this.minDtDraft = minDtDraft;
  }

  public Date getMaxDtDraft() {
    return this.maxDtDraft;
  }

  public void setMaxDtDraft(Date maxDtDraft) {
    this.maxDtDraft = maxDtDraft;
  }

  public Double getDraftNo() {
    return this.draftNo;
  }

  public void setDraftNo(Double draftNo) {
    this.draftNo = draftNo;
  }
}