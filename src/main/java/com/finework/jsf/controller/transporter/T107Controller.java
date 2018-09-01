package com.finework.jsf.controller.transporter;

import com.finework.core.ejb.entity.SysOrganization;
import com.finework.core.ejb.entity.SysTransportStaff;
import com.finework.core.ejb.entity.SysTransportStaffSpecial;
import com.finework.core.ejb.entity.SysTransportStaffSpecialDetail;
import com.finework.core.ejb.entity.SysTransportation;
import com.finework.core.ejb.entity.SysTransportationExpDetail;
import com.finework.core.ejb.entity.SysTranspostationExp;
import com.finework.core.util.Constants;
import com.finework.core.util.DateTimeUtil;
import com.finework.jsf.common.BaseController;
import com.finework.jsf.common.NaviController;
import com.finework.jsf.common.UserInfoController;
import com.finework.core.util.JsfUtil;
import com.finework.core.util.MessageBundleLoader;
import com.finework.core.util.NumberUtils;
import com.finework.core.util.ReportUtil;
import com.finework.core.util.StringUtil;
import com.finework.ejb.facade.OrganizationFacade;
import com.finework.ejb.facade.TransporterFacade;
import com.finework.jsf.model.CalculateSalaryStaff;
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
@ManagedBean(name = T107Controller.CONTROLLER_NAME)
@SessionScoped
public class T107Controller extends BaseController {

    private static final Logger LOG = Logger.getLogger(T107Controller.class);
    public static final String CONTROLLER_NAME = "t107Controller";

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
    private SysTransportStaff tpstaff_find;
    private Date startDate;
    private Date toDate;
    private Integer status;

    //
    private List<SysTransportStaff> printSelected;
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
            if (null == startDate) {
                GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance(Locale.US);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                startDate = cal.getTime();
            }
            if (null == toDate) {
                GregorianCalendar calEnd = (GregorianCalendar) GregorianCalendar.getInstance(Locale.US);
                toDate = calEnd.getTime();
            }

            items = transportStaffFacade.findSysTransportStaffListByCriteria(tpstaff_find, null, "Y");
            Double totalSum = 0.0;
            for (SysTransportStaff transstaff : items) {
                List<Date> dates = new ArrayList<>();
                
                List<SysTransportation> staffs = transporterFacade.findStaffSysTransportationListByCriteria(transstaff, Constants.TRANSPORTATION_COMPLETE, startDate, toDate);//staff
                List<SysTransportation> follow_staffs1 = transporterFacade.findStafffollow1SysTransportationListByCriteria(transstaff, Constants.TRANSPORTATION_COMPLETE, startDate, toDate);//staff follow 1
                List<SysTransportation> follow_staffs2 = transporterFacade.findStafffollow2SysTransportationListByCriteria(transstaff, Constants.TRANSPORTATION_COMPLETE, startDate, toDate);//staff follow  2  

                transstaff.setSysTransportationList(staffs);
                transstaff.setSysTransportationList1(follow_staffs1);
                transstaff.setSysTransportationList2(follow_staffs2);
                
                
                //ค่า OT
                Double statfs_ = new CalculateSalaryStaff().calculaterOTandStaffExternal(staffs, transstaff.getEarningPerday(),transstaff.getTransportType());
                Double follow_staffs1_ = new CalculateSalaryStaff().calculaterOTandFollowStaff(follow_staffs1, transstaff.getDailyWage(),transstaff.getTransportType());
                Double follow_staffs2_ = new CalculateSalaryStaff().calculaterOTandFollowStaff(follow_staffs2, transstaff.getDailyWage(),transstaff.getTransportType());
                transstaff.setValueWorking(statfs_ + follow_staffs1_ + follow_staffs2_);
                
             
                //คำนวณค่าเบี้ยเลี้ยง
                for (SysTransportation tpStaff : staffs) {
                   if (!dates.contains(tpStaff.getTpOrderDate()))dates.add(tpStaff.getTpOrderDate());
                }
                for (SysTransportation tpStaff : follow_staffs1) {
                   if (!dates.contains(tpStaff.getTpOrderDate()))dates.add(tpStaff.getTpOrderDate());
                }
                for (SysTransportation tpStaff : follow_staffs2) {
                   if (!dates.contains(tpStaff.getTpOrderDate()))dates.add(tpStaff.getTpOrderDate());
                }
                Double allowance=(dates.size()>0) ? dates.size()*(null !=transstaff.getAllowance()?transstaff.getAllowance():0): 0.0;
                transstaff.setTotalAllowance(allowance);
                transstaff.setPerTrip(dates.size());
                
                //รายได้ประจำของพนักงาน
                Double statffSalary=0.0;
                if(Objects.equals(Constants.TRANSPORT_STAFF,transstaff.getTransportstaffType())){
                    statffSalary=(null !=transstaff.getSalary()?transstaff.getSalary():0);
                }else{
                    statffSalary=(null !=transstaff.getDailyWage()?transstaff.getDailyWage():0)*((dates.size()>0) ? dates.size():0.0);
                }
                Double salary=statffSalary+(null !=transstaff.getRentHouse()?transstaff.getRentHouse():0)+(null !=transstaff.getTelCharge()?transstaff.getTelCharge():0);
                transstaff.setTotalAmount(salary);

                //Special staff
                List<SysTransportStaffSpecial> transportationSpecial= transporterFacade.findSysTransportStaffSpecialListByCriteria(transstaff, Constants.TRANSPORT_STAFF_SPECIAL, startDate, toDate);
                Double special_ = 0.0;
                for (SysTransportStaffSpecial special : transportationSpecial) {
                    Double total_=0.0;
                    for (SysTransportStaffSpecialDetail specialDetail : special.getSysTransportStaffSpecialDetailList()) {
                        special_ += (null != specialDetail.getAmount() ? specialDetail.getAmount() : 0.0);
                        total_+=(null !=specialDetail.getAmount()?specialDetail.getAmount():0.0);
                    }
                    special.setTotalSpcial(total_);
                }
                transstaff.setSysTransportStaffSpecialList(transportationSpecial);
                transstaff.setTotalSpecial(special_);//รายได้พิเศษ
                
                //Special staff no vat
                List<SysTransportStaffSpecial> transportationSpecialNovat= transporterFacade.findSysTransportStaffSpecialListByCriteria(transstaff, Constants.TRANSPORT_STAFF_SPECIAL_NO_VAT, startDate, toDate);
                Double specialNovat_ = 0.0;
                for (SysTransportStaffSpecial special : transportationSpecialNovat) {
                    Double total_=0.0;
                    for (SysTransportStaffSpecialDetail specialDetail : special.getSysTransportStaffSpecialDetailList()) {
                        specialNovat_ += (null != specialDetail.getAmount() ? specialDetail.getAmount() : 0.0);
                        total_+=(null !=specialDetail.getAmount()?specialDetail.getAmount():0.0);
                    }
                    special.setTotalSpcial(total_);
                }
                transstaff.setSysTransportStaffSpecialNovatList(transportationSpecialNovat);
                transstaff.setTotalSpecialnoVat(specialNovat_);
                
                Double incomeNotVat=(transstaff.getTotalAmount()+transstaff.getValueWorking()+transstaff.getTotalAllowance()+transstaff.getTotalSpecial());
                //Double totalVat=incomeNotVat*0.03;
                Double totalVat=incomeNotVat*0.00;
                transstaff.setTotalIncome(incomeNotVat);
                transstaff.setTotalIncomeVat(totalVat);//หัก 3%
                transstaff.setTotalIncomeNet(incomeNotVat-totalVat);//รายได้รวมหลังหัก 3%
                
                //Exp staff
                List<SysTranspostationExp> transportationExp = transporterFacade.findSysTranspostationExpListByCriteria(transstaff, startDate, toDate);
                Double expenses = 0.0;
                for (SysTranspostationExp exp : transportationExp) {
                    Double total_=0.0;
                    for (SysTransportationExpDetail expDetail : exp.getSysTransportationExpDetailList()) {
                        expenses += (null != expDetail.getAmount() ? expDetail.getAmount() : 0.0);
                        total_+=(null !=expDetail.getAmount()?expDetail.getAmount():0.0);
                    }
                    exp.setTotalExp(total_);
                }
                transstaff.setTransportationExp(transportationExp);
                transstaff.setTotalExp(expenses);
                
                transstaff.setTotallastNet((transstaff.getTotalIncomeNet()+specialNovat_)-expenses);
                totalSum=totalSum+transstaff.getTotallastNet();
                
                
                
            }
            totalSummary=StringUtil.numberFormat(totalSum, "#,##0.00");
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }


    @Override
    public void reportPDF() {
        try {
            ReportUtil report = new ReportUtil();
            List reportList_ = new ArrayList<>();
            List<TransporterReportBean> reportList_main = new ArrayList<>();
            List<TransporterReportBean> reportList = new ArrayList<>();
            
            if(null !=selected.getSalary() || null !=selected.getDailyWage()){
                TransporterReportBean bean1 = new TransporterReportBean();
                String salary_value="";
                if(Objects.equals(Constants.TRANSPORT_STAFF,selected.getTransportstaffType())){
                    bean1.setDetail("เงินเดือน");
                    salary_value = NumberUtils.numberFormat((null !=selected.getSalary()?selected.getSalary():0.0), "#,##0.00");

                }else{
                   bean1.setDetail("รายได้ต่อวัน ("+selected.getPerTrip()+")"); 
                   Double saraly_day =(null !=selected.getDailyWage()?selected.getDailyWage():0.0)*((selected.getPerTrip()>0) ? selected.getPerTrip():0.0);
                   salary_value= NumberUtils.numberFormat(saraly_day, "#,##0.00");
                }
                bean1.setAmount((StringUtils.equals("0.00", salary_value)) ? "" : salary_value);
                reportList.add(bean1);   
            }
            
             //
           /*
            if(null !=selected.getAllowance()){
                TransporterReportBean bean2 = new TransporterReportBean();
                bean2.setDetail("เบี้ยเลี้ยงต่อวัน"); 
                String value = NumberUtils.numberFormat((null != selected.getAllowance()) ? selected.getAllowance() : 0.0, "#,##0.00");
                bean2.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                reportList.add(bean2);
            }
            */

            if(null !=selected.getRentHouse()){
                TransporterReportBean bean2 = new TransporterReportBean();
                bean2.setDetail("ค่าเช่าบ้าน");
                String value = NumberUtils.numberFormat((null != selected.getRentHouse()) ? selected.getRentHouse() : 0.0, "#,##0.00");
                bean2.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                reportList.add(bean2);
            }
            
            if(null !=selected.getTelCharge()){
                TransporterReportBean bean2 = new TransporterReportBean();
                bean2.setDetail("ค่าโทรศัพท์");
                String value = NumberUtils.numberFormat((null != selected.getTelCharge()) ? selected.getTelCharge() : 0.0, "#,##0.00");
                bean2.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                reportList.add(bean2);
            }
           
            List<SysTransportation> list = selected.getSysTransportationList();
            if (!list.isEmpty()) {
                TransporterReportBean bean = new TransporterReportBean();
                bean.setDetail("** พนักงานขับรถ **");
                reportList.add(bean);
                Double ot=0.0;
                for (SysTransportation tpStaff : list) {
                    ot+=(null != tpStaff.getWorkMoneyOT()) ? tpStaff.getWorkMoneyOT() : 0.0;
                }
                TransporterReportBean beanOT = new TransporterReportBean(); 
                if(Objects.equals(Constants.TRANSPORT_TYPE_EXTERNAL, selected.getTransportType())){
                    beanOT.setDetail("รวมรายได้");
                    beanOT.setWorkunit("พนักงานขับรถภายนอก");
                }else{
                    beanOT.setDetail("รวม OT");
                }
                String value = NumberUtils.numberFormat(ot, "#,##0.00");
                beanOT.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                reportList.add(beanOT);
            }

            List<SysTransportation> listfollow1 = selected.getSysTransportationList1();
            if (!listfollow1.isEmpty()) {
                TransporterReportBean bean = new TransporterReportBean();
                bean.setDetail("** พนักงานติดรถ(1) **");
                reportList.add(bean);
                Double ot=0.0;
                for (SysTransportation tpStaff : listfollow1) {
                     ot+=(null != tpStaff.getWorkMoneyOT()) ? tpStaff.getWorkMoneyOT() : 0.0;
                }
                TransporterReportBean beanOT = new TransporterReportBean();
                beanOT.setDetail("รวม OT");
                String value = NumberUtils.numberFormat(ot, "#,##0.00");
                beanOT.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                reportList.add(beanOT);
            }

            List<SysTransportation> listfollow2 = selected.getSysTransportationList2();
            if (!listfollow2.isEmpty()) {
                TransporterReportBean bean = new TransporterReportBean();
                bean.setDetail("** พนักงานติดรถ(2) **");
                reportList.add(bean);
                Double ot=0.0;
                for (SysTransportation tpStaff : listfollow2) {
                    ot+=(null != tpStaff.getWorkMoneyOT()) ? tpStaff.getWorkMoneyOT() : 0.0;
                }
                TransporterReportBean beanOT = new TransporterReportBean();
                beanOT.setDetail("รวม OT");
                String value = NumberUtils.numberFormat(ot, "#,##0.00");
                beanOT.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                reportList.add(beanOT);
            }
            
            //
            if(null !=selected.getTotalAllowance()){
                TransporterReportBean bean2 = new TransporterReportBean();
                bean2.setDetail("เบี้ยเลี้ยงต่อวัน ("+selected.getPerTrip()+")"); 
                String value = NumberUtils.numberFormat(selected.getTotalAllowance(), "#,##0.00");
                bean2.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                reportList.add(bean2);
            }
            

            int intRunningNo2 = 0;
            List<SysTransportStaffSpecial> listSpecial1 = selected.getSysTransportStaffSpecialList();
            if (!listSpecial1.isEmpty()) {
                TransporterReportBean bean = new TransporterReportBean();
                bean.setDetail("** รายได้(พิเศษ) **");
                reportList.add(bean);
                for (SysTransportStaffSpecial special : listSpecial1) {
                     TransporterReportBean bean3 = new TransporterReportBean();
                     bean3.setSeq(String.valueOf(intRunningNo2 += 1));
                     bean3.setDetail(DateTimeUtil.cvtDateForShow(special.getSpecialtpDate(), "dd/MM/yyyy", new Locale("th", "TH")));//วันที่ทำรายการ
                     reportList.add(bean3);
                    for (SysTransportStaffSpecialDetail specialDetail : special.getSysTransportStaffSpecialDetailList()) {
                        TransporterReportBean beandetail = new TransporterReportBean();
                        beandetail.setWorkunit(specialDetail.getSpecialDesc());
                        String value = NumberUtils.numberFormat((null != specialDetail.getAmount()) ? specialDetail.getAmount() : 0.0, "#,##0.00");
                        beandetail.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                        reportList.add(beandetail);
                    }
                }
            }
            
            int intRunningNo3 = 0;
            List<SysTransportStaffSpecial> listSpecialNoVat = selected.getSysTransportStaffSpecialNovatList();
            if (!listSpecialNoVat.isEmpty()) {
                TransporterReportBean bean = new TransporterReportBean();
                bean.setDetail("** รายได้(พิเศษไม่หัก 3%) **");
                reportList.add(bean);
                for (SysTransportStaffSpecial special : listSpecialNoVat) {
                     TransporterReportBean bean3 = new TransporterReportBean();
                     bean3.setSeq(String.valueOf(intRunningNo3 += 1));
                     bean3.setDetail(DateTimeUtil.cvtDateForShow(special.getSpecialtpDate(), "dd/MM/yyyy", new Locale("th", "TH")));//วันที่ทำรายการ
                     reportList.add(bean3);
                    for (SysTransportStaffSpecialDetail specialDetail : special.getSysTransportStaffSpecialDetailList()) {
                        TransporterReportBean beandetail = new TransporterReportBean();
                        beandetail.setWorkunit(specialDetail.getSpecialDesc());
                        String value = NumberUtils.numberFormat((null != specialDetail.getAmount()) ? specialDetail.getAmount() : 0.0, "#,##0.00");
                        beandetail.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                        reportList.add(beandetail);
                    }
                }
            }
            
            int intRunningNo1 = 0;
            List<SysTranspostationExp> listExp = selected.getTransportationExp();
            if (!listExp.isEmpty()) {
                TransporterReportBean bean = new TransporterReportBean();
                bean.setDetail("** ค่าใช้จ่าย **");
                reportList.add(bean);
                for (SysTranspostationExp exp : listExp) {
                     TransporterReportBean bean3 = new TransporterReportBean();
                     bean3.setSeq(String.valueOf(intRunningNo1 += 1));
                     bean3.setDetail(DateTimeUtil.cvtDateForShow(exp.getExptpDate(), "dd/MM/yyyy", new Locale("th", "TH")));//วันที่ทำรายการ
                     reportList.add(bean3);
                    for (SysTransportationExpDetail expDetail : exp.getSysTransportationExpDetailList()) {
                        TransporterReportBean beandetail = new TransporterReportBean();
                        beandetail.setWorkunit(expDetail.getDeductionId().getDeductionDesc());
                        String value = NumberUtils.numberFormat((null != expDetail.getAmount()) ? expDetail.getAmount() : 0.0, "#,##0.00");
                        beandetail.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                        reportList.add(beandetail);
                    }
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
            
            map.put("total_amount",NumberUtils.numberFormat(selected.getTotalIncome(), "#,##0.00"));
            map.put("total_tax",NumberUtils.numberFormat(selected.getTotalIncomeVat(), "#,##0.00"));
            map.put("total",NumberUtils.numberFormat(selected.getTotalIncomeNet(), "#,##0.00"));
            map.put("total_exp",NumberUtils.numberFormat(selected.getTotalExp(), "#,##0.00"));
            map.put("total_notax",NumberUtils.numberFormat(selected.getTotalSpecialnoVat(), "#,##0.00"));
            map.put("totalnet",NumberUtils.numberFormat(selected.getTotallastNet(), "#,##0.00"));


            map.put("reportCode", "T107");
            report.exportSubReport("t107", new String[]{"T107Report", "T107SubReport"}, "T107", map, reportList_);
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
                    
                    if(null !=sysTransportStaff.getSalary() || null !=sysTransportStaff.getDailyWage()){
                        TransporterReportBean bean1 = new TransporterReportBean();
                        String salary_value = "";
                        if (Objects.equals(Constants.TRANSPORT_STAFF, sysTransportStaff.getTransportstaffType())) {
                            bean1.setDetail("เงินเดือน");
                            salary_value = NumberUtils.numberFormat((null != sysTransportStaff.getSalary() ? sysTransportStaff.getSalary() : 0.0), "#,##0.00");

                        } else {
                            bean1.setDetail("รายได้ต่อวัน (" + sysTransportStaff.getPerTrip() + ")");
                            Double saraly_day = (null != sysTransportStaff.getDailyWage() ? sysTransportStaff.getDailyWage() : 0.0) * ((sysTransportStaff.getPerTrip() > 0) ? sysTransportStaff.getPerTrip() : 0.0);
                            salary_value = NumberUtils.numberFormat(saraly_day, "#,##0.00");
                        }
                        bean1.setAmount((StringUtils.equals("0.00", salary_value)) ? "" : salary_value);
                        reportList.add(bean1);
                    }

                     //
                    /*
                    if(null !=sysTransportStaff.getAllowance()){
                        TransporterReportBean bean2 = new TransporterReportBean();
                        bean2.setDetail("เบี้ยเลี้ยงต่อวัน"); 
                        String value = NumberUtils.numberFormat((null != sysTransportStaff.getAllowance()) ? sysTransportStaff.getAllowance() : 0.0, "#,##0.00");
                        bean2.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                        reportList.add(bean2);
                    }
                    */
                    
                    if(null !=sysTransportStaff.getRentHouse()){
                        TransporterReportBean bean2 = new TransporterReportBean();
                        bean2.setDetail("ค่าเช่าบ้าน");
                        String value = NumberUtils.numberFormat((null != sysTransportStaff.getRentHouse()) ? sysTransportStaff.getRentHouse() : 0.0, "#,##0.00");
                        bean2.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                        reportList.add(bean2);
                    }

                    if(null !=sysTransportStaff.getTelCharge()){
                        TransporterReportBean bean2 = new TransporterReportBean();
                        bean2.setDetail("ค่าโทรศัพท์");
                        String value = NumberUtils.numberFormat((null != sysTransportStaff.getTelCharge()) ? sysTransportStaff.getTelCharge() : 0.0, "#,##0.00");
                        bean2.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                        reportList.add(bean2);
                    }

                    List<SysTransportation> list = sysTransportStaff.getSysTransportationList();
                    if (!list.isEmpty()) {
                        TransporterReportBean bean = new TransporterReportBean();
                        bean.setDetail("** พนักงานขับรถ **");
                        reportList.add(bean);
                        Double ot=0.0;
                        for (SysTransportation tpStaff : list) {
                            ot+=(null != tpStaff.getWorkMoneyOT()) ? tpStaff.getWorkMoneyOT() : 0.0;
                        }
                        TransporterReportBean beanOT = new TransporterReportBean();
                        if (Objects.equals(Constants.TRANSPORT_TYPE_EXTERNAL, sysTransportStaff.getTransportType())) {
                            beanOT.setDetail("รวมรายได้");
                            beanOT.setWorkunit("พนักงานขับรถภายนอก");
                        } else {
                            beanOT.setDetail("รวม OT");
                        }
                        String value = NumberUtils.numberFormat(ot, "#,##0.00");
                        beanOT.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                        reportList.add(beanOT);
                    }

                    List<SysTransportation> listfollow1 = sysTransportStaff.getSysTransportationList1();
                    if (!listfollow1.isEmpty()) {
                        TransporterReportBean bean = new TransporterReportBean();
                        bean.setDetail("** พนักงานติดรถ(1) **");
                        reportList.add(bean);
                        Double ot=0.0;
                        for (SysTransportation tpStaff : listfollow1) {
                             ot+=(null != tpStaff.getWorkMoneyOT()) ? tpStaff.getWorkMoneyOT() : 0.0;
                        }
                        TransporterReportBean beanOT = new TransporterReportBean();
                        beanOT.setDetail("รวม OT");
                        String value = NumberUtils.numberFormat(ot, "#,##0.00");
                        beanOT.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                        reportList.add(beanOT);
                    }

                    List<SysTransportation> listfollow2 = sysTransportStaff.getSysTransportationList2();
                    if (!listfollow2.isEmpty()) {
                        TransporterReportBean bean = new TransporterReportBean();
                        bean.setDetail("** พนักงานติดรถ(2) **");
                        reportList.add(bean);
                        Double ot=0.0;
                        for (SysTransportation tpStaff : listfollow2) {
                            ot+=(null != tpStaff.getWorkMoneyOT()) ? tpStaff.getWorkMoneyOT() : 0.0;
                        }
                        TransporterReportBean beanOT = new TransporterReportBean();
                        beanOT.setDetail("รวม OT");
                        String value = NumberUtils.numberFormat(ot, "#,##0.00");
                        beanOT.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                        reportList.add(beanOT);
                    }

                    //
                    if (null != sysTransportStaff.getTotalAllowance()) {
                        TransporterReportBean bean2 = new TransporterReportBean();
                        bean2.setDetail("เบี้ยเลี้ยงต่อวัน (" + sysTransportStaff.getPerTrip() + ")");
                        String value = NumberUtils.numberFormat(sysTransportStaff.getTotalAllowance(), "#,##0.00");
                        bean2.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                        reportList.add(bean2);
                    }
                    
                    
                   if (null != sysTransportStaff.getSysTransportStaffSpecialList()) {
                     int intRunningNo2 = 0;
                     List<SysTransportStaffSpecial> listSpecial1 = sysTransportStaff.getSysTransportStaffSpecialList();
                     if (!listSpecial1.isEmpty()) {
                         TransporterReportBean bean = new TransporterReportBean();
                         bean.setDetail("** รายได้(พิเศษ) **");
                         reportList.add(bean);
                         for (SysTransportStaffSpecial special : listSpecial1) {
                             TransporterReportBean bean3 = new TransporterReportBean();
                             bean3.setSeq(String.valueOf(intRunningNo2 += 1));
                             bean3.setDetail(DateTimeUtil.cvtDateForShow(special.getSpecialtpDate(), "dd/MM/yyyy", new Locale("th", "TH")));//วันที่ทำรายการ
                             reportList.add(bean3);
                             for (SysTransportStaffSpecialDetail specialDetail : special.getSysTransportStaffSpecialDetailList()) {
                                 TransporterReportBean beandetail = new TransporterReportBean();
                                 beandetail.setWorkunit(specialDetail.getSpecialDesc());
                                 String value = NumberUtils.numberFormat((null != specialDetail.getAmount()) ? specialDetail.getAmount() : 0.0, "#,##0.00");
                                 beandetail.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                                 reportList.add(beandetail);
                             }
                         }
                     }
                 }

                 if (null != sysTransportStaff.getSysTransportStaffSpecialNovatList()) {
                     int intRunningNo3 = 0;
                     List<SysTransportStaffSpecial> listSpecialNoVat = sysTransportStaff.getSysTransportStaffSpecialNovatList();
                     if (!listSpecialNoVat.isEmpty()) {
                         TransporterReportBean bean = new TransporterReportBean();
                         bean.setDetail("** รายได้(พิเศษไม่หัก 3%) **");
                         reportList.add(bean);
                         for (SysTransportStaffSpecial special : listSpecialNoVat) {
                             TransporterReportBean bean3 = new TransporterReportBean();
                             bean3.setSeq(String.valueOf(intRunningNo3 += 1));
                             bean3.setDetail(DateTimeUtil.cvtDateForShow(special.getSpecialtpDate(), "dd/MM/yyyy", new Locale("th", "TH")));//วันที่ทำรายการ
                             reportList.add(bean3);
                             for (SysTransportStaffSpecialDetail specialDetail : special.getSysTransportStaffSpecialDetailList()) {
                                 TransporterReportBean beandetail = new TransporterReportBean();
                                 beandetail.setWorkunit(specialDetail.getSpecialDesc());
                                 String value = NumberUtils.numberFormat((null != specialDetail.getAmount()) ? specialDetail.getAmount() : 0.0, "#,##0.00");
                                 beandetail.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                                 reportList.add(beandetail);
                             }
                         }
                     }
                 }

                 if (null != sysTransportStaff.getTransportationExp()) {
                     int intRunningNo1 = 0;
                     List<SysTranspostationExp> listExp = sysTransportStaff.getTransportationExp();
                     if (!listExp.isEmpty()) {
                         TransporterReportBean bean = new TransporterReportBean();
                         bean.setDetail("** ค่าใช้จ่าย **");
                         reportList.add(bean);
                         for (SysTranspostationExp exp : listExp) {
                             TransporterReportBean bean3 = new TransporterReportBean();
                             bean3.setSeq(String.valueOf(intRunningNo1 += 1));
                             bean3.setDetail(DateTimeUtil.cvtDateForShow(exp.getExptpDate(), "dd/MM/yyyy", new Locale("th", "TH")));//วันที่ทำรายการ
                             reportList.add(bean3);
                             for (SysTransportationExpDetail expDetail : exp.getSysTransportationExpDetailList()) {
                                 TransporterReportBean beandetail = new TransporterReportBean();
                                 beandetail.setWorkunit(expDetail.getDeductionId().getDeductionDesc());
                                 String value = NumberUtils.numberFormat((null != expDetail.getAmount()) ? expDetail.getAmount() : 0.0, "#,##0.00");
                                 beandetail.setAmount((StringUtils.equals("0.00", value)) ? "" : value);
                                 reportList.add(beandetail);
                             }
                         }
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
                    map.put("staff_type", Objects.equals(Constants.TRANSPORT_STAFF,sysTransportStaff.getTransportstaffType())?"พนักงานขับรถ":"พนักงานติดรถ");

                    map.put("total_amount", NumberUtils.numberFormat(sysTransportStaff.getTotalIncome(), "#,##0.00"));
                    map.put("total_tax", NumberUtils.numberFormat(sysTransportStaff.getTotalIncomeVat(), "#,##0.00"));
                    map.put("total", NumberUtils.numberFormat(sysTransportStaff.getTotalIncomeNet(), "#,##0.00"));
                    map.put("total_exp", NumberUtils.numberFormat(sysTransportStaff.getTotalExp(), "#,##0.00"));
                    map.put("total_notax", NumberUtils.numberFormat(sysTransportStaff.getTotalSpecialnoVat(), "#,##0.00"));
                    map.put("totalnet", NumberUtils.numberFormat(sysTransportStaff.getTotallastNet(), "#,##0.00"));
            
                    map.put("reportCode", "T107");
                    JasperPrint print = report.exportSubReport_Template_mearge("template.jpg", "t107", new String[]{"T107Report", "T107SubReport"}, "T107", map, reportList_);
                    jasperPrintList.add(print);
             }
             if(!printSelected.isEmpty()){
                String pdfCode="T107";
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
      
       //Auto transporter_staff
    public List<SysTransportStaff> completeTransportStaff(String query) {
         List<SysTransportStaff> filteredSysTransportStaff = new ArrayList<>();
       try {
            List<SysTransportStaff> allTransportStaffs = transporterFacade.findSysTransportStaffList();
            for (SysTransportStaff sysTransportStaff:allTransportStaffs) {
               if(sysTransportStaff.getTransportstaffNickname()!=null && sysTransportStaff.getTransportstaffNickname().length()>0){
                if(sysTransportStaff.getTransportstaffNickname().toLowerCase().startsWith(query)) {
                    filteredSysTransportStaff.add(sysTransportStaff);
                }
               }
            }
         } catch (Exception ex) {
            LOG.error(ex);
        }
        return filteredSysTransportStaff;
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

    public SysTransportStaff getTpstaff_find() {
        return tpstaff_find;
    }

    public void setTpstaff_find(SysTransportStaff tpstaff_find) {
        this.tpstaff_find = tpstaff_find;
    }

    public String getTotalSummary() {
        return totalSummary;
    }

    public void setTotalSummary(String totalSummary) {
        this.totalSummary = totalSummary;
    }
    
    

}
