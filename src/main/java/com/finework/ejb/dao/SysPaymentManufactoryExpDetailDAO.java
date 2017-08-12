/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysPaymentManufactoryExpdetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysPaymentManufactoryExpDetailDAO extends AbstractDAO<SysPaymentManufactoryExpdetail> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysPaymentManufactoryExpDetailDAO() {
        super(SysPaymentManufactoryExpdetail.class);
    }
    
    public void deletePaymentManufactoryExpensesIdOnDetail(Integer payment_factory_id) throws Exception {
        Query q = em.createQuery("delete from SysPaymentManufactoryExpdetail o where o.paymentFactoryId.paymentFactoryId = :id");
        q.setParameter("id", payment_factory_id);
        q.executeUpdate();
    }
     
}
