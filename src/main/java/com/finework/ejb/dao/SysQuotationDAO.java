package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysMainQuotation;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysQuotationDAO extends AbstractDAO<SysMainQuotation>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysQuotationDAO() {
    super(SysMainQuotation.class);
  }

  public List<SysMainQuotation> findSysMainQuotationListByCriteria(String documentno, String subject, Date startDate, Date toDate, Integer typeform) throws Exception
  {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysMainQuotation m ");
    sb.append("where 1=1 ");

    if (null != typeform) {
      sb.append("and m.typeForm = :typeform ");
    }

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentno like :documentno ");
    }

    if ((null != subject) && (subject.length() > 0)) {
      sb.append("and m.subject like :subject ");
    }
    if (null != startDate) {
      sb.append("and m.quotationDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.quotationDate <= :toDate ");
    }
    sb.append("ORDER BY m.quotationId DESC ");

    Query q = this.em.createQuery(sb.toString());
    if (null != typeform) {
      q.setParameter("typeform", typeform);
    }
    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }
    if ((null != subject) && (subject.length() > 0)) {
      q.setParameter("subject", new StringBuilder().append("%").append(subject).append("%").toString());
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