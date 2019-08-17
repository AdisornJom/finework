package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysMaterialReceipts;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysMaterialReceiptsDAO extends AbstractDAO<SysMaterialReceipts>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysMaterialReceiptsDAO() {
    super(SysMaterialReceipts.class);
  }

  public List<SysMaterialReceipts> findSysMaterialReceiptsList()
  {
    Query q = this.em.createQuery("select o from SysMaterialReceipts o where o.status <>'N' order by  receiptsDate desc");
    return q.getResultList();
  }

  public List<SysMaterialReceipts> findSysMaterialReceiptsListByCriteria(String materialName, String supplierName, String status, Date startDate, Date toDate) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT o FROM SysMaterialReceipts o ");
    sb.append("where o.status <>'N' and o.materialId.status='Y' ");

    if ((null != materialName) && (materialName.length() > 0)) {
      sb.append("and o.materialId.materialDesc like :materialName ");
    }

    if ((null != supplierName) && (supplierName.length() > 0)) {
      sb.append("and o.supplierId.supplierNameTh like :supplierName ");
    }

    if ((null != status) && (status.length() > 0)) {
      sb.append("and o.status =:status  ");
    }

    if (null != startDate) {
      sb.append("and o.receiptsDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and o.receiptsDate <= :toDate ");
    }
    sb.append(" order by o.receiptsDate desc");

    Query q = this.em.createQuery(sb.toString());
    if ((null != materialName) && (materialName.length() > 0)) {
      q.setParameter("materialName", new StringBuilder().append("%").append(materialName).append("%").toString());
    }
    if ((null != supplierName) && (supplierName.length() > 0)) {
      q.setParameter("supplierName", new StringBuilder().append("%").append(supplierName).append("%").toString());
    }
    if ((null != status) && (status.length() > 0)) {
      q.setParameter("status", status);
    }
    if (null != startDate) {
      q.setParameter("startDate", startDate);
    }
    if (null != toDate) {
      q.setParameter("toDate", toDate);
    }

    return q.getResultList();
  }
}