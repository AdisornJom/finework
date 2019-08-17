package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysManufacturing;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysManufacturingDAO extends AbstractDAO<SysManufacturing>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysManufacturingDAO() {
    super(SysManufacturing.class);
  }

  public List<SysManufacturing> findSysManufacturingList()
  {
    Query q = this.em.createQuery("select o from SysManufacturing o where o.status ='Y' order by  manufacturingDesc asc");
    return q.getResultList();
  }

  public SysManufacturing findSysManufacturingById(Integer manufacturingId) {
    Query q = this.em.createQuery("select o from SysManufacturing o where o.manufacturingId =:manufacturingId order by  manufacturingDesc asc");
    q.setParameter("manufacturingId", manufacturingId);

    return (SysManufacturing)q.getSingleResult();
  }

  public List<SysManufacturing> findSysManufacturingListByCriteria(String itemname, String status) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT o FROM SysManufacturing o ");
    sb.append("where 1=1");
    if ((null != itemname) && (itemname.length() > 0)) {
      sb.append("and o.manufacturingDesc like :itemname ");
    }
    if ((null != status) && (status.length() > 0)) {
      sb.append("and o.status =:status  ");
    }
    sb.append(" order by manufacturingDesc asc");

    Query q = this.em.createQuery(sb.toString());
    if ((null != itemname) && (itemname.length() > 0)) {
      q.setParameter("itemname", new StringBuilder().append("%").append(itemname).append("%").toString());
    }
    if ((null != status) && (status.length() > 0)) {
      q.setParameter("status", status);
    }

    return q.getResultList();
  }

  public List<SysManufacturing> findSysManufacturingListByCriteria(String itemname, String status, int[] range) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT o FROM SysManufacturing o ");
    sb.append("where 1=1");
    if ((null != itemname) && (itemname.length() > 0)) {
      sb.append("and o.manufacturingDesc like :itemname ");
    }
    if ((null != status) && (status.length() > 0)) {
      sb.append("and o.status =:status  ");
    }
    sb.append(" order by manufacturingDesc asc");

    Query q = this.em.createQuery(sb.toString());
    if ((null != itemname) && (itemname.length() > 0)) {
      q.setParameter("itemname", new StringBuilder().append("%").append(itemname).append("%").toString());
    }
    if ((null != status) && (status.length() > 0)) {
      q.setParameter("status", status);
    }

    q.setMaxResults(range[1] - range[0]);
    q.setFirstResult(range[0]);
    return q.getResultList();
  }

  public int countSysManufacturingListByCriteria(String itemname, String status) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT count(o) FROM SysManufacturing o ");
    sb.append("where 1=1");
    if ((null != itemname) && (itemname.length() > 0)) {
      sb.append("and o.manufacturingDesc like :itemname ");
    }
    if ((null != status) && (status.length() > 0)) {
      sb.append("and o.status =:status  ");
    }
    sb.append(" order by manufacturingDesc asc");

    Query q = this.em.createQuery(sb.toString());
    if ((null != itemname) && (itemname.length() > 0)) {
      q.setParameter("itemname", new StringBuilder().append("%").append(itemname).append("%").toString());
    }
    if ((null != status) && (status.length() > 0)) {
      q.setParameter("status", status);
    }

    return ((Number)q.getSingleResult()).intValue();
  }
}