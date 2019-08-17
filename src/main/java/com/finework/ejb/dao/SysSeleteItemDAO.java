package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysSeleteitem;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysSeleteItemDAO extends AbstractDAO<SysSeleteitem>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysSeleteItemDAO() {
    super(SysSeleteitem.class);
  }

  public List<SysSeleteitem> findSysSeleteitemByCriteria(String name)
    throws Exception
  {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT o FROM SysSeleteitem o ");
    sb.append("where 1=1 ");
    if ((null != name) && (name.length() > 0)) {
      sb.append("and o.name = :name ");
    }

    sb.append(" order by typeId asc");

    Query q = this.em.createQuery(sb.toString());
    if ((null != name) && (name.length() > 0)) {
      q.setParameter("name", name);
    }

    return q.getResultList();
  }
}