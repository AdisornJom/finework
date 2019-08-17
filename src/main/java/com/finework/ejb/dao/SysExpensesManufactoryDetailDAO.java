package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysExpensesManufactoryDetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysExpensesManufactoryDetailDAO extends AbstractDAO<SysExpensesManufactoryDetail>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysExpensesManufactoryDetailDAO() {
    super(SysExpensesManufactoryDetail.class);
  }

  public void deleteExpensesManufactoryIdOnDetail(Integer expensesId) throws Exception {
    Query q = this.em.createQuery("delete from SysExpensesManufactoryDetail o where o.expensesId.expensesId = :id");
    q.setParameter("id", expensesId);
    q.executeUpdate();
  }
}