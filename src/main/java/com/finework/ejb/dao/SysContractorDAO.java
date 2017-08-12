/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysContractor;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysContractorDAO extends AbstractDAO<SysContractor> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysContractorDAO() {
        super(SysContractor.class);
    }
    
    
    public List<SysContractor> findSysContractorList() {
        Query q = em.createQuery("select o from SysContractor o where o.status ='Y'");
        return q.getResultList();
    }
     
     public List<SysContractor> findSysContractorByCriteria(String customerNameTh,String status) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o FROM SysContractor o ");
        sb.append("where 1=1 ");
        if(null != customerNameTh && customerNameTh.length() > 0){
            sb.append("and o.contractorNameTh like :contractorNameTh ");
        }
        if(null != status && status.length() > 0){
            sb.append("and o.status =:status  ");
        }

        Query q = em.createQuery(sb.toString());
        if(null != customerNameTh && customerNameTh.length() > 0){
            q.setParameter("contractorNameTh", "%" + customerNameTh + "%");
        }
        if(null != status && status.length() > 0){
            q.setParameter("status", status );
        }
        return q.getResultList();
    }
     
    public SysContractor findSysContractorById(Integer contractorId) {
        Query q = em.createQuery("select o from SysContractor o where o.contractorId =:contractorId  order by  contractorNameTh asc");
        q.setParameter("contractorId", contractorId );
        
        return (SysContractor)q.getSingleResult();
    }
}
