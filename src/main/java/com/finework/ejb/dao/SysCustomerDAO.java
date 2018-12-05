/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysCustomer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysCustomerDAO extends AbstractDAO<SysCustomer> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysCustomerDAO() {
        super(SysCustomer.class);
    }
    
    
    public List<SysCustomer> findSysCustomerList() {
        Query q = em.createQuery("select o from SysCustomer o where o.status ='Y'");
        return q.getResultList();
    }
     
     public List<SysCustomer> findSysCustomerByCriteria(String customerNameTh,String status) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o FROM SysCustomer o ");
        sb.append("where 1=1 ");
        if(null != customerNameTh && customerNameTh.length() > 0){
            sb.append("and o.customerNameTh like :customerNameTh ");
        }
        if(null != status && status.length() > 0){
            sb.append("and o.status =:status  ");
        }
        sb.append(" order by createdDt desc");
        
        Query q = em.createQuery(sb.toString());
        if(null != customerNameTh && customerNameTh.length() > 0){
            q.setParameter("customerNameTh", "%" + customerNameTh + "%");
        }
        if(null != status && status.length() > 0){
            q.setParameter("status", status );
        }
        
        
        return q.getResultList();
    }
     
    public SysCustomer findSysCustomerById(Integer customerId) {
        Query q = em.createQuery("select o from SysCustomer o where o.customerId =:customerId  order by  customerNameTh asc");
        q.setParameter("customerId", customerId );
        
        return (SysCustomer)q.getSingleResult();
    }
}
