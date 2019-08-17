package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysManufactoryDetail;
import com.finework.core.ejb.entity.SysWorkunit;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysManufactoryDetailDAO extends AbstractDAO<SysManufactoryDetail>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysManufactoryDetailDAO() {
    super(SysManufactoryDetail.class);
  }

  public void deleteManufactoryIdOnDetail(Integer factoryId) throws Exception {
    Query q = this.em.createQuery("delete from SysManufactoryDetail o where o.factoryId.factoryId = :id");
    q.setParameter("id", factoryId);
    q.executeUpdate();
  }

  public List<SysManufactoryDetail> findSysManufactoryDetailByCriteria(SysWorkunit sysWorkunit, Date startDate, Date toDate) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysManufactoryDetail m ");
    sb.append("where 1=1 ");

    if (null != sysWorkunit) {
      sb.append("and m.workunitId.workunitId = :workunitId ");
    }
    if (null != startDate) {
      sb.append("and m.factoryId.factoryDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.factoryId.factoryDate <= :toDate ");
    }
    sb.append("ORDER BY m.factoryId.factoryId DESC ");

    Query q = this.em.createQuery(sb.toString());
    if (null != sysWorkunit) {
      q.setParameter("workunitId", sysWorkunit.getWorkunitId());
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