package com.finework.ejb.facade;

import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysSequence;
import com.finework.ejb.bo.SequenceBO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class SequenceFacade
{

  @EJB
  private SequenceBO sequenceBO;

  public List<SysSequence> findSysSequenceList()
    throws Exception
  {
    return this.sequenceBO.findSysSequenceList();
  }

  public List<SysSequence> findSysSequenceListByCriteria(String custName) throws Exception
  {
    return this.sequenceBO.findSysSequenceListByCriteria(custName);
  }

  public List<SysCustomer> findCustNotSequenceList() throws Exception {
    return this.sequenceBO.findCustNotSequenceList();
  }

  public SysSequence findSysSequenceByCustomerId(Integer custmerId) throws Exception {
    return this.sequenceBO.findSysSequenceByCustomerId(custmerId);
  }

  public SysSequence findSysSequenceByCustomerIdRunningType(Integer custmerId, String runningType) throws Exception {
    return this.sequenceBO.findSysSequenceByCustomerIdRunningType(custmerId, runningType);
  }

  public void createSequence(SysSequence sysSequence) throws Exception {
    this.sequenceBO.createSequence(sysSequence);
  }

  public void editSequence(SysSequence sysSequence) throws Exception
  {
    this.sequenceBO.editSequence(sysSequence);
  }

  public void deleteSequence(SysSequence sysSequence) throws Exception
  {
    this.sequenceBO.deleteSequence(sysSequence);
  }

  public Double findSysSequenceBillingNewByYearMonth(String yearMonth) throws Exception {
    return this.sequenceBO.findSysSequenceBillingNewByYearMonth(yearMonth);
  }

  public Double findSysSequenceQuotationByYearMonth(String yearMonth) throws Exception {
    return this.sequenceBO.findSysSequenceQuotationByYearMonth(yearMonth);
  }

  public Double findSysSequenceQuotationChildByYearMonth(String documentno) throws Exception {
    return this.sequenceBO.findSysSequenceQuotationChildByYearMonth(documentno);
  }
}