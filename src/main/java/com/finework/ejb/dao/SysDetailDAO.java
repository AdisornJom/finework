package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysDetail;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysDetailDAO extends AbstractDAO<SysDetail>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysDetailDAO() {
    super(SysDetail.class);
  }

  public List<SysDetail> findSySDetailList()
  {
    Query q = this.em.createQuery("select o from SysDetail o where o.status ='Y' order by  detailDesc asc");
    return q.getResultList();
  }

  public SysDetail findSysDetailById(Integer detailId) {
    Query q = this.em.createQuery("select o from SysDetail o where o.detailId =:detailId order by  detailDesc asc");
    q.setParameter("detailId", detailId);

    return (SysDetail)q.getSingleResult();
  }

  public List<SysDetail> findSySDetailListByCriteria(String itemname, String status) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT o FROM SysDetail o ");
    sb.append("where 1=1");
    if ((null != itemname) && (itemname.length() > 0)) {
      sb.append("and o.detailDesc like :itemname ");
    }
    if ((null != status) && (status.length() > 0)) {
      sb.append("and o.status =:status  ");
    }

    Query q = this.em.createQuery(sb.toString());
    if ((null != itemname) && (itemname.length() > 0)) {
      q.setParameter("itemname", new StringBuilder().append("%").append(itemname).append("%").toString());
    }
    if ((null != status) && (status.length() > 0)) {
      q.setParameter("status", status);
    }
    sb.append(" order by detailDesc asc");
    return q.getResultList();
  }

  public List<SysDetail> findSySDetailListByHousePlan(Integer housePlanId, String status) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT o FROM SysDetail o ");
    sb.append("where 1=1");
    if (null != housePlanId) {
      sb.append("and o.housePlanId.detailId = :housePlanId ");
    }
    if ((null != status) && (status.length() > 0)) {
      sb.append("and o.status =:status  ");
    }

    Query q = this.em.createQuery(sb.toString());
    if (null != housePlanId) {
      q.setParameter("housePlanId", housePlanId);
    }
    if ((null != status) && (status.length() > 0)) {
      q.setParameter("status", status);
    }
    sb.append(" order by detailDesc asc");
    return q.getResultList();
  }
}