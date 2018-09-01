/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysMainQuotation;
import com.finework.core.ejb.entity.SysCustomer;
import com.finework.core.ejb.entity.SysWorkunit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysQuotationDAO extends AbstractDAO<SysMainQuotation> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysQuotationDAO() {
        super(SysMainQuotation.class);
    }

    
    public List<SysMainQuotation> findSysMainQuotationListByCriteria(String documentno,String subject,Date startDate, Date toDate) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT m FROM SysMainQuotation m ");
        sb.append("where 1=1 ");

        if (null != documentno && documentno.length() > 0) {
            sb.append("and m.documentno like :documentno ");
        }
                
        if (null != subject && subject.length() > 0) {
            sb.append("and m.subject like :subject ");
        }
        if (null != startDate) {
            sb.append("and m.quotationDate >= :startDate ");
        }
        if (null != toDate) {
            sb.append("and m.quotationDate <= :toDate ");
        }
        sb.append("ORDER BY m.quotationId DESC ");

        Query q = em.createQuery(sb.toString());
        if (null != documentno && documentno.length() > 0) {
            q.setParameter("documentno", "%" + documentno + "%");
        }
        if (null != subject && subject.length() > 0) {
            q.setParameter("subject", "%" + subject + "%");
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
