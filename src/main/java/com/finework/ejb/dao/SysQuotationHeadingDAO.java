package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysGeneralQuotationHeading;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SysQuotationHeadingDAO extends AbstractDAO<SysGeneralQuotationHeading>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysQuotationHeadingDAO() {
    super(SysGeneralQuotationHeading.class);
  }
}