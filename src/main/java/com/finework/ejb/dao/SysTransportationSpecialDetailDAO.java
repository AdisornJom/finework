package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysTransportationSpecial;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysTransportationSpecialDetailDAO extends AbstractDAO<SysTransportationSpecial>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysTransportationSpecialDetailDAO() {
    super(SysTransportationSpecial.class);
  }

  public void deleteTransportationSpecialTpIdOnDetail(Integer transportId) throws Exception {
    Query q = this.em.createQuery("delete from SysTransportationSpecial o where o.transportId.transportId = :id");
    q.setParameter("id", transportId);
    q.executeUpdate();
  }

  public List<SysTransportationSpecial> findSysTransportationSpecialByTransportID(Integer transportId) throws Exception {
    Query q = this.em.createQuery("select o from SysTransportationSpecial o where o.transportId.transportId = :id");
    q.setParameter("id", transportId);
    return q.getResultList();
  }
}