package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysPayment;
import com.finework.core.ejb.entity.SysWorkunit;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysPaymentDAO extends AbstractDAO<SysPayment>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysPaymentDAO() {
    super(SysPayment.class);
  }

  public List<SysPayment> findSysPaymentList() {
    Query q = this.em.createQuery("select o from SysPayment o  order by o.paymentDate");
    return q.getResultList();
  }

  public List<SysPayment> findSysPaymentListByCriteria(String paymentType, String documentno, SysWorkunit workunitId, Date startDate, Date toDate) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysPayment m ");
    sb.append("where 1=1 ");

    if ((null != paymentType) && (paymentType.length() > 0)) {
      sb.append("and m.paymentType = :paymentType ");
    }

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentno like :documentno ");
    }

    if (null != workunitId) {
      sb.append("and m.workunitId.workunitId = :workunitId ");
    }

    if (null != startDate) {
      sb.append("and m.paymentDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.paymentDate <= :toDate ");
    }
    sb.append("ORDER BY m.paymentId DESC ");

    Query q = this.em.createQuery(sb.toString());
    if ((null != paymentType) && (paymentType.length() > 0)) {
      q.setParameter("paymentType", paymentType);
    }
    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }

    if (null != workunitId) {
      q.setParameter("workunitId", workunitId.getWorkunitId());
    }

    if (null != startDate) {
      q.setParameter("startDate", startDate);
    }
    if (null != toDate) {
      q.setParameter("toDate", toDate);
    }
    return q.getResultList();
  }
}