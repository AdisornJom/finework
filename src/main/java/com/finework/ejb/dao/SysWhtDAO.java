package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysWht;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysWhtDAO extends AbstractDAO<SysWht>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysWhtDAO() {
    super(SysWht.class);
  }

  public List<SysWht> findSysWhtListByCriteria(String documentno, SysCustomer sysCustomer, Date startDate, Date toDate, Integer vatType) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysWht m ");
    sb.append("where 1=1 ");

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentno like :documentno ");
    }

    if (null != sysCustomer) {
      sb.append("and m.customerId.customerId like :customer ");
    }
    if (null != startDate) {
      sb.append("and m.whtDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.whtDate <= :toDate ");
    }
    if (null != vatType) {
      sb.append("and m.vatType = :vatType ");
    }

    sb.append("ORDER BY m.whtDate DESC ");

    Query q = this.em.createQuery(sb.toString());
    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }
    if (null != sysCustomer) {
      q.setParameter("customer", sysCustomer.getCustomerId());
    }
    if (null != startDate) {
      q.setParameter("startDate", startDate);
    }
    if (null != toDate) {
      q.setParameter("toDate", toDate);
    }
    if (null != vatType) {
      q.setParameter("vatType", vatType);
    }
    return q.getResultList();
  }
}