package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysSuppliers;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysSuppliersDAO extends AbstractDAO<SysSuppliers>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysSuppliersDAO() {
    super(SysSuppliers.class);
  }

  public SysSuppliers findSysSuppliersById(Integer supplierId) {
    Query q = this.em.createQuery("select o from SysSuppliers o where o.supplierId =:supplierId order by  supplierNameTh asc");
    q.setParameter("supplierId", supplierId);

    return (SysSuppliers)q.getSingleResult();
  }

  public List<SysSuppliers> findSysSuppliersList() {
    Query q = this.em.createQuery("select o from SysSuppliers o where o.status ='Y' order by  supplierNameTh asc");
    return q.getResultList();
  }

  public List<SysSuppliers> findSysSuppliersByCriteria(String customerNameTh, String status) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT o FROM SysSuppliers o ");
    sb.append("where 1=1 ");
    if ((null != customerNameTh) && (customerNameTh.length() > 0)) {
      sb.append("and o.supplierNameTh like :supplierNameTh ");
    }
    if ((null != status) && (status.length() > 0)) {
      sb.append("and o.status =:status  ");
    }
    sb.append(" order by supplierNameTh asc");

    Query q = this.em.createQuery(sb.toString());
    if ((null != customerNameTh) && (customerNameTh.length() > 0)) {
      q.setParameter("supplierNameTh", new StringBuilder().append("%").append(customerNameTh).append("%").toString());
    }
    if ((null != status) && (status.length() > 0)) {
      q.setParameter("status", status);
    }

    return q.getResultList();
  }
}