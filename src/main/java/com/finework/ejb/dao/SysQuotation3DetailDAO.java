package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysMainQuotation3;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysQuotation3DetailDAO extends AbstractDAO<SysMainQuotation3>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysQuotation3DetailDAO() {
    super(SysMainQuotation3.class);
  }

  public List<SysMainQuotation3> findSysQuotationDetail3ListByCriteria(Integer quotationId) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysMainQuotation3 m ");
    sb.append("where 1=1 ");

    if (null != quotationId) {
      sb.append("and m.quotationId.quotationId =:quotationId ");
    }
    sb.append("ORDER BY m.seq asc ");

    Query q = this.em.createQuery(sb.toString());
    if (null != quotationId) {
      q.setParameter("quotationId", quotationId);
    }
    return q.getResultList();
  }

  public void deleteQuotationIdOnDetail(Integer quotationId) throws Exception
  {
    Query q = this.em.createQuery("delete from SysMainQuotation3 o where o.quotationId.quotationId = :id");
    q.setParameter("id", quotationId);
    q.executeUpdate();
  }
}