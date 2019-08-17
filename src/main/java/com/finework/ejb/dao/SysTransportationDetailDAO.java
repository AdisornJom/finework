package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysTransportationDetail;
import com.finework.core.ejb.entity.SysWorkunit;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysTransportationDetailDAO extends AbstractDAO<SysTransportationDetail>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysTransportationDetailDAO() {
    super(SysTransportationDetail.class);
  }

  public void deleteTransportationTpIdOnDetail(Integer transportId) throws Exception {
    Query q = this.em.createQuery("delete from SysTransportationDetail o where o.transportId.transportId = :id");
    q.setParameter("id", transportId);
    q.executeUpdate();
  }

  public List<SysTransportationDetail> findSysTransportationDetailByTransportID(Integer transportId) throws Exception {
    Query q = this.em.createQuery("select o from SysTransportationDetail o where o.transportId.transportId = :id");
    q.setParameter("id", transportId);
    return q.getResultList();
  }

  public List<SysTransportationDetail> findSysTransportationDetailByCriteria(SysWorkunit sysWorkunit, Date startDate, Date toDate)
    throws Exception
  {
    return null;
  }
}