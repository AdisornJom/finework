/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysExpensesManufactoryDeduction;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysExpensesManufactoryDeductionDAO extends AbstractDAO<SysExpensesManufactoryDeduction> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysExpensesManufactoryDeductionDAO() {
        super(SysExpensesManufactoryDeduction.class);
    }
    
    
    public List<SysExpensesManufactoryDeduction> findSysExpensesManufactoryDeductionList() {
        Query q = em.createQuery("select o from SysExpensesManufactoryDeduction o where o.status ='Y' order by  deductionDesc asc");
        return q.getResultList();
    }
    
    public SysExpensesManufactoryDeduction findSysExpensesManufactoryDeductionById(Integer deductionId) {
        Query q = em.createQuery("select o from SysExpensesManufactoryDeduction o where o.deductionId =:deductionId ");
        q.setParameter("deductionId", deductionId );
        
        return (SysExpensesManufactoryDeduction)q.getSingleResult();
    }
     
     public List<SysExpensesManufactoryDeduction> findSysExpensesManufactoryDeductionListByCriteria(String deductionName,String status) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o FROM SysExpensesManufactoryDeduction o ");
        sb.append("where 1=1");
        if(null != deductionName && deductionName.length() > 0){
            sb.append("and o.deductionDesc like :deductionName ");
        }
        if(null != status && status.length() > 0){
            sb.append("and o.status =:status  ");
        }

        Query q = em.createQuery(sb.toString());
        if(null != deductionName && deductionName.length() > 0){
            q.setParameter("deductionName", "%" + deductionName + "%");
        }
        if(null != status && status.length() > 0){
            q.setParameter("status", status );
        }
        sb.append(" order by createdDt desc");
        return q.getResultList();
    }
     
   
}
