package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysContractor;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysContractorDAO extends AbstractDAO<SysContractor>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysContractorDAO() {
    super(SysContractor.class);
  }

  public List<SysContractor> findSysContractorList()
  {
    Query q = this.em.createQuery("select o from SysContractor o where o.status ='Y'");
    return q.getResultList();
  }

  public List<SysContractor> findSysContractorByCriteria(String customerNameTh, String status) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT o FROM SysContractor o ");
    sb.append("where 1=1 ");
    if ((null != customerNameTh) && (customerNameTh.length() > 0)) {
      sb.append("and o.contractorNameTh like :contractorNameTh ");
    }
    if ((null != status) && (status.length() > 0)) {
      sb.append("and o.status =:status  ");
    }

    Query q = this.em.createQuery(sb.toString());
    if ((null != customerNameTh) && (customerNameTh.length() > 0)) {
      q.setParameter("contractorNameTh", new StringBuilder().append("%").append(customerNameTh).append("%").toString());
    }
    if ((null != status) && (status.length() > 0)) {
      q.setParameter("status", status);
    }
    return q.getResultList();
  }

  public SysContractor findSysContractorById(Integer contractorId) {
    Query q = this.em.createQuery("select o from SysContractor o where o.contractorId =:contractorId  order by  contractorNameTh asc");
    q.setParameter("contractorId", contractorId);

    return (SysContractor)q.getSingleResult();
  }

  public List<SysContractor> findSysContractorByCriteria(SysContractor contractor) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysContractor m ");
    sb.append("where 1=1 ");

    if (null != contractor) {
      sb.append("and m.contractorId = :contractor ");
    }

    Query q = this.em.createQuery(sb.toString());

    if (null != contractor) {
      q.setParameter("contractor", contractor.getContractorId());
    }

    return q.getResultList();
  }
}