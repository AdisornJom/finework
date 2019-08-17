package com.finework.jsf.controller.quotation;

import java.util.stream.Collectors;
import java.util.Map;
import com.finework.core.ejb.entity.SysOrganization;
import com.finework.core.util.ThaiBaht;
import java.util.HashMap;
import com.finework.core.util.NumberUtils;
import com.finework.core.util.ReportUtil;
import javax.faces.event.AjaxBehaviorEvent;
import java.util.Calendar;
import java.util.Locale;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import com.finework.core.util.DateTimeUtil;
import org.primefaces.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import com.finework.jsf.common.NaviController;
import javax.annotation.PostConstruct;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import java.util.Date;
import com.finework.core.ejb.entity.SysGeneralQuotationDetail;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysDetail;
import com.finework.core.ejb.entity.SysGeneralQuotationHeading;
import com.finework.core.ejb.entity.SysMainQuotation;
import com.finework.core.util.Constants;
import java.util.List;
import com.finework.ejb.facade.OrganizationFacade;
import com.finework.jsf.common.SequenceController;
import com.finework.jsf.common.UserInfoController;
import com.finework.ejb.facade.StockFacade;
import com.finework.ejb.facade.CustomerFacade;
import javax.inject.Inject;
import com.finework.ejb.facade.QuotationFacade;
import org.apache.log4j.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import com.finework.jsf.common.BaseController;

@Named(Q100Controller.CONTROLLER_NAME)
@SessionScoped
public class Q100Controller extends BaseController
{
    private static final Logger LOG;
    public static final String CONTROLLER_NAME = "q100Controller";
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
    private List<SysGeneralQuotationHeading> headingItems;
    private List<SysCustomer> allCustomers;
    private List<SysWorkunit> allWorkunit;
    private String headingtxt;
    private SysGeneralQuotationHeading tempHeading;
    private SysGeneralQuotationDetail dumpMyDetail_selected1;
    private String documentno;
    private String subject;
    private Date startDate;
    private Date toDate;
    private Double total1;
    private Double total1_discount;
    private Double total1_vat;
    private Double total1_net;
    private String total1_th;
    private SysCustomer cust_selected;
    private SysWorkunit workunit_selected;
    private List<SysDetail> allSysDetail;
    
    public Q100Controller() {
        this.total1 = 0.0;
        this.total1_discount = 0.0;
        this.total1_vat = 0.0;
        this.total1_net = 0.0;
    }
    
    @PostConstruct
    public void init() {
        try {
            this.allCustomers = (List<SysCustomer>)this.customerFacade.findSysCustomerList();
            this.allWorkunit = (List<SysWorkunit>)this.customerFacade.findSysWorkunitList();
            this.allSysDetail = stockFacade.findSysDetailList();
            this.search();
            this.searchHeading();
        }
        catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            Q100Controller.LOG.error((Object)ex);
        }
    }
    
    public void next(final String path) {
        final NaviController nav = (NaviController)JsfUtil.getManagedBeanValue("naviController");
        nav.next(path);
    }
    
    public void prepareCreate(final String page) {
        this.selected = new SysMainQuotation();
        this.dumpMyDetail_selected1 = new SysGeneralQuotationDetail();
        this.next(page);
    }
    
    public void prepareEdit() {
        this.dumpMyDetail_selected1 = new SysGeneralQuotationDetail();
    }
    
    public void prepareEdit(final String page) {
        if (null != this.selected.getSysGeneralQuotationDetailList() && this.selected.getSysGeneralQuotationDetailList().size() > 0) {
            Double total_ = 0.0;
            for (final SysGeneralQuotationDetail sysdetail : this.selected.getSysGeneralQuotationDetailList()) {
                total_ += sysdetail.getAmount();
            }
            this.total1 = total_;
            this.total1_discount = ((null != this.selected.getTotal1Discount()) ? this.selected.getTotal1Discount() : 0.0);
            this.total1_vat = (this.total1 - this.total1_discount) * 0.07;
            this.total1_net = this.total1 - this.total1_discount + this.total1_vat;
        }
        this.next(page);
    }
    
    public void cancel(final String path) {
        this.clearData();
        this.clearDatatTotal1();
        this.search();
        this.next(path);
    }
    
    public void clearData() {
        this.selected = new SysMainQuotation();
        this.cust_selected = new SysCustomer();
        this.workunit_selected = new SysWorkunit();
        this.total1_discount = 0.0;
    }
    
    @Override
    public void create() {
        try {
            if (StringUtils.isBlank((CharSequence)this.selected.getSubject())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "\u0e40\u0e23\u0e37\u0e48\u0e2d\u0e07" }));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (StringUtils.isBlank((CharSequence)this.selected.getInvite())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "\u0e40\u0e23\u0e35\u0e22\u0e19" }));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (null == this.selected.getQuotationDate()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "\u0e27\u0e31\u0e19\u0e17\u0e35\u0e48\u0e17\u0e33\u0e23\u0e32\u0e22\u0e01\u0e32\u0e23" }));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (null == this.selected.getSysGeneralQuotationDetailList() || this.selected.getSysGeneralQuotationDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "\u0e23\u0e32\u0e22\u0e25\u0e30\u0e40\u0e2d\u0e35\u0e22\u0e14" }));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            this.selected.setTypeForm(4);
            this.selected.setCustomerId(this.cust_selected);
            this.selected.setWorkunitId(this.workunit_selected);
            this.selected.setCreatedBy(this.userInfo.getAdminUser().getUsername());
            this.selected.setCreatedDt(DateTimeUtil.getSystemDate());
            this.selected.setModifiedBy(this.userInfo.getAdminUser().getUsername());
            this.selected.setModifiedDt(DateTimeUtil.getSystemDate());
            Double total1 = 0.0;
            Double total1_discount = 0.0;
            final Double total_tax1 = 0.0;
            Double total_amount1 = 0.0;
            final List<SysGeneralQuotationDetail> detal_add = new ArrayList<>();
            for (final SysGeneralQuotationDetail sysQuotation : this.selected.getSysGeneralQuotationDetailList()) {
                sysQuotation.setQuoDetailId((Integer)null);
                sysQuotation.setQuotationId(this.selected);
                total1 += sysQuotation.getAmount();
                detal_add.add(sysQuotation);
            }
            total1_discount = total1 - this.total1_discount;
            total_amount1 = total1_discount + total_tax1;
            this.selected.setSysGeneralQuotationDetailList((List)detal_add);
            this.selected.setTotal(total1);
            this.selected.setTotal1Discount(this.total1_discount);
            this.selected.setTotalTax(total_tax1);
            this.selected.setTotalAmount(total_amount1);
            this.selected.setStatus(Constants.STATUS_WAITING);
            this.runningNoCustomer(DateTimeUtil.getSystemDate());
            this.quotationFacade.createSysMainQuotation(this.selected);
            this.clearData();
            this.clearDatatTotal1();
            this.search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
        }
        catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            Q100Controller.LOG.error((Object)ex);
        }
    }
    
    public void edit() {
        try {
            this.selected.setQuotationId((Integer)null);
            if (StringUtils.isBlank((CharSequence)this.selected.getSubject())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "\u0e40\u0e23\u0e37\u0e48\u0e2d\u0e07" }));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (StringUtils.isBlank((CharSequence)this.selected.getInvite())) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "\u0e40\u0e23\u0e35\u0e22\u0e19" }));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (null == this.selected.getQuotationDate()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "\u0e27\u0e31\u0e19\u0e17\u0e35\u0e48\u0e17\u0e33\u0e23\u0e32\u0e22\u0e01\u0e32\u0e23" }));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            if (null == this.selected.getSysGeneralQuotationDetailList() || this.selected.getSysGeneralQuotationDetailList().isEmpty()) {
                JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessageFormat("messages.code.2002", new Object[] { "\u0e23\u0e32\u0e22\u0e25\u0e30\u0e40\u0e2d\u0e35\u0e22\u0e14" }));
                RequestContext.getCurrentInstance().scrollTo("listForm:create_msg");
                return;
            }
            this.selected.setModifiedBy(this.userInfo.getAdminUser().getUsername());
            this.selected.setModifiedDt(DateTimeUtil.getSystemDate());
            Double total1 = 0.0;
            Double total1_discount = 0.0;
            final Double total_tax1 = 0.0;
            Double total_amount1 = 0.0;
            final List<SysGeneralQuotationDetail> detal_add = new ArrayList<SysGeneralQuotationDetail>();
            for (final SysGeneralQuotationDetail sysQuotation : this.selected.getSysGeneralQuotationDetailList()) {
                sysQuotation.setQuoDetailId((Integer)null);
                sysQuotation.setQuotationId(this.selected);
                total1 += sysQuotation.getAmount();
                detal_add.add(sysQuotation);
            }
            total1_discount = total1 - this.total1_discount;
            total_amount1 = total1_discount + total_tax1;
            this.selected.setSysMainQuotation1List((List)null);
            this.selected.setSysMainQuotation2List((List)null);
            this.selected.setSysMainQuotation3List((List)null);
            this.selected.setSysGeneralQuotationDetailList((List)detal_add);
            this.selected.setTotal(total1);
            this.selected.setTotal1Discount(this.total1_discount);
            this.selected.setTotalTax(total_tax1);
            this.selected.setTotalAmount(total_amount1);
            this.runningNoChildCustomer(this.selected.getDocumentno());
            this.quotationFacade.createSysMainQuotation(this.selected);
            this.clearData();
            this.clearDatatTotal1();
            this.search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4001"));
            this.next("quotation/q100/index");
        }
        catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            Q100Controller.LOG.error((Object)ex);
        }
    }
    
    public void delete() {
        try {
            this.quotationFacade.deleteSysMainQuotation(this.selected);
            this.search();
            JsfUtil.addFacesInformationMessage(MessageBundleLoader.getMessage("messages.code.4002"));
        }
        catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            Q100Controller.LOG.error((Object)ex);
        }
    }
    
    public void checkTotalPrice1() {
        Double total_ = 0.0;
        if (null != this.selected.getSysGeneralQuotationDetailList() && this.selected.getSysGeneralQuotationDetailList().size() > 0) {
            for (final SysGeneralQuotationDetail sysdetail : this.selected.getSysGeneralQuotationDetailList()) {
                total_ += sysdetail.getAmount();
            }
            this.total1 = total_;
            final Double totaldiscount = total_ - this.total1_discount;
            this.total1_vat = 0.0;
            this.total1_net = totaldiscount + this.total1_vat;
        }
        else {
            this.clearDatatTotal1();
        }
    }
    
    public void clearDatatTotal1() {
        this.total1_discount = 0.0;
        this.total1_vat = 0.0;
        this.total1_net = 0.0;
        this.total1 = 0.0;
    }
    
    public void search() {
        try {
            if (null == this.startDate) {
                final GregorianCalendar cal = (GregorianCalendar)Calendar.getInstance(Locale.US);
                cal.set(11, 0);
                cal.set(12, 0);
                cal.set(13, 0);
                cal.set(5, 1);
                this.startDate = cal.getTime();
            }
            if (null == this.toDate) {
                final GregorianCalendar calEnd = (GregorianCalendar)Calendar.getInstance(Locale.US);
                calEnd.set(11, 23);
                calEnd.set(12, 59);
                calEnd.set(13, 59);
                this.toDate = calEnd.getTime();
            }
            this.items = (List<SysMainQuotation>)this.quotationFacade.findSysMainQuotationListByCriteria(this.documentno, this.subject, this.startDate, this.toDate, Integer.valueOf(4));
            this.printSelected = new ArrayList();
        }
        catch (Exception ex) {
            Q100Controller.LOG.error((Object)ex);
        }
    }
    
    public void searchHeading() {
        try {
            this.headingItems = (List<SysGeneralQuotationHeading>)this.quotationFacade.findAllQuotationHeading();
        }
        catch (Exception ex) {
            Q100Controller.LOG.error((Object)ex);
        }
    }
    
    public void addDetail1() {
        try {
            if (this.dumpMyDetail_selected1.getUnit() == null || this.dumpMyDetail_selected1.getVolume() == null) {
                return;
            }
            if (this.dumpMyDetail_selected1.getValueUnit() == null && this.dumpMyDetail_selected1.getValueUnit() <= 0.0) {
                return;
            }
            Double total = 0.0;
            Double volumePerUnit = 0.0;
            Double volumeTotal = 0.0;
            volumePerUnit = ((this.dumpMyDetail_selected1.getVolumePerUnit() != null && this.dumpMyDetail_selected1.getVolumePerUnit() > 0.0) ? this.dumpMyDetail_selected1.getVolumePerUnit() : 1.0);
            volumeTotal = this.dumpMyDetail_selected1.getVolume() * volumePerUnit;
            total = volumeTotal * this.dumpMyDetail_selected1.getValueUnit();
            if (this.selected.getSysGeneralQuotationDetailList() == null) {
                this.selected.setSysGeneralQuotationDetailList((List)new ArrayList());
            }
            this.dumpMyDetail_selected1.setVolumeTotal(volumeTotal);
            this.dumpMyDetail_selected1.setAmount(total);
            this.selected.getSysGeneralQuotationDetailList().add(this.dumpMyDetail_selected1);
            this.checkTotalPrice1();
            this.clearData_sysDetail1();
        }
        catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            Q100Controller.LOG.error((Object)ex);
        }
    }
    
    public void deleteDetail1() {
        try {
            this.selected.getSysGeneralQuotationDetailList().remove(this.dumpMyDetail_selected1);
            this.checkTotalPrice1();
            this.clearData_sysDetail1();
        }
        catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            Q100Controller.LOG.error((Object)ex);
        }
    }
    
    public void createHeading() {
        try {
            if (null != this.headingtxt && !this.headingtxt.equals("")) {
                final SysGeneralQuotationHeading heading = new SysGeneralQuotationHeading();
                heading.setHeadingId((Integer)null);
                heading.setHeadingValue(this.headingtxt);
                this.quotationFacade.createSysGeneralQuotationHeading(heading);
                this.searchHeading();
            }
            this.headingtxt = "";
        }
        catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            Q100Controller.LOG.error((Object)ex);
        }
    }
    
    public void deleteHeading() {
        try {
            this.quotationFacade.deleteSysSysGeneralQuotationHeading(this.tempHeading);
            this.searchHeading();
        }
        catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            Q100Controller.LOG.error((Object)ex);
        }
    }
    
    public void clearData_sysDetail1() {
        this.dumpMyDetail_selected1 = new SysGeneralQuotationDetail();
    }
    
    public void action(final AjaxBehaviorEvent event) {
        if (null != this.selected.getSysGeneralQuotationDetailList() && this.selected.getSysGeneralQuotationDetailList().size() > 0) {
            this.selected.getSysGeneralQuotationDetailList().clear();
            this.checkTotalPrice1();
            this.clearData_sysDetail1();
        }
    }
    
    public void handleKeyEvent() {
    }
    
    public List<SysCustomer> completeCustomer(final String query) {
        final List<SysCustomer> filteredCustomers = new ArrayList<SysCustomer>();
        try {
            final List<SysCustomer> allCustomers = (List<SysCustomer>)this.customerFacade.findSysCustomerList();
            for (final SysCustomer sysCustomer : allCustomers) {
                if (sysCustomer.getCustomerNameTh() != null && sysCustomer.getCustomerNameTh().length() > 0 && sysCustomer.getCustomerNameTh().toLowerCase().startsWith(query)) {
                    filteredCustomers.add(sysCustomer);
                }
            }
        }
        catch (Exception ex) {
            Q100Controller.LOG.error((Object)ex);
        }
        return filteredCustomers;
    }
    
    public List<SysWorkunit> completeWorkunit(final String query) {
        final List<SysWorkunit> filteredWorkunit = new ArrayList<SysWorkunit>();
        try {
            final List<SysWorkunit> allWorkunit = (List<SysWorkunit>)this.customerFacade.findSysWorkunitList();
            for (final SysWorkunit sysWorkunit : allWorkunit) {
                if (sysWorkunit.getWorkunitName() != null && sysWorkunit.getWorkunitName().length() > 0 && sysWorkunit.getWorkunitName().toLowerCase().startsWith(query)) {
                    filteredWorkunit.add(sysWorkunit);
                }
            }
        }
        catch (Exception ex) {
            Q100Controller.LOG.error((Object)ex);
        }
        return filteredWorkunit;
    }
    
    public void runningNoCustomer(final Date date) {
        final String yearMonth = DateTimeUtil.cvtDateForShow(date, "yyMM", new Locale("th", "TH"));
        final String sequence_no = this.sequence.runningNoNewQuoation(yearMonth);
        this.selected.setDocumentno(sequence_no);
    }
    
    public void runningNoChildCustomer(final String documentno) {
        final String sequence_no = this.sequence.runningNoNewChildQuoation(documentno);
        this.selected.setChildDocumentno(sequence_no);
    }
    
    public void reportPDF() {
        try {
            final ReportUtil report = new ReportUtil();
            final List reportList_ = new ArrayList();
            final List<QuotationReportBean> reportList_main = new ArrayList<QuotationReportBean>();
            final List<QuotationReportBean> reportList1 = new ArrayList<QuotationReportBean>();
            final List<QuotationReportBean> reportList2 = new ArrayList<QuotationReportBean>();
            final List<QuotationReportBean> reportList3 = new ArrayList<QuotationReportBean>();
            final SysMainQuotation rpt_sysDelivery = this.quotationFacade.findByPK(this.selected.getQuotationId());
            final List<SysGeneralQuotationDetail> list = (List<SysGeneralQuotationDetail>)rpt_sysDelivery.getSysGeneralQuotationDetailList();
            Double total1 = 0.0;
            Double total1_discount = 0.0;
            final Double total1_vat = 0.0;
            Double total1_net = 0.0;
            Double total1_ = 0.0;
            int i = 0;
            for (final SysGeneralQuotationDetail to : list) {
                final QuotationReportBean bean = new QuotationReportBean();
                ++i;
                bean.setHeading(to.getHeading());
                bean.setSeq(String.valueOf(i));
                bean.setDetail(to.getDetail());
                bean.setVolume(NumberUtils.numberFormat(to.getVolume(), "#,##0"));
                bean.setUnit(to.getUnit());
                bean.setValueUnit(NumberUtils.numberFormat(to.getValueUnit(), "#,##0.00"));
                bean.setAmount(NumberUtils.numberFormat(to.getAmount(), "#,##0.00"));
                total1_ += to.getAmount();
                reportList1.add(bean);
            }
            total1 = total1_;
            total1_discount = total1 - ((null != rpt_sysDelivery.getTotal1Discount()) ? rpt_sysDelivery.getTotal1Discount() : 0.0);
            total1_net = total1_discount + total1_vat;
            reportList_main.add(new QuotationReportBean("", "", "", "", "", "", ""));
            reportList_.add(reportList_main);
            reportList_.add(reportList1);
            reportList_.add(reportList2);
            reportList_.add(reportList3);
            final HashMap map = new HashMap();
            final SysOrganization org = this.organizationFacade.findSysOrganizationByStatus("Y");
            map.put("org_name_th", org.getOrgNameTh());
            map.put("org_name_eng", org.getOrgNameEng());
            map.put("org_address_th", org.getOrgAddressTh());
            map.put("org_tel", org.getOrgTel());
            map.put("org_branch", org.getOrgBranch());
            map.put("org_taxid", org.getOrgTax());
            map.put("org_bank", org.getOrgBank());
            map.put("org_bank_name", org.getOrgBankName());
            map.put("org_recall", org.getOrgRecall());
            map.put("documentno", (null != rpt_sysDelivery.getDocumentno()) ? rpt_sysDelivery.getDocumentno() : "-");
            map.put("quotation_date", DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getQuotationDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            map.put("subject", rpt_sysDelivery.getSubject());
            map.put("invite", rpt_sysDelivery.getInvite());
            map.put("email", (null != rpt_sysDelivery.getEmail()) ? rpt_sysDelivery.getEmail() : "-");
            map.put("remark", StringUtils.isNotBlank((CharSequence)rpt_sysDelivery.getRemark()) ? ("                " + rpt_sysDelivery.getRemark()) : "-");
            map.put("customer", (null != rpt_sysDelivery.getCustomerId()) ? rpt_sysDelivery.getCustomerId().getCustomerNameTh() : "-");
            map.put("workunit", (null != rpt_sysDelivery.getWorkunitId()) ? rpt_sysDelivery.getWorkunitId().getWorkunitName() : "-");
            map.put("total1", NumberUtils.numberFormat(total1, "#,##0.00"));
            map.put("total1_discount", NumberUtils.numberFormat((null != rpt_sysDelivery.getTotal1Discount() ? rpt_sysDelivery.getTotal1Discount() : 0.0), "#,##0.00"));
            map.put("total1_vat", NumberUtils.numberFormat(total1_vat, "#,##0.00"));
            map.put("total1_net", NumberUtils.numberFormat(total1_net, "#,##0.00"));
            map.put("total1_char", (total1_net == 0.0) ? "" : new ThaiBaht().getText((Number)total1_net));
            map.put("reportCode", "Q100");
            report.exportSubReportQ100("q100", new String[] { "Q100Report", "Q100SubReport" }, "Q100", map, reportList_);
        }
        catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            Q100Controller.LOG.error((Object)ex);
        }
    }
    
    public void reportPDF2() {
        try {
            final ReportUtil report = new ReportUtil();
            final List reportList_ = new ArrayList();
            final List<QuotationReportBean> reportList_main = new ArrayList<QuotationReportBean>();
            final List<QuotationReportBean> reportList1 = new ArrayList<QuotationReportBean>();
            final List<QuotationReportBean> reportList2 = new ArrayList<QuotationReportBean>();
            final List<QuotationReportBean> reportList3 = new ArrayList<QuotationReportBean>();
            final SysMainQuotation rpt_sysDelivery = this.quotationFacade.findByPK(this.selected.getQuotationId());
            final List<SysGeneralQuotationDetail> list = (List<SysGeneralQuotationDetail>)rpt_sysDelivery.getSysGeneralQuotationDetailList();
            Double total1 = 0.0;
            Double total1_discount = 0.0;
            final Double total1_vat = 0.0;
            Double total1_net = 0.0;
            Double total1_ = 0.0;
            int i = 0;
            int groupCount = 0;
            final Map<String, List<SysGeneralQuotationDetail>> generalQDetail = 
                    list.stream().collect(Collectors.groupingBy(w -> w.getHeading()));
            for (final Map.Entry<String, List<SysGeneralQuotationDetail>> entry : generalQDetail.entrySet()) {
                ++groupCount;
                for (final SysGeneralQuotationDetail to : entry.getValue()) {
                    final QuotationReportBean bean = new QuotationReportBean();
                    ++i;
                    bean.setHeading(String.valueOf(groupCount));
                    bean.setHeadingTxt(to.getHeading());
                    bean.setSeq(String.valueOf(i));
                    bean.setDetail(to.getDetail());
                    bean.setVolume(NumberUtils.numberFormat(to.getVolume(), "#,##0"));
                    bean.setUnit(to.getUnit());
                    bean.setValueUnit(NumberUtils.numberFormat(to.getValueUnit(), "#,##0.00"));
                    bean.setAmount(NumberUtils.numberFormat(to.getAmount(), "#,##0.00"));
                    total1_ += to.getAmount();
                    reportList1.add(bean);
                }
            }
            total1 = total1_;
            total1_discount = total1 - ((null != rpt_sysDelivery.getTotal1Discount()) ? rpt_sysDelivery.getTotal1Discount() : 0.0);
            total1_net = total1_discount + total1_vat;
            reportList_main.add(new QuotationReportBean("", "", "", "", "", "", ""));
            reportList_.add(reportList_main);
            reportList_.add(reportList1);
            reportList_.add(reportList2);
            reportList_.add(reportList3);
            final HashMap map = new HashMap();
            final SysOrganization org = this.organizationFacade.findSysOrganizationByStatus("Y");
            map.put("org_name_th", org.getOrgNameTh());
            map.put("org_name_eng", org.getOrgNameEng());
            map.put("org_address_th", org.getOrgAddressTh());
            map.put("org_tel", org.getOrgTel());
            map.put("org_branch", org.getOrgBranch());
            map.put("org_taxid", org.getOrgTax());
            map.put("org_bank", org.getOrgBank());
            map.put("org_bank_name", org.getOrgBankName());
            map.put("org_recall", org.getOrgRecall());
            map.put("documentno", (null != rpt_sysDelivery.getDocumentno()) ? rpt_sysDelivery.getDocumentno() : "-");
            map.put("quotation_date", DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getQuotationDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            map.put("subject", rpt_sysDelivery.getSubject());
            map.put("invite", rpt_sysDelivery.getInvite());
            map.put("email", (null != rpt_sysDelivery.getEmail()) ? rpt_sysDelivery.getEmail() : "-");
            map.put("remark", StringUtils.isNotBlank((CharSequence)rpt_sysDelivery.getRemark()) ? ("                " + rpt_sysDelivery.getRemark()) : "-");
            map.put("customer", (null != rpt_sysDelivery.getCustomerId()) ? rpt_sysDelivery.getCustomerId().getCustomerNameTh() : "-");
            map.put("workunit", (null != rpt_sysDelivery.getWorkunitId()) ? rpt_sysDelivery.getWorkunitId().getWorkunitName() : "-");
            map.put("total1", NumberUtils.numberFormat(total1, "#,##0.00"));
            map.put("total1_discount", NumberUtils.numberFormat((null != rpt_sysDelivery.getTotal1Discount() ? rpt_sysDelivery.getTotal1Discount() : 0.0), "#,##0.00"));
            map.put("total1_vat", NumberUtils.numberFormat(total1_vat, "#,##0.00"));
            map.put("total1_net", NumberUtils.numberFormat(total1_net, "#,##0.00"));
            map.put("total1_char", (total1_net == 0.0) ? "" : new ThaiBaht().getText((Number)total1_net));
            map.put("reportCode", "Q100");
            report.exportSubReportQ100("q100", new String[] { "Q100Report2", "Q100SubReport2" }, "Q100", map, reportList_);
        }
        catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            Q100Controller.LOG.error((Object)ex);
        }
    }
    
    public void reportPDF3() {
        try {
            final ReportUtil report = new ReportUtil();
            final List reportList_ = new ArrayList();
            final List<QuotationReportBean> reportList_main = new ArrayList<>();
            final List<QuotationReportBean> reportList1 = new ArrayList<>();
            final List<QuotationReportBean> reportList2 = new ArrayList<>();
            final List<QuotationReportBean> reportList3 = new ArrayList<>();
            final SysMainQuotation rpt_sysDelivery = this.quotationFacade.findByPK(this.selected.getQuotationId());
            final List<SysGeneralQuotationDetail> list = (List<SysGeneralQuotationDetail>)rpt_sysDelivery.getSysGeneralQuotationDetailList();
            Double total1 = 0.0;
            Double total1_discount = 0.0;
            final Double total1_vat = 0.0;
            Double total1_net = 0.0;
            Double total1_ = 0.0;
            int i = 0;
            int groupCount = 0;
            final Map<String, List<SysGeneralQuotationDetail>> generalQDetail = 
                    list.stream().collect(Collectors.groupingBy(w -> w.getHeading()));
            for (final Map.Entry<String, List<SysGeneralQuotationDetail>> entry : generalQDetail.entrySet()) {
                ++groupCount;
                for (final SysGeneralQuotationDetail to : entry.getValue()) {
                    final QuotationReportBean bean = new QuotationReportBean();
                    ++i;
                    bean.setHeading(String.valueOf(groupCount));
                    bean.setHeadingTxt(to.getHeading());
                    bean.setSeq(String.valueOf(i));
                    bean.setDetail(to.getDetail());
                    bean.setVolume(NumberUtils.numberFormat(to.getVolume(), "#,##0"));
                    bean.setUnit(to.getUnit());
                    bean.setValueUnit(NumberUtils.numberFormat(to.getValueUnit(), "#,##0.00"));
                    bean.setValuePerUnit(NumberUtils.numberFormat((null != to.getVolumePerUnit() ? to.getVolumePerUnit() : 1.0), "#,##0"));
                    bean.setTotal(NumberUtils.numberFormat(to.getVolume() * ((null != to.getVolumePerUnit() ? to.getVolumePerUnit() : 1.0)), "#,##0"));
                    bean.setAmount(NumberUtils.numberFormat(to.getAmount(), "#,##0.00"));
                    total1_ += to.getAmount() * ((null != to.getVolumePerUnit()) ? to.getVolumePerUnit() : 0.0);
                    reportList1.add(bean);
                }
            }
            total1 = total1_;
            total1_discount = total1 - ((null != rpt_sysDelivery.getTotal1Discount()) ? rpt_sysDelivery.getTotal1Discount() : 0.0);
            total1_net = total1_discount + total1_vat;
            reportList_main.add(new QuotationReportBean("", "", "", "", "", "", ""));
            reportList_.add(reportList_main);
            reportList_.add(reportList1);
            reportList_.add(reportList2);
            reportList_.add(reportList3);
            final HashMap map = new HashMap();
            final SysOrganization org = this.organizationFacade.findSysOrganizationByStatus("Y");
            map.put("org_name_th", org.getOrgNameTh());
            map.put("org_name_eng", org.getOrgNameEng());
            map.put("org_address_th", org.getOrgAddressTh());
            map.put("org_tel", org.getOrgTel());
            map.put("org_branch", org.getOrgBranch());
            map.put("org_taxid", org.getOrgTax());
            map.put("org_bank", org.getOrgBank());
            map.put("org_bank_name", org.getOrgBankName());
            map.put("org_recall", org.getOrgRecall());
            map.put("documentno", (null != rpt_sysDelivery.getDocumentno()) ? rpt_sysDelivery.getDocumentno() : "-");
            map.put("quotation_date", DateTimeUtil.cvtDateForShow(rpt_sysDelivery.getQuotationDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            map.put("subject", rpt_sysDelivery.getSubject());
            map.put("invite", rpt_sysDelivery.getInvite());
            map.put("email", (null != rpt_sysDelivery.getEmail()) ? rpt_sysDelivery.getEmail() : "-");
            map.put("remark", StringUtils.isNotBlank((CharSequence)rpt_sysDelivery.getRemark()) ? ("                " + rpt_sysDelivery.getRemark()) : "-");
            map.put("customer", (null != rpt_sysDelivery.getCustomerId()) ? rpt_sysDelivery.getCustomerId().getCustomerNameTh() : "-");
            map.put("workunit", (null != rpt_sysDelivery.getWorkunitId()) ? rpt_sysDelivery.getWorkunitId().getWorkunitName() : "-");
            map.put("total1", NumberUtils.numberFormat(total1, "#,##0.00"));
            map.put("total1_discount", NumberUtils.numberFormat((null != rpt_sysDelivery.getTotal1Discount() ? rpt_sysDelivery.getTotal1Discount() : 0.0), "#,##0.00"));
            map.put("total1_vat", NumberUtils.numberFormat(total1_vat, "#,##0.00"));
            map.put("total1_net", NumberUtils.numberFormat(total1_net, "#,##0.00"));
            map.put("total1_char", (total1_net == 0.0) ? "" : new ThaiBaht().getText((Number)total1_net));
            map.put("reportCode", "Q100");
            report.exportSubReportQ100("q100", new String[] { "Q100Report3", "Q100SubReport3" }, "Q100", map, reportList_);
        }
        catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            Q100Controller.LOG.error((Object)ex);
        }
    }
    
    public String convertPriceToString(final Double totalprice) {
        if (totalprice == 0.0) {
            return "";
        }
        return new ThaiBaht().getText((Number)totalprice);
    }
    
    public StockFacade getStockFacade() {
        return this.stockFacade;
    }
    
    public void setStockFacade(final StockFacade stockFacade) {
        this.stockFacade = stockFacade;
    }
    
    public UserInfoController getUserInfo() {
        return this.userInfo;
    }
    
    public void setUserInfo(final UserInfoController userInfo) {
        this.userInfo = userInfo;
    }
    
    public List<SysMainQuotation> getItems() {
        return this.items;
    }
    
    public void setItems(final List<SysMainQuotation> items) {
        this.items = items;
    }
    
    public SysMainQuotation getSelected() {
        return this.selected;
    }
    
    public void setSelected(final SysMainQuotation selected) {
        this.selected = selected;
    }
    
    public List<SysMainQuotation> getPrintSelected() {
        return this.printSelected;
    }
    
    public void setPrintSelected(final List<SysMainQuotation> printSelected) {
        this.printSelected = printSelected;
    }
    
    public SysGeneralQuotationDetail getDumpMyDetail_selected1() {
        return this.dumpMyDetail_selected1;
    }
    
    public void setDumpMyDetail_selected1(final SysGeneralQuotationDetail dumpMyDetail_selected1) {
        this.dumpMyDetail_selected1 = dumpMyDetail_selected1;
    }
    
    public String getDocumentno() {
        return this.documentno;
    }
    
    public void setDocumentno(final String documentno) {
        this.documentno = documentno;
    }
    
    public String getSubject() {
        return this.subject;
    }
    
    public void setSubject(final String subject) {
        this.subject = subject;
    }
    
    public Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getToDate() {
        return this.toDate;
    }
    
    public void setToDate(final Date toDate) {
        this.toDate = toDate;
    }
    
    public Double getTotal1() {
        return this.total1;
    }
    
    public void setTotal1(final Double total1) {
        this.total1 = total1;
    }
    
    public Double getTotal1_vat() {
        return this.total1_vat;
    }
    
    public void setTotal1_vat(final Double total1_vat) {
        this.total1_vat = total1_vat;
    }
    
    public Double getTotal1_net() {
        return this.total1_net;
    }
    
    public void setTotal1_net(final Double total1_net) {
        this.total1_net = total1_net;
    }
    
    public String getTotal1_th() {
        return this.total1_th;
    }
    
    public void setTotal1_th(final String total1_th) {
        this.total1_th = total1_th;
    }
    
    public List<SysGeneralQuotationHeading> getHeadingItems() {
        return this.headingItems;
    }
    
    public void setHeadingItems(final List<SysGeneralQuotationHeading> headingItems) {
        this.headingItems = headingItems;
    }
    
    public Double getTotal1_discount() {
        return this.total1_discount;
    }
    
    public void setTotal1_discount(final Double total1_discount) {
        this.total1_discount = total1_discount;
    }
    
    public SysCustomer getCust_selected() {
        return this.cust_selected;
    }
    
    public void setCust_selected(final SysCustomer cust_selected) {
        this.cust_selected = cust_selected;
    }
    
    public SysWorkunit getWorkunit_selected() {
        return this.workunit_selected;
    }
    
    public void setWorkunit_selected(final SysWorkunit workunit_selected) {
        this.workunit_selected = workunit_selected;
    }
    
    public String getHeadingtxt() {
        return this.headingtxt;
    }
    
    public void setHeadingtxt(final String headingtxt) {
        this.headingtxt = headingtxt;
    }
    
    public List<SysCustomer> getAllCustomers() {
        return this.allCustomers;
    }
    
    public void setAllCustomers(final List<SysCustomer> allCustomers) {
        this.allCustomers = allCustomers;
    }
    
    public List<SysWorkunit> getAllWorkunit() {
        return this.allWorkunit;
    }
    
    public void setAllWorkunit(final List<SysWorkunit> allWorkunit) {
        this.allWorkunit = allWorkunit;
    }
    
    public SysGeneralQuotationHeading getTempHeading() {
        return this.tempHeading;
    }
    
    public void setTempHeading(final SysGeneralQuotationHeading tempHeading) {
        this.tempHeading = tempHeading;
    }
    
    static {
        LOG = Logger.getLogger((Class)Q100Controller.class);
    }
}