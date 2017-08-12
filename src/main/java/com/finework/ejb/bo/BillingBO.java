package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysBilling;
import com.finework.core.ejb.entity.SysBillingDetail;
import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysWorkunit;
import com.finework.ejb.dao.SysBillingDAO;
import com.finework.ejb.dao.SysBillingDetailDAO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Adisorn j.
 */
@Stateless(name = "finework.BillingBO")
public class BillingBO {

    @EJB
    private SysBillingDAO sysBillingDAO;
    @EJB
    private SysBillingDetailDAO sysBillingDetailDAO;

    public List<SysBilling> findSysBillingList(String billingType) throws Exception {
        return sysBillingDAO.findSysBillingList(billingType);
    }
    
    public List<SysBilling> findSysBillingListByCriteria(String billingType,String documentno,String companyName, Date startDate, Date toDate, int[] range) throws Exception {
        List<SysBilling> sysBilling = sysBillingDAO.findSysBillingListByCriteria(billingType,documentno,companyName, startDate, toDate,range);
        for (SysBilling u : sysBilling) {
            u.getSysPrintBillingList().toString();
        }
        return sysBilling;
    }
    
    public int countSysBillingListByCriteria(String billingType,String documentno,String companyName, Date startDate, Date toDate) throws Exception {
       return sysBillingDAO.countSysBillingListByCriteria(billingType,documentno,companyName, startDate, toDate);
    }
    
     public List<SysBilling> findSysBillingListByCriteriaForReport(List<String> listNotBillingType,String billingType,String documentno,String companyName, Date startDate, Date toDate, int[] range) throws Exception {
        List<SysBilling> sysBilling = sysBillingDAO.findSysBillingListByCriteriaForReport(listNotBillingType,billingType,documentno,companyName, startDate, toDate,range);
        for (SysBilling u : sysBilling) {
            u.getSysPrintBillingList().toString();
        }
        return sysBilling;
    }
    
    public int countSysBillingListByCriteriaForReport(List<String> listNotBillingType,String billingType,String documentno,String companyName, Date startDate, Date toDate) throws Exception {
       return sysBillingDAO.countSysBillingListByCriteriaForReport(listNotBillingType,billingType,documentno,companyName, startDate, toDate);
    }
    
    
     public List<SysBilling> findSysBillingListByCriteria(String billingType,String documentno,String companyName, Date startDate, Date toDate) throws Exception {
        List<SysBilling> sysBilling = sysBillingDAO.findSysBillingListByCriteria(billingType,documentno,companyName, startDate, toDate);
        for (SysBilling u : sysBilling) {
            u.getSysPrintBillingList().toString();
        }
        return sysBilling;
    }
    
     
    public List<SysBilling> findSysBillingWorkunitList(String billingType,String billingType2,SysWorkunit workunit) throws Exception {
       return sysBillingDAO.findSysBillingWorkunitList(billingType,billingType2,workunit);
    }
    
    public List<SysBilling> findSysBillingCustomerWorkunitListByCriteria(String billingType,String billingType2,String documtnno,SysCustomer customer,SysWorkunit workunit) throws Exception {
       return sysBillingDAO.findSysBillingCustomerWorkunitListByCriteria(billingType,billingType2,documtnno,customer,workunit);
    }
    
     
  
    public SysBilling findByPK(Integer id){
      return  sysBillingDAO.find(id);
    }
    
    public void createSysBilling(SysBilling sysBilling) throws Exception{
        sysBillingDAO.create(sysBilling);
    }
    
    public void editSysBilling(SysBilling sysBilling) throws Exception{
        sysBillingDAO.edit(sysBilling);
    }
    
    public void deleteSysBilling (SysBilling sysBilling) throws Exception{
        sysBillingDAO.remove(sysBilling);
    }
    //=========================
     public void createSysBillingDetail(SysBillingDetail sysBillingDetail) throws Exception{
        sysBillingDetailDAO.create(sysBillingDetail);
    }
    
    public void editSysBillingDetail(SysBillingDetail sysBillingDetail) throws Exception{
        sysBillingDetailDAO.edit(sysBillingDetail);
    }
    
    public void deleteSysBillingDetail (SysBillingDetail sysBillingDetail) throws Exception{
        sysBillingDetailDAO.remove(sysBillingDetail);
    }
    
    public void deleteBillingIdOnDetail(Integer billingId) throws Exception {
        sysBillingDetailDAO.deleteBillingIdOnDetail(billingId);
    }
    
    
   
}
