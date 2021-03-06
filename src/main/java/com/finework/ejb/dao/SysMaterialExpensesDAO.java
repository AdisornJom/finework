package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysMaterialExpenses;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysMaterialExpensesDAO extends AbstractDAO<SysMaterialExpenses>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysMaterialExpensesDAO() {
    super(SysMaterialExpenses.class);
  }

  public List<SysMaterialExpenses> findSysMaterialExpensesList()
  {
    Query q = this.em.createQuery("select o from SysMaterialExpenses o where o.status <>'N' order by  expensesDate desc");
    return q.getResultList();
  }

  public List<SysMaterialExpenses> findSysMaterialExpensesListByCriteria(String contractorName, String status, Date startDate, Date toDate) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT o FROM SysMaterialExpenses o ");
    sb.append("where o.status <>'N' ");

    if ((null != contractorName) && (contractorName.length() > 0)) {
      sb.append("and o.contractorId.contractorNameTh like :contractor ");
    }

    if ((null != status) && (status.length() > 0)) {
      sb.append("and o.status =:status  ");
    }

    if (null != startDate) {
      sb.append("and o.expensesDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and o.expensesDate <= :toDate ");
    }
    sb.append(" order by o.expensesDate desc");

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