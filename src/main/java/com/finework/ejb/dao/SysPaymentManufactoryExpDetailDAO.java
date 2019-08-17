package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysPaymentManufactoryExpdetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysPaymentManufactoryExpDetailDAO extends AbstractDAO<SysPaymentManufactoryExpdetail>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysPaymentManufactoryExpDetailDAO() {
    super(SysPaymentManufactoryExpdetail.class);
  }

  public void deletePaymentManufactoryExpensesIdOnDetail(Integer payment_factory_id) throws Exception {
    Query q = this.em.createQuery("delete from SysPaymentManufactoryExpdetail o where o.paymentFactoryId.paymentFactoryId = :id");
    q.setParameter("id", payment_factory_id);
    q.executeUpdate();
  }
}