package com.finework.ejb.dao;

import com.finework.core.ejb.entity.ReportR105TO;
import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysPaymentManufactory;
import com.finework.core.util.DateTimeUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysPaymentManufactoryDAO extends AbstractDAO<SysPaymentManufactory>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysPaymentManufactoryDAO() {
    super(SysPaymentManufactory.class);
  }

  public SysPaymentManufactory findSysPaymentManufactoryById(Integer paymentFactoryId) {
    Query q = this.em.createQuery("select o from SysPaymentManufactory o where o.paymentFactoryId =:paymentFactoryId");
    q.setParameter("paymentFactoryId", paymentFactoryId);

    return (SysPaymentManufactory)q.getSingleResult();
  }

  public List<SysPaymentManufactory> findSysPaymentManufactoryList() {
    Query q = this.em.createQuery("select o from SysPaymentManufactory o  order by o.paymentFactoryId DESC");
    return q.getResultList();
  }

  public List<SysPaymentManufactory> findSysPaymentManufactoryList(String documentno, SysContractor contractor) {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysPaymentManufactory m ");
    sb.append("where 1=1 ");

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentNo like :documentno ");
    }
    if (null != contractor) {
      sb.append("and m.contractorId.contractorId = :contractor ");
    }
    sb.append("ORDER BY m.paymentFactoryId DESC ");

    Query q = this.em.createQuery(sb.toString());
    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }
    if (null != contractor) {
      q.setParameter("contractor", contractor.getContractorId());
    }
    return q.getResultList();
  }

  public List<SysPaymentManufactory> findSysPaymentManufactoryR106List(Date startDate, Date toDate, int[] range) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysPaymentManufactory m ");
    sb.append("where 1=1 ");
    if (null != startDate) {
      sb.append("and m.paymentStartDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.paymentEndDate <= :toDate ");
    }

    sb.append("ORDER BY m.paymentFactoryId DESC ");

    Query q = this.em.createQuery(sb.toString());
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

  public int countSysPaymentManufactoryR106List(Date startDate, Date toDate) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT count(m) FROM SysPaymentManufactory m ");
    sb.append("where 1=1 ");

    if (null != startDate) {
      sb.append("and m.paymentStartDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.paymentEndDate <= :toDate ");
    }

    Query q = this.em.createQuery(sb.toString());
    if (null != startDate) {
      q.setParameter("startDate", startDate);
    }
    if (null != toDate) {
      q.setParameter("toDate", toDate);
    }

    return ((Number)q.getSingleResult()).intValue();
  }

  public BigDecimal sumSysPaymentManufactoryR106List(Date startDate, Date toDate) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT sum(m.facNet) as total FROM SysPaymentManufactory m ");
    sb.append("where 1=1 ");

    if (null != startDate) {
      sb.append("and m.paymentStartDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.paymentEndDate <= :toDate ");
    }

    Query q = this.em.createQuery(sb.toString());
    if (null != startDate) {
      q.setParameter("startDate", startDate);
    }
    if (null != toDate) {
      q.setParameter("toDate", toDate);
    }
    try
    {
      Double result = (Double)q.getSingleResult();
      return result == null ? BigDecimal.ZERO : new BigDecimal(result.doubleValue()); } catch (NoResultException noe) {
    }
    return BigDecimal.ZERO;
  }

  public List<SysPaymentManufactory> findSysPaymentManufactoryOverDrawListByCriteria(String documentno, SysContractor contractor, Date startDate, Date toDate) throws Exception
  {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysPaymentManufactory m ");
    sb.append("where 1=1 ");

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentNo like :documentno ");
    }

    if (null != contractor) {
      sb.append("and m.contractorId.contractorId = :contractor ");
    }
    if (null != startDate) {
      sb.append("and m.paymentStartDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.paymentEndDate <= :toDate ");
    }
    sb.append("and m.facNet < 0.0 ");

    sb.append("ORDER BY m.paymentFactoryId DESC ");

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
    return q.getResultList();
  }

  public List<SysPaymentManufactory> findSysPaymentManufactoryListByCriteria(String documentno, SysContractor contractor, Date startDate, Date toDate) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysPaymentManufactory m ");
    sb.append("where 1=1 ");

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentNo like :documentno ");
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
    sb.append("ORDER BY m.paymentFactoryId DESC ");

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
    return q.getResultList();
  }

  public List<SysPaymentManufactory> findSysPaymentManufactoryListByCriteria(String documentno, SysContractor contractor, Date startDate, Date toDate, int[] range) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysPaymentManufactory m ");
    sb.append("where 1=1 ");

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentNo like :documentno ");
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
    sb.append("ORDER BY m.paymentFactoryId DESC ");

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

    q.setMaxResults(range[1] - range[0]);
    q.setFirstResult(range[0]);
    return q.getResultList();
  }

  public int countSysPaymentManufactoryListByCriteria(String documentno, SysContractor contractor, Date startDate, Date toDate) throws Exception
  {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT count(m) FROM SysPaymentManufactory m ");
    sb.append("where 1=1 ");

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentNo like :documentno ");
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

  public List<ReportR105TO> findReportR105List(Integer contractorId, Date dateFrom, Date dateTo) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("select b.contractor_id as contractor_id,b.contractor_name_th as name,b.contractor_nickname as nickname,sum(a.fac_net) as factory_net ");
    sb.append("from sys_payment_manufactory a,sys_contractor b  ");
    sb.append("where a.contractor_id=b.contractor_id ");
    if (contractorId != null) {
      sb.append("and b.contractor_id =").append(contractorId).append(" ");
    }
    if (dateFrom != null) {
      sb.append(" and a.payment_startdate >= '").append(DateTimeUtil.dateToString(dateFrom, "yyyy-MM-dd HH:mm:ss")).append("'");
    }
    if (dateTo != null) {
      sb.append(" and a.payment_enddate <= '").append(DateTimeUtil.dateToString(dateTo, "yyyy-MM-dd HH:mm:ss")).append("'");
    }
    sb.append(" group by b.contractor_name_th,b.contractor_nickname");
    Query q = this.em.createNativeQuery(sb.toString(), ReportR105TO.class);
    return q.getResultList();
  }

  public List<ReportR105TO> findReportR105List(Integer contractorId, Date dateFrom, Date dateTo, int[] range) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("select b.contractor_id as contractor_id,b.contractor_name_th as name,b.contractor_nickname as nickname,sum(a.fac_net) as factory_net ");
    sb.append("from sys_payment_manufactory a,sys_contractor b  ");
    sb.append("where a.contractor_id=b.contractor_id ");
    if (contractorId != null) {
      sb.append("and b.contractor_id =").append(contractorId).append(" ");
    }
    if (dateFrom != null) {
      sb.append(" and a.payment_startdate >= '").append(DateTimeUtil.dateToString(dateFrom, "dd-MM-yyyy")).append("'");
    }
    if (dateTo != null) {
      sb.append(" and a.payment_enddate <= '").append(DateTimeUtil.dateToString(dateTo, "dd-MM-yyyy")).append("'");
    }
    sb.append(" group by b.contractor_name_th,b.contractor_nickname");

    Query q = this.em.createNativeQuery(sb.toString(), ReportR105TO.class);
    q.setMaxResults(range[1] - range[0]);
    q.setFirstResult(range[0]);
    return q.getResultList();
  }

  public BigInteger count_reportR105List(Integer contractorId, Date dateFrom, Date dateTo) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("select count(*) from ( ");
    sb.append("select b.contractor_name_th from sys_payment_manufactory a,sys_contractor b  where a.contractor_id=b.contractor_id");
    if (contractorId != null) {
      sb.append("and b.contractor_id =").append(contractorId).append(" ");
    }
    if (dateFrom != null) {
      sb.append(" and a.payment_startdate >= '").append(DateTimeUtil.dateToString(dateFrom, "dd-MM-yyyy")).append("'");
    }
    if (dateTo != null) {
      sb.append(" and a.payment_enddate <= '").append(DateTimeUtil.dateToString(dateTo, "dd-MM-yyyy")).append("'");
    }
    sb.append(" group by b.contractor_name_th)as x");
    Query q = this.em.createNativeQuery(sb.toString());
    try {
      BigInteger result = (BigInteger)q.getSingleResult();
      return result == null ? BigInteger.ZERO : result; } catch (NoResultException noe) {
    }
    return BigInteger.ZERO;
  }
}