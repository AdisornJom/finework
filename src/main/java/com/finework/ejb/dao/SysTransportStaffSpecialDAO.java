/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysTransportStaff;
import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysTransportStaffSpecial;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysTransportStaffSpecialDAO extends AbstractDAO<SysTransportStaffSpecial> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysTransportStaffSpecialDAO() {
        super(SysTransportStaffSpecial.class);
    }
   
    public void deleteSysTransportStaffSpecialIdOnDetail(Integer specialtpId) throws Exception {
        Query q = em.createQuery("delete from SysTransportStaffSpecialDetail o where o.specialtpId.specialtpId = :id");
        q.setParameter("id", specialtpId);
        q.executeUpdate();
    }
    
    public List<SysTransportStaffSpecial> findSysTransportStaffSpecialList() {
        Query q = em.createQuery("select o from SysTransportStaffSpecial o order by  o.specialtpId desc");
        return q.getResultList();
    }
    
    public SysTransportStaffSpecial findSysTransportStaffSpecialById(Integer specialtpId) {
        Query q = em.createQuery("select o from SysTransportStaffSpecial o where o.specialtpId =:specialtpId ");
        q.setParameter("specialtpId", specialtpId );
        
        return (SysTransportStaffSpecial)q.getSingleResult();
    }
     
    public List<SysTransportStaffSpecial> findSysTransportStaffSpecialListByCriteria(SysTransportStaff transportstaffId,Integer specialType,Date startDate, Date toDate) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT m FROM SysTransportStaffSpecial m ");
        sb.append("where 1=1 ");
        
        if (null != transportstaffId) {
            sb.append("and m.transportstaffId.transportstaffId = :transportstaffId ");
        }
        
        if (null != specialType) {
            sb.append("and m.specialType = :specialType ");
        }
  
        if (null != startDate) {
            sb.append("and m.specialtpDate >= :startDate ");
        }
        if (null != toDate) {
            sb.append("and m.specialtpDate <= :toDate ");
        }
        sb.append("ORDER BY m.specialtpId DESC ");

        Query q = em.createQuery(sb.toString());
        if (null != transportstaffId) {
            q.setParameter("transportstaffId", transportstaffId.getTransportstaffId());
        }

        if (null != specialType) {
           q.setParameter("specialType",specialType);
        }

        if (null != startDate) {
            q.setParameter("startDate", startDate);
        }
        if (null != toDate) {
            q.setParameter("toDate", toDate);
        }
        
        return q.getResultList();
    }
   
}
