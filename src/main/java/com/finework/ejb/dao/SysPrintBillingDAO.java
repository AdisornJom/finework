package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysPrintBilling;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SysPrintBillingDAO extends AbstractDAO<SysPrintBilling>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysPrintBillingDAO() {
    super(SysPrintBilling.class);
  }
}