package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysBillingDetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysBillingDetailDAO extends AbstractDAO<SysBillingDetail>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysBillingDetailDAO() {
    super(SysBillingDetail.class);
  }

  public void deleteBillingIdOnDetail(Integer billingId) throws Exception {
    Query q = this.em.createQuery("delete from SysBillingDetail o where o.billingId.billingId = :id");
    q.setParameter("id", billingId);
    q.executeUpdate();
  }
}