package com.finework.ejb.facade;

import com.finework.core.ejb.entity.ReportR105TO;
import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysPaymentManufactoryDetail;
import com.finework.core.ejb.entity.SysPaymentManufactory;
import com.finework.ejb.bo.PaymentManufactoryBO;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn J.
 */
@Stateless
public class PaymentManufactoryFacade {

    @EJB
    private PaymentManufactoryBO paymentManufactoryBO;
   
    public List<SysPaymentManufactory> findSysPaymentManufactoryList() throws Exception {
       return paymentManufactoryBO.findSysPaymentManufactoryList();
    }
    
    public List<SysPaymentManufactory> findSysPaymentManufactoryList(String documentno,SysContractor contractor) throws Exception {
        return paymentManufactoryBO.findSysPaymentManufactoryList(documentno,contractor);
    }

    public List<SysPaymentManufactory> findSysPaymentManufactoryOverDrawListByCriteria(String documentno,SysContractor contractor, Date startDate, Date toDate) throws Exception {
       return paymentManufactoryBO.findSysPaymentManufactoryOverDrawListByCriteria(documentno,contractor, startDate, toDate);
    }
    
     
    public List<SysPaymentManufactory> findSysPaymentManufactoryListByCriteria(String documentno,SysContractor contractor, Date startDate, Date toDate) throws Exception {
       return paymentManufactoryBO.findSysPaymentManufactoryListByCriteria(documentno,contractor, startDate, toDate);
    }
    
    public SysPaymentManufactory findSysPaymentManufactoryById(Integer id){
      return  paymentManufactoryBO.findSysPaymentManufactoryById(id);
    }

    //=======================================
    public List<SysPaymentManufactory> findSysPaymentManufactoryListByCriteria(String documentno,SysContractor contractor, Date startDate, Date toDate, int[] range) throws Exception {
       return paymentManufactoryBO.findSysPaymentManufactoryListByCriteria(documentno,contractor, startDate, toDate,range);
    }
    
    public int countSysPaymentManufactoryListByCriteria(String documentno,SysContractor contractor, Date startDate, Date toDate) throws Exception {
       return paymentManufactoryBO.countSysPaymentManufactoryListByCriteria(documentno,contractor, startDate, toDate);
    }
    
    
    public SysPaymentManufactory findSysPaymentManufactory(SysPaymentManufactory sysManufactory) throws Exception {
        return paymentManufactoryBO.findSysPaymentManufactoryById(sysManufactory);
    }
   
    public void createSysPaymentManufactory(SysPaymentManufactory sysManufactory) throws Exception {
       paymentManufactoryBO.createSysPaymentManufactory(sysManufactory);
    }

   
    public void editSysPaymentManufactory(SysPaymentManufactory sysManufactory) throws Exception {
        paymentManufactoryBO.editSysPaymentManufactory(sysManufactory);
    }

   
    public void deleteSysPaymentManufactory(SysPaymentManufactory sysManufactory) throws Exception {
       paymentManufactoryBO.deleteSysPaymentManufactory(sysManufactory);
    }
//=======================================================
    public void createSysPaymentManufactoryDetail(SysPaymentManufactoryDetail sysPaymentManufactoryDetail) throws Exception{
        paymentManufactoryBO.createSysPaymentManufactoryDetail(sysPaymentManufactoryDetail);
    }
    
    public void editSysPaymentManufactoryDetail(SysPaymentManufactoryDetail sysPaymentManufactoryDetail) throws Exception{
        paymentManufactoryBO.editSysPaymentManufactoryDetail(sysPaymentManufactoryDetail);
    }
    
    public void deleteSysPaymentManufactoryDetail (SysPaymentManufactoryDetail sysPaymentManufactoryDetail) throws Exception{
        paymentManufactoryBO.deleteSysPaymentManufactoryDetail(sysPaymentManufactoryDetail);
    }
    
    public void deletePaymentManufactoryIdOnDetail(Integer factoryId) throws Exception {
        paymentManufactoryBO.deletePaymentManufactoryIdOnDetail(factoryId);
    }
    
    
    public void deletePaymentManufactoryExpensesIdOnDetail(Integer factoryId) throws Exception {
        paymentManufactoryBO.deletePaymentManufactoryExpensesIdOnDetail(factoryId);
    }
    
    //Report
    public List<ReportR105TO> findReportR105List(Integer contractorId, Date dateFrom, Date dateTo) throws Exception {
        return paymentManufactoryBO.findReportR105List(contractorId, dateFrom, dateTo);
    }
    
    public List<ReportR105TO> findReportR105List(Integer contractorId, Date dateFrom, Date dateTo, int[] range) throws Exception {
        return paymentManufactoryBO.findReportR105List(contractorId, dateFrom, dateTo, range);
    }
    
    public BigInteger count_reportR105List(Integer contractorId, Date dateFrom, Date dateTo) throws Exception {
        return paymentManufactoryBO.count_reportR105List(contractorId, dateFrom, dateTo);
    }
    
     public List<SysPaymentManufactory> findSysPaymentManufactoryR106List(Date dateFrom, Date dateTo, int[] range) throws Exception {
        return paymentManufactoryBO.findSysPaymentManufactoryR106List(dateFrom, dateTo, range);
    }
    
    public int countSysPaymentManufactoryR106List(Date dateFrom, Date dateTo) throws Exception {
        return paymentManufactoryBO.countSysPaymentManufactoryR106List(dateFrom, dateTo);
    }
    
    public BigDecimal sumSysPaymentManufactoryR106List(Date dateFrom, Date dateTo) throws Exception {
        return paymentManufactoryBO.sumSysPaymentManufactoryR106List(dateFrom, dateTo);
    }
   
}
