package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysMaterialExpensesDetail;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysMaterialExpensesDetailDAO extends AbstractDAO<SysMaterialExpensesDetail>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysMaterialExpensesDetailDAO() {
    super(SysMaterialExpensesDetail.class);
  }

  public List<SysMaterialExpensesDetail> findSysMaterialExpensesDetailListByCriteria(String contractorName, String status, Date startDate, Date toDate) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT o FROM SysMaterialExpensesDetail o ");
    sb.append("where o.expensesId.status <>'N' ");

    if ((null != contractorName) && (contractorName.length() > 0)) {
      sb.append("and o.expensesId.contractorId.contractorNameTh like :contractor ");
    }

    if ((null != status) && (status.length() > 0)) {
      sb.append("and o.expensesId.status =:status  ");
    }

    if (null != startDate) {
      sb.append("and o.expensesId.expensesDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and o.expensesId.expensesDate <= :toDate ");
    }
    sb.append(" order by o.expensesId.expensesDate desc");

    Query q = this.em.createQuery(sb.toString());
    if ((null != contractorName) && (contractorName.length() > 0)) {
      q.setParameter("contractor", new StringBuilder().append("%").append(contractorName).append("%").toString());
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