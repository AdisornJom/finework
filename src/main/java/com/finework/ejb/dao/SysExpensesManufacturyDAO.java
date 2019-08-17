package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysExpensesManufactory;
import com.finework.core.ejb.entity.SysExpensesManufactoryDeduction;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysExpensesManufacturyDAO extends AbstractDAO<SysExpensesManufactory>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysExpensesManufacturyDAO() {
    super(SysExpensesManufactory.class);
  }

  public List<SysExpensesManufactory> findSysExpensesManufactoryList()
  {
    Query q = this.em.createQuery("select o from SysExpensesManufactory o  order by  createdDt asc");
    return q.getResultList();
  }

  public SysExpensesManufactory findSysExpensesManufactoryById(Integer expensesId) {
    Query q = this.em.createQuery("select o from SysExpensesManufactory o where o.expensesId =:expensesId ");
    q.setParameter("expensesId", expensesId);

    return (SysExpensesManufactory)q.getSingleResult();
  }

  public List<SysExpensesManufactory> findSysExpensesManufactoryListByCriteria(String documentNo, SysExpensesManufactoryDeduction deduction, SysContractor contractor, Date startDate, Date toDate) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysExpensesManufactory m ");
    sb.append("where 1=1 ");

    if ((null != documentNo) && (documentNo.length() > 0)) {
      sb.append("and m.documentNo like :documentNo ");
    }

    if (null != deduction) {
      sb.append("and m.deductionId.deductionId = :deduction ");
    }

    if (null != contractor) {
      sb.append("and m.contractorId.contractorId = :contractor ");
    }
    if (null != startDate) {
      sb.append("and m.expensesDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.expensesDate <= :toDate ");
    }
    sb.append("ORDER BY m.expensesId DESC ");

    Query q = this.em.createQuery(sb.toString());
    if ((null != documentNo) && (documentNo.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentNo).append("%").toString());
    }
    if (null != deduction) {
      q.setParameter("deduction", deduction.getDeductionId());
    }
    if (null != contractor) {
      q.setParameter("contractor", contractor.getContractorId());
    }
    if (null != startDate) {
      q.setParameter("startDate", startDate);
    }
    if (null != toDate) {
      q.setParameter("toDate", toDate);
    }
    return q.getResultList();
  }

  public List<SysExpensesManufactory> findSysExpensesManufactoryListByCriteria(String documentNo, SysExpensesManufactoryDeduction deduction, SysContractor contractor, Date startDate, Date toDate, int[] range) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysExpensesManufactory m ");
    sb.append("where 1=1 ");

    if ((null != documentNo) && (documentNo.length() > 0)) {
      sb.append("and m.documentNo like :documentNo ");
    }

    if (null != deduction) {
      sb.append("and m.deductionId.deductionId = :deduction ");
    }

    if (null != contractor) {
      sb.append("and m.contractorId.contractorId = :contractor ");
    }
    if (null != startDate) {
      sb.append("and m.expensesDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.expensesDate <= :toDate ");
    }
    sb.append("ORDER BY m.expensesId DESC ");

    Query q = this.em.createQuery(sb.toString());
    if ((null != documentNo) && (documentNo.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentNo).append("%").toString());
    }
    if (null != deduction) {
      q.setParameter("deduction", deduction.getDeductionId());
    }
    if (null != contractor) {
      q.setParameter("contractor", contractor.getContractorId());
    }
    if (null != startDate) {
      q.setParameter("startDate", startDate);
    }
    if (null != toDate) {
      q.setParameter("toDate", toDate);
    }

    q.setMaxResults(range[1] - range[0]);
    q.setFirstResult(range[0]);
    return q.getResultList();
  }

  public int countSysExpensesManufactoryListByCriteria(String documentNo, SysExpensesManufactoryDeduction deduction, SysContractor contractor, Date startDate, Date toDate) throws Exception
  {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT count(m) FROM SysExpensesManufactory m ");
    sb.append("where 1=1 ");

    if ((null != documentNo) && (documentNo.length() > 0)) {
      sb.append("and m.documentNo like :documentNo ");
    }

    if (null != deduction) {
      sb.append("and m.deductionId.deductionId = :deduction ");
    }

    if (null != contractor) {
      sb.append("and m.contractorId.contractorId = :contractor ");
    }
    if (null != startDate) {
      sb.append("and m.expensesDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.expensesDate <= :toDate ");
    }
    sb.append("ORDER BY m.expensesId DESC ");

    Query q = this.em.createQuery(sb.toString());
    if ((null != documentNo) && (documentNo.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentNo).append("%").toString());
    }
    if (null != deduction) {
      q.setParameter("deduction", deduction.getDeductionId());
    }
    if (null != contractor) {
      q.setParameter("contractor", contractor.getContractorId());
    }
    if (null != startDate) {
      q.setParameter("startDate", startDate);
    }
    if (null != toDate) {
      q.setParameter("toDate", toDate);
    }

    return ((Number)q.getSingleResult()).intValue();
  }

  public int findSysExpensesManufactoryListByFactoryId(Integer factoryId) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT count(m) FROM SysExpensesManufactory m ");
    sb.append("where 1=1 ");

    if (null != factoryId) {
      sb.append("and m.factoryId.factoryId = :factoryId ");
    }

    Query q = this.em.createQuery(sb.toString());
    if (null != factoryId) {
      q.setParameter("factoryId", factoryId);
    }
    return ((Number)q.getSingleResult()).intValue();
  }

  public List<SysExpensesManufactory> findSysManufactoryExpensesListByCriteria(String documentno, SysContractor contractor, Date startDate, Date toDate, int status) throws Exception
  {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysExpensesManufactory m ");
    sb.append("where 1=1 ");

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.factoryId.documentno like :documentno ");
    }

    if (null != contractor) {
      sb.append("and m.contractorId.contractorId = :contractor ");
    }
    if (null != startDate) {
      sb.append("and m.expensesDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.expensesDate <= :toDate ");
    }

    if (0 != status) {
      sb.append("and m.expensesStatus = :status ");
    }

    sb.append("ORDER BY m.expensesId DESC ");

    Query q = this.em.createQuery(sb.toString());
    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }
    if (null != contractor) {
      q.setParameter("contractor", contractor.getContractorId());
    }
    if (null != startDate) {
      q.setParameter("startDate", startDate);
    }
    if (null != toDate) {
      q.setParameter("toDate", toDate);
    }
    if (0 != status) {
      q.setParameter("status", Integer.valueOf(status));
    }
    return q.getResultList();
  }

  public void updateStatusSysManufactoryExpensesByexpensesId(int expensesStatus, int expensesId) {
    Query q = this.em.createQuery("update SysExpensesManufactory o set o.expensesStatus=:expensesStatus where o.expensesId=:expensesId");
    q.setParameter("expensesStatus", Integer.valueOf(expensesStatus));
    q.setParameter("expensesId", Integer.valueOf(expensesId));
    q.executeUpdate();
  }
}