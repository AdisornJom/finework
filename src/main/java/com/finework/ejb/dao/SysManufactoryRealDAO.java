/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysContractor;
import com.finework.core.ejb.entity.SysForeman;
import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysManufactoryReal;
import com.finework.core.ejb.entity.SysWorkunit;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysManufactoryRealDAO extends AbstractDAO<SysManufactoryReal> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysManufactoryRealDAO() {
        super(SysManufactoryReal.class);
    }
    
    public List<SysManufactoryReal> findSysManufactoryRealList() {
        Query q = em.createQuery("select o from SysManufactoryReal o  order by o.successDate");
        return q.getResultList();
    }
    
    public void updateStatusSysManufactoryReal(int status,SysManufactoryReal sysManufactoryReal){
        Query q = em.createQuery("update SysManufactoryReal o set o.status=:status where o.factoryId.factoryId=:factoryId");
        q.setParameter("status", status);
        q.setParameter("factoryId", sysManufactoryReal.getFactoryId().getFactoryId());
        q.executeUpdate();
    }
    
    public void updateStatusTransporterSysManufactoryReal(int statusTransporter,SysManufactoryReal sysManufactoryReal){
        Query q = em.createQuery("update SysManufactoryReal o set o.statusTransporter=:statusTransporter where o.factoryRealId=:factoryRealId");
        q.setParameter("statusTransporter", statusTransporter);
        q.setParameter("factoryRealId", sysManufactoryReal.getFactoryRealId());
        q.executeUpdate();
    }
    
     public void updateStatusSysManufactoryRealByfactoryRealId(int status,int factoryRealId){
        Query q = em.createQuery("update SysManufactoryReal o set o.status=:status where o.factoryRealId=:factoryRealId");
        q.setParameter("status", status);
        q.setParameter("factoryRealId", factoryRealId);
        q.executeUpdate();
    }
     
   public void deleteSysManufactoryRealByfactoryRealId(int factoryRealId){
        Query q = em.createQuery("delete SysManufactoryReal o  where o.factoryRealId=:factoryRealId");
        q.setParameter("factoryRealId", factoryRealId);
        q.executeUpdate();
    }
    
     public int findSysManufactoryRealListByFactoryId(Integer factoryId) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(m) FROM SysManufactoryReal m ");
        sb.append("where 1=1 ");
          
        if (null != factoryId) {
            sb.append("and m.factoryId.factoryId = :factoryId ");
        }
       
        Query q = em.createQuery(sb.toString());
        if (null != factoryId) {
            q.setParameter("factoryId", factoryId);
        }
        return ((Number) q.getSingleResult()).intValue();
     }
     
      //status_transporter 0=normal, 1=on_process, 2=Complete
    public List<SysManufactoryReal> findTranSporterSysManufactoryRealListByCriteria(String documentno,SysForeman foremanId,SysWorkunit workUnitId, Date startDate, Date toDate,int statusTransporter) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT m FROM SysManufactoryReal m ");
        sb.append("where 1=1 ");
        
        if (null != documentno && documentno.length() > 0) {
            sb.append("and m.factoryId.documentno like :documentno ");
        }   
        if (null != foremanId) {
            sb.append("and m.factoryId.foremanId.foremanId = :foremanId ");
        }
        if (null != workUnitId) {
           sb.append("and (m.manufactoryDetailId.workunitId.workunitId = :workUnitId or m.manufactoryDetailId.workunitId is null) ");
        }
        if (null != startDate) {
            sb.append("and m.successDate >= :startDate ");
        }
        if (null != toDate) {
            sb.append("and m.successDate <= :toDate ");
        }
        
        if (0 != statusTransporter) {
            sb.append("and m.statusTransporter = :statusTransporter ");
        }
        
        sb.append("ORDER BY m.factoryRealId DESC ");

        Query q = em.createQuery(sb.toString());
        if (null != documentno && documentno.length() > 0) {
            q.setParameter("documentno", "%" + documentno + "%");
        }
        if (null != foremanId) {
            q.setParameter("foremanId", foremanId.getForemanId());
        }
        if (null != workUnitId) {
            q.setParameter("workUnitId", workUnitId.getWorkunitId());
        }
        if (null != startDate) {
            q.setParameter("startDate", startDate);
        }
        if (null != toDate) {
            q.setParameter("toDate", toDate);
        }
        if (0 != statusTransporter) {
            q.setParameter("statusTransporter", statusTransporter);
        }
        return q.getResultList();
    }
    
    //status 1=normal, 2=payment
    public List<SysManufactoryReal> findSysManufactoryRealListByCriteria(String documentno,SysContractor contractor,SysForeman foreman,Date startDate, Date toDate,int status) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT m FROM SysManufactoryReal m ");
        sb.append("where 1=1 ");
        
        if (null != documentno && documentno.length() > 0) {
            sb.append("and m.factoryId.documentno like :documentno ");
        }
                
        if (null != contractor) {
            sb.append("and m.factoryId.contractorId.contractorId = :contractor ");
        }
        if (null != foreman) {
            sb.append("and m.factoryId.foremanId.foremanId = :foreman ");
        }
        if (null != startDate) {
            sb.append("and m.successDate >= :startDate ");
        }
        if (null != toDate) {
            sb.append("and m.successDate <= :toDate ");
        }
        
        if (0 != status) {
            sb.append("and m.status = :status ");
        }
        
        sb.append("ORDER BY m.factoryRealId DESC ");

        Query q = em.createQuery(sb.toString());
        if (null != documentno && documentno.length() > 0) {
            q.setParameter("documentno", "%" + documentno + "%");
        }
        if (null != contractor) {
            q.setParameter("contractor", contractor.getContractorId());
        }
        if (null != foreman) {
            q.setParameter("foreman", foreman.getForemanId());
        }
        if (null != startDate) {
            q.setParameter("startDate", startDate);
        }
        if (null != toDate) {
            q.setParameter("toDate", toDate);
        }
        if (0 != status) {
            q.setParameter("status", status);
        }
        return q.getResultList();
    }
    
    public List<SysManufactoryReal> findSysManufactoryRealListByCriteria(String documentno,SysContractor contractor, Date startDate, Date toDate, int[] range) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT m FROM SysManufactoryReal m ");
        sb.append("where 1=1 ");

        if (null != documentno && documentno.length() > 0) {
            sb.append("and m.factoryId.documentno like :documentno ");
        }
                
        if (null != contractor ) {
            sb.append("and m.factoryId.contractorId.contractorId = :contractor ");
        }
        if (null != startDate) {
            sb.append("and m.successDate >= :startDate ");
        }
        if (null != toDate) {
            sb.append("and m.successDate <= :toDate ");
        }
        sb.append("ORDER BY m.factoryRealId DESC ");

        Query q = em.createQuery(sb.toString());
        if (null != documentno && documentno.length() > 0) {
            q.setParameter("documentno", "%" + documentno + "%");
        }
        if (null != contractor) {
            q.setParameter("contractor", contractor.getContractorId());
        }
        if (null != startDate) {
            q.setParameter("startDate", startDate);
        }
        if (null != toDate) {
            q.setParameter("toDate", toDate);
        }
        
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
     
     
    public int countSysManufactoryRealListByCriteria(String documentno,SysContractor contractor, Date startDate, Date toDate) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(m) FROM SysManufactoryReal m ");
        sb.append("where 1=1 ");

        if (null != documentno && documentno.length() > 0) {
           sb.append("and m.factoryId.documentno like :documentno ");
        }
                
        if (null != contractor ) {
           sb.append("and m.factoryId.contractorId.contractorId = :contractor ");
        }
        if (null != startDate) {
            sb.append("and m.successDate >= :startDate ");
        }
        if (null != toDate) {
            sb.append("and m.successDate <= :toDate ");
        }

        Query q = em.createQuery(sb.toString());
         if (null != documentno && documentno.length() > 0) {
            q.setParameter("documentno", "%" + documentno + "%");
        }
        if (null != contractor) {
            q.setParameter("contractor", contractor.getContractorId());
        }
        if (null != startDate) {
            q.setParameter("startDate", startDate);
        }
        if (null != toDate) {
            q.setParameter("toDate", toDate);
        }
        

        return ((Number) q.getSingleResult()).intValue();
    }
    
    
     public List<SysManufactoryReal> findSysManufactoryRealByManufactoryDetailId(Integer manufactoryDetailId) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT m FROM SysManufactoryReal m ");
        sb.append("where 1=1 ");
        
        if (null != manufactoryDetailId) {
            sb.append("and m.manufactoryDetailId.manufactoryDetailId = :manufactoryDetailId  ");
        }
               
        sb.append("ORDER BY m.factoryRealId DESC ");

        Query q = em.createQuery(sb.toString());
        if (null != manufactoryDetailId) {
            q.setParameter("manufactoryDetailId", manufactoryDetailId);
        }
        return q.getResultList();
    }
    
}
