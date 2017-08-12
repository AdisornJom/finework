/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysExpensesManufactoryDetail;
import com.finework.core.util.Persistence;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysExpensesManufactoryDetailDAO extends AbstractDAO<SysExpensesManufactoryDetail> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysExpensesManufactoryDetailDAO() {
        super(SysExpensesManufactoryDetail.class);
    }
    
    public void deleteExpensesManufactoryIdOnDetail(Integer expensesId) throws Exception {
        Query q = em.createQuery("delete from SysExpensesManufactoryDetail o where o.expensesId.expensesId = :id");
        q.setParameter("id", expensesId);
        q.executeUpdate();
    }
     
}
