package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysPaymentManufactoryDetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysPaymentManufactoryDetailDAO extends AbstractDAO<SysPaymentManufactoryDetail>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysPaymentManufactoryDetailDAO() {
    super(SysPaymentManufactoryDetail.class);
  }

  public void deletePaymentManufactoryIdOnDetail(Integer payment_factory_id) throws Exception {
    Query q = this.em.createQuery("delete from SysPaymentManufactoryDetail o where o.paymentFactoryId.paymentFactoryId = :id");
    q.setParameter("id", payment_factory_id);
    q.executeUpdate();
  }
}