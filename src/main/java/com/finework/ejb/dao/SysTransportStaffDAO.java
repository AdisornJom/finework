/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysTransportStaff;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysTransportStaffDAO extends AbstractDAO<SysTransportStaff> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysTransportStaffDAO() {
        super(SysTransportStaff.class);
    }
    
    public List<SysTransportStaff> findSysTransportStaffList() {
        Query q = em.createQuery("select o from SysTransportStaff o where o.status ='Y'  order by o.transportstaffId asc");
        return q.getResultList();
    }
    
    public List<SysTransportStaff> findSysTransportStaffList(Integer transportstaffType) {
        Query q = em.createQuery("select o from SysTransportStaff o where o.status ='Y' and o.transportstaffType=:transportstaffType order by o.transportstaffId asc");
        q.setParameter("transportstaffType", transportstaffType );
        return q.getResultList();
    }
     
     public List<SysTransportStaff> findSysTransportStaffByCriteria(String transportstaffNameTh,Integer tranSportStaffType,String status) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o FROM SysTransportStaff o ");
        sb.append("where 1=1 ");
        if(null != transportstaffNameTh && transportstaffNameTh.length() > 0){
            sb.append("and o.transportstaffNameTh like :transportstaffNameTh ");
        }
        if(null != tranSportStaffType){
            sb.append("and o.transportstaffType =:transportstaffType  ");
        }
        if(null != status && status.length() > 0){
            sb.append("and o.status =:status  ");
        }

        Query q = em.createQuery(sb.toString());
        if(null != transportstaffNameTh && transportstaffNameTh.length() > 0){
            q.setParameter("transportstaffNameTh", "%" + transportstaffNameTh + "%");
        }
        if(null != tranSportStaffType){
            q.setParameter("transportstaffType", tranSportStaffType );
        }
        if(null != status && status.length() > 0){
            q.setParameter("status", status );
        }
        return q.getResultList();
    }
     
    public SysTransportStaff findSysTransportStaffById(Integer tranSportStaffId,Integer transportstaffType) {
        Query q = em.createQuery("select o from SysTransportStaff o where o.transportstaffId =:transportstaffId and o.transportstaffType=:transportstaffType order by o.transportstaffId asc");
        q.setParameter("transportstaffId", tranSportStaffId );
        q.setParameter("transportstaffType", transportstaffType );
        
        return (SysTransportStaff)q.getSingleResult();
    }
}
