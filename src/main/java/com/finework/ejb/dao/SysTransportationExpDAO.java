/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.ejb.entity.SysTransportStaff;
import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysTranspostationExp;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysTransportationExpDAO extends AbstractDAO<SysTranspostationExp> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysTransportationExpDAO() {
        super(SysTranspostationExp.class);
    }
   
    public void deleteTransportationExpIdOnDetail(Integer expId) throws Exception {
        Query q = em.createQuery("delete from SysTransportationExpDetail o where o.exptpId.exptpId = :id");
        q.setParameter("id", expId);
        q.executeUpdate();
    }
    
    public List<SysTranspostationExp> findSysTranspostationExpList() {
        Query q = em.createQuery("select o from SysTranspostationExp o order by  o.exptpId desc");
        return q.getResultList();
    }
    
    public SysTranspostationExp findSysTranspostationExpById(Integer exptpId) {
        Query q = em.createQuery("select o from SysTranspostationExp o where o.exptpId =:exptpId ");
        q.setParameter("exptpId", exptpId );
        
        return (SysTranspostationExp)q.getSingleResult();
    }
     
    public List<SysTranspostationExp> findSysTranspostationExpListByCriteria(SysTransportStaff transportstaffId,Date startDate, Date toDate) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT m FROM SysTranspostationExp m ");
        sb.append("where 1=1 ");
        
        if (null != transportstaffId) {
            sb.append("and m.transportstaffId.transportstaffId = :transportstaffId ");
        }
  
        if (null != startDate) {
            sb.append("and m.exptpDate >= :startDate ");
        }
        if (null != toDate) {
            sb.append("and m.exptpDate <= :toDate ");
        }
        sb.append("ORDER BY m.exptpId DESC ");

        Query q = em.createQuery(sb.toString());
         if (null != transportstaffId) {
            q.setParameter("transportstaffId", transportstaffId.getTransportstaffId());
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
