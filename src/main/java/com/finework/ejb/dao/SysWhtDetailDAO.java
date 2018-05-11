/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.ejb.dao;

import com.finework.core.util.Persistence;
import com.finework.core.ejb.entity.SysWhtDetail;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class SysWhtDetailDAO extends AbstractDAO<SysWhtDetail> {

    @PersistenceContext(unitName = Persistence.finework)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SysWhtDetailDAO() {
        super(SysWhtDetail.class);
    }
    
    public void deleteWhtIdOnDetail(Integer whtId) throws Exception {
        Query q = em.createQuery("delete from SysWhtDetail o where o.whtId.whtId = :id");
        q.setParameter("id", whtId);
        q.executeUpdate();
    }
     
}
