/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysBillingDetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysBillingDetailDAO extends AbstractDAO<SysBillingDetail> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysBillingDetailDAO() {
        super(SysBillingDetail.class);
    }
    
    public void deleteBillingIdOnDetail(Integer billingId) throws Exception {
        Query q = em.createQuery("delete from SysBillingDetail o where o.billingId.billingId = :id");
        q.setParameter("id", billingId);
        q.executeUpdate();
    }
     
}
