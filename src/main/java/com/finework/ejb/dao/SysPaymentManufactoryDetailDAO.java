/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysPaymentManufactoryDetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysPaymentManufactoryDetailDAO extends AbstractDAO<SysPaymentManufactoryDetail> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysPaymentManufactoryDetailDAO() {
        super(SysPaymentManufactoryDetail.class);
    }
    
    public void deletePaymentManufactoryIdOnDetail(Integer payment_factory_id) throws Exception {
        Query q = em.createQuery("delete from SysPaymentManufactoryDetail o where o.paymentFactoryId.paymentFactoryId = :id");
        q.setParameter("id", payment_factory_id);
        q.executeUpdate();
    }
     
}
