package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysTransportationServiceDetail;
import com.finework.core.ejb.entity.SysWorkunit;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysTransportationServiceDetailDAO extends AbstractDAO<SysTransportationServiceDetail>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysTransportationServiceDetailDAO() {
    super(SysTransportationServiceDetail.class);
  }

  public void deleteTransportationServiceTpIdOnDetail(Integer transportId) throws Exception {
    Query q = this.em.createQuery("delete from SysTransportationServiceDetail o where o.transportId.transportId = :id");
    q.setParameter("id", transportId);
    q.executeUpdate();
  }

  public List<SysTransportationServiceDetail> findSysTransportationServiceDetailByTransportID(Integer transportId) throws Exception {
    Query q = this.em.createQuery("select o from SysTransportationServiceDetail o where o.transportId.transportId = :id");
    q.setParameter("id", transportId);
    return q.getResultList();
  }

  public List<SysTransportationServiceDetail> findSysTransportationServiceDetailByCriteria(SysWorkunit sysWorkunit, Date startDate, Date toDate)
    throws Exception
  {
    return null;
  }
}