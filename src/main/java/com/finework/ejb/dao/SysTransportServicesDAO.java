/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysTransportServices;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysTransportServicesDAO extends AbstractDAO<SysTransportServices> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysTransportServicesDAO() {
        super(SysTransportServices.class);
    }
    
    
    public List<SysTransportServices> findSysTransportServicesList() {
        Query q = em.createQuery("select o from SysTransportServices o where o.status ='Y' order by  tpserviceDesc asc");
        return q.getResultList();
    }
    
    public SysTransportServices findSysTransportServicesById(Integer tpserviceId) {
        Query q = em.createQuery("select o from SysTransportServices o where o.tpserviceId =:tpserviceId and o.status ='Y' ");
        q.setParameter("tpserviceId", tpserviceId );
        
        return (SysTransportServices)q.getSingleResult();
    }
     
     public List<SysTransportServices> findSysTransportServicesListByCriteria(String tpserviceDesc,String status) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o FROM SysTransportServices o ");
        sb.append("where 1=1");
        if(null != tpserviceDesc && tpserviceDesc.length() > 0){
            sb.append("and o.tpserviceDesc like :tpserviceDesc ");
        }
        if(null != status && status.length() > 0){
            sb.append("and o.status =:status  ");
        }

        Query q = em.createQuery(sb.toString());
        if(null != tpserviceDesc && tpserviceDesc.length() > 0){
            q.setParameter("tpserviceDesc", "%" + tpserviceDesc + "%");
        }
        if(null != status && status.length() > 0){
            q.setParameter("status", status );
        }
        sb.append(" order by createdDt desc");
        return q.getResultList();
    }
     
   
}
