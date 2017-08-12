/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysPrepareTransportDetail;
import com.finework.core.ejb.entity.SysWorkunit;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysPrepareTransportDetailDAO extends AbstractDAO<SysPrepareTransportDetail> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysPrepareTransportDetailDAO() {
        super(SysPrepareTransportDetail.class);
    }
    
    public void deletePrepareTpIdOnDetail(Integer prepareTpId) throws Exception {
        Query q = em.createQuery("delete from SysPrepareTransportDetail o where o.prepareTpId.prepareTpId = :id");
        q.setParameter("id", prepareTpId);
        q.executeUpdate();
    }
    
    public List<SysPrepareTransportDetail> findSysPrepareTransportDetailByPrepareID(Integer prepareTpId) throws Exception {
        Query q = em.createQuery("select o from SysPrepareTransportDetail o where o.prepareTpId.prepareTpId = :id");
        q.setParameter("id", prepareTpId);
        return q.getResultList();
    }
    
    public List<SysPrepareTransportDetail> findSysPrepareTransportDetailByCriteria(SysWorkunit sysWorkunit,Date startDate, Date toDate) throws Exception {
     /*   StringBuilder sb = new StringBuilder();
        sb.append("SELECT m FROM SysPrepareTransportDetail m ");
        sb.append("where 1=1 ");

        if (null != sysWorkunit) {
            sb.append("and m.workunitId.workunitId = :workunitId ");
        }
        if (null != startDate) {
            sb.append("and m.factoryId.factoryDate >= :startDate ");
        }
        if (null != toDate) {
            sb.append("and m.factoryId.factoryDate <= :toDate ");
        }
        sb.append("ORDER BY m.factoryId.factoryId DESC ");

        Query q = em.createQuery(sb.toString());
        if (null != sysWorkunit) {
            q.setParameter("workunitId", sysWorkunit.getWorkunitId());
        }
        if (null != startDate) {
            q.setParameter("startDate", startDate);
        }
        if (null != toDate) {
            q.setParameter("toDate", toDate);
        }
        return q.getResultList();*/
       return null;
    }
    
     
}
