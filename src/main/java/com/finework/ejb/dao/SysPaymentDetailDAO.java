/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysPaymentDetail;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysPaymentDetailDAO extends AbstractDAO<SysPaymentDetail> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysPaymentDetailDAO() {
        super(SysPaymentDetail.class);
    }
    
    public List<SysPaymentDetail> findSysPaymentDetailList(Integer paymentId) throws Exception {
        Query q = em.createQuery("select o from SysPaymentDetail o  where o.paymentId.paymentId = :id");
        q.setParameter("id", paymentId);
        return q.getResultList();
    }
    
    public void deletePaymentIdOnDetail(Integer paymentId) throws Exception {
        Query q = em.createQuery("delete from SysPaymentDetail o where o.paymentId.paymentId = :id");
        q.setParameter("id", paymentId);
        q.executeUpdate();
    }

     
}
