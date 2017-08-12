package com.finework.jsf.controller.transporter;

import com.finework.core.ejb.entity.SysLogisticCar;
import com.finework.core.ejb.entity.SysOrganization;
import com.finework.core.ejb.entity.SysTransportStaff;
import com.finework.core.ejb.entity.SysTransportation;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.core.util.Constants;
import com.finework.core.util.DateTimeUtil;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.NumberUtils;
import com.finework.core.util.ReportUtil;
import com.finework.ejb.facade.OrganizationFacade;
import com.finework.ejb.facade.TransporterFacade;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Adisorn j.
 */
@ManagedBean(name = T105Controller.CONTROLLER_NAME)
@SessionScoped
public class T105Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(T105Controller.class);
    public static final String CONTROLLER_NAME = "t105Controller";

    @ManagedProperty(value = "#{" + UserInfoController.CONTROLLER_NAME + "}")
    private UserInfoController userInfo;

    @Inject
    private TransporterFacade transportStaffFacade;
    @Inject
    private TransporterFacade transporterFacade;
    @Inject
    private OrganizationFacade organizationFacade;

    private List<SysTransportStaff> items;
    private SysTransportStaff selected;

    //find by criteria
    private String transportName;
    private Date startDate;
    private Date toDate;
    private Integer status;

    //
    private List<SysTransportStaff> printSelected;

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
                cal.set(Calendar.DAY_OF_MONTH, 1);
                startDate = cal.getTime();
            }
            if (null == toDate) {
                GregorianCalendar calEnd = (GregorianCalendar) GregorianCalendar.getInstance(Locale.US);
                toDate = calEnd.getTime();
            }

            items = transportStaffFacade.findSysTransportStaffListByCriteria(transportName, null, "Y");
            for (SysTransportStaff transstaff : items) {
                List<SysTransportation> staffs = transporterFacade.findStaffSysTransportationListByCriteria(transstaff, Constants.TRANSPORTATION_COMPLETE, startDate, toDate);//staff
                List<SysTransportation> follow_staffs1 = transporterFacade.findStafffollow1SysTransportationListByCriteria(transstaff, Constants.TRANSPORTATION_COMPLETE, startDate, toDate);//staff follow 1
                List<SysTransportation> follow_staffs2 = transporterFacade.findStafffollow2SysTransportationListByCriteria(transstaff, Constants.TRANSPORTATION_COMPLETE, startDate, toDate);//staff follow  2  

                transstaff.setSysTransportationList(staffs);
                transstaff.setSysTransportationList1(follow_staffs1);
                transstaff.setSysTransportationList2(follow_staffs2);

                Double statfs_ = calculaterOT(staffs, transstaff.getEarningPerday());
                Double follow_staffs1_ = calculaterOT(follow_staffs1, transstaff.getDailyWage());
                Double follow_staffs2_ = calculaterOT(follow_staffs2, transstaff.getDailyWage());

                transstaff.setValueWorking(statfs_ + follow_staffs1_ + follow_staffs2_);
            }

        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    public Double calculaterOT(List<SysTransportation> sysTransportations, Double amtPerday) {
        Double moneyWork = 0.0;
        try {
            for (SysTransportation trans : sysTransportations) {
                boolean ot = false;
                if (trans.getTpOt() || trans.getTpOTTimevalue()) {
                    if (trans.getTpOt()) {
                        ot = true;
                    }

                    if (ot) {
                        SysLogisticCar car = trans.getLogisticId();

                        //item.transportCost eq 1?'ต่อเที่ยว':item.transportCost eq 2?'ต่อระยะ'
                        if (Objects.equals(car.getTransportCost(), Constants.TRANSPORT_COST_TRAVEL)) {
                            moneyWork += car.getCharterFlights();
                            trans.setWorkMoneyOT(car.getCharterFlights());
                        } else {
                            SysWorkunit workUnit = trans.getWorkunitId();
                            workUnit.getDistance();// 1. ใกล้ 2.ไกล Constants.WORKUNIT_DISTANCE_NEAR;Constants.WORKUNIT_DISTANCE_LONG;
                            if (Objects.equals(Constants.WORKUNIT_DISTANCE_LONG, workUnit.getDistance())) {
                                moneyWork += car.getTransportShort();
                                trans.setWorkMoneyOT(car.getTransportShort());
                            } else {
                                moneyWork += car.getTransportLong();
                                trans.setWorkMoneyOT(car.getTransportLong());
                            }
                        }
                    } else {
                        //คิดตามช่วงเวลา 
                       Double value=((amtPerday!=null?amtPerday:0)/6)*trans.getTpOtTimeHours();
                       moneyWork += value;
                       trans.setWorkMoneyOT(value);
                        
                   /*     SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
                        String timeWork = sdf.format(trans.getTpDateComplete());
                        if (Integer.parseInt(timeWork) > 200000) {//>20:00 คิด 1 แรง ไม่ใช่ ครึ่งแรก
                            moneyWork += amtPerday / 1;
                            trans.setWorkMoneyOT(amtPerday / 1);
                        } else {
                            moneyWork += amtPerday / 2;
                            trans.setWorkMoneyOT(amtPerday / 2);
                        }
                    */
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error(ex);
        }

        return moneyWork;
    }

    @Override
    public void reportPDF() {
        try {
            ReportUtil report = new ReportUtil();
            List reportList_ = new ArrayList<>();
            List<TransporterReportBean> reportList_main = new ArrayList<>();
            List<TransporterReportBean> reportList = new ArrayList<>();
            List<Date> dates = new ArrayList<>();
            
            int intRunningNo = 0;
            List<SysTransportation> list = selected.getSysTransportationList();
            if (!list.isEmpty()) {
                TransporterReportBean bean = new TransporterReportBean();
                bean.setDetail("พนักงานขับรถ");
                reportList.add(bean);
                for (SysTransportation tpStaff : list) {
                    TransporterReportBean beandetail = new TransporterReportBean();
                    beandetail.setSeq(String.valueOf(intRunningNo += 1));
                    beandetail.setDetail(tpStaff.getDocumentNo());
                    beandetail.setWorkunit(tpStaff.getWorkunitId().getWorkunitName());
                    beandetail.setBillNo(DateTimeUtil.cvtDateForShow(tpStaff.getTpOrderDate(), "dd/MM/yyyy", new Locale("th", "TH")));//วันที่ทำรายการ
                    beandetail.setPlot(DateTimeUtil.cvtDateForShow(tpStaff.getTpDateComplete(), "dd/MM/yyyy", new Locale("th", "TH")));
                    beandetail.setVolumn((tpStaff.getTpOt()) ? "/" : "");//OT ต่อเที่ยว
                    beandetail.setUnit((tpStaff.getTpOTTimevalue()) ? "/" : "");//OT_ต่อเนื่อง
                    String value=NumberUtils.numberFormat((null != tpStaff.getWorkMoneyOT()) ? tpStaff.getWorkMoneyOT() : 0.0, "#,##0.00");
                    beandetail.setAmount((StringUtils.equals("0.00",value)) ? "": value);
                    reportList.add(beandetail);
                    
                   if (!dates.contains(tpStaff.getTpOrderDate()))dates.add(tpStaff.getTpOrderDate());
                }
            }

            List<SysTransportation> listfollow1 = selected.getSysTransportationList1();
            if (!listfollow1.isEmpty()) {
                TransporterReportBean bean = new TransporterReportBean();
                bean.setDetail("พนักงานติดรถ(1)");
                reportList.add(bean);
                for (SysTransportation tpStaff : listfollow1) {
                    TransporterReportBean beandetail = new TransporterReportBean();
                    beandetail.setSeq(String.valueOf(intRunningNo += 1));
                    beandetail.setDetail(tpStaff.getDocumentNo());
                    beandetail.setWorkunit(tpStaff.getWorkunitId().getWorkunitName());
                    beandetail.setBillNo(DateTimeUtil.cvtDateForShow(tpStaff.getTpOrderDate(), "dd/MM/yyyy", new Locale("th", "TH")));//วันที่ทำรายการ
                    beandetail.setPlot(DateTimeUtil.cvtDateForShow(tpStaff.getTpDateComplete(), "dd/MM/yyyy", new Locale("th", "TH")));
                    beandetail.setVolumn((tpStaff.getTpOt()) ? "/" : "");//OT ต่อเที่ยว
                    beandetail.setUnit((tpStaff.getTpOTTimevalue()) ? "/" : "");//OT_ต่อเนื่อง
                    String value=NumberUtils.numberFormat((null != tpStaff.getWorkMoneyOT()) ? tpStaff.getWorkMoneyOT() : 0.0, "#,##0.00");
                    beandetail.setAmount((StringUtils.equals("0.00",value)) ? "": value);
                    reportList.add(beandetail);

                    if (!dates.contains(tpStaff.getTpOrderDate()))dates.add(tpStaff.getTpOrderDate());
                }
            }

            List<SysTransportation> listfollow2 = selected.getSysTransportationList2();
            if (!listfollow2.isEmpty()) {
                TransporterReportBean bean = new TransporterReportBean();
                bean.setDetail("พนักงานติดรถ(2)");
                reportList.add(bean);

                for (SysTransportation tpStaff : listfollow2) {
                    TransporterReportBean beandetail = new TransporterReportBean();
                    beandetail.setSeq(String.valueOf(intRunningNo += 1));
                    beandetail.setDetail(tpStaff.getDocumentNo());
                    beandetail.setWorkunit(tpStaff.getWorkunitId().getWorkunitName());
                    beandetail.setBillNo(DateTimeUtil.cvtDateForShow(tpStaff.getTpOrderDate(), "dd/MM/yyyy", new Locale("th", "TH")));//วันที่ทำรายการ
                    beandetail.setPlot(DateTimeUtil.cvtDateForShow(tpStaff.getTpDateComplete(), "dd/MM/yyyy", new Locale("th", "TH")));
                    beandetail.setVolumn((tpStaff.getTpOt()) ? "/" : "");//OT ต่อเที่ยว
                    beandetail.setUnit((tpStaff.getTpOTTimevalue()) ? "/" : "");//OT_ต่อเนื่อง
                    String value=NumberUtils.numberFormat((null != tpStaff.getWorkMoneyOT()) ? tpStaff.getWorkMoneyOT() : 0.0, "#,##0.00");
                    beandetail.setAmount((StringUtils.equals("0.00",value)) ? "": value);
                    reportList.add(beandetail);

                    if (!dates.contains(tpStaff.getTpOrderDate()))dates.add(tpStaff.getTpOrderDate());
                }
            }

            reportList_main.add(new TransporterReportBean("", "", "","", "", "", "", ""));
            reportList_.add(reportList_main);
            reportList_.add(reportList);
            HashMap map = new HashMap();
            SysOrganization org = organizationFacade.findSysOrganizationByStatus("Y");
            map.put("org_name_th", org.getOrgNameTh());
            map.put("org_name_eng", org.getOrgNameEng());
            map.put("org_address_th", org.getOrgAddressTh());
            map.put("org_address_en", org.getOrgAddressEn());
            map.put("org_tel", org.getOrgTel());
            map.put("org_branch", org.getOrgBranch());
            map.put("org_taxid", org.getOrgTax());
            map.put("org_bank", org.getOrgBank());
            map.put("org_bank_name", org.getOrgBankName());
            map.put("org_recall", org.getOrgRecall());

            map.put("staffname", selected.getTransportstaffNameTh());
            map.put("staffnickname", selected.getTransportstaffNickname());
            map.put("send_date", DateTimeUtil.cvtDateForShow(DateTimeUtil.getSystemDate(), "dd/MM/yyyy", new Locale("th", "TH")));
            map.put("telephone", selected.getTel());
            map.put("tax_id", selected.getTaxId());
            map.put("staff_type", Objects.equals(Constants.TRANSPORT_STAFF,selected.getTransportstaffType())?"พนักงานขับรถ":"พนักงานติดรถ");
            
            Double total=(null != selected.getValueWorking()) ? selected.getValueWorking() : 0.0;
            Double allowance=(dates.size()>0) ? dates.size()*(null !=selected.getAllowance()?selected.getAllowance():0): 0.0;
            Double toalnet=total+allowance;
            map.put("amount",NumberUtils.numberFormat(total, "#,##0.00"));
            map.put("allowance",NumberUtils.numberFormat(allowance, "#,##0.00"));
            map.put("totalnet",NumberUtils.numberFormat(toalnet, "#,##0.00"));

            map.put("reportCode", "T105");
            report.exportSubReport("t105", new String[]{"T105Report", "T105SubReport"}, "T105", map, reportList_);
        } catch (Exception ex) {
            JsfUtil.addFacesErrorMessage(MessageBundleLoader.getMessage("messages.code.9001"));
            LOG.error(ex);
        }
    }
    
      public void printPdfMuti(){
         try {
             List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
             Collections.sort(printSelected, new SysTransportStaff());
             for (SysTransportStaff sysTransportStaff : printSelected) {
                    ReportUtil report = new ReportUtil();
                    List reportList_ = new ArrayList<>();
                    List<TransporterReportBean> reportList_main = new ArrayList<>();
                    List<TransporterReportBean> reportList = new ArrayList<>();
                    List<Date> dates = new ArrayList<>();

                    int intRunningNo = 0;
                    List<SysTransportation> list = sysTransportStaff.getSysTransportationList();
                    if (!list.isEmpty()) {
                        TransporterReportBean bean = new TransporterReportBean();
                        bean.setDetail("พนักงานขับรถ");
                        reportList.add(bean);
                        for (SysTransportation tpStaff : list) {
                            TransporterReportBean beandetail = new TransporterReportBean();
                            beandetail.setSeq(String.valueOf(intRunningNo += 1));
                            beandetail.setDetail(tpStaff.getDocumentNo());
                            beandetail.setWorkunit(tpStaff.getWorkunitId().getWorkunitName());
                            beandetail.setBillNo(DateTimeUtil.cvtDateForShow(tpStaff.getTpOrderDate(), "dd/MM/yyyy", new Locale("th", "TH")));//วันที่ทำรายการ
                            beandetail.setPlot(DateTimeUtil.cvtDateForShow(tpStaff.getTpDateComplete(), "dd/MM/yyyy", new Locale("th", "TH")));
                            beandetail.setVolumn((tpStaff.getTpOt()) ? "/" : "");//OT ต่อเที่ยว
                            beandetail.setUnit((tpStaff.getTpOTTimevalue()) ? "/" : "");//OT_ต่อเนื่อง
                            String value=NumberUtils.numberFormat((null != tpStaff.getWorkMoneyOT()) ? tpStaff.getWorkMoneyOT() : 0.0, "#,##0.00");
                            beandetail.setAmount((StringUtils.equals("0.00",value)) ? "": value);
                            reportList.add(beandetail);

                            if (!dates.contains(tpStaff.getTpOrderDate()))dates.add(tpStaff.getTpOrderDate());
                        }
                    }

                    List<SysTransportation> listfollow1 = sysTransportStaff.getSysTransportationList1();
                    if (!listfollow1.isEmpty()) {
                        TransporterReportBean bean = new TransporterReportBean();
                        bean.setDetail("พนักงานติดรถ(1)");
                        reportList.add(bean);
                        for (SysTransportation tpStaff : listfollow1) {
                            TransporterReportBean beandetail = new TransporterReportBean();
                            beandetail.setSeq(String.valueOf(intRunningNo += 1));
                            beandetail.setDetail(tpStaff.getDocumentNo());
                            beandetail.setWorkunit(tpStaff.getWorkunitId().getWorkunitName());
                            beandetail.setBillNo(DateTimeUtil.cvtDateForShow(tpStaff.getTpOrderDate(), "dd/MM/yyyy", new Locale("th", "TH")));//วันที่ทำรายการ
                            beandetail.setPlot(DateTimeUtil.cvtDateForShow(tpStaff.getTpDateComplete(), "dd/MM/yyyy", new Locale("th", "TH")));
                            beandetail.setVolumn((tpStaff.getTpOt()) ? "/" : "");//OT ต่อเที่ยว
                            beandetail.setUnit((tpStaff.getTpOTTimevalue()) ? "/" : "");//OT_ต่อเนื่อง
                            String value=NumberUtils.numberFormat((null != tpStaff.getWorkMoneyOT()) ? tpStaff.getWorkMoneyOT() : 0.0, "#,##0.00");
                            beandetail.setAmount((StringUtils.equals("0.00",value)) ? "": value);
                            reportList.add(beandetail);

                            if (!dates.contains(tpStaff.getTpOrderDate()))dates.add(tpStaff.getTpOrderDate());
                        }
                    }

                    List<SysTransportation> listfollow2 = sysTransportStaff.getSysTransportationList2();
                    if (!listfollow2.isEmpty()) {
                        TransporterReportBean bean = new TransporterReportBean();
                        bean.setDetail("พนักงานติดรถ(2)");
                        reportList.add(bean);

                        for (SysTransportation tpStaff : listfollow2) {
                            TransporterReportBean beandetail = new TransporterReportBean();
                            beandetail.setSeq(String.valueOf(intRunningNo += 1));
                            beandetail.setDetail(tpStaff.getDocumentNo());
                            beandetail.setWorkunit(tpStaff.getWorkunitId().getWorkunitName());
                            beandetail.setBillNo(DateTimeUtil.cvtDateForShow(tpStaff.getTpOrderDate(), "dd/MM/yyyy", new Locale("th", "TH")));//วันที่ทำรายการ
                            beandetail.setPlot(DateTimeUtil.cvtDateForShow(tpStaff.getTpDateComplete(), "dd/MM/yyyy", new Locale("th", "TH")));
                            beandetail.setVolumn((tpStaff.getTpOt()) ? "/" : "");//OT ต่อเที่ยว
                            beandetail.setUnit((tpStaff.getTpOTTimevalue()) ? "/" : "");//OT_ต่อเนื่อง
                            String value=NumberUtils.numberFormat((null != tpStaff.getWorkMoneyOT()) ? tpStaff.getWorkMoneyOT() : 0.0, "#,##0.00");
                            beandetail.setAmount((StringUtils.equals("0.00",value)) ? "": value);
                            reportList.add(beandetail);

                            if (!dates.contains(tpStaff.getTpOrderDate()))dates.add(tpStaff.getTpOrderDate());
                        }
                    }

                    reportList_main.add(new TransporterReportBean("", "", "","", "", "", "", ""));
                    reportList_.add(reportList_main);
                    reportList_.add(reportList);
                    HashMap map = new HashMap();
                    SysOrganization org = organizationFacade.findSysOrganizationByStatus("Y");
                    map.put("org_name_th", org.getOrgNameTh());
                    map.put("org_name_eng", org.getOrgNameEng());
                    map.put("org_address_th", org.getOrgAddressTh());
                    map.put("org_address_en", org.getOrgAddressEn());
                    map.put("org_tel", org.getOrgTel());
                    map.put("org_branch", org.getOrgBranch());
                    map.put("org_taxid", org.getOrgTax());
                    map.put("org_bank", org.getOrgBank());
                    map.put("org_bank_name", org.getOrgBankName());
                    map.put("org_recall", org.getOrgRecall());

                    map.put("staffname", sysTransportStaff.getTransportstaffNameTh());
                    map.put("staffnickname", sysTransportStaff.getTransportstaffNickname());
                    map.put("send_date", DateTimeUtil.cvtDateForShow(DateTimeUtil.getSystemDate(), "dd/MM/yyyy", new Locale("th", "TH")));
                    map.put("telephone", sysTransportStaff.getTel());
                    map.put("tax_id", sysTransportStaff.getTaxId());
                    map.put("staff_type", Objects.equals(Constants.TRANSPORT_STAFF, sysTransportStaff.getTransportstaffType()) ? "พนักงานขับรถ" : "พนักงานติดรถ");

                    Double total = (null != selected.getValueWorking()) ? selected.getValueWorking() : 0.0;
                    Double allowance = (dates.size() > 0) ? dates.size() * (null != selected.getAllowance() ? selected.getAllowance() : 0) : 0.0;
                    Double toalnet = total + allowance;
                    map.put("amount", NumberUtils.numberFormat(total, "#,##0.00"));
                    map.put("allowance", NumberUtils.numberFormat(allowance, "#,##0.00"));
                    map.put("totalnet", NumberUtils.numberFormat(toalnet, "#,##0.00"));
                    

                    map.put("reportCode", "T105");
                    JasperPrint print = report.exportSubReport_Template_mearge("template.jpg", "t105", new String[]{"T105Report", "T105SubReport"}, "T105", map, reportList_);
                    jasperPrintList.add(print);
             }
             if(!printSelected.isEmpty()){
                String pdfCode="T105";
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

    @Override
    public void create() {

    }

    @Override
    public void prepareCreate() {
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

    private void clearData() {
        selected = new SysTransportStaff();
    }

    public UserInfoController getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoController userInfo) {
        this.userInfo = userInfo;
    }

    public List<SysTransportStaff> getItems() {
        return items;
    }

    public void setItems(List<SysTransportStaff> items) {
        this.items = items;
    }

    public SysTransportStaff getSelected() {
        return selected;
    }

    public void setSelected(SysTransportStaff selected) {
        this.selected = selected;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
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

    public List<SysTransportStaff> getPrintSelected() {
        return printSelected;
    }

    public void setPrintSelected(List<SysTransportStaff> printSelected) {
        this.printSelected = printSelected;
    }

}
