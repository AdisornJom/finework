package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysBilling;
import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysWorkunit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SysBillingDAO extends AbstractDAO<SysBilling>
{

  @PersistenceContext(unitName="fineworkPU")
  private EntityManager em;

  protected EntityManager getEntityManager()
  {
    return this.em;
  }

  public SysBillingDAO() {
    super(SysBilling.class);
  }

  public List<SysBilling> findSysBillingList(String billingType) {
    Query q = this.em.createQuery(new StringBuilder().append("select o from SysBilling o where o.billingType ='").append(billingType).append("' order by o.sendDate").toString());
    return q.getResultList();
  }

  public List<SysBilling> findSysBillingListByCriteria(String billingType, String documentno, String companyName, Date startDate, Date toDate) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysBilling m ");
    sb.append("where 1=1 ");

    if ((null != billingType) && (billingType.length() > 0)) {
      sb.append("and m.billingType = :billingType ");
    }

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentno like :documentno ");
    }

    if ((null != companyName) && (companyName.length() > 0)) {
      sb.append("and m.customerId.customerNameTh like :companyName ");
    }
    if (null != startDate) {
      sb.append("and m.sendDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.sendDate <= :toDate ");
    }
    sb.append("ORDER BY m.billingId DESC ");

    Query q = this.em.createQuery(sb.toString());
    if ((null != billingType) && (billingType.length() > 0)) {
      q.setParameter("billingType", billingType);
    }
    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }
    if ((null != companyName) && (companyName.length() > 0)) {
      q.setParameter("companyName", new StringBuilder().append("%").append(companyName).append("%").toString());
    }
    if (null != startDate) {
      q.setParameter("startDate", startDate);
    }
    if (null != toDate) {
      q.setParameter("toDate", toDate);
    }
    return q.getResultList();
  }

  public List<SysBilling> findSysBillingListByCriteria(String billingType, String documentno, String companyName, Date startDate, Date toDate, int[] range) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysBilling m ");
    sb.append("where 1=1 ");

    if ((null != billingType) && (billingType.length() > 0)) {
      sb.append("and m.billingType = :billingType ");
    }

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentno like :documentno ");
    }

    if ((null != companyName) && (companyName.length() > 0)) {
      sb.append("and m.customerId.customerNameTh like :companyName ");
    }
    if (null != startDate) {
      sb.append("and m.sendDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.sendDate <= :toDate ");
    }
    sb.append("ORDER BY m.billingId DESC ");

    Query q = this.em.createQuery(sb.toString());
    if ((null != billingType) && (billingType.length() > 0)) {
      q.setParameter("billingType", billingType);
    }
    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }
    if ((null != companyName) && (companyName.length() > 0)) {
      q.setParameter("companyName", new StringBuilder().append("%").append(companyName).append("%").toString());
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

  public int countSysBillingListByCriteria(String billingType, String documentno, String companyName, Date startDate, Date toDate) throws Exception
  {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT count(m) FROM SysBilling m ");
    sb.append("where 1=1 ");

    if ((null != billingType) && (billingType.length() > 0)) {
      sb.append("and m.billingType = :billingType ");
    }

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentno like :documentno ");
    }

    if ((null != companyName) && (companyName.length() > 0)) {
      sb.append("and m.customerId.customerNameTh like :companyName ");
    }
    if (null != startDate) {
      sb.append("and m.sendDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.sendDate <= :toDate ");
    }

    Query q = this.em.createQuery(sb.toString());
    if ((null != billingType) && (billingType.length() > 0)) {
      q.setParameter("billingType", billingType);
    }
    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }
    if ((null != companyName) && (companyName.length() > 0)) {
      q.setParameter("companyName", new StringBuilder().append("%").append(companyName).append("%").toString());
    }
    if (null != startDate) {
      q.setParameter("startDate", startDate);
    }
    if (null != toDate) {
      q.setParameter("toDate", toDate);
    }

    return ((Number)q.getSingleResult()).intValue();
  }

  public List<SysBilling> findSysBillingListByCriteriaForReport(List<String> listNotBillingType, String billingType, String documentno, String companyName, Date startDate, Date toDate, int[] range) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysBilling m ");
    sb.append("where 1=1 ");

    if (null != listNotBillingType) {
      sb.append("and m.billingType not in :notBillingType ");
    }

    if ((null != billingType) && (billingType.length() > 0)) {
      sb.append("and m.billingType = :billingType ");
    }

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentno like :documentno ");
    }

    if ((null != companyName) && (companyName.length() > 0)) {
      sb.append("and m.customerId.customerNameTh like :companyName ");
    }
    if (null != startDate) {
      sb.append("and m.sendDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.sendDate <= :toDate ");
    }
    sb.append("ORDER BY m.billingId DESC ");

    Query q = this.em.createQuery(sb.toString());
    if (null != listNotBillingType) {
      q.setParameter("notBillingType", listNotBillingType);
    }

    if ((null != billingType) && (billingType.length() > 0)) {
      q.setParameter("billingType", billingType);
    }

    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }
    if ((null != companyName) && (companyName.length() > 0)) {
      q.setParameter("companyName", new StringBuilder().append("%").append(companyName).append("%").toString());
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

  public int countSysBillingListByCriteriaForReport(List<String> listNotBillingType, String billingType, String documentno, String companyName, Date startDate, Date toDate) throws Exception
  {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT count(m) FROM SysBilling m ");
    sb.append("where 1=1 ");

    if (null != listNotBillingType) {
      sb.append("and m.billingType not in :notBillingType ");
    }

    if ((null != billingType) && (billingType.length() > 0)) {
      sb.append("and m.billingType = :billingType ");
    }

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentno like :documentno ");
    }

    if ((null != companyName) && (companyName.length() > 0)) {
      sb.append("and m.customerId.customerNameTh like :companyName ");
    }
    if (null != startDate) {
      sb.append("and m.sendDate >= :startDate ");
    }
    if (null != toDate) {
      sb.append("and m.sendDate <= :toDate ");
    }

    Query q = this.em.createQuery(sb.toString());
    if (null != listNotBillingType) {
      q.setParameter("notBillingType", listNotBillingType);
    }
    if ((null != billingType) && (billingType.length() > 0)) {
      q.setParameter("billingType", billingType);
    }
    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }
    if ((null != companyName) && (companyName.length() > 0)) {
      q.setParameter("companyName", new StringBuilder().append("%").append(companyName).append("%").toString());
    }
    if (null != startDate) {
      q.setParameter("startDate", startDate);
    }
    if (null != toDate) {
      q.setParameter("toDate", toDate);
    }

    return ((Number)q.getSingleResult()).intValue();
  }

  public List<SysBilling> findSysBillingWorkunitList(String billingType, String billingType2, SysWorkunit workunitId) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysBilling m ");
    sb.append("where 1=1 ");

    if (((null != billingType) && (billingType.length() > 0)) || ((null != billingType2) && (billingType2.length() > 0))) {
      sb.append("and m.billingType in :billingType ");
    }

    if (null != workunitId) {
      sb.append("and m.workunitId.workunitId = :workunitId ");
    }

    sb.append("ORDER BY m.billingId DESC ");

    Query q = this.em.createQuery(sb.toString());
    List listParameter = new ArrayList();
    listParameter.add(billingType);
    listParameter.add(billingType2);

    if (((null != billingType) && (billingType.length() > 0)) || ((null != billingType2) && (billingType2.length() > 0))) {
      q.setParameter("billingType", listParameter);
    }

    q.setParameter("workunitId", null != workunitId ? workunitId.getWorkunitId() : "");

    return q.getResultList();
  }

  public List<SysBilling> findSysBillingCustomerWorkunitListByCriteria(String billingType, String billingType2, String documentno, SysCustomer customerId, SysWorkunit workunitId) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("SELECT m FROM SysBilling m ");
    sb.append("where m.receiveMoney is null ");

    if (((null != billingType) && (billingType.length() > 0)) || ((null != billingType2) && (billingType2.length() > 0))) {
      sb.append("and m.billingType in :billingType ");
    }

    if ((null != documentno) && (documentno.length() > 0)) {
      sb.append("and m.documentno like :documentno ");
    }

    if (null != customerId) {
      sb.append("and m.customerId.customerId = :customerId ");
    }

    if (null != workunitId) {
      sb.append("and m.workunitId.workunitId = :workunitId ");
    }

    sb.append("ORDER BY m.billingId DESC ");

    Query q = this.em.createQuery(sb.toString());
    List listParameter = new ArrayList();
    listParameter.add(billingType);
    listParameter.add(billingType2);

    if (((null != billingType) && (billingType.length() > 0)) || ((null != billingType2) && (billingType2.length() > 0))) {
      q.setParameter("billingType", listParameter);
    }

    if ((null != documentno) && (documentno.length() > 0)) {
      q.setParameter("documentno", new StringBuilder().append("%").append(documentno).append("%").toString());
    }

    if (null != customerId) {
      q.setParameter("customerId", customerId.getCustomerId());
    }

    q.setParameter("workunitId", null != workunitId ? workunitId.getWorkunitId() : "");

    return q.getResultList();
  }
}