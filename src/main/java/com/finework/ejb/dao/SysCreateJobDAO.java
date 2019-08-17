package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysCreatejob;
import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysWorkunit;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysCreateJobDAO extends AbstractDAO<SysCreatejob>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysCreateJobDAO() {
    super(SysCreatejob.class);
  }

  public List<SysCreatejob> findSysCreatejobListByCriteria(SysForeman foremanId, String documentno, SysWorkunit workunitId, Integer status, Date startDate, Date toDate, int[] range) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysCreatejob m ");
    sb.append("where 1=1 ");

    if (null != foremanId) {
      sb.append("and m.foremanId.foremanId = :foremanId ");
    }

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentNo like :documentno ");
    }

    if (null != workunitId) {
      sb.append("and m.workunitId.workunitId = :workunitId ");
    }

    if (null != status) {
      sb.append("and m.status = :status ");
    }

    if (null != startDate) {
      sb.append("and m.jobDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.jobDate <= :toDate ");
    }

    sb.append("ORDER BY m.jobId DESC ");

    Query q = this.em.createQuery(sb.toString());
    if (null != foremanId) {
      q.setParameter("foremanId", foremanId.getForemanId());
    }
    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }
    if (null != workunitId) {
      q.setParameter("workunitId", workunitId.getWorkunitId());
    }
    if (null != status) {
      q.setParameter("status", status);
    }
    if (null != startDate) {
      q.setParameter("startDate", startDate);
    }
    if (null != toDate) {
      q.setParameter("toDate", toDate);
    }

    q.setMaxResults(range[1] - range[0]);
    q.setFirstResult(range[0]);
    return q.getResultList();
  }

  public int countSysCreatejobListByCriteria(SysForeman foremanId, String documentno, SysWorkunit workunitId, Integer status, Date startDate, Date toDate) throws Exception
  {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT count(m) FROM SysCreatejob m ");
    sb.append("where 1=1 ");

    if (null != foremanId) {
      sb.append("and m.foremanId.foremanId = :foremanId ");
    }

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentNo like :documentno ");
    }

    if (null != workunitId) {
      sb.append("and m.workunitId.workunitId = :workunitId ");
    }

    if (null != status) {
      sb.append("and m.status = :status ");
    }

    if (null != startDate) {
      sb.append("and m.jobDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.jobDate <= :toDate ");
    }

    Query q = this.em.createQuery(sb.toString());
    if (null != foremanId) {
      q.setParameter("foremanId", foremanId.getForemanId());
    }
    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }
    if (null != workunitId) {
      q.setParameter("workunitId", workunitId.getWorkunitId());
    }
    if (null != status) {
      q.setParameter("status", status);
    }
    if (null != startDate) {
      q.setParameter("startDate", startDate);
    }
    if (null != toDate) {
      q.setParameter("toDate", toDate);
    }

    return ((Number)q.getSingleResult()).intValue();
  }
}