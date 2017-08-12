/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysDetail;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysDetailDAO extends AbstractDAO<SysDetail> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysDetailDAO() {
        super(SysDetail.class);
    }
    
    
    public List<SysDetail> findSySDetailList() {
        Query q = em.createQuery("select o from SysDetail o where o.status ='Y' order by  detailDesc asc");
        return q.getResultList();
    }
    
    public SysDetail findSysDetailById(Integer detailId) {
        Query q = em.createQuery("select o from SysDetail o where o.detailId =:detailId order by  detailDesc asc");
        q.setParameter("detailId", detailId );
        
        return (SysDetail)q.getSingleResult();
    }
     
     public List<SysDetail> findSySDetailListByCriteria(String itemname,String status) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o FROM SysDetail o ");
        sb.append("where 1=1");
        if(null != itemname && itemname.length() > 0){
            sb.append("and o.detailDesc like :itemname ");
        }
        if(null != status && status.length() > 0){
            sb.append("and o.status =:status  ");
        }

        Query q = em.createQuery(sb.toString());
        if(null != itemname && itemname.length() > 0){
            q.setParameter("itemname", "%" + itemname + "%");
        }
        if(null != status && status.length() > 0){
            q.setParameter("status", status );
        }
        sb.append(" order by detailDesc asc");
        return q.getResultList();
    }
     
      public List<SysDetail> findSySDetailListByHousePlan(Integer housePlanId,String status) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT o FROM SysDetail o ");
        sb.append("where 1=1");
        if(null != housePlanId){
            sb.append("and o.housePlanId.detailId = :housePlanId ");
        }
        if(null != status && status.length() > 0){
            sb.append("and o.status =:status  ");
        }

        Query q = em.createQuery(sb.toString());
        if(null != housePlanId){
            q.setParameter("housePlanId",housePlanId );
        }
        if(null != status && status.length() > 0){
            q.setParameter("status", status );
        }
        sb.append(" order by detailDesc asc");
        return q.getResultList();
    }
     
     
}
