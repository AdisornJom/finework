package com.finework.ejb.bo;

import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysSequence;
import com.finework.ejb.dao.SysSequenceDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name="finework.SequenceBO")
public class SequenceBO
{

  @EJB
  private SysSequenceDAO sysSequenceDAO;

  public List<SysSequence> findSysSequenceList()
    throws Exception
  {
    return this.sysSequenceDAO.findSysSequenceList();
  }

  public List<SysSequence> findSysSequenceListByCriteria(String custName) throws Exception {
    return this.sysSequenceDAO.findSysSequenceListByCriteria(custName);
  }

  public List<SysCustomer> findCustNotSequenceList() throws Exception {
    return this.sysSequenceDAO.findCustNotSequenceList();
  }
  public SysSequence findSysSequenceByCustomerId(Integer custmerId) throws Exception {
    return this.sysSequenceDAO.findSysSequenceByCustomerId(custmerId);
  }

  public SysSequence findSysSequenceByCustomerIdRunningType(Integer custmerId, String runningType) throws Exception {
    return this.sysSequenceDAO.findSysSequenceByCustomerIdRunningType(custmerId, runningType);
  }

  public void createSequence(SysSequence sysSequence) throws Exception {
    this.sysSequenceDAO.create(sysSequence);
  }

  public void editSequence(SysSequence sysSequence) throws Exception {
    this.sysSequenceDAO.edit(sysSequence);
  }

  public void deleteSequence(SysSequence sysSequence) throws Exception {
    this.sysSequenceDAO.remove(sysSequence);
  }

  public Double findSysSequenceBillingNewByYearMonth(String yearMonth) throws Exception {
    return this.sysSequenceDAO.findSysSequenceBillingNewByYearMonth(yearMonth);
  }

  public Double findSysSequenceQuotationByYearMonth(String yearMonth) throws Exception {
    return this.sysSequenceDAO.findSysSequenceQuotationByYearMonth(yearMonth);
  }

  public Double findSysSequenceQuotationChildByYearMonth(String documentno) throws Exception {
    return this.sysSequenceDAO.findSysSequenceQuotationChildByYearMonth(documentno);
  }
}