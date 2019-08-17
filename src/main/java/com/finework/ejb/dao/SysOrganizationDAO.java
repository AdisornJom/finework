package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysOrganization;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysOrganizationDAO extends AbstractDAO<SysOrganization>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysOrganizationDAO() {
    super(SysOrganization.class);
  }

  public SysOrganization findSysOrganizationByStatus(String status) {
    Query q = this.em.createQuery("select o from SysOrganization o where o.status=:status ");

    q.setParameter("status", status);
    try
    {
      return (SysOrganization)q.getSingleResult(); } catch (NoResultException e) {
    }
    return null;
  }
}