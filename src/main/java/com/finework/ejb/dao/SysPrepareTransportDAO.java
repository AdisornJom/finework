package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysPrepareTransport;
import com.finework.core.ejb.entity.SysWorkunit;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysPrepareTransportDAO extends AbstractDAO<SysPrepareTransport>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysPrepareTransportDAO() {
    super(SysPrepareTransport.class);
  }

  public List<SysPrepareTransport> findSysPrepareTransportList()
  {
    Query q = this.em.createQuery("select o from SysPrepareTransport o where o.status ='Y' order by  o.tpserviceDesc asc");
    return q.getResultList();
  }

  public SysPrepareTransport findSysPrepareTransportById(Integer prepareTpId) {
    Query q = this.em.createQuery("select o from SysPrepareTransport o where o.prepareTpId =:prepareTpId  order by o.prepareTpId asc");
    q.setParameter("prepareTpId", prepareTpId);

    return (SysPrepareTransport)q.getSingleResult();
  }

  public void updateStatusSysPrepareTransportByprepareTpId(Integer status, Integer prepareTpId) {
    Query q = this.em.createQuery("update SysPrepareTransport o set o.status=:status where o.prepareTpId=:prepareTpId");
    q.setParameter("status", status);
    q.setParameter("prepareTpId", prepareTpId);
    q.executeUpdate();
  }

  public List<SysPrepareTransport> findSysPrepareTransportListByCriteria(SysForeman foremanId, String documentno, SysWorkunit workunitId, Integer status, Date startDate, Date toDate) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysPrepareTransport m ");
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
      sb.append("and m.prepareTpDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.prepareTpDate <= :toDate ");
    }
    sb.append("ORDER BY m.prepareTpId DESC ");

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

    return q.getResultList();
  }
}