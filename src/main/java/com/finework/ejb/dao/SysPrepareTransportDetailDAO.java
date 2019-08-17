package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysPrepareTransportDetail;
import com.finework.core.ejb.entity.SysWorkunit;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysPrepareTransportDetailDAO extends AbstractDAO<SysPrepareTransportDetail>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysPrepareTransportDetailDAO() {
    super(SysPrepareTransportDetail.class);
  }

  public void deletePrepareTpIdOnDetail(Integer prepareTpId) throws Exception {
    Query q = this.em.createQuery("delete from SysPrepareTransportDetail o where o.prepareTpId.prepareTpId = :id");
    q.setParameter("id", prepareTpId);
    q.executeUpdate();
  }

  public List<SysPrepareTransportDetail> findSysPrepareTransportDetailByPrepareID(Integer prepareTpId) throws Exception {
    Query q = this.em.createQuery("select o from SysPrepareTransportDetail o where o.prepareTpId.prepareTpId = :id");
    q.setParameter("id", prepareTpId);
    return q.getResultList();
  }

  public List<SysPrepareTransportDetail> findSysPrepareTransportDetailByCriteria(SysWorkunit sysWorkunit, Date startDate, Date toDate)
    throws Exception
  {
    return null;
  }
}