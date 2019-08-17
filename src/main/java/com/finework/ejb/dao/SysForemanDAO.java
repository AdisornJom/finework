package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysForeman;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class SysForemanDAO extends AbstractDAO<SysForeman>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysForemanDAO() {
    super(SysForeman.class);
  }

  public List<SysForeman> findSysForemanList()
  {
    Query q = this.em.createQuery("select o from SysForeman o where o.status ='Y'");
    return q.getResultList();
  }

  public List<SysForeman> findSysForemanByCriteria(String foremanNameTh, String status) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT o FROM SysForeman o ");
    sb.append("where 1=1 ");
    if ((null != foremanNameTh) && (foremanNameTh.length() > 0)) {
      sb.append("and o.foremanNameTh like :foremanNameTh ");
    }
    if ((null != status) && (status.length() > 0)) {
      sb.append("and o.status =:status  ");
    }

    Query q = this.em.createQuery(sb.toString());
    if ((null != foremanNameTh) && (foremanNameTh.length() > 0)) {
      q.setParameter("foremanNameTh", new StringBuilder().append("%").append(foremanNameTh).append("%").toString());
    }
    if ((null != status) && (status.length() > 0)) {
      q.setParameter("status", status);
    }
    return q.getResultList();
  }

  public SysForeman findSysForemanById(Integer foremanId) {
    Query q = this.em.createQuery("select o from SysForeman o where o.foremanId =:foremanId  order by  foremanNameTh asc");
    q.setParameter("foremanId", foremanId);

    return (SysForeman)q.getSingleResult();
  }

  public SysForeman isExistUser(String username) {
    Query q = this.em.createQuery("select o from SysForeman o where lower(o.username) =?1 ");
    q.setParameter(1, StringUtils.lowerCase(username));
    try {
      SysForeman u = (SysForeman)q.getSingleResult();
      this.em.refresh(u);
      return u; } catch (NoResultException e) {
    }
    return null;
  }
}