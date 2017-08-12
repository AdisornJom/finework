package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysBilling;
import com.finework.core.ejb.entity.SysBillingDetail;
import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysPrintBilling;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.bo.BillingBO;
import com.finework.ejb.bo.PrintBillingBO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn J.
 */
@Stateless
public class BillingFacade {

    @EJB
    private BillingBO billingBO;
    @EJB
    private PrintBillingBO printBillingBO;

   
    public List<SysBilling> findSysBillingList(String billingType) throws Exception {
       return billingBO.findSysBillingList(billingType);
    }

    public List<SysBilling> findSysBillingListByCriteria(String billingType,String documentno,String companyName, Date startDate, Date toDate) throws Exception {
       return billingBO.findSysBillingListByCriteria(billingType,documentno,companyName, startDate, toDate);
    }
    
    //=======================================
    public List<SysBilling> findSysBillingListByCriteria(String billingType,String documentno,String companyName, Date startDate, Date toDate, int[] range) throws Exception {
       return billingBO.findSysBillingListByCriteria(billingType,documentno,companyName, startDate, toDate,range);
    }
    
    public int countSysBillingListByCriteria(String billingType,String documentno,String companyName, Date startDate, Date toDate) throws Exception {
       return billingBO.countSysBillingListByCriteria(billingType,documentno,companyName, startDate, toDate);
    }
    
    //======================================
    public List<SysBilling> findSysBillingListByCriteriaForReport(List<String> listNotBillingType,String billingType,String documentno,String companyName, Date startDate, Date toDate, int[] range) throws Exception {
       return billingBO.findSysBillingListByCriteriaForReport(listNotBillingType,billingType,documentno,companyName, startDate, toDate,range);
    }
    
    public int countSysBillingListByCriteriaForReport(List<String> listNotBillingType,String billingType,String documentno,String companyName, Date startDate, Date toDate) throws Exception {
       return billingBO.countSysBillingListByCriteriaForReport(listNotBillingType,billingType,documentno,companyName, startDate, toDate);
    }
    
    
    //=================================
    public List<SysBilling> findSysBillingWorkunitList(String billingType,String billingType2,SysWorkunit workunit) throws Exception {
       return billingBO.findSysBillingWorkunitList(billingType,billingType2,workunit);
    }
    
    public List<SysBilling> findSysBillingCustomerWorkunitListByCriteria(String billingType,String billingType2,String documentno,SysCustomer customer,SysWorkunit workunit) throws Exception {
       return billingBO.findSysBillingCustomerWorkunitListByCriteria(billingType,billingType2,documentno,customer,workunit);
    }
    
    
    public SysBilling  findByPK(Integer id){
       return  billingBO.findByPK(id);
    }
   
    public void createSysBilling(SysBilling sysBilling) throws Exception {
       billingBO.createSysBilling(sysBilling);
    }

   
    public void editSysBilling(SysBilling sysBilling) throws Exception {
        billingBO.editSysBilling(sysBilling);
    }

   
    public void deleteSysBilling(SysBilling sysBilling) throws Exception {
       billingBO.deleteSysBilling(sysBilling);
    }
//=======================================================
    public void createSysBillingDetail(SysBillingDetail sysBillingDetail) throws Exception{
        billingBO.createSysBillingDetail(sysBillingDetail);
    }
    
    public void editSysBillingDetail(SysBillingDetail sysBillingDetail) throws Exception{
        billingBO.editSysBillingDetail(sysBillingDetail);
    }
    
    public void deleteSysBillingDetail (SysBillingDetail sysBillingDetail) throws Exception{
        billingBO.deleteSysBillingDetail(sysBillingDetail);
    }
    
    public void deleteBillingIdOnDetail(Integer billingId) throws Exception {
        billingBO.deleteBillingIdOnDetail(billingId);
    }
    
    public void createSysPrintBilling(SysPrintBilling sysPrintBilling) throws Exception {
       printBillingBO.createSysPrintBilling(sysPrintBilling);
    }
}
