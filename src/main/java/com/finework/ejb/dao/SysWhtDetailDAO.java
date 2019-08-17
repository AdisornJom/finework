package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysWhtDetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysWhtDetailDAO extends AbstractDAO<SysWhtDetail>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysWhtDetailDAO() {
    super(SysWhtDetail.class);
  }

  public void deleteWhtIdOnDetail(Integer whtId) throws Exception {
    Query q = this.em.createQuery("delete from SysWhtDetail o where o.whtId.whtId = :id");
    q.setParameter("id", whtId);
    q.executeUpdate();
  }
}