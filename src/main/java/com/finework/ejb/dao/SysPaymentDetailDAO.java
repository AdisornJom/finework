package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysPaymentDetail;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysPaymentDetailDAO extends AbstractDAO<SysPaymentDetail>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysPaymentDetailDAO() {
    super(SysPaymentDetail.class);
  }

  public List<SysPaymentDetail> findSysPaymentDetailList(Integer paymentId) throws Exception {
    Query q = this.em.createQuery("select o from SysPaymentDetail o  where o.paymentId.paymentId = :id");
    q.setParameter("id", paymentId);
    return q.getResultList();
  }

  public void deletePaymentIdOnDetail(Integer paymentId) throws Exception {
    Query q = this.em.createQuery("delete from SysPaymentDetail o where o.paymentId.paymentId = :id");
    q.setParameter("id", paymentId);
    q.executeUpdate();
  }
}