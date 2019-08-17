package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysManufactory;
import com.finework.core.ejb.entity.SysWorkunit;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysManufactoryDAO extends AbstractDAO<SysManufactory>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysManufactoryDAO() {
    super(SysManufactory.class);
  }

  public SysManufactory findSysManufactoryById(Integer factoryId) {
    Query q = this.em.createQuery("select o from SysManufactory o where o.factoryId =:factoryId order by  factoryId desc");
    q.setParameter("factoryId", factoryId);

    return (SysManufactory)q.getSingleResult();
  }

  public List<SysManufactory> findSysManufactoryList() {
    Query q = this.em.createQuery("select o from SysManufactory o  order by o.factoryId DESC");
    return q.getResultList();
  }

  public List<SysManufactory> findSysManufactoryList(String nickname) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysManufactory m ");
    sb.append("where 1=1 ");

    if ((null != nickname) && (nickname.length() > 0)) {
      sb.append("and m.contractorId.contractorNickname like :contractor ");
    }
    sb.append("GROUP BY m.contractorId.contractorNickname ");
    sb.append("order by m.contractorId.contractorNickname asc ");
    Query q = this.em.createQuery(sb.toString());
    if ((null != nickname) && (nickname.length() > 0)) {
      q.setParameter("contractor", new StringBuilder().append("%").append(nickname).append("%").toString());
    }

    return q.getResultList();
  }

  public List<SysManufactory> findSysManufactoryList(String documentno, SysContractor contractor) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysManufactory m ");
    sb.append("where 1=1 ");

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentno like :documentno ");
    }
    if (null != contractor) {
      sb.append("and m.contractorId.contractorId = :contractor ");
    }
    sb.append("ORDER BY m.factoryId DESC ");

    Query q = this.em.createQuery(sb.toString());
    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }
    if (null != contractor) {
      q.setParameter("contractor", contractor.getContractorId());
    }
    return q.getResultList();
  }

  public List<SysManufactory> findSysManufactoryRealOutstandingListByCriteria(String documentno, SysWorkunit sysWorkunit, SysContractor contractor, Date startDate, Date toDate, int paymentType) throws Exception
  {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysManufactory m  ");
    sb.append("where 1=1 ");

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentno like :documentno ");
    }

    if (null != sysWorkunit) {
      sb.append("and m.workunitId.workunitId = :workunitId ");
    }

    if (null != contractor) {
      sb.append("and m.contractorId.contractorId = :contractor ");
    }
    if (null != startDate) {
      sb.append("and m.factoryDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.factoryDate <= :toDate ");
    }

    sb.append("ORDER BY m.factoryId DESC ");

    Query q = this.em.createQuery(sb.toString());
    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }
    if (null != sysWorkunit) {
      q.setParameter("workunitId", sysWorkunit.getWorkunitId());
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

  public List<SysManufactory> findSysManufactoryListByCriteria(String documentno, SysWorkunit sysWorkunit, SysContractor contractor, SysForeman foreman, Date startDate, Date toDate) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysManufactory m ");
    sb.append("where 1=1 ");

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentno like :documentno ");
    }

    if (null != sysWorkunit) {
      sb.append("and m.sysManufactoryDetailList.workunitId.workunitId = :workunitId ");
    }

    if (null != foreman) {
      sb.append("and m.foremanId.foremanId = :foreman ");
    }

    if (null != contractor) {
      sb.append("and m.contractorId.contractorId = :contractor ");
    }
    if (null != startDate) {
      sb.append("and m.factoryDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.factoryDate <= :toDate ");
    }
    sb.append("ORDER BY m.factoryId DESC ");

    Query q = this.em.createQuery(sb.toString());
    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }
    if (null != sysWorkunit) {
      q.setParameter("workunitId", sysWorkunit.getWorkunitId());
    }
    if (null != contractor) {
      q.setParameter("contractor", contractor.getContractorId());
    }
    if (null != foreman) {
      q.setParameter("foreman", foreman.getForemanId());
    }
    if (null != startDate) {
      q.setParameter("startDate", startDate);
    }
    if (null != toDate) {
      q.setParameter("toDate", toDate);
    }
    return q.getResultList();
  }

  public List<SysManufactory> findSysManufactoryListByForemanCriteria(String documentno, SysForeman foreman, Date startDate, Date toDate) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysManufactory m ");
    sb.append("where 1=1 ");

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentno like :documentno ");
    }

    if (null != foreman) {
      sb.append("and m.foremanId.foremanId = :foreman ");
    }
    if (null != startDate) {
      sb.append("and m.factoryDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.factoryDate <= :toDate ");
    }
    sb.append("ORDER BY m.factoryId DESC ");

    Query q = this.em.createQuery(sb.toString());
    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }
    if (null != foreman) {
      q.setParameter("foreman", foreman.getForemanId());
    }
    if (null != startDate) {
      q.setParameter("startDate", startDate);
    }
    if (null != toDate) {
      q.setParameter("toDate", toDate);
    }
    return q.getResultList();
  }

  public List<SysManufactory> findSysManufactoryListByCriteria(String documentno, SysWorkunit sysWorkunit, SysContractor contractor, Date startDate, Date toDate, int[] range) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysManufactory m ");
    sb.append("where 1=1 ");

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentno like :documentno ");
    }

    if (null != sysWorkunit) {
      sb.append("and m.workunitId.workunitId = :workunitId ");
    }

    if (null != contractor) {
      sb.append("and m.contractorId.contractorId like :contractor ");
    }
    if (null != startDate) {
      sb.append("and m.factoryDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.factoryDate <= :toDate ");
    }
    sb.append("ORDER BY m.factoryId DESC ");

    Query q = this.em.createQuery(sb.toString());
    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }
    if (null != sysWorkunit) {
      q.setParameter("workunitId", sysWorkunit.getWorkunitId());
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

  public int countSysManufactoryListByCriteria(String documentno, SysWorkunit sysWorkunit, SysContractor contractor, Date startDate, Date toDate) throws Exception
  {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT count(m) FROM SysManufactory m ");
    sb.append("where 1=1 ");

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentno like :documentno ");
    }

    if (null != sysWorkunit) {
      sb.append("and m.workunitId.workunitId = :workunitId ");
    }
    if (null != contractor) {
      sb.append("and m.contractorId.contractorId like :contractor ");
    }
    if (null != startDate) {
      sb.append("and m.factoryDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.factoryDate <= :toDate ");
    }

    Query q = this.em.createQuery(sb.toString());
    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }
    if (null != sysWorkunit) {
      q.setParameter("workunitId", sysWorkunit.getWorkunitId());
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
}