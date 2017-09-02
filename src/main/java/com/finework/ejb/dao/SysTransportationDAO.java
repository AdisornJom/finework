/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.ejb.entity.SysTransportStaff;
import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysTransportation;
import com.finework.core.ejb.entity.SysWorkunit;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;


@Stateless
public class SysTransportationDAO extends AbstractDAO<SysTransportation> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysTransportationDAO() {
        super(SysTransportation.class);
    }
   
    
    public List<SysTransportation> findSysTransportationList() {
        Query q = em.createQuery("select o from SysTransportation o order by  o.transportId asc");
        return q.getResultList();
    }
    
    public SysTransportation findSysTransportationById(Integer transportId) {
        Query q = em.createQuery("select o from SysTransportation o where o.transportId =:transportId ");
        q.setParameter("transportId", transportId );
        
        return (SysTransportation)q.getSingleResult();
    }
     
    public List<SysTransportation> findSysTransportationListByCriteria(SysForeman foremanId,String documentno,SysWorkunit workunitId,Integer status,Date startDate, Date toDate) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT m FROM SysTransportation m ");
        sb.append("where 1=1 ");
        
        if (null != foremanId) {
            sb.append("and m.foremanId.foremanId = :foremanId ");
        }

        if (null != documentno && documentno.length() > 0) {
            sb.append("and m.documentNo like :documentno ");
        }
        
        if (null != workunitId) {
            sb.append("and m.workunitId.workunitId = :workunitId ");
        }
        
        if (null != status){
            sb.append("and m.status = :status ");
        }
                
        if (null != startDate) {
            sb.append("and m.tpOrderDate >= :startDate ");
        }
        if (null != toDate) {
            sb.append("and m.tpOrderDate <= :toDate ");
        }
        sb.append("ORDER BY m.transportId DESC ");

        Query q = em.createQuery(sb.toString());
         if (null != foremanId) {
            q.setParameter("foremanId", foremanId.getForemanId());
        }
        if (null != documentno && documentno.length() > 0) {
            q.setParameter("documentno", "%" + documentno + "%");
        }
        
        if (null != workunitId) {
           q.setParameter("workunitId", workunitId.getWorkunitId());
        }
        
        if (null != status){
            q.setParameter("status", status);
        }

        if (null != startDate) {
            q.setParameter("startDate", startDate);
        }
        if (null != toDate) {
            q.setParameter("toDate", toDate);
        }
        
        return q.getResultList();
    }
     
    //find staff
    public List<SysTransportation> findStaffSysTransportationListByCriteria(SysTransportStaff transportstaffId,Integer status,Date startDate, Date toDate) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT m FROM SysTransportation m ");
        sb.append("where 1=1  ");
        
        if (null != transportstaffId) {
            sb.append("and m.tpstaffId.transportstaffId = :transportstaffId ");
        }

        if (null != status){
            sb.append("and m.status = :status ");
        }
                
        if (null != startDate) {
            sb.append("and m.tpDateComplete >= :startDate ");
        }
        if (null != toDate) {
            sb.append("and m.tpDateComplete <= :toDate ");
        }
        sb.append("ORDER BY m.transportId DESC ");

        Query q = em.createQuery(sb.toString());
         if (null != transportstaffId) {
            q.setParameter("transportstaffId", transportstaffId.getTransportstaffId());
        }
        
        if (null != status){
            q.setParameter("status", status);
        }

        if (null != startDate) {
            q.setParameter("startDate", startDate, TemporalType.DATE);
        }
        if (null != toDate) {
            q.setParameter("toDate", toDate,TemporalType.DATE);
        }
        
        return q.getResultList();
    }
    
     public List<SysTransportation> findStafffollow1SysTransportationListByCriteria(SysTransportStaff transportstaffId,Integer status,Date startDate, Date toDate) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT m FROM SysTransportation m ");
        sb.append("where 1=1  ");
        
        if (null != transportstaffId) {
            sb.append("and m.tpcarstaffId1.transportstaffId = :transportstaffId ");
        }

        if (null != status){
            sb.append("and m.status = :status ");
        }
                
        if (null != startDate) {
            sb.append("and m.tpDateComplete >= :startDate ");
        }
        if (null != toDate) {
            sb.append("and m.tpDateComplete <= :toDate ");
        }
        sb.append("ORDER BY m.transportId DESC ");

        Query q = em.createQuery(sb.toString());
         if (null != transportstaffId) {
            q.setParameter("transportstaffId", transportstaffId.getTransportstaffId());
        }
        
        if (null != status){
            q.setParameter("status", status);
        }

        if (null != startDate) {
            q.setParameter("startDate", startDate, TemporalType.DATE);
        }
        if (null != toDate) {
            q.setParameter("toDate", toDate,TemporalType.DATE);
        }
        
        return q.getResultList();
    }
     
      public List<SysTransportation> findStafffollow2SysTransportationListByCriteria(SysTransportStaff transportstaffId,Integer status,Date startDate, Date toDate) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT m FROM SysTransportation m ");
        sb.append("where 1=1  ");
        
        if (null != transportstaffId) {
            sb.append("and m.tpcarstaffId2.transportstaffId = :transportstaffId ");
        }

        if (null != status){
            sb.append("and m.status = :status ");
        }
                
        if (null != startDate) {
            sb.append("and m.tpDateComplete >= :startDate ");
        }
        if (null != toDate) {
            sb.append("and m.tpDateComplete <= :toDate ");
        }
        sb.append("ORDER BY m.transportId DESC ");

        Query q = em.createQuery(sb.toString());
         if (null != transportstaffId) {
            q.setParameter("transportstaffId", transportstaffId.getTransportstaffId());
        }
        
        if (null != status){
            q.setParameter("status", status);
        }

         if (null != startDate) {
            q.setParameter("startDate", startDate, TemporalType.DATE);
        }
        if (null != toDate) {
            q.setParameter("toDate", toDate,TemporalType.DATE);
        }
        
        return q.getResultList();
    }
     
   
}
