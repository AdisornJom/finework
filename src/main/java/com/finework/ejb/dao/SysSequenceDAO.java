package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysSequence;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysSequenceDAO extends AbstractDAO<SysSequence>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysSequenceDAO() {
    super(SysSequence.class);
  }

  public List<SysSequence> findSysSequenceList()
  {
    Query q = this.em.createQuery("select o from SysSequence o ");
    return q.getResultList();
  }

  public List<SysSequence> findSysSequenceListByCriteria(String custName) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT o FROM SysSequence o ");
    sb.append("where 1=1");
    if ((null != custName) && (custName.length() > 0)) {
      sb.append("and o.customerId.customerNameTh like :custName ");
    }

    Query q = this.em.createQuery(sb.toString());
    if ((null != custName) && (custName.length() > 0)) {
      q.setParameter("custName", new StringBuilder().append("%").append(custName).append("%").toString());
    }

    return q.getResultList();
  }

  public SysSequence findSysSequenceByCustomerId(Integer customerId) {
    Query q = this.em.createQuery("select o from SysSequence o where o.customerId.customerId =:customerId and o.status='Y' ");

    q.setParameter("customerId", customerId);
    try
    {
      return (SysSequence)q.getSingleResult(); } catch (NoResultException e) {
    }
    return null;
  }

  public SysSequence findSysSequenceByCustomerIdRunningType(Integer customerId, String runningType)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("select o from SysSequence o ");
    sb.append("where 1=1 ");

    if (null != customerId) {
      sb.append("and o.customerId.customerId =:customerId ");
    }
    if (null != runningType) {
      sb.append("and o.runningType = :runningType ");
    }

    Query q = this.em.createQuery(sb.toString());
    if (null != customerId) {
      q.setParameter("customerId", customerId);
    }
    if (null != runningType) {
      q.setParameter("runningType", runningType);
    }
    try
    {
      return (SysSequence)q.getSingleResult(); } catch (NoResultException e) {
    }
    return null;
  }

  public List<SysCustomer> findCustNotSequenceList()
  {
    Query q = this.em.createQuery("select o from SysCustomer o where o.customerId not in(select c.customerId.customerId from SysSequence c)");

    return q.getResultList();
  }

  public Double findSysSequenceBillingNewByYearMonth(String yearMonth) {
    StringBuilder sb = new StringBuilder();
    sb.append("select coalesce(max(SUBSTRING(documentno ,5 ))+1,1) from sys_billing where ");
    sb.append(" (billing_type= '").append("GOODRECEIPT").append("'");
    sb.append(" or billing_type = '").append("SALESINVOICE").append("')");
    sb.append(" and SUBSTRING(documentno,1, 4)= '").append(yearMonth).append("'");

    Query q = this.em.createNativeQuery(sb.toString());
    try {
      Double result = (Double)q.getSingleResult();
      return Double.valueOf(result == null ? 0.0D : result.doubleValue()); } catch (NoResultException noe) {
    }
    return Double.valueOf(0.0D);
  }

  public Double findSysSequenceQuotationByYearMonth(String yearMonth)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("select coalesce(max(SUBSTRING(documentno ,5 ))+1,1) from sys_main_quotation where ");
    sb.append(" SUBSTRING(documentno,1, 4)= '").append(yearMonth).append("'");

    Query q = this.em.createNativeQuery(sb.toString());
    try {
      Double result = (Double)q.getSingleResult();
      return Double.valueOf(result == null ? 0.0D : result.doubleValue()); } catch (NoResultException noe) {
    }
    return Double.valueOf(0.0D);
  }

  public Double findSysSequenceQuotationChildByYearMonth(String documentno) {
    StringBuilder sb = new StringBuilder();
    sb.append("select coalesce(max(SUBSTRING(child_documentno ,9 ))+1,1) from sys_main_quotation where ");
    sb.append(" documentno= '").append(documentno).append("'");

    Query q = this.em.createNativeQuery(sb.toString());
    try {
      Double result = (Double)q.getSingleResult();
      return Double.valueOf(result == null ? 0.0D : result.doubleValue()); } catch (NoResultException noe) {
    }
    return Double.valueOf(0.0D);
  }
}