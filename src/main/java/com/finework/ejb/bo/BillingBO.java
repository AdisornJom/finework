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

@Stateless(name="finework.BillingBO")
public class BillingBO
{

  @EJB
  private SysBillingDAO sysBillingDAO;

  @EJB
  private SysBillingDetailDAO sysBillingDetailDAO;

  public List<SysBilling> findSysBillingList(String billingType)
    throws Exception
  {
    return this.sysBillingDAO.findSysBillingList(billingType);
  }

  public List<SysBilling> findSysBillingListByCriteria(String billingType, String documentno, String companyName, Date startDate, Date toDate, int[] range) throws Exception {
    List<SysBilling> sysBilling = this.sysBillingDAO.findSysBillingListByCriteria(billingType, documentno, companyName, startDate, toDate, range);
    for (SysBilling u : sysBilling) {
      u.getSysPrintBillingList().toString();
    }
    return sysBilling;
  }

  public int countSysBillingListByCriteria(String billingType, String documentno, String companyName, Date startDate, Date toDate) throws Exception {
    return this.sysBillingDAO.countSysBillingListByCriteria(billingType, documentno, companyName, startDate, toDate);
  }

  public List<SysBilling> findSysBillingListByCriteriaForReport(List<String> listNotBillingType, String billingType, String documentno, String companyName, Date startDate, Date toDate, int[] range) throws Exception {
    List<SysBilling> sysBilling = this.sysBillingDAO.findSysBillingListByCriteriaForReport(listNotBillingType, billingType, documentno, companyName, startDate, toDate, range);
    for (SysBilling u : sysBilling) {
      u.getSysPrintBillingList().toString();
    }
    return sysBilling;
  }

  public int countSysBillingListByCriteriaForReport(List<String> listNotBillingType, String billingType, String documentno, String companyName, Date startDate, Date toDate) throws Exception {
    return this.sysBillingDAO.countSysBillingListByCriteriaForReport(listNotBillingType, billingType, documentno, companyName, startDate, toDate);
  }

  public List<SysBilling> findSysBillingListByCriteria(String billingType, String documentno, String companyName, Date startDate, Date toDate) throws Exception
  {
    List<SysBilling> sysBilling = this.sysBillingDAO.findSysBillingListByCriteria(billingType, documentno, companyName, startDate, toDate);
    for (SysBilling u : sysBilling) {
      u.getSysPrintBillingList().toString();
    }
    return sysBilling;
  }

  public List<SysBilling> findSysBillingWorkunitList(String billingType, String billingType2, SysWorkunit workunit) throws Exception
  {
    return this.sysBillingDAO.findSysBillingWorkunitList(billingType, billingType2, workunit);
  }

  public List<SysBilling> findSysBillingCustomerWorkunitListByCriteria(String billingType, String billingType2, String documtnno, SysCustomer customer, SysWorkunit workunit) throws Exception {
    return this.sysBillingDAO.findSysBillingCustomerWorkunitListByCriteria(billingType, billingType2, documtnno, customer, workunit);
  }

  public SysBilling findByPK(Integer id)
  {
    return (SysBilling)this.sysBillingDAO.find(id);
  }

  public void createSysBilling(SysBilling sysBilling) throws Exception {
    this.sysBillingDAO.create(sysBilling);
  }

  public void editSysBilling(SysBilling sysBilling) throws Exception {
    this.sysBillingDAO.edit(sysBilling);
  }

  public void deleteSysBilling(SysBilling sysBilling) throws Exception {
    this.sysBillingDAO.remove(sysBilling);
  }

  public void createSysBillingDetail(SysBillingDetail sysBillingDetail) throws Exception {
    this.sysBillingDetailDAO.create(sysBillingDetail);
  }

  public void editSysBillingDetail(SysBillingDetail sysBillingDetail) throws Exception {
    this.sysBillingDetailDAO.edit(sysBillingDetail);
  }

  public void deleteSysBillingDetail(SysBillingDetail sysBillingDetail) throws Exception {
    this.sysBillingDetailDAO.remove(sysBillingDetail);
  }

  public void deleteBillingIdOnDetail(Integer billingId) throws Exception {
    this.sysBillingDetailDAO.deleteBillingIdOnDetail(billingId);
  }
}