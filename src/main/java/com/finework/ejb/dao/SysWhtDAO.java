/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysWht;
import com.finework.core.ejb.entity.SysCustomer;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysWhtDAO extends AbstractDAO<SysWht> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysWhtDAO() {
        super(SysWht.class);
    }
    
    public List<SysWht> findSysWhtListByCriteria(String documentno,SysCustomer sysCustomer, Date startDate, Date toDate) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT m FROM SysWht m ");
        sb.append("where 1=1 ");
        
        if (null != documentno && documentno.length() > 0) {
            sb.append("and m.documentno like :documentno ");
        }
                
        if (null != sysCustomer) {
            sb.append("and m.customerId.customerId like :customer ");
        }
        if (null != startDate) {
            sb.append("and m.whtDate >= :startDate ");
        }
        if (null != toDate) {
            sb.append("and m.whtDate <= :toDate ");
        }
        sb.append("ORDER BY m.whtDate DESC ");

        Query q = em.createQuery(sb.toString());
        if (null != documentno && documentno.length() > 0) {
            q.setParameter("documentno", "%" + documentno + "%");
        }
        if (null != sysCustomer) {
            q.setParameter("customer", sysCustomer.getCustomerId());
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
