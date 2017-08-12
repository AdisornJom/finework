/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysLogisticCar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysLogisticCarDAO extends AbstractDAO<SysLogisticCar> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysLogisticCarDAO() {
        super(SysLogisticCar.class);
    }
    
    
    public List<SysLogisticCar> findSysLogisticCarList() {
        Query q = em.createQuery("select o from SysLogisticCar o where o.status ='Y' ");
        return q.getResultList();
    }
     
     public List<SysLogisticCar> findSysLogisticCarByCriteria(String logisticRegisterCar,String logisticCarType,String status) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o FROM SysLogisticCar o ");
        sb.append("where 1=1 ");
        if(null != logisticRegisterCar && logisticRegisterCar.length() > 0){
            sb.append("and o.logisticRegisterCar like :logisticRegisterCar ");
        }
        if(null != logisticCarType && logisticCarType.length() > 0){
            sb.append("and o.logisticCarType =:logisticCarType  ");
        }
        if(null != status && status.length() > 0){
            sb.append("and o.status =:status  ");
        }

        sb.append("ORDER BY o.logisticId DESC ");
         
        Query q = em.createQuery(sb.toString());
        if(null != logisticRegisterCar && logisticRegisterCar.length() > 0){
            q.setParameter("logisticRegisterCar", "%" + logisticRegisterCar + "%");
        }
        if(null != logisticCarType && logisticCarType.length() > 0){
            q.setParameter("logisticCarType",  logisticCarType );
        }
        if(null != status && status.length() > 0){
            q.setParameter("status", status );
        }
        return q.getResultList();
    }
     
    public SysLogisticCar findSysLogisticCarById(Integer logisticId) {
        Query q = em.createQuery("select o from SysLogisticCar o where o.logisticId =:logisticId  order by  logisticId asc");
        q.setParameter("logisticId", logisticId );
        
        return (SysLogisticCar)q.getSingleResult();
    }
}
