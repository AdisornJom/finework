package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysWorkunit;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysWorkUnitDAO extends AbstractDAO<SysWorkunit>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysWorkUnitDAO() {
    super(SysWorkunit.class);
  }

  public List<SysWorkunit> fineworkUnitList()
  {
    Query q = this.em.createQuery("select o from SysWorkunit o where o.status ='Y'");
    return q.getResultList();
  }

  public List<SysWorkunit> fineworkUnitListByCriteria(String workUnitName, String status) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT o FROM SysWorkunit o ");
    sb.append("where 1=1 ");
    if ((null != workUnitName) && (workUnitName.length() > 0)) {
      sb.append("and o.workunitName like :workUnitName ");
    }
    if ((null != status) && (status.length() > 0)) {
      sb.append("and o.status =:status  ");
    }

    Query q = this.em.createQuery(sb.toString());
    if ((null != workUnitName) && (workUnitName.length() > 0)) {
      q.setParameter("workUnitName", new StringBuilder().append("%").append(workUnitName).append("%").toString());
    }
    if ((null != status) && (status.length() > 0)) {
      q.setParameter("status", status);
    }
    return q.getResultList();
  }

  public SysWorkunit findSysWorkunitById(Integer customerId) {
    Query q = this.em.createQuery("select o from SysWorkunit o where o.workunitId =:workunitId order by workunitName asc ");
    q.setParameter("workunitId", customerId);

    return (SysWorkunit)q.getSingleResult();
  }
}