/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysSequence;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;


@Stateless
public class SysSequenceDAO extends AbstractDAO<SysSequence> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysSequenceDAO() {
        super(SysSequence.class);
    }
    
    
    public List<SysSequence> findSysSequenceList() {
        Query q = em.createQuery("select o from SysSequence o ");
        return q.getResultList();
    }
     
     public List<SysSequence> findSysSequenceListByCriteria(String custName) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o FROM SysSequence o ");
        sb.append("where 1=1");
        if(null != custName && custName.length() > 0){
            sb.append("and o.customerId.customerNameTh like :custName ");
        }

        Query q = em.createQuery(sb.toString());
        if(null != custName && custName.length() > 0){
            q.setParameter("custName", "%" + custName + "%");
        }

        return q.getResultList();
    }
     
    public SysSequence findSysSequenceByCustomerId(Integer customerId) {
        Query q = em.createQuery("select o from SysSequence o "
                + "where o.customerId.customerId =:customerId and o.status='Y' ");
        q.setParameter("customerId", customerId );
       
        try {
            return (SysSequence)q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
     public SysSequence findSysSequenceByCustomerIdRunningType(Integer customerId,String runningType) {        
        StringBuilder sb = new StringBuilder();
        sb.append("select o from SysSequence o ");
        sb.append("where 1=1 ");

        if (null != customerId ) {
            sb.append("and o.customerId.customerId =:customerId ");
        }
        if (null != runningType) {
            sb.append("and o.runningType = :runningType ");
        }

        Query q = em.createQuery(sb.toString());
        if (null != customerId ) {
            q.setParameter("customerId", customerId);
        }
        if (null != runningType) {
            q.setParameter("runningType", runningType);
        }
        
        try {
            return (SysSequence)q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
     
    public List<SysCustomer> findCustNotSequenceList() {

        Query q = em.createQuery("select o from SysCustomer o "
                + "where o.customerId not in(select c.customerId.customerId from SysSequence c)"); 
        
        return q.getResultList();
    }
    
}
