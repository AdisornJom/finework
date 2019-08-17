package com.finework.ejb.dao;

import com.finework.core.ejb.entity.AdminUserRole;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SysUserRoleDAO extends AbstractDAO<AdminUserRole>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysUserRoleDAO() {
    super(AdminUserRole.class);
  }
}